package com.pinturerias.sucursal.servicio;

import com.pinturerias.compartidos.dto.EtiquetasProductoDTO;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.configuracion.TenantExecutor;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import com.pinturerias.general.entidad.EtiquetaGeneral;
import com.pinturerias.general.entidad.ProductoEtiquetaGeneral;
import com.pinturerias.general.repositorio.EtiquetaGeneralRepository;
import com.pinturerias.general.repositorio.ProductoEtiquetaGeneralRepository;
import com.pinturerias.sucursal.entidad.EtiquetaSucursal;
import com.pinturerias.sucursal.entidad.ProductoEtiquetaSucursal;
import com.pinturerias.sucursal.repositorio.EtiquetaSucursalRepository;
import com.pinturerias.sucursal.repositorio.ProductoEtiquetaSucursalRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class ProductoEtiquetaSucursalService {

    private final ProductoEtiquetaSucursalRepository repositorio;
    private final EtiquetaSucursalRepository etiquetaSucursalRepository;
    private final EtiquetaGeneralRepository etiquetaGeneralRepository;
    private final ProductoEtiquetaGeneralRepository productoEtiquetaGeneralRepository;
    private final TenantExecutor tenantExecutor;

    public ProductoEtiquetaSucursalService(ProductoEtiquetaSucursalRepository repositorio,
                                          EtiquetaSucursalRepository etiquetaSucursalRepository,
                                          EtiquetaGeneralRepository etiquetaGeneralRepository,
                                          ProductoEtiquetaGeneralRepository productoEtiquetaGeneralRepository,
                                          TenantExecutor tenantExecutor) {
        this.repositorio = repositorio;
        this.etiquetaSucursalRepository = etiquetaSucursalRepository;
        this.etiquetaGeneralRepository = etiquetaGeneralRepository;
        this.productoEtiquetaGeneralRepository = productoEtiquetaGeneralRepository;
        this.tenantExecutor = tenantExecutor;
    }

    public EtiquetasProductoDTO obtenerLocales(Long productoId, Contexto contextoProducto, Tipo tipoProducto) {
        ProductoEtiquetaSucursal asignacion = repositorio
                .findByProductoIdAndContextoProductoAndTipoProducto(productoId, contextoProducto, tipoProducto)
                .orElse(null);

        if (asignacion == null) {
            return new EtiquetasProductoDTO(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }

        return construirRespuesta(
                new ArrayList<>(normalizarIds(asignacion.getEtiquetasGeneralesIds())),
                new ArrayList<>(normalizarIds(asignacion.getEtiquetasSucursalIds())),
                false,
                productoId,
                tipoProducto
        );
    }

    public EtiquetasProductoDTO obtenerVisibles(Long productoId, Contexto contextoProducto, Tipo tipoProducto) {
        EtiquetasProductoDTO locales = obtenerLocales(productoId, contextoProducto, tipoProducto);
        return construirRespuesta(
                locales.getEtiquetasGeneralesIds(),
                locales.getEtiquetasSucursalIds(),
                contextoProducto == Contexto.GENERAL,
                productoId,
                tipoProducto
        );
    }

    public EtiquetasProductoDTO guardar(Long productoId,
                                        Contexto contextoProducto,
                                        Tipo tipoProducto,
                                        EtiquetasProductoDTO dto) {
        Set<Long> idsGenerales = normalizarIds(dto.getEtiquetasGeneralesIds());
        Set<Long> idsSucursal = normalizarIds(dto.getEtiquetasSucursalIds());

        validarEtiquetasGenerales(idsGenerales);
        validarEtiquetasSucursal(idsSucursal);

        ProductoEtiquetaSucursal asignacion = repositorio
                .findByProductoIdAndContextoProductoAndTipoProducto(productoId, contextoProducto, tipoProducto)
                .orElseGet(ProductoEtiquetaSucursal::new);

        asignacion.setProductoId(productoId);
        asignacion.setContextoProducto(contextoProducto);
        asignacion.setTipoProducto(tipoProducto);
        asignacion.setEtiquetasGeneralesIds(idsGenerales);
        asignacion.setEtiquetasSucursalIds(idsSucursal);

        repositorio.save(asignacion);
        return obtenerVisibles(productoId, contextoProducto, tipoProducto);
    }

    public void sincronizar(Long productoId,
                            Contexto contextoProducto,
                            Tipo tipoProducto,
                            List<Long> etiquetasGeneralesIds,
                            List<Long> etiquetasSucursalIds) {
        guardar(productoId, contextoProducto, tipoProducto, new EtiquetasProductoDTO(
                etiquetasGeneralesIds,
                etiquetasSucursalIds,
                new ArrayList<>()
        ));
    }

    public void eliminar(Long productoId, Contexto contextoProducto, Tipo tipoProducto) {
        repositorio.findByProductoIdAndContextoProductoAndTipoProducto(productoId, contextoProducto, tipoProducto)
                .ifPresent(repositorio::delete);
    }

    private EtiquetasProductoDTO construirRespuesta(List<Long> idsGeneralesLocales,
                                                    List<Long> idsSucursalLocales,
                                                    boolean incluirGeneralesAsignadas,
                                                    Long productoId,
                                                    Tipo tipoProducto) {
        Set<Long> idsGeneralesVisibles = new LinkedHashSet<>();

        if (incluirGeneralesAsignadas) {
            idsGeneralesVisibles.addAll(tenantExecutor.ejecutarEnTenant(null, () ->
                    productoEtiquetaGeneralRepository.findByProductoIdAndTipoProducto(productoId, tipoProducto)
                            .map(ProductoEtiquetaGeneral::getEtiquetasGeneralesIds)
                            .orElseGet(TreeSet::new)
            ));
        }

        idsGeneralesVisibles.addAll(normalizarIds(idsGeneralesLocales));

        List<Long> generales = new ArrayList<>(idsGeneralesVisibles);
        List<Long> sucursales = new ArrayList<>(normalizarIds(idsSucursalLocales));

        List<String> nombresGenerales = tenantExecutor.ejecutarEnTenant(null, () ->
                etiquetaGeneralRepository.findAllById(generales).stream()
                        .map(EtiquetaGeneral::getValor)
                        .toList()
        );

        List<String> nombresLocales = etiquetaSucursalRepository.findAllById(sucursales).stream()
                .map(EtiquetaSucursal::getValor)
                .toList();

        List<String> visibles = new ArrayList<>();
        visibles.addAll(nombresGenerales);
        visibles.addAll(nombresLocales);
        visibles.sort(String.CASE_INSENSITIVE_ORDER);

        EtiquetasProductoDTO dto = new EtiquetasProductoDTO();
        dto.setEtiquetasGeneralesIds(new ArrayList<>(normalizarIds(idsGeneralesLocales)));
        dto.setEtiquetasSucursalIds(sucursales);
        dto.setEtiquetas(visibles);
        return dto;
    }

    private void validarEtiquetasGenerales(Set<Long> ids) {
        if (ids.isEmpty()) {
            return;
        }

        int cantidad = tenantExecutor.ejecutarEnTenant(null, () -> etiquetaGeneralRepository.findAllById(ids).size());
        if (cantidad != ids.size()) {
            throw new RecursoNoEncontradoException("Una o más etiquetas generales no existen");
        }
    }

    private void validarEtiquetasSucursal(Set<Long> ids) {
        if (ids.isEmpty()) {
            return;
        }

        List<EtiquetaSucursal> etiquetas = etiquetaSucursalRepository.findAllById(ids);
        if (etiquetas.size() != ids.size()) {
            throw new RecursoNoEncontradoException("Una o más etiquetas de sucursal no existen");
        }
    }

    private Set<Long> normalizarIds(List<Long> ids) {
        if (ids == null) {
            return new TreeSet<>();
        }

        return ids.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private Set<Long> normalizarIds(Set<Long> ids) {
        if (ids == null) {
            return new TreeSet<>();
        }

        return ids.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(TreeSet::new));
    }
}

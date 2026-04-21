package com.pinturerias.general.servicio;

import com.pinturerias.compartidos.dto.EtiquetasProductoDTO;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import com.pinturerias.general.entidad.EtiquetaGeneral;
import com.pinturerias.general.entidad.ProductoEtiquetaGeneral;
import com.pinturerias.general.repositorio.EtiquetaGeneralRepository;
import com.pinturerias.general.repositorio.ProductoEtiquetaGeneralRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class ProductoEtiquetaGeneralService {

    private final ProductoEtiquetaGeneralRepository repositorio;
    private final EtiquetaGeneralRepository etiquetaGeneralRepository;

    public ProductoEtiquetaGeneralService(ProductoEtiquetaGeneralRepository repositorio,
                                         EtiquetaGeneralRepository etiquetaGeneralRepository) {
        this.repositorio = repositorio;
        this.etiquetaGeneralRepository = etiquetaGeneralRepository;
    }

    public EtiquetasProductoDTO obtener(Long productoId, Tipo tipoProducto) {
        List<Long> ids = repositorio.findByProductoIdAndTipoProducto(productoId, tipoProducto)
                .map(ProductoEtiquetaGeneral::getEtiquetasGeneralesIds)
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);

        return construirRespuesta(ids);
    }

    public EtiquetasProductoDTO guardar(Long productoId, Tipo tipoProducto, EtiquetasProductoDTO dto) {
        Set<Long> idsNormalizados = normalizarIds(dto.getEtiquetasGeneralesIds());
        validarEtiquetasGenerales(idsNormalizados);

        ProductoEtiquetaGeneral asignacion = repositorio.findByProductoIdAndTipoProducto(productoId, tipoProducto)
                .orElseGet(ProductoEtiquetaGeneral::new);

        asignacion.setProductoId(productoId);
        asignacion.setTipoProducto(tipoProducto);
        asignacion.setEtiquetasGeneralesIds(idsNormalizados);

        ProductoEtiquetaGeneral guardada = repositorio.save(asignacion);
        return construirRespuesta(new ArrayList<>(guardada.getEtiquetasGeneralesIds()));
    }

    public void sincronizar(Long productoId, Tipo tipoProducto, List<Long> etiquetasGeneralesIds) {
        EtiquetasProductoDTO dto = new EtiquetasProductoDTO();
        dto.setEtiquetasGeneralesIds(etiquetasGeneralesIds);
        guardar(productoId, tipoProducto, dto);
    }

    public void eliminar(Long productoId, Tipo tipoProducto) {
        repositorio.findByProductoIdAndTipoProducto(productoId, tipoProducto)
                .ifPresent(repositorio::delete);
    }

    private EtiquetasProductoDTO construirRespuesta(List<Long> etiquetasGeneralesIds) {
        List<Long> ids = new ArrayList<>(normalizarIds(etiquetasGeneralesIds));
        List<String> nombres = etiquetaGeneralRepository.findAllById(ids).stream()
                .map(EtiquetaGeneral::getValor)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();

        EtiquetasProductoDTO dto = new EtiquetasProductoDTO();
        dto.setEtiquetasGeneralesIds(ids);
        dto.setEtiquetasSucursalIds(new ArrayList<>());
        dto.setEtiquetas(nombres);
        return dto;
    }

    private void validarEtiquetasGenerales(Set<Long> ids) {
        if (ids.isEmpty()) {
            return;
        }

        List<EtiquetaGeneral> etiquetas = etiquetaGeneralRepository.findAllById(ids);
        if (etiquetas.size() != ids.size()) {
            throw new RecursoNoEncontradoException("Una o más etiquetas generales no existen");
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
}

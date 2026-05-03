package com.pinturerias.sucursal.servicio;

import com.pinturerias.compartidos.dto.EtiquetaRefDTO;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.compartidos.servicio.ValidadorAsignacionEtiquetaService;
import com.pinturerias.sucursal.entidad.ProductoEtiquetaSucursal;
import com.pinturerias.sucursal.repositorio.ProductoEtiquetaSucursalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductoEtiquetaSucursalService {

    private final ProductoEtiquetaSucursalRepository repository;
    private final ValidadorAsignacionEtiquetaService validadorAsignacionEtiquetaService;


    public void eliminar(Long id, Long etiquetaId) {
        repository.delete(repository.findByProductoIdAndEtiquetaId(id,etiquetaId));
    }

    @Transactional
    public void sincronizar(
            Long productoId,
            Contexto contexto,
            Tipo tipo,
            List<Long> etiquetasGeneralesIds,
            List<Long> etiquetasSucursalIds
    ) {

        // 🔹 Normalizar null → lista vacía
        etiquetasGeneralesIds = etiquetasGeneralesIds == null ? List.of() : etiquetasGeneralesIds;
        etiquetasSucursalIds = etiquetasSucursalIds == null ? List.of() : etiquetasSucursalIds;

        System.out.println(etiquetasSucursalIds);
        // 🔹 Evitar duplicados
        Set<Long> generalSet = new HashSet<>(etiquetasGeneralesIds);
        Set<Long> sucursalSet = new HashSet<>(etiquetasSucursalIds);

        // 🔹 Validar existencia
        for (Long id : generalSet) {
            validadorAsignacionEtiquetaService.validarAsignacionEnSucursal(
                    new EtiquetaRefDTO(id, Contexto.GENERAL)
            );
        }

        for (Long id : sucursalSet) {
            validadorAsignacionEtiquetaService.validarAsignacionEnSucursal(
                    new EtiquetaRefDTO(id, Contexto.SUCURSAL)
            );
        }

        // 🔹 Traer estado actual
        List<ProductoEtiquetaSucursal> actuales =
                repository.findByProductoIdAndTipo(productoId, tipo);

        Set<String> actualesKeys = actuales.stream()
                .map(e -> e.getEtiquetaId() + "-" + e.getContexto())
                .collect(Collectors.toSet());

        Set<String> nuevosKeys = new HashSet<>();

        // 🔹 Armar estado nuevo
        generalSet.forEach(id ->
                nuevosKeys.add(id + "-" + Contexto.GENERAL)
        );

        sucursalSet.forEach(id ->
                nuevosKeys.add(id + "-" + Contexto.SUCURSAL)
        );

        // ELIMINAR LOS QUE SOBRAN
        for (ProductoEtiquetaSucursal actual : actuales) {

            String key = actual.getEtiquetaId() + "-" + actual.getContexto();

            if (!nuevosKeys.contains(key)) {
                repository.delete(actual);
            }
        }

        // INSERTAR LOS NUEVOS
        for (String key : nuevosKeys) {

            if (!actualesKeys.contains(key)) {

                String[] parts = key.split("-");
                Long etiquetaId = Long.valueOf(parts[0]);
                Contexto ctx = Contexto.valueOf(parts[1]);

                repository.save(
                        ProductoEtiquetaSucursal.builder()
                                .productoId(productoId)
                                .etiquetaId(etiquetaId)
                                .contexto(ctx)
                                .tipo(tipo)
                                .build()
                );
            }
        }
    }
}
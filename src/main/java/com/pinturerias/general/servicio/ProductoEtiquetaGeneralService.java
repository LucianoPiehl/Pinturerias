package com.pinturerias.general.servicio;

import com.pinturerias.compartidos.dto.EtiquetaRefDTO;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.compartidos.servicio.ValidadorAsignacionEtiquetaService;
import com.pinturerias.general.entidad.ProductoEtiquetaGeneral;
import com.pinturerias.general.repositorio.ProductoEtiquetaGeneralRepository;
import com.pinturerias.sucursal.entidad.ProductoEtiquetaSucursal;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductoEtiquetaGeneralService {

    private final ValidadorAsignacionEtiquetaService validadorAsignacionEtiquetaService;
    private final ProductoEtiquetaGeneralRepository repository;

    @Transactional
    public void sincronizar(
            Long productoId,
            Tipo tipo,
            List<Long> etiquetasGeneralesIds
    ) {

        // 🔹 Normalizar null → lista vacía
        etiquetasGeneralesIds = etiquetasGeneralesIds == null ? List.of() : etiquetasGeneralesIds;

        // 🔹 Evitar duplicados
        Set<Long> generalSet = new HashSet<>(etiquetasGeneralesIds);

        // 🔹 Validar existencia
        for (Long id : generalSet) {
            validadorAsignacionEtiquetaService.validarAsignacionEnGeneral(
                    new EtiquetaRefDTO(id, Contexto.GENERAL)
            );
        }


        // 🔹 Traer estado actual
        List<ProductoEtiquetaGeneral> actuales =
                repository.findByProductoIdAndTipo(productoId, tipo);

        Set<String> actualesKeys = actuales.stream()
                .map(e -> e.getEtiquetaId() + "-" + e.getContexto())
                .collect(Collectors.toSet());

        Set<String> nuevosKeys = new HashSet<>();

        // 🔹 Armar estado nuevo
        generalSet.forEach(id ->
                nuevosKeys.add(id + "-" + Contexto.GENERAL)
        );


        // ELIMINAR LOS QUE SOBRAN
        for (ProductoEtiquetaGeneral actual : actuales) {

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
                        ProductoEtiquetaGeneral.builder()
                                .productoId(productoId)
                                .etiquetaId(etiquetaId)
                                .contexto(ctx)
                                .tipo(tipo)
                                .build()
                );
            }
        }

    }

    public void eliminar(Long id, Tipo tipo) {
    }
}
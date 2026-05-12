package com.pinturerias.compartidos.servicio;

import com.pinturerias.compartidos.dto.etiqueta.EtiquetaDTO;
import com.pinturerias.compartidos.entidad.shared.Etiqueta;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.configuracion.tenant.TenantExecutor;
import com.pinturerias.general.entidad.ProductoEtiquetaGeneral;
import com.pinturerias.general.repositorio.EtiquetaGeneralRepository;
import com.pinturerias.general.repositorio.ProductoEtiquetaGeneralRepository;
import com.pinturerias.sucursal.entidad.ProductoEtiquetaSucursal;
import com.pinturerias.sucursal.repositorio.EtiquetaSucursalRepository;
import com.pinturerias.sucursal.repositorio.ProductoEtiquetaSucursalRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoEtiquetaOrquestadorService {

    //Modificar esto Esta clase es un patron orquestador, no puede acceder a los repositorios, solo puede orquestar services.
    private final ProductoEtiquetaSucursalRepository sucursalRelRepo;
    private final ProductoEtiquetaGeneralRepository generalRelRepo;

    private final EtiquetaGeneralRepository etiquetaGeneralRepo;
    private final EtiquetaSucursalRepository etiquetaSucursalRepo;

    private final TenantExecutor tenantExecutor;


    /*
      Para productos de SUCURSAL
     */
    @Transactional
    public List<EtiquetaDTO> obtenerEtiquetasSucursal(Long productoId, Tipo tipo) {

        List<ProductoEtiquetaSucursal> relaciones =
                sucursalRelRepo.findByProductoIdAndTipo(productoId, tipo);

        List<Long> idsGeneral = new ArrayList<>();
        List<Long> idsSucursal = new ArrayList<>();

        for (ProductoEtiquetaSucursal rel : relaciones) {
            if (rel.getContexto() == Contexto.GENERAL) {
                idsGeneral.add(rel.getEtiquetaId());
            } else {
                idsSucursal.add(rel.getEtiquetaId());
            }
        }

        List<EtiquetaDTO> resultado = new ArrayList<>();

        // GENERAL
        if (!idsGeneral.isEmpty()) {
            List<Etiqueta> etiquetasGenerales = tenantExecutor.ejecutarEnTenant(
                    null,
                    () -> etiquetaGeneralRepo.findAllById(idsGeneral)
            );
            etiquetasGenerales.forEach(e -> resultado.add(
                    new EtiquetaDTO(e.getId(), e.getValor(), Contexto.GENERAL, e.getHabilitado())
            ));
        }

        // SUCURSAL
        if (!idsSucursal.isEmpty()) {
            etiquetaSucursalRepo.findAllById(idsSucursal)
                    .forEach(e -> resultado.add(
                            new EtiquetaDTO(e.getId(), e.getValor(), Contexto.SUCURSAL, e.getHabilitado())
                    ));
        }

        return resultado;
    }

    /*
      Para productos GENERAL
     */
    @Transactional
    public List<EtiquetaDTO> obtenerEtiquetasGeneral(Long productoId, Tipo tipo) {

        return tenantExecutor.ejecutarEnTenant(null, () -> {

            List<ProductoEtiquetaGeneral> relaciones =
                    generalRelRepo.findByProductoIdAndTipo(productoId, tipo);

            List<EtiquetaDTO> resultado = new ArrayList<>();

            for (ProductoEtiquetaGeneral rel : relaciones) {

                etiquetaGeneralRepo.findById(rel.getEtiquetaId())
                        .ifPresent(e -> resultado.add(
                                new EtiquetaDTO(
                                        e.getId(),
                                        e.getValor(),
                                        Contexto.GENERAL,
                                        e.getHabilitado()
                                )
                        ));
            }

            return resultado;
        });
    }
}
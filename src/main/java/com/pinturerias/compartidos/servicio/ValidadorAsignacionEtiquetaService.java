package com.pinturerias.compartidos.servicio;

import com.pinturerias.compartidos.dto.EtiquetaRefDTO;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.configuracion.TenantContext;
import com.pinturerias.configuracion.TenantExecutor;
import com.pinturerias.excepciones.ExcepcionApi;
import com.pinturerias.general.repositorio.EtiquetaGeneralRepository;
import com.pinturerias.sucursal.repositorio.EtiquetaSucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidadorAsignacionEtiquetaService {

    private final TenantExecutor tenantExecutor;
    private final EtiquetaGeneralRepository etiquetaGeneralRepository;
    private final EtiquetaSucursalRepository etiquetaSucursalRepository;

    /*
     Validación para asignar etiqueta a producto de SUCURSAL
     */
    public void validarAsignacionEnSucursal(EtiquetaRefDTO ref) {

        if (ref.getContexto() == Contexto.GENERAL) {

            boolean existe = tenantExecutor.ejecutarEnTenant(
                    null,
                    () -> etiquetaGeneralRepository.existsById(ref.getId())
            );

            if (!existe) {
                throw new ExcepcionApi(400, "Etiqueta GENERAL no existe");
            }

        } else {

            boolean existe = tenantExecutor.ejecutarEnTenant(
                    TenantContext.getTenantId(),
                    () -> etiquetaSucursalRepository.existsById(ref.getId())
            );

            if (!existe) {
                throw new ExcepcionApi(400, "Etiqueta SUCURSAL no existe");
            }
        }
    }

    /*
      Validación para producto GENERAL
     */
    public void validarAsignacionEnGeneral(EtiquetaRefDTO ref) {

        if (ref.getContexto() != Contexto.GENERAL) {
            throw new ExcepcionApi(400,
                    "Producto GENERAL no puede usar etiquetas de SUCURSAL");
        }

        boolean existe = tenantExecutor.ejecutarEnTenant(
                null,
                () -> etiquetaGeneralRepository.existsById(ref.getId())
        );

        if (!existe) {
            throw new ExcepcionApi(400, "Etiqueta GENERAL no existe");
        }
    }
}

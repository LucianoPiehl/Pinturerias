package com.pinturerias.configuracion;

import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class TenantExecutor {
    /*
      Ejecuta un bloque de código en un tenant específico.

      tenantId → tenant destino (null = BD general)
      action → código a ejecutar
     */
    public <T> T ejecutarEnTenant(String tenantId, Supplier<T> action) {

        // 🔹 Guardamos el tenant actual (ej: "sucursal1")
        String tenantOriginal = TenantContext.getTenantId();

        try {
            // 🔹 Cambiamos el contexto de ejecución

            if (tenantId == null) {
                // Si es null → usamos la BD general
                TenantContext.clear();
            } else {
                // Si no → usamos la BD de la sucursal
                TenantContext.setTenantId(tenantId);
            }

            // 🔹 Ejecutamos el código bajo ese contexto
            return action.get();

        } finally {
            // RESTAURACIÓN OBLIGATORIA

            // Volvemos al tenant original del request
            if (tenantOriginal == null) {
                TenantContext.clear();
            } else {
                TenantContext.setTenantId(tenantOriginal);
            }
        }
    }
}



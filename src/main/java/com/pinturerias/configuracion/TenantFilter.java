package com.pinturerias.configuracion;

import com.pinturerias.general.entidad.Sucursal;
import com.pinturerias.general.repositorio.SucursalRepository;
import com.pinturerias.excepciones.ExcepcionApi;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TenantFilter implements Filter {

    private final TenantRegistry tenantRegistry;

    public TenantFilter(TenantRegistry tenantRegistry) {
        this.tenantRegistry = tenantRegistry;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest http = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            String tenantId = resolverTenant(http);

            if (tenantId != null) {
                TenantContext.setTenantId(tenantId);
            } else {
                TenantContext.clear();
            }

            chain.doFilter(request, response);

        } catch (ExcepcionApi ex) {
            httpResponse.setStatus(ex.getStatus());
            httpResponse.setContentType("text/plain;charset=UTF-8");
            httpResponse.getWriter().write(ex.getMessage());
        } finally {
            TenantContext.clear();
        }
    }

    private String resolverTenant(HttpServletRequest request) {

        String headerTenant = request.getHeader("X-Tenant-Id");

        if (headerTenant == null || headerTenant.isBlank()) {
            return null; // o lanzar error si querés hacerlo obligatorio
        }

        String tenantId = headerTenant.trim();

        validarTenantRegistrado(tenantId);

        return tenantId;
    }

    private void validarTenantRegistrado(String tenantId) {
        if (!tenantRegistry.currentTenants().containsKey(tenantId)) {
            throw new ExcepcionApi(400, "Tenant no registrado: " + tenantId);
        }
    }
}



package com.pinturerias.com.pinturerias.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component //Esto hace que Spring detecte el filtro automáticamente
/* Todas las request HTTP pasa siempre por todos los filtros registrados antes de llegar al controller */
public class TenantFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest http = (HttpServletRequest) request;
        String tenantId = http.getHeader("X-Tenant-Id");

        if (tenantId != null && !tenantId.isBlank()) {
            TenantContext.setTenantId(tenantId.trim());
        } else {
            TenantContext.clear();
        }

        chain.doFilter(request, response);
    }
}
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

    private static final Pattern SUCURSAL_PATH_PATTERN = Pattern.compile("^/api/sucursal/([^/]+)(/.*)?$");

    private final SucursalRepository sucursalRepository;
    private final TenantRegistry tenantRegistry;

    public TenantFilter(SucursalRepository sucursalRepository, TenantRegistry tenantRegistry) {
        this.sucursalRepository = sucursalRepository;
        this.tenantRegistry = tenantRegistry;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest http = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            String tenantId = resolverTenant(http);

            if (tenantId != null && !tenantId.isBlank()) {
                TenantContext.setTenantId(tenantId.trim());
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
        Matcher matcher = SUCURSAL_PATH_PATTERN.matcher(request.getRequestURI());
        if (matcher.matches()) {
            String tenantDesdeRuta = resolverTenantDesdeRuta(matcher.group(1));
            String headerTenant = request.getHeader("X-Tenant-Id");

            if (headerTenant != null && !headerTenant.isBlank()
                    && !headerTenant.trim().equalsIgnoreCase(tenantDesdeRuta)) {
                throw new ExcepcionApi(400,
                        "El header X-Tenant-Id no coincide con la sucursal de la URL. Use solo la URL o envie el codigo correcto.");
            }

            validarTenantRegistrado(tenantDesdeRuta);
            return tenantDesdeRuta;
        }

        String headerTenant = request.getHeader("X-Tenant-Id");
        if (headerTenant != null && !headerTenant.isBlank()) {
            String tenantId = headerTenant.trim();
            validarTenantRegistrado(tenantId);
            return tenantId;
        }
        return null;
    }

    private String resolverTenantDesdeRuta(String valorSucursal) {
        if (valorSucursal.matches("\\d+")) {
            Optional<Sucursal> sucursal = sucursalRepository.findById(Long.valueOf(valorSucursal));
            return sucursal.map(Sucursal::getCodigo)
                    .orElseThrow(() -> new IllegalArgumentException("Sucursal no encontrada: " + valorSucursal));
        }

        return valorSucursal;
    }

    private void validarTenantRegistrado(String tenantId) {
        if (!tenantRegistry.currentTenants().containsKey(tenantId)) {
            throw new ExcepcionApi(400, "Tenant no registrado: " + tenantId);
        }
    }
}




package com.pinturerias.compartidos.servicio;

import com.pinturerias.compartidos.dto.EtiquetasProductoDTO;
import com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.configuracion.TenantExecutor;
import com.pinturerias.general.servicio.ProductoGeneralService;
import com.pinturerias.sucursal.entidad.ProductoPrecioStock;
import com.pinturerias.sucursal.servicio.ProductoEtiquetaSucursalService;
import com.pinturerias.sucursal.servicio.ProductoPrecioStockService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CatalogoPinturaService {

    private final ProductoGeneralService productoGeneralService;
    private final ProductoPrecioStockService productoPrecioStockService;
    private final ProductoEtiquetaSucursalService productoEtiquetaSucursalService;
    private final TenantExecutor tenantExecutor;
    private final PrecioProductoService precioProductoService;

    public CatalogoPinturaService(
            ProductoGeneralService productoGeneralService,
            ProductoPrecioStockService productoPrecioStockService,
            ProductoEtiquetaSucursalService productoEtiquetaSucursalService,
            TenantExecutor tenantExecutor,
            PrecioProductoService precioProductoService
    ) {
        this.productoGeneralService = productoGeneralService;
        this.productoPrecioStockService = productoPrecioStockService;
        this.productoEtiquetaSucursalService = productoEtiquetaSucursalService;
        this.tenantExecutor = tenantExecutor;
        this.precioProductoService = precioProductoService;
    }

    public List<ProductoPinturaDTO> listarProductosPintura(String tenantId) {
        List<ProductoPinturaDTO> productosGeneral = tenantExecutor.ejecutarEnTenant(null,
                productoGeneralService::getAllProductosPintura);

        List<ProductoPrecioStock> controlesLocales = tenantExecutor.ejecutarEnTenant(tenantId,
                () -> productoPrecioStockService.getAllByTipoProducto(Tipo.PINTURA));

        asociarControlesLocales(controlesLocales, productosGeneral);
        asociarEtiquetasLocales(tenantId, productosGeneral);
        return productosGeneral;
    }

    private void asociarControlesLocales(List<ProductoPrecioStock> controlesLocales,
                                         List<ProductoPinturaDTO> productosGeneral) {
        Map<Long, ProductoPinturaDTO> mapaDto = productosGeneral.stream()
                .collect(Collectors.toMap(ProductoPinturaDTO::getIdProducto, dto -> dto));

        for (ProductoPrecioStock control : controlesLocales) {
            ProductoPinturaDTO dto = mapaDto.get(control.getProductoId());
            if (dto == null) {
                continue;
            }

            dto.setPrecioFinal(
                    precioProductoService.calcularPrecioFinalSucursal(
                            dto.getPrecioFinal(),
                            control.getPorcentajeAjuste()
                    )
            );
            dto.setStock(control.getStock());
        }
    }

    private void asociarEtiquetasLocales(String tenantId, List<ProductoPinturaDTO> productosGeneral) {
        tenantExecutor.ejecutarEnTenant(tenantId, () -> {
            for (ProductoPinturaDTO dto : productosGeneral) {
                EtiquetasProductoDTO etiquetas = productoEtiquetaSucursalService.obtenerVisibles(
                        dto.getIdProducto(),
                        Contexto.GENERAL,
                        Tipo.PINTURA
                );
                dto.setEtiquetas(etiquetas.getEtiquetas());
                dto.setEtiquetasGeneralesIds(etiquetas.getEtiquetasGeneralesIds());
                dto.setEtiquetasSucursalIds(etiquetas.getEtiquetasSucursalIds());
            }
            return null;
        });
    }
}

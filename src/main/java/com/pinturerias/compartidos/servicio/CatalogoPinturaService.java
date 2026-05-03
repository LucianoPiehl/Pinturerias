package com.pinturerias.compartidos.servicio;

import com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.configuracion.TenantExecutor;
import com.pinturerias.general.servicio.ProductoGeneralService;
import com.pinturerias.sucursal.entidad.ProductoPrecioStock;
import com.pinturerias.sucursal.servicio.ProductoEtiquetaSucursalService;
import com.pinturerias.sucursal.servicio.ProductoPrecioStockService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogoPinturaService {

    private final ProductoGeneralService productoGeneralService;
    private final ProductoPrecioStockService productoPrecioStockService;
    private final TenantExecutor tenantExecutor;
    private final PrecioProductoService precioProductoService;


    public List<ProductoPinturaDTO> listarProductosPintura(String tenantId) {
        List<ProductoPinturaDTO> productosGeneral = tenantExecutor.ejecutarEnTenant(null,
                productoGeneralService::getAllProductosPintura);

        List<ProductoPrecioStock> controlesLocales = tenantExecutor.ejecutarEnTenant(tenantId,
                () -> productoPrecioStockService.getAllByTipoProducto(Tipo.PINTURA));

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

}

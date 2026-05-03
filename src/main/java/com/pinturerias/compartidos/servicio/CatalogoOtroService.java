package com.pinturerias.compartidos.servicio;

import com.pinturerias.compartidos.dto.ProductoOtroDTO;;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.configuracion.TenantExecutor;
import com.pinturerias.general.servicio.ProductoGeneralService;
import com.pinturerias.sucursal.entidad.ProductoPrecioStock;
import com.pinturerias.sucursal.servicio.ProductoEtiquetaSucursalService;
import com.pinturerias.sucursal.servicio.ProductoPrecioStockService;
import com.pinturerias.sucursal.servicio.ProductoSucursalService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogoOtroService {
    private final ProductoGeneralService productoGeneralService;
    private final ProductoPrecioStockService productoPrecioStockService;
    private final TenantExecutor tenantExecutor;
    private final PrecioProductoService precioProductoService;
    private final ProductoSucursalService productoSucursalService;


    public List<ProductoOtroDTO> listarProductosOtro(String tenantId) {
        List<ProductoOtroDTO> productosGeneral = tenantExecutor.ejecutarEnTenant(null,
                productoGeneralService::getAllProductosOtro);

        List<ProductoPrecioStock> controlesLocales = tenantExecutor.ejecutarEnTenant(tenantId,
                () -> productoPrecioStockService.getAllByTipoProducto(Tipo.OTRO));

        List<ProductoOtroDTO> productosSucursal = productoSucursalService.listarProductosOtro();

        asociarControlesLocales(controlesLocales, productosGeneral);
        List<ProductoOtroDTO> productosOtro = Stream.concat(productosSucursal.stream(), productosGeneral.stream())
                .collect(Collectors.toList());
        return productosOtro;
    }

    private void asociarControlesLocales(List<ProductoPrecioStock> controlesLocales,
                                         List<ProductoOtroDTO> productosGeneral) {
        Map<Long, ProductoOtroDTO> mapaDto = productosGeneral.stream()
                .collect(Collectors.toMap(ProductoOtroDTO::getIdProducto, dto -> dto));

        for (ProductoPrecioStock control : controlesLocales) {
            ProductoOtroDTO dto = mapaDto.get(control.getProductoId());
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

package com.pinturerias.compartidos.servicio;

import com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.compartidos.entidad.general.ProductoPinturaGeneral;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.configuracion.TenantExecutor;
import com.pinturerias.general.servicio.ProductoGeneralService;
import com.pinturerias.sucursal.entidad.ProductoPrecioStock;
import com.pinturerias.sucursal.servicio.ProductoPrecioStockService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CatalogoPinturaService {

    private final ProductoGeneralService productoGeneralService;
    private final ProductoPrecioStockService productoPrecioStockService;
    private final TenantExecutor tenantExecutor;
    private final PrecioProductoService precioProductoService;

    public CatalogoPinturaService(
            ProductoGeneralService productoGeneralService,
            ProductoPrecioStockService productoPrecioStockService,
            TenantExecutor tenantExecutor,
            PrecioProductoService precioProductoService
    ) {
        this.productoGeneralService = productoGeneralService;
        this.productoPrecioStockService = productoPrecioStockService;
        this.tenantExecutor = tenantExecutor;
        this.precioProductoService = precioProductoService;
    }

    public List<ProductoPinturaDTO> listarProductosPintura(String tenantId) {
        List<ProductoPinturaDTO> productosGeneral = tenantExecutor.ejecutarEnTenant(null, () ->
                productoGeneralService.getAllProductosPintura()
                        .stream()
                        .map(this::mapToDTO)
                        .toList()
        );

        List<ProductoPrecioStock> controlesLocales = tenantExecutor.ejecutarEnTenant(tenantId, () ->
                productoPrecioStockService.getAllByTipoProducto(Tipo.PINTURA)
        );

        asociarControlesLocales(controlesLocales, productosGeneral);
        return productosGeneral;
    }

    private ProductoPinturaDTO mapToDTO(ProductoPinturaGeneral producto) {
        ProductoPinturaDTO dto = new ProductoPinturaDTO();
        dto.setIdProducto(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setEtiquetas(producto.getEtiquetas());
        dto.setPrecioFinal(precioProductoService.calcularPrecioRecomendadoGeneral(producto));
        dto.setMarca(producto.getMarca());
        dto.setTipo(Tipo.PINTURA);
        dto.setContexto(Contexto.GENERAL);
        dto.setTipoPintura(producto.getTipoPintura());
        dto.setTamanoEnv(producto.getTamanoEnv());
        dto.setColorBase(producto.getColorBase());
        dto.setStock(0);
        return dto;
    }

    private void asociarControlesLocales(List<ProductoPrecioStock> controlesLocales, List<ProductoPinturaDTO> productosGeneral) {
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

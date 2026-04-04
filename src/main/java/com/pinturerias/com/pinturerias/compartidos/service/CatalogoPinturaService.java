package com.pinturerias.com.pinturerias.compartidos.service;

import com.pinturerias.com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.com.pinturerias.compartidos.entity.general.ProductoPinturaGeneral;
import com.pinturerias.com.pinturerias.compartidos.entity.sucursal.ProductoPinturaSucursal;
import com.pinturerias.com.pinturerias.compartidos.enumerate.Contexto;
import com.pinturerias.com.pinturerias.compartidos.enumerate.Tipo;
import com.pinturerias.com.pinturerias.config.TenantExecutor;
import com.pinturerias.com.pinturerias.general.service.ProductoGeneralService;
import com.pinturerias.com.pinturerias.sucursal.entity.ProductoPrecioStock;
import com.pinturerias.com.pinturerias.sucursal.service.ProductoPrecioStockService;
import com.pinturerias.com.pinturerias.sucursal.service.ProductoSucursalService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class CatalogoPinturaService {

    private final ProductoGeneralService productoGeneralService;
    private final ProductoSucursalService productoSucursalService;
    private final ProductoPrecioStockService productoPrecioStockService;
    private final TenantExecutor tenantExecutor;

    // Inyección por constructor
    public CatalogoPinturaService(
            ProductoGeneralService ProductoGeneralService, ProductoGeneralService productoGeneralService,
            ProductoSucursalService productoSucursalService, ProductoPrecioStockService productoPrecioStockService,
            TenantExecutor tenantExecutor
    ) {
        this.productoGeneralService = productoGeneralService;
        this.productoSucursalService = productoSucursalService;
        this.productoPrecioStockService = productoPrecioStockService;
        this.tenantExecutor = tenantExecutor;
    }

    /*
    Obtiene el catálogo completo para una sucursal.

    tenantId → identificador de la sucursal
    @return lista combinada de productos
     */
    public List<ProductoPinturaDTO> listarProductosPintura(String tenantId) {

        // Obtener pinturas globales (BD general)
        List<ProductoPinturaDTO> productosGeneral = tenantExecutor.ejecutarEnTenant(null, () ->
                productoGeneralService.listarProductosPintura()
                        .stream()
                        .map(this::mapToDTO)
                        .toList()
        );

        // Obtener el stock y precio que corresponden a los productos generales de la sucursal.
        List<ProductoPrecioStock> preciosStock = tenantExecutor.ejecutarEnTenant(tenantId, () ->
                productoPrecioStockService.listar()
        );

        //Asociamos a cada producto con su stock
        asociarTablasStockProductoGeneral(preciosStock, productosGeneral);

        // Obtener productos propios de la sucursal
        List<ProductoPinturaDTO> productosSucursal = tenantExecutor.ejecutarEnTenant(tenantId, () ->
                productoSucursalService.listarProductosPintura()
                        .stream()
                        .map(this::mapToDTO)
                        .toList()
        );

        // Unimos las listas
        List<ProductoPinturaDTO> catalogo = new ArrayList<>();

        catalogo.addAll(productosGeneral);      // productos globales
        catalogo.addAll(productosSucursal);  // productos propios

        return catalogo;
    }

    /*
     Combina productos general con los de sucursal.
     Convierte entidad a DTO.
     */

    private ProductoPinturaDTO mapToDTO(ProductoPinturaGeneral producto) {

        ProductoPinturaDTO dto = new ProductoPinturaDTO();

        dto.setIdProducto(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setEtiqueta(producto.getEtiquetas());
        dto.setPrecioFinal(producto.getPrecioFinal());
        dto.setMarca(producto.getMarca());
        dto.setTipo(Tipo.PINTURA);
        dto.setContexto(Contexto.GENERAL);
        dto.setIdCategoria(producto.getCategoria());
        dto.setTipoPintura(producto.getTipoPintura());
        dto.setTamanoEnv(producto.getTamanoEnv());
        dto.setColor(producto.getColor());
        return dto;
    }

    private ProductoPinturaDTO mapToDTO(ProductoPinturaSucursal producto) {

        ProductoPinturaDTO dto = new ProductoPinturaDTO();
        dto.setIdProducto(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setEtiqueta(producto.getEtiquetas());
        dto.setPrecioFinal(producto.getPrecioFinal());
        dto.setMarca(producto.getMarca());
        dto.setTipo(Tipo.PINTURA);
        dto.setContexto(Contexto.SUCURSAL);
        dto.setStock(producto.getStock());
        dto.setIdCategoria(producto.getCategoria());
        dto.setTipoPintura(producto.getTipoPintura());
        dto.setTamanoEnv(producto.getTamEnv());
        dto.setColor(producto.getColor());


        return dto;
    }

    private  void asociarTablasStockProductoGeneral(List<ProductoPrecioStock> productosPrecioStock, List<ProductoPinturaDTO> productosGeneral) {
        // 🔹 Creamos un mapa para acceso rápido por ID
        Map<Long, ProductoPinturaDTO> mapaDTO = productosGeneral.stream()
                .collect(Collectors.toMap(
                        ProductoPinturaDTO::getId,
                        dto -> dto
                ));

        // 🔹 Recorremos los datos de precio/stock
        for (ProductoPrecioStock pps : productosPrecioStock) {

            // 🔹 Buscamos el DTO correspondiente
            ProductoPinturaDTO dto = mapaDTO.get(pps.getProductoId());

            if (dto != null) {
                // 🔹 Si existe → actualizamos valores
                dto.setPrecioFinal(pps.getPrecio());// hay que determinar un tipo de dato para todos los precios que se manejen
                //Se puede castear para evitar el error y hacer una prueba pero no es una solucion correcta.
                dto.setStock(pps.getStock());
            }

            // 🔹 Si no existe → ignoramos
        }
    }
}


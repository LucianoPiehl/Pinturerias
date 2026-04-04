package com.pinturerias.com.pinturerias.compartidos.service;

import com.pinturerias.com.pinturerias.compartidos.dto.ProductoPinturaDTO;
import com.pinturerias.com.pinturerias.compartidos.entity.general.ProductoPinturaGeneral;
import com.pinturerias.com.pinturerias.compartidos.entity.sucursal.ProductoPinturaSucursal;
import com.pinturerias.com.pinturerias.compartidos.enumerate.Contexto;
import com.pinturerias.com.pinturerias.compartidos.enumerate.Tipo;
import com.pinturerias.com.pinturerias.config.TenantExecutor;
import com.pinturerias.com.pinturerias.general.service.ProductoGeneralService;
import com.pinturerias.com.pinturerias.sucursal.service.ProductoSucursalService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class CatalogoPinturaService {

    private final ProductoGeneralService productoGeneralService;
    private final ProductoSucursalService productoSucursalService;
    private final TenantExecutor tenantExecutor;

    // Inyección por constructor
    public CatalogoPinturaService(
            ProductoGeneralService ProductoGeneralService, ProductoGeneralService productoGeneralService,
            ProductoSucursalService productoSucursalService,
            TenantExecutor tenantExecutor
    ) {
        this.productoGeneralService = productoGeneralService;
        this.productoSucursalService = productoSucursalService;
        this.tenantExecutor = tenantExecutor;
    }

    /*
    Obtiene el catálogo completo para una sucursal.

    tenantId → identificador de la sucursal
    @return lista combinada de productos
     */
    public List<ProductoPinturaDTO> listarProductosPintura(String tenantId) {

        // 🔹 1. Obtener pinturas globales (BD general)
        List<ProductoPinturaDTO> productosGeneral = tenantExecutor.ejecutarEnTenant(null, () ->
                productoGeneralService.listarProductosPintura()
                        .stream()
                        .map(this::mapToDTO)
                        .toList()
        );
        // 🔹 2. Obtener productos propios de la sucursal
        List<ProductoPinturaDTO> productosSucursal = tenantExecutor.ejecutarEnTenant(tenantId, () ->
                productoSucursalService.listarProductosPintura()
                        .stream()
                        .map(this::mapToDTO)
                        .toList()
        );

        // 🔹 3. Unimos las listas
        List<ProductoPinturaDTO> catalogo = new ArrayList<>();

        catalogo.addAll(productosGeneral);      // productos globales
        catalogo.addAll(productosSucursal);  // productos propios

        return catalogo;
    }

    /*
     * Combina productos general con los de sucursal.
     
      Convierte entidad a DTO.
     * Se reutiliza para ambos tipos de productos.
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
        //no se puede setear el stock que tiene la sucursal de un producto general, se debe obtener una lista aparte de la sucursal.
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
}

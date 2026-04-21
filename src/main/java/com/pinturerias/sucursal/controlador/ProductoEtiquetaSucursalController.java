package com.pinturerias.sucursal.controlador;

import com.pinturerias.compartidos.dto.EtiquetasProductoDTO;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.sucursal.servicio.ProductoEtiquetaSucursalService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sucursal/{sucursalId}/productos-etiquetas/{contextoProducto}/{tipoProducto}/{productoId}")
public class ProductoEtiquetaSucursalController {

    private final ProductoEtiquetaSucursalService servicio;

    public ProductoEtiquetaSucursalController(ProductoEtiquetaSucursalService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public EtiquetasProductoDTO obtener(@PathVariable Long sucursalId,
                                        @PathVariable Contexto contextoProducto,
                                        @PathVariable Tipo tipoProducto,
                                        @PathVariable Long productoId) {
        return servicio.obtenerVisibles(productoId, contextoProducto, tipoProducto);
    }

    @PutMapping
    public EtiquetasProductoDTO actualizar(@PathVariable Long sucursalId,
                                           @PathVariable Contexto contextoProducto,
                                           @PathVariable Tipo tipoProducto,
                                           @PathVariable Long productoId,
                                           @RequestBody EtiquetasProductoDTO dto) {
        return servicio.guardar(productoId, contextoProducto, tipoProducto, dto);
    }

    @DeleteMapping
    public void eliminar(@PathVariable Long sucursalId,
                         @PathVariable Contexto contextoProducto,
                         @PathVariable Tipo tipoProducto,
                         @PathVariable Long productoId) {
        servicio.eliminar(productoId, contextoProducto, tipoProducto);
    }
}

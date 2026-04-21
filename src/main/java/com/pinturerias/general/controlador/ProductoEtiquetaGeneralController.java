package com.pinturerias.general.controlador;

import com.pinturerias.compartidos.dto.EtiquetasProductoDTO;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.general.servicio.ProductoEtiquetaGeneralService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/general/productos-etiquetas/{tipoProducto}/{productoId}")
public class ProductoEtiquetaGeneralController {

    private final ProductoEtiquetaGeneralService servicio;

    public ProductoEtiquetaGeneralController(ProductoEtiquetaGeneralService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public EtiquetasProductoDTO obtener(@PathVariable Tipo tipoProducto,
                                        @PathVariable Long productoId) {
        return servicio.obtener(productoId, tipoProducto);
    }

    @PutMapping
    public EtiquetasProductoDTO actualizar(@PathVariable Tipo tipoProducto,
                                           @PathVariable Long productoId,
                                           @RequestBody EtiquetasProductoDTO dto) {
        dto.setEtiquetasSucursalIds(null);
        return servicio.guardar(productoId, tipoProducto, dto);
    }

    @DeleteMapping
    public void eliminar(@PathVariable Tipo tipoProducto,
                         @PathVariable Long productoId) {
        servicio.eliminar(productoId, tipoProducto);
    }
}

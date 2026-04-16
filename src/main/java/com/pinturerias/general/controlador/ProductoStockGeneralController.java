package com.pinturerias.general.controlador;

import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.general.entidad.ProductoStockGeneral;
import com.pinturerias.general.servicio.ProductoStockGeneralService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/general/stock")
public class ProductoStockGeneralController {

    private final ProductoStockGeneralService servicio;

    public ProductoStockGeneralController(ProductoStockGeneralService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<ProductoStockGeneral> listar() {
        return servicio.listar();
    }

    @GetMapping("/{productoId}")
    public ProductoStockGeneral obtener(@PathVariable Long productoId,
                                        @RequestParam Tipo tipoProducto) {
        return servicio.obtenerPorProductoId(productoId, tipoProducto);
    }

    @PostMapping("/{productoId}")
    public ProductoStockGeneral guardar(@PathVariable Long productoId,
                                        @RequestParam Tipo tipoProducto,
                                        @RequestParam Integer stock) {
        return servicio.guardar(productoId, tipoProducto, stock);
    }
}




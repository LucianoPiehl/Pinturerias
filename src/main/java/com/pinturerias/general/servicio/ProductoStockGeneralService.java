package com.pinturerias.general.servicio;

import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.general.entidad.ProductoStockGeneral;
import com.pinturerias.general.repositorio.ProductoStockGeneralRepository;
import com.pinturerias.excepciones.ExcepcionApi;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoStockGeneralService {

    private final ProductoStockGeneralRepository repositorio;

    public ProductoStockGeneralService(ProductoStockGeneralRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<ProductoStockGeneral> listar() {
        return repositorio.findAll();
    }

    public ProductoStockGeneral guardar(Long productoId, Tipo tipoProducto, Integer stock) {
        ProductoStockGeneral entity = repositorio.findByProductoIdAndTipoProducto(productoId, tipoProducto)
                .orElse(new ProductoStockGeneral(productoId, tipoProducto, stock));
        entity.setStock(stock);
        return repositorio.save(entity);
    }

    public ProductoStockGeneral obtenerPorProductoId(Long productoId, Tipo tipoProducto) {
        return repositorio.findByProductoIdAndTipoProducto(productoId, tipoProducto)
                .orElseThrow(() -> new ExcepcionApi(
                        404,
                        "No existe stock central para el producto " + productoId + " del tipo " + tipoProducto
                ));
    }

    public void descontar(Long productoId, Tipo tipoProducto, Integer cantidad) {
        ProductoStockGeneral stock = obtenerPorProductoId(productoId, tipoProducto);
        if (stock.getStock() < cantidad) {
            throw new ExcepcionApi(
                    400,
                    "Stock central insuficiente para el producto " + productoId + " del tipo " + tipoProducto
            );
        }
        stock.setStock(stock.getStock() - cantidad);
        repositorio.save(stock);
    }
}





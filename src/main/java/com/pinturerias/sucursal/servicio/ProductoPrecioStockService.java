package com.pinturerias.sucursal.servicio;

import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.compartidos.servicio.PrecioProductoService;
import com.pinturerias.excepciones.ExcepcionApi;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import com.pinturerias.sucursal.entidad.ProductoPrecioStock;
import com.pinturerias.sucursal.repositorio.ProductoPrecioStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoPrecioStockService {
    private final ProductoPrecioStockRepository repo;
    private final PrecioProductoService precioProductoService;

    public ProductoPrecioStockService(ProductoPrecioStockRepository repo,
                                      PrecioProductoService precioProductoService) {
        this.repo = repo;
        this.precioProductoService = precioProductoService;
    }

    public ProductoPrecioStock save(Long productoGeneralId, Tipo tipoProducto, Double porcentajeAjuste, Integer stock) {
        ProductoPrecioStock pps = repo.findByProductoIdAndTipoProducto(productoGeneralId, tipoProducto)
                .orElse(new ProductoPrecioStock(productoGeneralId, tipoProducto, porcentajeAjuste, stock));

        pps.setPorcentajeAjuste(normalizarPorcentaje(porcentajeAjuste));
        pps.setStock(stock);

        return enriquecerRespuesta(repo.save(pps));
    }

    public ProductoPrecioStock saveDesdePrecioFinal(Long productoGeneralId,
                                                    Tipo tipoProducto,
                                                    Double precioFinalSucursal,
                                                    Integer stock) {
        Double porcentajeAjuste = precioProductoService.calcularPorcentajeAjuste(
                productoGeneralId,
                tipoProducto,
                precioFinalSucursal
        );
        return save(productoGeneralId, tipoProducto, porcentajeAjuste, stock);
    }

    public ProductoPrecioStock acreditarStock(Long productoGeneralId, Tipo tipoProducto, Integer cantidad) {
        ProductoPrecioStock pps = repo.findByProductoIdAndTipoProducto(productoGeneralId, tipoProducto)
                .orElse(new ProductoPrecioStock(productoGeneralId, tipoProducto, 0D, 0));

        pps.setStock(pps.getStock() + cantidad);
        return repo.save(pps);
    }

    public ProductoPrecioStock getProductoGeneralId(Long productoGeneralId, Tipo tipoProducto) {
        ProductoPrecioStock control = repo.findByProductoIdAndTipoProducto(productoGeneralId, tipoProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No hay control local para el producto general ID: " + productoGeneralId
                ));
        return enriquecerRespuesta(control);
    }

    public List<ProductoPrecioStock> getAll() {
        return repo.findAll().stream()
                .map(this::enriquecerRespuesta)
                .toList();
    }

    public List<ProductoPrecioStock> getAllByTipoProducto(Tipo tipoProducto) {
        return repo.findAllByTipoProducto(tipoProducto).stream()
                .map(this::enriquecerRespuesta)
                .toList();
    }

    public void delete(Long productoGeneralId, Tipo tipoProducto) {
        ProductoPrecioStock pps = repo.findByProductoIdAndTipoProducto(productoGeneralId, tipoProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No hay control local para el producto general ID: " + productoGeneralId
                ));
        repo.delete(pps);
    }

    public void descontarStock(Long productoGeneralId, Tipo tipoProducto, Integer cantidad) {
        ProductoPrecioStock pps = repo.findByProductoIdAndTipoProducto(productoGeneralId, tipoProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No hay control local para el producto general ID: " + productoGeneralId
                ));
        if (pps.getStock() < cantidad) {
            throw new ExcepcionApi(400, "Stock local insuficiente para el producto general ID: " + productoGeneralId);
        }
        pps.setStock(pps.getStock() - cantidad);
        repo.save(pps);
    }

    private ProductoPrecioStock enriquecerRespuesta(ProductoPrecioStock control) {
        Double precioRecomendado = precioProductoService.calcularPrecioRecomendadoGeneral(
                control.getProductoId(),
                control.getTipoProducto()
        );
        control.setPrecioRecomendadoGeneral(precioRecomendado);
        control.setPrecioFinalSucursal(
                precioProductoService.calcularPrecioFinalSucursal(
                        precioRecomendado,
                        control.getPorcentajeAjuste()
                )
        );
        return control;
    }

    private Double normalizarPorcentaje(Double porcentajeAjuste) {
        return porcentajeAjuste != null ? porcentajeAjuste : 0D;
    }
}





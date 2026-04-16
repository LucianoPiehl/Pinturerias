package com.pinturerias.compartidos.servicio;

import com.pinturerias.compartidos.entidad.Producto;
import com.pinturerias.compartidos.entidad.general.ProductoOtroGeneral;
import com.pinturerias.compartidos.entidad.general.ProductoPinturaGeneral;
import com.pinturerias.compartidos.entidad.sucursal.ProductoOtroSucursal;
import com.pinturerias.compartidos.enumeracion.Tipo;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import com.pinturerias.general.repositorio.ProductoOtroGeneralRepository;
import com.pinturerias.general.repositorio.ProductoPinturaGeneralRepository;
import com.pinturerias.sucursal.repositorio.ProductoOtroSucursalRepository;
import com.pinturerias.sucursal.repositorio.ProductoPrecioStockRepository;
import org.springframework.stereotype.Service;

@Service
public class PrecioProductoService {

    private final ProductoOtroGeneralRepository productoOtroGeneralRepository;
    private final ProductoPinturaGeneralRepository productoPinturaGeneralRepository;
    private final ProductoPrecioStockRepository productoPrecioStockRepository;
    private final ProductoOtroSucursalRepository productoOtroSucursalRepository;

    public PrecioProductoService(
            ProductoOtroGeneralRepository productoOtroGeneralRepository,
            ProductoPinturaGeneralRepository productoPinturaGeneralRepository,
            ProductoPrecioStockRepository productoPrecioStockRepository,
            ProductoOtroSucursalRepository productoOtroSucursalRepository
    ) {
        this.productoOtroGeneralRepository = productoOtroGeneralRepository;
        this.productoPinturaGeneralRepository = productoPinturaGeneralRepository;
        this.productoPrecioStockRepository = productoPrecioStockRepository;
        this.productoOtroSucursalRepository = productoOtroSucursalRepository;
    }

    public Double calcularPrecioRecomendadoGeneral(Long productoId, Tipo tipoProducto) {
        return calcularPrecioRecomendadoGeneral(obtenerProductoGeneral(productoId, tipoProducto));
    }

    public Double calcularPrecioRecomendadoGeneral(Producto producto) {
        double precio = valorSeguro(producto.getPrecioBase());

        if (producto instanceof ProductoPinturaGeneral pintura) {
            precio = calcularPrecioFinalSucursal(precio, obtenerPorcentajeTipoPintura(pintura));
            precio = calcularPrecioFinalSucursal(precio, obtenerPorcentajeColorBase(pintura));
            precio = calcularPrecioFinalSucursal(precio, obtenerPorcentajeTamanoEnvase(pintura));
        }

        return precio;
    }

    public Double calcularPrecioFinalSucursal(Long productoId, Tipo tipoProducto) {
        Double porcentajeAjuste = productoPrecioStockRepository
                .findByProductoIdAndTipoProducto(productoId, tipoProducto)
                .map(control -> valorSeguro(control.getPorcentajeAjuste()))
                .orElse(0D);

        return calcularPrecioFinalSucursal(
                calcularPrecioRecomendadoGeneral(productoId, tipoProducto),
                porcentajeAjuste
        );
    }

    public Double calcularPrecioFinalSucursal(Long productoId, Tipo tipoProducto, Double porcentajeAjuste) {
        return calcularPrecioFinalSucursal(
                calcularPrecioRecomendadoGeneral(productoId, tipoProducto),
                porcentajeAjuste
        );
    }

    public Double calcularPrecioFinalSucursal(Double precioRecomendadoGeneral, Double porcentajeAjuste) {
        return valorSeguro(precioRecomendadoGeneral) * factorPorcentaje(porcentajeAjuste);
    }

    public Double calcularPorcentajeAjuste(Long productoId, Tipo tipoProducto, Double precioFinalSucursal) {
        double precioRecomendado = calcularPrecioRecomendadoGeneral(productoId, tipoProducto);
        if (precioRecomendado == 0D) {
            return 0D;
        }
        return ((valorSeguro(precioFinalSucursal) / precioRecomendado) - 1D) * 100D;
    }

    public Double obtenerPrecioProductoSucursal(Long productoId) {
        ProductoOtroSucursal producto = productoOtroSucursalRepository.findById(productoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto de sucursal no encontrado"));
        return valorSeguro(producto.getPrecioFinal());
    }

    private Producto obtenerProductoGeneral(Long productoId, Tipo tipoProducto) {
        if (tipoProducto == Tipo.OTRO) {
            return productoOtroGeneralRepository.findById(productoId)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Producto general OTRO no encontrado"));
        }

        if (tipoProducto == Tipo.PINTURA) {
            return productoPinturaGeneralRepository.findById(productoId)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Producto general PINTURA no encontrado"));
        }

        throw new RecursoNoEncontradoException("Tipo de producto general no soportado");
    }

    private Double obtenerPorcentajeTipoPintura(ProductoPinturaGeneral pintura) {
        return pintura.getTipoPintura() != null ? pintura.getTipoPintura().getPorcentajeAumento() : 0D;
    }

    private Double obtenerPorcentajeColorBase(ProductoPinturaGeneral pintura) {
        return pintura.getColorBase() != null ? pintura.getColorBase().getPorcentajeAumento() : 0D;
    }

    private Double obtenerPorcentajeTamanoEnvase(ProductoPinturaGeneral pintura) {
        return pintura.getTamanoEnv() != null ? pintura.getTamanoEnv().getPorcentajeAumento() : 0D;
    }

    private Double valorSeguro(Double valor) {
        return valor != null ? valor : 0D;
    }

    private Double factorPorcentaje(Double porcentaje) {
        return 1D + (valorSeguro(porcentaje) / 100D);
    }
}

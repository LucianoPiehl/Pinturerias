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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrecioProductoService {

    private final ProductoOtroGeneralRepository productoOtroGeneralRepository;
    private final ProductoPinturaGeneralRepository productoPinturaGeneralRepository;
    private final ProductoPrecioStockRepository productoPrecioStockRepository;
    private final ProductoOtroSucursalRepository productoOtroSucursalRepository;

    public Double calcularPrecioRecomendadoGeneral(Long productoId, Tipo tipoProducto) {
        return calcularPrecioRecomendadoGeneral(obtenerProductoGeneral(productoId, tipoProducto));
    }

    public Double calcularPrecioRecomendadoGeneral(Producto producto) {
        if (producto instanceof ProductoPinturaGeneral pintura) {
            return calcularPrecioBasePintura(pintura);
        }

        return valorSeguro(producto.getPrecioBase());
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

    private Double calcularPrecioBasePintura(ProductoPinturaGeneral pintura) {
        double valorColorBasePorLitro = pintura.getColorBase() != null
                ? valorSeguro(pintura.getColorBase().getValorFijoPorLitro())
                : 0D;
        double litros = pintura.getTamanoEnv() != null
                ? valorSeguro(pintura.getTamanoEnv().getCapacidad())
                : 0D;
        double valorTipoPinturaPorLitro = pintura.getTipoPintura() != null
                ? valorSeguro(pintura.getTipoPintura().getValorFijoPorLitro())
                : 0D;

        return valorColorBasePorLitro * litros * valorTipoPinturaPorLitro;
    }

    private Double valorSeguro(Double valor) {
        return valor != null ? valor : 0D;
    }

    private Double factorPorcentaje(Double porcentaje) {
        return 1D + (valorSeguro(porcentaje) / 100D);
    }
}

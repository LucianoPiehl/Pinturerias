package com.pinturerias.com.pinturerias.sucursal.service;

import com.pinturerias.com.pinturerias.compartidos.director.ProductoDirector;
import com.pinturerias.com.pinturerias.compartidos.dto.ProductoDTO;
import com.pinturerias.com.pinturerias.compartidos.entity.Producto;
import com.pinturerias.com.pinturerias.compartidos.entity.sucursal.ProductoPinturaSucursal;
import com.pinturerias.com.pinturerias.compartidos.entity.sucursal.ProductoOtroSucursal;
//import com.pinturerias.com.pinturerias.sucursal.dto.ProductoSucursalViewDTO;
import com.pinturerias.com.pinturerias.sucursal.repository.ProductoOtroSucursalRepository;
import com.pinturerias.com.pinturerias.sucursal.repository.ProductoPinturaSucursalRepository;

//import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class ProductoSucursalService {
    private final ProductoDirector director;
    private final ProductoPinturaSucursalRepository repoPintura;
    private final ProductoOtroSucursalRepository repoOtro;
    private final ProductoPrecioStockService ppsService;

    public ProductoSucursalService(
            ProductoDirector director,
            ProductoPinturaSucursalRepository pinturaRepo,
            ProductoOtroSucursalRepository otroRepo,
            ProductoPrecioStockService ppsService
    ) {
        this.director = director;
        this.repoPintura = pinturaRepo;
        this.repoOtro = otroRepo;
        this.ppsService = ppsService;
    };
    
    public Producto crearPintura(ProductoDTO dto) {

        Producto producto = director.construirProducto(dto);       
        return repoPintura.save((ProductoPinturaSucursal) producto);
    }

    public Producto crearOtro(ProductoDTO dto) {

        Producto producto = director.construirProducto(dto);       
        return repoOtro.save((ProductoOtroSucursal) producto);
    }


    public List<ProductoOtroSucursal> listarProductosOtro() {
        return repoOtro.findAll();
    }
    
    public List<ProductoPinturaSucursal> listarProductosPintura() {
        return repoPintura.findAll();
    }

    public void eliminarProductoOtro(Long id) {
        repoOtro.deleteById(id);
    }
    public void eliminarProductoPintura(Long id) {
        repoOtro.deleteById(id);
    }
}

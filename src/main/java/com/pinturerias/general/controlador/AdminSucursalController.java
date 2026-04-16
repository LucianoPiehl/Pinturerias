package com.pinturerias.general.controlador;

import com.pinturerias.general.entidad.Sucursal;
import com.pinturerias.general.servicio.SucursalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/general/sucursales")
public class AdminSucursalController {

    private final SucursalService servicio;

    @GetMapping
    public List<Sucursal> getAll() {
        return servicio.listar();
    }

    @PostMapping
    public Sucursal create(@RequestBody Sucursal s) {
        return servicio.registrar(s);
    }


    @PutMapping("/{id}")
    public Sucursal updateSucursal(
            @PathVariable Long id,
            @RequestBody Sucursal s) {

        return servicio.actualizarSucursal(id, s);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        servicio.eliminar(id);


    }
    public AdminSucursalController(SucursalService servicio) {
        this.servicio = servicio;

    }


}



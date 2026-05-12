package com.pinturerias.general.controlador;

import com.pinturerias.compartidos.dto.usuario.UsuarioCreateDTO;
import com.pinturerias.compartidos.dto.usuario.UsuarioResponseDTO;
import com.pinturerias.compartidos.dto.usuario.UsuarioUpdateDTO;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.servicio.UsuarioOrquestadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/general/usuarios")
@RequiredArgsConstructor
public class UsuarioGeneralController {
    private final UsuarioOrquestadorService usuarioOrquestadorService;

    @GetMapping
    public List<UsuarioResponseDTO> listar(){
        return usuarioOrquestadorService.listarUsuariosGeneral();
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO listarUsuario(@PathVariable Long id){
        return usuarioOrquestadorService.listarUsuarioGeneral(id);
    }

    @PostMapping("/encargado-sucursal")
    public void crearUsuarioEncargado(@Valid @RequestBody UsuarioCreateDTO dto){
        dto.setContexto(Contexto.SUCURSAL);// seteamos contexto sucursal porque este usuario pertenece a sucursal por mas que lo estemos creando desde general
        usuarioOrquestadorService.crearUsuarioEncargadoEnSucursal(dto);
    }

    @PostMapping
    public UsuarioResponseDTO crear(@Valid @RequestBody UsuarioCreateDTO dto){
        dto.setContexto(Contexto.GENERAL);
        return usuarioOrquestadorService.crearUsuarioGeneral(dto);
    }

    @PutMapping("/{id}")
    public void actualizar(@Valid @RequestBody UsuarioUpdateDTO dto){
        usuarioOrquestadorService.actualizarUsuarioGeneral(dto);
    }

    @PutMapping("/{id}/deshabilitar")
    public boolean deshabilitarUsuario(@PathVariable Long id){
        return usuarioOrquestadorService.deshabilitarUsuarioGeneral(id);
    }
}

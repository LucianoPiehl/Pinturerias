package com.pinturerias.sucursal.controlador;

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
@RequestMapping("/api/sucursal/usuarios")
@RequiredArgsConstructor
public class UsuarioSucursalController {

    private final UsuarioOrquestadorService usuarioOrquestadorService;

    @GetMapping
    public List<UsuarioResponseDTO> listar(){
        return usuarioOrquestadorService.listarUsuariosSucursal();
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO listarUsuario(Long id){
        return usuarioOrquestadorService.listarUsuarioSucursal(id);
    }

    @PostMapping
    public UsuarioResponseDTO crear(@Valid @RequestBody UsuarioCreateDTO dto){
        dto.setContexto(Contexto.SUCURSAL);
        return usuarioOrquestadorService.crearUsuarioSucursal(dto);
    }

    @PutMapping("/{id}")
    public void actualizar(@Valid @RequestBody UsuarioUpdateDTO dto){
        usuarioOrquestadorService.actualizarUsuarioSucursal(dto);
    }

    @PutMapping("/{id}/deshabilitar")
    public boolean deshabilitarUsuario(@PathVariable Long id){
        return usuarioOrquestadorService.deshabilitarUsuarioSucursal(id);
    }
}

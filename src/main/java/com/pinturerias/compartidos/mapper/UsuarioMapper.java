package com.pinturerias.compartidos.mapper;

import com.pinturerias.compartidos.dto.usuario.UsuarioCreateDTO;
import com.pinturerias.compartidos.dto.usuario.UsuarioResponseDTO;
import com.pinturerias.compartidos.entidad.shared.Usuario;
import com.pinturerias.general.entidad.UsuarioIndice;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toUsuario(UsuarioCreateDTO dto) {

        Usuario usuario = new Usuario();

        usuario.setNombre(dto.getNombre());

        usuario.setTelefono(dto.getTelefono());

        usuario.setHabilitado(true);

        usuario.setRol(dto.getRol());

        return usuario;
    }

    public UsuarioIndice toUsuarioIndice(UsuarioCreateDTO dto) {

        UsuarioIndice indice = new UsuarioIndice();

        indice.setUsername(dto.getUsername());

        indice.setPassword(dto.getPassword());

        indice.setSucursalId(dto.getSucursalId());

        indice.setRol(dto.getRol());

        indice.setContexto(dto.getContexto());

        indice.setHabilitado(true);

        return indice;
    }

    public UsuarioResponseDTO toResponseDTO(
            Usuario usuario,
            UsuarioIndice indice
    ){
        return new UsuarioResponseDTO(usuario.getId(), indice.getUsername() ,usuario.getNombre(), indice.getRol(),usuario.getHabilitado());
    }
}

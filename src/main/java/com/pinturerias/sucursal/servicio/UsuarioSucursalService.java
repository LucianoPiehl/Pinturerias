package com.pinturerias.sucursal.servicio;

import com.pinturerias.compartidos.dto.usuario.UsuarioUpdateDTO;
import com.pinturerias.compartidos.entidad.shared.Usuario;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import com.pinturerias.sucursal.repositorio.UsuarioSucursalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioSucursalService {

    private UsuarioSucursalRepository usuarioSucursalRepository;

    public Usuario crear(Usuario usuario){
        return usuarioSucursalRepository.save(usuario);
    }

    public List<Usuario> listar(){
        return usuarioSucursalRepository.findAll();
    }

    public Usuario actualizar(UsuarioUpdateDTO datos){
        Usuario u = usuarioSucursalRepository.findById(datos.getId()).orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        u.setNombre(datos.getNombre());
        u.setRol(datos.getRol());
        u.setHabilitado(datos.getHabilitado());
        u.setTelefono(datos.getTelefono());
        return usuarioSucursalRepository.save(u);
    }

    public void deshabilitar(Long id){
        Usuario u = usuarioSucursalRepository.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("Usuario No Encontrado"));
        u.setHabilitado(false);
        usuarioSucursalRepository.save(u);
    }

    public Usuario listarUsuario(Long id) {
        return usuarioSucursalRepository.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("Usuario No Encontrado"));
    }
}

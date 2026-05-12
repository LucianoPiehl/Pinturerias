package com.pinturerias.general.servicio;

import com.pinturerias.compartidos.dto.usuario.UsuarioUpdateDTO;
import com.pinturerias.compartidos.entidad.shared.Usuario;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import com.pinturerias.general.repositorio.UsuarioGeneralRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioGeneralService {

    private UsuarioGeneralRepository usuarioGeneralRepository;

    public Usuario crear(Usuario usuario){
        return usuarioGeneralRepository.save(usuario);
    }

    public List<Usuario> listar(){
        return usuarioGeneralRepository.findAll();
    }

    public Usuario listarUsuario(Long id) {
        return usuarioGeneralRepository.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("Usuario No Encontrado"));
    }

    public Usuario actualizar(UsuarioUpdateDTO datos){
        Usuario u = usuarioGeneralRepository.findById(datos.getId()).orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        u.setNombre(datos.getNombre());
        u.setRol(datos.getRol());
        u.setHabilitado(datos.getHabilitado());
        u.setTelefono(datos.getTelefono());
        return usuarioGeneralRepository.save(u);
    }

    public void deshabilitar(Long id){
        Usuario u = usuarioGeneralRepository.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("Usuario No Encontrado"));
        u.setHabilitado(false);
        usuarioGeneralRepository.save(u);
    }

}

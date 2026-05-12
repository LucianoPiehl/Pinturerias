package com.pinturerias.general.servicio;

import com.pinturerias.compartidos.dto.usuario.UsuarioUpdateDTO;
import com.pinturerias.compartidos.entidad.shared.Usuario;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import com.pinturerias.general.entidad.UsuarioIndice;
import com.pinturerias.general.repositorio.UsuarioIndiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioIndiceService {

    private UsuarioIndiceRepository usuarioIndiceRepository;

    public UsuarioIndice crear(UsuarioIndice usuarioIndice){
        usuarioIndiceRepository.save(usuarioIndice);
        return usuarioIndice;
    }

    public List<UsuarioIndice> listar(){
        return usuarioIndiceRepository.findAll();
    }

    public void eliminar(Long id){
        UsuarioIndice u = usuarioIndiceRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        if (!u.getHabilitado()){
            usuarioIndiceRepository.delete(u);
        }
        throw new IllegalArgumentException(
                "El Usuario Se Encuentra Habilitado Aún, Deshabilitelo Para Poder Eliminarlo"
        );
    }

    public boolean deshabilitar(Long id){
        UsuarioIndice u = usuarioIndiceRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        u.setHabilitado(false);
        usuarioIndiceRepository.save(u);
        return true;
    }

    public boolean existsByUsername(String username) {
        return usuarioIndiceRepository.existsByUsername(username);
    }

    public UsuarioIndice actualizar(UsuarioUpdateDTO datos){
        UsuarioIndice u = usuarioIndiceRepository.findById(datos.getId()).orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        u.setUsername(datos.getUsername());
        u.setRol(datos.getRol());
        u.setHabilitado(datos.getHabilitado());
        return usuarioIndiceRepository.save(u);
    }

    public UsuarioIndice listarIndice(Long id) {
        return usuarioIndiceRepository.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("Indice No Encontrado"));
    }
}

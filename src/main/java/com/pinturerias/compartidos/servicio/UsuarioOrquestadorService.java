package com.pinturerias.compartidos.servicio;

import com.pinturerias.compartidos.dto.usuario.UsuarioCreateDTO;
import com.pinturerias.compartidos.dto.usuario.UsuarioResponseDTO;
import com.pinturerias.compartidos.dto.usuario.UsuarioUpdateDTO;
import com.pinturerias.compartidos.entidad.shared.Usuario;
import com.pinturerias.compartidos.mapper.UsuarioMapper;
import com.pinturerias.configuracion.tenant.TenantExecutor;
import com.pinturerias.general.entidad.UsuarioIndice;
import com.pinturerias.general.servicio.UsuarioGeneralService;
import com.pinturerias.general.servicio.UsuarioIndiceService;
import com.pinturerias.sucursal.servicio.UsuarioSucursalService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsuarioOrquestadorService {
    private final UsuarioGeneralService usuarioGeneralService;
    private final UsuarioSucursalService usuarioSucursalService;
    private final UsuarioIndiceService usuarioIndiceService;
    private final TenantExecutor tenantExecutor;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDTO crearUsuarioGeneral(UsuarioCreateDTO dto){
        UsuarioIndice indice = crearIndice(dto);
        Usuario usuario = crearUsuario(indice, dto);
        usuarioGeneralService.crear(usuario);
        return usuarioMapper.toResponseDTO(usuario, indice);
    }

    @Transactional
    public UsuarioResponseDTO crearUsuarioSucursal(UsuarioCreateDTO dto){
        UsuarioIndice indice = tenantExecutor.ejecutarEnTenant(null, () -> crearIndice(dto));
        Usuario usuario = crearUsuario(indice, dto);
        usuarioSucursalService.crear(usuario);
        return usuarioMapper.toResponseDTO(usuario, indice);
    }

    @Transactional(readOnly = true) //Le decimos a SpringBoot que no hace falta revisar si se modificaron datos ya que solo hace un get (mejor performance)
    public List<UsuarioResponseDTO> listarUsuariosSucursal(){
        List<Usuario> usuarios = usuarioSucursalService.listar();
        List<UsuarioIndice> usuarioIndices = tenantExecutor.ejecutarEnTenant(null, ()-> usuarioIndiceService.listar());
        return sincronizarConIndices(usuarios,usuarioIndices);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarUsuariosGeneral(){
        List<Usuario> usuarios = usuarioGeneralService.listar();
        List<UsuarioIndice> usuarioIndices = usuarioIndiceService.listar();
        return sincronizarConIndices(usuarios, usuarioIndices);
    }

    @Transactional(readOnly = true)//Averiguar que hace esto y porque true y no false
    public UsuarioResponseDTO listarUsuarioSucursal(Long id){
        Usuario usuario = usuarioSucursalService.listarUsuario(id);
        UsuarioIndice indice = tenantExecutor.ejecutarEnTenant(null, ()->usuarioIndiceService.listarIndice(id));
        return usuarioMapper.toResponseDTO(
                usuario,
                indice
        );
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO listarUsuarioGeneral(Long id){
        Usuario usuario = usuarioGeneralService.listarUsuario(id);
        UsuarioIndice indice = usuarioIndiceService.listarIndice(id);
        return usuarioMapper.toResponseDTO(
                usuario,
                indice
        );
    }

    @Transactional
    public void crearUsuarioEncargadoEnSucursal(UsuarioCreateDTO dto){
        UsuarioIndice indice = usuarioIndiceService.crear(usuarioMapper.toUsuarioIndice(dto));
        Usuario usuario = usuarioMapper.toUsuario(dto);
        usuario.setId(indice.getId());
        tenantExecutor.ejecutarEnTenant(dto.getSucursalId(), ()-> usuarioSucursalService.crear(usuario));
    }

    @Transactional
    public void actualizarUsuarioGeneral(UsuarioUpdateDTO dto){
        usuarioIndiceService.actualizar(dto);
        usuarioGeneralService.actualizar(dto);
    }

    @Transactional
    public boolean deshabilitarUsuarioGeneral(Long id){
        usuarioGeneralService.deshabilitar(id);
        return usuarioIndiceService.deshabilitar(id);
    }

    @Transactional
    public boolean deshabilitarUsuarioSucursal(Long id){
        usuarioSucursalService.deshabilitar(id);
        return tenantExecutor.ejecutarEnTenant(null,()-> usuarioIndiceService.deshabilitar(id));
    }

    @Transactional
    public void actualizarUsuarioSucursal(UsuarioUpdateDTO dto){
        usuarioSucursalService.actualizar(dto);
        tenantExecutor.ejecutarEnTenant(null,()->usuarioIndiceService.actualizar(dto));
    }

    private UsuarioIndice crearIndice(UsuarioCreateDTO dto){
        if(usuarioIndiceService.existsByUsername(dto.getUsername())){
            throw new IllegalArgumentException(
                    "El username ya existe"
            );
        }
        UsuarioIndice indice = usuarioMapper.toUsuarioIndice(dto);
        indice.setPassword(passwordEncoder.encode(dto.getPassword()));
        indice = usuarioIndiceService.crear(indice);
        return indice;
    }

    private Usuario crearUsuario(UsuarioIndice usuarioIndice, UsuarioCreateDTO dto){
        Usuario usuario = usuarioMapper.toUsuario(dto);
        usuario.setId(usuarioIndice.getId());
        return usuario;
    }

    private List<UsuarioResponseDTO> sincronizarConIndices(List<Usuario> usuarios, List<UsuarioIndice> usuarioIndices){
        Map<Long, UsuarioIndice> indicesMap =
                usuarioIndices.stream()
                        .collect(Collectors.toMap(
                                UsuarioIndice::getId,
                                i -> i));

        return usuarios.stream().map(usuario -> {
            UsuarioIndice indice = indicesMap.get(usuario.getId());
            if(indice == null){
                throw new IllegalStateException(
                        "UsuarioIndice no encontrado para usuario id: "
                                + usuario.getId()
                );
            }
            return usuarioMapper.toResponseDTO(
                    usuario,
                    indice
            );
                })
                .toList();

    }
}

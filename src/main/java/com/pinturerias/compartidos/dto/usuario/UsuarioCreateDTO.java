package com.pinturerias.compartidos.dto.usuario;

import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.enumeracion.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioCreateDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String nombre;

    private String telefono;
    @NotNull
    private Rol rol;

    private String sucursalId;

    private Contexto contexto;

}

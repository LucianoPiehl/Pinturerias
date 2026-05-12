package com.pinturerias.compartidos.dto.usuario;

import com.pinturerias.compartidos.enumeracion.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioUpdateDTO {
    @NotNull
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String nombre;
    @NotBlank
    private String telefono;
    @NotNull
    private Rol rol;

    private Boolean habilitado;

}

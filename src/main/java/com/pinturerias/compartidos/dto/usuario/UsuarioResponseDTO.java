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
public class UsuarioResponseDTO {
    @NotBlank
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String nombre;
    @NotNull
    private Rol rol;

    private boolean habilitado;
}

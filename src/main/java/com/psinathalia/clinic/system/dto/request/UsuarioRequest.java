package com.psinathalia.clinic.system.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioRequest {
    private String cpf;
    private String email;
    private String senha;
    private Long pacienteId;
}

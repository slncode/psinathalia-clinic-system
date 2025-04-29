package com.psinathalia.clinic.system.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponse {

    private Long id;
    private String cpf;
    private String email;
    private String senha;
    private Long pacienteId;
}

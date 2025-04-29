package com.psinathalia.clinic.system.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroPacienteResponse {
    private UsuarioResponse usuario;
    private PacienteResponse paciente;
}

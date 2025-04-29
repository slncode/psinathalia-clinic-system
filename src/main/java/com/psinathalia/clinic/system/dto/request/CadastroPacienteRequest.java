package com.psinathalia.clinic.system.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroPacienteRequest {
    private UsuarioRequest usuario;
    private PacienteRequest paciente;

}
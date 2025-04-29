package com.psinathalia.clinic.system.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PacienteResponse {

    private Long id;
    private PessoaResponse pessoa;
    private EnderecoResponse endereco;
    private String escola;
    private String responsavelFinanceiro;

}
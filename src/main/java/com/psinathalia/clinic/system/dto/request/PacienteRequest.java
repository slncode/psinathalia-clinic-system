package com.psinathalia.clinic.system.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PacienteRequest {

    @NotNull(message = "Pessoa é obrigatória")
    @Valid
    private PessoaRequest pessoa;

    @NotNull(message = "Endereço é obrigatório")
    @Valid
    private EnderecoRequest endereco;

    private String escola;
    private String responsavelFinanceiro;

}
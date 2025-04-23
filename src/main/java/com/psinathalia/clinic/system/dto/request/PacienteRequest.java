package com.psinathalia.clinic.system.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class PacienteRequest {

    @NotNull(message = "Pessoa é obrigatória")
    @Valid
    private PessoaRequest pessoa;

    @NotNull(message = "Endereço é obrigatório")
    @Valid
    private EnderecoRequest endereco;

    private String responsavelFinanceiro;
    private String escola;

    // Getters e Setters

    public PessoaRequest getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaRequest pessoa) {
        this.pessoa = pessoa;
    }

    public EnderecoRequest getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoRequest endereco) {
        this.endereco = endereco;
    }

    public String getResponsavelFinanceiro() {
        return responsavelFinanceiro;
    }

    public void setResponsavelFinanceiro(String responsavelFinanceiro) {
        this.responsavelFinanceiro = responsavelFinanceiro;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }
}
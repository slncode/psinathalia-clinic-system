package com.psinathalia.clinic.system.dto.response;

public class PacienteResponse {

    private Long id;
    private PessoaResponse pessoa;
    private EnderecoResponse endereco;
    private String escola;
    private String responsavelFinanceiro;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PessoaResponse getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaResponse pessoa) {
        this.pessoa = pessoa;
    }

    public EnderecoResponse getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoResponse endereco) {
        this.endereco = endereco;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }

    public String getResponsavelFinanceiro() {
        return responsavelFinanceiro;
    }

    public void setResponsavelFinanceiro(String responsavelFinanceiro) {
        this.responsavelFinanceiro = responsavelFinanceiro;
    }
}
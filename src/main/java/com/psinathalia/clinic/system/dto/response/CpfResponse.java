package com.psinathalia.clinic.system.dto.response;

public class CpfResponse {
    private String cpf;

    public CpfResponse() {}

    public CpfResponse(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
package com.psinathalia.clinic.system.dto.request;

public class VerificarCpfRequest {
    private String cpfDigitado;
    private Long pacienteId;

    // Getters e Setters
    public String getCpfDigitado() {
        return cpfDigitado;
    }

    public void setCpfDigitado(String cpfDigitado) {
        this.cpfDigitado = cpfDigitado;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }
}
package com.psinathalia.clinic.system.exception;

public class PacienteNotFoundException extends RuntimeException{
    public PacienteNotFoundException(Long pacienteId) {
        super("Paciente com ID " + pacienteId + " não encontrado.");
    }
}

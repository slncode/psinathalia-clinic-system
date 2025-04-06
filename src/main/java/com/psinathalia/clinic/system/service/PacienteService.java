package com.psinathalia.clinic.system.service;

import com.psinathalia.clinic.system.model.Paciente;
import com.psinathalia.clinic.system.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private CryptoService cryptoService;

    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> findById(Long id) {
        return pacienteRepository.findById(id);
    }

    public Paciente save(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public void delete(Long id) {
        pacienteRepository.deleteById(id);
    }

    public boolean verificarCpf(String cpfDigitado, Long pacienteId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));

        // Comparar CPF digitado com CPF armazenado (criptografado)
        return cryptoService.matches(cpfDigitado, paciente.getPessoa().getCpf());
    }

    public String getCpfDescriptografado(Long pacienteId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));
        return cryptoService.decrypt(paciente.getPessoa().getCpf());
    }
}

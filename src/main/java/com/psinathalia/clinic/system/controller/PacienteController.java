package com.psinathalia.clinic.system.controller;

import com.psinathalia.clinic.system.model.Endereco;
import com.psinathalia.clinic.system.model.Paciente;
import com.psinathalia.clinic.system.repository.PacienteRepository;
import com.psinathalia.clinic.system.service.CryptoService;
import com.psinathalia.clinic.system.service.PacienteService;
import com.psinathalia.clinic.system.service.CepService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pacientes")
@CrossOrigin(origins = "http://localhost:3000")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private CepService cepService;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping
    public List<Paciente> getAllPacientes() {
        List<Paciente> pacientes = pacienteService.findAll();
        pacientes.sort(Comparator.comparing(paciente -> paciente.getPessoa().getNome()));
        return pacientes;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Paciente> getPacienteById(@PathVariable Long id) {
        return pacienteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Paciente> createPaciente(@RequestBody @Valid Paciente paciente) {
        // Remove caracteres especiais do CPF
        String cpf = paciente.getPessoa().getCpf().replaceAll("[^\\d]", "");
        if (cpf.length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos.");
        }
        //paciente.getPessoa().setCpf(cpf);
        paciente.getPessoa().setCpf(cryptoService.encrypt(cpf));

        try {
            if (paciente.getEndereco() != null && paciente.getEndereco().getCep() != null) {
                Endereco enderecoPreenchido = cepService.buscarEnderecoPorCep(paciente.getEndereco().getCep());

                if (enderecoPreenchido == null || enderecoPreenchido.getCep() == null) {
                    return ResponseEntity.badRequest().body(null);
                }

                enderecoPreenchido.setNumero(paciente.getEndereco().getNumero());
                enderecoPreenchido.setComplemento(paciente.getEndereco().getComplemento());

                paciente.setEndereco(enderecoPreenchido);
                paciente.getPessoa().setIdade(Period.between(paciente.getPessoa().getDataNascimento(), LocalDate.now()).getYears());
            } else {
                return ResponseEntity.badRequest().body(null);
            }

            Paciente novoPaciente = pacienteRepository.save(paciente);
            return ResponseEntity.ok(novoPaciente);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/verificarCpf")
    public ResponseEntity<Boolean> verificarCpf(@RequestBody Map<String, String> requestBody) {
        String cpfDigitado = requestBody.get("cpfDigitado");
        Long pacienteId = requestBody.containsKey("pacienteId") ? Long.parseLong(requestBody.get("pacienteId")) : null;

        if (cpfDigitado == null) {
            return ResponseEntity.badRequest().body(false);
        }

        boolean resultado = pacienteService.verificarCpf(cpfDigitado, pacienteId);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}/cpf")
    public ResponseEntity<String> getCpf(@PathVariable Long id) {
        String cpfDescriptografado = pacienteService.getCpfDescriptografado(id);
        return ResponseEntity.ok(cpfDescriptografado);
    }




//    @PostMapping
//    public Paciente createPaciente(@RequestBody Paciente paciente) {
//        // Remove caracteres especiais (pontos e traços) do CPF
//        String cpf = paciente.getCpf().replaceAll("[^\\d]", ""); // Mantém apenas os números
//
//        // Verifica se o CPF possui 11 dígitos
//        if (cpf.length() != 11) {
//            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos.");
//        }
//
//        // Atualiza o CPF do usuário com apenas os números
//        paciente.setCpf(cpf);
//
//        // Salva o usuário no banco
//        return pacienteService.save(paciente);
//    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        pacienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

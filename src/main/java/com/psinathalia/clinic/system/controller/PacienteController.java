package com.psinathalia.clinic.system.controller;

import com.psinathalia.clinic.system.dto.request.PacienteRequest;
import com.psinathalia.clinic.system.dto.request.VerificarCpfRequest;
import com.psinathalia.clinic.system.dto.response.CpfResponse;
import com.psinathalia.clinic.system.dto.response.PacienteResponse;
import com.psinathalia.clinic.system.exception.CpfInvalidoException;
import com.psinathalia.clinic.system.exception.EnderecoInvalidoException;
import com.psinathalia.clinic.system.exception.PacienteNotFoundException;
import com.psinathalia.clinic.system.model.Endereco;
import com.psinathalia.clinic.system.model.Paciente;
import com.psinathalia.clinic.system.repository.PacienteRepository;
import com.psinathalia.clinic.system.service.CryptoService;
import com.psinathalia.clinic.system.service.PacienteService;
import com.psinathalia.clinic.system.service.CepService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.psinathalia.clinic.system.mapper.PacienteMapper;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/pacientes")
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
    public ResponseEntity<List<PacienteResponse>> getAllPacientes() {
        List<PacienteResponse> pacientes = pacienteService.findAllOrdered();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> getPacienteById(@PathVariable Long id) {
        try {
            PacienteResponse response = pacienteService.findByIdResponse(id);
            return ResponseEntity.ok(response);
        } catch (PacienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/cpf")
    public ResponseEntity<CpfResponse> getCpf(@PathVariable Long id) {
        String cpfDescriptografado = pacienteService.getCpfDescriptografado(id);
        return ResponseEntity.ok(new CpfResponse(cpfDescriptografado));
    }

    @PostMapping
    public ResponseEntity<PacienteResponse> createPaciente(@RequestBody @Valid PacienteRequest request) {
        PacienteResponse paciente = pacienteService.createPaciente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(paciente);
    }

    @PostMapping("/verificarCpf")
    public ResponseEntity<Boolean> verificarCpf(@RequestBody VerificarCpfRequest request) {
        String cpfDigitado = request.getCpfDigitado();
        Long pacienteId = request.getPacienteId();

        if (cpfDigitado == null) {
            return ResponseEntity.badRequest().body(false);
        }

        boolean resultado = pacienteService.verificarCpf(cpfDigitado, pacienteId);
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um paciente", description = "Remove um paciente do sistema pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Paciente deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
            @ApiResponse(responseCode = "400", description = "ID inválido")
    })
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        try {
            pacienteService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (PacienteNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado");
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao deletar paciente"
            );
        }
    }

}

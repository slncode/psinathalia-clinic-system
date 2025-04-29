package com.psinathalia.clinic.system.controller;

import com.psinathalia.clinic.system.dto.request.CadastroPacienteRequest;
import com.psinathalia.clinic.system.dto.response.CadastroPacienteResponse;
import com.psinathalia.clinic.system.exception.DadosInvalidosException;
import com.psinathalia.clinic.system.exception.UsuarioJaExisteException;
import com.psinathalia.clinic.system.service.CadastroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/cadastro")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CadastroController {

    private final CadastroService cadastroService;

    @PostMapping
    public ResponseEntity<CadastroPacienteResponse> cadastrar(@Valid @RequestBody CadastroPacienteRequest request) {
        try {
            CadastroPacienteResponse response = cadastroService.cadastrarPacienteComUsuario(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UsuarioJaExisteException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (DadosInvalidosException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao processar cadastro: " + e.getMessage()
            );
        }
    }

    @ExceptionHandler(UsuarioJaExisteException.class)
    public ResponseEntity<String> handleUsuarioJaExiste(UsuarioJaExisteException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(DadosInvalidosException.class)
    public ResponseEntity<String> handleDadosInvalidos(DadosInvalidosException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor: " + e.getMessage());
    }
}
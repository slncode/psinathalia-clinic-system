package com.psinathalia.clinic.system.controller;

import com.psinathalia.clinic.system.dto.response.CepResponse;
import com.psinathalia.clinic.system.exception.CepNotFoundException;
import com.psinathalia.clinic.system.model.Endereco;
import com.psinathalia.clinic.system.service.CepService;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cep")
@CrossOrigin(origins = "http://localhost:3000")
public class CepController {

    private final CepService cepService;

    public CepController(CepService cepService) {
        this.cepService = cepService;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<CepResponse> buscarCep(
            @PathVariable
            @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos numéricos")
            String cep) {
        try {
            Endereco endereco = cepService.buscarEnderecoPorCep(cep);
            return ResponseEntity.ok(new CepResponse("ok", "Endereço encontrado", endereco));
        } catch (CepNotFoundException e) {
            return ResponseEntity.status(404)
                    .body(new CepResponse("erro", e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new CepResponse("erro", "Erro ao consultar CEP: " + e.getMessage(), null));
        }
    }
}
package com.psinathalia.clinic.system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoRequest {

    // Getters e Setters
    @NotBlank(message = "CEP é obrigatório")
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    @NotBlank(message = "Número é obrigatório")
    private String numero;

    private String complemento;

}
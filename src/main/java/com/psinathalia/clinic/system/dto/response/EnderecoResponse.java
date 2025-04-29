package com.psinathalia.clinic.system.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoResponse {

    private Long id;
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private String numero;
    private String complemento;

}
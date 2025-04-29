package com.psinathalia.clinic.system.dto.response;

import com.psinathalia.clinic.system.enums.Sexo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class PessoaResponse {

    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private String idade;
    private String cpf;
    private String email;
    private Sexo sexo;




}
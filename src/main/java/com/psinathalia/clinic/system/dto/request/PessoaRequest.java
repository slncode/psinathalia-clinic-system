package com.psinathalia.clinic.system.dto.request;

import com.psinathalia.clinic.system.enums.Sexo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class PessoaRequest {

    // Getters e Setters
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate dataNascimento;


    @NotBlank(message = "CPF é obrigatório")
    //@CPF(message = "CPF inválido")
    private String cpf;

    @NotNull(message = "Sexo é obrigatório")
    private Sexo sexo;

    @Email
    private String email;

}
package com.psinathalia.clinic.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "enderecos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String cep;

    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;

    @NotNull
    private String numero;

    private String complemento;

    @OneToOne(mappedBy = "endereco")
    private Paciente paciente;
}
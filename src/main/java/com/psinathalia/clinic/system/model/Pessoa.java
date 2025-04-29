package com.psinathalia.clinic.system.model;

import com.psinathalia.clinic.system.enums.Sexo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "pessoas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @NotNull
    private LocalDate dataNascimento;

    private String idade;

    @NotNull
    @Column(unique = true)
    private String cpf;

    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sexo sexo;

    @OneToOne(mappedBy = "pessoa")
    private Paciente paciente;
}
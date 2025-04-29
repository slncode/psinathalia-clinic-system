package com.psinathalia.clinic.system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String token;
    private String tipo;
    private String nome;
    private String perfil;
}

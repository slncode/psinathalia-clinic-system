package com.psinathalia.clinic.system.dto.response;

import com.psinathalia.clinic.system.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CepResponse {
    private String status;
    private String message;
    private Endereco endereco;
}
package com.psinathalia.clinic.system.service;

import com.psinathalia.clinic.system.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SenhaService {

    @Autowired
    private SecurityConfig passwordEncoder;

    public String criptografarSenha(String senha) {
        return passwordEncoder.passwordEncoder().encode(senha);
    }

    public boolean verificarSenha(String senhaDigitada, String senhaArmazenada) {
        return passwordEncoder.passwordEncoder().matches(senhaDigitada, senhaArmazenada);
    }
}
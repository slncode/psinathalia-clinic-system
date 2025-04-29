package com.psinathalia.clinic.system.mapper;

import com.psinathalia.clinic.system.dto.request.UsuarioRequest;
import com.psinathalia.clinic.system.dto.response.UsuarioResponse;
import com.psinathalia.clinic.system.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    private final PacienteMapper pacienteMapper;

    public Usuario toModel(UsuarioRequest request) {
        if (request == null) return null;

        Usuario usuario = new Usuario();
        usuario.setCpf(request.getCpf());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(request.getSenha()); // A senha será criptografada no service
        return usuario;
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        if (usuario == null) return null;

        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setCpf(usuario.getCpf());
        response.setEmail(usuario.getEmail());

        if (usuario.getPaciente() != null) {
            response.setPacienteId(usuario.getPaciente().getId());
        }

        return response;
    }
}
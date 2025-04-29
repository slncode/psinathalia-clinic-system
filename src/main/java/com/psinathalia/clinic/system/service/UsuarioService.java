package com.psinathalia.clinic.system.service;

import com.psinathalia.clinic.system.dto.request.LoginRequest;
import com.psinathalia.clinic.system.dto.response.LoginResponse;
import com.psinathalia.clinic.system.exception.CredenciaisInvalidasException;
import com.psinathalia.clinic.system.model.Paciente;
import com.psinathalia.clinic.system.model.Usuario;
import com.psinathalia.clinic.system.repository.PacienteRepository;
import com.psinathalia.clinic.system.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SenhaService senhaService;

    public LoginResponse autenticar(LoginRequest request) {
        Optional<Usuario> usuarioOpt;

        // Verifica se é email ou CPF
        if (request.getLogin().contains("@")) {
            usuarioOpt = usuarioRepository.findByEmail(request.getLogin());
        } else {
            usuarioOpt = usuarioRepository.findByCpf(request.getLogin());
        }

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Aqui compare a senha usando BCrypt, por exemplo:
            if (senhaService.verificarSenha(request.getSenha(), usuario.getSenha())) {
                // Monta o LoginResponse conforme sua lógica
                return criarLoginResponse(usuario);
            }
        }

        throw new CredenciaisInvalidasException("Credenciais inválidas");
    }

    private LoginResponse criarLoginResponse(Usuario usuario) {
        LoginResponse response = new LoginResponse();

        // Obtendo o nome: pode ser via Paciente > Pessoa, ou direto se for psicólogo, etc.
        String nome = null;
        String perfil = null;

        if (usuario.getPaciente() != null) {
            // Nome do paciente
            nome = usuario.getPaciente().getPessoa().getNome();
            perfil = "PACIENTE";
        } else {
            // Se tiver outros perfis, adicione aqui
            nome = "Usuário"; // ou outro campo, se houver
            perfil = "USUARIO";
        }

        response.setNome(nome);
        response.setPerfil(perfil);
        response.setToken(gerarToken());
        response.setTipo("Bearer");
        return response;
    }

    private String gerarToken() {
        // Implementação do gerador de token
        return "token-teste";
    }
}
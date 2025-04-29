package com.psinathalia.clinic.system.service;

import com.psinathalia.clinic.system.dto.request.CadastroPacienteRequest;
import com.psinathalia.clinic.system.dto.response.CadastroPacienteResponse;
import com.psinathalia.clinic.system.exception.CpfInvalidoException;
import com.psinathalia.clinic.system.mapper.PacienteMapper;
import com.psinathalia.clinic.system.mapper.UsuarioMapper;
import com.psinathalia.clinic.system.model.Paciente;
import com.psinathalia.clinic.system.model.Usuario;
import com.psinathalia.clinic.system.repository.PacienteRepository;
import com.psinathalia.clinic.system.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastroService {

    private final UsuarioRepository usuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final CryptoService cryptoService;

    @Transactional
    public CadastroPacienteResponse cadastrarPacienteComUsuario(CadastroPacienteRequest request) {

        // 1. Validações iniciais
        String cpf = validarEProcessarCpf(request.getUsuario().getCpf());
        request.getPaciente().getPessoa().setCpf(cryptoService.encrypt(cpf));
        request.getUsuario().setCpf(cryptoService.encrypt(cpf));
        request.getPaciente().getPessoa().setEmail(request.getUsuario().getEmail());
        validarDadosCadastro(request);

        // 2. Cria o paciente (com pessoa e endereço)
        Paciente paciente = pacienteMapper.toModel(request.getPaciente());
        paciente = pacienteRepository.save(paciente);

        // 3. Cria o usuário
        Usuario usuario = usuarioMapper.toModel(request.getUsuario());
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setCpf(cryptoService.encrypt(cpf));
        usuario.setPaciente(paciente);
        usuario = usuarioRepository.save(usuario);

        // 4. Cria e retorna a response
        CadastroPacienteResponse response = new CadastroPacienteResponse();
        response.setPaciente(pacienteMapper.toResponse(paciente));
        response.setUsuario(usuarioMapper.toResponse(usuario));

        return response;
    }

    private void validarDadosCadastro(CadastroPacienteRequest request) {
        // Validar se CPF já existe
        if (usuarioRepository.findByCpf(request.getUsuario().getCpf()).isPresent()) {
            throw new RuntimeException("CPF já cadastrado");
        }

        // Validar se email já existe
        if (usuarioRepository.findByEmail(request.getUsuario().getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        // Validar se CPF e email do usuário correspondem aos dados da pessoa
        if (!request.getUsuario().getCpf().equals(request.getPaciente().getPessoa().getCpf())) {
            throw new RuntimeException("CPF do usuário deve ser igual ao CPF da pessoa");
        }

        if (!request.getUsuario().getEmail().equals(request.getPaciente().getPessoa().getEmail())) {
            throw new RuntimeException("Email do usuário deve ser igual ao email da pessoa");
        }
    }

    private String validarEProcessarCpf(String cpf) {
        String cpfLimpo = cpf.replaceAll("[^\\d]", "");
        if (cpfLimpo.length() != 11) {
            throw new CpfInvalidoException("CPF inválido. Deve conter 11 dígitos.");
        }
        return cpfLimpo;
    }
}
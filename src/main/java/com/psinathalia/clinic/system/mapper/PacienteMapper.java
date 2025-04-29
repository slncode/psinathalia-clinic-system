package com.psinathalia.clinic.system.mapper;

import com.psinathalia.clinic.system.dto.request.PacienteRequest;
import com.psinathalia.clinic.system.dto.response.PacienteResponse;
import com.psinathalia.clinic.system.model.Paciente;
import com.psinathalia.clinic.system.model.Pessoa;
import com.psinathalia.clinic.system.model.Endereco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PacienteMapper {

    private final PessoaMapper pessoaMapper;
    private final EnderecoMapper enderecoMapper;

    public Paciente toModel(PacienteRequest request) {

        if (request == null) return null;

        Paciente paciente = new Paciente();

        // Usar os mappers específicos
        Pessoa pessoa = pessoaMapper.toModel(request.getPessoa());
        if (pessoa != null) {
            pessoa.setPaciente(paciente);
            paciente.setPessoa(pessoa);
        }

        Endereco endereco = enderecoMapper.toModel(request.getEndereco());
        if (endereco != null) {
            endereco.setPaciente(paciente);
            paciente.setEndereco(endereco);
        }

        if (request.getResponsavelFinanceiro() != null && !request.getResponsavelFinanceiro().isEmpty()) {
            paciente.setResponsavelFinanceiro(request.getResponsavelFinanceiro());
        }

        if (request.getEscola() != null && !request.getEscola().isEmpty()) {
            paciente.setEscola(request.getEscola());
        }

        return paciente;
    }

    public PacienteResponse toResponse(Paciente paciente) {
        if (paciente == null) return null;

        PacienteResponse response = new PacienteResponse();
        response.setId(paciente.getId());
        response.setPessoa(pessoaMapper.toResponse(paciente.getPessoa()));
        response.setEndereco(enderecoMapper.toResponse(paciente.getEndereco()));
        response.setResponsavelFinanceiro(paciente.getResponsavelFinanceiro());
        response.setEscola(paciente.getEscola());

        return response;
    }
}
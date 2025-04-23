package com.psinathalia.clinic.system.mapper;

import com.psinathalia.clinic.system.dto.request.PacienteRequest;
import com.psinathalia.clinic.system.dto.request.PessoaRequest;
import com.psinathalia.clinic.system.dto.request.EnderecoRequest;
import com.psinathalia.clinic.system.dto.response.PacienteResponse;
import com.psinathalia.clinic.system.dto.response.PessoaResponse;
import com.psinathalia.clinic.system.dto.response.EnderecoResponse;
import com.psinathalia.clinic.system.model.Paciente;
import com.psinathalia.clinic.system.model.Pessoa;
import com.psinathalia.clinic.system.model.Endereco;

public class PacienteMapper {

    // Request -> Model
    public static Paciente toModel(PacienteRequest request) {
        if (request == null) return null;

        Paciente paciente = new Paciente();
        paciente.setPessoa(toModel(request.getPessoa()));
        paciente.setEndereco(toModel(request.getEndereco()));
        if(!request.getResponsavelFinanceiro().isEmpty() && request.getResponsavelFinanceiro() != null) {
            paciente.setResponsavelFinanceiro(request.getResponsavelFinanceiro());
        }
        if(!request.getEscola().isEmpty() && request.getEscola() != null) {
            paciente.setEscola(request.getEscola());
        }
        return paciente;
    }

    private static Pessoa toModel(PessoaRequest request) {
        if (request == null) return null;

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(request.getNome());
        pessoa.setCpf(request.getCpf());
        pessoa.setSexo(request.getSexo());
        pessoa.setEmail(request.getEmail());
        pessoa.setDataNascimento(request.getDataNascimento());
        return pessoa;
    }

    private static Endereco toModel(EnderecoRequest request) {
        if (request == null) return null;

        Endereco endereco = new Endereco();
        endereco.setCep(request.getCep());
        endereco.setNumero(request.getNumero());
        endereco.setComplemento(request.getComplemento());
        return endereco;
    }

    // Model -> Response
    public static PacienteResponse toResponse(Paciente paciente) {
        if (paciente == null) return null;

        PacienteResponse response = new PacienteResponse();
        response.setId(paciente.getId());
        response.setPessoa(toResponse(paciente.getPessoa()));
        response.setEndereco(toResponse(paciente.getEndereco()));
        response.setResponsavelFinanceiro(paciente.getResponsavelFinanceiro());
        response.setEscola(paciente.getEscola());
        return response;
    }

    private static PessoaResponse toResponse(Pessoa pessoa) {
        if (pessoa == null) return null;

        PessoaResponse response = new PessoaResponse();
        response.setNome(pessoa.getNome());
        response.setCpf(pessoa.getCpf());
        response.setSexo(pessoa.getSexo());
        response.setEmail(pessoa.getEmail());
        response.setDataNascimento(pessoa.getDataNascimento());
        response.setIdade(pessoa.getIdade());
        return response;
    }

    private static EnderecoResponse toResponse(Endereco endereco) {
        if (endereco == null) return null;

        EnderecoResponse response = new EnderecoResponse();
        response.setCep(endereco.getCep());
        response.setLogradouro(endereco.getLogradouro());
        response.setBairro(endereco.getBairro());
        response.setLocalidade(endereco.getLocalidade());
        response.setUf(endereco.getUf());
        response.setNumero(endereco.getNumero());
        response.setComplemento(endereco.getComplemento());
        return response;
    }
}
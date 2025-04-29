package com.psinathalia.clinic.system.mapper;

import com.psinathalia.clinic.system.dto.request.PessoaRequest;
import com.psinathalia.clinic.system.dto.response.PessoaResponse;
import com.psinathalia.clinic.system.model.Pessoa;
import org.springframework.stereotype.Component;

@Component
public class PessoaMapper {

    public Pessoa toModel(PessoaRequest request) {
        if (request == null) return null;

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(request.getNome());
        pessoa.setDataNascimento(request.getDataNascimento());
        pessoa.setCpf(request.getCpf());
        pessoa.setEmail(request.getEmail());
        pessoa.setSexo(request.getSexo());

        return pessoa;
    }

    public PessoaResponse toResponse(Pessoa pessoa) {
        if (pessoa == null) return null;

        PessoaResponse response = new PessoaResponse();
        response.setId(pessoa.getId());
        response.setNome(pessoa.getNome());
        response.setDataNascimento(pessoa.getDataNascimento());
        response.setIdade(pessoa.getIdade());
        response.setCpf(pessoa.getCpf());
        response.setEmail(pessoa.getEmail());
        response.setSexo(pessoa.getSexo());

        return response;
    }
}
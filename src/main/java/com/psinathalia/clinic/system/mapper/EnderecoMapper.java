package com.psinathalia.clinic.system.mapper;

import com.psinathalia.clinic.system.dto.request.EnderecoRequest;
import com.psinathalia.clinic.system.dto.response.EnderecoResponse;
import com.psinathalia.clinic.system.model.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

    public Endereco toModel(EnderecoRequest request) {
        if (request == null) return null;

        Endereco endereco = new Endereco();
        endereco.setCep(request.getCep());
        endereco.setLogradouro(request.getLogradouro());
        endereco.setBairro(request.getBairro());
        endereco.setLocalidade(request.getLocalidade());
        endereco.setUf(request.getUf());
        endereco.setNumero(request.getNumero());
        endereco.setComplemento(request.getComplemento());

        return endereco;
    }

    public EnderecoResponse toResponse(Endereco endereco) {
        if (endereco == null) return null;

        EnderecoResponse response = new EnderecoResponse();
        response.setId(endereco.getId());
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
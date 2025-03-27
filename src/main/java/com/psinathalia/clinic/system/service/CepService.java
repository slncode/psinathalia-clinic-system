package com.psinathalia.clinic.system.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psinathalia.clinic.system.model.Endereco;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CepService {
    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/";

    public Endereco buscarEnderecoPorCep(String cep) throws Exception {
        String url = VIA_CEP_URL + cep + "/json/";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Endereco endereco = objectMapper.readValue(response.body(), Endereco.class);


        if (endereco == null || endereco.getCep() == null) {
            throw new RuntimeException("CEP não encontrado");
        }

        return endereco;
    }

}

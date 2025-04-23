package com.psinathalia.clinic.system.service;

import com.psinathalia.clinic.system.dto.request.EnderecoRequest;
import com.psinathalia.clinic.system.dto.request.PacienteRequest;
import com.psinathalia.clinic.system.exception.CpfInvalidoException;
import com.psinathalia.clinic.system.exception.CpfJaCadastradoException;
import com.psinathalia.clinic.system.exception.EnderecoInvalidoException;
import com.psinathalia.clinic.system.exception.PacienteNotFoundException;
import com.psinathalia.clinic.system.mapper.PacienteMapper;
import com.psinathalia.clinic.system.model.Endereco;
import com.psinathalia.clinic.system.model.Paciente;
import com.psinathalia.clinic.system.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private CepService cepService;

    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> findById(Long id) {
        return pacienteRepository.findById(id);
    }

    public Paciente save(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public void delete(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new PacienteNotFoundException(id));
        pacienteRepository.delete(paciente);
    }

    public boolean verificarCpf(String cpfDigitado, Long pacienteId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNotFoundException(pacienteId));

        // Comparar CPF digitado com CPF armazenado (criptografado)
        return cryptoService.matches(cpfDigitado, paciente.getPessoa().getCpf());
    }

    public String getCpfDescriptografado(Long pacienteId) {

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new PacienteNotFoundException(pacienteId));

        return cryptoService.decrypt(paciente.getPessoa().getCpf());
    }


    @Transactional
    public Paciente createPaciente(PacienteRequest request) {
        // Converte request para entidade
        Paciente paciente = PacienteMapper.toModel(request);

        // Valida e processa CPF
        String cpf = validarEProcessarCpf(request.getPessoa().getCpf());
        paciente.getPessoa().setCpf(cryptoService.encrypt(cpf));

        // Busca e valida endereço
        Endereco endereco = null;
        try {
            endereco = buscarEValidarEndereco(request.getEndereco());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        paciente.setEndereco(endereco);

        // Calcula idade
        calcularIdade(paciente);

        // Salva paciente
        try {
            return pacienteRepository.save(paciente);
        } catch (DataIntegrityViolationException e) {
            throw new CpfJaCadastradoException("CPF já cadastrado.");
        }

    }

    private String validarEProcessarCpf(String cpf) {
        String cpfLimpo = cpf.replaceAll("[^\\d]", "");
        if (cpfLimpo.length() != 11) {
            throw new CpfInvalidoException("CPF inválido. Deve conter 11 dígitos.");
        }
        return cpfLimpo;
    }

    private Endereco buscarEValidarEndereco(EnderecoRequest enderecoRequest) throws Exception {
        if (enderecoRequest == null || enderecoRequest.getCep() == null) {
            throw new EnderecoInvalidoException("CEP é obrigatório");
        }

        Endereco enderecoPreenchido = cepService.buscarEnderecoPorCep(enderecoRequest.getCep());

        if (enderecoPreenchido == null || enderecoPreenchido.getCep() == null) {
            throw new EnderecoInvalidoException("CEP não encontrado");
        }

        enderecoPreenchido.setNumero(enderecoRequest.getNumero());
        enderecoPreenchido.setComplemento(enderecoRequest.getComplemento());

        return enderecoPreenchido;
    }

//    private void calcularIdade(Paciente paciente) {
//        if (paciente.getPessoa().getDataNascimento() != null) {
//            int idade = Period.between(
//                    paciente.getPessoa().getDataNascimento(),
//                    LocalDate.now()
//            ).getYears();
//            paciente.getPessoa().setIdade(idade);
//        }
//    }

    private void calcularIdade(Paciente paciente) {
        if (paciente.getPessoa().getDataNascimento() != null) {
            LocalDate dataNascimento = paciente.getPessoa().getDataNascimento();
            LocalDate hoje = LocalDate.now();
            Period periodo = Period.between(dataNascimento, hoje);

            int anos = periodo.getYears();
            int meses = periodo.getMonths();
            int dias = periodo.getDays();

            StringBuilder idade = new StringBuilder();

            if (anos < 1) {
                if (meses < 1) {
                    idade.append(dias).append(dias == 1 ? " dia" : " dias");
                } else {
                    idade.append(meses).append(meses == 1 ? " mês" : " meses");
                    if (dias > 0) {
                        idade.append(" e ").append(dias).append(dias == 1 ? " dia" : " dias");
                    }
                }
            } else {
                idade.append(anos).append(anos == 1 ? " ano" : " anos");
                if (meses > 0) {
                    idade.append(" ").append(meses).append(meses == 1 ? " mês" : " meses");
                }if (dias > 0){
                    idade.append(" e ").append(dias).append(dias == 1 ? " dia" : " dias");
                }

            }

            paciente.getPessoa().setIdade(idade.toString());
        }
    }
}

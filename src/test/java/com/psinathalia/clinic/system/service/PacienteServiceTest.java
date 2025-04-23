package com.psinathalia.clinic.system.service;

import com.psinathalia.clinic.system.exception.PacienteNotFoundException;
import com.psinathalia.clinic.system.model.Paciente;
import com.psinathalia.clinic.system.model.Pessoa;
import com.psinathalia.clinic.system.repository.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    private static final String PATIENT_NOT_FOUND_MESSAGE = "Paciente com ID %d não encontrado.";

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private CryptoService cryptoService;

    @InjectMocks
    private PacienteService pacienteService;

    @Test
    void deveRetornarTrueQuandoCpfForValido() {

        String cpfDigitado = "99999999901";
        Paciente paciente = new Paciente();
        Pessoa pessoa = new Pessoa();
        paciente.setId(1L);
        paciente.setPessoa(pessoa);
        String cpf = "99999999901";
        paciente.getPessoa().setCpf(cryptoService.encrypt(cpf));

        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        when(cryptoService.matches(cpfDigitado, paciente.getPessoa().getCpf())).thenReturn(true);

        boolean result = pacienteService.verificarCpf(cpfDigitado, paciente.getId());

        assertTrue(result);
        verify(pacienteRepository).findById(paciente.getId());
        verify(cryptoService).matches(cpfDigitado,paciente.getPessoa().getCpf());
    }

    @Test
    void deveRetornarFalseQaundoCpfForInvalido(){

        String cpfDigitado = "99999999902";
        Paciente paciente = new Paciente();
        Pessoa pessoa = new Pessoa();
        paciente.setId(1L);
        paciente.setPessoa(pessoa);
        String cpf = "99999999901";
        paciente.getPessoa().setCpf(cryptoService.encrypt(cpf));

        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        when(cryptoService.matches(cpfDigitado, paciente.getPessoa().getCpf())).thenReturn(false);

        boolean result = pacienteService.verificarCpf(cpfDigitado, paciente.getId());

        assertFalse(result);
        verify(pacienteRepository).findById(paciente.getId());
        verify(cryptoService).matches(cpfDigitado,paciente.getPessoa().getCpf());
    }

    @Test
    void deveLancarExececaoQuandoPacienteNaoForEncontrado() {
        // Arrange
        String cpfDigitado = "12345678900";
        Long pacienteId = 1L;

        when(pacienteService.findById(pacienteId))
                .thenReturn(Optional.empty());

        // Act & Assert
        PacienteNotFoundException exception = assertThrows(
                PacienteNotFoundException.class,
                () -> pacienteService.verificarCpf(cpfDigitado, pacienteId)
        );

        assertEquals(String.format(PATIENT_NOT_FOUND_MESSAGE, pacienteId), exception.getMessage());
        verify(pacienteRepository).findById(pacienteId);
        verify(cryptoService, never()).matches(anyString(), anyString());
    }

    @Test
    void deveRetornarCpfDescriptografadoComSucesso() {
        // Arrange
        Long pacienteId = 1L;
        String cpfOriginal = "99999999901";
        String cpfCriptografado = "CPF_CRIPTOGRAFADO";  // pode ser qualquer valor para teste

        Pessoa pessoa = new Pessoa();
        pessoa.setCpf(cpfCriptografado);

        Paciente paciente = new Paciente();
        paciente.setId(pacienteId);
        paciente.setPessoa(pessoa);

        when(pacienteRepository.findById(pacienteId)).thenReturn(Optional.of(paciente));
        when(cryptoService.decrypt(cpfCriptografado)).thenReturn(cpfOriginal);

        // Act
        String cpfDescriptografado = pacienteService.getCpfDescriptografado(pacienteId);

        // Assert
        assertEquals(cpfOriginal, cpfDescriptografado);
        verify(pacienteRepository).findById(pacienteId);
        verify(cryptoService).decrypt(cpfCriptografado);
    }

    @Test
    void deveLancarExcecaoQuandoPacienteNaoExistir() {
        // Arrange
        Long pacienteId = 1L;

        when(pacienteRepository.findById(pacienteId)).thenReturn(Optional.empty()).toString();

        // Act & Assert
        PacienteNotFoundException exception = assertThrows(
                PacienteNotFoundException.class,
                () -> pacienteService.getCpfDescriptografado(pacienteId)
        );

        String expectedMessage = String.format(PATIENT_NOT_FOUND_MESSAGE, pacienteId);
        assertEquals(expectedMessage, exception.getMessage());
        verify(pacienteRepository).findById(pacienteId);
        verify(cryptoService, never()).decrypt(any());
    }

    @Test
    void deveLancarExcecaoQuandoDecryptFalhar() {
        // Arrange
        Long pacienteId = 1L;
        String cpfCriptografado = "CPF_CRIPTOGRAFADO";

        Pessoa pessoa = new Pessoa();
        pessoa.setCpf(cpfCriptografado);

        Paciente paciente = new Paciente();
        paciente.setPessoa(pessoa);

        when(pacienteRepository.findById(pacienteId)).thenReturn(Optional.of(paciente));
        when(cryptoService.decrypt(cpfCriptografado)).thenThrow(new RuntimeException("Erro ao descriptografar"));

        // Act & Assert
        assertThrows(
                RuntimeException.class,
                () -> pacienteService.getCpfDescriptografado(pacienteId)
        );

        verify(pacienteRepository).findById(pacienteId);
        verify(cryptoService).decrypt(cpfCriptografado);
    }
}
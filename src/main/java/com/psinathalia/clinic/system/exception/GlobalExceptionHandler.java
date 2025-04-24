package com.psinathalia.clinic.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CpfInvalidoException.class)
    public ResponseEntity<ErrorResponse> handleCpfInvalido(CpfInvalidoException e) {
        ErrorResponse error = new ErrorResponse(e.getMessage(), "CPF Inválido");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(EnderecoInvalidoException.class)
    public ResponseEntity<ErrorResponse> handleEnderecoInvalido(EnderecoInvalidoException e) {
        ErrorResponse error = new ErrorResponse(e.getMessage(), "Endereço Inválido");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception e) {
        ErrorResponse error = new ErrorResponse(
                "Erro interno do servidor",
                e.getClass().getSimpleName()
        );
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(CpfJaCadastradoException.class)
    public ResponseEntity<ErrorResponse> handleCpfJaCadastrado(CpfJaCadastradoException e) {
        ErrorResponse error = new ErrorResponse(e.getMessage(), "CPF já cadastrado");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error); // 409 Conflict
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Pega todos os erros de validação
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        // Cria uma mensagem concatenada com todos os erros
        String errorMessage = String.join("; ", errors);

        ErrorResponse error = new ErrorResponse(
                errorMessage,
                "Erro de validação"
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = ex.getMessage();

        // Verificar se é um erro de enum
        if (message != null && message.contains("Cannot deserialize value of type") && message.contains("from String")) {
            // Extrair o nome do enum e os valores válidos da mensagem de erro
            String enumClass = message.substring(message.indexOf("`") + 1, message.lastIndexOf("`"));

            // Melhorar a extração do valor inválido
            String invalidValue = message.substring(message.indexOf("from String ") + 12);
            invalidValue = invalidValue.substring(0, invalidValue.indexOf("\": not one"));
            // Remover aspas extras do valor
            invalidValue = invalidValue.replace("\"", "");

            String validValues = message.substring(message.indexOf("[") + 1, message.indexOf("]"));

            String errorMessage = String.format(
                    "O valor '%s' é inválido para o campo do tipo %s. Valores aceitos são: %s",
                    invalidValue,
                    enumClass.substring(enumClass.lastIndexOf(".") + 1),
                    validValues
            );

            ErrorResponse error = new ErrorResponse(
                    errorMessage,
                    "Valor inválido para enumeração"
            );

            return ResponseEntity.badRequest().body(error);
        }

        // Para outros tipos de erros de parse JSON
        ErrorResponse error = new ErrorResponse(
                "Erro na formatação do JSON ou valor inválido",
                "Erro de formatação"
        );

        return ResponseEntity.badRequest().body(error);
    }
}
package br.edu.imepac.administrativo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ClinicaMedicaHandleExceptions {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        System.err.println("An error occurred: " + e.getMessage());
        return ResponseEntity.status(500).body("Erro inesperado, tente novamente!");
    }
}

package br.edu.imepac.administrativo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.dao.DataIntegrityViolationException;

@Slf4j // **@Slf4j**: Automatically generates a logger for the class, allowing logging of messages.
@ControllerAdvice // **@ControllerAdvice**: Indicates that this class provides centralized exception handling for controllers.
public class ClinicaMedicaHandleExceptions {

    // **@ExceptionHandler**: Specifies the type of exception this method handles.
    // This method handles generic exceptions and returns a 500 Internal Server Error.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado, tente novamente!"); // Returns a response with status 500.
    }

    // **@ExceptionHandler**: Handles `AuthenticationClinicaMedicaException` and returns a 401 Unauthorized status.
    @ExceptionHandler(AuthenticationClinicaMedicaException.class)
    public ResponseEntity<String> handleUnauthorized(Exception e) {
        log.error("An error occurred: " + e.getMessage()); // Logs the error message.
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Dados de acesso inválido!"); // Returns a response with status 401.
    }
    /**
     * Captura exceções de violação de integridade de dados do banco de dados,
     * como tentativas de inserir um valor duplicado em uma coluna com restrição UNIQUE.
     * Retorna um status HTTP 409 (Conflict).
     *
     * @param ex A exceção DataIntegrityViolationException capturada.
     * @return Um ResponseEntity com uma mensagem de erro clara e o status 409.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorMessage = "Erro de integridade dos dados. O recurso que você está tentando criar já existe ou viola uma restrição do banco de dados.";

        // Podemos tentar extrair uma mensagem mais específica do erro
        if (ex.getCause() != null && ex.getCause().getMessage() != null) {
            errorMessage = ex.getCause().getMessage();
        }

        // Retorna uma mensagem amigável com o status de conflito
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Conflito de dados: " + errorMessage);
    }

}
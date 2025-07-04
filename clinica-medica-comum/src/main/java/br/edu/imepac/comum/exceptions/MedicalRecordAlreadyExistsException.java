package br.edu.imepac.comum.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.CONFLICT)
public class MedicalRecordAlreadyExistsException extends RuntimeException {
    public MedicalRecordAlreadyExistsException(String message) { super(message); }
}
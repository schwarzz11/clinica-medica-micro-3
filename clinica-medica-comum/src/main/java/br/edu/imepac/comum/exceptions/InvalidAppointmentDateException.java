package br.edu.imepac.comum.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAppointmentDateException extends RuntimeException {
    public InvalidAppointmentDateException(String message) { super(message); }
}
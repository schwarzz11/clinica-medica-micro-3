package br.edu.imepac.comum.dtos.prontuario;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO para requisições de criação ou atualização de prontuários.
 * Contém os dados necessários para criar ou modificar um prontuário.
 */
@Data // Gera getters, setters, toString, equals e hashCode
public class ProntuarioRequest {

    @NotNull(message = "A data de criação não pode ser nula.")
    @PastOrPresent(message = "A data de criação não pode ser futura.")
    private LocalDateTime dataCriacao;

    private String historicoMedico;
    private String medicacaoAtual;
    private String alergias;

    @NotNull(message = "O ID do paciente não pode ser nulo.")
    private Long pacienteId;
}
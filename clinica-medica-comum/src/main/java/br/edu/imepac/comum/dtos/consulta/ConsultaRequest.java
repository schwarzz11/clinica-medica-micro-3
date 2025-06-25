package br.edu.imepac.comum.dtos.consulta;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ConsultaRequest {
    private Long pacienteId;
    private Long medicoId;
    private LocalDateTime dataHorario;
    private String sintomas;
    private boolean eRetorno;
}
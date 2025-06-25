package br.edu.imepac.comum.dtos.consulta;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ConsultaDto {
    private Long id;
    private String nomePaciente;
    private String nomeMedico;
    private LocalDateTime dataHorario;
    private boolean eRetorno;
    private boolean estaAtiva;
}

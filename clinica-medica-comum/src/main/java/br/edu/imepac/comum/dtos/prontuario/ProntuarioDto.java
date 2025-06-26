package br.edu.imepac.comum.dtos.prontuario;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProntuarioDto {
    private Long id;
    private Long consultaId;
    private LocalDateTime dataConsulta;
    private String nomePaciente;
    private String nomeMedico;
    private String receituario;
    private String exames;
    private String observacoes;
}

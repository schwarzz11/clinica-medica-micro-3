package br.edu.imepac.comum.dtos.prontuario;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO para representação de prontuários em respostas da API.
 * Contém os dados do prontuário, incluindo o nome do paciente associado.
 */
@Data // Gera getters, setters, toString, equals e hashCode
public class ProntuarioDto {
    private Long id;
    private LocalDateTime dataCriacao;
    private String historicoMedico;
    private String medicacaoAtual;
    private String alergias;
    private Long pacienteId;
    private String pacienteNome; // Adicionado para exibir o nome do paciente
}

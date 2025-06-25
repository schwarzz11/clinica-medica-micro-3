package br.edu.imepac.comum.dtos.paciente;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PacienteDto {
    private Long id;
    private String nome;
    private String cpf;
    private char sexo;
    private LocalDate dataNascimento;
    private String telefone;
    private String celular;
    private String email;
    private boolean possuiConvenio;
    private String nomeConvenio; // Nome do convênio para exibição
    private String numeroCarteirinha;
    private LocalDate validadeCarteirinha;
}

package br.edu.imepac.comum.dtos.prontuario;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProntuarioRequest {

    @NotNull(message = "O ID da consulta não pode ser nulo.")
    private Long consultaId;

    private String receituario;
    private String exames;
    private String observacoes;
}

package br.edu.imepac.comum.dtos.convenio;

import lombok.Data;

@Data
public class ConvenioDto {
    private Long id;
    private String nomeEmpresa;
    private String cnpj;
    private String nomeContato;
    private String telefone;
}
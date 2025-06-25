package br.edu.imepac.comum.dtos.convenio;

import lombok.Data;

@Data
public class ConvenioRequest {
    private String nomeEmpresa;
    private String cnpj;
    private String nomeContato;
    private String telefone;
}

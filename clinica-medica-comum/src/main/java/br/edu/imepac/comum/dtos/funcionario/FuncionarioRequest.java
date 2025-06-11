package br.edu.imepac.comum.dtos.funcionario;

import br.edu.imepac.comum.domain.EnumTipoFuncionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioRequest {
    private String usuario;
    private String senha;
    private String nome;
    private Integer idade;
    private char sexo;
    private String cpf;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String contato;
    private String email;
    private LocalDate dataNascimento;
    private EnumTipoFuncionario tipoFuncionario;
}
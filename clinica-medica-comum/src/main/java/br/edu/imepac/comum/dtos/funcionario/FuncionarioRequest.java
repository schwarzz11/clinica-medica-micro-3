package br.edu.imepac.comum.dtos.funcionario;

import br.edu.imepac.comum.domain.EnumTipoFuncionario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class FuncionarioRequest {

    // Adicione todos os campos que vêm na requisição para criar um funcionário

    @NotBlank(message = "O nome do funcionário não pode estar em branco.")
    private String nome;

    @NotBlank(message = "O CPF não pode estar em branco.")
    private String cpf;

    @NotBlank(message = "O e-mail não pode estar em branco.")
    private String email;

    @NotBlank(message = "O usuário não pode estar em branco.")
    private String usuario;

    @NotBlank(message = "A senha não pode estar em branco.")
    private String senha;

    private char sexo;
    private LocalDate dataNascimento;

    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;

    private EnumTipoFuncionario tipoFuncionario;

    // *** CORREÇÃO APLICADA AQUI ***
    // Adicionando o campo que estava em falta para associar o perfil.
    @NotNull(message = "O ID do perfil é obrigatório.")
    private Long perfilId;
}

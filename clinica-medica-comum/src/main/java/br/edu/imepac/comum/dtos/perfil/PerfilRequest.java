package br.edu.imepac.comum.dtos.perfil;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO para requisições de criação ou atualização de perfil.
 * Contém os dados necessários para criar ou modificar um perfil.
 */
@Data // Gera getters, setters, toString, equals e hashCode
public class PerfilRequest {

    @NotBlank(message = "O nome do perfil não pode estar em branco.") // Garante que o campo não é nulo e não é apenas espaços em branco
    @Size(min = 3, max = 100, message = "O nome do perfil deve ter entre 3 e 100 caracteres.") // Define o tamanho mínimo e máximo da string
    private String nome;
}

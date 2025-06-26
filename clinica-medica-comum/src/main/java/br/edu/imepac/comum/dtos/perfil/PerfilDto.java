package br.edu.imepac.comum.dtos.perfil;

import lombok.Data;

/**
 * DTO para representação de perfis em respostas da API.
 * Contém os dados do perfil a serem exibidos.
 */
@Data // Gera getters, setters, toString, equals e hashCode
public class PerfilDto {
    private Long id;
    private String nome;
}
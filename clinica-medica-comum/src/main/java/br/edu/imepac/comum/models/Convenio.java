package br.edu.imepac.comum.models;

import jakarta.persistence.*;
import lombok.Data;

@Data // Anotação do Lombok para gerar Getters, Setters, toString, etc.
@Entity // Anotação do JPA para indicar que esta classe é uma entidade
@Table(name = "convenios") // Especifica o nome da tabela no banco
public class Convenio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_empresa", nullable = false, length = 100)
    private String nomeEmpresa;

    @Column(nullable = false, length = 14)
    private String cnpj;

    @Column(name = "nome_contato", length = 100)
    private String nomeContato;

    @Column(length = 20)
    private String telefone;
}
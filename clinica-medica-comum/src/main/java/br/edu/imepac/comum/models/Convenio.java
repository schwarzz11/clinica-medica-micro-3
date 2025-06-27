package br.edu.imepac.comum.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "convenios")
@SQLDelete(sql = "UPDATE convenios SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
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

    private boolean ativo = true;
}
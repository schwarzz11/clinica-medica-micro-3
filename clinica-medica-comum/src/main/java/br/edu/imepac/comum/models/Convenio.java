package br.edu.imepac.comum.models;

import jakarta.persistence.*;
import lombok.Getter; // Importe Getter
import lombok.Setter; // Importe Setter

// *** CORREÇÃO APLICADA AQUI ***
// Substituímos @Data por @Getter e @Setter.
@Getter
@Setter
@Entity
@Table(name = "convenios")
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
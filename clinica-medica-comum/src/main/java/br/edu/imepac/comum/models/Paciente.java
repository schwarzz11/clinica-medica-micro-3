// =======================================================================
package br.edu.imepac.comum.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "pacientes")
@SQLDelete(sql = "UPDATE pacientes SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    private char sexo;
    private String rg;
    private String orgaoEmissor;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String telefone;
    private String celular;

    @Column(unique = true)
    private String email;

    private boolean possuiConvenio;
    private String numeroCarteirinha;
    private LocalDate validadeCarteirinha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "convenio_id")
    private Convenio convenio;

    private boolean ativo = true;
}
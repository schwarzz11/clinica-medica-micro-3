package br.edu.imepac.comum.models;

import jakarta.persistence.*;
import lombok.Getter;  // Importe Getter
import lombok.Setter;  // Importe Setter
import java.time.LocalDate;

// *** CORREÇÃO APLICADA AQUI ***
// Substituímos @Data por @Getter e @Setter para evitar loops infinitos
// em métodos como toString(), equals() e hashCode().
@Getter
@Setter
@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    private char sexo;

    @Column(length = 20)
    private String rg;

    private String orgaoEmissor;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(length = 255)
    private String rua;

    @Column(length = 10)
    private String numero;

    @Column(length = 100)
    private String complemento;

    @Column(length = 100)
    private String bairro;

    @Column(length = 100)
    private String cidade;

    @Column(length = 2)
    private String estado;

    @Column(length = 15)
    private String telefone;

    @Column(length = 15)
    private String celular;

    @Column(length = 100, unique = true)
    private String email;

    private boolean possuiConvenio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "convenio_id")
    private Convenio convenio;

    private String numeroCarteirinha;

    private LocalDate validadeCarteirinha;
}
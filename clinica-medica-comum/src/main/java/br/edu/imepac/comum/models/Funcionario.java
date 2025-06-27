package br.edu.imepac.comum.models;

import br.edu.imepac.comum.domain.EnumTipoFuncionario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "funcionarios")
@SQLDelete(sql = "UPDATE funcionarios SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 50)
    private String usuario;

    @Column(nullable = false)
    private String senha;

    private char sexo;
    private LocalDate dataNascimento;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;

    @Enumerated(EnumType.STRING)
    private EnumTipoFuncionario tipoFuncionario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    private boolean ativo = true;
}
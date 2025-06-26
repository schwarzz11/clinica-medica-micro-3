package br.edu.imepac.comum.models;

import br.edu.imepac.comum.domain.EnumTipoFuncionario;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "funcionarios")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 50, unique = true)
    private String usuario;

    @Column(nullable = false, length = 255)
    private String senha; // Em produção, isto deve ser encriptado com BCrypt

    private char sexo;
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

    @Enumerated(EnumType.STRING)
    private EnumTipoFuncionario tipoFuncionario;

    // CORREÇÃO: Adicionando a relação com Perfil
    // Um funcionário tem um perfil, e um perfil pode pertencer a muitos funcionários.
    @ManyToOne(fetch = FetchType.EAGER) // Usamos EAGER para que o perfil seja carregado junto com o funcionário
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;
}

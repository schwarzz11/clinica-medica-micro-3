package br.edu.imepac.comum.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "perfis")
@SQLDelete(sql = "UPDATE perfis SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    // Coluna para deleção lógica
    private boolean ativo = true;

    // Permissões
    private boolean cadastrarFuncionario;
    private boolean lerFuncionario;
    private boolean atualizarFuncionario;
    private boolean deletarFuncionario;
    private boolean listarFuncionario;
    private boolean cadastrarPaciente;
    private boolean lerPaciente;
    private boolean atualizarPaciente;
    private boolean deletarPaciente;
    private boolean listarPaciente;
    private boolean cadastrarConsulta;
    private boolean lerConsulta;
    private boolean atualizarConsulta;
    private boolean deletarConsulta;
    private boolean listarConsulta;
    private boolean cadastrarEspecialidade;
    private boolean lerEspecialidade;
    private boolean atualizarEspecialidade;
    private boolean deletarEspecialidade;
    private boolean listarEspecialidade;
    private boolean cadastrarConvenio;
    private boolean lerConvenio;
    private boolean atualizarConvenio;
    private boolean deletarConvenio;
    private boolean listarConvenio;
    private boolean cadastrarProntuario;
    private boolean lerProntuario;
    private boolean atualizarProntuario;
    private boolean deletarProntuario;
    private boolean listarProntuario;
}
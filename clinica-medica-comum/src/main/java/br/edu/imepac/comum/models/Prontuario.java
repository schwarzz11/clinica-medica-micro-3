package br.edu.imepac.comum.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "prontuarios")
@SQLDelete(sql = "UPDATE prontuarios SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id", nullable = false, unique = true)
    private Consulta consulta;

    @Column(columnDefinition = "TEXT")
    private String receituario;

    @Column(columnDefinition = "TEXT")
    private String exames;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    private boolean ativo = true;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }
}
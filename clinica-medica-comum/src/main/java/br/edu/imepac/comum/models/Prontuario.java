package br.edu.imepac.comum.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Representa o registro de um atendimento médico específico.
 * Está diretamente ligado a uma consulta.
 */
@Entity
@Table(name = "prontuarios")
@Data
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento um-para-um com Consulta.
    // Cada prontuário é o registro de uma única consulta.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id", nullable = false, unique = true)
    private Consulta consulta;

    @Column(columnDefinition = "TEXT")
    private String receituario;

    @Column(columnDefinition = "TEXT")
    private String exames;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }
}

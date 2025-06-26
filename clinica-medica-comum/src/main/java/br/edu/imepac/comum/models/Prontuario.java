package br.edu.imepac.comum.models;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * Representa o prontuário médico de um paciente.
 * Contém informações como histórico médico, medicação atual e alergias.
 */
@Entity
@Table(name = "prontuarios")
@Data // Gera getters, setters, toString, equals e hashCode
@NoArgsConstructor // Gera construtor sem argumentos
@AllArgsConstructor // Gera construtor com todos os argumentos
public class Prontuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "historico_medico", columnDefinition = "TEXT")
    private String historicoMedico;

    @Column(name = "medicacao_atual", columnDefinition = "TEXT")
    private String medicacaoAtual;

    @Column(name = "alergias", columnDefinition = "TEXT")
    private String alergias;

    // Relacionamento ManyToOne com Paciente
    // Um prontuário pertence a um único paciente.
    @ManyToOne(fetch = FetchType.LAZY) // Carregamento preguiçoso para evitar busca desnecessária
    @JoinColumn(name = "paciente_id", nullable = false) // Coluna da chave estrangeira na tabela prontuarios
    private Paciente paciente;
}

package br.edu.imepac.comum.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento com Paciente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    // Relacionamento com Funcionario (o Médico)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private Funcionario medico;

    @Column(nullable = false)
    private LocalDateTime dataHorario;

    @Column(length = 500)
    private String sintomas;

    private boolean eRetorno;

    // Para controlar se a consulta foi cancelada
    private boolean estaAtiva = true;
}

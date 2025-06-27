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
@Table(name = "consultas")
// A deleção lógica da consulta está em seu próprio serviço para controle de status
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private Funcionario medico;

    @Column(nullable = false)
    private LocalDateTime dataHorario;

    @Column(length = 500)
    private String sintomas;

    private boolean eRetorno;

    private boolean estaAtiva = true;
}
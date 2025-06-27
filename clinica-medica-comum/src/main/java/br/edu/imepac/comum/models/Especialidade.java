package br.edu.imepac.comum.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name = "especialidades")
@SQLDelete(sql = "UPDATE especialidades SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
public class Especialidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    private boolean ativo = true;
}
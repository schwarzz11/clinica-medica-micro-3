package br.edu.imepac.comum.repositories;

import br.edu.imepac.comum.models.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações de CRUD em Prontuario.
 * Extende JpaRepository para fornecer métodos prontos para persistência.
 */
@Repository
public interface ProntuarioRepository extends JpaRepository<Prontuario, Long>{
}

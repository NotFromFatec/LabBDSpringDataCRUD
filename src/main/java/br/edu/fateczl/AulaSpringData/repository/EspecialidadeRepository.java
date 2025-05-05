package br.edu.fateczl.AulaSpringData.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.fateczl.AulaSpringData.model.Especialidade;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
    // Métodos CRUD básicos e de paginação/ordenação já incluídos
}
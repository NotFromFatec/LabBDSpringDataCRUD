package br.edu.fateczl.AulaSpringData.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.edu.fateczl.AulaSpringData.model.Medico;
import br.edu.fateczl.AulaSpringData.model.Especialidade; // Import necessário para o tipo no JOIN
import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    // c. Query JPQL que retorne a lista de médicos por tipo de especialidade
    //    O tipo de especialidade está no campo 'nome' da entidade Especialidade
    @Query("SELECT m FROM Medico m JOIN m.especialidade e WHERE e.nome = :nomeEspecialidade")
    List<Medico> findByEspecialidadeNome(@Param("nomeEspecialidade") String nomeEspecialidade);

    // Alternativa usando derivação de nome de método (mais simples se a estrutura permitir):
    // List<Medico> findByEspecialidadeNome(String nomeEspecialidade);
}
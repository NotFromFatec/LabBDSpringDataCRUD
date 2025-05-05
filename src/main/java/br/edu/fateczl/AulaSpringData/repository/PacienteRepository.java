package br.edu.fateczl.AulaSpringData.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.fateczl.AulaSpringData.model.Paciente;
import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    // a. Método findBy para consultar pacientes por nome, com ordenação ascendente
    //    Usando ContainingIgnoreCase para busca flexível (case-insensitive)
    List<Paciente> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

    // b. Método findBy para consultar o primeiro paciente de um dado telefone
    //    Spring Data JPA entende "findFirst" ou "findTop"
    Optional<Paciente> findFirstByTelefone(String telefone);

}
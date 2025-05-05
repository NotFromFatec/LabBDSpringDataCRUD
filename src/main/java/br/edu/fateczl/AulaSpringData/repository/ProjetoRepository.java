package br.edu.fateczl.AulaSpringData.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fateczl.AulaSpringData.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {

}

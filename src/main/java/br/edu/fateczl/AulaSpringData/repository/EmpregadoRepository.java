package br.edu.fateczl.AulaSpringData.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fateczl.AulaSpringData.model.Empregado;

public interface EmpregadoRepository extends JpaRepository<Empregado, Integer> {

}

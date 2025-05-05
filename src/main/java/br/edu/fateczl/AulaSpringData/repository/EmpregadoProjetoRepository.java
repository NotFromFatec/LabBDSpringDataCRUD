package br.edu.fateczl.AulaSpringData.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fateczl.AulaSpringData.model.EmpregadoProjeto;
import br.edu.fateczl.AulaSpringData.model.EmpregadoProjetoId;

public interface EmpregadoProjetoRepository 
				extends JpaRepository<EmpregadoProjeto, EmpregadoProjetoId>{

}

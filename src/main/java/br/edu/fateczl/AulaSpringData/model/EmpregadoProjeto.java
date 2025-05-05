package br.edu.fateczl.AulaSpringData.model;

import java.time.LocalDate;

import org.hibernate.annotations.Formula;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empregado_projeto")
@IdClass(EmpregadoProjetoId.class)
public class EmpregadoProjeto {

	@Id
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Empregado.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "empregado_matricula", nullable = false)
	private Empregado empregado;
	@Id
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Projeto.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "projeto_numero", nullable = false)
	private Projeto projeto;
	@Column(name = "data_alocacao", nullable = false)
	private LocalDate dataAlocacao;
	@Formula("DATEDIFF(DAY, data_alocacao, GETDATE())")
	private int tempoEmpregadoProjeto;
	
}

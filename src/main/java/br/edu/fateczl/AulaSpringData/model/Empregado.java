package br.edu.fateczl.AulaSpringData.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.Formula;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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
@Table(name = "empregado")
@Inheritance(strategy = InheritanceType.JOINED)
public class Empregado {
	
	@Id
	@Column(name = "matricula", nullable = false)
	private int matricula;
	@Column(name = "nome_empregado", length = 50, nullable = false)
	private String nome;
	@Column(name = "sexo", length = 1, nullable = false)
	private String sexo;
	@Column(name = "telefone", length = 10, nullable = true)	
	private String telefone;
	@Column(name = "data_admissao", nullable = false)
	private LocalDate dataAdmissao;
	@Formula("CONVERT(CHAR(10), data_admissao, 103)")
	private String dtAdmissao;
	@Column(name = "cargo", length = 20, nullable = true)	
	private String cargo;
	@Column(name = "salario", precision = 7, scale = 2, nullable = false)
	private BigDecimal salario;
	@ManyToOne(targetEntity = Depto.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "depto_codigo", nullable = false)
	private Depto depto;
	
}

package br.edu.fateczl.AulaSpringData.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "depto")
public class Depto {

	@Id
	@Column(name = "codigo", nullable = false)
	private int codigo;
	@Column(name = "nome_depto", length = 20, nullable = false)
	private String nome;
	@Column(name = "sigla", length = 3, nullable = true)
	private String sigla;
	
}

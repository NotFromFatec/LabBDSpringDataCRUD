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
@Table(name = "projeto")
public class Projeto {

	@Id
	@Column(name = "numero", nullable = false)
	private int numero;
	@Column(name = "nome_projeto", length = 40,nullable = false)
	private String nome;
	@Column(name = "horas", nullable = false)
	private int horas;
	
}

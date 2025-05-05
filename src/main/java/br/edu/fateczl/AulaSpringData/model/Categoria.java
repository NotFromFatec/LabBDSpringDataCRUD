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
@Table(name = "categoria")
public class Categoria {

	@Id
	@Column(name = "id", nullable = false)
	private int id;
	@Column(name = "tipo", length = 15, nullable = false)
	private String tipo;
}

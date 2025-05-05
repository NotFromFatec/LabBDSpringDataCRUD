package br.edu.fateczl.AulaSpringData.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmpregadoProjetoId implements Serializable {

	private static final long serialVersionUID = 1L;
	private Empregado empregado;
	private Projeto projeto;

}

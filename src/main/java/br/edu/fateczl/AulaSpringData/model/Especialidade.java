package br.edu.fateczl.AulaSpringData.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "especialidade")
public class Especialidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usa auto-incremento do BD
    @Column(name = "id", nullable = false)
    private Long id; // Usando Long para IDs é uma boa prática

    @Column(name = "nome", length = 50, nullable = false, unique = true)
    private String nome;
}
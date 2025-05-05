package br.edu.fateczl.AulaSpringData.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded; // Importa a anotação Embedded
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
@Table(name = "paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Embedded // Indica que os campos de Endereco serão mapeados aqui
    private Endereco endereco;

    @Column(name = "telefone", length = 15, nullable = true) // Ajustado tamanho para (XX) XXXXX-XXXX
    private String telefone;

    @Column(name = "numero_beneficiario", length = 30, nullable = false, unique = true)
    private String numeroBeneficiario;
}
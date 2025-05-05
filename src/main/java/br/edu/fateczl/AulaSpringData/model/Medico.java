package br.edu.fateczl.AulaSpringData.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded; // Importa a anotação Embedded
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "medico")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo", nullable = false) // Mantendo 'codigo' como no PDF
    private Long codigo;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Embedded // Indica que os campos de Endereco serão mapeados aqui
    private Endereco endereco;

    @Column(name = "contato", length = 15, nullable = true) // Pode ser telefone/celular
    private String contato;

    @ManyToOne(targetEntity = Especialidade.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidade_id", nullable = false)
    private Especialidade especialidade;
}
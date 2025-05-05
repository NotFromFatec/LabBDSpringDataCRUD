package br.edu.fateczl.AulaSpringData.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable 
public class Endereco {

    @Column(name = "rua", length = 100, nullable = false)
    private String rua;

    @Column(name = "numero", length = 10, nullable = false)
    private String numero;

    @Column(name = "cep", length = 9, nullable = false) // Formato "XXXXX-XXX"
    private String cep;

    @Column(name = "complemento", length = 50, nullable = true)
    private String complemento;
}
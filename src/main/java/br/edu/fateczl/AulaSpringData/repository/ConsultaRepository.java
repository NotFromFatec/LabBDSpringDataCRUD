package br.edu.fateczl.AulaSpringData.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.edu.fateczl.AulaSpringData.model.Consulta;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    // d. Query JPQL que retorne a lista de consultas de um determinado dia
    //    É mais seguro buscar por um intervalo (início e fim do dia)
    //    O parâmetro :dataInicio deve ser o início do dia (e.g., 2024-07-28 00:00:00)
    //    O parâmetro :dataFim deve ser o início do dia seguinte (e.g., 2024-07-29 00:00:00)
    @Query("SELECT c FROM Consulta c WHERE c.dataHora >= :dataInicio AND c.dataHora < :dataFim")
    List<Consulta> findConsultasPorDia(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);

    // e. Query nativa que retorne a quantidade de consultas de um determinado dia
    //    Usando a mesma lógica de intervalo para robustez. A sintaxe da função de data pode variar com o BD.
    //    Esta query nativa usa placeholder ?1, ?2 para os parâmetros na ordem.
    @Query(value = "SELECT COUNT(*) FROM consulta WHERE data_hora >= ?1 AND data_hora < ?2", nativeQuery = true)
    long countConsultasPorDiaNativa(LocalDateTime dataInicio, LocalDateTime dataFim);

    // Alternativa JPQL para a contagem (geralmente preferível se não houver necessidade específica de SQL nativo)
    @Query("SELECT COUNT(c) FROM Consulta c WHERE c.dataHora >= :dataInicio AND c.dataHora < :dataFim")
    long countConsultasPorDiaJPQL(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);
}
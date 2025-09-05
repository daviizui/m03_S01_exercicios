package br.com.jmt.exercicoTrello.repositories;

import br.com.jmt.exercicoTrello.entities.AcaoSustentavel;
import br.com.jmt.exercicoTrello.enums.CategoriaAcao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AcaoSustentavelRepository extends JpaRepository<AcaoSustentavel, Long> {

    List<AcaoSustentavel> findByCategoria(CategoriaAcao categoria);


    List<AcaoSustentavel> findByDataRealizacao(LocalDate dataRealizacao);


    List<AcaoSustentavel> findByResponsavelContainingIgnoreCase(String responsavel);

    List<AcaoSustentavel> findByTituloContainingIgnoreCase(String titulo);


    boolean existsByTituloAndDescricaoAndCategoriaAndDataRealizacaoAndResponsavel(
            String titulo,
            String descricao,
            CategoriaAcao categoria,
            LocalDate dataRealizacao,
            String responsavel
    );
}

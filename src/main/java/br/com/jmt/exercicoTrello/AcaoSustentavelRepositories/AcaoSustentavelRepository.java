package br.com.jmt.exercicoTrello.AcaoSustentavelRepositories;

import br.com.jmt.exercicoTrello.entities.AcaoSustentavel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcaoSustentavelRepository extends JpaRepository<AcaoSustentavel, Long> {
}

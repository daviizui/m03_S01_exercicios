package br.com.jmt.exercicoTrello.dtos;

import br.com.jmt.exercicoTrello.enums.CategoriaAcao;
import lombok.Data;

import java.time.LocalDate;
@Data
public class AcaoSustentavelResponse {
    private Long id;
    private String titulo;
    private String descricao;
    private CategoriaAcao categoria;
    private LocalDate dataRealizacao;
    private String responsavel;
}

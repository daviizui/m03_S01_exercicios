package br.com.jmt.exercicoTrello.dtos;

import br.com.jmt.exercicoTrello.entities.AcaoSustentavel;
import br.com.jmt.exercicoTrello.enums.CategoriaAcao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcaoSustentavelRequest {
    @NotBlank(message = "O título é obrigatório")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @NotNull(message = "A categoria é obrigatória")
    private CategoriaAcao categoria;

    @NotNull(message = "A data de realização é obrigatória")
    @PastOrPresent(message = "A data de realização não pode ser futura")
    private LocalDate dataRealizacao;

    @NotBlank(message = "O responsável é obrigatório")
    @Size(max = 100, message = "O nome do responsável deve ter no máximo 100 caracteres")
    private String responsavel;

    public AcaoSustentavel toEntity() {
       AcaoSustentavel acao = new AcaoSustentavel();
         acao.setTitulo(this.titulo);
            acao.setDescricao(this.descricao);
            acao.setCategoria(this.categoria);
            acao.setDataRealizacao(this.dataRealizacao);
            acao.setResponsavel(this.responsavel);
            return acao;
    }
}

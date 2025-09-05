package br.com.jmt.exercicoTrello.services;


import br.com.jmt.exercicoTrello.entities.AcaoSustentavel;
import br.com.jmt.exercicoTrello.enums.CategoriaAcao;
import br.com.jmt.exercicoTrello.repositories.AcaoSustentavelRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AcaoSustentavelService {

    private final AcaoSustentavelRepository acaoRepository;
    private List<AcaoSustentavel> acoes;

    public AcaoSustentavelService(AcaoSustentavelRepository acaoSustentavelRepository) {
        this.acaoRepository = acaoSustentavelRepository;
    }

    @Transactional
    public AcaoSustentavel addAcao(AcaoSustentavel acaoSustentavel){
        boolean exists = acaoRepository.existsByTituloAndDescricaoAndCategoriaAndDataRealizacaoAndResponsavel(
                acaoSustentavel.getTitulo(),
                acaoSustentavel.getDescricao(),
                acaoSustentavel.getCategoria(),
                acaoSustentavel.getDataRealizacao(),
                acaoSustentavel.getResponsavel());
        if (exists)
            throw new RuntimeException("Ação já existe no banco de dados");
        return acaoRepository.save(acaoSustentavel);
    }
    @Transactional
    public String deleteAcoes(){
        acaoRepository.deleteAll();
        return "Todos as ações foram deletados";
    }

    @Transactional
    public String deleteAcoaById (Long id){
        if(acaoRepository.existsById(id)){
            acaoRepository.deleteById(id);
            return "Filme com Id " + id + " deletado com sucesso";
        }else {
            throw new RuntimeException("Ação com ID " + id + " não encontrado");
        }
    }
    public long countAcoes(){
        return acaoRepository.count();
    }

    @Transactional
    public String updateAcao(Long id, AcaoSustentavel updateAcao){
        return acaoRepository.findById(id).map(acao -> {
            acao.setCategoria(updateAcao.getCategoria());
            acao.setDataRealizacao(updateAcao.getDataRealizacao());
            acao.setDescricao(updateAcao.getDescricao());
            acao.setResponsavel(updateAcao.getResponsavel());
            acao.setTitulo(updateAcao.getTitulo());
          acaoRepository.save(acao);
            return "Ação com Id " + id + " atualizada com sucesso";
        }).orElseThrow(() -> new RuntimeException("Ação com ID " + id + " não encontrado"));
    }

    public List<AcaoSustentavel> getAcoes(){
        return acaoRepository.findAll();
    }


    public List<AcaoSustentavel> getAcaoByCategoria(CategoriaAcao categoriaAcao) {
        acoes = acaoRepository.findByCategoria(categoriaAcao);
        if (acoes.isEmpty()) {
            throw new RuntimeException("Nenhuma ação encontrada para a categoria: " + categoriaAcao);
        }
        return acoes;
    }
}

package br.com.jmt.exercicoTrello.controllers;


import br.com.jmt.exercicoTrello.dtos.AcaoSustentavelRequest;
import br.com.jmt.exercicoTrello.dtos.AcaoSustentavelResponse;
import br.com.jmt.exercicoTrello.entities.AcaoSustentavel;
import br.com.jmt.exercicoTrello.enums.CategoriaAcao;
import br.com.jmt.exercicoTrello.services.AcaoSustentavelService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/acoes")
public class AcaoSustentavelControler {


    private final AcaoSustentavelService acaoSustentavelService;

   public AcaoSustentavelControler(AcaoSustentavelService acaoSustentavelService) {
        this.acaoSustentavelService = acaoSustentavelService;
    }

    @PostMapping
    public ResponseEntity<AcaoSustentavelResponse> adicionarAcao(@Valid @RequestBody AcaoSustentavelRequest request) {
        log.info("Recebida solicitação para adicionar uma nova ação sustentável.");
        AcaoSustentavel acao = convertToEntity(request);
        AcaoSustentavel novaAcao = acaoSustentavelService.addAcao(acao);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponseDto(novaAcao));
    }

    @GetMapping
    public ResponseEntity<List<AcaoSustentavelResponse>> listarTodasAsAcoes() {
        log.info("Recebida solicitação para listar todas as ações sustentáveis.");
        List<AcaoSustentavel> acoes = acaoSustentavelService.getAcoes();
        List<AcaoSustentavelResponse> responses = acoes.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping(params = "categoria")
    public List<AcaoSustentavelResponse> buscarAcaoPorId(@PathVariable CategoriaAcao categoria) {
        return acaoSustentavelService.getAcaoByCategoria(categoria).stream().map(category ->{
            AcaoSustentavelResponse dto = new AcaoSustentavelResponse();
            dto.setId(category.getId());
            dto.setTitulo(category.getTitulo());
            dto.setDescricao(category.getDescricao());
            dto.setCategoria(category.getCategoria());
            dto.setDataRealizacao(category.getDataRealizacao());
            dto.setResponsavel(category.getResponsavel());
            return dto;
        }).toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateAcao(@PathVariable Long id, @Valid @RequestBody AcaoSustentavelRequest request){
       String mensagem = acaoSustentavelService.updateAcao(id, request.toEntity());
         return ResponseEntity.ok(Map.of("message", mensagem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletarAcaoPorId(@PathVariable Long id) {
        log.info("Recebida solicitação para deletar ação sustentável com ID: {}", id);
        String mensagem = acaoSustentavelService.deleteAcoaById(id);
        return ResponseEntity.ok(Map.of("message", mensagem));
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deletarTodasAsAcoes() {
        log.info("Recebida solicitação para deletar todas as ações sustentáveis.");
        String mensagem = acaoSustentavelService.deleteAcoes();
        return ResponseEntity.ok(Map.of("message", mensagem));
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> contarAcoes() {
        log.info("Recebida solicitação para contar as ações sustentáveis.");
        Long count = acaoSustentavelService.countAcoes();
        return ResponseEntity.ok(Map.of("count", count));
    }

    private AcaoSustentavel convertToEntity(AcaoSustentavelRequest request) {
        AcaoSustentavel acao = new AcaoSustentavel();
        acao.setTitulo(request.getTitulo());
        acao.setDescricao(request.getDescricao());
        acao.setCategoria(request.getCategoria());
        acao.setDataRealizacao(request.getDataRealizacao());
        acao.setResponsavel(request.getResponsavel());
        return acao;
    }

    private AcaoSustentavelResponse convertToResponseDto(AcaoSustentavel acao) {
        AcaoSustentavelResponse dto = new AcaoSustentavelResponse();
        dto.setId(acao.getId());
        dto.setTitulo(acao.getTitulo());
        dto.setDescricao(acao.getDescricao());
        dto.setCategoria(acao.getCategoria());
        dto.setDataRealizacao(acao.getDataRealizacao());
        dto.setResponsavel(acao.getResponsavel());
        return dto;
    }





}

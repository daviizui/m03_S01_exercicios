package br.com.jmt.exercicoTrello.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

public class GlobalExceptionHandler {
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleRecursoNaoEncontradoException(RecursoNaoEncontradoException ex) {
        Map<String, String> erro = new HashMap<>();
        erro.put("status", "404 Not Found");
        erro.put("mensagem", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> erros = new HashMap<>();
        erros.put("status", "400 Bad Request");
        erros.put("mensagem", "Erro de validação nos campos.");

        Map<String, String> detalhes = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String nomeCampo = ((FieldError) error).getField();
            String mensagemErro = error.getDefaultMessage();
            detalhes.put(nomeCampo, mensagemErro);
        });

        erros.put("detalhes", detalhes);

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> erro = new HashMap<>();
        erro.put("status", "500 Internal Server Error");
        erro.put("mensagem", "Ocorreu um erro interno no servidor.");
        // Em um ambiente de produção, não retorne a mensagem completa da exceção por segurança.
        //erro.put("detalhes", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
    @ExceptionHandler(ConflitoDeDadosException.class)
    public ResponseEntity<Map<String, String>> handleConflitoDeDadosException(ConflitoDeDadosException ex) {
        Map<String, String> erro = new HashMap<>();
        erro.put("status", "409 Conflict");
        erro.put("mensagem", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }
}

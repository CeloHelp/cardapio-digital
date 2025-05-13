package com.cddigital.cardapio_digital.exceptions;

import com.cddigital.cardapio_digital.exceptions.costumized.CategoriaNaoEncontradaException;
import com.cddigital.cardapio_digital.exceptions.costumized.ClienteNaoEncontradoException;
import com.cddigital.cardapio_digital.exceptions.costumized.PedidoNaoEncontradoException;
import com.cddigital.cardapio_digital.exceptions.costumized.ProdutoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    public ResponseEntity<ErroResponseDTO> handlePedidoNaoEncontrado(PedidoNaoEncontradoException ex) {
        ErroResponseDTO erro = new ErroResponseDTO("PEDIDO_NAO_ENCONTRADO", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<ErroResponseDTO> handleClienteNaoEncontrado(ClienteNaoEncontradoException ex) {
        ErroResponseDTO erro = new ErroResponseDTO("CLIENTE_NAO_ENCONTRADO", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDTO> handleException(Exception ex) {
        ErroResponseDTO erro = new ErroResponseDTO("ERRO_INTERNO", "Ocorreu um erro inesperado.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ErroResponseDTO> handleProdutoNaoEncontrado(ProdutoNaoEncontradoException ex) {
        ErroResponseDTO erro = new ErroResponseDTO("PRODUTO_NAO_ENCONTRADO", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(CategoriaNaoEncontradaException.class)
    public ResponseEntity<ErroResponseDTO> handleCategoriaNaoEncontrada(CategoriaNaoEncontradaException ex) {
        ErroResponseDTO erro = new ErroResponseDTO("CATEGORIA_NAO_ENCONTRADA", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

}

package com.cddigital.cardapio_digital.exceptions.costumized;

import java.util.UUID;

public class ProdutoNaoEncontradoException extends RuntimeException {
    public ProdutoNaoEncontradoException(UUID id) {

      super("Produto com ID " + id + " nao encontrado");
    }
}

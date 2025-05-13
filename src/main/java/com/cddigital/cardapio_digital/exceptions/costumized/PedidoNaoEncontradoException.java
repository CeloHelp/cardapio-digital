package com.cddigital.cardapio_digital.exceptions.costumized;

import java.util.UUID;

public class PedidoNaoEncontradoException extends RuntimeException {
    public PedidoNaoEncontradoException(UUID id) {
        super("Pedido com ID " + id + "Nao encontrado");
    }
}

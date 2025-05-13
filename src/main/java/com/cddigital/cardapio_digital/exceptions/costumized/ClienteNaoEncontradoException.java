package com.cddigital.cardapio_digital.exceptions.costumized;

import java.util.UUID;

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException(UUID id) {

        super("Cliente com ID " + id + " nao encontrado");
    }
}

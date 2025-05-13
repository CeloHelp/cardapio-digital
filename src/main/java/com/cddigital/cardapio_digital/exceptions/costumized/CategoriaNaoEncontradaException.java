package com.cddigital.cardapio_digital.exceptions.costumized;

import java.util.UUID;

public class CategoriaNaoEncontradaException extends RuntimeException {
    public CategoriaNaoEncontradaException(UUID id) {

        super("Categoria com ID " + id + " nao encontrada");
    }
}

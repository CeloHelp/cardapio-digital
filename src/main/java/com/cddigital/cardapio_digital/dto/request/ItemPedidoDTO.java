package com.cddigital.cardapio_digital.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ItemPedidoDTO(
        @NotNull(message = "O Id do produto é obrigatório")
        UUID produto,

        @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
        int quantidade
) {
}

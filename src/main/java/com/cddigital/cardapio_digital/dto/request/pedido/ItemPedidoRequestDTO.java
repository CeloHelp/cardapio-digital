package com.cddigital.cardapio_digital.dto.request.pedido;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ItemPedidoRequestDTO(
        @NotNull(message = "O Id do idProduto é obrigatório")
        UUID idProduto,

        @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
        int quantidade
) {
}

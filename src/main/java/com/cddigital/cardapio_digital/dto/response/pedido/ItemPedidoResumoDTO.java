package com.cddigital.cardapio_digital.dto.response.pedido;

import java.math.BigDecimal;

public record ItemPedidoResumoDTO(
        String nomeProduto,
        BigDecimal precoUnitario,
        int quantidade,
        BigDecimal subtotal
) {
}

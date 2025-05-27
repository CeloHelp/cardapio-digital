package com.cddigital.cardapio_digital.dto.response.pedido;

import com.cddigital.cardapio_digital.entity.PedidoItem;

import java.math.BigDecimal;

public record ItemPedidoResumoDTO(
        String nomeProduto,
        BigDecimal precoUnitario,
        int quantidade,
        BigDecimal subtotal
) {
    public static ItemPedidoResumoDTO fromEntity(PedidoItem item) {
        return new ItemPedidoResumoDTO(
                item.getProduto().getNome(),
                item.getProduto().getPreco(),
                item.getQuantidade(),
                item.getProduto().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()))
        );
    }
}

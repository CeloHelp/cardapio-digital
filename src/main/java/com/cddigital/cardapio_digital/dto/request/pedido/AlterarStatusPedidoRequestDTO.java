package com.cddigital.cardapio_digital.dto.request.pedido;

import com.cddigital.cardapio_digital.enums.StatusPedido;

import java.util.UUID;

public record AlterarStatusPedidoRequestDTO(
        UUID idPedido,
        StatusPedido novoStatus)
{
}

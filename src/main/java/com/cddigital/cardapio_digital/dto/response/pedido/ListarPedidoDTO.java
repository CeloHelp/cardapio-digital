package com.cddigital.cardapio_digital.dto.response.pedido;

import java.util.List;
import java.util.UUID;

public record ListarPedidoDTO(
        UUID id,
        List<ItemPedidoResumoDTO> items

) {
}

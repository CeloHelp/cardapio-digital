package com.cddigital.cardapio_digital.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PedidoResponseDTO(
        UUID id,
        String nomeCliente,
        String telefone,
        LocalDateTime dataHora,
        String status,
        BigDecimal total,
        List<ItemPedidoResumoDTO> itens

) {
}

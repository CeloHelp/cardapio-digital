package com.cddigital.cardapio_digital.dto.response.pedido;

import com.cddigital.cardapio_digital.entity.Cliente;
import com.cddigital.cardapio_digital.entity.Pedido;

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
    public static PedidoResponseDTO fromEntity(Pedido pedido) {
        Cliente cliente = pedido.getCliente();

        List<ItemPedidoResumoDTO> itensResumo = pedido.getItens()
                .stream()
                .map(item -> new ItemPedidoResumoDTO(
                        item.getProduto().getNome(),
                        item.getProduto().getPreco(),
                        item.getQuantidade(),
                        item.getProduto().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()))
                ))
                .toList();

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getCliente().getNome(),
                pedido.getCliente().getTelefone(),
                pedido.getDataHora(),
                pedido.getStatusPedido().name(),
                pedido.getTotal(),
                itensResumo
        );
    }
}
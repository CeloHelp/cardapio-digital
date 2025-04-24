package com.cddigital.cardapio_digital.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record PedidoRequestDTO(
        @NotBlank
        String nomeCliente,
        @NotBlank
        String telefone,
        @NotEmpty
        List <ItemPedidoDTO> itens
) {}



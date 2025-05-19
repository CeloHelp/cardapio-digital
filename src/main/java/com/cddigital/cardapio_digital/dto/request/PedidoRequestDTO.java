package com.cddigital.cardapio_digital.dto.request;

import com.cddigital.cardapio_digital.enums.StatusGlobal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record PedidoRequestDTO(
        @NotBlank
        UUID idCliente,
        @NotEmpty
        List <ItemPedidoRequestDTO> itens,
        @NotEmpty
        int quantidade
) {}



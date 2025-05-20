package com.cddigital.cardapio_digital.dto.request;

import com.cddigital.cardapio_digital.enums.StatusGlobal;

import java.util.UUID;

public record AlterarStatusClienteRequestDTO(
        UUID idCliente,
        StatusGlobal status
) {
}

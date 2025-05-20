package com.cddigital.cardapio_digital.dto.response;

import java.util.UUID;

public record ClienteResponseDTO(
        UUID id,
        String nome,
        String telefone,
        String email, com.cddigital.cardapio_digital.enums.StatusGlobal status) {
}

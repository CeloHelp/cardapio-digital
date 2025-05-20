package com.cddigital.cardapio_digital.dto.response.categoria;

import java.util.UUID;

public record CategoriaResponseDTO(
        UUID id,
        String nome
) {
}

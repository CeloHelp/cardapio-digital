package com.cddigital.cardapio_digital.dto.request.categoria;

import com.cddigital.cardapio_digital.enums.StatusGlobal;

import java.util.UUID;

public record AlterarStatusCategoriaRequestDTO(
        UUID idCategoria,
        StatusGlobal status
) {
}

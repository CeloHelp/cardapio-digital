package com.cddigital.cardapio_digital.dto.request.categoria;

import jakarta.validation.constraints.NotBlank;

public record EditarCategoriaRequestDTO(
        @NotBlank
        String nome
) {
}

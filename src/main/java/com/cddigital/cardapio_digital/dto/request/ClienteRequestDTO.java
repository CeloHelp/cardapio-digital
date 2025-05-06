package com.cddigital.cardapio_digital.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ClienteRequestDTO(
        @NotBlank String nome,
        @NotBlank String telefone,
        @NotBlank String email
        ) {
}

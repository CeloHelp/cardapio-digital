package com.cddigital.cardapio_digital.dto.request.produto;

import com.cddigital.cardapio_digital.enums.StatusGlobal;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AlterarStatusProdutoRequestDTO(
        @NotNull
        UUID idProduto,
        StatusGlobal status
) {
}

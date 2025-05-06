package com.cddigital.cardapio_digital.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoRequestDTO(
        //@NotBlank(message = "O nome do idProduto deve ser informado.")
        @Size(min = 3, message = "O nome do idProduto deve ter no mínimo 3 caracteres")
        String nome,
        @NotBlank(message = "O idProduto deve conter uma descrição")
        @Size(min = 10,message = "A descrição deve ter no mínimo 20 caracteres ")
        String descricao,
        BigDecimal preco,
        String imagemUrl,
        UUID categoria
) {
}

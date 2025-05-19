package com.cddigital.cardapio_digital.dto.response;

import com.cddigital.cardapio_digital.entity.Categoria;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoResponseDTO(
        UUID id,
        String nome,
        String descricao,
        BigDecimal preco,
        String imagemUrl,
        Categoria categoria,
        String status
) {

}

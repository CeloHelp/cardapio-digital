package com.cddigital.cardapio_digital.dto.response.produto;

import com.cddigital.cardapio_digital.entity.Categoria;
import com.cddigital.cardapio_digital.entity.Produto;

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
    public static  ProdutoResponseDTO fromEntity(Produto produto) {
         return new ProdutoResponseDTO(
                 produto.getId(),
                 produto.getNome(),
                 produto.getDescricao(),
                 produto.getPreco(),
                 produto.getImagemUrl(),
                 produto.getCategoria(),
                 produto.getStatus()
         );
    }

}

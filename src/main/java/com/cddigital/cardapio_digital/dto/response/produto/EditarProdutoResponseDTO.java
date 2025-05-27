package com.cddigital.cardapio_digital.dto.response.produto;

import com.cddigital.cardapio_digital.entity.Categoria;
import com.cddigital.cardapio_digital.entity.Produto;

import java.math.BigDecimal;
import java.util.UUID;

public record EditarProdutoResponseDTO(
        UUID id,
        String nome,
        String descricao,
        BigDecimal preco,
        String imagemUrl
) {
    public static EditarProdutoResponseDTO fromEntity(Produto produto) {
        return new EditarProdutoResponseDTO(

                        produto.getId(),
                        produto.getNome(),
                        produto.getDescricao(),
                        produto.getPreco(),
                        produto.getImagemUrl()
        );
    }
}

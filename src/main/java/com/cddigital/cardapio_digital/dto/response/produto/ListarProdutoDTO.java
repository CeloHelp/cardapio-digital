package com.cddigital.cardapio_digital.dto.response.produto;

import com.cddigital.cardapio_digital.entity.Produto;

import java.math.BigDecimal;
import java.util.UUID;

public record ListarProdutoDTO(
        UUID id,
        String nome,
        String descricao,
        BigDecimal preco,
        String imagemURL

)     /* converter no próprio DTO para manter o código mais limpo */ {
    public static ListarProdutoDTO fromEntity(Produto produto) {
        return new ListarProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getImagemUrl()
        );

    }
}

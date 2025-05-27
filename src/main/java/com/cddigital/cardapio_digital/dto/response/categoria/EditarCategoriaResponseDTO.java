package com.cddigital.cardapio_digital.dto.response.categoria;

import com.cddigital.cardapio_digital.entity.Categoria;

public record EditarCategoriaResponseDTO(
        String nome
) {
    public static  EditarCategoriaResponseDTO fromEntity(Categoria categoria) {
        return new EditarCategoriaResponseDTO(categoria.getNome());
    }

}

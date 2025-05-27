package com.cddigital.cardapio_digital.dto.response.categoria;

import com.cddigital.cardapio_digital.entity.Categoria;

import java.util.UUID;

public record CategoriaResponseDTO(
        UUID id,
        String nome
) {
    public static  CategoriaResponseDTO fromEntity(Categoria categoria) {
        return new CategoriaResponseDTO(categoria.getId(), categoria.getNome());

    }
}

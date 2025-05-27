package com.cddigital.cardapio_digital.dto.response.categoria;

import com.cddigital.cardapio_digital.dto.response.cliente.AlterarStatusClienteResponseDTO;
import com.cddigital.cardapio_digital.entity.Categoria;

import java.util.UUID;

public record ListarCategoriaDTO(
        UUID id,
        String nome
) {
    public static ListarCategoriaDTO fromEntity(Categoria categoria) {
        return new ListarCategoriaDTO(
                categoria.getId(),
                categoria.getNome()
        );
    }
}

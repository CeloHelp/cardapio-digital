package com.cddigital.cardapio_digital.dto.response.cliente;

import com.cddigital.cardapio_digital.entity.Cliente;

import java.util.UUID;

public record ListarClienteDTO(
        UUID id,
        String nome,
        String telefone
) {
    public static  ListarClienteDTO fromEntity(Cliente cliente) {
        return new ListarClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone()
        );
    }
}

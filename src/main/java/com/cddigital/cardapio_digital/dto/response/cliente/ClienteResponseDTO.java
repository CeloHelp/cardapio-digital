package com.cddigital.cardapio_digital.dto.response.cliente;

import com.cddigital.cardapio_digital.entity.Cliente;

import java.util.UUID;

public record ClienteResponseDTO(
        UUID id,
        String nome,
        String telefone,
        String email,
        com.cddigital.cardapio_digital.enums.StatusGlobal status,
        String endereco,
        int numero) {

    public static  ClienteResponseDTO fromEntity(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getStatus(),
                cliente.getEndereco(),
                cliente.getNumero()
        );
    }
}

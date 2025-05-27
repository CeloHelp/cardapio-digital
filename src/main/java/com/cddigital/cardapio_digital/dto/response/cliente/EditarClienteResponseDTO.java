package com.cddigital.cardapio_digital.dto.response.cliente;

import com.cddigital.cardapio_digital.entity.Cliente;
import jakarta.validation.constraints.NotBlank;

public record EditarClienteResponseDTO(
         String nome,
         String telefone,
         String email,
        String endereco,
        int numero
) {
    public static EditarClienteResponseDTO fromEntity(Cliente cliente) {
        return new EditarClienteResponseDTO(
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getEndereco(),
                cliente.getNumero()
        );
    }
}

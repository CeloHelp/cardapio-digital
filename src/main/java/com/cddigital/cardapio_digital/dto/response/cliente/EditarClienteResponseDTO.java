package com.cddigital.cardapio_digital.dto.response.cliente;

import jakarta.validation.constraints.NotBlank;

public record EditarClienteResponseDTO(
         String nome,
         String telefone,
         String email,
        String endereco,
        int numero
) {
}

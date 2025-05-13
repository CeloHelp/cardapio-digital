package com.cddigital.cardapio_digital.service;

import com.cddigital.cardapio_digital.dto.request.ClienteRequestDTO;
import com.cddigital.cardapio_digital.dto.response.ClienteResponseDTO;
import com.cddigital.cardapio_digital.entity.Cliente;
import com.cddigital.cardapio_digital.exceptions.costumized.ClienteNaoEncontradoException;
import com.cddigital.cardapio_digital.repository.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteResponseDTO cadastrarCliente(ClienteRequestDTO clienteRequestDTO) {
        var cliente = new Cliente();

        BeanUtils.copyProperties(clienteRequestDTO, cliente);
        clienteRepository.save(cliente);

        return new ClienteResponseDTO(
            cliente.getId(),
            cliente.getNome(),
            cliente.getTelefone(),
            cliente.getEmail()
        );
    }

    public Cliente buscarClientePorId(UUID id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));
    }
}

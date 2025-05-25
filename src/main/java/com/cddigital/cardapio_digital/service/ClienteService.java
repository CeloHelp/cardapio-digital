package com.cddigital.cardapio_digital.service;

import com.cddigital.cardapio_digital.dto.request.cliente.AlterarStatusClienteRequestDTO;
import com.cddigital.cardapio_digital.dto.request.cliente.ClienteRequestDTO;
import com.cddigital.cardapio_digital.dto.request.cliente.EditarClienteRequestDTO;
import com.cddigital.cardapio_digital.dto.response.cliente.AlterarStatusClienteResponseDTO;
import com.cddigital.cardapio_digital.dto.response.cliente.ClienteResponseDTO;
import com.cddigital.cardapio_digital.dto.response.cliente.EditarClienteResponseDTO;
import com.cddigital.cardapio_digital.dto.response.cliente.ListarClienteDTO;
import com.cddigital.cardapio_digital.dto.response.produto.ListarProdutoDTO;
import com.cddigital.cardapio_digital.entity.Cliente;
import com.cddigital.cardapio_digital.enums.StatusGlobal;
import com.cddigital.cardapio_digital.exceptions.costumized.ClienteNaoEncontradoException;
import com.cddigital.cardapio_digital.repository.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteResponseDTO cadastrarCliente(ClienteRequestDTO clienteRequestDTO) {
        var cliente = new Cliente();

        cliente.setStatus(StatusGlobal.ATIVO);

        BeanUtils.copyProperties(clienteRequestDTO, cliente);
        clienteRepository.save(cliente);

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

    public Cliente buscarClientePorId(UUID id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));
    }

    public List<ListarClienteDTO> listarClientes() {
        return  clienteRepository.findByStatus(StatusGlobal.ATIVO)
                .stream()
                .map(ListarClienteDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public AlterarStatusClienteResponseDTO alterarStatusCliente(AlterarStatusClienteRequestDTO alterarClienteRequestDTO) {
        Cliente cliente = clienteRepository.findById(alterarClienteRequestDTO.idCliente())
                .orElseThrow(() -> new ClienteNaoEncontradoException(alterarClienteRequestDTO.idCliente()));

        cliente.setStatus(StatusGlobal.INATIVO);
        clienteRepository.save(cliente);

        return new AlterarStatusClienteResponseDTO(
                "Cliente" + cliente.getId() + "Alterado com sucesso para: " + cliente.getStatus()
        );
    }

    public EditarClienteResponseDTO editarCliente(UUID id,EditarClienteRequestDTO editarClienteRequestDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));
        BeanUtils.copyProperties(editarClienteRequestDTO, cliente, "id");

        clienteRepository.save(cliente);

        return new EditarClienteResponseDTO(
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getEndereco(),
                cliente.getNumero()
        );
    }
}

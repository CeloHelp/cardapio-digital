package com.cddigital.cardapio_digital.service;

import com.cddigital.cardapio_digital.dto.request.cliente.AlterarStatusClienteRequestDTO;
import com.cddigital.cardapio_digital.dto.request.cliente.ClienteRequestDTO;
import com.cddigital.cardapio_digital.dto.request.cliente.EditarClienteRequestDTO;
import com.cddigital.cardapio_digital.dto.response.cliente.AlterarStatusClienteResponseDTO;
import com.cddigital.cardapio_digital.dto.response.cliente.ClienteResponseDTO;
import com.cddigital.cardapio_digital.dto.response.cliente.EditarClienteResponseDTO;
import com.cddigital.cardapio_digital.dto.response.cliente.ListarClienteDTO;
import com.cddigital.cardapio_digital.entity.Cliente;
import com.cddigital.cardapio_digital.enums.StatusGlobal;
import com.cddigital.cardapio_digital.exceptions.costumized.ClienteNaoEncontradoException;
import com.cddigital.cardapio_digital.repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {
    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("Should register client successfully")
    void cadastrarCliente() {
        ClienteRequestDTO dto = new ClienteRequestDTO("João", "123456789", "joao@email.com", "Rua A", 10);
        Cliente cliente = new Cliente();
        cliente.setId(UUID.randomUUID());
        cliente.setNome("João");
        cliente.setTelefone("123456789");
        cliente.setEmail("joao@email.com");
        cliente.setEndereco("Rua A");
        cliente.setNumero(10);
        cliente.setStatus(StatusGlobal.ATIVO);
        Mockito.when(clienteRepository.save(Mockito.any())).thenReturn(cliente);
        ClienteResponseDTO response = clienteService.cadastrarCliente(dto);
        assertEquals("João", response.nome());
        assertEquals("123456789", response.telefone());
        assertEquals("joao@email.com", response.email());
        assertEquals("Rua A", response.endereco());
        assertEquals(10, response.numero());
        assertEquals(StatusGlobal.ATIVO, response.status());
        Mockito.verify(clienteRepository).save(Mockito.any());
    }

    @Test
    @DisplayName("Should fetch client by ID successfully")
    void buscarClientePorId() {
        UUID id = UUID.randomUUID();
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome("Maria");
        Mockito.when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        Cliente result = clienteService.buscarClientePorId(id);
        assertEquals("Maria", result.getNome());
        Mockito.verify(clienteRepository).findById(id);
    }

    @Test
    @DisplayName("Should throw exception when fetching non-existent client")
    void buscarClientePorIdInexistente() {
        UUID id = UUID.randomUUID();
        Mockito.when(clienteRepository.findById(id)).thenReturn(Optional.empty());
        Exception ex = assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.buscarClientePorId(id));
        assertEquals("Cliente com ID " + id + " nao encontrado", ex.getMessage());
    }

    @Test
    @DisplayName("Should list only active clients")
    void listarClientes() {
        Cliente c1 = new Cliente();
        c1.setId(UUID.randomUUID());
        c1.setNome("João");
        c1.setTelefone("123456789");
        c1.setStatus(StatusGlobal.ATIVO);
        Cliente c2 = new Cliente();
        c2.setId(UUID.randomUUID());
        c2.setNome("Maria");
        c2.setTelefone("987654321");
        c2.setStatus(StatusGlobal.ATIVO);
        List<Cliente> clientes = List.of(c1, c2);
        Mockito.when(clienteRepository.findByStatus(StatusGlobal.ATIVO)).thenReturn(clientes);
        List<ListarClienteDTO> result = clienteService.listarClientes();
        assertEquals(2, result.size());
        assertEquals("João", result.get(0).nome());
        assertEquals("Maria", result.get(1).nome());
        Mockito.verify(clienteRepository).findByStatus(StatusGlobal.ATIVO);
    }

    @Test
    @DisplayName("Should change client status successfully")
    void alterarStatusCliente() {
        UUID id = UUID.randomUUID();
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setStatus(StatusGlobal.ATIVO);
        Mockito.when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        Mockito.when(clienteRepository.save(Mockito.any())).thenReturn(cliente);
        AlterarStatusClienteRequestDTO dto = new AlterarStatusClienteRequestDTO(id, StatusGlobal.INATIVO);
        AlterarStatusClienteResponseDTO response = clienteService.alterarStatusCliente(dto);
        assertEquals(StatusGlobal.INATIVO, cliente.getStatus());
        assertTrue(response.mensagem().contains("Alterado com sucesso"));
        Mockito.verify(clienteRepository).save(cliente);
    }

    @Test
    @DisplayName("Should throw exception when changing status of non-existent client")
    void alterarStatusClienteInexistente() {
        UUID id = UUID.randomUUID();
        Mockito.when(clienteRepository.findById(id)).thenReturn(Optional.empty());
        AlterarStatusClienteRequestDTO dto = new AlterarStatusClienteRequestDTO(id, StatusGlobal.INATIVO);
        Exception ex = assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.alterarStatusCliente(dto));
        assertEquals("Cliente com ID " + id + " nao encontrado", ex.getMessage());
    }

    @Test
    @DisplayName("Should edit client successfully")
    void editarCliente() {
        UUID id = UUID.randomUUID();
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome("João");
        cliente.setTelefone("123456789");
        cliente.setEmail("joao@email.com");
        cliente.setEndereco("Rua A");
        cliente.setNumero(10);
        cliente.setStatus(StatusGlobal.ATIVO);
        EditarClienteRequestDTO dto = new EditarClienteRequestDTO("João Editado", "987654321", "editado@email.com", "Rua B", 20);
        Mockito.when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        Mockito.when(clienteRepository.save(Mockito.any())).thenReturn(cliente);
        EditarClienteResponseDTO response = clienteService.editarCliente(id, dto);
        assertEquals("João Editado", response.nome());
        assertEquals("987654321", response.telefone());
        assertEquals("editado@email.com", response.email());
        assertEquals("Rua B", response.endereco());
        assertEquals(20, response.numero());
        Mockito.verify(clienteRepository).save(cliente);
    }

    @Test
    @DisplayName("Should throw exception when editing non-existent client")
    void editarClienteInexistente() {
        UUID id = UUID.randomUUID();
        EditarClienteRequestDTO dto = new EditarClienteRequestDTO("João Editado", "987654321", "editado@email.com", "Rua B", 20);
        Mockito.when(clienteRepository.findById(id)).thenReturn(Optional.empty());
        Exception ex = assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.editarCliente(id, dto));
        assertEquals("Cliente com ID " + id + " nao encontrado", ex.getMessage());
    }
} 
package com.cddigital.cardapio_digital.service;

import com.cddigital.cardapio_digital.dto.request.pedido.AlterarStatusPedidoRequestDTO;
import com.cddigital.cardapio_digital.dto.request.pedido.ItemPedidoRequestDTO;
import com.cddigital.cardapio_digital.dto.request.pedido.PedidoRequestDTO;
import com.cddigital.cardapio_digital.dto.response.pedido.AlterarStatusPedidoResponseDTO;
import com.cddigital.cardapio_digital.dto.response.pedido.ListarPedidoDTO;
import com.cddigital.cardapio_digital.dto.response.pedido.PedidoResponseDTO;
import com.cddigital.cardapio_digital.entity.Cliente;
import com.cddigital.cardapio_digital.entity.Pedido;
import com.cddigital.cardapio_digital.entity.PedidoItem;
import com.cddigital.cardapio_digital.entity.Produto;
import com.cddigital.cardapio_digital.enums.StatusPedido;
import com.cddigital.cardapio_digital.exceptions.costumized.PedidoNaoEncontradoException;
import com.cddigital.cardapio_digital.exceptions.costumized.ProdutoNaoEncontradoException;
import com.cddigital.cardapio_digital.repository.PedidoRepository;
import com.cddigital.cardapio_digital.repository.ProdutoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {
    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private ProdutoRepository produtoRepository;
    @Mock
    private ClienteService clienteService;
    @InjectMocks
    private PedidoService pedidoService;

    @Test
    @DisplayName("Should create order successfully")
    void criarPedido() {
        UUID clienteId = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setNome("Cliente Teste");
        cliente.setTelefone("123456789");
        Produto produto = new Produto();
        produto.setId(produtoId);
        produto.setNome("Produto Teste");
        produto.setPreco(BigDecimal.valueOf(10));
        ItemPedidoRequestDTO itemDTO = new ItemPedidoRequestDTO(produtoId, 2);
        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO(clienteId, List.of(itemDTO), 2);
        Mockito.when(clienteService.buscarClientePorId(clienteId)).thenReturn(cliente);
        Mockito.when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));
        Mockito.when(pedidoRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));
        PedidoResponseDTO response = pedidoService.criarPedido(pedidoRequestDTO);
        assertEquals("Cliente Teste", response.nomeCliente());
        assertEquals("123456789", response.telefone());
        assertEquals(BigDecimal.valueOf(20), response.total());
        assertEquals(1, response.itens().size());
        assertEquals("Produto Teste", response.itens().get(0).nomeProduto());
        assertEquals(BigDecimal.valueOf(10), response.itens().get(0).precoUnitario());
        assertEquals(2, response.itens().get(0).quantidade());
        assertEquals(BigDecimal.valueOf(20), response.itens().get(0).subtotal());
    }

    @Test
    @DisplayName("Should throw exception when product not found during order creation")
    void criarPedidoProdutoNaoEncontrado() {
        UUID clienteId = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        ItemPedidoRequestDTO itemDTO = new ItemPedidoRequestDTO(produtoId, 2);
        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO(clienteId, List.of(itemDTO), 2);
        Mockito.when(clienteService.buscarClientePorId(clienteId)).thenReturn(cliente);
        Mockito.when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());
        Exception ex = assertThrows(ProdutoNaoEncontradoException.class, () -> pedidoService.criarPedido(pedidoRequestDTO));
        assertEquals("Produto com ID " + produtoId + " nao encontrado", ex.getMessage());
    }

    @Test
    @DisplayName("Should list all orders")
    void listarPedidos() {
        Pedido pedido = new Pedido();
        pedido.setId(UUID.randomUUID());
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatusPedido(StatusPedido.AGUARDANDO);
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Teste");
        pedido.setCliente(cliente);
        Produto produto = new Produto();
        produto.setNome("Produto Teste");
        produto.setPreco(BigDecimal.valueOf(10));
        PedidoItem item = new PedidoItem();
        item.setProduto(produto);
        item.setQuantidade(2);
        item.setPedido(pedido);
        pedido.setItens(List.of(item));
        pedido.setTotal(BigDecimal.valueOf(20));
        Mockito.when(pedidoRepository.findAll()).thenReturn(List.of(pedido));
        List<ListarPedidoDTO> result = pedidoService.listarPedidos();
        assertEquals(1, result.size());
        assertEquals(pedido.getId(), result.get(0).id());
        assertEquals(1, result.get(0).items().size());
        assertEquals("Produto Teste", result.get(0).items().get(0).nomeProduto());
    }

    @Test
    @DisplayName("Should change order status successfully")
    void alterarStatusPedido() {
        UUID pedidoId = UUID.randomUUID();
        Pedido pedido = new Pedido();
        pedido.setId(pedidoId);
        pedido.setStatusPedido(StatusPedido.AGUARDANDO);
        Mockito.when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));
        Mockito.when(pedidoRepository.save(Mockito.any())).thenReturn(pedido);
        AlterarStatusPedidoRequestDTO dto = new AlterarStatusPedidoRequestDTO(pedidoId, StatusPedido.ENTREGUE);
        AlterarStatusPedidoResponseDTO response = pedidoService.alterarStatusPedido(dto);
        assertEquals(StatusPedido.ENTREGUE, pedido.getStatusPedido());
        assertTrue(response.mensagem().contains("alterado com sucesso"));
        Mockito.verify(pedidoRepository).save(pedido);
    }

    @Test
    @DisplayName("Should throw exception when changing status of non-existent order")
    void alterarStatusPedidoInexistente() {
        UUID pedidoId = UUID.randomUUID();
        Mockito.when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.empty());
        AlterarStatusPedidoRequestDTO dto = new AlterarStatusPedidoRequestDTO(pedidoId, StatusPedido.ENTREGUE);
        Exception ex = assertThrows(PedidoNaoEncontradoException.class, () -> pedidoService.alterarStatusPedido(dto));
        assertEquals("Pedido com ID " + pedidoId + "Nao encontrado", ex.getMessage());
    }

    @Test
    @DisplayName("Should list orders by client")
    void listarPedidosPorCliente() {
        UUID clienteId = UUID.randomUUID();
        Pedido pedido = new Pedido();
        pedido.setId(UUID.randomUUID());
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        pedido.setCliente(cliente);
        Produto produto = new Produto();
        produto.setNome("Produto Teste");
        produto.setPreco(BigDecimal.valueOf(10));
        PedidoItem item = new PedidoItem();
        item.setProduto(produto);
        item.setQuantidade(2);
        item.setPedido(pedido);
        pedido.setItens(List.of(item));
        Mockito.when(pedidoRepository.findByClienteId(clienteId)).thenReturn(List.of(pedido));
        List<ListarPedidoDTO> result = pedidoService.listarPedidosPorCliente(clienteId);
        assertEquals(1, result.size());
        assertEquals(pedido.getId(), result.get(0).id());
        assertEquals(1, result.get(0).items().size());
        assertEquals("Produto Teste", result.get(0).items().get(0).nomeProduto());
    }

    @Test
    @DisplayName("Should fetch order by ID successfully")
    void buscarPedidoPorId() {
        UUID pedidoId = UUID.randomUUID();
        Pedido pedido = new Pedido();
        pedido.setId(pedidoId);
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Teste");
        cliente.setTelefone("123456789");
        pedido.setCliente(cliente);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatusPedido(StatusPedido.AGUARDANDO);
        pedido.setTotal(BigDecimal.valueOf(20));
        Produto produto = new Produto();
        produto.setNome("Produto Teste");
        produto.setPreco(BigDecimal.valueOf(10));
        PedidoItem item = new PedidoItem();
        item.setProduto(produto);
        item.setQuantidade(2);
        item.setPedido(pedido);
        pedido.setItens(List.of(item));
        Mockito.when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));
        PedidoResponseDTO response = pedidoService.buscarPedidoPorId(pedidoId);
        assertEquals("Cliente Teste", response.nomeCliente());
        assertEquals("123456789", response.telefone());
        assertEquals(BigDecimal.valueOf(20), response.total());
        assertEquals(1, response.itens().size());
        assertEquals("Produto Teste", response.itens().get(0).nomeProduto());
    }

    @Test
    @DisplayName("Should throw exception when fetching non-existent order by ID")
    void buscarPedidoPorIdInexistente() {
        UUID pedidoId = UUID.randomUUID();
        Mockito.when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.empty());
        Exception ex = assertThrows(PedidoNaoEncontradoException.class, () -> pedidoService.buscarPedidoPorId(pedidoId));
        assertEquals("Pedido com ID " + pedidoId + "Nao encontrado", ex.getMessage());
    }
} 
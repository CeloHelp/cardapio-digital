package com.cddigital.cardapio_digital.service;

import com.cddigital.cardapio_digital.dto.request.pedido.AlterarStatusPedidoRequestDTO;
import com.cddigital.cardapio_digital.dto.request.pedido.ItemPedidoRequestDTO;
import com.cddigital.cardapio_digital.dto.request.pedido.PedidoRequestDTO;
import com.cddigital.cardapio_digital.dto.response.pedido.AlterarStatusPedidoResponseDTO;
import com.cddigital.cardapio_digital.dto.response.pedido.ItemPedidoResumoDTO;
import com.cddigital.cardapio_digital.dto.response.pedido.ListarPedidoDTO;
import com.cddigital.cardapio_digital.dto.response.pedido.PedidoResponseDTO;
import com.cddigital.cardapio_digital.entity.*;
import com.cddigital.cardapio_digital.enums.StatusPedido;
import com.cddigital.cardapio_digital.exceptions.costumized.PedidoNaoEncontradoException;
import com.cddigital.cardapio_digital.exceptions.costumized.ProdutoNaoEncontradoException;
import com.cddigital.cardapio_digital.repository.PedidoRepository;
import com.cddigital.cardapio_digital.repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteService clienteService;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, ClienteService clienteService) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.clienteService = clienteService;
    }

    public PedidoResponseDTO criarPedido(PedidoRequestDTO pedidoRequestDTO) {
        // Buscar cliente
        Cliente cliente = clienteService.buscarClientePorId(pedidoRequestDTO.idCliente());

        // Criar pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatusPedido(StatusPedido.AGUARDANDO);

        List<PedidoItem> itens = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        // Criar e calcular itens do pedido
        for (ItemPedidoRequestDTO itemDTO : pedidoRequestDTO.itens()) {
            Produto produto = produtoRepository.findById(itemDTO.idProduto())
                    .orElseThrow(() -> new ProdutoNaoEncontradoException(itemDTO.idProduto()));

            PedidoItem item = new PedidoItem();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            item.setPedido(pedido);

            itens.add(item);

            BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(itemDTO.quantidade()));
            total = total.add(subtotal);
        }

        pedido.setItens(itens);
        pedido.setTotal(total);

        pedidoRepository.save(pedido);



        return PedidoResponseDTO.fromEntity(pedido);
    }

    public List<ListarPedidoDTO> listarPedidos() {
        return pedidoRepository.findAll()
                .stream()
                .map(pedido -> new ListarPedidoDTO(
                        pedido.getId(),
                        pedido.getItens().stream()
                                .map(item -> new ItemPedidoResumoDTO(
                                        item.getProduto().getNome(),
                                        item.getProduto().getPreco(),
                                        item.getQuantidade(),
                                        item.getProduto().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()))

                                ))
                                .toList()
                ))
                .toList();
    }


    public AlterarStatusPedidoResponseDTO alterarStatusPedido(AlterarStatusPedidoRequestDTO alterarStatusPedidoRequestDTO) {

        Pedido pedido = pedidoRepository.findById(alterarStatusPedidoRequestDTO.idPedido())
                .orElseThrow(() -> new PedidoNaoEncontradoException(alterarStatusPedidoRequestDTO.idPedido()));
        pedido.setStatusPedido(alterarStatusPedidoRequestDTO.novoStatus());

        pedidoRepository.save(pedido);

        return new AlterarStatusPedidoResponseDTO("Status do pedido " + pedido.getId() + " alterado com sucesso para " + pedido.getStatusPedido()
        );


    }

    public List<ListarPedidoDTO> listarPedidosPorCliente(UUID idCliente) {
        // Busca todos os pedidos feitos pelo cliente com base no ID
        List<Pedido> pedidos = pedidoRepository.findByClienteId(idCliente);

        // Converte a lista de pedidos em uma lista de DTOs resumidos
        return pedidos.stream()
                .map(pedido -> new ListarPedidoDTO(
                        pedido.getId(),
                        pedido.getItens().stream()
                                .map(ItemPedidoResumoDTO::fromEntity)
                                .toList()
                ))
                .toList();



    }


    public PedidoResponseDTO buscarPedidoPorId(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
       return PedidoResponseDTO.fromEntity(pedido);
   }








}


package com.cddigital.cardapio_digital.service;

import com.cddigital.cardapio_digital.dto.request.ItemPedidoRequestDTO;
import com.cddigital.cardapio_digital.dto.request.PedidoRequestDTO;
import com.cddigital.cardapio_digital.dto.response.ItemPedidoResumoDTO;
import com.cddigital.cardapio_digital.dto.response.ListarPedidoDTO;
import com.cddigital.cardapio_digital.dto.response.PedidoResponseDTO;
import com.cddigital.cardapio_digital.entity.*;
import com.cddigital.cardapio_digital.enums.StatusPedido;
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
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDTO.idProduto()));

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

        // Montar resposta
        List<ItemPedidoResumoDTO> itensResumo = itens.stream()
                .map(item -> new ItemPedidoResumoDTO(
                        item.getProduto().getNome(),
                        item.getProduto().getPreco(),
                        item.getQuantidade(),
                        item.getProduto().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()))
                )).toList();

        return new PedidoResponseDTO(
                pedido.getId(),
                cliente.getNome(),
                cliente.getTelefone(),
                pedido.getDataHora(),
                pedido.getStatusPedido().name(),
                pedido.getTotal(),
                itensResumo
        );
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






}


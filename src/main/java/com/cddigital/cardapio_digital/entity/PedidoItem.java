package com.cddigital.cardapio_digital.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "PEDIDO_ITEM")
public class PedidoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
     private int quantidade;
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

}

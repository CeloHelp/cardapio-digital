package com.cddigital.cardapio_digital.entity;

import com.cddigital.cardapio_digital.enums.StatusPedido;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "PEDIDO")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private BigDecimal total;
    private LocalDateTime dataHora;
   private StatusPedido statusPedido;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
   private  List<PedidoItem> itens = new ArrayList<>();


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
    public StatusPedido getStatusPedido() {
        return statusPedido;
    }
    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }
    public List<PedidoItem> getItens() {
        return itens;
    }
    public void setItens(List<PedidoItem> itens) {
        this.itens = itens;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}

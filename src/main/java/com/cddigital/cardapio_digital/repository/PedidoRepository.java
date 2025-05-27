package com.cddigital.cardapio_digital.repository;

import com.cddigital.cardapio_digital.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public  interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    List<Pedido> findByClienteId(UUID idCliente);




}

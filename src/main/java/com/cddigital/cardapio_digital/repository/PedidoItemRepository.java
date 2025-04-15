package com.cddigital.cardapio_digital.repository;

import com.cddigital.cardapio_digital.entity.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, UUID> {
}

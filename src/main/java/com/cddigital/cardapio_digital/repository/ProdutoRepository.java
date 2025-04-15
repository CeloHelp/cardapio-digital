package com.cddigital.cardapio_digital.repository;

import com.cddigital.cardapio_digital.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
}

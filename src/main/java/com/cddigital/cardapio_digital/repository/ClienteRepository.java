package com.cddigital.cardapio_digital.repository;

import com.cddigital.cardapio_digital.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
}

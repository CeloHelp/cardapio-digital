package com.cddigital.cardapio_digital.repository;

import com.cddigital.cardapio_digital.entity.Cliente;
import com.cddigital.cardapio_digital.enums.StatusGlobal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    List<Cliente> findByStatus(StatusGlobal status);
}

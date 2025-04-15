package com.cddigital.cardapio_digital.repository;

import com.cddigital.cardapio_digital.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
}

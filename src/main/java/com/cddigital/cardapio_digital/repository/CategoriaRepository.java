package com.cddigital.cardapio_digital.repository;

import com.cddigital.cardapio_digital.entity.Categoria;
import com.cddigital.cardapio_digital.enums.StatusGlobal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {

  List<Categoria> findByStatus(StatusGlobal statusGlobal);
}

package com.cddigital.cardapio_digital.controller;

import com.cddigital.cardapio_digital.dto.request.AlterarStatusCategoriaRequestDTO;
import com.cddigital.cardapio_digital.dto.request.CategoriaRequestDTO;
import com.cddigital.cardapio_digital.dto.response.AlterarStatusCategoriaResponseDTO;
import com.cddigital.cardapio_digital.dto.response.CategoriaResponseDTO;
import com.cddigital.cardapio_digital.entity.Categoria;
import com.cddigital.cardapio_digital.repository.CategoriaRepository;
import com.cddigital.cardapio_digital.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cardapio/v1/categorias")
public class CategoriaControllerV1 {
    private final CategoriaService categoriaService;

    public CategoriaControllerV1(CategoriaService categoriaService) {
        this.categoriaService =  categoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> cadastrarCategoria(@Valid @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
         CategoriaResponseDTO response = categoriaService.cadastrarCategoria(categoriaRequestDTO);

         URI location = URI.create("/cardapio/v1/categorias/" + response.id());

         return ResponseEntity.created(location).body(response);

    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AlterarStatusCategoriaResponseDTO> alterarStatusCategoria(@PathVariable UUID id, @Valid @RequestBody AlterarStatusCategoriaRequestDTO alterarStatusCategoriaRequestDTO) {
        AlterarStatusCategoriaResponseDTO response = categoriaService.alterarStatusCategoria(alterarStatusCategoriaRequestDTO);
        return ResponseEntity.ok(response);
    }




}


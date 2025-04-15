package com.cddigital.cardapio_digital.controller;

import com.cddigital.cardapio_digital.dto.request.ProdutoRequestDTO;
import com.cddigital.cardapio_digital.dto.response.ProdutoResponseDTO;
import com.cddigital.cardapio_digital.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/cardapio/v1/produtos")
public class ProdutoControllerV1 {
    private final ProdutoService produtoService;

    public ProdutoControllerV1(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO){
        ProdutoResponseDTO response = produtoService.cadastrarProduto(produtoRequestDTO);

        URI location = URI.create("/cardapio/v1/produtos" + response.id());

        return ResponseEntity.created(location).body(response);
    }

}

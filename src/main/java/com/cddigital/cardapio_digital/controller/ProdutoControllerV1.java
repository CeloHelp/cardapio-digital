package com.cddigital.cardapio_digital.controller;

import com.cddigital.cardapio_digital.dto.request.produto.AlterarStatusProdutoRequestDTO;
import com.cddigital.cardapio_digital.dto.request.produto.EditarProdutoRequestDTO;
import com.cddigital.cardapio_digital.dto.request.produto.ProdutoRequestDTO;
import com.cddigital.cardapio_digital.dto.response.produto.AlterarStatusProdutoResponseDTO;
import com.cddigital.cardapio_digital.dto.response.produto.EditarProdutoResponseDTO;
import com.cddigital.cardapio_digital.dto.response.produto.ListarProdutoDTO;
import com.cddigital.cardapio_digital.dto.response.produto.ProdutoResponseDTO;
import com.cddigital.cardapio_digital.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<List<ListarProdutoDTO>> listarProdutos(){
        List<ListarProdutoDTO> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    @PatchMapping("{id}/status")
    public ResponseEntity<AlterarStatusProdutoResponseDTO> alterarStatusProduto(@PathVariable UUID id, @RequestBody @Valid AlterarStatusProdutoRequestDTO alterarProdutoRequestDTO){
        AlterarStatusProdutoResponseDTO response = produtoService.AlterarStatusProduto(alterarProdutoRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EditarProdutoResponseDTO> editarProduto(@PathVariable UUID id, @RequestBody @Valid EditarProdutoRequestDTO editarProdutoRequestDTO){
        EditarProdutoResponseDTO response = produtoService.editarProduto(id, editarProdutoRequestDTO);
        return ResponseEntity.ok(response);
    }

}

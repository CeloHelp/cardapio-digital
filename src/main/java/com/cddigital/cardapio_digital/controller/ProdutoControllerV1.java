package com.cddigital.cardapio_digital.controller;

import com.cddigital.cardapio_digital.dto.request.produto.AlterarStatusProdutoRequestDTO;
import com.cddigital.cardapio_digital.dto.request.produto.EditarProdutoRequestDTO;
import com.cddigital.cardapio_digital.dto.request.produto.ProdutoRequestDTO;
import com.cddigital.cardapio_digital.dto.response.produto.AlterarStatusProdutoResponseDTO;
import com.cddigital.cardapio_digital.dto.response.produto.EditarProdutoResponseDTO;
import com.cddigital.cardapio_digital.dto.response.produto.ListarProdutoDTO;
import com.cddigital.cardapio_digital.dto.response.produto.ProdutoResponseDTO;
import com.cddigital.cardapio_digital.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Tag(name = "Produtos", description = "Operações de cadastro, listagem, edição e alteração de status dos produtos")
@RestController
@RequestMapping("/cardapio/v1/produtos")
public class ProdutoControllerV1 {

    private final ProdutoService produtoService;

    public ProdutoControllerV1(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(summary = "Cadastrar um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO){
        ProdutoResponseDTO response = produtoService.cadastrarProduto(produtoRequestDTO);
        URI location = URI.create("/cardapio/v1/produtos" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Listar todos os produtos ativos")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<ListarProdutoDTO>> listarProdutos(){
        List<ListarProdutoDTO> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    @Operation(summary = "Alterar o status de um produto (ex: inativar)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status do produto alterado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PatchMapping("{id}/status")
    public ResponseEntity<AlterarStatusProdutoResponseDTO> alterarStatusProduto(
            @PathVariable UUID id,
            @RequestBody @Valid AlterarStatusProdutoRequestDTO alterarProdutoRequestDTO){
        AlterarStatusProdutoResponseDTO response = produtoService.AlterarStatusProduto(alterarProdutoRequestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Editar dados de um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<EditarProdutoResponseDTO> editarProduto(
            @PathVariable UUID id,
            @RequestBody @Valid EditarProdutoRequestDTO editarProdutoRequestDTO){
        EditarProdutoResponseDTO response = produtoService.editarProduto(id, editarProdutoRequestDTO);
        return ResponseEntity.ok(response);
    }

}

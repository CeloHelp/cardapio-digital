package com.cddigital.cardapio_digital.controller;

import com.cddigital.cardapio_digital.dto.request.categoria.AlterarStatusCategoriaRequestDTO;
import com.cddigital.cardapio_digital.dto.request.categoria.CategoriaRequestDTO;
import com.cddigital.cardapio_digital.dto.request.categoria.EditarCategoriaRequestDTO;
import com.cddigital.cardapio_digital.dto.response.categoria.AlterarStatusCategoriaResponseDTO;
import com.cddigital.cardapio_digital.dto.response.categoria.CategoriaResponseDTO;
import com.cddigital.cardapio_digital.dto.response.categoria.EditarCategoriaResponseDTO;
import com.cddigital.cardapio_digital.dto.response.categoria.ListarCategoriaDTO;
import com.cddigital.cardapio_digital.service.CategoriaService;
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

@Tag(name = "Categorias", description = "Operações relacionadas às categorias de produtos")
@RestController
@RequestMapping("/cardapio/v1/categorias")
public class CategoriaControllerV1 {

    private final CategoriaService categoriaService;

    public CategoriaControllerV1(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Operation(summary = "Cadastrar uma nova categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> cadastrarCategoria(@Valid @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
        CategoriaResponseDTO response = categoriaService.cadastrarCategoria(categoriaRequestDTO);
        URI location = URI.create("/cardapio/v1/categorias/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Alterar o status de uma categoria (ex: inativar)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status da categoria alterado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<AlterarStatusCategoriaResponseDTO> alterarStatusCategoria(
            @PathVariable UUID id,
            @Valid @RequestBody AlterarStatusCategoriaRequestDTO alterarStatusCategoriaRequestDTO) {
        AlterarStatusCategoriaResponseDTO response = categoriaService.alterarStatusCategoria(alterarStatusCategoriaRequestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Editar uma categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @PatchMapping("{id}")
    public ResponseEntity<EditarCategoriaResponseDTO> editarCategoria(
            @PathVariable UUID id,
            @Valid @RequestBody EditarCategoriaRequestDTO editarCategoriaRequestDTO) {
        EditarCategoriaResponseDTO response = categoriaService.editarCategoria(id, editarCategoriaRequestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar todas as categorias ativas")
    @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<ListarCategoriaDTO>> listarCategorias() {
        List<ListarCategoriaDTO> categorias = categoriaService.listarCategoria();
        return ResponseEntity.ok(categorias);
    }
}

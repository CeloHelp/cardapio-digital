package com.cddigital.cardapio_digital.service;

import com.cddigital.cardapio_digital.dto.request.categoria.AlterarStatusCategoriaRequestDTO;
import com.cddigital.cardapio_digital.dto.request.categoria.CategoriaRequestDTO;
import com.cddigital.cardapio_digital.dto.request.categoria.EditarCategoriaRequestDTO;
import com.cddigital.cardapio_digital.dto.response.categoria.AlterarStatusCategoriaResponseDTO;
import com.cddigital.cardapio_digital.dto.response.categoria.CategoriaResponseDTO;
import com.cddigital.cardapio_digital.dto.response.categoria.EditarCategoriaResponseDTO;
import com.cddigital.cardapio_digital.dto.response.categoria.ListarCategoriaDTO;
import com.cddigital.cardapio_digital.entity.Categoria;
import com.cddigital.cardapio_digital.enums.StatusGlobal;
import com.cddigital.cardapio_digital.exceptions.costumized.CategoriaNaoEncontradaException;
import com.cddigital.cardapio_digital.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {
    @Mock
    private CategoriaRepository categoriaRepository;
    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    @DisplayName("Should register category successfully")
    void cadastrarCategoria() {
        CategoriaRequestDTO dto = new CategoriaRequestDTO("Bebidas");
        Categoria categoria = new Categoria();
        categoria.setId(UUID.randomUUID());
        categoria.setNome("Bebidas");
        categoria.setStatus(StatusGlobal.ATIVO);
        Mockito.when(categoriaRepository.save(Mockito.any())).thenReturn(categoria);
        CategoriaResponseDTO response = categoriaService.cadastrarCategoria(dto);
        assertEquals("Bebidas", response.nome());
        Mockito.verify(categoriaRepository).save(Mockito.any());
    }

    @Test
    @DisplayName("Should change category status successfully")
    void alterarStatusCategoria() {
        UUID id = UUID.randomUUID();
        StatusGlobal novoStatus = StatusGlobal.INATIVO;
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNome("Lanches");
        categoria.setStatus(StatusGlobal.ATIVO);
        Mockito.when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));
        Mockito.when(categoriaRepository.save(Mockito.any())).thenReturn(categoria);
        AlterarStatusCategoriaRequestDTO dto = new AlterarStatusCategoriaRequestDTO(id, novoStatus);
        AlterarStatusCategoriaResponseDTO response = categoriaService.alterarStatusCategoria(dto);
        assertEquals(StatusGlobal.INATIVO, categoria.getStatus());
        assertTrue(response.mensagem().contains("Alterado com sucesso"));
        Mockito.verify(categoriaRepository).save(categoria);
    }

    @Test
    @DisplayName("Should throw exception when changing status of non-existent category")
    void alterarStatusCategoriaInexistente() {
        UUID id = UUID.randomUUID();
        StatusGlobal novoStatus = StatusGlobal.INATIVO;
        Mockito.when(categoriaRepository.findById(id)).thenReturn(Optional.empty());
        AlterarStatusCategoriaRequestDTO dto = new AlterarStatusCategoriaRequestDTO(id, novoStatus);
        Exception ex = assertThrows(CategoriaNaoEncontradaException.class, () -> categoriaService.alterarStatusCategoria(dto));
        assertEquals("Categoria com ID " + id + " nao encontrada", ex.getMessage());
    }

    @Test
    @DisplayName("Should edit category successfully")
    void editarCategoria() {
        UUID id = UUID.randomUUID();
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNome("Doces");
        categoria.setStatus(StatusGlobal.ATIVO);
        EditarCategoriaRequestDTO dto = new EditarCategoriaRequestDTO("Doces Novos");
        Mockito.when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));
        Mockito.when(categoriaRepository.save(Mockito.any())).thenReturn(categoria);
        EditarCategoriaResponseDTO response = categoriaService.editarCategoria(id, dto);
        assertEquals("Doces Novos", response.nome());
        Mockito.verify(categoriaRepository).save(categoria);
    }

    @Test
    @DisplayName("Should throw exception when editing non-existent category")
    void editarCategoriaInexistente() {
        UUID id = UUID.randomUUID();
        EditarCategoriaRequestDTO dto = new EditarCategoriaRequestDTO("Doces Novos");
        Mockito.when(categoriaRepository.findById(id)).thenReturn(Optional.empty());
        Exception ex = assertThrows(CategoriaNaoEncontradaException.class, () -> categoriaService.editarCategoria(id, dto));
        assertEquals("Categoria com ID " + id + " nao encontrada", ex.getMessage());
    }

    @Test
    @DisplayName("Should list only active categories")
    void listarCategoria() {
        Categoria cat1 = new Categoria();
        cat1.setId(UUID.randomUUID());
        cat1.setNome("Bebidas");
        cat1.setStatus(StatusGlobal.ATIVO);
        Categoria cat2 = new Categoria();
        cat2.setId(UUID.randomUUID());
        cat2.setNome("Lanches");
        cat2.setStatus(StatusGlobal.ATIVO);
        List<Categoria> categorias = List.of(cat1, cat2);
        Mockito.when(categoriaRepository.findByStatus(StatusGlobal.ATIVO)).thenReturn(categorias);
        List<ListarCategoriaDTO> result = categoriaService.listarCategoria();
        assertEquals(2, result.size());
        Mockito.verify(categoriaRepository).findByStatus(StatusGlobal.ATIVO);
    }
}
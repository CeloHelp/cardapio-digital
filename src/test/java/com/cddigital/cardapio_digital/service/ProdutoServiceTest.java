package com.cddigital.cardapio_digital.service;

import com.cddigital.cardapio_digital.dto.request.produto.AlterarStatusProdutoRequestDTO;
import com.cddigital.cardapio_digital.dto.response.produto.AlterarStatusProdutoResponseDTO;
import com.cddigital.cardapio_digital.entity.Produto;
import com.cddigital.cardapio_digital.enums.StatusGlobal;
import com.cddigital.cardapio_digital.exceptions.costumized.ProdutoNaoEncontradoException;
import com.cddigital.cardapio_digital.repository.ProdutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;


    @InjectMocks
    ProdutoService produtoService;




    @Test
    @DisplayName("Should alterate produto status when everything is Ok")
    void alterarStatusProdutoCase1() {

        UUID id = UUID.randomUUID();
        Produto produto = new Produto();
        produto.setId(id);
        produto.setStatus(StatusGlobal.ATIVO);
        AlterarStatusProdutoRequestDTO requestDTO = new AlterarStatusProdutoRequestDTO(id, StatusGlobal.INATIVO);

        Mockito.when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));

        AlterarStatusProdutoResponseDTO responseDTO = produtoService.AlterarStatusProduto(requestDTO);

        Assertions.assertEquals(StatusGlobal.INATIVO, StatusGlobal.valueOf(produto.getStatus()));

        Mockito.verify(produtoRepository).save(produto);









    }

    @Test
    @DisplayName("Should throw Exception when produto cannot be find")
    void alterarStatusProdutoCase2() throws ProdutoNaoEncontradoException {

        UUID id = UUID.randomUUID();
        Produto produto = new Produto();
        produto.setId(id);
        produto.setStatus(StatusGlobal.ATIVO);

        Mockito.when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        Exception thrown = Assertions.assertThrows(ProdutoNaoEncontradoException.class, () -> {
            AlterarStatusProdutoRequestDTO requestDTO = new AlterarStatusProdutoRequestDTO(id, StatusGlobal.ATIVO);
            produtoService.AlterarStatusProduto(requestDTO);

        });

        Assertions.assertEquals("Produto com ID " + id + " nao encontrado", thrown.getMessage());

    }
}
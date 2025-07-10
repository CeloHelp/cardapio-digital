package com.cddigital.cardapio_digital.service;

import com.cddigital.cardapio_digital.dto.request.produto.AlterarStatusProdutoRequestDTO;
import com.cddigital.cardapio_digital.dto.request.produto.ProdutoRequestDTO;
import com.cddigital.cardapio_digital.dto.request.produto.EditarProdutoRequestDTO;
import com.cddigital.cardapio_digital.dto.response.produto.AlterarStatusProdutoResponseDTO;
import com.cddigital.cardapio_digital.dto.response.produto.EditarProdutoResponseDTO;
import com.cddigital.cardapio_digital.dto.response.produto.ProdutoResponseDTO;
import com.cddigital.cardapio_digital.entity.Categoria;
import com.cddigital.cardapio_digital.entity.Produto;
import com.cddigital.cardapio_digital.enums.StatusGlobal;
import com.cddigital.cardapio_digital.exceptions.costumized.ProdutoNaoEncontradoException;
import com.cddigital.cardapio_digital.repository.ProdutoRepository;
import jakarta.validation.*;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;


    @InjectMocks
    ProdutoService produtoService;









    @Test
    @DisplayName("Should create a product when everything is OK ")


    void cadastrarProdutoCase1() {

        Categoria categoria = new Categoria();
        categoria.setId(UUID.randomUUID());       // criando categoria (objeto auxiliar) //
        categoria.setNome("Lanches");



        UUID id = UUID.randomUUID();     // criando o produto //
        Produto produto = new Produto();

        produto.setNome(produto.getNome());
        produto.setDescricao(produto.getDescricao());
        produto.setPreco(produto.getPreco());
        produto.setImagemUrl(produto.getImagemUrl());
        produto.setStatus(StatusGlobal.ATIVO);
        produto.setCategoria(categoria);
        ProdutoRequestDTO produtoRequestDTO =
                new ProdutoRequestDTO(produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getImagemUrl(), categoria.getId());


        Mockito.when(produtoRepository.save(Mockito.any())).thenReturn(produto);

        ProdutoResponseDTO responseDTO = produtoService.cadastrarProduto(produtoRequestDTO);

        Assertions.assertEquals(produto.getNome(), responseDTO.nome());
        Assertions.assertEquals(produto.getDescricao(), responseDTO.descricao());
        Assertions.assertEquals(produto.getPreco(), responseDTO.preco());
        Assertions.assertEquals(produto.getImagemUrl(), responseDTO.imagemUrl());
        Assertions.assertEquals(produto.getStatus(), responseDTO.status());

        Mockito.verify(produtoRepository).save(Mockito.any());



    }

    @Test
    @DisplayName("Should Throw a Exception when product registration is something null ")
    void cadastrarProdutoCase2() {

        Categoria categoria = new Categoria();
        categoria.setId(UUID.randomUUID());       // criando categoria (objeto auxiliar) //
        categoria.setNome(null);



        UUID id = UUID.randomUUID();     // criando o produto //
        Produto produto = new Produto();

        produto.setNome(produto.getNome());
        produto.setDescricao(produto.getDescricao());
        produto.setPreco(produto.getPreco());
        produto.setImagemUrl(produto.getImagemUrl());
        produto.setStatus(StatusGlobal.ATIVO);
        produto.setCategoria(categoria);


        ProdutoRequestDTO dtoInvalido =
        new ProdutoRequestDTO(null, null, null, produto.getImagemUrl(), null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<ProdutoRequestDTO>> violations = validator.validate(dtoInvalido);

        Assertions.assertFalse(violations.isEmpty());

        violations.forEach(violation -> System.out.println(violation.getMessage()));















    }





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
        produto.setStatus(StatusGlobal.ATIVO); // criando o objeto

        Mockito.when(produtoRepository.findById(id)).thenReturn(Optional.empty()); // simulando que o produto não foi encontrado no banco de dados

        Exception thrown = Assertions.assertThrows(ProdutoNaoEncontradoException.class, () -> {  // lançando a exception no seguinte fluxo

            // simulando a requisição do usuário  como ativo (status padrão)
            AlterarStatusProdutoRequestDTO requestDTO = new AlterarStatusProdutoRequestDTO(id, StatusGlobal.ATIVO);

            produtoService.AlterarStatusProduto(requestDTO);

        });

        Assertions.assertEquals("Produto com ID " + id + " nao encontrado", thrown.getMessage()); // simulando mensagem da exception

    }

    @Test
    @DisplayName("Should edit produto when everything is Ok")
    void editarProdutoCase1() {
        UUID id = UUID.randomUUID();
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome("Produto Antigo");
        produto.setDescricao("Descricao Antiga");
        produto.setPreco(BigDecimal.valueOf(10.0));
        produto.setImagemUrl("url-antiga");
        produto.setStatus(StatusGlobal.ATIVO);

        EditarProdutoRequestDTO editarDTO = new EditarProdutoRequestDTO(
                "Produto Novo", "Descricao Nova", BigDecimal.valueOf(20.0), "url-nova"
        );

        Mockito.when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));
        Mockito.when(produtoRepository.save(Mockito.any())).thenReturn(produto);

        EditarProdutoResponseDTO responseDTO = produtoService.editarProduto(id, editarDTO);

        Assertions.assertEquals("Produto Novo", responseDTO.nome());
        Assertions.assertEquals("Descricao Nova", responseDTO.descricao());
        Assertions.assertEquals(BigDecimal.valueOf(20.0), responseDTO.preco());
        Assertions.assertEquals("url-nova", responseDTO.imagemUrl());
        Mockito.verify(produtoRepository).save(produto);
    }

    @Test
    @DisplayName("Should throw Exception when editing a produto that does not exist")
    void editarProdutoCase2() {
        UUID id = UUID.randomUUID();
        EditarProdutoRequestDTO editarDTO = new EditarProdutoRequestDTO(
                "Produto Novo", "Descricao Nova", BigDecimal.valueOf(20.0), "url-nova"
        );
        Mockito.when(produtoRepository.findById(id)).thenReturn(Optional.empty());
        Exception thrown = Assertions.assertThrows(ProdutoNaoEncontradoException.class, () -> {
            produtoService.editarProduto(id, editarDTO);
        });
        Assertions.assertEquals("Produto com ID " + id + " nao encontrado", thrown.getMessage());
    }

    @Test
    @DisplayName("Should list only active produtos")
    void listarProdutosCase1() {
        Produto produto1 = new Produto();
        produto1.setId(UUID.randomUUID());
        produto1.setNome("Produto 1");
        produto1.setStatus(StatusGlobal.ATIVO);
        Produto produto2 = new Produto();
        produto2.setId(UUID.randomUUID());
        produto2.setNome("Produto 2");
        produto2.setStatus(StatusGlobal.ATIVO);
        List<Produto> produtos = List.of(produto1, produto2);
        Mockito.when(produtoRepository.findByStatus(StatusGlobal.ATIVO)).thenReturn(produtos);
        List<?> result = produtoService.listarProdutos();
        Assertions.assertEquals(2, result.size());
        Mockito.verify(produtoRepository).findByStatus(StatusGlobal.ATIVO);
    }
}
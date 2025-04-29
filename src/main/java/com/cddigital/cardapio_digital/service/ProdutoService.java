package com.cddigital.cardapio_digital.service;

import com.cddigital.cardapio_digital.dto.request.ProdutoRequestDTO;
import com.cddigital.cardapio_digital.dto.response.ProdutoResponseDTO;
import com.cddigital.cardapio_digital.entity.Produto;
import com.cddigital.cardapio_digital.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static jakarta.persistence.GenerationType.UUID;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoResponseDTO cadastrarProduto(final ProdutoRequestDTO produtoRequestDTO) {
        var produto = new Produto();


        BeanUtils.copyProperties(produtoRequestDTO, produto);
        produtoRepository.save(produto);

        return new ProdutoResponseDTO(
               produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getImagemUrl(),
                produto.getCategoria()
        );

    }

}

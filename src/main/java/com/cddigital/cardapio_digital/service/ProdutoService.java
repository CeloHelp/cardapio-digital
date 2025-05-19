package com.cddigital.cardapio_digital.service;

import com.cddigital.cardapio_digital.dto.request.AlterarStatusProdutoRequestDTO;
import com.cddigital.cardapio_digital.dto.request.ProdutoRequestDTO;
import com.cddigital.cardapio_digital.dto.response.AlterarStatusProdutoResponseDTO;
import com.cddigital.cardapio_digital.dto.response.ListarProdutoDTO;
import com.cddigital.cardapio_digital.dto.response.ProdutoResponseDTO;
import com.cddigital.cardapio_digital.entity.Produto;
import com.cddigital.cardapio_digital.enums.StatusGlobal;
import com.cddigital.cardapio_digital.exceptions.costumized.ProdutoNaoEncontradoException;
import com.cddigital.cardapio_digital.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoResponseDTO cadastrarProduto(final ProdutoRequestDTO produtoRequestDTO) {
        var produto = new Produto();

        produto.setStatus(StatusGlobal.ATIVO);


        BeanUtils.copyProperties(produtoRequestDTO, produto);
        produtoRepository.save(produto);

        return new ProdutoResponseDTO(
               produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getImagemUrl(),
                produto.getCategoria(),
                produto.getStatus()

        );

    }

public List<ListarProdutoDTO> listarProdutos() {
        return produtoRepository.findByStatus(StatusGlobal.ATIVO)
                .stream()
                .map(ListarProdutoDTO::fromEntity)
                .collect(Collectors.toList());
}

public AlterarStatusProdutoResponseDTO AlterarStatusProduto(AlterarStatusProdutoRequestDTO inativarProdutoRequestDTO) {
        Produto produto = produtoRepository.findById(inativarProdutoRequestDTO.idProduto())
                .orElseThrow(()  -> new ProdutoNaoEncontradoException(inativarProdutoRequestDTO.idProduto()));

        produto.setStatus(StatusGlobal.INATIVO);
        produtoRepository.save(produto);

        return new AlterarStatusProdutoResponseDTO(
                "Produto" + produto.getId() + " alterado com sucesso para " + produto.getStatus()
        );
}

}

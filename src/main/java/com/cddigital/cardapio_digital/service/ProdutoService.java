package com.cddigital.cardapio_digital.service;

import com.cddigital.cardapio_digital.dto.request.produto.AlterarStatusProdutoRequestDTO;
import com.cddigital.cardapio_digital.dto.request.produto.EditarProdutoRequestDTO;
import com.cddigital.cardapio_digital.dto.request.produto.ProdutoRequestDTO;
import com.cddigital.cardapio_digital.dto.response.produto.AlterarStatusProdutoResponseDTO;
import com.cddigital.cardapio_digital.dto.response.produto.EditarProdutoResponseDTO;
import com.cddigital.cardapio_digital.dto.response.produto.ListarProdutoDTO;
import com.cddigital.cardapio_digital.dto.response.produto.ProdutoResponseDTO;
import com.cddigital.cardapio_digital.entity.Produto;
import com.cddigital.cardapio_digital.enums.StatusGlobal;
import com.cddigital.cardapio_digital.exceptions.costumized.ProdutoNaoEncontradoException;
import com.cddigital.cardapio_digital.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
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

        return  ProdutoResponseDTO.fromEntity(produto);

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

    produtoRepository.save(produto);

        return new AlterarStatusProdutoResponseDTO(
                "Produto" + produto.getId() + " alterado com sucesso para " + produto.getStatus()
        );


}

public EditarProdutoResponseDTO editarProduto(UUID id, EditarProdutoRequestDTO editarProdutoRequestDTO) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));



        BeanUtils.copyProperties(editarProdutoRequestDTO, produto, "id");
        produtoRepository.save(produto);

        return EditarProdutoResponseDTO.fromEntity(produto);


}

}

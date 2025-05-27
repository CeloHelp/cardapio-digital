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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaService (CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }


    public CategoriaResponseDTO cadastrarCategoria(CategoriaRequestDTO categoriaRequestDTO) {
        var categoria = new Categoria();

        categoria.setStatus(StatusGlobal.ATIVO);



        BeanUtils.copyProperties(categoriaRequestDTO, categoria);

        categoriaRepository.save(categoria);

        return CategoriaResponseDTO.fromEntity(categoria);
    }

    public AlterarStatusCategoriaResponseDTO alterarStatusCategoria(AlterarStatusCategoriaRequestDTO alterarStatusCategoriaRequestDTO) {
        Categoria categoria = categoriaRepository.findById(alterarStatusCategoriaRequestDTO.idCategoria())
                .orElseThrow(() -> new CategoriaNaoEncontradaException(alterarStatusCategoriaRequestDTO.idCategoria()));

        categoria.setStatus(StatusGlobal.INATIVO);
        categoriaRepository.save(categoria);

        return new AlterarStatusCategoriaResponseDTO(
                "Categoria " + categoria.getNome() + " Alterado com sucesso para: " + categoria.getStatus()
        );

    }

    public EditarCategoriaResponseDTO editarCategoria(UUID id, EditarCategoriaRequestDTO editarCategoriaRequestDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(id));

        BeanUtils.copyProperties(editarCategoriaRequestDTO, categoria);

        categoriaRepository.save(categoria);
        return EditarCategoriaResponseDTO.fromEntity(categoria);
    }

    public List<ListarCategoriaDTO> listarCategoria() {
        return categoriaRepository.findByStatus(StatusGlobal.ATIVO)
                .stream()
                .map(ListarCategoriaDTO::fromEntity)
                .collect(Collectors.toList());

    }


}

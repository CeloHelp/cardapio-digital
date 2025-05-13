package com.cddigital.cardapio_digital.service;

import com.cddigital.cardapio_digital.dto.request.CategoriaRequestDTO;
import com.cddigital.cardapio_digital.dto.response.CategoriaResponseDTO;
import com.cddigital.cardapio_digital.entity.Categoria;
import com.cddigital.cardapio_digital.repository.CategoriaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaService (CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }


    public CategoriaResponseDTO cadastrarCategoria(CategoriaRequestDTO categoriaRequestDTO) {
        var categoria = new Categoria();



        BeanUtils.copyProperties(categoriaRequestDTO, categoria);

        categoriaRepository.save(categoria);

        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome()
        );
    }


}

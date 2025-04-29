package com.cddigital.cardapio_digital.controller;


import com.cddigital.cardapio_digital.dto.request.PedidoRequestDTO;
import com.cddigital.cardapio_digital.dto.response.PedidoResponseDTO;
import com.cddigital.cardapio_digital.repository.ClienteRepository;
import com.cddigital.cardapio_digital.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/cardapio/v1/pedidos")
public class PedidoControllerV1 {

    private PedidoService pedidoService;

    public PedidoControllerV1(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity <PedidoResponseDTO> criarPedido(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        PedidoResponseDTO pedidoResponseDTO = pedidoService.criarPedido(pedidoRequestDTO);
        URI location = URI.create("/cardapio/v1/pedidos" + pedidoResponseDTO.id());
        return ResponseEntity.created(location).body(pedidoResponseDTO);
    }

}

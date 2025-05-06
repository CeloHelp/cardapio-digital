package com.cddigital.cardapio_digital.controller;


import com.cddigital.cardapio_digital.dto.request.AlterarStatusPedidoRequestDTO;
import com.cddigital.cardapio_digital.dto.request.PedidoRequestDTO;
import com.cddigital.cardapio_digital.dto.response.AlterarStatusPedidoResponseDTO;
import com.cddigital.cardapio_digital.dto.response.ListarPedidoDTO;
import com.cddigital.cardapio_digital.dto.response.PedidoResponseDTO;
import com.cddigital.cardapio_digital.repository.ClienteRepository;
import com.cddigital.cardapio_digital.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    @GetMapping
    public ResponseEntity <List<ListarPedidoDTO>> listarPedidos() {
        List<ListarPedidoDTO> pedidos = pedidoService.listarPedidos();
        URI location = URI.create("/cardapio/v1/listarpedidos");
        return ResponseEntity.ok(pedidos);


    }
    @PatchMapping
    public ResponseEntity AlterarStatusPedido(@RequestBody AlterarStatusPedidoRequestDTO alterarStatusPedidoRequestDTO) {
        AlterarStatusPedidoResponseDTO response = pedidoService.alterarStatusPedido(alterarStatusPedidoRequestDTO);
        URI location = URI.create("/cardapio/v1/status");
        return ResponseEntity.ok(response);
    }



}

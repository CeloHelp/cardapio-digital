package com.cddigital.cardapio_digital.controller;

import com.cddigital.cardapio_digital.dto.request.AlterarStatusPedidoRequestDTO;
import com.cddigital.cardapio_digital.dto.request.PedidoRequestDTO;
import com.cddigital.cardapio_digital.dto.response.AlterarStatusPedidoResponseDTO;
import com.cddigital.cardapio_digital.dto.response.ListarPedidoDTO;
import com.cddigital.cardapio_digital.dto.response.PedidoResponseDTO;
import com.cddigital.cardapio_digital.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cardapio/v1/pedidos")
public class PedidoControllerV1 {

    private final PedidoService pedidoService;

    public PedidoControllerV1(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        PedidoResponseDTO pedidoResponseDTO = pedidoService.criarPedido(pedidoRequestDTO);
        URI location = URI.create("/cardapio/v1/pedidos/" + pedidoResponseDTO.id());
        return ResponseEntity.created(location).body(pedidoResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ListarPedidoDTO>> listarPedidos() {
        List<ListarPedidoDTO> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @PatchMapping("/status")
    public ResponseEntity<AlterarStatusPedidoResponseDTO> alterarStatusPedido(
            @RequestBody AlterarStatusPedidoRequestDTO alterarStatusPedidoRequestDTO) {
        AlterarStatusPedidoResponseDTO response = pedidoService.alterarStatusPedido(alterarStatusPedidoRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<ListarPedidoDTO>> listarPedidoPorCliente(@PathVariable UUID idCliente) {
        List<ListarPedidoDTO> pedidos = pedidoService.listarPedidosPorCliente(idCliente);
        return ResponseEntity.ok(pedidos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPedidoPorId(@PathVariable UUID id) {
        PedidoResponseDTO pedido = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }

}

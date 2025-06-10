package com.cddigital.cardapio_digital.controller;

import com.cddigital.cardapio_digital.dto.request.pedido.AlterarStatusPedidoRequestDTO;
import com.cddigital.cardapio_digital.dto.request.pedido.PedidoRequestDTO;
import com.cddigital.cardapio_digital.dto.response.pedido.AlterarStatusPedidoResponseDTO;
import com.cddigital.cardapio_digital.dto.response.pedido.ListarPedidoDTO;
import com.cddigital.cardapio_digital.dto.response.pedido.PedidoResponseDTO;
import com.cddigital.cardapio_digital.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Tag(name = "Pedidos", description = "Operações relacionadas a pedidos")
@RestController
@RequestMapping("/cardapio/v1/pedidos")
public class PedidoControllerV1 {

    private final PedidoService pedidoService;

    public PedidoControllerV1(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Criar um novo pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados")
    })
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        PedidoResponseDTO pedidoResponseDTO = pedidoService.criarPedido(pedidoRequestDTO);
        URI location = URI.create("/cardapio/v1/pedidos/" + pedidoResponseDTO.id());
        return ResponseEntity.created(location).body(pedidoResponseDTO);
    }

    @Operation(summary = "Listar todos os pedidos")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<ListarPedidoDTO>> listarPedidos() {
        List<ListarPedidoDTO> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Alterar o status de um pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status do pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PatchMapping("/status")
    public ResponseEntity<AlterarStatusPedidoResponseDTO> alterarStatusPedido(
            @RequestBody AlterarStatusPedidoRequestDTO alterarStatusPedidoRequestDTO) {
        AlterarStatusPedidoResponseDTO response = pedidoService.alterarStatusPedido(alterarStatusPedidoRequestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar pedidos por cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos do cliente retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<ListarPedidoDTO>> listarPedidoPorCliente(@PathVariable UUID idCliente) {
        List<ListarPedidoDTO> pedidos = pedidoService.listarPedidosPorCliente(idCliente);
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Buscar pedido por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPedidoPorId(@PathVariable UUID id) {
        PedidoResponseDTO pedido = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }
}

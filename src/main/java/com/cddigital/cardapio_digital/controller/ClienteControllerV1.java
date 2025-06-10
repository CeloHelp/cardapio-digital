package com.cddigital.cardapio_digital.controller;

import com.cddigital.cardapio_digital.dto.request.cliente.AlterarStatusClienteRequestDTO;
import com.cddigital.cardapio_digital.dto.request.cliente.ClienteRequestDTO;
import com.cddigital.cardapio_digital.dto.request.cliente.EditarClienteRequestDTO;
import com.cddigital.cardapio_digital.dto.response.cliente.AlterarStatusClienteResponseDTO;
import com.cddigital.cardapio_digital.dto.response.cliente.ClienteResponseDTO;
import com.cddigital.cardapio_digital.dto.response.cliente.EditarClienteResponseDTO;
import com.cddigital.cardapio_digital.dto.response.cliente.ListarClienteDTO;
import com.cddigital.cardapio_digital.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Tag(name = "Clientes", description = "Operações de cadastro, listagem, edição e alteração de status dos clientes")
@RestController
@RequestMapping("/cardapio/v1/clientes")
public class ClienteControllerV1 {

    private final ClienteService clienteService;

    public ClienteControllerV1(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Cadastrar um novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(@RequestBody ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO clienteResponseDTO = clienteService.cadastrarCliente(clienteRequestDTO);
        URI location =  URI.create("/cardapio/v1/clientes/" + clienteResponseDTO.id());
        return ResponseEntity.created(location).body(clienteResponseDTO);
    }

    @Operation(summary = "Alterar o status de um cliente (ex: inativar)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status do cliente alterado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PatchMapping("{id}/status")
    public ResponseEntity<AlterarStatusClienteResponseDTO> atualizarCliente(
            @PathVariable UUID id,
            @RequestBody @Valid AlterarStatusClienteRequestDTO alterarStatusClienteRequestDTO) {
        AlterarStatusClienteResponseDTO response = clienteService.alterarStatusCliente(alterarStatusClienteRequestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Editar os dados de um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PatchMapping("{id}")
    public ResponseEntity<EditarClienteResponseDTO> editarCliente(
            @PathVariable UUID id,
            @RequestBody EditarClienteRequestDTO editarClienteRequestDTO) {
        EditarClienteResponseDTO response = clienteService.editarCliente(id, editarClienteRequestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar todos os clientes ativos")
    @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<ListarClienteDTO>> listarClientes(){
        List<ListarClienteDTO> clientes = clienteService.listarClientes();
        return  ResponseEntity.ok(clientes);
    }
}

package com.cddigital.cardapio_digital.controller;

import com.cddigital.cardapio_digital.dto.request.cliente.AlterarStatusClienteRequestDTO;
import com.cddigital.cardapio_digital.dto.request.cliente.ClienteRequestDTO;
import com.cddigital.cardapio_digital.dto.response.cliente.AlterarStatusClienteResponseDTO;
import com.cddigital.cardapio_digital.dto.response.cliente.ClienteResponseDTO;
import com.cddigital.cardapio_digital.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/cardapio/v1/clientes")
public class ClienteControllerV1 {

    private ClienteService clienteService;

    public ClienteControllerV1(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(@RequestBody ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO clienteResponseDTO = clienteService.cadastrarCliente(clienteRequestDTO);
        URI location =  URI.create("/cardapio/v1/clientes/" + clienteResponseDTO.id());
        return ResponseEntity.created(location).body(clienteResponseDTO);

    }

    @PatchMapping("{id}/status")
    public ResponseEntity<AlterarStatusClienteResponseDTO> atualizarCliente(@PathVariable UUID id, @RequestBody @Valid AlterarStatusClienteRequestDTO alterarStatusClienteRequestDTO) {
        AlterarStatusClienteResponseDTO response = clienteService.alterarStatusCliente(alterarStatusClienteRequestDTO);

               return ResponseEntity.ok(response);

    }
}

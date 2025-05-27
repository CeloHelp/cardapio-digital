package com.cddigital.cardapio_digital.controller;

import com.cddigital.cardapio_digital.dto.request.cliente.AlterarStatusClienteRequestDTO;
import com.cddigital.cardapio_digital.dto.request.cliente.ClienteRequestDTO;
import com.cddigital.cardapio_digital.dto.request.cliente.EditarClienteRequestDTO;
import com.cddigital.cardapio_digital.dto.response.cliente.AlterarStatusClienteResponseDTO;
import com.cddigital.cardapio_digital.dto.response.cliente.ClienteResponseDTO;
import com.cddigital.cardapio_digital.dto.response.cliente.EditarClienteResponseDTO;
import com.cddigital.cardapio_digital.dto.response.cliente.ListarClienteDTO;
import com.cddigital.cardapio_digital.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cardapio/v1/clientes")
public class ClienteControllerV1 {

    private final ClienteService clienteService;

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

    @PatchMapping("{id}")
    public ResponseEntity<EditarClienteResponseDTO> editarCliente(@PathVariable UUID id, @RequestBody EditarClienteRequestDTO editarClienteRequestDTO) {
        EditarClienteResponseDTO response = clienteService.editarCliente(id, editarClienteRequestDTO);
        return ResponseEntity.ok(response);
    }


    @GetMapping
     public ResponseEntity<List<ListarClienteDTO>> listarClientes(){
        List<ListarClienteDTO> clientes = clienteService.listarClientes();
        return  ResponseEntity.ok(clientes);

     }
}

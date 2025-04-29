package com.cddigital.cardapio_digital.controller;

import com.cddigital.cardapio_digital.dto.request.ClienteRequestDTO;
import com.cddigital.cardapio_digital.dto.response.ClienteResponseDTO;
import com.cddigital.cardapio_digital.entity.Cliente;
import com.cddigital.cardapio_digital.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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

}

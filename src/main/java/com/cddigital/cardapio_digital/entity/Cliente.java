package com.cddigital.cardapio_digital.entity;

import com.cddigital.cardapio_digital.enums.StatusGlobal;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;
    private String telefone;
    private String email;
    private String endereco;
    private int numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusGlobal status = StatusGlobal.ATIVO;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public StatusGlobal getStatus() {
        return status;
    }
    public void setStatus(StatusGlobal status) {
        this.status = status;
    }
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }

}

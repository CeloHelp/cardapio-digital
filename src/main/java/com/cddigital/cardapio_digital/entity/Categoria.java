package com.cddigital.cardapio_digital.entity;

import com.cddigital.cardapio_digital.enums.StatusGlobal;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "CATEGORIA")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;

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

    public StatusGlobal getStatus() {
        return status;
    }
    public void setStatus(StatusGlobal status) {
        this.status = status;
    }
}

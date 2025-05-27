package com.cddigital.cardapio_digital.entity;

import com.cddigital.cardapio_digital.enums.StatusGlobal;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "PRODUTO")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String imagemUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusGlobal status = StatusGlobal.ATIVO;
    @ManyToOne
   private  Categoria categoria;

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
    public String getDescricao() {
        return descricao;
    }
    public String getStatus() {
        return status.toString();
    }

    public void setStatus(StatusGlobal status) {
        this.status = status;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public BigDecimal getPreco() {
        return preco;
    }
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
    public String getImagemUrl() {
        return imagemUrl;
    }
    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}

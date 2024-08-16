package br.com.app.controlepedidos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private Double preco;

    public Produto(){
    }

    public Produto(String nome, Double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    private void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    private void setPreco(Double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return  "id: " + id +
                ", nome: '" + nome +
                ", preco: " + preco;
    }
}

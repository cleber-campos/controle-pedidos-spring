package br.com.app.controlepedidos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_itens_pedidos")
public class ItemPedido{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    private Double preco;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    public ItemPedido(){
    }

    public ItemPedido(Integer quantidade, Double preco, Produto produto, Pedido pedido) {
        super();
        this.quantidade = quantidade;
        this.preco = preco;
        this.produto = produto;
        this.pedido = pedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getquantidade() {
        return quantidade;
    }

    private void setquantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getpreco() {
        return preco;
    }

    private void setpreco(Double preco) {
        this.preco = preco;
    }

    public Produto getproduto() {
        return produto;
    }

    private void setproduto(Produto produto) {
        this.produto = produto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Double subTotal(){
        return this.quantidade * this.preco;
    }

    @Override
    public String toString() {
        return "ID Item: " + id +
                " | Cod Produto: " + getproduto().getId() +
                " | Descricao: " + getproduto().getNome() +
                " | Preco R$" + String.format("%.2f", preco) +
                " | Qtde: "  + quantidade +
                " | Subtotal: " + String.format("%.2f",subTotal()) + "\n";
    }
}

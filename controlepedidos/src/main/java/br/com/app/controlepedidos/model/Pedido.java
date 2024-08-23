package br.com.app.controlepedidos.model;

import br.com.app.controlepedidos.enums.StatusPedido;
import jakarta.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_pedidos")
public class Pedido {
    @Transient
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date Data;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<ItemPedido> items = new ArrayList<>();

    public Pedido(){
    }

    public Pedido(Date Data, StatusPedido status, Cliente cliente) {
        super();
        this.Data = Data;
        this.status = status;
        this.cliente = cliente;
    }

    public Long getid() {
        return id;
    }

    public void setid(Long id) {
        this.id = id;
    }

    public Date getData() {
        return Data;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public void addItem(ItemPedido item){
        items.add(item);
    }

    public void removeItem(ItemPedido item){
        items.remove(item);
    }

    public double total(){
       double sum = 0;
       for (ItemPedido it : items) {
           sum += it.subTotal();
       }
        return sum;
    }

    @Override
    public String toString() {
        return  "Numero: " + id + "\n" +
                "Status: " + status + "\n" +
                "Cliente: " + cliente + "\n" +
                items + "\n" +
                "Total Pedido: R$" + String.format("%.2f", total()) + "\n";
    }
}
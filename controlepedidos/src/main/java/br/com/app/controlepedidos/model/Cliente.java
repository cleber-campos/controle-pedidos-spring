package br.com.app.controlepedidos.model;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private String email;
    private Date DataNascimento;

    @OneToMany(mappedBy = "cliente",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Pedido> pedidos = new ArrayList<>();

    @Transient
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public Cliente(){
    }

    public Cliente(String nome, String email, Date DataNascimento) {
        this.nome = nome;
        this.email = email;
        this.DataNascimento = DataNascimento;
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

    public String getEmail() {
        return email;
    }

    public Date getDataNascimento() {
        return DataNascimento;
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
                " | Email: " + email +
                " | Data Nasc: " + sdf.format(getDataNascimento());
    }

}

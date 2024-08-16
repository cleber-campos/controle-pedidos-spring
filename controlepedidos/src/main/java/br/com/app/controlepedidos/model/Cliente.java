package br.com.app.controlepedidos.model;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        DataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return nome + " (" + sdf.format(getDataNascimento()) + ") - " + email;
    }
}

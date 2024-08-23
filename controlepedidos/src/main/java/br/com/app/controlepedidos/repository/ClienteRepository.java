package br.com.app.controlepedidos.repository;

import br.com.app.controlepedidos.model.Cliente;
import br.com.app.controlepedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT p FROM Cliente c " +
            "JOIN c.pedidos p " +
            "WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nomeCliente, '%'))")
    List<Pedido> buscarPedidoPorCliente(String nomeCliente);
}

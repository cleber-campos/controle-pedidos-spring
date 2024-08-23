package br.com.app.controlepedidos.repository;

import br.com.app.controlepedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Query("SELECT p FROM Pedido p " +
            "WHERE p.id = :numeroPedido")
    List<Pedido> buscarPedidoPorNumero(int numeroPedido);

}

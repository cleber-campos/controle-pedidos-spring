package br.com.app.controlepedidos.repository;

import br.com.app.controlepedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}

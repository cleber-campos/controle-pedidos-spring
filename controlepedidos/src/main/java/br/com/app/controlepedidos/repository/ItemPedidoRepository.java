package br.com.app.controlepedidos.repository;

import br.com.app.controlepedidos.model.ItemPedido;
import br.com.app.controlepedidos.model.Pedido;
import br.com.app.controlepedidos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    @Query("SELECT i FROM ItemPedido i " +
            "JOIN i.produto pr " +
            "WHERE pr = :produto AND i.pedido = :pedido")
    Optional<ItemPedido> buscarProdutoNoItemPedido(Produto produto, Pedido pedido);
}

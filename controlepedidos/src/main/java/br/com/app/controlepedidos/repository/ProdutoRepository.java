package br.com.app.controlepedidos.repository;

import br.com.app.controlepedidos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}

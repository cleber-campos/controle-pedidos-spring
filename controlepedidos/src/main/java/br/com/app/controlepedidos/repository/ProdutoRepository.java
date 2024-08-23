package br.com.app.controlepedidos.repository;

import br.com.app.controlepedidos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByNomeContainingIgnoreCase(String nomeProduto);
}

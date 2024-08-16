package br.com.app.controlepedidos.repository;

import br.com.app.controlepedidos.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}

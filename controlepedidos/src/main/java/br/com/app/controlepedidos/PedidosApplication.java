package br.com.app.controlepedidos;


import br.com.app.controlepedidos.principal.Principal;
import br.com.app.controlepedidos.repository.ClienteRepository;
import br.com.app.controlepedidos.repository.ItemPedidoRepository;
import br.com.app.controlepedidos.repository.PedidoRepository;
import br.com.app.controlepedidos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParseException;

@SpringBootApplication
public class PedidosApplication implements CommandLineRunner {
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(PedidosApplication.class, args);
	}


	@Override
	public void run(String... args) throws ParseException {
        Principal principal = new Principal(clienteRepository, produtoRepository,
				pedidoRepository, itemPedidoRepository);
        principal.execute();
	}
}

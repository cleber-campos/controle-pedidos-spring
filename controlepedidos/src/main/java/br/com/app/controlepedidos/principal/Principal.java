package br.com.app.controlepedidos.principal;

import br.com.app.controlepedidos.enums.StatusPedido;
import br.com.app.controlepedidos.model.Cliente;
import br.com.app.controlepedidos.model.ItemPedido;
import br.com.app.controlepedidos.model.Pedido;
import br.com.app.controlepedidos.model.Produto;
import br.com.app.controlepedidos.repository.ClienteRepository;
import br.com.app.controlepedidos.repository.ItemPedidoRepository;
import br.com.app.controlepedidos.repository.PedidoRepository;
import br.com.app.controlepedidos.repository.ProdutoRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Principal {
    Scanner leitura = new Scanner(System.in);
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public Principal(ClienteRepository clienteRepository, ProdutoRepository produtoRepository,
                     PedidoRepository pedidoRepository, ItemPedidoRepository itemPedidoRepository) {
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public void execute() throws ParseException {
        var op = -1;
        while(op != 0){
            headerMenu();
            op = leitura.nextInt();
            leitura.nextLine();

            switch (op){
                case 1:
                    cadastrarCliente();
                    break;

                case 2:
                    cadastrarProduto();
                    break;

                case 3:
                    criarPedido();
                    break;

                case 4:
                    listarClientes();
                    break;

                case 5:
                    listarProdutos();
                    break;

                case 6:
                    listarPedidos();
                    break;

                case 7:
                    buscarClientesPorNome();
                    break;

                case 8:
                    buscarProdutoPorNome();
                    break;

                case 9:
                    buscarPedidoPorCliente();
                    break;

                case 10:
                    buscarPedidoPorNumero();
                    break;

                case 0:
                    System.out.println("Encerrando a aplicacao!");
                    leitura.close();
                    break;

                default:
                    System.out.println("Opcao invalida!");
            }
        }
    }

    private void cadastrarCliente() throws ParseException {
        System.out.print("Nome Cliente: ");
        String nomeCliente = leitura.nextLine();
        System.out.print("Email: ");
        String email = leitura.next();
        System.out.print("Data de Nascimento (DD/MM/YYYY): ");
        Date DataNascimento = sdf.parse(leitura.next());
        Cliente cliente = new Cliente(nomeCliente, email, DataNascimento);
        clienteRepository.save(cliente);
        System.out.println("cliente salvo no banco!");
    }

    private void cadastrarProduto() {
        System.out.print("Nome do Produto: ");
        var nomeProduto = leitura.nextLine();
        System.out.print("Preco: ");
        var precoProduto = leitura.nextDouble();
        Produto produto = new Produto(nomeProduto, precoProduto);
        produtoRepository.save(produto);
        System.out.println("produto salvo no banco!");
    }

    private void criarPedido() throws ParseException {
        //criar pedido
        //1) buscar cliente
        //clienteExisteNoBanco
        //no > //cadastrarNovoCliente
        //yes > //retornarCliente
        Cliente clienteTeste = new Cliente("Alex Green",
                "alex@gmail.com", sdf.parse("15/03/1985"));
        clienteRepository.save(clienteTeste);
        System.out.println("cliente salvo!");
        //2) cria obj pedido
        //Pedido pedido = new Pedido(new Date(), statusPedido, cliente);
        Pedido pedidoTeste = new Pedido(new Date(), StatusPedido.PROCESSING, clienteTeste);
        pedidoRepository.save(pedidoTeste);
        System.out.println("pedido salvo!");

        //criar itens do pedido
        //1) buscar produto
        //produtoExisteNoBanco
        //no > //cadastrarNovoProduto
        //yes > //retornarProduto
        Produto produtoTeste1 = new Produto("TV",1000.00);
        Produto produtoTeste2 = new Produto("Mouse",40.00);

        produtoRepository.saveAll(Arrays.asList(produtoTeste1, produtoTeste2));
        System.out.println("produtos salvos!");

        //2) cria obj itemPedido
        ItemPedido itemPedidoTeste1 = new ItemPedido(1, 1000.00, produtoTeste1,pedidoTeste);
        ItemPedido itemPedidoTeste2 = new ItemPedido(2, 40.00, produtoTeste2,pedidoTeste);

        itemPedidoRepository.saveAll(Arrays.asList(itemPedidoTeste1, itemPedidoTeste2));
        System.out.println("itens salvos!");
        //3) associar item ao pedido
        pedidoTeste.getItems().add(itemPedidoTeste1);
        pedidoTeste.getItems().add(itemPedidoTeste2);
        System.out.println("itens associados");

    }

    private void buscarPedidoPorNumero() {
    }

    private void buscarPedidoPorCliente() {
    }

    private void buscarProdutoPorNome() {
    }

    private void buscarClientesPorNome() {
    }

    private void listarPedidos() {
    }

    private void listarProdutos() {
        var produtos = produtoRepository.findAll();
        produtos.forEach(System.out::println);
    }

    private void listarClientes() {
        var clientes = clienteRepository.findAll();
        clientes.forEach(System.out::println);
    }

    public void headerMenu(){
        var menu = """
                  ------------------------------
                  ***   CONTROLE DE PEDIDOS ***
                  ------------------------------
                  1 - Cadastrar Cliente
                  2 - Cadastrar Produto
                  3 - Criar Pedido
                  4 - Listar Clientes
                  5 - Listar Produtos
                  6 - Listar Pedidos
                  7 - Buscar Cliente por Nome
                  8 - Buscar Produto por Nome
                  9 - Buscar Pedido por Cliente
                  10- Buscar Pedido por Numero
                  0 - Sair
                  ------------------------------
                  """;
        System.out.print('\n' +  menu);
    }

    private void criarPedidoTeste() throws ParseException {
        //1) criar cliente
        Cliente clienteTeste = new Cliente("Alex Green",
                "alex@gmail.com", sdf.parse("15/03/1985"));
        clienteRepository.save(clienteTeste);
        System.out.println("cliente salvo!");

        //2) cria pedido
        Pedido pedidoTeste = new Pedido(new Date(), StatusPedido.PROCESSING, clienteTeste);
        pedidoRepository.save(pedidoTeste);
        System.out.println("pedido salvo!");

        //3) criar itens do pedido
        Produto produtoTeste1 = new Produto("TV",1000.00);
        Produto produtoTeste2 = new Produto("Mouse",40.00);

        produtoRepository.saveAll(Arrays.asList(produtoTeste1, produtoTeste2));
        System.out.println("produtos salvos!");

        //4) cria itemPedido
        ItemPedido itemPedidoTeste1 = new ItemPedido(1, 1000.00, produtoTeste1,pedidoTeste);
        ItemPedido itemPedidoTeste2 = new ItemPedido(2, 40.00, produtoTeste2,pedidoTeste);

        itemPedidoRepository.saveAll(Arrays.asList(itemPedidoTeste1, itemPedidoTeste2));
        System.out.println("itens salvos!");
        //5) associar item ao pedido
        pedidoTeste.getItems().add(itemPedidoTeste1);
        pedidoTeste.getItems().add(itemPedidoTeste2);
        System.out.println("itens associados");

    }

}

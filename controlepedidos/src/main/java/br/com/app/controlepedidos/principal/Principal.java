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
import java.util.*;

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
                    var cliente = cadastrarCliente();
                    System.out.println(cliente);
                    break;

                case 2:
                    var produto = cadastrarProduto();
                    System.out.println(produto);
                    break;

                case 3:
                    cadastrarPedido();
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
                    System.out.print("Digite o nome do Cliente: ");
                    var nomeCliente = leitura.nextLine();
                    var clienteEncontrado = buscarClientesPorNome(nomeCliente);
                    if(clienteEncontrado.isEmpty()) {
                        System.out.println("Cliente nao encontrado");
                    }else {
                        headerCliente();
                        System.out.println(clienteEncontrado.get());
                    }
                    break;

                case 8:
                    System.out.print("Digite o nome do Produto: ");
                    var nomeProduto = leitura.nextLine();
                    var produtoEncontrado = buscarProdutoPorNome(nomeProduto);
                    if(produtoEncontrado.isEmpty()) {
                        System.out.println("Produto nao encontrado");
                    }else {
                        headerCliente();
                        System.out.println(produtoEncontrado.get());
                    }
                    break;

                case 9:
                    buscarPedidosPorCliente();
                    break;

                case 10:
                    buscarPedidoPorNumero();
                    break;

                case 0:
                    System.out.println("Encerrando a aplicacao!");
                    leitura.close();
                    break;

                case 99:
                    criarPedidoTeste();
                    break;

                default:
                    System.out.println("Opcao invalida!");
            }
        }
    }

    private Cliente cadastrarCliente() throws ParseException {
        Cliente novoCliente;
        System.out.print("Digite o nome do Cliente: ");
        var nomeCliente = leitura.nextLine();
        var cliente = buscarClientesPorNome(nomeCliente);
        if (cliente.isEmpty()) {
            System.out.print("Digite o Email: ");
            String email = leitura.next();
            System.out.print("Data de Nascimento (DD/MM/YYYY): ");
            Date DataNascimento = sdf.parse(leitura.next());
            novoCliente = new Cliente(nomeCliente.toUpperCase(), email.toUpperCase(), DataNascimento);
            clienteRepository.save(novoCliente);
            System.out.println("Novo cliente salvo no banco!");
        }else {
            System.out.println("Cliente encontrado!");
            return cliente.get();
        }
        return novoCliente;
    }

    private Produto cadastrarProduto() {
        Produto novoProduto;
        System.out.print("Digite o nome do Produto: ");
        var nomeProduto = leitura.nextLine();
        var produto = buscarProdutoPorNome(nomeProduto);
        if(produto.isEmpty()){
            System.out.print("Preco: ");
            var precoProduto = leitura.nextDouble();
            novoProduto = new Produto(nomeProduto.toUpperCase(), precoProduto);
            produtoRepository.save(novoProduto);
            System.out.println("Novo produto salvo no banco!");
        } else {
            System.out.println("Produto ja cadastrado!");
            return produto.get();
        }
        return novoProduto;
    }

    private void cadastrarPedido() throws ParseException {
        // 1) cadastrar cliente
        var menucliente = """
                  ------------------------------
                  ****       CLIENTE       ****
                  ------------------------------
                  """;
        System.out.println(menucliente);
        var cliente =  cadastrarCliente();
        System.out.println("#Cliente selecionado!" + "\n");

        // 2) criar pedido
        var menupedido = """
                  ------------------------------
                  ****       PEDIDO      ****
                  ------------------------------
                  """;
        System.out.println(menupedido);
        Pedido pedido = new Pedido(new Date(), StatusPedido.PROCESSING, cliente);
        pedidoRepository.save(pedido);
        System.out.println("#Pedido Numero: [" + pedido.getid() + "] - Criado!" + "\n");

        // 3) criar itens do pedido
        var menuItens = """
                  ------------------------------
                  ****   ITENS DO PEDIDO    ****
                  ------------------------------
                  """;
        System.out.println(menuItens);
        var resp = "S";
        while(!resp.equalsIgnoreCase("N") || !resp.equalsIgnoreCase("n") ){
            System.out.print("Incluir um novo item no pedido? S/N: ");
            resp = leitura.nextLine().trim();

            if(resp.equalsIgnoreCase("S")){
                // 3.1) cadastrar produto
                var produto = cadastrarProduto();
                System.out.println("#Produto selecionado!" + "\n");

                if(buscarProdutoNoItemPedido(produto, pedido)){
                    System.out.println("Produto ja consta no pedido");
                } else {
                    // 3.2) incluir itens no pedido
                    int qtdProduto;
                    qtdProduto = 0;
                    while (qtdProduto == 0) {
                        System.out.print("Digite a quantidade comprada: ");
                        qtdProduto = leitura.nextInt();
                        leitura.nextLine();
                    }
                    ItemPedido itemPedido = new ItemPedido(qtdProduto, produto.getPreco(), produto, pedido);
                    itemPedidoRepository.save(itemPedido);
                    System.out.println("#Item Incluido!");

                    //3.3) associar item ao pedido
                    pedido.getItems().add(itemPedido);
                    System.out.println("#Item associado" + "\n" );
                }

            }
        }
        System.out.println("Pedido Finalizado!");
    }

    private void buscarPedidoPorNumero() {
        System.out.print("Digite o numero do Pedido: ");
        var numeroPedido = leitura.nextInt();
        var pedidos = pedidoRepository.buscarPedidoPorNumero(numeroPedido);
        if(pedidos.isEmpty()){
            System.out.println("Pedido nao encontrado!");
        } else {
            pedidos.forEach(System.out::println);
        }
    }

    private void buscarPedidosPorCliente() {
        System.out.print("Digite o nome do cliente: ");
        var nomeCliente = leitura.nextLine();
        var pedidos = clienteRepository.buscarPedidoPorCliente(nomeCliente);
        if(pedidos.isEmpty()){
            System.out.println("Pedido nao encontrado!");
        } else {
            var pedidosEncontrados = pedidos.stream().toList();
            headerPedido();
            for (Pedido x : pedidosEncontrados) {
                System.out.println("Numero: " + x.getid() + '\n' +
                        "Status: " + x.getStatus() + '\n' +
                        "Cliente: " + x.getCliente() + '\n' +
                        "ITENS:" + '\n' +
                        x.getItems() + '\n' +
                        "------------------------------");
            }
        }
    }

    private Optional<Cliente> buscarClientesPorNome(String nomeCliente) {
        return clienteRepository.findByNomeContainingIgnoreCase(nomeCliente);
    }

    private Optional<Produto> buscarProdutoPorNome(String nomeProduto){
        return produtoRepository.findByNomeContainingIgnoreCase(nomeProduto);
    }

    private boolean buscarProdutoNoItemPedido(Produto produto, Pedido pedido){
        var registroEncontrado = itemPedidoRepository.buscarProdutoNoItemPedido(produto, pedido);
        return registroEncontrado.isPresent();
    }

    private void listarPedidos() {
        var pedidos = pedidoRepository.findAll();
        headerPedido();
        pedidos.forEach(System.out::println);
    }

    private void listarProdutos() {
        var produtos = produtoRepository.findAll();
        headerProduto();
        produtos.forEach(System.out::println);
    }

    private void listarClientes() {
        var clientes = clienteRepository.findAll();
        headerCliente();
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
                  99- #criar pedido de teste#
                  
                  0 - Sair
                  ------------------------------
                  """;
        System.out.print('\n' +  menu);
    }

    public void headerCliente(){
        var menu = """
                  ------------------------------
                  ****       CLIENTES       ****
                  ------------------------------
                  """;
        System.out.print('\n' +  menu);
    }

    public void headerProduto(){
        var menu = """
                  ------------------------------
                  ****       PRODUTOS       ****
                  ------------------------------
                  """;
        System.out.print('\n' +  menu);
    }

    public void headerPedido(){
        var menu = """
                  ------------------------------
                  ****        PEDIDOS       ****
                  ------------------------------
                  """;
        System.out.print('\n' +  menu);
    }

    public void criarPedidoTeste() throws ParseException {
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

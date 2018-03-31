package com.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Cidade;
import com.cursomc.domain.Cliente;
import com.cursomc.domain.Endereco;
import com.cursomc.domain.Estado;
import com.cursomc.domain.Pagamento;
import com.cursomc.domain.PagamentoComBoleto;
import com.cursomc.domain.PagamentoComCartao;
import com.cursomc.domain.Pedido;
import com.cursomc.domain.Produto;
import com.cursomc.domain.enums.EstadoPagamento;
import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.repositories.CategoriaRepository;
import com.cursomc.repositories.CidadeRepository;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.repositories.EnderecoRepository;
import com.cursomc.repositories.EstadoRepository;
import com.cursomc.repositories.PagamentoRepository;
import com.cursomc.repositories.PedidoRepository;
import com.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	
	/**
	 * O @Autowired Instancia a Classe automaticamente para ser usada
	 */
	@Autowired
	CategoriaRepository categoriaRepository;
	
	
	/** Criando o relacionamento muitos pra muitos Categorias e Produtos
	 * O @Autowired Instancia a Classe automaticamente para ser usada
	 * 
	 */
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	EstadoRepository estadoRepository;
	
	@Autowired
	CidadeRepository cidadeRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	PagamentoRepository pagamentoRepository;
	
	@Autowired
	PedidoRepository pedidoRepository;


	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	
	/**
	 *Criando O metodo auxiliar que instancia os objetos ao iniciar a aplicação
	 *Usando o CommandLineRunner do springframework 
	 */
	@Override
	public void run(String... args) throws Exception {
		Categoria cat1=new Categoria(null,"Informática");
		Categoria cat2=new Categoria(null,"Escritório");
		
		Produto p1=new Produto(null,"Computador",2000.00);
		Produto p2=new Produto(null,"Impressora",800.00);
		Produto p3=new Produto(null,"Mouse",80.00);
		
       cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
       cat2.getProdutos().addAll(Arrays.asList(p2));

       
       p1.getCategorias().addAll(Arrays.asList(cat1));
       p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
       p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		/**
		 *Cria os dois Estados e Tres cidades
		 */
		Estado est1=new Estado(null,"Minas Gerais");
		Estado est2=new Estado(null,"São Paulo");
		
		
		Cidade c1=new Cidade(null, "Uberlândia", est1);
		Cidade c2=new Cidade(null, "São Paulo", est2);
		Cidade c3=new Cidade(null, "Campinas", est2);
		
		
		
		/**
		 *Adiciona as cidades aos estados
		 */
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		

		/**
		 *Cria os dois Clientes e os Tipos 
		 *e Salva no Banco criando as duas tabelas
		 */

		Cliente cli1=new Cliente(null, "Maria Silva", "maria@gmail.com", "3638912377",TipoCliente.PESSOAFISICA);
		Cliente cli2=new Cliente(null, "Laura", "Laura@gmail.com", "91237700087",TipoCliente.PESSOAJURIDICA);
		
		/**
		 *Insere os dois numeros de telefone do cliente
		 */
		cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));
		
		
		Endereco e1=new Endereco(null, "Rua das Flores", "300", "Apto 203", "Jardim", "38220834",c1,cli1);
		Endereco e2=new Endereco(null, "Avenida Matos","105","Sala 800", "centro", "38777012",c2,cli1);
	    
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido ped1= new Pedido(null,sdf.parse("30/09/2017 10:22"), cli1,e1);
		Pedido ped2= new Pedido(null,sdf.parse("10/10/2017 19:45"), cli1,e2);


		Pagamento pagt1=new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1,6);
		
		ped1.setPagamento(pagt1);
		
		Pagamento pagt2=new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2,sdf.parse("20/10/2017 19:45"),null);

		ped2.setPagamento(pagt2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		/**
		 *Insere os dois enderecos no cliente 1
		 */
		

		estadoRepository.saveAll(Arrays.asList(est1,est2));
		
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		clienteRepository.saveAll(Arrays.asList(cli1,cli2));
	    
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		
		pagamentoRepository.saveAll(Arrays.asList(pagt1,pagt2));
		
		
	}


	
	
	
	
}

package com.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Cidade;
import com.cursomc.domain.Cliente;
import com.cursomc.domain.Estado;
import com.cursomc.domain.Produto;
import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.repositories.CategoriaRepository;
import com.cursomc.repositories.CidadeRepository;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.repositories.EstadoRepository;
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

		estadoRepository.saveAll(Arrays.asList(est1,est2));
		
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		
		
		/**
		 *Cria os dois Clientes e os Tipos
		 *e Salva no Banco criando as duas tabelas
		 */

		Cliente cli1=new Cliente(null, "Maria Silva", "maria@gmail.com", "3638912377",TipoCliente.PESSOAFISICA);
		Cliente cli2=new Cliente(null, "Laura", "Laura@gmail.com", "91237700087",TipoCliente.PESSOAJURIDICA);

		clienteRepository.saveAll(Arrays.asList(cli1,cli2));
		
		System.out.println("A pessoa 1 é:"+cli1.getTipo());
		System.out.println("A pessoa 1 é:"+cli2.getTipo());
	}
	
	
	
}

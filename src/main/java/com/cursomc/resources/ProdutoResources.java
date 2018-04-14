package com.cursomc.resources;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Produto;
import com.cursomc.dto.CategoriaDTO;
import com.cursomc.dto.ProdutoDTO;
import com.cursomc.resources.utils.URL;
import com.cursomc.services.ProdutoService;

	/** 
	* Classe controladora que Faz as requisições para a Classe de Serviço que por sua 
	* vez requisita a Classe Repository que extende a JPARepository/DAO que manipula o Banco de Dados 
	*  
	*/

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResources {
	
	
	@Autowired
	private ProdutoService produtoService;
	

	 
	//End Points PRODUZ JSON
		/** End Point
		* Metodo que busca a Produto com todos os produtos relacionados a ela
		* Ou Lança uma exceção se não encontrar
		* Como não vai Consumir JSON não precisa Ler o @RequestBody do objeto
		*/
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Produto produto=produtoService.buscaPorId(id);
		return  ResponseEntity.ok().body(produto);
	
		}
	
	
	/** 
	* End Points que faz a Paginação dos Produtos 
	*/
	@RequestMapping(method=RequestMethod.GET,
	produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ProdutoDTO>> buscaProdutosPorPaginacao(
			@RequestParam(value="nome",defaultValue="") String nome,
			@RequestParam(value="categorias",defaultValue="") String categorias,
			@RequestParam(value="page",defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage",defaultValue="24")Integer linesPerPage,
		    @RequestParam(value="orderBy",defaultValue="nome") String orderBy,
		    @RequestParam(value="direction",defaultValue="ASC") String direction) {
		
		String nomeDecoded=URL.decodeParam(nome);
		List<Integer> ids=URL.decodeIntList(categorias);
		Page<Produto>produtosBuscadas=produtoService.search(nomeDecoded,ids,page, linesPerPage, direction, orderBy);
		Page<ProdutoDTO>listDTO=produtosBuscadas.map(obj->new ProdutoDTO(obj));

		return new ResponseEntity<Page<ProdutoDTO>> (listDTO,HttpStatus.OK);
			
	}
	
				
	}
	
	



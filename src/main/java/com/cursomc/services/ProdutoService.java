package com.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Produto;
import com.cursomc.repositories.CategoriaRepository;
import com.cursomc.repositories.ProdutoRepository;
import com.cursomc.services.exceptions.ObjectNotFoundException;



/**
 * @author emers
 *
 */
@Service
public class ProdutoService {
	
	//O Spring Injeta o objeto ProdutoRepository na classe Produto ProdutoService
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	/**
	 *Metodo que busca a Produto pelo sei id
	 * Ou se não encontrar o id, lança uma exceção
	 */
	public Produto buscaPorId(Integer id) {
		Optional<Produto> produto = produtoRepository.findById(id);

		return produto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não Encontrado! id:" + id + ", Tipo: " + Produto.class.getName()));

	}
	
	/**
	 *Metodo que busca os Produtos por nome e categoria 
	 *OBS: É passada uma lista com os ids da categoria. 1,2,3,4,5
	 */
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String direction,
			String orderBy) {

		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria>categorias=categoriaRepository.findAllById(ids);
		return produtoRepository.search(nome,categorias,pageRequest);

	}

}
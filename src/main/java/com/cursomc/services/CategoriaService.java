package com.cursomc.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Categoria;
import com.cursomc.dto.CategoriaDTO;
import com.cursomc.repositories.CategoriaRepository;
import com.cursomc.services.exceptions.ObjectNotFoundException;

/**
 * @author emers
 *
 */
@Service
public class CategoriaService {

	// O Spring Injeta o objeto categoriaRepository na classe Categoria
	// CategoriaService
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria cadastrar(Categoria categoria) {

		return categoriaRepository.save(categoria);

	}

	public Categoria alterar(Categoria categoria) {

		return categoriaRepository.save(categoria);

	}

	public void excluir(Categoria categoria) {

		categoriaRepository.delete(categoria);

	}

	public Collection<Categoria> buscaTodos() {

		return categoriaRepository.findAll();

	}
	
	/**
	 * Metodo que é chamado usando a CategoriaDTO busca a Categoria faz o mesmo que o buscaTodos()
	 * só que usa o List 
	 */
	public List<Categoria> findAll() {

		return categoriaRepository.findAll();

	}
	
	
	/**
	 * Metodo que Cria uma Categoria a partir do DTO
	 */
	public Categoria fromDTO(CategoriaDTO categoriaDTO ) {

		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());

	}
	
	
	/**
	 * Metodo que usa o Page do Spring data para fazer a paginação.
	 * Usando 4 parametros, que são a Pagina,Linhas por pagina,a Direção crescente ou decrescente e o tipo de ordenação.
	 */
	public Page<Categoria> findPage(Integer page,Integer linesPerPage,String direction,String orderBy) {
                                                               //Converte a String em uma Direction do SpringData//
     PageRequest pageRequest=new PageRequest(page,linesPerPage,Direction.valueOf(direction),orderBy);
     return categoriaRepository.findAll(pageRequest);
	}

	/**
	 * Metodo que busca a Categoria pelo sei id Ou se não encontrar o id, lança uma
	 * exceção
	 */
	public Categoria buscaPorId(Integer id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);

		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não Encontrado! id:" + id + ", Tipo: " + Categoria.class.getName()));

	}

}
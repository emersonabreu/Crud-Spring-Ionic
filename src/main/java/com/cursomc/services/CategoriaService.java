package com.cursomc.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Categoria;
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

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepository.save(categoria);

	}

	public Categoria alterar(Categoria categoria) {

		return categoriaRepository.save(categoria);

	}
	
	
	/** Altera com os tratamentos nos relacionamentos
	 * Recupera os dados do Categoria pelo id que veio no End Point Http, busca a Categoria no Banco com todos os 
	 * dados relacionados preenchidos, salva só os atributos da classe Categoria e insere os dados relacionados que já estavam.
	 */
	public Categoria update(Categoria categoria) {

		Categoria catEncontrado=buscaPorId(categoria.getId());
		
		updateData(catEncontrado,categoria);
		
		return categoriaRepository.save(catEncontrado);

	}
	
	/** Função que altera o nome daCategoria que veio do Banco
	 */
	private void updateData(Categoria catEncontrado,Categoria categoria) {
		catEncontrado.setNome(categoria.getNome());
		
		
		
	}
	
	

	public void excluir(Categoria categoria) {
		
		  try {
				categoriaRepository.delete(categoria);

			} catch (DataIntegrityViolationException exception) {
				
		throw new DataIntegrityViolationException("Não pode Excluir Pois existe Entidades Relacionadas",exception.getMostSpecificCause());
			}

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
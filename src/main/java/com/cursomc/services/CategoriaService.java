package com.cursomc.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Categoria;
import com.cursomc.repositories.CategoriaRepository;
import com.cursomc.services.exceptions.ObjectNotFoundException;

/**
 * @author emers
 *
 */
@Service
public class CategoriaService {
	
	
	
	//O Spring Injeta o objeto categoriaRepository na classe Categoria CategoriaService
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	
	public Categoria cadastrar(Categoria categoria) {
		return categoriaRepository.save(categoria);
		
		
			
		}
	
	
	public Collection<Categoria> buscaTodos() {
		
		
		return categoriaRepository.findAll();
			
		}
	

	/**
	 *Metodo que busca a Categoria pelo sei id
	 * Ou se não encontrar o id, lança uma exceção
	 */
public Categoria buscaPorId(Integer id) {
	Optional<Categoria> categoria=categoriaRepository.findById(id);
	
			return categoria.orElseThrow(()-> new ObjectNotFoundException(
				"Objeto não Encontrado! id:" + id + ", Tipo: " + Categoria.class.getName()));

	 
}
	

}
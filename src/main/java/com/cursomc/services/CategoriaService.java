package com.cursomc.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Categoria;
import com.cursomc.repositories.CategoriaRepository;

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


public Categoria buscaPorId(Integer id) {
	Optional<Categoria> categoria=categoriaRepository.findById(id);
	return categoria.orElse(null);
	 
}
	

}
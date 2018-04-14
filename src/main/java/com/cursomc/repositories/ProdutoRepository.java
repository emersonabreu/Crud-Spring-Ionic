package com.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Produto;
/** 
* Classe que realiza as operações no banco de dados já que é uma extenção da JPA
*  
*/
@Repository
public interface ProdutoRepository  extends JpaRepository <Produto, Integer> {
	
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN:categorias")
	Page<Produto> search(@Param("nome") String nome, @Param("categorias")List<Categoria>categorias,Pageable pageRequest ); 

	/** 
	* Metodo que faz a mesma consulta query 
	*/
	Page<Produto>findDistinctByNomeContainingAndCategoriasIn(@Param("nome") String nome, @Param("categorias")List<Categoria>categorias,Pageable pageRequest ); 
    

	}




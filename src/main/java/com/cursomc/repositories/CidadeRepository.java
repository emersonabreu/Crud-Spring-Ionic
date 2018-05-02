package com.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cursomc.domain.Cidade;
import java.util.List;

/** 
* Classe que realiza as operações no banco de dados já que é uma extenção da JPA
*  
*/
@Repository
public interface CidadeRepository  extends JpaRepository <Cidade, Integer> {

	/** 
	* Busca uma lista de Cidades pelo id do estado passado 
	*/
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Cidade obj WHERE obj.estado.id = :estadoId ORDER BY obj.nome")
	public List<Cidade> findCidades(@Param("estadoId") Integer estado_id);
}

package com.cursomc.repositories;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.cursomc.domain.Estado;
/** 
* Classe que realiza as operações no banco de dados já que é uma extenção da JPA
*  
*/
@Repository
public interface EstadoRepository  extends JpaRepository <Estado, Integer> {
	
	@Transactional(readOnly=true)
	public List<Estado> findAllByOrderByNome();

}

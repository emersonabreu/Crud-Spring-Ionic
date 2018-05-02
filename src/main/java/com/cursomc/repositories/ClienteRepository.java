package com.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.cursomc.domain.Categoria;
import com.cursomc.domain.Cliente;
/** 
* Classe que realiza as operações no banco de dados já que é uma extenção da JPA
*  
*/
@Repository
public interface ClienteRepository  extends JpaRepository <Cliente, Integer> {

	
	/** 
	*Acrescentando metodo pronto do Springdata na ClienteRepository
	*Ele busca o cliente pelo seu email: 
	*OBS:@Transactional(readOnly=true) Faz diminuir o locking no gerenciamento 
	*de transaçoes no Banco
	*/
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);
}

package com.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursomc.domain.Pedido;
/** 
* Classe que realiza as operações no banco de dados já que é uma extenção da JPA
*  
*/
@Repository
public interface PedidoRepository  extends JpaRepository <Pedido, Integer> {

}

package com.cursomc.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Pedido;
import com.cursomc.repositories.PedidoRepository;
import com.cursomc.services.exceptions.ObjectNotFoundException;

/**
 * @author emers
 *
 */
@Service
public class PedidoService {
	
	
	
	//O Spring Injeta o objeto pedidoRepository na classe Pedido PedidoService
	@Autowired
	private PedidoRepository pedidoRepository;
	
	
	public Pedido cadastrar(Pedido pedido) {
		return pedidoRepository.save(pedido);
		
		
			
		}
	
	
	public Collection<Pedido> buscaTodos() {
		
		
		return pedidoRepository.findAll();
			
		}
	

	/**
	 *Metodo que busca a Pedido pelo sei id
	 * Ou se não encontrar o id, lança uma exceção
	 */
public Pedido buscaPorId(Integer id) {
	Optional<Pedido> pedido=pedidoRepository.findById(id);
	
			return pedido.orElseThrow(()-> new ObjectNotFoundException(
				"Objeto não Encontrado! id:" + id + ", Tipo: " + Pedido.class.getName()));

	 
}
	

}
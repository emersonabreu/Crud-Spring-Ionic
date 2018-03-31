package com.cursomc.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Cliente;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.services.exceptions.ObjectNotFoundException;

/**
 * Classe de Servico que Salva os Objetos no Banco de Dados usando os metodos da JPA
 *
 */
@Service
public class ClienteService {
	
	
	
	//O Spring Injeta o objeto clienteRepository na classe Cliente ClienteService
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	public Cliente cadastrar(Cliente cliente) {
		return clienteRepository.save(cliente);
		
		
			
		}
	
	
	public Collection<Cliente> buscaTodos() {
		
		
		return clienteRepository.findAll();
			
		}
	

	/**
	 *Metodo que busca a Cliente pelo sei id
	 * Ou se não encontrar o id, lança uma exceção
	 */
public Cliente buscaPorId(Integer id) {
	Optional<Cliente> cliente=clienteRepository.findById(id);
	
			return cliente.orElseThrow(()-> new ObjectNotFoundException(
				"Objeto não Encontrado! id:" + id + ", Tipo: " + Cliente.class.getName()));

	 
}
	

}
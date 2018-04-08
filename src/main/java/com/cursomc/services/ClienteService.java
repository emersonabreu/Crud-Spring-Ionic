package com.cursomc.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Cidade;
import com.cursomc.domain.Cliente;
import com.cursomc.domain.Endereco;
import com.cursomc.domain.Estado;
import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.domain.Cliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.dto.ClienteNewDTO;
import com.cursomc.repositories.CidadeRepository;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.repositories.EnderecoRepository;
import com.cursomc.repositories.EstadoRepository;
import com.cursomc.services.exceptions.ObjectNotFoundException;

/**
 * Classe de Servico que Salva os Objetos no Banco  Dados usando os metodos da JPA
 *
 */
@Service
public class ClienteService {
	
	
	
	//O Spring Injeta o objeto clienteRepository na classe Cliente ClienteService
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	/**
	 * Metodo que busca a Cliente pelo sei id Ou se não encontrar o id, lança uma
	 * exceção
	 */
	public Cliente buscaPorId(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);

		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não Encontrado! id:" + id + ", Tipo: " + Cliente.class.getName()));

	}
	
	
	
	
	public Cliente insert(Cliente cliente) {
		
		cliente.setId(null);
		cliente=clienteRepository.save(cliente);
		
		return cliente;	
		}
	
	
	
	@Transactional
	public Cliente cadastrar(Cliente cliente) {
		
		cliente.setId(null);
		cliente=clienteRepository.save(cliente);
		
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;	
		}
	
	
		
	
	/** Altera com os tratamentos nos relacionamentos
	 * Recupera os dados do Cliente pelo id que veio no End Point Http, busca o Cliente no Banco com todos os 
	 * dados relacionados preenchidos, salva só os atributos da classe Cliente e insere os dados relacionados que já estavam.
	 *&&&&&&&&&&&&&&&&&&&&&EXEMPLO&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	 *& "id":       1,              & Repete   "id":     1,                 &
	 *& "nome": "Maria Silva",      & Salva    "nome":   "Souza Silva",     &
	 *&"email": "maria@gmail.com",  & Salva    "email":  "Souza@gmail.com", &
	 *&"cpfOuCnpj":"36378912377",   & Repete   "cpfOuCnpj":"36378912377",   &
	 *&"enderecos": [],             & Repete    "enderecos": [],            &
	 *&"telefones": []              & Repete    "telefones": []             &
	 *&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	 */
	public Cliente update(Cliente cliente) {

		Cliente cliEncontrado=buscaPorId(cliente.getId());
		
		updateData(cliEncontrado,cliente);
		
		return clienteRepository.save(cliEncontrado);

	}
	
	/** Função que altera o nome e o email no Cliente que veio do Banco
	 */
	private void updateData(Cliente cliEncontrado,Cliente cliente) {
		cliEncontrado.setNome(cliente.getNome());
		cliEncontrado.setEmail(cliente.getEmail());
		
		
	}
	
	
	
	
	
	
	
	public void excluir(Cliente cliente) {
		
        try {
        	clienteRepository.delete(cliente);
		} catch (DataIntegrityViolationException exception) {
			
			throw new DataIntegrityViolationException("Não pode Excluir Pois existe Entidades Relacionadas",exception.getMostSpecificCause());
		}
		

	}

	
	
	/**
	 * Metodo que é chamado usando a ClienteDTO busca o Cliente faz o mesmo que o buscaTodos()
	 * só que usa o List 
	 */
	public List<Cliente> findAll() {

		return clienteRepository.findAll();

	}
	
	
	/**
	 * Metodo que Cria um Cliente a partir do DTO
	 */
	public Cliente fromDTO(ClienteDTO clienteDTO ) {

		return new Cliente(clienteDTO.getId(),clienteDTO.getNome(), clienteDTO.getEmail(),null,null);

	}
	
	
	
	/** Insere um Cliente no Banco a partir da ClienteNewDTO
	 * Obs: Instancia o Cliente, A Cidade, O Endereco a Partir da ClienteNewDTO
	 * Sobrescreve o metodo fromDTO()
	 */
	public Cliente fromDTO(ClienteNewDTO clienteNewDTO ) {

	Cliente cliente=new Cliente(null,clienteNewDTO.getNome(),
			clienteNewDTO.getEmail(),clienteNewDTO.getCpfOuCnpj(),
			TipoCliente.toEnum(clienteNewDTO.getTipo()));
	
    
	Cidade cidade = new Cidade(clienteNewDTO.getCidadeId(), null, null);
	
	Endereco endereco = new Endereco(null, clienteNewDTO.getLogradouro(),clienteNewDTO.getNumero(),
			clienteNewDTO.getComplemento(),clienteNewDTO.getBairro(),clienteNewDTO.getCep(), cliente,cidade);
	
	cliente.getEnderecos().add(endereco);
	cliente.getTelefones().add(clienteNewDTO.getTelefone1());
	
	if (clienteNewDTO.getTelefone2()!=null) {
		cliente.getTelefones().add(clienteNewDTO.getTelefone2());
	}
	if (clienteNewDTO.getTelefone3()!=null) {
		cliente.getTelefones().add(clienteNewDTO.getTelefone3());
		
	}

	return cliente;
	
	}
	
	
	
	/**
	 * Metodo que usa o Page do Spring data para fazer a paginação.
	 * Usando 4 parametros, que são a Pagina,Linhas por pagina,a Direção crescente ou decrescente e o tipo de ordenação.
	 */
	public Page<Cliente> findPage(Integer page,Integer linesPerPage,String direction,String orderBy) {
                                                               //Converte a String em uma Direction do SpringData//
     PageRequest pageRequest=new PageRequest(page,linesPerPage,Direction.valueOf(direction),orderBy);
     return clienteRepository.findAll(pageRequest);
	}
	
	

	

}
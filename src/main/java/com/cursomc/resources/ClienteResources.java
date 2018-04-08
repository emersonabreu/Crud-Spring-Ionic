package com.cursomc.resources;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cursomc.domain.Cliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.dto.ClienteNewDTO;
import com.cursomc.services.ClienteService;

	/** 
	* Classe controladora que Faz as requisições para a Classe de Serviço que por sua 
	* vez requisita a Classe Repository que extende a JPARepository/DAO que manipula o Banco de Dados 
	*  
	*/

@RestController
@RequestMapping(value="/clientes")
public class ClienteResources {
	
	
	@Autowired
	private ClienteService clienteService;
	
	//End Points PRODUZ JSON
		/** End Point
		* Metodo que busca a cliente com todos os produtos relacionados a ela
		* Ou Lança uma exceção se não encontrar
		* Como não vai Consumir JSON não precisa Ler o @RequestBody do objeto
		*/
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Cliente cliente=clienteService.buscaPorId(id);
		return  ResponseEntity.ok().body(cliente);
	
		}
	
	// End Points CONSOME JSON
	/**
	 * Salva e Retorna o que Salvou Metodo que pega o objeto JSON e salva no model
	 * cliente. O @RequestBody lê o corpo do JSON e salva no objeto Após salvar,
	 * joga ele dentro de um Map/lista Depois faz o inverso através do
	 * ResponseEntity, devolvendo o Objeto para o browser consome e produz JSON
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/salvar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> cadastrar(@RequestBody Cliente cliente) {

		Cliente clienteCadastrada = clienteService.insert(cliente);

		return new ResponseEntity<>(clienteCadastrada, HttpStatus.CREATED);

	}
	
	// End Points CONSOME JSON
	/**
	 * Salvar o primeiro Cliente Usando a ClienteNewDTO
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/salvarOprimeiro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> cadastrar(@RequestBody ClienteNewDTO clienteNewDTO) {

		Cliente clienteCadastrada = clienteService.fromDTO(clienteNewDTO);
		clienteCadastrada = clienteService.cadastrar(clienteCadastrada);

		return new ResponseEntity<>(clienteCadastrada, HttpStatus.CREATED);

	}
	
	
	//End Points PRODUZ JSON
	/** 
	* Metodo que retorna uma lista de objetos no formato JSON para o browser.
	* Produz JSON 
	* Como não vai Consumir JSON não precisa Ler o @RequestBody do objeto
	*/
	@RequestMapping(method=RequestMethod.GET, value="/todas",
	produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Cliente>> buscarTodasClientes() {

		Collection<Cliente>clientesBuscados=clienteService.findAll();
		return new ResponseEntity<Collection<Cliente>> (clientesBuscados,HttpStatus.OK);
			
	}
	
	
	//End Points PRODUZ JSON 
			/** 
			* Metodo que traz uma lista de  todas as Clientes e seus Produtos
			* pega só as clientes joga em outra lista e a retorna.
			*  
			*/
		@RequestMapping(method=RequestMethod.GET, value="/dto",
				produces=MediaType.APPLICATION_JSON_VALUE)
				public ResponseEntity<List<ClienteDTO>> findAll() {
			
			        /**Lista os clientes com todos os objetos relacionados a ele
			        */
					List<Cliente>list=clienteService.findAll();
					
					/**Tranforma em uma outra lista que retorna só os atributos de Cliente
					 * usando o método da ClienteDTO
				     */
					List<ClienteDTO>listDTO=list.stream().map(obj->new ClienteDTO(obj)).collect(Collectors.toList());
					return new ResponseEntity<List<ClienteDTO>> (listDTO,HttpStatus.OK);
						
				}
		
		
		//
		/** 
		* End Points que faz a Paginação da Cliente
		*/
		@RequestMapping(method=RequestMethod.GET, value="/page",
		produces=MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Page<ClienteDTO>> buscaClientesPorPaginacao(
				@RequestParam(value="page",defaultValue="0") Integer page,
				@RequestParam(value="linesPerPage",defaultValue="24")Integer linesPerPage,
			    @RequestParam(value="direction",defaultValue="ASC") String direction,
			    @RequestParam(value="orderBy",defaultValue="nome") String orderBy) {

			Page<Cliente>clientesBuscadas=clienteService.findPage(page, linesPerPage, direction, orderBy);
			Page<ClienteDTO>listDTO=clientesBuscadas.map(obj->new ClienteDTO(obj));

			return new ResponseEntity<Page<ClienteDTO>> (listDTO,HttpStatus.OK);
				
		}
		
			
	
	//End Points CONSOME JSON
	/** Altera Cliente com Validação DTO
	 * 
	 */
@RequestMapping(method=RequestMethod.PUT, value="/alterarComValidacao",
		consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<?> alterarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
	Cliente clienteAlterado=clienteService.fromDTO(clienteDTO);
			
			
			return new ResponseEntity<>(clienteAlterado,HttpStatus.OK);			

}
	

	//End Points CONSOME JSON
			/** Altera Cliente 
			 * 
			 */
		@RequestMapping(method=RequestMethod.PUT, value="/alterar",
				consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
				public ResponseEntity<Cliente> alterarCliente(@RequestBody Cliente cliente) {
			Cliente clienteAlterado=clienteService.update(cliente);
					
					
					return new ResponseEntity<Cliente>(clienteAlterado,HttpStatus.OK);
						
				}
		
		
		/** Não PRODUZ NEM CONSOME JSON
		 * Só exclui um objeto pelo seu id passado como parametro na url
		 */
		@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
		public ResponseEntity<Cliente> excluirCliente(@PathVariable Integer id) {
			
			Cliente clienteEncontrado=clienteService.buscaPorId(id);
			 
			 if(clienteEncontrado==null) {
				 
				 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			 }
			 
			 clienteService.excluir(clienteEncontrado);
			 return new ResponseEntity<>(HttpStatus.OK);

				 
		}

	}
		




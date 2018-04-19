package com.cursomc.resources;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cursomc.domain.Pedido;
import com.cursomc.services.PedidoService;

/**
 * Classe controladora que Faz as requisições para a Classe de Serviço que por
 * sua vez requisita a Classe Repository que extende a JPARepository/DAO que
 * manipula o Banco de Dados
 * 
 */

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResources {

	@Autowired
	private PedidoService pedidoService;

	// End Points PRODUZ JSON
	/**
	 * End Point Metodo que busca a pedido com todos os produtos relacionados a ela
	 * Ou Lança uma exceção se não encontrar Como não vai Consumir JSON não precisa
	 * Ler o @RequestBody do objeto
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Pedido pedido = pedidoService.buscaPorId(id);
		return ResponseEntity.ok().body(pedido);

	}

	// End Points PRODUZ JSON
	/**
	 * Metodo que retorna uma lista de objetos no formato JSON para o browser.
	 * Produz JSON Como não vai Consumir JSON não precisa Ler o @RequestBody do
	 * objeto
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/todas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Pedido>> buscarTodasPedidos() {

		Collection<Pedido> pedidosBuscados = pedidoService.buscaTodos();
		return new ResponseEntity<Collection<Pedido>>(pedidosBuscados, HttpStatus.OK);

	}

	// End Points CONSOME JSON
	/**
	 * Salva e Retorna o que Salvou Metodo que pega o objeto JSON e salva no model
	 * cliente. O @RequestBody lê o corpo do JSON e salva no objeto Após salvar,
	 * joga ele dentro de um Map/lista Depois faz o inverso através do
	 * ResponseEntity, devolvendo o Objeto para o browser consome e produz JSON
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/salvar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> cadastrar(@RequestBody Pedido pedido) {
		Pedido pedidoCadastrada = pedidoService.insert(pedido);

		return new ResponseEntity<>(pedidoCadastrada, HttpStatus.CREATED);

	}

	

}

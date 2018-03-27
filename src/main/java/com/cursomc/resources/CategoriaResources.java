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

import com.cursomc.domain.Categoria;
import com.cursomc.services.CategoriaService;

	/** 
	* Classe controladora que Faz as requisições para a Classe de Serviço que por sua 
	* vez requisita a Classe Repository que extende a JPARepository/DAO que manipula o Banco de Dados 
	*  
	*/

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResources {
	
	
	@Autowired
	private CategoriaService categoriaService;
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Categoria categoria=categoriaService.buscaPorId(id);
		return  ResponseEntity.ok().body(categoria);
	
		}
	
	
	//End Points PRODUZ JSON
	/** 
	* Metodo que retorna uma lista de objetos no formato JSON para o browser.
	* Produz JSON 
	* Como não vai Consumir JSON não precisa Ler o @RequestBody do objeto
	*/
	@RequestMapping(method=RequestMethod.GET, value="/todas",
	produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Categoria>> buscarTodasCategorias() {

		Collection<Categoria>categoriasBuscados=categoriaService.buscaTodos();
		return new ResponseEntity<Collection<Categoria>> (categoriasBuscados,HttpStatus.OK);
			
	}
	
	//End Points CONSOME JSON
	/** Salva e Retorna o que Salvou
	 * Metodo que pega o objeto JSON e salva no model cliente.
	 * O @RequestBody lê o corpo do JSON e salva no objeto
	 * Após salvar, joga ele dentro de um Map/lista
	 * Depois faz o inverso através do ResponseEntity, devolvendo o Objeto para o browser 
	 * consome e produz JSON
	 */
	@RequestMapping(method=RequestMethod.POST, value="/salvar",
	consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> cadastrar(@RequestBody Categoria categoria) {
		Categoria categoriaCadastrada = categoriaService.cadastrar(categoria);
		
		return new ResponseEntity<>(categoriaCadastrada,HttpStatus.CREATED);
			
	}

}


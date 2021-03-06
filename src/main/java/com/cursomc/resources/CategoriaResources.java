package com.cursomc.resources;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cursomc.domain.Categoria;
import com.cursomc.dto.CategoriaDTO;
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
	
	//End Points PRODUZ JSON
		/** End Point
		* Metodo que busca a categoria com todos os produtos relacionados a ela
		* Ou Lança uma exceção se não encontrar
		* Como não vai Consumir JSON não precisa Ler o @RequestBody do objeto
		*/
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
	
	
	//End Points PRODUZ JSON
		/** 
		* Metodo que traz uma lista de  todas as Categorias e seus Produtos
		* pega só as categorias joga em outra lista e a retorna.
		*  
		*/
	@RequestMapping(method=RequestMethod.GET, value="/dto",
			produces=MediaType.APPLICATION_JSON_VALUE)
			public ResponseEntity<List<CategoriaDTO>> findAll() {
		
		        /**Lista as categorias com todos os objetos relacionados a ela
		        */
				List<Categoria>list=categoriaService.findAll();
				
				/**Tranforma em uma outra lista que retorna só os atributos de Categoria
				 * usando o método da CategoriaDTO
			     */
				List<CategoriaDTO>listDTO=list.stream().map(obj->new CategoriaDTO(obj)).collect(Collectors.toList());
				return new ResponseEntity<List<CategoriaDTO>> (listDTO,HttpStatus.OK);
					
			}
	
	
	
	
	
	
		/** 
		* End Points que faz a Paginação da Categoria
		*/
		@RequestMapping(method=RequestMethod.GET, value="/page",
		produces=MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Page<CategoriaDTO>> buscaCategoriasPorPaginacao(
				@RequestParam(value="page",defaultValue="0") Integer page,
				@RequestParam(value="linesPerPage",defaultValue="24")Integer linesPerPage,
			    @RequestParam(value="direction",defaultValue="ASC") String direction,
			    @RequestParam(value="orderBy",defaultValue="nome") String orderBy) {

			Page<Categoria>categoriasBuscadas=categoriaService.findPage(page, linesPerPage, direction, orderBy);
			Page<CategoriaDTO>listDTO=categoriasBuscadas.map(obj->new CategoriaDTO(obj));

			return new ResponseEntity<Page<CategoriaDTO>> (listDTO,HttpStatus.OK);
				
		}

	
	
	//End Points CONSOME JSON
		/** Validando dados com DTO
		 * Salva a Categoria com as condicoes definidas na categoriaDTO
		 * @NotEmpty(message="Campo Obrigatorio")
		 * @Length(min=5,max=80)
		*/
		@PreAuthorize("hasAnyRole('ADMIN')")
		@RequestMapping(method=RequestMethod.POST)
		public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto) {
			Categoria obj = categoriaService.fromDTO(objDto);
			obj = categoriaService.insert(obj);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
			return ResponseEntity.created(uri).build();
		}
		

		//End Points CONSOME JSON
				/** Altera Categoria com Validação DTO
				 * Só o usuario com perfil de admin pode Alterar Categoria com Validação DTO 
				 * @PreAuthorize("hasAnyRole('ADMIN')")
				 */
		    @PreAuthorize("hasAnyRole('ADMIN')")
			@RequestMapping(method=RequestMethod.PUT, value="/alterarComValidacao",
					consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
					public ResponseEntity<?> alterarCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
				Categoria categoriaAlterado=categoriaService.fromDTO(categoriaDTO);
						
						
						return new ResponseEntity<>(categoriaAlterado,HttpStatus.OK);			
	
			}
			
			
	
	//End Points CONSOME JSON
		/** Altera Categoria com os tratamentos nos relacionamentos
		 * usando o metodo update(Categoria categoria):
		 * Só o usuario com perfil de admin tem permissão de alterar @PreAuthorize("hasAnyRole('ADMIN')")
		 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.PUT, value="/alterar",
			consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
			public ResponseEntity<Categoria> alterarCliente(@RequestBody Categoria categoria) {
		Categoria categoriaAlterado=categoriaService.update(categoria);;
				
				
				return new ResponseEntity<Categoria>(categoriaAlterado,HttpStatus.OK);
					
			}
	
	
	/** Não PRODUZ NEM CONSOME JSON
	 * Só exclui um objeto pelo seu id passado como parametro na url
	 * Só o usuario com perfil de admin tem permissão deletar @PreAuthorize("hasAnyRole('ADMIN')")
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity<Categoria> excluirCategoria(@PathVariable Integer id) {
		
		Categoria categoriaEncontrado=categoriaService.buscaPorId(id);
		 
		 if(categoriaEncontrado==null) {
			 
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
		 
		 categoriaService.excluir(categoriaEncontrado);
		 return new ResponseEntity<>(HttpStatus.OK);

			 
	}

}


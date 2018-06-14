package com.cursomc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cursomc.domain.Cidade;
import com.cursomc.domain.Cliente;
import com.cursomc.domain.Endereco;
import com.cursomc.domain.enums.Perfil;
import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.dto.ClienteNewDTO;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.repositories.EnderecoRepository;
import com.cursomc.security.UserSS;
import com.cursomc.services.exceptions.AuthorizationException;
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
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private S3Service s3Service;
	
	
	@Autowired
	private ImageService imageService;
	/**
	 * Definindo o tamanho da imagem e o prefixo cp da imagem
	 */
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	/**
	 * Metodo que busca a Cliente pelo sei id Ou se não encontrar o id, lança uma
	 * exceção
	 */
	public Cliente buscaPorId(Integer id) {	
		
		/**Primeiro verifica as permissoes do usuário
		 * se não tiver permissão ou se não for admin ou se o id do cliente for diferente ele 
		 * não poderá acessar os dados de outro Cliente
		 */
		UserSS user = UserService.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Cliente> cliente = clienteRepository.findById(id);
        
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não Encontrado! id:" + id + ", Tipo: " + Cliente.class.getName()));

	}
	
	
	/**
	 * Metodo que busca a Cliente pelo seu email Ou se não encontrar o email, lança uma
	 * exceção
	 */
	public Cliente findByEmail(String email) {	
		
		/**Primeiro verifica as permissoes do usuário
		 * se não tiver permissão ou se não for admin ou se o email do cliente for diferente ele 
		 * não poderá acessar os dados de outro Cliente
		 */
		UserSS user = UserService.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
		Cliente cliente = clienteRepository.findByEmail(email);
        
		return cliente;

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
	
	
	
	
	/** Altera com validação
	 */
	public Cliente update(ClienteDTO clienteDTO) {

		Cliente cliEncontrado=buscaPorId(clienteDTO.getId());
		
		updateData(cliEncontrado,clienteDTO);
		
		return clienteRepository.save(cliEncontrado);

	}
	
	/** Função que altera o nome e o email no Cliente que veio do Banco
	 */
	private void updateData(Cliente cliEncontrado,ClienteDTO clienteDTO) {
		cliEncontrado.setNome(clienteDTO.getNome());
		cliEncontrado.setEmail(clienteDTO.getEmail());
		

		
	}
	
	
	
	
	public void excluir(Cliente cliente) {
		
        try {
        	clienteRepository.delete(cliente);
		} catch (DataIntegrityViolationException exception) {
			
			throw new DataIntegrityViolationException("Não pode Excluir Pois O Cliente Tem Pedidos Relacionados",exception.getMostSpecificCause());
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

		return new Cliente(clienteDTO.getId(),clienteDTO.getNome(), clienteDTO.getEmail(),null,null,null);

	}
	
	
	
	/** Insere um Cliente no Banco a partir da ClienteNewDTO
	 * Obs: Instancia o Cliente, A Cidade, O Endereco a Partir da ClienteNewDTO
	 * Sobrescreve o metodo fromDTO()
	 * OBS:A ClienteNewDTO chama a interface ClienteInsert que Implementa a 
	 * ClienteInsertValidator
	 */
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),TipoCliente.toEnum(objDto.getTipo()),pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
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
	
	/**
	 * Metodo que pega a imagem se tiver autenticado 
	 * recorta redimenciona e salva =cp.1.jpg
	 */
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
	
	
	

}
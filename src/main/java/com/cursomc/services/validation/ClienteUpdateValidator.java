package com.cursomc.services.validation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;
import com.cursomc.domain.Cliente;
import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.dto.ClienteNewDTO;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.resources.exception.FieldMessage;
import com.cursomc.services.validation.utils.BR;
/** 
* Classe que faz as validações dos dados dos clientes
*  
*/

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	//Usado pra pegar dados da url
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		//Pega os atributos da url e joga na Map de Strig: Obs tem de fazer o casting de objeto
		Map<String, String> map=(Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId;
		uriId=Integer.parseInt(map.get("id"));
		
		
		List<FieldMessage> list = new ArrayList<>();
		
		
		Cliente aux=clienteRepository.findByEmail(objDto.getEmail());
		
		//Se o email for igual e o id diferente não pode atualizar
        if (aux != null && !aux.getId().equals(uriId)){
			list.add(new FieldMessage("Email", "Email Já Existente"));

        }

        
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}


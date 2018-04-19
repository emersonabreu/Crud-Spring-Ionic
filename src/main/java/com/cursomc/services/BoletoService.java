package com.cursomc.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.PagamentoComBoleto;
import com.cursomc.domain.Pedido;
import com.cursomc.domain.Produto;
import com.cursomc.repositories.CategoriaRepository;
import com.cursomc.repositories.ProdutoRepository;
import com.cursomc.services.exceptions.ObjectNotFoundException;



/**
 *Classe que simula o webservice que gera o boleto
 *
 */
@Service
public class BoletoService {
	
	public void preencherPagamentoComBoleto(
			PagamentoComBoleto boleto, Date instanteDoPedido) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(instanteDoPedido);
		calendar.add(calendar.DAY_OF_MONTH,7);
		boleto.setDataVencimento(calendar.getTime());
			
		}
}
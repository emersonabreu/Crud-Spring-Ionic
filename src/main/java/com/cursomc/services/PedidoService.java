package com.cursomc.services;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.domain.ItemPedido;
import com.cursomc.domain.PagamentoComBoleto;
import com.cursomc.domain.Pedido;
import com.cursomc.domain.enums.EstadoPagamento;
import com.cursomc.repositories.ItemPedidoRepository;
import com.cursomc.repositories.PagamentoRepository;
import com.cursomc.repositories.PedidoRepository;
import com.cursomc.repositories.ProdutoRepository;
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
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;	
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido cadastrar(Pedido pedido) {
		return pedidoRepository.save(pedido);
		
		
			
		}
	
	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto boleto=(PagamentoComBoleto )pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(boleto,pedido.getInstante());
		}
		
		pedido=pedidoRepository.save(pedido);
		
		pagamentoRepository.save(pedido.getPagamento());
		
		for (ItemPedido ip : pedido.getItens()) {
			
	  ip.setDesconto(0.0);
	  ip.setPreco(produtoService.buscaPorId(ip.getProduto().getId()).getPreco());
	  ip.setPedido(pedido);
		}
		
		itemPedidoRepository.saveAll(pedido.getItens());
		return pedido;
		
		
			
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
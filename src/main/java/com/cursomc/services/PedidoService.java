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
import com.cursomc.repositories.ClienteRepository;
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

	// O Spring Injeta o objeto pedidoRepository na classe Pedido PedidoService
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

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;

	public Pedido cadastrar(Pedido pedido) {
		return pedidoRepository.save(pedido);

	}

	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		// Como veio só o id do Cliente, ele busca todos os dados desse cliente
		// no banco e seta no Pedido
		pedido.setCliente(clienteService.buscaPorId(pedido.getCliente().getId()));
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);

		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto boleto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(boleto, pedido.getInstante());
		}

		pedido = pedidoRepository.save(pedido);

		pagamentoRepository.save(pedido.getPagamento());

		for (ItemPedido ip : pedido.getItens()) {

			ip.setDesconto(0.0);
			// Como veio o id do Produto no na lista de ItemPedido
			// , ele busca todos os dados desse Produto no banco e seta no ItemPedido
			ip.setProduto(produtoService.buscaPorId(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(pedido);
		}

		itemPedidoRepository.saveAll(pedido.getItens());
		emailService.sendOrderConfirmationEmail(pedido);
		return pedido;

	}

	public Collection<Pedido> buscaTodos() {

		return pedidoRepository.findAll();

	}

	/**
	 * Metodo que busca a Pedido pelo sei id Ou se não encontrar o id, lança uma
	 * exceção
	 */
	public Pedido buscaPorId(Integer id) {
		Optional<Pedido> pedido = pedidoRepository.findById(id);

		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não Encontrado! id:" + id + ", Tipo: " + Pedido.class.getName()));

	}

}
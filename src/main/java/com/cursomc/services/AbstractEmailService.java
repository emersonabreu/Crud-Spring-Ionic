package com.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	/** Variável que armazena o valor da default.sender=emersondamiaosouza@gmail.com na 
	 * application.properties que será o destinatario do email no caso emersondamiaosouza@gmail.com
	 */
	@Value("${default.sender}")
	private String sender;
	
	/** Metodo que cria uma variavel sm, que armazena os dados do email
	 * e do pedido que foram criados no metodo prepareSimpleMailMessageFromPedido(Pedido obj)
	 */
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}
	

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
	
	
}

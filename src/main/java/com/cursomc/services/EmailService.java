package com.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.cursomc.domain.Pedido;

/**  Interface que contem os metodos que vão ser implementados na AbstractEmailService 
 */
public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	
}
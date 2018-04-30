package com.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.cursomc.domain.Cliente;
import com.cursomc.domain.Pedido;

/**  Interface que contem os metodos que vão ser implementados na AbstractEmailService 
 */
public interface EmailService {
	
	
	/** Versão de Texto puro 
	 */
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	
	
	/** Versão de confirmação com Html e Tymeleaf
	 */
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	void sendHtmlEmail(MimeMessage msg);
	
	
	/** Envia um Email Confirmando a nova Senha se o usuario tiver esquecido a senha
	 */
	void sendNewPasswordEmail(Cliente cliente, String newPass);
	
	
}
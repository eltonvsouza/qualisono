package br.com.util;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
	 
@Component
@Service("mail")
public class Mail {
	@Autowired
	private MailSender mailSender;
//	@Autowired
//	private SimpleMailMessage alertMailMessage;
	@Autowired
	private JavaMailSenderImpl javaMailSenderImpl;
	
	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
//
//	public SimpleMailMessage getAlertMailMessage() {
//		return alertMailMessage;
//	}
//
//	public void setAlertMailMessage(SimpleMailMessage alertMailMessage) {
//		this.alertMailMessage = alertMailMessage;
//	}
//
	public JavaMailSenderImpl getJavaMailSenderImpl() {
		return javaMailSenderImpl;
	}

	public void setJavaMailSenderImpl(JavaMailSenderImpl javaMailSenderImpl) {
		this.javaMailSenderImpl = javaMailSenderImpl;
	}

//	public void configMail(){
//		Properties javaMailProperties = new Properties();
//		javaMailProperties.setProperty("host", "smtp.gmail.com");
//		javaMailProperties.setProperty("port", "465");
//		javaMailProperties.setProperty("protocol", "smtps");
//		javaMailProperties.setProperty("username", "qualisono@gmail.com.br");
//		javaMailProperties.setProperty("password", "q123456s");
//		javaMailProperties.setProperty("mail.smtps.auth", "true");
//		javaMailProperties.setProperty("mail.smtps.starttls.enable", "true");
//		javaMailProperties.setProperty("mail.smtps.debug", "true");
//		javaMailSenderImpl.setJavaMailProperties(javaMailProperties);
//	}

	public void sendMail(String from, String to, String subject, String body) {
//		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
//		Properties javaMailProperties = new Properties();
//		javaMailProperties.put("host", "smtp.gmail.com");
//		javaMailProperties.put("port", "465");
//		javaMailProperties.put("protocol", "smtps");
//		javaMailProperties.put("username", "qualisono@gmail.com.br");
//		javaMailProperties.put("password", "q123456s");
//		javaMailProperties.put("mail.smtps.auth", "true");
//		javaMailProperties.put("mail.smtps.starttls.enable", "true");
//		javaMailProperties.put("mail.smtps.debug", "true");
//		javaMailProperties.setProperty("mail.transport.protocol", "smtp");
//		javaMailProperties.setProperty("mail.smtp.auth", "true");
//		javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
//		javaMailProperties.setProperty("mail.debug", "true");
//		javaMailProperties.put("javaMailProperties", javaMailProperties);
//		javaMailSenderImpl.setJavaMailProperties(javaMailProperties);
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
		System.out.println("ENVIANDO...");
	}

//	public void sendAlertMail(String alert, String from, String to, String subject) {
//	public void sendAlertMail(String alert) {
//		SimpleMailMessage mailMessage = new SimpleMailMessage(alertMailMessage);
////		mailMessage.setFrom(from);
////		mailMessage.setTo(to);
////		mailMessage.setSubject(subject);
//		mailMessage.setText(alert);
//		mailSender.send(mailMessage);
//	}
}
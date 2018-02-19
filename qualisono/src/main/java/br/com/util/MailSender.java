package br.com.util;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class MailSender extends JavaMailSenderImpl{
//	<property name="host" value="smtp.gmail.com"/>
//    <property name="port" value="25"/>
//    <property name="username" value="qualisono@gmail.com"/>
//    <property name="password" value="q123456s"/>
//    <property name="javaMailProperties">
//	    <props>
//	        <prop key="mail.transport.protocol">smtp</prop>
//            <prop key="mail.smtp.auth">true</prop>
//            <prop key="mail.smtp.starttls.enable">true</prop>
//            <prop key="mail.debug">true</prop>
//		</props>
//	</property>
//<!--	<property name="proxySet" value="true"/>-->
//<!--	<property name="socksProxyHost" value="10.39.0.112"/>-->
//<!--	<property name="socksProxyPort" value="3128"/>-->
	
	public MailSender() {
		super();
		setHost("smtp.gmail.com");
		setPort(25);
		setUsername("qualisono@gmail.com");
		setPassword("q123456s");
		getJavaMailProperties().setProperty("mail.transport.protocol", "smtp");
		getJavaMailProperties().setProperty("mail.smtp.auth", "true");
		getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
		getJavaMailProperties().setProperty("mail.debug", "true");
		getJavaMailProperties().setProperty("proxySet", "true");
		getJavaMailProperties().setProperty("ProxyHost", "10.39.0.112");
		getJavaMailProperties().setProperty("ProxyPort", "3128");
//		getJavaMailProperties().put("proxySet", "true");
//		getJavaMailProperties().put("ProxyHost", "10.39.0.112");
//		getJavaMailProperties().put("ProxyPort", "3128");
		
//		Properties javaMailProperties = new Properties();
//		javaMailProperties.setProperty("mail.transport.protocol", "smtp");
//		javaMailProperties.setProperty("mail.smtp.auth", "true");
//		javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
//		javaMailProperties.setProperty("mail.debug", "true");
//		javaMailProperties.put("proxySet", "true");
//		javaMailProperties.put("socksProxyHost", "10.39.0.112");
//		javaMailProperties.put("socksProxyPort", "3128");
//		javaMailProperties.put("javaMailProperties", javaMailProperties);
//		setJavaMailProperties(javaMailProperties);
		
	}
	 
}

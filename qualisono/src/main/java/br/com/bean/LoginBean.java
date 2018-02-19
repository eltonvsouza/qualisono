package br.com.bean;

import java.security.Security;

import javax.mail.MessagingException;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import br.com.model.Usuario;
import br.com.util.Mail;
import br.com.util.ScheduledJob;

@Component("loginBean")
@Scope("session")
public class LoginBean {
	private Usuario usuario;
	
	public LoginBean() {
        usuario = new Usuario();
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext){
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication){
                usuario.setUsuario(((User)authentication.getPrincipal()).getUsername());
            }
        }
        ScheduledJob job = new ScheduledJob();
        job.enviaEmail();
    }

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}

package br.com.util;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.bean.LoginBean;
import br.com.dao.UsuarioDAOImpl;
import br.com.dao.utility.HibernateUtility;
import br.com.model.Unidade;
import br.com.model.Usuario;

@Component
@Scope("session")
public class SistemaValidator {
	private String disabled = "false";
	private LoginBean loginBean;
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	private Unidade unidade;
	private Date today = new Date();

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
//		this.disabled = disabled;
		if (unidade.getValidade().before(today)){
			disabled = "false";
		}else{
			disabled = "true";
		}
	}
	
	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public UsuarioDAOImpl<Usuario> getUsuarioDAOImpl() {
		return usuarioDAOImpl;
	}

	public void setUsuarioDAOImpl(UsuarioDAOImpl<Usuario> usuarioDAOImpl) {
		this.usuarioDAOImpl = usuarioDAOImpl;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = carregar(usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario()).getUnidade().getCodigo());
	}

	public Unidade carregar(Integer codigo) {
		Session session = HibernateUtility.getSession();
		Unidade unidade = null;
		try {
			Query select = session
					.createQuery("SELECT c FROM " + Unidade.class.getName() + " c WHERE c.codigo = :codigo");
			select.setParameter("codigo", codigo);
			unidade = (Unidade) select.uniqueResult();
		} catch (HibernateException he) {
			he.printStackTrace();
		} finally {
			session.close();
			session.getSessionFactory().close();
			System.gc();
		}
		return unidade;
	}
	
	public void setar(){
		if (unidade.getValidade().before(today)){
			disabled = "false";
		}else{
			disabled = "true";
		}
	}
	
}

package br.com.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.dao.TipoFuncionarioDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.TipoFuncionario;
import br.com.model.Usuario;

@Component
@Scope("session")
public class TipoFuncionarioBean {
	private TipoFuncionario tipoFuncionario;
	private LoginBean loginBean;
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	private TipoFuncionarioDAOImpl<TipoFuncionario> tipoFuncionarioDAOImpl;
	private Collection<TipoFuncionario> tipoFuncionarios;
	private List<SelectItem> listaTipoFuncionario;
	
	public TipoFuncionario getTipoFuncionario() {
		return tipoFuncionario;
	}

	public void setTipoFuncionario(TipoFuncionario tipoFuncionario) {
		this.tipoFuncionario = tipoFuncionario;
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

	public TipoFuncionarioDAOImpl<TipoFuncionario> getTipoFuncionarioDAOImpl() {
		return tipoFuncionarioDAOImpl;
	}

	public void setTipoFuncionarioDAOImpl(TipoFuncionarioDAOImpl<TipoFuncionario> tipoFuncionarioDAOImpl) {
		this.tipoFuncionarioDAOImpl = tipoFuncionarioDAOImpl;
	}
	
	public Collection<TipoFuncionario> getTipoFuncionarios() throws Exception{
		tipoFuncionarios = tipoFuncionarioDAOImpl.listar(usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario()).getUnidade());
		return tipoFuncionarios;
	}
	
	public void setTipoFuncionarios(Collection<TipoFuncionario> tipoFuncionarios) {
		this.tipoFuncionarios = tipoFuncionarios;
	}
	
	public List<SelectItem> getListaTipoFuncionario() throws Exception {
		listaTipoFuncionario = new ArrayList<SelectItem>();
		TipoFuncionarioDAOImpl<TipoFuncionario> tipoFuncionarioDAOImpl = new TipoFuncionarioDAOImpl<TipoFuncionario>();
		Collection<TipoFuncionario> tipoFuncionario = tipoFuncionarioDAOImpl.listar(usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario()).getUnidade());
		for (TipoFuncionario c : tipoFuncionario) {
			listaTipoFuncionario.add(new SelectItem(c, c.getDescricao()));
		}
		return listaTipoFuncionario;
	}
}

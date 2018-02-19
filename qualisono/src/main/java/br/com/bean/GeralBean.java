package br.com.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.dao.GeralDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Geral;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
public class GeralBean {
	private Geral geral;
	private LoginBean loginBean;
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	@Autowired
	private GeralDAOImpl<Geral> geralDAOImpl;
	private Collection<Geral> gerais;
	private List<SelectItem> listaGeral;
	private List<SelectItem> listaGeralGrupo;
	
	public Geral getGeral() {
		return this.geral;
	}

	public void setGeral(Geral geral) {
		this.geral = geral;
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

	public GeralDAOImpl<Geral> getGeralDAOImpl() {
		return geralDAOImpl;
	}

	public void setGeralDAOImpl(GeralDAOImpl<Geral> geralDAOImpl) {
		this.geralDAOImpl = geralDAOImpl;
	}
	
	public Unidade getUnidade() {
		return usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario()).getUnidade();
	}
	
	public Collection<Geral> getGerais() throws Exception{
		gerais = geralDAOImpl.listar(getUnidade());
		return gerais;
	}
	
	public void setGerais(Collection<Geral> gerais) {
		this.gerais = gerais;
	}
	
	public List<SelectItem> getListaGeral() throws Exception {
		listaGeral = new ArrayList<SelectItem>();
		GeralDAOImpl<Geral> geralDAOImpl = new GeralDAOImpl<Geral>();
		Collection<Geral> geral = geralDAOImpl.listar(getUnidade());
		for (Geral c : geral) {
			listaGeral.add(new SelectItem(c.getChave(), c.getValor()));
		}
		return listaGeral;
	}
	
	public List<SelectItem> getListaGeralGrupo(String grupo) throws Exception {
		listaGeralGrupo = new ArrayList<SelectItem>();
		GeralDAOImpl<Geral> geralDAOImpl = new GeralDAOImpl<Geral>();
		Collection<Geral> geral = geralDAOImpl.listarGrupo(getUnidade(), grupo);
		for (Geral c : geral) {
			listaGeralGrupo.add(new SelectItem(c.getChave(), c.getValor()));
		}
		return listaGeralGrupo;
	}
	
	public void relatorioGeral() throws Exception {
		GenericRelatorio relatorio = new GenericRelatorio();
		Map parametro = new HashMap();
		try {
			relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioGeral.jasper", "salvarPdf", parametro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

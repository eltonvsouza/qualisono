package br.com.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.dao.TransportadoraDAOImpl;
import br.com.dao.UfDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Transportadora;
import br.com.model.Uf;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
public class TransportadoraBean {

	private Transportadora transportadora;
	private LoginBean loginBean;
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	@Autowired
	private TransportadoraDAOImpl<Transportadora> transportadoraDAOImpl;
	private UfDAOImpl<Uf> ufDAOImpl;
	private Collection<Transportadora> transportadoras;
	private List<SelectItem> listaTransportadora;
	private Integer idBusca;
	private String mensagem;
	private boolean disableEndereco;

	public Transportadora getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(Transportadora transportadora) {
		this.transportadora = transportadora;
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

	public TransportadoraDAOImpl<Transportadora> getTransportadoraDAOImpl() {
		return transportadoraDAOImpl;
	}

	public void setTransportadoraDAOImpl(TransportadoraDAOImpl<Transportadora> transportadoraDAOImpl) {
		this.transportadoraDAOImpl = transportadoraDAOImpl;
	}


	public UfDAOImpl<Uf> getUfDAOImpl() {
		return ufDAOImpl;
	}

	public void setUfDAOImpl(UfDAOImpl<Uf> ufDAOImpl) {
		this.ufDAOImpl = ufDAOImpl;
	}

	public Collection<Transportadora> getTransportadoras() throws Exception {
		transportadoras = transportadoraDAOImpl.listar(getUnidade());
		return transportadoras;
	}

	public void setTransportadoras(Collection<Transportadora> transportadoras) {
		this.transportadoras = transportadoras;
	}

	public List<SelectItem> getListaTransportadora() throws Exception {
		listaTransportadora = new ArrayList<SelectItem>();
		TransportadoraDAOImpl<Transportadora> transportadoraDAOImpl = new TransportadoraDAOImpl<Transportadora>();
		Collection<Transportadora> transportadora = transportadoraDAOImpl.listar(getUnidade());
		for (Transportadora c : transportadora) {
			listaTransportadora.add(new SelectItem(c, c.getNome()));
		}
		return listaTransportadora;
	}
	
	public List<Transportadora> autocompleteTransportadora(Object event) throws Exception{
		String cod = (String) event;
		ArrayList<Transportadora>retorno = new ArrayList<Transportadora>();
		TransportadoraDAOImpl<Transportadora> transportadoraDAOImpl = new TransportadoraDAOImpl<Transportadora>();
		Collection<Transportadora> transportadora = transportadoraDAOImpl.listar(getUnidade());
		for (Transportadora c : transportadora) {
			if((c.getCodigo() != null && String.valueOf(c.getCodigo()).indexOf(cod.toLowerCase()) == 0) || "".equals(cod)){
				retorno.add(c);
			}
		}
		return retorno;
	}
	
	public Integer getIdBusca() {
		return idBusca;
	}

	public void setIdBusca(Integer idBusca) {
		this.idBusca = idBusca;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}


	public boolean isDisableEndereco() {
		return disableEndereco;
	}

	public void setDisableEndereco(boolean disableEndereco) {
		this.disableEndereco = disableEndereco;
	}

	public Unidade getUnidade() {
		return usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario()).getUnidade();
	}
	
	public String carregarCadastrar() {
		limpar();
		return "/tela/transportadora/cadastrarTransportadora";
	}

	public String carregarAlterar() {
		transportadora = transportadoraDAOImpl.carregar(idBusca);
		return "/tela/transportadora/alterarTransportadora";
	}

	public String cadastrar() {
		transportadora.setUnidade(getUnidade());
		if(valida()){
			transportadoraDAOImpl.salvar(transportadora);
			limpar();
		}
		return "/tela/transportadora/cadastrarTransportadora";
	}

	public String alterar() {
		if(valida()){
			transportadoraDAOImpl.salvarOuAtualizar(transportadora);
			return "/tela/transportadora/listarTransportadora";
		}else{
			return "/tela/transportadora/alterarTransportadora";
		}
	}

	public void excluir() {
		transportadora = transportadoraDAOImpl.carregar(idBusca);
		transportadoraDAOImpl.excluir(transportadora);
	}
	
	public boolean valida(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Transportadora>> constraintViolations = validator.validate(transportadora);
		
		if(constraintViolations.size() >= 1){
			Iterator i = constraintViolations.iterator();
			while(i.hasNext()){
				ConstraintViolation mensagem = (ConstraintViolation) i.next();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem.getMessage(), mensagem.getMessage());
				FacesContext.getCurrentInstance().addMessage("cadastro", message);
			}
			return false;
		}else{
			return true;
		}
	}

	public void limpar() {
		transportadora = null;
		transportadora = new Transportadora();
		disableEndereco = false;
		transportadora.setUf(16);
		transportadora.setCespecial(true);
		transportadora.setCcredito(true);
	}

	 public void relatorioGeral() throws Exception {
		 GenericRelatorio relatorio = new GenericRelatorio();
		 Map parametro = new HashMap();
		 parametro.put("unidade", getUnidade().getCodigo());
		 try {
			 relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioTransportadora.jasper", "salvarPdf", parametro);
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
	 }
}

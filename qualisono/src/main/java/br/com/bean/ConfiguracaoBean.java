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
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import br.com.dao.ConfiguracaoDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Configuracao;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
public class ConfiguracaoBean {

	private Configuracao configuracao;
	private LoginBean loginBean;
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	@Autowired
	private ConfiguracaoDAOImpl<Configuracao> configuracaoDAOImpl;
	private Collection<Configuracao> configuracoes;
	private Integer idBusca;
	private String mensagem;
	private List<SelectItem> listaConfiguracao;
	
	public ConfiguracaoBean() {
		configuracao = new Configuracao();
	}
	
	public Configuracao getConfiguracao() {
		return this.configuracao;
	}

	public void setConfiguracao(Configuracao configuracao) {
		this.configuracao = configuracao;
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

	public ConfiguracaoDAOImpl<Configuracao> getConfiguracaoDAOImpl() {
		return configuracaoDAOImpl;
	}

	public void setConfiguracaoDAOImpl(ConfiguracaoDAOImpl<Configuracao> configuracaoDAOImpl) {
		this.configuracaoDAOImpl = configuracaoDAOImpl;
	}
	
	@Secured("ROLE_ADMIN")
	public Collection<Configuracao> getConfiguracoes() throws Exception{
		configuracoes = configuracaoDAOImpl.listar(getUnidade());
		return configuracoes;
	}
	
	public void setConfiguracoes(Collection<Configuracao> configuracoes) {
		this.configuracoes = configuracoes;
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
	
	public List<SelectItem> getListaConfiguracao() throws Exception {
		listaConfiguracao = new ArrayList<SelectItem>();
		ConfiguracaoDAOImpl<Configuracao> configuracaoDAOImpl = new ConfiguracaoDAOImpl<Configuracao>();
		Collection<Configuracao> configuracao = configuracaoDAOImpl.listar(getUnidade());
		for (Configuracao c : configuracao) {
			listaConfiguracao.add(new SelectItem(c.getCodigo(), String.valueOf(c.getCodigo()) + " - " + c.getDescricao()));
		}
		return listaConfiguracao;
	}
	
	public Unidade getUnidade() {
		return usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario()).getUnidade();
	}
	
	@Secured("ROLE_ADMIN")
	public String carregarCadastrar(){
		limpar();
		return "/tela/configuracao/cadastrarConfiguracao";
	}

	@Secured("ROLE_ADMIN")
	public String carregarAlterar() {
		configuracao = configuracaoDAOImpl.carregar(idBusca);
		return "/telan/configuracao/alterarConfiguracao";
	}
	
	public String cadastrar() {
		if(valida()){
			configuracao.setUnidade(getUnidade());
			configuracaoDAOImpl.salvar(configuracao);
			limpar();
		}
		return "/tela/configuracao/cadastrarConfiguracao";
	}
	
	public String alterar() {
		if(valida()){
			configuracaoDAOImpl.salvarOuAtualizar(configuracao);
			return "/tela/configuracao/listarConfiguracao";
		}else{
			return "/tela/configuracao/alterarConfiguracao";
		}
	}
	
	public String excluir(){
		configuracao = configuracaoDAOImpl.carregar(idBusca);
		configuracaoDAOImpl.excluir(configuracao);
		return "/tela/configuracao/listarConfiguracao";
	}
	
	public boolean valida(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Configuracao>> constraintViolations = validator.validate(configuracao);
		
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
	
	public void limpar(){
		configuracao = null;
		configuracao = new Configuracao();
	}
	
	public List<String> autocompleteGrupo(Object event) throws Exception{
		String cod = (String) event;
		ArrayList<String>retorno = new ArrayList<String>();
		ConfiguracaoDAOImpl<Configuracao> configuracaoDAOImpl = new ConfiguracaoDAOImpl<Configuracao>();
		Collection<String> configuracao = configuracaoDAOImpl.getGrupo();
		for (String c : configuracao) {
			if((c != null && c.indexOf(cod.toLowerCase()) == 0) || "".equals(cod)){
				retorno.add(c);
			}
		}
		return retorno;
	}
	
	public void relatorioGeral() throws Exception {
		GenericRelatorio relatorio = new GenericRelatorio();
		Map parametro = new HashMap();
		parametro.put("unidade", getUnidade().getCodigo());
		try {
			relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioConfiguracao.jasper", "salvarPdf", parametro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

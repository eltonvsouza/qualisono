package br.com.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

import br.com.dao.FuncionarioDAOImpl;
import br.com.dao.TipoFuncionarioDAOImpl;
import br.com.dao.UfDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Funcionario;
import br.com.model.TipoFuncionario;
import br.com.model.Uf;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
public class FuncionarioBean {

	private Funcionario funcionario;
	private LoginBean loginBean;
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	@Autowired
	private FuncionarioDAOImpl<Funcionario> funcionarioDAOImpl;
	@Autowired
	private TipoFuncionarioDAOImpl<TipoFuncionario> tipoFuncionarioDAOImpl;
	private UfDAOImpl<Uf> ufDAOImpl;
	private Collection<Funcionario> funcionarios;
	private List<SelectItem> listaFuncionario;
	private Integer idBusca;
	private String mensagem;
	private boolean disableEndereco;
	private String foco;
	private String comissao;
	private Date today = new Date();
	private TipoFuncionario monitor = new TipoFuncionario();
	private TipoFuncionario pdistribuidor = new TipoFuncionario();;

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
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

	public FuncionarioDAOImpl<Funcionario> getFuncionarioDAOImpl() {
		return funcionarioDAOImpl;
	}

	public void setFuncionarioDAOImpl(FuncionarioDAOImpl<Funcionario> funcionarioDAOImpl) {
		this.funcionarioDAOImpl = funcionarioDAOImpl;
	}


	public UfDAOImpl<Uf> getUfDAOImpl() {
		return ufDAOImpl;
	}

	public void setUfDAOImpl(UfDAOImpl<Uf> ufDAOImpl) {
		this.ufDAOImpl = ufDAOImpl;
	}

	public Collection<Funcionario> getFuncionarios() throws Exception {
		funcionarios = funcionarioDAOImpl.listar(getUnidade());
		return funcionarios;
	}

	public void setFuncionarios(Collection<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<SelectItem> getListaFuncionario() throws Exception {
		listaFuncionario = new ArrayList<SelectItem>();
		FuncionarioDAOImpl<Funcionario> funcionarioDAOImpl = new FuncionarioDAOImpl<Funcionario>();
		Collection<Funcionario> funcionario = funcionarioDAOImpl.listar(getUnidade());
		for (Funcionario c : funcionario) {
			listaFuncionario.add(new SelectItem(c, c.getNome()));
		}
		return listaFuncionario;
	}

	public List<SelectItem> getListaFuncionarioTipo(TipoFuncionario tipoFuncionario) throws Exception {
		System.out.println(tipoFuncionario.getCodigo());
		listaFuncionario = new ArrayList<SelectItem>();
		FuncionarioDAOImpl<Funcionario> funcionarioDAOImpl = new FuncionarioDAOImpl<Funcionario>();
		Collection<Funcionario> funcionario = funcionarioDAOImpl.listarTipo(getUnidade(), tipoFuncionario);
		for (Funcionario c : funcionario) {
			listaFuncionario.add(new SelectItem(c, c.getNome()));
		}
		return listaFuncionario;
	}

//	public List<Funcionario> autocompletePDistribuidor(Object event) throws Exception{
//		String cod = (String) event;
//		ArrayList<Funcionario>retorno = new ArrayList<Funcionario>();
//		FuncionarioDAOImpl<Funcionario> funcionarioDAOImpl = new FuncionarioDAOImpl<Funcionario>();
//		Collection<Funcionario> funcionario = funcionarioDAOImpl.listarTipo(usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario()).getUnidade(), pDistribuidor);
//		for (Funcionario c : funcionario) {
//			if((c.getCodigo() != null && String.valueOf(c.getCodigo()).indexOf(cod.toLowerCase()) == 0) || "".equals(cod)){
//				retorno.add(c);
//			}
//		}
//		return retorno;
//	}
//	
//	public List<Funcionario> autocompleteMonitor(Object event) throws Exception{
//		String cod = (String) event;
//		ArrayList<Funcionario>retorno = new ArrayList<Funcionario>();
//		FuncionarioDAOImpl<Funcionario> funcionarioDAOImpl = new FuncionarioDAOImpl<Funcionario>();
//		Collection<Funcionario> funcionario = funcionarioDAOImpl.listarTipo(usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario()).getUnidade(), monitor);
//		for (Funcionario c : funcionario) {
//			if((c.getCodigo() != null && String.valueOf(c.getCodigo()).indexOf(cod.toLowerCase()) == 0) || "".equals(cod)){
//				retorno.add(c);
//			}
//		}
//		return retorno;
//	}
	
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

	public String getFoco() {
		return foco;
	}

	public void setFoco(String foco) {
		this.foco = foco;
	}

	public String getComissao() {
		return comissao;
	}

	public void setComissao(String comissao) {
		this.comissao = String.valueOf(funcionario.getTipofuncionario().getComissao());
	}
	
	public TipoFuncionario getMonitor() {
		return monitor;
	}

	public void setMonitor(TipoFuncionario monitor) {
		this.monitor = monitor;
	}

	public TipoFuncionario getPdistribuidor() {
		return pdistribuidor;
	}

	public void setPdistribuidor(TipoFuncionario pdistribuidor) {
		this.pdistribuidor = pdistribuidor;
	}
	
	public Unidade getUnidade() {
		return usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario()).getUnidade();
	}

	public String carregarCadastrar() {
		limpar();
		return "/tela/funcionario/cadastrarFuncionario";
	}

	public String carregarAlterar() {
		funcionario = funcionarioDAOImpl.carregar(idBusca);
		setarComissao();
		return "/tela/funcionario/alterarFuncionario";
	}

	public String cadastrar() {
		funcionario.setUnidade(usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario()).getUnidade());
		if(valida()){
			funcionarioDAOImpl.salvar(funcionario);
			limpar();
		}
		return "/tela/funcionario/cadastrarFuncionario";
	}

	public String alterar() {
		if(valida()){
			funcionarioDAOImpl.salvarOuAtualizar(funcionario);
			return "/tela/funcionario/listarFuncionario";
		}else{
			return "/tela/funcionario/alterarFuncionario";
		}
	}

	public void excluir() {
		funcionario = funcionarioDAOImpl.carregar(idBusca);
		funcionarioDAOImpl.excluir(funcionario);
	}
	
	public boolean valida(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validate(funcionario);
		
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

	public void setarComissao(){
		this.comissao = String.valueOf(funcionario.getTipofuncionario().getComissao());
	}
	
	public void limpar() {
		funcionario = null;
		funcionario = new Funcionario();
		foco = "#cpfcnpj";
		disableEndereco = false;
		funcionario.setTipo("cnpj");
		funcionario.setUf(16);
		funcionario.setAtivo(true);
		funcionario.setDatacadastro(today);
		comissao = String.valueOf(tipoFuncionarioDAOImpl.carregar(1).getComissao());
		monitor.setCodigo(1);
		pdistribuidor.setCodigo(1);
//		System.out.println(monitor.getCodigo() + " - " + pDistribuidor.getCodigo());
	}

	 public void relatorioGeral() throws Exception {
		 GenericRelatorio relatorio = new GenericRelatorio();
		 Map parametro = new HashMap();
		 parametro.put("unidade", getUnidade().getCodigo());
		 try {
			 relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioFuncionario.jasper", "salvarPdf", parametro);
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
	 }
}

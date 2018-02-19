package br.com.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.dao.FornecedorDAOImpl;
import br.com.dao.UfDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Fornecedor;
import br.com.model.Uf;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
public class FornecedorBean {

	private Fornecedor fornecedor;
	private LoginBean loginBean;
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	@Autowired
	private FornecedorDAOImpl<Fornecedor> fornecedorDAOImpl;
	private UfDAOImpl<Uf> ufDAOImpl;
	private Collection<Fornecedor> fornecedors;
	private List<SelectItem> listaFornecedor;
	private Integer idBusca;
	private String mensagem;
	private boolean disableEndereco;
//	private EnderecoDAOImpl<Endereco> enderecoDAOImpl;
//	private BairroDAOImpl<Bairro> bairroDAOImpl;
//	private CidadeDAOImpl<Cidade> cidadeDAOImpl;
//	private PaisesDAOImpl<Paises> paisesDAOImpl;
//	private Endereco endereco; 
//	private Bairro bairro;
//	private Cidade cidade;
//	private Paises pais;

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
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
	
	public FornecedorDAOImpl<Fornecedor> getFornecedorDAOImpl() {
		return fornecedorDAOImpl;
	}

	public void setFornecedorDAOImpl(FornecedorDAOImpl<Fornecedor> fornecedorDAOImpl) {
		this.fornecedorDAOImpl = fornecedorDAOImpl;
	}


	public UfDAOImpl<Uf> getUfDAOImpl() {
		return ufDAOImpl;
	}

	public void setUfDAOImpl(UfDAOImpl<Uf> ufDAOImpl) {
		this.ufDAOImpl = ufDAOImpl;
	}

//	public EnderecoDAOImpl<Endereco> getEnderecoDAOImpl() {
//		return enderecoDAOImpl;
//	}
//
//	public void setEnderecoDAOImpl(EnderecoDAOImpl<Endereco> enderecoDAOImpl) {
//		this.enderecoDAOImpl = enderecoDAOImpl;
//	}
//
//	public BairroDAOImpl<Bairro> getBairroDAOImpl() {
//		return bairroDAOImpl;
//	}
//
//	public void setBairroDAOImpl(BairroDAOImpl<Bairro> bairroDAOImpl) {
//		this.bairroDAOImpl = bairroDAOImpl;
//	}
//
//	public CidadeDAOImpl<Cidade> getCidadeDAOImpl() {
//		return cidadeDAOImpl;
//	}
//
//	public void setCidadeDAOImpl(CidadeDAOImpl<Cidade> cidadeDAOImpl) {
//		this.cidadeDAOImpl = cidadeDAOImpl;
//	}
//	
//	public PaisesDAOImpl<Paises> getPaisesDAOImpl() {
//		return paisesDAOImpl;
//	}
//
//	public void setPaisesDAOImpl(PaisesDAOImpl<Paises> paisesDAOImpl) {
//		this.paisesDAOImpl = paisesDAOImpl;
//	}
//	
//	public Endereco getEndereco() {
//		return endereco;
//	}
//
//	public void setEndereco(Endereco endereco) {
//		this.endereco = endereco;
//	}
//
//	public Bairro getBairro() {
//		return bairro;
//	}
//
//	public void setBairro(Bairro bairro) {
//		this.bairro = bairro;
//	}
//
//	public Cidade getCidade() {
//		return cidade;
//	}
//
//	public void setCidade(Cidade cidade) {
//		this.cidade = cidade;
//	}
//
//	public Paises getPais() {
//		return pais;
//	}
//
//	public void setPais(Paises pais) {
//		this.pais = pais;
//	}

	public Collection<Fornecedor> getFornecedors() throws Exception {
		fornecedors = fornecedorDAOImpl.listar(getUnidade());
		return fornecedors;
	}

	public void setFornecedors(Collection<Fornecedor> fornecedors) {
		this.fornecedors = fornecedors;
	}

	public List<SelectItem> getListaFornecedor() throws Exception {
		listaFornecedor = new ArrayList<SelectItem>();
		FornecedorDAOImpl<Fornecedor> fornecedorDAOImpl = new FornecedorDAOImpl<Fornecedor>();
		Collection<Fornecedor> fornecedor = fornecedorDAOImpl.listar(getUnidade());
		for (Fornecedor c : fornecedor) {
			listaFornecedor.add(new SelectItem(c, c.getNome()));
		}
		return listaFornecedor;
	}
	
	public List<Fornecedor> autocompleteFornecedor(Object event) throws Exception{
		String cod = (String) event;
		ArrayList<Fornecedor>retorno = new ArrayList<Fornecedor>();
		FornecedorDAOImpl<Fornecedor> fornecedorDAOImpl = new FornecedorDAOImpl<Fornecedor>();
		Collection<Fornecedor> fornecedor = fornecedorDAOImpl.listar(getUnidade());
		for (Fornecedor c : fornecedor) {
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
		return "/tela/fornecedor/cadastrarFornecedor";
	}

	public String carregarAlterar() {
		fornecedor = fornecedorDAOImpl.carregar(idBusca);
		return "/tela/fornecedor/alterarFornecedor";
	}

	public String cadastrar() {
		fornecedor.setUnidade(getUnidade());
		if(valida()){
			fornecedorDAOImpl.salvar(fornecedor);
			limpar();
		}
		return "/tela/fornecedor/cadastrarFornecedor";
	}

	public String alterar() {
		if(valida()){
			fornecedorDAOImpl.salvarOuAtualizar(fornecedor);
			return "/tela/fornecedor/listarFornecedor";
		}else{
			return "/tela/fornecedor/alterarFornecedor";
		}
	}

	public void excluir() {
		fornecedor = fornecedorDAOImpl.carregar(idBusca);
		fornecedorDAOImpl.excluir(fornecedor);
	}
	
	public boolean valida(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Fornecedor>> constraintViolations = validator.validate(fornecedor);
		
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
		fornecedor = null;
		fornecedor = new Fornecedor();
		disableEndereco = false;
		fornecedor.setUf(16);
		fornecedor.setCespecial(true);
		fornecedor.setCcredito(true);
	}

	 public void relatorioGeral() throws Exception {
		 GenericRelatorio relatorio = new GenericRelatorio();
		 Map parametro = new HashMap();
		 parametro.put("unidade", getUnidade().getCodigo());
		 try {
			 relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioFornecedor.jasper", "salvarPdf", parametro);
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
	 }
	 
//	 public void carregarEndereco(){
//		endereco = enderecoDAOImpl.carregarEndereco(fornecedor.getCep().replaceAll("-", ""));
//		setFoco("#pais");
//		if(endereco != null){
//			setFoco("#numero");
//			disableEndereco = true;
//			bairro = bairroDAOImpl.carregar(endereco.getBairroCodigo());
//			cidade = cidadeDAOImpl.carregar(bairro.getCidadeCodigo());
//			
//			fornecedor.setLogradouro(endereco.getEnderecoLogradouro());
//			fornecedor.setBairro(bairro.getBairroDescricao());
//			fornecedor.setCidade(cidade.getCidadeDescricao());
//			fornecedor.setUf(cidade.getUfCodigo());
//			fornecedor.setPais("Brasil");
//		}else{
//			disableEndereco = false;
//		}
//	}
}

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

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.dao.ProdutoDAOImpl;
import br.com.dao.UfDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Fornecedor;
import br.com.model.Produto;
import br.com.model.Uf;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
@Lazy
public class ProdutoBean {

	private Produto produto;
	private LoginBean loginBean;
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	@Autowired
	private ProdutoDAOImpl<Produto> produtoDAOImpl;
	private UfDAOImpl<Uf> ufDAOImpl;
	private Collection<Produto> produtos;
	private List<SelectItem> listaProduto;
	private Integer idBusca;
	private String mensagem;
	private boolean disableEndereco;
	private String foco;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
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

	public ProdutoDAOImpl<Produto> getProdutoDAOImpl() {
		return produtoDAOImpl;
	}

	public void setProdutoDAOImpl(ProdutoDAOImpl<Produto> produtoDAOImpl) {
		this.produtoDAOImpl = produtoDAOImpl;
	}


	public UfDAOImpl<Uf> getUfDAOImpl() {
		return ufDAOImpl;
	}

	public void setUfDAOImpl(UfDAOImpl<Uf> ufDAOImpl) {
		this.ufDAOImpl = ufDAOImpl;
	}

	public Collection<Produto> getProdutos() throws Exception {
		produtos = produtoDAOImpl.listar(getUnidade());
		return produtos;
	}

	public void setProdutos(Collection<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<SelectItem> getListaProduto() throws Exception {
		listaProduto = new ArrayList<SelectItem>();
		ProdutoDAOImpl<Produto> produtoDAOImpl = new ProdutoDAOImpl<Produto>();
		Collection<Produto> produto = produtoDAOImpl.listar(getUnidade());
		for (Produto c : produto) {
			listaProduto.add(new SelectItem(c, c.getCodprod() + " - " + c.getDescricao()));
		}
		return listaProduto;
	}
	
	public List<SelectItem> getListaProdutoFornecedor(Fornecedor fornecedor) throws Exception {
		listaProduto = new ArrayList<SelectItem>();
		ProdutoDAOImpl<Produto> produtoDAOImpl = new ProdutoDAOImpl<Produto>();
		Collection<Produto> produto = produtoDAOImpl.listarProdutoFornecedor(getUnidade(), fornecedor);
		for (Produto c : produto) {
			listaProduto.add(new SelectItem(c, c.getCodprod() + " - " + c.getDescricao()));
		}
		return listaProduto;
	}
	
	public List<Produto> autocompleteProduto(Object event) throws Exception{
		String cod = (String) event;
		ArrayList<Produto>retorno = new ArrayList<Produto>();
		ProdutoDAOImpl<Produto> produtoDAOImpl = new ProdutoDAOImpl<Produto>();
		Collection<Produto> produto = produtoDAOImpl.listar(getUnidade());
		for (Produto c : produto) {
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

	public String getFoco() {
		return foco;
	}

	public void setFoco(String foco) {
		this.foco = foco;
	}

	public Unidade getUnidade() {
		return usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario()).getUnidade();
	}
	
	public String carregarCadastrar() {
		limpar();
		return "/tela/produto/cadastrarProduto";
	}

	public String carregarAlterar() {
		produto = produtoDAOImpl.carregar(idBusca);
		return "/tela/produto/alterarProduto";
	}

	public String cadastrar() {
		if(valida()){
			produto.setUnidade(getUnidade());
			produtoDAOImpl.salvar(produto);
			limpar();
		}
		return "/tela/produto/cadastrarProduto";
	}

	public String alterar() {
		if(valida()){
			produtoDAOImpl.salvarOuAtualizar(produto);
			return "/tela/produto/listarProduto";
		}else{
			return "/tela/produto/alterarProduto";
		}
	}

	public void excluir() {
		produto = produtoDAOImpl.carregar(idBusca);
		produtoDAOImpl.excluir(produto);
	}
	
	public boolean valida(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Produto>> constraintViolations = validator.validate(produto);
		
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
		produto = null;
		produto = new Produto();
		foco = "#codprod";
		disableEndereco = false;
	}

	 public void relatorioGeral() throws Exception {
		 GenericRelatorio relatorio = new GenericRelatorio();
		 Map parametro = new HashMap();
		 parametro.put("unidade", getUnidade().getCodigo());
		 try {
			 relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioProduto.jasper", "salvarPdf", parametro);
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
	 }
}

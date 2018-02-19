package br.com.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.dao.ClienteDAOImpl;
import br.com.dao.CompraDAOImpl;
import br.com.dao.FornecedorDAOImpl;
import br.com.dao.TransportadoraDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Cliente;
import br.com.model.Compra;
import br.com.model.CompraItem;
import br.com.model.Fornecedor;
import br.com.model.Transportadora;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;
import br.com.util.Parse;

@Component
@Scope("session")
public class CompraBean {

	private Compra compra;
	private CompraItem compraItem;
	private LoginBean loginBean;
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	private DataModel<CompraItem> tbCompraItem;
	private List<CompraItem> lista;
	private Set<CompraItem> compraitems = new HashSet<CompraItem>();
	@Autowired
	private CompraDAOImpl<Compra> compraDAOImpl;
	private Collection<Compra> compras;
	private List<SelectItem> listaCompra;
	private Integer idBusca;
	private String mensagem;
	private String foco;
	private Date today = new Date();
	private Integer tquantidade;
	private Double tvtotal;
	private Double tbcicms;
	private Double tvicms;
	private Double taicms;
	private Double tvipi;
	private Double taipi;

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public CompraItem getCompraItem() {
		return compraItem;
	}

	public void setCompraItem(CompraItem compraItem) {
		this.compraItem = compraItem;
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

	public DataModel<CompraItem> getTbCompraItem() {
		return tbCompraItem;
	}

	public void setTbCompraItem(DataModel<CompraItem> tbCompraItem) {
		this.tbCompraItem = tbCompraItem;
	}

	public List<CompraItem> getLista() {
		return lista;
	}

	public void setLista(List<CompraItem> lista) {
		this.lista = lista;
	}

	public Set<CompraItem> getCompraitems() {
		return compraitems;
	}

	public void setCompraitems(Set<CompraItem> compraitems) {
		this.compraitems = compraitems;
	}

	public CompraDAOImpl<Compra> getCompraDAOImpl() {
		return compraDAOImpl;
	}

	public void setCompraDAOImpl(CompraDAOImpl<Compra> compraDAOImpl) {
		this.compraDAOImpl = compraDAOImpl;
	}

	public Collection<Compra> getCompras() throws Exception {
		compras = compraDAOImpl.listar(getUnidade());
		return compras;
	}

	public void setCompras(Collection<Compra> compras) {
		this.compras = compras;
	}

	public List<SelectItem> getListaCompra() throws Exception {
		listaCompra = new ArrayList<SelectItem>();
		CompraDAOImpl<Compra> compraDAOImpl = new CompraDAOImpl<Compra>();
		Collection<Compra> compra = compraDAOImpl.listar(getUnidade());
		for (Compra c : compra) {
			listaCompra.add(new SelectItem(c, c.getData() + " - " +c.getNnf() + " - " + c.getFornecedor()));
		}
		return listaCompra;
	}
	
	public List<Compra> autocompleteCompra(Object event) throws Exception{
		String cod = (String) event;
		ArrayList<Compra>retorno = new ArrayList<Compra>();
		CompraDAOImpl<Compra> compraDAOImpl = new CompraDAOImpl<Compra>();
		Collection<Compra> compra = compraDAOImpl.listar(getUnidade());
		for (Compra c : compra) {
			if((String.valueOf(c.getNnf()) != null && String.valueOf(c.getNnf()).indexOf(cod.toLowerCase()) == 0) || "".equals(cod)){
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

	public String getFoco() {
		return foco;
	}

	public void setFoco(String foco) {
		this.foco = foco;
	}
	
	public Unidade getUnidade() {
		return usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario()).getUnidade();
	}

	public Integer getTquantidade() {
		return tquantidade;
	}

	public void setTquantidade(Integer tquantidade) {
		this.tquantidade = tquantidade;
	}

	public Double getTvtotal() {
		return tvtotal;
	}

	public void setTvtotal(Double tvtotal) {
		this.tvtotal = tvtotal;
	}

	public Double getTbcicms() {
		return tbcicms;
	}

	public void setTbcicms(Double tbcicms) {
		this.tbcicms = tbcicms;
	}

	public Double getTvicms() {
		return tvicms;
	}

	public void setTvicms(Double tvicms) {
		this.tvicms = tvicms;
	}

	public Double getTaicms() {
		return taicms;
	}

	public void setTaicms(Double taicms) {
		this.taicms = taicms;
	}

	public Double getTvipi() {
		return tvipi;
	}

	public void setTvipi(Double tvipi) {
		this.tvipi = tvipi;
	}

	public Double getTaipi() {
		return taipi;
	}

	public void setTaipi(Double taipi) {
		this.taipi = taipi;
	}

	public String carregarCadastrar() {
		limpar();
		return "/tela/compra/cadastrarCompra";
	}

	public String carregarAlterar() {
		compra = compraDAOImpl.carregar(idBusca);
		return "/tela/compra/alterarCompra";
	}
	
	public String addItem(){
		System.out.println("TESTANDO");
		if(!compraItem.getQuantidade().equals(null)){
			System.out.println("TESTANDO");
			compraItem.setUnidade(getUnidade());
			compraItem.setCompra(compra);
			lista.add(compraItem);
			compra.getCompraitems().add(compraItem);
			tbCompraItem = new ListDataModel<CompraItem>(lista);
			atualizaTotalTabela();
			limparItem();
		}
		return "/tela/compra/cadastrarCompra";
	}
	
	private void atualizaTotalTabela() {
		compraItem.setVtotal(compraItem.getProduto().getVcompra() * compraItem.getQuantidade());
		compraItem.setBcicms(0.0);
		compraItem.setVicms(0.0);
		compraItem.setAicms(0.0);
		compraItem.setVipi(0.0);
		compraItem.setAipi(0.0);
		tquantidade += compraItem.getQuantidade();
		tvtotal += compraItem.getVtotal();
		tbcicms += compraItem.getBcicms();
		tvicms += compraItem.getVicms();
		taicms += compraItem.getAicms();
		tvipi += compraItem.getVipi();
		taipi += compraItem.getAipi();
	}

	public String cadastrar() {
		if(valida()){
			if (!compra.getCompraitems().isEmpty()) {
				compra.setUnidade(getUnidade());
				compra.setValor(tvtotal);
				compraDAOImpl.salvar(compra);
				limpar();
			}else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ADICIONE UM PRODUTO", "ADICIONE UM PRODUTO!");
				FacesContext.getCurrentInstance().addMessage("cadastro", message);
			}
		}
		return "/tela/compra/cadastrarCompra";
	}

	public String alterar() {
		if(valida()){
			compraDAOImpl.salvarOuAtualizar(compra);
			return "/tela/compra/listarCompra";
		}else{
			return "/tela/compra/alterarCompra";
		}
	}

	public void excluir() {
		compra = compraDAOImpl.carregar(idBusca);
		compraDAOImpl.excluir(compra);
	}
	
	public boolean valida(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Compra>> constraintViolations = validator.validate(compra);
		
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
	
	public boolean validaItem(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<CompraItem>> constraintViolations = validator.validate(compraItem);
		
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
		compra = null;
		compra = new Compra();
		foco = "#nnf";
		compra.setData(today);
		Fornecedor f = new Fornecedor();
		f.setCodigo(1);
		compra.setFornecedor(f);
		Transportadora t = new Transportadora();
		t.setCodigo(1);
		compra.setTransportadora(t);
		limparLista();
		limparItem();
	}

	public void limparLista() {
		tquantidade = 0;
		tvtotal = 0.0;
		tbcicms = 0.0;
		tvicms = 0.0;
		taicms = 0.0;
		taicms = 0.0;
		tvipi = 0.0;
		taipi = 0.0;
		compra.getCompraitems().clear();
		lista = null;
		lista = new ArrayList<CompraItem>();
		tbCompraItem = null;
	}
	
	public void limparItem() {
		compraItem = null;
		compraItem = new CompraItem();
//		foco = "#produto";
	}
	
	 public void relatorioGeral() throws Exception {
		 GenericRelatorio relatorio = new GenericRelatorio();
		 Map parametro = new HashMap();
		 parametro.put("unidade", getUnidade().getCodigo());
		 try {
			 relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioCompra.jasper", "salvarPdf", parametro);
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
	 }
}

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

import br.com.dao.FuncionarioDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.dao.VendaDAOImpl;
import br.com.model.Cliente;
import br.com.model.Funcionario;
import br.com.model.TipoFuncionario;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.model.Venda;
import br.com.model.VendaItem;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
public class VendaBean {

	private Venda venda;
	private VendaItem vendaItem;
	private LoginBean loginBean;
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	private DataModel<VendaItem> tbVendaItem;
	private List<VendaItem> lista;
	private Set<VendaItem> vendaitems = new HashSet<VendaItem>();
	@Autowired
	private VendaDAOImpl<Venda> vendaDAOImpl;
	private Collection<Venda> vendas;
	private List<SelectItem> listaVenda;
	private Integer idBusca;
	private String mensagem;
	private String foco;
	private Date today = new Date();
	private TipoFuncionario monitor = new TipoFuncionario();
	private TipoFuncionario pdistribuidor = new TipoFuncionario();
	private Integer tquantidade;
	private Integer tpontuacao;
	private Double tvtotal;
	private Double tcomissaopd;
	private Double tcomissaom;

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public VendaItem getVendaItem() {
		return vendaItem;
	}

	public void setVendaItem(VendaItem vendaItem) {
		this.vendaItem = vendaItem;
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

	public DataModel<VendaItem> getTbVendaItem() {
		return tbVendaItem;
	}

	public void setTbVendaItem(DataModel<VendaItem> tbVendaItem) {
		this.tbVendaItem = tbVendaItem;
	}

	public List<VendaItem> getLista() {
		return lista;
	}

	public void setLista(List<VendaItem> lista) {
		this.lista = lista;
	}

	public Set<VendaItem> getVendaitems() {
		return vendaitems;
	}

	public void setVendaitems(Set<VendaItem> vendaitems) {
		this.vendaitems = vendaitems;
	}

	public VendaDAOImpl<Venda> getVendaDAOImpl() {
		return vendaDAOImpl;
	}

	public void setVendaDAOImpl(VendaDAOImpl<Venda> vendaDAOImpl) {
		this.vendaDAOImpl = vendaDAOImpl;
	}

	public Collection<Venda> getVendas() throws Exception {
		vendas = vendaDAOImpl.listar(getUnidade());
		return vendas;
	}

	public void setVendas(Collection<Venda> vendas) {
		this.vendas = vendas;
	}

	public List<SelectItem> getListaVenda() throws Exception {
		listaVenda = new ArrayList<SelectItem>();
		VendaDAOImpl<Venda> vendaDAOImpl = new VendaDAOImpl<Venda>();
		Collection<Venda> venda = vendaDAOImpl.listar(getUnidade());
		for (Venda c : venda) {
			listaVenda.add(new SelectItem(c.getCodigo(), c.getData() + " - " +c.getCliente() + " - " + c.getFuncionarioByPdistribuidor()));
		}
		return listaVenda;
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

	public Integer getTquantidade() {
		return tquantidade;
	}

	public void setTquantidade(Integer tquantidade) {
		this.tquantidade = tquantidade;
	}

	public Integer getTpontuacao() {
		return tpontuacao;
	}

	public void setTpontuacao(Integer tpontuacao) {
		this.tpontuacao = tpontuacao;
	}

	public Double getTvtotal() {
		return tvtotal;
	}

	public void setTvtotal(Double tvalor) {
		this.tvtotal = tvalor;
	}

	public Double getTcomissaopd() {
		return tcomissaopd;
	}

	public void setTcomissaopd(Double tcomissaopd) {
		this.tcomissaopd = tcomissaopd;
	}

	public Double getTcomissaom() {
		return tcomissaom;
	}

	public void setTcomissaom(Double tcomissaom) {
		this.tcomissaom = tcomissaom;
	}

	public String carregarCadastrar() {
		limpar();
		return "/tela/venda/cadastrarVenda";
	}

	public String carregarAlterar() {
		venda = vendaDAOImpl.carregar(idBusca);
		return "/tela/venda/alterarVenda";
	}
	
	public String addItem(){
		if(validaItem()){
			vendaItem.setUnidade(getUnidade());
			vendaItem.setVenda(venda);
			lista.add(vendaItem);
			venda.getVendaitems().add(vendaItem);
			tbVendaItem = new ListDataModel<VendaItem>(lista);
			atualizaTotalTabela();
			limparItem();
		}
		return "/tela/venda/cadastrarVenda";
	}

	private void atualizaTotalTabela() {
		vendaItem.setPontuacao(venda.getFuncionarioByPdistribuidor().getTipofuncionario().getPontuacao());
		vendaItem.setValor(vendaItem.getProduto().getVvenda() * vendaItem.getQuantidade());
		vendaItem.setComissaom(venda.getFuncionarioByMonitor().getTipofuncionario().getComissao());
		vendaItem.setComissaopd(venda.getFuncionarioByPdistribuidor().getTipofuncionario().getComissao());
		tquantidade += vendaItem.getQuantidade();
		tpontuacao += vendaItem.getPontuacao();
		tvtotal += vendaItem.getValor();
		tcomissaom += vendaItem.getComissaom();
		tcomissaopd += vendaItem.getComissaopd();
	}
	
	public String cadastrar() {
		if(valida()){
			if (!venda.getVendaitems().isEmpty()) {
				venda.setUnidade(getUnidade());
				venda.setValor(tvtotal);
				vendaDAOImpl.salvar(venda);
				limpar();
			}else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ADICIONE UM PRODUTO", "ADICIONE UM PRODUTO!");
				FacesContext.getCurrentInstance().addMessage("cadastro", message);
			}
		}
		return "/tela/venda/cadastrarVenda";
	}

	public String alterar() {
		if(valida()){
			vendaDAOImpl.salvarOuAtualizar(venda);
			return "/tela/venda/listarVenda";
		}else{
			return "/tela/venda/alterarVenda";
		}
	}

	public void excluir() {
		venda = vendaDAOImpl.carregar(idBusca);
		vendaDAOImpl.excluir(venda);
	}
	
	public boolean valida(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Venda>> constraintViolations = validator.validate(venda);
		
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
		Set<ConstraintViolation<VendaItem>> constraintViolations = validator.validate(vendaItem);
		
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
		venda = null;
		venda = new Venda();
		foco = "#cliente";
		venda.setData(today);
		Cliente c = new Cliente();
		c.setCodigo(1);
		venda.setCliente(c);
		venda.setFpagamento("cartao");
		monitor.setCodigo(1);
		pdistribuidor.setCodigo(2);
		FuncionarioDAOImpl<Funcionario> funcionarioDAOImpl = new FuncionarioDAOImpl<Funcionario>(); 
		venda.setFuncionarioByMonitor(funcionarioDAOImpl.carregar(1));
		venda.setFuncionarioByPdistribuidor(funcionarioDAOImpl.carregar(2));
		limparLista();
		limparItem();
	}

	public void limparLista() {
		tquantidade = 0;
		tpontuacao = 0;
		tvtotal = 0.0;
		tcomissaom = 0.0;
		tcomissaopd = 0.0;
		venda.getVendaitems().clear();
		lista = null;
		lista = new ArrayList<VendaItem>();
		tbVendaItem = null;
	}
	
	public void limparItem() {
		vendaItem = null;
		vendaItem = new VendaItem();
		vendaItem.setTipodesconto("D");
	}
	
	public void relatorioGeral() throws Exception {
		GenericRelatorio relatorio = new GenericRelatorio();
		Map parametro = new HashMap();
		parametro.put("unidade", getUnidade().getCodigo());
		try {
			relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioVenda.jasper", "salvarPdf", parametro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

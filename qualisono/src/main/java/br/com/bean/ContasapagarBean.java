package br.com.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
import br.com.dao.ContasapagarDAOImpl;
import br.com.dao.FornecedorDAOImpl;
import br.com.dao.TransportadoraDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Cliente;
import br.com.model.Contasapagar;
import br.com.model.Fornecedor;
import br.com.model.Parcelasapagar;
import br.com.model.Transportadora;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
public class ContasapagarBean {

	private Contasapagar contasapagar;
	private Parcelasapagar parcelasapagar;
	private LoginBean loginBean;
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	private DataModel<Parcelasapagar> tbParcela;
	@Autowired
	private ContasapagarDAOImpl<Contasapagar> contasapagarDAOImpl;
	private Collection<Contasapagar> contasapagars;
	private List<SelectItem> listaContasapagar;
	private List<Parcelasapagar> listaParcelasaPagar;
	private List<SelectItem> listaOpcoesCategoria;
	private Integer idBusca;
	private String mensagem;
	private Date today = new Date();
	
	public Contasapagar getContasapagar() {
		return contasapagar;
	}

	public void setContasapagar(Contasapagar contasapagar) {
		this.contasapagar = contasapagar;
	}

	public Parcelasapagar getParcelasapagar() {
		return parcelasapagar;
	}

	public void setParcelasapagar(Parcelasapagar parcelasapagar) {
		this.parcelasapagar = parcelasapagar;
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

	public DataModel<Parcelasapagar> getTbParcela() {
		return tbParcela;
	}

	public void setTbParcela(DataModel<Parcelasapagar> tbParcela) {
		this.tbParcela = tbParcela;
	}

	public ContasapagarDAOImpl<Contasapagar> getContasapagarDAOImpl() {
		return contasapagarDAOImpl;
	}

	public void setContasapagarDAOImpl(ContasapagarDAOImpl<Contasapagar> contasapagarDAOImpl) {
		this.contasapagarDAOImpl = contasapagarDAOImpl;
	}

	public Collection<Contasapagar> getContasapagars() throws Exception {
		contasapagars = contasapagarDAOImpl.listar(getUnidade());
		return contasapagars;
	}

	public void setContasapagars(Collection<Contasapagar> contasapagars) {
		this.contasapagars = contasapagars;
	}

	public List<SelectItem> getListaContasapagar() throws Exception {
		listaContasapagar = new ArrayList<SelectItem>();
		ContasapagarDAOImpl<Contasapagar> contasapagarDAOImpl = new ContasapagarDAOImpl<Contasapagar>();
		Collection<Contasapagar> contasapagar = contasapagarDAOImpl.listar(getUnidade());
		for (Contasapagar c : contasapagar) {
			listaContasapagar.add(new SelectItem(c, c.getDatavencimento() + " - " + c.getOpcaocategoria() ));
		}
		return listaContasapagar;
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
	
	public Unidade getUnidade() {
		return usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario()).getUnidade();
	}

	public String carregarCadastrar() {
		limpar();
		return "/tela/contasapagar/cadastrarContasapagar";
	}

	public String carregarAlterar() {
		contasapagar = contasapagarDAOImpl.carregar(idBusca);
//		listaParcelasaPagar.addAll(contasapagar.getParcelasapagars());
//		tbParcela = new ListDataModel<Parcelasapagar>(listaParcelasaPagar);
		System.out.println(contasapagar.getParcelasapagars().size());
		return "/tela/contasapagar/alterarContasapagar";
	}

	public String cadastrar() {
		if(valida()){
			if(!listaVazia()){
				contasapagar.setUnidade(getUnidade());
				System.out.println(contasapagar.getOpcaocategoria());
				contasapagarDAOImpl.salvar(contasapagar);
				limpar();
			}
		}
		return "/tela/contasapagar/cadastrarContasapagar";
	}
	

	public String alterar() {
		if(valida()){
			if(!listaVazia()){
				contasapagarDAOImpl.salvarOuAtualizar(contasapagar);
				return "/tela/contasapagar/listarContasapagar";
			}else{
				return "/tela/contasapagar/alterarContasapagar";
			}
		}else{
			return "/tela/contasapagar/alterarContasapagar";
		}
	}

	public void excluir() {
		contasapagar = contasapagarDAOImpl.carregar(idBusca);
		contasapagarDAOImpl.excluir(contasapagar);
	}

	public boolean listaVazia(){
		if(contasapagar.getParcelasapagars().size() > 0){
			return false;
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NENHUMA PARCELA GERADA", "NENHUMA PARCELA GERADA!");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
			return true;
		}
	}
	
	public void gerarParcelas(){
		Double parcela = contasapagar.getValor()/contasapagar.getParcela();
		for (int i = 0; i < contasapagar.getParcela(); i++){
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(contasapagar.getDatavencimento());
			calendar.add(Calendar.DAY_OF_MONTH, contasapagar.getDia() * i);
			
			parcelasapagar.setContasapagar(contasapagar);
			parcelasapagar.setUnidade(getUnidade());
			parcelasapagar.setDatavencimento(calendar.getTime());
			parcelasapagar.setValorparcela(parcela);
			contasapagar.getParcelasapagars().add(parcelasapagar);
			listaParcelasaPagar.add(parcelasapagar);
			limparParcela();
		}
		
		tbParcela = new ListDataModel<Parcelasapagar>(listaParcelasaPagar);
	}
	
	public boolean valida(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Contasapagar>> constraintViolations = validator.validate(contasapagar);
		
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
	
	public List<SelectItem> getListaOpcoesCategoria() throws Exception {
		listaOpcoesCategoria = new ArrayList<SelectItem>();
		if(contasapagar.getCategoria().equals("f")){
			FornecedorDAOImpl<Fornecedor> fornecedorDAOImpl = new FornecedorDAOImpl<Fornecedor>();
			Collection<Fornecedor> fornecedores = fornecedorDAOImpl.listar(getUnidade());
			for (Fornecedor c : fornecedores) {
				listaOpcoesCategoria.add(new SelectItem(c.getCodigo(), c.getNome() ));
			}
		}else{
			if(contasapagar.getCategoria().equals("c")){
				ClienteDAOImpl<Cliente> clienteDAOImpl = new ClienteDAOImpl<Cliente>();
				Collection<Cliente> clientes = clienteDAOImpl.listar(getUnidade());
				for (Cliente c : clientes) {
					listaOpcoesCategoria.add(new SelectItem(c.getCodigo(), c.getNome() ));
				}
			}else{
				if(contasapagar.getCategoria().equals("t")){
					TransportadoraDAOImpl<Transportadora> transportadoraDAOImpl = new TransportadoraDAOImpl<Transportadora>();
					Collection<Transportadora> transportadoras = transportadoraDAOImpl.listar(getUnidade());
					for (Transportadora c : transportadoras) {
						listaOpcoesCategoria.add(new SelectItem(c.getCodigo(), c.getNome() ));
					}
				}
			}
		}
		return listaOpcoesCategoria;
	}
	
	public void limpar() {
		contasapagar = null;
		contasapagar = new Contasapagar();
		limparListaParcelasaPagar();
		limparParcela();
		contasapagar.setDatavencimento(today);
		contasapagar.setCategoria("f");
	}
	
	public void limparListaParcelasaPagar(){
		contasapagar.getParcelasapagars().clear();
		listaParcelasaPagar = null;
		listaParcelasaPagar = new ArrayList<Parcelasapagar>();
		tbParcela = null;
	}
	
	public void limparParcela() {
		parcelasapagar = null;
		parcelasapagar = new Parcelasapagar();
	}

	 public void relatorioGeral() throws Exception {
		 GenericRelatorio relatorio = new GenericRelatorio();
		 Map parametro = new HashMap();
		 parametro.put("unidade", getUnidade().getCodigo());
		 parametro.put("geral", "cpcategoria");
		 try {
			 relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioContasapagar.jasper", "salvarPdf", parametro);
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
	 }
}

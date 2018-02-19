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

import br.com.dao.ClienteDAOImpl;
import br.com.dao.UfDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Cliente;
import br.com.model.Uf;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
public class ClienteBean {

	private Cliente cliente;
	private LoginBean loginBean;
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	@Autowired
	private ClienteDAOImpl<Cliente> clienteDAOImpl;
	@Autowired
	private UfDAOImpl<Uf> ufDAOImpl;
	private Collection<Cliente> clientes;
	private List<SelectItem> listaCliente;
	private Integer idBusca;
	private String mensagem;
	private boolean disableEndereco;
	private String usuario; 
//	private EnderecoDAOImpl<Endereco> enderecoDAOImpl;
//	private BairroDAOImpl<Bairro> bairroDAOImpl;
//	private CidadeDAOImpl<Cidade> cidadeDAOImpl;
//	private PaisesDAOImpl<Paises> paisesDAOImpl;
//	private Endereco endereco; 
//	private Bairro bairro;
//	private Cidade cidade;
//	private Paises pais;
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public ClienteDAOImpl<Cliente> getClienteDAOImpl() {
		return clienteDAOImpl;
	}

	public void setClienteDAOImpl(ClienteDAOImpl<Cliente> clienteDAOImpl) {
		this.clienteDAOImpl = clienteDAOImpl;
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

	public Collection<Cliente> getClientes() throws Exception {
		clientes = clienteDAOImpl.listar(getUnidade());
		return clientes;
	}

	public void setClientes(Collection<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<SelectItem> getListaCliente() throws Exception {
		listaCliente = new ArrayList<SelectItem>();
		ClienteDAOImpl<Cliente> clienteDAOImpl = new ClienteDAOImpl<Cliente>();
		Collection<Cliente> cliente = clienteDAOImpl.listar(getUnidade());
		for (Cliente c : cliente) {
			listaCliente.add(new SelectItem(c, c.getNome()));
		}
		return listaCliente;
	}
	
	public List<Cliente> autocompleteCliente(Object event) throws Exception{
		String cod = (String) event;
		ArrayList<Cliente>retorno = new ArrayList<Cliente>();
		ClienteDAOImpl<Cliente> clienteDAOImpl = new ClienteDAOImpl<Cliente>();
		Collection<Cliente> cliente = clienteDAOImpl.listar(getUnidade());
		for (Cliente c : cliente) {
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
		return "/tela/cliente/cadastrarCliente";
	}

	public String carregarAlterar() {
		cliente = clienteDAOImpl.carregar(idBusca);
		return "/tela/cliente/alterarCliente";
	}

	public String cadastrar() {
		if(valida()){
			cliente.setUnidade(getUnidade());
			clienteDAOImpl.salvar(cliente);
			limpar();
		}
		return "/tela/cliente/cadastrarCliente";
	}

	public String alterar() {
		if(valida()){
			clienteDAOImpl.salvarOuAtualizar(cliente);
			return "/tela/cliente/listarCliente";
		}else{
			return "/tela/cliente/alterarCliente";
		}
	}

	public void excluir() {
		cliente = clienteDAOImpl.carregar(idBusca);
		clienteDAOImpl.excluir(cliente);
	}
	
	public boolean valida(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
		
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
		cliente = null;
		cliente = new Cliente();
		disableEndereco = false;
		cliente.setUf(16);
		cliente.setAtivo(true);
		cliente.setSexo("M");
		cliente.setCespecial(true);
		cliente.setCcredito(true);
		cliente.setDependentes(0);
	}

	 public void relatorioGeral() throws Exception {
		 GenericRelatorio relatorio = new GenericRelatorio();
		 Map parametro = new HashMap();
		 parametro.put("unidade", getUnidade().getCodigo());
		 try {
			 relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioCliente.jasper", "salvarPdf", parametro);
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
	 }
	 
//	 public void carregarEndereco(){
//		endereco = enderecoDAOImpl.carregarEndereco(cliente.getCep().replaceAll("-", ""));
//		setFoco("#pais");
//		if(endereco != null){
//			setFoco("#numero");
//			disableEndereco = true;
//			bairro = bairroDAOImpl.carregar(endereco.getBairroCodigo());
//			cidade = cidadeDAOImpl.carregar(bairro.getCidadeCodigo());
//			
//			cliente.setLogradouro(endereco.getEnderecoLogradouro());
//			cliente.setBairro(bairro.getBairroDescricao());
//			cliente.setCidade(cidade.getCidadeDescricao());
//			cliente.setUf(cidade.getUfCodigo());
//			cliente.setPais("Brasil");
//		}else{
//			disableEndereco = false;
//		}
//	}
}

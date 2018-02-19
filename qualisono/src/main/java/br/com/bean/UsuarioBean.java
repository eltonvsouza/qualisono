package br.com.bean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import br.com.dao.UsuarioDAOImpl;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
public class UsuarioBean {
	private Usuario usuario;
	@Autowired
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	private Collection<Usuario> usuarios;
	private String senhaConfirmacao;
	private Integer idBusca;
	private String mensagem;
	private Boolean disable;
	private LoginBean loginBean;
	private String foco;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public UsuarioDAOImpl<Usuario> getUsuarioDAOImpl() {
		return usuarioDAOImpl;
	}	
	
	public void setUsuarioDAOImpl(UsuarioDAOImpl<Usuario> usuarioDAOImpl) {
		this.usuarioDAOImpl = usuarioDAOImpl;
	}
	
	@Secured("ROLE_ADMIN")
	public Collection<Usuario> getUsuarios() throws Exception {
		usuarios = usuarioDAOImpl.listar(getUnidade());
		return usuarios;
	}
	
	public void setUsuarios(Collection<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public int getIdBusca() {
		return idBusca;
	}
	
	public void setIdBusca(int idBusca) {
		this.idBusca = idBusca;
	}
	
	public String getSenhaConfirmacao() {
		return senhaConfirmacao;
	}

	public void setSenhaConfirmacao(String senhaConfirmacao) {
		this.senhaConfirmacao = senhaConfirmacao;
	}

	public String getMensagem() {
		return mensagem;
	}
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public Boolean getDisable() {
		return disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
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

	@Secured("ROLE_ADMIN")
	public String carregarCadastrar() {
		limpar();
		return "/tela/usuario/cadastrarUsuario";
	}
	
	@Secured("ROLE_ADMIN")
	public String carregarAlterar() {
		usuario = usuarioDAOImpl.carregar(idBusca);
		this.disable = true;
		this.foco = "#nome";
		return "/tela/usuario/alterarUsuario";
	}
	
	public String carregarAlterarSenha() {
		usuario = usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario());
		return "/tela/usuario/alterarSenha";
	}
	
	public String cadastrar(){
		if (valida()){
			if(validaSenha(usuario, senhaConfirmacao)){
				usuario.setUnidade(getUnidade());
				usuario.setSenha(encrypt(usuario.getSenha()));
				usuarioDAOImpl.salvar(usuario);
				limpar();
			}else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "AS SENHAS NÃO CONFEREM", "AS SENHAS NÃO CONFEREM!");
				FacesContext.getCurrentInstance().addMessage("cadastro", message);
			}
		}
		return "/tela/usuario/cadastrarUsuario";
	}
	
	public String alterar() {
		if(valida()){
			usuarioDAOImpl.salvarOuAtualizar(usuario);
			return "/tela/usuario/listarUsuario";
		}else{
			return "/tela/usuario/alterarUsuario";
		}
	}
	
	public String alterarSenha() {
		if(validaSenha(usuario, senhaConfirmacao)){
			usuario.setSenha(encrypt(usuario.getSenha()));
			usuarioDAOImpl.salvarOuAtualizar(usuario);
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "AS SENHAS NÃO CONFEREM", "AS SENHAS NÃO CONFEREM!");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
		}
		return "/tela/usuario/alterarSenha";
	}
	
	public void excluir(){
		usuario = usuarioDAOImpl.carregar(idBusca);
		usuarioDAOImpl.excluir(usuario);
	}
	
	public boolean valida(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Usuario>> constraintViolations = validator.validate(usuario);
		
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
	
	public boolean validaSenha(Usuario usuario, String senha){
		if(usuario.getSenha().equals(senha)){
			return true;
		}else{
			return false;
		}
	}
	
	public static String encrypt(String password) {   
		java.security.MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        md.update(password.getBytes());
        byte byteData[] = md.digest();
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	return hexString.toString();
	   }
	
	public void limpar(){
		usuario = null;
		usuario = new Usuario();
		foco = "#usuario";
//		usuario.setGrupo("ROLE_USER");
		this.disable = false;
	}
	
	public void relatorioGeral() throws Exception {
		GenericRelatorio relatorio = new GenericRelatorio();
		Map parametro = new HashMap();
		parametro.put("unidade", getUnidade().getCodigo());
		try {
			relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioUsuario.jasper", "exibirPdf", parametro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

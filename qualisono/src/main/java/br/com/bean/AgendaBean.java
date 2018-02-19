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
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.dao.AgendaDAOImpl;
import br.com.dao.UfDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Agenda;
import br.com.model.Uf;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;
import br.com.util.Mail;
import br.com.util.ScheduledJob;

@Component
@Scope("session")
public class AgendaBean {
	private Agenda agenda;
	private LoginBean loginBean;
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	@Autowired
	private AgendaDAOImpl<Agenda> agendaDAOImpl;
	private Collection<Agenda> agendas;
	private List<SelectItem> listaAgenda;
	private Integer idBusca;
	private String mensagem;
	
	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
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

	public AgendaDAOImpl<Agenda> getAgendaDAOImpl() {
		return agendaDAOImpl;
	}

	public void setAgendaDAOImpl(AgendaDAOImpl<Agenda> agendaDAOImpl) {
		this.agendaDAOImpl = agendaDAOImpl;
	}

	public Collection<Agenda> getAgendas() throws Exception {
		agendas = agendaDAOImpl.listar(getUnidade());
		return agendas;
	}

	public void setAgendas(Collection<Agenda> agendas) {
		this.agendas = agendas;
	}

	public List<SelectItem> getListaAgenda() throws Exception {
		listaAgenda = new ArrayList<SelectItem>();
		AgendaDAOImpl<Agenda> agendaDAOImpl = new AgendaDAOImpl<Agenda>();
		Collection<Agenda> agenda = agendaDAOImpl.listar(getUnidade());
		for (Agenda c : agenda) {
			listaAgenda.add(new SelectItem(c, c.getAssunto() + " - " + c.getDatahora() ));
		}
		return listaAgenda;
	}
	
	public List<Agenda> autocompleteAgenda(Object event) throws Exception{
		String cod = (String) event;
		ArrayList<Agenda>retorno = new ArrayList<Agenda>();
		AgendaDAOImpl<Agenda> agendaDAOImpl = new AgendaDAOImpl<Agenda>();
		Collection<Agenda> agenda = agendaDAOImpl.listar(getUnidade());
		for (Agenda c : agenda) {
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

	public Unidade getUnidade() {
		return usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario()).getUnidade();
	}
	
	public String carregarCadastrar() {
		limpar();
		return "/tela/agenda/cadastrarAgenda";
	}

	public String carregarAlterar() {
		agenda = agendaDAOImpl.carregar(idBusca);
		return "/tela/agenda/alterarAgenda";
	}

	public String cadastrar() {
		if(valida()){
			agenda.setUnidade(getUnidade());
			agendaDAOImpl.salvar(agenda);
			limpar();
		}
        return "/tela/agenda/cadastrarAgenda";
	}

	public String alterar() {
		if(valida()){
			agendaDAOImpl.salvarOuAtualizar(agenda);
			return "/tela/agenda/listarAgenda";
		}else{
			return "/tela/agenda/alterarAgenda";
		}
	}

	public void excluir() {
		agenda = agendaDAOImpl.carregar(idBusca);
		agendaDAOImpl.excluir(agenda);
	}
	
	public boolean valida(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Agenda>> constraintViolations = validator.validate(agenda);
		
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
		agenda = null;
		agenda = new Agenda();
	}
	
//	timetask # quartz ! # crontab
	public void enviarEmail(){
		
	}

	 public void relatorioGeral() throws Exception {
		 GenericRelatorio relatorio = new GenericRelatorio();
		 Map parametro = new HashMap();
		 parametro.put("unidade", getUnidade().getCodigo());
		 parametro.put("geral", "assuntoemail");
		 try {
			 relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioAgenda.jasper", "salvarPdf", parametro);
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
	 }
}

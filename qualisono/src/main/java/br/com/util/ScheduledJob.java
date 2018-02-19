package br.com.util;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.bean.LoginBean;
import br.com.dao.AgendaDAOImpl;
import br.com.dao.ConfiguracaoDAOImpl;
import br.com.dao.GeralDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Agenda;
import br.com.model.Configuracao;
import br.com.model.Geral;
import br.com.model.Unidade;
import br.com.model.Usuario;

@Component
public class ScheduledJob {
	private Date today = new Date();
//	private AgendaDAOImpl<Agenda> agendaDAOImpl;
	private ConfiguracaoDAOImpl<Configuracao> configuracaoDAOImpl;
	private LoginBean loginBean;
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	
//	Campo		Obrigatório?	Valores permitidos	Caracteres especiais permitidos
//	Segundos		Sim			0-59				* / , -
//	Minutos			Sim			0-59				* / , -
//	Horas			Sim			0-23				* / , -
//	Dia do mês		Sim			1-31				* / , - ? L W
//	Mês				Sim			1-12 ou JAN-DEC		* / , -
//	Dia da semana	Sim			0-6 ou SUN-SAT		* / , - ? L #
//	Ano				Não			1970–2099			* / , -
	
//	fixedRate = 5000 Executa tarefa a cada 5 segundos
//	fixedDelay = 5000 Executa tarefa com intervalos de 5 segundos entre o término de uma tarefa e início de outra
//	@Scheduled(fixedRate=5000)
//	@Scheduled(fixedDelay=30000)

	@Scheduled(cron="00 00 06 * * *")
	public void enviaEmail(){
		try {
			ConfiguracaoDAOImpl<Configuracao> configuracaoDAOImpl = new ConfiguracaoDAOImpl<Configuracao>();
			GeralDAOImpl<Geral> geralDAOImpl = new GeralDAOImpl<Geral>();
			AgendaDAOImpl<Agenda> agendaDAOImpl = new AgendaDAOImpl<Agenda>();
			if(!agendaDAOImpl.listarData(today).isEmpty()){
				Collection<Agenda> agenda = agendaDAOImpl.listarData(today);
				Unidade unidade = usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario()).getUnidade();
				for (Agenda c : agenda) {
					ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/applicationContext.xml");
					Mail mailService = (Mail) context.getBean("mail");
					String assunto = geralDAOImpl.carregarGrupoChave(unidade, "assuntoemail", c.getAssunto()).getDescricao();
					SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
					String dataHora = simpleFormat.format(c.getDatahora());
					mailService.sendMail(configuracaoDAOImpl.carregarGrupoTipo("sistema", "email").getValor().toString(), c.getEmail(), assunto + " - " + dataHora, "Data/Hora: " + dataHora + "\n\n" + c.getMensagem());
					//		mailService.sendAlertMail("Exception occurred");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

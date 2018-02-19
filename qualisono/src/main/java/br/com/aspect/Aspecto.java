package br.com.aspect;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class Aspecto {
	
//	@After("execution(* *.salvar(..)) throws java.io.IOException")
	@After("execution(* *.salvar(..))")
	public void afterInserir(){
		System.out.println("\n ##### CADASTRO EFETUADO COM SUCESSO ##### ");

		// Cria uma nova mensagem de informação para o JSF
//		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,	"CADASTRO EFETUADO COM SUCESSO", "CADASTRO EFETUADO COM SUCESSO!");
		// Adiciona a mensagem ao formulário de cadastro
//		FacesContext.getCurrentInstance().addMessage("cadastro", message);
	}

	@After("execution(* *.listar(..))")
	public void afterListar(){
		System.out.println("\n ##### LISTAGEM ##### ");
	}

	@After("execution(* *.salvarOuAtualizar(..))")
	public void afterAlterar(){
		System.out.println("\n ##### REGISTRO ALTERADO COM SUCESSO ##### ");
	}

	@After("execution(* *.excluir(..))")
	public void afterExcluir(){
		System.out.println("\n ##### REGISTRO EXCLUÍDO COM SUCESSO ##### ");
	}


//	@AfterThrowing(pointcut="execution(* *.salvar(..))", throwing="he")
//	public void afterErro(org.hibernate.exception.ConstraintViolationException he){
//		System.out.println("\n ##### ERRO AO EXECUTAR AÇÃO ##### " + he);
//	}
	
	@AfterThrowing(pointcut = "execution(* *.salvar(..))", throwing = "e")  
    public void checkError(Throwable e) {  
		System.out.println("\n ##### ERRO AO EFETUAR O CADASTRO ##### ");

		// Cria uma nova mensagem de informação para o JSF
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO AO EFETUAR O CADASTRO", "ERRO AO EFETUAR O CADASTRO!");
		// Adiciona a mensagem ao formulário de cadastro
		FacesContext.getCurrentInstance().addMessage("cadastro", message);
    }
//	org.hibernate.util.JDBCExceptionReporter
//	org.hibernate.event.def.AbstractFlushingEventListener
//	org.hibernate.exception.ConstraintViolationException
//	java.sql.BatchUpdateException
	
	
}
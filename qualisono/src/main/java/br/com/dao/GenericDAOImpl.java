package br.com.dao;

import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import br.com.dao.utility.HibernateUtility;
import br.com.model.Unidade;

@Service
public abstract class GenericDAOImpl<T> implements GenericDAO<T> {
	
	private SessionFactory sessionFactory;
	
	public T carregar(Integer id) {
		Session session = HibernateUtility.getSession();
		Object object = null;
		try {
			Query select = session
					.createQuery("SELECT c FROM " + getClasseConsulta().getName() + " c WHERE c.id = :id");
			select.setParameter("id", id);
			object = select.uniqueResult();
		} catch (HibernateException he) {
			he.printStackTrace();
		} finally {
			session.close();
			session.getSessionFactory().close();
			System.gc();
		}
		return (T) object;
	}

	public Collection<T> listar(Unidade unidade) throws Exception {

		Collection<T> lista = null;
		Session session = HibernateUtility.getSession();
		
		try {
			Query consulta = session.createQuery("from " + getClasseConsulta().getName() + " c WHERE c.unidade = :unidade");
			consulta.setParameter("unidade", unidade);
			lista = consulta.list();
		} catch (HibernateException he) {
			he.printStackTrace();
		} finally {
			session.close();
			session.getSessionFactory().close();
			System.gc();
		}
		return lista;
	}
	
	/**
	 * Retorna a classe que será utilizada para consulta ou listagem.
	 * @return a classe que será utilizada para consulta ou listagem.
	 */
	protected abstract Class<T> getClasseConsulta();
	
//	@Transactional
	public void salvar(T object){
		Session session = HibernateUtility.getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(object);
			tx.commit();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,	"CADASTRO EFETUADO COM SUCESSO", "CADASTRO EFETUADO COM SUCESSO!");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
		}catch (ConstraintViolationException e) {
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getErrorCode());
			System.out.println(e.getSQLException());
			FacesMessage message;
			if (e.getErrorCode() == 1062) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "DUPLICIDADE DE DADOS", "DUPLICIDADE DE DADOS!");
			}else{
				if (e.getErrorCode() == 1452) {
					message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "DEPENDÊNCIA DE DADOS", "DEPENDÊNCIA DE DADOS!");
				}else{
					message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO AO EFETUAR O CADASTRO", "ERRO AO EFETUAR O CADASTRO!");
				}
			}
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
		}catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO AO EFETUAR O CADASTRO", "ERRO AO EFETUAR O CADASTRO!");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
		}finally {
			session.close();
			session.getSessionFactory().close();
			System.gc();
		}
	}
	
	public void salvarOuAtualizar(T object) {
		Session session = HibernateUtility.getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.saveOrUpdate(object);
			tx.commit();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,	"ALTERAÇÃO EFETUADA COM SUCESSO", "ALTERAÇÃO EFETUADA COM SUCESSO!");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
		} catch (NonUniqueObjectException e) {
			session.merge(object);
			tx.rollback();
			System.out.println("\nERRO ALTERAR");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "REGISTRO NÃO PODE SER ALTERADO", "REGISTRO NÃO PODE SER ALTERADO!");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
		}
		// Envia as alterações do objeto para o banco e retira o objeto da
		// sessão.
		session.flush();
		session.evict(object);
		session.getSessionFactory().close();

	}
	
	public void excluir(T object) {
		Session session = HibernateUtility.getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.delete(object);
			tx.commit();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,	"REGISTRO EXCLUÍDO COM SUCESSO", "REGISTRO EXCLUÍDO COM SUCESSO!");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
		}catch (ConstraintViolationException e) {
			e.printStackTrace();
			tx.rollback();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "REGISTRO NÃO PODE SER EXCLUÍDO", "REGISTRO NÃO PODE SER EXCLUÍDO!");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
		} catch (HibernateException he) {
			he.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
			session.getSessionFactory().close();
			System.gc();
		}
		
	}
}

package br.com.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import br.com.dao.utility.HibernateUtility;
import br.com.model.Configuracao;

@Service
public class ConfiguracaoDAOImpl<T> extends GenericDAOImpl<T> implements ConfiguracaoDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Configuracao.class;
	}
	
	public List<String> getGrupo() {
		List<String> lista = null;
		Session session = HibernateUtility.getSession();
		try {
			Query select = session
					.createQuery("SELECT DISTINCT c.grupo FROM " + getClasseConsulta().getName() + " c");
			lista = select.list();
		} catch (HibernateException he) {
			he.printStackTrace();
		} finally {
			session.close();
			session.getSessionFactory().close();
			System.gc();
		}
		return lista;
	}
	
	public Collection<T> carregarGrupo(String grupo) {
		Collection<T> lista = null;
		Session session = HibernateUtility.getSession();
		try {
			Query select = session
					.createQuery("SELECT c FROM " + getClasseConsulta().getName() + " c WHERE c.grupo = :grupo");
			select.setParameter("grupo", grupo);
			lista = select.list();
		} catch (HibernateException he) {
			he.printStackTrace();
		} finally {
			session.close();
			session.getSessionFactory().close();
			System.gc();
		}
		return lista;
	}
	
	public T carregarGrupoTipo(String grupo, String tipo) {
		Session session = HibernateUtility.getSession();
		Object object = null;
		try {
			Query select = session.createQuery("SELECT c FROM " + getClasseConsulta().getName() + " c WHERE c.grupo = :grupo AND" +
							"																				c.tipo = :tipo");
			select.setParameter("grupo", grupo);
			select.setParameter("tipo", tipo);
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
	
}

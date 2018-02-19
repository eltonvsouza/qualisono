package br.com.dao;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import br.com.dao.utility.HibernateUtility;
import br.com.model.Geral;
import br.com.model.Unidade;

@Service
public class GeralDAOImpl<T> extends GenericDAOImpl<T> implements GeralDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Geral.class;
	}
	
	public Collection<T> listarGrupo(Unidade unidade, String grupo) throws Exception {
		Collection<T> lista = null;
		Session session = HibernateUtility.getSession();
		try {
			Query consulta = session.createQuery("from " + getClasseConsulta().getName() + " c WHERE c.grupo = :grupo AND" +
												 "													 c.unidade = :unidade");
			consulta.setParameter("grupo", grupo);
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
		
	public T carregarGrupoChave(Unidade unidade, String grupo, String chave) throws Exception {
		Session session = HibernateUtility.getSession();
		Object object = null;
		try {
			Query consulta = session.createQuery("from " + getClasseConsulta().getName() + " c WHERE c.grupo = :grupo AND" +
					"																				 c.chave = :chave AND" +
					"																				 c.unidade = :unidade");
			consulta.setParameter("grupo", grupo);
			consulta.setParameter("chave", chave);
			consulta.setParameter("unidade", unidade);
			object = consulta.uniqueResult();
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

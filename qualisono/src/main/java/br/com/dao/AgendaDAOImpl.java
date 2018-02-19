package br.com.dao;

import java.util.Collection;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import br.com.dao.utility.HibernateUtility;
import br.com.model.Agenda;

@Service
public class AgendaDAOImpl<T> extends GenericDAOImpl<T> implements AgendaDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Agenda.class;
	}
	
	public Collection<T> listarData(Date data) throws Exception {
		Collection<T> lista = null;
		Session session = HibernateUtility.getSession();
		try {
			Query consulta = session.createQuery("from " + getClasseConsulta().getName() + " c WHERE DATE_FORMAT(c.datahora, '%Y-%m-%d') = DATE_FORMAT(:data, '%Y-%m-%d')");
			consulta.setParameter("data", data);
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
}

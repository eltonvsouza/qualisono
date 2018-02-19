package br.com.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import br.com.dao.utility.HibernateUtility;
import br.com.model.Usuario;

@Service
public class UsuarioDAOImpl<T> extends GenericDAOImpl<T> implements UsuarioDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Usuario.class;
	}
	
	public T carregarUsuario(String login) {
		Session session = HibernateUtility.getSession();
		Object object = null;
		try {
			Query select = session
					.createQuery("SELECT c FROM " + getClasseConsulta().getName() + " c WHERE c.usuario = :login");
			select.setParameter("login", login);
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

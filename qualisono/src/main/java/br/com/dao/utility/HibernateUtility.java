package br.com.dao.utility;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class HibernateUtility {

	static private SessionFactory sessionFactory;
	static private Session session;

	static public Session getSession() {
		try {
			sessionFactory = new AnnotationConfiguration().configure(
					"/hibernate.cfg.xml").buildSessionFactory();
		} catch (Exception e) {
			sessionFactory = null;
			e.printStackTrace();
		}
		return session = sessionFactory.openSession();
	}
}


package br.com.dao;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import br.com.dao.utility.HibernateUtility;
import br.com.model.Fornecedor;
import br.com.model.Produto;
import br.com.model.Unidade;

@Service
public class ProdutoDAOImpl<T> extends GenericDAOImpl<T> implements ProdutoDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Produto.class;
	}
	public Collection<T> listarProdutoFornecedor(Unidade unidade, Fornecedor fornecedor) throws Exception {
		Collection<T> lista = null;
		Session session = HibernateUtility.getSession();
		try {
			Query consulta = session.createQuery("from " + getClasseConsulta().getName() + " c WHERE c.unidade = :unidade AND" +
												 "													 c.fornecedor = :fornecedor");
			consulta.setParameter("unidade", unidade);
			consulta.setParameter("fornecedor", fornecedor);
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

package br.com.dao;

import org.springframework.stereotype.Service;
import br.com.model.Venda;

@Service
public class VendaDAOImpl<T> extends GenericDAOImpl<T> implements VendaDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Venda.class;
	}
}

package br.com.dao;

import org.springframework.stereotype.Service;

import br.com.model.Compra;

@Service
public class CompraDAOImpl<T> extends GenericDAOImpl<T> implements CompraDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Compra.class;
	}
}

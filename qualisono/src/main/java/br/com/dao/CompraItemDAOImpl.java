package br.com.dao;

import org.springframework.stereotype.Service;

import br.com.model.CompraItem;

@Service
public class CompraItemDAOImpl<T> extends GenericDAOImpl<T> implements CompraItemDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return CompraItem.class;
	}
}

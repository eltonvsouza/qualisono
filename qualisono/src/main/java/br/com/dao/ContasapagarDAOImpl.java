package br.com.dao;

import org.springframework.stereotype.Service;

import br.com.model.Contasapagar;

@Service
public class ContasapagarDAOImpl<T> extends GenericDAOImpl<T> implements ContasapagarDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Contasapagar.class;
	}
}

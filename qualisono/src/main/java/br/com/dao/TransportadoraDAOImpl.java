package br.com.dao;

import org.springframework.stereotype.Service;

import br.com.model.Transportadora;

@Service
public class TransportadoraDAOImpl<T> extends GenericDAOImpl<T> implements TransportadoraDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Transportadora.class;
	}
}

package br.com.dao;

import org.springframework.stereotype.Service;

import br.com.model.TipoFuncionario;

@Service
public class TipoFuncionarioDAOImpl<T> extends GenericDAOImpl<T> implements TipoFuncionarioDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return TipoFuncionario.class;
	}
}

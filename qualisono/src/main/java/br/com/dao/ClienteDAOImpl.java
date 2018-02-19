package br.com.dao;

import org.springframework.stereotype.Service;
import br.com.model.Cliente;

@Service
public class ClienteDAOImpl<T> extends GenericDAOImpl<T> implements ClienteDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Cliente.class;
	}
}

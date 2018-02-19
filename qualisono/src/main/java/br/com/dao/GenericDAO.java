package br.com.dao;

import java.util.Collection;

import br.com.model.Unidade;

public abstract interface GenericDAO<T> {
	public abstract void salvar(T object);
	public abstract Collection<T> listar(Unidade unidade) throws Exception;
	public T carregar(Integer id);
	public abstract void salvarOuAtualizar(T object);
	public abstract void excluir(T object);
}

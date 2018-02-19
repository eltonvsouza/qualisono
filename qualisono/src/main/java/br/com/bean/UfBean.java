package br.com.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.dao.UfDAOImpl;
import br.com.model.Uf;

@Component
@Scope("session")
public class UfBean {

	private Uf uf;
	private UfDAOImpl<Uf> ufDAOImpl;
	private Collection<Uf> ufs;
	private List<SelectItem> listaUf;
	
	public Uf getUf() {
		return uf;
	}

	public void setUf(Uf uf) {
		this.uf = uf;
	}

	public UfDAOImpl<Uf> getUfDAOImpl() {
		return ufDAOImpl;
	}

	public void setUfDAOImpl(UfDAOImpl<Uf> ufDAOImpl) {
		this.ufDAOImpl = ufDAOImpl;
	}
	
	public Collection<Uf> getUfs() throws Exception{
		ufs = ufDAOImpl.listar();
		return ufs;
	}
	
	public void setUfs(Collection<Uf> ufs) {
		this.ufs = ufs;
	}
	
	public List<SelectItem> getListaUf() throws Exception {
		listaUf = new ArrayList<SelectItem>();
		UfDAOImpl<Uf> ufDAOImpl = new UfDAOImpl<Uf>();
		Collection<Uf> uf = ufDAOImpl.listar();
		for (Uf c : uf) {
			listaUf.add(new SelectItem(String.valueOf(c.getUfCodigo()), c.getUfSigla()));
		}
		return listaUf;
	}
}

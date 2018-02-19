package br.com.model;

// Generated 08/03/2012 15:36:20 by Hibernate Tools 3.2.4.GA

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Tipofuncionario generated by hbm2java
 */
@Entity
@Table(name = "tipofuncionario", catalog = "qualisono")
public class TipoFuncionario implements java.io.Serializable {

	private Integer codigo;
	private Unidade unidade;
	private String descricao;
	private double comissao;
	private int pontuacao;
	private Set<Funcionario> funcionarios = new HashSet<Funcionario>(0);

	public TipoFuncionario() {
	}

	public TipoFuncionario(Unidade unidade, double comissao, int pontuacao) {
		this.unidade = unidade;
		this.comissao = comissao;
		this.pontuacao = pontuacao;
	}

	public TipoFuncionario(Unidade unidade, String descricao, double comissao,
			int pontuacao, Set<Funcionario> funcionarios) {
		this.unidade = unidade;
		this.descricao = descricao;
		this.comissao = comissao;
		this.pontuacao = pontuacao;
		this.funcionarios = funcionarios;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "codigo", unique = true, nullable = false)
	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unidade", nullable = false)
	public Unidade getUnidade() {
		return this.unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	@Column(name = "descricao", length = 100)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "comissao", nullable = false, precision = 5)
	public double getComissao() {
		return this.comissao;
	}

	public void setComissao(double comissao) {
		this.comissao = comissao;
	}

	@Column(name = "pontuacao", nullable = false)
	public int getPontuacao() {
		return this.pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipofuncionario")
	public Set<Funcionario> getFuncionarios() {
		return this.funcionarios;
	}

	public void setFuncionarios(Set<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoFuncionario other = (TipoFuncionario) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}

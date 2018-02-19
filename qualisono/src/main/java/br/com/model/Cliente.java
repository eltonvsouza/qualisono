package br.com.model;

// Generated 08/03/2012 15:36:20 by Hibernate Tools 3.2.4.GA

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Cliente generated by hbm2java
 */
@Entity
@Table(name = "cliente", catalog = "qualisono")
public class Cliente implements java.io.Serializable {

	private Integer codigo;
	private Unidade unidade;
	private String nome;
	private String cpf;
	private Date datanascimento;
	private Date datacadastro;
	private String sexo;
	private Integer cnh;
	private String rg;
	private String orgaoemissor;
	private Date dataexpedrg;
	private String nomemae;
	private String naturalidade;
	private String ecivil;
	private Integer dependentes;
	private String telefone;
	private String telefone2;
	private String contato;
	private String email;
	private String cep;
	private Integer uf;
	private String cidade;
	private String bairro;
	private String logradouro;
	private String pais;
	private Integer numero;
	private String complemento;
	private String tiporesid;
	private Double valorresid;
	private Integer temporesid;
	private String empresa;
	private String cargo;
	private String profissao;
	private String cnpj;
	private String natocupacao;
	private String tipocliente;
	private boolean ativo;
	private String banco;
	private String agencia;
	private String nconta;
	private Boolean cespecial;
	private Date dtabertura;
	private Boolean ccredito;
	private String afinidade;
	private Set<Venda> vendas = new HashSet<Venda>(0);

	public Cliente() {
	}

	public Cliente(Unidade unidade, String nome, String cpf, String sexo,
			String nomemae, String tipocliente, boolean ativo, String banco,
			String agencia, String nconta) {
		this.unidade = unidade;
		this.nome = nome;
		this.cpf = cpf;
		this.sexo = sexo;
		this.nomemae = nomemae;
		this.tipocliente = tipocliente;
		this.ativo = ativo;
		this.banco = banco;
		this.agencia = agencia;
		this.nconta = nconta;
	}

	public Cliente(Unidade unidade, String nome, String cpf,
			Date datanascimento, Date datacadastro, String sexo, Integer cnh,
			String rg, String orgaoemissor, Date dataexpedrg, String nomemae,
			String naturalidade, String ecivil, Integer dependentes,
			String telefone, String telefone2, String contato, String email,
			String cep, Integer uf, String cidade, String bairro,
			String logradouro, String pais, Integer numero, String complemento,
			String tiporesid, Double valorresid, Integer temporesid,
			String empresa, String cargo, String profissao, String cnpj,
			String natocupacao, String tipocliente, boolean ativo,
			String banco, String agencia, String nconta, Boolean cespecial,
			Date dtabertura, Boolean ccredito, String afinidade,
			Set<Venda> vendas) {
		this.unidade = unidade;
		this.nome = nome;
		this.cpf = cpf;
		this.datanascimento = datanascimento;
		this.datacadastro = datacadastro;
		this.sexo = sexo;
		this.cnh = cnh;
		this.rg = rg;
		this.orgaoemissor = orgaoemissor;
		this.dataexpedrg = dataexpedrg;
		this.nomemae = nomemae;
		this.naturalidade = naturalidade;
		this.ecivil = ecivil;
		this.dependentes = dependentes;
		this.telefone = telefone;
		this.telefone2 = telefone2;
		this.contato = contato;
		this.email = email;
		this.cep = cep;
		this.uf = uf;
		this.cidade = cidade;
		this.bairro = bairro;
		this.logradouro = logradouro;
		this.pais = pais;
		this.numero = numero;
		this.complemento = complemento;
		this.tiporesid = tiporesid;
		this.valorresid = valorresid;
		this.temporesid = temporesid;
		this.empresa = empresa;
		this.cargo = cargo;
		this.profissao = profissao;
		this.cnpj = cnpj;
		this.natocupacao = natocupacao;
		this.tipocliente = tipocliente;
		this.ativo = ativo;
		this.banco = banco;
		this.agencia = agencia;
		this.nconta = nconta;
		this.cespecial = cespecial;
		this.dtabertura = dtabertura;
		this.ccredito = ccredito;
		this.afinidade = afinidade;
		this.vendas = vendas;
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

	@NotEmpty(message="Campo Nome: Obrigat�rio")
	@Length(min=3, max=200, message="Campo Nome: Tamanho m�nimo de 3 caracteres")
	@Column(name = "nome", nullable = false, length = 200)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "cpf", nullable = false, length = 14)
	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Past(message="Campo Data de Nascimento: Imposs�vel cadastrar datas futuras")
	@Temporal(TemporalType.DATE)
	@Column(name = "datanascimento", length = 10)
	public Date getDatanascimento() {
		return this.datanascimento;
	}

	public void setDatanascimento(Date datanascimento) {
		this.datanascimento = datanascimento;
	}

	@Past(message="Campo Data de Nascimento: Imposs�vel cadastrar datas futuras")
	@Temporal(TemporalType.DATE)
	@Column(name = "datacadastro", length = 10)
	public Date getDatacadastro() {
		return this.datacadastro;
	}

	public void setDatacadastro(Date datacadastro) {
		this.datacadastro = datacadastro;
	}

	@NotEmpty(message="Campo Sexo: Obrigat�rio")
	@Column(name = "sexo", nullable = false, length = 20)
	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@Column(name = "cnh")
	public Integer getCnh() {
		return this.cnh;
	}

	public void setCnh(Integer cnh) {
		this.cnh = cnh;
	}

	@Column(name = "rg", length = 10)
	public String getRg() {
		return this.rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	@Column(name = "orgaoemissor", length = 5)
	public String getOrgaoemissor() {
		return this.orgaoemissor;
	}

	public void setOrgaoemissor(String orgaoemissor) {
		this.orgaoemissor = orgaoemissor;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dataexpedrg", length = 10)
	public Date getDataexpedrg() {
		return this.dataexpedrg;
	}

	public void setDataexpedrg(Date dataexpedrg) {
		this.dataexpedrg = dataexpedrg;
	}

	@NotEmpty(message="Campo Nome da M�e: Obrigat�rio")
	@Length(min=3, max=200, message="Campo Nome da M�e: Tamanho m�nimo de 3 caracteres")
	@Column(name = "nomemae", nullable = false, length = 200)
	public String getNomemae() {
		return this.nomemae;
	}

	public void setNomemae(String nomemae) {
		this.nomemae = nomemae;
	}

	@Column(name = "naturalidade", length = 50)
	public String getNaturalidade() {
		return this.naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	@Column(name = "ecivil", length = 20)
	public String getEcivil() {
		return this.ecivil;
	}

	public void setEcivil(String ecivil) {
		this.ecivil = ecivil;
	}

	@Column(name = "dependentes")
	public Integer getDependentes() {
		return this.dependentes;
	}

	public void setDependentes(Integer dependentes) {
		this.dependentes = dependentes;
	}

	@Column(name = "telefone", length = 13)
	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Column(name = "telefone2", length = 13)
	public String getTelefone2() {
		return this.telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	@Column(name = "contato", length = 50)
	public String getContato() {
		return this.contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	@Email(message="Campo E-Mail: Inv�lido")
	@Column(name = "email", length = 20)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "cep", length = 11)
	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Column(name = "uf")
	public Integer getUf() {
		return this.uf;
	}

	public void setUf(Integer uf) {
		this.uf = uf;
	}

	@Column(name = "cidade", length = 100)
	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@Column(name = "bairro", length = 100)
	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	@Column(name = "logradouro", length = 100)
	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	@Column(name = "pais", length = 50)
	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	@Column(name = "numero")
	public Integer getNumero() {
		return this.numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	@Column(name = "complemento", length = 100)
	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@Column(name = "tiporesid", length = 20)
	public String getTiporesid() {
		return this.tiporesid;
	}

	public void setTiporesid(String tiporesid) {
		this.tiporesid = tiporesid;
	}

	@Column(name = "valorresid", precision = 9)
	public Double getValorresid() {
		return this.valorresid;
	}

	public void setValorresid(Double valorresid) {
		this.valorresid = valorresid;
	}

	@Column(name = "temporesid")
	public Integer getTemporesid() {
		return this.temporesid;
	}

	public void setTemporesid(Integer temporesid) {
		this.temporesid = temporesid;
	}

	@Column(name = "empresa", length = 100)
	public String getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	@Column(name = "cargo", length = 100)
	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	@Column(name = "profissao", length = 100)
	public String getProfissao() {
		return this.profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	@Column(name = "cnpj", length = 18)
	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Column(name = "natocupacao", length = 20)
	public String getNatocupacao() {
		return this.natocupacao;
	}

	public void setNatocupacao(String natocupacao) {
		this.natocupacao = natocupacao;
	}

	@NotEmpty(message="Campo Tipo do Cliente: Obrigat�rio")
	@Column(name = "tipocliente", nullable = false, length = 20)
	public String getTipocliente() {
		return this.tipocliente;
	}

	public void setTipocliente(String tipocliente) {
		this.tipocliente = tipocliente;
	}

	@NotNull(message="Campo Ativo: Obrigat�rio")
	@Column(name = "ativo", nullable = false)
	public boolean isAtivo() {
		return this.ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@NotEmpty(message="Campo Banco: Obrigat�rio")
	@Length(min=3, max=20, message="Campo Banco: Tamanho m�nimo de 3 caracteres")
	@Column(name = "banco", nullable = false, length = 20)
	public String getBanco() {
		return this.banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	@NotEmpty(message="Campo Ag�ncia: Obrigat�rio")
	@Length(min=3, max=15, message="Campo Ag�ncia: Tamanho m�nimo de 3 caracteres")
	@Column(name = "agencia", nullable = false, length = 15)
	public String getAgencia() {
		return this.agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	@NotEmpty(message="Campo Num Conta: Obrigat�rio")
	@Length(min=3, max=15, message="Campo Num Conta: Tamanho m�nimo de 3 caracteres")
	@Column(name = "nconta", nullable = false, length = 15)
	public String getNconta() {
		return this.nconta;
	}

	public void setNconta(String nconta) {
		this.nconta = nconta;
	}

	@Column(name = "cespecial")
	public Boolean getCespecial() {
		return this.cespecial;
	}

	public void setCespecial(Boolean cespecial) {
		this.cespecial = cespecial;
	}

	@Past(message="Campo Data de Abertura: Imposs�vel cadastrar datas futuras")
	@Temporal(TemporalType.DATE)
	@Column(name = "dtabertura", length = 10)
	public Date getDtabertura() {
		return this.dtabertura;
	}

	public void setDtabertura(Date dtabertura) {
		this.dtabertura = dtabertura;
	}

	@Column(name = "ccredito")
	public Boolean getCcredito() {
		return this.ccredito;
	}

	public void setCcredito(Boolean ccredito) {
		this.ccredito = ccredito;
	}

	
	@Column(name = "afinidade", length = 20)
	public String getAfinidade() {
		return this.afinidade;
	}

	public void setAfinidade(String afinidade) {
		this.afinidade = afinidade;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
	public Set<Venda> getVendas() {
		return this.vendas;
	}

	public void setVendas(Set<Venda> vendas) {
		this.vendas = vendas;
	}

}

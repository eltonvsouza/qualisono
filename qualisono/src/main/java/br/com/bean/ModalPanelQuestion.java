package br.com.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("session")
public class ModalPanelQuestion {

	private String mensagem;
	private String tipo;
	private UsuarioBean usuarioBean;
	private ConfiguracaoBean configuracaoBean;
	private ClienteBean clienteBean;
	private FuncionarioBean funcionarioBean;
	private FornecedorBean fornecedorBean;
	private ProdutoBean produtoBean;
	private CompraBean compraBean;
	private VendaBean vendaBean;
	private TransportadoraBean transportadoraBean;
	private AgendaBean agendaBean;
	private String entidade;

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	public FuncionarioBean getFuncionarioBean() {
		return funcionarioBean;
	}
	
	public void setFuncionarioBean(FuncionarioBean funcionarioBean) {
		this.funcionarioBean = funcionarioBean;
	}
	
	public FornecedorBean getFornecedorBean() {
		return fornecedorBean;
	}

	public void setFornecedorBean(FornecedorBean fornecedorBean) {
		this.fornecedorBean = fornecedorBean;
	}

	public ProdutoBean getProdutoBean() {
		return produtoBean;
	}

	public void setProdutoBean(ProdutoBean produtoBean) {
		this.produtoBean = produtoBean;
	}

	public ClienteBean getClienteBean() {
		return clienteBean;
	}

	public void setClienteBean(ClienteBean clienteBean) {
		this.clienteBean = clienteBean;
	}

	public UsuarioBean getUsuarioBean() {
		return usuarioBean;
	}

	public void setUsuarioBean(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}

	public CompraBean getCompraBean() {
		return compraBean;
	}

	public void setCompraBean(CompraBean compraBean) {
		this.compraBean = compraBean;
	}

	public VendaBean getVendaBean() {
		return vendaBean;
	}

	public void setVendaBean(VendaBean vendaBean) {
		this.vendaBean = vendaBean;
	}

	public TransportadoraBean getTransportadoraBean() {
		return transportadoraBean;
	}

	public void setTransportadoraBean(TransportadoraBean transportadoraBean) {
		this.transportadoraBean = transportadoraBean;
	}
	
	public AgendaBean getAgendaBean() {
		return agendaBean;
	}

	public void setAgendaBean(AgendaBean agendaBean) {
		this.agendaBean = agendaBean;
	}

	public ConfiguracaoBean getConfiguracaoBean() {
		return configuracaoBean;
	}

	public void setConfiguracaoBean(ConfiguracaoBean configuracaoBean) {
		this.configuracaoBean = configuracaoBean;
	}

	public void setaAtributos(){
		if(tipo.equals("Excluir")){
			setMensagem("Deseja excluir o registro?");
		}else{
			if(tipo.equals("Logout")){
				setMensagem("Deseja sair do sistema?");
			}
		}
	}

	public String getEntidade() {
		return entidade;
	}

	public void setEntidade(String entidade) {
		this.entidade = entidade;
	}

	public void acao(){
		if(tipo.equals("Excluir")){
			excluir();
		}else{
			if(tipo.equals("Logout")){
				System.out.println("\nLogout\n");
			}
		}
		limpaAtributos();
	}
	
	private void limpaAtributos() {
		mensagem = null;
		tipo = null;
	}

	private void excluir() {
		if(entidade.equals("fornecedor")){
			fornecedorBean.excluir();
		}else{
			if(entidade.equals("funcionario")){
				funcionarioBean.excluir();
			}else{
				if(entidade.equals("cliente")){
					clienteBean.excluir();
				}else{
					if(entidade.equals("usuario")){
						usuarioBean.excluir();
					}else{
						if(entidade.equals("produto")){
							produtoBean.excluir();
						}else{
							if(entidade.equals("compra")){
								compraBean.excluir();
							}else{
								if(entidade.equals("venda")){
									vendaBean.excluir();
								}else{
									if(entidade.equals("transportadora")){
										transportadoraBean.excluir();
									}else{
										if(entidade.equals("agenda")){
											agendaBean.excluir();
										}else{
//											if(entidade.equals("produto")){
//												produtoBean.excluir();
//											}else{
												if(entidade.equals("configuracao")){
													configuracaoBean.excluir();
												}
//											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}

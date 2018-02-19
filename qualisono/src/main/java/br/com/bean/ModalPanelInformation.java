package br.com.bean;


public class ModalPanelInformation {

	private String mensagem;
	private String tipo;

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
	
	public void setaAtributos(){
		if(tipo.equals("Cadastro")){
			setMensagem("Cadastro efetuado com sucesso.");
		}else{
			if(tipo.equals("Alteracao")){
				setMensagem("Registro alterado com sucesso.");
			}
		}
	}
}

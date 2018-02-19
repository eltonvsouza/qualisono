package br.com.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import br.com.dao.ConfiguracaoDAOImpl;
import br.com.model.Configuracao;

@Component
@Scope("session")
public class BackupRestoreBean {
	private ConfiguracaoDAOImpl<Configuracao> configuracaoDAOImpl;
	private String usuario;
	private String senha;
	private int uploadsAvailable = 1;
    private boolean autoUpload = true;
    private boolean useFlash = false;
	
    public ConfiguracaoDAOImpl<Configuracao> getConfiguracaoDAOImpl() {
		return configuracaoDAOImpl;
	}

	public void setConfiguracaoDAOImpl(
			ConfiguracaoDAOImpl<Configuracao> configuracaoDAOImpl) {
		this.configuracaoDAOImpl = configuracaoDAOImpl;
	}

	public String getUsuario() {
		return configuracaoDAOImpl.carregarGrupoTipo("bancodados", "usuario").getValor();
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return configuracaoDAOImpl.carregarGrupoTipo("bancodados", "senha").getValor();
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Secured("ROLE_ADMIN")
	public int getUploadsAvailable() {
		return uploadsAvailable;
	}

	public void setUploadsAvailable(int uploadsAvailable) {
		this.uploadsAvailable = uploadsAvailable;
	}

	public boolean isAutoUpload() {
		return autoUpload;
	}

	public void setAutoUpload(boolean autoUpload) {
		this.autoUpload = autoUpload;
	}

	public boolean isUseFlash() {
		return useFlash;
	}

	public void setUseFlash(boolean useFlash) {
		this.useFlash = useFlash;
	}

	@Secured("ROLE_ADMIN")
	public void backup(){
		SimpleDateFormat formatador = new SimpleDateFormat("ddMMyyyy");
    	Date today = new Date();
    	String data = formatador.format(today);
        String backup = configuracaoDAOImpl.carregarGrupoTipo("sistema", "backup").getValor();
        String tabelaBackup = configuracaoDAOImpl.carregarGrupoTipo("bancodados", "tabelaBackup").getValor();
        try{  
			byte[] bytes = new byte[1024];
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			Process p = Runtime.getRuntime().exec("cmd /c \"" + backup + "\" -u" + getUsuario() + " -p" + getSenha() + " -x -e qualisono " + tabelaBackup);
			InputStream in = p.getInputStream();
	    	OutputStream out = null;
			int size;
			response.setContentType("text/plain");
			response.addHeader("Content-Disposition", "attachment; filename=backup" + data + ".sql");
			ServletOutputStream servletOutputStream = response.getOutputStream();
			while ((size = in.read()) != -1){  
				servletOutputStream.write(size);
	        }
			response.setContentLength(size);
			servletOutputStream.flush();
			servletOutputStream.close();
			facesContext.responseComplete();
        } catch (IOException ex) {  
        	ex.printStackTrace();  
        }
        
    }
    
    public void restore(UploadEvent event){
    	String restore = configuracaoDAOImpl.carregarGrupoTipo("sistema", "restore").getValor();
    	UploadItem item = event.getUploadItem();
        try{  
            	Runtime.getRuntime().exec("cmd /c \"" + restore + "\" -u" + getUsuario() + " -p" + getSenha() + " qualisono < " + item.getFile().getPath());
        }catch (IOException ex){  
        	ex.printStackTrace();  
        }
    }
}

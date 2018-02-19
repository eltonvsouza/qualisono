package br.com.relatorio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;

import org.hibernate.Session;

import br.com.dao.utility.HibernateUtility;
import br.com.util.DotPrinter;
import br.com.util.PrintJobWatcher;

public class GenericRelatorio {

	public void gerarRelatorio(String jasper, String impressao, Map parametro) {
		InputStream relatorioJasper = getClass().getResourceAsStream(jasper);
		Session session = HibernateUtility.getSession();
		
//		Image logo = new ImageIcon(getClass().getResource("/imagem/logo.png")).getImage();
//		BufferedImage logo = ImageIO.read(new File("/br/com/relatorio/logo.png"));
//		ImageIcon logo = new ImageIcon("/imagem/logo.png");
		ImageIcon logo = new ImageIcon(getClass().getResource("/br/com/relatorio/logo.gif"));
		parametro.put("logo", logo.getImage());
		
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		
//		InputStream reportStream = facesContext.getExternalContext().getResourceAsStream(jasper);
		
		try {
//			ServletOutputStream servletOutputStream = response.getOutputStream();
//			JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, null, session.connection());
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(relatorioJasper, parametro, session.connection());
//			JasperViewer jasperviewer = new JasperViewer(print, false);  
//            jasperviewer.show();
			
			if (impressao.equals("exibirPdf"))
				exibirPdf(jasperPrint);
			else
				if(impressao.equals("salvarPdf"))
					salvarPdf(jasperPrint);
				else
					if(impressao.equals("imprimirPdf"))
						imprimirPdf(jasperPrint);
					else
						if (impressao.equals("imprimirTxt"))
							imprimirTxt(jasperPrint);
						else
							if (impressao.equals("salvarTxt"))
								salvarTxt(jasperPrint);
			
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			session.close();
			session.getSessionFactory().close();
			System.gc();
		}
	}

	private void imprimirPdf(JasperPrint jasperPrint) throws JRException {
		JasperPrintManager.printReport(jasperPrint,false); 
		
	}

	private void imprimirTxt(JasperPrint jasperPrint) throws JRException {
		
////	GERA ARQUIVO INCOMPLETO NA RAIZ DO SISTEMA (o buffer fica completo)
//		JRCsvExporter exporter = new JRCsvExporter();  
//		exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, " ");  
//		StringBuffer sBuffer = new StringBuffer();  
//		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);  
//		exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, sBuffer);
//		exporter.exportReport();
//System.out.println(sBuffer.toString());
//		DotPrinter dotPrinter = new DotPrinter();
////		dotPrinter.set(sBuffer.toString());
//		dotPrinter.port("recibo.txt");
//		dotPrinter.print();
		
		
////		NAO EXPORTA, EXIBE ERRO:
////			Character width in pixels or page width in characters must be specified and must be greater than zero.
//		JRExporter exporter = new JRTextExporter();
//		exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, " ");
//		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//		StringBuffer sBuffer = new StringBuffer();
//		exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, sBuffer);
//		exporter.exportReport();
//		System.out.println(sBuffer.toString());
		
//		IMPRESSAO APARECE NA LISTA (fila de impressao) MAS SOME  
//		http://members.shaw.ca/bsanders/printfromdos.htm
//		net  use  lpt1: \\computername\printersharename /persistent:yes
//		net  use  lpt1: \delete
		JRCsvExporter exporter = new JRCsvExporter();  
		exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, " ");  
		StringBuffer sBuffer = new StringBuffer();  
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);  
		exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, sBuffer);
		exporter.exportReport();
//		System.out.println(sBuffer.toString());
		FileOutputStream fos = null;
		PrintStream ps = null;
		try{ 
			fos = new FileOutputStream("LPT1:");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		ps = new PrintStream(fos);
		ps.print(sBuffer.toString());

		
////		http://pt.scribd.com/doc/36798641/101/Usando-impressoras-matriciais-diretamente
////		http://www.fabiosalvador.com.br/apostilas/apostilajava.pdf
//		PrintService impressora = null;
//		try {
//			DocFlavor df = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
//			PrintService[ ] ps = PrintServiceLookup.lookupPrintServices(df, null);
//			for (PrintService p: ps) {
//				System.out.println("Impressora encontrada: " + p.getName());
//				if (p.getName().contains("Text") || p.getName().contains("Generic") || p.getName().contains("Epson") || p.getName().contains("v6")) {
//					System.out.println("Impressora Selecionada: " + p.getName());
//					impressora = p;
//					break;
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (impressora == null) {
//			System.out.println("Nenhuma impressora matricial encontrada!");
//		} else {
//			JRCsvExporter exporter = new JRCsvExporter();
//			exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, " ");  
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
//			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);  
//			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
//			exporter.exportReport();
//			byte[] output = baos.toByteArray();
//			try {
//				DocPrintJob dpj = impressora.createPrintJob();
//				InputStream stream = new ByteArrayInputStream (output);
//				DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
//				Doc doc = new SimpleDoc(stream, flavor, null);
//				//VERIFICA QUANDO O TRABALHO ESTA COMPLETO
////                PrintJobWatcher pjDone = new PrintJobWatcher(dpj);
//				dpj.print(doc, null);
//				 //AGUARDA A CONCLUSAO DO TRABALHO
////                pjDone.waitForDone();
////                baos.close();
//
//			} catch (PrintException e) {
//				e.printStackTrace();
//			}
//		}

////		Abre janela (applet) para seleção de impressora
//		net.sf.jasperreports.engine.JasperManager.printPages(jasperPrint, 1, 11, true);
		
	}

	//	ABRE JANELA PARA SALVAR ARQUIVO TXT
	private void salvarTxt(JasperPrint jasperPrint) throws JRException, IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		JRCsvExporter exporter = new JRCsvExporter();  
		exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, " ");  
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);  
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);  
		exporter.exportReport();
		byte[] output = baos.toByteArray();  
		response.setContentType("text/plain");
		response.addHeader("Content-Disposition", "attachment; filename=recibo.txt");
		response.setContentLength(output.length);  
		ServletOutputStream ouputStream = response.getOutputStream();  
		ouputStream.write(output);  
		ouputStream.flush();
		ouputStream.close();
		facesContext.responseComplete();
	}

	private void exibirTxt(JasperPrint jasperPrint) throws JRException, IOException {
////		GERA  ARQUIVO NA RAIZ DO SISTEMA (arquivo fica em branco)
//		JRTextExporter exporterTxt = new JRTextExporter();
//		exporterTxt.setParameter(JRExporterParameter.JASPER_PRINT, print);
//		exporterTxt.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "teste.txt");  
//        exporterTxt.setParameter(JRTextExporterParameter.PAGE_HEIGHT,new Float(90));  
//        exporterTxt.setParameter(JRTextExporterParameter.PAGE_WIDTH,new Float(90));  
//        exporterTxt.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,new Float(90));  
//        exporterTxt.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,new Float(90));
//        try {
//			exporterTxt.exportReport();
//		} catch (JRException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
////		GERA ARQUIVO INCOMPLETO NA RAIZ DO SISTEMA (o buffer fica completo)
//		JRCsvExporter exporter = new JRCsvExporter();  
//		exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, " ");  
//		StringBuffer sBuffer = new StringBuffer();  
//		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);  
//		exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, sBuffer);
//		exporter.exportReport();
//System.out.println(sBuffer.toString());
//		DotPrinter dotPrinter = new DotPrinter();
//		dotPrinter.set(sBuffer.toString());
//		dotPrinter.port("recibo.txt");
//		dotPrinter.print();
		

////		IMPRESSAO APARECE NA LISTA (fila de impressao) MAS SOME  
////		http://members.shaw.ca/bsanders/printfromdos.htm
////		net  use  lpt1: \\computername\printersharename /persistent:yes
//		JRCsvExporter exporter = new JRCsvExporter();  
//		exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, " ");  
//		StringBuffer sBuffer = new StringBuffer();  
//		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);  
//		exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, sBuffer);
//		exporter.exportReport();
//System.out.println(sBuffer.toString());
//		FileOutputStream fos = null;
//		PrintStream ps = null;
//		try{ 
//			fos = new FileOutputStream("\\\\xps\\hp");
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		try {
//			ps = new PrintStream(fos);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		ps.print(sBuffer.toString());

		
////		http://pt.scribd.com/doc/36798641/101/Usando-impressoras-matriciais-diretamente
////		http://www.fabiosalvador.com.br/apostilas/apostilajava.pdf
//		PrintService impressora = null;
//		try {
//			DocFlavor df = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
//			PrintService[ ] ps = PrintServiceLookup.lookupPrintServices(df, null);
//			for (PrintService p: ps) {
//				System.out.println("Impressora encontrada: " + p.getName());
//				if (p.getName().contains("Text") || p.getName().contains("Generic") || p.getName().contains("Epson") || p.getName().contains("series")) {
//					System.out.println("Impressora Selecionada: " + p.getName());
//					impressora = p;
//					break;
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (impressora == null) {
//			System.out.println("Nenhuma impressora matricial encontrada!");
//		} else {
//			JRCsvExporter exporter = new JRCsvExporter();
//			exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, " ");  
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
//			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);  
//			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
//			exporter.exportReport();
//			byte[] output = baos.toByteArray();
//			try {
//				DocPrintJob dpj = impressora.createPrintJob();
//				InputStream stream = new ByteArrayInputStream (output);
//				DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
//				Doc doc = new SimpleDoc(stream, flavor, null);
//				//VERIFICA QUANDO O TRABALHO ESTA COMPLETO
////                PrintJobWatcher pjDone = new PrintJobWatcher(dpj);
//				dpj.print(doc, null);
//				 //AGUARDA A CONCLUSAO DO TRABALHO
////                pjDone.waitForDone();
////                baos.close();
//
//			} catch (PrintException e) {
//				e.printStackTrace();
//			}
//		}

//		Abre janela (applet) para seleção de impressora
//		net.sf.jasperreports.engine.JasperManager.printPages(print, 1, 11, true);
		
	}

	private void exibirPdf(JasperPrint jasperPrint) throws JRException, IOException {
		byte[] bytes = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
//		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
//		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		
//		String path = facesContext.getExternalContext().getRealPath("/");
//		response.sendRedirect(path + "\\relatorio.jsf");
		
		bytes = JasperExportManager.exportReportToPdf(jasperPrint);
		if(bytes.length <= 1000){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NÃO EXISTE DADOS PARA ESTE RELATÓRIO", "NÃO EXISTE DADOS PARA ESTE RELATÓRIO!");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
		}else{
//			Exibe janela para salvar o relatório
			response.setContentType("application/pdf"); //JasperViewer.viewReport(preencher, false);
//			response.addHeader("Content-Disposition", "attachment; filename=relatorio.pdf");
//	        response.setHeader("Cache-Control", "no-cache");
			response.setContentLength(bytes.length);
//			response.reset();
			
//			Exibe relatorio na tela
//            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            
//            Finaliza o processo de exibição do pdf
//            facesContext.getApplication().getStateManager().saveView(facesContext);
//            facesContext.responseComplete();

//            Gera o relatorio em PDF na raiz do projeto
//			String path = facesContext.getExternalContext().getRealPath("/");
//            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\relatorio.pdf");
            
			
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
			facesContext.responseComplete();
		}
	}

	private void salvarPdf(JasperPrint jasperPrint) throws IOException, JRException {
		byte[] bytes = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
//		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
//		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		
//		String path = facesContext.getExternalContext().getRealPath("/");
//		response.sendRedirect(path + "\\relatorio.jsf");
		
		bytes = JasperExportManager.exportReportToPdf(jasperPrint);
		if(bytes.length <= 1000){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "NÃO EXISTE DADOS PARA ESTE RELATÓRIO", "NÃO EXISTE DADOS PARA ESTE RELATÓRIO!");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
		}else{
//			Exibe janela para salvar o relatório
			response.setContentType("application/pdf"); //JasperViewer.viewReport(preencher, false);
			response.addHeader("Content-Disposition", "attachment; filename=relatorio.pdf");
//	        response.setHeader("Cache-Control", "no-cache");
			response.setContentLength(bytes.length);
//			response.reset();
			
//			Exibe relatorio na tela
//            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            
//            Finaliza o processo de exibição do pdf
//            facesContext.getApplication().getStateManager().saveView(facesContext);
//            facesContext.responseComplete();

//            Gera o relatorio em PDF na raiz do projeto
//			String path = facesContext.getExternalContext().getRealPath("/");
//            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\relatorio.pdf");
            
			
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
			facesContext.responseComplete();
		}
		
	}
}
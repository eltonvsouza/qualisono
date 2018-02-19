package br.com.util;

import java.io.FileNotFoundException; 
import java.io.FileOutputStream; 
import java.io.IOException; 
import java.io.PrintWriter; 
import java.util.ArrayList; 

/** 
 * Esta classe deve ser usada para controlar a impress�o em impressoras 
 * matriciais.
 * http://old.nabble.com/Impress%C3%A3o-de-etiquetas-td24515061.html
 * @author eltonvs@... 
 */ 
public class DotPrinter { 

    public enum Alignment { 
        left, 
        right 
    } 

    /** 
     * Use este m�todo para indicar a porta de comunica��o com a 
     * impressora. 
     * Podem ser as portas normais, como LPT1, LPT2, PRN, etc 
     * Pode ser tamb�m o mapeamento da impressora na rede, 
     * como \\SERVIDOR\IMPRESSORA, por exemplo. 
     * Ou ainda um arquivo qualquer, local (no servidor) como C:\TESTE.TXT 
     * ou um arquivo na rede, como \\MINHAESTACAO\MEUDISCO\TESTE.TXT 
     * @param port - porta e comunica��o com a impressora. 
     */ 
    public void port(String port) { 
        this.port = port; 
    } 

    /** 
     * Este m�todo deve ser chamado para iniciar uma nova p�gina. 
     */ 
    public void nextPage() { 
        pages().add(new char[rows][cols]); 
        row = 0; 
        col = 0; 
    } 

    public DotPrinter nextRow() { 
        row++; 
        col = 0; 
        return this; 
    } 

    /** 
     * Use este m�todo para indicar a linha a ser usada. 
     * @param row 
     * @return 
     */ 
    public DotPrinter row(int row) { 
        this.row = row; 
        col = 0; 
        return this; 
    } 

    /** 
     * Retorna a linha que est� sendo processada. 
     * @return 
     */ 
    public int row() { 
        if (row==0) { row = 1; } 
        return row; 
    } 

    /** 
     * Use este m�todo para indicar a linha a ser usada. 
     * @param col 
     * @return 
     */ 
    public DotPrinter col(int col) { 
        this.col = col; 
        return this; 
    } 

    /** 
     * Este m�todo serve para colocar um texto em uma determinada 
     * posi��o na p�gina. 
     * Observe que n�o h� quebra de linha, ou seja, 
     * apenas a linha atual � atualizada, se o texto 
     * ultrapassar a largura da p�gina, ser� ignorado. 
     * @param text - texto a ser impresso no local indicado. 
     * @return 
     */ 
    public DotPrinter set(String text) { 
        return set(text, Alignment.left); 
    } 

    /** 
     * Este m�todo serve para colocar um texto em uma determinada 
     * posi��o na p�gina. 
     * Observe que n�o h� quebra de linha, ou seja, 
     * apenas a linha atual � atualizada, se o texto 
     * ultrapassar a largura da p�gina, ser� ignorado. 
     * @param text - texto a ser impresso no local indicado. 
     * @param alignment 
     * @return 
     */ 
    public DotPrinter set(String text, Alignment alignment) { 
        int page = pages().size(); 
        if (page==0) { 
            nextPage(); 
            page = 1; 
        } 
        if (row==0) { row = 1; } 
        if (col==0) { col = 1; } 

        /** 
         * S� considera se estiver dentro da pagina. 
         */ 
        if (row<=rows) { 
            /** 
             * Processa cada um dos caracteres da string. 
             * At� o tamanho da string e desde que a linha 
             * enquanto a linha seja menor que o n�mero de linhas da p�gina 
             * e enquanto a coluna seja menor que o n�mero de colunas da p�gina. 
             */ 
            for (int i = 0; i<text.length(); i++) { 
                int l = row-1; 
                if (alignment==Alignment.left) { 
                    if (col+i<=cols) { 
                        int c = col-1+i; 
                        page(page)[l][c] = text.charAt(i); 
                    } 
                } 
                else { 
                    if (col-i>0) { 
                        int c = col-1-i; 
                        page(page)[l][c] = text.charAt(text.length()-1-i); 
                    } 
                } 
            } 
        } 
        return this; 
    } 

    /** 
     * Este m�todo deve ser usado para imprimir na impressora. 
     * Ele ir� abrir a conex�o com a impressora, imprimir todo relat�rio 
     * e fechar a conex�o. 
     * @throws energy.Problem 
     */ 
    public void print() { 
        openPrinter(); 
        try { 
            /** 
             * Processa cada uma das p�ginas do relat�rio. 
             */ 
            for (int page = 0; page < pages().size(); page++) { 
                /** 
                 * Processa cada uma das linhas da p�gina. 
                 */ 
                for (row = 0; row < rows; row++) { 

                    /** 
                     * Guarda uma linha inteira, que ser� impressa. 
                     */ 
                    char line[] = new char[cols]; 
                    for (int col = 0; col < cols; col++) { 
                        char character = page(page+1)[row][col]; 
                        if (character=='\0') { 
                            character = ' '; 
                        } 
                        line[col] = character;
                    } 

                    /** 
                     * Imprime a linha na impressora. 
                     */ 
                    printRow(line); 
                } 

//                /** 
//                 * Depois de imprimir todas as linhas, indica � impressora 
//                 * que esta deve ir p�ra a pr�xima p�gina, usando um form feed. 
//                 */ 
//                printer.write('\f'); 
            } 
        /** 
         * Para garantir que a conex�o com a impressora ser� fecha, 
         * mesmo que ocorra algum erro durante o processo. 
         */ 
        } finally { 
            closePrinter(); 
        } 
    } 

/******************************************************************************* 
 * Metodos e atributos privados. 
 */ 

    /** 
     * Este � a porta de comunica��o com a impressora. 
     */ 
    private String port; 

    /** 
     * Caso o su�rio n�o informe uma porta, o sistema ir� usar a LPT1: 
     * @return 
     */ 
    private String port() { 
        if (port==null) { return "LPT1:"; } 
        return port; 
    } 

    private ArrayList<char[][]> pages; 

    private ArrayList<char[][]> pages() { 
        if (pages==null) { 
             pages = new ArrayList<char[][]>(); 
        } 
        return pages; 
    } 

    /** 
     * N�mero de linhas da p�gina. 
     */ 
    private int rows = 66; 

    /** 
     * Largura da p�gina. N�mero de colunas da p�gina. 
     */ 
    private int cols = 80; 

    /** 
     * Este atributo indica a linha que est� sendo usada. 
     */ 
    private int row = 0; 

    /** 
     * Este atributo indica a coluna que est� sendo usada. 
     */ 
    private int col = 0; 

    /** 
     * Este m�todo retorna a p�gina indica. 
     * @return - p�gina solicitada. 
     */ 
    private char[][] page(int page) { 
        while (pages().size()<page) { nextPage(); } 
        return pages().get(page-1); 
    } 

    /** 
     * Este m�todo manda uma linha inteira para a impressora. 
     * @param row 
     */ 
    private void printRow(char line[]) { 
        /** 
         * Imprime a linha. 
         */ 
        printer.write(String.valueOf(line)); 

        /** 
         * E indica � impressora que este deve ir para a pr�xima linha, 
         * usando um row feed. 
         */ 
        printer.write('\r'); // 0D 
        printer.write('\n'); // 0A 
    } 

    /** 
     * Esta � o atributo que conecta com a impressora. 
     */ 
    private PrintWriter printer; 

    /** 
     * Este � o atributo que cria ao stream com a impressora. 
     */ 
    private FileOutputStream outputFile; 

    /** 
     * Este m�todo abre a conex�o com a impressora. 
     * @throws energy.Problem 
     */ 
    private void openPrinter()  { 
        try { 
            /** 
             * Usa a parta de sa�da definida pelo usu�rio. 
             */ 
            outputFile = new FileOutputStream(port()); 
        } catch (FileNotFoundException ex) { 
            ex.printStackTrace(); 
        } 
        printer = new PrintWriter(outputFile); 
    } 

    /** 
     * Este m�todo efetua o fechamento da conex�o com a impressora. 
     * @throws energy.Problem 
     */ 
    private void closePrinter()  { 
        if (printer != null) { 
            printer.close(); 
        } 
        if (outputFile != null) { 
            try { 
                outputFile.close(); 
            } catch (IOException ex) { 
                ex.printStackTrace(); 
            } 
        } 
    } 
} 
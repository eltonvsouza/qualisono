package br.com.util;

import java.io.FileNotFoundException; 
import java.io.FileOutputStream; 
import java.io.IOException; 
import java.io.PrintWriter; 
import java.util.ArrayList; 

/** 
 * Esta classe deve ser usada para controlar a impressão em impressoras 
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
     * Use este método para indicar a porta de comunicação com a 
     * impressora. 
     * Podem ser as portas normais, como LPT1, LPT2, PRN, etc 
     * Pode ser também o mapeamento da impressora na rede, 
     * como \\SERVIDOR\IMPRESSORA, por exemplo. 
     * Ou ainda um arquivo qualquer, local (no servidor) como C:\TESTE.TXT 
     * ou um arquivo na rede, como \\MINHAESTACAO\MEUDISCO\TESTE.TXT 
     * @param port - porta e comunicação com a impressora. 
     */ 
    public void port(String port) { 
        this.port = port; 
    } 

    /** 
     * Este método deve ser chamado para iniciar uma nova página. 
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
     * Use este método para indicar a linha a ser usada. 
     * @param row 
     * @return 
     */ 
    public DotPrinter row(int row) { 
        this.row = row; 
        col = 0; 
        return this; 
    } 

    /** 
     * Retorna a linha que está sendo processada. 
     * @return 
     */ 
    public int row() { 
        if (row==0) { row = 1; } 
        return row; 
    } 

    /** 
     * Use este método para indicar a linha a ser usada. 
     * @param col 
     * @return 
     */ 
    public DotPrinter col(int col) { 
        this.col = col; 
        return this; 
    } 

    /** 
     * Este método serve para colocar um texto em uma determinada 
     * posição na página. 
     * Observe que não há quebra de linha, ou seja, 
     * apenas a linha atual é atualizada, se o texto 
     * ultrapassar a largura da página, será ignorado. 
     * @param text - texto a ser impresso no local indicado. 
     * @return 
     */ 
    public DotPrinter set(String text) { 
        return set(text, Alignment.left); 
    } 

    /** 
     * Este método serve para colocar um texto em uma determinada 
     * posição na página. 
     * Observe que não há quebra de linha, ou seja, 
     * apenas a linha atual é atualizada, se o texto 
     * ultrapassar a largura da página, será ignorado. 
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
         * Só considera se estiver dentro da pagina. 
         */ 
        if (row<=rows) { 
            /** 
             * Processa cada um dos caracteres da string. 
             * Até o tamanho da string e desde que a linha 
             * enquanto a linha seja menor que o número de linhas da página 
             * e enquanto a coluna seja menor que o número de colunas da página. 
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
     * Este método deve ser usado para imprimir na impressora. 
     * Ele irá abrir a conexão com a impressora, imprimir todo relatório 
     * e fechar a conexão. 
     * @throws energy.Problem 
     */ 
    public void print() { 
        openPrinter(); 
        try { 
            /** 
             * Processa cada uma das páginas do relatório. 
             */ 
            for (int page = 0; page < pages().size(); page++) { 
                /** 
                 * Processa cada uma das linhas da página. 
                 */ 
                for (row = 0; row < rows; row++) { 

                    /** 
                     * Guarda uma linha inteira, que será impressa. 
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
//                 * Depois de imprimir todas as linhas, indica à impressora 
//                 * que esta deve ir pára a próxima página, usando um form feed. 
//                 */ 
//                printer.write('\f'); 
            } 
        /** 
         * Para garantir que a conexão com a impressora será fecha, 
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
     * Este é a porta de comunicação com a impressora. 
     */ 
    private String port; 

    /** 
     * Caso o suário não informe uma porta, o sistema irá usar a LPT1: 
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
     * Número de linhas da página. 
     */ 
    private int rows = 66; 

    /** 
     * Largura da página. Número de colunas da página. 
     */ 
    private int cols = 80; 

    /** 
     * Este atributo indica a linha que está sendo usada. 
     */ 
    private int row = 0; 

    /** 
     * Este atributo indica a coluna que está sendo usada. 
     */ 
    private int col = 0; 

    /** 
     * Este método retorna a página indica. 
     * @return - página solicitada. 
     */ 
    private char[][] page(int page) { 
        while (pages().size()<page) { nextPage(); } 
        return pages().get(page-1); 
    } 

    /** 
     * Este método manda uma linha inteira para a impressora. 
     * @param row 
     */ 
    private void printRow(char line[]) { 
        /** 
         * Imprime a linha. 
         */ 
        printer.write(String.valueOf(line)); 

        /** 
         * E indica à impressora que este deve ir para a próxima linha, 
         * usando um row feed. 
         */ 
        printer.write('\r'); // 0D 
        printer.write('\n'); // 0A 
    } 

    /** 
     * Esta é o atributo que conecta com a impressora. 
     */ 
    private PrintWriter printer; 

    /** 
     * Este é o atributo que cria ao stream com a impressora. 
     */ 
    private FileOutputStream outputFile; 

    /** 
     * Este método abre a conexão com a impressora. 
     * @throws energy.Problem 
     */ 
    private void openPrinter()  { 
        try { 
            /** 
             * Usa a parta de saída definida pelo usuário. 
             */ 
            outputFile = new FileOutputStream(port()); 
        } catch (FileNotFoundException ex) { 
            ex.printStackTrace(); 
        } 
        printer = new PrintWriter(outputFile); 
    } 

    /** 
     * Este método efetua o fechamento da conexão com a impressora. 
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
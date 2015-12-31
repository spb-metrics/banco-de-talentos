/****
 * Copyright 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008 C�mara dos Deputados, Brasil
 * 
 * Este arquivo � parte do programa TALENTOS - Banco de Talentos
 *
 * O TALENTOS � um software livre; voc� pode redistribu�-lo e/ou modific�-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); na vers�o 2 da Licen�a.
 *
 * Este programa � distribu�do na esperan�a que possa ser �til, mas SEM NENHUMA GARANTIA; sem uma garantia impl�cita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. Veja a Licen�a P�blica Geral GNU para maiores detalhes.
 * 
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU, sob o t�tulo "LICENCA.txt", junto com este programa, se n�o, escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 ***/

package br.gov.camara.negocio.bancotalentos.pojo;

/**
 * POJO para tabela de apoio de meta dados
 * 
 */
public class TabelaApoioMM
{
	// Vari�veis de inst�ncia
    public Integer intIdentificador;
    public String strNomeTabela;
    public TabelaApoioMM objTabelaApoioMestreMM;
    public String strNomeIdentificador;
    public String strNomeDescritor;
    public String strNomeIdentificadorTabelaMestre;

    /**
     * Obt�m o identificador
     * 
     * @return Integer Contendo o dado
     */
    public Integer getIdentificador()
    {
        return intIdentificador;
    }

    /**
     * Obt�m tabela de apoio mestre de meta dados
     * 
     * @return TabelaApoioMM Contendo o dado
     */
    public TabelaApoioMM getTabelaApoioMestreMM()
    {
        return objTabelaApoioMestreMM;
    }

    /**
     * Obt�m nome do descritor
     * 
     * @return String Contendo o dado
     */
    public String getNomeDescritor()
    {
        return strNomeDescritor;
    }

    /**
     * Obt�m nome do identificador da tabela
     * 
     * @return String Contendo o dado
     */
    public String getNomeIdentificador()
    {
        return strNomeIdentificador;
    }

    /**
     * Obt�m nome do identificador da tabela mestre
     * 
     * @return String Contendo o dado
     */
    public String getNomeIdentificadorTabelaMestre()
    {
        return strNomeIdentificadorTabelaMestre;
    }

    /**
     * Obt�m nome da tabela
     * 
     * @return String Contendo o dado
     */
    public String getNomeTabela()
    {
        return strNomeTabela;
    }

    /**
     * Preenche identificador
     * 
     * @param intIdentificador Com o dado desejado
     */
    public void setIdentificador(Integer intIdentificador)
    {
        this.intIdentificador = intIdentificador;
    }

    /**
     * Preenche tabela de apoio mestre de meta dados
     * 
     * @param objTabelaApoioMestreMM Com o dado desejado
     */
    public void setTabelaApoioMestreMM(TabelaApoioMM objTabelaApoioMestreMM)
    {
        this.objTabelaApoioMestreMM = objTabelaApoioMestreMM;
    }

    /**
     * Preenche o nome do descritor
     * 
     * @param strNomeDescritor Com o dado desejado
     */
    public void setNomeDescritor(String strNomeDescritor)
    {
        this.strNomeDescritor = strNomeDescritor;
    }

    /**
     * Preenche o identificador da tabela
     * 
     * @param strNomeIdentificador Com o dado desejado
     */
    public void setNomeIdentificador(String strNomeIdentificador)
    {
        this.strNomeIdentificador = strNomeIdentificador;
    }

    /**
     * Preenche o nome do identificador da tabela mestre
     * 
     * @param strNomeIdentificadorTabelaMestre Com o dado desejado
     */
    public void setNomeIdentificadorTabelaMestre(String strNomeIdentificadorTabelaMestre)
    {
        this.strNomeIdentificadorTabelaMestre = strNomeIdentificadorTabelaMestre;
    }

    /**
     * Preenche o nome da tabela
     * 
     * @param strNomeTabela Com o dado desejado
     */
    public void setNomeTabela(String strNomeTabela)
    {
        this.strNomeTabela = strNomeTabela;
    }

}

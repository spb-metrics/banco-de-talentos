/****
 * Copyright 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008 Câmara dos Deputados, Brasil
 * 
 * Este arquivo é parte do programa TALENTOS - Banco de Talentos
 *
 * O TALENTOS é um software livre; você pode redistribuí-lo e/ou modificá-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); na versão 2 da Licença.
 *
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU para maiores detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU, sob o título "LICENCA.txt", junto com este programa, se não, escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 ***/

package br.gov.camara.negocio.bancotalentos.pojo;

/**
 * POJO para tabela de apoio de meta dados
 * 
 */
public class TabelaApoioMM
{
	// Variáveis de instância
    public Integer intIdentificador;
    public String strNomeTabela;
    public TabelaApoioMM objTabelaApoioMestreMM;
    public String strNomeIdentificador;
    public String strNomeDescritor;
    public String strNomeIdentificadorTabelaMestre;

    /**
     * Obtém o identificador
     * 
     * @return Integer Contendo o dado
     */
    public Integer getIdentificador()
    {
        return intIdentificador;
    }

    /**
     * Obtém tabela de apoio mestre de meta dados
     * 
     * @return TabelaApoioMM Contendo o dado
     */
    public TabelaApoioMM getTabelaApoioMestreMM()
    {
        return objTabelaApoioMestreMM;
    }

    /**
     * Obtém nome do descritor
     * 
     * @return String Contendo o dado
     */
    public String getNomeDescritor()
    {
        return strNomeDescritor;
    }

    /**
     * Obtém nome do identificador da tabela
     * 
     * @return String Contendo o dado
     */
    public String getNomeIdentificador()
    {
        return strNomeIdentificador;
    }

    /**
     * Obtém nome do identificador da tabela mestre
     * 
     * @return String Contendo o dado
     */
    public String getNomeIdentificadorTabelaMestre()
    {
        return strNomeIdentificadorTabelaMestre;
    }

    /**
     * Obtém nome da tabela
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

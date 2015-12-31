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
 * POJO para grupo (de servidores)
 */
public class Estatistica
{
    
    //  Vari�veis de inst�ncia
    private int    intQuantidade;
    private String strDescricao;
    private int    intPessoa;
    private int    intNivel;
    private String strParametro;
    
    /**
     * Obt�m Quantidade
     * 
     * @return int Contendo o dado 
     * 
     */
    public int getQuantidade()
    {
        return intQuantidade;
    }

    /**
     * Obt�m Pessoa
     * 
     * @return int Contendo o dado 
     * 
     */
    public int getPessoa()
    {
        return intPessoa;
    }

    /**
     * Obt�m N�vel
     * 
     * @return int Contendo o dado 
     * 
     */
    public int getNivel()
    {
        return intNivel;
    }

    /**
     * Obt�m descri��o
     * 
     * @return String Contendo o dado 
     * 
     */
    public String getDescricao()
    {
        return strDescricao;
    }

    /**
     * Obt�m parametro
     * 
     * @return String Contendo o dado 
     * 
     */
    public String getParametro()
    {
        return strParametro;
    }

    /**
     * Preenche c�digo
     * 
     * @param int Com o dado desejado
     * 
     */
    public void setQuantidade(int intQuantidade)
    {
        this.intQuantidade = intQuantidade;
    }

    /**
     * Preenche pessoa
     * 
     * @param int Com o dado desejado
     * 
     */
    public void setPessoa(int intPessoa)
    {
        this.intPessoa = intPessoa;
    }

    /**
     * Preenche n�vel
     * 
     * @param int Com o dado desejado
     * 
     */
    public void setNivel(int intNivel)
    {
        this.intNivel = intNivel;
    }

    /**
     * Preenche descri��o
     * 
     * @param String Com o dado desejado
     * 
     */
    public void setDescricao(String strDescricao)
    {
        this.strDescricao = strDescricao;
    }

    /**
     * Preenche parametro
     * 
     * @param String Com o dado desejado
     * 
     */
    public void setParametro(String strParametro)
    {
        this.strParametro = strParametro;
    }

}

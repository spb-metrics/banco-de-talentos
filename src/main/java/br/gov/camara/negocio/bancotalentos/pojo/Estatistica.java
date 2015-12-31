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
 * POJO para grupo (de servidores)
 */
public class Estatistica
{
    
    //  Variáveis de instância
    private int    intQuantidade;
    private String strDescricao;
    private int    intPessoa;
    private int    intNivel;
    private String strParametro;
    
    /**
     * Obtém Quantidade
     * 
     * @return int Contendo o dado 
     * 
     */
    public int getQuantidade()
    {
        return intQuantidade;
    }

    /**
     * Obtém Pessoa
     * 
     * @return int Contendo o dado 
     * 
     */
    public int getPessoa()
    {
        return intPessoa;
    }

    /**
     * Obtém Nível
     * 
     * @return int Contendo o dado 
     * 
     */
    public int getNivel()
    {
        return intNivel;
    }

    /**
     * Obtém descrição
     * 
     * @return String Contendo o dado 
     * 
     */
    public String getDescricao()
    {
        return strDescricao;
    }

    /**
     * Obtém parametro
     * 
     * @return String Contendo o dado 
     * 
     */
    public String getParametro()
    {
        return strParametro;
    }

    /**
     * Preenche código
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
     * Preenche nível
     * 
     * @param int Com o dado desejado
     * 
     */
    public void setNivel(int intNivel)
    {
        this.intNivel = intNivel;
    }

    /**
     * Preenche descrição
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

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

import  br.gov.camara.negocio.comum.pojo.TipoHTML;
import java.util.Set;

/**
 * POJO para atributo de talento
 */
public class AtributoTalento
{
	// Vari�veis de inst�ncia
    public Integer intIdentificador;
    public AtributoTalento objAtributoTalentoPai;
    public String strNome;
    public TipoHTML objTipoHTML;
    public String strMascara;
    public TabelaApoioMM objTabelaApoioMM;
    public String strIndicativoPesquisa;
    public String strDescricaoPesquisa;
    public String strTipoDado;
    public Set setAtributoTalentoOpcoes;
     
    /**
     * Obt�m identificador
     * 
     * @return Integer Contendo o dado
     */
    public Integer getIdentificador()
    {
        return intIdentificador;
    }

    /**
     * Obt�m atributo pai
     * 
     * @return AtributoTalento Contendo o dado
     */
    public AtributoTalento getAtributoTalentoPai()
    {
        return objAtributoTalentoPai;
    }

    /**
     * Obt�m tabela de apoio de meta dado
     * 
     * @return TabelaApoioMM Contendo o dado
     */
    public TabelaApoioMM getTabelaApoioMM()
    {
        return objTabelaApoioMM;
    }

    /**
     * Obt�m tipo de html
     * 
     * @return TipoHTML Contendo o dado
     */
    public TipoHTML getTipoHTML()
    {
        return objTipoHTML;
    }

    /**
     * Obt�m descri��o de pesquisa
     * 
     * @return String Contendo o dado
     */
    public String getDescricaoPesquisa()
    {
        return strDescricaoPesquisa;
    }

    /**
     * Obt�m indicativo de pesquisa, indicando se o campo � do tipo POPUP, ou seja, se aparecer� uma imagem
     * (lupa) para se abrir uma tela de pesquisa. O campo de conter op��es.
     * 
     * @return String Contendo o dado
     */
    public String getIndicativoPesquisa()
    {
        return strIndicativoPesquisa;
    }

    /**
     * Obt�m m�scara
     * 
     * @return String Contendo o dado
     */
    public String getMascara()
    {
        return strMascara;
    }

    /**
     * Obt�m nome
     * 
     * @return String Contendo o dado
     */
    public String getNome()
    {
        return strNome;
    }

    /**
     * Obt�m tipo de dado
     * 
     * @return String Contendo o dado
     */
    public String getTipoDado()
    {
        return strTipoDado;
    }

    /**
     * Preenche identificador
     * 
     * @param Integer Com o dado desejado
     */
    public void setIdentificador(Integer intIdentificador)
    {
        this.intIdentificador = intIdentificador;
    }

    /**
     * Preenche atributo pai
     * 
     * @param objAtributoTalentoPai Com o dado desejado
     */
    public void setAtributoTalentoPai(AtributoTalento objAtributoTalentoPai)
    {
        this.objAtributoTalentoPai = objAtributoTalentoPai;
    }

    /**
     * Preenche tabela de apoio de meta dado 
     * 
     * @param objTabelaApoioMM Com o dado desejado
     */
    public void setTabelaApoioMM(TabelaApoioMM objTabelaApoioMM)
    {
        this.objTabelaApoioMM = objTabelaApoioMM;
    }

    /**
     * Preenche o tipo de html
     * 
     * @param objTipoHTML Com o dado desejado
     */
    public void setTipoHTML(TipoHTML objTipoHTML)
    {
        this.objTipoHTML = objTipoHTML;
    }

    /**
     * Preenche o indicativo de pesquisa 
     * 
     * @param strDescricaoPesquisa Com o dado desejado
     */
    public void setDescricaoPesquisa(String strDescricaoPesquisa)
    {
        this.strDescricaoPesquisa = strDescricaoPesquisa;
    }

    /**
     * Preenche o indicativo de pesquisa
     * 
     * @param strIndicativoPesquisa Com o dado desejado
     */
    public void setIndicativoPesquisa(String strIndicativoPesquisa)
    {
        this.strIndicativoPesquisa = strIndicativoPesquisa;
    }

    /**
     * Preenche a m�scara
     * 
     * @param strMascara Com o dado desejado
     */
    public void setMascara(String strMascara)
    {
        this.strMascara = strMascara;
    }

    /**
     * Preenche o nome
     * 
     * @param strNome Com o dado desejado
     */
    public void setNome(String strNome)
    {
        this.strNome = strNome;
    }

    /**
     * Preenche o tipo de dado
     * 
     * @param strTipoDado Preenche o tipo de dado
     */
    public void setTipoDado(String strTipoDado)
    {
        this.strTipoDado = strTipoDado;
    }

	/**
	 * Obt�m op��es do atributo 
	 * 
	 * @return Set Com os dados desejados
	 */
	public Set getAtributoTalentoOpcoes()
	{
		return setAtributoTalentoOpcoes;
	}

	/**
     * Preenche as op��es do atributo
     * 
	 * @param set
	 */
	public void setAtributoTalentoOpcoes(Set setAtributoTalentoOpcoes)
	{
        this.setAtributoTalentoOpcoes = setAtributoTalentoOpcoes;
	}
}

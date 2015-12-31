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

import  br.gov.camara.negocio.comum.pojo.TipoHTML;
import java.util.Set;

/**
 * POJO para atributo de talento
 */
public class AtributoTalento
{
	// Variáveis de instância
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
     * Obtém identificador
     * 
     * @return Integer Contendo o dado
     */
    public Integer getIdentificador()
    {
        return intIdentificador;
    }

    /**
     * Obtém atributo pai
     * 
     * @return AtributoTalento Contendo o dado
     */
    public AtributoTalento getAtributoTalentoPai()
    {
        return objAtributoTalentoPai;
    }

    /**
     * Obtém tabela de apoio de meta dado
     * 
     * @return TabelaApoioMM Contendo o dado
     */
    public TabelaApoioMM getTabelaApoioMM()
    {
        return objTabelaApoioMM;
    }

    /**
     * Obtém tipo de html
     * 
     * @return TipoHTML Contendo o dado
     */
    public TipoHTML getTipoHTML()
    {
        return objTipoHTML;
    }

    /**
     * Obtém descrição de pesquisa
     * 
     * @return String Contendo o dado
     */
    public String getDescricaoPesquisa()
    {
        return strDescricaoPesquisa;
    }

    /**
     * Obtém indicativo de pesquisa, indicando se o campo é do tipo POPUP, ou seja, se aparecerá uma imagem
     * (lupa) para se abrir uma tela de pesquisa. O campo de conter opções.
     * 
     * @return String Contendo o dado
     */
    public String getIndicativoPesquisa()
    {
        return strIndicativoPesquisa;
    }

    /**
     * Obtém máscara
     * 
     * @return String Contendo o dado
     */
    public String getMascara()
    {
        return strMascara;
    }

    /**
     * Obtém nome
     * 
     * @return String Contendo o dado
     */
    public String getNome()
    {
        return strNome;
    }

    /**
     * Obtém tipo de dado
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
     * Preenche a máscara
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
	 * Obtém opções do atributo 
	 * 
	 * @return Set Com os dados desejados
	 */
	public Set getAtributoTalentoOpcoes()
	{
		return setAtributoTalentoOpcoes;
	}

	/**
     * Preenche as opções do atributo
     * 
	 * @param set
	 */
	public void setAtributoTalentoOpcoes(Set setAtributoTalentoOpcoes)
	{
        this.setAtributoTalentoOpcoes = setAtributoTalentoOpcoes;
	}
}

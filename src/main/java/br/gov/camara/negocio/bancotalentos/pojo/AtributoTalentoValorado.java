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
 * POJO para valoração de atributo de talento
 */
public class AtributoTalentoValorado
{
	// Variáveis de instância
    private Integer intIdentificador;
    private Talento objTalento; 
    private CategoriaAtributoTalento objCategoriaAtributoTalento; 
    private String strValoracao; 
    private AtributoTalentoOpcao objAtributoTalentoOpcao; 

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
	 * Obtém opção de atributo de talento
	 * 
	 * @return AtributoTalentoOpcao Contendo o dado
	 */
	public AtributoTalentoOpcao getAtributoTalentoOpcao()
	{
		return objAtributoTalentoOpcao;
	}

	/**
	 * Obtém categoria/atributo
	 * 
	 * @return CategoriaAtributoTalento Contendo o dado
	 */
	public CategoriaAtributoTalento getCategoriaAtributoTalento()
	{
		return objCategoriaAtributoTalento;
	}

	/**
	 * Obtém talento
	 * 
	 * @return Talento Contendo o dado
	 */
	public Talento getTalento()
	{
		return objTalento;
	}

	/**
	 * Obtém valoração
	 * 
	 * @return String Contendo o dado
	 */
	public String getValoracao()
	{
		return strValoracao;
	}

	/**
	 * Preenche o identificador
	 * 
	 * @param intIdentificador Com o dado desejado
	 */
	public void setIdentificador(Integer intIdentificador)
	{
		this.intIdentificador = intIdentificador;
	}

	/**
	 * Preenche a opção
	 * 
	 * @param objAtributoTalentoOpcao Com o dado desejado
	 */
	public void setAtributoTalentoOpcao(AtributoTalentoOpcao objAtributoTalentoOpcao)
	{
		this.objAtributoTalentoOpcao = objAtributoTalentoOpcao;
	}

	/**
	 * Preenche a categoria/atributo
	 * 
	 * @param objCategoriaAtributoTalento Com o dado desejado
	 */
	public void setCategoriaAtributoTalento(CategoriaAtributoTalento objCategoriaAtributoTalento)
	{
		this.objCategoriaAtributoTalento = objCategoriaAtributoTalento;
	}

	/**
	 * Preenche o talento
	 * 
	 * @param objTalento Com o dado desejado
	 */
	public void setTalento(Talento objTalento)
	{
		this.objTalento = objTalento;
	}

	/**
	 * Preenche valoração
	 * 
	 * @param strValoracao Com o dado desejado
	 */
	public void setValoracao(String strValoracao)
	{
		this.strValoracao = strValoracao;
	}
}
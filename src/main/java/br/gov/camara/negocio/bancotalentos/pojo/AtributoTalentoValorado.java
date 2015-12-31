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
 * POJO para valora��o de atributo de talento
 */
public class AtributoTalentoValorado
{
	// Vari�veis de inst�ncia
    private Integer intIdentificador;
    private Talento objTalento; 
    private CategoriaAtributoTalento objCategoriaAtributoTalento; 
    private String strValoracao; 
    private AtributoTalentoOpcao objAtributoTalentoOpcao; 

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
	 * Obt�m op��o de atributo de talento
	 * 
	 * @return AtributoTalentoOpcao Contendo o dado
	 */
	public AtributoTalentoOpcao getAtributoTalentoOpcao()
	{
		return objAtributoTalentoOpcao;
	}

	/**
	 * Obt�m categoria/atributo
	 * 
	 * @return CategoriaAtributoTalento Contendo o dado
	 */
	public CategoriaAtributoTalento getCategoriaAtributoTalento()
	{
		return objCategoriaAtributoTalento;
	}

	/**
	 * Obt�m talento
	 * 
	 * @return Talento Contendo o dado
	 */
	public Talento getTalento()
	{
		return objTalento;
	}

	/**
	 * Obt�m valora��o
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
	 * Preenche a op��o
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
	 * Preenche valora��o
	 * 
	 * @param strValoracao Com o dado desejado
	 */
	public void setValoracao(String strValoracao)
	{
		this.strValoracao = strValoracao;
	}
}
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
public class TipoFiltroConsulta
{
    
    //  Vari�veis de inst�ncia
    private Integer intIdentificador;
    private String  strNomeTipoFiltroConsulta;
    private String  strNomeObjetoControlado;

	/**
	 * Obt�m o Identificador
	 * 
	 * @return Integer Contendo o dado
	 */
	public Integer getIdentificador() 
	{
		return intIdentificador;
	}
	
	/**
	 * Preenche Identificador
	 * 
	 * @param Integer Com o dado desejado
	 */
	public void setIdentificador(Integer intIdentificador) 
	{
		this.intIdentificador = intIdentificador;
	}
	
	/**
	 * Obt�m o nome do objeto controlado
	 * 
	 * @return String Contendo o dado
	 */
	public String getNomeObjetoControlado() 
	{
		return strNomeObjetoControlado;
	}
	
	/**
	 * Preenche o nome do objeto controlado
	 * 
	 * @param String Com o dado preenchido
	 */
	public void setNomeObjetoControlado(String strNomeObjetoControlado) 
	{
		this.strNomeObjetoControlado = strNomeObjetoControlado;
	}
	
	/**
	 * Obt�m o nome do tipo de filtro da consulta
	 * 
	 * @return String Contendo o dado
	 */
	public String getNomeTipoFiltroConsulta() 
	{
		return strNomeTipoFiltroConsulta;
	}
	
	/**
	 * Preenche o nome do tipo de filtro de consulta
	 * 
	 * @param String Com o dado preenchido
	 */
	public void setNomeTipoFiltroConsulta(String strNomeTipoFiltroConsulta) 
	{
		this.strNomeTipoFiltroConsulta = strNomeTipoFiltroConsulta;
	}
}

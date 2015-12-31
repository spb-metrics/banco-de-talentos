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
public class TipoFiltroConsulta
{
    
    //  Variáveis de instância
    private Integer intIdentificador;
    private String  strNomeTipoFiltroConsulta;
    private String  strNomeObjetoControlado;

	/**
	 * Obtém o Identificador
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
	 * Obtém o nome do objeto controlado
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
	 * Obtém o nome do tipo de filtro da consulta
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

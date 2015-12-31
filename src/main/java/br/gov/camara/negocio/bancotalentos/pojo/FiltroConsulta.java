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

import java.util.Set;

/**
 * POJO para grupo (de servidores)
 */
public class FiltroConsulta
{
    
    //  Variáveis de instância
    private Integer intIdentificador;
    private String  strNomeFiltroConsulta;
    private TipoFiltroConsulta objTipoFiltroConsulta;
    private Set     setGrupoCriterioConsulta;
    private Set     setFiltroConsultaUsuario;
    private Set     setFiltroConsultaGrupo;

	/**
	 * Obtém Identificador
	 * 
	 * @return Integer Contendo o dado
	 */
	public Integer getIdentificador() 
	{
		return intIdentificador;
	}
	
	/**
	 * Preenche o Identificador
	 * 
	 * @param Integer Contendo o dado
	 */
	public void setIdentificador(Integer intIdentificador) 
	{
		this.intIdentificador = intIdentificador;
	}
	
	/**
	 * Obtém o tipo de filtro para consulta
	 * 
	 * @return List Contendo o dado
	 */
	public TipoFiltroConsulta getTipoFiltroConsulta() 
	{
		return objTipoFiltroConsulta;
	}
	
	/**
	 * Preenche o tipo de filtro para consulta
	 * 
	 * @param List Contendo o dado
	 */
	public void setTipoFiltroConsulta(TipoFiltroConsulta objTipoFiltroConsulta) 
	{
		this.objTipoFiltroConsulta = objTipoFiltroConsulta;
	}
	
	/**
	 * Obtém os grupos de critérios de consulta
	 * 
	 * @return Set Contendo o dado
	 */
	public Set getGrupoCriterioConsulta() 
	{
		return setGrupoCriterioConsulta;
	}
	
	/**
	 * Obtém os filtros de consulta de usuário
	 * 
	 * @return Set Contendo o dado
	 */
	public Set getFiltroConsultaUsuario() 
	{
		return setFiltroConsultaUsuario;
	}

	/**
	 * Obtém os filtros de consulta de grupo
	 * 
	 * @return Set Contendo o dado
	 */
	public Set getFiltroConsultaGrupo() 
	{
		return setFiltroConsultaGrupo;
	}

	/**
	 * Preenche os grupos de critérios de consulta
	 * 
	 * @param Set Contendo o dado
	 */
	public void setGrupoCriterioConsulta(Set setGrupoCriterioConsulta) 
	{
		this.setGrupoCriterioConsulta = setGrupoCriterioConsulta;
	}

	/**
	 * Preenche os grupos de critérios de consulta
	 * 
	 * @param Set Contendo o dado
	 */
	public void setFiltroConsultaUsuario(Set setFiltroConsultaUsuario) 
	{
		this.setFiltroConsultaUsuario = setFiltroConsultaUsuario;
	}

	/**
	 * Preenche os grupos de critérios de consulta
	 * 
	 * @param Set Contendo o dado
	 */
	public void setFiltroConsultaGrupo(Set setFiltroConsultaGrupo) 
	{
		this.setFiltroConsultaGrupo = setFiltroConsultaGrupo;
	}
	
	
	/**
	 * Obtém o nome do filtro de consulta
	 * 
	 * @return String Contendo o dado
	 */
	public String getNomeFiltroConsulta() 
	{
		return strNomeFiltroConsulta;
	}
	
	/**
	 * Preenche o nome do filtro de consulta
	 * 
	 * @param String Contendo o dado
	 */
	public void setNomeFiltroConsulta(String strNomeFiltroConsulta) 
	{
		this.strNomeFiltroConsulta = strNomeFiltroConsulta;
	}
}

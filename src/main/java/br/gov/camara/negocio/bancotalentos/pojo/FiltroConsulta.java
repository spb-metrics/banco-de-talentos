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

import java.util.Set;

/**
 * POJO para grupo (de servidores)
 */
public class FiltroConsulta
{
    
    //  Vari�veis de inst�ncia
    private Integer intIdentificador;
    private String  strNomeFiltroConsulta;
    private TipoFiltroConsulta objTipoFiltroConsulta;
    private Set     setGrupoCriterioConsulta;
    private Set     setFiltroConsultaUsuario;
    private Set     setFiltroConsultaGrupo;

	/**
	 * Obt�m Identificador
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
	 * Obt�m o tipo de filtro para consulta
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
	 * Obt�m os grupos de crit�rios de consulta
	 * 
	 * @return Set Contendo o dado
	 */
	public Set getGrupoCriterioConsulta() 
	{
		return setGrupoCriterioConsulta;
	}
	
	/**
	 * Obt�m os filtros de consulta de usu�rio
	 * 
	 * @return Set Contendo o dado
	 */
	public Set getFiltroConsultaUsuario() 
	{
		return setFiltroConsultaUsuario;
	}

	/**
	 * Obt�m os filtros de consulta de grupo
	 * 
	 * @return Set Contendo o dado
	 */
	public Set getFiltroConsultaGrupo() 
	{
		return setFiltroConsultaGrupo;
	}

	/**
	 * Preenche os grupos de crit�rios de consulta
	 * 
	 * @param Set Contendo o dado
	 */
	public void setGrupoCriterioConsulta(Set setGrupoCriterioConsulta) 
	{
		this.setGrupoCriterioConsulta = setGrupoCriterioConsulta;
	}

	/**
	 * Preenche os grupos de crit�rios de consulta
	 * 
	 * @param Set Contendo o dado
	 */
	public void setFiltroConsultaUsuario(Set setFiltroConsultaUsuario) 
	{
		this.setFiltroConsultaUsuario = setFiltroConsultaUsuario;
	}

	/**
	 * Preenche os grupos de crit�rios de consulta
	 * 
	 * @param Set Contendo o dado
	 */
	public void setFiltroConsultaGrupo(Set setFiltroConsultaGrupo) 
	{
		this.setFiltroConsultaGrupo = setFiltroConsultaGrupo;
	}
	
	
	/**
	 * Obt�m o nome do filtro de consulta
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

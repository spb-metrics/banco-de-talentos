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

package br.gov.camara.negocio.autenticacaoperfil.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.AgrupadorPerfil;
import br.gov.camara.negocio.autenticacaoperfil.pojo.AgrupadorPerfilGrupo;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Grupo;
import br.gov.camara.negocio.autenticacaoperfil.pojo.PerfilSistema;
import br.gov.camara.negocio.exception.DAOException;
import br.gov.camara.negocio.util.Consulta;

/**
 * Classe DAO para a tabela Grupo
 */

public class AgrupadorPerfilGrupoDAO extends DAO
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(AgrupadorPerfilGrupoDAO.class);

    /**
     * Construtor sem argumentos
     *
     */
    public AgrupadorPerfilGrupoDAO()
    {
        super("AgrupadorPerfilGrupo");
    }

    /**
     * Construtor sem argumentos
     *
     */
    public AgrupadorPerfilGrupoDAO(String nomeConexao)
    {
        super("AgrupadorPerfilGrupo", nomeConexao);
    }

    /**
     * Obt�m todos os registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterTodos() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilGrupo agrupadorPerfilGrupo");
        strbConsulta.append(" ORDER BY agrupadorPerfilGrupo.identificador ASC");

        return super.obter(strbConsulta.toString());
    }

    /**
     * Obt�m um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public Object obterPelaChave(String strChave) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilGrupo agrupadorPerfilGrupo");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" agrupadorPerfilGrupo.identificador = ");
        strbConsulta.append(strChave);

        return (AgrupadorPerfilGrupo) obter(strbConsulta.toString()).get(0);
    }

    /**
     * Obt�m o total de registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalRegistros() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT COUNT(*)");
        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilGrupo agrupadorPerfilGrupo");

        // Recupera os dados
        return obterTotalRegistros(strbConsulta.toString());
    }

    /**
     * Obt�m os registros de determinada p�gina
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilGrupo agrupadorPerfilGrupo");
        strbConsulta.append(" ORDER BY agrupadorPerfilGrupo.identificador ASC");

        // Recupera os dados
        return obterPorPagina(intNumeroPagina, intMaximoPagina, strbConsulta.toString());
    }

    /**
     * Obt�m total de registros da consulta
     *
     * @param Consulta objeto contendo os filtros para consulta
     *  
     * @return List contendo o resultado da pesquisa
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalRegistros(Consulta objConsulta) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        // Declara��es
        int intRetorno = 0;

        try
        {
            String strFiltro = objConsulta.montarFiltro();

            // Monta query
            StringBuffer strbConsulta = new StringBuffer();

            strbConsulta.append(" SELECT COUNT(*)");
            strbConsulta.append(" FROM");
            strbConsulta.append(" AgrupadorPerfilGrupo agrupadorPerfilGrupo");

            if (!strFiltro.trim().equals(""))
            {
                strbConsulta.append(" WHERE");
                strbConsulta.append(strFiltro);
            }

            // Recupera os dados
            intRetorno = obterTotalRegistros(strbConsulta.toString());

            // Retorna
            return intRetorno;
        }
        catch (CDException cde)
        {
            throw new DAOException("Ocorreu um erro ao consultar registros na classe " + strNomeClasse, cde);
        }
    }

    /**
     * Exclui um registro
     *
     * @param Object POJO representando o objeto a ser exclu�do
     * 
     * @return String Contendo a consulta de exclus�o 
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public String excluirImpl(Object objAgrupadorPerfilGrupo) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilGrupo agrupadorPerfilGrupo");
        strbConsulta.append(" WHERE agrupadorPerfilGrupo.agrupadorPerfil.identificador = ");
        strbConsulta.append(((AgrupadorPerfilGrupo) objAgrupadorPerfilGrupo).getAgrupadorPerfil().getIdentificador().toString());

        return strbConsulta.toString();
    }

    public List obterGruposDisponiveis(AgrupadorPerfil objAgrupadorPerfil) throws DAOException
    {
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM Grupo grupo");
        strbConsulta.append(" WHERE grupo.codigo NOT IN ( ");
        strbConsulta.append("   SELECT agrupadorPerfilGrupo.grupo.codigo ");
        strbConsulta.append("   FROM AgrupadorPerfilGrupo agrupadorPerfilGrupo ");
        strbConsulta.append("   WHERE agrupadorPerfilGrupo.agrupadorPerfil.identificador = ");
        strbConsulta.append(objAgrupadorPerfil.getIdentificador().toString());
        strbConsulta.append(" )");
        strbConsulta.append(" ORDER BY grupo.codigo ASC");

        // Recupera os dados
        return obter(strbConsulta.toString());
    }

    public List obterGruposSelecionados(AgrupadorPerfil objAgrupadorPerfil) throws DAOException
    {
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append("   SELECT agrupadorPerfilGrupo.grupo ");
        strbConsulta.append("   FROM AgrupadorPerfilGrupo agrupadorPerfilGrupo ");
        strbConsulta.append("   WHERE agrupadorPerfilGrupo.agrupadorPerfil.identificador = ");
        strbConsulta.append(objAgrupadorPerfil.getIdentificador().toString());
        strbConsulta.append(" ORDER BY agrupadorPerfilGrupo.grupo.codigo ASC");

        // Recupera os dados
        return obter(strbConsulta.toString());
    }

    public List obterPeloGrupoEPerfilSistema(Grupo objGrupo, PerfilSistema objPerfilSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT agrupadorPerfilGrupo");
        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilGrupo agrupadorPerfilGrupo, PerfilSistemaAgrupadorPerfil perfilSistemaAgrupadorPerfil");
        strbConsulta.append(" WHERE agrupadorPerfilGrupo.agrupadorPerfil.identificador = perfilSistemaAgrupadorPerfil.agrupadorPerfil.identificador ");
        strbConsulta.append(" AND agrupadorPerfilGrupo.grupo.codigo = ");
        strbConsulta.append(objGrupo.getCodigo().toString());
        strbConsulta.append(" AND perfilSistemaAgrupadorPerfil.perfilSistema.identificador = ");
        strbConsulta.append(objPerfilSistema.getIdentificador().toString());

        return obter(strbConsulta.toString());
    }

}

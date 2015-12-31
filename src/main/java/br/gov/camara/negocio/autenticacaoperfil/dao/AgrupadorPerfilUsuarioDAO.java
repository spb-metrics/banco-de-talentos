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

package br.gov.camara.negocio.autenticacaoperfil.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.AgrupadorPerfil;
import br.gov.camara.negocio.autenticacaoperfil.pojo.AgrupadorPerfilUsuario;
import br.gov.camara.negocio.autenticacaoperfil.pojo.PerfilSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;
import br.gov.camara.negocio.exception.DAOException;
import br.gov.camara.negocio.util.Consulta;

/**
 * Classe DAO para a tabela Grupo
 */

public class AgrupadorPerfilUsuarioDAO extends DAO
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(AgrupadorPerfilUsuarioDAO.class);

    /**
     * Construtor sem argumentos
     *
     */
    public AgrupadorPerfilUsuarioDAO()
    {
        super("AgrupadorPerfilUsuario");
    }

    /**
     * Construtor sem argumentos
     *
     */
    public AgrupadorPerfilUsuarioDAO(String nomeConexao)
    {
        super("AgrupadorPerfilUsuario", nomeConexao);
    }

    /**
     * Obtém todos os registros
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
        strbConsulta.append(" AgrupadorPerfilUsuario agrupadorPerfilUsuario");
        strbConsulta.append(" ORDER BY agrupadorPerfilUsuario.identificador ASC");

        return super.obter(strbConsulta.toString());
    }

    /**
     * Obtém um registro a partir da chave
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
        strbConsulta.append(" AgrupadorPerfilUsuario agrupadorPerfilUsuario");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" agrupadorPerfilUsuario.identificador = ");
        strbConsulta.append(strChave);

        return (AgrupadorPerfilUsuario) obter(strbConsulta.toString()).get(0);
    }

    /**
     * Obtém o total de registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalRegistros(AgrupadorPerfilUsuario objAgrupadorPerfilUsuario) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT COUNT(*)");
        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilUsuario agrupadorPerfilUsuario");
        strbConsulta.append(" WHERE agrupadorPerfilUsuario.agrupadorPerfil.identificador =  ");
        strbConsulta.append(objAgrupadorPerfilUsuario.getAgrupadorPerfil().getIdentificador().toString());

        // Recupera os dados
        return obterTotalRegistros(strbConsulta.toString());
    }

    /**
     * Obtém os registros de determinada página
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPorPagina(AgrupadorPerfilUsuario objAgrupadorPerfilUsuario, int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilUsuario agrupadorPerfilUsuario");
        strbConsulta.append(" WHERE agrupadorPerfilUsuario.agrupadorPerfil.identificador =  ");
        strbConsulta.append(objAgrupadorPerfilUsuario.getAgrupadorPerfil().getIdentificador().toString());
        strbConsulta.append(" ORDER BY agrupadorPerfilUsuario.identificador ASC");

        // Recupera os dados
        return obterPorPagina(intNumeroPagina, intMaximoPagina, strbConsulta.toString());
    }

    /**
     * Obtém total de registros da consulta
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
        // Declarações
        int intRetorno = 0;

        try
        {
            String strFiltro = objConsulta.montarFiltro();

            // Monta query
            StringBuffer strbConsulta = new StringBuffer();

            strbConsulta.append(" SELECT COUNT(*)");
            strbConsulta.append(" FROM");
            strbConsulta.append(" AgrupadorPerfilUsuario agrupadorPerfilUsuario");

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
     * @param Object POJO representando o objeto a ser excluído
     * 
     * @return String Contendo a consulta de exclusão 
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public String excluirImpl(Object objAgrupadorPerfilUsuario) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilUsuario agrupadorPerfilUsuario");
        strbConsulta.append(" WHERE agrupadorPerfilUsuario.identificador = ");
        strbConsulta.append(((AgrupadorPerfilUsuario) objAgrupadorPerfilUsuario).getIdentificador().toString());

        return strbConsulta.toString();
    }

    public AgrupadorPerfilUsuario obterPeloUsuarioEAgrupadorPerfil(AgrupadorPerfilUsuario objAgrupadorPerfilUsuario) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilUsuario agrupadorPerfilUsuario");
        strbConsulta.append(" WHERE agrupadorPerfilUsuario.agrupadorPerfil.identificador = ");
        strbConsulta.append(objAgrupadorPerfilUsuario.getAgrupadorPerfil().getIdentificador().toString());
        strbConsulta.append(" AND agrupadorPerfilUsuario.usuarioSistema.identificador = ");
        strbConsulta.append(objAgrupadorPerfilUsuario.getUsuarioSistema().getIdentificador().toString());

        List lstConsulta = obter(strbConsulta.toString());

        if (lstConsulta != null && lstConsulta.size() > 0)
        {
            return (AgrupadorPerfilUsuario) lstConsulta.get(0);
        }

        return null;
    }

    public List obterPeloUsuarioEPerfilSistema(UsuarioSistema objUsuarioSistema, PerfilSistema objPerfilSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT agrupadorPerfilUsuario");
        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilUsuario agrupadorPerfilUsuario, PerfilSistemaAgrupadorPerfil perfilSistemaAgrupadorPerfil");
        strbConsulta.append(" WHERE agrupadorPerfilUsuario.agrupadorPerfil.identificador = perfilSistemaAgrupadorPerfil.agrupadorPerfil.identificador ");
        strbConsulta.append(" AND agrupadorPerfilUsuario.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuarioSistema.getIdentificador().toString());
        strbConsulta.append(" AND perfilSistemaAgrupadorPerfil.perfilSistema.identificador = ");
        strbConsulta.append(objPerfilSistema.getIdentificador().toString());

        return obter(strbConsulta.toString());
    }

    public List obterPeloPerfil(PerfilSistema objPerfilSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT agrupadorPerfilUsuario");
        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilUsuario agrupadorPerfilUsuario, PerfilSistemaAgrupadorPerfil perfilSistemaAgrupadorPerfil");
        strbConsulta.append(" WHERE agrupadorPerfilUsuario.agrupadorPerfil.identificador = perfilSistemaAgrupadorPerfil.agrupadorPerfil.identificador ");
        strbConsulta.append(" AND perfilSistemaAgrupadorPerfil.perfilSistema.identificador = ");
        strbConsulta.append(objPerfilSistema.getIdentificador().toString());

        return obter(strbConsulta.toString());
    }

    public List obterPeloAgrupador(AgrupadorPerfil objAgrupadorPerfil) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT agrupadorPerfilUsuario");
        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilUsuario agrupadorPerfilUsuario");
        strbConsulta.append(" WHERE agrupadorPerfilUsuario.agrupadorPerfil.identificador = ");
        strbConsulta.append(objAgrupadorPerfil.getIdentificador().toString());
        strbConsulta.append(" ORDER BY agrupadorPerfilUsuario.usuarioSistema.nome ");

        return obter(strbConsulta.toString());
    }

}

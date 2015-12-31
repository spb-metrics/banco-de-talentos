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
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioCriterioAcessoPerfil;
import br.gov.camara.negocio.exception.DAOException;
import br.gov.camara.negocio.util.Consulta;

/**
 * Classe DAO para a tabela Grupo
 */

public class UsuarioCriterioAcessoPerfilDAO extends DAO
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(UsuarioCriterioAcessoPerfilDAO.class);

    /**
     * Construtor sem argumentos
     *
     */
    public UsuarioCriterioAcessoPerfilDAO()
    {
        super("Usuário Critério Acesso Perfil");
    }

    /**
     * Construtor sem argumentos
     *
     */
    public UsuarioCriterioAcessoPerfilDAO(String nomeConexao)
    {
        super("Usuário Critério Acesso Perfil", nomeConexao);
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
        strbConsulta.append(" UsuarioCriterioAcessoPerfil usuarioCriterioAcessoPerfil");
        strbConsulta.append(" ORDER BY");
        strbConsulta.append(" usuarioCriterioAcessoPerfil.usuarioSistema.nome ASC");

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

        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioCriterioAcessoPerfil usuarioCriterioAcessoPerfil");
        strbConsulta.append(" WHERE usuarioCriterioAcessoPerfil.identificador = ");
        strbConsulta.append(strChave);

        return obter(strbConsulta.toString()).get(0);
    }

    /**
     * Obtém o total de registros
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

        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT COUNT(*)");
        strbConsulta.append(" FROM UsuarioCriterioAcessoPerfil usuarioCriterioAcessoPerfil");

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
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioCriterioAcessoPerfil usuarioCriterioAcessoPerfil");
        strbConsulta.append(" ORDER BY");
        strbConsulta.append(" usuarioCriterioAcessoPerfil.usuarioSistema.nome ASC");

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
            strbConsulta.append("SELECT COUNT(*) FROM UsuarioCriterioAcessoPerfil usuarioCriterioAcessoPerfil");
            if (!strFiltro.trim().equals(""))
            {
                strbConsulta.append(" WHERE ");
                strbConsulta.append(strFiltro);
            }

            // Recupera os dados
            intRetorno = obterTotalRegistros(strbConsulta.toString());
        }
        catch (CDException cde)
        {
            throw new DAOException("Ocorreu um erro ao consultar registros na classe " + strNomeClasse, cde);
        }

        // Retorna
        return intRetorno;
    }

    /**
     * Obtém todos os registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPeloPerfilUsuarioSistema(UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioCriterioAcessoPerfil usuarioCriterioAcessoPerfil");
        strbConsulta.append(" WHERE usuarioCriterioAcessoPerfil.criterioAcessoPerfil.perfilSistema.identificador = ");
        strbConsulta.append(objUsuarioCriterioAcessoPerfil.getCriterioAcessoPerfil().getPerfilSistema().getIdentificador().intValue());
        strbConsulta.append(" AND usuarioCriterioAcessoPerfil.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuarioCriterioAcessoPerfil.getUsuarioSistema().getIdentificador().intValue());

        return super.obter(strbConsulta.toString());
    }

    /**
     * Obtém todos os registros pelo perfil, usuário e pre-requisito
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPeloPerfilUsuarioSistemaPreRequisito(UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioCriterioAcessoPerfil usuarioCriterioAcessoPerfil");
        strbConsulta.append(" WHERE usuarioCriterioAcessoPerfil.criterioAcessoPerfil.perfilSistema.identificador = ");
        strbConsulta.append(objUsuarioCriterioAcessoPerfil.getCriterioAcessoPerfil().getPerfilSistema().getIdentificador().intValue());
        strbConsulta.append(" AND usuarioCriterioAcessoPerfil.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuarioCriterioAcessoPerfil.getUsuarioSistema().getIdentificador().intValue());
        if ("S".equals(objUsuarioCriterioAcessoPerfil.getCriterioAcessoPerfil().getPreRequisito()))
        {
            strbConsulta.append(" AND usuarioCriterioAcessoPerfil.criterioAcessoPerfil.preRequisito = 'S'");
        }
        else
        {
            strbConsulta.append(" AND usuarioCriterioAcessoPerfil.criterioAcessoPerfil.preRequisito <> 'S'");
        }

        return super.obter(strbConsulta.toString());
    }

    /**
     * Obtém todos os registros pelo perfil, usuário e pre-requisito
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPeloPerfilUsuarioSistemaPreRequisitoValorado(UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioCriterioAcessoPerfil usuarioCriterioAcessoPerfil");
        strbConsulta.append(" WHERE usuarioCriterioAcessoPerfil.criterioAcessoPerfil.perfilSistema.identificador = ");
        strbConsulta.append(objUsuarioCriterioAcessoPerfil.getCriterioAcessoPerfil().getPerfilSistema().getIdentificador().intValue());
        strbConsulta.append(" AND usuarioCriterioAcessoPerfil.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuarioCriterioAcessoPerfil.getUsuarioSistema().getIdentificador().intValue());
        strbConsulta.append(" AND usuarioCriterioAcessoPerfil.criterioAcessoPerfil.valorCriterioAcessoPerfil IS NOT NULL");
        strbConsulta.append(" AND usuarioCriterioAcessoPerfil.criterioAcessoPerfil.valorCriterioAcessoPerfil <> ''");
        if ("S".equals(objUsuarioCriterioAcessoPerfil.getCriterioAcessoPerfil().getPreRequisito()))
        {
            strbConsulta.append(" AND usuarioCriterioAcessoPerfil.criterioAcessoPerfil.preRequisito = 'S'");
        }
        else
        {
            strbConsulta.append(" AND usuarioCriterioAcessoPerfil.criterioAcessoPerfil.preRequisito <> 'S'");
        }

        return super.obter(strbConsulta.toString());
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
    public String excluirImpl(Object objPOJO) throws DAOException
    {
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil = ((UsuarioCriterioAcessoPerfil) objPOJO);

        strbConsulta.append(" FROM UsuarioCriterioAcessoPerfil usuarioCriterioAcessoPerfil");
        strbConsulta.append(" WHERE  usuarioCriterioAcessoPerfil.criterioAcessoPerfil.identificador = ");
        strbConsulta.append(objUsuarioCriterioAcessoPerfil.getCriterioAcessoPerfil().getIdentificador().intValue());
        if (objUsuarioCriterioAcessoPerfil.getUsuarioSistema() != null)
        {
            strbConsulta.append(" AND usuarioCriterioAcessoPerfil.usuarioSistema.identificador = ");
            strbConsulta.append(objUsuarioCriterioAcessoPerfil.getUsuarioSistema().getIdentificador());
        }

        return strbConsulta.toString();
    }

    public List obterPeloUsuarioCriterioAcesso(UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM UsuarioCriterioAcessoPerfil usuarioCriterioAcessoPerfil");
        strbConsulta.append(" WHERE  usuarioCriterioAcessoPerfil.criterioAcessoPerfil.identificador = ");
        strbConsulta.append(objUsuarioCriterioAcessoPerfil.getCriterioAcessoPerfil().getIdentificador().intValue());
        strbConsulta.append(" AND usuarioCriterioAcessoPerfil.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuarioCriterioAcessoPerfil.getUsuarioSistema().getIdentificador());

        return obter(strbConsulta.toString());
    }

}

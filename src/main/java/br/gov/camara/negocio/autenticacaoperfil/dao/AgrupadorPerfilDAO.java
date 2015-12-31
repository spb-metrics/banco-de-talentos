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

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.AgrupadorPerfil;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;
import br.gov.camara.negocio.exception.DAOException;
import br.gov.camara.negocio.util.Consulta;

/**
 * Classe DAO para a tabela Grupo
 */

public class AgrupadorPerfilDAO extends DAO
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(AgrupadorPerfilDAO.class);

    /**
     * Construtor sem argumentos
     *
     */
    public AgrupadorPerfilDAO()
    {
        super("AgrupadorPerfil");
    }

    /**
     * Construtor sem argumentos
     *
     */
    public AgrupadorPerfilDAO(String nomeConexao)
    {
        super("AgrupadorPerfil", nomeConexao);
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
        strbConsulta.append(" AgrupadorPerfil agrupadorPerfil");
        strbConsulta.append(" ORDER BY agrupadorPerfil.identificador ASC");

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
        strbConsulta.append(" AgrupadorPerfil agrupadorPerfil");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" agrupadorPerfil.identificador = ");
        strbConsulta.append(strChave);

        return (AgrupadorPerfil) obter(strbConsulta.toString()).get(0);
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

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT COUNT(*)");
        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfil agrupadorPerfil");

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
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfil agrupadorPerfil");
        strbConsulta.append(" ORDER BY agrupadorPerfil.identificador ASC");

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
            strbConsulta.append(" AgrupadorPerfil agrupadorPerfil");

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
    public String excluirImpl(Object objAgrupadorPerfil) throws DAOException
    {
        return " FROM AgrupadorPerfil agrupadorPerfil "
                + " WHERE agrupadorPerfil.identificador =  "
                + ((AgrupadorPerfil) objAgrupadorPerfil).getIdentificador();
    }

    /**
    * Método que pede a inicialização da propriedade AgrupadorPerfil, que é lazy
    * 
    * @param AgrupadorPerfil Classe que tem o atributo que vai ser recuperado
    * 
    * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
    * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
    */

    public void inicializarAgrupadorPerfil(AgrupadorPerfil objAgrupadorPerfil) throws DAOException
    {
        inicializarRelacionamento(objAgrupadorPerfil);
    }

    /**
    *  Método que pede a inicialização da propriedade AgrupadorPerfil, que é lazy
    * 
    * @param List  Lista de objetos que tem o atributo que vai ser recuperado
    * 
    * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
    * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
    */

    public void inicializarAgrupadorPerfil(List lstAgrupadorPerfil) throws DAOException
    {
        Iterator itrAgrupadorPerfil = lstAgrupadorPerfil.iterator();
        while (itrAgrupadorPerfil.hasNext())
        {
            inicializarRelacionamento(((AgrupadorPerfil) itrAgrupadorPerfil.next()));
        }
    }

    /**
     * Método que pede a inicialização da propriedade agrupadorPerfilAutorizado, que é lazy
     * 
     * @param AgrupadorPerfil Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarUsuariosAutorizados(AgrupadorPerfil objAgrupadorPerfil) throws DAOException
    {
        if (objAgrupadorPerfil.getUsuariosAutorizados() != null && objAgrupadorPerfil.getUsuariosAutorizados().size() > 0)
        {
            Iterator itrAgrupadorPerfil = objAgrupadorPerfil.getUsuariosAutorizados().iterator();
            while (itrAgrupadorPerfil.hasNext())
            {
                inicializarRelacionamento(((UsuarioSistema) itrAgrupadorPerfil.next()));
            }
        }
    }

    /**
     * Método que pede a inicialização da propriedade agrupadorPerfilAutorizado, que é lazy
     * 
     * @param List Lista de objetos que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarUsuariosAutorizados(List lstAgrupadorPerfil) throws DAOException
    {
        Iterator itrAgrupadorPerfil = lstAgrupadorPerfil.iterator();
        while (itrAgrupadorPerfil.hasNext())
        {
            inicializarUsuariosAutorizados(((AgrupadorPerfil) itrAgrupadorPerfil.next()));
        }
    }

    /**
     * Verifica a existência de algum usuário relacionado ao agrupador de perfil
     *
     * @param AgrupadorPerfil objeto contendo os filtros para consulta
     *  
     * @return List Contendo o resultado da consulta 
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterUsuariosAssociados(AgrupadorPerfil objAgrupadorPerfil) throws DAOException
    {
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilUsuario agrupadorPerfilUsuario");
        strbConsulta.append(" WHERE agrupadorPerfilUsuario.agrupadorPerfil.identificador = ");
        strbConsulta.append(objAgrupadorPerfil.getIdentificador().toString());

        return obter(strbConsulta.toString());
    }

    /**
     * Verifica a existência de algum sistema relacionado ao agrupador de perfil
     *
     * @param AgrupadorPerfil objeto contendo os filtros para consulta
     *  
     * @return List Contendo o resultado da consulta 
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterSistemasAssociados(AgrupadorPerfil objAgrupadorPerfil) throws DAOException
    {
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilSistema agrupadorPerfilSistema");
        strbConsulta.append(" WHERE agrupadorPerfilSistema.agrupadorPerfil.identificador = ");
        strbConsulta.append(objAgrupadorPerfil.getIdentificador().toString());

        return obter(strbConsulta.toString());
    }

    /**
     * Verifica a existência de algum grupo relacionado ao agrupador de perfil
     *
     * @param AgrupadorPerfil objeto contendo os filtros para consulta
     *  
     * @return List Contendo o resultado da consulta 
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterGruposAssociados(AgrupadorPerfil objAgrupadorPerfil) throws DAOException
    {
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilGrupo agrupadorPerfilGrupo");
        strbConsulta.append(" WHERE agrupadorPerfilGrupo.agrupadorPerfil.identificador = ");
        strbConsulta.append(objAgrupadorPerfil.getIdentificador().toString());

        return obter(strbConsulta.toString());
    }

    /**
     * Verifica a existência de algum grupo relacionado ao agrupador de perfil
     *
     * @param AgrupadorPerfil objeto contendo os filtros para consulta
     *  
     * @return List Contendo o resultado da consulta 
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPerfisAssociados(AgrupadorPerfil objAgrupadorPerfil) throws DAOException
    {
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" PerfilSistemaAgrupadorPerfil perfilSistemaAgrupadorPerfil");
        strbConsulta.append(" WHERE perfilSistemaAgrupadorPerfil.agrupadorPerfil.identificador = ");
        strbConsulta.append(objAgrupadorPerfil.getIdentificador().toString());

        return obter(strbConsulta.toString());
    }

    /**
     * Obtém lista de agrupadores em que o usuário é autorizado
     *
     * @param UsuarioSistema objeto contendo os filtros para consulta
     *  
     * @return List Contendo o resultado da consulta 
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterAgrupadoresDeAutorizadoPorPagina(UsuarioSistema objUsuarioSistema, int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT agrupadorPerfil");
        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfil agrupadorPerfil JOIN agrupadorPerfil.usuariosAutorizados usuarios");
        strbConsulta.append(" WHERE usuarios.identificador = ");
        strbConsulta.append(objUsuarioSistema.getIdentificador().toString());

        return obterPorPagina(intNumeroPagina, intMaximoPagina, strbConsulta.toString());
    }

    /**
     * Obtém o total de registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalRegistrosAgrupadoresDeAutorizado(UsuarioSistema objUsuarioSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT COUNT(*)");
        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfil agrupadorPerfil JOIN agrupadorPerfil.usuariosAutorizados usuarios");
        strbConsulta.append(" WHERE usuarios.identificador = ");
        strbConsulta.append(objUsuarioSistema.getIdentificador().toString());

        // Recupera os dados
        return obterTotalRegistros(strbConsulta.toString());
    }

}

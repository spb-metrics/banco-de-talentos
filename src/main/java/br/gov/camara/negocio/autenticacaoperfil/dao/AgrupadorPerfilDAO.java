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
        strbConsulta.append(" AgrupadorPerfil agrupadorPerfil");
        strbConsulta.append(" ORDER BY agrupadorPerfil.identificador ASC");

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
        strbConsulta.append(" AgrupadorPerfil agrupadorPerfil");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" agrupadorPerfil.identificador = ");
        strbConsulta.append(strChave);

        return (AgrupadorPerfil) obter(strbConsulta.toString()).get(0);
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
        strbConsulta.append(" AgrupadorPerfil agrupadorPerfil");

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
        strbConsulta.append(" AgrupadorPerfil agrupadorPerfil");
        strbConsulta.append(" ORDER BY agrupadorPerfil.identificador ASC");

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
     * @param Object POJO representando o objeto a ser exclu�do
     * 
     * @return String Contendo a consulta de exclus�o 
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
    * M�todo que pede a inicializa��o da propriedade AgrupadorPerfil, que � lazy
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
    *  M�todo que pede a inicializa��o da propriedade AgrupadorPerfil, que � lazy
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
     * M�todo que pede a inicializa��o da propriedade agrupadorPerfilAutorizado, que � lazy
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
     * M�todo que pede a inicializa��o da propriedade agrupadorPerfilAutorizado, que � lazy
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
     * Verifica a exist�ncia de algum usu�rio relacionado ao agrupador de perfil
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
     * Verifica a exist�ncia de algum sistema relacionado ao agrupador de perfil
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
     * Verifica a exist�ncia de algum grupo relacionado ao agrupador de perfil
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
     * Verifica a exist�ncia de algum grupo relacionado ao agrupador de perfil
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
     * Obt�m lista de agrupadores em que o usu�rio � autorizado
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
     * Obt�m o total de registros
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

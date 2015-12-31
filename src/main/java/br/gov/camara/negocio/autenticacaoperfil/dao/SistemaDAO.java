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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.AgrupadorPerfil;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Grupo;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Sistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;
import br.gov.camara.negocio.exception.DAOException;

/**
 * 
 * Classe DAO para perfil de sistema
 *
 */

public class SistemaDAO extends DAO
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(DAO.class);

    /**
     * Construtor sem argumentos
     *
     */
    public SistemaDAO()
    {
        super("Perfil de sistema");
    }

    /**
     * Construtor sem argumentos
     *
     */
    public SistemaDAO(String nomeConexao)
    {
        super("Perfil de sistema", nomeConexao);
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
        strbConsulta.append(" Sistema sistema");
        strbConsulta.append(" ORDER BY sistema.nome ASC");

        // Retorna
        return obter(strbConsulta.toString());
    }

    /**
     * Obt�m todos os registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterTodosAgrupadorNull() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" Sistema sistema");
        strbConsulta.append(" WHERE sistema.sistemaAgrupador IS NULL ");
        strbConsulta.append(" ORDER BY sistema.nome ASC");

        // Retorna
        return obter(strbConsulta.toString());
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
        strbConsulta.append(" Sistema sistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" sistema.identificador = ");
        strbConsulta.append(strChave);

        // Verifica se h� retorno
        List lstResultado = obter(strbConsulta.toString());
        if (!lstResultado.isEmpty())
            return (Sistema) lstResultado.get(0);
        else
        {
            return null;
        }
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
    public Object obterPelaChave(Sistema objSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" Sistema sistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" sistema.identificador = ");
        strbConsulta.append(objSistema.getIdentificador());

        // Verifica se h� retorno
        List lstResultado = obter(strbConsulta.toString());
        if (!lstResultado.isEmpty())
            return (Sistema) lstResultado.get(0);
        else
        {
            return null;
        }
    }

    /**
     * Obt�m um registro a partir do objeto controlado
     *
     * @param strObjetoControlado Objeto controlado do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public Sistema obterPeloObjetoControlado(String strObjetoControlado) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" Sistema sistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" sistema.objetoControlado = '");
        strbConsulta.append(strObjetoControlado);
        strbConsulta.append("'");

        // Verifica se h� retorno
        List lstResultado = obter(strbConsulta.toString());
        if (!lstResultado.isEmpty())
            return (Sistema) lstResultado.get(0);
        else
        {
            return null;
        }
    }

    /**
     * Obt�m um registro a partir do objeto controlado
     *
     * @param strObjetoControlado Objeto controlado do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public boolean isObjetoControladoExistente(Sistema objSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" Sistema sistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" sistema.objetoControlado = '");
        strbConsulta.append(objSistema.getObjetoControlado());
        strbConsulta.append("'");

        if (objSistema.getIdentificador() != null)
        {
            strbConsulta.append(" AND sistema.identificador != ");
            strbConsulta.append(objSistema.getIdentificador().intValue());
        }

        List lstResultado = obter(strbConsulta.toString());

        return (!lstResultado.isEmpty());
    }

    /**
     * Exclui um registro
     *
     * @param Object POJO representando o objeto a ser exclu�do
     * 
     * @return String Contendo a consulta de exclus�o 
     * 
     * @throws DAOException Se ocorrer algum erro
     * 
     */
    protected String excluirImpl(Object objPOJO) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" Sistema sistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" sistema.identificador = ");
        strbConsulta.append(((Sistema) objPOJO).getIdentificador());

        return strbConsulta.toString();
    }

    /**
     * Define o m�todo utilizado para contar os registros de uma consulta
     * 
     * @return int Contendo o total de registros da consulta especificada
     * 
     * @throws DAOException Se ocorrer algum erro
     * 
     */
    public int obterTotalRegistros(Sistema objSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT COUNT(*)");
        strbConsulta.append(" Sistema sistema");

        if (objSistema.getSistemaAgrupador() != null)
        {
            strbConsulta.append(" WHERE sistema.sistemaAgrupador.identificador = ");
            strbConsulta.append(objSistema.getSistemaAgrupador().getIdentificador().intValue());
        }
        else
        {
            strbConsulta.append(" WHERE sistema.sistemaAgrupador.identificador IS NULL");
        }

        // Recupera os dados
        return obterTotalRegistros(strbConsulta.toString());
    }

    /**
     * Obt�m os registros de determinada p�gina
     *
     * @param intNumeroPagina N�mero da p�gina a ser exibida
     * @param intMaximoPagina N�mero total de registros da p�gina
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina, Sistema objSistema) throws DAOException
    {

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" Sistema sistema");

        if (objSistema.getSistemaAgrupador() != null)
        {
            strbConsulta.append(" WHERE sistema.sistemaAgrupador.identificador = ");
            strbConsulta.append(objSistema.getSistemaAgrupador().getIdentificador().intValue());
        }
        else
        {
            strbConsulta.append(" WHERE sistema.sistemaAgrupador.identificador IS NULL");
        }

        strbConsulta.append(" ORDER BY sistema.descricao ASC");

        // Recupera os dados
        return obterPorPagina(intNumeroPagina, intMaximoPagina, strbConsulta.toString());
    }

    /**
     * Obt�m os registros pelo Sistema agrupador
     *
     * @param objSistema contendo os par�metros da pesquisa
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    public List obterPeloSistemaAgrupador(Sistema objSistema) throws DAOException
    {
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" Sistema sistema");

        if (objSistema.getSistemaAgrupador() == null)
        {
            strbConsulta.append(" WHERE sistema.sistemaAgrupador.identificador IS NULL");
        }
        else
        {
            strbConsulta.append(" WHERE sistema.sistemaAgrupador.identificador = ");
            strbConsulta.append(objSistema.getSistemaAgrupador().getIdentificador().intValue());
        }
        strbConsulta.append(" ORDER BY sistema.nome");

        // Recupera os dados
        return obter(strbConsulta.toString());
    }

    /**
     * Obt�m os registros pelo Sistema agrupador
     *
     * @param objSistema contendo os par�metros da pesquisa
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    public List obterPeloSistemaAgrupador(Integer intIdentificador) throws DAOException
    {
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" Sistema sistema");

        if (intIdentificador == null)
        {
            strbConsulta.append(" WHERE sistema.sistemaAgrupador.identificador IS NULL");
        }
        else
        {
            strbConsulta.append(" WHERE sistema.sistemaAgrupador.identificador = ");
            strbConsulta.append(intIdentificador.intValue());
        }
        strbConsulta.append(" ORDER BY sistema.nome");

        // Recupera os dados
        return obter(strbConsulta.toString());
    }

    /**
     * Obt�m os registros pelo Sistema agrupador
     *
     * @param objSistema contendo os par�metros da pesquisa
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    public List obterSistemasFilhos(Sistema objSistema) throws DAOException
    {
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" Sistema sistema");

        if (objSistema.getSistemaAgrupador() == null)
        {
            strbConsulta.append(" WHERE sistema.sistemaAgrupador.identificador IS NULL");
        }
        else
        {
            strbConsulta.append(" WHERE sistema.sistemaAgrupador.identificador = ");
            strbConsulta.append(objSistema.getIdentificador().intValue());
        }
        strbConsulta.append(" ORDER BY sistema.nome");

        // Recupera os dados
        return obter(strbConsulta.toString());
    }

    /**
     * Obt�m os registros pelo Sistema agrupador
     *
     * @param objSistema contendo os par�metros da pesquisa
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    public List obterPelaChaveAgrupadorNull(Sistema objSistema) throws DAOException
    {
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" Sistema sistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" sistema.identificador = ");
        strbConsulta.append(objSistema.getIdentificador().intValue());
        strbConsulta.append(" AND sistema.sistemaAgrupador IS NULL");

        // Recupera os dados
        return obter(strbConsulta.toString());
    }

    /**
     * Obt�m os registros pelo Sistema agrupador
     *
     * @param objSistema contendo os par�metros da pesquisa
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    public List obterSistemaAgrupadorNull() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" Sistema sistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" sistema.sistemaAgrupador IS NULL");

        // Recupera os dados
        return obter(strbConsulta.toString());
    }

    /**
     * M�todo que pede a inicializa��o da propriedade SistemaAgrupador, que � lazy
     * 
     * @param ConsultaPermissoes Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarSistemaAgrupador(Sistema objSistema) throws DAOException
    {
        inicializarRelacionamento(objSistema.getSistemaAgrupador());
    }

    /**
     *  M�todo que pede a inicializa��o da propriedade Sistema Agrupador, que � lazy
     * 
     * @param List  Lista de objetos que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarSistemaAgrupador(List lstSistemaAgrupador) throws DAOException
    {
        Iterator itrSistemaAgrupador = lstSistemaAgrupador.iterator();
        while (itrSistemaAgrupador.hasNext())
        {
            inicializarSistemaAgrupador(((Sistema) itrSistemaAgrupador.next()));
        }
    }

    /**
     * Obt�m os sistemas que o usu�rio informado tem acesso (considerando as datas de validade) 
     *
     * @param objUsuario contendo os par�metros da pesquisa
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    public List obterPorUsuario(UsuarioSistema objUsuario) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        Map mapSistemas = new TreeMap();

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT DISTINCT usuarioPerfilSistema.perfilSistema.sistema");
        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioPerfilSistema usuarioPerfilSistema");
        strbConsulta.append(" WHERE usuarioPerfilSistema.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuario.getIdentificador().intValue());
        strbConsulta.append(" AND usuarioPerfilSistema.perfilSistema.sistema.visibilidade != 'N'");
        strbConsulta.append(" AND usuarioPerfilSistema.perfilSistema.sistema.acesso IS NOT NULL");
        strbConsulta.append(" AND ( usuarioPerfilSistema.dataInicioValidade is null OR usuarioPerfilSistema.dataInicioValidade <= ");
        strbConsulta.append(obterCampoDataAtual());
        strbConsulta.append("     )");
        strbConsulta.append(" AND (usuarioPerfilSistema.dataTerminoValidade is null OR usuarioPerfilSistema.dataTerminoValidade >= ");
        strbConsulta.append(obterCampoDataAtual());
        strbConsulta.append("     )");
        strbConsulta.append(" ORDER BY usuarioPerfilSistema.perfilSistema.sistema.nome ASC");

        // Obt�m os sistemas habilitados para o usu�rio
        List lstSistemasUsuario = obter(strbConsulta.toString());

        // Como n�o existe a clausula UNION no HQL foi necess�rio separar as consultas de sistemas de usu�rio e 
        // os sistemas que pertencem ao grupo do usu�rio e colocar num MAP para n�o ter repeti��o.
        for (int i = 0; i < lstSistemasUsuario.size(); i++)
        {
            Sistema objSistema = (Sistema) lstSistemasUsuario.get(i);
            mapSistemas.put(objSistema.getObjetoControlado(), objSistema);
        }

        // Consulta os sistemas associados ao agrupador e que o usu�rio fa�a parte
        strbConsulta.setLength(0);
        strbConsulta.append(" SELECT agrupadorPerfilSistema.sistema");
        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilUsuario agrupadorPerfilUsuario, AgrupadorPerfilSistema agrupadorPerfilSistema ");
        strbConsulta.append(" WHERE agrupadorPerfilUsuario.agrupadorPerfil.identificador = agrupadorPerfilSistema.agrupadorPerfil.identificador ");
        strbConsulta.append(" AND agrupadorPerfilUsuario.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuario.getIdentificador());
        strbConsulta.append(" AND agrupadorPerfilSistema.sistema.visibilidade != 'N'");
        strbConsulta.append(" AND agrupadorPerfilSistema.sistema.acesso IS NOT NULL");
        strbConsulta.append(" ORDER BY agrupadorPerfilSistema.sistema.nome ASC");

        // Obt�m os sistemas habilitados para o usu�rio
        List lstSistemasAgrupadorUsuario = obter(strbConsulta.toString());

        // Como n�o existe a clausula UNION no HQL foi necess�rio separar as consultas de sistemas de usu�rio e 
        // os sistemas que pertencem ao grupo do usu�rio e colocar num MAP para n�o ter repeti��o.
        for (int i = 0; i < lstSistemasAgrupadorUsuario.size(); i++)
        {
            Sistema objSistema = (Sistema) lstSistemasAgrupadorUsuario.get(i);
            mapSistemas.put(objSistema.getObjetoControlado(), objSistema);
        }

        if (objUsuario.getGrupo() != null)
        {
            // Limpa StringBuffer
            strbConsulta.setLength(0);

            strbConsulta.append(" SELECT DISTINCT perfilSistema.sistema");
            strbConsulta.append(" FROM PerfilSistema perfilSistema");
            strbConsulta.append(" JOIN perfilSistema.grupos grupo");
            strbConsulta.append(" WHERE grupo.codigo = ");
            strbConsulta.append(objUsuario.getGrupo().getCodigo().intValue());
            strbConsulta.append(" AND perfilSistema.sistema.visibilidade != 'N'");
            strbConsulta.append(" AND perfilSistema.sistema.acesso IS NOT NULL");
            strbConsulta.append(" ORDER BY perfilSistema.sistema.nome ASC");

            // Obt�m os sistemas habilitados para o grupo do usu�rio
            List lstSistemasGrupo = obter(strbConsulta.toString());

            for (int i = 0; i < lstSistemasGrupo.size(); i++)
            {
                Sistema objSistema = (Sistema) lstSistemasGrupo.get(i);
                mapSistemas.put(objSistema.getObjetoControlado(), objSistema);
            }

            // Consulta os sistema habilitados para o grupo do agrupador e que o usu�rio fa�a parte
            strbConsulta.setLength(0);
            strbConsulta.append(" SELECT agrupadorPerfilSistema.sistema");
            strbConsulta.append(" FROM");
            strbConsulta.append(" AgrupadorPerfilGrupo agrupadorPerfilGrupo, AgrupadorPerfilSistema agrupadorPerfilSistema ");
            strbConsulta.append(" WHERE agrupadorPerfilGrupo.agrupadorPerfil.identificador = agrupadorPerfilSistema.agrupadorPerfil.identificador ");
            strbConsulta.append(" AND agrupadorPerfilGrupo.grupo.codigo = ");
            strbConsulta.append(objUsuario.getGrupo().getCodigo().intValue());
            strbConsulta.append(" AND agrupadorPerfilSistema.sistema.visibilidade != 'N'");
            strbConsulta.append(" AND agrupadorPerfilSistema.sistema.acesso IS NOT NULL");
            strbConsulta.append(" ORDER BY agrupadorPerfilSistema.sistema.nome ASC");

            // Obt�m os sistemas habilitados para o usu�rio
            List lstSistemasAgrupadorGrupo = obter(strbConsulta.toString());

            // Como n�o existe a clausula UNION no HQL foi necess�rio separar as consultas de sistemas de usu�rio e 
            // os sistemas que pertencem ao grupo do usu�rio e colocar num MAP para n�o ter repeti��o.
            for (int i = 0; i < lstSistemasAgrupadorGrupo.size(); i++)
            {
                Sistema objSistema = (Sistema) lstSistemasAgrupadorGrupo.get(i);
                mapSistemas.put(objSistema.getObjetoControlado(), objSistema);
            }

        }

        return Collections.list(Collections.enumeration(mapSistemas.values()));
    }

    /**
     * Obt�m os registros pelo usu�rio
     *
     * @param objUsuario contendo os par�metros da pesquisa
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    public List obterPorUsuario(UsuarioSistema objUsuario, boolean blnFiltrarVisibilidade) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        Map mapSistemas = new TreeMap();

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT DISTINCT usuarioPerfilSistema.perfilSistema.sistema ");
        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioPerfilSistema usuarioPerfilSistema");
        strbConsulta.append(" WHERE usuarioPerfilSistema.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuario.getIdentificador().intValue());

        if (blnFiltrarVisibilidade)
        {
            strbConsulta.append(" AND usuarioPerfilSistema.perfilSistema.sistema.visibilidade != 'N'");
            strbConsulta.append(" AND usuarioPerfilSistema.perfilSistema.sistema.acesso IS NOT NULL");
        }
        strbConsulta.append(" ORDER BY usuarioPerfilSistema.perfilSistema.sistema.nome ASC");

        // Obt�m os sistemas habilitados para o usu�rio
        List lstSistemasUsuario = obter(strbConsulta.toString());

        // Como n�o existe a clausula UNION no HQL foi necess�rio separar as consultas de sistemas de usu�rio e 
        // os sistemas que pertencem ao grupo do usu�rio e colocar num MAP para n�o ter repeti��o.
        for (int i = 0; i < lstSistemasUsuario.size(); i++)
        {
            Sistema objSistema = (Sistema) lstSistemasUsuario.get(i);
            mapSistemas.put(objSistema.getObjetoControlado(), objSistema);
        }
        if (objUsuario.getGrupo() != null)
        {
            // Limpa StringBuffer
            strbConsulta.setLength(0);

            strbConsulta.append(" SELECT DISTINCT perfilSistema.sistema ");
            strbConsulta.append(" FROM");
            strbConsulta.append(" PerfilSistema perfilSistema");
            strbConsulta.append(" JOIN perfilSistema.grupos grupo");
            strbConsulta.append(" WHERE grupo.codigo = ");
            strbConsulta.append(objUsuario.getGrupo().getCodigo().intValue());
            if (blnFiltrarVisibilidade)
            {
                strbConsulta.append(" AND perfilSistema.sistema.visibilidade != 'N'");
                strbConsulta.append(" AND perfilSistema.sistema.acesso IS NOT NULL");
            }
            strbConsulta.append(" ORDER BY perfilSistema.sistema.nome ASC");

            // Obt�m os sistemas habilitados para o grupo do usu�rio
            List lstSistemasGrupo = obter(strbConsulta.toString());

            for (int i = 0; i < lstSistemasGrupo.size(); i++)
            {
                Sistema objSistema = (Sistema) lstSistemasGrupo.get(i);
                mapSistemas.put(objSistema.getObjetoControlado(), objSistema);
            }
        }

        return Collections.list(Collections.enumeration(mapSistemas.values()));
    }

    /**
     * Obt�m os registros pelo usu�rio
     *
     * @param objUsuario contendo os par�metros da pesquisa
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    public List obterSistemasComunsEntreUsuarioComAcessoEUsuarioEhGestor(UsuarioSistema objUsuario, UsuarioSistema objUsuarioGestor) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        Map mapSistemas = new TreeMap();

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT DISTINCT perfilSistema.sistema ");
        strbConsulta.append(" FROM");
        strbConsulta.append(" UsuarioPerfilSistema usuarioPerfilSistema JOIN usuarioPerfilSistema.perfilSistema perfilSistema");
        strbConsulta.append(" WHERE usuarioPerfilSistema.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuario.getIdentificador().intValue());
        strbConsulta.append(" AND");
        strbConsulta.append(PerfilSistemaDAO.obterHqlUsuarioEhGestor(objUsuarioGestor));
        strbConsulta.append(" ORDER BY perfilSistema.sistema.nome ASC");

        // Obt�m os sistemas habilitados para o usu�rio
        List lstSistemasUsuario = obter(strbConsulta.toString());

        // Como n�o existe a clausula UNION no HQL foi necess�rio separar as consultas de sistemas de usu�rio e 
        // os sistemas que pertencem ao grupo do usu�rio e colocar num MAP para n�o ter repeti��o.
        for (int i = 0; i < lstSistemasUsuario.size(); i++)
        {
            Sistema objSistema = (Sistema) lstSistemasUsuario.get(i);
            mapSistemas.put(objSistema.getObjetoControlado(), objSistema);
        }

        if (objUsuario.getGrupo() != null)
        {
            // Limpa StringBuffer
            strbConsulta.setLength(0);

            strbConsulta.append(" SELECT DISTINCT perfilSistema.sistema ");
            strbConsulta.append(" FROM");
            strbConsulta.append(" PerfilSistema perfilSistema ");
            strbConsulta.append(" JOIN perfilSistema.grupos grupo");
            strbConsulta.append(" WHERE grupo.codigo = ");
            strbConsulta.append(objUsuario.getGrupo().getCodigo().intValue());
            strbConsulta.append(" AND");
            strbConsulta.append(PerfilSistemaDAO.obterHqlUsuarioEhGestor(objUsuarioGestor));
            strbConsulta.append(" ORDER BY perfilSistema.sistema.nome ASC");

            // Obt�m os sistemas habilitados para o grupo do usu�rio
            List lstSistemasGrupo = obter(strbConsulta.toString());

            for (int i = 0; i < lstSistemasGrupo.size(); i++)
            {
                Sistema objSistema = (Sistema) lstSistemasGrupo.get(i);
                mapSistemas.put(objSistema.getObjetoControlado(), objSistema);
            }
        }

        return Collections.list(Collections.enumeration(mapSistemas.values()));
    }

    /**
     * Obt�m os registros pelo usu�rio
     *
     * @param objUsuario contendo os par�metros da pesquisa
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    public List obterPorGrupoUsuarioEhGestor(Grupo objGrupo, UsuarioSistema objUsuarioGestor) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        Map mapSistemas = new TreeMap();

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT DISTINCT perfilSistema.sistema ");
        strbConsulta.append(" FROM");
        strbConsulta.append(" PerfilSistema perfilSistema ");
        strbConsulta.append(" JOIN perfilSistema.grupos grupo ");
        strbConsulta.append(" WHERE grupo.codigo = ");
        strbConsulta.append(objGrupo.getCodigo().intValue());
        strbConsulta.append(" AND");
        strbConsulta.append(PerfilSistemaDAO.obterHqlUsuarioEhGestor(objUsuarioGestor));
        strbConsulta.append(" ORDER BY perfilSistema.sistema.nome ASC");

        List lstPerfilSistema = this.obter(strbConsulta.toString());
        for (int i = 0; i < lstPerfilSistema.size(); i++)
        {
            Sistema objSistema = (Sistema) lstPerfilSistema.get(i);
            mapSistemas.put(objSistema.getObjetoControlado(), objSistema);
        }

        // Consulta os grupos associados ao sistema e que o usu�rio fa�a parte
        strbConsulta.setLength(0);
        strbConsulta.append(" SELECT DISTINCT perfilSistema.sistema");
        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilGrupo agrupadorPerfilGrupo, PerfilSistemaAgrupadorPerfil perfilSistemaAgrupadorPerfil");
        strbConsulta.append(" JOIN perfilSistemaAgrupadorPerfil.perfilSistema perfilSistema ");
        strbConsulta.append(" WHERE agrupadorPerfilGrupo.agrupadorPerfil.identificador = perfilSistemaAgrupadorPerfil.agrupadorPerfil.identificador ");
        strbConsulta.append(" AND agrupadorPerfilGrupo.grupo.codigo = ");
        strbConsulta.append(objGrupo.getCodigo());
        strbConsulta.append(" AND ");
        strbConsulta.append(PerfilSistemaDAO.obterHqlUsuarioEhGestor(objUsuarioGestor));
        strbConsulta.append(" ORDER BY perfilSistema.sistema.nome ASC");

        List lstAgrupadorPerfilGrupo = this.obter(strbConsulta.toString());
        for (int i = 0; i < lstAgrupadorPerfilGrupo.size(); i++)
        {
            Sistema objSistema = (Sistema) lstAgrupadorPerfilGrupo.get(i);
            mapSistemas.put(objSistema.getObjetoControlado(), objSistema);
        }

        return Collections.list(Collections.enumeration(mapSistemas.values()));
    }

    /**
     * Obt�m os registros pelo usu�rio
     *
     * @param objUsuario contendo os par�metros da pesquisa
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    public List obterSistemasUsuarioEhGestorAgrupadorNull(UsuarioSistema objUsuarioGestor) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT DISTINCT gestorSistema.sistema");
        strbConsulta.append(" FROM");
        strbConsulta.append(" GestorSistema gestorSistema");
        strbConsulta.append(" WHERE gestorSistema.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuarioGestor.getIdentificador().intValue());
        strbConsulta.append(" AND gestorSistema.sistema.sistemaAgrupador IS NULL ");
        strbConsulta.append(" ORDER BY gestorSistema.sistema.nome ASC");

        // Obt�m os sistemas habilitados para o usu�rio
        return obter(strbConsulta.toString());
    }

    /**
     * Obt�m os registros pelo usu�rio
     *
     * @param objUsuario contendo os par�metros da pesquisa
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    public List obterSistemasUsuarioEhGestor(UsuarioSistema objUsuarioGestor) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        Map mapSistemas = new TreeMap();

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT DISTINCT gestorSistema.sistema");
        strbConsulta.append(" FROM");
        strbConsulta.append(" GestorSistema gestorSistema");
        strbConsulta.append(" WHERE gestorSistema.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuarioGestor.getIdentificador().intValue());
        strbConsulta.append(" ORDER BY gestorSistema.sistema.nome ASC");

        // Obt�m os sistemas habilitados para o usu�rio
        List lstSistemasUsuario = obter(strbConsulta.toString());

        // Como n�o existe a clausula UNION no HQL foi necess�rio separar as consultas de sistemas de usu�rio e 
        // os sistemas que pertencem ao grupo do usu�rio e colocar num MAP para n�o ter repeti��o.
        for (int i = 0; i < lstSistemasUsuario.size(); i++)
        {
            Sistema objSistema = (Sistema) lstSistemasUsuario.get(i);
            mapSistemas.put(objSistema.getObjetoControlado(), objSistema);
        }

        // Limpa StringBuffer
        strbConsulta.setLength(0);

        strbConsulta.append(" SELECT DISTINCT perfilSistema.sistema ");
        strbConsulta.append(" FROM");
        strbConsulta.append(" PerfilSistema perfilSistema ");
        strbConsulta.append(" WHERE");
        strbConsulta.append(PerfilSistemaDAO.obterHqlUsuarioEhGestor(objUsuarioGestor));
        strbConsulta.append(" ORDER BY perfilSistema.sistema.nome ASC");

        // Obt�m os sistemas habilitados para o grupo do usu�rio
        List lstSistemasGrupo = obter(strbConsulta.toString());

        for (int i = 0; i < lstSistemasGrupo.size(); i++)
        {
            Sistema objSistema = (Sistema) lstSistemasGrupo.get(i);
            mapSistemas.put(objSistema.getObjetoControlado(), objSistema);
        }

        return Collections.list(Collections.enumeration(mapSistemas.values()));
    }

    /**
     * Obt�m os registros pelo usu�rio
     *
     * @param objUsuario contendo os par�metros da pesquisa
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    public List obterSistemasDoAgrupador(AgrupadorPerfil objAgrupadorPerfil) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT agrupadorPerfilSistema.sistema ");
        strbConsulta.append(" FROM AgrupadorPerfilSistema agrupadorPerfilSistema ");
        strbConsulta.append(" WHERE agrupadorPerfilSistema.agrupadorPerfil.identificador = ");
        strbConsulta.append(objAgrupadorPerfil.getIdentificador().toString());
        strbConsulta.append(" ORDER BY agrupadorPerfilSistema.sistema.objetoControlado ");

        // Obt�m os Sistemas associados ao Agrupador
        return obter(strbConsulta.toString());
    }

    /**
     * Obt�m os registros pelo usu�rio
     *
     * @param objUsuario contendo os par�metros da pesquisa
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    /*protected static String obterHqlUsuarioEhGestor(UsuarioSistema objUsuarioGestor) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" (");
        strbConsulta.append("   perfilSistema.sistema.identificador IN ");
        strbConsulta.append("   (");
        strbConsulta.append("       SELECT gestorSistema.sistema.identificador FROM");
        strbConsulta.append("       GestorSistema gestorSistema");
        strbConsulta.append("       WHERE gestorSistema.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuarioGestor.getIdentificador().intValue());
        strbConsulta.append("   )");
        strbConsulta.append("   OR perfilSistema.sistema.identificador IN");
        strbConsulta.append("   (");
        strbConsulta.append("       SELECT usuarioPerfilSistema.perfilSistema.sistema.identificador FROM");
        strbConsulta.append("       UsuarioPerfilSistema usuarioPerfilSistema ");
        strbConsulta.append("       WHERE usuarioPerfilSistema.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuarioGestor.getIdentificador().intValue());
        strbConsulta.append("       AND usuarioPerfilSistema.indicativoGestor = 'S'");
        strbConsulta.append("   )");
        strbConsulta.append(" )");

        return strbConsulta.toString();
    }*/

    /**
     * M�todo que pede a inicializa��o da propriedade Sistema, que � lazy
     * 
     * @param Cracha Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarSistema(Sistema objSistema) throws DAOException
    {
        this.inicializarRelacionamento(objSistema);
    }

}

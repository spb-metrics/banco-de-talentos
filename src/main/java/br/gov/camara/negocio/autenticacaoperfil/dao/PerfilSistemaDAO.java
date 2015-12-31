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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.FuncionalidadeSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Grupo;
import br.gov.camara.negocio.autenticacaoperfil.pojo.PerfilSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Sistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;
import br.gov.camara.negocio.exception.DAOException;

/**
 * 
 * Classe DAO para perfil de sistema
 * 
 */

public class PerfilSistemaDAO extends DAO
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(DAO.class);

    /**
     * Construtor sem argumentos
     * 
     */
    public PerfilSistemaDAO()
    {
        super("Perfil de sistema");
    }

    public PerfilSistemaDAO(String nomeConexao)
    {
        super(nomeConexao);
    }

    /**
     * Obt�m todos os registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * 
     * @throws DAOException
     *             se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
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
        strbConsulta.append(" PerfilSistema perfilSistema");
        strbConsulta.append(" ORDER BY perfilSistema.nome ASC");

        // Retorna
        return obter(strbConsulta.toString());
    }

    /**
     * Obt�m todos os registros pelo sistema a que ele pertence
     * 
     * @param objSistema
     *            Sistema a qual o perfil de sistema pertence
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * 
     * @throws DAOException
     *             se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     * 
     */
    public List obterPeloSistema(Sistema objSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" PerfilSistema perfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" perfilSistema.sistema.identificador = ");
        strbConsulta.append(objSistema.getIdentificador());
        strbConsulta.append(" ORDER BY perfilSistema.nome ASC");

        // Retorna
        return obter(strbConsulta.toString());
    }

    /**
     * Obt�m todos os registros pelo sistema e pelo perfil agrupador
     * 
     * @param objSistema
     *            Sistema a qual o perfil de sistema pertence
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * 
     * @throws DAOException
     *             se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     * 
     */
    public List obterPeloSistemaPerfilAgrupador(PerfilSistema objPerfilSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" PerfilSistema perfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" perfilSistema.sistema.identificador = ");
        strbConsulta.append(objPerfilSistema.getSistema().getIdentificador().intValue());
        strbConsulta.append(" AND perfilSistema.perfilAgrupador.identificador = ");
        strbConsulta.append(objPerfilSistema.getPerfilAgrupador().getIdentificador().intValue());
        strbConsulta.append(" ORDER BY perfilSistema.nome ASC");

        // Retorna
        return obter(strbConsulta.toString());
    }

    /**
     * Obt�m todos os registros pelo sistema e pelo perfil agrupador
     * 
     * @param objSistema
     *            Sistema a qual o perfil de sistema pertence
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * 
     * @throws DAOException
     *             se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     * 
     */
    public List obterPeloPerfilAgrupador(PerfilSistema objPerfilSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" PerfilSistema perfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" perfilSistema.sistema.identificador = ");
        strbConsulta.append(objPerfilSistema.getSistema().getIdentificador().intValue());

        if (objPerfilSistema.getPerfilAgrupador() == null)
        {
            strbConsulta.append(" AND perfilSistema.perfilAgrupador IS NULL");
        }
        else
        {
            strbConsulta.append(" AND perfilSistema.perfilAgrupador.identificador = ");
            strbConsulta.append(objPerfilSistema.getPerfilAgrupador().getIdentificador().intValue());
        }
        strbConsulta.append(" ORDER BY perfilSistema.nome ASC");

        // Retorna
        return obter(strbConsulta.toString());
    }

    /**
     * Obt�m todos os registros pelo sistema e pelo perfil agrupador
     * 
     * @param objSistema
     *            Sistema a qual o perfil de sistema pertence
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * 
     * @throws DAOException
     *             se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     * 
     */
    public List obterPeloSistemaAgrupadorNull(PerfilSistema objPerfilSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" PerfilSistema perfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" perfilSistema.perfilAgrupador IS NULL ");

        if (objPerfilSistema.getSistema() == null)
        {
            strbConsulta.append(" AND perfilSistema.sistema IS NULL ");
        }
        else
        {
            strbConsulta.append(" AND perfilSistema.sistema.identificador = ");
            strbConsulta.append(objPerfilSistema.getSistema().getIdentificador().intValue());
        }
        strbConsulta.append(" ORDER BY perfilSistema.nome ASC");

        // Retorna
        return obter(strbConsulta.toString());
    }

    /**
     * Obt�m um registro a partir da chave
     * 
     * @param strChave
     *            Chave do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     * 
     * @throws DAOException
     *             se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     * 
     */
    public Object obterPelaChave(PerfilSistema objPerfilSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" PerfilSistema perfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" perfilSistema.identificador = ");
        strbConsulta.append(objPerfilSistema.getIdentificador().intValue());

        // Verifica se h� retorno
        List lstResultado = obter(strbConsulta.toString());
        if (!lstResultado.isEmpty())
        {
            return (PerfilSistema) lstResultado.get(0);
        }
        else
        {
            return null;
        }
    }

    /**
     * Obt�m um registro a partir da chave
     * 
     * @param strChave
     *            Chave do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     * 
     * @throws DAOException
     *             se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
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
        strbConsulta.append(" PerfilSistema perfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" perfilSistema.identificador = ");
        strbConsulta.append(strChave);

        // Verifica se h� retorno
        List lstResultado = obter(strbConsulta.toString());
        if (!lstResultado.isEmpty())
            return (PerfilSistema) lstResultado.get(0);
        else
        {
            return null;
        }
    }

    /**
     * Obt�m um registro a partir do objeto controlado
     * 
     * @param strObjetoControlado
     *            Objeto controlado do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     * 
     * @throws DAOException
     *             se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     * 
     */
    public PerfilSistema obterPeloObjetoControlado(String strObjetoControlado) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" PerfilSistema perfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" perfilSistema.objetoControlado = '");
        strbConsulta.append(strObjetoControlado.trim());
        strbConsulta.append("'");

        // Verifica se h� retorno
        List lstResultado = obter(strbConsulta.toString());
        if (!lstResultado.isEmpty())
            return (PerfilSistema) lstResultado.get(0);
        else
        {
            return null;
        }
    }

    public boolean isObjetoControladoCadastrado(PerfilSistema objPerfilSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" PerfilSistema perfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" perfilSistema.objetoControlado = '");
        strbConsulta.append(objPerfilSistema.getObjetoControlado().trim());
        strbConsulta.append("'");
        strbConsulta.append(" AND perfilSistema.identificador <> ");
        strbConsulta.append(objPerfilSistema.getIdentificador().intValue());

        // Verifica se h� retorno
        List lstResultado = obter(strbConsulta.toString());
        if (!lstResultado.isEmpty())
            return true;
        else
        {
            return false;
        }
    }

    /**
     * Obt�m o objeto controlado que j� est� cadastrado
     * 
     * @param objPerfilSistema POJO com os par�metros de pesquisa
     * 
     * @return PerfilSistema POJO que possui o objeto controlado
     * 
     * @throws DAOExceptionSe ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public PerfilSistema obterObjetoControladoCadastrado(PerfilSistema objPerfilSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" PerfilSistema perfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" perfilSistema.objetoControlado = '");
        strbConsulta.append(objPerfilSistema.getObjetoControlado().trim());
        strbConsulta.append("'");
        strbConsulta.append(" AND perfilSistema.identificador <> ");
        strbConsulta.append(objPerfilSistema.getIdentificador().intValue());

        // Verifica se h� retorno
        List lstResultado = obter(strbConsulta.toString());
        if (!lstResultado.isEmpty())
            return (PerfilSistema) lstResultado.get(0);
        else
        {
            return null;
        }
    }

    /**
     * Exclui um registro
     * 
     * @param Object
     *            POJO representando o objeto a ser exclu�do
     * 
     * @return String Contendo a consulta de exclus�o
     * 
     * @throws DAOException
     *             Se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     * 
     */
    public String excluirImpl(Object objPOJO) throws DAOException
    {

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" PerfilSistema perfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" perfilSistema.identificador = ");
        strbConsulta.append(((PerfilSistema) objPOJO).getIdentificador().intValue());

        return strbConsulta.toString();
    }

    /**
     * M�todo que pede a inicializa��o da propriedade grupo, que � lazy
     * 
     * @param PerfilSistema
     *            Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException
     *             Se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     */
    public void inicializarGrupos(PerfilSistema objPerfilSistema) throws DAOException
    {
        if (objPerfilSistema.getGrupos() != null && objPerfilSistema.getGrupos().size() > 0)
        {
            Iterator itrGrupos = objPerfilSistema.getGrupos().iterator();
            while (itrGrupos.hasNext())
            {
                inicializarRelacionamento(((Grupo) itrGrupos.next()));
            }
        }
    }

    /**
     * M�todo que pede a inicializa��o da propriedade perfil agrupador, que �
     * lazy
     * 
     * @param PerfilAgrupador
     *            Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException
     *             Se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     */
    public void inicializarPerfilAgrupador(PerfilSistema objPerfilSistema) throws DAOException
    {
        inicializarRelacionamento(objPerfilSistema.getPerfilAgrupador());
    }

    /**
     * M�todo que pede a inicializa��o da propriedade grupos, que � lazy
     * 
     * @param List
     *            Lista de objetos que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException
     *             Se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     */
    public void inicializarGrupos(List lstPerfisSistema) throws DAOException
    {
        Iterator itrPerfisSistema = lstPerfisSistema.iterator();
        while (itrPerfisSistema.hasNext())
        {
            inicializarGrupos(((PerfilSistema) itrPerfisSistema.next()));
        }
    }

    /**
     * Obt�m a hierarquia do sistema
     * 
     * @param objSistema
     *            pojo contendo os par�metros da consulta
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * 
     * @throws DAOException
     *             Se ocorrer algum erro
     * 
     */
    public List obterHierarquia(PerfilSistema objPerfilSistema) throws DAOException
    {
        List lstRetorno = new ArrayList();

        if (objPerfilSistema.getPerfilAgrupador() != null && objPerfilSistema.getPerfilAgrupador().getIdentificador().intValue() < 0)
        {
            objPerfilSistema.setPerfilAgrupador(null);
        }

        while (objPerfilSistema.getPerfilAgrupador() != null)
        {
            objPerfilSistema = (PerfilSistema) obterPelaChave(objPerfilSistema.getPerfilAgrupador());

            if (objPerfilSistema == null)
            {
                throw new DAOException("O sistema especificado n�o existe");
            }
            else
            {
                lstRetorno.add(objPerfilSistema.getIdentificador().intValue() + ";" + objPerfilSistema.getNome());
            }
        }
        // Insere hierarquia inicial
        lstRetorno.add("-1;" + "In�cio");

        // Ordena hierarquia
        ListIterator litRetorno = lstRetorno.listIterator();
        lstRetorno = new ArrayList();
        while (litRetorno.hasNext())
        {
            litRetorno.next();
        }
        while (litRetorno.hasPrevious())
        {
            lstRetorno.add(lstRetorno.size() + 1 + ";" + litRetorno.previous());
        }

        return lstRetorno;
    }

    /**
     * M�todo que pede a inicializa��o da propriedade funcionalidades, que �
     * lazy
     * 
     * @param PerfilSistema
     *            Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException
     *             Se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     */
    public void inicializarFuncionalidades(PerfilSistema objPerfilSistema) throws DAOException
    {
        if (objPerfilSistema.getFuncionalidades() != null && objPerfilSistema.getFuncionalidades().size() > 0)
        {
            Iterator itrPerfilSistema = objPerfilSistema.getFuncionalidades().iterator();
            while (itrPerfilSistema.hasNext())
            {
                inicializarRelacionamento(((FuncionalidadeSistema) itrPerfilSistema.next()));
            }
        }
    }

    /**
     * M�todo que pede a inicializa��o da propriedade funcionalidades, que �
     * lazy
     * 
     * @param List
     *            Lista de objetos que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException
     *             Se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     */
    public void inicializarFuncionalidades(List lstPerfisSistema) throws DAOException
    {
        Iterator itrPerfisSistema = lstPerfisSistema.iterator();
        while (itrPerfisSistema.hasNext())
        {
            inicializarFuncionalidades(((PerfilSistema) itrPerfisSistema.next()));
        }
    }

    /**
     * Obt�m um registro a partir do objeto controlado
     * 
     * @param strObjetoControlado
     *            Objeto controlado do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     * 
     * @throws DAOException
     *             se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     * 
     */
    public List obterPelaChaveNomeEOuObjetoControladoEOuNomePerfil(PerfilSistema objPerfilSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" PerfilSistema perfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" perfilSistema.sistema.identificador = ");
        strbConsulta.append(objPerfilSistema.getSistema().getIdentificador().toString());

        if (objPerfilSistema.getObjetoControlado() != null && !"".equals(objPerfilSistema.getObjetoControlado()))
        {
            strbConsulta.append(" AND UPPER(perfilSistema.objetoControlado) LIKE '%");
            strbConsulta.append(objPerfilSistema.getObjetoControlado().toUpperCase());
            strbConsulta.append("%'");
        }
        if (objPerfilSistema.getNome() != null && !"".equals(objPerfilSistema.getNome()))
        {
            strbConsulta.append(" AND UPPER(perfilSistema.nome) LIKE '%");
            strbConsulta.append(objPerfilSistema.getNome().toUpperCase());
            strbConsulta.append("%'");
        }

        // Verifica se h� retorno
        return obter(strbConsulta.toString());
    }

    public boolean possuiFilhos(PerfilSistema objPerfilSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        boolean blnRetorno = false;

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" PerfilSistema perfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" perfilSistema.perfilAgrupador.identificador = ");
        strbConsulta.append(objPerfilSistema.getIdentificador().intValue());

        // Verifica se h� retorno
        List lstPerfis = obter(strbConsulta.toString());

        if (lstPerfis != null && lstPerfis.size() > 0)
        {
            blnRetorno = true;
        }
        return blnRetorno;
    }

    /**
     * Obt�m os grupos dispon�veis de um determinado perfil de sistema
     * 
     * @param objPerfilSistema
     *            Perfil do sistema
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * 
     * @throws DAOException
     *             se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     * 
     */
    public List obterGruposDisponiveis(PerfilSistema objPerfilSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" Grupo grupo");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" grupo.codigo NOT IN");
        strbConsulta.append(" (SELECT perfilSistema.grupos.elements");
        strbConsulta.append("   FROM");
        strbConsulta.append("   PerfilSistema perfilSistema");
        strbConsulta.append("   WHERE");
        strbConsulta.append("   perfilSistema.identificador = ");
        strbConsulta.append(objPerfilSistema.getIdentificador().intValue());
        strbConsulta.append(" )");
        strbConsulta.append(" ORDER BY grupo.descricao ASC");

        return obter(strbConsulta.toString());

    }

    /**
     * Obt�m os grupos dispon�veis de um determinado perfil de sistema
     * 
     * @param objPerfilSistema
     *            Perfil do sistema
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * 
     * @throws DAOException
     *             se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     * 
     */
    public List obterPeloSistemaEOuUsuarioEOuGrupo(Sistema objSistema, UsuarioSistema objUsuarioSistema, Grupo objGrupo) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();
        StringBuffer strbSelect = new StringBuffer(" SELECT DISTINCT perfilSistema");
        StringBuffer strbFrom = new StringBuffer(" FROM ");
        StringBuffer strbWhere = new StringBuffer(" WHERE 1 = 1");
        StringBuffer strbOrder = new StringBuffer(" ORDER BY perfilSistema.nome");

        // Verifica se a consulta possui filtro de usu�rio
        if (objUsuarioSistema != null && objUsuarioSistema.getIdentificador() != null)
        {
            strbFrom.append(" UsuarioPerfilSistema usuarioPerfilSistema JOIN usuarioPerfilSistema.perfilSistema perfilSistema ");
            strbWhere.append(" AND usuarioPerfilSistema.usuarioSistema.identificador = ");
            strbWhere.append(objUsuarioSistema.getIdentificador().intValue());
        }
        else
        {
            strbFrom.append(" PerfilSistema perfilSistema ");
        }

        // Verifica se a consulta possui filtro de sistema
        if (objSistema != null && objSistema.getIdentificador() != null)
        {
            strbWhere.append(" AND perfilSistema.sistema.identificador = ");
            strbWhere.append(objSistema.getIdentificador().intValue());
        }

        // Verifica se a consulta possui filtro de grupo
        if (objGrupo != null && objGrupo.getCodigo() != null)
        {
            strbFrom.append(" JOIN perfilSistema.grupos grupo");
            strbWhere.append(" AND grupo.codigo = ");
            strbWhere.append(objGrupo.getCodigo().intValue());
        }

        strbConsulta.append(strbSelect);
        strbConsulta.append(strbFrom);
        strbConsulta.append(strbWhere);
        strbConsulta.append(strbOrder);

        return obter(strbConsulta.toString());

    }

    /**
     * Obt�m os grupos dispon�veis de um determinado perfil de sistema
     * 
     * @param objPerfilSistema
     *            Perfil do sistema
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * 
     * @throws DAOException
     *             se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     * 
     */
    public boolean grupoUsuarioPossuiPerfil(PerfilSistema objPerfilSistema, UsuarioSistema objUsuarioSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        boolean blnRetorno = false;

        List lstConsulta = null;

        // Monta query
        StringBuffer strbConsulta = new StringBuffer(" SELECT DISTINCT perfilSistema");
        strbConsulta.append(" FROM PerfilSistema perfilSistema JOIN perfilSistema.grupos grupo");
        strbConsulta.append(" WHERE perfilSistema.identificador = ");
        strbConsulta.append(objPerfilSistema.getIdentificador());
        strbConsulta.append("AND grupo.codigo = ");
        strbConsulta.append(objUsuarioSistema.getGrupo().getCodigo());

        lstConsulta = obter(strbConsulta.toString());

        if (lstConsulta != null && lstConsulta.size() > 0)
        {
            blnRetorno = true;
        }

        return blnRetorno;

    }

    /**
     * Obt�m os grupos dispon�veis de um determinado perfil de sistema
     * 
     * @param objPerfilSistema
     *            Perfil do sistema
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * 
     * @throws DAOException
     *             se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     * 
     */
    public List obterPeloSistemaUsuarioEhGestor(Sistema objSistema, UsuarioSistema objUsuarioGestor) throws DAOException
    {

        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" PerfilSistema perfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" perfilSistema.sistema.identificador = ");
        strbConsulta.append(objSistema.getIdentificador());
        strbConsulta.append(" AND ");
        strbConsulta.append(obterHqlUsuarioEhGestor(objUsuarioGestor));

        return obter(strbConsulta.toString());
    }

    /**
     * Obt�m os grupos dispon�veis de um determinado perfil de sistema
     * 
     * @param objPerfilSistema
     *            Perfil do sistema
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * 
     * @throws DAOException
     *             se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     * 
     */
    public List obterPeloSistemaUsuarioEhGestor(PerfilSistema objPerfilSistema, UsuarioSistema objUsuarioGestor) throws DAOException
    {

        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" PerfilSistema perfilSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" perfilSistema.sistema.identificador = ");
        strbConsulta.append(objPerfilSistema.getSistema().getIdentificador());
        strbConsulta.append(" AND ");
        strbConsulta.append(obterHqlUsuarioEhGestor(objUsuarioGestor));

        if (objPerfilSistema.getPerfilAgrupador() == null)
        {
            strbConsulta.append(" AND perfilSistema.perfilAgrupador IS NULL");
        }
        else
        {
            strbConsulta.append(" AND perfilSistema.perfilAgrupador.identificador = ");
            strbConsulta.append(objPerfilSistema.getPerfilAgrupador().getIdentificador().intValue());
        }

        // Obt�m os sistemas habilitados para o usu�rio
        return obter(strbConsulta.toString());
    }

    /**
     * Obt�m todos os registros pelo sistema e pelo perfil agrupador
     * 
     * @param objSistema
     *            Sistema a qual o perfil de sistema pertence
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * 
     * @throws DAOException
     *             se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     * 
     */
    public List obterPeloPerfilAgrupador(Integer intIdentificador) throws DAOException
    {

        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" PerfilSistema perfilSistema");

        if (intIdentificador == null)
        {
            strbConsulta.append(" WHERE perfilSistema.perfilAgrupador.identificador IS NULL");
        }
        else
        {
            strbConsulta.append(" WHERE perfilSistema.perfilAgrupador.identificador = ");
            strbConsulta.append(intIdentificador.intValue());
        }
        // Recupera os dados
        return obter(strbConsulta.toString());
    }

    /**
     * Obt�m os grupos dispon�veis de um determinado perfil de sistema
     * 
     * @param objPerfilSistema
     *            Perfil do sistema
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * 
     * @throws DAOException
     *             se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     * 
     */
    public List obterPerfisPeloSistemaQueSaoComunsEntreUsuarioComAcessoEUsuarioEhGestor(PerfilSistema objPerfilSistema, UsuarioSistema objUsuario, UsuarioSistema objUsuarioGestor) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        Map mapPerfis = new TreeMap();

        // Monta consulta que retorna os perfis que o usu�rio possui, dentre os quais o usu�rio logado � gestor do sistema

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT usuarioPerfilSistema.perfilSistema");
        strbConsulta.append(" FROM UsuarioPerfilSistema usuarioPerfilSistema");
        strbConsulta.append(" JOIN usuarioPerfilSistema.perfilSistema perfilSistema");
        strbConsulta.append(" WHERE usuarioPerfilSistema.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuario.getIdentificador().intValue());
        strbConsulta.append(" AND");
        strbConsulta.append(obterHqlUsuarioEhGestor(objUsuarioGestor));
        strbConsulta.append(" AND usuarioPerfilSistema.perfilSistema.sistema.identificador = ");
        strbConsulta.append(objPerfilSistema.getSistema().getIdentificador());

        // Obt�m os sistemas habilitados para o usu�rio
        List lstGestorSistema = obter(strbConsulta.toString());

        // Como n�o existe a clausula UNION no HQL foi necess�rio separar as
        // consultas de perfis de usu�rio e
        // os perfis que pertencem ao grupo do usu�rio e colocar num MAP para
        // n�o ter repeti��o.
        for (int i = 0; i < lstGestorSistema.size(); i++)
        {
            PerfilSistema objPerf = (PerfilSistema) lstGestorSistema.get(i);
            mapPerfis.put(objPerf.getObjetoControlado(), objPerf);
        }
        if (objUsuario.getGrupo() != null)
        {
            // Limpa Conte�do do StringBuffer
            strbConsulta.setLength(0);

            strbConsulta.append(" SELECT perfilSistema");
            strbConsulta.append(" FROM PerfilSistema perfilSistema JOIN perfilSistema.grupos grupo");
            strbConsulta.append(" WHERE grupo.codigo = ");
            strbConsulta.append(objUsuario.getGrupo().getCodigo().intValue());
            strbConsulta.append(" AND");
            strbConsulta.append(obterHqlUsuarioEhGestor(objUsuarioGestor));
            strbConsulta.append(" AND perfilSistema.sistema.identificador = ");
            strbConsulta.append(objPerfilSistema.getSistema().getIdentificador());

            // Obt�m os sistemas habilitados para o grupo do usu�rio
            List lstGestorPerfil = obter(strbConsulta.toString());

            for (int i = 0; i < lstGestorPerfil.size(); i++)
            {
                PerfilSistema objPerf = (PerfilSistema) lstGestorPerfil.get(i);
                mapPerfis.put(objPerf.getObjetoControlado(), objPerf);
            }
        }

        // Consulta os perfis associados ao agrupador e que o usu�rio fa�a parte
        strbConsulta.setLength(0);
        strbConsulta.append(" SELECT perfilSistemaAgrupadorPerfil.perfilSistema");
        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilUsuario agrupadorPerfilUsuario, PerfilSistemaAgrupadorPerfil perfilSistemaAgrupadorPerfil ");
        strbConsulta.append(" JOIN perfilSistemaAgrupadorPerfil.perfilSistema perfilSistema ");
        strbConsulta.append(" WHERE agrupadorPerfilUsuario.agrupadorPerfil.identificador = perfilSistemaAgrupadorPerfil.agrupadorPerfil.identificador ");
        strbConsulta.append(" AND agrupadorPerfilUsuario.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuario.getIdentificador());
        strbConsulta.append(" AND perfilSistema.sistema.identificador = ");
        strbConsulta.append(objPerfilSistema.getSistema().getIdentificador());
        strbConsulta.append(" AND ");
        strbConsulta.append(obterHqlUsuarioEhGestor(objUsuarioGestor));

        List lstPerfisAgrupadores = this.obter(strbConsulta.toString());
        for (int i = 0; i < lstPerfisAgrupadores.size(); i++)
        {
            PerfilSistema objPerf = (PerfilSistema) lstPerfisAgrupadores.get(i);
            mapPerfis.put(objPerf.getObjetoControlado(), objPerf);
        }

        // Consulta os grupos associados ao agrupador e que o usu�rio fa�a parte
        strbConsulta.setLength(0);
        strbConsulta.append(" SELECT perfilSistemaAgrupadorPerfil.perfilSistema");
        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilGrupo agrupadorPerfilGrupo, PerfilSistemaAgrupadorPerfil perfilSistemaAgrupadorPerfil");
        strbConsulta.append(" JOIN perfilSistemaAgrupadorPerfil.perfilSistema perfilSistema ");
        strbConsulta.append(" WHERE agrupadorPerfilGrupo.agrupadorPerfil.identificador = perfilSistemaAgrupadorPerfil.agrupadorPerfil.identificador ");
        strbConsulta.append(" AND agrupadorPerfilGrupo.grupo.codigo = ");
        strbConsulta.append(objUsuario.getGrupo().getCodigo());
        strbConsulta.append(" AND perfilSistema.sistema.identificador = ");
        strbConsulta.append(objPerfilSistema.getSistema().getIdentificador());
        strbConsulta.append(" AND ");
        strbConsulta.append(obterHqlUsuarioEhGestor(objUsuarioGestor));

        List lstAgrupadorPerfilGrupo = this.obter(strbConsulta.toString());
        for (int i = 0; i < lstAgrupadorPerfilGrupo.size(); i++)
        {
            PerfilSistema objPerf = (PerfilSistema) lstAgrupadorPerfilGrupo.get(i);
            mapPerfis.put(objPerf.getObjetoControlado(), objPerf);
        }

        return Collections.list(Collections.enumeration(mapPerfis.values()));
    }

    /**
     * Obt�m os grupos dispon�veis de um determinado perfil de sistema
     * 
     * @param objPerfilSistema
     *            Perfil do sistema
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * 
     * @throws DAOException
     *             se ocorrer algum erro relacionado com o acesso a banco de
     *             dados
     * 
     */
    public List obterPeloSistemaGrupoUsuarioEhGestor(PerfilSistema objPerfilSistema, Grupo objGrupo, UsuarioSistema objUsuarioGestor) throws DAOException
    {

        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        Map mapPerfis = new TreeMap();

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT perfilSistema ");
        strbConsulta.append(" FROM ");
        strbConsulta.append(" PerfilSistema perfilSistema JOIN perfilSistema.grupos grupo ");
        strbConsulta.append(" WHERE grupo.codigo =  ");
        strbConsulta.append(objGrupo.getCodigo().intValue());
        strbConsulta.append(" AND perfilSistema.sistema.identificador =  ");
        strbConsulta.append(objPerfilSistema.getSistema().getIdentificador());
        strbConsulta.append(" AND ");
        strbConsulta.append(obterHqlUsuarioEhGestor(objUsuarioGestor));

        List lstPerfilSistema = this.obter(strbConsulta.toString());
        for (int i = 0; i < lstPerfilSistema.size(); i++)
        {
            PerfilSistema objPerf = (PerfilSistema) lstPerfilSistema.get(i);
            mapPerfis.put(objPerf.getObjetoControlado(), objPerf);
        }

        // Consulta os grupos associados ao sistema e que o usu�rio fa�a parte
        strbConsulta.setLength(0);
        strbConsulta.append(" SELECT perfilSistemaAgrupadorPerfil.perfilSistema");
        strbConsulta.append(" FROM");
        strbConsulta.append(" AgrupadorPerfilGrupo agrupadorPerfilGrupo, PerfilSistemaAgrupadorPerfil perfilSistemaAgrupadorPerfil");
        strbConsulta.append(" JOIN perfilSistemaAgrupadorPerfil.perfilSistema perfilSistema ");
        strbConsulta.append(" WHERE agrupadorPerfilGrupo.agrupadorPerfil.identificador = perfilSistemaAgrupadorPerfil.agrupadorPerfil.identificador ");
        strbConsulta.append(" AND agrupadorPerfilGrupo.grupo.codigo = ");
        strbConsulta.append(objGrupo.getCodigo());
        strbConsulta.append(" AND perfilSistema.sistema.identificador = ");
        strbConsulta.append(objPerfilSistema.getSistema().getIdentificador());
        strbConsulta.append(" AND ");
        strbConsulta.append(obterHqlUsuarioEhGestor(objUsuarioGestor));

        List lstAgrupadorPerfilGrupo = this.obter(strbConsulta.toString());
        for (int i = 0; i < lstAgrupadorPerfilGrupo.size(); i++)
        {
            PerfilSistema objPerf = (PerfilSistema) lstAgrupadorPerfilGrupo.get(i);
            mapPerfis.put(objPerf.getObjetoControlado(), objPerf);
        }

        return Collections.list(Collections.enumeration(mapPerfis.values()));

    }

    public PerfilSistema obterPeloObjetoControladoDaFuncionalidade(String strObjControlado) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT perfilSistema ");
        strbConsulta.append(" FROM ");
        strbConsulta.append(" PerfilSistema perfilSistema JOIN perfilSistema.funcionalidades funcionalidade ");
        strbConsulta.append(" WHERE ");
        strbConsulta.append(" funcionalidade.objetoControlado = '");
        strbConsulta.append(strObjControlado);
        strbConsulta.append("'");

        // Verifica se h� retorno
        List lstResultado = obter(strbConsulta.toString());
        if (!lstResultado.isEmpty())
            return (PerfilSistema) lstResultado.get(0);
        else
        {
            return null;
        }
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
    protected static String obterHqlUsuarioEhGestor(UsuarioSistema objUsuarioGestor) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" (");
        strbConsulta.append("   perfilSistema.sistema.identificador IN  ");
        strbConsulta.append("   ( ");
        strbConsulta.append("       SELECT gestorSistema.sistema.identificador ");
        strbConsulta.append("       FROM GestorSistema gestorSistema ");
        strbConsulta.append("       WHERE gestorSistema.usuarioSistema.identificador =  ");
        strbConsulta.append(objUsuarioGestor.getIdentificador().intValue());
        strbConsulta.append("   ) ");
        strbConsulta.append("   OR perfilSistema.identificador IN  ");
        strbConsulta.append("   ( ");
        strbConsulta.append("       SELECT usuarioPerfilSistema.perfilSistema.identificador ");
        strbConsulta.append("       FROM UsuarioPerfilSistema usuarioPerfilSistema ");
        strbConsulta.append("       WHERE usuarioPerfilSistema.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuarioGestor.getIdentificador().intValue());
        strbConsulta.append("       AND usuarioPerfilSistema.indicativoGestor = 'S' ");
        strbConsulta.append("   ) ");
        strbConsulta.append(" ) ");

        return strbConsulta.toString();
    }

    /**
     * Obt�m todos os registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPerfilSistemaNotIn(PerfilSistema objPerfilSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" PerfilSistema perfilSistema ");
        strbConsulta.append(" WHERE perfilSistema.sistema.identificador = ");
        strbConsulta.append(objPerfilSistema.getSistema().getIdentificador().intValue());
        strbConsulta.append(" AND perfilSistema.identificador <> ");
        strbConsulta.append(objPerfilSistema.getIdentificador().intValue());
        strbConsulta.append(" ORDER BY");
        strbConsulta.append(" perfilSistema.nome ASC");

        return super.obter(strbConsulta.toString());
    }

    /**
     * M�todo que pede a inicializa��o da propriedade PerfilSistema, que � lazy
     * 
     * @param PerfilSistema Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarPerfilSistema(PerfilSistema objPerfilSistema) throws DAOException
    {
        this.inicializarRelacionamento(objPerfilSistema);
    }

}

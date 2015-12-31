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

package br.gov.camara.negocio;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

import br.gov.camara.biblioteca.exception.DataException;
import br.gov.camara.biblioteca.util.DataNova;
import br.gov.camara.biblioteca.util.StringUtil;
import br.gov.camara.hibernate.HibernateUtilCD;
import br.gov.camara.negocio.exception.DAOException;

/**
 * Classe abstrata para DAOs
 */
public abstract class DAO
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(DAO.class);

    // private Session hbsSession;
    protected final String strNomeClasse;

    protected final String nomeConexao;

    protected String dialeto = null;
    protected Boolean isDialetoIngres = null;
    protected Boolean isDialetoOracle = null;
    protected Boolean isDialetoMySQL = null;

    protected String strCampoData = null;

    private static final String METODO_NAO_IMPLEMENTADO = "M�todo n�o implementado.";

    /**
     * Cria um novo objeto
     * 
     * @param strNomeClasse Para nomear a classe
     */
    public DAO(String strNomeClasse)
    {
        this.strNomeClasse = strNomeClasse;
        this.nomeConexao = null;
    }

    /**
     * Cria um novo objeto
     * 
     * @param strNomeClasse Para nomear a classe
     */
    public DAO(String strNomeClasse, String strNomeConexao)
    {
        this.strNomeClasse = strNomeClasse;
        this.nomeConexao = strNomeConexao;
    }

    /**
     * Abre uma sess�o Hibernate e inicia uma transa��o (BeginTrans)
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public static final void iniciarTransacao(String nomeConexao) throws DAOException
    {
        // Inicializa o ambiente de persist�ncia
        try
        {
            HibernateUtilCD.iniciarTransacao(nomeConexao);
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, HibernateUtilCD.obterDialeto(nomeConexao));
        }
    }

    /**
     * Abre uma sess�o Hibernate e inicia uma transa��o (BeginTrans)
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public static final void iniciarTransacao() throws DAOException
    {
        iniciarTransacao(HibernateUtilCD.obterNomeConexaoPadrao());
    }

    /**
     * Fecha a sess�o hibernate
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public static final void desconectar(String nomeConexao) throws DAOException
    {
        try
        {
            HibernateUtilCD.fecharSessao(nomeConexao);
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, HibernateUtilCD.obterDialeto(nomeConexao));
        }
    }

    /**
     * Fecha a sess�o hibernate
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public static final void desconectar() throws DAOException
    {
        desconectar(HibernateUtilCD.obterNomeConexaoPadrao());
    }

    /**
     * Fecha a sess�o atual Hibernate e realiza uma transa��o (CommitTrans)
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public static final void realizarTransacao(String nomeConexao) throws DAOException
    {
        // Realiza transa��o
        try
        {
            HibernateUtilCD.realizarTransacao(nomeConexao);
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, HibernateUtilCD.obterDialeto(nomeConexao));
        }
    }

    /**
     * Fecha a sess�o atual Hibernate e realiza uma transa��o (CommitTrans)
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public static final void realizarTransacao() throws DAOException
    {
        // Realiza transa��o
        realizarTransacao(HibernateUtilCD.obterNomeConexaoPadrao());
    }

    /**
     * Fecha a sess�o atual Hibernate e desfaz uma transa��o (RollbackTrans)
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public static final void desfazerTransacao(String nomeConexao) throws DAOException
    {
        // Desfaz transa��o
        try
        {
            HibernateUtilCD.desfazerTransacao(nomeConexao);
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, HibernateUtilCD.obterDialeto(nomeConexao));
        }
    }

    /**
     * Fecha a sess�o atual Hibernate e desfaz uma transa��o (RollbackTrans)
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public static final void desfazerTransacao() throws DAOException
    {
        // Desfaz transa��o
        desfazerTransacao(HibernateUtilCD.obterNomeConexaoPadrao());
    }

    /**
     * Obt�m o total de registros
     * 
     * @param strConsulta String contendo a consulta para contar os registros
     * @return List Contendo os POJOs representando os registro obtidos
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * @deprecated Utilize {@link obterTotalRegistrosPorHQL(String, ParametroDAO)}
     * @see 
     */
    public final int obterTotalRegistros(String strConsulta) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Declara��es
        int intRetorno = 0;

        try
        {
            // Recupera os dados
            intRetorno = ((Integer) obterQueryPorHQLComParametros(strConsulta, null).uniqueResult()).intValue();
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }

        return intRetorno;
    }

    /**
     * Obt�m o total de registros
     * 
     * @param strConsulta String contendo a consulta para contar os registros
     * @return List Contendo os POJOs representando os registro obtidos
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * @see 
     */
    public final int obterTotalRegistrosPorHQL(String strConsulta, ParametroDAO parametros) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        Integer intResultado = (Integer) obterObjetoPorHQL(strConsulta, parametros);
        int retorno = 0;

        if (intResultado != null)
        {
            retorno = intResultado.intValue();
        }
        return retorno;
    }

    /**
     * Obt�m registros de acordo com o filtro especificado
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * @param strConsulta Contendo a consulta
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * @deprecated Use {@link obterListaPorHQL(String, ParametroDAO)} ou {@link obterObjetoPorHQL(String, ParametroDAO)}
     */
    protected final List obter(String strConsulta) throws DAOException
    {
        return obterListaPorHQL(strConsulta, null);
    }

    /**
     * Obt�m registros de acordo com o filtro especificado
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * @param strConsulta Contendo a consulta
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    protected final List obterListaPorHQL(String strConsulta, ParametroDAO parametros) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Declara��es
        List resultado = null;

        try
        {
            // Recupera os dados
            Query query = obterQueryPorHQLComParametros(strConsulta, parametros);

            resultado = query.list();
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }

        return resultado;
    }

    /**
     * Obt�m UM registro de acordo com o filtro especificado
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * @param strConsulta Contendo a consulta
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    protected final Object obterObjetoPorHQL(String strConsulta, ParametroDAO parametros) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Declara��es
        Object resultado = null;

        try
        {
            // Recupera os dados
            Query query = obterQueryPorHQLComParametros(strConsulta, parametros);

            resultado = query.uniqueResult();
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }

        return resultado;
    }

    /**
     * Obt�m UM registro de acordo com o filtro especificado
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * @param strConsulta Contendo a consulta
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     * @deprecated Deixou de ser usado porque, quando n�o realiz�vamos o flush imediatamente, obt�nhamos um erro hibernate to tipo
     * "org.hibernate.AssertionFailure: possible nonthreadsafe access to session"
     */
    protected final Object obterObjetoPorHQLComBloqueio(String strConsulta, ParametroDAO parametros, String aliasDoObjetoABloquear) throws DAOException
    {
        DAOException.dispararExcecao("Este m�todo causa um erro e, por isso, n�o deve mais ser usado.");

        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Declara��es
        Object resultado = null;

        try
        {
            // Recupera os dados
            Query query = obterQueryPorHQLComParametros(strConsulta, parametros, aliasDoObjetoABloquear);

            resultado = query.uniqueResult();
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }

        return resultado;
    }

    /**
     * Obt�m os registros de determinada p�gina, de acordo com o filtro especificado
     * 
     * @param int N�mero da p�gina a ser mostrada
     * @param int Quantidade de registros
     * @param strConsulta Contendo a consulta
     * @return List contendo o resultado da pesquisa
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    protected final List obterPorPagina(int intNumeroPagina, int intMaximoPagina, String strConsulta) throws DAOException
    {
        return obterPorPaginaComParametro(intNumeroPagina, intMaximoPagina, strConsulta, null);
    }

    /**
     * Obt�m os registros de determinada p�gina, de acordo com o filtro especificado
     * 
     * @param int N�mero da p�gina a ser mostrada
     * @param int Quantidade de registros
     * @param strConsulta Contendo a consulta
     * @return List contendo o resultado da pesquisa
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    protected final List obterPorPaginaComParametro(int intNumeroPagina, int intMaximoPagina, String strConsulta, ParametroDAO parametros) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Declara��es
        List lstRetorno = null;

        try
        {
            // Recupera os dados
            Query qryConsulta = obterQueryPorHQLComParametros(strConsulta, parametros);

            if (log.isDebugEnabled())
            {
                log.debug("Conex�o: '" + this.nomeConexao + "' - Consulta: " + strConsulta);
            }

            if (intMaximoPagina > 0)
            {
                qryConsulta.setMaxResults(intMaximoPagina);
                if (intNumeroPagina < 1)
                {
                    intNumeroPagina = 1;
                }
                qryConsulta.setFirstResult((intNumeroPagina - 1) * intMaximoPagina);
            }

            lstRetorno = qryConsulta.list();
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }

        return lstRetorno;
    }

    /**
     * Obt�m todos os registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * @deprecated
     */
    public List obterTodos() throws DAOException
    {
        throw new DAOException(METODO_NAO_IMPLEMENTADO);
    }

    /**
     * Obt�m um registro a partir da chave
     * 
     * @param strChave Chave do registro a ser obtido
     * @return Object POJO representando o registro obtido
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * @deprecated Utilize {@link obterPelaChave(Object)}
     */
    public Object obterPelaChave(String strChave) throws DAOException
    {
        throw new DAOException(METODO_NAO_IMPLEMENTADO);
    }

    /**
     * Obt�m um registro a partir da chave
     * 
     * @param objPOJO POJO contendo o(s) identificador(es) preenchidos para a recupera��o do objeto
     * @return Object POJO representando o registro obtido
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public Object obterPelaChave(Object objPOJO) throws DAOException
    {
        throw new DAOException(METODO_NAO_IMPLEMENTADO);
    }

    /**
     * Verifica se � necess�rio obter sess�o do Hibernate, e chama a implementa��o de inclus�o
     * 
     * @param objPOJO POJO representando o objeto a ser inclu�do
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * @return retorna a chave do objeto que foi incluido
     */
    public String incluir(Object objPOJO) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strRetorno = null;

        Object objRetorno = null;

        try
        {
            Session hbsSession = HibernateUtilCD.obterSessao(this.nomeConexao);

            // Grava este novo objeto
            objRetorno = this.incluirImpl(objPOJO);

            if (objRetorno != null)
            {
                strRetorno = objRetorno.toString();
            }

            if (HibernateUtilCD.realizarFlushImediatamente(this.nomeConexao))
            {
                // Executa os comandos pendentes
                hbsSession.flush();
            }
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }

        return strRetorno;
    }

    /**
     * Verifica se � necess�rio obter sess�o do Hibernate, e chama a implementa��o de altera��o
     * 
     * @param objPOJO POJO representando o objeto a ser alterado
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public final void alterar(Object objPOJO) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        try
        {
            // Obt�m sess�o
            Session hbsSession = HibernateUtilCD.obterSessao(this.nomeConexao);

            // Altera objeto
            this.alterarImpl(objPOJO);

            if (HibernateUtilCD.realizarFlushImediatamente(this.nomeConexao))
            {
                // For�a a execu��o do comando SQL
                hbsSession.flush();
            }
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }
    }

    /**
     * Verifica se � necess�rio obter sess�o do Hibernate, e chama a implementa��o de exclus�o
     * 
     * @param objPOJO POJO representando o objeto a ser exclu�do
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public final void excluir(Object objPOJO) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        try
        {
            // Obt�m sess�o
            Session hbsSession = HibernateUtilCD.obterSessao(this.nomeConexao);

            // Inicia transa��o
            String strConsulta = this.excluirImpl(objPOJO);

            // Despersiste objeto
            if (strConsulta != null)
            {
                this.excluir(strConsulta);
            }
            else
            {
                hbsSession.delete(objPOJO);
                if (HibernateUtilCD.realizarFlushImediatamente(this.nomeConexao))
                {
                    // For�a a execu��o do comando SQL 
                    hbsSession.flush();
                }
            }

        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }
    }

    /**
     * Verifica se � necess�rio obter sess�o do Hibernate, e efetua uma exclus�o partir de um HQL passado
     * 
     * @param objPOJO POJO representando o objeto a ser exclu�do
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    protected final void excluir(String strConsulta) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        try
        {
            // Obt�m sess�o
            Session hbsSession = HibernateUtilCD.obterSessao(this.nomeConexao);

            // Exclui
            if (strConsulta.indexOf("DELETE ") == -1)
            {
                strConsulta = "DELETE " + strConsulta;
            }

            Query query = obterQueryPorHQLComParametros(strConsulta, null);
            if (query != null)
            {
                query.executeUpdate();
            }

            if (log.isDebugEnabled())
            {
                log.debug("Conex�o: '" + this.nomeConexao + "' - Consulta: " + strConsulta);
            }

            if (HibernateUtilCD.realizarFlushImediatamente(this.nomeConexao))
            {
                // For�a a execu��o do comando SQL
                hbsSession.flush();
            }
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }
    }

    /**
     * Verifica se � necess�rio obter sess�o do Hibernate, e chama a implementa��o de exclus�o
     * 
     * @param objPOJO POJO representando o objeto a ser exclu�do
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * @deprecated
     */
    public final void excluirSQL(Object objPOJO) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        try
        {
            String strConsulta = this.excluirImpl(objPOJO);

            // Despersiste objeto
            if (strConsulta != null)
            {
                // Executa SQL
                HibernateUtilCD.executarSQL(this.nomeConexao, strConsulta);
            }
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }
    }

    /**
     * Executa um comando SQL informado como par�metro
     * 
     * @deprecated Utilizar {@link executarSQL(String)}
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public final void excluirSQL(String objPOJO) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        this.executarSQL(objPOJO);
    }

    /**
     * Executa um comando SQL informado como par�metroo
     * 
     * @param objPOJO POJO representando o SQL a ser executado
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * @deprecated Utilize preferencial {@link executarSQLComParametros(String, ParametroDAO)}
     */
    public final int executarSQL(String objPOJO) throws DAOException
    {
        int retorno = 0;
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        try
        {
            // Executa SQL
            retorno = HibernateUtilCD.executarSQL(this.nomeConexao, objPOJO);
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }

        return retorno;
    }

    /**
     * Executa um comando SQL informado como par�metroo
     * 
     * @param objPOJO POJO representando o SQL a ser executado
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public final int executarSQLComParametros(String objPOJO, ParametroDAO parametros) throws DAOException
    {
        int retorno = 0;
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        try
        {
            // Executa SQL
            retorno = HibernateUtilCD.executarSQLComParametros(this.nomeConexao, objPOJO, parametros);
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }

        return retorno;
    }

    /**
     * Inclui um registro
     * 
     * @param SituacaoServidor POJO representando o objeto a ser inclu�do
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * @return Retorna um objeto serializado simbolizando a chave do objeto inserido
     */
    protected Serializable incluirImpl(Object objPOJO) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        Serializable objSerializado = null;

        try
        {
            // Obt�m sess�o
            Session hbsSession = HibernateUtilCD.obterSessao(this.nomeConexao);

            // Grava este novo objeto
            objSerializado = hbsSession.save(objPOJO);
        }
        catch (Throwable he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }

        return objSerializado;
    }

    /**
     * Altera um registro
     * 
     * @param SituacaoServidor POJO representando o objeto a ser alterado
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    protected void alterarImpl(Object objPOJO) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        try
        {
            // Obt�m sess�o
            Session hbsSession = HibernateUtilCD.obterSessao(this.nomeConexao);

            // Atualiza objeto
            hbsSession.update(objPOJO);
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }
    }

    /**
     * M�todo que monta um comando SQL para a exclus�o do registro relacionado com o objeto informado no par�metro
     * 
     * @param SituacaoServidor POJO representando o objeto a ser exclu�do
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * @return String com o comando SQL para excluir o registro relacionado com o par�metro
     */
    protected String excluirImpl(Object objPOJO) throws DAOException
    {
        // Retorna null por default para informar que a exclus�o n�o ser� feita por SQL 
        return null;
    }

    /**
     * Inicializa o relacionamento do objeto passado
     * 
     * @param objRelacionado Objeto desejado
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    protected final void inicializarRelacionamento(Object objRelacionado) throws DAOException
    {
        try
        {
            Hibernate.initialize(objRelacionado);
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }
    }

    /**
     * Informa se o relacionamento est� ou n�o iniciliazado
     * 
     * @param objRelacionado Objeto desejado
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public static final boolean estaInicializado(Object objRelacionado) throws DAOException
    {
        boolean retorno = false;
        try
        {
            retorno = Hibernate.isInitialized(objRelacionado);
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he);
        }

        return retorno;
    }

    /**
     * Obt�m registros de acordo com um comando SQL especificado
     * 
     * @return List Contendo os POJOs representando os registros obtidos
     * @param strConsulta SQL contendo a consulta
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * @deprecated Utilize preferencialmente {@link #obterPorSQLComParametros(String, ParametroDAO)}
     */
    public final List obterPorSQL(String strConsulta) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Declara��es
        List lstResultado = null;

        try
        {
            // Executa SQL
            lstResultado = HibernateUtilCD.carregarPorSQL(this.nomeConexao, strConsulta);
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }

        return lstResultado;
    }

    /**
     * Obt�m registros de acordo com um comando SQL especificado e respectivos par�metros
     * 
     * @param strConsulta SQL contendo a consulta
     * @param parametros Par�metros da consulta
     * @return List Contendo os POJOs representando os registros obtidos
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public final List obterPorSQLComParametros(String strConsulta, ParametroDAO parametros) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Declara��es
        List lstResultado = null;

        try
        {
            // Executa SQL
            lstResultado = HibernateUtilCD.carregarPorSQLComParametros(this.nomeConexao, strConsulta, parametros);
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }

        return lstResultado;
    }

    /**
     * Obt�m propriedade do arquivo de acordo com a chave passada
     * 
     * @param strMascara String contendo as m�scaras a serem substitu�das
     * @param strParam0 Valor do parametro que substitui a primeira m�scara {0}
     * @return String Contendo a cl�usula query correta
     */
    public final String realizarSubstituicao(String strMascara, String strParam0)
    {
        return StringUtil.realizarSubstituicao(strMascara, strParam0);
    }

    /**
     * Obt�m propriedade do arquivo de acordo com a chave passada
     * 
     * @param strMascara String contendo as m�scaras a serem substitu�das
     * @param strParam0 Valor do parametro que substitui a primeira m�scara {0}
     * @param strParam1 Valor do parametro que substitui a segunda m�scara {1}
     * @return String Contendo a cl�usula query correta
     */
    public final String realizarSubstituicao(String strMascara, String strParam0, String strParam1)
    {
        return StringUtil.realizarSubstituicao(strMascara, strParam0, strParam1);
    }

    /**
     * Obt�m propriedade do arquivo de acordo com a chave passada
     * 
     * @param strMascara String contendo as m�scaras a serem substitu�das
     * @param strParam0 Valor do parametro que substitui a primeira m�scara {0}
     * @param strParam1 Valor do parametro que substitui a segunda m�scara {1}
     * @param strParam2 Valor do parametro que substitui a terceira m�scara {2}
     * @return String Contendo a cl�usula query correta
     */
    public final String realizarSubstituicao(String strMascara, String strParam0, String strParam1, String strParam2)
    {
        return StringUtil.realizarSubstituicao(strMascara, strParam0, strParam1, strParam2);
    }

    /**
     * Obt�m propriedade do arquivo de acordo com a chave passada
     * 
     * @param strMascara String contendo as m�scaras a serem substitu�das
     * @param strParam0 Valor do parametro que substitui a primeira m�scara {0}
     * @param strParam1 Valor do parametro que substitui a segunda m�scara {1}
     * @param strParam2 Valor do parametro que substitui a terceira m�scara {2}
     * @param strParam3 Valor do parametro que substitui a quarta m�scara {3}
     * @return String Contendo
     */
    public final String realizarSubstituicao(String strMascara, String strParam0, String strParam1, String strParam2, String strParam3)
    {
        return StringUtil.realizarSubstituicao(strMascara, strParam0, strParam1, strParam2, strParam3);
    }

    public final String obterPropriedadeSubstituicao(String chave)
    {
        return HibernateUtilCD.obterPropriedadeSubstituicao(this.nomeConexao, chave);
    }

    public boolean isDialetoTratado() throws DAOException
    {
        boolean dialetoTratado = false;
        dialetoTratado = (this.isIngres() ? true : dialetoTratado || false);
        dialetoTratado = (this.isOracle() ? true : dialetoTratado || false);
        dialetoTratado = (this.isMySQL() ? true : dialetoTratado || false);
        return dialetoTratado;
    }

    public final String obterCampoDataAtual() throws DAOException
    {
        if (this.strCampoData == null)
        {
            if (this.isIngres())
            {
                this.strCampoData = "Date('today')";
            }
            else if (this.isOracle())
            {
                this.strCampoData = "TO_DATE(TO_CHAR(SYSDATE, 'DD/MM/YYYY'), 'DD/MM/YYYY')"; // "TO_CHAR(SYSDATE, 'DD/MM/YYYY')";
            }
            else if (this.isMySQL())
            {
                this.strCampoData = "STR_TO_DATE(DATE_FORMAT(NOW(), '%d/%m/%Y'), '%d/%m/%Y')"; // "DATE_FORMAT(NOW(), '%d/%m/%Y')";
            }
            else
            {
                throw new DAOException("O dialeto Hibernate em uso, " + obterDialeto() + ", ainda n�o � tratado por este m�todo.");
            }
        }

        return this.strCampoData;
    }

    public final String obterCampoDataHoraAtual() throws DAOException
    {
        if (this.strCampoData == null)
        {
            if (this.isIngres())
            {
                this.strCampoData = "Date('now')";
            }
            else if (this.isOracle())
            {
                this.strCampoData = "TO_DATE(TO_CHAR(SYSDATE, 'DD/MM/YYYY HH:MI:SS'), 'DD/MM/YYYY HH:MI:SS')";
            }
            else if (this.isMySQL())
            {
                this.strCampoData = "STR_TO_DATE(DATE_FORMAT(NOW(), '%d/%m/%Y %H:%i:%s'), '%d/%m/%Y %H:%i:%s')"; // "DATE_FORMAT(NOW(), '%d/%m/%Y')";
            }
            else
            {
                throw new DAOException("O dialeto Hibernate em uso, " + obterDialeto() + ", ainda n�o � tratado por este m�todo.");
            }
        }

        return this.strCampoData;
    }

    protected final String obterDialeto()
    {
        if (this.dialeto == null)
        {
            this.dialeto = HibernateUtilCD.obterDialeto(this.nomeConexao);
        }

        return this.dialeto;
    }

    private boolean isIngres()
    {
        if (this.isDialetoIngres == null)
        {
            if (obterDialeto().toLowerCase().indexOf("ingres") > -1)
            {
                this.isDialetoIngres = Boolean.TRUE;
                this.isDialetoOracle = Boolean.FALSE;
                this.isDialetoMySQL = Boolean.FALSE;
            }
            else
            {
                this.isDialetoIngres = Boolean.FALSE;
            }
        }
        return this.isDialetoIngres;
    }

    private boolean isOracle()
    {
        if (this.isDialetoOracle == null)
        {
            if (obterDialeto().toLowerCase().indexOf("oracle") > -1)
            {
                this.isDialetoIngres = Boolean.FALSE;
                this.isDialetoOracle = Boolean.TRUE;
                this.isDialetoMySQL = Boolean.FALSE;
            }
            else
            {
                this.isDialetoOracle = Boolean.FALSE;
            }
        }
        return this.isDialetoOracle;
    }

    private boolean isMySQL()
    {
        if (this.isDialetoMySQL == null)
        {
            if (obterDialeto().toLowerCase().indexOf("mysql") > -1)
            {
                this.isDialetoIngres = Boolean.FALSE;
                this.isDialetoOracle = Boolean.FALSE;
                this.isDialetoMySQL = Boolean.TRUE;
            }
            else
            {
                this.isDialetoMySQL = Boolean.FALSE;
            }
        }
        return this.isDialetoMySQL;
    }

    private String formatarParametroDate(Date datParametro, boolean comHora) throws DAOException
    {
        String strFormato = null;
        String retorno = null;

        if (this.isIngres())
        {
            strFormato = "MM/dd/yyyy";
            if (comHora)
            {
                strFormato += " hh:mm:ss";
            }
        }
        else if (this.isOracle())
        {
            strFormato = "dd/MM/yyyy";
            if (comHora)
            {
                strFormato += " hh:mm:ss";
            }
        }
        else if (this.isMySQL())
        {
            strFormato = "yyyy-MM-dd";
            if (comHora)
            {
                strFormato += " HH:mm:ss";
            }
        }
        else
        {
            throw new DAOException("O dialeto Hibernate em uso, " + obterDialeto() + ", ainda n�o � tratado por este m�todo.");
        }

        try
        {
            retorno = DataNova.formatarData(datParametro, strFormato);
        }
        catch (DataException de)
        {
            throw new DAOException("A data informada (" + datParametro + ") n�o � v�lida.");
        }
        return retorno;
    }

    public final String formatarParametroData(Date datParametro) throws DAOException
    {
        return this.formatarParametroDate(datParametro, false);
    }

    public final String formatarParametroDataHora(Date datParametro) throws DAOException
    {
        return this.formatarParametroDate(datParametro, true);
    }

    public static final String obterNomeConexaoTabela(String nomeTabela) throws DAOException
    {
        return HibernateUtilCD.obterNomeConexaoTabela(nomeTabela);
    }

    /**
     * Executa a query HQL especificada e retorna quantos registros foram afetados
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * @param strConsulta Contendo a consulta
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    protected final int executarHQL(String strConsulta) throws DAOException
    {
        return executarHQLComParametros(strConsulta, null);
    }

    /**
     * Executa a query HQL especificada e retorna quantos registros foram afetados
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     * @param strConsulta Contendo a consulta
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    protected final int executarHQLComParametros(String strConsulta, ParametroDAO parametros) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Declara��es
        int lstRetorno = 0;

        try
        {
            // Recupera os dados
            Query query = obterQueryPorHQLComParametros(strConsulta, parametros);
            lstRetorno = query.executeUpdate();
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }

        return lstRetorno;
    }

    /**
     * Obt�m registros de acordo com um comando SQL especificado e respectivos par�metros
     * 
     * @param strConsulta SQL contendo a consulta
     * @param parametros Par�metros da consulta
     * @return List Contendo os POJOs representando os registros obtidos
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    private Query obterQueryPorHQLComParametros(String consultaHQL, ParametroDAO parametros) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        Query query = null;

        try
        {
            Session hbsSession = HibernateUtilCD.obterSessao(this.nomeConexao);

            if (log.isDebugEnabled())
            {
                log.debug("Conex�o: '" + this.nomeConexao + "' - Consulta: " + consultaHQL);
            }

            // Recupera os dados
            query = hbsSession.createQuery(consultaHQL);
            if (parametros != null)
            {
                query = parametros.setarParametroQuery(query);
            }

        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }

        return query;
    }

    /**
     * Obt�m registros de acordo com um comando SQL especificado e respectivos par�metros
     * 
     * @param strConsulta SQL contendo a consulta
     * @param parametros Par�metros da consulta
     * @return List Contendo os POJOs representando os registros obtidos
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     * @deprecated Deixou de ser usado porque, quando n�o realiz�vamos o flush imediatamente, obt�nhamos um erro hibernate to tipo
     * "org.hibernate.AssertionFailure: possible nonthreadsafe access to session"
     */
    private Query obterQueryPorHQLComParametros(String consultaHQL, ParametroDAO parametros, String aliasDoObjetoABloquear) throws DAOException
    {
        DAOException.dispararExcecao("Este m�todo causa um erro e, por isso, n�o deve mais ser usado.");

        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        Query query = null;

        try
        {
            Session hbsSession = HibernateUtilCD.obterSessao(this.nomeConexao);

            if (log.isDebugEnabled())
            {
                log.debug("Conex�o: '" + this.nomeConexao + "' - Consulta: " + consultaHQL);
            }

            // Recupera os dados
            query = hbsSession.createQuery(consultaHQL);
            if (parametros != null)
            {
                query = parametros.setarParametroQuery(query);
            }

            if (aliasDoObjetoABloquear != null)
            {
                query.setLockMode(aliasDoObjetoABloquear, LockMode.UPGRADE);
            }

        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }

        return query;
    }

    /**
     * Remove a inst�ncia de um objeto do cache do hibernate.
     * ATEN��O: esta solu��o n�o deve ser utilizada, pois se existe alguma atualiza��o pendente que ainda n�o foi "commitada"
     * (sobretudo com a utiliza��o do !realizarFlushImediato), a atualiza��o n�o � realizada no banco de dados.
     * 
     * @param SituacaoServidor POJO representando o objeto a ser alterado
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * @deprecated
     */
    public final void removerObjetoDoCache(Object objPOJO) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        try
        {
            //            // Obt�m sess�o
            //            Session hbsSession = HibernateUtilCD.obterSessao(this.nomeConexao);
            //
            //            // Remover do cache da sess�o hibernate
            //            hbsSession.evict(objPOJO);
            throw new DAOException("Este m�todo - removerObjetoDoCache - n�o deve ser utilizado. Ver coment�rio.");
        }
        catch (HibernateException he)
        {
            DAOException.dispararExcecao(he, obterDialeto());
        }
    }
}

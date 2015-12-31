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

package br.gov.camara.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.function.ClassicAvgFunction;
import org.hibernate.dialect.function.ClassicCountFunction;
import org.hibernate.dialect.function.ClassicSumFunction;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.jdbc.util.SQLStatementLogger;
import org.hibernate.mapping.Table;

import sigesp.comum.util.hibernate.ColecaoDadosConexao;
import sigesp.comum.util.hibernate.DadosConexao;
import br.gov.camara.biblioteca.util.ClasseDinamica;
import br.gov.camara.exception.CDException;
import br.gov.camara.hibernate.exception.HibernateExceptionCD;
import br.gov.camara.hibernate.interceptor.operation.OperationInterceptorManager;
import br.gov.camara.hibernate.interceptor.session.SessionInterceptorManager;

public class GerenciadorConfiguracaoHibernate
{
    /**
    * Logger for this class
    */
    private static final Log log = LogFactory.getLog(GerenciadorConfiguracaoHibernate.class);

    protected static final int CONEXAO_POSICAO_DADOS_CONEXAO = 0;
    protected static final int CONEXAO_POSICAO_SESSION_FACTORY = 1;
    protected static final int CONEXAO_POSICAO_SQL_LOGGER = 2;
    protected static final int CONEXAO_POSICOES_TOTAL = 3;

    protected static final String CHAVE_INTERCEPTOR_MANAGER_SESSION = "sessionInterceptorManager";
    protected static final String CHAVE_DELIMITADORA_SESSAO_TRANSACAO_IMPL = "delimitadoraSessaoTransacaoImpl";

    protected static final String CHAVE_REALIZAR_FLUSH_IMEDIATAMENTE = "realizar.flush.imediatamente";

    protected static final String CHAVE_HIBERNATE_SQL_SHOW = "hibernate.show_sql";
    protected static final String CHAVE_HIBERNATE_SQL_FORMAT = "hibernate.format_sql";

    protected static final String CLASSE_PADRAO_DELIMITADORA_SESSAO_TRANSACAO = "br.gov.camara.hibernate.FacadeDelimitadora";

    protected static Map mapPersistenciaSessionFactory;
    protected static Map mapNomesConexoes;
    protected static String nomeConexaoPadrao;

    private static boolean inicializado = false;

    private GerenciadorConfiguracaoHibernate()
    {
        super();
    }

    public synchronized static void iniciar(ColecaoDadosConexao conexoesDados)
    {
        if (inicializado)
        {
            if (log.isInfoEnabled())
            {
                log.info("Gerenciador de configurações do Hibernate já inicializado");
            }
        }
        else
        {
            if (log.isInfoEnabled())
            {
                log.info("Inicializando o gerenciador de configurações do Hibernate...");
            }

            String strNomeConexaoPadrao = (String) conexoesDados.obterConexaoPeloIndice(0).getNomesConexao().get(0);
            Iterator iteratorConexoes = conexoesDados.obterIterator();

            Map mapPersistSF = new HashMap();
            Map mapNomesConex = new HashMap();

            List listaGeralTabelas = new ArrayList();

            int numeroConexao = 0;
            while (iteratorConexoes.hasNext())
            {
                DadosConexao dadosConexao = (DadosConexao) iteratorConexoes.next();
                if (log.isInfoEnabled())
                {
                    log.info("Inicializando a conexão '" + numeroConexao + "'");
                }
                Configuration configuracaoHibernate = criarConfiguracaoHibernate(dadosConexao);

                // Configura os Interceptadores com operações de persistência
                List interceptorsOperation = dadosConexao.getInterceptorsPorTipo(OperationInterceptorManager.TIPO_INTERCEPTOR_OPERATION);
                if (interceptorsOperation != null)
                {
                    configuracaoHibernate.setInterceptor(new OperationInterceptorManager(interceptorsOperation));
                }

                // Para manter a compatibilidade (o retorno continuará sendo Integer ao invês de Long, como foi alterado)
                configuracaoHibernate.addSqlFunction("count", new ClassicCountFunction());
                configuracaoHibernate.addSqlFunction("avg", new ClassicAvgFunction());
                configuracaoHibernate.addSqlFunction("sum", new ClassicSumFunction());

                // Para manter a compatibilidade com o comportamento das sessões na versão 2
                configuracaoHibernate.setProperty("hibernate.connection.release_mode", "on_close");

                // Cria um mapa de todas as tabelas referenciadas nesta conexão
                List mapTabelas = dadosConexao.getMapeamentosTabelas();
                Iterator itrTabelas = configuracaoHibernate.getTableMappings();
                while (itrTabelas.hasNext())
                {
                    String nomeTable = ((Table) itrTabelas.next()).getName().toLowerCase();

                    if (mapTabelas.contains(nomeTable))
                    {
                        if (log.isWarnEnabled())
                        {
                            log.warn("*** ATENÇÃO: O nome da tabela '"
                                    + nomeTable
                                    + "' está sendo referenciado mais de uma vez na conexão '"
                                    + dadosConexao.getNomesConexao().toString()
                                    + "'.");
                        }
                    }
                    if (listaGeralTabelas.contains(nomeTable))
                    {
                        if (log.isWarnEnabled())
                        {
                            log.warn("*** ATENÇÃO: O nome da tabela '" + nomeTable + "' está sendo referenciado em MAIS DE UMA conexão distinta.");
                        }
                    }
                    mapTabelas.add(nomeTable);
                }
                listaGeralTabelas.addAll(mapTabelas);

                // Constroi a fábrica de sessão do Hibernate
                SessionFactory sessionFactory = configuracaoHibernate.buildSessionFactory();

                // Configura os Interceptadores de sessão
                List interceptorsSession = dadosConexao.getInterceptorsPorTipo(SessionInterceptorManager.TIPO_INTERCEPTOR_SESSION);
                if (interceptorsSession != null && interceptorsSession.size() > 0)
                {
                    dadosConexao.setObjetoConexao(CHAVE_INTERCEPTOR_MANAGER_SESSION, new SessionInterceptorManager(interceptorsSession));
                }

                // Configura a Delimitadora da Facade (responsável por controlar as conexões e as transações)
                DelimitadoraSessaoTransacao objFacadeChamadora = null;
                String nomeDelimitadoraSessaoTransacaoImpl = obterPropriedadeConexao(dadosConexao, CHAVE_DELIMITADORA_SESSAO_TRANSACAO_IMPL);
                if (nomeDelimitadoraSessaoTransacaoImpl == null || "".equals(nomeDelimitadoraSessaoTransacaoImpl))
                {
                    nomeDelimitadoraSessaoTransacaoImpl = System.getProperty("delimitadoraSessaoTransacaoImpl");
                    if (nomeDelimitadoraSessaoTransacaoImpl == null || "".equals(nomeDelimitadoraSessaoTransacaoImpl))
                    {
                        nomeDelimitadoraSessaoTransacaoImpl = CLASSE_PADRAO_DELIMITADORA_SESSAO_TRANSACAO;
                    }
                }
                try
                {
                    objFacadeChamadora = (DelimitadoraSessaoTransacao) new ClasseDinamica().obterInstancia(nomeDelimitadoraSessaoTransacaoImpl);
                }
                catch (CDException e)
                {
                    throw new RuntimeException("Erro ao instanciar a classe delimitadora das sessões e transações: " + e.getMessage(), e);
                }
                dadosConexao.setObjetoConexao(CHAVE_DELIMITADORA_SESSAO_TRANSACAO_IMPL, objFacadeChamadora);

                // Configura o SQL Logger da conexão
                boolean sqlLog = "true".equals(obterPropriedadeConexao(dadosConexao, CHAVE_HIBERNATE_SQL_SHOW));
                boolean sqlFormat = "true".equals(obterPropriedadeConexao(dadosConexao, CHAVE_HIBERNATE_SQL_FORMAT));
                SQLStatementLogger statementLogger = new SQLStatementLogger(sqlLog, sqlFormat);

                // Guarda os objetos da conexão
                ArrayList conexao = new ArrayList(CONEXAO_POSICOES_TOTAL);
                conexao.add(CONEXAO_POSICAO_DADOS_CONEXAO, dadosConexao);
                conexao.add(CONEXAO_POSICAO_SESSION_FACTORY, sessionFactory);
                conexao.add(CONEXAO_POSICAO_SQL_LOGGER, statementLogger);

                // Cria o ponteiro com os diversos nomes das conexões
                List lstNomesConexao = dadosConexao.getNomesConexao();
                String strNomeConexao;
                for (int i = 0; i < lstNomesConexao.size(); i++)
                {
                    strNomeConexao = (String) lstNomesConexao.get(i);
                    mapNomesConex.put(strNomeConexao, Integer.toString(numeroConexao));
                }

                mapPersistSF.put(Integer.toString(numeroConexao), conexao);

                numeroConexao++;
            }

            if (mapPersistSF.size() == 0)
            {
                throw new HibernateExceptionCD("Nenhuma conexão Hibernate foi inicializada");
            }

            mapPersistenciaSessionFactory = mapPersistSF;
            mapNomesConexoes = mapNomesConex;

            nomeConexaoPadrao = strNomeConexaoPadrao;

            inicializado = true;

            if (log.isInfoEnabled())
            {
                log.info("... fim da inicialização do gerenciador de configurações do Hibernate...");
            }
        }
    }

    /**
     * Retorna o identificador da conexão associado com o nome da conexão informado (alias)
     * 
     * @return número da conexão informada
     */
    protected static String obterPonteiroConexao(String nomeConexao) throws HibernateExceptionCD
    {
        if (log.isDebugEnabled())
        {
            log.debug("Obtendo o ponteiro para a conexão '" + nomeConexao + "'");
        }

        if (!inicializado)
        {
            throw new HibernateExceptionCD("O Hibernate ainda não foi inicializado.");
        }

        String ponteiroConexao;

        Map mapNomes = mapNomesConexoes;
        ponteiroConexao = (String) mapNomes.get(nomeConexao);

        if (ponteiroConexao == null)
        {
            throw new HibernateExceptionCD("Não foi encontrada nenhuma conexão com o nome '" + nomeConexao + "'");
        }

        if (log.isDebugEnabled())
        {
            log.debug("Retornando o nome do ponteiro para a conexão '" + nomeConexao + "' = '" + ponteiroConexao + "'");
        }

        return ponteiroConexao;
    }

    private static DadosConexao obterDadosConexao(String nomeConexao)
    {
        Map mapPersistSF = mapPersistenciaSessionFactory;
        List lstconexao = null;

        try
        {
            String strPonteiro = GerenciadorConfiguracaoHibernate.obterPonteiroConexao(nomeConexao);
            lstconexao = (List) mapPersistSF.get(strPonteiro);
        }
        catch (HibernateException he)
        {
            // Sem problemas ... Retornar nulo
        }

        DadosConexao dadosConexao = null;
        if (lstconexao != null)
        {
            dadosConexao = (DadosConexao) lstconexao.get(CONEXAO_POSICAO_DADOS_CONEXAO);
        }

        return dadosConexao;
    }

    public static SQLStatementLogger obterGeradorLogSQL(String nomeConexao)
    {
        Map mapPersistSF = mapPersistenciaSessionFactory;
        List lstconexao = null;

        try
        {
            if (nomeConexao == null)
            {
                nomeConexao = nomeConexaoPadrao;
            }

            String strPonteiro = GerenciadorConfiguracaoHibernate.obterPonteiroConexao(nomeConexao);
            lstconexao = (List) mapPersistSF.get(strPonteiro);
        }
        catch (HibernateException he)
        {
            // Sem problemas ... Retornar nulo
        }

        SQLStatementLogger dadosConexao = null;
        if (lstconexao != null)
        {
            dadosConexao = (SQLStatementLogger) lstconexao.get(CONEXAO_POSICAO_SQL_LOGGER);
        }

        return dadosConexao;
    }

    /**
     * Fetch the SessionFactory from application scope.
     * 
     * @param request The requeste we are servicing
     * @return The SessionFactory for this application session
     * @throws HibernateExceptionCD
     */
    protected static SessionFactory obterSessionFactory(String nomeConexao) throws HibernateExceptionCD
    {
        if (nomeConexao == null)
        {
            nomeConexao = nomeConexaoPadrao;
        }

        if (log.isDebugEnabled())
        {
            log.debug("Tentando recuperar a SessionFactory da conexão '" + nomeConexao + "'...");
        }

        if (!inicializado)
        {
            throw new HibernateExceptionCD("O Hibernate ainda não foi inicializado.");
        }

        Map mapConexoes = mapPersistenciaSessionFactory;

        List lstConexao = (List) mapConexoes.get(nomeConexao);

        if (lstConexao == null)
        {
            if (log.isErrorEnabled())
            {
                log.error("Não foi encontrada a SessionFactory da conexão '" + nomeConexao + "'");
            }
            throw new HibernateExceptionCD("Não foi encontrada a SessionFactory da conexão '" + nomeConexao + "'");
        }

        SessionFactory hsfSessionFactory = (SessionFactory) lstConexao.get(CONEXAO_POSICAO_SESSION_FACTORY);

        if (hsfSessionFactory == null)
        {
            if (log.isErrorEnabled())
            {
                log.error("A SessionFactory recuperada da conexão '" + nomeConexao + "' é nula");
            }
            throw new HibernateExceptionCD("A SessionFactory recuperada da conexão '" + nomeConexao + "' é nula");
        }

        if (log.isDebugEnabled())
        {
            log.debug("A SessionFactory da conexão '" + nomeConexao + "' foi recuperada com sucesso");
        }

        return hsfSessionFactory;
    }

    private static Configuration criarConfiguracaoHibernate(DadosConexao dadosConexao) throws HibernateExceptionCD
    {
        // Mandar para o hibernate cada configuração lida
        Configuration configuracaoHibernate = new Configuration();

        // Mandar os parâmetros da conexão
        configuracaoHibernate.addProperties(dadosConexao.getPropriedadesConexao());

        // Mandar os mapeamentos da conexão
        Iterator iteratorMapeamentos = dadosConexao.getMapeamentosConexao().iterator();

        String arquivoMapeamento = null;
        while (iteratorMapeamentos.hasNext())
        {
            arquivoMapeamento = (String) iteratorMapeamentos.next();
            try
            {
                configuracaoHibernate.addResource(arquivoMapeamento);
            }
            catch (MappingException me)
            {
                throw new HibernateExceptionCD(me);
            }
        }

        return configuracaoHibernate;
    }

    /**
     * Obtém o conteúdo da chave da conexão informada
     * 
     * @param nome da conexão
     * @param chave a ser recuperada
     * @return conteúdo da chave recuperada
     */
    static DelimitadoraSessaoTransacao obterDelimitadoraSessaoTransacaoConexao(String nomeConexao)
    {
        DelimitadoraSessaoTransacao retorno = null;

        if (nomeConexao == null)
        {
            nomeConexao = nomeConexaoPadrao;
        }

        DadosConexao dadosConexao = obterDadosConexao(nomeConexao);
        if (dadosConexao != null)
        {
            retorno = (DelimitadoraSessaoTransacao) dadosConexao.getObjetoConexao(CHAVE_DELIMITADORA_SESSAO_TRANSACAO_IMPL);
        }

        return retorno;
    }

    /**
     * Obtém o conteúdo da chave da conexão informada
     * 
     * @param nome da conexão
     * @param chave a ser recuperada
     * @return conteúdo da chave recuperada
     */
    static SessionInterceptorManager obterSessionInterceptorManagerConexao(String nomeConexao)
    {
        SessionInterceptorManager retorno = null;

        if (nomeConexao == null)
        {
            nomeConexao = nomeConexaoPadrao;
        }

        DadosConexao dadosConexao = obterDadosConexao(nomeConexao);
        if (dadosConexao != null)
        {
            retorno = (SessionInterceptorManager) dadosConexao.getObjetoConexao(CHAVE_INTERCEPTOR_MANAGER_SESSION);
        }

        return retorno;
    }

    /**
     * Obtém o nome da conexão padrão
     * 
     * @return nome da conexão padrão
     */
    public static String obterNomeConexaoPadrao()
    {
        return nomeConexaoPadrao;
    }

    /**
     * Retorna nome de uma das conexões associadas com o identificador (número) informado
     * 
     * @return nome da conexão
     */
    protected static String obterNomeConexao(String ponteiroConexao) throws HibernateExceptionCD
    {
        if (log.isDebugEnabled())
        {
            log.debug("Obtendo o nome de uma conexão que aponta para '" + ponteiroConexao + "'");
        }

        if (!inicializado)
        {
            throw new HibernateExceptionCD("O Hibernate ainda não foi inicializado.");
        }

        Map mapNomes = mapNomesConexoes;

        Set setTransacoesAbertas = mapNomes.keySet();
        String strNomeConexao = null;

        // Recupera os dados das transações abertas
        if (setTransacoesAbertas != null)
        {
            Iterator itrTransacoesAbertas = setTransacoesAbertas.iterator();
            while (itrTransacoesAbertas.hasNext())
            {
                String nomeConexao = (String) itrTransacoesAbertas.next();
                String strPonteiro = GerenciadorConfiguracaoHibernate.obterPonteiroConexao(nomeConexao);

                if (ponteiroConexao.equals(strPonteiro))
                {
                    strNomeConexao = nomeConexao;
                    break;
                }
            }
        }

        if (strNomeConexao == null)
        {
            throw new HibernateExceptionCD("Não foi encontrada nenhuma conexão que aponta para '" + ponteiroConexao + "'");
        }

        if (log.isDebugEnabled())
        {
            log.debug("Retornando o nome da conexão '" + strNomeConexao + "' que aponta para o ponteiro '" + ponteiroConexao + "'");
        }

        return strNomeConexao;
    }

    /**
     * Obtém o dialeto especificado para a conexão padrão
     * 
     * @return nome do dialeto
     */
    public static String obterDialeto()
    {
        return obterDialeto(nomeConexaoPadrao);
    }

    /**
     * Obtém o dialeto especificado para a conexão especificada
     * 
     * @return nome do dialeto
     */
    public static String obterDialeto(String nomeConexao)
    {
        if (nomeConexao == null)
        {
            nomeConexao = nomeConexaoPadrao;
        }

        String strDialeto = null;

        try
        {
            SessionFactoryImplementor objSessionFactoryImpl = (SessionFactoryImplementor) GerenciadorConfiguracaoHibernate.obterSessionFactory(GerenciadorConfiguracaoHibernate.obterPonteiroConexao(nomeConexao));
            if (objSessionFactoryImpl != null)
            {
                strDialeto = objSessionFactoryImpl.getDialect().toString();
            }
        }
        catch (HibernateException he)
        {
            // Não precisa fazer nada... Paciência...
        }

        return strDialeto;
    }

    /**
     * Obtém a propriedade que indica se é para realizar o flush imediato ou não
     * 
     * @return indicativo se é para executar o flush imediatamente depois de alguns comandos hibernate
     */
    public static boolean realizarFlushImediatamente(String nomeConexao)
    {
        if (nomeConexao == null)
        {
            nomeConexao = nomeConexaoPadrao;
        }

        String valor = null;
        boolean retorno = false;

        DadosConexao dadosConexao = obterDadosConexao(nomeConexao);

        valor = obterPropriedadeConexao(dadosConexao, CHAVE_REALIZAR_FLUSH_IMEDIATAMENTE);
        if (valor != null && "true".equals(valor))
        {
            retorno = true;
        }

        return retorno;
    }

    /**
     * Obtém o conteúdo da propriedade da conexão informada
     * 
     * @param nome da conexão
     * @param chave a ser recuperada
     * @return conteúdo da chave recuperada
     */
    public static String obterPropriedadeConexao(String nomeConexao, String chave)
    {
        if (nomeConexao == null)
        {
            nomeConexao = nomeConexaoPadrao;
        }

        String valor = null;

        DadosConexao dadosConexao = obterDadosConexao(nomeConexao);

        valor = obterPropriedadeConexao(dadosConexao, chave);
        return valor;
    }

    /**
     * Obtém o conteúdo da propriedade da conexão informada
     * 
     * @param dadosConexao Dados da conexão
     * @param chave a ser recuperada
     * @return conteúdo da chave recuperada
     */
    private static String obterPropriedadeConexao(DadosConexao dadosConexao, String chave)
    {
        String valor = null;

        if (dadosConexao != null)
        {
            Properties propriedades = dadosConexao.getPropriedadesConexao();
            valor = propriedades.getProperty(chave);
        }

        return valor;
    }

    /**
     * Obtém o conteúdo da chave da conexão informada
     * 
     * @param nome da conexão
     * @param chave a ser recuperada
     * @return conteúdo da chave recuperada
     */
    public static String obterPropriedadeSubstituicao(String nomeConexao, String chave)
    {
        if (nomeConexao == null)
        {
            nomeConexao = nomeConexaoPadrao;
        }

        String valor = null;

        DadosConexao dadosConexao = obterDadosConexao(nomeConexao);
        if (dadosConexao != null)
        {
            Properties propriedades = dadosConexao.getSubstituicoesConexao();
            valor = propriedades.getProperty(chave);
        }

        return valor;
    }

    public synchronized static void finalizar()
    {
        if (mapPersistenciaSessionFactory != null)
        {
            Set nomeConfiguracoes = mapPersistenciaSessionFactory.keySet();
            if (nomeConfiguracoes != null)
            {
                Iterator iteratorNomeConfiguracoes = nomeConfiguracoes.iterator();

                while (iteratorNomeConfiguracoes.hasNext())
                {
                    String nomeConexao = (String) iteratorNomeConfiguracoes.next();
                    SessionFactory sessionFactory = null;

                    try
                    {
                        sessionFactory = GerenciadorConfiguracaoHibernate.obterSessionFactory(nomeConexao);
                    }
                    catch (Exception e)
                    {
                        if (log.isWarnEnabled())
                        {
                            log.warn("Ocorreu o seguinte erro obtendo a SessionFactory da conexão '" + nomeConexao + "': " + e.getMessage());
                        }
                    }

                    if (log.isInfoEnabled())
                    {
                        log.info("...Fechando a SessionFactory da conexão '" + nomeConexao + "'...");
                    }

                    try
                    {
                        if (sessionFactory != null)
                        {
                            sessionFactory.close();
                        }
                    }
                    catch (HibernateException e)
                    {
                        if (log.isDebugEnabled())
                        {
                            log.debug("...Ocorreu o seguinte erro fechando a ultima SessionFactory: " + e.getMessage());
                        }
                        // too late now
                    }

                    sessionFactory = null;
                }

                mapPersistenciaSessionFactory = null;
            }
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("...Não existe nenhuma SessionFactory registrada...");
            }
        }

        inicializado = false;
    }

    protected static String obterNomeConexaoTabela(String nomeTabela) throws HibernateExceptionCD
    {
        String nomeConexaoRetorno = null;
        if (mapPersistenciaSessionFactory != null)
        {
            Set nomeConfiguracoes = mapPersistenciaSessionFactory.keySet();
            if (nomeConfiguracoes != null)
            {
                Iterator iteratorNomeConfiguracoes = nomeConfiguracoes.iterator();

                while (iteratorNomeConfiguracoes.hasNext())
                {
                    String nomeConexao = obterNomeConexao((String) iteratorNomeConfiguracoes.next());

                    DadosConexao dadosConexao = obterDadosConexao(nomeConexao);
                    if (dadosConexao.getMapeamentosTabelas().contains(nomeTabela.toLowerCase()))
                    {
                        nomeConexaoRetorno = nomeConexao;
                        break;
                    }

                }
            }
        }

        return nomeConexaoRetorno;
    }
}

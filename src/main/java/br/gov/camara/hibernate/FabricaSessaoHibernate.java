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
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.gov.camara.hibernate.exception.HibernateExceptionCD;
import br.gov.camara.hibernate.interceptor.session.SessionInterceptorManager;

public class FabricaSessaoHibernate
{
    /**
    * Logger for this class
    */
    private static final Log log = LogFactory.getLog(FabricaSessaoHibernate.class);

    protected static final int SESSAO_POSICAO_SESSION = 0;
    protected static final int SESSAO_POSICAO_FACADE_CHAMADORA = 1;
    protected static final int SESSAO_POSICOES_TOTAL = 2;

    public static final ThreadLocal threadLocalMapSessionAbertas = new ThreadLocal();

    private FabricaSessaoHibernate()
    {}

    /**
     * Obtem uma sessão do hibernate Se já existe uma sessão aberta, a mesma é retornada Senão, uma nova sessão é criada
     * Observação: a sessão fica registrada na ThreadLocal
     * 
     * @param request The requeset we are servicing
     * @return An open session
     * @throws HibernateExceptionCD
     */
    public static Session obterSessao(String nomeConexao) throws HibernateExceptionCD
    {
        if (nomeConexao == null)
        {
            nomeConexao = GerenciadorConfiguracaoHibernate.obterNomeConexaoPadrao();
        }

        if (log.isDebugEnabled())
        {
            log.debug("Obtendo a sessão do Hibernate para a conexão '" + nomeConexao + "'");
        }

        // Verificar se a sessão ou a transação Hibernate existem na sessão
        Session hseHibernateSession = obterSessaoIniciada(nomeConexao);

        try
        {
            if (hseHibernateSession != null)
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Já existe sessão aberta para a conexão '" + nomeConexao + "'. Retornando a mesma...");
                }
            }
            else
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Não existe sessão aberta para a conexão '" + nomeConexao + "'. Criando uma nova");
                }

                // Como estará sendo criada uma nova sessão, descartar qualquer
                // transação existente
                Transaction trxTransacao = FabricaTransacaoHibernate.obterTransacaoIniciada(nomeConexao);

                if (trxTransacao != null)
                {
                    if (log.isWarnEnabled())
                    {
                        log.warn("Não existia nenhuma sessão aberta, mas havia uma transação aberta para a conexão '" + nomeConexao + "'. Desfazendo-a...");
                    }
                    try
                    {
                        trxTransacao.rollback();
                    }
                    catch (Exception e2)
                    {
                        if (log.isInfoEnabled())
                        {
                            // Não precisa fazer nada... Paciência...
                            log.info("Ocorreu um erro desfazendo a transação aberta (sem sessão)" + e2.getMessage());
                        }
                    }
                    trxTransacao = null;
                    FabricaTransacaoHibernate.removerTransacaoIniciada(nomeConexao);
                }

                // Obter nova sessão Hibernate
                hseHibernateSession = iniciarNovaSessao(nomeConexao);
            }
        }
        catch (Exception e)
        {
            // Oooppsss... Ocorreu um erro... :-(
            if (log.isErrorEnabled())
            {
                log.error("...Ocorreu o seguinte erro tentando obter uma nova sessão do Hibernate: " + e.getMessage());
            }

            if (hseHibernateSession != null)
            {
                try
                {
                    hseHibernateSession.close();
                }
                catch (Exception e2)
                {
                    if (log.isInfoEnabled())
                    {
                        // Não precisa fazer nada... Paciência...
                        log.info("Ocorreu um erro fechando a sessão Hibernate com erro na criação: " + e2.getMessage());
                    }
                }
                hseHibernateSession = null;
            }

            if (obterSessaoIniciada(nomeConexao) != null)
            {
                removerSessaoIniciada(nomeConexao);
            }

            if (e instanceof HibernateExceptionCD)
            {
                throw (HibernateExceptionCD) e;
            }
            else
            {
                throw new HibernateExceptionCD(e);
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug("Retornando a sessão do Hibernate para a conexão '" + nomeConexao + "'");
        }

        return hseHibernateSession;
    }

    /**
     * Finaliza a sessão do hibernate Se não existir sessão (e transação), nada é feito Se existir transação aberta, ela
     * é desfeita, a sessão é finalizada e um erro é gerado
     * 
     * @param request The requeset we are servicing
     * @throws HibernateExceptionCD
     */
    public static void fecharSessao(String nomeConexao) throws HibernateExceptionCD
    {
        if (nomeConexao == null)
        {
            nomeConexao = GerenciadorConfiguracaoHibernate.obterNomeConexaoPadrao();
        }

        if (log.isDebugEnabled())
        {
            log.debug("Tentando fechar a sessão do Hibernate da conexão '" + nomeConexao + "'...");
        }

        // Verificar existência da sessão e da transação Hibernate
        String comTransacao = null;

        // Verificar se a sessão Hibernate existe na sessão
        Session hseHibernateSession = obterSessaoIniciada(nomeConexao);

        // Verificar se a Facade chamadora é a mesma que iniciou
        if (hseHibernateSession != null)
        {
            if (!isMesmaFacadeIniciouSessao(nomeConexao))
            {
                return;
            }
        }

        // Obter transação existente
        Transaction trxTransacao = FabricaTransacaoHibernate.obterTransacaoIniciada(nomeConexao);
        List errosOcorridos = new ArrayList();

        // Finalizar a sessão Hibernate
        try
        {
            // Desfazer transação se houver uma em andamento...
            if (trxTransacao != null)
            {
                try
                {
                    comTransacao = FabricaTransacaoHibernate.forcarFechamentoTransacao(nomeConexao);
                }
                catch (Exception e)
                {
                    log.error("Ocorreu um erro tentando forçar o fechamento da transação existente para a conexão '" + nomeConexao + "': " + e.getMessage());
                    // Não precisa fazer nada
                    errosOcorridos.add(e);
                }
            }

            if (hseHibernateSession == null)
            {
                if (log.isDebugEnabled())
                {
                    log.debug("...Não é possível executar um flush da sessão Hibernate para a conexão '"
                            + nomeConexao
                            + "' porque não foi encontrada nenhuma sessão aberta...");
                }
            }
            else
            {
                if (log.isDebugEnabled())
                {
                    log.debug("...Executando um flush da sessão Hibernate para a conexão '" + nomeConexao + "'...");
                }

                // ... Fazendo um flush da sessão Hibernate
                hseHibernateSession.flush();

                // "Gato" para resolver o problema do Ingres que abre uma transação automaticamente...
                try
                {
                    // "Gato" para resolver o problema do Ingres que abre uma transação automaticamente...
                    // Parece que esse problema acontece também com o Oracle
                    ConexaoJDBC.resolverFaltaAutocommit(nomeConexao, hseHibernateSession);
                }
                catch (Exception e)
                {
                    log.error("Ocorreu um erro tentando realizar a transação (por causa do Ingres que abre uma automaticamente) para a conexão '"
                            + nomeConexao
                            + "': "
                            + e.getMessage());
                    e.printStackTrace();
                    // Paciência... NÃO MAIS !!!!
                    errosOcorridos.add(e);
                }
            }

            if (!errosOcorridos.isEmpty())
            {
                StringBuffer erros = new StringBuffer();
                for (int i = 0; i < errosOcorridos.size(); i++)
                {
                    erros.append(i);
                    erros.append(") ");
                    erros.append(((Throwable) errosOcorridos.get(i)).getMessage());
                    erros.append(".\n");
                }
                throw new HibernateException(erros.toString());
            }
        }
        catch (Exception e)
        {
            if (log.isErrorEnabled())
            {
                log.error("...Ocorreu o seguinte erro executando o flush para a conexão '" + nomeConexao + "': " + e.getMessage());
                // e.printStackTrace();
            }

            HibernateExceptionCD.dispararExcecao(e);
        }
        finally
        {
            if (log.isDebugEnabled())
            {
                log.debug("...Finalmente, fechando a sessão Hibernate para a conexão '"
                        + nomeConexao
                        + "' e removendo as referências (sessão e transação Hibernate)...");
            }

            // Fecha a sessão Hibernate
            try
            {
                if (hseHibernateSession == null)
                {
                    if (log.isDebugEnabled())
                    {
                        log.debug("...Não é possível fechar a sessão Hibernate para a conexão '"
                                + nomeConexao
                                + "' porque não foi encontrada nenhuma sessão aberta...");
                    }
                }
                else
                {
                    // Executar interceptor
                    try
                    {
                        // Foi definido algum interceptor de sessão ?
                        SessionInterceptorManager sessionInterceptorManager = GerenciadorConfiguracaoHibernate.obterSessionInterceptorManagerConexao(nomeConexao);
                        if (sessionInterceptorManager != null)
                        {
                            hseHibernateSession = sessionInterceptorManager.executarAntesFechar(hseHibernateSession, nomeConexao);
                        }
                    }
                    catch (Exception e)
                    {
                        // Opsss ocorreu um erro ao executar o interceptor
                        log.error("Ocorreu um erro executando o sessionInterceptor antes de fechar a conexão '" + nomeConexao + "': " + e.getMessage());
                    }

                    hseHibernateSession.close();
                }
            }
            catch (Exception e)
            {
                log.error("Ocorreu um erro fechando a sessão Hibernate para a conexão '" + nomeConexao + "': " + e.getMessage());

                // Não precisa fazer nada... Paciência...
            }

            // Remover o ponteiro para a sessão Hibernate
            hseHibernateSession = null;
            if (obterSessaoIniciada(nomeConexao) != null)
            {
                removerSessaoIniciada(nomeConexao);
            }

            if (log.isDebugEnabled())
            {
                log.debug("...Concluído o fechamento da sessão e transação Hibernate para a conexão '" + nomeConexao + "'");
            }

            if (comTransacao != null && !"".equals(comTransacao))
            {
                if (log.isErrorEnabled())
                {
                    log.error("A sessão hibernate para a conexão '"
                            + nomeConexao
                            + "' foi fechada, contudo havia uma transação em andamento ("
                            + comTransacao
                            + ") que foi desfeita");
                }

                throw new HibernateExceptionCD("A sessão hibernate para a conexão '"
                        + nomeConexao
                        + "' foi fechada, contudo havia uma transação em andamento ("
                        + comTransacao
                        + ") que foi desfeita");
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug("Fim do método");
        }
    }

    private static void removerSessaoIniciada(String nomeConexao) throws HibernateExceptionCD
    {
        Map mapSessionAbertas = obterMapSessoesIniciadas();
        mapSessionAbertas.remove(GerenciadorConfiguracaoHibernate.obterPonteiroConexao(nomeConexao));
    }

    private static boolean isMesmaFacadeIniciouSessao(String nomeConexao) throws HibernateExceptionCD
    {
        boolean mesmaFacade = false;

        // Verificar se a Facade chamadora é a mesma que iniciou
        List strFacadeChamadora = GerenciadorConfiguracaoHibernate.obterDelimitadoraSessaoTransacaoConexao(nomeConexao).obterTodas();
        if (strFacadeChamadora == null || strFacadeChamadora.size() > 1)
        {
            mesmaFacade = false;
        }
        else
        {
            String strFacadeChamadoraInicial = obterFacadeChamadoraSessaoIniciada(nomeConexao);
            if (strFacadeChamadoraInicial != null && strFacadeChamadoraInicial.equals(strFacadeChamadora.get(0)))
            {
                mesmaFacade = true;
                if (log.isDebugEnabled())
                {
                    log.debug("A facade chamadora é a mesma que iniciou (" + strFacadeChamadora + ").");
                }
            }
            else
            {
                mesmaFacade = false;
                if (log.isDebugEnabled())
                {
                    log.debug("A facade chamadora atual é "
                            + strFacadeChamadora
                            + ", mas quem iniciou foi "
                            + strFacadeChamadoraInicial
                            + ". Ignorando este método.");
                }
            }
        }

        return mesmaFacade;
    }

    private static Map obterMapSessoesIniciadas()
    {
        Map mapSessionAbertas = (Map) threadLocalMapSessionAbertas.get();
        if (mapSessionAbertas == null)
        {
            synchronized (threadLocalMapSessionAbertas)
            {
                mapSessionAbertas = (Map) threadLocalMapSessionAbertas.get();
                if (mapSessionAbertas == null)
                {
                    mapSessionAbertas = new HashMap();
                    threadLocalMapSessionAbertas.set(mapSessionAbertas);
                }
            }
        }

        return mapSessionAbertas;
    }

    private static Session obterSessaoIniciada(String nomeConexao) throws HibernateExceptionCD
    {
        Map mapSessionAbertas = obterMapSessoesIniciadas();
        Session hseHibernateSession = null;
        List lstSessaoAberta = null;

        lstSessaoAberta = (List) mapSessionAbertas.get(GerenciadorConfiguracaoHibernate.obterPonteiroConexao(nomeConexao));

        if (lstSessaoAberta != null)
        {
            hseHibernateSession = (Session) lstSessaoAberta.get(SESSAO_POSICAO_SESSION);
        }

        return hseHibernateSession;
    }

    private static String obterFacadeChamadoraSessaoIniciada(String nomeConexao) throws HibernateExceptionCD
    {
        Map mapSessionAbertas = obterMapSessoesIniciadas();
        List lstSessaoAberta = null;
        String strFacadeChamadora = null;
        lstSessaoAberta = (List) mapSessionAbertas.get(GerenciadorConfiguracaoHibernate.obterPonteiroConexao(nomeConexao));
        if (lstSessaoAberta != null)
        {
            strFacadeChamadora = (String) lstSessaoAberta.get(SESSAO_POSICAO_FACADE_CHAMADORA);
        }

        return strFacadeChamadora;
    }

    private static Session iniciarNovaSessao(String nomeConexao) throws HibernateExceptionCD
    {
        // Obtém a nova sessão
        Session hseHibernateSession = null;
        try
        {
            hseHibernateSession = GerenciadorConfiguracaoHibernate.obterSessionFactory(GerenciadorConfiguracaoHibernate.obterPonteiroConexao(nomeConexao)).openSession();
        }
        catch (HibernateException he)
        {
            if (he instanceof HibernateExceptionCD)
            {
                throw (HibernateExceptionCD) he;
            }
            else
            {
                throw new HibernateExceptionCD(he);
            }
        }

        String strFacadeChamadora = GerenciadorConfiguracaoHibernate.obterDelimitadoraSessaoTransacaoConexao(nomeConexao).obterPrimeira();

        // Foi definido algum interceptor ?
        try
        {
            // Foi definido algum interceptor de sessão ?
            SessionInterceptorManager sessionInterceptorManager = GerenciadorConfiguracaoHibernate.obterSessionInterceptorManagerConexao(nomeConexao);
            if (sessionInterceptorManager != null)
            {
                hseHibernateSession = sessionInterceptorManager.executarAposObterNovaSessao(hseHibernateSession, nomeConexao);
            }
        }
        catch (Exception e)
        {
            // Opsss ocorreu um erro ao executar o interceptor
            log.error("Ocorreu um erro executando o sessionInterceptor após obter a conexão '" + nomeConexao + "': " + e.getMessage());
            if (e instanceof HibernateExceptionCD)
            {
                throw (HibernateExceptionCD) e;
            }
            else
            {
                throw new HibernateExceptionCD(e);
            }
        }

        ArrayList arlSessao = new ArrayList(SESSAO_POSICOES_TOTAL);
        arlSessao.add(SESSAO_POSICAO_SESSION, hseHibernateSession);
        arlSessao.add(SESSAO_POSICAO_FACADE_CHAMADORA, strFacadeChamadora);

        // Registrar a sessão do Hibernate no map da ThreadLocal
        Map mapSessionAbertas = obterMapSessoesIniciadas();
        mapSessionAbertas.put(GerenciadorConfiguracaoHibernate.obterPonteiroConexao(nomeConexao), arlSessao);

        if (log.isDebugEnabled())
        {
            log.debug("Iniciando uma nova sessão chamada pela Facade: " + strFacadeChamadora);
        }

        return hseHibernateSession;
    }

    static String forcarFechamentoSessoesAbertas()
    {
        StringBuffer strNomeSessoesAbertas = new StringBuffer();

        // Recupera os dados das transações abertas
        Map mapSessoesAbertas = obterMapSessoesIniciadas();
        Set setSessoesAbertas = mapSessoesAbertas.keySet();
        if (setSessoesAbertas != null)
        {
            Iterator itrSessoesAbertas = setSessoesAbertas.iterator();
            while (itrSessoesAbertas.hasNext())
            {
                String nomePonteiro = (String) itrSessoesAbertas.next();
                String nomeConexao = GerenciadorConfiguracaoHibernate.obterNomeConexao(nomePonteiro);
                String facadeChamadora = obterFacadeChamadoraSessaoIniciada(nomeConexao);
                strNomeSessoesAbertas.append(nomeConexao).append("-").append(facadeChamadora).append(", ");

                fecharSessao(nomeConexao);
            }

            if (!"".equals(strNomeSessoesAbertas.toString()))
            {
                strNomeSessoesAbertas.deleteCharAt(strNomeSessoesAbertas.length() - 1).deleteCharAt(strNomeSessoesAbertas.length() - 1);
                log.error("Sessões abertas: " + strNomeSessoesAbertas.toString());
            }
        }

        return strNomeSessoesAbertas.toString();
    }
}

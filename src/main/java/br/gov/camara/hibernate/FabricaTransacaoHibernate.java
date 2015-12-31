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

public class FabricaTransacaoHibernate
{
    /**
    * Logger for this class
    */
    private static final Log log = LogFactory.getLog(FabricaTransacaoHibernate.class);

    private static final int TRANSACAO_POSICAO_TRANSACTION = 0;
    private static final int TRANSACAO_POSICAO_FACADE_CHAMADORA = 1;
    private static final int TRANSACAO_POSICOES_TOTAL = 2;

    private static final ThreadLocal threadLocalMapTransactionAbertas = new ThreadLocal();

    private FabricaTransacaoHibernate()
    {}

    private static Transaction iniciarNovaTransacao(String nomeConexao, Session hseHibernateSession) throws HibernateExceptionCD
    {
        Transaction trxTransacao = null;
        try
        {
            trxTransacao = hseHibernateSession.beginTransaction();
        }
        catch (HibernateException he)
        {
            throw new HibernateExceptionCD(he);
        }

        // // Garantir que a conexão foi realmente estabelecida com o banco de dados
        // String queryTesteConexao = HibernateUtilCD.obterPropriedadeSubstituicao(nomeConexao,
        // HibernateUtilCD.CHAVE_QUERY_TIMESTAMP_BD);
        // if (!"".equals(queryTesteConexao) && queryTesteConexao != null)
        // {
        // executarSQL(hseHibernateSession, nomeConexao, queryTesteConexao);
        // }

        String strFacadeChamadora = GerenciadorConfiguracaoHibernate.obterDelimitadoraSessaoTransacaoConexao(nomeConexao).obterPrimeira();

        ArrayList arlTransacao = new ArrayList(TRANSACAO_POSICOES_TOTAL);
        arlTransacao.add(TRANSACAO_POSICAO_TRANSACTION, trxTransacao);
        arlTransacao.add(TRANSACAO_POSICAO_FACADE_CHAMADORA, strFacadeChamadora);

        // Registrar a transação do Hibernate no map da ThreadLocal
        Map mapTransactionAbertas = obterMapTransacoesIniciadas();
        mapTransactionAbertas.put(GerenciadorConfiguracaoHibernate.obterPonteiroConexao(nomeConexao), arlTransacao);

        if (log.isDebugEnabled())
        {
            log.debug("Iniciando uma nova transação chamada pela Facade: " + strFacadeChamadora);
        }

        return trxTransacao;
    }

    private static Map obterMapTransacoesIniciadas()
    {
        Map mapTransactionAbertas = (Map) threadLocalMapTransactionAbertas.get();
        if (mapTransactionAbertas == null)
        {
            synchronized (threadLocalMapTransactionAbertas)
            {
                mapTransactionAbertas = (Map) threadLocalMapTransactionAbertas.get();
                if (mapTransactionAbertas == null)
                {
                    mapTransactionAbertas = new HashMap();
                    threadLocalMapTransactionAbertas.set(mapTransactionAbertas);
                }
            }
        }

        return mapTransactionAbertas;
    }

    static void removerTransacaoIniciada(String nomeConexao) throws HibernateExceptionCD
    {
        Map mapTransactionAbertas = obterMapTransacoesIniciadas();
        mapTransactionAbertas.remove(GerenciadorConfiguracaoHibernate.obterPonteiroConexao(nomeConexao));
    }

    /**
     * Efetua um rollback na transação em andamento Se não existir transação, NENHUM erro é gerado, mas esta informação
     * é registrada no LOG de nível INFO
     * 
     * @throws HibernateExceptionCD se ocorrer algum erro relacionado ao Hibernate
     */
    static void desfazerTransacao(String nomeConexao, boolean forcarFechamentoIgnorandoFacadeChamadora) throws HibernateExceptionCD
    {
        if (nomeConexao == null)
        {
            nomeConexao = GerenciadorConfiguracaoHibernate.obterNomeConexaoPadrao();
        }

        if (log.isDebugEnabled())
        {
            log.debug("Tentando efetuar rollback na transação da conexão '" + nomeConexao + "'");
        }

        // Obter transação existente
        Transaction trxTransacao = obterTransacaoIniciada(nomeConexao);

        if (trxTransacao == null)
        {
            if (log.isDebugEnabled())
            {
                log.debug("...Não existe nenhuma sessão do Hibernate em andamento para a conexão '" + nomeConexao + "'");
            }
            return;
        }

        // Verificar se a Facade chamadora é a mesma que iniciou
        if (!isMesmaFacadeIniciouTransacao(nomeConexao) && !forcarFechamentoIgnorandoFacadeChamadora)
        {
            return;
        }

        // Finalizar a transação e a sessão Hibernate
        try
        {
            if (log.isDebugEnabled())
            {
                log.debug("...Como existe uma transação aberta para a conexão '" + nomeConexao + "', finaliza-a com rollback");
            }

            // Rollback
            trxTransacao.rollback();
        }
        catch (Exception e)
        {
            // Não precisa fazer nada... Paciência...

            if (log.isErrorEnabled())
            {
                log.error("...Ocorreu o seguinte erro desfazendo a transação para a conexão '" + nomeConexao + "'" + e.getMessage());
            }
            throw new HibernateExceptionCD(e);
        }
        finally
        {
            trxTransacao = null;
            if (obterTransacaoIniciada(nomeConexao) != null)
            {
                removerTransacaoIniciada(nomeConexao);
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug("Transação da conexão '" + nomeConexao + "' desfeita");
        }
    }

    /**
     * Efetua um rollback na transação em andamento Se não existir transação, NENHUM erro é gerado, mas esta informação
     * é registrada no LOG de nível INFO
     * 
     * @throws HibernateExceptionCD se ocorrer algum erro relacionado ao Hibernate
     */
    public static void desfazerTransacao(String nomeConexao) throws HibernateExceptionCD
    {
        desfazerTransacao(nomeConexao, false);
    }

    static Transaction obterTransacaoIniciada(String nomeConexao) throws HibernateExceptionCD
    {
        Map mapTransactionAbertas = obterMapTransacoesIniciadas();

        Transaction trxTransacao = null;
        List lstTransacaoAberta = (List) mapTransactionAbertas.get(GerenciadorConfiguracaoHibernate.obterPonteiroConexao(nomeConexao));
        if (lstTransacaoAberta != null)
        {
            trxTransacao = (Transaction) lstTransacaoAberta.get(TRANSACAO_POSICAO_TRANSACTION);
        }

        return trxTransacao;
    }

    private static boolean isMesmaFacadeIniciouTransacao(String nomeConexao) throws HibernateExceptionCD
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
            String strFacadeChamadoraInicial = obterFacadeChamadoraTransacaoIniciada(nomeConexao);
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

    private static String obterFacadeChamadoraTransacaoIniciada(String nomeConexao) throws HibernateExceptionCD
    {
        Map mapTransactionAbertas = obterMapTransacoesIniciadas();

        String strFacadeChamadora = null;
        List lstTransacaoAberta = (List) mapTransactionAbertas.get(GerenciadorConfiguracaoHibernate.obterPonteiroConexao(nomeConexao));
        if (lstTransacaoAberta != null)
        {
            strFacadeChamadora = (String) lstTransacaoAberta.get(TRANSACAO_POSICAO_FACADE_CHAMADORA);
        }

        return strFacadeChamadora;
    }

    /**
     * Efetua um commit na transação em andamento Se não existir transação, um erro é gerado
     * 
     * @throws HibernateExceptionCD se ocorrer algum erro relacionado ao Hibernate
     */
    public static void realizarTransacao(String nomeConexao) throws HibernateExceptionCD
    {
        if (nomeConexao == null)
        {
            nomeConexao = GerenciadorConfiguracaoHibernate.obterNomeConexaoPadrao();
        }

        if (log.isDebugEnabled())
        {
            log.debug("Tentando efetuar commit na transação da conexão '" + nomeConexao + "'");
        }

        // Já existe alguma transação em andamento ?
        Transaction trxTransacao = obterTransacaoIniciada(nomeConexao);

        if (trxTransacao == null)
        {
            if (log.isErrorEnabled())
            {
                log.error("...Não existe nenhuma sessão do Hibernate em andamento para a conexão '" + nomeConexao + "'");
            }
            throw new HibernateExceptionCD("Não existe nenhuma sessão do Hibernate em andamento para a conexão '" + nomeConexao + "'");
        }

        // Verificar se a Facade chamadora é a mesma que iniciou
        if (!isMesmaFacadeIniciouTransacao(nomeConexao))
        {
            return;
        }

        // Finalizar a transação Hibernate
        try
        {
            if (log.isDebugEnabled())
            {
                log.debug("...Como existe uma transação aberta para a conexão '" + nomeConexao + "', finaliza-a com commit");
            }

            // Commit
            trxTransacao.commit();
        }
        catch (Exception e)
        {
            if (log.isErrorEnabled())
            {
                log.error("...Ocorreu o seguinte erro realizando a transação da conexão '" + nomeConexao + "':" + e.getMessage());
            }

            try
            {
                if (trxTransacao != null)
                {
                    trxTransacao.rollback();
                }
            }
            catch (Exception e2)
            {
                if (log.isDebugEnabled())
                {
                    // Não precisa fazer nada... Paciência...
                    log.debug("Ocorreu um erro desfazendo a transação com problemas da conexão '" + nomeConexao + "':" + e2.getMessage());
                }
            }

            throw new HibernateExceptionCD(e);
        }
        finally
        {
            trxTransacao = null;
            if (obterTransacaoIniciada(nomeConexao) != null)
            {
                removerTransacaoIniciada(nomeConexao);
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug("Fim do método");
        }

    }

    /**
     * Inicia uma transação, caso não exista uma em andamento Antes de iniciar a transação, uma sessão é obtida
     * Observação: a transação fica registrada na ThreadLocal
     * 
     * @throws HibernateExceptionCD se ocorrer algum erro relacionado ao Hibernate
     */
    public static Session iniciarTransacao(String nomeConexao) throws HibernateExceptionCD
    {
        if (nomeConexao == null)
        {
            nomeConexao = GerenciadorConfiguracaoHibernate.obterNomeConexaoPadrao();
        }

        if (log.isDebugEnabled())
        {
            log.debug("\n\nNova conexao: " + nomeConexao + "- [" + Math.random() + "]\n\n");
            log.debug("Tentando iniciar transação para a conexão '" + nomeConexao + "'");
        }

        // Obter sessão do Hibernate
        Session hseHibernateSession = FabricaSessaoHibernate.obterSessao(nomeConexao);

        // Já existe alguma transação em andamento ?
        if (obterTransacaoIniciada(nomeConexao) != null)
        {
            if (log.isDebugEnabled())
            {
                log.debug("Transação já existe para a conexão '" + nomeConexao + "'. Retornando a mesma.");
            }
            return hseHibernateSession;
        }

        try
        {
            // Iniciar transação
            iniciarNovaTransacao(nomeConexao, hseHibernateSession);
        }
        catch (Exception e)
        {
            // Oooppsss... Ocorreu um erro... :-(
            if (log.isErrorEnabled())
            {
                log.error("...Ocorreu o seguinte erro iniciando uma transação na sessão do Hibernate: " + e.getMessage());
            }

            if (obterTransacaoIniciada(nomeConexao) != null)
            {
                removerTransacaoIniciada(nomeConexao);
            }

            throw new HibernateExceptionCD(e);
        }

        if (log.isDebugEnabled())
        {
            log.debug("Retornando a sessão do Hibernate com trasação iniciada para a conexão '" + nomeConexao + "'");
        }

        return hseHibernateSession;
    }

    static String forcarFechamentoTransacao(String nomeConexao) throws HibernateExceptionCD
    {
        StringBuffer strNomeTransacoesAbertas = new StringBuffer();

        String facadeChamadora = obterFacadeChamadoraTransacaoIniciada(nomeConexao);
        if (facadeChamadora != null)
        {
            strNomeTransacoesAbertas.append(nomeConexao).append("-").append(facadeChamadora);
        }

        FabricaTransacaoHibernate.desfazerTransacao(nomeConexao, true);

        return strNomeTransacoesAbertas.toString();
    }

    static String forcarFechamentoTransacoesAbertas() throws HibernateExceptionCD
    {
        StringBuffer strNomeTransacoesAbertas = new StringBuffer();

        // Recupera os dados das transações abertas
        Map mapTransacoesAbertas = FabricaTransacaoHibernate.obterMapTransacoesIniciadas();
        Set setTransacoesAbertas = mapTransacoesAbertas.keySet();
        if (setTransacoesAbertas != null)
        {
            Iterator itrTransacoesAbertas = setTransacoesAbertas.iterator();
            while (itrTransacoesAbertas.hasNext())
            {
                String nomePonteiro = (String) itrTransacoesAbertas.next();
                String nomeConexao = GerenciadorConfiguracaoHibernate.obterNomeConexao(nomePonteiro);
                strNomeTransacoesAbertas.append(forcarFechamentoTransacao(nomeConexao)).append(", ");
            }

            if (!"".equals(strNomeTransacoesAbertas.toString()))
            {
                strNomeTransacoesAbertas.deleteCharAt(strNomeTransacoesAbertas.length() - 1).deleteCharAt(strNomeTransacoesAbertas.length() - 1);
                log.error("Transações abertas: " + strNomeTransacoesAbertas.toString());
            }
        }

        return strNomeTransacoesAbertas.toString();
    }
}

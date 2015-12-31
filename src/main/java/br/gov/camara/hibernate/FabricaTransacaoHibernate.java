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

        // // Garantir que a conex�o foi realmente estabelecida com o banco de dados
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

        // Registrar a transa��o do Hibernate no map da ThreadLocal
        Map mapTransactionAbertas = obterMapTransacoesIniciadas();
        mapTransactionAbertas.put(GerenciadorConfiguracaoHibernate.obterPonteiroConexao(nomeConexao), arlTransacao);

        if (log.isDebugEnabled())
        {
            log.debug("Iniciando uma nova transa��o chamada pela Facade: " + strFacadeChamadora);
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
     * Efetua um rollback na transa��o em andamento Se n�o existir transa��o, NENHUM erro � gerado, mas esta informa��o
     * � registrada no LOG de n�vel INFO
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
            log.debug("Tentando efetuar rollback na transa��o da conex�o '" + nomeConexao + "'");
        }

        // Obter transa��o existente
        Transaction trxTransacao = obterTransacaoIniciada(nomeConexao);

        if (trxTransacao == null)
        {
            if (log.isDebugEnabled())
            {
                log.debug("...N�o existe nenhuma sess�o do Hibernate em andamento para a conex�o '" + nomeConexao + "'");
            }
            return;
        }

        // Verificar se a Facade chamadora � a mesma que iniciou
        if (!isMesmaFacadeIniciouTransacao(nomeConexao) && !forcarFechamentoIgnorandoFacadeChamadora)
        {
            return;
        }

        // Finalizar a transa��o e a sess�o Hibernate
        try
        {
            if (log.isDebugEnabled())
            {
                log.debug("...Como existe uma transa��o aberta para a conex�o '" + nomeConexao + "', finaliza-a com rollback");
            }

            // Rollback
            trxTransacao.rollback();
        }
        catch (Exception e)
        {
            // N�o precisa fazer nada... Paci�ncia...

            if (log.isErrorEnabled())
            {
                log.error("...Ocorreu o seguinte erro desfazendo a transa��o para a conex�o '" + nomeConexao + "'" + e.getMessage());
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
            log.debug("Transa��o da conex�o '" + nomeConexao + "' desfeita");
        }
    }

    /**
     * Efetua um rollback na transa��o em andamento Se n�o existir transa��o, NENHUM erro � gerado, mas esta informa��o
     * � registrada no LOG de n�vel INFO
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

        // Verificar se a Facade chamadora � a mesma que iniciou
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
                    log.debug("A facade chamadora � a mesma que iniciou (" + strFacadeChamadora + ").");
                }
            }
            else
            {
                mesmaFacade = false;
                if (log.isDebugEnabled())
                {
                    log.debug("A facade chamadora atual � "
                            + strFacadeChamadora
                            + ", mas quem iniciou foi "
                            + strFacadeChamadoraInicial
                            + ". Ignorando este m�todo.");
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
     * Efetua um commit na transa��o em andamento Se n�o existir transa��o, um erro � gerado
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
            log.debug("Tentando efetuar commit na transa��o da conex�o '" + nomeConexao + "'");
        }

        // J� existe alguma transa��o em andamento ?
        Transaction trxTransacao = obterTransacaoIniciada(nomeConexao);

        if (trxTransacao == null)
        {
            if (log.isErrorEnabled())
            {
                log.error("...N�o existe nenhuma sess�o do Hibernate em andamento para a conex�o '" + nomeConexao + "'");
            }
            throw new HibernateExceptionCD("N�o existe nenhuma sess�o do Hibernate em andamento para a conex�o '" + nomeConexao + "'");
        }

        // Verificar se a Facade chamadora � a mesma que iniciou
        if (!isMesmaFacadeIniciouTransacao(nomeConexao))
        {
            return;
        }

        // Finalizar a transa��o Hibernate
        try
        {
            if (log.isDebugEnabled())
            {
                log.debug("...Como existe uma transa��o aberta para a conex�o '" + nomeConexao + "', finaliza-a com commit");
            }

            // Commit
            trxTransacao.commit();
        }
        catch (Exception e)
        {
            if (log.isErrorEnabled())
            {
                log.error("...Ocorreu o seguinte erro realizando a transa��o da conex�o '" + nomeConexao + "':" + e.getMessage());
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
                    // N�o precisa fazer nada... Paci�ncia...
                    log.debug("Ocorreu um erro desfazendo a transa��o com problemas da conex�o '" + nomeConexao + "':" + e2.getMessage());
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
            log.debug("Fim do m�todo");
        }

    }

    /**
     * Inicia uma transa��o, caso n�o exista uma em andamento Antes de iniciar a transa��o, uma sess�o � obtida
     * Observa��o: a transa��o fica registrada na ThreadLocal
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
            log.debug("Tentando iniciar transa��o para a conex�o '" + nomeConexao + "'");
        }

        // Obter sess�o do Hibernate
        Session hseHibernateSession = FabricaSessaoHibernate.obterSessao(nomeConexao);

        // J� existe alguma transa��o em andamento ?
        if (obterTransacaoIniciada(nomeConexao) != null)
        {
            if (log.isDebugEnabled())
            {
                log.debug("Transa��o j� existe para a conex�o '" + nomeConexao + "'. Retornando a mesma.");
            }
            return hseHibernateSession;
        }

        try
        {
            // Iniciar transa��o
            iniciarNovaTransacao(nomeConexao, hseHibernateSession);
        }
        catch (Exception e)
        {
            // Oooppsss... Ocorreu um erro... :-(
            if (log.isErrorEnabled())
            {
                log.error("...Ocorreu o seguinte erro iniciando uma transa��o na sess�o do Hibernate: " + e.getMessage());
            }

            if (obterTransacaoIniciada(nomeConexao) != null)
            {
                removerTransacaoIniciada(nomeConexao);
            }

            throw new HibernateExceptionCD(e);
        }

        if (log.isDebugEnabled())
        {
            log.debug("Retornando a sess�o do Hibernate com trasa��o iniciada para a conex�o '" + nomeConexao + "'");
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

        // Recupera os dados das transa��es abertas
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
                log.error("Transa��es abertas: " + strNomeTransacoesAbertas.toString());
            }
        }

        return strNomeTransacoesAbertas.toString();
    }
}

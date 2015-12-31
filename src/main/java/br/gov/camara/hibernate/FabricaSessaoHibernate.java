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
     * Obtem uma sess�o do hibernate Se j� existe uma sess�o aberta, a mesma � retornada Sen�o, uma nova sess�o � criada
     * Observa��o: a sess�o fica registrada na ThreadLocal
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
            log.debug("Obtendo a sess�o do Hibernate para a conex�o '" + nomeConexao + "'");
        }

        // Verificar se a sess�o ou a transa��o Hibernate existem na sess�o
        Session hseHibernateSession = obterSessaoIniciada(nomeConexao);

        try
        {
            if (hseHibernateSession != null)
            {
                if (log.isDebugEnabled())
                {
                    log.debug("J� existe sess�o aberta para a conex�o '" + nomeConexao + "'. Retornando a mesma...");
                }
            }
            else
            {
                if (log.isDebugEnabled())
                {
                    log.debug("N�o existe sess�o aberta para a conex�o '" + nomeConexao + "'. Criando uma nova");
                }

                // Como estar� sendo criada uma nova sess�o, descartar qualquer
                // transa��o existente
                Transaction trxTransacao = FabricaTransacaoHibernate.obterTransacaoIniciada(nomeConexao);

                if (trxTransacao != null)
                {
                    if (log.isWarnEnabled())
                    {
                        log.warn("N�o existia nenhuma sess�o aberta, mas havia uma transa��o aberta para a conex�o '" + nomeConexao + "'. Desfazendo-a...");
                    }
                    try
                    {
                        trxTransacao.rollback();
                    }
                    catch (Exception e2)
                    {
                        if (log.isInfoEnabled())
                        {
                            // N�o precisa fazer nada... Paci�ncia...
                            log.info("Ocorreu um erro desfazendo a transa��o aberta (sem sess�o)" + e2.getMessage());
                        }
                    }
                    trxTransacao = null;
                    FabricaTransacaoHibernate.removerTransacaoIniciada(nomeConexao);
                }

                // Obter nova sess�o Hibernate
                hseHibernateSession = iniciarNovaSessao(nomeConexao);
            }
        }
        catch (Exception e)
        {
            // Oooppsss... Ocorreu um erro... :-(
            if (log.isErrorEnabled())
            {
                log.error("...Ocorreu o seguinte erro tentando obter uma nova sess�o do Hibernate: " + e.getMessage());
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
                        // N�o precisa fazer nada... Paci�ncia...
                        log.info("Ocorreu um erro fechando a sess�o Hibernate com erro na cria��o: " + e2.getMessage());
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
            log.debug("Retornando a sess�o do Hibernate para a conex�o '" + nomeConexao + "'");
        }

        return hseHibernateSession;
    }

    /**
     * Finaliza a sess�o do hibernate Se n�o existir sess�o (e transa��o), nada � feito Se existir transa��o aberta, ela
     * � desfeita, a sess�o � finalizada e um erro � gerado
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
            log.debug("Tentando fechar a sess�o do Hibernate da conex�o '" + nomeConexao + "'...");
        }

        // Verificar exist�ncia da sess�o e da transa��o Hibernate
        String comTransacao = null;

        // Verificar se a sess�o Hibernate existe na sess�o
        Session hseHibernateSession = obterSessaoIniciada(nomeConexao);

        // Verificar se a Facade chamadora � a mesma que iniciou
        if (hseHibernateSession != null)
        {
            if (!isMesmaFacadeIniciouSessao(nomeConexao))
            {
                return;
            }
        }

        // Obter transa��o existente
        Transaction trxTransacao = FabricaTransacaoHibernate.obterTransacaoIniciada(nomeConexao);
        List errosOcorridos = new ArrayList();

        // Finalizar a sess�o Hibernate
        try
        {
            // Desfazer transa��o se houver uma em andamento...
            if (trxTransacao != null)
            {
                try
                {
                    comTransacao = FabricaTransacaoHibernate.forcarFechamentoTransacao(nomeConexao);
                }
                catch (Exception e)
                {
                    log.error("Ocorreu um erro tentando for�ar o fechamento da transa��o existente para a conex�o '" + nomeConexao + "': " + e.getMessage());
                    // N�o precisa fazer nada
                    errosOcorridos.add(e);
                }
            }

            if (hseHibernateSession == null)
            {
                if (log.isDebugEnabled())
                {
                    log.debug("...N�o � poss�vel executar um flush da sess�o Hibernate para a conex�o '"
                            + nomeConexao
                            + "' porque n�o foi encontrada nenhuma sess�o aberta...");
                }
            }
            else
            {
                if (log.isDebugEnabled())
                {
                    log.debug("...Executando um flush da sess�o Hibernate para a conex�o '" + nomeConexao + "'...");
                }

                // ... Fazendo um flush da sess�o Hibernate
                hseHibernateSession.flush();

                // "Gato" para resolver o problema do Ingres que abre uma transa��o automaticamente...
                try
                {
                    // "Gato" para resolver o problema do Ingres que abre uma transa��o automaticamente...
                    // Parece que esse problema acontece tamb�m com o Oracle
                    ConexaoJDBC.resolverFaltaAutocommit(nomeConexao, hseHibernateSession);
                }
                catch (Exception e)
                {
                    log.error("Ocorreu um erro tentando realizar a transa��o (por causa do Ingres que abre uma automaticamente) para a conex�o '"
                            + nomeConexao
                            + "': "
                            + e.getMessage());
                    e.printStackTrace();
                    // Paci�ncia... N�O MAIS !!!!
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
                log.error("...Ocorreu o seguinte erro executando o flush para a conex�o '" + nomeConexao + "': " + e.getMessage());
                // e.printStackTrace();
            }

            HibernateExceptionCD.dispararExcecao(e);
        }
        finally
        {
            if (log.isDebugEnabled())
            {
                log.debug("...Finalmente, fechando a sess�o Hibernate para a conex�o '"
                        + nomeConexao
                        + "' e removendo as refer�ncias (sess�o e transa��o Hibernate)...");
            }

            // Fecha a sess�o Hibernate
            try
            {
                if (hseHibernateSession == null)
                {
                    if (log.isDebugEnabled())
                    {
                        log.debug("...N�o � poss�vel fechar a sess�o Hibernate para a conex�o '"
                                + nomeConexao
                                + "' porque n�o foi encontrada nenhuma sess�o aberta...");
                    }
                }
                else
                {
                    // Executar interceptor
                    try
                    {
                        // Foi definido algum interceptor de sess�o ?
                        SessionInterceptorManager sessionInterceptorManager = GerenciadorConfiguracaoHibernate.obterSessionInterceptorManagerConexao(nomeConexao);
                        if (sessionInterceptorManager != null)
                        {
                            hseHibernateSession = sessionInterceptorManager.executarAntesFechar(hseHibernateSession, nomeConexao);
                        }
                    }
                    catch (Exception e)
                    {
                        // Opsss ocorreu um erro ao executar o interceptor
                        log.error("Ocorreu um erro executando o sessionInterceptor antes de fechar a conex�o '" + nomeConexao + "': " + e.getMessage());
                    }

                    hseHibernateSession.close();
                }
            }
            catch (Exception e)
            {
                log.error("Ocorreu um erro fechando a sess�o Hibernate para a conex�o '" + nomeConexao + "': " + e.getMessage());

                // N�o precisa fazer nada... Paci�ncia...
            }

            // Remover o ponteiro para a sess�o Hibernate
            hseHibernateSession = null;
            if (obterSessaoIniciada(nomeConexao) != null)
            {
                removerSessaoIniciada(nomeConexao);
            }

            if (log.isDebugEnabled())
            {
                log.debug("...Conclu�do o fechamento da sess�o e transa��o Hibernate para a conex�o '" + nomeConexao + "'");
            }

            if (comTransacao != null && !"".equals(comTransacao))
            {
                if (log.isErrorEnabled())
                {
                    log.error("A sess�o hibernate para a conex�o '"
                            + nomeConexao
                            + "' foi fechada, contudo havia uma transa��o em andamento ("
                            + comTransacao
                            + ") que foi desfeita");
                }

                throw new HibernateExceptionCD("A sess�o hibernate para a conex�o '"
                        + nomeConexao
                        + "' foi fechada, contudo havia uma transa��o em andamento ("
                        + comTransacao
                        + ") que foi desfeita");
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug("Fim do m�todo");
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

        // Verificar se a Facade chamadora � a mesma que iniciou
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
        // Obt�m a nova sess�o
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
            // Foi definido algum interceptor de sess�o ?
            SessionInterceptorManager sessionInterceptorManager = GerenciadorConfiguracaoHibernate.obterSessionInterceptorManagerConexao(nomeConexao);
            if (sessionInterceptorManager != null)
            {
                hseHibernateSession = sessionInterceptorManager.executarAposObterNovaSessao(hseHibernateSession, nomeConexao);
            }
        }
        catch (Exception e)
        {
            // Opsss ocorreu um erro ao executar o interceptor
            log.error("Ocorreu um erro executando o sessionInterceptor ap�s obter a conex�o '" + nomeConexao + "': " + e.getMessage());
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

        // Registrar a sess�o do Hibernate no map da ThreadLocal
        Map mapSessionAbertas = obterMapSessoesIniciadas();
        mapSessionAbertas.put(GerenciadorConfiguracaoHibernate.obterPonteiroConexao(nomeConexao), arlSessao);

        if (log.isDebugEnabled())
        {
            log.debug("Iniciando uma nova sess�o chamada pela Facade: " + strFacadeChamadora);
        }

        return hseHibernateSession;
    }

    static String forcarFechamentoSessoesAbertas()
    {
        StringBuffer strNomeSessoesAbertas = new StringBuffer();

        // Recupera os dados das transa��es abertas
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
                log.error("Sess�es abertas: " + strNomeSessoesAbertas.toString());
            }
        }

        return strNomeSessoesAbertas.toString();
    }
}

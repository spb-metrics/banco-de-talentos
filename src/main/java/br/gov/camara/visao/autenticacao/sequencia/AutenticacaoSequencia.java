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

package br.gov.camara.visao.autenticacao.sequencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import sigesp.comum.util.struts.SigespSequencia;
import sigesp.comum.util.struts.actions.ActionMessagesUtil;
import br.gov.camara.seguranca.Permissao;
import br.gov.camara.seguranca.SegurancaWeb;
import br.gov.camara.seguranca.UnidadeAutenticadora;
import br.gov.camara.seguranca.UsuarioAutenticado;
import br.gov.camara.seguranca.exception.SegurancaException;


import br.gov.camara.seguranca.util.SegurancaUtil;
import br.gov.camara.visao.autenticacao.form.EncaminharLogonForm;

/**
 * Sequencia AutenticacaoSequencia
 */

public final class AutenticacaoSequencia extends SigespSequencia
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(AutenticacaoSequencia.class);

    /**
     * Metodo construtor
     *
     * @param mapping Mapeamento executado
     * @param form FormulÃ¡rio associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que jÃ¡ ocorreram
     * @param mensagens Colecao de mensagens a serem exibidas
     * @param AutenticacaoAction Classe Action associada
     */

    public AutenticacaoSequencia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages erros,
            ActionMessages mensagens, Action action, Map mapRecursos)
    {
        this.mapping = mapping;
        this.form = form;
        this.request = request;
        this.response = response;
        this.errosRequest = erros;
        this.action = action;
        this.mensagensRequest = mensagens;
        this.arlMensagens = new ArrayList();
        this.mapRecursos = mapRecursos;

        this.erros = new ActionMessages();
        this.mensagens = new ActionMessages();
    }

    // Metodos chamados

    /**
     * Metodo encaminharLogoff
     * 
     * @return DOCUMENTAR!
     */

    public ArrayList encaminharLogoff()
    {
        // Recupera sessao
        HttpSession session = request.getSession(false);

        if (session != null)
        {
            SegurancaWeb segurancaWeb = SegurancaWeb.obterSegurancaWeb(session);

            if (segurancaWeb != null)
            {
                // obtém usuario autenticado
                UsuarioAutenticado usuarioAutenticado = segurancaWeb.obterUsuarioAutenticado();

                // Tem usuario logado ?
                if (segurancaWeb.temUsuarioLogado(session))
                {
                    if (log.isInfoEnabled())
                    {
                        log.info("Efetuando logout | " + usuarioAutenticado.obterLogin());
                    }

                    mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", "O Logout foi efetuado com sucesso"));
                }
                else
                {
                    mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", "Nenhum usuário está logado"));
                }

                // Remove os dados de autenticação da sessão
                segurancaWeb.invalidar(session);
            }

            // Invalida a sessão
            session.invalidate();
        }

        // Cria uma nova sessão
        session = request.getSession(true);

        // Fim do metodo
        arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, mensagens);
        arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, erros);

        return (arlMensagens);
    }

    /**
     * Metodo encaminharLogon
     * 
     * @return DOCUMENTAR!
     */

    public ArrayList encaminharLogon()
    {
        // Recupera sessao
        HttpSession session = request.getSession(false);
        if (session != null)
        {
            SegurancaWeb segurancaWeb = SegurancaWeb.obterSegurancaWeb(session);

            if (segurancaWeb != null)
            {
                // Remove os dados de autenticação da sessão
                segurancaWeb.invalidar(session);
            }

            // Invalida a sessão
            session.invalidate();
        }

        // Cria uma nova sessão
        session = request.getSession(true);

        // Fim do metodo
        arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, mensagens);
        arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, erros);

        return (arlMensagens);
    }

    /**
     * Metodo validarAutenticacaoExterna
     * 
     * @return DOCUMENTAR!
     */

    public ArrayList validarAutenticacaoExterna()
    {
        // Verifica se existe uma sessão existente
        HttpSession session = request.getSession(true);

        

        // Fim do metodo
        arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, mensagens);
        arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, erros);

        return (arlMensagens);
    }

    /**
     * Metodo validacaoLogonEfetuarLogon
     * 
     * @return DOCUMENTAR!
     */

    public ArrayList validarLogonEfetuarLogon()
    {
        // Recupera sessao
        HttpSession session = request.getSession(true);

        UsuarioAutenticado usuarioAutenticado = null;

        // Recuperar os parâmetros esperados
        String strLogin = ((EncaminharLogonForm) form).getTxtLogin();
        String strSenha = ((EncaminharLogonForm) form).getTxtSenha();

        if ((strLogin == null) || "".equals(strLogin))
        {
            if (log.isDebugEnabled())
            {
                log.debug("O Login não foi informado");
            }
            erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", "O Login deve ser informado"));
        }
        else
        {

            if ((strSenha == null) || "".equals(strSenha))
            {
                if (log.isDebugEnabled())
                {
                    log.debug("A Senha não foi informada");
                }
                erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", "A senha deve ser informada"));
            }
        }
        strLogin = strLogin.trim().toUpperCase();

        // Registra os erros encontrados no escopo de request
        if (erros.isEmpty())
        {
            // Autenticar Usuario
            try
            {
                SegurancaUtil objSegUtil = new SegurancaUtil();
                UnidadeAutenticadora unidadeAutenticadora = objSegUtil.obterInstanciaUnidadeAutenticadora();
                usuarioAutenticado = unidadeAutenticadora.autenticarUsuario(strLogin, strSenha);
                Permissao permissao = objSegUtil.obterInstanciaPermissao();

                SegurancaWeb seguranca = objSegUtil.obterInstanciaSegurancaWeb(unidadeAutenticadora, usuarioAutenticado, permissao);
                session.setAttribute(SegurancaWeb.CHAVE_SEGURANCAWEB, seguranca);
            }
            catch (SegurancaException ue)
            {
                if (log.isDebugEnabled())
                {
                    log.debug(ue.getMessage());
                }

                erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", ue.getMessage()));
            }
        }

        if (erros.isEmpty())
        {
            if (log.isInfoEnabled())
            {
                log.info("Logon realizado | " + strLogin + " | " + usuarioAutenticado.obterNome());
            }

            mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", "Usuário logado com sucesso"));
        }
        else
        {
            if (log.isInfoEnabled())
            {
                log.info("Logon inválido | " + strLogin);
            }
        }

        // Fim do metodo
        arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, mensagens);
        arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, erros);

        return (arlMensagens);
    }

    /**
     * Metodo validacaoLogonEncaminharModulo
     */

    public ArrayList validarLogonEncaminharModulo()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Recupera sessao
        HttpSession session = request.getSession(true);

        // Efetuar validacoes / preparaÃ§Ãµes necessarias

        // Exemplo para adicionar um erro:
        // erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", "Mensagem de erro a ser exibida"));

        if (erros.isEmpty())
        {

            try
            {
                // -----------------------------------
                // Corpo do metodo - Completar aqui...
                // -----------------------------------

                RequestDispatcher rd = null;
                String redir = null;
                SegurancaWeb seguranca = SegurancaWeb.obterSegurancaWeb(session);
                if (seguranca != null && seguranca.temUsuarioLogado(session))
                {
                    String strModulo = (String) request.getAttribute("modulo");
                    String strURLInicialPosLogin = (String) session.getServletContext().getInitParameter("URLInicialPosLogin");
                    if (!(strModulo == null || "".equals(strModulo)))
                    {
                        // É uma autenticação externa, isto é, foi especificado o módulo destino, 
                        // portanto redirecionar para o módulo especificado
                        redir = request.getContextPath() + "/" + strModulo + "/index" + strModulo + ".do";
                        //response.sendRedirect(redir);
                        //rd = request.getRequestDispatcher(redir);
                    }
                    else if (!(strURLInicialPosLogin == null || "".equals(strURLInicialPosLogin)))
                    {
                        // Não é uma autenticação externa, mas existe um módulo inicial padrão
                        // portanto redirecionar para o módulo especificado
                        redir = request.getContextPath() + "/" + strURLInicialPosLogin;
                        //response.sendRedirect(redir);
                        //rd = request.getRequestDispatcher(redir);
                    }
                }
                else
                {
                    redir = request.getContextPath() + "/Autenticacao/encaminharLogon.do";
                    //response.sendRedirect(redir);
                    //rd = request.getRequestDispatcher(redir);
                }
                if (redir != null)
                {
                    request.setAttribute("redir", redir);
                }
                if (rd != null)
                {
                    rd.forward(request, response);
                }

                // Exemplo de envio de uma mensagem (sucesso) para o usuario
                // mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", "Mensagem informativa a ser exibida"));
            } // Remover os comentÃ¡rios abaixo
            //catch (CDException cde)
            //{
            //    erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", obterMensagemErro(cde)));
            //}
            catch (IOException e)
            {
                erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", e.getMessage()));
            }
            catch (ServletException e)
            {
                erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", e.getMessage()));
            }
            finally
            {}
        }

        arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, mensagens);
        arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, erros);

        return (arlMensagens);

    }

}

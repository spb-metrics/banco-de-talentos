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

package br.gov.camara.util.actionplugin.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import be.ff.gui.web.struts.action.ActionPlugInChain;
import be.ff.gui.web.struts.action.ActionPlugInException;
import br.gov.camara.seguranca.SegurancaWeb;
import br.gov.camara.seguranca.exception.SegurancaException;
import br.gov.camara.util.actionplugin.ActionPluginUtil;
import br.gov.camara.util.actionplugin.plugin.BaseActionPlugIn;

/**
 * DOCUMENTAR!
 */
public class SegurancaActionPlugIn extends BaseActionPlugIn
{
    /**
    * Logger for this class
    */
    private static final Log log = LogFactory.getLog(SegurancaActionPlugIn.class);

    // "A conexão expirou. Autentique-se novamente.";
    private static final String CHAVE_SESSAO_INVALIDA = "sigesp.comum.erro.seguranca.sessao.invalida";
    // "A conexão expirou. Autentique-se novamente.";
    private static final String CHAVE_SESSAO_EXPIRADA = "sigesp.comum.erro.seguranca.sessao.expirada";
    // "Autentique-se primeiro.";
    private static final String CHAVE_USUARIO_NAO_AUTENTICADO = "sigesp.comum.erro.seguranca.usuario.naoautenticado";
    // "Acesso não autorizado.";
    private static final String CHAVE_USUARIO_NAO_AUTORIZADO = "sigesp.comum.erro.seguranca.usuario.naoautorizado";
    // "Autenticação inválida.";
    private static final String CHAVE_AUTENTICACAO_INVALIDA = "sigesp.comum.erro.seguranca.autenticacao.invalida";
    // "A senha expirou. Ela deve ser alterada.";
    private static final String CHAVE_SENHA_EXPIRADA_ALTERAR = "sigesp.comum.erro.seguranca.senha.alterar";

    /**
     * DOCUMENTAR!
     *
     * @param mapping DOCUMENTAR!
     * @param form DOCUMENTAR!
     * @param request DOCUMENTAR!
     * @param response DOCUMENTAR!
     * @param chain DOCUMENTAR!
     *
     * @return DOCUMENTAR!
     *
     * @throws ActionPlugInException DOCUMENTAR!
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionPlugInChain chain) throws ActionPlugInException
    {

        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo 'execute' - URL='" + ActionPluginUtil.getRequestURL(request) + "'");
        }

        // Recuperar a sessão atual
        HttpSession session = request.getSession(false);
        String strMensagem = null;

        ActionMessages erros = (ActionMessages) request.getAttribute(Globals.ERROR_KEY);

        if (erros == null)
        {
            if (log.isDebugEnabled())
            {
                log.debug("Criando um novo ActionErrors");
            }
            erros = new ActionErrors();
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("Recuperando o ActionErrors do request");
            }
        }

        // A sessão é válida ?
        if (session == null)
        {
            session = request.getSession(true);

            // A sessão não é válida
            strMensagem = obterMensagemRecursoComum(request, CHAVE_SESSAO_INVALIDA);
            if (strMensagem != null)
            {
                erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", strMensagem));
                registraErroRequest(request, erros);
            }

            if (log.isInfoEnabled())
            {
                log.info("Sessão inválida - encaminhando para logon");
            }
            return mapping.findForward("encaminharLogon");
        }

        // Obter dados da segurança
        SegurancaWeb segurancaWeb = SegurancaWeb.obterSegurancaWeb(session);

        // Os dados da segurança estão registrados na session HTTP e tem um usuário logado ?
        if (segurancaWeb == null || !segurancaWeb.temUsuarioLogado(session))
        {
            // Não existe registro da segurança ou não existe usuário logado

            // Registrar a mensagem correspondente no request
            if (session.isNew())
            {
                strMensagem = obterMensagemRecursoComum(request, CHAVE_SESSAO_EXPIRADA);
                if (log.isDebugEnabled())
                {
                    log.debug("Sessão válida e é nova, mas nenhum usuário autenticado. Encaminhando para logon.");
                }
                if (strMensagem != null)
                {
                    erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", strMensagem));
                    registraErroRequest(request, erros);
                }
            }
            else
            {
                strMensagem = obterMensagemRecursoComum(request, CHAVE_USUARIO_NAO_AUTENTICADO);
                if (log.isDebugEnabled())
                {
                    log.debug("Sessão válida e NÃO é nova, mas nenhum usuário autenticado. Encaminhando para logon.");
                }
                if (strMensagem != null)
                {
                    erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", strMensagem));
                    registraErroRequest(request, erros);
                }
            }

            // Encaminhar para logon
            if (log.isInfoEnabled())
            {
                log.info("Não existe registro da segurança ou não existe usuário logado - encaminhando para logon");
            }
            return mapping.findForward("encaminharLogon");
        }

        // A autenticação é válida ?
        boolean blnAutenticacaoValida = false;

        try
        {
            blnAutenticacaoValida = segurancaWeb.obterUnidadeAutenticadora().validarAutenticacao(segurancaWeb.obterUsuarioAutenticado());
        }
        catch (SegurancaException se)
        {
            // A autenticacao não é válida
            strMensagem = se.getMessage();
        }

        if (!blnAutenticacaoValida)
        {
            if (log.isDebugEnabled())
            {
                log.debug("A autenticação não é válida: " + strMensagem);
            }

            if (strMensagem.indexOf("senha") < 0)
            {
                strMensagem = obterMensagemRecursoComum(request, CHAVE_AUTENTICACAO_INVALIDA);
            }
            else
            {
                strMensagem = obterMensagemRecursoComum(request, CHAVE_SENHA_EXPIRADA_ALTERAR);
            }

            if (strMensagem != null)
            {
                erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", strMensagem));
                registraErroRequest(request, erros);
            }

            // Encaminhar para logon
            if (log.isInfoEnabled())
            {
                log.info("A autenticação não é válida - encaminhando para logon");
            }
            return mapping.findForward("encaminharLogon");
        }

        // Verifica se o usuário tem autorização para acessar a URL em questão

        boolean blnTemAutorizacao = false;
        String strObjetoControlado = "";

        strObjetoControlado = ActionPluginUtil.getActionPath(request);

        try
        {
            blnTemAutorizacao = segurancaWeb.obterPermissao().temAutorizacaoFuncionalidade(segurancaWeb.obterUsuarioAutenticado(), strObjetoControlado);
        }
        catch (SegurancaException ue)
        {
            if (log.isErrorEnabled())
            {
                log.error("Erro inesperado ao tentar validar a autorização deste usuário: " + ue.getMessage());
            }
        }

        if (!blnTemAutorizacao)
        {
            strMensagem = obterMensagemRecursoComum(request, CHAVE_USUARIO_NAO_AUTORIZADO);
            if (log.isInfoEnabled())
            {
                log.info("Usuário SEM permissão: " + strObjetoControlado);
            }
            if (strMensagem != null)
            {
                erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", strMensagem));
                registraErroRequest(request, erros);
            }

            return mapping.findForward("encaminharUsuarioNaoAutorizado");
        }

        if (log.isInfoEnabled())
        {
            log.info("Usuário com permissão: " + strObjetoControlado);
        }

        return chain.execute(mapping, form, request, response);

    }
}

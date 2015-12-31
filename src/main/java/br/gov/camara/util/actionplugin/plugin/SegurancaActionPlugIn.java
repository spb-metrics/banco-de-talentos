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

    // "A conex�o expirou. Autentique-se novamente.";
    private static final String CHAVE_SESSAO_INVALIDA = "sigesp.comum.erro.seguranca.sessao.invalida";
    // "A conex�o expirou. Autentique-se novamente.";
    private static final String CHAVE_SESSAO_EXPIRADA = "sigesp.comum.erro.seguranca.sessao.expirada";
    // "Autentique-se primeiro.";
    private static final String CHAVE_USUARIO_NAO_AUTENTICADO = "sigesp.comum.erro.seguranca.usuario.naoautenticado";
    // "Acesso n�o autorizado.";
    private static final String CHAVE_USUARIO_NAO_AUTORIZADO = "sigesp.comum.erro.seguranca.usuario.naoautorizado";
    // "Autentica��o inv�lida.";
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

        // Recuperar a sess�o atual
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

        // A sess�o � v�lida ?
        if (session == null)
        {
            session = request.getSession(true);

            // A sess�o n�o � v�lida
            strMensagem = obterMensagemRecursoComum(request, CHAVE_SESSAO_INVALIDA);
            if (strMensagem != null)
            {
                erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", strMensagem));
                registraErroRequest(request, erros);
            }

            if (log.isInfoEnabled())
            {
                log.info("Sess�o inv�lida - encaminhando para logon");
            }
            return mapping.findForward("encaminharLogon");
        }

        // Obter dados da seguran�a
        SegurancaWeb segurancaWeb = SegurancaWeb.obterSegurancaWeb(session);

        // Os dados da seguran�a est�o registrados na session HTTP e tem um usu�rio logado ?
        if (segurancaWeb == null || !segurancaWeb.temUsuarioLogado(session))
        {
            // N�o existe registro da seguran�a ou n�o existe usu�rio logado

            // Registrar a mensagem correspondente no request
            if (session.isNew())
            {
                strMensagem = obterMensagemRecursoComum(request, CHAVE_SESSAO_EXPIRADA);
                if (log.isDebugEnabled())
                {
                    log.debug("Sess�o v�lida e � nova, mas nenhum usu�rio autenticado. Encaminhando para logon.");
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
                    log.debug("Sess�o v�lida e N�O � nova, mas nenhum usu�rio autenticado. Encaminhando para logon.");
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
                log.info("N�o existe registro da seguran�a ou n�o existe usu�rio logado - encaminhando para logon");
            }
            return mapping.findForward("encaminharLogon");
        }

        // A autentica��o � v�lida ?
        boolean blnAutenticacaoValida = false;

        try
        {
            blnAutenticacaoValida = segurancaWeb.obterUnidadeAutenticadora().validarAutenticacao(segurancaWeb.obterUsuarioAutenticado());
        }
        catch (SegurancaException se)
        {
            // A autenticacao n�o � v�lida
            strMensagem = se.getMessage();
        }

        if (!blnAutenticacaoValida)
        {
            if (log.isDebugEnabled())
            {
                log.debug("A autentica��o n�o � v�lida: " + strMensagem);
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
                log.info("A autentica��o n�o � v�lida - encaminhando para logon");
            }
            return mapping.findForward("encaminharLogon");
        }

        // Verifica se o usu�rio tem autoriza��o para acessar a URL em quest�o

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
                log.error("Erro inesperado ao tentar validar a autoriza��o deste usu�rio: " + ue.getMessage());
            }
        }

        if (!blnTemAutorizacao)
        {
            strMensagem = obterMensagemRecursoComum(request, CHAVE_USUARIO_NAO_AUTORIZADO);
            if (log.isInfoEnabled())
            {
                log.info("Usu�rio SEM permiss�o: " + strObjetoControlado);
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
            log.info("Usu�rio com permiss�o: " + strObjetoControlado);
        }

        return chain.execute(mapping, form, request, response);

    }
}

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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ModuleUtils;

import be.ff.gui.web.struts.action.ActionPlugIn;
import be.ff.gui.web.struts.action.ActionPlugInChain;
import be.ff.gui.web.struts.action.ActionPlugInConfig;
import be.ff.gui.web.struts.action.ActionPlugInException;

public abstract class BaseActionPlugIn implements ActionPlugIn
{

    private static Log log = LogFactory.getLog(BaseActionPlugIn.class);
    private ServletContext context = null;

    public BaseActionPlugIn()
    {
        super();
    }

    /**
     * DOCUMENTAR!
     *
     * @param config DOCUMENTAR!
     */
    public final void init(ActionPlugInConfig config) throws ActionPlugInException
    {
        this.context = config.getServletContext();
        executarInicializacao(config);
    }

    protected void executarInicializacao(ActionPlugInConfig config) throws ActionPlugInException
    {
        // O padrão é não fazer nada, a menos que seja sobrescrito...
        return;
    }

    protected void registraErroRequest(HttpServletRequest request, ActionMessages erros)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Salvando ActionErrors no request");
        }

        request.setAttribute(Globals.ERROR_KEY, erros);
    }

    /**
     * Return the specified message resources for the current module.
     *
     * @param request The servlet request we are processing
     * @param key The key specified in the
     *  <code>&lt;message-resources&gt;</code> element for the
     *  requested bundle
     *
     * @since Struts 1.1
     */
    protected String obterMensagemRecursoComum(HttpServletRequest request, String key)
    {

        String strMensagem = null;
        ModuleConfig moduleConfig = ModuleUtils.getInstance().getModuleConfig(request, context);

        // Return the requested message resources instance
        MessageResources resource = ((MessageResources) context.getAttribute("comum" + moduleConfig.getPrefix()));

        if (resource != null)
        {
            strMensagem = resource.getMessage(key);
            if (log.isDebugEnabled())
            {
                log.debug("Mensagem recuperada pela chave '" + key + "' do recurso 'comum': '" + strMensagem + "'");
            }
        }
        else
        {
            if (log.isWarnEnabled())
            {
                log.warn("Ocorreu um erro recuperando o recurso 'comum' do módulo '" + moduleConfig.getPrefix() + "' no request");
            }
        }

        return strMensagem;
    }

    /**
     * DOCUMENTAR!
     */
    public void destroy()
    {
        this.context = null;
    }

    public abstract ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionPlugInChain chain) throws ActionPlugInException;

}
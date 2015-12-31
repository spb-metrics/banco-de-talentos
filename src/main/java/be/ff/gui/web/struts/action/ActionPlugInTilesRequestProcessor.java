/*
 * Struts Action Plug-in Extension. Copyright (C) 2002 ASQdotCOM NV (http://www.asqdotcom.be/) This extension is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version. This
 * extension is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 */
package be.ff.gui.web.struts.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.tiles.TilesRequestProcessor;

import br.gov.camara.util.actionplugin.ActionPluginUtil;

/**
 * This is class is identical to the <code>ActionPlugInRequestProcessor</code> class, except that it extends the
 * <code>org.apache.struts.tiles.TilesRequestProcessor</code> class. Use this request processor if you both need Tiles
 * and the action plug-in extension.
 * <p>
 * <code>&lt;controller
 * processorClass=&quot;be.ff.gui.web.struts.action.ActionPlugInTilesRequestProcessor&quot;&gt;</code>
 * </p>
 * 
 * @author Gerrie Kimpen
 * @see ActionPlugInRequestProcessor
 * @see ActionPlugIn
 * @see ActionPlugInChain
 * @since FF 1.0
 */
public class ActionPlugInTilesRequestProcessor extends TilesRequestProcessor
{
    private static Log log = LogFactory.getLog(ActionPlugInTilesRequestProcessor.class);

    /**
     * General-purpose preprocessing hook that can be overridden as required by subclasses. Return <code>true</code>
     * if you want standard processing to continue, or <code>false</code> if the response has already been completed.
     * The default implementation does nothing.
     * 
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param path DOCUMENTAR!
     * @return DOCUMENTAR!
     */
    public ActionMapping processMapping(HttpServletRequest request, HttpServletResponse response, String path) throws IOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Seta qual a ação que está sendo executada
        request.getSession().setAttribute("CHULTACAVE", ActionPluginUtil.getActionPath(request));

        // Chamar o processamento normal do RequestProcessor
        ActionMapping mapping = super.processMapping(request, response, path);

        // Em caso de não ter encontrado uma referencia para o mapeamento, finalizar
        // que é quando mapping == null

        if (mapping != null)
        {
            try
            {
                // tigger the action plug-in chain
                ActionPlugInChain actionFilterChain = new ActionPlugInChain();
                if (log.isDebugEnabled())
                {
                    log.debug("mapping=" + mapping + " - actionFilterChain=" + actionFilterChain);
                }

                ForwardConfig actionForward = actionFilterChain.execute(mapping, null, request, response);
                if (log.isDebugEnabled())
                {
                    log.debug("actionForward=" + actionForward);
                }

                if (actionForward != null)
                {
                    // Para que o request processor aborte o restante do processamento.
                    mapping = null;

                    /*
                     * The chain has decided that a forward should occur; the target action will not execute.
                     */
                    if (log.isDebugEnabled())
                    {
                        log.debug("The chain has decided that a forward should occur; the target action will not execute.");
                    }

                    // Process the returned ActionForward instance
                    try
                    {
                        if (log.isInfoEnabled())
                        {
                            log.info("O ActionPlugIn decidiu que deve ocorrer um forward para " + actionForward.getPath());
                        }
                        this.processForwardConfig(request, response, actionForward);
                    }
                    catch (ServletException se)
                    {
                        if (log.isErrorEnabled())
                        {
                            log.error("Ocorreu um erro processando o ActionForward '" + actionForward.getPath() + "'");
                            se.printStackTrace();
                        }
                    }
                }
            }
            catch (ActionPlugInException e)
            {
                StringBuffer errorMsg = new StringBuffer();
                errorMsg.append("An exception occurred in the action plug-in chain");
                if (e.getMessage() != null)
                {
                    errorMsg.append(": ");
                    errorMsg.append(e.getMessage());
                }
                else
                {
                    errorMsg.append(".");
                }
                Throwable cause = (e.getRootCause() != null) ? e.getRootCause() : e;
                log.error(errorMsg.toString(), cause);

                throw new IOException(errorMsg.toString());
            }
        }

        return (mapping);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.struts.action.RequestProcessor#processPopulate(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse, org.apache.struts.action.ActionForm,
     *      org.apache.struts.action.ActionMapping)
     */
    protected void processPopulate(HttpServletRequest request, HttpServletResponse response, ActionForm form, ActionMapping mapping) throws ServletException
    {

        if (log.isDebugEnabled())
        {
            log.debug("Entrada no método...");
        }

        if (form == null)
        {
            if (log.isDebugEnabled())
            {
                log.debug("...Como o formulário é nulo, não precisa fazer nada. Finalizando este método.");
            }
            return;
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("...Formulário sendo avaliado: " + form.toString() + "...");
            }
        }

        if (!form.equals(request.getAttribute("APIRP_formAnterior")))
        {
            // O formulário ainda não foi processado
            if ("sim".equals(request.getAttribute("limparFB")))
            {
                // Foi pedido para limpar o FB, portanto não popular...
                if (log.isDebugEnabled())
                {
                    log.debug("...Foi pedido para limpar o FB, portanto ele não está sendo populado com os dados do formulário.");
                }
            }
            else
            {
                // Popular com os dados no request...
                if (log.isDebugEnabled())
                {
                    log.debug("...O formulário no request ainda não foi 'populado' ou é diferente daquele tratado anteriormente...");
                    log.debug("...Populando o formulário com os dados armazenados no request...");
                }
                super.processPopulate(request, response, form, mapping);
            }

            if (log.isDebugEnabled())
            {
                log.debug("...Guardando a informação de que este formulário já foi processado neste request. Finalizando este método.");
            }

            request.setAttribute("APIRP_formAnterior", form);
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("...Como este formulário já foi 'populado' neste request, ignorando esta tarefa. Finalizando este método.");
            }
        }
    }
}

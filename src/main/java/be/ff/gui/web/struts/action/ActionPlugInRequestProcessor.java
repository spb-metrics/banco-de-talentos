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
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.RequestProcessor;

/**
 * This is an extended version of the Struts <code>RequestProcessor</code>. It tirggers the action plug-in chain
 * before the targetted Struts action executes.
 * <p>
 * Make sure that you add a <code>&lt;controller&gt;</code> tag to the <code>struts-config.xml</code> file to let
 * the Sruts framework know that this implementation of the <code>RequestProcessor</code> should be used to handle
 * requests, instead of the default one.
 * </p>
 * 
 * @author Gerrie Kimpen
 * @since FF 1.0
 * @see ActionPlugIn
 * @see ActionPlugInChain
 */
public class ActionPlugInRequestProcessor extends RequestProcessor
{
    private static Log log = LogFactory.getLog(ActionPlugInRequestProcessor.class);

    /**
     * Triggers the action plug-in chain before asking the specified <code>Action</code> instance to handle this
     * request. If some <code>ActionPlugIn</code> instance in the chain returns an <code>ActionForward</code>
     * instance, then the rest of the chain and actual targetted Struts <code>Action</code> are bypassed!
     * <p>
     * This method returns the <code>ActionForward</code> instance (if any) returned by the called <code>Action</code>
     * or <code>ActionPlugIn</code> for further processing.
     * </p>
     * 
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param action The <code>Action</code> instance to be used
     * @param form The <code>ActionForm</code> instance to pass to this <code>Action</code>
     * @param mapping The <code>ActionMapping</code> instance to pass to this <code>Action</code>
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet exception occurs
     */
    public ActionForward processActionPerform(HttpServletRequest request, HttpServletResponse response, Action action, ActionForm form, ActionMapping mapping) throws IOException, ServletException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no método");
        }

        try
        {
            // tigger the action plug-in chain
            ActionPlugInChain actionFilterChain = new ActionPlugInChain();
            ActionForward actionForward = actionFilterChain.execute(mapping, form, request, response);
            if (actionForward != null)
            {
                /*
                 * The chain has decided that a forward should occur; the target action will not execute.
                 */
                if (log.isDebugEnabled())
                {
                    log.debug("The chain has decided that a forward should occur; the target action will not execute.");
                }
                return actionForward;
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

            throw new ServletException(errorMsg.toString(), cause);
        }

        if (log.isDebugEnabled())
        {
            log.debug("Call the Struts processActionPerform; execute the target action class.");
        }
        // call the Struts processActionPerform; execute the target action class
        return super.processActionPerform(request, response, action, form, mapping);
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

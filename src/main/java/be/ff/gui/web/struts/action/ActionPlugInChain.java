/*
 * Struts Action Plug-in Extension.
 * Copyright (C) 2002 ASQdotCOM NV (http://www.asqdotcom.be/)
 *
 * This extension is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This extension is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
package be.ff.gui.web.struts.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.gov.camara.util.actionplugin.ActionPluginUtil;

/**
 * An instance of this class is passed into an <code>ActionPlugIn</code>'s <code>execute</code> method, enabeling the
 * plug-in developer to access the invocation chain of action plug-ins, during a request for a Struts action.
 * 
 * <p>
 * <code>ActionPlugIn</code>s use the <code>ActionPlugInChain</code> to invoke the next plug-in in the chain, or if the
 * calling plug-in is the last plug-in in the chain, to invoke the Struts action at the end of the chain.
 * </p>
 *
 * @author Gerrie Kimpen
 *
 * @see ActionPlugIn
 * @since FF 1.0
 */
public class ActionPlugInChain
{
    private static Log log = LogFactory.getLog(ActionPlugInChain.class);

    // chain attributes
    private static List activeActionPlugInRegister = null;
    private static List activeActionPlugInDefinitionRegister = null;

    private int registerIndex = -1;

    private static boolean inicializado = false;

    /**
     * This static method initializes the action plug-in chain, by instantiating and initializing all configured
     * <code>ActionPlugIn</code> classes. This method must be called before the action plug-in mechanism comes into
     * action. Therefore it will be called in the <code>ActionPlugInPlugIn</code> class, which executes at application
     * start-up.
     *
     * @param chainDef the <code>ActionPlugInChainDefinition</code> instance that is used to initialize the chain and
     *        the configured action plug-ins
     * @param context the <code>ServletContext</code> instance used to initialize the configured action plug-ins
     */
    protected static void init(ActionPlugInChainDefinition chainDef, ServletContext context)
    {
        if (inicializado)
        {
            if (log.isDebugEnabled())
            {
                log.debug("O ActionPlugInChain já foi inicializado anteriormente. Ignorando.");
            }
            return;
        }

        if (log.isDebugEnabled())
        {
            log.debug("[ActionPlugInChain::init] Intializing the action plug-in chain ...");
        }
        activeActionPlugInRegister = new ArrayList();
        activeActionPlugInDefinitionRegister = new ArrayList();

        // loop through all configured action plug-ins
        List plugInDefs = chainDef.getActionPlugInDefintions();
        Iterator iter = plugInDefs.iterator();
        while (iter.hasNext())
        {
            ActionPlugInDefinition def = (ActionPlugInDefinition) iter.next();

            try
            {
                // create an instance of the action plug-in class
                if (log.isDebugEnabled())
                {
                    log.debug("[ActionPlugInChain::init] Instantiating '" + def.getClassName() + "' action plug-in ...");
                }
                ActionPlugIn actionPlugIn = null;
                actionPlugIn = (ActionPlugIn) Class.forName(def.getClassName()).newInstance();

                try
                {
                    // initialize action plug-in instance
                    if (log.isDebugEnabled())
                    {
                        log.debug("[ActionPlugInChain::init] Initializing '" + def.getClassName() + "' action plug-in ...");
                    }
                    ActionPlugInConfig config = new ActionPlugInConfig(def, context);
                    actionPlugIn.init(config);

                    // add this action plug-in to the list of active plug-ins
                    addActivePlugIn(actionPlugIn, def);
                }
                catch (ActionPlugInException e)
                {
                    String errorMsg = "[ActionPlugInChain::init] The initialization of the '" + def.getClassName()
                        + "' action plug-in failed. This action plug-in it will not be placed into service.";
                    Throwable cause = (e.getRootCause() != null) ? e.getRootCause() : e;
                    log.error(errorMsg, cause);
                }
            }
            catch (Exception e)
            {
                // InstantiationException, IllegalAccessException, ClassNotFoundException
                String errorMsg = "[ActionPlugInChain::init] The instantiation of the '" + def.getClassName()
                    + "' action plug-in failed. This action plug-in it will not be placed into service.";
                log.error(errorMsg, e);
            }
        }
        inicializado = true;
    }

    /**
     * This static method calls the <code>destroy</code> method on all <code>ActionPlugIn</code>s in the chain,
     * enabling them to release any referenced resources.
     */
    protected static void destroy()
    {
        if (log.isDebugEnabled())
        {
            log.debug("[ActionPlugInChain::init] Destroying the action plug-in chain ...");
        }

        ListIterator iter = activeActionPlugInRegister.listIterator();
        while (iter.hasNext())
        {
            ActionPlugIn actionPlugIn = (ActionPlugIn) iter.next();
            actionPlugIn.destroy();
        }
    }

    /**
     * This method calls <code>execute</code> method of the <code>ActionPlugIn</code> instance that is next in the
     * chain. Typically this method is called inside an <code>ActionPlugIn</code>'s <code>execute</code> method when
     * it is finished performing its logic and wants to pass control to the next <code>ActionPlugIn</code> in the
     * chain.
     * 
     * <p>
     * This method returns the <code>ActionForward</code> instance that is returned by one of the
     * <code>ActionPlugIn</code> instances in the chain, or <code>null</code> when none of the registered action
     * plug-ins decided to return an <code>ActionForward</code> instance. In the latter case the targetted Struts
     * action will be executed.
     * </p>
     *
     * @param mapping the <code>ActionMapping</code> instance, as provided by the Struts framework
     * @param form the <code>ActionForm</code> instance, as provided by the Struts framework
     * @param request the <code>HttpServletRequest</code> instance, as provided by the Struts framework
     * @param response the <code>HttpServletResponse</code> instance, as provided by the Struts framework
     *
     * @return an <code>ActionForward</code> instance or <code>null</code>
     *
     * @throws ActionPlugInException when a plug-in in the chain fails to execute for some reason
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ActionPlugInException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no método");
        }

        ActionPlugIn actionFilter = getNextActionFilter(mapping, request);
        if (actionFilter == null)
        {
            if (log.isDebugEnabled())
            {
                log.debug("No more plug-ins to execute. The chain has reached it last element and no ActionForward where issued. This means that the targetted Struts action will soon execute.");
            }

            /* No more plug-ins to execute. The chain has reached it last element
             and no ActionForward where issued. This means that the targetted
             Struts action will soon execute. */
            return null;
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("Pass control to the next plug-in in the chain.");
            }

            // pass control to the next plug-in in the chain
            ActionForward acfProximoForward = null;

            acfProximoForward = actionFilter.execute(mapping, form, request, response, this);

            if (acfProximoForward == null && registerIndex < activeActionPlugInRegister.size())
            {
                if (log.isErrorEnabled())
                {
                    log.error("O forward especificado não foi encontrado.");
                }
                throw new ActionPlugInException("O forward especificado não foi encontrado.");
            }

            return acfProximoForward;
        }
    }

    /**
     * Adds the action plug-in and its accompanying defintion to the register of active action plug-ins.
     *
     * @param actionPlugIn the <code>ActionPlugIn</code> instance that will be registered
     * @param def the <code>ActionPlugInDefinition</code> instance that defines this <code>actionPlugIn</code>
     */
    private static void addActivePlugIn(ActionPlugIn actionPlugIn, ActionPlugInDefinition def)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no método");
        }

        // add this action plug-in to the list of active plug-ins
        activeActionPlugInRegister.add(actionPlugIn);
        activeActionPlugInDefinitionRegister.add(def);
    }

    /**
     * Returns the next plug-in in the chain, that has not executed yet and that is not disabled for this action.
     *
     * @param mapping the <code>ActionMapping</code> as provided by the Struts framework
     *
     * @return the next <code>ActionPlugIn</code> in the chain, that has not executed yet
     */
    private ActionPlugIn getNextActionFilter(ActionMapping mapping, HttpServletRequest request)
    {
        // the action path for the current action
        String currentActionPath = ActionPluginUtil.getActionPath(request);

        if (currentActionPath == null)
        {
            String requestURL = request.getRequestURI();
            currentActionPath = requestURL.substring(requestURL.lastIndexOf('/'));
            currentActionPath = currentActionPath.substring(0, currentActionPath.lastIndexOf('.'));
            log.warn("'getRequestURL() == null' / 'getRequestURI() == " + requestURL + "' ==>  Verificar se está correto !!!");
        }

        if (log.isDebugEnabled())
        {
            log.debug("Avaliando o mapping.getPath()='" + mapping.getPath() + "' / actionPath='" + currentActionPath + "'...");
        }

        ActionPlugIn nextActionPlugIn = null;
        while (nextActionPlugIn == null)
        {
            ActionPlugInDefinition nextDef = null;

            if (++registerIndex < activeActionPlugInRegister.size())
            {
                nextDef = (ActionPlugInDefinition) activeActionPlugInDefinitionRegister.get(registerIndex);
            }
            else
            {
                break;
            }

            if (nextDef != null)
            {
                boolean ativaPlugin = false;

                // Plugin DESABILITADO para a ação em questão ?
                // Boolean plugInDisabledForAction = nextDef.hasDisabledActionPath(currentActionPath);
                if (!currentActionPath.equals(mapping.getPath()))
                {
                    log.warn("mapping.getPath()='" + mapping.getPath() + "' é diferente de actionPath='" + currentActionPath + "'.");
                }
                Boolean plugInDisabledForAction = nextDef.hasDisabledActionPath(mapping.getPath());
                if (log.isDebugEnabled())
                {
                    log.debug("... para a definição=" + nextDef.getClassName() + ", desabilitado=" + plugInDisabledForAction);
                }

                if (plugInDisabledForAction == null)
                {
                    // Plugin HABILITADO para a ação em questão ?
                    Boolean plugInEnabledForAction = nextDef.hasEnabledActionPath(currentActionPath);
                    if (log.isDebugEnabled())
                    {
                        log.debug("... para a definição=" + nextDef.getClassName() + ", habilitado=" + plugInEnabledForAction);
                    }

                    if (plugInEnabledForAction != null && plugInEnabledForAction.booleanValue())
                    {
                        // Ativar plugin
                        ativaPlugin = true;
                    }
                }
                else if (!plugInDisabledForAction.booleanValue())
                {
                    // Ativar plugin
                    ativaPlugin = true;
                }

                if (ativaPlugin)
                {
                    nextActionPlugIn = (ActionPlugIn) activeActionPlugInRegister.get(registerIndex);
                }
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug("nextActionPlugIn=" + nextActionPlugIn);
        }

        return nextActionPlugIn;
    }

    public static boolean foiInicializado()
    {
        return inicializado;
    }
}

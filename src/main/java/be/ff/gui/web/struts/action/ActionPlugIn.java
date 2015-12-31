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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Basically action plug-ins are Java classes which are transparantly invoked during a
 * request to a Struts action. The action plug-in mechanism is, in concept, very similar
 * to the Servlet Filter mechanism and is meant to provide a clean and transparent way
 * to plug-in common controller logic (such as authentication, workflow, etc.) for all
 * Struts actions in an application. Action plug-ins can be chained allowing the execution
 * of a sequence of plug-ins.
 *
 * <p>Action plug-ins can be chained simply by specifying multiple
 * <code>&lt;action-plug-in&gt;</code> entries in the
 * <code>action-plug-in-config.xml</code> configuration file.
 *
 * <p>Action plug-ins are configured in the <code>action-plug-in-config.xml</code>
 * configuration file, which is typically located in the <code>WEB-INF</code>
 * directory of a Web application.</p>
 *
 * <p>Action plug-ins implement their logic in the <code>execute</code> method.
 * Every action plug-in has access to an <code>ActionPlugInConfig</code> object
 * from which it can obtain its initialization parameters and a reference to the
 * <code>javax.servlet.ServletContext</code> which it can use, for example, to
 * load resources needed for action plug-in tasks.</p>
 *
 * <p>Typical examples for this design are:</p>
 * <ul>
 *  <li>Authentication action plug-ins</li>
 *  <li>Workflow action plug-ins</li>
 *  <li>etc.</li>
 * </ul>
 *
 * @author Gerrie Kimpen
 * @since FF 1.0
 * @see ActionPlugInConfig
 * @see ActionPlugInChain
 */
public interface ActionPlugIn
{

    /**
     * This method is called by the action plug-in framework to indicate to a plug-in
     * that it is being placed into service. The framework calls the <code>init</code>
     * method exactly once after instantiating the action plug-in. The <code>init</code>
     * method must complete successfully before the action plug-in is asked to do any
     * work.
     *
     * <p>The action plug-in framework will not place the action plug-in into service if
     * the <code>init</code> method throws a <code>ActionPlugInException</code>.</p>
     *
     * @param config an <code>ActionPlugInConfig</code> instance that provides initialization support for this action plug-in
     * @throws ActionPlugInException if the action plug-in fails to initialize for some reason
     */
    public void init(ActionPlugInConfig config) throws ActionPlugInException;

    /**
     * The <code>execute</code> method of the action plug-in is called by the
     * framework each time a request/response pair is passed through the plug-in chain.
     *
     * <p>The <code>ActionPlugInChain</code> instance passed in to this method allows the
     * action plug-in to pass on control to the next entity in the chain. On the other
     * hand this method could decide <em>not</em> to call the next entity in the chain by
     * returning an <code>ActionForward</code> instance immediately, which cuts of the
     * action plug-in chain on the point of return.</p>
     *
     * @param mapping the <code>ActionMapping</code> instance, as provided by the Struts framework
     * @param form the <code>ActionForm</code> instance, as provided by the Struts framework
     * @param request the <code>HttpServletRequest</code> instance, as provided by the Struts framework
     * @param response the <code>HttpServletResponse</code> instance, as provided by the Struts framework
     * @param chain reference to the <code>ActionPlugInChain</code> in which this plug-in is participating
     * @throws ActionPlugInException when the plug-in fails for some reason
     */
    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        ActionPlugInChain chain)
        throws ActionPlugInException;

    /**
     * This method gives the plug-in an opportunity to clean up any resources that are
     * being held (for example, memory, file handles, threads) and make sure that any
     * persistent state is synchronized with the plug-in's current state in memory.
     *
     * <p>After this method has been called, no subsequent calls will be made to the
     * <code>execute</code> method again on this instance of the plug-in.</p>
     */
    public void destroy();
}

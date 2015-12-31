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

import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;

/**
 * A configuration object, that is used by the action plug-in mechanism to pass information
 * to an action plug-in during initialization. This information is specified in the
 * <code>action-plug-in-config.xml</code>.
 *
 * @author Gerrie Kimpen
 * @since FF 1.0
 * @see ActionPlugIn
 */
public class ActionPlugInConfig
{

    private Map initParams = null;
    private ServletContext servletContext = null;

    /**
     * Constucts an <code>ActionPlugInConfig</code> instance.
     *
     * @param def the action plug-in properties as they are defined in the configuration file
     * @param context the <code>ServletContext</code> of the application in which a plug-in will run
     */
    protected ActionPlugInConfig(ActionPlugInDefinition def, ServletContext context)
    {

        this.initParams = def.getInitParams();
        this.servletContext = context;
    }

    /**
     * Returns a <code>String</code> containing the value of the named initialization parameter, or
     * <code>null</code> if the parameter does not exist.
     *
     * @param name a <code>String</code> specifying the name of the initialization parameter
     * @return a <code>String</code> containing the value of the initialization parameter
     */
    public String getInitParameter(String name)
    {
        return (String) initParams.get(name);
    }

    /**
     * Returns the names of the plug-in's initialization parameters as an <code>Iterator</code> of
     * <code>String</code> objects, or an empty <code>Iterator</code> if the plug-in has no
     * initialization parameters.
     *
     * @return an <code>Iterator</code> of String objects containing the names of the plug-in's initialization parameters
     */
    public Iterator getInitParameterNames()
    {
        return initParams.keySet().iterator();
    }

    /**
     * Returns a reference to the <code>javax.servlet.ServletContext</code> in which the caller is executing.
     *
     * @return a <code>javax.servlet.ServletContext</code> object, used by the caller to interact with its servlet container
     */
    public ServletContext getServletContext()
    {
        return servletContext;
    }
}

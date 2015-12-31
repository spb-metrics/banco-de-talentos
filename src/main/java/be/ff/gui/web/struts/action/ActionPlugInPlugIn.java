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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

/**
 * This Struts plug-in loads and verifies (using a DTD) the action plug-in configuration
 * file, which is typically called <code>action-plug-in-config.xml</code>. It also
 * initializes and destroys the action plug-in chain.
 *
 * @author Gerrie Kimpen
 * @since FF 1.0
 * @see ActionPlugInChainDefinition
 * @see ActionPlugInDefinition
 */
public class ActionPlugInPlugIn implements PlugIn
{
    private static Log log = LogFactory.getLog(ActionPlugInPlugIn.class);

    private static final String DOCTYPE_PUBLIC_ID = "-//ASQdotCOM//DTD Struts Action Plug-In Extension 1.0//EN";
    private String configFile = null;
    private String configDTD = null;

    /**
     * Returns the context-relative path to the action plug-in configuration file.
     * Typically this is <code>/WEB-INF/action-plug-in-config.xml</code>.
     *
     * @return the context-relative path to the action plug-in configuration file
     */
    public String getConfigFile()
    {
        return (configFile == null) ? "" : configFile;
    }

    /**
     * Used to set the context-relative path to the action plug-in configuration file.
     * Typically this is <code>/WEB-INF/action-plug-in-config.xml</code>. This method
     * is called by the Struts plug-in mechanism.
     *
     * @param configFile a context-relative path to the action plug-in configuration file
     */
    public void setConfigFile(String configFile)
    {
        this.configFile = configFile;
    }

    /**
     * Returns the context-relative path to the DTD of the action plug-in configuration
     * file. Typically this is <code>/WEB-INF/action-plug-in-config_1_0.dtd</code>.
     *
     * @return the context-relative path to the DTD of action plug-in configuration file
     */
    public String getConfigDTD()
    {
        return configDTD;
    }

    /**
     * Used to set the context-relative path to the DTD of the action plug-in configuration
     * file. Typically this is <code>/WEB-INF/action-plug-in-config_1_0.dtd</code>. This
     * method is called by the Struts plug-in mechanism.
     *
     * @param configDTD a context-relative path to the DTD of the action plug-in configuration file
     */
    public void setConfigDTD(String configDTD)
    {
        this.configDTD = configDTD;
    }

    /**
     * Loads and verifies (using a DTD) the action plug-in configuration file. Afterwards this
     * method initializes the <code>ActionPlugInChain</code>.
     *
     * @param config ApplicationConfig for the sub-application with which this plug in is associated
     * @exception ServletException if this <code>PlugIn</code> cannot be successfully initialized
     */
    public void init(ActionServlet config, ModuleConfig module) throws ServletException
    {
        if (ActionPlugInChain.foiInicializado())
        {
            if (log.isDebugEnabled())
            {
                log.debug("O ActionPlugInPlugIn já foi inicializado anteriormente. Ignorando.");
            }
            return;
        }

        if (log.isDebugEnabled())
        {
            log.debug("Entrada no método");
        }

        // this value object will hold the values specified in the action plug-in config file
        ActionPlugInChainDefinition chainDefintion = new ActionPlugInChainDefinition();

        // create a digester to scan through the action plug-in config file
        Digester digester = new Digester();
        digester.push(chainDefintion);

        // try to find a local DTD to validate the configuration file
        ServletContext context = config.getServletContext();
        if (getConfigDTD() == null)
        {
            String msg = "[ActionPlugInPlugIn::init] Please specify a valid context relative location of "
                + "the DTD for the action plug-in configuration file. Do this in the Struts configuration "
                + "file at the plug-in tag that initializes the Action Plug-in Extension.";
            log.warn(msg);
            digester.setValidating(false);
            // Although set to false the SAX based parser STILL wants to load the publicly
            // available DTD that is specified in the XML file!! This causes an error on
            // times when the DTD at the specified location is not available.
            // Why doesn't this statement turn this of????
        }
        else
        {
            URL dtdURL = null;
            try
            {
                dtdURL = context.getResource(getConfigDTD());
                if (dtdURL == null)
                {
                    String msg = "[ActionPlugInPlugIn::init] The action plug-in configuration DTD was not found at the " + "context relative location '"
                        + getConfigDTD() + "'. Please make sure that this DTD is available at "
                        + "a context relative location, because the system identifier that is specified in the "
                        + "configuration file might not be accessible.";
                    log.warn(msg);
                    digester.setValidating(false);
                    // Although set to false the SAX based parser STILL wants to load the publicly
                    // available DTD that is specified in the XML file!! This causes an error on
                    // times when the DTD at the specified location is not available.
                    // Why doesn't this statement turn this of????
                }
                else
                {
                    digester.register(DOCTYPE_PUBLIC_ID, dtdURL.toString());
                    digester.setValidating(true);
                }
            }
            catch (MalformedURLException e)
            {
                String msg = "[ActionPlugInPlugIn::init] The location of the DTD for the action plug-in configuration file was " + "invalid:'" + getConfigDTD()
                    + "'. Please specify a valid context relative location for the DTD.";
                log.warn(msg);
                digester.setValidating(false);
                // Although set to false the SAX based parser STILL wants to load the publicly
                // available DTD that is specified in the XML file!! This causes an error on
                // times when the DTD at the specified location is not available.
                // Why doesn't this statement turn this of????
            }
        }

        // add rules
        digester.addObjectCreate("action-plug-in-config/action-plug-in", ActionPlugInDefinition.class);
        digester.addSetNext("action-plug-in-config/action-plug-in", "addActionPlugInDefintion", ActionPlugInDefinition.class.getName());
        digester.addCallMethod("action-plug-in-config/action-plug-in/class", "setClassName", 0);
        digester.addCallMethod("action-plug-in-config/action-plug-in/init-params/param", "addInitParam", 2);
        digester.addCallParam("action-plug-in-config/action-plug-in/init-params/param/name", 0);
        digester.addCallParam("action-plug-in-config/action-plug-in/init-params/param/value", 1);
        digester.addCallMethod("action-plug-in-config/action-plug-in/disabled-for/action-path", "addDisabledActionPath", 0);
        digester.addCallMethod("action-plug-in-config/action-plug-in/enabled-for/action-path", "addEnabledActionPath", 0);

        // point to the XML file
        InputStream input = context.getResourceAsStream(getConfigFile());
        if (input == null)
        {
            String msg = "[ActionPlugInPlugIn::init] The action plug-in configuration file was not found " + "at location '" + getConfigFile()
                + "'. Please make sure that you have specified " + "the correct value for the 'configFile' property of the action plug-in Struts "
                + "plug-in that is defined in struts-config.xml. Please note that this path is Web " + "application context relative.";
            throw new ServletException(msg);
        }

        // run the digester
        try
        {
            digester.parse(new BufferedInputStream(input));
        }
        catch (Exception e)
        {
            String msg = "[ActionPlugInPlugIn::init] An exception was thrown during the parsing of the " + "action plug-in configuration file ("
                + getConfigFile() + "). Make sure that " + "the configuration file refers to a DTD and that its content is valid according to " + "that DTD.";
            throw new ServletException(msg, e);
        }
        finally
        {
            if (input != null)
            {
                try
                {
                    input.close();
                }
                catch (IOException e)
                {
                    log.error("[ActionPlugInPlugIn::init] An IOException was thrown while trying to close the input stream of the action plug-in configuration file.", e);
                }
            }
        }

        // ask the action plug-in chain to initialize itself
        ActionPlugInChain.init(chainDefintion, context);
    }

    /**
     * Destroys the action plug-in chain.
     */
    public void destroy()
    {
        ActionPlugInChain.destroy();
    }
}

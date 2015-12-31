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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * This class represents the values as they are specified in an <code>&lt;action-plug-in&gt;</code> tag of the
 * <code>action-plug-in-config.xml</code> configuration file.
 * 
 * <p>
 * The actual parsing of the <code>action-plug-in-config.xml</code> configuration file is done in the
 * <code>ActionPlugInPlugIn</code> class.
 * </p>
 *
 * @author Gerrie Kimpen
 *
 * @see ActionPlugInChainDefinition
 * @see ActionPlugInPlugIn
 * @since FF 1.0
 */
public class ActionPlugInDefinition
{
    private String className = null;
    private Map initParams = null;
    private Set disabledActionPaths = null;
    private Set enabledActionPaths = null;

    /**
     * Constructs a new <code>ActionPlugInDefinition</code> instance.
     */
    public ActionPlugInDefinition()
    {
        initParams = new HashMap();
        disabledActionPaths = new HashSet();
        enabledActionPaths = new HashSet();
    }

    /**
     * Returns the value of the <code>&lt;class&gt;</code> tag like it was specified in the action plug-in
     * configuration file.
     *
     * @return the value of the <code>&lt;class&gt;</code> tag
     */
    public String getClassName()
    {
        return className;
    }

    /**
     * Used to set the value of the <code>&lt;class&gt;</code> tag like it was specified in the action plug-in
     * configuration file.
     *
     * @param className the value of the <code>&lt;class&gt;</code> tag
     */
    public void setClassName(String className)
    {
        this.className = className;
    }

    /**
     * Returns a <code>Map</code> that contains the <code>&lt;init-params&gt;</code> like they were specified in the
     * action plug-in configuration file.
     *
     * @return a <code>Map</code> instance that holds key-value pairs represented by the values of the
     *         <code>&lt;name&gt;</code> and <code>&lt;value&gt;</code> tags
     */
    public Map getInitParams()
    {
        return initParams;
    }

    /**
     * Adds a key/value pair to the initialization parameter list that is returned by the <code>getInitParams</code>
     * method.
     *
     * @param name the name of the init parameter, as specified in the <code>&lt;name&gt;</code> tag
     * @param value the value of the init parameter, as specified in the <code>&lt;value&gt;</code> tag
     */
    public void addInitParam(String name, String value)
    {
        initParams.put(name, value);
    }

    /**
     * Returns a <code>Map</code> that contains the (<code>&lt;disabled-for&gt;</code>)
     * <code>&lt;action-path&gt;</code> values, like they were specified in the action plug-in configuration file.
     *
     * @return a <code>Set</code> containing the values of the action paths for which this action plug-in has to be
     *         disabled
     */
    public Set getDisabledActionPaths()
    {
        return disabledActionPaths;
    }

    /**
     * Adds a Struts action path (<code>ActionMapping::getPath</code>) value, which is specified in the action plug-in
     * configuration file, to the <code>Set</code> that is returned by the <code>getDisabledActionPaths</code> method.
     *
     * @param path the value of the action path, as specified in the <code>&lt;action-path&gt;</code> tag
     */
    public void addDisabledActionPath(String path)
    {
        disabledActionPaths.add(path);
    }

    /**
     * Returns <code>true</code> if the specfied action path is in the set of disabled action paths returned by the
     * <code>getDisabledActionPaths</code> method; else <code>false</code>.
     *
     * @param actionPath the Struts action path (<code>ActionMapping::getPath</code>) to search
     *
     * @return <code>true</code> if the specfied action path is in the set of disabled action paths; else
     *         <code>false</code>
     */
    public Boolean hasDisabledActionPath(String actionPath)
    {
        Boolean retorno = null;

        if (disabledActionPaths.size() != 0)
        {
            retorno = new Boolean(contemMapeamento(disabledActionPaths, actionPath));
        }

        return retorno;
    }

    /**
     * Returns a <code>Map</code> that contains the (<code>&lt;disabled-for&gt;</code>)
     * <code>&lt;action-path&gt;</code> values, like they were specified in the action plug-in configuration file.
     *
     * @return a <code>Set</code> containing the values of the action paths for which this action plug-in has to be
     *         disabled
     */
    public Set getEnabledActionPaths()
    {
        return enabledActionPaths;
    }

    /**
     * Adds a Struts action path (<code>ActionMapping::getPath</code>) value, which is specified in the action plug-in
     * configuration file, to the <code>Set</code> that is returned by the <code>getDisabledActionPaths</code> method.
     *
     * @param path the value of the action path, as specified in the <code>&lt;action-path&gt;</code> tag
     */
    public void addEnabledActionPath(String path)
    {
        enabledActionPaths.add(path);
    }

    /**
     * Returns <code>true</code> if the specfied action path is in the set of disabled action paths returned by the
     * <code>getDisabledActionPaths</code> method; else <code>false</code>.
     *
     * @param actionPath the Struts action path (<code>ActionMapping::getPath</code>) to search
     *
     * @return <code>true</code> if the specfied action path is in the set of disabled action paths; else
     *         <code>false</code>
     */
    public Boolean hasEnabledActionPath(String actionPath)
    {
        Boolean retorno = null;

        if (enabledActionPaths.size() != 0)
        {
            retorno = new Boolean(contemMapeamento(enabledActionPaths, actionPath));
        }

        return retorno;
    }

    /**
     * Returns this class represented as a <code>String</code>.
     *
     * @return this class, represented as a <code>String</code>
     */
    public String toString()
    {
        StringBuffer stringRep = new StringBuffer();

        stringRep.append("-- action plug-in class --\n");
        stringRep.append(getClassName());
        stringRep.append("\n");

        stringRep.append("-- init params (key = value) --\n");
        Set set = getInitParams().keySet();
        Iterator iter = set.iterator();
        while (iter.hasNext())
        {
            String name = (String) iter.next();
            String value = (String) initParams.get(name);
            stringRep.append(name);
            stringRep.append(" = ");
            stringRep.append(value);
            stringRep.append("\n");
        }

        stringRep.append("-- disabled action paths --\n");
        set = getDisabledActionPaths();
        iter = set.iterator();
        while (iter.hasNext())
        {
            String path = (String) iter.next();
            stringRep.append(path);
            stringRep.append("\n");
        }

        stringRep.append("-- enabled action paths --\n");
        set = getEnabledActionPaths();
        iter = set.iterator();
        while (iter.hasNext())
        {
            String path = (String) iter.next();
            stringRep.append(path);
            stringRep.append("\n");
        }

        return stringRep.toString();
    }

    private boolean contemMapeamento(Set mapeamento, String strMapeamento)
    {
        boolean retorno = false;

        Iterator iteracao = mapeamento.iterator();
        while (iteracao.hasNext() && !retorno)
        {
            String strDefinidaActionPlugin = (String) iteracao.next();
            //A linha de baixo estava considerando um batimento: /indexSigespSimulacaoAposentadoria e /indexSigesp  
            //retorno = strMapeamento.matches(".*" + strDefinidaActionPlugin + ".*");
            retorno = strMapeamento.equals(strDefinidaActionPlugin);
        }

        return retorno;
    }
}

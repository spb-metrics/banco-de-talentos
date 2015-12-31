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

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * This class represents the values as they are specified in the
 * <code>action-plug-in-config.xml</code> configuration file.
 *
 * <p>The actual parsing of the <code>action-plug-in-config.xml</code>
 * configuration file is done in the <code>ActionPlugInPlugIn</code>
 * class.</p>
 *
 * @author Gerrie Kimpen
 * @since FF 1.0
 * @see ActionPlugInPlugIn
 */
public class ActionPlugInChainDefinition {

    /**
     * A list of ActionPlugInDefinition classes.
     */
    private ArrayList actionPlugInDefs = null;

    /**
     * Constructs a new <code>ActionPlugInChainDefinition</code> instance.
     */
    public ActionPlugInChainDefinition() {
        actionPlugInDefs = new ArrayList();
    }

    /**
     * Returns a <code>List</code> of <code>ActionPlugInDefinition</code> instances
     * which reprensent the values of each <code>&lt;action-plug-in&gt;</code> tag,
     * as they are specified in the action plug-in configuration file.
     *
     * @return a <code>List</code> of <code>ActionPlugInDefinition</code> instances
     */
    public List getActionPlugInDefintions() {
        return actionPlugInDefs;
    }

    /**
     * Adds an <code>ActionPlugInDefinition</code> instance to the <code>List</code>
     * that is returned by the <code>getActionPlugInDefintions</code> method.
     *
     * @param def an <code>ActionPlugInDefinition</code> instance that must be added to the action plug-in defintions list
     */
    public void addActionPlugInDefintion(ActionPlugInDefinition def) {
        actionPlugInDefs.add(def);
    }

    /**
     * Returns this class represented as a <code>String</code>.
     *
     * @return this class, represented as a <code>String</code>
     */
    public String toString() {

        StringBuffer stringRep = new StringBuffer();
        ListIterator iter = getActionPlugInDefintions().listIterator();
        while (iter.hasNext()) {
            ActionPlugInDefinition def = (ActionPlugInDefinition) iter.next();
            stringRep.append("\n---- ActionPlugInDefinition ----\n");
            stringRep.append(def.toString());
        }
        return stringRep.toString();
    }
}

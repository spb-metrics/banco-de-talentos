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

import br.gov.camara.exception.CDException;

/**
 * Exception that is thrown when something goes wrong in an action plug-in.
 *
 * @author Gerrie Kimpen
 * @since FF 1.0
 * @see ActionPlugIn
 */
public class ActionPlugInException extends CDException
{

    private Throwable rootCause = null;

    /**
     * Constructs a new plug-in exception with the specified message.
     *
     * @param message a <code>String</code> specifying the text of the exception message
     */
    public ActionPlugInException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new plug-in exception with the specified message and
     * the <code>Throwable</code> instance that caused the action plug-in to
     * quit its normal operation.
     *
     * @param message a <code>String</code> specifying the text of the exception message
     * @param rootCause the <code>Throwable</code> that caused the action plug-in to fail
     */
    public ActionPlugInException(String message, Throwable rootCause)
    {
        super(message);
        this.rootCause = rootCause;
    }

    /**
     * Constructs a new plug-in exception with the <code>Throwable</code>
     * instance that caused the action plug-in to quit its normal operation.
     *
     * @param rootCause the <code>Throwable</code> that caused the action plug-in to fail
     */
    public ActionPlugInException(Throwable rootCause)
    {
        this.rootCause = rootCause;
    }

    /**
     * Returns the <code>Throwable</code> instance that caused the action plug-in to
     * quit its normal operation, or null if there was no &quot;root cause&quot;
     * specified in the constructor.
     *
     * @return rootCause the <code>Throwable</code> that caused the action plug-in to fail, or null
     */
    public Throwable getRootCause()
    {
        return rootCause;
    }
}

/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.displaytag.export;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.jsp.JspException;

/**
 * Main interface for exportViews which need to output binary data.
 * 
 * @author Fabrizio Giustina
 * @version $Revision: 1.3 $ ($Author: fgiust $)
 */
public abstract class BinaryExportView implements ExportView
{

    /**
     * Returns the exported content as a String.
     * 
     * @param out output writer
     * @throws IOException for exceptions in accessing the output stream
     * @throws JspException for other exceptions during export
     */
    public abstract void doExport(OutputStream out) throws IOException, JspException;

    /**
     * can be implemented to escape values for different output.
     * 
     * @param value original column value
     * @return escaped column value
     */
    protected String escapeColumnValue(Object value)
    {
        if (value == null)
        {
            return "";
        }

        String retorno = value.toString();

        // Substituições
        retorno = retorno.replaceAll("\t", " ");
        retorno = retorno.replaceAll("\n", " ");
        retorno = retorno.replaceAll("\r", " ");

        int posicaoInicial;
        int posicaoFinal;

        // <a ..... >
        String[] htmlTags = { "<a", "<img", "</" };
        String delimitadorHtml = ">";
        for (int i = 0; i < htmlTags.length; i++)
        {
            do
            {
                posicaoFinal = -1;
                posicaoInicial = retorno.indexOf(htmlTags[i]);
                if (posicaoInicial > -1)
                {
                    posicaoFinal = retorno.indexOf(delimitadorHtml, posicaoInicial);
                    if (posicaoFinal > -1)
                    {
                        retorno = retorno.substring(0, posicaoInicial) + retorno.substring(posicaoFinal + 1);
                    }
                }
            }
            while (posicaoInicial > -1 && posicaoFinal > -1);
        }

        // Remover espacos repetidos
        while (retorno.indexOf("  ") > -1)
        {
            retorno = retorno.replaceAll("  ", " ");
        }

        return retorno.trim();
    }

}

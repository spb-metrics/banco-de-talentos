/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.displaytag.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.tags.TableTag;
import org.displaytag.tags.TableTagParameters;

import br.gov.camara.exception.CDException;
import br.gov.camara.util.relatorio.ExibicaoDocumento;

/**
 * Actually writes out the content of the wrapped response. Used by the j2ee filter and the Spring interceptor
 * implementations.
 * 
 * @author Fabrizio Giustina
 * @version $Revision: 1.6 $ ($Author: fgiust $)
 */
public final class ExportDelegate
{

    /**
     * logger
     */
    private static Log log = LogFactory.getLog(ExportDelegate.class);

    /**
     * Donìt instantiate.
     */
    private ExportDelegate()
    {
    // unused
    }

    /**
     * Actually writes exported data. Extracts content from the Map stored in request with the
     * <code>TableTag.FILTER_CONTENT_OVERRIDE_BODY</code> key.
     * 
     * @param wrapper BufferedResponseWrapper implementation
     * @param response HttpServletResponse
     * @param request ServletRequest
     * @throws IOException exception thrown by response writer/outputStream
     */
    protected static void writeExport(HttpServletResponse response, ServletRequest request, BufferedResponseWrapper wrapper) throws IOException
    {

        if (wrapper.isOutRequested())
        {
            // data already written
            log.debug("Filter operating in unbuffered mode. Everything done, exiting");
            return;
        }

        // if you reach this point the PARAMETER_EXPORTING has been found, but the special header has never been set in
        // response (this is the signal from table tag that it is going to write exported data)
        log.debug("Filter operating in buffered mode. ");

        Map bean = (Map) request.getAttribute(TableTag.FILTER_CONTENT_OVERRIDE_BODY);

        if (log.isDebugEnabled())
        {
            log.debug(bean);
        }

        Object pageContent = bean.get(TableTagParameters.BEAN_BODY);

        if (pageContent == null)
        {
            if (log.isDebugEnabled())
            {
                log.debug("Filter is enabled but exported content has not been found. Maybe an error occurred?");
            }

            PrintWriter out = response.getWriter();
            out.write(wrapper.getContentAsString());
            out.flush();
            return;
        }

        // clear headers
        if (!response.isCommitted())
        {
            response.reset();
        }

        String filename = (String) bean.get(TableTagParameters.BEAN_FILENAME);
        String contentType = "application/download";

        String characterEncoding = wrapper.getCharacterEncoding();
        String wrappedContentType = wrapper.getContentType();

        if (wrappedContentType != null && wrappedContentType.indexOf("charset") > -1)
        {
            // charset is already specified (see #921811)
            characterEncoding = StringUtils.substringAfter(wrappedContentType, "charset=");
        }

        if (characterEncoding != null && contentType.indexOf("charset") == -1) //$NON-NLS-1$
        {
            contentType += "; charset=" + characterEncoding; //$NON-NLS-1$
        }

        ExibicaoDocumento exibicao = new ExibicaoDocumento();
        exibicao.setTipoConteudo(contentType);
        exibicao.setNomeArquivo(filename);
        exibicao.setAsAttachment(true);
        if (((String) bean.get(TableTagParameters.BEAN_CONTENTTYPE)).indexOf("excel") > -1)
        {
            exibicao.setExtensaoArquivo(".xls");
        }
        else if (((String) bean.get(TableTagParameters.BEAN_CONTENTTYPE)).indexOf("csv") > -1)
        {
            exibicao.setExtensaoArquivo(".csv");
        }
        else if (((String) bean.get(TableTagParameters.BEAN_CONTENTTYPE)).indexOf("xml") > -1)
        {
            exibicao.setExtensaoArquivo(".xml");
        }
        else if (((String) bean.get(TableTagParameters.BEAN_CONTENTTYPE)).indexOf("pdf") > -1)
        {
            exibicao.setExtensaoArquivo(".pdf");
        }

        byte[] content;
        if (pageContent instanceof String)
        {
            content = ((String) pageContent).getBytes(characterEncoding);
        }
        else
        {
            content = (byte[]) pageContent;
        }

        try
        {
            exibicao.gerarExibicaoDocumento(response, content);
        }
        catch (CDException cde)
        {
            // TODO Tratar erro....
        }
    }
}

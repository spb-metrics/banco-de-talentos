/****
 * Copyright 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008 C�mara dos Deputados, Brasil
 * 
 * Este arquivo � parte do programa TALENTOS - Banco de Talentos
 *
 * O TALENTOS � um software livre; voc� pode redistribu�-lo e/ou modific�-lo dentro dos termos da Licen�a P�blica Geral GNU como publicada pela Funda��o do Software Livre (FSF); na vers�o 2 da Licen�a.
 *
 * Este programa � distribu�do na esperan�a que possa ser �til, mas SEM NENHUMA GARANTIA; sem uma garantia impl�cita de ADEQUA��O a qualquer MERCADO ou APLICA��O EM PARTICULAR. Veja a Licen�a P�blica Geral GNU para maiores detalhes.
 * 
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU, sob o t�tulo "LICENCA.txt", junto com este programa, se n�o, escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 ***/

/*
 * Created on 20/10/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package sigesp.comum.util.exception;

import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;
import org.apache.struts.util.ModuleException;

/**
 * @author P_6414
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GlobalExceptionHandler extends ExceptionHandler
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(GlobalExceptionHandler.class);

    /* (non-Javadoc)
     * @see org.apache.struts.action.ExceptionHandler#execute(java.lang.Exception, org.apache.struts.config.ExceptionConfig, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward execute(
        Exception ex,
        ExceptionConfig ae,
        ActionMapping mapping,
        ActionForm formInstance,
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException
    {

        if (log.isDebugEnabled())
        {
            log.debug("Entrada no m�todo...");
        }

        ActionForward forward = null;
        ActionError error = null;
        String property = null;

        // Build the forward from the exception mapping if it exists
        // or from the form input
        if (ae.getPath() != null)
        {
            if (log.isDebugEnabled())
            {
                log.debug("...Encaminhando para o mapeamento informado...");
            }
            forward = new ActionForward(ae.getPath());
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("...Como n�o foi informado um mapeamento, direcionando para 'Input Forward'...");
            }
            forward = mapping.getInputForward();
        }

        // Figure out the error
        if (ex instanceof ModuleException)
        {
            if (log.isDebugEnabled())
            {
                log.debug("...O erro � proveniente de uma 'ModuleException'...");
                log.debug("...Exibindo a StackTrace:");
            }

            gerarLog(ex);

            error = ((ModuleException) ex).getError();
            property = ((ModuleException) ex).getProperty();
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("...O erro N�O � proveniente de uma 'ModuleException'...");
                log.debug("...Exibindo a StackTrace:");
            }

            Integer intIDErro = new Integer(gerarLog(ex));

            String strMensagemErro = null;

            if (ex.getMessage() == null)
            {
                strMensagemErro = "" + ex;
            }
            else
            {
                strMensagemErro = "" + ex.getMessage();
            }

            error = new ActionError(ae.getKey(), intIDErro, strMensagemErro);
            property = error.getKey();
        }

        storeException(request, property, error, ae.getScope());

        return forward;
    }

    protected void storeException(HttpServletRequest request, String property, ActionError error, String scope)
    {

        if (log.isDebugEnabled())
        {
            log.debug("Entrada no m�todo...");
        }

        // J� existe algum ActionErrors no request ?
        ActionErrors errors = (ActionErrors) request.getAttribute(Globals.ERROR_KEY);

        if (errors == null)
        {
            errors = new ActionErrors();
        }

        errors.add(property, error);

        if (scope == "session")
        {
            request.getSession().setAttribute(Globals.ERROR_KEY, errors);
        }
        else
        {
            request.setAttribute(Globals.ERROR_KEY, errors);
        }
    }

    /**
     * Gera mensagem de log
     *
     */
    protected int gerarLog(Throwable cause)
    {
        int intIdentificadorErro = 0;

        // Gera log
        if (log.isErrorEnabled())
        {

            DecimalFormat dcfFormato = new DecimalFormat("000000");

            intIdentificadorErro = 1 + (int) (Math.random() * 999999);
            StackTraceElement[] steException = cause.getStackTrace();
            for (int i = 0; i < steException.length; i++)
            {
                log.error(dcfFormato.format(intIdentificadorErro) + " | ST: " + steException[i]);
            }
            log.error(
                dcfFormato.format(intIdentificadorErro) + " | GM: " + cause.getMessage() + " | " + cause.getClass());
        }

        return intIdentificadorErro;
    }

}

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

package br.gov.camara.util.actionplugin.plugin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.RequestUtils;

import be.ff.gui.web.struts.action.ActionPlugInChain;
import be.ff.gui.web.struts.action.ActionPlugInConfig;
import be.ff.gui.web.struts.action.ActionPlugInException;

/**
 * DOCUMENTAR!
 */
public class AtributoDefinidoActionPlugIn extends BaseActionPlugIn
{
    /**
    * Logger for this class
    */
    private static final Log log = LogFactory.getLog(AtributoDefinidoActionPlugIn.class);

    /** Busca na Session */
    private static final String SESSION = "session";
    /** Busca no Request */
    private static final String REQUEST = "request";
    /** Busca no  ServletContext*/
    private static final String CONTEXT = "context";

    String nomeAtributoContexto = null;
    String forwardNaoDefinido = null;
    String mensagemErro = null;
    String escopo = null;

    /**
     * DOCUMENTAR!
     *
     * @param config DOCUMENTAR!
     */
    public void executarInicializacao(ActionPlugInConfig config) throws ActionPlugInException
    {
        nomeAtributoContexto = (String) config.getInitParameter("nomeAtributoContexto");
        forwardNaoDefinido = (String) config.getInitParameter("forwardNaoDefinido");
        mensagemErro = (String) config.getInitParameter("mensagemErro");
        escopo = (String) config.getInitParameter("escopo");
    }

    /**
     * DOCUMENTAR!
     *
     * @param mapping DOCUMENTAR!
     * @param form DOCUMENTAR!
     * @param request DOCUMENTAR!
     * @param response DOCUMENTAR!
     * @param chain DOCUMENTAR!
     *
     * @return DOCUMENTAR!
     *
     * @throws ActionPlugInException DOCUMENTAR!
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionPlugInChain chain) throws ActionPlugInException
    {

        if (log.isDebugEnabled())
        {
            try
            {
                log.debug("Entrada no metodo 'execute' - URL='" + RequestUtils.requestURL(request).toString() + "'");
            }
            catch (Exception e)
            {}
        }

        String strMensagem = null;

        ActionMessages erros = (ActionMessages) request.getAttribute(Globals.ERROR_KEY);

        if (erros == null)
        {
            if (log.isDebugEnabled())
            {
                log.debug("Criando um novo ActionErrors");
            }
            erros = new ActionErrors();
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("Recuperando o ActionErrors do request");
            }
        }

        // In�cio...
        boolean bAtributoNaoDefinido = false;

        Object valorAtributoContexto = null;

        if (escopo != null && !"".equals(escopo))
        {
            if (escopo.equals(AtributoDefinidoActionPlugIn.SESSION))
            {
                //procurando na Session
                valorAtributoContexto = request.getSession().getAttribute(nomeAtributoContexto);
                if (valorAtributoContexto == null)
                {
                    bAtributoNaoDefinido = true;
                }
            }
            else if (escopo.equals(AtributoDefinidoActionPlugIn.REQUEST))
            {
                //procurando na Request
                valorAtributoContexto = request.getAttribute(nomeAtributoContexto);
                if (valorAtributoContexto == null)
                {
                    bAtributoNaoDefinido = true;
                }
            }
            else if (escopo.equals(AtributoDefinidoActionPlugIn.CONTEXT))
            {
                //procurando no ServletContext
                valorAtributoContexto = request.getSession().getServletContext().getAttribute(nomeAtributoContexto);
                if (valorAtributoContexto == null)
                {
                    bAtributoNaoDefinido = true;
                }
            }
        }
        else
        {
            //aqui n�o definiu nenhum tipo de scope
            // Procura...
            valorAtributoContexto = request.getAttribute(nomeAtributoContexto);

            if (valorAtributoContexto == null)
            {
                valorAtributoContexto = request.getSession().getAttribute(nomeAtributoContexto);
                if (valorAtributoContexto == null)
                {
                    valorAtributoContexto = request.getSession().getServletContext().getAttribute(nomeAtributoContexto);
                }
            }

            // Foi definido um valor ?
            if (valorAtributoContexto == null)
            {
                bAtributoNaoDefinido = true;
            }
        }

        if (bAtributoNaoDefinido)
        {
            // O atributo n�o foi definido ou � nulo....
            strMensagem = mensagemErro;
            if (strMensagem != null)
            {
                erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", strMensagem));
                registraErroRequest(request, erros);
            }

            if (log.isInfoEnabled())
            {
                log.info("O atributo '" + nomeAtributoContexto + "' n�o foi definido em nenhum escopo ou � nulo");
            }
            return mapping.findForward(forwardNaoDefinido);
        }

        return chain.execute(mapping, form, request, response);

    }
}

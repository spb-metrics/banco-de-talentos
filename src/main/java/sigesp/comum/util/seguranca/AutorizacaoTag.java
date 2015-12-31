/****
 * Copyright 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008 Câmara dos Deputados, Brasil
 * 
 * Este arquivo é parte do programa TALENTOS - Banco de Talentos
 *
 * O TALENTOS é um software livre; você pode redistribuí-lo e/ou modificá-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); na versão 2 da Licença.
 *
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU para maiores detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU, sob o título "LICENCA.txt", junto com este programa, se não, escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 ***/

package sigesp.comum.util.seguranca;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

import br.gov.camara.seguranca.Permissao;
import br.gov.camara.seguranca.SegurancaWeb;
import br.gov.camara.seguranca.UsuarioAutenticado;

/**
 * Abstract base class for the various conditional evaluation tags.
 */

public class AutorizacaoTag extends TagSupport
{

    /**
     * 
     */
    private static final long serialVersionUID = -201320154335281316L;

    // Inicializa o log
    private static Log log = LogFactory.getLog(AutorizacaoTag.class);

    /**
     * The name of the HTTP request header to be used as a variable.
     */
    protected String strObjetoControlado = null;

    public String getObjetoControlado()
    {
        return (this.strObjetoControlado);
    }

    public void setObjetoControlado(String strObjetoControlado)
    {
        this.strObjetoControlado = strObjetoControlado;
    }

    /**
     * The message resources for this package.
     */
    protected static MessageResources messages = MessageResources.getMessageResources("org.apache.struts.taglib.logic.LocalStrings");

    // --------------------------------------------------------- Public Methods

    /**
     * Perform the test required for this particular tag, and either evaluate
     * or skip the body of this tag.
     *
     * @exception JspException if a JSP exception occurs
     */
    public int doStartTag() throws JspException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        HttpSession sessao = pageContext.getSession();
        SegurancaWeb segurancaWeb = SegurancaWeb.obterSegurancaWeb(sessao);

        if (segurancaWeb == null)
        {
            if (log.isDebugEnabled())
            {
                log.debug("Não existe autenticação registrada na sessão... Ignorando corpo.");
            }
            return (SKIP_BODY);
        }

        if (!segurancaWeb.temUsuarioLogado(sessao))
        {
            if (log.isDebugEnabled())
            {
                log.debug("Não existe autenticação registrada na sessão... Ignorando corpo.");
            }
            return (SKIP_BODY);
        }

        try
        {
            UsuarioAutenticado usuarioAutenticado = segurancaWeb.obterUsuarioAutenticado();
            Permissao permissao = segurancaWeb.obterPermissao();
            if (permissao.temAutorizacaoFuncionalidade(usuarioAutenticado, getObjetoControlado()))
            {
                return (EVAL_BODY_INCLUDE);
            }
            else
            {
                return (SKIP_BODY);
            }
        }
        catch (Exception ex)
        {
            return (SKIP_BODY);
        }
    }

    /**
     * Evaluate the remainder of the current page normally.
     *
     * @exception JspException if a JSP exception occurs
     */
    public int doEndTag() throws JspException
    {
        return (EVAL_PAGE);
    }

    /**
     * Release all allocated resources.
     */
    public void release()
    {
        super.release();
        strObjetoControlado = null;
    }
}

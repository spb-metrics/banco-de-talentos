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

/**
 * 
 */
package br.gov.camara.util.log4j;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;

import br.gov.camara.seguranca.SegurancaWeb;
import br.gov.camara.seguranca.UsuarioAutenticado;
import br.gov.camara.seguranca.util.UsuarioAutenticadoUtil;

/**
 * @author p_6414
 *
 */
public class RequestInfoMDCFilter implements Filter
{

    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig arg0) throws ServletException
    {
    // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest httpRequest = ((HttpServletRequest) request);

        // Recuperar dados da seguran�a
        UsuarioAutenticado usuario = null;
        HttpSession session = httpRequest.getSession();
        SegurancaWeb segurancaWeb = SegurancaWeb.obterSegurancaWeb(session);
        if (segurancaWeb != null)
        {
            usuario = segurancaWeb.obterUsuarioAutenticado();
        }

        // Recuperar a origem do request
        MDC.put("remoteHost", httpRequest.getRemoteHost());
        MDC.put("remoteAddr", httpRequest.getRemoteAddr());

        boolean bUserAdded = true;
        if (usuario != null)
        {
            bUserAdded = true;
            String strUser = usuario.obterLogin();
            MDC.put("usuario", strUser);
            UsuarioAutenticadoUtil.setUsuarioAutenticado(usuario);
        }

        try
        {
            // Continue processing the rest of the filter chain.
            chain.doFilter(request, response);
        }
        finally
        {
            MDC.remove("remoteHost");
            MDC.remove("remoteAddr");

            if (bUserAdded)
            {
                MDC.remove("usuario");
                UsuarioAutenticadoUtil.setUsuarioAutenticado(null);
            }
        }

    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy()
    {
    // TODO Auto-generated method stub

    }

}

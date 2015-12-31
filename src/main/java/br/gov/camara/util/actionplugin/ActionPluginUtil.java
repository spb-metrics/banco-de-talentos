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

/**
 * 
 */
package br.gov.camara.util.actionplugin;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.RequestUtils;

/**
 * @author p_6414
 */
public class ActionPluginUtil
{
    private static Log log = LogFactory.getLog(ActionPluginUtil.class);

    public static String getRequestURL(HttpServletRequest request)
    {
        String strURL = null;

        try
        {
            strURL = RequestUtils.requestURL(request).toString();
        }
        catch (MalformedURLException mue)
        {
            if (log.isErrorEnabled())
            {
                log.error("Erro inesperado ao obter a URL deste request: " + mue.getMessage());
            }
        }

        return strURL;
    }

    public static String getActionPath(HttpServletRequest request)
    {
        String strUrl = ActionPluginUtil.getRequestURL(request);
        String strActionPath = null;

        if (strUrl != null && !"".equals(strUrl))
        {
            strActionPath = strUrl.substring(strUrl.lastIndexOf('/'));
            strActionPath = strActionPath.substring(0, strActionPath.lastIndexOf('.'));
        }

        return strActionPath;
    }
}

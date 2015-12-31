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

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

package br.gov.camara.util.locale;

import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

/**
 * Plugin para a configuração do locale
 */
public class LocalePlugIn implements PlugIn
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(LocalePlugIn.class);
    private String strLinguagem = "pt";
    private String strPais = "BR";
    private String strTimeZoneID = "America/Sao_Paulo";

    // Implementação método init
    public void init(ActionServlet servlet, ModuleConfig moduleConfig) throws ServletException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Inicializando o plug-in do Locale...");
        }

        Locale.setDefault(new Locale(this.strLinguagem, this.strPais));
        TimeZone.setDefault(TimeZone.getTimeZone(this.strTimeZoneID));

        // Atribui o ID para a instância deste servidor de aplicação
        if (servlet.getServletContext().getAttribute("IdInstanciaServidorAplicacao") == null)
        {
            // note a single Random object is reused here
            servlet.getServletContext().setAttribute("IdInstanciaServidorAplicacao", Integer.toString((new Random()).nextInt()));
        }
    }

    // Implementação do método destroy
    public void destroy()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Finalizando o plug-in do Locale...");
        }
    }

    public String getLinguagem()
    {
        return this.strLinguagem;
    }

    public String getPais()
    {
        return this.strPais;
    }

    public void setLinguagem(String strLinguagem)
    {
        this.strLinguagem = strLinguagem;
    }

    public void setPais(String strPais)
    {
        this.strPais = strPais;
    }

    public String getTimeZoneID()
    {
        return this.strTimeZoneID;
    }

    public void setTimeZoneID(String strTimeZoneID)
    {
        this.strTimeZoneID = strTimeZoneID;
    }

}

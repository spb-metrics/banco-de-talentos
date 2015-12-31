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
 * Plugin para a configura��o do locale
 */
public class LocalePlugIn implements PlugIn
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(LocalePlugIn.class);
    private String strLinguagem = "pt";
    private String strPais = "BR";
    private String strTimeZoneID = "America/Sao_Paulo";

    // Implementa��o m�todo init
    public void init(ActionServlet servlet, ModuleConfig moduleConfig) throws ServletException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Inicializando o plug-in do Locale...");
        }

        Locale.setDefault(new Locale(this.strLinguagem, this.strPais));
        TimeZone.setDefault(TimeZone.getTimeZone(this.strTimeZoneID));

        // Atribui o ID para a inst�ncia deste servidor de aplica��o
        if (servlet.getServletContext().getAttribute("IdInstanciaServidorAplicacao") == null)
        {
            // note a single Random object is reused here
            servlet.getServletContext().setAttribute("IdInstanciaServidorAplicacao", Integer.toString((new Random()).nextInt()));
        }
    }

    // Implementa��o do m�todo destroy
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

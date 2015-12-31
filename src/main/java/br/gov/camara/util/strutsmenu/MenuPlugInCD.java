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

package br.gov.camara.util.strutsmenu;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import net.sf.navigator.displayer.MenuDisplayerMapping;
import net.sf.navigator.menu.MenuPlugIn;
import net.sf.navigator.menu.MenuRepository;
import net.sf.navigator.util.LoadableResourceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;

public class MenuPlugInCD extends MenuPlugIn
{
    // ~ Instance fields
    // ========================================================

    /**
     * The <code>Log</code> instance for this class.
     */
    private static Log log = LogFactory.getLog(MenuPlugInCD.class);

    private MenuRepository repository;

    private String menuConfig = "/WEB-INF/menu-config.xml";

    private HttpServlet servlet;

    // ~ Methods
    // ================================================================

    public String getMenuConfig()
    {
        return menuConfig;
    }

    public void setMenuConfig(String menuConfig)
    {
        this.menuConfig = menuConfig;
    }

    public void init(ActionServlet servlet, ModuleConfig config) throws ServletException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Starting struts-menu initialization");
        }

        this.servlet = servlet;

        // Altera��o por LLD para permitir trabalhar com m�dulos
        repository = (MenuRepository) servlet.getServletContext().getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
        if (repository == null)
        {
            repository = new MenuRepository();
            if (log.isDebugEnabled())
            {
                log.debug("Criando um novo reposit�rio para os menus.");
            }
        }
        // repository = new MenuRepository();
        // Fim da Altera��o por LLD

        // Altera��o em 2006-02-06 por LLD para permitir a especifica��o 
        // de mais de um arquivo de menu no struts-config
        String[] arrMenuConfig = menuConfig.split(",");
        int contador = 0;
        while (contador < arrMenuConfig.length)
        {
            repository.setLoadParam(arrMenuConfig[contador].trim());
            repository.setServletContext(servlet.getServletContext());
            try
            {
                repository.load();
            }
            catch (LoadableResourceException lre)
            {
                throw new ServletException("Failure initializing struts-menu: " + lre.getMessage());
            }
            contador++;
        }

        if (arrMenuConfig.length > 0)
        {
            // Utilizar o CoolMenu3 para o "SigespCoolMenu"
            MenuDisplayerMapping sigespDisplayer = new MenuDisplayerMapping();
            sigespDisplayer.setName("SigespCoolMenu");
            sigespDisplayer.setType("br.gov.camara.util.strutsmenu.ExtendedCoolMenuDisplayer");

            repository.addMenuDisplayerMapping(sigespDisplayer);
        }

        servlet.getServletContext().setAttribute(MenuRepository.MENU_REPOSITORY_KEY, repository);

        if (log.isDebugEnabled())
        {
            log.debug("struts-menu initialization successful");
        }
    }

    public void destroy()
    {
        repository = null;
        servlet.getServletContext().removeAttribute(MenuRepository.MENU_REPOSITORY_KEY);
        menuConfig = null;
        servlet = null;
    }
}

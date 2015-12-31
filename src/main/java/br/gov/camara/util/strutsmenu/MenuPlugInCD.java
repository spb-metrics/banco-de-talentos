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

        // Alteração por LLD para permitir trabalhar com módulos
        repository = (MenuRepository) servlet.getServletContext().getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
        if (repository == null)
        {
            repository = new MenuRepository();
            if (log.isDebugEnabled())
            {
                log.debug("Criando um novo repositório para os menus.");
            }
        }
        // repository = new MenuRepository();
        // Fim da Alteração por LLD

        // Alteração em 2006-02-06 por LLD para permitir a especificação 
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

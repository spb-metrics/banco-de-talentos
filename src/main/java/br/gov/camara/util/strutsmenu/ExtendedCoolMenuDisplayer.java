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

/*
 * CoolMenuDisplayer.java
 *
 * Created on March 21, 2002, 5:47 PM
 */
package br.gov.camara.util.strutsmenu;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import net.sf.navigator.displayer.CoolMenuDisplayer;
import net.sf.navigator.menu.MenuComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * DOCUMENT ME!
 *
 * @author ssayles
 */
public class ExtendedCoolMenuDisplayer extends CoolMenuDisplayer
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(ExtendedCoolMenuDisplayer.class);

    /*Variables for each menu item: (** means that they have to be spesified!)
     0 name: The name of the item. This must be unique for each item. Do not use spaces or strange charachters in this one! **
     1 parent: The name of the menuitem you want this to "connect" to. This will be a submenu of the item that have the name you place in here. ** for all other then the topitems
     2 text: The text you want in the item. ** (except if you use images)
     3 link: The page you want this item to link to.
     4 target: The target window or frame you want the link to go to (Default is same window if you're not using frames, and the mainframe if you're using frames)
     width: The width of the element. If not spesified it will get the default width spesified above.
     height: The height of the element. If not spesified it will get the default height spesified above.
     5 img1: The "off" image for element if you want to use images.
     6 img2: The image that appears onmouseover if using images.
     7 bgcoloroff: The background color for this item. If not spesified it will get the default background color spesified above.
     8 bgcoloron: The "on" background color for this item. If not spesified it will get the default "on" background color spesified above.
     9 textcolor: The text color for this item. If not spesified it will get the default text color spesified above.
     10 hovercolor: The "on" text color for this item. If not spesified it will get the default "on" text color spesified above. Netscape4 ignores this
     11 onclick: If you want something to happen when the element is clicked (different from going to a link) spesifiy it here.
     onmouseover: This will happen when you mouseover the element. Could be status text, another imageswap or whatever.
     onmouseout: This will happen when you mouseout the element.
     
     Remember you can have as many levels/sublevels as you want. Just make sure you spesify the correct "parent" for each item.
     To set styles for each level see above.
     */

    //oCMenu.makeMenu('top0','','&nbsp;News','','')
    //	oCMenu.makeMenu('sub10','top1','New scripts','/scripts/index.asp?show=new')
    /** main message format of the menu.  only 10 args max in jdk1.3 :( */
    private static MessageFormat menuMessage = new MessageFormat("oCMenu.makeMenu(''{0}'',''{1}'',''{2}'',''{3}'',''{4}'','''','''',"
            + "''{5}'',''{6}'',{7},{8},{9},");
    private static MessageFormat menuEstendidoLLD = new MessageFormat(",''{0}'',''{1}'',''{2}''");
    private static final String IMG_ROOT_PATH = "cmImageRootPath";
    private static final String HOVER = "cmHoverColor";
    private static final String DIS_HOVER = "cmDisHoverColor";
    private static final String TOP_IMAGE = "cmTopMenuImage";
    private static final String SUB_IMAGE = "cmSubMenuImage";

    private static final String END_STATEMENT = "\noCMenu.makeStyle(); oCMenu.construct()\n";
    private static final String SCRIPT_END = "</script>\n";

    private boolean blnMenuCriado = false;
    boolean temPermissaoQualquerFilho = false;

    protected void buildMenuString(MenuComponent menu, StringBuffer sb, boolean allowed)
    {
        if (allowed)
        {
            if (log.isDebugEnabled())
            {
                if (menu.getPage() == null)
                {
                    log.debug("...o usuário tem permissão para menu '" + menu.getName() + "'");
                }
                else
                {
                    log.debug("...o usuário tem permissão para menu '" + menu.getPage() + "'");
                }
            }

            MenuComponent[] subMenus = menu.getMenuComponents();

            // Estamos tratando do nível superior (primeiro nível dos menus...)
            if (menu.getParent() == null)
            {
                temPermissaoQualquerFilho = false;
            }

            StringBuffer sbFilhos = new StringBuffer();
            if (subMenus.length > 0)
            {
                for (int i = 0; i < subMenus.length; i++)
                {
                    boolean temPermissao = isAllowed(subMenus[i]);
                    buildMenuString(subMenus[i], sbFilhos, temPermissao);
                    temPermissaoQualquerFilho = temPermissaoQualquerFilho || temPermissao;
                }
            }

            blnMenuCriado = true;
            sb.append(menuMessage.format(getArgs(menu, allowed)));
            sb.append((allowed) ? HOVER : DIS_HOVER);
            sb.append(",'");
            sb.append((menu.getOnclick() == null) ? EMPTY : menu.getOnclick());
            sb.append("'");
            sb.append(menuEstendidoLLD.format(getArgsEstendidoLLD(menu)));
            sb.append(")\n");

            sb.append(sbFilhos);
        }
        else
        {
            if (log.isDebugEnabled())
            {
                if (menu.getPage() == null)
                {
                    log.debug("...o usuário NÃO tem permissão para menu '" + menu.getName() + "'");
                }
                else
                {
                    log.debug("...o usuário NÃO tem permissão para menu '" + menu.getPage() + "'");
                }
            }
        }
    }

    protected String[] getArgsEstendidoLLD(MenuComponent menu)
    {
        String[] args = new String[3];
        args[0] = EMPTY;
        args[1] = EMPTY;
        args[2] = getMenuToolTip(menu);

        return args;
    }

    /**
     * Return a translated title for the menu item that will contain the <code>TOP_IMAGE</code> javscript variable if
     * it is a parent menu with sub menus, or the <code>SUB_IMAGE</code> variable if it is a sub menu with sub menus.
     *
     * @param menu DOCUMENTAR!
     *
     * @return DOCUMENTAR!
     */
    protected String getTitle(MenuComponent menu)
    {
        String title = getMessage(menu.getTitle());

        if (menu.getMenuComponents().length > 0)
        {

            if (menu.getParent() == null)
            { //then us the top image
                if (temPermissaoQualquerFilho)
                {
                    title += "'+" + TOP_IMAGE + "+'";
                }
            }
            else
            { //use the sub menu image
                title += "'+" + SUB_IMAGE + "+'";
            }
        }

        // Estensão LLD: se foi especificada uma imagem, incluir no título

        if (!("".equals(menu.getImage()) || menu.getImage() == null))
        {
            title = "\u003Cimg src=\"'+" + IMG_ROOT_PATH + "+'" + menu.getImage() + "\"\u003E" + title;
        }

        return title;
    }

    /**
     * Alterações com relação ao método original: somente coloca 
     * o javascript referente à criação do menu se foi incluída 
     * pelo menos uma função que cria um item de menu.
     */
    public void end(PageContext context)
    {
        if (log.isDebugEnabled())
        {
            log.debug("FIM da avaliação da permissão do usuário - blnMenuCriado=" + blnMenuCriado + " - Instância=" + this.hashCode());
        }

        if (blnMenuCriado)
        {
            try
            {
                out.print(END_STATEMENT);
            }
            catch (Exception e)
            {}
        }
        try
        {
            out.print(SCRIPT_END);
        }
        catch (Exception e)
        {}
    }

    /* (non-Javadoc)
     * @see com.fgm.web.menu.displayer.MenuDisplayer#display(com.fgm.web.menu.MenuComponent)
     */
    public void display(MenuComponent menu) throws JspException, IOException
    {
        if (log.isDebugEnabled())
        {
            if (menu.getPage() == null)
            {
                log.debug("Avaliando a permissão do usuário para o menu '"
                        + menu.getName()
                        + "'... - blnMenuCriado="
                        + blnMenuCriado
                        + " - Instância="
                        + this.hashCode());
            }
            else
            {
                log.debug("Avaliando a permissão do usuário para o menu '"
                        + menu.getPage()
                        + "'... - blnMenuCriado="
                        + blnMenuCriado
                        + " - Instância="
                        + this.hashCode());
            }
        }
        StringBuffer sb = new StringBuffer();
        buildMenuString(menu, sb, true);
        out.print(sb);
        if (log.isDebugEnabled())
        {
            if (menu.getPage() == null)
            {
                log.debug("Permissão avaliada para o menu '" + menu.getName() + "'... - blnMenuCriado=" + blnMenuCriado + " - Instância=" + this.hashCode());
            }
            else
            {
                log.debug("Permissão avaliada para o menu '" + menu.getPage() + "'... - blnMenuCriado=" + blnMenuCriado + " - Instância=" + this.hashCode());
            }
        }
    }
}

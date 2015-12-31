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

package sigesp.comum.util.tag;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Classe Tag Handler para tabela
 */
public class NivelTag extends TagSupport
{
    private String strLista = "";
    private String strLink = "";
    private String strParametro = "";

    /**
     * Obtém configuração de lista
     *
     * @return Conteúdo de lista
     */
    public String getLista()
    {
        return strLista;
    }

    /**
     * Atribui configuração de lista
     *
     * @param strLinha Conteúdo de lista
     */
    public void setLista(String strLista)
    {
        this.strLista = strLista;
    }

    /**
     * Obtém configuração de link
     *
     * @return Conteúdo de link
     */
    public String getLink()
    {
        return strLink;
    }

    /**
     * Atribui configuração de link
     *
     * @param strLink Conteúdo de link
     */
    public void setLink(String strLink)
    {
        this.strLink = strLink;
    }

    /**
     * Obtém configuração de parâmetro
     *
     * @return Conteúdo de parâmetro
     */
    public String getParametro()
    {
        return strLink;
    }

    /**
     * Atribui configuração de parâmetro
     *
     * @param strParametro Conteúdo de parâmetro
     */
    public void setParametro(String strParametro)
    {
        this.strParametro = strParametro;
    }

    /**
     * Finalização da tag
     *
     * @return Ação da tag
     */
    public int doEndTag()
    {
        JspWriter out = pageContext.getOut();

        // Verifica qual contexto está a lista

        try
        {
            List lstLista = null;
            if (pageContext.getRequest().getAttribute(strLista) != null)
            {
                lstLista = (List) pageContext.getRequest().getAttribute(strLista);
            }
            if (pageContext.getSession().getAttribute(strLista) != null)
            {
                lstLista = (List) pageContext.getSession().getAttribute(strLista);
            }
            if (pageContext.getAttribute(strLista) != null)
            {
                lstLista = (List) pageContext.getAttribute(strLista);
            }

            // Itera lista

            Iterator itrLista = lstLista.iterator();
            String strSaida = "";
            while (itrLista.hasNext())
            {
                String strItem = (String) itrLista.next();
                String[] strItemTokens = strItem.split(";");
                strSaida += "<a href=\""
                    + strLink
                    + "?"
                    + strParametro
                    + "="
                    + strItemTokens[0]
                    + "\" title=\""
                    + strItemTokens[2]
                    + "\">"
                    + strItemTokens[0]
                    + "</a>"
                    + "\n";
                if (itrLista.hasNext())
                {
                    strSaida += " > ";
                }
            }
            out.println(strSaida);
        }
        catch (IOException ioe)
        {

        }
        return (EVAL_PAGE);
    }
}

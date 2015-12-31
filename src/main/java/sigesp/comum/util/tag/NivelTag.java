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
     * Obt�m configura��o de lista
     *
     * @return Conte�do de lista
     */
    public String getLista()
    {
        return strLista;
    }

    /**
     * Atribui configura��o de lista
     *
     * @param strLinha Conte�do de lista
     */
    public void setLista(String strLista)
    {
        this.strLista = strLista;
    }

    /**
     * Obt�m configura��o de link
     *
     * @return Conte�do de link
     */
    public String getLink()
    {
        return strLink;
    }

    /**
     * Atribui configura��o de link
     *
     * @param strLink Conte�do de link
     */
    public void setLink(String strLink)
    {
        this.strLink = strLink;
    }

    /**
     * Obt�m configura��o de par�metro
     *
     * @return Conte�do de par�metro
     */
    public String getParametro()
    {
        return strLink;
    }

    /**
     * Atribui configura��o de par�metro
     *
     * @param strParametro Conte�do de par�metro
     */
    public void setParametro(String strParametro)
    {
        this.strParametro = strParametro;
    }

    /**
     * Finaliza��o da tag
     *
     * @return A��o da tag
     */
    public int doEndTag()
    {
        JspWriter out = pageContext.getOut();

        // Verifica qual contexto est� a lista

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

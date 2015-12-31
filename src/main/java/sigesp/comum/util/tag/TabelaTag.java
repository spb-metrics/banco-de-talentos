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

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Classe Tag Handler para tabela
 */
public class TabelaTag extends TagSupport
{
    private String strLinha = "impar";
    private String strBorda = "0";
    private String strLargura = "";

    /**
     * Obt�m configura��o de linha
     *
     * @return Conte�do de linha
     */
    public String getLinha()
    {
        return strLinha;
    }

    /**
     * Atribui configura��o de linha
     *
     * @param strLinha Conte�do de linha
     */
    public void setLinha(String strLinha)
    {
        this.strLinha = strLinha;
    }

    /**
     * Obt�m configura��o de borda
     *
     * @return Conte�do de borda
     */
    public String getBorda()
    {
        return strBorda;
    }

    /**
     * Atribui configura��o de borda
     *
     * @param strBorda Conte�do de borda
     */
    public void setBorda(String strBorda)
    {
        this.strBorda = strBorda;
    }

    /**
     * Obt�m largura
     *
     * @return Conte�do de largura
     */
    public String getLargura()
    {
        return strLargura;
    }

    /**
     * Atribui largura
     *
     * @param strBorda Conte�do de largura
     */
    public void setLargura(String strLargura)
    {
        this.strLargura = strLargura;
    }

    /**
     * Inicializa��o da tag
     *
     * @return A��o da tag
     */
    public int doStartTag()
    {
        try
        {
            JspWriter out = pageContext.getOut();
            out.print(
                "<table class=\"table\" cellspacing=\"0\" cellpadding=\"4\" bordercolor=\"#FFFFFF\" "
                    + "bordercolorlight=\"#8D8D8D\" border=\" "
                    + strBorda
                    + "\" "
                    + ("".equals(strLargura) ? "" : " width=\"" + strLargura + "\"")
                    + ">");
        }
        catch (IOException ioe)
        {
        }

        return (EVAL_BODY_INCLUDE);
    }

    /**
     * Finaliza��o da tag
     *
     * @return A��o da tag
     */
    public int doEndTag()
    {
        try
        {
            JspWriter out = pageContext.getOut();
            out.print("</table>");
        }
        catch (IOException ioe)
        {
        }

        return (EVAL_PAGE);
    }
}

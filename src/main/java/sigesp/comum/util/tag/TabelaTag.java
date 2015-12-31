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
     * Obtém configuração de linha
     *
     * @return Conteúdo de linha
     */
    public String getLinha()
    {
        return strLinha;
    }

    /**
     * Atribui configuração de linha
     *
     * @param strLinha Conteúdo de linha
     */
    public void setLinha(String strLinha)
    {
        this.strLinha = strLinha;
    }

    /**
     * Obtém configuração de borda
     *
     * @return Conteúdo de borda
     */
    public String getBorda()
    {
        return strBorda;
    }

    /**
     * Atribui configuração de borda
     *
     * @param strBorda Conteúdo de borda
     */
    public void setBorda(String strBorda)
    {
        this.strBorda = strBorda;
    }

    /**
     * Obtém largura
     *
     * @return Conteúdo de largura
     */
    public String getLargura()
    {
        return strLargura;
    }

    /**
     * Atribui largura
     *
     * @param strBorda Conteúdo de largura
     */
    public void setLargura(String strLargura)
    {
        this.strLargura = strLargura;
    }

    /**
     * Inicialização da tag
     *
     * @return Ação da tag
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
     * Finalização da tag
     *
     * @return Ação da tag
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

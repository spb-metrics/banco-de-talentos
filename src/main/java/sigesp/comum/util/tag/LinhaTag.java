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

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Classe Tag Hanlder de linha
 */
public class LinhaTag extends TagSupport
{
    private String strCabecalho = "nao";
    private String strSpan = "";
    private String strLargura = "";

    /**
     * Obtém configuração de cabeçalho
     *
     * @return Conteúdo de cabeçalho
     */
    public String getCabecalho()
    {
        return strCabecalho;
    }

    /**
     * Atribui configuração de cabeçalho
     *
     * @param strCabecalho Conteúdo de cabeçalho
     */
    public void setCabecalho(String strCabecalho)
    {
        this.strCabecalho = strCabecalho;
    }

    /**
     * Obtém configuração de span
     *
     * @return Conteúdo de span
     */
    public String getSpan()
    {
        return strSpan;
    }

    /**
     * Atribui configuração de span
     *
     * @param strSpan Conteúdo de span
     */
    public void setSpan(String strSpan)
    {
        this.strSpan = strSpan;
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
     *
     * @throws JspTagException se ocorrer algum erro relacionado a tag
     */
    public int doStartTag() throws JspTagException
    {
        TabelaTag parent = (TabelaTag) findAncestorWithClass(this, TabelaTag.class);
        if (parent == null)
        {
            throw new JspTagException("Linha fora de tabela");
        }

        try
        {
            JspWriter out = pageContext.getOut();

            // Verifica se é cabeçalho

            if ("sim".equals(strCabecalho))
            {
                out.print(
                    "<tr class=\"tableRowHeader\" " + ("".equals(strLargura) ? "" : " width=\"" + strLargura + "\""));
            }
            else
            {
                if ("impar".equals(parent.getLinha()))
                {
                    out.print(
                        "<tr class=\"tableRowOdd\" " + ("".equals(strLargura) ? "" : " width=\"" + strLargura + "\""));
                    parent.setLinha("par");
                }
                else
                {
                    out.print(
                        "<tr class=\"tableRowEven\" " + ("".equals(strLargura) ? "" : " width=\"" + strLargura + "\""));
                    parent.setLinha("impar");
                }
            }

            // Verifica se há span
            if ("".equals(strSpan))
            {
                out.print(" rowspan=\"" + strSpan + "\"");
            }

            // Adiciona fechamento da tag
            out.print(">");
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

            out.print("</tr>");
        }
        catch (IOException ioe)
        {
        }

        return (EVAL_PAGE);
    }
}

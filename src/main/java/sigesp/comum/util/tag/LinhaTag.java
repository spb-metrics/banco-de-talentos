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
     * Obt�m configura��o de cabe�alho
     *
     * @return Conte�do de cabe�alho
     */
    public String getCabecalho()
    {
        return strCabecalho;
    }

    /**
     * Atribui configura��o de cabe�alho
     *
     * @param strCabecalho Conte�do de cabe�alho
     */
    public void setCabecalho(String strCabecalho)
    {
        this.strCabecalho = strCabecalho;
    }

    /**
     * Obt�m configura��o de span
     *
     * @return Conte�do de span
     */
    public String getSpan()
    {
        return strSpan;
    }

    /**
     * Atribui configura��o de span
     *
     * @param strSpan Conte�do de span
     */
    public void setSpan(String strSpan)
    {
        this.strSpan = strSpan;
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

            // Verifica se � cabe�alho

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

            // Verifica se h� span
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
     * Finaliza��o da tag
     *
     * @return A��o da tag
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

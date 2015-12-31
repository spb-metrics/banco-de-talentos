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
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.taglibs.display.ColumnDecorator;

/**
 * Classe Tag Handler de coluna
 */
public class ColunaTag extends BodyTagSupport
{
    private String strSpan = "";

    private String strLargura = "";

    private String strDecorator = "";

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
     * Obtém decorator
     *
     * @return String decorator formatado
     */
    public String getDecorator()
    {
        return strDecorator;
    }

    /**
     * Atribui decorator
     *
     * @param strDecorator
     */
    public void setDecorator(String strDecorator)
    {
        this.strDecorator = strDecorator;
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
            throw new JspTagException("Coluna sem tabela");
        }

        return (EVAL_BODY_BUFFERED);
    }

    /**
     * Finalização da tag
     *
     * @return Ação da tag
     */
    public int doAfterBody()
    {
        try
        {
            String Texto = "";
            BodyContent body = getBodyContent();
            JspWriter out = body.getEnclosingWriter();
            Texto = "<td class=\"tableCell\"" + ("".equals(strSpan) ? "" : (" colspan=\"" + strSpan + "\""))
                + ("".equals(strLargura) ? "" : " width=\"" + strLargura + "\"") + ">";
            if ((strDecorator != null) && (!strDecorator.equals("")))
            {
                ColumnDecorator objCol = null;
                objCol = loadColumnDecorator(strDecorator);
                Texto += objCol.decorate(body.getString());
            }
            else
            {
                Texto += body.getString();
            }
            Texto += "</td>";

            out.print(Texto);
        }
        catch (IOException ioe)
        {}
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (SKIP_BODY);
    }

    /**
     * Se for definido uma classe decorator, será feita uma instância da mesma e retornada.
     * 
     * @param columnDecoratorName String nome da classe do Decorator
     * 
     * @return instância de ColumnDecorator
     * 
     */
    private ColumnDecorator loadColumnDecorator(String columnDecoratorName)
    {
        if (columnDecoratorName == null || columnDecoratorName.length() == 0)
        {
            return null;
        }
        try
        {
            Class decoratorClass = Class.forName(columnDecoratorName);
            return (ColumnDecorator) decoratorClass.newInstance();
        }

        catch (ClassNotFoundException e)
        {}
        catch (InstantiationException e)
        {}
        catch (IllegalAccessException e)
        {}
        catch (ClassCastException e)
        {}
        return null;

    }

}

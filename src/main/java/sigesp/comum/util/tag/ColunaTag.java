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
     * Obt�m decorator
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
            throw new JspTagException("Coluna sem tabela");
        }

        return (EVAL_BODY_BUFFERED);
    }

    /**
     * Finaliza��o da tag
     *
     * @return A��o da tag
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
     * Se for definido uma classe decorator, ser� feita uma inst�ncia da mesma e retornada.
     * 
     * @param columnDecoratorName String nome da classe do Decorator
     * 
     * @return inst�ncia de ColumnDecorator
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

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

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.tagext.TagSupport;

import sigesp.comum.util.bean.Bean;
import sigesp.comum.util.exception.FabricaBeansException;

/**
 * Classe Tag Handler para tabela
 */
public class ParametroTag extends TagSupport
{
    /** Nome da lista onde est�(�o) o(s) bean(s) */
    private String strLista = "";

    /** Nome da propriedade do bean a ser recuperada */
    private String strPropriedade = "";

    /** Nome do atributo que ser� colocado no contexto */
    private String strAtributo = "";

    /** Escopo de onde ser� obtido o valor */
    private String strContexto = "";

    /** Nome do par�metro a ser recuperado */
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
     * Obt�m configura��o de propriedade
     *
     * @return Conte�do de propriedade
     */
    public String getPropriedade()
    {
        return strPropriedade;
    }

    /**
     * Atribui configura��o de propriedade
     *
     * @param strLinha Conte�do de propriedade
     */
    public void setPropriedade(String strPropriedade)
    {
        this.strPropriedade = strPropriedade;
    }

    /**
     * Obt�m configura��o de atributo
     *
     * @return Conte�do de atributo
     */
    public String getAtributo()
    {
        return strAtributo;
    }

    /**
     * Atribui configura��o de atributo
     *
     * @param strBorda Conte�do de atributo
     */
    public void setAtributo(String strAtributo)
    {
        this.strAtributo = strAtributo;
    }

    /**
     * Obt�m contexto
     *
     * @return Conte�do de contexto
     */
    public String getContexto()
    {
        return strContexto;
    }

    /**
     * Atribui contexto
     *
     * @param strBorda Conte�do de contexto
     */
    public void setContexto(String strContexto)
    {
        this.strContexto = strContexto;
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
            String strResultado = "";

            if (strParametro != null && !"".equals(strParametro))
            {
                // Verifica qual em contexto est� o par�metro

                String strString = null;
                if ("request".equalsIgnoreCase(strContexto) && pageContext.getRequest().getParameter(strParametro) != null)
                {
                    strString = (String) pageContext.getRequest().getParameter(strParametro);
                }
                else if ("session".equalsIgnoreCase(strContexto) && pageContext.getSession().getAttribute(strParametro) != null)
                {
                    strString = (String) pageContext.getSession().getAttribute(strParametro);
                }
                else if (pageContext.getAttribute(strParametro) != null)
                {
                    strString = (String) pageContext.getAttribute(strParametro);
                }

                strResultado += strString;
            }
            else
            {
                // Verifica qual contexto est� a lista

                List lstLista = null;
                if ("request".equalsIgnoreCase(strContexto) && pageContext.getRequest().getAttribute(strLista) != null)
                {
                    lstLista = (List) pageContext.getRequest().getAttribute(strLista);
                }
                else if ("session".equalsIgnoreCase(strContexto) && pageContext.getSession().getAttribute(strLista) != null)
                {
                    lstLista = (List) pageContext.getSession().getAttribute(strLista);
                }
                else if (pageContext.getAttribute(strLista) != null)
                {
                    lstLista = (List) pageContext.getAttribute(strLista);
                }

                // Itera lista

                Iterator itrLista = lstLista.iterator();

                while (itrLista.hasNext())
                {
                    Bean beaLista = new Bean(itrLista.next());
                    strResultado += beaLista.obterPropriedade(strPropriedade) + (itrLista.hasNext() ? ";" : "");
                }

            }

            // Armazena no contexto desejado

            if ("request".equals(strContexto))
            {
                pageContext.getRequest().setAttribute(strAtributo, strResultado);
            }
            if ("session".equals(strContexto))
            {
                pageContext.getSession().setAttribute(strAtributo, strResultado);
            }
            if ("pageContext".equals(strContexto))
            {
                pageContext.setAttribute(strAtributo, strResultado);
            }
        }
        catch (FabricaBeansException fbe)
        {}

        return (EVAL_PAGE);
    }

    /**
     * @return Returns the strParametro.
     */
    public String getParametro()
    {
        return this.strParametro;
    }

    /**
     * @param strParametro The strParametro to set.
     */
    public void setParametro(String strParametro)
    {
        this.strParametro = strParametro;
    }
}

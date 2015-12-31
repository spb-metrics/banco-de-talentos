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
    /** Nome da lista onde está(ão) o(s) bean(s) */
    private String strLista = "";

    /** Nome da propriedade do bean a ser recuperada */
    private String strPropriedade = "";

    /** Nome do atributo que será colocado no contexto */
    private String strAtributo = "";

    /** Escopo de onde será obtido o valor */
    private String strContexto = "";

    /** Nome do parâmetro a ser recuperado */
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
     * Obtém configuração de propriedade
     *
     * @return Conteúdo de propriedade
     */
    public String getPropriedade()
    {
        return strPropriedade;
    }

    /**
     * Atribui configuração de propriedade
     *
     * @param strLinha Conteúdo de propriedade
     */
    public void setPropriedade(String strPropriedade)
    {
        this.strPropriedade = strPropriedade;
    }

    /**
     * Obtém configuração de atributo
     *
     * @return Conteúdo de atributo
     */
    public String getAtributo()
    {
        return strAtributo;
    }

    /**
     * Atribui configuração de atributo
     *
     * @param strBorda Conteúdo de atributo
     */
    public void setAtributo(String strAtributo)
    {
        this.strAtributo = strAtributo;
    }

    /**
     * Obtém contexto
     *
     * @return Conteúdo de contexto
     */
    public String getContexto()
    {
        return strContexto;
    }

    /**
     * Atribui contexto
     *
     * @param strBorda Conteúdo de contexto
     */
    public void setContexto(String strContexto)
    {
        this.strContexto = strContexto;
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
            String strResultado = "";

            if (strParametro != null && !"".equals(strParametro))
            {
                // Verifica qual em contexto está o parâmetro

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
                // Verifica qual contexto está a lista

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

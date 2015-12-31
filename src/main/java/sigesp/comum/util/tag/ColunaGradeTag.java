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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import sigesp.comum.util.bean.Bean;
import sigesp.comum.util.exception.FabricaBeansException;

/**
 * Classe Tag Handler para colunas da grade
 */
public class ColunaGradeTag extends TagSupport
{
    private String strLargura = "";
    private String strTitulo = "";
    private String strPropriedade = "";
    private String strAlinhamento = "";
    private String strValor = "";
    private String strParametroID = "";
    private String strParametroNome = "";
    private String strHref = "";

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
     * @param strLargura Conteúdo de largura
     */
    public void setLargura(String strLargura)
    {
        this.strLargura = strLargura;
    }

    /**
     * Obtém título
     *
     * @return Conteúdo do título
     */
    public String getTitulo()
    {
        return strTitulo;
    }

    /**
     * Atribui título
     *
     * @param strTitulo Conteúdo do título
     */
    public void setTitulo(String strTitulo)
    {
        this.strTitulo = strTitulo;
    }

    /**
     * Obtém propriedade
     *
     * @return Conteúdo de proprieadade
     */
    public String getPropriedade()
    {
        return strPropriedade;
    }

    /**
     * Atribui propriedade
     *
     * @param strPropriedade Conteúdo de propriedade
     */
    public void setPropriedade(String strPropriedade)
    {
        this.strPropriedade = strPropriedade;
    }

    /**
     * Obtém valor
     *
     * @return Conteúdo de valor
     */
    public String getValor()
    {
        return strValor;
    }

    /**
     * Atribui valor
     *
     * @param strValor Conteúdo de valor
     */
    public void setValor(String strValor)
    {
        this.strValor = strValor;
    }

    /**
     * Obtém identificador do parâmetro
     *
     * @return Conteúdo do identificador do parâmetro
     */
    public String getParametroID()
    {
        return strParametroID;
    }

    /**
     * Atribui identificador do parâmetro
     *
     * @param strParametroID Conteúdo do identificador do parâmetro
     */
    public void setParametroID(String strParametroID)
    {
        this.strParametroID = strParametroID;
    }

    /**
     * Obtém nome do parâmetro
     *
     * @return Conteúdo do nome do parâmetro
     */
    public String getParametroNome()
    {
        return strParametroNome;
    }

    /**
     * Atribui nome do parâmetro
     *
     * @param strParametroNome Conteúdo do nome do parâmetro
     */
    public void setParametroNome(String strParametroNome)
    {
        this.strParametroNome = strParametroNome;
    }

    /**
     * Obtém href
     *
     * @return Conteúdo do href
     */
    public String getHref()
    {
        return strHref;
    }

    /**
     * Atribui href
     *
     * @param strHref Conteúdo do href
     */
    public void setHref(String strHref)
    {
        this.strHref = strHref;
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
        GradeTag parent = (GradeTag) findAncestorWithClass(this, GradeTag.class);
        if (parent == null)
        {
            throw new JspTagException("Coluna sem grade");
        }

        // Declarações
        List lstTabela = null;
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpSession session = request.getSession();

        // Verifica pageContext
        if (pageContext.getAttribute(parent.getLista()) != null)
        {
            lstTabela = (ArrayList) pageContext.getAttribute(parent.getLista());
        }

        // Verifica session
        if (session.getAttribute(parent.getLista()) != null)
        {
            lstTabela = (ArrayList) session.getAttribute(parent.getLista());
        }

        // Verifica request
        if (request.getAttribute(parent.getLista()) != null)
        {
            lstTabela = (ArrayList) request.getAttribute(parent.getLista());
        }

        Iterator itrTabela = lstTabela.iterator();

        // Obtém configuração de títulos
        ArrayList arlTitulo = parent.getTitulo();

        // Obtém configuração de larguras
        ArrayList arlLargura = parent.getLarguraColuna();

        // Obtém configuração de colunas
        ArrayList arlConteudo = parent.getConteudo();

        // Cria configuração de linhas            
        ArrayList arlColuna = new ArrayList();

        // Adiciona título
        arlTitulo.add(strTitulo);

        // Adiciona título
        arlLargura.add(strLargura);

        // Itera beans e adiciona ao conteúdo
        while (itrTabela.hasNext())
        {
            try
            {
                if (!"".equals(strPropriedade))
                {
                    Bean beaTabela = new Bean(itrTabela.next());
                    arlColuna.add(beaTabela.obterPropriedade(strPropriedade));
                }
                else
                {
                    Bean beaTabela = new Bean(itrTabela.next());
                    arlColuna.add(
                        "<a href=\""
                            + strHref
                            + "?"
                            + strParametroID
                            + "="
                            + beaTabela.obterPropriedade(strParametroNome)
                            + "\">"
                            + strValor
                            + "</a>");
                }
            }
            catch (FabricaBeansException fbe)
            {
            }
        }

        // Armazena coluna
        arlConteudo.add(arlColuna);

        // Retorno
        return (SKIP_BODY);
    }
}

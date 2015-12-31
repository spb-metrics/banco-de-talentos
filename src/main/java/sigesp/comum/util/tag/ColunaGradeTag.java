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
     * @param strLargura Conte�do de largura
     */
    public void setLargura(String strLargura)
    {
        this.strLargura = strLargura;
    }

    /**
     * Obt�m t�tulo
     *
     * @return Conte�do do t�tulo
     */
    public String getTitulo()
    {
        return strTitulo;
    }

    /**
     * Atribui t�tulo
     *
     * @param strTitulo Conte�do do t�tulo
     */
    public void setTitulo(String strTitulo)
    {
        this.strTitulo = strTitulo;
    }

    /**
     * Obt�m propriedade
     *
     * @return Conte�do de proprieadade
     */
    public String getPropriedade()
    {
        return strPropriedade;
    }

    /**
     * Atribui propriedade
     *
     * @param strPropriedade Conte�do de propriedade
     */
    public void setPropriedade(String strPropriedade)
    {
        this.strPropriedade = strPropriedade;
    }

    /**
     * Obt�m valor
     *
     * @return Conte�do de valor
     */
    public String getValor()
    {
        return strValor;
    }

    /**
     * Atribui valor
     *
     * @param strValor Conte�do de valor
     */
    public void setValor(String strValor)
    {
        this.strValor = strValor;
    }

    /**
     * Obt�m identificador do par�metro
     *
     * @return Conte�do do identificador do par�metro
     */
    public String getParametroID()
    {
        return strParametroID;
    }

    /**
     * Atribui identificador do par�metro
     *
     * @param strParametroID Conte�do do identificador do par�metro
     */
    public void setParametroID(String strParametroID)
    {
        this.strParametroID = strParametroID;
    }

    /**
     * Obt�m nome do par�metro
     *
     * @return Conte�do do nome do par�metro
     */
    public String getParametroNome()
    {
        return strParametroNome;
    }

    /**
     * Atribui nome do par�metro
     *
     * @param strParametroNome Conte�do do nome do par�metro
     */
    public void setParametroNome(String strParametroNome)
    {
        this.strParametroNome = strParametroNome;
    }

    /**
     * Obt�m href
     *
     * @return Conte�do do href
     */
    public String getHref()
    {
        return strHref;
    }

    /**
     * Atribui href
     *
     * @param strHref Conte�do do href
     */
    public void setHref(String strHref)
    {
        this.strHref = strHref;
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
        GradeTag parent = (GradeTag) findAncestorWithClass(this, GradeTag.class);
        if (parent == null)
        {
            throw new JspTagException("Coluna sem grade");
        }

        // Declara��es
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

        // Obt�m configura��o de t�tulos
        ArrayList arlTitulo = parent.getTitulo();

        // Obt�m configura��o de larguras
        ArrayList arlLargura = parent.getLarguraColuna();

        // Obt�m configura��o de colunas
        ArrayList arlConteudo = parent.getConteudo();

        // Cria configura��o de linhas            
        ArrayList arlColuna = new ArrayList();

        // Adiciona t�tulo
        arlTitulo.add(strTitulo);

        // Adiciona t�tulo
        arlLargura.add(strLargura);

        // Itera beans e adiciona ao conte�do
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

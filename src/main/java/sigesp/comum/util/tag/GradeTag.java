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
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Classe Tag Handler para grade
 */
public class GradeTag extends TagSupport
{
    private String strLinha = "impar";
    private String strBorda = "0";
    private String strLargura = "";
    private String strLista = "";
    private String strQtdeMaxima = "0";
    private String strPagina = "";
    private int intPagina = 0;
    private int intTotalPaginas = 1;
    private ArrayList arlConteudo = new ArrayList();
    private ArrayList arlTitulo = new ArrayList();
    private ArrayList arlLarguraColuna = new ArrayList();

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
     * @param strLargura Conteúdo de largura
     */
    public void setLargura(String strLargura)
    {
        this.strLargura = strLargura;
    }

    /**
     * Obtém lista
     *
     * @return Lista
     */
    public String getLista()
    {
        return strLista;
    }

    /**
     * Atribui lista
     *
     * @param strLista Lista
     */
    public void setLista(String strLista)
    {
        this.strLista = strLista;
    }

    /**
     * Obtém configuração de quantidade de registros por página
     *
     * @return Conteúdo de quantidade de registros por página
     */
    public String getQtdeMaxima()
    {
        return strQtdeMaxima;
    }

    /**
     * Atribui configuração de quantidade de registros por página
     *
     * @param strQtdeMaxima Conteúdo de quantidade de registros por página
     */
    public void setQtdeMaxima(String strQtdeMaxima)
    {
        if (Integer.parseInt(strQtdeMaxima) < 0)
        {
            throw new RuntimeException("A quantidade máxima não pode ser negativa");
        }
        this.strQtdeMaxima = strQtdeMaxima;
    }

    /**
     * Obtém nome da página
     *
     * @return Conteúdo do nome da página
     */
    public String getPagina()
    {
        return strPagina;
    }

    /**
     * Atribui nome da página
     *
     * @param strPagina Conteúdo do nome da página
     */
    public void setPagina(String strPagina)
    {
        if ("".equals(strPagina))
        {
            throw new RuntimeException("A página não pode ser vazia");
        }

        String[] strURL = strPagina.split("?");
        this.strPagina = strURL[0] + "?";
        if (strURL.length == 2)
        {
            String[] strParam = strURL[1].split("&");
            for (int i = 0; i < strParam.length; i++)
            {
                String[] strValor = strParam[i].split("=");
                if ((strValor.length == 2) && !strValor[0].equals("pagina"))
                {
                    this.strPagina += (strValor[0] + "=" + strValor[1] + "&");
                }
            }
        }
    }

    /**
     * Obtém conteúdo a ser exibido
     *
     * @return Conteúdo a ser exibido
     */
    public ArrayList getConteudo()
    {
        return arlConteudo;
    }

    /**
     * Atribui conteúdo a ser exibido
     *
     * @param strConteudo Conteúdo a ser exibido
     */
    public void setConteudo(ArrayList arlConteudo)
    {
        this.arlConteudo = arlConteudo;
    }

    /**
     * Obtém títulos a serem exibidos
     *
     * @return Conteúdo dos títulos a serem exibidos
     */
    public ArrayList getTitulo()
    {
        return arlTitulo;
    }

    /**
     * Atribui títulos a serem exibidos
     *
     * @param strTitulo Conteúdo dos títulos a serem exibidos
     */
    public void setTitulo(ArrayList arlTitulo)
    {
        this.arlTitulo = arlTitulo;
    }

    /**
     * Obtém larguras de coluna
     *
     * @return Conteúdo das larguras de coluna
     */
    public ArrayList getLarguraColuna()
    {
        return arlLarguraColuna;
    }

    /**
     * Atribui larguras de coluna
     *
     * @param LarguraColuna Conteúdo das larguras de coluna
     */
    public void setLarguraColuna(ArrayList arlLarguraColuna)
    {
        this.arlLarguraColuna = arlLarguraColuna;
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
            out.print("<table class=\"table\" cellspacing=\"0\" cellpadding=\"4\" bordercolor=\"#FFFFFF\" "
                + "bordercolorlight=\"#8D8D8D\" border=\" " + strBorda + "\" "
                + ("".equals(strLargura) ? "" : (" width=\"" + strLargura + "\"")) + ">");
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

            // Obtém colunas e linhas para impressão
            Iterator itrTitulo = arlTitulo.iterator();
            String[][] strTitulo = null;
            Iterator itrLarguraColuna = arlLarguraColuna.iterator();
            String[][] strLarguraColuna = null;
            Iterator itrConteudo = arlConteudo.iterator();
            String[][] strGrade = null;
            int intLinha = 0;
            int intColuna = 0;

            while (itrTitulo.hasNext())
            {
                // Verifica se já foi instanciado o array
                if (strTitulo == null)
                {
                    strTitulo = new String[1][arlTitulo.size()];
                }

                // Obtém valores dos títulos
                strTitulo[0][intColuna++] = (String) itrTitulo.next();
            }

            intColuna = 0;
            while (itrLarguraColuna.hasNext())
            {
                // Verifica se já foi instanciado o array
                if (strLarguraColuna == null)
                {
                    strLarguraColuna = new String[1][arlLarguraColuna.size()];
                }

                // Obtém valores dos títulos
                strLarguraColuna[0][intColuna++] = (String) itrLarguraColuna.next();
            }

            while (itrConteudo.hasNext())
            {
                // Obtém valores das colunas
                ArrayList arlColuna = (ArrayList) itrConteudo.next();
                Iterator itrColuna = arlColuna.iterator();
                int intColunaConteudo = 0;

                // Verifica se já foi instanciado o array
                if (strGrade == null)
                {
                    strGrade = new String[arlConteudo.size()][arlColuna.size()];
                }

                // Alimenta array
                while (itrColuna.hasNext())
                {
                    strGrade[intLinha][intColunaConteudo++] = itrColuna.next().toString();
                }

                // Incrementa linha
                intLinha++;
            }

            // Troca posição dos arrays
            String[][] strTemp = new String[strGrade[0].length][strGrade.length];
            for (int i = 0; i < strGrade.length; i++)
            {
                for (int j = 0; j < strGrade[i].length; j++)
                {
                    strTemp[j][i] = strGrade[i][j];
                }
            }
            strGrade = strTemp;

            // Verifica quantidade de registros para exibição
            if (Integer.parseInt(strQtdeMaxima) == 0)
            {
                strQtdeMaxima = String.valueOf(strGrade.length);
            }

            // Faz contagem de páginas
            if ((Integer.parseInt(strQtdeMaxima) > 0) && (pageContext.getRequest().getParameter("pagina") == null))
            {
                intTotalPaginas = strGrade.length / Integer.parseInt(strQtdeMaxima);
                if ((strGrade.length % Integer.parseInt(strQtdeMaxima)) > 0)
                {
                    intTotalPaginas++;
                }
                intPagina = 1;
            }

            // Seta página atual
            if (pageContext.getRequest().getParameter("pagina") != null)
            {
                intPagina = Integer.parseInt(pageContext.getRequest().getParameter("pagina"));
            }

            // Monta navegador de páginas
            String strNavegador = "";
            if (intTotalPaginas > 1)
            {
                String strAnterior = ((intPagina > 1)
                    ? ("<a href=\"" + strPagina + "pagina=" + (intPagina - 1) + "\"><<</a>") : "");
                String strProximo = ((intPagina < intTotalPaginas)
                    ? ("<a href=\"" + strPagina + "pagina=" + (intPagina + 1) + "\">>></a>") : "");
                strNavegador = " " + strAnterior + intPagina + " de " + intTotalPaginas + strProximo;
            }

            // Cria cabeçalho da tabela
            for (int i = 0; i < strTitulo.length; i++)
            {
                out.print("<tr class=\"tableRowHeader\">");

                for (int j = 0; j < strTitulo[i].length; j++)
                {
                    out.print("<td class=\"tableCell\" "
                        + ("".equals(strLarguraColuna[i][j]) ? "" : (" width=\"" + strLarguraColuna[i][j] + "\""))
                        + ">" + strTitulo[i][j] + "</td>");
                }
                out.print("</tr>");
            }

            // Cria linhas e colunas da tabela
            for (int i = (Integer.parseInt(strQtdeMaxima) * intPagina) - Integer.parseInt(strQtdeMaxima);
                        i < (Integer.parseInt(strQtdeMaxima) * intPagina); i++)
            {
                if (i < strGrade.length)
                {
                    if ("impar".equals(getLinha()))
                    {
                        out.print("<tr class=\"tableRowOdd\">");
                        setLinha("par");
                    }
                    else
                    {
                        out.print("<tr class=\"tableRowEven\">");
                        setLinha("impar");
                    }
                    for (int j = 0; j < strGrade[i].length; j++)
                    {
                        out.print("<td class=\"tableCell\">" + strGrade[i][j] + "</td>");
                    }
                }
                out.println("</tr>");
            }

            // Verifica se existe navegador
            if (!"".equals(strNavegador))
            {
                out.print("<tr class=\"tableRowHeader\"><td class=\"tableCell\" colspan=\"" + strGrade[0].length
                    + "\" align=\"right\">" + strNavegador + "</td></tr>");
            }
            out.print("</table>");

            // Limpa conteúdo
            strLinha = "impar";
            arlTitulo.clear();
            arlLarguraColuna.clear();
            arlConteudo.clear();
        }
        catch (IOException ioe)
        {
        }

        return (EVAL_PAGE);
    }
}

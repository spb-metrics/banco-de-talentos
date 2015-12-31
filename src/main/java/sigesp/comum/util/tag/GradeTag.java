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
     * Obt�m configura��o de linha
     *
     * @return Conte�do de linha
     */
    public String getLinha()
    {
        return strLinha;
    }

    /**
     * Atribui configura��o de linha
     *
     * @param strLinha Conte�do de linha
     */
    public void setLinha(String strLinha)
    {
        this.strLinha = strLinha;
    }

    /**
     * Obt�m configura��o de borda
     *
     * @return Conte�do de borda
     */
    public String getBorda()
    {
        return strBorda;
    }

    /**
     * Atribui configura��o de borda
     *
     * @param strBorda Conte�do de borda
     */
    public void setBorda(String strBorda)
    {
        this.strBorda = strBorda;
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
     * @param strLargura Conte�do de largura
     */
    public void setLargura(String strLargura)
    {
        this.strLargura = strLargura;
    }

    /**
     * Obt�m lista
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
     * Obt�m configura��o de quantidade de registros por p�gina
     *
     * @return Conte�do de quantidade de registros por p�gina
     */
    public String getQtdeMaxima()
    {
        return strQtdeMaxima;
    }

    /**
     * Atribui configura��o de quantidade de registros por p�gina
     *
     * @param strQtdeMaxima Conte�do de quantidade de registros por p�gina
     */
    public void setQtdeMaxima(String strQtdeMaxima)
    {
        if (Integer.parseInt(strQtdeMaxima) < 0)
        {
            throw new RuntimeException("A quantidade m�xima n�o pode ser negativa");
        }
        this.strQtdeMaxima = strQtdeMaxima;
    }

    /**
     * Obt�m nome da p�gina
     *
     * @return Conte�do do nome da p�gina
     */
    public String getPagina()
    {
        return strPagina;
    }

    /**
     * Atribui nome da p�gina
     *
     * @param strPagina Conte�do do nome da p�gina
     */
    public void setPagina(String strPagina)
    {
        if ("".equals(strPagina))
        {
            throw new RuntimeException("A p�gina n�o pode ser vazia");
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
     * Obt�m conte�do a ser exibido
     *
     * @return Conte�do a ser exibido
     */
    public ArrayList getConteudo()
    {
        return arlConteudo;
    }

    /**
     * Atribui conte�do a ser exibido
     *
     * @param strConteudo Conte�do a ser exibido
     */
    public void setConteudo(ArrayList arlConteudo)
    {
        this.arlConteudo = arlConteudo;
    }

    /**
     * Obt�m t�tulos a serem exibidos
     *
     * @return Conte�do dos t�tulos a serem exibidos
     */
    public ArrayList getTitulo()
    {
        return arlTitulo;
    }

    /**
     * Atribui t�tulos a serem exibidos
     *
     * @param strTitulo Conte�do dos t�tulos a serem exibidos
     */
    public void setTitulo(ArrayList arlTitulo)
    {
        this.arlTitulo = arlTitulo;
    }

    /**
     * Obt�m larguras de coluna
     *
     * @return Conte�do das larguras de coluna
     */
    public ArrayList getLarguraColuna()
    {
        return arlLarguraColuna;
    }

    /**
     * Atribui larguras de coluna
     *
     * @param LarguraColuna Conte�do das larguras de coluna
     */
    public void setLarguraColuna(ArrayList arlLarguraColuna)
    {
        this.arlLarguraColuna = arlLarguraColuna;
    }

    /**
     * Inicializa��o da tag
     *
     * @return A��o da tag
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
     * Finaliza��o da tag
     *
     * @return A��o da tag
     */
    public int doEndTag()
    {
        try
        {
            JspWriter out = pageContext.getOut();

            // Obt�m colunas e linhas para impress�o
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
                // Verifica se j� foi instanciado o array
                if (strTitulo == null)
                {
                    strTitulo = new String[1][arlTitulo.size()];
                }

                // Obt�m valores dos t�tulos
                strTitulo[0][intColuna++] = (String) itrTitulo.next();
            }

            intColuna = 0;
            while (itrLarguraColuna.hasNext())
            {
                // Verifica se j� foi instanciado o array
                if (strLarguraColuna == null)
                {
                    strLarguraColuna = new String[1][arlLarguraColuna.size()];
                }

                // Obt�m valores dos t�tulos
                strLarguraColuna[0][intColuna++] = (String) itrLarguraColuna.next();
            }

            while (itrConteudo.hasNext())
            {
                // Obt�m valores das colunas
                ArrayList arlColuna = (ArrayList) itrConteudo.next();
                Iterator itrColuna = arlColuna.iterator();
                int intColunaConteudo = 0;

                // Verifica se j� foi instanciado o array
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

            // Troca posi��o dos arrays
            String[][] strTemp = new String[strGrade[0].length][strGrade.length];
            for (int i = 0; i < strGrade.length; i++)
            {
                for (int j = 0; j < strGrade[i].length; j++)
                {
                    strTemp[j][i] = strGrade[i][j];
                }
            }
            strGrade = strTemp;

            // Verifica quantidade de registros para exibi��o
            if (Integer.parseInt(strQtdeMaxima) == 0)
            {
                strQtdeMaxima = String.valueOf(strGrade.length);
            }

            // Faz contagem de p�ginas
            if ((Integer.parseInt(strQtdeMaxima) > 0) && (pageContext.getRequest().getParameter("pagina") == null))
            {
                intTotalPaginas = strGrade.length / Integer.parseInt(strQtdeMaxima);
                if ((strGrade.length % Integer.parseInt(strQtdeMaxima)) > 0)
                {
                    intTotalPaginas++;
                }
                intPagina = 1;
            }

            // Seta p�gina atual
            if (pageContext.getRequest().getParameter("pagina") != null)
            {
                intPagina = Integer.parseInt(pageContext.getRequest().getParameter("pagina"));
            }

            // Monta navegador de p�ginas
            String strNavegador = "";
            if (intTotalPaginas > 1)
            {
                String strAnterior = ((intPagina > 1)
                    ? ("<a href=\"" + strPagina + "pagina=" + (intPagina - 1) + "\"><<</a>") : "");
                String strProximo = ((intPagina < intTotalPaginas)
                    ? ("<a href=\"" + strPagina + "pagina=" + (intPagina + 1) + "\">>></a>") : "");
                strNavegador = " " + strAnterior + intPagina + " de " + intTotalPaginas + strProximo;
            }

            // Cria cabe�alho da tabela
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

            // Limpa conte�do
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

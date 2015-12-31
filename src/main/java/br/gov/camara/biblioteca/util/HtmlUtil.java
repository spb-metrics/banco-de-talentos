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

package br.gov.camara.biblioteca.util;

/**
 * Classe responsável por retornar campos no formatdo HTML
 *
 * @author P_999326
 *
 */
public class HtmlUtil
{
    /**
     * Substitui a quebra de linha do Java "\n" por "<BR>" que é quebra de linha do HTML
     * Substitui os primeiros espaços em branco da linha por "&nbsp;"
     * Dessa forma o texto não distorce no formato HTML
     *
     * @param texto 
     *
     * @return texto formatado em HTML
     */
    public static String formatarTextoEmHtml(String texto)
    {
        String strRetorno = "";

        if (texto == null)
        {
            //pode ser q o Obj String esteja null
            texto = "";
        }

        if (texto.indexOf("\n") > 0)
        {
            // substituindo o sinal de  > por uma tag(html) &gt;
            texto = texto.replaceAll(">", "&gt;");

            // substituindo o sinal de  < por uma tag(html) &lt;
            texto = texto.replaceAll("<", "&lt;");

            // substituindo a quebra de linha do Java por uma tag <BR>
            texto = texto.replaceAll("\r\n", "<BR>");

            //recuperando as linhas do texto
            String linhasTexto[] = texto.split("<BR>");

            //percorrendo todas as linhas
            for (int i = 0; i < linhasTexto.length; i++)
            {
                String linha = linhasTexto[i];

                //tranformando a linha em um array de caracter
                String espacao[] = linha.split(" ");

                String aux = "";

                //percorrendo os caracteres da linha
                for (int j = 0; j < espacao.length; j++)
                {
                    String caracter = espacao[j];

                    //enquanto os primeiros caracter for espaço em branco coloca-se "&nbsp;" 
                    if (caracter.equals(""))
                    {
                        aux += "&nbsp;";
                    }
                    else
                    {
                        break;
                    }

                }

                strRetorno += aux + linha + "<BR>";
            }
        }
        else
        {
            strRetorno = texto;
        }
        return strRetorno;
    }
}

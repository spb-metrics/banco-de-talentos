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

package br.gov.camara.biblioteca.util;

/**
 * Classe respons�vel por retornar campos no formatdo HTML
 *
 * @author P_999326
 *
 */
public class HtmlUtil
{
    /**
     * Substitui a quebra de linha do Java "\n" por "<BR>" que � quebra de linha do HTML
     * Substitui os primeiros espa�os em branco da linha por "&nbsp;"
     * Dessa forma o texto n�o distorce no formato HTML
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

                    //enquanto os primeiros caracter for espa�o em branco coloca-se "&nbsp;" 
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

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

/*
 * Created on 04/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.gov.camara.biblioteca.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * @author p_999126 To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Moeda
{
    /**
     * 
     */
    private Moeda()
    {}

    private static String SIMBOLO_BRASIL_REAIS = "R$ ";

    public static String formatarMoeda(double dblValor)
    {
        return formatarMoeda(dblValor, SIMBOLO_BRASIL_REAIS);
    }

    public static String formatarNumero(double dblValor)
    {

        // Declara o retorno
        String strRetorno = "";

        // Declara o Locale para o Brasil
        Locale locale = new Locale("pt", "br");

        // Obtem o formatador de valores monet�rios
        NumberFormat nf = NumberFormat.getNumberInstance(locale);

        // Formata o valor
        strRetorno = nf.format(dblValor);

        return strRetorno;
    }

    public static String formatarMoeda(double dblValor, String strSimbolo)
    {

        // Declara o retorno
        String strRetorno = "";

        // Formata o valor
        strRetorno = formatarNumero(dblValor);

        int intPosicaoVirgula = strRetorno.indexOf(",");
        if (intPosicaoVirgula == -1)
        {
            strRetorno = strRetorno + ",00";
        }
        else if (strRetorno.length() - intPosicaoVirgula == 1)
        {
            strRetorno = strRetorno + "00";
        }
        else if (strRetorno.length() - intPosicaoVirgula == 2)
        {
            strRetorno = strRetorno + "0";
        }

        strRetorno = strSimbolo + strRetorno;

        return strRetorno;
    }

    public static String retornarValorSemSimbolo(String strMoeda)
    {

        // Declara o Locale para o Brasil
        Locale locale = new Locale("pt", "br");
        StringBuffer stbMoedaSemSimbolo = new StringBuffer();

        for (int i = 0; i < strMoeda.length(); i++)
        {
            if (strMoeda.charAt(i) == '-')
            {
                stbMoedaSemSimbolo.append(strMoeda.charAt(i));
            }
            else if (strMoeda.charAt(i) == '.')
            {
                stbMoedaSemSimbolo.append(strMoeda.charAt(i));
            }
            else if (strMoeda.charAt(i) == ',')
            {
                stbMoedaSemSimbolo.append(strMoeda.charAt(i));
            }
            else if (strMoeda.charAt(i) >= '0' && strMoeda.charAt(i) <= '9')
            {
                stbMoedaSemSimbolo.append(strMoeda.charAt(i));
            }
        }

        // Obtem o formatador de valores monet�rios
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        Number numRetorno = null;
        try
        {
            numRetorno = nf.parse(stbMoedaSemSimbolo.toString());
        }
        catch (ParseException pe)
        {
            // Erro...
        }

        return numRetorno.toString();
    }

    public static String formatarMoedaPadraoAmericano(String strValor)
    {
        return retornarValorSemSimbolo(strValor);
    }

    /**
     * @param valorReferencia
     * @return
     */
    public static boolean validarValor(String valorReferencia)
    {
        try
        {
            new Double(valorReferencia);
            return true;
        }
        catch (RuntimeException rtme)
        {
            return false;
        }
    }
}

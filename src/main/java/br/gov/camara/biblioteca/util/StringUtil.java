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

/**
 * 
 */
package br.gov.camara.biblioteca.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author P_6414
 *
 */
public class StringUtil
{
    /**
    * Logger for this class
    */
    private static final Log log = LogFactory.getLog(StringUtil.class);

    public static String obterSubstring(String strString, String strParametro, String strDelimitadorInicial, String strDelimitadorFinal)
    {
        String strRetorno = null;

        // A string informada cont�m a substring informada (par�metro) ? 
        int posicao = strString.indexOf(strParametro);
        if (posicao > -1)
        {
            // Recupera o conte�do da string da posi��o final da substring
            posicao += strParametro.length();
            String strSubstring = strString.substring(posicao);

            // Recupera a partir do delimitador inicial, caso exista...
            posicao = strSubstring.indexOf(strDelimitadorInicial);
            if (posicao > -1)
            {
                strSubstring = strSubstring.substring(posicao + 1);

                // Verifica a exist�ncia do delimitador final
                posicao = strSubstring.indexOf(strDelimitadorFinal);
                if (posicao > -1)
                {
                    strRetorno = strSubstring.substring(0, posicao);
                }
                else
                {
                    strRetorno = strSubstring;
                }

            }

        }

        return strRetorno;
    }

    public static String obterParametro(String strString, String strParametro)
    {
        return obterParametro(strString, strParametro, "&");
    }

    public static String obterParametro(String strString, String strParametro, String strDelimitador)
    {
        return obterSubstring(strString, strParametro, "=", strDelimitador);
    }

    /**
     * Fun��o que substitui numa string uma express�o por outra
     *
     * @param strStringOriginal String contendo a express�o a ser substitu�da
     * @param strExpressaoExistente Express�o a ser substitu�da
     * @param strNovaExpressao Nova express�o
     *
     * @return Retorna uma nova string com contendo a express�o substitu�da
     */
    public static String substituirExpressao(String strStringOriginal, String strExpressaoExistente, String strNovaExpressao)
    {
        // Registra log
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no m�todo");
        }

        int intPosicao = 0;
        StringBuffer stbNovaString = new StringBuffer(strStringOriginal);

        while ((intPosicao = stbNovaString.indexOf(strExpressaoExistente, intPosicao)) >= 0)
        {
            stbNovaString.replace(intPosicao, intPosicao + strExpressaoExistente.length(), strNovaExpressao);
            intPosicao = intPosicao + strNovaExpressao.length();
        }

        return stbNovaString.toString();
    }

    /**
     * Preenche uma string entrada com caracter informado, � esquerda ou � direita
     * 
     * @author P_6478 
     * 
     * @param strInput: string a ser preenchida
     * @param strCaracter: string de preenchimento
     * @param intTamanhoOutput: tamanho da string a ser retornada 
     * @param intDirecao: 0 - esquerda / 1 - direita
     */

    public static String preencherComCaracter(String strInput, String strCaracter, int intTamanhoOutput, int intDirecao)
    {
        String strOutPut = "";

        for (strOutPut = strInput; strOutPut.length() < intTamanhoOutput;)
        {
            if (intDirecao == 0)
            {
                strOutPut = strCaracter + strOutPut;
            }
            else
            {
                strOutPut = strOutPut + strCaracter;
            }
        }
        return strOutPut;
    }

    /**
     * @param strNumOrigem
     * @param intBaseOrigem
     * @param intBaseDestino
     * @return
     */
    public static String converterEntreBases(String strNumOrigem, int intBaseOrigem, int intBaseDestino)
    {
        if (log.isDebugEnabled())
        {
            log.debug("converteEntreBases(String strNumOrigem="
                    + strNumOrigem
                    + ", int intBaseOrigem="
                    + intBaseOrigem
                    + ", int intBaseDestino="
                    + intBaseDestino
                    + ") - start");
        }

        int vintContador;
        Long vTamanhoOrigem;
        Long vTamanhoDestino;

        String pnuDestino;

        Long vOrigemDecimal;
        String vDestinoString;

        Long vintPotencia;

        String vDigitos = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        /*
         * If Len(strNumOrigem) = 0 Or intBaseOrigem = 0 Or intBaseDestino = 0 Then Exit Function 'Erro na passagem dos
         * parametros para pr4ConverteEntreBases.'; End If
         */
        if (strNumOrigem.length() == 0 || intBaseOrigem == 0 || intBaseDestino == 0)
        {
            if (log.isDebugEnabled())
            {
                log.debug("converteEntreBases(String strNumOrigem="
                        + strNumOrigem
                        + ", int intBaseOrigem="
                        + intBaseOrigem
                        + ", int intBaseDestino="
                        + intBaseDestino
                        + ") - end - return value=");
            }
            return "";
        }

        /*
         * If intBaseOrigem < 2 Or intBaseOrigem > 35 Then Exit Function 'Erro: Base origem fora nos limites
         * (pr4ConverteEntreBases).'; End If
         */
        if (intBaseOrigem < 2 || intBaseOrigem > 35)
        {
            if (log.isDebugEnabled())
            {
                log.debug("converteEntreBases(String strNumOrigem="
                        + strNumOrigem
                        + ", int intBaseOrigem="
                        + intBaseOrigem
                        + ", int intBaseDestino="
                        + intBaseDestino
                        + ") - end - return value=");
            }
            return "";
        }

        /*
         * If intBaseDestino < 2 Or intBaseDestino > 35 Then Exit Function 'Erro: Base destino fora nos limites
         * (pr4ConverteEntreBases).'; End If
         */
        if (intBaseDestino < 2 || intBaseDestino > 35)
        {
            if (log.isDebugEnabled())
            {
                log.debug("converteEntreBases(String strNumOrigem="
                        + strNumOrigem
                        + ", int intBaseOrigem="
                        + intBaseOrigem
                        + ", int intBaseDestino="
                        + intBaseDestino
                        + ") - end - return value=");
            }
            return "";
        }

        strNumOrigem = strNumOrigem.toUpperCase();
        vTamanhoOrigem = new Long(strNumOrigem.length());

        /*
         * '// ---------------------------------------------------- '// Primeiro converto o strNumOrigem para base 10
         * (decimal) '// ----------------------------------------------------
         */

        vintPotencia = new Long(0);
        vOrigemDecimal = new Long(0);
        for (vintContador = vTamanhoOrigem.intValue() - 1; vintContador >= 0; vintContador--)
        {
            // vOrigemDecimal = vOrigemDecimal + ((InStr(1, vDigitos, Mid$(strNumOrigem, vintContador, 1)) - 1) *
            // (intBaseOrigem ^ vintPotencia))
            vOrigemDecimal = new Long(vOrigemDecimal.intValue()
                    + vDigitos.indexOf(strNumOrigem.substring(vintContador, vintContador + 1))
                    * new Double(Math.pow(intBaseOrigem, vintPotencia.intValue())).longValue());
            vintPotencia = new Long(vintPotencia.intValue() + 1);
        }

        /*
         * '// ---------------------------------------------------- '// Agora converto o vOrigemDecimal para a base
         * indicada '// ----------------------------------------------------
         */

        vDestinoString = "";
        while (vOrigemDecimal.intValue() >= intBaseDestino)
        {
            // vDestinoString = vDestinoString & Mid$(vDigitos, (vOrigemDecimal Mod intBaseDestino) + 1, 1)
            int restoBase = vOrigemDecimal.intValue() % intBaseDestino;
            vDestinoString = vDestinoString + vDigitos.substring(restoBase, restoBase + 1);
            vOrigemDecimal = new Long(vOrigemDecimal.intValue() / intBaseDestino);
        }

        if (vOrigemDecimal.intValue() > 0)
        {
            vDestinoString = vDestinoString + vDigitos.substring(vOrigemDecimal.intValue(), vOrigemDecimal.intValue() + 1);
        }

        /*
         * '// ------------------------------------------------------------------ '// Por �ltimo atribuo vDestinoString
         * para pnuDestino na ordem correta '// ------------------------------------------------------------------
         */

        vTamanhoDestino = new Long(vDestinoString.length());
        pnuDestino = "";
        for (vintContador = vTamanhoDestino.intValue(); vintContador >= 1; vintContador--)
        {
            pnuDestino = pnuDestino + vDestinoString.substring(vintContador - 1, vintContador);
        }

        if (log.isDebugEnabled())
        {
            log.debug("converteEntreBases(String strNumOrigem="
                    + strNumOrigem
                    + ", int intBaseOrigem="
                    + intBaseOrigem
                    + ", int intBaseDestino="
                    + intBaseDestino
                    + ") - end - return value="
                    + pnuDestino);
        }
        return pnuDestino;

    }

    /**
     * Obt�m propriedade do arquivo de acordo com a chave passada
     * 
     * @param strMascara String contendo as m�scaras a serem substitu�das
     * @param strParam0 Valor do parametro que substitui a primeira m�scara {0}
     * @return String Contendo a cl�usula query correta
     */
    public static String realizarSubstituicao(String strMascara, String strParam0)
    {
        String[] parametro = { strParam0 };
        return realizarSubstituicao(strMascara, parametro);
    }

    /**
     * Obt�m propriedade do arquivo de acordo com a chave passada
     * 
     * @param strMascara String contendo as m�scaras a serem substitu�das
     * @param strParam0 Valor do parametro que substitui a primeira m�scara {0}
     * @param strParam1 Valor do parametro que substitui a segunda m�scara {1}
     * @return String Contendo a cl�usula query correta
     */
    public static String realizarSubstituicao(String strMascara, String strParam0, String strParam1)
    {
        String[] parametro = { strParam0, strParam1 };
        return realizarSubstituicao(strMascara, parametro);
    }

    /**
     * Obt�m propriedade do arquivo de acordo com a chave passada
     * 
     * @param strMascara String contendo as m�scaras a serem substitu�das
     * @param strParam0 Valor do parametro que substitui a primeira m�scara {0}
     * @param strParam1 Valor do parametro que substitui a segunda m�scara {1}
     * @param strParam2 Valor do parametro que substitui a terceira m�scara {2}
     * @return String Contendo a cl�usula query correta
     */
    public static String realizarSubstituicao(String strMascara, String strParam0, String strParam1, String strParam2)
    {
        String[] parametro = { strParam0, strParam1, strParam2 };
        return realizarSubstituicao(strMascara, parametro);
    }

    /**
     * Obt�m propriedade do arquivo de acordo com a chave passada
     * 
     * @param strMascara String contendo as m�scaras a serem substitu�das
     * @param strParam0 Valor do parametro que substitui a primeira m�scara {0}
     * @param strParam1 Valor do parametro que substitui a segunda m�scara {1}
     * @param strParam2 Valor do parametro que substitui a terceira m�scara {2}
     * @param strParam3 Valor do parametro que substitui a quarta m�scara {3}
     * @return String Contendo
     */
    public static String realizarSubstituicao(String strMascara, String strParam0, String strParam1, String strParam2, String strParam3)
    {
        String[] parametro = { strParam0, strParam1, strParam2, strParam3 };
        return realizarSubstituicao(strMascara, parametro);
    }

    /**
     * Obt�m propriedade do arquivo de acordo com a chave passada
     * 
     * @param strMascara String contendo as m�scaras a serem substitu�das
     * @param strParam0 Valor do parametro que substitui a primeira m�scara {0}
     * @param strParam1 Valor do parametro que substitui a segunda m�scara {1}
     * @param strParam2 Valor do parametro que substitui a terceira m�scara {2}
     * @param strParam3 Valor do parametro que substitui a quarta m�scara {3}
     * @return String Contendo
     */
    public static String realizarSubstituicao(String strMascara, Object[] stringArrParametros)
    {
        String strRetorno = strMascara;

        if (stringArrParametros != null && stringArrParametros.length > 0)
        {
            for (int i = 0; i < stringArrParametros.length; i++)
            {
                strRetorno = strRetorno.replaceAll("\\{" + i + "\\}", (String) stringArrParametros[i]);
            }
        }

        return strRetorno;
    }

    /**
     * Obt�m propriedade do arquivo de acordo com a chave passada
     * 
     * @param strMascara String contendo as m�scaras a serem substitu�das
     * @param strParam0 Valor do parametro que substitui a primeira m�scara {0}
     * @param strParam1 Valor do parametro que substitui a segunda m�scara {1}
     * @param strParam2 Valor do parametro que substitui a terceira m�scara {2}
     * @param strParam3 Valor do parametro que substitui a quarta m�scara {3}
     * @return String Contendo
     */
    public static String realizarSubstituicao(String strMascara, List lstParametros)
    {
        String strRetorno = strMascara;

        if (lstParametros != null && !lstParametros.isEmpty())
        {
            strRetorno = realizarSubstituicao(strMascara, lstParametros.toArray());
        }

        return strRetorno;
    }

}

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

package br.gov.camara.seguranca.util;

import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.biblioteca.util.CRC32;
import br.gov.camara.biblioteca.util.StringUtil;

/**
 * Rotinas de codificação e decodificação de Strings
 */
public class Criptografia
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(Criptografia.class);

    // Constantes
    private static String pstrCaracteresValidosEntrada = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.&=-_ :/";
    private static String pstrCaracteresValidosSaida = "0123tuvw456ABCDEF789GHIJKLabcd{efgMNOPUsxyÇzVWXYZ.§çmnQRS-$@hijklTopqr";

    /**
     * Codifica uma String utilizando a Chave informada
     * 
     * @param strString = String a ser codificada
     * @param strChave = Chave a ser utilizada para a codificação
     * @return String codificada
     */
    public static String codificarString(String strString, String strChave) throws CriptografiaException
    {
        if (log.isDebugEnabled())
        {
            log.debug("codificarString(String strString=" + strString + ", String strChave=" + strChave + ") - start");
        }

        // Valida os parâmetros
        if ((strString == null) || "".equals(strString))
        {
            throw new CriptografiaException("A String a ser codificada não foi informada.");
        }

        if ((strChave == null) || "".equals(strChave))
        {
            throw new CriptografiaException("A Chave a ser utilizada não foi informada.");
        }

        // Valida os caracteres de entrada
        StringBuffer strNovaString = new StringBuffer();
        for (int i = 0; i < strString.length(); i++)
        {
            strNovaString.append(obterCaracterTratado(strString.charAt(i)));
        }
        strString = strNovaString.toString();

        // Gera o CRC da String a ser codificada
        CRC32 crcString = new CRC32();
        // long lngCRCString = crcString.crc32(strString);
        long lngCRCString = crcString.semiCRC(strString.getBytes());

        // Concatena o CRC na String
        DecimalFormat nftDoisDigitos = new DecimalFormat("00");
        strString = nftDoisDigitos.format(String.valueOf(lngCRCString).length()) + strString + String.valueOf(lngCRCString);

        // Cria a chave interna
        long lngChave = 0;
        for (int i = 0; i < strChave.length(); i++)
        {
            lngChave = lngChave + strChave.charAt(i);
        }

        // Gera o deslocamento
        int intDeslocamento = (int) lngChave % pstrCaracteresValidosEntrada.length();

        // String Codificada
        String strStringCodificada = "";

        int intPosicao = 0;
        for (int i = 0; i < strString.length(); i++)
        {
            intPosicao = pstrCaracteresValidosEntrada.indexOf(strString.charAt(i)) + intDeslocamento;
            if (intPosicao >= pstrCaracteresValidosEntrada.length())
            {
                intPosicao = intPosicao - pstrCaracteresValidosEntrada.length();
            }
            strStringCodificada += pstrCaracteresValidosSaida.charAt(intPosicao);
        }

        // Retorna a String Codificada

        if (log.isDebugEnabled())
        {
            log.debug("codificarString(String strString=" + strString + ", String strChave=" + strChave + ") - end - return value=" + strStringCodificada);
        }
        return strStringCodificada;
    }

    /**
     * Decodifica uma String utilizando a Chave informada
     * 
     * @param strStringCodificada String a ser decodificada
     * @param strChave Chave a ser utilizada para a decodificação
     * @return String decodificada
     */
    public static String decodificarString(String strStringCodificada, String strChave) throws CriptografiaException
    {
        if (log.isDebugEnabled())
        {
            log.debug("decodificarString(String strStringCodificada=" + strStringCodificada + ", String strChave=" + strChave + ") - start");
        }

        // Valida os parâmetros
        if ((strStringCodificada == null) || "".equals(strStringCodificada) || (strStringCodificada.length() < 2))
        {
            String mensagem = "decodificarString - A String a ser decodificada não foi informada.";
            log.debug(mensagem);
            throw new CriptografiaException(mensagem);
        }

        if ((strChave == null) || "".equals(strChave))
        {
            String mensagem = "decodificarString - A Chave a ser utilizada não foi informada.";
            log.debug(mensagem);
            throw new CriptografiaException(mensagem);
        }

        // Valida os caracteres de entrada
        for (int i = 0; i < strStringCodificada.length(); i++)
        {
            if (pstrCaracteresValidosSaida.indexOf(strStringCodificada.charAt(i)) < 0)
            {
                String mensagem = "decodificarString - A string informada possui caracteres que não são tratados por este método";
                log.debug(mensagem);
                throw new CriptografiaException(mensagem);
            }
        }

        // Cria a chave interna
        long lngChave = 0;
        for (int i = 0; i < strChave.length(); i++)
        {
            lngChave = lngChave + strChave.charAt(i);
        }

        // Gera o deslocamento
        int intDeslocamento = (int) lngChave % pstrCaracteresValidosSaida.length();

        // Gera String Decodificada
        String strStringDecodificada = "";

        int intPosicao = 0;
        for (int i = 0; i < strStringCodificada.length(); i++)
        {
            intPosicao = pstrCaracteresValidosSaida.indexOf(strStringCodificada.charAt(i)) - intDeslocamento;
            if (intPosicao < 0)
            {
                intPosicao = intPosicao + pstrCaracteresValidosSaida.length();
            }
            strStringDecodificada += pstrCaracteresValidosEntrada.charAt(intPosicao);
        }

        // Recupera o tamanho do CRC armazenado na String
        String strTamanhoCRC = strStringDecodificada.substring(0, 2);

        if ((strTamanhoCRC.charAt(0) < 48) || (strTamanhoCRC.charAt(1) < 48))
        {
            String mensagem = "decodificarString - A String a ser decodificada é inválida ou está num formato diferente do esperado";
            log.debug(mensagem);
            throw new CriptografiaException(mensagem);
        }
        if ((strTamanhoCRC.charAt(0) > 57) || (strTamanhoCRC.charAt(1) > 57))
        {
            String mensagem = "decodificarString - A String a ser decodificada é inválida ou está num formato diferente do esperado";
            log.debug(mensagem);
            throw new CriptografiaException(mensagem);
        }

        int intTamanhoCRC = Integer.parseInt(strTamanhoCRC);

        // O tamanho recuperado é válido ?
        if ((intTamanhoCRC >= strStringDecodificada.length()) || (intTamanhoCRC == 0))
        {
            String mensagem = "decodificarString - A String a ser decodificada é inválida ou está num formato diferente do esperado";
            log.debug(mensagem);
            throw new CriptografiaException(mensagem);
        }

        // Recupera o CRC armazenado
        String strCRC = strStringDecodificada.substring(strStringDecodificada.length() - intTamanhoCRC);

        for (int i = 0; i < strCRC.length(); i++)
        {
            if ((strCRC.charAt(i) < 45) || (strCRC.charAt(i) == 47) || (strCRC.charAt(i) > 57))
            {
                String mensagem = "decodificarString - A String a ser decodificada é inválida ou está num formato diferente do esperado";
                log.debug(mensagem);
                throw new CriptografiaException(mensagem);
            }
        }

        long lngCRCStringRecebida = 0;

        try
        {
            lngCRCStringRecebida = Long.parseLong(strCRC);
        }
        catch (NumberFormatException nfe)
        {
            String mensagem = "decodificarString - A String a ser decodificada é inválida ou está num formato diferente do esperado";
            log.debug(mensagem);
            throw new CriptografiaException(mensagem);
        }

        // String Recebida
        strStringDecodificada = strStringDecodificada.substring(2, strStringDecodificada.length() - intTamanhoCRC);

        // Gera o CRC da String Decodificada
        CRC32 crcString = new CRC32();
        // long lngCRCString = crcString.crc32(strStringDecodificada);
        long lngCRCString = crcString.semiCRC(strStringDecodificada.getBytes());

        // Valida o CRC
        if (lngCRCStringRecebida != lngCRCString)
        {
            String mensagem = "decodificarString - A String a ser decodificada é inválida ou está num formato diferente do esperado";
            log.debug(mensagem);
            throw new CriptografiaException(mensagem);
        }

        strStringDecodificada = obterStringOriginal(strStringDecodificada);

        // Retorna a String Decodificada
        if (log.isDebugEnabled())
        {
            log.debug("decodificarString(String strStringCodificada="
                    + strStringCodificada
                    + ", String strChave="
                    + strChave
                    + ") - end - return value="
                    + strStringDecodificada);
        }
        return strStringDecodificada;
    }

    /**
     * Gera o número hash de uma String calculado utilizando a chave informada
     * 
     * @param strString String da qual deve ser obtido o número hash
     * @param strChave Chave a ser utilizada
     * @return Número hash obtido
     */
    public static long obterHashString(String strString, String strChave)
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterHashString(String strString=" + strString + ", String strChave=" + strChave + ") - start");
        }

        int uHASH_DEPTH = 6;
        int[] k = new int[uHASH_DEPTH + 1];
        String s = "";
        int i;
        int j;
        int k1;
        int n;
        char c;
        long fHash;
        long fTemp;

        fHash = 0;

        if (!strChave.equals(""))
        {
            s = strChave + strString;

            n = s.length();

            c = s.charAt(0);
            k[1] = c;

            for (j = 2; j <= uHASH_DEPTH; j++)
            {
                k1 = (k[j - 1] % n);

                c = s.charAt(k1);

                k[j] = c;

                if (k[j] == k[j - 1])
                {
                    k[j] = k[j] + 1;
                }
            }

            for (i = 1; i <= n; i++)
            {
                fTemp = i;

                for (j = 1; j <= uHASH_DEPTH; j++)
                {
                    k1 = ((i + k[j]) % n);

                    c = s.charAt(k1);

                    fTemp = fTemp * (c);
                }

                fHash = fHash + fTemp;
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterHashString(String strString=" + strString + ", String strChave=" + strChave + ") - end - return value=" + fHash);
        }
        return fHash;
    }

    private static String obterCaracterTratado(char caracter)
    {
        String retorno = null;
        int posicao = pstrCaracteresValidosEntrada.indexOf(caracter);

        if (posicao >= 0 && caracter != '/')
        {
            retorno = String.valueOf(caracter);
        }
        else
        {
            retorno = StringUtil.converterEntreBases(String.valueOf((int) caracter), 10, 16);
            if (retorno.length() == 1)
            {
                retorno = "/0" + retorno;
            }
            else
            {
                retorno = "/" + retorno;
            }
        }

        return retorno;
    }

    public static String embaralhar(String strPalavra)
    {
        if (log.isDebugEnabled())
        {
            log.debug("embaralhar(String strPalavra=" + strPalavra + ") - start");
        }

        // Embaralha uma palavra passada
        int intTamanho;
        String strLetra = "";
        int intSlot;
        String strPalavraTrabalho;

        intTamanho = strPalavra.length();
        strPalavraTrabalho = strPalavra;

        for (int i = intTamanho - 1; i >= 0; i--)
        {
            intSlot = (int) (Math.random() * i);
            strLetra += strPalavraTrabalho.substring(intSlot, intSlot + 1);
            strPalavraTrabalho = strPalavraTrabalho.substring(0, intSlot) + strPalavraTrabalho.substring(intSlot + 1, strPalavraTrabalho.length());
        }

        if (log.isDebugEnabled())
        {
            log.debug("embaralhar(String strPalavra=" + strPalavra + ") - end - return value=" + strLetra);
        }
        return strLetra;
    }

    private static String obterStringOriginal(String strStringTratada)
    {
        StringBuffer strNovaString = new StringBuffer();
        String strCaracterOriginal;
        int intPosicao;

        intPosicao = strStringTratada.indexOf("/");
        while (intPosicao >= 0)
        {
            strCaracterOriginal = strStringTratada.substring(intPosicao + 1, intPosicao + 3);

            // Tenta converter o código para o caracter original
            strCaracterOriginal = StringUtil.converterEntreBases(strCaracterOriginal, 16, 10);

            // Define as novas strings
            strNovaString.append(strStringTratada.substring(0, intPosicao));
            strNovaString.append((char) Integer.parseInt(strCaracterOriginal));
            strStringTratada = strStringTratada.substring(intPosicao + 3);

            intPosicao = strStringTratada.indexOf("/");
        }

        return strNovaString.toString() + strStringTratada;
    }
}

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

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.biblioteca.exception.DataException;

/**
 * Classe utilitária para manipulação de datas
 */
public class DataNova
{

    public static final int FORMAT_DDMMYYYY = 1;
    public static final int FORMAT_MMDDYYYY = 2;
    public static final int FORMAT_YYYYMMDD = 3;
    public static final int FORMAT_YYYYDDMM = 4;
    public static final int FORMAT_MMYYYY = 5;
    public static final int FORMAT_YYYY = 6;

    public static final String FORMATO_DATA = "dd/MM/yyyy";
    public static final String FORMATO_DATAHORA = FORMATO_DATA + " HH:mm:ss";

    // Inicializa o log
    private static Log log = LogFactory.getLog(DataNova.class);

    /**
     * @deprecated
     * @return
     */
    public static SimpleDateFormat getDateFormat()
    {
        return getFormatoData();
    }

    public static SimpleDateFormat getFormato(String formato) throws DataException
    {
        if (formato == null)
        {
            throw new DataException("O formato não foi especificado.");
        }

        SimpleDateFormat dateFormat = null;

        try
        {
            dateFormat = new SimpleDateFormat(formato);
            dateFormat.setLenient(false);
        }
        catch (IllegalArgumentException ne)
        {
            throw new DataException("O formato especificado não é válido.");
        }

        return dateFormat;
    }

    public static SimpleDateFormat getFormatoData()
    {
        String formato = FORMATO_DATA;
        SimpleDateFormat retorno = null;

        try
        {
            retorno = getFormato(formato);
        }
        catch (DataException de)
        {
            // Não deveria acontecer, visto que o motivo seria formato inválido e este não pode ser.
        }

        return retorno;
    }

    public static SimpleDateFormat getFormatoDataHora()
    {
        String formato = FORMATO_DATAHORA;
        SimpleDateFormat retorno = null;

        try
        {
            retorno = getFormato(formato);
        }
        catch (DataException de)
        {
            // Não deveria acontecer, visto que o motivo seria formato inválido e este não pode ser.
        }

        return retorno;
    }

    /**
     * formatarTimeStamp
     * 
     * @param strTmp1 : String que representa uma Data, ou Data e Hora, nos
     *            formatos: dd/mm/yyyy dd/mm/yyyy hh:mm dd/mm/yyyy hh:mm:ss
     * @return : objeto tipo java.sql.Timestamp, a ser utilizado em operações
     *         JDBC.
     */
    public static Timestamp formatarTimeStamp(String strTmp1) throws DataException
    {
        Timestamp tmtDataHora = null;
        java.util.Date datUtil = null;

        try
        {
            SimpleDateFormat sdfDiaMesAno = getFormatoDataHora();
            String strData = "";
            int intMaximo = ((strTmp1.length() > 19) ? 19 : strTmp1.length());
            strData = strTmp1.substring(0, intMaximo);

            if (intMaximo == 10)
            {
                strData += " 00:00:00";
            }

            if (intMaximo == 16)
            {
                strData += ":00";
            }

            datUtil = sdfDiaMesAno.parse(strData.replace('-', '/'));
        }
        catch (ParseException pae)
        {
            if (log.isFatalEnabled())
            {
                log.fatal("Erro (ParseException) ao tentar converter " + strTmp1 + " para formato TimeStamp. Excecao = " + pae.getMessage());
            }

            throw new DataException("Erro (ParseException) ao tentar converter " + strTmp1 + " para formato TimeStamp. Excecao = " + pae.getMessage());
        }

        // fim do catch 1
        tmtDataHora = new Timestamp(datUtil.getTime());

        return tmtDataHora;
    }

    /**
     * formatarCalendar
     * 
     * @param strTmp1 : String que representa uma Data, ou Data e Hora, nos
     *            formatos: dd/mm/yyyy dd/mm/yyyy hh:mm dd/mm/yyyy hh:mm:ss
     * @return : objeto tipo java.util.Calendar.
     */
    public static Calendar formatarCalendar(String strTmp1) throws DataException
    {
        Calendar calDataHora = Calendar.getInstance();
        calDataHora.setTime(parse(strTmp1));

        return calDataHora;
    }

    /**
     * formatarDate
     * 
     * @param strTmp1 : String que representa uma Data, ou Data e Hora, nos
     *            formatos: dd/mm/yyyy dd/mm/yyyy hh:mm dd/mm/yyyy hh:mm:ss
     * @return : objeto tipo java.util.Date.
     */
    public static Date formatarDate(String strTmp1) throws DataException
    {
        Date dtDataHora = new Date();
        dtDataHora = parse(strTmp1);

        return dtDataHora;
    }

    /**
     * @return : um Date representando a data e hora atual
     */
    public static Date getDataHora()
    {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    /**
     * obterData()
     * 
     * @return : uma string representando a data atual, no formato dd/mm/aaaa
     */
    public static String obterData()
    {
        Calendar cal = Calendar.getInstance();
        java.util.Date dataAgora = cal.getTime();

        SimpleDateFormat sdf = getFormatoData();

        return sdf.format(dataAgora);
    }

    /**
     * Retorna o número de anos de acordo com uma data de referencia.
     * ATENCAO:ele irá retornar -1 se a data de referencia for menor do que a
     * data Nascimento
     * 
     * @return : retorna a idade com referencia
     */
    public static int obterIdadeEm(String dataReferencia, String dataNascimento) throws DataException
    {
        int idade = -1;

        Calendar dataReferenciaCalendar = Calendar.getInstance();
        Calendar dataMovel = Calendar.getInstance();

        dataReferenciaCalendar = formatarCalendar(dataReferencia);
        dataMovel = formatarCalendar(dataNascimento);

        while (dataMovel.before(dataReferenciaCalendar) || DataNova.format(dataMovel).equals(DataNova.format(dataReferenciaCalendar)))
        {
            ++idade;
            dataMovel.add(Calendar.YEAR, 1);
        }

        return idade;
    }

    /**
     * Obtém a data atual
     * 
     * @return String contendo a data atual
     */
    public static String obterDataAtual()
    {
        GregorianCalendar gclData = new GregorianCalendar();

        return ((gclData.get(GregorianCalendar.DAY_OF_MONTH) > 9) ? "" : "0")
                + gclData.get(GregorianCalendar.DAY_OF_MONTH)
                + "/"
                + (((gclData.get(GregorianCalendar.MONTH) + 1) > 9) ? "" : "0")
                + (gclData.get(GregorianCalendar.MONTH) + 1)
                + "/"
                + gclData.get(GregorianCalendar.YEAR);
    }

    /**
     * obterDataHora()
     * 
     * @return : uma string representando a data/hora atual, no formato
     *         dd/mm/aaaa HH:mm:ss
     */
    public static String obterDataHora()
    {
        Calendar cal = Calendar.getInstance();
        java.util.Date dataAgora = cal.getTime();

        SimpleDateFormat sdf = getFormatoDataHora();

        return sdf.format(dataAgora);
    }

    /**
     * Obtém a data e hora atual
     * 
     * @return String contendo a data e a hora atual
     */
    public static String obterDataHoraAtual()
    {
        GregorianCalendar gclData = new GregorianCalendar();

        return ((gclData.get(GregorianCalendar.DAY_OF_MONTH) > 9) ? "" : "0")
                + gclData.get(GregorianCalendar.DAY_OF_MONTH)
                + "/"
                + (((gclData.get(GregorianCalendar.MONTH) + 1) > 9) ? "" : "0")
                + (gclData.get(GregorianCalendar.MONTH) + 1)
                + "/"
                + gclData.get(GregorianCalendar.YEAR)
                + " "
                + ((gclData.get(GregorianCalendar.HOUR_OF_DAY) > 9) ? "" : "0")
                + gclData.get(GregorianCalendar.HOUR_OF_DAY)
                + ":"
                + ((gclData.get(GregorianCalendar.MINUTE) > 9) ? "" : "0")
                + gclData.get(GregorianCalendar.MINUTE)
                + ":"
                + ((gclData.get(GregorianCalendar.SECOND) > 9) ? "" : "0")
                + gclData.get(GregorianCalendar.SECOND);
    }

    /**
     * obterHora()
     * 
     * @return : uma string representando a hora atual, no formato HH:mm:ss
     */
    public static String obterHora()
    {
        Calendar cal = Calendar.getInstance();
        java.util.Date dataAgora = cal.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        return sdf.format(dataAgora);
    }

    /**
     * Verifica se determinada data é válida
     * 
     * @param strData DOCUMENTAR!
     * @return DOCUMENTAR!
     */
    public static boolean validarData(String strData)
    {
        try
        {
            SimpleDateFormat df = getFormatoData();
            df.setLenient(false);
            df.parse(strData);
            return true;
        }
        catch (ParseException e)
        {
            return false;
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }
    }

    /**
     * Formata data de um Calendar.
     * 
     * @param calData valor da data
     * @return String data formatada "dd/MM/aaaa"
     */
    public static String format(Calendar calData)
    {
        if (calData == null)
        {
            return "";
        }
        // Declaração do retorno
        String strRetorno = (calData.get(Calendar.DATE) > 9 ? "" : "0")
                + calData.get(Calendar.DATE)
                + "/"
                + (calData.get(Calendar.MONTH) > 8 ? "" : "0")
                + (calData.get(Calendar.MONTH) + 1)
                + "/"
                + (calData.get(Calendar.YEAR) > 9 ? "" : "0")
                + +calData.get(Calendar.YEAR);

        return strRetorno;
    }

    /**
     * Formata data hora de um Calendar.
     * 
     * @param calData valor da datahora
     * @return String data formatada "dd/MM/aaaa HH/mm/ss"
     */
    public static String formatDataHora(Calendar calData)
    {
        if (calData == null)
        {
            return "";
        }
        // Declaração do retorno
        String strRetorno = (calData.get(Calendar.DATE) > 9 ? "" : "0")
                + calData.get(Calendar.DATE)
                + "/"
                + (calData.get(Calendar.MONTH) > 8 ? "" : "0")
                + (calData.get(Calendar.MONTH) + 1)
                + "/"
                + (calData.get(Calendar.YEAR) > 9 ? "" : "0")
                + +calData.get(Calendar.YEAR)
                + " "
                + (calData.get(Calendar.HOUR_OF_DAY) > 9 ? "" : "0")
                + calData.get(Calendar.HOUR_OF_DAY)
                + ":"
                + (calData.get(Calendar.MINUTE) > 9 ? "" : "0")
                + calData.get(Calendar.MINUTE)
                + ":"
                + (calData.get(Calendar.SECOND) > 9 ? "" : "0")
                + calData.get(Calendar.SECOND);

        return strRetorno;
    }

    /**
     * Formata data de um Object (contendo um Calendar).
     * 
     * @param objData valor da data
     * @return String data formatada "dd/MM/aaaa"
     */
    public static String format(Object objData)
    {
        if (objData == null)
        {
            return "";
        }

        Calendar calData = (Calendar) objData;

        // Declaração do retorno
        String strRetorno = (calData.get(Calendar.DATE) > 9 ? "" : "0")
                + calData.get(Calendar.DATE)
                + "/"
                + (calData.get(Calendar.MONTH) > 8 ? "" : "0")
                + (calData.get(Calendar.MONTH) + 1)
                + "/"
                + (calData.get(Calendar.YEAR) > 9 ? "" : "0")
                + +calData.get(Calendar.YEAR);
        return strRetorno;
    }

    /**
     * Formata data de um Date.
     * 
     * @param dtData valor da data
     * @return String data formatada "dd/MM/aaaa"
     */
    public static String format(Date dtData)
    {
        if (dtData == null)
        {
            return "";
        }

        Calendar objCalendar = Calendar.getInstance();
        objCalendar.setTime(dtData);

        // Declaração do retorno
        String strRetorno = (objCalendar.get(Calendar.DAY_OF_MONTH) > 9 ? "" : "0")
                + objCalendar.get(Calendar.DAY_OF_MONTH)
                + "/"
                + (objCalendar.get(Calendar.MONTH) > 8 ? "" : "0")
                + (objCalendar.get(Calendar.MONTH) + 1)
                + "/"
                + (objCalendar.get(Calendar.YEAR) > 9 ? "" : "0")
                + objCalendar.get(Calendar.YEAR);

        return strRetorno;
    }

    public static Date parse(String data) throws DataException
    {
        try
        {
            SimpleDateFormat dateFormat = null;
            if (data.length() > 10)
            {
                dateFormat = getFormatoDataHora();
            }
            else
            {
                dateFormat = getFormatoData();
            }
            return dateFormat.parse(data);
        }
        catch (ParseException pexc)
        {
            throw new DataException("A data '" + data + "' é inválida");
        }
    }

    /**
     * Método formata a data de acordo com a constante passada como parâmetro.
     * Os formatos comtemplados são dd/mm/yyyy, mm/dd/yyyy, yyyy/dd/mm e
     * yyyy/mm/yyyy.
     * 
     * @param strData Data a ser formatada. Deve estar no formato dd/mm/yyyy.
     * @param intFormato Formato desejado para o retorno.
     * @return Data formatada de acordo com o parâmetro strData.
     */
    public static String format(String strData, int intFormato)
    {

        // Declarações
        String strRetorno = null;
        String strDia = null;
        String strMes = null;
        String strAno = null;
        String strDataTmp = strData;

        // Para o caso de termos um formato yyyy-mm-dd hh:mm:ss.d, ignora o .d
        if (strData != null)
        {
            if (strData.length() == 21)
            {
                strData = strData.substring(0, 19);
            }
        }
        if (strData == null || (strData.length() != 10 && strData.length() != 19) || strData.equals("9999-12-31 23:59:59"))
        {
            return "";
        }

        if (strData.length() == 19 && strData.indexOf("-") != -1)
        {
            strDataTmp = strData.substring(8, 10) + "/" + strData.substring(5, 7) + "/" + strData.substring(0, 4);
        }
        else
        {
            if (strData.length() == 19 && strData.indexOf("-") == -1)
            {
                strDataTmp = strData.substring(0, 10);
            }
        }

        // Seta os valores ao dia, mes e ano
        strDia = strDataTmp.substring(0, 2);
        strMes = strDataTmp.substring(3, 5);
        strAno = strDataTmp.substring(6);

        switch (intFormato)
        {
            case FORMAT_MMDDYYYY:
                strRetorno = strMes + "/" + strDia + "/" + strAno;
                break;
            case FORMAT_DDMMYYYY:
                strRetorno = strDia + "/" + strMes + "/" + strAno;
                break;
            case FORMAT_YYYYDDMM:
                strRetorno = strAno + "/" + strDia + "/" + strMes;
                break;
            case FORMAT_YYYYMMDD:
                strRetorno = strAno + "/" + strMes + "/" + strDia;
                break;
            case FORMAT_MMYYYY:
                strRetorno = strMes + "/" + strAno;
                break;
            case FORMAT_YYYY:
                strRetorno = strAno;
                break;
        }

        return strRetorno;

    }

    /**
     * Converte uma string para Date tentando identificar o formato da data na
     * string. Os formatos comtemplados são dd?mm?yyyy e yyyy?mm?dd com ou sem
     * hora (24H)
     * 
     * @param strDataHora Data e Hora a ser formatada.
     * @return Date Data convertida.
     */
    public static Date converterParseDataHora(String strDataHora) throws DataException
    {

        // Declarações
        String strDataTmp = strDataHora;
        int posicaoSeparador = -1;
        int tamanhoDataHora = -1;

        if (strDataHora == null || "".equals(strDataHora))
        {
            return null;
        }

        if (strDataHora.length() < 10)
        {
            throw new DataException("Não foi possível identificar o formato da data na string '" + strDataHora + "'");
        }

        // Identificar a posição do primeiro caracter não numérico
        tamanhoDataHora = strDataHora.length();
        for (int i = 0; i <= tamanhoDataHora; i++)
        {
            if (strDataHora.charAt(i) < '0' || strDataHora.charAt(i) > '9')
            {
                posicaoSeparador = i;
                break;
            }
        }

        if (posicaoSeparador != 2 && posicaoSeparador != 4)
        {
            throw new DataException("Não foi possível identificar o formato da data na string '" + strDataHora + "'");
        }

        // Se a posição = 2, então o formato deve ser dd/mm/yyyy -> Não precisa
        // fazer nada
        // Se a posição = 4, então o formato deve ser yyyy/mm/dd -> Converter
        if (posicaoSeparador == 4)
        {
            strDataTmp = strDataHora.substring(8, 10) + "/" + strDataHora.substring(5, 7) + "/" + strDataHora.substring(0, 4) + strDataHora.substring(10);
        }

        return formatarDate(strDataTmp);
    }

    /**
     * Método formata a datahora de acordo com a constante passada como
     * parâmetro. Os formatos comtemplados são dd/mm/yyyy, mm/dd/yyyy,
     * yyyy/dd/mm e yyyy/mm/yyyy.
     * 
     * @param strData Data a ser formatada. Deve estar no formato dd/mm/yyyy.
     * @param intFormato Formato desejado para o retorno.
     * @return Data formatada de acordo com o parâmetro strData.
     */
    public static String formatDataHora(String strData, int intFormato)
    {

        // Declarações
        String strRetorno = null;
        String strDia = null;
        String strMes = null;
        String strAno = null;
        String strHora = "";
        String strDataTmp = strData;

        if (strData == null || (strData.length() != 10 && strData.length() != 19))
        {
            return "";
        }

        if (strData.indexOf("-") != -1)
        {
            strDataTmp = strData.substring(8, 10) + "/" + strData.substring(5, 7) + "/" + strData.substring(0, 4);
        }

        // Seta os valores ao dia, mes e ano
        strDia = strDataTmp.substring(0, 2);
        strMes = strDataTmp.substring(3, 5);
        strAno = strDataTmp.substring(6, 10);
        if (strData.length() > 11)//
        {
            strHora = strData.substring(10);
        }
        switch (intFormato)
        {
            case FORMAT_MMDDYYYY:
                strRetorno = strMes + "/" + strDia + "/" + strAno;
                break;
            case FORMAT_DDMMYYYY:
                strRetorno = strDia + "/" + strMes + "/" + strAno;
                break;
            case FORMAT_YYYYDDMM:
                strRetorno = strAno + "/" + strDia + "/" + strMes;
                break;
            case FORMAT_YYYYMMDD:
                strRetorno = strAno + "/" + strMes + "/" + strDia;
                break;
            case FORMAT_MMYYYY:
                strRetorno = strMes + "/" + strAno;
                break;
            case FORMAT_YYYY:
                strRetorno = strAno;
                break;
        }

        if (strData.length() > 11)//
        {
            return strRetorno + strHora;
        }
        else
        {
            return strRetorno;
        }

    }

    public static String format(Date dtData, int intFormato)
    {
        String strRetorno = format(dtData);
        return format(strRetorno, intFormato);
    }

    public static String format(Calendar calData, int intFormato)
    {
        String strRetorno = format(calData);
        return format(strRetorno, intFormato);
    }

    public static String formatDataHora(Calendar calData, int intFormato)
    {
        String strRetorno = formatDataHora(calData);
        return formatDataHora(strRetorno, intFormato);
    }

    /**
     * Formata a data passada no formato desejado
     * 
     * @param datData Data a ser formatada
     * @param strFormato Formato desejado
     * @return String contendo a data formatada
     */
    public static String formatarData(Date datData, String strFormato) throws DataException
    {
        SimpleDateFormat dtfFormato = getFormato(strFormato);
        return dtfFormato.format(datData);
    }

    /**
     * Converte uma data no formato String para Date
     * 
     * @param strData String contendo a data
     * @return Date com a data passada como String
     */
    public static Date converter(String strData)
    {
        int intDia = 0;
        int intMes = 0;
        int intAno = 0;
        int intHora = 0;
        int intMinuto = 0;
        int intSegundo = 0;
        // Calendar objCalendar;

        // Quebra a data para poder utilizar a Classe Calendar para adicionar
        // valores
        intDia = Integer.parseInt(strData.substring(0, 2));
        intMes = Integer.parseInt(strData.substring(3, 5));
        intAno = Integer.parseInt(strData.substring(6, 10));

        if (strData.length() > 11)//
        {
            intHora = Integer.parseInt(strData.substring(11, 13));
            intMinuto = Integer.parseInt(strData.substring(14, 16));
            intSegundo = Integer.parseInt(strData.substring(17, 19));
            // Cria Calendar passando todos os parâmetos
            // objCalendar = new GregorianCalendar(intAno, intMes, intDia,
            // intHora, intMinuto, intSegundo);

        }
        else
        {
            // objCalendar = new GregorianCalendar(intAno, intMes, intDia);
        }

        if (strData.length() > 11)
        {
            Calendar calData = Calendar.getInstance();
            calData.set(intAno, intMes - 1, intDia, intHora, intMinuto, intSegundo);
            return calData.getTime();
        }
        else
        {
            Calendar calData = Calendar.getInstance();
            calData.set(intAno, intMes - 1, intDia);
            return calData.getTime();
        }

    }

    /**
     * Converte uma data Date para Calendar
     * 
     * @param datData Date contendo a data
     * @return Date com a data passada como String
     */
    public static Calendar converter(Date strData)
    {
        Calendar datCalender = Calendar.getInstance();
        datCalender.setTime(strData);

        return datCalender;
    }

    /**
     * Faz uma adição de data, de acordo com o tipo de elemento a ser
     * adicionado. Para se subtrair um valor, utilize um número negativo.
     * 
     * @param datData Date contendo a data que sofrerá a operação de adição
     * @param chrTipo Elemento da data a ser adicionado. 'D' para dia, 'M' para
     *            mês, 'Y' para ano, 'h' para hora, 'm' para minuto ou 's' para
     *            segundo
     * @param intValor Valor a ser adicionado
     * @return Date contendo a data adicionada
     */
    public static Date adicionar(Date datData, char chrTipo, int intValor)
    {

        // Cria Calendar passando todos os parâmetos
        Calendar calData = Calendar.getInstance();
        calData.setTime(datData);

        calData = adicionar(calData, chrTipo, intValor);

        // Retorna como String
        return calData.getTime();

    }

    /**
     * Faz uma adição de data, de acordo com o tipo de elemento a ser
     * adicionado. Para se subtrair um valor, utilize um número negativo.
     * 
     * @param calData Calendar contendo a data que sofrerá a operação de adição
     * @param chrTipo Elemento da data a ser adicionado. 'd' para dia, 'M' para
     *            mês, 'y' para ano, 'h' para hora, 'm' para minuto ou 's' para
     *            segundo
     * @param intValor Valor a ser adicionado
     * @return Calendar contendo a data adicionada
     */
    public static Calendar adicionarNovo(Calendar calData, char chrTipo, int intValor)
    {
        // Cria novo parâmetro
        Calendar calNovaData = Calendar.getInstance();
        calNovaData.setTime(calData.getTime());

        return adicionar(calNovaData, chrTipo, intValor);
    }

    /**
     * Faz uma adição de data, de acordo com o tipo de elemento a ser
     * adicionado. Para se subtrair um valor, utilize um número negativo.
     * 
     * @param calData Calendar contendo a data que sofrerá a operação de adição
     * @param chrTipo Elemento da data a ser adicionado. 'd' para dia, 'M' para
     *            mês, 'y' para ano, 'h' para hora, 'm' para minuto ou 's' para
     *            segundo
     * @param intValor Valor a ser adicionado
     * @return Calendar contendo a data adicionada
     */
    public static Calendar adicionar(Calendar calData, char chrTipo, int intValor)
    {

        // Verifica qual será a operação feita na data
        int intCampo = Calendar.DAY_OF_MONTH;
        switch (chrTipo)
        {
            case 'D':
                intCampo = Calendar.DAY_OF_MONTH;
                break;

            case 'M':
                intCampo = Calendar.MONTH;

                break;

            case 'Y':
                intCampo = Calendar.YEAR;
                break;

            case 'h':
                intCampo = Calendar.HOUR_OF_DAY;
                break;

            case 'm':
                intCampo = Calendar.MINUTE;
                break;

            case 's':
                intCampo = Calendar.SECOND;
                break;
        }

        // Efetua a operação de adição
        calData.add(intCampo, intValor);

        // Retorna como String
        return calData;

    }

    /**
     * Faz uma adição de data, de acordo com o tipo de elemento a ser
     * adicionado. Para se subtrair um valor, utilize um número negativo.
     * ATENCAO! Favor informar no formato DD/MM/YYYY
     * 
     * @param strData String contendo a data que sofrerá a operação de adição
     * @param chrTipo Elemento da data a ser adicionado. O valor é o mesmo do
     *            Calendar
     * @param intValor Valor a ser adicionado
     * @param formatacaoData Formato da data de retorno, levando em consideração
     *            o formato dessa classe
     * @return String contendo a data adicionada e formatada
     */
    public static String adicionar(String strData, int chrTipo, int intValor, int formatacaoData)
    {
        // ATENCAO:Internamente está ocorrendo algum problema na soma do dia
        // 12/09/2013 com 31
        // dias, o que em Calendar infelizmente está resultando 13/10/2013(e
        // pior, às vezes!)
        // Assim, trocamos pelo Gregorian porque ele não dá o mesmo problema.
        GregorianCalendar calendario = new GregorianCalendar();
        calendario.setTime(DataNova.converter(strData));
        calendario.add(chrTipo, intValor);

        // Retorna como String
        return DataNova.format(calendario, formatacaoData);

    }

    public static int daysUntil(Calendar fromDate, Calendar toDate)
    {
        return daysSinceEpoch(toDate) - daysSinceEpoch(fromDate);
    }

    public static int daysSinceEpoch(Calendar day)
    {
        int year = day.get(Calendar.YEAR);
        int month = day.get(Calendar.MONTH);
        int daysThisYear = cumulDaysToMonth[month] + day.get(Calendar.DAY_OF_MONTH) - 1;
        if ((month > 1) && isLeapYear(year))
        {
            daysThisYear++;
        }

        return daysToYear(year) + daysThisYear;
    }

    public static boolean isLeapYear(int year)
    {
        return (year % 400 == 0) || ((year % 100 != 0) && (year % 4 == 0));
    }

    static int daysToYear(int year)
    {
        return (365 * year) + numLeapsToYear(year);
    }

    static int numLeapsToYear(int year)
    {
        int num4y = (year - 1) / 4;
        int num100y = (year - 1) / 100;
        int num400y = (year - 1) / 400;
        return num4y - num100y + num400y;
    }

    private static final int[] cumulDaysToMonth = { 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334 };

    /**
     * Atenção! Essa função irá incluir o dia de início do período
     * 
     * @param strDataInicial
     * @param strDataFinal
     * @return
     */
    public static String subtrairData(String strDataInicial, String strDataFinal) throws DataException
    {
        String strRetorno = "";

        strRetorno = DataNova.daysUntil(DataNova.formatarCalendar(strDataInicial), DataNova.formatarCalendar(strDataFinal)) + 1 + "";
        return strRetorno;
    }

    public static String subtrairData(Calendar calDataInicial, Calendar calDataFinal) throws DataException
    {
        return subtrairData(format(calDataInicial), format(calDataFinal));
    }

    public static String subtrairData(String strData, int numeroDias)
    {
        int intDia;
        int intMes;
        int intAno;
        int intHora = 0;
        int intMinuto = 0;
        int intSegundo = 0;
        Calendar calData;

        // Quebra a data para poder utilizar a Classe Calendar para adicionar
        // valores
        intDia = Integer.parseInt(strData.substring(0, 2));
        intMes = Integer.parseInt(strData.substring(3, 5));
        intAno = Integer.parseInt(strData.substring(6, 10));
        if (strData.length() > 11)//
        {
            intHora = Integer.parseInt(strData.substring(11, 13));
            intMinuto = Integer.parseInt(strData.substring(14, 16));
            intSegundo = Integer.parseInt(strData.substring(17, 19));
        }

        // Cria Calendar passando todos os parâmetos
        if (strData.length() > 11)//
        {
            calData = new GregorianCalendar(intAno, intMes, intDia, intHora, intMinuto, intSegundo);
        }
        else
        {
            calData = new GregorianCalendar(intAno, intMes, intDia);

        }

        calData.add(Calendar.DAY_OF_MONTH, -numeroDias);

        return formatDataHora(calData, DataNova.FORMAT_DDMMYYYY);

    }

    /**
     * Obtem o intervalo entre dias, desconsiderando as horas. Assim, 14/09/2005 -
     * 07/09/2005 dá 7 dias.
     * 
     * @param strDataFinal - Data final
     * @param strDataInicial - Data Inicial
     * @return Numero de dias não incluindo o dia
     */
    public static int obterIntervalo(String strDataInicial, String strDataFinal) throws DataException
    {

        long diferencaDias = 0;
        // long moduloDaDiferenca = 0;

        Calendar calDataInicial = DataNova.formatarCalendar(strDataInicial);

        Calendar calDataFinal = DataNova.formatarCalendar(strDataFinal);

        diferencaDias = (calDataInicial.getTimeInMillis() - calDataFinal.getTimeInMillis()) / 1000 / 60 / 60 / 24;
        // moduloDaDiferenca = (calDataInicial.getTimeInMillis() -
        // calDataFinal.getTimeInMillis())%1000*60*60*24;

        // Torça que funcione!
        return (int) diferencaDias;
    }

    /**
     * Obtem o intervalo entre dias, desconsiderando as horas, porém incluindo o
     * dia de inicio. Assim, 14/09/2005 - 07/09/2005 dá 8 dias.
     * 
     * @param strDataFinal - Data final
     * @param strDataInicial - Data Inicial
     * @return Numero de dias incluindo o dia
     */
    public static int obterIntervaloIncluindoODia(String strDataInicial, String strDataFinal) throws DataException
    {
        return DataNova.obterIntervalo(strDataInicial, strDataFinal) + 1;
    }

    public static boolean isMaiorDoQue(String strDataInicial, String strDataFinal) throws DataException
    {

        // Cria Calendar passando todos os parâmetos
        Calendar calDataInicial = DataNova.formatarCalendar(strDataInicial);
        Calendar calDataFinal = DataNova.formatarCalendar(strDataFinal);

        return calDataInicial.after(calDataFinal);

    }

    /**
     * Obtem o intervalo em meses entre duas datas
     * @param calDataFinal - Data final
     * @param calDataInicial - Data Inicial
     * @return Numero de meses 
     */
    public static int obterIntervaloEntreDatas(Calendar calDataInicial, Calendar calDataFinal, char chrTipo) throws DataException
    {
        int intRetorno = 0;

        int intAnoFinal = calDataFinal.get(Calendar.YEAR);
        int intAnoInicial = calDataInicial.get(Calendar.YEAR);
        int intMesFinal = calDataFinal.get(Calendar.MONTH) + 1;
        int intMesInicial = calDataInicial.get(Calendar.MONTH) + 1;

        switch (chrTipo)
        {
            case 'D':
                throw new DataException("Opção não implementada.");

            case 'M':
                intRetorno = ((intAnoFinal - intAnoInicial) * 12) + (intMesFinal - intMesInicial);
                break;

            case 'Y':
                throw new DataException("Opção não implementada.");

            case 'h':
                throw new DataException("Opção não implementada.");

            case 'm':
                throw new DataException("Opção não implementada.");

            case 's':
                throw new DataException("Opção não implementada.");
        }

        return intRetorno;
    }

}
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Exemplo de utiliza��o: Extenso teste = new Extenso(new BigDecimal(args[0]));
 * System.out.println("Numero : " + (new
 * DecimalFormat().format(Double.valueOf(args[0]))));
 * System.out.println("Extenso : " + teste.toString());
 */
public class Extenso
{
    private ArrayList nro;

    private BigInteger num;

    private boolean negativo;

    private String Qualificadores[][] = { { "centavo", "centavos" }, { "", "" }, { "mil", "mil" }, { "milh�o", "milh�es" }, { "bilh�o", "bilh�es" }, { "trilh�o", "trilh�es" }, { "quatrilh�o", "quatrilh�es" }, { "quintilh�o", "quintilh�es" }, { "sextilh�o", "sextilh�es" }, { "septilh�o", "septilh�es" } };

    private String Numeros[][] = { { "zero", "um", "dois", "tr�s", "quatro", "cinco", "seis", "sete", "oito", "nove", "dez", "onze", "doze", "treze", "quatorze", "quinze", "dezesseis", "dezessete", "dezoito", "dezenove" }, { "vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa" }, { "cem", "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos", "setecentos", "oitocentos", "novecentos" } };

    /**
     * Construtor
     */
    public Extenso()
    {
        nro = new ArrayList();
    }

    /**
     * Construtor
     * 
     * @param dec
     *            valor para colocar por extenso
     */
    public Extenso(BigDecimal dec)
    {
        this();
        setNumber(dec);
    }

    /**
     * Constructor for the Extenso object
     * 
     * @param dec
     *            valor para colocar por extenso
     */
    public Extenso(double dec)
    {
        this();
        setNumber(dec);
    }

    /**
     * Sets the Number attribute of the Extenso object
     * 
     * @param dec
     *            The new Number value
     */
    public void setNumber(BigDecimal dec)
    {
        negativo = (dec.doubleValue() < 0);

        if (negativo)
        {
            dec = new BigDecimal(dec.doubleValue() * -1);
        }

        // Converte para inteiro arredondando os centavos
        num = dec.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).toBigInteger();

        // Adiciona valores
        nro.clear();
        if (num.equals(BigInteger.ZERO))
        {
            // Centavos
            nro.add(new Integer(0));
            // Valor
            nro.add(new Integer(0));
        }
        else
        {
            // Adiciona centavos
            addRemainder(100);

            // Adiciona grupos de 1000
            while (!num.equals(BigInteger.ZERO))
            {

                addRemainder(1000);
            }
        }
    }

    public void setNumber(double dec)
    {
        setNumber(new BigDecimal(dec));
    }

    // /**
    // * Description of the Method
    // */
    // public void show() {
    // Iterator valores = nro.iterator();
    //
    // while (valores.hasNext()) {
    // System.out.println(((Integer) valores.next()).intValue());
    // }
    // System.out.println(toString());
    // }

    /**
     * Description of the Method
     * 
     * @return Description of the Returned Value
     */
    public String toString()
    {
        StringBuffer buf = new StringBuffer();

        int ct;

        for (ct = nro.size() - 1; ct > 0; ct--)
        {
            // Se ja existe texto e o atual n�o � zero
            if (buf.length() > 0 && !ehGrupoZero(ct))
            {
                buf.append(" e ");
            }
            buf.append(numToString(((Integer) nro.get(ct)).intValue(), ct));
        }
        if (buf.length() > 0)
        {
            if (ehUnicoGrupo())
                buf.append(" de ");
            while (buf.toString().endsWith(" "))
                buf.setLength(buf.length() - 1);
            if (ehPrimeiroGrupoUm())
                buf.insert(0, "h");
            if (nro.size() == 2 && ((Integer) nro.get(1)).intValue() == 1)
            {
                buf.append(" real");
            }
            else
            {
                buf.append(" reais");
            }
            if (((Integer) nro.get(0)).intValue() != 0)
            {
                buf.append(" e ");
            }
        }
        if (((Integer) nro.get(0)).intValue() != 0)
        {
            buf.append(numToString(((Integer) nro.get(0)).intValue(), 0));
        }

        if (negativo)
        {
            buf.insert(0, "menos ");
        }

        if (buf.length() == 0)
            return "zero";

        return buf.toString();
    }

    private boolean ehPrimeiroGrupoUm()
    {
        if (((Integer) nro.get(nro.size() - 1)).intValue() == 1)
            return true;
        return false;
    }

    /**
     * Adds a feature to the Remainder attribute of the Extenso object
     * 
     * @param divisor
     *            The feature to be added to the Remainder attribute
     */
    private void addRemainder(int divisor)
    {
        // Encontra newNum[0] = num modulo divisor, newNum[1] = num dividido
        // divisor
        BigInteger[] newNum = num.divideAndRemainder(BigInteger.valueOf(divisor));

        // Adiciona modulo
        nro.add(new Integer(newNum[1].intValue()));

        // Altera numero
        num = newNum[0];
    }

    /**
     * Description of the Method
     * 
     * @return Description of the Returned Value
     */
    private boolean ehUnicoGrupo()
    {
        if (nro.size() <= 3)
            return false;
        if (!ehGrupoZero(1) && !ehGrupoZero(2))
            return false;
        boolean hasOne = false;
        for (int i = 3; i < nro.size(); i++)
        {
            if (((Integer) nro.get(i)).intValue() != 0)
            {
                if (hasOne)
                    return false;
                hasOne = true;
            }
        }
        return true;
    }

    boolean ehGrupoZero(int ps)
    {
        if (ps <= 0 || ps >= nro.size())
            return true;
        return ((Integer) nro.get(ps)).intValue() == 0;
    }

    /**
     * Description of the Method
     * 
     * @param numero
     *            Description of Parameter
     * @param escala
     *            Description of Parameter
     * @return Description of the Returned Value
     */
    private String numToString(int numero, int escala)
    {
        int unidade = (numero % 10);
        int dezena = (numero % 100); // * nao pode dividir por 10 pois
        // verifica de 0..19
        int centena = (numero / 100);
        StringBuffer buf = new StringBuffer();

        if (numero != 0)
        {
            if (centena != 0)
            {
                if (dezena == 0 && centena == 1)
                {
                    buf.append(Numeros[2][0]);
                }
                else
                {
                    buf.append(Numeros[2][centena]);
                }
            }

            if ((buf.length() > 0) && (dezena != 0))
            {
                buf.append(" e ");
            }
            if (dezena > 19)
            {
                dezena /= 10;
                buf.append(Numeros[1][dezena - 2]);
                if (unidade != 0)
                {
                    buf.append(" e ");
                    buf.append(Numeros[0][unidade]);
                }
            }
            else if (centena == 0 || dezena != 0)
            {
                buf.append(Numeros[0][dezena]);
            }

            buf.append(" ");
            if (numero == 1)
            {
                buf.append(Qualificadores[escala][0]);
            }
            else
            {
                buf.append(Qualificadores[escala][1]);
            }
        }

        return buf.toString();
    }

    // /**
    // * Para teste
    // *
    // *@param args numero a ser convertido
    // */
    // public static void main(String[] args) {
    // if (args.length == 0) {
    // System.out.println("Sintax : ...Extenso <numero>");
    // return;
    // }
    // Extenso teste = new Extenso(new BigDecimal(args[0]));
    // System.out.println("Numero : " + ((Double.valueOf(args[0]))));
    // System.out.println("Extenso : " + teste.toString());
    // }
}

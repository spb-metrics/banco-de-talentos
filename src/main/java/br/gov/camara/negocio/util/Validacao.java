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

package br.gov.camara.negocio.util;

public class Validacao
{
    public static boolean validarCNPJCPF(final String s_aux)
    {
        boolean valido = false;

        //------- Rotina para CPF        
        if (s_aux.length() == 11)
        {
            int d1, d2;
            int digito1, digito2, resto;
            int digitoCPF;
            String nDigResult;
            d1 = d2 = 0;
            digito1 = digito2 = resto = 0;
            for (int n_Count = 1; n_Count < s_aux.length() - 1; n_Count++)
            {
                digitoCPF = Integer.valueOf(s_aux.substring(n_Count - 1, n_Count)).intValue();
                //--------- Multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.
                d1 = d1 + (11 - n_Count) * digitoCPF;
                //--------- Para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.
                d2 = d2 + (12 - n_Count) * digitoCPF;
            }

            //--------- Primeiro resto da divis�o por 11.
            resto = (d1 % 11);
            //--------- Se o resultado for 0 ou 1 o digito � 0 caso contr�rio o digito � 11 menos o resultado anterior.
            if (resto < 2)
            {
                digito1 = 0;
            }
            else
            {
                digito1 = 11 - resto;
            }
            d2 += 2 * digito1;
            //--------- Segundo resto da divis�o por 11.
            resto = (d2 % 11);
            //--------- Se o resultado for 0 ou 1 o digito � 0 caso contr�rio o digito � 11 menos o resultado anterior.
            if (resto < 2)
            {
                digito2 = 0;
            }
            else
            {
                digito2 = 11 - resto;
            }
            //--------- Digito verificador do CPF que est� sendo validado.
            String nDigVerific = s_aux.substring(s_aux.length() - 2, s_aux.length());
            //--------- Concatenando o primeiro resto com o segundo.
            nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
            //--------- Comparar o digito verificador do cpf com o primeiro resto + o segundo resto.
            valido = nDigVerific.equals(nDigResult);
        }
        //-------- Rotina para CNPJ        
        else if (s_aux.length() == 14)
        {
            int soma = 0, dig;
            String cnpj_calc = s_aux.substring(0, 12);
            char[] chr_cnpj = s_aux.toCharArray();

            //--------- Primeira parte
            for (int i = 0; i < 4; i++)
            {
                if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
                {
                    soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
                }
            }
            for (int i = 0; i < 8; i++)
            {
                if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9)
                {
                    soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
                }
            }
            dig = 11 - (soma % 11);
            cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

            //--------- Segunda parte
            soma = 0;
            for (int i = 0; i < 5; i++)
            {
                if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
                {
                    soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
                }
            }
            for (int i = 0; i < 8; i++)
            {
                if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9)
                {
                    soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
                }
            }
            dig = 11 - (soma % 11);
            cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

            valido = s_aux.equals(cnpj_calc);
        }

        return valido;
    }
}

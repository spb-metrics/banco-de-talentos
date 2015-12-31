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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Classe para compila��o e valida��o de express�es regulares
 */
public class ExpressaoRegular
{
    /**
     * Compila express�o regular (utilizada para saber se a express�o � v�lida)
     * 
     * @param strExpressaoRegular String contendo a express�o
     * 
     * @return boolean Contendo a valida��o
     */
    public static boolean compilar(String strExpressaoRegular)
    {
        try
        {
            Pattern.compile(strExpressaoRegular);
            return true;
        }
		catch (PatternSyntaxException pse)
		{
		    return false;
		}
    }

    /**
     * Valida um texto dada a express�o regular
     * 
     * @param strExpressaoRegular String contendo a express�o
     * @param strTexto String contendo o texto a ser validado
     * 
     * @return boolean Contendo a valida��o
     */
    public static boolean validar(String strExpressaoRegular, String strTexto)
    {
        // Declara��es
        Pattern patPattern = null;
        Matcher matMatcher = null;
        
        // Valida a m�scara        
        if ("".equals(strExpressaoRegular))
        {
            return true;                        
        }
        patPattern = Pattern.compile(strExpressaoRegular);
        matMatcher = patPattern.matcher(strTexto );
        if (matMatcher.matches())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

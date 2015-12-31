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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Classe para compilação e validação de expressões regulares
 */
public class ExpressaoRegular
{
    /**
     * Compila expressão regular (utilizada para saber se a expressão é válida)
     * 
     * @param strExpressaoRegular String contendo a expressão
     * 
     * @return boolean Contendo a validação
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
     * Valida um texto dada a expressão regular
     * 
     * @param strExpressaoRegular String contendo a expressão
     * @param strTexto String contendo o texto a ser validado
     * 
     * @return boolean Contendo a validação
     */
    public static boolean validar(String strExpressaoRegular, String strTexto)
    {
        // Declarações
        Pattern patPattern = null;
        Matcher matMatcher = null;
        
        // Valida a máscara        
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

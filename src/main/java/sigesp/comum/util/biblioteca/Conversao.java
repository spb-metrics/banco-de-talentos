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

package sigesp.comum.util.biblioteca;

import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe que contém funções genéricas de conversão
 */
public class Conversao
{
	// Inicializa o log
	private static Log log = LogFactory.getLog(Conversao.class);

	/**
	 * Função que separa partes de uma expressão dado um delimitador
	 *
	 * @param strExpressao Expressão a ser separada
	 * @param strDelimitador Delimitador que será utilizado para a separação
	 *
	 * @return Array de strings contendo as partes que foram separadas
	 */
	public static String[] split(String strExpressao, String strDelimitador)
	{
		// Registra log
		if (log.isDebugEnabled())
		{
			log.debug("Entrada no método");
		}

		StringTokenizer stkTokenizer = new StringTokenizer(strExpressao,
				strDelimitador);
		String[] strTemp = new String[stkTokenizer.countTokens()];

		int i = 0;

		// Armazena tokens em um array
		while (stkTokenizer.hasMoreTokens())
		{
			strTemp[i++] = stkTokenizer.nextToken();
		}

		// Retorno
		return strTemp;
	}

	/**
	 * Função que substitui numa string uma expressão por outra
	 *
	 * @param strString String contendo a expressão a ser substituída
	 * @param strExpressao Expressão a ser substituída
	 * @param strNovaExpressao Nova expressão
	 *
	 * @return Retorna uma nova string com contendo a expressão substituída
	 */
	public static String substituirExpressaoString(String strString,
		String strExpressao, String strNovaExpressao)
	{
		// Registra log
		if (log.isDebugEnabled())
		{
			log.debug("Entrada no método");
		}

		int intPosicao = -1;
		StringBuffer stbNovaString = new StringBuffer(strString);

		while ((intPosicao = stbNovaString.indexOf(strExpressao)) >= 0)
		{
			stbNovaString.replace(intPosicao,
				intPosicao + strExpressao.length(), strNovaExpressao);
		}

		return stbNovaString.toString();
	}

	/**
	 * formatarSomentePrimeiraMaiuscula: recebe uma expressão e retorna o novo
	 * nome com apenas a primeira letra maiúscula.
	 *
	 * @param strExpressao Expressao a formatar.
	 *
	 * @return Expressao formatada com a inicial em maiúsculo.
	 */
	public static String formatarSomentePrimeiraMaiuscula(String strExpressao)
	{
		// Registra log
		if (log.isDebugEnabled())
		{
			log.debug("Entrada no método");
		}

		String strRestante = strExpressao.substring(1).toLowerCase();
		String strPrimeiraLetra = "" + strExpressao.charAt(0);
		strPrimeiraLetra = strPrimeiraLetra.toUpperCase();

		return (strPrimeiraLetra + strRestante);
	}
}

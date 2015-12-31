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

package sigesp.comum.util.biblioteca;

import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe que cont�m fun��es gen�ricas de convers�o
 */
public class Conversao
{
	// Inicializa o log
	private static Log log = LogFactory.getLog(Conversao.class);

	/**
	 * Fun��o que separa partes de uma express�o dado um delimitador
	 *
	 * @param strExpressao Express�o a ser separada
	 * @param strDelimitador Delimitador que ser� utilizado para a separa��o
	 *
	 * @return Array de strings contendo as partes que foram separadas
	 */
	public static String[] split(String strExpressao, String strDelimitador)
	{
		// Registra log
		if (log.isDebugEnabled())
		{
			log.debug("Entrada no m�todo");
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
	 * Fun��o que substitui numa string uma express�o por outra
	 *
	 * @param strString String contendo a express�o a ser substitu�da
	 * @param strExpressao Express�o a ser substitu�da
	 * @param strNovaExpressao Nova express�o
	 *
	 * @return Retorna uma nova string com contendo a express�o substitu�da
	 */
	public static String substituirExpressaoString(String strString,
		String strExpressao, String strNovaExpressao)
	{
		// Registra log
		if (log.isDebugEnabled())
		{
			log.debug("Entrada no m�todo");
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
	 * formatarSomentePrimeiraMaiuscula: recebe uma express�o e retorna o novo
	 * nome com apenas a primeira letra mai�scula.
	 *
	 * @param strExpressao Expressao a formatar.
	 *
	 * @return Expressao formatada com a inicial em mai�sculo.
	 */
	public static String formatarSomentePrimeiraMaiuscula(String strExpressao)
	{
		// Registra log
		if (log.isDebugEnabled())
		{
			log.debug("Entrada no m�todo");
		}

		String strRestante = strExpressao.substring(1).toLowerCase();
		String strPrimeiraLetra = "" + strExpressao.charAt(0);
		strPrimeiraLetra = strPrimeiraLetra.toUpperCase();

		return (strPrimeiraLetra + strRestante);
	}
}

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
 * Created on 27/07/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.gov.camara.biblioteca.decorator;

/**
 * @author p_999126
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StringDecorator
{
    /**
     * Este m�todo � respons�vel por formatar os valores de um
     * pojo para sua apresenta��o.
     * @param obj - Object contendo o valor a ser formatado.
     * @return Retorna um objeto do tipo String com o valor
     * j� formatado.
     */
    public static String decorate(Object obj)
    {

        String strRetorno = "";

        if (obj != null)
        {

            if (obj instanceof String)
            {
                strRetorno = obj.toString().toUpperCase();
            }
            else if (obj instanceof Boolean)
            {
                if (((Boolean) obj).booleanValue() == true)
                {
                    strRetorno = "SIM";
                }
                else
                {
                    strRetorno = "N�O";
                }
            }
            else
            {
                strRetorno = obj.toString().toUpperCase();
            }
        }
        
        return strRetorno;
    }
}
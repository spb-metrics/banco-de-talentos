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

public class Mascara
{
    public static String retirarMascara(String strValor){
        String strRetorno = "";
        
        //Retira a m�scara
        strRetorno = strValor.replaceAll("\\.","");
        strRetorno = strRetorno.replaceAll("\\-","");
        strRetorno = strRetorno.replaceAll("\\/","");
        
        return strRetorno;
    }

    public static String formatarCPF(String strCPF)
    {
        String strRetorno = "";

        //Verifica o tamanho do parametro passado
        if (strCPF.length() != 11)
        {
            return "";
        }
        
        //Loop no String
        for (int i = 0; i < strCPF.length(); i++)        
        {
            strRetorno += strCPF.charAt(i);
            
            if(i == 2 || i == 5)
            {
                strRetorno += ".";                
            }
            else if (i == 8)
            {
                strRetorno += "-";
            }
        }
        
        return strRetorno;
    }

    public static String formatarCGC(String strCGC)
    {
        String strRetorno = "";
        
        //Verifica o tamanho do parametro passado
        if (strCGC.length() != 14)
        {
            return "";
        }
        
        //Loop no String
        for (int i = 0; i < strCGC.length(); i++)        
        {
            strRetorno += strCGC.charAt(i);
            
            if(i == 1 || i == 4)
            {
                strRetorno += ".";                
            }
            else if (i == 7)
            {
                strRetorno += "/";
            }
            else if (i == 11)
            {
                strRetorno += "-";
            }
        }
        
        return strRetorno;
    }
    
    public static String formatarCEP(String strCEP)
    {
        String strRetorno = "";

        //Verifica o tamanho do parametro passado
        if (strCEP.length() != 8)
        {
            return "";
        }
        
        //Loop no String
        for (int i = 0; i < strCEP.length(); i++)        
        {
            strRetorno += strCEP.charAt(i);
            
            if(i == 4)
            {
                strRetorno += "-";                
            }            
        }
        
        return strRetorno;        
    }    
}
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

public class Mascara
{
    public static String retirarMascara(String strValor){
        String strRetorno = "";
        
        //Retira a máscara
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
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

/**
 *
 * Classe para permitir consultas gen�ricas para servi�os
 */
public class Filtro extends Campo
{
    // Vari�veis de inst�ncia
    private int intTipo;
    private String strValorPadrao = "";
    private String strConectorWhere = "AND";
    
    /**
     * Obt�m tipo de dado
     * 
     * @return int Contendo o tipo de dado
     * 
     */
    public int getTipo()
    {
        return intTipo;
    }

    /**
     * Obt�m valor padr�o
     * 
     * @return String Contendo o valor padr�o
     * 
     */
    public String getValorPadrao()
    {
        return strValorPadrao;
    }

    /**
     * Atribui tipo de dado
     * 
     */
    public void setTipo(int intTipo)
    {
        this.intTipo = intTipo;
    }

    /**
     * Atribui valor padr�o
     * 
     */
    public void setValorPadrao(String strValorPadrao)
    {
        this.strValorPadrao = strValorPadrao;
    }
        
    /**
     * Obt�m o conector da cl�usula WHERE
     * 
     * @return String Contendo o conector da cl�usula WHERE
     * 
     */
    public String getConectorWhere()
    {
        return strConectorWhere;
    }

    /**
     * Atribui o tipo de conector na consulta (OR ou AND)
     */
    public void setConectorWhere(String strConectorWhere)
    {
        this.strConectorWhere = strConectorWhere;
    }

}

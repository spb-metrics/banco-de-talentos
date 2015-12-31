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

package br.gov.camara.negocio.util;

/**
 *
 * Classe para permitir consultas genéricas para serviços
 */
public class Filtro extends Campo
{
    // Variáveis de instância
    private int intTipo;
    private String strValorPadrao = "";
    private String strConectorWhere = "AND";
    
    /**
     * Obtém tipo de dado
     * 
     * @return int Contendo o tipo de dado
     * 
     */
    public int getTipo()
    {
        return intTipo;
    }

    /**
     * Obtém valor padrão
     * 
     * @return String Contendo o valor padrão
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
     * Atribui valor padrão
     * 
     */
    public void setValorPadrao(String strValorPadrao)
    {
        this.strValorPadrao = strValorPadrao;
    }
        
    /**
     * Obtém o conector da cláusula WHERE
     * 
     * @return String Contendo o conector da cláusula WHERE
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

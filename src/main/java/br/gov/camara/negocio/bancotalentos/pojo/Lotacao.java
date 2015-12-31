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

package br.gov.camara.negocio.bancotalentos.pojo;

import java.util.Calendar;

import br.gov.camara.biblioteca.util.DataNova;

/**
 * POJO para histórico de lotações
 */
public class Lotacao
{

    // Variáveis de instância
    private Integer intIdentificador;
    private String strDescricao;
    private Calendar calDataInicio;
    private Calendar calDataTermino;

    /**
     * Obtém data de inicio
     * 
     * @return Calendar Contendo o dado
     * 
     */
    public Calendar getDataInicio()
    {
        return calDataInicio;
    }

    /**
     * Obtém data de inicio formatada
     * 
     * @return Calendar Contendo o dado
     * 
     */
    public String getDataInicioFormatada()
    {
        return DataNova.format(calDataInicio);
    }

    /**
     * Obtém data de término
     * 
     * @return Calendar Contendo o dado
     * 
     */
    public Calendar getDataTermino()
    {
        return calDataTermino;
    }

    /**
     * Obtém data de término formatada
     * 
     * @return Calendar Contendo o dado
     * 
     */
    public String getDataTerminoFormatada()
    {
        return DataNova.format(calDataTermino);
    }

    /**
     * Obtém identificador
     * 
     * @return Integer Contendo o dado
     * 
     */
    public Integer getIdentificador()
    {
        return intIdentificador;
    }

    /**
     * Obtém descrição
     * 
     * @return String Contendo o dado
     * 
     */
    public String getDescricao()
    {
        return strDescricao;
    }

    /**
     * Preenche data de início
     * 
     * @param Calendar Com o dado desejado
     * 
     */
    public void setDataInicio(Calendar calDataInicio)
    {
        this.calDataInicio = calDataInicio;
    }

    /**
     * Preenche data de término
     * 
     * @param Calendar Como o dado desejado
     * 
     */
    public void setDataTermino(Calendar calDataTermino)
    {
        this.calDataTermino = calDataTermino;
    }

    /**
     * Preenche identificador
     * 
     * @param Integer Com o dado desejado
     * 
     */
    public void setIdentificador(Integer intIdentificador)
    {
        this.intIdentificador = intIdentificador;
    }

    /**
     * Atribui descrição
     * 
     * @param String valor da descrição
     * 
     */
    public void setDescricao(String strDescricao)
    {
        this.strDescricao = strDescricao;
    }

}
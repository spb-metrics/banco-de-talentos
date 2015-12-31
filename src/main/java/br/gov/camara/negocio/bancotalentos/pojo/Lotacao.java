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

package br.gov.camara.negocio.bancotalentos.pojo;

import java.util.Calendar;

import br.gov.camara.biblioteca.util.DataNova;

/**
 * POJO para hist�rico de lota��es
 */
public class Lotacao
{

    // Vari�veis de inst�ncia
    private Integer intIdentificador;
    private String strDescricao;
    private Calendar calDataInicio;
    private Calendar calDataTermino;

    /**
     * Obt�m data de inicio
     * 
     * @return Calendar Contendo o dado
     * 
     */
    public Calendar getDataInicio()
    {
        return calDataInicio;
    }

    /**
     * Obt�m data de inicio formatada
     * 
     * @return Calendar Contendo o dado
     * 
     */
    public String getDataInicioFormatada()
    {
        return DataNova.format(calDataInicio);
    }

    /**
     * Obt�m data de t�rmino
     * 
     * @return Calendar Contendo o dado
     * 
     */
    public Calendar getDataTermino()
    {
        return calDataTermino;
    }

    /**
     * Obt�m data de t�rmino formatada
     * 
     * @return Calendar Contendo o dado
     * 
     */
    public String getDataTerminoFormatada()
    {
        return DataNova.format(calDataTermino);
    }

    /**
     * Obt�m identificador
     * 
     * @return Integer Contendo o dado
     * 
     */
    public Integer getIdentificador()
    {
        return intIdentificador;
    }

    /**
     * Obt�m descri��o
     * 
     * @return String Contendo o dado
     * 
     */
    public String getDescricao()
    {
        return strDescricao;
    }

    /**
     * Preenche data de in�cio
     * 
     * @param Calendar Com o dado desejado
     * 
     */
    public void setDataInicio(Calendar calDataInicio)
    {
        this.calDataInicio = calDataInicio;
    }

    /**
     * Preenche data de t�rmino
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
     * Atribui descri��o
     * 
     * @param String valor da descri��o
     * 
     */
    public void setDescricao(String strDescricao)
    {
        this.strDescricao = strDescricao;
    }

}
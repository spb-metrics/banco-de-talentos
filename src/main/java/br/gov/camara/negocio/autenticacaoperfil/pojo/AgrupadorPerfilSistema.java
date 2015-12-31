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

package br.gov.camara.negocio.autenticacaoperfil.pojo;

import java.util.Calendar;

import br.gov.camara.hibernate.interceptor.InterceptorUltimaAtualizacao;

/**
 * POJO para perfil de sistema
 *  
 */

public class AgrupadorPerfilSistema implements InterceptorUltimaAtualizacao
{

    // Vari�veis de inst�ncia
    private Integer intIdentificador;
    private AgrupadorPerfil objAgrupadorPerfil;
    private Sistema objSistema;
    private Calendar calDataUltimaAtualizacao;
    private String strUsuarioUltimaAtualizacao;

    /**
     * Obt�m Data da �ltima atualiza��o do registro
     * 
     * @return Calendar Contendo o dado
     * 
     */
    public Calendar getDataUltimaAtualizacao()
    {
        return calDataUltimaAtualizacao;
    }

    /**
     * Obt�m Usu�rio que realizou a �ltima atualiza�ao do registro
     * 
     * @return UsuarioSistema Contendo o dado
     * 
     */
    public String getUsuarioUltimaAtualizacao()
    {
        return strUsuarioUltimaAtualizacao;
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
     * Obt�m o Agrupador do Perfil
     * 
     * @return AgrupadorPerfil Contendo o dado
     * 
     */
    public AgrupadorPerfil getAgrupadorPerfil()
    {
        return objAgrupadorPerfil;
    }

    /**
     * Obt�m o Perfil do Sistema
     * 
     * @return Sistema Contendo o dado
     * 
     */
    public Sistema getSistema()
    {
        return objSistema;
    }

    /**
     * Atribui identificador
     *  
     * @param Integer Com o dado desejado
     * 
     */
    public void setIdentificador(Integer intIdentificador)
    {
        this.intIdentificador = intIdentificador;
    }

    /**
     * Atribui Agrupador do perfil
     *  
     * @param AgrupadorPerfil Com o dado desejado
     * 
     */
    public void setAgrupadorPerfil(AgrupadorPerfil objAgrupadorPerfil)
    {
        this.objAgrupadorPerfil = objAgrupadorPerfil;
    }

    /**
     * Atribui Sistema
     *  
     * @param Cracha Com o dado desejado
     * 
     */
    public void setSistema(Sistema objSistema)
    {
        this.objSistema = objSistema;
    }

    /**
     * @param calDataUltimaAtualizacao Data da �ltima atualiza��o do registro.
     */
    public void setDataUltimaAtualizacao(Calendar calDataUltimaAtualizacao)
    {
        this.calDataUltimaAtualizacao = calDataUltimaAtualizacao;
    }

    /**
     * @param objUsuarioUltimaAtualizacao Usu�rio que realizou a �ltima atualiza��o do registro.
     */
    public void setUsuarioUltimaAtualizacao(String strUsuarioUltimaAtualizacao)
    {
        this.strUsuarioUltimaAtualizacao = strUsuarioUltimaAtualizacao;
    }

}
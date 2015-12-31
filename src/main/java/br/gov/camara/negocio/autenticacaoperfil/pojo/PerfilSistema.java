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
import java.util.Set;

import br.gov.camara.hibernate.interceptor.InterceptorHistoricoAtualizacao;
import br.gov.camara.hibernate.interceptor.InterceptorUltimaAtualizacao;

/**
 * POJO para perfil de sistema
 *  
 */

public class PerfilSistema implements InterceptorUltimaAtualizacao, InterceptorHistoricoAtualizacao
{

    // Vari�veis de inst�ncia
    private Integer intIdentificador;
    private String strNome;
    private String strDescricao;
    private String strObjetoControlado;
    private Sistema objSistema;
    private PerfilSistema objPerfilAgrupador;
    private Set setGrupos;
    private Set setFuncionalidades;
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
     * Obt�m nome do perfil
     * 
     * @return String Contendo o dado
     * 
     */
    public String getNome()
    {
        return strNome;
    }

    /**
     * Obt�m nome do perfil
     * 
     * @return String Contendo o dado
     * 
     */
    public String getDescricao()
    {
        return strDescricao;
    }

    /**
     * Obt�m objeto controlado
     * 
     * @return String Contendo o dado
     * 
     */
    public String getObjetoControlado()
    {
        return strObjetoControlado;
    }

    /**
     * Obt�m sistema
     * 
     * @return Sistema Contendo o dado
     * 
     */
    public Sistema getSistema()
    {
        return objSistema;
    }

    /**
     * Obt�m grupos
     * 
     * @return Set Contendo o dado
     * 
     */
    public Set getGrupos()
    {
        return setGrupos;
    }

    /**
     * Obt�m funcionalidades
     * 
     * @return Set Contendo o dado
     * 
     */
    public Set getFuncionalidades()
    {
        return setFuncionalidades;
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
     * Atribui nome do perfil
     *  
     * @param String Com o dado desejado
     * 
     */
    public void setNome(String strNome)
    {
        this.strNome = strNome;
    }

    /**
     * Atribui nome do perfil
     *  
     * @param String Com o dado desejado
     * 
     */
    public void setDescricao(String strDescricao)
    {
        this.strDescricao = strDescricao;
    }

    /**
     * Atribui objeto controlado
     *  
     * @param String Com o dado desejado
     * 
     */
    public void setObjetoControlado(String strObjetoControlado)
    {
        this.strObjetoControlado = strObjetoControlado;
    }

    /**
     * Atribui sistema
     *  
     * @param ConsultaPermissoes Com o dado desejado
     * 
     */
    public void setSistema(Sistema objSistema)
    {
        this.objSistema = objSistema;
    }

    /**
     * Atribui grupos
     *  
     * @param String valor do nome
     * 
     */
    public void setGrupos(Set setGrupos)
    {
        this.setGrupos = setGrupos;
    }

    /**
     * Atribui funcionalidades
     *  
     * @param String valor do nome
     * 
     */
    public void setFuncionalidades(Set setFuncionalidades)
    {
        this.setFuncionalidades = setFuncionalidades;
    }

    /**
     * @return Returns the objPerfilAgrupador.
     */
    public PerfilSistema getPerfilAgrupador()
    {
        return this.objPerfilAgrupador;
    }

    /**
     * @param objPerfilAgrupador The objPerfilAgrupador to set.
     */
    public void setPerfilAgrupador(PerfilSistema objPerfilAgrupador)
    {
        this.objPerfilAgrupador = objPerfilAgrupador;
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

    public String getChaveEntidade()
    {
        return getIdentificador().toString();
    }

}
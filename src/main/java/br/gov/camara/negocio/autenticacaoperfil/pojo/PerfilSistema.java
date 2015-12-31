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

    // Variáveis de instância
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
     * Obtém Data da última atualização do registro
     * 
     * @return Calendar Contendo o dado
     * 
     */
    public Calendar getDataUltimaAtualizacao()
    {
        return calDataUltimaAtualizacao;
    }

    /**
     * Obtém Usuário que realizou a última atualizaçao do registro
     * 
     * @return UsuarioSistema Contendo o dado
     * 
     */
    public String getUsuarioUltimaAtualizacao()
    {
        return strUsuarioUltimaAtualizacao;
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
     * Obtém nome do perfil
     * 
     * @return String Contendo o dado
     * 
     */
    public String getNome()
    {
        return strNome;
    }

    /**
     * Obtém nome do perfil
     * 
     * @return String Contendo o dado
     * 
     */
    public String getDescricao()
    {
        return strDescricao;
    }

    /**
     * Obtém objeto controlado
     * 
     * @return String Contendo o dado
     * 
     */
    public String getObjetoControlado()
    {
        return strObjetoControlado;
    }

    /**
     * Obtém sistema
     * 
     * @return Sistema Contendo o dado
     * 
     */
    public Sistema getSistema()
    {
        return objSistema;
    }

    /**
     * Obtém grupos
     * 
     * @return Set Contendo o dado
     * 
     */
    public Set getGrupos()
    {
        return setGrupos;
    }

    /**
     * Obtém funcionalidades
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
     * @param calDataUltimaAtualizacao Data da última atualização do registro.
     */
    public void setDataUltimaAtualizacao(Calendar calDataUltimaAtualizacao)
    {
        this.calDataUltimaAtualizacao = calDataUltimaAtualizacao;
    }

    /**
     * @param objUsuarioUltimaAtualizacao Usuário que realizou a última atualização do registro.
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
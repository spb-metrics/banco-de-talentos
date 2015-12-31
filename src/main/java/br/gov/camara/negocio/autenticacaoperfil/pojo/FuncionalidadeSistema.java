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

import br.gov.camara.hibernate.interceptor.InterceptorHistoricoAtualizacao;
import br.gov.camara.hibernate.interceptor.InterceptorUltimaAtualizacao;

/**
 * POJO para perfil de sistema
 *  
 */

public class FuncionalidadeSistema implements InterceptorUltimaAtualizacao, InterceptorHistoricoAtualizacao
{

    // Variáveis de instância
    private Integer intIdentificador;
    private String strNome;
    private String strDescricao;
    private String strObjetoControlado;
    private Sistema objSistema;
    private FuncionalidadeSistema objFuncionalidadeAgrupadora;
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
     * @return Returns the strDescricao.
     */
    public String getDescricao()
    {
        return strDescricao;
    }

    /**
     * @param strDescricao The strDescricao to set.
     */
    public void setDescricao(String strDescricao)
    {
        this.strDescricao = strDescricao;
    }

    /**
     * @return Returns the objFuncionalidadeAgrupadora.
     */
    public FuncionalidadeSistema getFuncionalidadeAgrupadora()
    {
        return objFuncionalidadeAgrupadora;
    }

    /**
     * @param objFuncionalidadeAgrupadora The objFuncionalidadeAgrupadora to set.
     */
    public void setFuncionalidadeAgrupadora(FuncionalidadeSistema objFuncionalidadeAgrupadora)
    {
        this.objFuncionalidadeAgrupadora = objFuncionalidadeAgrupadora;
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
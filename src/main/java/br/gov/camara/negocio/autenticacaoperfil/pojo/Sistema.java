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

public class Sistema implements InterceptorUltimaAtualizacao, InterceptorHistoricoAtualizacao
{

    // Variáveis de instância
    private Integer intIdentificador;
    private String strNome;
    private String strDescricao;
    private Sistema objSistemaAgrupador;
    private String strObjetoControlado;
    private String strVisibilidade = "N";
    private String strAcesso;
    private String strSigla;
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
     * @return the acesso
     */
    public String getAcesso()
    {
        return this.strAcesso;
    }

    /**
     * @return the sigla
     */
    public String getSigla()
    {
        return this.strSigla;
    }

    /**
     * @param acesso the acesso to set
     */
    public void setAcesso(String acesso)
    {
        this.strAcesso = acesso;
    }

    /**
     * @param sigla the acesso to set
     */
    public void setSigla(String strSigla)
    {
        this.strSigla = strSigla;
    }

    /**
     * @return the visibilidade
     */
    public String getVisibilidade()
    {
        return this.strVisibilidade;
    }

    /**
     * @param visibilidade the visibilidade to set
     */
    public void setVisibilidade(String visibilidade)
    {
        this.strVisibilidade = visibilidade;
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
     * Obtém descrição do perfil
     * 
     * @return String Contendo o dado
     * 
     */
    public String getDescricao()
    {
        return strDescricao;
    }

    /**
     * Obtém sistema agrupador
     * 
     * @return String Contendo o dado
     * 
     */
    public Sistema getSistemaAgrupador()
    {
        return objSistemaAgrupador;
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
     * Atribui identificador
     *  
     * @param int valor do Identificador
     * 
     */
    public void setIdentificador(Integer intIdentificador)
    {
        this.intIdentificador = intIdentificador;
    }

    /**
     * Atribui nome do perfil
     *  
     * @param String valor do nome
     * 
     */
    public void setNome(String strNome)
    {
        this.strNome = strNome;
    }

    /**
     * Atribui descrição do perfil
     *  
     * @param String valor da descrição do sistema
     * 
     */
    public void setDescricao(String strDescricao)
    {
        this.strDescricao = strDescricao;
    }

    /**
     * Atribui sistema agrupador
     *  
     * @param ConsultaPermissoes valor do sistema agrupador
     * 
     */
    public void setSistemaAgrupador(Sistema objSistemaAgrupador)
    {
        this.objSistemaAgrupador = objSistemaAgrupador;
    }

    /**
     * Atribui objeto controlado
     *  
     * @param String valor do nome
     * 
     */
    public void setObjetoControlado(String strObjetoControlado)
    {
        this.strObjetoControlado = strObjetoControlado;
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
        return this.intIdentificador.toString();
    }
}

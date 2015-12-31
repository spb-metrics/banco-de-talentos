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
import java.util.Date;

import br.gov.camara.hibernate.interceptor.InterceptorHistoricoAtualizacao;
import br.gov.camara.hibernate.interceptor.InterceptorUltimaAtualizacao;

/**
 * POJO para usuário de sistema
 *  
 */

public class UsuarioSistema implements InterceptorUltimaAtualizacao, InterceptorHistoricoAtualizacao
{

    // Variáveis de instância
    private Integer intIdentificador;
    private String strNome;
    private String strLogin;
    private String strSenhaHash;
    private String strExpiracaoSenha;
    private Date datAtualizacaoSenha;
    private Date datValidade;
    private Date datDesligamento;
    private Grupo objGrupo;
    private String strChaveConsulta;
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
        return this.calDataUltimaAtualizacao;
    }

    /**
     * Obtém Usuário que realizou a última atualizaçao do registro
     * 
     * @return UsuarioSistema Contendo o dado
     * 
     */
    public String getUsuarioUltimaAtualizacao()
    {
        return this.strUsuarioUltimaAtualizacao;
    }

    /**
     * Obtém identificador
     * 
     * @return Integer Contendo o dado
     * 
     */
    public Integer getIdentificador()
    {
        return this.intIdentificador;
    }

    /**
     * Obtém nome do usuário
     * 
     * @return String Contendo o dado
     * 
     */
    public String getNome()
    {
        return this.strNome;
    }

    /**
     * Obtém login
     * 
     * @return String Contendo o dado
     * 
     */
    public String getLogin()
    {
        return this.strLogin;
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
     * Atribui nome do usuário
     *  
     * @param String valor do nome
     * 
     */
    public void setNome(String strNome)
    {
        this.strNome = strNome;
    }

    /**
     * Atribui login
     *  
     * @param String valor do nome
     * 
     */
    public void setLogin(String strLogin)
    {
        this.strLogin = strLogin;
    }

    /**
     * @return Returns the datAtualizacaoSenha.
     */
    public Date getDataAtualizacaoSenha()
    {
        return this.datAtualizacaoSenha;
    }

    /**
     * @param datAtualizacaoSenha The datAtualizacaoSenha to set.
     */
    public void setDataAtualizacaoSenha(Date datAtualizacaoSenha)
    {
        this.datAtualizacaoSenha = datAtualizacaoSenha;
    }

    /**
     * @return Returns the datDesligamento.
     */
    public Date getDataDesligamento()
    {
        return this.datDesligamento;
    }

    /**
     * @param datDesligamento The datDesligamento to set.
     */
    public void setDataDesligamento(Date datDesligamento)
    {
        this.datDesligamento = datDesligamento;
    }

    /**
     * @return Returns the datValidade.
     */
    public Date getDataValidade()
    {
        return this.datValidade;
    }

    /**
     * @param datValidade The datValidade to set.
     */
    public void setDataValidade(Date datValidade)
    {
        this.datValidade = datValidade;
    }

    /**
     * @return Returns the objGrupo.
     */
    public Grupo getGrupo()
    {
        return this.objGrupo;
    }

    /**
     * @param objGrupo The objGrupo to set.
     */
    public void setGrupo(Grupo objGrupo)
    {
        this.objGrupo = objGrupo;
    }

    /**
     * @return Returns the strExpiracaoSenha.
     */
    public String getExpiracaoSenha()
    {
        return this.strExpiracaoSenha;
    }

    /**
     * @param strExpiracaoSenha The strExpiracaoSenha to set.
     */
    public void setExpiracaoSenha(String strExpiracaoSenha)
    {
        this.strExpiracaoSenha = strExpiracaoSenha;
    }

    /**
     * @return Returns the strSenhaHash.
     */
    public String getSenhaHash()
    {
        return this.strSenhaHash;
    }

    /**
     * @param strSenhaHash The strSenhaHash to set.
     */
    public void setSenhaHash(String strSenhaHash)
    {
        this.strSenhaHash = strSenhaHash;
    }

    /**
     * @return Returns the strChaveConsulta.
     */
    public String getChaveConsulta()
    {
        return this.strChaveConsulta;
    }

    /**
     * @param strChaveConsulta The strChaveConsulta to set.
     */
    public void setChaveConsulta(String strChaveConsulta)
    {
        this.strChaveConsulta = strChaveConsulta;
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
        return this.getIdentificador().toString();
    }

}
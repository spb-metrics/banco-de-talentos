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
import java.util.Date;

import br.gov.camara.hibernate.interceptor.InterceptorHistoricoAtualizacao;
import br.gov.camara.hibernate.interceptor.InterceptorUltimaAtualizacao;

/**
 * POJO para usu�rio de sistema
 *  
 */

public class UsuarioSistema implements InterceptorUltimaAtualizacao, InterceptorHistoricoAtualizacao
{

    // Vari�veis de inst�ncia
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
     * Obt�m Data da �ltima atualiza��o do registro
     * 
     * @return Calendar Contendo o dado
     * 
     */
    public Calendar getDataUltimaAtualizacao()
    {
        return this.calDataUltimaAtualizacao;
    }

    /**
     * Obt�m Usu�rio que realizou a �ltima atualiza�ao do registro
     * 
     * @return UsuarioSistema Contendo o dado
     * 
     */
    public String getUsuarioUltimaAtualizacao()
    {
        return this.strUsuarioUltimaAtualizacao;
    }

    /**
     * Obt�m identificador
     * 
     * @return Integer Contendo o dado
     * 
     */
    public Integer getIdentificador()
    {
        return this.intIdentificador;
    }

    /**
     * Obt�m nome do usu�rio
     * 
     * @return String Contendo o dado
     * 
     */
    public String getNome()
    {
        return this.strNome;
    }

    /**
     * Obt�m login
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
     * Atribui nome do usu�rio
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
        return this.getIdentificador().toString();
    }

}
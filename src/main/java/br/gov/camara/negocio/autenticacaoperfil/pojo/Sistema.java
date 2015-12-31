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

import br.gov.camara.hibernate.interceptor.InterceptorHistoricoAtualizacao;
import br.gov.camara.hibernate.interceptor.InterceptorUltimaAtualizacao;

public class Sistema implements InterceptorUltimaAtualizacao, InterceptorHistoricoAtualizacao
{

    // Vari�veis de inst�ncia
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
     * Obt�m descri��o do perfil
     * 
     * @return String Contendo o dado
     * 
     */
    public String getDescricao()
    {
        return strDescricao;
    }

    /**
     * Obt�m sistema agrupador
     * 
     * @return String Contendo o dado
     * 
     */
    public Sistema getSistemaAgrupador()
    {
        return objSistemaAgrupador;
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
     * Atribui descri��o do perfil
     *  
     * @param String valor da descri��o do sistema
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
        return this.intIdentificador.toString();
    }
}

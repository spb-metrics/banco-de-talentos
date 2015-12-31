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

public class CriterioAcessoPerfil implements InterceptorUltimaAtualizacao, InterceptorHistoricoAtualizacao
{

    //  Variáveis de instância
    private Integer intIdentificador;
    private PerfilSistema objPerfilSistema;
    private CriterioAcesso objCriterioAcesso;
    private String strValorCriterioAcessoPerfil;
    private String strNomeCriterioAcessoPerfil;
    private Calendar calDataUltimaAtualizacao;
    private String strUsuarioUltimaAtualizacao;
    private String strPreRequisito;

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
     * Obtém o pré-requisito
     * 
     * @return String contendo o dado
     * 
     */
    public String getPreRequisito()
    {
        return strPreRequisito;
    }
    
    /**
     * Obtém a chave
     * 
     * @return String identificador do registro 
     * 
     */
    public Integer getIdentificador()
    {
        return intIdentificador;
    }

    /**
     * Obtém Nome do critério
     *  
     * @return String nome do critério
     * 
     */
    public PerfilSistema getPerfilSistema()
    {
        return objPerfilSistema;
    }

    /**
     * Obtém Classe implementadora
     * 
     * @return nome da classe implementadora 
     * 
     */
    public CriterioAcesso getCriterioAcesso()
    {
        return objCriterioAcesso;
    }

    /**
     * Preenche identificador
     * 
     * @param intIdentificador Com o dado desejado
     * 
     */
    public void setIdentificador(Integer intIdentificador)
    {
        this.intIdentificador = intIdentificador;
    }

    /**
     * Preenche nome do critério
     * 
     * @param strNome Com o dado desejado
     * 
     */
    public void setPerfilSistema(PerfilSistema objPerfilSistema)
    {
        this.objPerfilSistema = objPerfilSistema;
    }

    /**
     * Preenche Classe implementadora
     * 
     * @param String Com o dado desejado
     * 
     */
    public void setCriterioAcesso(CriterioAcesso objCriterioAcesso)
    {
        this.objCriterioAcesso = objCriterioAcesso;
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

    public String getNomeCriterioAcessoPerfil()
    {
        return strNomeCriterioAcessoPerfil;
    }

    public void setNomeCriterioAcessoPerfil(String strNomeCriterioAcessoPerfil)
    {
        this.strNomeCriterioAcessoPerfil = strNomeCriterioAcessoPerfil;
    }

    public String getValorCriterioAcessoPerfil()
    {
        return strValorCriterioAcessoPerfil;
    }

    public void setValorCriterioAcessoPerfil(String strValorCriterioAcessoPerfil)
    {
        this.strValorCriterioAcessoPerfil = strValorCriterioAcessoPerfil;
    }

    public void setPreRequisito(String strPreRequisito)
    {
        this.strPreRequisito = strPreRequisito;
    }

    public String getChaveEntidade()
    {
        return getCriterioAcesso().toString();
    }
}

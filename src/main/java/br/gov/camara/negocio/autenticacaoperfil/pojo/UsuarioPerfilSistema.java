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

import java.io.Serializable;
import java.util.Calendar;

import br.gov.camara.hibernate.interceptor.InterceptorHistoricoAtualizacao;
import br.gov.camara.hibernate.interceptor.InterceptorUltimaAtualizacao;

public class UsuarioPerfilSistema implements Serializable, Comparable, InterceptorUltimaAtualizacao, InterceptorHistoricoAtualizacao
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    //  Variáveis de instância
    private PerfilSistema objPerfilSistema;
    private UsuarioSistema objUsuarioSistema;
    private String strIndicativoGestor;
    private Calendar calDataInicioValidade;
    private Calendar calDataTerminoValidade;
    private Integer intUsuarioPermissaoAcesso;
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
     * Obtém Objeto Perfil sistema
     * 
     * @return UsuarioSistema Contendo o dado 
     * 
     */
    public PerfilSistema getPerfilSistema()
    {
        return objPerfilSistema;
    }

    /**
     * Obtém Objeto Usuario sistema
     * 
     * @return UsuarioSistema Contendo o dado 
     * 
     */
    public UsuarioSistema getUsuarioSistema()
    {
        return objUsuarioSistema;
    }

    /**
     * Obtém Objeto IndicativoGestor
     * 
     * @return Sistema Contendo o dado 
     * 
     */
    public String getIndicativoGestor()
    {
        return strIndicativoGestor;
    }

    /**
     * Obtém Objeto DataInicioValidade
     * 
     * @return Sistema Contendo o dado 
     * 
     */
    public Calendar getDataInicioValidade()
    {
        return calDataInicioValidade;
    }

    /**
     * Obtém Objeto DataTerminoValidade
     * 
     * @return Sistema Contendo o dado 
     * 
     */
    public Calendar getDataTerminoValidade()
    {
        return calDataTerminoValidade;
    }

    /**
     * Preenche Objeto de Perfil Sistema
     * 
     * @param objUsuarioSistema Com o dado desejado
     * 
     */
    public void setPerfilSistema(PerfilSistema objPerfilSistema)
    {
        this.objPerfilSistema = objPerfilSistema;
    }

    /**
     * Preenche Objeto de Usuario Sistema
     * 
     * @param objSistema Com o dado desejado
     * 
     */
    public void setUsuarioSistema(UsuarioSistema objUsuarioSistema)
    {
        this.objUsuarioSistema = objUsuarioSistema;
    }

    /**
     * Preenche Indicativo de Administrador do sistema
     * 
     * @param String Com o dado desejado
     * 
     */
    public void setIndicativoGestor(String strIndicativoGestor)
    {
        this.strIndicativoGestor = strIndicativoGestor;
    }

    public Integer getUsuarioPermissaoAcesso()
    {
        return intUsuarioPermissaoAcesso;
    }

    public void setUsuarioPermissaoAcesso(Integer intUsuarioPermissaoAcesso)
    {
        this.intUsuarioPermissaoAcesso = intUsuarioPermissaoAcesso;
    }
    
    /**
     * Preenche Data de início da validade
     * 
     * @param String Com o dado desejado
     * 
     */
    public void setDataInicioValidade(Calendar calDataInicioValidade)
    {
        this.calDataInicioValidade = calDataInicioValidade;
    }

    /**
     * Preenche Data de término da validade
     * 
     * @param String Com o dado desejado
     * 
     */
    public void setDataTerminoValidade(Calendar calDataTerminoValidade)
    {
        this.calDataTerminoValidade = calDataTerminoValidade;
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

    public boolean equals(Object obj)
    {
        UsuarioPerfilSistema usuarioPerfilSistema = (UsuarioPerfilSistema) obj;

        if (getPerfilSistema() == null
                || getUsuarioSistema() == null
                || usuarioPerfilSistema.getPerfilSistema() == null
                || usuarioPerfilSistema.getUsuarioSistema() == null)
        {
            return false;
        }
        else
        {
            if (usuarioPerfilSistema.getPerfilSistema().equals(this.getPerfilSistema())
                    && usuarioPerfilSistema.getUsuarioSistema().equals(this.getUsuarioSistema()))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

    }

    public int hashCode()
    {
        return objPerfilSistema.hashCode();
    }

    public String toString()
    {
        if (getPerfilSistema() == null || getUsuarioSistema() == null)
        {
            return this.getIndicativoGestor() + "";
        }
        else
        {
            return this.getIndicativoGestor() + this.getPerfilSistema().toString() + this.getUsuarioSistema().toString();
        }
    }

    public int compareTo(Object o)
    {
        return this.toString().compareTo(o.toString());
    }

    public String getChaveEntidade()
    {
        return "P:" + getPerfilSistema().getIdentificador().toString() + " - U:" + getUsuarioSistema().getIdentificador().toString();
    }


}

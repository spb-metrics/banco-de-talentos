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

    //  Vari�veis de inst�ncia
    private PerfilSistema objPerfilSistema;
    private UsuarioSistema objUsuarioSistema;
    private String strIndicativoGestor;
    private Calendar calDataInicioValidade;
    private Calendar calDataTerminoValidade;
    private Integer intUsuarioPermissaoAcesso;
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
     * Obt�m Objeto Perfil sistema
     * 
     * @return UsuarioSistema Contendo o dado 
     * 
     */
    public PerfilSistema getPerfilSistema()
    {
        return objPerfilSistema;
    }

    /**
     * Obt�m Objeto Usuario sistema
     * 
     * @return UsuarioSistema Contendo o dado 
     * 
     */
    public UsuarioSistema getUsuarioSistema()
    {
        return objUsuarioSistema;
    }

    /**
     * Obt�m Objeto IndicativoGestor
     * 
     * @return Sistema Contendo o dado 
     * 
     */
    public String getIndicativoGestor()
    {
        return strIndicativoGestor;
    }

    /**
     * Obt�m Objeto DataInicioValidade
     * 
     * @return Sistema Contendo o dado 
     * 
     */
    public Calendar getDataInicioValidade()
    {
        return calDataInicioValidade;
    }

    /**
     * Obt�m Objeto DataTerminoValidade
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
     * Preenche Data de in�cio da validade
     * 
     * @param String Com o dado desejado
     * 
     */
    public void setDataInicioValidade(Calendar calDataInicioValidade)
    {
        this.calDataInicioValidade = calDataInicioValidade;
    }

    /**
     * Preenche Data de t�rmino da validade
     * 
     * @param String Com o dado desejado
     * 
     */
    public void setDataTerminoValidade(Calendar calDataTerminoValidade)
    {
        this.calDataTerminoValidade = calDataTerminoValidade;
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

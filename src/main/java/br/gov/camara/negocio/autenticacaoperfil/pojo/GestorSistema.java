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

public class GestorSistema implements Serializable, Comparable, InterceptorUltimaAtualizacao, InterceptorHistoricoAtualizacao
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    //  Variáveis de instância
    private UsuarioSistema objUsuarioSistema;
    private Sistema objSistema;
    private String strIndicativoAdministrador;
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
     * Obtém Objeto Sistema
     * 
     * @return Sistema Contendo o dado 
     * 
     */
    public Sistema getSistema()
    {
        return objSistema;
    }

    /**
     * Obtém Objeto IndicativoAdministrador
     * 
     * @return Sistema Contendo o dado 
     * 
     */
    public String getIndicativoAdministrador()
    {
        return strIndicativoAdministrador;
    }

    /**
     * Obtém Indicativo de Administrador do sistema
     * 
     * @return String Contendo o dado 
     * 
     */
    public String strIndicativoAdministrador()
    {
        return strIndicativoAdministrador;
    }

    /**
     * Preenche Objeto de Usuario de Sistema
     * 
     * @param objUsuarioSistema Com o dado desejado
     * 
     */
    public void setUsuarioSistema(UsuarioSistema objUsuarioSistema)
    {
        this.objUsuarioSistema = objUsuarioSistema;
    }

    /**
     * Preenche Objeto de Sistema
     * 
     * @param objSistema Com o dado desejado
     * 
     */
    public void setSistema(Sistema objSistema)
    {
        this.objSistema = objSistema;
    }

    /**
     * Preenche Indicativo de Administrador do sistema
     * 
     * @param String Com o dado desejado
     * 
     */
    public void setIndicativoAdministrador(String strIndicativoAdministrador)
    {
        this.strIndicativoAdministrador = strIndicativoAdministrador;
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
        GestorSistema gestorSistema = (GestorSistema) obj;

        if (getSistema() == null || getUsuarioSistema() == null || gestorSistema.getSistema() == null || gestorSistema.getUsuarioSistema() == null)
        {
            return false;
        }
        else
        {
            if (gestorSistema.getSistema().equals(this.getSistema()) && gestorSistema.getUsuarioSistema().equals(this.getUsuarioSistema()))
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
        return objSistema.hashCode();
    }

    public String toString()
    {
        if (getSistema() == null || getUsuarioSistema() == null)
        {
            return this.getIndicativoAdministrador() + "";
        }
        else
        {
            return this.getIndicativoAdministrador() + this.getSistema().toString() + this.getUsuarioSistema().toString();
        }
    }

    public int compareTo(Object o)
    {
        return this.toString().compareTo(o.toString());
    }

    public String getChaveEntidade()
    {
        return "U:"+ getUsuarioSistema().getIdentificador().toString() + " - " + "S:" + getSistema().getIdentificador().toString();
    }

}

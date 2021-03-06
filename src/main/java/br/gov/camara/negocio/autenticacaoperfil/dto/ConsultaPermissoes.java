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

package br.gov.camara.negocio.autenticacaoperfil.dto;

import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioPerfilSistema;

public class ConsultaPermissoes
{

    private String strDescricao;
    private boolean blnPossuiPermissao;
    private UsuarioPerfilSistema objUsuarioPerfilSistema;

    public boolean isPossuiPermissao()
    {
        return blnPossuiPermissao;
    }

    public void setPossuiPermissao(boolean blnPossuiPermissao)
    {
        this.blnPossuiPermissao = blnPossuiPermissao;
    }

    public UsuarioPerfilSistema getUsuarioPerfilSistema()
    {
        return objUsuarioPerfilSistema;
    }

    public void setUsuarioPerfilSistema(UsuarioPerfilSistema objUsuarioPerfilSistema)
    {
        this.objUsuarioPerfilSistema = objUsuarioPerfilSistema;
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
     * Atribui descri��o do perfil
     *  
     * @param String valor da descri��o do sistema
     * 
     */
    public void setDescricao(String strDescricao)
    {
        this.strDescricao = strDescricao;
    }

}
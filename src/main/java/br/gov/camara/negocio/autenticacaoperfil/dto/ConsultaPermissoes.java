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
     * Atribui descrição do perfil
     *  
     * @param String valor da descrição do sistema
     * 
     */
    public void setDescricao(String strDescricao)
    {
        this.strDescricao = strDescricao;
    }

}
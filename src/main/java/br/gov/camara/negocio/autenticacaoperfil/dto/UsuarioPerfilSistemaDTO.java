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

public class UsuarioPerfilSistemaDTO extends UsuarioPerfilSistema
{
    private boolean blnCriterioAcessoOk = true;
    private String strJustificativa = "";
    private boolean blnDataInicioOk = true;
    private boolean blnDataTerminoOk = true;
    private String strDataInicio = "";
    private String strDataTermino = "";

    public String getDataInicio()
    {
        return strDataInicio;
    }

    public void setDataInicio(String strDataInicio)
    {
        this.strDataInicio = strDataInicio;
    }

    public String getDataTermino()
    {
        return strDataTermino;
    }

    public void setDataTermino(String strDataTermino)
    {
        this.strDataTermino = strDataTermino;
    }

    public boolean isDataInicioOk()
    {
        return blnDataInicioOk;
    }

    public void setDataInicioOk(boolean blnDataInicioOk)
    {
        this.blnDataInicioOk = blnDataInicioOk;
    }

    public boolean isDataTerminoOk()
    {
        return blnDataTerminoOk;
    }

    public void setDataTerminoOk(boolean blnDataTerminoOk)
    {
        this.blnDataTerminoOk = blnDataTerminoOk;
    }

    public String getJustificativa()
    {
        return strJustificativa;
    }

    public void setJustificativa(String strJustificativa)
    {
        this.strJustificativa = strJustificativa;
    }

    public boolean isCriterioAcessoOk()
    {
        return blnCriterioAcessoOk;
    }

    public void setCriterioAcessoOk(boolean blnCriterioAcessoOk)
    {
        this.blnCriterioAcessoOk = blnCriterioAcessoOk;
    }

}

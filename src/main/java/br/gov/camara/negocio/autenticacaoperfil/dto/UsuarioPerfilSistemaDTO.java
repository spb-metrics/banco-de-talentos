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

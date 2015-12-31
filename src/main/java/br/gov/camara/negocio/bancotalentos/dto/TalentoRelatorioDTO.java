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

package br.gov.camara.negocio.bancotalentos.dto;

import java.util.List;

/**
 * POJO para pessoal
 */

public class TalentoRelatorioDTO
{

    // Variáveis de instância
    String strNomeTalento = null;

    String strIdAtributo = null;
    String strIdTalento = null;
    String strDataLancamento = null;
    String strDescricaoAtributo = null;
    String strValorAtributo = null;

    List lstAtributosTalentoValorados = null;

    public String getNomeTalento()
    {
        return strNomeTalento;
    }

    public void setNomeTalento(String strNomeTalento)
    {
        this.strNomeTalento = strNomeTalento;
    }

    public List getAtributosTalentoValorados()
    {
        return lstAtributosTalentoValorados;
    }

    public void setAtributosTalentoValorados(List lstAtributosTalentoValorados)
    {
        this.lstAtributosTalentoValorados = lstAtributosTalentoValorados;
    }

    public String getDataLancamento()
    {
        return strDataLancamento;
    }

    public void setDataLancamento(String strDataLancamento)
    {
        this.strDataLancamento = strDataLancamento;
    }

    public String getDescricaoAtributo()
    {
        return strDescricaoAtributo;
    }

    public void setDescricaoAtributo(String strDescricaoAtributo)
    {
        this.strDescricaoAtributo = strDescricaoAtributo;
    }

    public String getValorAtributo()
    {
        return strValorAtributo;
    }

    public void setValorAtributo(String strValorAtributo)
    {
        this.strValorAtributo = strValorAtributo;
    }

    public String getIdAtributo()
    {
        return strIdAtributo;
    }

    public void setIdAtributo(String strIdAtributo)
    {
        this.strIdAtributo = strIdAtributo;
    }

    public String getIdTalento()
    {
        return strIdTalento;
    }

    public void setIdTalento(String strIdTalento)
    {
        this.strIdTalento = strIdTalento;
    }
}
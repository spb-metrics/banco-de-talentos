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

/**
 * POJO para pessoal
 */

public class AtributoTalentoRelatorioDTO
{

    // Variáveis de instância
    String strDataLancamento = null;
    String strDescricaoAtributo = null;
    String strValorAtributo = null;

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

    public String getDataLancamento()
    {
        return strDataLancamento;
    }

    public void setDataLancamento(String strDataLancamento)
    {
        this.strDataLancamento = strDataLancamento;
    }
}
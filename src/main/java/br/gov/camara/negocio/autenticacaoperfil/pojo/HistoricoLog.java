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
import java.util.Set;

/**
 * POJO para grupo (de servidores)
 */
public class HistoricoLog
{

    //  Variáveis de instância
    private Integer strIdeHistorico;
    private String strIdentificador;
    private Calendar strDataHistorico;
    private String strNomeEntidade;
    private String strUsuario;
    private String strOperacao;
    private Set setDetalheHistoricoLog;

    public HistoricoLog(String strIdentificador, String strNomeEntidade, String strOperacao, Set setDetalheHistoricoLog)
    {
        this.strIdentificador = strIdentificador;
        this.strNomeEntidade = strNomeEntidade;
        this.strOperacao = strOperacao;
        this.setDetalheHistoricoLog = setDetalheHistoricoLog;
    }

    public HistoricoLog()
    {}

    public Calendar getDataHistorico()
    {
        return strDataHistorico;
    }

    public void setDataHistorico(Calendar strDataHistorico)
    {
        this.strDataHistorico = strDataHistorico;
    }

    public Integer getIdeHistorico()
    {
        return strIdeHistorico;
    }

    public void setIdeHistorico(Integer strIdeHistorico)
    {
        this.strIdeHistorico = strIdeHistorico;
    }

    public String getIdentificador()
    {
        return strIdentificador;
    }

    public void setIdentificador(String strIdentificador)
    {
        this.strIdentificador = strIdentificador;
    }

    public String getNomeEntidade()
    {
        return strNomeEntidade;
    }

    public void setNomeEntidade(String strNomeEntidade)
    {
        this.strNomeEntidade = strNomeEntidade;
    }

    public String getOperacao()
    {
        return strOperacao;
    }

    public void setOperacao(String strOperacao)
    {
        this.strOperacao = strOperacao;
    }

    public String getUsuario()
    {
        return strUsuario;
    }

    public void setUsuario(String strUsuario)
    {
        this.strUsuario = strUsuario;
    }

    public Set getDetalheHistoricoLog()
    {
        return setDetalheHistoricoLog;
    }

    public void setDetalheHistoricoLog(Set setDetalheHistoricoLog)
    {
        this.setDetalheHistoricoLog = setDetalheHistoricoLog;
    }
}

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

import java.util.Calendar;
import java.util.Set;

/**
 * POJO para grupo (de servidores)
 */
public class HistoricoLog
{

    //  Vari�veis de inst�ncia
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

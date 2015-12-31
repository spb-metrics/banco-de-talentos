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

package br.gov.camara.negocio.bancotalentos.pojo;

import java.util.Calendar;

import br.gov.camara.biblioteca.util.DataNova;

/**
 * POJO para talento
 */
public class Talento
{
    private Integer intIdentificador;
    private CategoriaTalento objCategoriaTalento;
    private Calendar calDataLancamento;
    private Pessoa objPessoa;

    /**
     * Obt�m data de lan�amento 
     * 
     * @return Calendar Contendo o dado
     */
    public Calendar getDataLancamento()
    {
        return calDataLancamento;
    }

    /**
     * Obt�m data de lan�amento formatada 
     * 
     * @return Calendar Contendo o dado
     */
    public String getDataLancamentoFormatada()
    {
        return DataNova.format(calDataLancamento);
    }

    /**
     * Obt�m identificador
     * 
     * @return Integer Contendo o dado
     */
    public Integer getIdentificador()
    {
        return intIdentificador;
    }

    /**
     * Obt�m categoria de talento
     * 
     * @return CategoriaTalento Contendo o dado
     */
    public CategoriaTalento getCategoriaTalento()
    {
        return objCategoriaTalento;
    }

    /**
     * Obt�m pessoa a qual pertence o talento
     * 
     * @return Pessoa Contendo o dado
     */
    public Pessoa getPessoa()
    {
        return objPessoa;
    }

    /**
     * Preenche data de lan�amento
     * 
     * @param calDataLancamento Com o dado desejado
     */
    public void setDataLancamento(Calendar calDataLancamento)
    {
        this.calDataLancamento = calDataLancamento;
    }

    /**
     * Preenche data de lan�amento formatada (apenas para padronizar o bean)
     * 
     * @param calDataLancamento Com o dado desejado
     */
    public void setDataLancamentoFormatada(String strDataLancamento)
    {}

    /**
     * Preeche identificador
     * 
     * @param intIdentificador Com o dado desejado
     */
    public void setIdentificador(Integer intIdentificador)
    {
        this.intIdentificador = intIdentificador;
    }

    /**
     * Preenche categoria de talento
     * 
     * @param objCategoriaTalento Com o dado desejado
     */
    public void setCategoriaTalento(CategoriaTalento objCategoriaTalento)
    {
        this.objCategoriaTalento = objCategoriaTalento;
    }

    /**
     * Preenche a pessoa a qual pertence o talento
     * 
     * @param objPessoa Com o dado desejado
     */
    public void setPessoa(Pessoa objPessoa)
    {
        this.objPessoa = objPessoa;
    }

}

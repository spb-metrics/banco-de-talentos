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
     * Obtém data de lançamento 
     * 
     * @return Calendar Contendo o dado
     */
    public Calendar getDataLancamento()
    {
        return calDataLancamento;
    }

    /**
     * Obtém data de lançamento formatada 
     * 
     * @return Calendar Contendo o dado
     */
    public String getDataLancamentoFormatada()
    {
        return DataNova.format(calDataLancamento);
    }

    /**
     * Obtém identificador
     * 
     * @return Integer Contendo o dado
     */
    public Integer getIdentificador()
    {
        return intIdentificador;
    }

    /**
     * Obtém categoria de talento
     * 
     * @return CategoriaTalento Contendo o dado
     */
    public CategoriaTalento getCategoriaTalento()
    {
        return objCategoriaTalento;
    }

    /**
     * Obtém pessoa a qual pertence o talento
     * 
     * @return Pessoa Contendo o dado
     */
    public Pessoa getPessoa()
    {
        return objPessoa;
    }

    /**
     * Preenche data de lançamento
     * 
     * @param calDataLancamento Com o dado desejado
     */
    public void setDataLancamento(Calendar calDataLancamento)
    {
        this.calDataLancamento = calDataLancamento;
    }

    /**
     * Preenche data de lançamento formatada (apenas para padronizar o bean)
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

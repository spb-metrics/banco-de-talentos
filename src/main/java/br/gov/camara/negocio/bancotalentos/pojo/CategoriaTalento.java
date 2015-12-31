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

import java.util.Set;

/**
 * POJO para categoria de talento
 */
public class CategoriaTalento
{
    
    // Variáveis de instância
    private Integer intIdentificador;
    private String strNome;
    private String strDescricao;
    private String strDicaPreenchimento;
    private String strIndicativoUnicidade;
    private Integer intSequencialOrdenacao;
    private Set setGruposCategoriaTalento;

    /**
     * Obtém identificador
     * 
     * @return Integer Contendo o dado
     * 
     */
    public Integer getIdentificador()
    {
        return intIdentificador;
    }

    /**
     * Obtém ordenação 
     * 
     * @return Integer Contendo o dado
     * 
     */
    public Integer getSequencialOrdenacao()
    {
        return intSequencialOrdenacao;
    }

    /**
     * Obtém descrição 
     * 
     * @return String Contendo o dado
     * 
     */
    public String getDescricao()
    {
        return strDescricao;
    }

    /**
     * Obtém dica de preenchimento
     * 
     * @return String Contendo o dado
     * 
     */
    public String getDicaPreenchimento()
    {
        return strDicaPreenchimento;
    }

    /**
     * Obtém nome
     * 
     * @return String Contendo o dado
     * 
     */
    public String getNome()
    {
        return strNome;
    }

    /**
     * Obtém indicativo de unicidade (a categoria só pode ser inserida uma vez por servidor)
     * 
     * @return String Contendo o dado
     * 
     */
    public String getIndicativoUnicidade()
    {
        return strIndicativoUnicidade;
    }

    /**
     * Obtém os grupos que podem acessar a categoria
     * 
     * @return Set Contendo o ado
     */
    public Set getGruposCategoriaTalento()
    {
        return setGruposCategoriaTalento;
    }

    /**
     * Preenche identificador
     *  
     * @param intIdentificador Contendo o dado
     * 
     */
    public void setIdentificador(Integer intIdentificador)
    {
        this.intIdentificador = intIdentificador;
    }

    /**
     * Preenche ordenação
     * 
     * @param Integer Contendo o dado
     * 
     */
    public void setSequencialOrdenacao(Integer intSequencialOrdenacao)
    {
        this.intSequencialOrdenacao = intSequencialOrdenacao;
    }

    /**
     * Preenche descrição
     * 
     * @param String Contendo o dado
     * 
     */
    public void setDescricao(String strDescricao)
    {
        this.strDescricao = strDescricao;
    }

    /**
     * Preenche dica de preenchimento
     * 
     * @param String Contendo o dado
     * 
     */
    public void setDicaPreenchimento(String strDicaPreenchimento)
    {
        this.strDicaPreenchimento = strDicaPreenchimento;
    }

    /**
     * Preenche nome
     * 
     * @param String Contendo o dado
     * 
     */
    public void setNome(String strNome)
    {
        this.strNome = strNome;
    }

    /**
     * Preenche unicidade
     * 
     * @param String Contendo o dado
     * 
     */
    public void setIndicativoUnicidade(String strIndicativoUnicidade)
    {
        this.strIndicativoUnicidade = strIndicativoUnicidade;
    }

    /**
     * Preenche os grupos que podem acessar a categoria
     * 
     * @param Set Contendo o dado
     * 
     */
    public void setGruposCategoriaTalento(Set setGruposCategoriaTalento)
    {
        this.setGruposCategoriaTalento = setGruposCategoriaTalento;
    }
}
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

import java.util.Set;

/**
 * POJO para categoria de talento
 */
public class CategoriaTalento
{
    
    // Vari�veis de inst�ncia
    private Integer intIdentificador;
    private String strNome;
    private String strDescricao;
    private String strDicaPreenchimento;
    private String strIndicativoUnicidade;
    private Integer intSequencialOrdenacao;
    private Set setGruposCategoriaTalento;

    /**
     * Obt�m identificador
     * 
     * @return Integer Contendo o dado
     * 
     */
    public Integer getIdentificador()
    {
        return intIdentificador;
    }

    /**
     * Obt�m ordena��o 
     * 
     * @return Integer Contendo o dado
     * 
     */
    public Integer getSequencialOrdenacao()
    {
        return intSequencialOrdenacao;
    }

    /**
     * Obt�m descri��o 
     * 
     * @return String Contendo o dado
     * 
     */
    public String getDescricao()
    {
        return strDescricao;
    }

    /**
     * Obt�m dica de preenchimento
     * 
     * @return String Contendo o dado
     * 
     */
    public String getDicaPreenchimento()
    {
        return strDicaPreenchimento;
    }

    /**
     * Obt�m nome
     * 
     * @return String Contendo o dado
     * 
     */
    public String getNome()
    {
        return strNome;
    }

    /**
     * Obt�m indicativo de unicidade (a categoria s� pode ser inserida uma vez por servidor)
     * 
     * @return String Contendo o dado
     * 
     */
    public String getIndicativoUnicidade()
    {
        return strIndicativoUnicidade;
    }

    /**
     * Obt�m os grupos que podem acessar a categoria
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
     * Preenche ordena��o
     * 
     * @param Integer Contendo o dado
     * 
     */
    public void setSequencialOrdenacao(Integer intSequencialOrdenacao)
    {
        this.intSequencialOrdenacao = intSequencialOrdenacao;
    }

    /**
     * Preenche descri��o
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
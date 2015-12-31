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
 * POJO para categoria/atributo de talento (relaciona uma categoria com um atributo)
 */

public class CategoriaAtributoTalento
{
    //  Variáveis de instância
    private Integer intIdentificador;
    private Integer intSequencialOrdenacao;
    private AtributoTalento objAtributoTalento;
    private CategoriaTalento objCategoriaTalento;
    private String strIndicativoObrigatoriedade;
    private String strFormacaoDescricao;
    private String strApelido;
    private String strDicaPreenchimento;
    private Set setAtributosTalentoValorados;
    private boolean blnMudancaIndicativoObrigatoriedade;

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
     * Obtém sequência
     * 
     * @return Integer Contendo o dado
     * 
     */
    public Integer getSequencialOrdenacao()
    {
        return intSequencialOrdenacao;
    }

    /**
     * Obtém atributo de talento
     * 
     * @return AtributoTalento Contendo o dado
     * 
     */
    public AtributoTalento getAtributoTalento()
    {
        return objAtributoTalento;
    }

    /**
     * Obtém categoria de talento
     * 
     * @return CategoriaTalento Contendo o dado
     * 
     */
    public CategoriaTalento getCategoriaTalento()
    {
        return objCategoriaTalento;
    }

    /**
     * Obtém apelido
     * 
     * @return String Contendo o dado
     * 
     */
    public String getApelido()
    {
        return strApelido;
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
     * Obtém formação de descrição
     * 
     * @return String Contendo o dado
     * 
     */
    public String getFormacaoDescricao()
    {
        return strFormacaoDescricao;
    }

    /**
     * Obtém obrigatoriedade
     * 
     * @return String Contendo o dado
     * 
     */
    public String getIndicativoObrigatoriedade()
    {
        return strIndicativoObrigatoriedade;
    }

    /**
     * Obtém atributos valorados  
     * 
     * @return Set Contendo o dado 
     * 
     */
    public Set getAtributosTalentoValorados()
    {
        return setAtributosTalentoValorados;
    }

    /**
     * Preenche identificador
     * 
     * @param Integer Com o dado desejado
     * 
     */
    public void setIdentificador(Integer intIdentificador)
    {
        this.intIdentificador = intIdentificador;
    }

    /**
     * Preenche sequência
     * 
     * @param Integer Com o dado desejado
     * 
     */
    public void setSequencialOrdenacao(Integer intSequencialOrdenacao)
    {
        this.intSequencialOrdenacao = intSequencialOrdenacao;
    }

    /**
     * Preenche atributo de talento
     * 
     * @param AtributoTalento Com o dado desejado
     * 
     */
    public void setAtributoTalento(AtributoTalento objAtributoTalento)
    {
        this.objAtributoTalento = objAtributoTalento;
    }

    /**
     * Preeche categoria de talento
     * 
     * @param CategoriaTalento Com o dado desejado
     * 
     */
    public void setCategoriaTalento(CategoriaTalento objCategoriaTalento)
    {
        this.objCategoriaTalento = objCategoriaTalento;
    }

    /**
     * Preenche apelido
     * 
     * @param String Com o dado desejado
     * 
     */
    public void setApelido(String strApelido)
    {
        this.strApelido = strApelido;
    }

    /**
     * Preenche dica de preenchimento
     * 
     * @param String Com o dado desejado
     * 
     */
    public void setDicaPreenchimento(String strDicaPreenchimento)
    {
        this.strDicaPreenchimento = strDicaPreenchimento;
    }

    /**
     * Preenche formação de descrição
     * 
     * @param String Com o dado desejado
     * 
     */
    public void setFormacaoDescricao(String strFormacaoDescricao)
    {
        this.strFormacaoDescricao = strFormacaoDescricao;
    }

    /**
     * Atribui obrigatoriedade
     * 
     * @param String Com o dado desejado
     * 
     */
    public void setIndicativoObrigatoriedade(String strIndicativoObrigatoriedade)
    {
        if ("N".equals(this.strIndicativoObrigatoriedade) &&
                "S".equals(strIndicativoObrigatoriedade))
        {
            blnMudancaIndicativoObrigatoriedade = true;
        }
        this.strIndicativoObrigatoriedade = strIndicativoObrigatoriedade;
    }

    /**
     * Atribui atributo valorado
     * 
     * @param Set Com o dado desejado
     * 
     */
    public void setAtributosTalentoValorados(Set setAtributosTalentoValorados)
    {
        this.setAtributosTalentoValorados = setAtributosTalentoValorados;
    }
    /**
     * Verifica se houve mudança de obrigatoriedade
     * 
     * @return boolean Indicando se houve não
     */
    public boolean isBlnMudancaIndicativoObrigatoriedade()
    {
        return blnMudancaIndicativoObrigatoriedade;
    }
}

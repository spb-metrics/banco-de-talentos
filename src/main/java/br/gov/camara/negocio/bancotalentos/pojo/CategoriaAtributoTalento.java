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
 * POJO para categoria/atributo de talento (relaciona uma categoria com um atributo)
 */

public class CategoriaAtributoTalento
{
    //  Vari�veis de inst�ncia
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
     * Obt�m sequ�ncia
     * 
     * @return Integer Contendo o dado
     * 
     */
    public Integer getSequencialOrdenacao()
    {
        return intSequencialOrdenacao;
    }

    /**
     * Obt�m atributo de talento
     * 
     * @return AtributoTalento Contendo o dado
     * 
     */
    public AtributoTalento getAtributoTalento()
    {
        return objAtributoTalento;
    }

    /**
     * Obt�m categoria de talento
     * 
     * @return CategoriaTalento Contendo o dado
     * 
     */
    public CategoriaTalento getCategoriaTalento()
    {
        return objCategoriaTalento;
    }

    /**
     * Obt�m apelido
     * 
     * @return String Contendo o dado
     * 
     */
    public String getApelido()
    {
        return strApelido;
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
     * Obt�m forma��o de descri��o
     * 
     * @return String Contendo o dado
     * 
     */
    public String getFormacaoDescricao()
    {
        return strFormacaoDescricao;
    }

    /**
     * Obt�m obrigatoriedade
     * 
     * @return String Contendo o dado
     * 
     */
    public String getIndicativoObrigatoriedade()
    {
        return strIndicativoObrigatoriedade;
    }

    /**
     * Obt�m atributos valorados  
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
     * Preenche sequ�ncia
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
     * Preenche forma��o de descri��o
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
     * Verifica se houve mudan�a de obrigatoriedade
     * 
     * @return boolean Indicando se houve n�o
     */
    public boolean isBlnMudancaIndicativoObrigatoriedade()
    {
        return blnMudancaIndicativoObrigatoriedade;
    }
}

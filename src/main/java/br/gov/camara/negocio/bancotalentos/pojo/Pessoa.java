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
import java.util.Set;

import br.gov.camara.biblioteca.util.DataNova;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Grupo;

/**
 * POJO para pessoal
 */

public class Pessoa
{

    // Variáveis de instância
    private Integer intIdentificador;
    private String strNome;
    private Calendar calDataNascimento;
    private String strSexo;
    private String strCpf;
    private String strRg;
    private Grupo objGrupo;
    private Set setLotacoes;

    /**
     * Obtém data de nascimento
     * 
     * @return Calendar Contendo o dado
     *  
     */
    public Calendar getDataNascimento()
    {
        return calDataNascimento;
    }

    /**
     * Obtém data de nascimento formatada
     * 
     * @return Calendar Contendo o dado
     *  
     */
    public String getDataNascimentoFormatada()
    {
        return DataNova.format(calDataNascimento);
    }

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
     * Obtém histórico de lotações 
     * 
     * @return Set Contendo o dado
     * 
     */
    public Set getLotacoes()
    {
        return setLotacoes;
    }

    /**
     * Obtém CPF
     * 
     * @return String Contendo o dado
     * 
     */
    public String getCpf()
    {
        return strCpf;
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
     * Obtém RG
     * 
     * @return String Contendo o dado
     */
    public String getRg()
    {
        return strRg;
    }

    /**
     * Obtém sexo
     * 
     * @return String Contendo o dados
     * 
     */
    public String getSexo()
    {
        return strSexo;
    }

    /**
     * Obtém grupo
     * 
     * @return String Contendo o dado 
     * 
     */
    public Grupo getGrupo()
    {
        return objGrupo;
    }

    /**
     * Obtém data de nascimento
     * 
     * @param Calendar Contendo o dado
     * 
     */
    public void setDataNascimento(Calendar calDataNascimento)
    {
        this.calDataNascimento = calDataNascimento;
    }

    /**
     * Preenche identificador
     * 
     * @param intIdentificador Com o dado desejado
     * 
     */
    public void setIdentificador(Integer intIdentificador)
    {
        this.intIdentificador = intIdentificador;
    }

    /**
     * Preenche histórico de lotações do servidor
     * 
     * @param Set Com o dado desejado
     * 
     */
    public void setLotacoes(Set setLotacoes)
    {
        this.setLotacoes = setLotacoes;
    }

    /**
     * Preenhce CPF
     * 
     * @param String Com o dado desejado
     * 
     */
    public void setCpf(String strCpf)
    {
        this.strCpf = strCpf;
    }

    /**
     * Preenche nome
     * 
     * @param String Com o dado desejado
     */
    public void setNome(String strNome)
    {
        this.strNome = strNome;
    }

    /**
     * Preenhce RG
     * 
     * @param String Com o dado desejado
     * 
     */
    public void setRg(String strRg)
    {
        this.strRg = strRg;
    }

    /**
     * Preenche sexo
     * 
     * @param String Com o dado desejado
     * 
     */
    public void setSexo(String strSexo)
    {
        this.strSexo = strSexo;
    }

    /**
     * Preenche grupo
     * 
     * @param String Com o dado desejado
     * 
     */
    public void setGrupo(Grupo objGrupo)
    {
        this.objGrupo = objGrupo;
    }

}
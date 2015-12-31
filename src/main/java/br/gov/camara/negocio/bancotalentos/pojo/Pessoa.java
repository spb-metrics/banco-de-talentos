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
import java.util.Set;

import br.gov.camara.biblioteca.util.DataNova;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Grupo;

/**
 * POJO para pessoal
 */

public class Pessoa
{

    // Vari�veis de inst�ncia
    private Integer intIdentificador;
    private String strNome;
    private Calendar calDataNascimento;
    private String strSexo;
    private String strCpf;
    private String strRg;
    private Grupo objGrupo;
    private Set setLotacoes;

    /**
     * Obt�m data de nascimento
     * 
     * @return Calendar Contendo o dado
     *  
     */
    public Calendar getDataNascimento()
    {
        return calDataNascimento;
    }

    /**
     * Obt�m data de nascimento formatada
     * 
     * @return Calendar Contendo o dado
     *  
     */
    public String getDataNascimentoFormatada()
    {
        return DataNova.format(calDataNascimento);
    }

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
     * Obt�m hist�rico de lota��es 
     * 
     * @return Set Contendo o dado
     * 
     */
    public Set getLotacoes()
    {
        return setLotacoes;
    }

    /**
     * Obt�m CPF
     * 
     * @return String Contendo o dado
     * 
     */
    public String getCpf()
    {
        return strCpf;
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
     * Obt�m RG
     * 
     * @return String Contendo o dado
     */
    public String getRg()
    {
        return strRg;
    }

    /**
     * Obt�m sexo
     * 
     * @return String Contendo o dados
     * 
     */
    public String getSexo()
    {
        return strSexo;
    }

    /**
     * Obt�m grupo
     * 
     * @return String Contendo o dado 
     * 
     */
    public Grupo getGrupo()
    {
        return objGrupo;
    }

    /**
     * Obt�m data de nascimento
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
     * Preenche hist�rico de lota��es do servidor
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
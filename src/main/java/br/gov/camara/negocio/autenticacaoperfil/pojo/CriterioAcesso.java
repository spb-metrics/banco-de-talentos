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

import br.gov.camara.hibernate.interceptor.InterceptorHistoricoAtualizacao;
import br.gov.camara.hibernate.interceptor.InterceptorUltimaAtualizacao;

public class CriterioAcesso implements InterceptorUltimaAtualizacao, InterceptorHistoricoAtualizacao
{

    //  Vari�veis de inst�ncia
    private Integer intIdentificador;
    private String strNome;
    private String strClasseImplementadora;
    private String strDescricao;
    private Calendar calDataUltimaAtualizacao;
    private String strUsuarioUltimaAtualizacao;

    /**
     * Obt�m Data da �ltima atualiza��o do registro
     * 
     * @return Calendar Contendo o dado
     * 
     */
    public Calendar getDataUltimaAtualizacao()
    {
        return calDataUltimaAtualizacao;
    }

    /**
     * Obt�m Usu�rio que realizou a �ltima atualiza�ao do registro
     * 
     * @return UsuarioSistema Contendo o dado
     * 
     */
    public String getUsuarioUltimaAtualizacao()
    {
        return strUsuarioUltimaAtualizacao;
    }

    /**
     * Obt�m a chave
     * 
     * @return String identificador do registro 
     * 
     */
    public Integer getIdentificador()
    {
        return intIdentificador;
    }

    /**
     * Obt�m Nome do crit�rio
     *  
     * @return String nome do crit�rio
     * 
     */
    public String getNome()
    {
        return strNome;
    }

    /**
     * Obt�m Classe implementadora
     * 
     * @return nome da classe implementadora 
     * 
     */
    public String getClasseImplementadora()
    {
        return strClasseImplementadora;
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
     * Preenche nome do crit�rio
     * 
     * @param strNome Com o dado desejado
     * 
     */
    public void setNome(String strNome)
    {
        this.strNome = strNome;
    }

    /**
     * Preenche Classe implementadora
     * 
     * @param String Com o dado desejado
     * 
     */
    public void setClasseImplementadora(String strClasseImplementadora)
    {
        this.strClasseImplementadora = strClasseImplementadora;
    }

    /**
     * @param calDataUltimaAtualizacao Data da �ltima atualiza��o do registro.
     */
    public void setDataUltimaAtualizacao(Calendar calDataUltimaAtualizacao)
    {
        this.calDataUltimaAtualizacao = calDataUltimaAtualizacao;
    }

    /**
     * @param objUsuarioUltimaAtualizacao Usu�rio que realizou a �ltima atualiza��o do registro.
     */
    public void setUsuarioUltimaAtualizacao(String strUsuarioUltimaAtualizacao)
    {
        this.strUsuarioUltimaAtualizacao = strUsuarioUltimaAtualizacao;
    }

    public String getDescricao()
    {
        return strDescricao;
    }

    public void setDescricao(String strDescricao)
    {
        this.strDescricao = strDescricao;
    }

    public String getChaveEntidade()
    {
        return getIdentificador().toString();
    }

}

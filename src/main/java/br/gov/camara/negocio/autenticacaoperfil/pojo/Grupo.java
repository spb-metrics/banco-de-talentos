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


/**
 * POJO para grupo (de servidores)
 */
public class Grupo
{
    
    //  Vari�veis de inst�ncia
    private Integer intCodigo;
    private String strDescricao;

    /**
     * Obt�m c�digo
     * 
     * @return Integer Contendo o dado 
     * 
     */
    public Integer getCodigo()
    {
        return intCodigo;
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
     * Preenche c�digo
     * 
     * @param Integer Com o dado desejado
     * 
     */
    public void setCodigo(Integer intCodigo)
    {
        this.intCodigo = intCodigo;
    }

    /**
     * Preenche descri��o
     * 
     * @param String Com o dado desejado
     * 
     */
    public void setDescricao(String strDescricao)
    {
        this.strDescricao = strDescricao;
    }

}

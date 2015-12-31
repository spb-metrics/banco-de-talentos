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

/**
 * POJO para op��o de atributo de talento
 */
public class AtributoTalentoOpcao implements Comparable
{
	// Vari�veis de inst�ncia
    public Integer intIdentificador;
    public AtributoTalentoOpcao objAtributoTalentoOpcaoPai;
    public AtributoTalento objAtributoTalento;
    public String strDescricao;

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
     * Obt�m atributo de talento
     * 
     * @return AtributoTalento Contendo o dado
     */
    public AtributoTalento getAtributoTalento()
    {
        return objAtributoTalento;
    }

    /**
     * Obt�m op��o pai
     * 
     * @return AtributoTalentoOpcao Contendo o dado
     */
    public AtributoTalentoOpcao getAtributoTalentoOpcaoPai()
    {
        return objAtributoTalentoOpcaoPai;
    }

    /**
     * Obt�m descri��o
     * 
     * @return String Contendo o dado
     */
    public String getDescricao()
    {
        return strDescricao;
    }

    /**
     * Preenche identificador
     * 
     * @param intIdentificador Com o dado desejado
     */
    public void setIdentificador(Integer intIdentificador)
    {
        this.intIdentificador = intIdentificador;
    }

    /**
     * Preeche atributo de talento
     * 
     * @param objAtributoTalentoTalento Contendo o dado
     */
    public void setAtributoTalento(AtributoTalento objAtributoTalento)
    {
        this.objAtributoTalento = objAtributoTalento;
    }

    /**
     * Preeche op��o pai
     * 
     * @param objAtributoTalentoTalentoOpcaoPai Contendo o dado
     */
    public void setAtributoTalentoOpcaoPai(AtributoTalentoOpcao objAtributoTalentoTalentoOpcaoPai)
    {
        this.objAtributoTalentoOpcaoPai = objAtributoTalentoTalentoOpcaoPai;
    }

    /**
     * Preenche descri��o
     * 
     * @param strDescricao Contendo o dado
     */
    public void setDescricao(String strDescricao)
    {
        this.strDescricao = strDescricao;
    }
    
    /**
     * M�todo necess�rio para a compara��o e ordena��o das strings de descri��o desse objetos
     */
    public int compareTo(Object obj)
    {
        int retorno = 0;
        AtributoTalentoOpcao objATO = (AtributoTalentoOpcao)obj;
        
        retorno = this.strDescricao.compareToIgnoreCase(objATO.getDescricao());
                
        return retorno;
    }

}

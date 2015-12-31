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

package br.gov.camara.negocio.exception;

import br.gov.camara.exception.CDException;

/**
 * Classe para tratamento de exce��es em DAOs
 */
public class DAOException extends CDException
{
    /**
     * 
     */
    private static final long serialVersionUID = 5794949280517716027L;

    /**
     * Cria um novo objeto DAOException
     */
    public DAOException()
    {
        super(ERRO_INESPERADO);
    }

    /**
     * Cria um novo objeto DAOException
     * 
     * @param strMensagem Mensagem a ser utilizada no caso da ocorr�ncia de uma exce��o
     */
    public DAOException(String strMensagem)
    {
        super(strMensagem);
    }

    /**
     * Cria um novo objeto DAOException
     * 
     * @param cause Causa da exce��o
     */
    public DAOException(Throwable cause)
    {
        super(getMensagem(cause), cause, MensagemErroBD.obterMensagem(cause));
    }

    /**
     * Cria um novo objeto DAOException
     * 
     * @param strMensagem Mensagem a ser utilizada no caso da ocorr�ncia de uma exce��o
     * @param cause Causa da exce��o
     */
    public DAOException(String strMensagem, Throwable cause)
    {
        super(strMensagem, cause, MensagemErroBD.obterMensagem(cause));
    }

    /**
     * Cria um novo objeto DAOException
     * 
     * @param dialeto Dialeto a ser tratado
     * @param sqle SQLException gerada pela conex�o
     */
    public DAOException(String strMensagem, Throwable excecao, String nomeClasseDialeto)
    {
        super(strMensagem, excecao, MensagemErroBD.obterMensagem(excecao, nomeClasseDialeto));
    }

    /**
     * Cria um novo objeto DAOException
     * 
     * @param dialeto Dialeto a ser tratado
     * @param sqle SQLException gerada pela conex�o
     */
    public DAOException(Throwable excecao, String nomeClasseDialeto)
    {
        super(excecao, MensagemErroBD.obterMensagem(excecao, nomeClasseDialeto));
    }

    public static void dispararExcecao(Throwable e, String nomeClasseDialeto) throws DAOException
    {
        if (e instanceof DAOException)
        {
            throw (DAOException) e;
        }
        else
        {
            throw new DAOException(e, nomeClasseDialeto);
        }
    }

    public static void dispararExcecao(Throwable e) throws DAOException
    {
        if (e instanceof DAOException)
        {
            throw (DAOException) e;
        }
        else
        {
            throw new DAOException(e);
        }
    }

    public static void dispararExcecao(String mensagem) throws DAOException
    {
        throw new DAOException(mensagem);
    }

}

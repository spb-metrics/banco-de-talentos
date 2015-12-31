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

package br.gov.camara.negocio.exception;

import br.gov.camara.exception.CDException;

/**
 * Classe para tratamento de exceções em DAOs
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
     * @param strMensagem Mensagem a ser utilizada no caso da ocorrência de uma exceção
     */
    public DAOException(String strMensagem)
    {
        super(strMensagem);
    }

    /**
     * Cria um novo objeto DAOException
     * 
     * @param cause Causa da exceção
     */
    public DAOException(Throwable cause)
    {
        super(getMensagem(cause), cause, MensagemErroBD.obterMensagem(cause));
    }

    /**
     * Cria um novo objeto DAOException
     * 
     * @param strMensagem Mensagem a ser utilizada no caso da ocorrência de uma exceção
     * @param cause Causa da exceção
     */
    public DAOException(String strMensagem, Throwable cause)
    {
        super(strMensagem, cause, MensagemErroBD.obterMensagem(cause));
    }

    /**
     * Cria um novo objeto DAOException
     * 
     * @param dialeto Dialeto a ser tratado
     * @param sqle SQLException gerada pela conexão
     */
    public DAOException(String strMensagem, Throwable excecao, String nomeClasseDialeto)
    {
        super(strMensagem, excecao, MensagemErroBD.obterMensagem(excecao, nomeClasseDialeto));
    }

    /**
     * Cria um novo objeto DAOException
     * 
     * @param dialeto Dialeto a ser tratado
     * @param sqle SQLException gerada pela conexão
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

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

public class NegocioException extends CDException
{

    /**
     * 
     */
    private static final long serialVersionUID = 957943215629944068L;

    /**
     * Cria um novo objeto CDException
     */
    public NegocioException()
    {
        super("Erro inesperado no sistema", true);
    }

    /**
     * Cria um novo objeto CDException
     *
     * @param strMensagem Mensagem da exceção
     */
    public NegocioException(String strMensagem)
    {
        super(strMensagem, true);
    }

    /**
     * Cria um novo objeto CDException
     *
     * @param strMensagem Mensagem da exceção
     * @param strMensagemUsuario Mensagem para o usuário
     */
    public NegocioException(String strMensagem, String strMensagemUsuario)
    {
        super(strMensagem, strMensagemUsuario, true);
    }

    /**
     * Cria um novo objeto CDException
     *
     * @param cause Causa da exceção
     */
    public NegocioException(Throwable cause)
    {
        super(cause, true);
    }

    /**
     * Cria um novo objeto CDException
     *
     * @param strMensagem Mensagem da exceção
     * @param cause Causa da exceção
     */
    public NegocioException(String strMensagem, Throwable cause)
    {
        super(strMensagem, cause, true);
    }

    public static void dispararExcecao(Exception e) throws NegocioException
    {
        if (e instanceof NegocioException)
        {
            throw (NegocioException) e;
        }
        else
        {
            throw new NegocioException(e.getMessage(), e);
        }
    }

}

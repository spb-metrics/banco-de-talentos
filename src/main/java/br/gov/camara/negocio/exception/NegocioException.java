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
     * @param strMensagem Mensagem da exce��o
     */
    public NegocioException(String strMensagem)
    {
        super(strMensagem, true);
    }

    /**
     * Cria um novo objeto CDException
     *
     * @param strMensagem Mensagem da exce��o
     * @param strMensagemUsuario Mensagem para o usu�rio
     */
    public NegocioException(String strMensagem, String strMensagemUsuario)
    {
        super(strMensagem, strMensagemUsuario, true);
    }

    /**
     * Cria um novo objeto CDException
     *
     * @param cause Causa da exce��o
     */
    public NegocioException(Throwable cause)
    {
        super(cause, true);
    }

    /**
     * Cria um novo objeto CDException
     *
     * @param strMensagem Mensagem da exce��o
     * @param cause Causa da exce��o
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

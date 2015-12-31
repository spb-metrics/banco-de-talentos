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

package sigesp.comum.util.exception;

import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe padrão de exceções
 */
public class SigespException extends Exception
{
    private Log log = LogFactory.getLog(this.getClass());
    private int intIdentificadorErro = -1;
    protected String strMensagemUsuario = null;

    /**
     * Cria um novo objeto SigespException
     */
    public SigespException()
    {
        super("Erro inesperado no sistema");
        gerarLog();
    }

    /**
     * Cria um novo objeto SigespException
     *
     * @param strMensagem Mensagem da exceção
     */
    public SigespException(String strMensagem)
    {
        super(strMensagem);
        gerarLog();
    }

    /**
     * Cria um novo objeto SigespException
     *
     * @param strMensagem Mensagem da exceção
     * @param strMensagemUsuario Mensagem para o usuário
     */
    public SigespException(String strMensagem, String strMensagemUsuario)
    {
        super(strMensagem);
        this.strMensagemUsuario = strMensagemUsuario;
        gerarLog();
    }

    /**
     * Cria um novo objeto SigespException
     *
     * @param cause Causa da exceção
     */
    public SigespException(Throwable cause)
    {
        super("Erro inesperado", cause);
        intIdentificadorErro = ((SigespException) cause).getIdentificadorErro();
        gerarLog();
    }

    /**
     * Cria um novo objeto SigespException
     *
     * @param strMensagem Mensagem da exceção
     * @param cause Causa da exceção
     */
    public SigespException(String strMensagem, Throwable cause)
    {
        super(strMensagem, cause);
        intIdentificadorErro = ((SigespException) cause).getIdentificadorErro();
        strMensagemUsuario = ((SigespException) cause).getMensagemUsuario();
        gerarLog();
    }

    /**
     * Gera mensagem de log
     *
     */
    protected void gerarLog()
    {
        // Gera log
        if (log.isErrorEnabled())
        {
            DecimalFormat dcfFormato = new DecimalFormat("000000");

            if (getCause() == null)
            {
                intIdentificadorErro = 1 + (int) (Math.random() * 999999);
                StackTraceElement[] steException = getStackTrace();
                for (int i = 0; i < steException.length; i++)
                {
                    log.error(dcfFormato.format(intIdentificadorErro) + " | ST: " + steException[i]);
                }
            }
            log.error(dcfFormato.format(intIdentificadorErro) + " | GM: " + getMessage() + " | " + getClass());
        }
    }

    /**
     * Método getter para mensagem de usuário
     *
     * @return String contendo a mensagem para o usuário
     */
    public String getMensagemUsuario()
    {
        return strMensagemUsuario;
    }

    /**
     * Método getter para identificador do erro
     *
     * @return Identificado do erro
     */
    public int getIdentificadorErro()
    {
        return intIdentificadorErro;
    }

    /**
     * Obtém mensagem gerada para o usuário
     *
     * @return String contendo a mensagem
     */
    public String obterMensagem()
    {
        if (strMensagemUsuario != null)
        {
            return strMensagemUsuario;
        }
        else
        {
            return getMessage();
        }
    }

    /**
     * Obtém toda a pilha de exceções ocorrida
     *
     * @return String com a mensagem da pilha
     */
    public String obterExcecoes()
    {
        String strRetorno = getMessage() + "\n";

        Throwable throwable = getCause();
        while (throwable != null)
        {
            strRetorno += (throwable.getMessage() + "\n");
            throwable = throwable.getCause();
        }

        return strRetorno;
    }
}

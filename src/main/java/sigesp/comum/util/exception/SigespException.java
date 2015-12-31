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

package sigesp.comum.util.exception;

import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe padr�o de exce��es
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
     * @param strMensagem Mensagem da exce��o
     */
    public SigespException(String strMensagem)
    {
        super(strMensagem);
        gerarLog();
    }

    /**
     * Cria um novo objeto SigespException
     *
     * @param strMensagem Mensagem da exce��o
     * @param strMensagemUsuario Mensagem para o usu�rio
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
     * @param cause Causa da exce��o
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
     * @param strMensagem Mensagem da exce��o
     * @param cause Causa da exce��o
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
     * M�todo getter para mensagem de usu�rio
     *
     * @return String contendo a mensagem para o usu�rio
     */
    public String getMensagemUsuario()
    {
        return strMensagemUsuario;
    }

    /**
     * M�todo getter para identificador do erro
     *
     * @return Identificado do erro
     */
    public int getIdentificadorErro()
    {
        return intIdentificadorErro;
    }

    /**
     * Obt�m mensagem gerada para o usu�rio
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
     * Obt�m toda a pilha de exce��es ocorrida
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

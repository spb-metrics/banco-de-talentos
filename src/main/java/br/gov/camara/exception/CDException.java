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

package br.gov.camara.exception;

import java.text.DecimalFormat;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe padrão de exceções
 */
public class CDException extends Exception
{
    private Log log = LogFactory.getLog(CDException.class);

    public static final String ERRO_INESPERADO = "Ocorreu um erro inesperado.";

    private static final long serialVersionUID = 1661419714317374637L;

    private int idErroCDException = 0;
    private String mensagemUsuario = null;

    /**
     * Cria um novo objeto CDException
     */
    public CDException()
    {
        super(ERRO_INESPERADO);
        gerarLog();
    }

    /**
     * Cria um novo objeto CDException
     * 
     * 
     */
    public CDException(boolean noLog)
    {
        super(ERRO_INESPERADO);
    }

    /**
     * Cria um novo objeto CDException
     *
     * @param strMensagem Mensagem da exceção
     */
    public CDException(String strMensagem)
    {
        super(strMensagem);
        gerarLog();
    }

    /**
     * Cria um novo objeto CDException
     *
     * @param strMensagem Mensagem da exceção
     * 
     */
    public CDException(String strMensagem, boolean noLog)
    {
        super(strMensagem);
    }

    /**
     * Cria um novo objeto CDException
     *
     * @param strMensagem Mensagem da exceção
     * @param strMensagemUsuario Mensagem para o usuário
     */
    public CDException(String strMensagem, String strMensagemUsuario)
    {
        super(strMensagem);
        this.mensagemUsuario = strMensagemUsuario;
        gerarLog();
    }

    /**
     * Cria um novo objeto CDException
     *
     * @param strMensagem Mensagem da exceção
     * @param strMensagemUsuario Mensagem para o usuário
     * 
     */
    public CDException(String strMensagem, String strMensagemUsuario, boolean noLog)
    {
        super(strMensagem);
        this.mensagemUsuario = strMensagemUsuario;
    }

    /**
     * Cria um novo objeto CDException
     *
     * @param cause Causa da exceção
     */
    public CDException(Throwable cause)
    {
        super(getMensagem(cause), cause);
        if (cause instanceof CDException)
        {
            idErroCDException = ((CDException) cause).getIdentificadorErro();
            mensagemUsuario = ((CDException) cause).getMensagemUsuario();
        }
        gerarLog();
    }

    /**
     * Cria um novo objeto CDException
     *
     * @param cause Causa da exceção
     * 
     */
    public CDException(Throwable cause, boolean noLog)
    {
        super(getMensagem(cause), cause);
        if (cause instanceof CDException)
        {
            idErroCDException = ((CDException) cause).getIdentificadorErro();
            mensagemUsuario = ((CDException) cause).getMensagemUsuario();
        }
    }

    /**
     * Cria um novo objeto CDException
     *
     * @param strMensagem Mensagem da exceção
     * @param cause Causa da exceção
     */
    public CDException(String strMensagem, Throwable cause)
    {
        super(strMensagem, cause);
        if (cause instanceof CDException)
        {
            idErroCDException = ((CDException) cause).getIdentificadorErro();
            mensagemUsuario = ((CDException) cause).getMensagemUsuario();
        }
        gerarLog();
    }

    /**
     * Cria um novo objeto CDException
     *
     * @param strMensagem Mensagem da exceção
     * @param cause Causa da exceção
     * 
     */
    public CDException(String strMensagem, Throwable cause, boolean noLog)
    {
        super(strMensagem, cause);
        if (cause instanceof CDException)
        {
            idErroCDException = ((CDException) cause).getIdentificadorErro();
            mensagemUsuario = ((CDException) cause).getMensagemUsuario();
        }
    }

    /**
     * Cria um novo objeto CDException
     *
     * @param strMensagem Mensagem da exceção
     * @param cause Causa da exceção
     */
    public CDException(String strMensagem, Throwable cause, String strMensagemUsuario)
    {
        super(strMensagem, cause);
        mensagemUsuario = strMensagemUsuario;
        if (cause instanceof CDException)
        {
            idErroCDException = ((CDException) cause).getIdentificadorErro();
        }
        gerarLog();
    }

    /**
     * Cria um novo objeto CDException
     *
     * @param strMensagem Mensagem da exceção
     * @param cause Causa da exceção
     * 
     */
    public CDException(String strMensagem, Throwable cause, String strMensagemUsuario, boolean noLog)
    {
        super(strMensagem, cause);
        mensagemUsuario = strMensagemUsuario;
        if (cause instanceof CDException)
        {
            idErroCDException = ((CDException) cause).getIdentificadorErro();
        }
    }

    /**
     * Cria um novo objeto CDException
     *
     * @param strMensagem Mensagem da exceção
     * @param cause Causa da exceção
     */
    public CDException(Throwable cause, String strMensagemUsuario)
    {
        super(getMensagem(cause), cause);
        mensagemUsuario = strMensagemUsuario;
        if (cause instanceof CDException)
        {
            idErroCDException = ((CDException) cause).getIdentificadorErro();
        }
        gerarLog();
    }

    /**
     * Cria um novo objeto CDException
     *
     * @param strMensagem Mensagem da exceção
     * @param cause Causa da exceção
     * 
     */
    public CDException(Throwable cause, String strMensagemUsuario, boolean noLog)
    {
        super(getMensagem(cause), cause);
        mensagemUsuario = strMensagemUsuario;
        if (cause instanceof CDException)
        {
            idErroCDException = ((CDException) cause).getIdentificadorErro();
        }
    }

    /**
     * Gera mensagem de log
     *
     */
    private void gerarLog()
    {
        // Gera log
        if (log.isErrorEnabled())
        {
            DecimalFormat dcfFormato = new DecimalFormat("000000");

            // Somente gerar um novo identificador, caso não tenha sido gerado um ainda.
            if (idErroCDException == 0)
            {
                Random objRandom = new Random();
                idErroCDException = 1 + objRandom.nextInt(999999);
            }

            StackTraceElement[] steException = getStackTrace();
            int i;
            for (i = 0; i < steException.length; i++)
            {
                if (i > 5)
                {
                    break;
                }
                else
                {
                    log.error(dcfFormato.format(idErroCDException) + " | ST: " + steException[i]);
                }

            }
            log.error(dcfFormato.format(idErroCDException) + " | GM: " + (i > 5 ? "(...) " : "") + getMensagemParaLog(this) + " | " + getClass());
        }
    }

    /**
     * Método getter para mensagem de usuário
     *
     * @return String contendo a mensagem para o usuário
     */
    protected String getMensagemUsuario()
    {
        return mensagemUsuario;
    }

    /**
     * Método getter para identificador do erro
     *
     * @return Identificado do erro
     */
    protected int getIdentificadorErro()
    {
        return idErroCDException;
    }

    /**
     * Obtém mensagem gerada para o usuário
     *
     * @return String contendo a mensagem
     * @deprecated Usar getMensagem
     */
    public String obterMensagem()
    {
        return getMensagem();
    }

    public String getMensagem()
    {
        return getMensagem(this);
    }

    protected static String getMensagem(Throwable throwable)
    {
        return getMensagemParaLog(throwable, false);
    }

    protected static String getMensagemParaLog(Throwable throwable)
    {
        return getMensagemParaLog(throwable, true);
    }

    private static String getMensagemParaLog(Throwable throwable, boolean paraLog)
    {
        StringBuffer mensagem = new StringBuffer();
        if (throwable instanceof CDException)
        {
            boolean temMensagemUsuario = false;

            if (((CDException) throwable).getMensagemUsuario() != null)
            {
                mensagem.append(((CDException) throwable).getMensagemUsuario());
                temMensagemUsuario = true;
            }
            else
            {
                mensagem.append(((CDException) throwable).getMessage());
            }

            if (((CDException) throwable).getIdentificadorErro() != 0 && !paraLog)
            {
                mensagem.append(" Ocorrência #");
                mensagem.append(Integer.toString(((CDException) throwable).getIdentificadorErro()));
                mensagem.append(".");
            }

            if (paraLog && temMensagemUsuario)
            {
                mensagem.append(" CAUSA: ");
                mensagem.append(throwable.getMessage());
                mensagem.append(".");
            }
        }
        else
        {
            mensagem.append(throwable.getMessage());
        }

        return mensagem.toString();
    }

    /**
     * Obtém toda a pilha de exceções ocorrida
     *
     * @return String com a mensagem da pilha
     * 
     */
    public String obterExcecoes()
    {
        StringBuffer strRetorno = new StringBuffer();
        strRetorno.append(getMensagem());
        strRetorno.append(" | ");

        Throwable throwable = getCause();
        while (throwable != null)
        {
            strRetorno.append(getMensagem(throwable));
            throwable = throwable.getCause();
            if (throwable != null)
            {
                strRetorno.append(" | ");
            }
        }

        return strRetorno.toString();
    }

    public static void dispararExcecao(Throwable e) throws CDException
    {
        if (e instanceof CDException)
        {
            throw (CDException) e;
        }
        else
        {
            throw new CDException(getMensagem(e), e);
        }
    }

    public static void dispararExcecao(String mensagem) throws CDException
    {
        throw new CDException(mensagem);
    }
}

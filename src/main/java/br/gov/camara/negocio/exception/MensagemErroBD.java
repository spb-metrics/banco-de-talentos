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

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;

import br.gov.camara.exception.CDException;

/**
 * Classe para recuperação de mensagens específicas para cada banco de dados
 */
public class MensagemErroBD
{
    /**
    * Logger for this class
    */
    private static final Log log = LogFactory.getLog(MensagemErroBD.class);

    private static final String RESTRICAO_NOT_NULL = "Ocorreu uma violação de restrição de valor não nulo.";
    private static final String RESTRICAO_CHECAGEM = "Ocorreu uma violação de restrição de checagem.";
    private static final String RESTRICAO_REFERENCIA = "Ocorreu uma violação de restrição de referência.";
    private static final String RESTRICAO_INTEGRIDADE_VIOLADA = "Ocorreu uma violação de restrição de integridade.";
    private static final String RESTRICAO_UNICA = "Ocorreu uma violação de restrição única.";

    /**
     * Obtém a mensagem a ser exibida pelo usuário
     * 
     * @param strBanco Banco utilizado
     * @param intErro Código do erro
     * @param strMensagem Mensagem gerada pelo banco
     * @return Mensagem a ser exibida
     */
    private static String obterMensagemPeloTipoExcecao(Throwable erro)
    {
        StringBuffer strMensagemRetorno = new StringBuffer();

        if (erro != null)
        {
            if (erro instanceof ConstraintViolationException)
            {
                strMensagemRetorno.append(RESTRICAO_INTEGRIDADE_VIOLADA);
                if (((ConstraintViolationException) erro).getConstraintName() != null)
                {
                    if (log.isInfoEnabled())
                    {
                        log.info(" Restrição " + ((ConstraintViolationException) erro).getConstraintName() + ".");
                    }
                }
            }
        }

        if ("".equals(strMensagemRetorno.toString()))
        {
            return null;
        }
        else
        {
            return strMensagemRetorno.toString();
        }
    }

    private static int obterNumeroErro(Throwable erro)
    {
        int numErro = 0;

        if (erro != null)
        {
            if (erro instanceof JDBCException)
            {
                ((JDBCException) erro).getErrorCode();
            }
            else if (erro instanceof SQLException)
            {
                ((SQLException) erro).getErrorCode();
            }
        }

        return numErro;
    }

    /**
     * Obtém a mensagem a ser exibida pelo usuário
     * 
     * @param strBanco Banco utilizado
     * @param intErro Código do erro
     * @param strMensagem Mensagem gerada pelo banco
     * @return Mensagem a ser exibida
     */
    protected static String obterMensagem(Throwable erro)
    {
        return obterMensagem(erro, null);
    }

    /**
     * Obtém a mensagem a ser exibida pelo usuário
     * 
     * @param strBanco Banco utilizado
     * @param intErro Código do erro
     * @param strMensagem Mensagem gerada pelo banco
     * @return Mensagem a ser exibida
     */
    protected static String obterMensagem(Throwable erro, String nomeClasseDialeto)
    {
        String mensagemRetorno = obterMensagemPeloTipoExcecao(erro);

        if (mensagemRetorno == null && nomeClasseDialeto != null)
        {
            mensagemRetorno = obterMensagemPeloNumeroErroEDialeto(erro, nomeClasseDialeto);
        }

        if (mensagemRetorno == null)
        {
            mensagemRetorno = CDException.ERRO_INESPERADO;
        }

        return mensagemRetorno;
    }

    /**
     * Obtém a mensagem a ser exibida pelo usuário
     * 
     * @param strBanco Banco utilizado
     * @param intErro Código do erro
     * @param strMensagem Mensagem gerada pelo banco
     * @return Mensagem a ser exibida
     */
    private static String obterMensagemPeloNumeroErroEDialeto(Throwable erro, String nomeClasseDialeto)
    {
        String strMensagemRetorno = null;

        int numErro = obterNumeroErro(erro);

        if (nomeClasseDialeto != null)
        {
            String dialeto = nomeClasseDialeto.toLowerCase();

            // Verifica qual o banco
            if (dialeto.indexOf("oracle") > -1)
            {
                strMensagemRetorno = obterMensagemOracle(numErro, erro);
            }
            else if (dialeto.indexOf("ingres") > -1)
            {
                strMensagemRetorno = obterMensagemIngres(numErro, erro);
            }
            else if (dialeto.indexOf("mysql") > -1)
            {
                strMensagemRetorno = obterMensagemMySQL(numErro, erro);
            }
        }

        return strMensagemRetorno;
    }

    private static String obterMensagemOracle(int intErro, Throwable erro)
    {
        // Declarações
        String mensagemRetorno = null;

        // Verifica erros conhecidos
        if (intErro == 1)
        {
            mensagemRetorno = RESTRICAO_UNICA;
        }
        else if (intErro == 1400)
        {
            mensagemRetorno = RESTRICAO_NOT_NULL;
        }
        else if (intErro == 2290)
        {
            mensagemRetorno = RESTRICAO_CHECAGEM;
        }
        else if (intErro == 2291)
        {
            mensagemRetorno = RESTRICAO_REFERENCIA;
        }
        else if (intErro == 2292 || intErro == 2291)
        {
            mensagemRetorno = RESTRICAO_INTEGRIDADE_VIOLADA;
        }
        else if (intErro == 20000)
        {
            String mensagem = erro.getMessage();
            mensagemRetorno = mensagem.substring(10, mensagem.indexOf("ORA-", 10));
        }

        // Retorna
        return mensagemRetorno;
    }

    private static String obterMensagemIngres(int intErro, Throwable erro)
    {
        // Declarações
        String mensagemRetorno = null;

        // Verifica erros conhecidos
        if (intErro == 1)
        {
            String mensagem = erro.getMessage();
            mensagemRetorno = mensagem.substring(32);
        }
        else if (intErro == 4500)
        {
            mensagemRetorno = RESTRICAO_UNICA;
        }

        // Retorna
        return mensagemRetorno;
    }

    private static String obterMensagemMySQL(int intErro, Throwable erro)
    {
        // Declarações
        String strRetorno = null;

        // Verifica erros conhecidos
        if (intErro == 1169)
        {
            strRetorno = RESTRICAO_UNICA;
        }
        else if (intErro == 1215 || intErro == 1217)
        {
            strRetorno = RESTRICAO_INTEGRIDADE_VIOLADA;
        }
        else if (intErro == 1216)
        {
            strRetorno = RESTRICAO_REFERENCIA;
        }
        else if (intErro == 1263)
        {
            strRetorno = RESTRICAO_NOT_NULL;
        }

        // Retorna
        return strRetorno;
    }
}

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

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;

import br.gov.camara.exception.CDException;

/**
 * Classe para recupera��o de mensagens espec�ficas para cada banco de dados
 */
public class MensagemErroBD
{
    /**
    * Logger for this class
    */
    private static final Log log = LogFactory.getLog(MensagemErroBD.class);

    private static final String RESTRICAO_NOT_NULL = "Ocorreu uma viola��o de restri��o de valor n�o nulo.";
    private static final String RESTRICAO_CHECAGEM = "Ocorreu uma viola��o de restri��o de checagem.";
    private static final String RESTRICAO_REFERENCIA = "Ocorreu uma viola��o de restri��o de refer�ncia.";
    private static final String RESTRICAO_INTEGRIDADE_VIOLADA = "Ocorreu uma viola��o de restri��o de integridade.";
    private static final String RESTRICAO_UNICA = "Ocorreu uma viola��o de restri��o �nica.";

    /**
     * Obt�m a mensagem a ser exibida pelo usu�rio
     * 
     * @param strBanco Banco utilizado
     * @param intErro C�digo do erro
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
                        log.info(" Restri��o " + ((ConstraintViolationException) erro).getConstraintName() + ".");
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
     * Obt�m a mensagem a ser exibida pelo usu�rio
     * 
     * @param strBanco Banco utilizado
     * @param intErro C�digo do erro
     * @param strMensagem Mensagem gerada pelo banco
     * @return Mensagem a ser exibida
     */
    protected static String obterMensagem(Throwable erro)
    {
        return obterMensagem(erro, null);
    }

    /**
     * Obt�m a mensagem a ser exibida pelo usu�rio
     * 
     * @param strBanco Banco utilizado
     * @param intErro C�digo do erro
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
     * Obt�m a mensagem a ser exibida pelo usu�rio
     * 
     * @param strBanco Banco utilizado
     * @param intErro C�digo do erro
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
        // Declara��es
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
        // Declara��es
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
        // Declara��es
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

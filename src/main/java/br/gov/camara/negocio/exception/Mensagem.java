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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe para recuperação de mensagens específicas para cada banco de dados
 */
public class Mensagem
{
    /**
    * Logger for this class
    */
    private static final Log log = LogFactory.getLog(Mensagem.class);

    private static final String strCHAVE_DUPLICADA = "A informação que você está tentando incluir já existe";
    private static final String strRESTRICAO_CHECAGEM = "O valor informado não é permitido";
    private static final String strREFERENCIA_INEXISTENTE = "A informação que você está tentando incluir ou alterar está referenciando algo que não existe";
    private static final String strINTEGRIDADE_VIOLADA = "A informação que você está tentando excluir está sendo referenciada em outra parte do sistema";
    private static final String strOBRIGATORIEDADE = "Você está omitindo uma informação obrigatória";

    /**
     * Obtém a mensagem a ser exibida pelo usuário
     * 
     * @param strBanco Banco utilizado
     * @param intErro Código do erro
     * @param strMensagem Mensagem gerada pelo banco
     * @return Mensagem a ser exibida
     */
    public static String obterMensagem(String strBanco, int intErro, String strMensagem)
    {
        String strMensagemRetorno = null;
        if (strBanco != null)
        {
            // Verifica qual o banco
            if (strBanco.toLowerCase().indexOf("oracle") > -1)
            {
                strMensagemRetorno = obterMensagemOracle(intErro, strMensagem);
            }
            else if (strBanco.toLowerCase().indexOf("ingres") > -1)
            {
                strMensagemRetorno = obterMensagemIngres(intErro, strMensagem);
            }
            else if (strBanco.toLowerCase().indexOf("mysql") > -1)
            {
                strMensagemRetorno = obterMensagemMySQL(intErro, strMensagem);
            }
        }

        if (strMensagemRetorno == null || "".equals(strMensagemRetorno))
        {
            strMensagemRetorno = "Erro desconhecido";
        }

        log.info(strMensagemRetorno + " -> " + strMensagem);

        return strMensagemRetorno;
    }

    private static String obterMensagemOracle(int intErro, String strMensagem)
    {
        // Declarações
        String strRetorno = null;

        // Verifica erros conhecidos
        if (intErro == 1)
        {
            strRetorno = strCHAVE_DUPLICADA;
        }
        else if (intErro == 1400)
        {
            strRetorno = strOBRIGATORIEDADE;
        }
        else if (intErro == 2291)
        {
            strRetorno = strRESTRICAO_CHECAGEM;
        }
        else if (intErro == 2291)
        {
            strRetorno = strREFERENCIA_INEXISTENTE;
        }
        else if (intErro == 2292)
        {
            strRetorno = strINTEGRIDADE_VIOLADA;
        }
        else if (intErro == 20000)
        {
            strRetorno = strMensagem.substring(10, strMensagem.indexOf("ORA-", 10));
        }

        // Retorna
        return strRetorno;
    }

    private static String obterMensagemIngres(int intErro, String strMensagem)
    {
        // Declarações
        String strRetorno = null;

        // Verifica erros conhecidos
        if (intErro == 1)
        {
            strRetorno = strMensagem.substring(32);
        }
        else if (intErro == 4500)
        {
            strRetorno = strCHAVE_DUPLICADA;
        }

        // Retorna
        return strRetorno;
    }

    private static String obterMensagemMySQL(int intErro, String strMensagem)
    {
        // Declarações
        String strRetorno = null;

        // Verifica erros conhecidos
        if (strMensagem.toLowerCase().indexOf("foreign key constraint") > -1)
        {
            strRetorno = strINTEGRIDADE_VIOLADA;
        }

        // Retorna
        return strRetorno;
    }
}

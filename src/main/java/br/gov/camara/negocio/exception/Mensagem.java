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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe para recupera��o de mensagens espec�ficas para cada banco de dados
 */
public class Mensagem
{
    /**
    * Logger for this class
    */
    private static final Log log = LogFactory.getLog(Mensagem.class);

    private static final String strCHAVE_DUPLICADA = "A informa��o que voc� est� tentando incluir j� existe";
    private static final String strRESTRICAO_CHECAGEM = "O valor informado n�o � permitido";
    private static final String strREFERENCIA_INEXISTENTE = "A informa��o que voc� est� tentando incluir ou alterar est� referenciando algo que n�o existe";
    private static final String strINTEGRIDADE_VIOLADA = "A informa��o que voc� est� tentando excluir est� sendo referenciada em outra parte do sistema";
    private static final String strOBRIGATORIEDADE = "Voc� est� omitindo uma informa��o obrigat�ria";

    /**
     * Obt�m a mensagem a ser exibida pelo usu�rio
     * 
     * @param strBanco Banco utilizado
     * @param intErro C�digo do erro
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
        // Declara��es
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
        // Declara��es
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
        // Declara��es
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

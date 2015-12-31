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

/**
 *
 */
package sigesp.comum.util.struts;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;

import br.gov.camara.exception.CDException;

/**
 * @author P_6414
 *
 */
public abstract class SigespSequencia
{

    // Variaveis internas

    protected ActionMapping mapping = null;
    protected ActionForm form = null;
    protected HttpServletRequest request = null;
    protected HttpServletResponse response = null;
    protected ActionMessages errosRequest = null;
    protected ActionMessages erros = null;
    protected ActionMessages mensagensRequest = null;
    protected ActionMessages mensagens = null;
    protected ArrayList arlMensagens = null;
    protected Action action = null;
    protected Map mapRecursos = null;

    /**
     * Obtem a mensagem a ser exibida dada uma CDExcetion, conforme o padrão:
     * 
     * Mensagem padrão a ser exibida [tpmensagemSequencia.chave_recuperar]
     * 
     * 1) Procura o texto associado à chave "sigesp.NomeModulo.interface.tpmensagem.chave_recuperar"
     * no arquivo de recursos do módulo atual. Se encontrou, retorna o texto;
     * 
     * 2) Se o texto não foi encontrado, procura a mesma chave (substituindo "NomeModulo" por "comum") 
     * no módulo comum. Se encontrou, retorna o texto;
     * 
     * 3) Se o texto não foi encontrado, retorna a mensagem antes das chaves "..... [..]", que 
     * no exemplo acima é "Mensagem padrão a ser exibida".
     * 
     * @param cde Exceção contendo a mensagem de erro
     * @param tipo Prefixo que indica o tipo da mensagem (nos exemplos acima: tpmensagem) 
     * @return
     */
    public String obterMensagem(CDException cde, String tipo)
    {
        String mensagem = cde.obterMensagem();
        if (mensagem == null)
        {
            mensagem = cde.getCause().getMessage();
            if (mensagem == null)
            {
                mensagem = "Ocorreu um erro do tipo: " + cde.getCause().getClass().getName();
            }
        }

        if (mensagem != null)
        {
            // Foi especificado para recuperar do arquivo de propriedades ? => qdo >= 0
            int recuperaRecursos = mensagem.indexOf(tipo + "Sequencia");

            if (recuperaRecursos > -1)
            {
                String mensagemRecuperada = null;
                String nomeModulo = obterNomeModulo();
                String chaveMensagem = null;
                if (recuperaRecursos == 0)
                {
                    // A mensagem na CDException só contém a chave no arquivo de recursos 
                    chaveMensagem = mensagem.substring(recuperaRecursos + (tipo + "Sequencia.").length());
                }
                else
                {
                    // A mensagem na CDException deve seguir o padrão "msg [tpmsg.chave]
                    chaveMensagem = mensagem.substring(recuperaRecursos + ("[" + tipo + "Sequencia.").length() - 1, mensagem.indexOf("]"));
                }

                // Primeiro, tenta recuperar a mensagem com a chave do módulo em questão
                if (!"".equals(nomeModulo))
                {
                    mensagemRecuperada = obterMensagem("sigesp." + nomeModulo + ".interface." + tipo + "." + chaveMensagem);
                }

                // Se não encontrou a mensagem, procura com a chave do no módulo comum
                if (mensagemRecuperada == null)
                {
                    nomeModulo = "comum";
                    mensagemRecuperada = obterMensagem("sigesp." + nomeModulo + ".interface." + tipo + "." + chaveMensagem);
                }

                // Se não encontrou nada, recupera a mensagem padrão
                if (mensagemRecuperada == null)
                {
                    if (recuperaRecursos == 0)
                    {
                        mensagemRecuperada = mensagem;
                    }
                    else
                    {
                        mensagemRecuperada = mensagem.substring(0, recuperaRecursos - 1);
                    }
                }

                mensagem = mensagemRecuperada;
            }
        }
        return mensagem;
    }

    public String obterMensagem(CDException cde)
    {
        return obterMensagem(cde, "mensagem");
    }

    public String obterMensagemErro(CDException cde)
    {
        return obterMensagem(cde, "erro");
    }

    public String obterMensagem(String chaveMensagem)
    {
        String mensagemRecuperada = null;
        String nomeModulo = obterNomeModulo();

        // Primeiro, tenta recuperar a mensagem do módulo em questão
        if (!"".equals(nomeModulo))
        {
            mensagemRecuperada = obterRecursos(nomeModulo).getMessage(chaveMensagem);
        }

        // Se não encontrou a mensagem, procura no módulo comum
        if (mensagemRecuperada == null)
        {
            nomeModulo = "comum";
            mensagemRecuperada = obterRecursos(nomeModulo).getMessage(chaveMensagem);
        }

        return mensagemRecuperada;
    }

    public MessageResources obterRecursos(String nomeModulo)
    {
        MessageResources recurso = (MessageResources) mapRecursos.get(nomeModulo);

        return recurso;
    }

    public String obterNomeModulo()
    {
        return ((ModuleConfig) request.getAttribute(Globals.MODULE_KEY)).getPrefix().substring(1);
    }
}

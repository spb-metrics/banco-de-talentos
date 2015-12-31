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
     * Obtem a mensagem a ser exibida dada uma CDExcetion, conforme o padr�o:
     * 
     * Mensagem padr�o a ser exibida [tpmensagemSequencia.chave_recuperar]
     * 
     * 1) Procura o texto associado � chave "sigesp.NomeModulo.interface.tpmensagem.chave_recuperar"
     * no arquivo de recursos do m�dulo atual. Se encontrou, retorna o texto;
     * 
     * 2) Se o texto n�o foi encontrado, procura a mesma chave (substituindo "NomeModulo" por "comum") 
     * no m�dulo comum. Se encontrou, retorna o texto;
     * 
     * 3) Se o texto n�o foi encontrado, retorna a mensagem antes das chaves "..... [..]", que 
     * no exemplo acima � "Mensagem padr�o a ser exibida".
     * 
     * @param cde Exce��o contendo a mensagem de erro
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
                    // A mensagem na CDException s� cont�m a chave no arquivo de recursos 
                    chaveMensagem = mensagem.substring(recuperaRecursos + (tipo + "Sequencia.").length());
                }
                else
                {
                    // A mensagem na CDException deve seguir o padr�o "msg [tpmsg.chave]
                    chaveMensagem = mensagem.substring(recuperaRecursos + ("[" + tipo + "Sequencia.").length() - 1, mensagem.indexOf("]"));
                }

                // Primeiro, tenta recuperar a mensagem com a chave do m�dulo em quest�o
                if (!"".equals(nomeModulo))
                {
                    mensagemRecuperada = obterMensagem("sigesp." + nomeModulo + ".interface." + tipo + "." + chaveMensagem);
                }

                // Se n�o encontrou a mensagem, procura com a chave do no m�dulo comum
                if (mensagemRecuperada == null)
                {
                    nomeModulo = "comum";
                    mensagemRecuperada = obterMensagem("sigesp." + nomeModulo + ".interface." + tipo + "." + chaveMensagem);
                }

                // Se n�o encontrou nada, recupera a mensagem padr�o
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

        // Primeiro, tenta recuperar a mensagem do m�dulo em quest�o
        if (!"".equals(nomeModulo))
        {
            mensagemRecuperada = obterRecursos(nomeModulo).getMessage(chaveMensagem);
        }

        // Se n�o encontrou a mensagem, procura no m�dulo comum
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

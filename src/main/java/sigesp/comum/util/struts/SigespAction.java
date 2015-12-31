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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.ajaxanywhere.AAUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ModuleUtils;
import org.apache.struts.util.RequestUtils;

import sigesp.comum.util.biblioteca.Constantes;
import sigesp.comum.util.struts.actions.ActionMessagesUtil;

/**
 * @author P_6414
 */
public abstract class SigespAction extends Action
{
    /**
     * Logger for this class
     */
    private static final Log log = LogFactory.getLog(SigespAction.class);

    protected ActionForm processarFormBean(HttpServletRequest request, ActionMapping mapping, ActionForm form)
    {
        HttpSession session = request.getSession();
        // Foi pedido pra remover este formbean ?

        // Recuperar o map
        Map mapRemocaoFormBeans = (HashMap) request.getAttribute(Constantes.strREMOCAO_FORMBEAN);
        if (mapRemocaoFormBeans != null)
        {
            if (mapRemocaoFormBeans.containsKey(mapping.getAttribute()))
            {
                if (mapping.getScope().equals(mapRemocaoFormBeans.get(mapping.getAttribute())))
                {
                    // Remover o FormBean
                    if ("request".equals(mapping.getScope()))
                    {
                        request.removeAttribute(mapping.getAttribute());
                    }
                    else
                    {
                        session.removeAttribute(mapping.getAttribute());
                    }

                    // Remover o FB do map
                    mapRemocaoFormBeans.remove(mapping.getAttribute());

                    // Como o map foi alterado, guarda-lo novamente no request
                    request.setAttribute(Constantes.strREMOCAO_FORMBEAN, mapRemocaoFormBeans);

                    // Registrar log
                    if (log.isDebugEnabled())
                    {
                        log.debug("O formbean '" + mapping.getAttribute() + "' foi novamente removido do escopo '" + mapping.getScope() + "'.");
                    }
                }
                else
                {
                    // Registrar log
                    if (log.isDebugEnabled())
                    {
                        log.debug("O escopo do mapping atual '"
                                + mapping.getScope()
                                + "' nao e o mesmo do registrado '"
                                + mapRemocaoFormBeans.get(mapping.getAttribute())
                                + "'.");
                    }
                }
            }
            else
            {
                // Registrar log
                if (log.isDebugEnabled())
                {
                    log.debug("O mapping atual '" + mapping.getAttribute() + "' nao esta registrado na lista de Formbeans a serem removidos.");
                }
            }
        }
        else
        {
            // Registrar log
            if (log.isDebugEnabled())
            {
                log.debug("Nao existe registro de Formbean a ser removido.");
            }
        }

        // Verifica se � necess�rio limpar form-bean
        if (request.getParameter("limparFB") != null)
        {
            form = this.criarNovoForm(mapping, request);
        }

        return form;
    }

    private ActionForm criarNovoForm(ActionMapping mapping, HttpServletRequest request)
    {
        ActionForm objForm = null;

        // Verifica se � necess�rio limpar form-bean
        if (request.getParameter("limparFB") != null)
        {
            // Look up the form bean configuration information to use
            String name = mapping.getName();
            FormBeanConfig config = mapping.getModuleConfig().findFormBeanConfig(name);
            if (config == null)
            {
                if (log.isInfoEnabled())
                {
                    log.info("N�o foi encontrada nenhuma configura��o FormBeanConfig com o nome '" + name + "'");
                }
            }
            else
            {
                objForm = RequestUtils.createActionForm(config, this.servlet);
                objForm.setServlet(this.getServlet());
                if ("request".equals(mapping.getScope()))
                {
                    request.setAttribute(mapping.getAttribute(), objForm);
                }
                else
                {
                    request.getSession().setAttribute(mapping.getAttribute(), objForm);
                }
            }
        }
        return objForm;
    }

    protected Map obterMapaMensagens(HttpServletRequest request)
    {
        // Inicializa as variaveis que conterao as mensagens e erros

        Map mapMensagens = new HashMap();

        ActionMessages errosRequest = (ActionMessages) request.getAttribute(Globals.ERROR_KEY);
        if (errosRequest == null)
        {
            errosRequest = new ActionMessages();
        }
        mapMensagens.put(ActionMessagesUtil.CHAVE_REQUEST_ERROS, errosRequest);

        ActionMessages mensagensRequest = (ActionMessages) request.getAttribute(Globals.MESSAGE_KEY);
        if (mensagensRequest == null)
        {
            mensagensRequest = new ActionMessages();
        }
        mapMensagens.put(ActionMessagesUtil.CHAVE_REQUEST_MENSAGENS, errosRequest);

        mapMensagens.put(ActionMessagesUtil.CHAVE_SEQUENCIA_ERROS, new ActionMessages());
        mapMensagens.put(ActionMessagesUtil.CHAVE_SEQUENCIA_MENSAGENS, new ActionMessages());

        return mapMensagens;
    }

    protected Map obterMapaRecursos(HttpServletRequest request)
    {
        Map mapRecursos = new HashMap();
        String nome = ModuleUtils.getInstance().getModuleName(request, request.getSession().getServletContext());

        if (nome.length() > 0)
        {
            mapRecursos.put(nome.substring(1), this.getResources(request));
        }
        mapRecursos.put("comum", this.getResources(request, "comum"));

        return mapRecursos;
    }

    protected void tratarExcecao(Exception lazye, Map mapMensagens, Map mapRecursos)
    {
        if (lazye.getCause() != null)
        {
            String nomeClasseCausaErro = lazye.getCause().getClass().getName();
            if (nomeClasseCausaErro.indexOf("ObjectNotFoundException") > -1)
            {
                String mensagemCompleta = lazye.getCause().toString();
                String nomeObjeto = null;
                int indiceOfClass = mensagemCompleta.indexOf("of class:");
                nomeObjeto = mensagemCompleta.substring(indiceOfClass + 10);
                String msgEx = ((MessageResources) mapRecursos.get("comum")).getMessage("sigesp.comum.banco.erro.problemaConsistencia");
                String msgLog = msgEx + " . O valor da chave '" + "obj.getIdentificador ()" + "' n�o foi encontrado no objeto " + nomeObjeto;
                log.error(msgLog);
                ((ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_SEQUENCIA_ERROS)).add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", msgEx));
            }
            else if (lazye.getCause().getMessage().indexOf("Session was closed") > -1)
            {
                String msgLog = "N�o foi poss�vel recuperar o objeto "
                        + lazye.getMessage().substring(lazye.getMessage().lastIndexOf("["), lazye.getMessage().lastIndexOf("]") + 1)
                        + " porque a sess�o Hibernate estava fechada.";
                log.error(msgLog);

                String msgEx = "N�o foi poss�vel recuperar o objeto ["
                        + lazye.getMessage().substring(lazye.getMessage().lastIndexOf(".") + 1, lazye.getMessage().indexOf("#"))
                        + "] porque a sess�o Hibernate estava fechada.";
                ((ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_SEQUENCIA_ERROS)).add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", msgEx));
            }
            else
            {
                throw new RuntimeException(lazye);
            }
        }
        else
        {
            throw new RuntimeException(lazye);
        }
    }

    protected ActionForward registrarMapaMensagens(HttpServletRequest request, Map mapMensagens, ActionMapping mapping)
    {
        ActionForward forwardErro = null;

        // Registra as mensagens no escopo de request
        if (!((ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_SEQUENCIA_MENSAGENS)).isEmpty())
        {
            ((ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_SEQUENCIA_MENSAGENS)).add(((ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_REQUEST_MENSAGENS)));
            this.saveMessages(request, ((ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_SEQUENCIA_MENSAGENS)));
        }

        // Registra os erros encontrados no escopo de request
        if (!((ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_SEQUENCIA_ERROS)).isEmpty())
        {
            if (log.isDebugEnabled())
            {
                log.debug("Existem erros, redirecionando para o formulario correspondente");
            }
            ((ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_SEQUENCIA_ERROS)).add(((ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_REQUEST_ERROS)));
            this.saveErrors(request, ((ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_SEQUENCIA_ERROS)));
            forwardErro = mapping.getInputForward();
        }

        if (AAUtils.isAjaxRequest(request))
        {
            AAUtils.addZonesToRefresh(request, "aaMensagens");
        }

        return forwardErro;
    }

    /**
     * Processa a requisicao HTTP especificada e cria a resposta HTTP correspondente (ou encaminha para um outro
     * componente que a ira criar).
     * 
     * @param mapping O ActionMapping utilizado para selecionar esta instancia
     * @param form O ActionForm bean (opcional) para esta requisicao
     * @param request A requisicao HTTP que esta sendo processada
     * @param response A resposta HTTP que esta sendo criada
     * @return Retorna uma instancia ActionForward descrevendo onde e como o controle deve ser encaminhado ou null se a
     *         resposta ja foi completada
     * @exception Excecao se a logica de sequencia enviar uma Exception
     */

    public void forcarRemocaoFormBean(ActionMapping mapping, HttpServletRequest request)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Forcando a remocao do FormBean '" + mapping.getAttribute() + "' do escopo '" + mapping.getScope() + "'.");
        }

        // Preparar o map
        Map mapRemocaoFormBeans = (HashMap) request.getSession().getAttribute(Constantes.strREMOCAO_FORMBEAN);

        if (mapRemocaoFormBeans == null)
        {
            mapRemocaoFormBeans = new HashMap();
        }
        if (!mapRemocaoFormBeans.containsKey(mapping.getAttribute()))
        {
            mapRemocaoFormBeans.put(mapping.getAttribute(), mapping.getScope());
        }
        request.setAttribute(Constantes.strREMOCAO_FORMBEAN, mapRemocaoFormBeans);

        // Remover o FormBean
        if ("request".equals(mapping.getScope()))
        {
            request.removeAttribute(mapping.getAttribute());
        }
        else
        {
            request.getSession().removeAttribute(mapping.getAttribute());
        }

    }
}

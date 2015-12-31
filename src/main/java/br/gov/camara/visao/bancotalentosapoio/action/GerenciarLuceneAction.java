/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.camara.visao.bancotalentosapoio.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import sigesp.comum.util.struts.SigespAction;
import sigesp.comum.util.struts.actions.ActionMessagesUtil;

import br.gov.camara.visao.bancotalentosapoio.sequencia.GerenciarLuceneSequencia;

/**
 *
 * @author christian.braz
 */
public class GerenciarLuceneAction extends SigespAction {

    // Inicializa o log
    private static Log log = LogFactory.getLog(GerenciarLuceneAction.class);

    // Metodos publicos -------------------------------------------------------
    /**
     * Processa a requisicao HTTP especificada e cria a resposta HTTP
     * correspondente (ou encaminha para um outro componente que a ira criar).
     *
     * @param mapping O ActionMapping utilizado para selecionar esta instancia
     * @param form O ActionForm bean (opcional) para esta requisicao
     * @param request A requisicao HTTP que esta sendo processada
     * @param response A resposta HTTP que esta sendo criada
     *
     * @return Retorna uma instancia ActionForward descrevendo onde e como
     * o controle deve ser encaminhado ou null se a resposta ja foi completada
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) {
        if (log.isDebugEnabled()) {
            log.debug("Entrada no metodo");
        }

        // Preparacao
        form = processarFormBean(request, mapping, form);
        Map mapMensagens = obterMapaMensagens(request);
        Map mapRecursos = obterMapaRecursos(request);

        // Variavel que indica se deve ou nao FORCAR a remocao do FormBean
        boolean blnForcarRemocaoFormBean = false;

        try {

            List arlMensagens = null;

            // Instancia a respectiva classe de sequencia
            GerenciarLuceneSequencia sequencia = new GerenciarLuceneSequencia(mapping, form, request, response, (ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_REQUEST_ERROS), (ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_REQUEST_MENSAGENS), this, mapRecursos);

            if ("/reindexarLucene".equals(mapping.getPath())) {

                arlMensagens = sequencia.reindexar();
            }
   
            if (arlMensagens != null && arlMensagens.size() > 0) {
                if (arlMensagens.size() > ActionMessagesUtil.INDEX_MENSAGENS) {
                    ((ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_SEQUENCIA_MENSAGENS)).add((ActionMessages) arlMensagens.get(ActionMessagesUtil.INDEX_MENSAGENS));
                }
                if (arlMensagens.size() > ActionMessagesUtil.INDEX_ERROS) {
                    ((ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_SEQUENCIA_ERROS)).add((ActionMessages) arlMensagens.get(ActionMessagesUtil.INDEX_ERROS));
                }
            }


        } catch (Exception excecao) {
            tratarExcecao(excecao, mapMensagens, mapRecursos);
        }

        ActionForward proximoForward = null;

        // Registra as mensagens no escopo de request
        proximoForward = registrarMapaMensagens(request, mapMensagens, mapping);
        if (proximoForward == null) {
            // Forcar a remocao do FormBean ?
            if (blnForcarRemocaoFormBean) {
                forcarRemocaoFormBean(mapping, request);
            }
            proximoForward = mapping.findForward("sucesso");
        }

        // Redirecionando para o proximo forward
        return proximoForward;
    }
}

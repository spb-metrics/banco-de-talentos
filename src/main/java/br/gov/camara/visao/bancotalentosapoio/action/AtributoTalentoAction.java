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

import br.gov.camara.visao.bancotalentosapoio.sequencia.AtributoTalentoSequencia;

/**
 * Action AtributoTalentoAction
 *
 * @version FALTA DOCUMENTACAO
 * @author FALTA DOCUMENTACAO
 */

public final class AtributoTalentoAction extends SigespAction
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(AtributoTalentoAction.class);

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
            HttpServletResponse response)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Preparacao
        form = processarFormBean(request, mapping, form);
        Map mapMensagens = obterMapaMensagens(request);
        Map mapRecursos = obterMapaRecursos(request);

        // Variavel que indica se deve ou nao FORCAR a remocao do FormBean
        boolean blnForcarRemocaoFormBean = false;

        try
        {

            List arlMensagens = null;

            // Instancia a respectiva classe de sequencia

            AtributoTalentoSequencia atributoTalentoSequencia = new AtributoTalentoSequencia (mapping, form, request, response, (ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_REQUEST_ERROS), (ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_REQUEST_MENSAGENS), this, mapRecursos);
        	
        // Efetuar as operacoes do mapeamento '/atributoTalentoEfetuarAlteracao'
        
        if ("/atributoTalentoEfetuarAlteracao".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/atributoTalentoEfetuarAlteracao'
            arlMensagens = atributoTalentoSequencia.atributoTalentoEfetuarAlteracao();
        }
     
        // Efetuar as operacoes do mapeamento '/atributoTalentoEfetuarExclusao'
        
        if ("/atributoTalentoEfetuarExclusao".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/atributoTalentoEfetuarExclusao'
            arlMensagens = atributoTalentoSequencia.atributoTalentoEfetuarExclusao();
        }
     
        // Efetuar as operacoes do mapeamento '/atributoTalentoPrepararVisualizacao'
        
        if ("/atributoTalentoPrepararVisualizacao".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/atributoTalentoPrepararVisualizacao'
            arlMensagens = atributoTalentoSequencia.atributoTalentoPrepararVisualizacao();
        }
     
        // Efetuar as operacoes do mapeamento '/detalheAtributoTalentoEfetuarInclusao'
        
        if ("/detalheAtributoTalentoEfetuarInclusao".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/detalheAtributoTalentoEfetuarInclusao'
            arlMensagens = atributoTalentoSequencia.detalheAtributoTalentoEfetuarInclusao();
            blnForcarRemocaoFormBean = true;
        }
     
        // Efetuar as operacoes do mapeamento '/detalheAtributoTalentoPrepararAlteracao'
        
        if ("/detalheAtributoTalentoPrepararAlteracao".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/detalheAtributoTalentoPrepararAlteracao'
            arlMensagens = atributoTalentoSequencia.detalheAtributoTalentoPrepararAlteracao();
        }
     
        // Efetuar as operacoes do mapeamento '/detalheAtributoTalentoPrepararInclusao'
        
        if ("/detalheAtributoTalentoPrepararInclusao".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/detalheAtributoTalentoPrepararInclusao'
            arlMensagens = atributoTalentoSequencia.detalheAtributoTalentoPrepararInclusao();
        }
     
        // Efetuar as operacoes do mapeamento '/exclusaoAtributoTalentoPrepararExclusao'
        
        if ("/exclusaoAtributoTalentoPrepararExclusao".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/exclusaoAtributoTalentoPrepararExclusao'
            arlMensagens = atributoTalentoSequencia.exclusaoAtributoTalentoPrepararExclusao();
        }
     

            if (arlMensagens != null && arlMensagens.size() > 0)
            {
                if (arlMensagens.size() > ActionMessagesUtil.INDEX_MENSAGENS)
                {
                    ((ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_SEQUENCIA_MENSAGENS)).add((ActionMessages) arlMensagens.get(ActionMessagesUtil.INDEX_MENSAGENS));
                }
                if (arlMensagens.size() > ActionMessagesUtil.INDEX_ERROS)
                {
                    ((ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_SEQUENCIA_ERROS)).add((ActionMessages) arlMensagens.get(ActionMessagesUtil.INDEX_ERROS));
                }
            }

        }
        catch (Exception excecao)
        {
            tratarExcecao(excecao, mapMensagens, mapRecursos);
        }

        ActionForward proximoForward = null;

        // Registra as mensagens no escopo de request
        proximoForward = registrarMapaMensagens(request, mapMensagens, mapping);
        if (proximoForward == null)
        {
            // Forcar a remocao do FormBean ?
            if (blnForcarRemocaoFormBean)
            {
                forcarRemocaoFormBean(mapping, request);
            }
            proximoForward = mapping.findForward("sucesso");
        }

        // Redirecionando para o proximo forward
        return proximoForward;
    }
}
    
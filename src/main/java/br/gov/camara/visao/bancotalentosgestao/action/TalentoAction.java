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



package br.gov.camara.visao.bancotalentosgestao.action;

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

import br.gov.camara.visao.bancotalentosgestao.sequencia.TalentoSequencia;

/**
 * Action TalentoAction
 *
 * @version FALTA DOCUMENTACAO
 * @author FALTA DOCUMENTACAO
 */

public final class TalentoAction extends SigespAction
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(TalentoAction.class);

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

            TalentoSequencia talentoSequencia = new TalentoSequencia (mapping, form, request, response, (ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_REQUEST_ERROS), (ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_REQUEST_MENSAGENS), this, mapRecursos);
        	
        // Efetuar as operacoes do mapeamento '/detalheTalentoBuscarHierarquia'
        
        if ("/detalheTalentoBuscarHierarquia".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/detalheTalentoBuscarHierarquia'
            arlMensagens = talentoSequencia.detalheTalentoBuscarHierarquia();
        }
     
        // Efetuar as operacoes do mapeamento '/detalheTalentoBuscarOpcao'
        
        if ("/detalheTalentoBuscarOpcao".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/detalheTalentoBuscarOpcao'
            arlMensagens = talentoSequencia.detalheTalentoBuscarOpcao();
        }
     
        // Efetuar as operacoes do mapeamento '/detalheTalentoPrepararAlteracao'
        
        if ("/detalheTalentoPrepararAlteracao".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/detalheTalentoPrepararAlteracao'
            arlMensagens = talentoSequencia.detalheTalentoPrepararAlteracao();
        }
     
        // Efetuar as operacoes do mapeamento '/detalheTalentoPrepararInclusao'
        
        if ("/detalheTalentoPrepararInclusao".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/detalheTalentoPrepararInclusao'
            arlMensagens = talentoSequencia.detalheTalentoPrepararInclusao();
        }
     
        // Efetuar as operacoes do mapeamento '/encaminhamentoTalentoEfetuarInclusao'
        
        if ("/encaminhamentoTalentoEfetuarInclusao".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/encaminhamentoTalentoEfetuarInclusao'
            arlMensagens = talentoSequencia.encaminhamentoTalentoEfetuarInclusao();
        }
     
        // Efetuar as operacoes do mapeamento '/exclusaoTalentoPrepararExclusao'
        
        if ("/exclusaoTalentoPrepararExclusao".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/exclusaoTalentoPrepararExclusao'
            arlMensagens = talentoSequencia.exclusaoTalentoPrepararExclusao();
        }
     
        // Efetuar as operacoes do mapeamento '/talentoEfetuarAlteracao'
        
        if ("/talentoEfetuarAlteracao".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/talentoEfetuarAlteracao'
            arlMensagens = talentoSequencia.talentoEfetuarAlteracao();
        }
     
        // Efetuar as operacoes do mapeamento '/talentoEfetuarExclusao'
        
        if ("/talentoEfetuarExclusao".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/talentoEfetuarExclusao'
            arlMensagens = talentoSequencia.talentoEfetuarExclusao();
        }
     
        // Efetuar as operacoes do mapeamento '/talentoEfetuarFiltro'
        
        if ("/talentoEfetuarFiltro".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/talentoEfetuarFiltro'
            arlMensagens = talentoSequencia.talentoEfetuarFiltro();
        }
     
        // Efetuar as operacoes do mapeamento '/talentoPrepararVisualizacao'
        
        if ("/talentoPrepararVisualizacao".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/talentoPrepararVisualizacao'
            arlMensagens = talentoSequencia.talentoPrepararVisualizacao();
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
    
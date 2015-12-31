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

import br.gov.camara.visao.bancotalentosgestao.sequencia.ConsultaTalentoSequencia;

/**
 * Action ConsultaTalentoAction
 *
 * @version FALTA DOCUMENTACAO
 * @author FALTA DOCUMENTACAO
 */

public final class ConsultaTalentoAction extends SigespAction
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(ConsultaTalentoAction.class);

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

            ConsultaTalentoSequencia consultaTalentoSequencia = new ConsultaTalentoSequencia (mapping, form, request, response, (ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_REQUEST_ERROS), (ActionMessages) mapMensagens.get(ActionMessagesUtil.CHAVE_REQUEST_MENSAGENS), this, mapRecursos);
        	
        // Efetuar as operacoes do mapeamento '/consultaTalentoAdicionarCriterio'
        
        if ("/consultaTalentoAdicionarCriterio".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/consultaTalentoAdicionarCriterio'
            arlMensagens = consultaTalentoSequencia.consultaTalentoAdicionarCriterio();
        }
     
        // Efetuar as operacoes do mapeamento '/consultaTalentoBuscarHierarquia'
        
        if ("/consultaTalentoBuscarHierarquia".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/consultaTalentoBuscarHierarquia'
            arlMensagens = consultaTalentoSequencia.consultaTalentoBuscarHierarquia();
        }
     
        // Efetuar as operacoes do mapeamento '/consultaTalentoBuscarOpcao'
        
        if ("/consultaTalentoBuscarOpcao".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/consultaTalentoBuscarOpcao'
            arlMensagens = consultaTalentoSequencia.consultaTalentoBuscarOpcao();
        }
     
        // Efetuar as operacoes do mapeamento '/consultaTalentoPrepararConsulta'
        
        if ("/consultaTalentoPrepararConsulta".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/consultaTalentoPrepararConsulta'
            arlMensagens = consultaTalentoSequencia.consultaTalentoPrepararConsulta();
        }
     
        // Efetuar as operacoes do mapeamento '/consultaTalentoPrepararVisualizacao'
        
        if ("/consultaTalentoPrepararVisualizacao".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/consultaTalentoPrepararVisualizacao'
            arlMensagens = consultaTalentoSequencia.consultaTalentoPrepararVisualizacao();
        }
     
        // Efetuar as operacoes do mapeamento '/consultaTalentoRemoverCriterio'
        
        if ("/consultaTalentoRemoverCriterio".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/consultaTalentoRemoverCriterio'
            arlMensagens = consultaTalentoSequencia.consultaTalentoRemoverCriterio();
        }
     
        // Efetuar as operacoes do mapeamento '/resultadoConsultaTalentoEfetuarConsulta'
        
        if ("/resultadoConsultaTalentoEfetuarConsulta".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/resultadoConsultaTalentoEfetuarConsulta'
            arlMensagens = consultaTalentoSequencia.resultadoConsultaTalentoEfetuarConsulta();
        }
     
        // Efetuar as operacoes do mapeamento '/resultadoConsultaTalentoGerarRelatorio'
        
        if ("/resultadoConsultaTalentoGerarRelatorio".equals(mapping.getPath()))
        {
        
            // Efetuar a operacao referente ao mapeamento '/resultadoConsultaTalentoGerarRelatorio'
            arlMensagens = consultaTalentoSequencia.resultadoConsultaTalentoGerarRelatorio();
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
    
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

package br.gov.camara.visao.bancotalentosgestao.sequencia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import sigesp.comum.util.struts.SigespSequencia;
import sigesp.comum.util.struts.actions.ActionMessagesUtil;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.bancotalentos.facade.AtributoTalentoOpcaoFacade;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaAtributoTalento;
import br.gov.camara.visao.bancotalentosgestao.form.ConsultaAtributoTalentoOpcaoForm;

/**
 * Sequencia ConsultaAtributoTalentoOpcaoSequencia
 */

public final class ConsultaAtributoTalentoOpcaoSequencia extends SigespSequencia
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(ConsultaAtributoTalentoOpcaoSequencia.class);

    /**
     * Metodo construtor
     *
     * @param mapping Mapeamento executado
     * @param form FormulÃ¡rio associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que jÃ¡ ocorreram
     * @param mensagens Colecao de mensagens a serem exibidas
     * @param ConsultaAtributoTalentoOpcaoAction Classe Action associada
     */

    public ConsultaAtributoTalentoOpcaoSequencia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,
            ActionMessages erros, ActionMessages mensagens, Action action, Map mapRecursos)
    {
        this.mapping = mapping;
        this.form = form;
        this.request = request;
        this.response = response;
        this.errosRequest = erros;
        this.action = action;
        this.mensagensRequest = mensagens;
        this.arlMensagens = new ArrayList();
        this.mapRecursos = mapRecursos;

        this.erros = new ActionMessages();
        this.mensagens = new ActionMessages();
    }

    // Metodos chamados

    /**
     * Metodo consultaAtributoTalentoOpcaoEfetuarConsulta
     */

    public ArrayList consultaAtributoTalentoOpcaoEfetuarConsulta()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = request.getSession();

        // List para armazenar resultado da consulta
        List lstAtributoTalentoOpcoes = null;
        String strTotalRegistros = "0";

        // Verifica página
        int intPagina;
        if (request.getParameter("pagina") != null)
        {
            intPagina = Integer.parseInt(request.getParameter("pagina"));
            strTotalRegistros = String.valueOf(session.getAttribute("strTotalRegistros"));
        }
        else
        {
            intPagina = 1;
        }

        // Obtém form
        ConsultaAtributoTalentoOpcaoForm consultaAtributoTalentoOpcaoForm = (ConsultaAtributoTalentoOpcaoForm) form;

        // Obtém categoria/atributo da consulta
        CategoriaAtributoTalento objCategoriaAtributoTalento = (CategoriaAtributoTalento) session.getAttribute("objCategoriaAtributoTalento");

        // Instancia Facade
        AtributoTalentoOpcaoFacade objAtributoTalentoOpcaoFacade = new AtributoTalentoOpcaoFacade();

        try
        {
            // Obtém dados
            lstAtributoTalentoOpcoes = objAtributoTalentoOpcaoFacade.obterPorDescricaoCategoriaAtributoTalentoHierarquiaPorPagina(consultaAtributoTalentoOpcaoForm.getAtributoTalentoOpcao(), objCategoriaAtributoTalento, intPagina, 20);
            if (intPagina == 1)
            {
                strTotalRegistros = String.valueOf(objAtributoTalentoOpcaoFacade.obterTotalRegistrosPorDescricaoCategoriaAtributoTalentoHierarquiaPorPagina(consultaAtributoTalentoOpcaoForm.getAtributoTalentoOpcao(), objCategoriaAtributoTalento));
                session.setAttribute("strTotalRegistros", strTotalRegistros);
            }
            request.setAttribute("lstAtributoTalentoOpcoes", lstAtributoTalentoOpcoes);
            request.setAttribute("strTotalRegistros", strTotalRegistros);
            request.setAttribute("strPaginacao", "talentoPrepararVisualizacao.do");
        }
        catch (CDException cde)
        {
            if (log.isErrorEnabled())
            {
                log.error(cde.obterExcecoes());
            }

            erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", cde.obterMensagem()));
        }

        // Mensagens        
        arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, mensagens);
        arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, erros);

        return (arlMensagens);
    }

    /**
     * Metodo consultaAtributoTalentoOpcaoPrepararVisualizacao
     */

    public ArrayList consultaAtributoTalentoOpcaoPrepararVisualizacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = request.getSession();

        // Instancia Facade
        AtributoTalentoOpcaoFacade objAtributoTalentoOpcaoFacade = new AtributoTalentoOpcaoFacade();

        try
        {
            // Obtém categoria/atributo para a consulta
            CategoriaAtributoTalento objCategoriaAtributoTalento = (CategoriaAtributoTalento) objAtributoTalentoOpcaoFacade.obterCategoriaAtributoTalentoPelaChave(request.getParameter("categoriaAtributoTalento"));
            session.setAttribute("objCategoriaAtributoTalento", objCategoriaAtributoTalento);

        }
        catch (CDException cde)
        {
            if (log.isErrorEnabled())
            {
                log.error(cde.obterExcecoes());
            }

            erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", cde.obterMensagem()));
        }

        arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, mensagens);
        arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, erros);

        return (arlMensagens);
    }

}

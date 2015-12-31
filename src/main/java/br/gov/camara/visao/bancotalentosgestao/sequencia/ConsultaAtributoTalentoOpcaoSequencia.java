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
     * @param form Formulário associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que já ocorreram
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

        // Obt�m sess�o
        HttpSession session = request.getSession();

        // List para armazenar resultado da consulta
        List lstAtributoTalentoOpcoes = null;
        String strTotalRegistros = "0";

        // Verifica p�gina
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

        // Obt�m form
        ConsultaAtributoTalentoOpcaoForm consultaAtributoTalentoOpcaoForm = (ConsultaAtributoTalentoOpcaoForm) form;

        // Obt�m categoria/atributo da consulta
        CategoriaAtributoTalento objCategoriaAtributoTalento = (CategoriaAtributoTalento) session.getAttribute("objCategoriaAtributoTalento");

        // Instancia Facade
        AtributoTalentoOpcaoFacade objAtributoTalentoOpcaoFacade = new AtributoTalentoOpcaoFacade();

        try
        {
            // Obt�m dados
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

        // Obt�m sess�o
        HttpSession session = request.getSession();

        // Instancia Facade
        AtributoTalentoOpcaoFacade objAtributoTalentoOpcaoFacade = new AtributoTalentoOpcaoFacade();

        try
        {
            // Obt�m categoria/atributo para a consulta
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

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

package br.gov.camara.visao.bancotalentosapoio.sequencia;

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
import br.gov.camara.negocio.autenticacaoperfil.facade.UsuarioSistemaFacade;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;
import br.gov.camara.visao.bancotalentosapoio.form.ConsultaUsuarioForm;

/**
 * Sequencia ConsultaUsuarioSequencia
 */

public final class ConsultaUsuarioSequencia extends SigespSequencia
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(ConsultaUsuarioSequencia.class);

    /**
     * Metodo construtor
     *
     * @param mapping Mapeamento executado
     * @param form FormulÃ¡rio associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que jÃ¡ ocorreram
     * @param mensagens Colecao de mensagens a serem exibidas
     * @param ConsultaUsuarioAction Classe Action associada
     */

    public ConsultaUsuarioSequencia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages erros,
            ActionMessages mensagens, Action action, Map mapRecursos)
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
     * Metodo consultaUsuarioEfetuarConsulta
     */

    public ArrayList consultaUsuarioEfetuarConsulta()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = request.getSession();

        // List para armazenar resultado da consulta
        List lstUsuarioSistema = null;
        String strTotalRegistros = "0";

        // Verifica página
        int intPagina;
        if (request.getParameter("pagina") != null)
        {
            intPagina = Integer.parseInt(request.getParameter("pagina"));
        }
        else
        {
            intPagina = 1;
        }

        // Obtém form
        ConsultaUsuarioForm consultaUsuarioForm = (ConsultaUsuarioForm) form;

        // Instancia Facade
        UsuarioSistemaFacade objUsuarioSistemaFacade = new UsuarioSistemaFacade();

        try
        {
            // Obtém dados
            if (consultaUsuarioForm.getIdentificador() != null && !consultaUsuarioForm.getIdentificador().equals(""))
            {
                strTotalRegistros = "1";
                lstUsuarioSistema = new ArrayList();
                UsuarioSistema objUsuarioSistema = objUsuarioSistemaFacade.obterPelaChave(consultaUsuarioForm.getIdentificador());
                if (objUsuarioSistema != null)
                {
                    lstUsuarioSistema.add(objUsuarioSistema);
                }
            }
            else if (consultaUsuarioForm.getNome() != null && !consultaUsuarioForm.getNome().equals(""))
            {
                strTotalRegistros = String.valueOf(objUsuarioSistemaFacade.obterTotalRegistrosPeloNome(consultaUsuarioForm.getNome()));
                lstUsuarioSistema = objUsuarioSistemaFacade.obterPeloNomePorPagina(consultaUsuarioForm.getNome(), intPagina, 20);
            }
            else
            {
                strTotalRegistros = String.valueOf(objUsuarioSistemaFacade.obterTotalRegistros());
                lstUsuarioSistema = objUsuarioSistemaFacade.obterTodosPorPagina(intPagina, 20);
            }
            request.setAttribute("lstUsuario", lstUsuarioSistema);
            request.setAttribute("strTotalRegistros", strTotalRegistros);
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
     * Metodo consultaUsuarioPrepararVisualizacao
     */

    public ArrayList consultaUsuarioPrepararVisualizacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, mensagens);
        arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, erros);

        return (arlMensagens);
    }

}

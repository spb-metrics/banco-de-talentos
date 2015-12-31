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
     * @param form Formulário associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que já ocorreram
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

        // Obt�m sess�o
        HttpSession session = request.getSession();

        // List para armazenar resultado da consulta
        List lstUsuarioSistema = null;
        String strTotalRegistros = "0";

        // Verifica p�gina
        int intPagina;
        if (request.getParameter("pagina") != null)
        {
            intPagina = Integer.parseInt(request.getParameter("pagina"));
        }
        else
        {
            intPagina = 1;
        }

        // Obt�m form
        ConsultaUsuarioForm consultaUsuarioForm = (ConsultaUsuarioForm) form;

        // Instancia Facade
        UsuarioSistemaFacade objUsuarioSistemaFacade = new UsuarioSistemaFacade();

        try
        {
            // Obt�m dados
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

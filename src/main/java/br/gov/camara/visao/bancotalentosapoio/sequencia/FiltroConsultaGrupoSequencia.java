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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.apache.struts.util.MessageResources;

import sigesp.comum.util.struts.SigespSequencia;
import sigesp.comum.util.struts.actions.ActionMessagesUtil;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Grupo;
import br.gov.camara.negocio.bancotalentos.facade.FiltroConsultaFacade;
import br.gov.camara.negocio.bancotalentos.pojo.FiltroConsulta;
import br.gov.camara.visao.bancotalentosapoio.form.FiltroConsultaGrupoForm;

/**
 * Sequencia FiltroConsultaGrupoSequencia
 */

public final class FiltroConsultaGrupoSequencia extends SigespSequencia
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(FiltroConsultaGrupoSequencia.class);

    /**
     * Metodo construtor
     *
     * @param mapping Mapeamento executado
     * @param form FormulÃ¡rio associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que jÃ¡ ocorreram
     * @param mensagens Colecao de mensagens a serem exibidas
     * @param FiltroConsultaGrupoAction Classe Action associada
     */

    public FiltroConsultaGrupoSequencia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages erros,
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
     * Metodo filtroConsultaGrupoEfetuarAtualizacao
     */

    public ArrayList filtroConsultaGrupoEfetuarAtualizacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = request.getSession();

        // Obtém o form
        FiltroConsultaGrupoForm filtroConsultaGrupoForm = (FiltroConsultaGrupoForm) form;

        // Instancia Facade
        FiltroConsultaFacade objFiltroConsultaFacade = new FiltroConsultaFacade();

        // Obtém objeto com o tipo de filtro de consulta relacionada
        FiltroConsulta objFiltroConsulta = (FiltroConsulta) session.getAttribute("objFiltroConsulta");

        try
        {
            // Altera dados
            String strGruposForm[] = filtroConsultaGrupoForm.getGrupoDestino();
            Set setGrupos = new HashSet();
            if (strGruposForm != null)
            {
                // Preenche lista de grupos selecionados
                for (int i = 0; i < strGruposForm.length; i++)
                {
                    Grupo objGrupo = new Grupo();
                    objGrupo.setCodigo(Integer.valueOf(strGruposForm[i]));
                    setGrupos.add(objGrupo);
                }
            }
            objFiltroConsulta.setFiltroConsultaGrupo(setGrupos);
            objFiltroConsultaFacade.alterar(objFiltroConsulta);

            // Mensagem
            mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", ((MessageResources) mapRecursos.get("comum")).getMessage("sigesp.comum.interface.mensagem.alteracao.realizada")));
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
     * Metodo filtroConsultaGrupoPrepararVisualizacao
     */

    public ArrayList filtroConsultaGrupoPrepararVisualizacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = request.getSession();

        // Obtém o form
        FiltroConsultaGrupoForm filtroConsultaGrupoForm = (FiltroConsultaGrupoForm) form;

        // List para armazenar resultado da consulta
        List lstFiltroConsulta = null;
        // String strTotalRegistros = "0";

        // Instancia Facade
        FiltroConsultaFacade objFiltroConsultaFacade = new FiltroConsultaFacade();

        try
        {
            // Verifica qual o Tipo de filtro passado, e obtém objeto para armanenar na sessão
            FiltroConsulta objFiltroConsulta = null;
            if (request.getParameter("chave") != null)
            {
                objFiltroConsulta = objFiltroConsultaFacade.obterPelaChave(request.getParameter("chave"));
                session.setAttribute("objFiltroConsulta", objFiltroConsulta);
            }
            else
            {
                objFiltroConsulta = (FiltroConsulta) session.getAttribute("objFiltroConsulta");
            }

            // Preenche formulário
            List lstGruposDisponiveis = objFiltroConsultaFacade.obterGruposDisponiveisFiltroConsultaGrupo(objFiltroConsulta);
            request.setAttribute("lstGruposDisponiveis", lstGruposDisponiveis);
            List lstGruposSelecionados = objFiltroConsultaFacade.obterGruposSelecionadosFiltroConsultaGrupo(objFiltroConsulta);
            request.setAttribute("lstGruposSelecionados", lstGruposSelecionados);

            //if (objFiltroConsulta.getFiltroConsultaUsuario() != null)
            //	strTotalRegistros = String.valueOf(objFiltroConsulta.getFiltroConsultaUsuario().size());

            // Título da tabela de detalhe
            request.setAttribute("strTituloTabelaDetalhe", "Filtro -  "
                    + objFiltroConsulta.getTipoFiltroConsulta().getNomeTipoFiltroConsulta()
                    + " > "
                    + objFiltroConsulta.getNomeFiltroConsulta());
            // request.setAttribute("strTotalRegistros", strTotalRegistros);
        }
        catch (CDException cde)
        {
            if (log.isErrorEnabled())
            {
                log.error(cde.obterExcecoes());
            }

            erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", cde.obterMensagem()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Mensagens        
        arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, mensagens);
        arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, erros);

        return (arlMensagens);
    }

}

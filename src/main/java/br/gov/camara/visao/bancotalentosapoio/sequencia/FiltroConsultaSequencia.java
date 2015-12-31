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
import br.gov.camara.negocio.bancotalentos.pojo.TipoFiltroConsulta;
import br.gov.camara.visao.bancotalentosapoio.form.DetalheFiltroConsultaForm;

/**
 * Sequencia FiltroConsultaSequencia
 */

public final class FiltroConsultaSequencia extends SigespSequencia
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(FiltroConsultaSequencia.class);

    /**
     * Metodo construtor
     *
     * @param mapping Mapeamento executado
     * @param form FormulÃ¡rio associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que jÃ¡ ocorreram
     * @param mensagens Colecao de mensagens a serem exibidas
     * @param FiltroConsultaAction Classe Action associada
     */

    public FiltroConsultaSequencia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages erros,
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
     * Metodo detalheFiltroConsultaEfetuarInclusao
     */

    public ArrayList detalheFiltroConsultaEfetuarInclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = request.getSession();

        // Obtém o form
        DetalheFiltroConsultaForm detalheFiltroConsultaForm = (DetalheFiltroConsultaForm) form;

        // Instancia Facade
        FiltroConsultaFacade objFiltroConsultaFacade = new FiltroConsultaFacade();

        // Instacia novo POJO
        FiltroConsulta objFiltroConsulta = new FiltroConsulta();

        // Obtém objeto com o tipo de filtro de consulta relacionada
        TipoFiltroConsulta objTipoFiltroConsulta = (TipoFiltroConsulta) session.getAttribute("objTipoFiltroConsulta");

        try
        {
            // Inclui dados
            objFiltroConsulta.setNomeFiltroConsulta(detalheFiltroConsultaForm.getNomeFiltroConsulta());
            objFiltroConsulta.setTipoFiltroConsulta(objTipoFiltroConsulta);
            String strGruposForm[] = detalheFiltroConsultaForm.getGrupoDestino();
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
            objFiltroConsulta.setGrupoCriterioConsulta(setGrupos);
            objFiltroConsultaFacade.incluir(objFiltroConsulta);

            // Mensagem
            mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", ((MessageResources) mapRecursos.get("comum")).getMessage("sigesp.comum.interface.mensagem.inclusao.realizada")));

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
     * Metodo detalheFiltroConsultaPrepararAlteracao
     */

    public ArrayList detalheFiltroConsultaPrepararAlteracao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = request.getSession();

        // Obtém o form
        DetalheFiltroConsultaForm detalheFiltroConsultaForm = (DetalheFiltroConsultaForm) form;

        // Obtém objeto com o tipo de filtro de consulta relacionada
        TipoFiltroConsulta objTipoFiltroConsulta = (TipoFiltroConsulta) session.getAttribute("objTipoFiltroConsulta");

        // Obtém a chave
        String strChave;
        if (request.getParameter("chave") != null)
        {
            strChave = request.getParameter("chave");
        }
        else
        {
            strChave = detalheFiltroConsultaForm.getIdentificador();
        }

        // Instancia Facade
        FiltroConsultaFacade objFiltroConsultaFacade = new FiltroConsultaFacade();

        try
        {
            // Obtém objeto de categoria de talento
            FiltroConsulta objFiltroConsulta;
            objFiltroConsulta = objFiltroConsultaFacade.obterPelaChave(strChave);

            // Preenche formulário
            detalheFiltroConsultaForm.setIdentificador(String.valueOf(objFiltroConsulta.getIdentificador()));
            detalheFiltroConsultaForm.setNomeFiltroConsulta(objFiltroConsulta.getNomeFiltroConsulta());
            List lstGruposDisponiveis = objFiltroConsultaFacade.obterGruposDisponiveisCriterioConsulta(objFiltroConsulta);
            request.setAttribute("lstGruposDisponiveis", lstGruposDisponiveis);
            List lstGruposSelecionados = objFiltroConsultaFacade.obterGruposSelecionadosCriterioConsulta(objFiltroConsulta);
            request.setAttribute("lstGruposSelecionados", lstGruposSelecionados);

            // Título da tabela de detalhe
            request.setAttribute("strTituloTabelaDetalhe", "Alteração - " + objTipoFiltroConsulta.getNomeTipoFiltroConsulta());
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

    /**
     * Metodo detalheFiltroConsultaPrepararInclusao
     */

    public ArrayList detalheFiltroConsultaPrepararInclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = request.getSession();

        // Obtém objeto com o tipo de filtro de consulta relacionada
        TipoFiltroConsulta objTipoFiltroConsulta = (TipoFiltroConsulta) session.getAttribute("objTipoFiltroConsulta");

        // Instancia Facade
        FiltroConsultaFacade objFiltroConsultaFacade = new FiltroConsultaFacade();
        FiltroConsulta objFiltroConsulta = new FiltroConsulta();
        try
        {
            // Verifica chave
            if (request.getParameter("chave") == null)
            {
                objFiltroConsulta.setIdentificador(new Integer(0));
                List lstGruposDisponiveis = objFiltroConsultaFacade.obterGruposDisponiveisFiltroConsultaGrupo(objFiltroConsulta);
                request.setAttribute("lstGruposDisponiveis", lstGruposDisponiveis);
                request.setAttribute("strTituloTabelaDetalhe", "Inclusão - " + objTipoFiltroConsulta.getNomeTipoFiltroConsulta());
            }
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

    /**
     * Metodo exclusaoFiltroConsultaPrepararExclusao
     */

    public ArrayList exclusaoFiltroConsultaPrepararExclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia Facade
        FiltroConsultaFacade objFiltroConsultaFacade = new FiltroConsultaFacade();

        // Cria bean
        FiltroConsulta objFiltroConsulta = null;

        // Cria lista para inserção do registro para exclusão
        List lstFiltroConsulta = new ArrayList();

        // Obtém chave para preparação de exclusão
        String strChave = request.getParameter("chave");

        try
        {
            // Obtém bean para preparação de exclusão
            objFiltroConsulta = objFiltroConsultaFacade.obterPelaChave(strChave);
            lstFiltroConsulta.add(objFiltroConsulta);

            // Armazena dados no request
            request.setAttribute("lstFiltroConsulta", lstFiltroConsulta);
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
     * Metodo filtroConsultaEfetuarAlteracao
     */

    public ArrayList filtroConsultaEfetuarAlteracao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = request.getSession();

        // Obtém o form
        DetalheFiltroConsultaForm detalheFiltroConsultaForm = (DetalheFiltroConsultaForm) form;

        // Instancia Facade
        FiltroConsultaFacade objFiltroConsultaFacade = new FiltroConsultaFacade();

        // Instacia novo POJO
        FiltroConsulta objFiltroConsulta;

        // Obtém objeto com o tipo de filtro de consulta relacionada
        TipoFiltroConsulta objTipoFiltroConsulta = (TipoFiltroConsulta) session.getAttribute("objTipoFiltroConsulta");

        try
        {
            // Busca categoria de talento para carregar o sequencial de ordenação
            objFiltroConsulta = objFiltroConsultaFacade.obterPelaChave(detalheFiltroConsultaForm.getIdentificador());

            // Altera dados
            objFiltroConsulta.setNomeFiltroConsulta(detalheFiltroConsultaForm.getNomeFiltroConsulta());
            objFiltroConsulta.setTipoFiltroConsulta(objTipoFiltroConsulta);
            String strGruposForm[] = detalheFiltroConsultaForm.getGrupoDestino();
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
            objFiltroConsulta.setGrupoCriterioConsulta(setGrupos);
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
     * Metodo filtroConsultaEfetuarExclusao
     */

    public ArrayList filtroConsultaEfetuarExclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém o form
        DetalheFiltroConsultaForm detalheFiltroConsultaForm = (DetalheFiltroConsultaForm) form;

        // Instancia Facade
        FiltroConsultaFacade objFiltroConsultaFacade = new FiltroConsultaFacade();

        // Instacia novo POJO
        FiltroConsulta objFiltroConsulta = new FiltroConsulta();

        // Obtém chave para preparação de exclusão
        String strChave = request.getParameter("chaveExclusao");

        try
        {
            // Exclui dados
            objFiltroConsulta.setIdentificador(Integer.valueOf(strChave));
            objFiltroConsultaFacade.excluir(objFiltroConsulta);

            // Mensagem
            mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", ((MessageResources) mapRecursos.get("comum")).getMessage("sigesp.comum.interface.mensagem.exclusao.realizada")));
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
     * Metodo filtroConsultaPrepararVisualizacao
     */

    public ArrayList filtroConsultaPrepararVisualizacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = request.getSession();

        // List para armazenar resultado da consulta
        List lstFiltroConsulta = null;
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

        // Instancia Facade
        FiltroConsultaFacade objFiltroConsultaFacade = new FiltroConsultaFacade();

        try
        {
            // Verifica qual o Tipo de filtro passado, e obtém objeto para armanenar na sessão
            TipoFiltroConsulta objTipoFiltroConsulta = null;
            if (request.getParameter("chave") != null)
            {
                objTipoFiltroConsulta = objFiltroConsultaFacade.obterTipoFiltroConsultaPelaChave(request.getParameter("chave"));
                session.setAttribute("objTipoFiltroConsulta", objTipoFiltroConsulta);
            }
            else
            {
                objTipoFiltroConsulta = (TipoFiltroConsulta) session.getAttribute("objTipoFiltroConsulta");
            }
            // Obtém dados
            lstFiltroConsulta = objFiltroConsultaFacade.obterPorTipoFiltroConsultaPorPagina(objTipoFiltroConsulta, intPagina, 20);
            if (intPagina == 1)
            {
                strTotalRegistros = String.valueOf(objFiltroConsultaFacade.obterTotalRegistrosPorTipoFiltroConsulta(objTipoFiltroConsulta));
                session.setAttribute("strTotalRegistros", strTotalRegistros);
            }
            request.setAttribute("lstFiltroConsulta", lstFiltroConsulta);
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

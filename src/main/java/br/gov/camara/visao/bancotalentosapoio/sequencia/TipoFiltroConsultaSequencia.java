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
import org.apache.struts.util.MessageResources;

import sigesp.comum.util.struts.SigespSequencia;
import sigesp.comum.util.struts.actions.ActionMessagesUtil;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.bancotalentos.facade.TipoFiltroConsultaFacade;
import br.gov.camara.negocio.bancotalentos.pojo.TipoFiltroConsulta;
import br.gov.camara.visao.bancotalentosapoio.form.DetalheTipoFiltroConsultaForm;

/**
 * Sequencia TipoFiltroConsultaSequencia
 */

public final class TipoFiltroConsultaSequencia extends SigespSequencia
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(TipoFiltroConsultaSequencia.class);

    /**
     * Metodo construtor
     *
     * @param mapping Mapeamento executado
     * @param form FormulÃ¡rio associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que jÃ¡ ocorreram
     * @param mensagens Colecao de mensagens a serem exibidas
     * @param TipoFiltroConsultaAction Classe Action associada
     */

    public TipoFiltroConsultaSequencia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages erros,
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
     * Metodo detalheTipoFiltroConsultaEfetuarInclusao
     */

    public ArrayList detalheTipoFiltroConsultaEfetuarInclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém o form
        DetalheTipoFiltroConsultaForm detalheTipoFiltroConsultaForm = (DetalheTipoFiltroConsultaForm) form;

        // Instancia Facade
        TipoFiltroConsultaFacade objTipoFiltroConsultaFacade = new TipoFiltroConsultaFacade();

        // Instacia novo POJO
        TipoFiltroConsulta objTipoFiltroConsulta = new TipoFiltroConsulta();

        try
        {
            // Inclui dados
            objTipoFiltroConsulta.setNomeTipoFiltroConsulta(detalheTipoFiltroConsultaForm.getNomeTipoFiltroConsulta());
            objTipoFiltroConsulta.setNomeObjetoControlado(detalheTipoFiltroConsultaForm.getObjetoControlado());
            objTipoFiltroConsultaFacade.incluir(objTipoFiltroConsulta);

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
     * Metodo detalheTipoFiltroConsultaPrepararAlteracao
     */

    public ArrayList detalheTipoFiltroConsultaPrepararAlteracao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém o form
        DetalheTipoFiltroConsultaForm detalheTipoFiltroConsultaForm = (DetalheTipoFiltroConsultaForm) form;

        // Obtém a chave
        String strChave;
        if (request.getParameter("chave") != null)
        {
            strChave = request.getParameter("chave");
        }
        else
        {
            strChave = detalheTipoFiltroConsultaForm.getIdentificador();
        }

        // Instancia Facade
        TipoFiltroConsultaFacade objTipoFiltroConsultaFacade = new TipoFiltroConsultaFacade();

        try
        {
            // Obtém objeto de categoria de talento
            TipoFiltroConsulta objTipoFiltroConsulta;
            objTipoFiltroConsulta = objTipoFiltroConsultaFacade.obterPelaChave(strChave);

            // Preenche formulário
            detalheTipoFiltroConsultaForm.setIdentificador(String.valueOf(objTipoFiltroConsulta.getIdentificador()));
            detalheTipoFiltroConsultaForm.setNomeTipoFiltroConsulta(objTipoFiltroConsulta.getNomeTipoFiltroConsulta());
            detalheTipoFiltroConsultaForm.setObjetoControlado(objTipoFiltroConsulta.getNomeObjetoControlado());

            // Título da tabela de detalhe
            request.setAttribute("strTituloTabelaDetalhe", "Alteração");
        }
        catch (CDException cde)
        {
            if (log.isErrorEnabled())
            {
                log.error(cde.obterExcecoes());
            }

            erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", cde.obterMensagem()));
        }

        // Marca encadeamento
        request.setAttribute("blnEncadeamento", new Boolean(true));

        arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, mensagens);
        arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, erros);

        return (arlMensagens);
    }

    /**
     * Metodo detalheTipoFiltroConsultaPrepararInclusao
     */

    public ArrayList detalheTipoFiltroConsultaPrepararInclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Título da tabela de detalhe
        request.setAttribute("strTituloTabelaDetalhe", "Inclusão");

        arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, mensagens);
        arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, erros);

        return (arlMensagens);
    }

    /**
     * Metodo exclusaoTipoFiltroConsultaPrepararExclusao
     */

    public ArrayList exclusaoTipoFiltroConsultaPrepararExclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia Facade
        TipoFiltroConsultaFacade objTipoFiltroConsultaFacade = new TipoFiltroConsultaFacade();

        // Cria bean
        TipoFiltroConsulta objTipoFiltroConsulta = null;

        // Cria lista para inserção do registro para exclusão
        List lstTipoFiltroConsulta = new ArrayList();

        // Obtém chave para preparação de exclusão
        String strChave = request.getParameter("chave");

        try
        {
            // Obtém bean para preparação de exclusão
            objTipoFiltroConsulta = objTipoFiltroConsultaFacade.obterPelaChave(strChave);
            lstTipoFiltroConsulta.add(objTipoFiltroConsulta);

            // Armazena dados no request
            request.setAttribute("lstTipoFiltroConsulta", lstTipoFiltroConsulta);
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
     * Metodo tipoFiltroConsultaEfetuarAlteracao
     */

    public ArrayList tipoFiltroConsultaEfetuarAlteracao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém o form
        DetalheTipoFiltroConsultaForm detalheTipoFiltroConsultaForm = (DetalheTipoFiltroConsultaForm) form;

        // Instancia Facade
        TipoFiltroConsultaFacade objTipoFiltroConsultaFacade = new TipoFiltroConsultaFacade();

        // Instacia novo POJO
        TipoFiltroConsulta objTipoFiltroConsulta = new TipoFiltroConsulta();

        try
        {
            // Altera dados
            objTipoFiltroConsulta.setIdentificador(new Integer(detalheTipoFiltroConsultaForm.getIdentificador()));
            objTipoFiltroConsulta.setNomeTipoFiltroConsulta(detalheTipoFiltroConsultaForm.getNomeTipoFiltroConsulta());
            objTipoFiltroConsulta.setNomeObjetoControlado(detalheTipoFiltroConsultaForm.getObjetoControlado());
            objTipoFiltroConsultaFacade.alterar(objTipoFiltroConsulta);

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
     * Metodo tipoFiltroConsultaEfetuarExclusao
     */

    public ArrayList tipoFiltroConsultaEfetuarExclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém o form
        DetalheTipoFiltroConsultaForm detalheTipoFiltroConsultaForm = (DetalheTipoFiltroConsultaForm) form;

        // Instancia Facade
        TipoFiltroConsultaFacade objTipoFiltroConsultaFacade = new TipoFiltroConsultaFacade();

        // Instacia novo POJO
        TipoFiltroConsulta objTipoFiltroConsulta = new TipoFiltroConsulta();

        // Obtém chave para preparação de exclusão
        String strChave = request.getParameter("chave");

        try
        {
            // Exclui dados
            objTipoFiltroConsulta.setIdentificador(Integer.valueOf(strChave));
            objTipoFiltroConsultaFacade.excluir(objTipoFiltroConsulta);

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
     * Metodo tipoFiltroConsultaPrepararVisualizacao
     */

    public ArrayList tipoFiltroConsultaPrepararVisualizacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = request.getSession();

        // List para armazenar resultado da consulta
        List lstTipoFiltroConsulta = null;
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
        TipoFiltroConsultaFacade objTipoFiltroConsultaFacade = new TipoFiltroConsultaFacade();

        try
        {
            // Obtém dados
            lstTipoFiltroConsulta = objTipoFiltroConsultaFacade.obterPorPagina(intPagina, 20);
            if (intPagina == 1)
            {
                strTotalRegistros = String.valueOf(objTipoFiltroConsultaFacade.obterTotalRegistros());
                session.setAttribute("strTotalRegistros", strTotalRegistros);
            }
            request.setAttribute("lstTipoFiltroConsulta", lstTipoFiltroConsulta);
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

}

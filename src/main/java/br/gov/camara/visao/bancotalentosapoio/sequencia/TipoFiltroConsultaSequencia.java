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
     * @param form Formulário associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que já ocorreram
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

        // Obt�m o form
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

        // Obt�m o form
        DetalheTipoFiltroConsultaForm detalheTipoFiltroConsultaForm = (DetalheTipoFiltroConsultaForm) form;

        // Obt�m a chave
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
            // Obt�m objeto de categoria de talento
            TipoFiltroConsulta objTipoFiltroConsulta;
            objTipoFiltroConsulta = objTipoFiltroConsultaFacade.obterPelaChave(strChave);

            // Preenche formul�rio
            detalheTipoFiltroConsultaForm.setIdentificador(String.valueOf(objTipoFiltroConsulta.getIdentificador()));
            detalheTipoFiltroConsultaForm.setNomeTipoFiltroConsulta(objTipoFiltroConsulta.getNomeTipoFiltroConsulta());
            detalheTipoFiltroConsultaForm.setObjetoControlado(objTipoFiltroConsulta.getNomeObjetoControlado());

            // T�tulo da tabela de detalhe
            request.setAttribute("strTituloTabelaDetalhe", "Altera��o");
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

        // T�tulo da tabela de detalhe
        request.setAttribute("strTituloTabelaDetalhe", "Inclus�o");

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

        // Cria lista para inser��o do registro para exclus�o
        List lstTipoFiltroConsulta = new ArrayList();

        // Obt�m chave para prepara��o de exclus�o
        String strChave = request.getParameter("chave");

        try
        {
            // Obt�m bean para prepara��o de exclus�o
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

        // Obt�m o form
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

        // Obt�m o form
        DetalheTipoFiltroConsultaForm detalheTipoFiltroConsultaForm = (DetalheTipoFiltroConsultaForm) form;

        // Instancia Facade
        TipoFiltroConsultaFacade objTipoFiltroConsultaFacade = new TipoFiltroConsultaFacade();

        // Instacia novo POJO
        TipoFiltroConsulta objTipoFiltroConsulta = new TipoFiltroConsulta();

        // Obt�m chave para prepara��o de exclus�o
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

        // Obt�m sess�o
        HttpSession session = request.getSession();

        // List para armazenar resultado da consulta
        List lstTipoFiltroConsulta = null;
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

        // Instancia Facade
        TipoFiltroConsultaFacade objTipoFiltroConsultaFacade = new TipoFiltroConsultaFacade();

        try
        {
            // Obt�m dados
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

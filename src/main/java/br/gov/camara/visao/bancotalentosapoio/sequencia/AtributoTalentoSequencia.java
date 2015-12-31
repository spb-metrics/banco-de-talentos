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
import br.gov.camara.negocio.bancotalentos.facade.AtributoTalentoFacade;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalento;
import br.gov.camara.negocio.bancotalentos.pojo.TabelaApoioMM;
import br.gov.camara.negocio.comum.bean.FabricaBeans;
import br.gov.camara.negocio.comum.pojo.TipoHTML;
import br.gov.camara.visao.bancotalentosapoio.form.DetalheAtributoTalentoForm;

/**
 * Sequencia AtributoTalentoSequencia
 */

public final class AtributoTalentoSequencia extends SigespSequencia
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(AtributoTalentoSequencia.class);

    /**
     * Metodo construtor
     *
     * @param mapping Mapeamento executado
     * @param form FormulÃ¡rio associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que jÃ¡ ocorreram
     * @param mensagens Colecao de mensagens a serem exibidas
     * @param AtributoTalentoAction Classe Action associada
     */

    public AtributoTalentoSequencia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages erros,
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
     * Metodo atributoTalentoEfetuarAlteracao
     */

    public ArrayList atributoTalentoEfetuarAlteracao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém o form
        DetalheAtributoTalentoForm detalheAtributoTalentoForm = (DetalheAtributoTalentoForm) form;

        // Instancia Facade
        AtributoTalentoFacade objAtributoTalentoFacade = new AtributoTalentoFacade();

        // Instacia novo POJO
        AtributoTalento objAtributoTalento = new AtributoTalento();

        try
        {
            // Altera dados
            objAtributoTalento.setIdentificador(Integer.valueOf(detalheAtributoTalentoForm.getIdentificador()));
            if (detalheAtributoTalentoForm.getAtributoTalentoPai() != null && !"".equals(detalheAtributoTalentoForm.getAtributoTalentoPai()))
            {
                AtributoTalento objAtributoTalentoPai = new AtributoTalento();
                objAtributoTalentoPai.setIdentificador(Integer.valueOf(detalheAtributoTalentoForm.getAtributoTalentoPai()));
                objAtributoTalento.setAtributoTalentoPai(objAtributoTalentoPai);
            }
            objAtributoTalento.setNome(detalheAtributoTalentoForm.getNome());
            if (detalheAtributoTalentoForm.getTipoHTML() != null && !"".equals(detalheAtributoTalentoForm.getTipoHTML()))
            {
                TipoHTML objTipoHTML = new TipoHTML();
                objTipoHTML.setIdentificador(Integer.valueOf(detalheAtributoTalentoForm.getTipoHTML()));
                objAtributoTalento.setTipoHTML(objTipoHTML);
            }
            objAtributoTalento.setMascara(detalheAtributoTalentoForm.getMascara());
            if (detalheAtributoTalentoForm.getTabelaApoioMM() != null && !"".equals(detalheAtributoTalentoForm.getTabelaApoioMM()))
            {
                TabelaApoioMM objTabelaApoioMM = new TabelaApoioMM();
                objTabelaApoioMM.setIdentificador(Integer.valueOf(detalheAtributoTalentoForm.getTabelaApoioMM()));
                objAtributoTalento.setTabelaApoioMM(objTabelaApoioMM);
            }
            objAtributoTalento.setIndicativoPesquisa((detalheAtributoTalentoForm.getIndicativoPesquisa() ? "S" : "N"));
            objAtributoTalento.setDescricaoPesquisa(detalheAtributoTalentoForm.getDescricaoPesquisa());
            objAtributoTalento.setTipoDado(detalheAtributoTalentoForm.getTipoDado());
            objAtributoTalentoFacade.alterar(objAtributoTalento);

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
     * Metodo atributoTalentoEfetuarExclusao
     */

    public ArrayList atributoTalentoEfetuarExclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém o form
        DetalheAtributoTalentoForm detalheAtributoTalentoForm = (DetalheAtributoTalentoForm) form;

        // Instancia Facade
        AtributoTalentoFacade objAtributoTalentoFacade = new AtributoTalentoFacade();

        // Instacia novo POJO
        AtributoTalento objAtributoTalento = new AtributoTalento();

        // Obtém chave para preparação de exclusão
        String strChave = request.getParameter("chave");

        try
        {
            // Exclui dados
            objAtributoTalento.setIdentificador(Integer.valueOf(strChave));
            objAtributoTalentoFacade.excluir(objAtributoTalento);

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

        // Marca encadeamento
        request.setAttribute("blnEncadeamento", new Boolean(true));

        // Mensagens        
        arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, mensagens);
        arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, erros);

        return (arlMensagens);
    }

    /**
     * Metodo atributoTalentoPrepararVisualizacao
     */

    public ArrayList atributoTalentoPrepararVisualizacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = request.getSession();

        // List para armazenar resultado da consulta
        List lstAtributosTalento = null;
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
        AtributoTalentoFacade objAtributoTalentoFacade = new AtributoTalentoFacade();

        try
        {
            // Obtém dados
            lstAtributosTalento = objAtributoTalentoFacade.obterPorPagina(intPagina, 20);
            if (intPagina == 1)
            {
                strTotalRegistros = String.valueOf(objAtributoTalentoFacade.obterTotalRegistros());
                session.setAttribute("strTotalRegistros", strTotalRegistros);
            }
            request.setAttribute("lstAtributosTalento", lstAtributosTalento);
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
     * Metodo detalheAtributoTalentoEfetuarInclusao
     */

    public ArrayList detalheAtributoTalentoEfetuarInclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém o form
        DetalheAtributoTalentoForm detalheAtributoTalentoForm = (DetalheAtributoTalentoForm) form;

        // Instancia Facade
        AtributoTalentoFacade objAtributoTalentoFacade = new AtributoTalentoFacade();

        // Instacia novo POJO
        AtributoTalento objAtributoTalento = new AtributoTalento();

        try
        {
            // Inclui dados
            if (detalheAtributoTalentoForm.getAtributoTalentoPai() != null && !"".equals(detalheAtributoTalentoForm.getAtributoTalentoPai()))
            {
                AtributoTalento objAtributoTalentoPai = new AtributoTalento();
                objAtributoTalentoPai.setIdentificador(Integer.valueOf(detalheAtributoTalentoForm.getAtributoTalentoPai()));
                objAtributoTalento.setAtributoTalentoPai(objAtributoTalentoPai);
            }
            objAtributoTalento.setNome(detalheAtributoTalentoForm.getNome());
            if (detalheAtributoTalentoForm.getTipoHTML() != null && !"".equals(detalheAtributoTalentoForm.getTipoHTML()))
            {
                TipoHTML objTipoHTML = new TipoHTML();
                objTipoHTML.setIdentificador(Integer.valueOf(detalheAtributoTalentoForm.getTipoHTML()));
                objAtributoTalento.setTipoHTML(objTipoHTML);
            }
            objAtributoTalento.setMascara(detalheAtributoTalentoForm.getMascara());
            if (detalheAtributoTalentoForm.getTabelaApoioMM() != null && !"".equals(detalheAtributoTalentoForm.getTabelaApoioMM()))
            {
                TabelaApoioMM objTabelaApoioMM = new TabelaApoioMM();
                objTabelaApoioMM.setIdentificador(Integer.valueOf(detalheAtributoTalentoForm.getTabelaApoioMM()));
                objAtributoTalento.setTabelaApoioMM(objTabelaApoioMM);
            }
            objAtributoTalento.setIndicativoPesquisa((detalheAtributoTalentoForm.getIndicativoPesquisa() ? "S" : "N"));
            objAtributoTalento.setDescricaoPesquisa(detalheAtributoTalentoForm.getDescricaoPesquisa());
            objAtributoTalento.setTipoDado(detalheAtributoTalentoForm.getTipoDado());
            objAtributoTalentoFacade.incluir(objAtributoTalento);

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
     * Metodo detalheAtributoTalentoPrepararAlteracao
     */

    public ArrayList detalheAtributoTalentoPrepararAlteracao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém o form
        DetalheAtributoTalentoForm detalheAtributoTalentoForm = (DetalheAtributoTalentoForm) form;

        // Obtém a chave
        String strChave;
        if (request.getParameter("chave") != null)
        {
            strChave = request.getParameter("chave");
        }
        else
        {
            strChave = detalheAtributoTalentoForm.getIdentificador();
        }

        // Instancia Facade
        AtributoTalentoFacade objAtributoTalentoFacade = new AtributoTalentoFacade();

        try
        {
            // Obtém objeto de atributo de talento
            AtributoTalento objAtributoTalento;
            objAtributoTalento = objAtributoTalentoFacade.obterPelaChave(strChave);

            // Obtém lista dos candidatos a pai, e armazena no request
            List lstCandidatosAPai = objAtributoTalentoFacade.obterCandidatosAPai(objAtributoTalento);
            request.setAttribute("lstCandidatosAPai", lstCandidatosAPai);

            // Preenche formulário
            detalheAtributoTalentoForm.setIdentificador(String.valueOf(objAtributoTalento.getIdentificador()));
            if (objAtributoTalento.getAtributoTalentoPai() != null)
            {
                detalheAtributoTalentoForm.setAtributoTalentoPai(String.valueOf(objAtributoTalento.getAtributoTalentoPai().getIdentificador()));
            }
            detalheAtributoTalentoForm.setNome(objAtributoTalento.getNome());
            if (objAtributoTalento.getTipoHTML() != null)
            {
                detalheAtributoTalentoForm.setTipoHTML(String.valueOf(objAtributoTalento.getTipoHTML().getIdentificador()));
            }
            detalheAtributoTalentoForm.setMascara(objAtributoTalento.getMascara());
            if (objAtributoTalento.getTabelaApoioMM() != null)
            {
                detalheAtributoTalentoForm.setTabelaApoioMM(String.valueOf(objAtributoTalento.getTabelaApoioMM().getIdentificador()));
            }
            detalheAtributoTalentoForm.setIndicativoPesquisa(("S".equals(objAtributoTalento.getIndicativoPesquisa()) ? true : false));
            detalheAtributoTalentoForm.setDescricaoPesquisa(objAtributoTalento.getDescricaoPesquisa());
            if (objAtributoTalento.getTabelaApoioMM() != null)
            {
                detalheAtributoTalentoForm.setTabelaApoioMM(String.valueOf(objAtributoTalento.getTabelaApoioMM().getIdentificador()));
            }
            detalheAtributoTalentoForm.setTipoDado(String.valueOf(objAtributoTalento.getTipoDado()));

            // Título da tabela de detalhe
            request.setAttribute("strTituloTabelaDetalhe", "Alteração - " + objAtributoTalento.getNome());
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
     * Metodo detalheAtributoTalentoPrepararInclusao
     */

    public ArrayList detalheAtributoTalentoPrepararInclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia Facade
        AtributoTalentoFacade objAtributoTalentoFacade = new AtributoTalentoFacade();

        try
        {
            // Obtém lista dos candidatos a pai, e armazena no request, se já não houver encadeamento
            if (request.getAttribute("blnEncadeamento") == null)
            {
                List lstCandidatosAPai = objAtributoTalentoFacade.obterCandidatosAPai(null);
                request.setAttribute("lstCandidatosAPai", lstCandidatosAPai);
            }

            // Obtém lista de tipos html, e armazena no request
            List lstTiposHTML = objAtributoTalentoFacade.obterTiposHTML();
            request.setAttribute("lstTiposHTML", lstTiposHTML);

            // Obtém lista de tabelas de apoio de meta dados, e armazena no request
            List lstTabelasApoioMM = objAtributoTalentoFacade.obterTabelasApoioMM();
            request.setAttribute("lstTabelasApoioMM", lstTabelasApoioMM);

            // Obtém lista dos tipos de dados, e armazena no request
            List lstTiposDado = FabricaBeans.criar(FabricaBeans.TIPO_DADO);
            request.setAttribute("lstTiposDado", lstTiposDado);

            // Título da tabela de detalhe
            if (request.getParameter("chave") == null)
            {
                request.setAttribute("strTituloTabelaDetalhe", "Inclusão");
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
     * Metodo exclusaoAtributoTalentoPrepararExclusao
     */

    public ArrayList exclusaoAtributoTalentoPrepararExclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia Facade
        AtributoTalentoFacade objAtributoTalentoFacade = new AtributoTalentoFacade();

        // Cria bean
        AtributoTalento objAtributoTalento = null;

        // Cria lista para inserção do registro para exclusão
        List lstAtributosTalento = new ArrayList();

        // Obtém chave para preparação de exclusão
        String strChave = request.getParameter("chave");

        try
        {
            // Obtém bean para preparação de exclusão
            objAtributoTalento = objAtributoTalentoFacade.obterPelaChave(strChave);
            lstAtributosTalento.add(objAtributoTalento);

            // Armazena dados no request
            request.setAttribute("lstAtributosTalento", lstAtributosTalento);
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

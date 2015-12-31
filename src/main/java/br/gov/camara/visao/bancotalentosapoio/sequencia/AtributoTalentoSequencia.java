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
     * @param form Formulário associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que já ocorreram
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

        // Obt�m o form
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

        // Obt�m o form
        DetalheAtributoTalentoForm detalheAtributoTalentoForm = (DetalheAtributoTalentoForm) form;

        // Instancia Facade
        AtributoTalentoFacade objAtributoTalentoFacade = new AtributoTalentoFacade();

        // Instacia novo POJO
        AtributoTalento objAtributoTalento = new AtributoTalento();

        // Obt�m chave para prepara��o de exclus�o
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

        // Obt�m sess�o
        HttpSession session = request.getSession();

        // List para armazenar resultado da consulta
        List lstAtributosTalento = null;
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
        AtributoTalentoFacade objAtributoTalentoFacade = new AtributoTalentoFacade();

        try
        {
            // Obt�m dados
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

        // Obt�m o form
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

        // Obt�m o form
        DetalheAtributoTalentoForm detalheAtributoTalentoForm = (DetalheAtributoTalentoForm) form;

        // Obt�m a chave
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
            // Obt�m objeto de atributo de talento
            AtributoTalento objAtributoTalento;
            objAtributoTalento = objAtributoTalentoFacade.obterPelaChave(strChave);

            // Obt�m lista dos candidatos a pai, e armazena no request
            List lstCandidatosAPai = objAtributoTalentoFacade.obterCandidatosAPai(objAtributoTalento);
            request.setAttribute("lstCandidatosAPai", lstCandidatosAPai);

            // Preenche formul�rio
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

            // T�tulo da tabela de detalhe
            request.setAttribute("strTituloTabelaDetalhe", "Altera��o - " + objAtributoTalento.getNome());
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
            // Obt�m lista dos candidatos a pai, e armazena no request, se j� n�o houver encadeamento
            if (request.getAttribute("blnEncadeamento") == null)
            {
                List lstCandidatosAPai = objAtributoTalentoFacade.obterCandidatosAPai(null);
                request.setAttribute("lstCandidatosAPai", lstCandidatosAPai);
            }

            // Obt�m lista de tipos html, e armazena no request
            List lstTiposHTML = objAtributoTalentoFacade.obterTiposHTML();
            request.setAttribute("lstTiposHTML", lstTiposHTML);

            // Obt�m lista de tabelas de apoio de meta dados, e armazena no request
            List lstTabelasApoioMM = objAtributoTalentoFacade.obterTabelasApoioMM();
            request.setAttribute("lstTabelasApoioMM", lstTabelasApoioMM);

            // Obt�m lista dos tipos de dados, e armazena no request
            List lstTiposDado = FabricaBeans.criar(FabricaBeans.TIPO_DADO);
            request.setAttribute("lstTiposDado", lstTiposDado);

            // T�tulo da tabela de detalhe
            if (request.getParameter("chave") == null)
            {
                request.setAttribute("strTituloTabelaDetalhe", "Inclus�o");
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

        // Cria lista para inser��o do registro para exclus�o
        List lstAtributosTalento = new ArrayList();

        // Obt�m chave para prepara��o de exclus�o
        String strChave = request.getParameter("chave");

        try
        {
            // Obt�m bean para prepara��o de exclus�o
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

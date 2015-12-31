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
import br.gov.camara.negocio.bancotalentos.facade.AtributoTalentoOpcaoFacade;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalento;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalentoOpcao;
import br.gov.camara.visao.bancotalentosapoio.form.DetalheAtributoTalentoOpcaoForm;

/**
 * Sequencia AtributoTalentoOpcaoSequencia
 */

public final class AtributoTalentoOpcaoSequencia extends SigespSequencia
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(AtributoTalentoOpcaoSequencia.class);

    /**
     * Metodo construtor
     *
     * @param mapping Mapeamento executado
     * @param form Formulário associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que já ocorreram
     * @param mensagens Colecao de mensagens a serem exibidas
     * @param AtributoTalentoOpcaoAction Classe Action associada
     */

    public AtributoTalentoOpcaoSequencia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,
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
     * Metodo atributoTalentoOpcaoEfetuarAlteracao
     */

    public ArrayList atributoTalentoOpcaoEfetuarAlteracao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m sess�o
        HttpSession session = this.request.getSession();

        // Obt�m o form
        DetalheAtributoTalentoOpcaoForm detalheAtributoTalentoOpcaoForm = (DetalheAtributoTalentoOpcaoForm) this.form;

        // Obt�m objeto com o atributo de talento relacionado
        AtributoTalento objAtributoTalento = (AtributoTalento) session.getAttribute("objAtributoTalento");

        // Instancia Facade
        AtributoTalentoOpcaoFacade objAtributoTalentoOpcaoFacade = new AtributoTalentoOpcaoFacade();

        // Instacia novo POJO
        AtributoTalentoOpcao objAtributoTalentoOpcao = new AtributoTalentoOpcao();

        try
        {
            // Altera dados
            objAtributoTalentoOpcao.setIdentificador(Integer.valueOf(detalheAtributoTalentoOpcaoForm.getIdentificador()));
            objAtributoTalentoOpcao.setAtributoTalento(objAtributoTalento);
            if (detalheAtributoTalentoOpcaoForm.getAtributoTalentoOpcaoPai() != null
                    && !"".equals(detalheAtributoTalentoOpcaoForm.getAtributoTalentoOpcaoPai()))
            {
                AtributoTalentoOpcao objAtributoTalentoOpcaoPai = new AtributoTalentoOpcao();
                objAtributoTalentoOpcaoPai.setIdentificador(Integer.valueOf(detalheAtributoTalentoOpcaoForm.getAtributoTalentoOpcaoPai()));
                objAtributoTalentoOpcao.setAtributoTalentoOpcaoPai(objAtributoTalentoOpcaoPai);
            }
            objAtributoTalentoOpcao.setDescricao(detalheAtributoTalentoOpcaoForm.getDescricao());
            objAtributoTalentoOpcaoFacade.alterar(objAtributoTalentoOpcao);

            // Mensagem
            this.mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", ((MessageResources) this.mapRecursos.get("comum")).getMessage("sigesp.comum.interface.mensagem.alteracao.realizada")));
        }
        catch (CDException cde)
        {
            if (log.isErrorEnabled())
            {
                log.error(cde.obterExcecoes());
            }

            this.erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", cde.obterMensagem()));
        }

        // Marca encadeamento
        this.request.setAttribute("blnEncadeamento", new Boolean(true));

        // Mensagens
        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);
    }

    /**
     * Metodo atributoTalentoOpcaoEfetuarExclusao
     */

    public ArrayList atributoTalentoOpcaoEfetuarExclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m o form
        DetalheAtributoTalentoOpcaoForm detalheAtributoTalentoOpcaoForm = (DetalheAtributoTalentoOpcaoForm) this.form;

        // Instancia Facade
        AtributoTalentoOpcaoFacade objAtributoTalentoOpcaoFacade = new AtributoTalentoOpcaoFacade();

        // Instacia novo POJO
        AtributoTalentoOpcao objAtributoTalentoOpcao = new AtributoTalentoOpcao();

        // Obt�m chave para prepara��o de exclus�o
        String strChave = this.request.getParameter("chave");

        try
        {
            // Exclui dados
            objAtributoTalentoOpcao.setIdentificador(Integer.valueOf(strChave));
            objAtributoTalentoOpcaoFacade.excluir(objAtributoTalentoOpcao);

            // Mensagem
            this.mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", ((MessageResources) this.mapRecursos.get("comum")).getMessage("sigesp.comum.interface.mensagem.exclusao.realizada")));
        }
        catch (CDException cde)
        {
            if (log.isErrorEnabled())
            {
                log.error(cde.obterExcecoes());
            }

            this.erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", cde.obterMensagem()));
        }

        // Marca encadeamento
        this.request.setAttribute("blnEncadeamento", new Boolean(true));

        // Mensagens
        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);
    }

    /**
     * Metodo atributoTalentoOpcaoPrepararVisualizacao
     */

    public ArrayList atributoTalentoOpcaoPrepararVisualizacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m sess�o
        HttpSession session = this.request.getSession();

        // List para armazenar resultado da consulta
        List lstAtributoTalentoOpcoes = null;
        String strTotalRegistros = "0";

        // Verifica p�gina
        int intPagina;
        if (this.request.getParameter("pagina") != null)
        {
            intPagina = Integer.parseInt(this.request.getParameter("pagina"));
            strTotalRegistros = String.valueOf(session.getAttribute("strTotalRegistros"));
        }
        else
        {
            intPagina = 1;
        }

        // Instancia Facade
        AtributoTalentoOpcaoFacade objAtributoTalentoOpcaoFacade = new AtributoTalentoOpcaoFacade();

        try
        {
            // Verifica qual o atributo de talento passado, e obt�m objeto para armanenar na sess�o
            AtributoTalento objAtributoTalento = null;
            if (this.request.getAttribute("blnEncadeamento") == null && this.request.getParameter("pagina") == null)
            {
                if (this.request.getParameter("chave") != null)
                {
                    objAtributoTalento = objAtributoTalentoOpcaoFacade.obterAtributoTalentoPelaChave(this.request.getParameter("chave"));
                    session.setAttribute("objAtributoTalento", objAtributoTalento);
                }
                else
                {
                    objAtributoTalento = (AtributoTalento) session.getAttribute("objAtributoTalento");
                }
            }
            else
            {
                objAtributoTalento = (AtributoTalento) session.getAttribute("objAtributoTalento");
            }

            // Obt�m dados
            lstAtributoTalentoOpcoes = objAtributoTalentoOpcaoFacade.obterPorAtributoTalentoPorPagina(objAtributoTalento, intPagina, 20);
            if (intPagina == 1)
            {
                strTotalRegistros = String.valueOf(objAtributoTalentoOpcaoFacade.obterTotalRegistrosPorAtributoTalento(objAtributoTalento));
                session.setAttribute("strTotalRegistros", strTotalRegistros);
            }
            this.request.setAttribute("lstAtributoTalentoOpcoes", lstAtributoTalentoOpcoes);
            this.request.setAttribute("strTotalRegistros", strTotalRegistros);
        }
        catch (CDException cde)
        {
            if (log.isErrorEnabled())
            {
                log.error(cde.obterExcecoes());
            }

            this.erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", cde.obterMensagem()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Mensagens
        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);
    }

    /**
     * Metodo detalheAtributoTalentoOpcaoEfetuarInclusao
     */

    public ArrayList detalheAtributoTalentoOpcaoEfetuarInclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m sess�o
        HttpSession session = this.request.getSession();

        // Obt�m o form
        DetalheAtributoTalentoOpcaoForm detalheAtributoTalentoOpcaoForm = (DetalheAtributoTalentoOpcaoForm) this.form;

        // Obt�m objeto com o atributo de talento relacionado
        AtributoTalento objAtributoTalento = (AtributoTalento) session.getAttribute("objAtributoTalento");

        // Instancia Facade
        AtributoTalentoOpcaoFacade objAtributoTalentoOpcaoFacade = new AtributoTalentoOpcaoFacade();

        // Instacia novo POJO
        AtributoTalentoOpcao objAtributoTalentoOpcao = new AtributoTalentoOpcao();

        try
        {
            // Inclui dados
            objAtributoTalentoOpcao.setAtributoTalento(objAtributoTalento);
            if (detalheAtributoTalentoOpcaoForm.getAtributoTalentoOpcaoPai() != null
                    && !"".equals(detalheAtributoTalentoOpcaoForm.getAtributoTalentoOpcaoPai()))
            {
                AtributoTalentoOpcao objAtributoTalentoOpcaoPai = new AtributoTalentoOpcao();
                objAtributoTalentoOpcaoPai.setIdentificador(Integer.valueOf(detalheAtributoTalentoOpcaoForm.getAtributoTalentoOpcaoPai()));
                objAtributoTalentoOpcao.setAtributoTalentoOpcaoPai(objAtributoTalentoOpcaoPai);
            }
            objAtributoTalentoOpcao.setDescricao(detalheAtributoTalentoOpcaoForm.getDescricao());
            objAtributoTalentoOpcaoFacade.incluir(objAtributoTalentoOpcao);

            // Mensagem
            this.mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", ((MessageResources) this.mapRecursos.get("comum")).getMessage("sigesp.comum.interface.mensagem.inclusao.realizada")));
        }
        catch (CDException cde)
        {
            if (log.isErrorEnabled())
            {
                log.error(cde.obterExcecoes());
            }

            this.erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", cde.obterMensagem()));
        }

        // Mensagens
        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);
    }

    /**
     * Metodo detalheAtributoTalentoOpcaoPrepararAlteracao
     */

    public ArrayList detalheAtributoTalentoOpcaoPrepararAlteracao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m sess�o
        HttpSession session = this.request.getSession();

        // Obt�m o form
        DetalheAtributoTalentoOpcaoForm detalheAtributoTalentoOpcaoForm = (DetalheAtributoTalentoOpcaoForm) this.form;

        // Obt�m a chave
        String strChave;
        if (this.request.getParameter("chave") != null)
        {
            strChave = this.request.getParameter("chave");
        }
        else
        {
            strChave = detalheAtributoTalentoOpcaoForm.getIdentificador();
        }

        // Obt�m objeto com o atributo de talento relacionado
        AtributoTalento objAtributoTalento = (AtributoTalento) session.getAttribute("objAtributoTalento");

        // Instancia Facade
        AtributoTalentoOpcaoFacade objAtributoTalentoOpcaoFacade = new AtributoTalentoOpcaoFacade();

        try
        {
            // Obt�m objeto de op��o de atributo de talento
            AtributoTalentoOpcao objAtributoTalentoOpcao = new AtributoTalentoOpcao();
            objAtributoTalentoOpcao = objAtributoTalentoOpcaoFacade.obterPelaChave(strChave);

            // Obt�m lista dos candidatos a pai, e armazena no request
            List lstCandidatosAPai = objAtributoTalentoOpcaoFacade.obterCandidatosAPai(objAtributoTalento, objAtributoTalentoOpcao);
            this.request.setAttribute("lstCandidatosAPai", lstCandidatosAPai);

            // Preenche formul�rio
            detalheAtributoTalentoOpcaoForm.setIdentificador(String.valueOf(objAtributoTalentoOpcao.getIdentificador()));
            if (objAtributoTalentoOpcao.getAtributoTalentoOpcaoPai() != null)
            {
                detalheAtributoTalentoOpcaoForm.setAtributoTalentoOpcaoPai(String.valueOf(objAtributoTalentoOpcao.getAtributoTalentoOpcaoPai().getIdentificador()));
            }
            detalheAtributoTalentoOpcaoForm.setDescricao(objAtributoTalentoOpcao.getDescricao());

            // T�tulo da tabela de detalhe
            this.request.setAttribute("strTituloTabelaDetalhe", "Altera��o - " + objAtributoTalentoOpcao.getDescricao());
        }
        catch (CDException cde)
        {
            if (log.isErrorEnabled())
            {
                log.error(cde.obterExcecoes());
            }

            this.erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", cde.obterMensagem()));
        }

        // Marca encadeamento
        this.request.setAttribute("blnEncadeamento", new Boolean(true));

        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);
    }

    /**
     * Metodo detalheAtributoTalentoOpcaoPrepararInclusao
     */

    public ArrayList detalheAtributoTalentoOpcaoPrepararInclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m sess�o
        HttpSession session = this.request.getSession();

        // Obt�m objeto com o atributo de talento relacionado
        AtributoTalento objAtributoTalento = (AtributoTalento) session.getAttribute("objAtributoTalento");

        // Instancia Facade
        AtributoTalentoOpcaoFacade objAtributoTalentoOpcaoFacade = new AtributoTalentoOpcaoFacade();

        try
        {
            // Obt�m lista dos candidatos a pai, e armazena no request, se j� n�o houver encadeamento
            if (this.request.getAttribute("blnEncadeamento") == null)
            {
                List lstCandidatosAPai = objAtributoTalentoOpcaoFacade.obterCandidatosAPai(objAtributoTalento, null);
                this.request.setAttribute("lstCandidatosAPai", lstCandidatosAPai);
            }

            // T�tulo da tabela de detalhe
            if (this.request.getParameter("chave") == null)
            {
                this.request.setAttribute("strTituloTabelaDetalhe", "Inclus�o");
            }
        }
        catch (CDException cde)
        {
            if (log.isErrorEnabled())
            {
                log.error(cde.obterExcecoes());
            }

            this.erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", cde.obterMensagem()));
        }

        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);
    }

    /**
     * Metodo exclusaoAtributoTalentoOpcaoPrepararExclusao
     */

    public ArrayList exclusaoAtributoTalentoOpcaoPrepararExclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia Facade
        AtributoTalentoOpcaoFacade objAtributoTalentoOpcaoFacade = new AtributoTalentoOpcaoFacade();

        // Cria bean
        AtributoTalentoOpcao objAtributoTalentoOpcao = null;

        // Cria lista para inser��o do registro para exclus�o
        List lstAtributoTalentoOpcoes = new ArrayList();

        // Obt�m chave para prepara��o de exclus�o
        String strChave = this.request.getParameter("chave");

        try
        {
            // Obt�m bean para prepara��o de exclus�o
            objAtributoTalentoOpcao = objAtributoTalentoOpcaoFacade.obterPelaChave(strChave);
            lstAtributoTalentoOpcoes.add(objAtributoTalentoOpcao);

            // Armazena dados no request
            this.request.setAttribute("lstAtributoTalentoOpcoes", lstAtributoTalentoOpcoes);
        }
        catch (CDException cde)
        {
            if (log.isErrorEnabled())
            {
                log.error(cde.obterExcecoes());
            }

            this.erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", cde.obterMensagem()));
        }

        // Mensagens
        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);
    }

}

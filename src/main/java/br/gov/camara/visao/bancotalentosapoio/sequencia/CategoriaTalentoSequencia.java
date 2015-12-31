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
import br.gov.camara.negocio.bancotalentos.facade.CategoriaTalentoFacade;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaTalento;
import br.gov.camara.visao.bancotalentosapoio.form.DetalheCategoriaTalentoForm;
import br.gov.camara.visao.bancotalentosapoio.form.OrdenacaoCategoriaTalentoForm;

/**
 * Sequencia CategoriaTalentoSequencia
 */

public final class CategoriaTalentoSequencia extends SigespSequencia
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(CategoriaTalentoSequencia.class);

    /**
     * Metodo construtor
     *
     * @param mapping Mapeamento executado
     * @param form Formulário associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que já ocorreram
     * @param mensagens Colecao de mensagens a serem exibidas
     * @param CategoriaTalentoAction Classe Action associada
     */

    public CategoriaTalentoSequencia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages erros,
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
     * Metodo categoriaTalentoEfetuarAlteracao
     */

    public ArrayList categoriaTalentoEfetuarAlteracao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m o form
        DetalheCategoriaTalentoForm detalheCategoriaTalentoForm = (DetalheCategoriaTalentoForm) form;

        // Instancia Facade
        CategoriaTalentoFacade objCategoriaTalentoFacade = new CategoriaTalentoFacade();

        // Instacia novo POJO
        CategoriaTalento objCategoriaTalento;

        try
        {
            // Busca categoria de talento para carregar o sequencial de ordena��o
            objCategoriaTalento = objCategoriaTalentoFacade.obterPelaChave(detalheCategoriaTalentoForm.getIdentificador());

            // Altera dados
            objCategoriaTalento.setNome(detalheCategoriaTalentoForm.getNome());
            objCategoriaTalento.setDescricao(detalheCategoriaTalentoForm.getDescricao());
            objCategoriaTalento.setDicaPreenchimento(detalheCategoriaTalentoForm.getDicaPreenchimento());
            objCategoriaTalento.setIndicativoUnicidade((detalheCategoriaTalentoForm.getIndicativoUnicidade() ? "S" : "N"));
            String strGruposForm[] = detalheCategoriaTalentoForm.getGrupoDestino();
            Set setGrupos = new HashSet();
            if (strGruposForm != null)
            {
                for (int i = 0; i < strGruposForm.length; i++)
                {
                    Grupo objGrupo = new Grupo();
                    objGrupo.setCodigo(Integer.valueOf(strGruposForm[i]));
                    setGrupos.add(objGrupo);
                }
            }
            objCategoriaTalento.setGruposCategoriaTalento(setGrupos);
            objCategoriaTalentoFacade.alterar(objCategoriaTalento);

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
     * Metodo categoriaTalentoEfetuarExclusao
     */

    public ArrayList categoriaTalentoEfetuarExclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m o form
        DetalheCategoriaTalentoForm detalheCategoriaTalentoForm = (DetalheCategoriaTalentoForm) form;

        // Instancia Facade
        CategoriaTalentoFacade objCategoriaTalentoFacade = new CategoriaTalentoFacade();

        // Instacia novo POJO
        CategoriaTalento objCategoriaTalento = new CategoriaTalento();

        // Obt�m chave para prepara��o de exclus�o
        String strChave = request.getParameter("chave");

        try
        {
            // Exclui dados
            objCategoriaTalento.setIdentificador(Integer.valueOf(strChave));
            objCategoriaTalentoFacade.excluir(objCategoriaTalento);

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
     * Metodo categoriaTalentoEfetuarOrdenacao
     */

    public ArrayList categoriaTalentoEfetuarOrdenacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m o form
        OrdenacaoCategoriaTalentoForm ordenacaoCategoriaTalentoForm = (OrdenacaoCategoriaTalentoForm) form;

        // Instancia Facade
        CategoriaTalentoFacade objCategoriaTalentoFacade = new CategoriaTalentoFacade();

        try
        {
            // Croa lista para ordena��o
            List lstOrdenacao = new ArrayList();

            // Itera categorias de talento obtidas do form
            String strOrdenacao[] = ordenacaoCategoriaTalentoForm.getSequencialPreenchimento();
            for (int i = 0; i < strOrdenacao.length; i++)
            {
                CategoriaTalento objCategoriaTalento = new CategoriaTalento();
                objCategoriaTalento.setIdentificador(Integer.valueOf(strOrdenacao[i]));
                lstOrdenacao.add(objCategoriaTalento);
            }

            // Ordena as categorias de talento
            objCategoriaTalentoFacade.ordenar(lstOrdenacao);

            // Mensagem
            mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", "Ordena��o efetuada com sucesso"));
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
     * Metodo categoriaTalentoPrepararVisualizacao
     */

    public ArrayList categoriaTalentoPrepararVisualizacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m sess�o
        HttpSession session = request.getSession();

        // List para armazenar resultado da consulta
        List lstCategoriasTalento = null;
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
        CategoriaTalentoFacade objCategoriaTalentoFacade = new CategoriaTalentoFacade();

        try
        {
            // Obt�m dados
            lstCategoriasTalento = objCategoriaTalentoFacade.obterPorPagina(intPagina, 20);
            if (intPagina == 1)
            {
                strTotalRegistros = String.valueOf(objCategoriaTalentoFacade.obterTotalRegistros());
                session.setAttribute("strTotalRegistros", strTotalRegistros);
            }
            request.setAttribute("lstCategoriasTalento", lstCategoriasTalento);
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
     * Metodo detalheCategoriaTalentoEfetuarInclusao
     */

    public ArrayList detalheCategoriaTalentoEfetuarInclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m o form
        DetalheCategoriaTalentoForm detalheCategoriaTalentoForm = (DetalheCategoriaTalentoForm) form;

        // Instancia Facade
        CategoriaTalentoFacade objCategoriaTalentoFacade = new CategoriaTalentoFacade();

        // Instacia novo POJO
        CategoriaTalento objCategoriaTalento = new CategoriaTalento();

        try
        {
            // Inclui dados
            objCategoriaTalento.setNome(detalheCategoriaTalentoForm.getNome());
            objCategoriaTalento.setDescricao(detalheCategoriaTalentoForm.getDescricao());
            objCategoriaTalento.setDicaPreenchimento(detalheCategoriaTalentoForm.getDicaPreenchimento());
            objCategoriaTalento.setIndicativoUnicidade((detalheCategoriaTalentoForm.getIndicativoUnicidade() ? "S" : "N"));
            String strGruposForm[] = detalheCategoriaTalentoForm.getGrupoDestino();
            Set setGrupos = new HashSet();
            if (strGruposForm != null)
            {
                for (int i = 0; i < strGruposForm.length; i++)
                {
                    Grupo objGrupo = new Grupo();
                    objGrupo.setCodigo(Integer.valueOf(strGruposForm[i]));
                    setGrupos.add(objGrupo);
                }
            }
            objCategoriaTalento.setGruposCategoriaTalento(setGrupos);
            objCategoriaTalentoFacade.incluir(objCategoriaTalento);

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
     * Metodo detalheCategoriaTalentoPrepararAlteracao
     */

    public ArrayList detalheCategoriaTalentoPrepararAlteracao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m o form
        DetalheCategoriaTalentoForm detalheCategoriaTalentoForm = (DetalheCategoriaTalentoForm) form;

        // Obt�m a chave
        String strChave;
        if (request.getParameter("chave") != null)
        {
            strChave = request.getParameter("chave");
        }
        else
        {
            strChave = detalheCategoriaTalentoForm.getIdentificador();
        }

        // Instancia Facade
        CategoriaTalentoFacade objCategoriaTalentoFacade = new CategoriaTalentoFacade();

        try
        {
            // Obt�m objeto de categoria de talento
            CategoriaTalento objCategoriaTalento;
            objCategoriaTalento = objCategoriaTalentoFacade.obterPelaChave(strChave);

            // Preenche formul�rio
            detalheCategoriaTalentoForm.setIdentificador(String.valueOf(objCategoriaTalento.getIdentificador()));
            detalheCategoriaTalentoForm.setNome(objCategoriaTalento.getNome());
            detalheCategoriaTalentoForm.setDescricao(objCategoriaTalento.getDescricao());
            detalheCategoriaTalentoForm.setDicaPreenchimento(objCategoriaTalento.getDicaPreenchimento());
            detalheCategoriaTalentoForm.setIndicativoUnicidade(("S".equals(objCategoriaTalento.getIndicativoUnicidade()) ? true : false));
            List lstGruposDisponiveis = objCategoriaTalentoFacade.obterGruposDisponiveis(objCategoriaTalento);
            request.setAttribute("lstGruposDisponiveis", lstGruposDisponiveis);
            List lstGruposSelecionados = objCategoriaTalentoFacade.obterGruposSelecionados(objCategoriaTalento);
            request.setAttribute("lstGruposSelecionados", lstGruposSelecionados);

            // T�tulo da tabela de detalhe
            request.setAttribute("strTituloTabelaDetalhe", "Altera��o - " + objCategoriaTalento.getNome());
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
     * Metodo detalheCategoriaTalentoPrepararInclusao
     */

    public ArrayList detalheCategoriaTalentoPrepararInclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia Facade
        CategoriaTalentoFacade objCategoriaTalentoFacade = new CategoriaTalentoFacade();

        try
        {

            // Obt�m lista de grupos, e armazena no request
            if (request.getAttribute("blnEncadeamento") == null)
            {
                List lstGruposDisponiveis = objCategoriaTalentoFacade.obterGrupos();
                request.setAttribute("lstGruposDisponiveis", lstGruposDisponiveis);
            }

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
     * Metodo exclusaoCategoriaTalentoPrepararExclusao
     */

    public ArrayList exclusaoCategoriaTalentoPrepararExclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia Facade
        CategoriaTalentoFacade objCategoriaTalentoFacade = new CategoriaTalentoFacade();

        // Cria bean
        CategoriaTalento objCategoriaTalento = null;

        // Cria lista para inser��o do registro para exclus�o
        List lstCategoriasTalento = new ArrayList();

        // Obt�m chave para prepara��o de exclus�o
        String strChave = request.getParameter("chave");

        try
        {
            // Obt�m bean para prepara��o de exclus�o
            objCategoriaTalento = objCategoriaTalentoFacade.obterPelaChave(strChave);
            lstCategoriasTalento.add(objCategoriaTalento);

            // Armazena dados no request
            request.setAttribute("lstCategoriasTalento", lstCategoriasTalento);
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
     * Metodo ordenacaoCategoriaTalentoPrepararVisualizacao
     */

    public ArrayList ordenacaoCategoriaTalentoPrepararVisualizacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia Facade
        CategoriaTalentoFacade objCategoriaTalentoFacade = new CategoriaTalentoFacade();

        try
        {
            // Obt�m lista para ordena��o
            List lstCategoriasTalento = objCategoriaTalentoFacade.obterTodos();

            // Armazena dados no request
            request.setAttribute("lstCategoriasTalento", lstCategoriasTalento);

            // T�tulo da tabela de detalhe
            request.setAttribute("strTituloTabelaDetalhe", "Ordena��o de categorias de talento");

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

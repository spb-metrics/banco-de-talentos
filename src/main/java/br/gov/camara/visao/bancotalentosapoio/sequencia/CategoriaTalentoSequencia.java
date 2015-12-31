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
     * @param form FormulÃ¡rio associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que jÃ¡ ocorreram
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

        // Obtém o form
        DetalheCategoriaTalentoForm detalheCategoriaTalentoForm = (DetalheCategoriaTalentoForm) form;

        // Instancia Facade
        CategoriaTalentoFacade objCategoriaTalentoFacade = new CategoriaTalentoFacade();

        // Instacia novo POJO
        CategoriaTalento objCategoriaTalento;

        try
        {
            // Busca categoria de talento para carregar o sequencial de ordenação
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

        // Obtém o form
        DetalheCategoriaTalentoForm detalheCategoriaTalentoForm = (DetalheCategoriaTalentoForm) form;

        // Instancia Facade
        CategoriaTalentoFacade objCategoriaTalentoFacade = new CategoriaTalentoFacade();

        // Instacia novo POJO
        CategoriaTalento objCategoriaTalento = new CategoriaTalento();

        // Obtém chave para preparação de exclusão
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

        // Obtém o form
        OrdenacaoCategoriaTalentoForm ordenacaoCategoriaTalentoForm = (OrdenacaoCategoriaTalentoForm) form;

        // Instancia Facade
        CategoriaTalentoFacade objCategoriaTalentoFacade = new CategoriaTalentoFacade();

        try
        {
            // Croa lista para ordenação
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
            mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", "Ordenação efetuada com sucesso"));
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

        // Obtém sessão
        HttpSession session = request.getSession();

        // List para armazenar resultado da consulta
        List lstCategoriasTalento = null;
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
        CategoriaTalentoFacade objCategoriaTalentoFacade = new CategoriaTalentoFacade();

        try
        {
            // Obtém dados
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

        // Obtém o form
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

        // Obtém o form
        DetalheCategoriaTalentoForm detalheCategoriaTalentoForm = (DetalheCategoriaTalentoForm) form;

        // Obtém a chave
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
            // Obtém objeto de categoria de talento
            CategoriaTalento objCategoriaTalento;
            objCategoriaTalento = objCategoriaTalentoFacade.obterPelaChave(strChave);

            // Preenche formulário
            detalheCategoriaTalentoForm.setIdentificador(String.valueOf(objCategoriaTalento.getIdentificador()));
            detalheCategoriaTalentoForm.setNome(objCategoriaTalento.getNome());
            detalheCategoriaTalentoForm.setDescricao(objCategoriaTalento.getDescricao());
            detalheCategoriaTalentoForm.setDicaPreenchimento(objCategoriaTalento.getDicaPreenchimento());
            detalheCategoriaTalentoForm.setIndicativoUnicidade(("S".equals(objCategoriaTalento.getIndicativoUnicidade()) ? true : false));
            List lstGruposDisponiveis = objCategoriaTalentoFacade.obterGruposDisponiveis(objCategoriaTalento);
            request.setAttribute("lstGruposDisponiveis", lstGruposDisponiveis);
            List lstGruposSelecionados = objCategoriaTalentoFacade.obterGruposSelecionados(objCategoriaTalento);
            request.setAttribute("lstGruposSelecionados", lstGruposSelecionados);

            // Título da tabela de detalhe
            request.setAttribute("strTituloTabelaDetalhe", "Alteração - " + objCategoriaTalento.getNome());
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

            // Obtém lista de grupos, e armazena no request
            if (request.getAttribute("blnEncadeamento") == null)
            {
                List lstGruposDisponiveis = objCategoriaTalentoFacade.obterGrupos();
                request.setAttribute("lstGruposDisponiveis", lstGruposDisponiveis);
            }

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

        // Cria lista para inserção do registro para exclusão
        List lstCategoriasTalento = new ArrayList();

        // Obtém chave para preparação de exclusão
        String strChave = request.getParameter("chave");

        try
        {
            // Obtém bean para preparação de exclusão
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
            // Obtém lista para ordenação
            List lstCategoriasTalento = objCategoriaTalentoFacade.obterTodos();

            // Armazena dados no request
            request.setAttribute("lstCategoriasTalento", lstCategoriasTalento);

            // Título da tabela de detalhe
            request.setAttribute("strTituloTabelaDetalhe", "Ordenação de categorias de talento");

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

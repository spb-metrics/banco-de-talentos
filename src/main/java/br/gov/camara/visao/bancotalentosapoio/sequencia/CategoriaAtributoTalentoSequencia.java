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
import br.gov.camara.negocio.bancotalentos.facade.CategoriaAtributoTalentoFacade;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalento;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaAtributoTalento;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaTalento;
import br.gov.camara.visao.bancotalentosapoio.form.DetalheCategoriaAtributoTalentoForm;
import br.gov.camara.visao.bancotalentosapoio.form.OrdenacaoCategoriaAtributoTalentoForm;

/**
 * Sequencia CategoriaAtributoTalentoSequencia
 */

public final class CategoriaAtributoTalentoSequencia extends SigespSequencia
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(CategoriaAtributoTalentoSequencia.class);

    /**
     * Metodo construtor
     *
     * @param mapping Mapeamento executado
     * @param form Formulário associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que já ocorreram
     * @param mensagens Colecao de mensagens a serem exibidas
     * @param CategoriaAtributoTalentoAction Classe Action associada
     */

    public CategoriaAtributoTalentoSequencia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,
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
     * Metodo categoriaAtributoTalentoEfetuarAlteracao
     */

    public ArrayList categoriaAtributoTalentoEfetuarAlteracao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m sess�o
        HttpSession session = request.getSession();

        // Obt�m o form
        DetalheCategoriaAtributoTalentoForm detalheCategoriaAtributoTalentoForm = (DetalheCategoriaAtributoTalentoForm) form;

        // Obt�m objeto com a categoria de talento relacionada
        CategoriaTalento objCategoriaTalento = (CategoriaTalento) session.getAttribute("objCategoriaTalento");

        // Instancia Facade
        CategoriaAtributoTalentoFacade objCategoriaAtributoTalentoFacade = new CategoriaAtributoTalentoFacade();

        // Instacia novo POJO
        CategoriaAtributoTalento objCategoriaAtributoTalento;

        try
        {
            // Busca categoria/atributo de talento para carregar o sequencial de ordena��o
            objCategoriaAtributoTalento = objCategoriaAtributoTalentoFacade.obterPelaChave(detalheCategoriaAtributoTalentoForm.getIdentificador());

            // Altera dados
            objCategoriaAtributoTalento.setCategoriaTalento(objCategoriaTalento);
            if (detalheCategoriaAtributoTalentoForm.getAtributoTalento() != null && !"".equals(detalheCategoriaAtributoTalentoForm.getAtributoTalento()))
            {
                AtributoTalento objAtributoTalento = new AtributoTalento();
                objAtributoTalento.setIdentificador(Integer.valueOf(detalheCategoriaAtributoTalentoForm.getAtributoTalento()));
                objCategoriaAtributoTalento.setAtributoTalento(objAtributoTalento);
            }
            objCategoriaAtributoTalento.setIndicativoObrigatoriedade((detalheCategoriaAtributoTalentoForm.getIndicativoObrigatoriedade() ? "S" : "N"));
            objCategoriaAtributoTalento.setFormacaoDescricao((detalheCategoriaAtributoTalentoForm.getFormacaoDescricao() ? "S" : "N"));
            objCategoriaAtributoTalento.setDicaPreenchimento(detalheCategoriaAtributoTalentoForm.getDicaPreenchimento());
            objCategoriaAtributoTalento.setApelido(detalheCategoriaAtributoTalentoForm.getApelido());
            objCategoriaAtributoTalentoFacade.alterar(objCategoriaAtributoTalento);

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

        // Marca encadeamento
        request.setAttribute("blnEncadeamento", new Boolean(true));

        // Mensagens        
        arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, mensagens);
        arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, erros);

        return (arlMensagens);
    }

    /**
     * Metodo categoriaAtributoTalentoEfetuarExclusao
     */

    public ArrayList categoriaAtributoTalentoEfetuarExclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m sess�o
        HttpSession session = request.getSession();

        // Obt�m valor de confirma��o, para realizar o controle da exclus�o 
        // (se aparece msg de confirma��o pra atributos valorados)
        String strConfirma = (String) session.getAttribute("strConfirma");
        session.removeAttribute("strConfirma");

        // Obt�m o form
        DetalheCategoriaAtributoTalentoForm detalheCategoriaAtributoTalentoForm = (DetalheCategoriaAtributoTalentoForm) form;

        // Instancia Facade
        CategoriaAtributoTalentoFacade objCategoriaAtributoTalentoFacade = new CategoriaAtributoTalentoFacade();

        // Instacia novo POJO
        CategoriaAtributoTalento objCategoriaAtributoTalento = new CategoriaAtributoTalento();

        // Obt�m chave para prepara��o de exclus�o
        String strChave = request.getParameter("chave");

        try
        {
            // Exclui dados
            objCategoriaAtributoTalento.setIdentificador(Integer.valueOf(strChave));
            objCategoriaAtributoTalentoFacade.excluir(objCategoriaAtributoTalento, strConfirma);

            // Mensagem
            mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", ((MessageResources) mapRecursos.get("comum")).getMessage("sigesp.comum.interface.mensagem.exclusao.realizada")));
        }
        catch (CDException cde)
        {
            if (log.isErrorEnabled())
            {
                log.error(cde.obterExcecoes());
            }
            // Verifica se a mensagem � de confirma��o de exclus�o
            if (cde.obterMensagem().indexOf("Confirma exclus�o?") > 0)
            {
                session.setAttribute("strConfirma", cde.obterMensagem());
                erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", "Confirme a exclus�o!"));
            }
            else
            {
                erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", cde.obterMensagem()));
            }
        }

        // Marca encadeamento
        request.setAttribute("blnEncadeamento", new Boolean(true));

        // Mensagens        
        arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, mensagens);
        arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, erros);

        return (arlMensagens);
    }

    /**
     * Metodo categoriaAtributoTalentoEfetuarOrdenacao
     */

    public ArrayList categoriaAtributoTalentoEfetuarOrdenacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m o form
        OrdenacaoCategoriaAtributoTalentoForm ordenacaoCategoriaAtributoTalentoForm = (OrdenacaoCategoriaAtributoTalentoForm) form;

        // Instancia Facade
        CategoriaAtributoTalentoFacade objCategoriaAtributoTalentoFacade = new CategoriaAtributoTalentoFacade();

        try
        {
            // Croa lista para ordena��o
            List lstOrdenacao = new ArrayList();

            // Itera categorias/atributos de talento obtidas do form
            String strOrdenacao[] = ordenacaoCategoriaAtributoTalentoForm.getSequencialPreenchimento();
            for (int i = 0; i < strOrdenacao.length; i++)
            {
                CategoriaAtributoTalento objCategoriaAtributoTalento = new CategoriaAtributoTalento();
                objCategoriaAtributoTalento.setIdentificador(Integer.valueOf(strOrdenacao[i]));
                lstOrdenacao.add(objCategoriaAtributoTalento);
            }

            // Ordena as categorias/atributos de talento
            objCategoriaAtributoTalentoFacade.ordenar(lstOrdenacao);

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
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Mensagens
        arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, mensagens);
        arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, erros);

        return (arlMensagens);
    }

    /**
     * Metodo categoriaAtributoTalentoPrepararVisualizacao
     */

    public ArrayList categoriaAtributoTalentoPrepararVisualizacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m sess�o
        HttpSession session = request.getSession();

        session.removeAttribute("strConfirma");

        // List para armazenar resultado da consulta
        List lstCategoriaAtributosTalento = null;
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
        CategoriaAtributoTalentoFacade objCategoriaAtributoTalentoFacade = new CategoriaAtributoTalentoFacade();

        try
        {
            // Verifica qual a categoria de talento passada, e obt�m objeto para armanenar na sess�o
            CategoriaTalento objCategoriaTalento = null;
            if (request.getAttribute("blnEncadeamento") == null && request.getParameter("pagina") == null)
            {
                if (request.getParameter("chave") != null)
                {
                    objCategoriaTalento = objCategoriaAtributoTalentoFacade.obterCategoriaTalentoPelaChave(request.getParameter("chave"));
                    session.setAttribute("objCategoriaTalento", objCategoriaTalento);
                }
                else
                {
                    objCategoriaTalento = (CategoriaTalento) session.getAttribute("objCategoriaTalento");
                }
            }
            else
            {
                objCategoriaTalento = (CategoriaTalento) session.getAttribute("objCategoriaTalento");
            }

            // Obt�m dados
            lstCategoriaAtributosTalento = objCategoriaAtributoTalentoFacade.obterPorCategoriaTalentoPorPagina(objCategoriaTalento, intPagina, 20);
            if (intPagina == 1)
            {
                strTotalRegistros = String.valueOf(objCategoriaAtributoTalentoFacade.obterTotalRegistrosPorCategoriaTalento(objCategoriaTalento));
                session.setAttribute("strTotalRegistros", strTotalRegistros);
            }
            request.setAttribute("lstCategoriaAtributosTalento", lstCategoriaAtributosTalento);
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

    /**
     * Metodo detalheCategoriaAtributoTalentoEfetuarInclusao
     */

    public ArrayList detalheCategoriaAtributoTalentoEfetuarInclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m sess�o
        HttpSession session = request.getSession();

        // Obt�m o form
        DetalheCategoriaAtributoTalentoForm detalheCategoriaAtributoTalentoForm = (DetalheCategoriaAtributoTalentoForm) form;

        // Obt�m objeto com a categoria de talento relacionada
        CategoriaTalento objCategoriaTalento = (CategoriaTalento) session.getAttribute("objCategoriaTalento");

        // Instancia Facade
        CategoriaAtributoTalentoFacade objCategoriaAtributoTalentoFacade = new CategoriaAtributoTalentoFacade();

        // Instacia novo POJO
        CategoriaAtributoTalento objCategoriaAtributoTalento = new CategoriaAtributoTalento();

        try
        {
            // Inclui dados
            objCategoriaAtributoTalento.setCategoriaTalento(objCategoriaTalento);
            if (detalheCategoriaAtributoTalentoForm.getAtributoTalento() != null && !"".equals(detalheCategoriaAtributoTalentoForm.getAtributoTalento()))
            {
                AtributoTalento objAtributoTalento = new AtributoTalento();
                objAtributoTalento.setIdentificador(Integer.valueOf(detalheCategoriaAtributoTalentoForm.getAtributoTalento()));
                objCategoriaAtributoTalento.setAtributoTalento(objAtributoTalento);
            }
            objCategoriaAtributoTalento.setIndicativoObrigatoriedade((detalheCategoriaAtributoTalentoForm.getIndicativoObrigatoriedade() ? "S" : "N"));
            objCategoriaAtributoTalento.setFormacaoDescricao((detalheCategoriaAtributoTalentoForm.getFormacaoDescricao() ? "S" : "N"));
            objCategoriaAtributoTalento.setDicaPreenchimento(detalheCategoriaAtributoTalentoForm.getDicaPreenchimento());
            objCategoriaAtributoTalento.setApelido(detalheCategoriaAtributoTalentoForm.getApelido());
            objCategoriaAtributoTalentoFacade.incluir(objCategoriaAtributoTalento);

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
     * Metodo detalheAtributoTalentoOpcaoPrepararAlteracao
     */

    public ArrayList detalheCategoriaAtributoTalentoPrepararAlteracao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m sess�o
        HttpSession session = request.getSession();

        // Obt�m o form
        DetalheCategoriaAtributoTalentoForm detalheCategoriaAtributoTalentoForm = (DetalheCategoriaAtributoTalentoForm) form;

        // Obt�m a chave
        String strChave;
        if (request.getParameter("chave") != null)
        {
            strChave = request.getParameter("chave");
        }
        else
        {
            strChave = detalheCategoriaAtributoTalentoForm.getIdentificador();
        }

        // Obt�m objeto com a categoria de talento relacionada
        CategoriaTalento objCategoriaTalento = (CategoriaTalento) session.getAttribute("objCategoriaTalento");

        // Instancia Facade
        CategoriaAtributoTalentoFacade objCategoriaAtributoTalentoFacade = new CategoriaAtributoTalentoFacade();

        try
        {
            // Obt�m objeto de categoria/atributo de talento
            CategoriaAtributoTalento objCategoriaAtributoTalento = new CategoriaAtributoTalento();
            objCategoriaAtributoTalento = objCategoriaAtributoTalentoFacade.obterPelaChave(strChave);

            // Preenche formul�rio
            detalheCategoriaAtributoTalentoForm.setIdentificador(String.valueOf(objCategoriaAtributoTalento.getIdentificador()));
            if (objCategoriaAtributoTalento.getAtributoTalento() != null)
            {
                detalheCategoriaAtributoTalentoForm.setAtributoTalento(String.valueOf(objCategoriaAtributoTalento.getAtributoTalento().getIdentificador()));
            }
            detalheCategoriaAtributoTalentoForm.setIndicativoObrigatoriedade(("S".equals(objCategoriaAtributoTalento.getIndicativoObrigatoriedade()) ? true : false));
            detalheCategoriaAtributoTalentoForm.setFormacaoDescricao(("S".equals(objCategoriaAtributoTalento.getFormacaoDescricao()) ? true : false));
            detalheCategoriaAtributoTalentoForm.setDicaPreenchimento(objCategoriaAtributoTalento.getDicaPreenchimento());
            detalheCategoriaAtributoTalentoForm.setApelido(objCategoriaAtributoTalento.getApelido());

            // T�tulo da tabela de detalhe
            request.setAttribute("strTituloTabelaDetalhe", "Altera��o - " + objCategoriaAtributoTalento.getCategoriaTalento().getNome());
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
     * Metodo detalheCategoriaAtributoTalentoPrepararInclusao
     */

    public ArrayList detalheCategoriaAtributoTalentoPrepararInclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m sess�o
        HttpSession session = request.getSession();

        // Obt�m objeto com o atributo de talento relacionado
        CategoriaTalento objCategoriaTalento = (CategoriaTalento) session.getAttribute("objCategoriaTalento");

        // Instancia Facade
        CategoriaAtributoTalentoFacade objCategoriaAtributoTalentoFacade = new CategoriaAtributoTalentoFacade();

        try
        {
            // Obt�m atributos de talento
            List lstAtributosTalento = objCategoriaAtributoTalentoFacade.obterAtributosTalento();
            request.setAttribute("lstAtributosTalento", lstAtributosTalento);

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
     * Metodo exclusaoCategoriaAtributoTalentoPrepararExclusao
     */

    public ArrayList exclusaoCategoriaAtributoTalentoPrepararExclusao()
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
        CategoriaAtributoTalentoFacade objCategoriaAtributoTalentoFacade = new CategoriaAtributoTalentoFacade();

        // Cria bean
        CategoriaAtributoTalento objCategoriaAtributoTalento = null;

        // Cria lista para inser��o do registro para exclus�o
        List lstCategoriaAtributosTalento = new ArrayList();

        // Obt�m chave para prepara��o de exclus�o
        String strChave = request.getParameter("chave");

        try
        {
            // Obt�m bean para prepara��o de exclus�o
            objCategoriaAtributoTalento = objCategoriaAtributoTalentoFacade.obterPelaChave(strChave);
            lstCategoriaAtributosTalento.add(objCategoriaAtributoTalento);

            // Armazena dados no request
            request.setAttribute("lstCategoriaAtributosTalento", lstCategoriaAtributosTalento);
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

    /**
     * Metodo ordenacaoCategoriaAtributoTalentoPrepararVisualizacao
     */

    public ArrayList ordenacaoCategoriaAtributoTalentoPrepararVisualizacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m sess�o
        HttpSession session = request.getSession();

        // Obt�m objeto com a categoria de talento relacionada
        CategoriaTalento objCategoriaTalento = (CategoriaTalento) session.getAttribute("objCategoriaTalento");

        // Instancia Facade
        CategoriaAtributoTalentoFacade objCategoriaAtributoTalentoFacade = new CategoriaAtributoTalentoFacade();

        try
        {
            // Obt�m lista para ordena��o
            List lstCategoriaAtributosTalento = objCategoriaAtributoTalentoFacade.obterTodosPorCategoriaTalento(objCategoriaTalento);

            // Armazena dados no request
            request.setAttribute("lstCategoriaAtributosTalento", lstCategoriaAtributosTalento);

            // T�tulo da tabela de detalhe
            request.setAttribute("strTituloTabelaDetalhe", "Ordena��o de categoria/atributos de talento");

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

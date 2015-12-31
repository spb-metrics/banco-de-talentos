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
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;
import br.gov.camara.negocio.bancotalentos.facade.FiltroConsultaFacade;
import br.gov.camara.negocio.bancotalentos.pojo.FiltroConsulta;
import br.gov.camara.visao.bancotalentosapoio.form.DetalheFiltroConsultaUsuarioForm;

/**
 * Sequencia FiltroConsultaUsuarioSequencia
 */

public final class FiltroConsultaUsuarioSequencia extends SigespSequencia
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(FiltroConsultaUsuarioSequencia.class);

    /**
     * Metodo construtor
     *
     * @param mapping Mapeamento executado
     * @param form Formulário associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que já ocorreram
     * @param mensagens Colecao de mensagens a serem exibidas
     * @param FiltroConsultaUsuarioAction Classe Action associada
     */

    public FiltroConsultaUsuarioSequencia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,
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
     * Metodo detalheFiltroConsultaUsuarioEfetuarInclusao
     */

    public ArrayList detalheFiltroConsultaUsuarioEfetuarInclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m sess�o
        HttpSession session = request.getSession();

        // Instancia Facade
        FiltroConsultaFacade objFiltroConsultaFacade = new FiltroConsultaFacade();

        // Cria bean
        UsuarioSistema objUsuarioSistema = new UsuarioSistema();

        // Obt�m o form
        DetalheFiltroConsultaUsuarioForm detalheFiltroConsultaUsuarioForm = (DetalheFiltroConsultaUsuarioForm) form;

        try
        {
            // Verifica qual o Tipo de filtro passado, e obt�m objeto para armanenar na sess�o
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
            objUsuarioSistema.setIdentificador(new Integer(detalheFiltroConsultaUsuarioForm.getIdentificador()));
            objFiltroConsultaFacade.incluir(objUsuarioSistema, objFiltroConsulta);

            // Mensagem
            mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", ((MessageResources) mapRecursos.get("comum")).getMessage("sigesp.comum.interface.mensagem.inclusao.realizada")));
        }
        catch (NumberFormatException e)
        {
            erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", ((MessageResources) mapRecursos.get("BancoTalentosApoio")).getMessage("sigesp.BancoTalentosApoio.interface.erro.identificadorUsuarioInvalido")));
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
     * Metodo detalheFiltroConsultaUsuarioPrepararInclusao
     */

    public ArrayList detalheFiltroConsultaUsuarioPrepararInclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        request.setAttribute("strTituloTabelaDetalhe", "Inclus�o");
        // Mensagens
        arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, mensagens);
        arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, erros);

        return (arlMensagens);
    }

    /**
     * Metodo exclusaoFiltroConsultaUsuarioPrepararExclusao
     */

    public ArrayList exclusaoFiltroConsultaUsuarioPrepararExclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m sess�o
        HttpSession session = request.getSession();

        // Instancia Facade
        FiltroConsultaFacade objFiltroConsultaFacade = new FiltroConsultaFacade();

        // Cria lista para inser��o do registro para exclus�o
        List lstFiltroConsultaUsuario = new ArrayList();

        // Obt�m chave para prepara��o de exclus�o
        String strChave = request.getParameter("chave");

        try
        {
            // Verifica qual o Tipo de filtro passado, e obt�m objeto para armanenar na sess�o
            FiltroConsulta objFiltroConsulta = null;
            objFiltroConsulta = (FiltroConsulta) session.getAttribute("objFiltroConsulta");
            lstFiltroConsultaUsuario.add(objFiltroConsultaFacade.obterPelaChave(strChave, objFiltroConsulta));
            request.setAttribute("lstFiltroConsultaUsuario", lstFiltroConsultaUsuario);
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
     * Metodo filtroConsultaUsuarioEfetuarExclusao
     */

    public ArrayList filtroConsultaUsuarioEfetuarExclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m sess�o
        HttpSession session = request.getSession();

        // Instancia Facade
        FiltroConsultaFacade objFiltroConsultaFacade = new FiltroConsultaFacade();

        // Cria bean
        UsuarioSistema objUsuarioSistema = new UsuarioSistema();

        try
        {
            // Verifica qual o Tipo de filtro passado, e obt�m objeto para armanenar na sess�o
            FiltroConsulta objFiltroConsulta = null;
            objFiltroConsulta = (FiltroConsulta) session.getAttribute("objFiltroConsulta");
            objUsuarioSistema.setIdentificador(new Integer(request.getParameter("chaveExclusao")));
            objFiltroConsultaFacade.excluir(objUsuarioSistema, objFiltroConsulta);

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
     * Metodo filtroConsultaUsuarioPrepararVisualizacao
     */

    public ArrayList filtroConsultaUsuarioPrepararVisualizacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m sess�o
        HttpSession session = request.getSession();

        // List para armazenar resultado da consulta

        // Instancia Facade
        FiltroConsultaFacade objFiltroConsultaFacade = new FiltroConsultaFacade();

        try
        {
            // Verifica qual o Tipo de filtro passado, e obt�m objeto para armanenar na sess�o
            FiltroConsulta objFiltroConsulta = null;
            if (request.getParameter("chave") == null)
            {
                objFiltroConsulta = (FiltroConsulta) session.getAttribute("objFiltroConsulta");
            }
            else
            {
                objFiltroConsulta = objFiltroConsultaFacade.obterPelaChave(request.getParameter("chave"));
            }
            session.setAttribute("objFiltroConsulta", objFiltroConsulta);

            // Preenche formul�rio
            request.setAttribute("lstFiltroConsultaUsuario", objFiltroConsulta.getFiltroConsultaUsuario());

            // T�tulo da tabela de detalhe
            request.setAttribute("strTituloTabelaDetalhe", "Filtro -  "
                    + objFiltroConsulta.getTipoFiltroConsulta().getNomeTipoFiltroConsulta()
                    + " > "
                    + objFiltroConsulta.getNomeFiltroConsulta());
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

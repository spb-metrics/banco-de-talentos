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


package br.gov.camara.visao.bancotalentosgestao.sequencia;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import sigesp.comum.util.struts.actions.ActionMessagesUtil;
import sigesp.comum.util.struts.SigespSequencia;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import br.gov.camara.biblioteca.util.GenericComparator;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.bancotalentos.facade.CurriculoFacade;
import br.gov.camara.negocio.bancotalentos.pojo.Pessoa;
import br.gov.camara.negocio.bancotalentos.pojo.Talento;
import br.gov.camara.seguranca.SegurancaWeb;
import br.gov.camara.util.relatorio.ExibicaoDocumento;
import br.gov.camara.util.relatorio.RelatorioFactory;
import br.gov.camara.util.relatorio.exception.RelatorioException;
import br.gov.camara.util.relatorio.jasper.RelatorioJasper;
/**
 * Sequencia CurriculoSequencia
 */

public final class CurriculoSequencia extends SigespSequencia
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(CurriculoSequencia.class);

    /**
     * Metodo construtor
     *
     * @param mapping Mapeamento executado
     * @param form Formulário associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que já ocorreram
     * @param mensagens Colecao de mensagens a serem exibidas
     * @param CurriculoAction Classe Action associada
     */

    public CurriculoSequencia (ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
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

        this.erros = new ActionMessages ();
        this.mensagens = new ActionMessages ();
    }

    // Metodos chamados

	
    /**
     * Metodo curriculoGerarRelatorio
     */

    public ArrayList curriculoGerarRelatorio()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia Facade
        CurriculoFacade objCurriculoFacade = new CurriculoFacade();

        String[] strIdePessoa = new String[1];
        strIdePessoa[0] = this.request.getParameter("chave");

        List lstRelatorio = null;
        try
        {
            // Obtem lista de servidores
            lstRelatorio = objCurriculoFacade.obterCurriculos(strIdePessoa);

            // Obt�m relat�rio
            RelatorioJasper relatorioPrincipal = (RelatorioJasper) RelatorioFactory.obterJasper(this.action.getServlet().getInitParameter("relatorioBancoTalentosGestao"), "Curriculo");

            // Adiciona SubRelat�rios e Par�metros
            relatorioPrincipal.adicionaSubRelatorio("Lotacoes");
            relatorioPrincipal.adicionaSubRelatorio("Talentos");
            //relatorioPrincipal.adicionaSubRelatorio("AtributosTalento");

            // T�tulo do relat�rio
            relatorioPrincipal.setTitulo("Curr�culo");

            // Obt�m PDF
            byte[] relatorio = relatorioPrincipal.obterPDF(lstRelatorio);

            ExibicaoDocumento exibicao = new ExibicaoDocumento();
            exibicao.setTipoConteudo("application/pdf");
            exibicao.setNomeArquivo("relatorio.pdf");
            exibicao.setAsAttachment(false);
            exibicao.gerarExibicaoDocumento(this.response, relatorio);
        }
        catch (RelatorioException re)
        {
            this.erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", re.obterMensagem()));
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
     * Metodo curriculoPrepararVisualizacao
     */

    public ArrayList curriculoPrepararVisualizacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obt�m sess�o
        HttpSession session = this.request.getSession();

        // Instancia Facade
        CurriculoFacade objCurriculoFacade = new CurriculoFacade();

        // Cria beans
        Pessoa objPessoa = null;
        List lstTalentos = null;

        try
        {
            // Obt�m usu�rio logado
            if (this.request.getAttribute("objPessoa") == null)
            {
                String strIdentificador = String.valueOf(SegurancaWeb.obterSegurancaWeb(session).obterUsuarioAutenticado().obterIdentificador());
                objPessoa = objCurriculoFacade.obterPessoaPelaChave(strIdentificador);
            }
            else
            {
                objPessoa = (Pessoa) this.request.getAttribute("objPessoa");
            }

            if (objPessoa != null)
            {

                // Obt�m pessoa
                this.request.setAttribute("objPessoa", objPessoa);
                ArrayList objLotacoes = new ArrayList(objPessoa.getLotacoes());

                // Collections.sort(objLotacoes, new
                // OrdenadorBean("dataInicio"));

                GenericComparator objGenericComparator = new GenericComparator("calDataInicio", GenericComparator.DESC, null, "getTime");
                Collections.sort(objLotacoes, objGenericComparator);

                this.request.setAttribute("lstLotacoes", objLotacoes);

                // Obt�m talentos da pessoa
                lstTalentos = objCurriculoFacade.obterTalentosPorPessoa(objPessoa);
                this.request.setAttribute("lstTalentos", lstTalentos);

                // Obt�m valora��es
                Iterator itrTalentos = lstTalentos.iterator();
                while (itrTalentos.hasNext())
                {
                    Talento objTalento = (Talento) itrTalentos.next();
                    List lstAtributosTalentoValorados = objCurriculoFacade.obterValoracoesPorTalento(objTalento);
                    this.request.setAttribute(String.valueOf(objTalento.getIdentificador()), lstAtributosTalentoValorados);
                }
            }
            else
            {
                this.erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", "Os dados do usu�rio n�o foram encontrados"));
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

        // Mensagens
        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);
    }
     
    /**
     * Metodo curriculoPrepararVisualizacaoConsulta
     */

    public ArrayList curriculoPrepararVisualizacaoConsulta()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia Facade
        CurriculoFacade objCurriculoFacade = new CurriculoFacade();

        // Prepara visualiza��o do curr�culo obtido pela consulta
        try
        {
            // Cria pessoa
            Pessoa objPessoa = objCurriculoFacade.obterPessoaPelaChave(this.request.getParameter("chave"));

            // Obt�m pessoa para consulta
            this.request.setAttribute("objPessoa", objPessoa);
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
     * Metodo curriculoPrepararVisualizacaoEstatistica
     */

    public ArrayList curriculoPrepararVisualizacaoEstatistica()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Recupera sessao

        HttpSession session = this.request.getSession();

        // Efetuar validacoes necessarias
        //
        // Exemplo para adicionar um erro:
        // erros.add(ActionErrors.GLOBAL_ERROR, new
        // ActionError("sigesp.comum.erro.generico", "Mensagem de erro a ser
        // exibida"));
        //
        // if (erros.isEmpty())
        // {

        // -----------------------------------
        // Corpo do metodo - Completar aqui...
        // -----------------------------------

        // Exemplo de envio de uma mensagem para o usuario
        // mensagens.add(ActionMessages.GLOBAL_MESSAGE, new
        // ActionMessage("sigesp.comum.mensagem.generica", "Mensagem informativa
        // a ser exibida"));

        // }
        // Fim do metodo

        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);

    }
     

}
    

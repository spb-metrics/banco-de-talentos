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

package br.gov.camara.visao.bancotalentosgestao.sequencia;

import java.net.URLDecoder;
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

import br.gov.camara.util.aplicacao.AplicacaoPlugIn;
import sigesp.comum.util.struts.SigespSequencia;
import sigesp.comum.util.struts.actions.ActionMessagesUtil;
import br.gov.camara.biblioteca.util.StringUtil;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.bancotalentos.facade.ConsultaEstatisticaCadastroFacade;
import br.gov.camara.negocio.bancotalentos.facade.CurriculoFacade;
import br.gov.camara.negocio.util.Consulta;
import br.gov.camara.seguranca.SegurancaWeb;
import br.gov.camara.util.relatorio.ExibicaoDocumento;
import br.gov.camara.util.relatorio.RelatorioFactory;
import br.gov.camara.util.relatorio.exception.RelatorioException;
import br.gov.camara.util.relatorio.jasper.RelatorioJasper;
import br.gov.camara.visao.bancotalentosgestao.form.ConsultaEstatisticaCadastroForm;

/**
 * Sequencia ConsultaEstatisticasCadastroSequencia
 */

public final class ConsultaEstatisticasCadastroSequencia extends SigespSequencia
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(ConsultaEstatisticasCadastroSequencia.class);

    /**
     * Metodo construtor
     *
     * @param mapping Mapeamento executado
     * @param form FormulÃ¡rio associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que jÃ¡ ocorreram
     * @param mensagens Colecao de mensagens a serem exibidas
     * @param ConsultaEstatisticasCadastroAction Classe Action associada
     */

    public ConsultaEstatisticasCadastroSequencia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,
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
     * Metodo consultaEstatisticaCadastroPrepararVisualizacao
     */

    public ArrayList consultaEstatisticaCadastroPrepararVisualizacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Recupera sessao

        HttpSession session = this.request.getSession();

        String strTituloTabelaDetalhe = "Opções da consulta";

        this.request.setAttribute("strTituloTabelaDetalhe", strTituloTabelaDetalhe);

        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);

    }

    /**
     * Metodo listagemConsultaEstatisticaEfetuarListagem
     */

    public ArrayList listagemConsultaEstatisticaEfetuarListagem()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Recupera sessao
        HttpSession session = this.request.getSession();

        // Recupera parametros de consulta
        String strParametros = URLDecoder.decode(this.request.getQueryString());

        String strDataInicial = StringUtil.obterParametro(strParametros, "dtIni");
        String strDataTermino = StringUtil.obterParametro(strParametros, "dtFim");
        String strGrupo = StringUtil.obterParametro(strParametros, "grupo");
        String strCategoria = StringUtil.obterParametro(strParametros, "categoria");
        String strData = StringUtil.obterParametro(strParametros, "data");

        List lstPessoas = null;

        // Instancia a Facade
        ConsultaEstatisticaCadastroFacade objConsultaEstatisticaCadastroFacade = new ConsultaEstatisticaCadastroFacade();

        // Monta objeto de consulta
        Consulta objConsulta = new Consulta();
        String strTotalRegistros = "";
        int intPagina = 0;
        boolean blnFiltroGrupo = false;

        try
        {
            String strIdentificadorUsuario = String.valueOf(SegurancaWeb.obterSegurancaWeb(session).obterUsuarioAutenticado().obterIdentificador());

            if ("sim".equals(AplicacaoPlugIn.obterConfiguracao("filtrarConsultaPorGrupo")))
            {
                blnFiltroGrupo = true;
            }

            if (this.request.getParameter("pagina") != null)
            {
                intPagina = Integer.parseInt(this.request.getParameter("pagina"));
            }
            else
            {
                intPagina = 1;
                objConsulta.setClasse("br.gov.camara.negocio.bancotalentos.facade.ConsultaEstatisticaCadastroFacade");
                if (strDataInicial != null && !"".equals(strDataInicial))
                {
                    objConsulta.adicionarCampoFiltro("", "talento.dataLancamento", Consulta.DATA_MAIOR_OU_IGUAL);
                    objConsulta.adicionarCriterioFiltro(strDataInicial);
                }
                if (strDataTermino != null && !"".equals(strDataTermino))
                {
                    objConsulta.adicionarCampoFiltro("", "talento.dataLancamento", Consulta.DATA_MENOR_OU_IGUAL);
                    objConsulta.adicionarCriterioFiltro(strDataTermino);
                }
                if (strGrupo != null && !"".equals(strGrupo))
                {
                    objConsulta.adicionarCampoFiltro("", "talento.pessoa.grupo.codigo", Consulta.NUMERO);
                    objConsulta.adicionarCriterioFiltro(strGrupo);
                }
                if (strCategoria != null && !"".equals(strCategoria))
                {
                    objConsulta.adicionarCampoFiltro("", "talento.categoriaTalento.identificador", Consulta.NUMERO);
                    objConsulta.adicionarCriterioFiltro(strCategoria);
                }
                if (strData != null && !"".equals(strData))
                {
                    objConsulta.adicionarCampoFiltro("", "talento.dataLancamento", Consulta.DATA);
                    objConsulta.adicionarCriterioFiltro(strData);
                }

                // Carrega total de registros
                strTotalRegistros = String.valueOf(objConsultaEstatisticaCadastroFacade.obterTotalRegistros(strIdentificadorUsuario, blnFiltroGrupo, strDataInicial, strDataTermino, strGrupo, strCategoria, strData));
                session.setAttribute("strTotalRegistros", strTotalRegistros);
            }
            // Realiza consulta
            lstPessoas = objConsultaEstatisticaCadastroFacade.consultarListagem(strIdentificadorUsuario, blnFiltroGrupo, strDataInicial, strDataTermino, strGrupo, strCategoria, strData, intPagina, 20);

            // Objeto que configura filtro 
            this.request.setAttribute("lstListagemPessoas", lstPessoas);
        }
        catch (CDException cde)
        {
            this.erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", cde.obterMensagem() + cde.getCause()));
        }

        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);
    }

    /**
     * Metodo listagemConsultaEstatisticaGerarRelatorio
     */

    public ArrayList listagemConsultaEstatisticaGerarRelatorio()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém form
        // ListagemConsultaEstatisticaForm objForm = (ListagemConsultaEstatisticaForm) form;

        // Instancia Facade
        CurriculoFacade objCurriculoFacade = new CurriculoFacade();

        String strIdePessoa[] = (this.request.getParameter("curriculos")).split(",");

        List lstRelatorio = null;
        try
        {
            // Obtem lista de servidores
            lstRelatorio = objCurriculoFacade.obterCurriculos(strIdePessoa);

            // Obtém relatório
            RelatorioJasper relatorioPrincipal = (RelatorioJasper) RelatorioFactory.obterJasper(this.action.getServlet().getInitParameter("relatorioBancoTalentosGestao"), "Curriculo");

            // Adiciona SubRelatórios e Parâmetros
            relatorioPrincipal.adicionaSubRelatorio("Lotacoes");
            relatorioPrincipal.adicionaSubRelatorio("Talentos");
            //relatorioPrincipal.adicionaSubRelatorio("AtributosTalento");

            // Título do relatório
            relatorioPrincipal.setTitulo("Currículo");

            // Obtém PDF
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
     * Metodo resultadoConsultaEstatisticaCadastroEfetuarConsulta
     */

    public ArrayList resultadoConsultaEstatisticaCadastroEfetuarConsulta()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Recupera sessao
        HttpSession session = this.request.getSession();

        // Instancia facade
        ConsultaEstatisticaCadastroFacade objConsultaEstatisticaCadastroFacade = new ConsultaEstatisticaCadastroFacade();

        // Recupera form
        ConsultaEstatisticaCadastroForm consultaEstatisticaCadastroForm = (ConsultaEstatisticaCadastroForm) this.form;

        String strDataInicio = consultaEstatisticaCadastroForm.getDataInicio();
        String strDataTermino = consultaEstatisticaCadastroForm.getDataTermino();
        List lstQuebras = new ArrayList();

        // Utilizado para verificação de utilização do filtro de grupo 
        boolean blnFiltroGrupo = false;

        // Carrega bean de telefone
        if (consultaEstatisticaCadastroForm.getQuebrasSelecionadas() != null)
        {
            for (int i = 0; i < consultaEstatisticaCadastroForm.getQuebrasSelecionadas().length; i++)
            {
                lstQuebras.add(consultaEstatisticaCadastroForm.getQuebrasSelecionadas()[i]);
            }
        }

        if ("sim".equals(AplicacaoPlugIn.obterConfiguracao("filtrarConsultaPorGrupo")))
        {
            blnFiltroGrupo = true;
        }

        try
        {
            String strIdentificadorUsuario = String.valueOf(SegurancaWeb.obterSegurancaWeb(session).obterUsuarioAutenticado().obterIdentificador());

            List lstResultadoConsulta = objConsultaEstatisticaCadastroFacade.consultar(strIdentificadorUsuario, blnFiltroGrupo, strDataInicio, strDataTermino, lstQuebras);

            this.request.setAttribute("lstResultadoConsulta", lstResultadoConsulta);
        }
        catch (CDException cde)
        {
            this.erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", cde.obterMensagem()));
        }

        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);

    }

}

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
import br.gov.camara.negocio.bancotalentos.facade.TalentoFacade;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalentoOpcao;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalentoValorado;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaAtributoTalento;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaTalento;
import br.gov.camara.negocio.bancotalentos.pojo.Pessoa;
import br.gov.camara.negocio.bancotalentos.pojo.Talento;
import br.gov.camara.negocio.bancotalentos.pojo.TalentoVisualizacao;
import br.gov.camara.negocio.bancotalentos.util.GerenciadorAtributoVirtual;
import br.gov.camara.seguranca.SegurancaWeb;
import br.gov.camara.seguranca.UsuarioAutenticado;
import br.gov.camara.visao.bancotalentosgestao.form.DetalheTalentoForm;

/**
 * Sequencia TalentoSequencia
 */

public final class TalentoSequencia extends SigespSequencia
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(TalentoSequencia.class);

    /**
     * Metodo construtor
     *
     * @param mapping Mapeamento executado
     * @param form FormulÃ¡rio associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que jÃ¡ ocorreram
     * @param mensagens Colecao de mensagens a serem exibidas
     * @param TalentoAction Classe Action associada
     */

    public TalentoSequencia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages erros,
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
     * Metodo detalheTalentoBuscarHierarquia
     */

    public ArrayList detalheTalentoBuscarHierarquia()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = this.request.getSession();

        // Obtém form
        DetalheTalentoForm detalheTalentoForm = (DetalheTalentoForm) this.form;

        // Instancia Facade
        TalentoFacade objTalentoFacade = new TalentoFacade();

        try
        {
            // Obtém categoria de talento 
            CategoriaTalento objCategoriaTalento = (CategoriaTalento) session.getAttribute("objCategoriaTalento");

            // Obtém opção de talento a partir da chave recebida
            AtributoTalentoOpcao objAtributoTalentoOpcao = null;
            objAtributoTalentoOpcao = objTalentoFacade.obterAtributoTalentoOpcaoPelaChave(this.request.getParameter("atributoTalentoOpcao"));

            // Obtém hierarquia superior da opção recebido
            List lstAtributoTalentoOpcaoHierarquiaSuperior = objTalentoFacade.obterAtributoTalentoOpcaoHierarquiaSuperior(objAtributoTalentoOpcao);

            // Seta categoria/atributo para buscar opção
            CategoriaAtributoTalento objCategoriaAtributoTalento = objTalentoFacade.obterCategoriaAtributoTalentoPorCategoriaTalentoAtributoTalento(objCategoriaTalento, objAtributoTalentoOpcao.getAtributoTalento());
            this.request.setAttribute("objCategoriaAtributoTalento", objCategoriaAtributoTalento);

            // Seta opção para buscar opção
            this.request.setAttribute("objAtributoTalentoOpcao", objAtributoTalentoOpcao);

            // Obtém opções dos atributos de talento (na relação categoria/atributo)
            Iterator itrAtributoTalentoOpcaoHierarquiaSuperior = lstAtributoTalentoOpcaoHierarquiaSuperior.iterator();
            while (itrAtributoTalentoOpcaoHierarquiaSuperior.hasNext())
            {
                // Obtém opção da hierarquia
                AtributoTalentoOpcao objAtributoTalentoOpcaoHierarquiaSuperior = (AtributoTalentoOpcao) itrAtributoTalentoOpcaoHierarquiaSuperior.next();

                // Obtém categoria/atributo relacionado à opção
                CategoriaAtributoTalento objCategoriaAtributoTalentoHierarquiaSuperior = objTalentoFacade.obterCategoriaAtributoTalentoPorCategoriaTalentoAtributoTalento(objCategoriaTalento, objAtributoTalentoOpcaoHierarquiaSuperior.getAtributoTalento());

                // Opções 
                if (objAtributoTalentoOpcaoHierarquiaSuperior.getAtributoTalentoOpcaoPai() != null)
                {
                    List lstAtributoTalentoOpcoes = objTalentoFacade.obterOpcoesPeloPaiEAtributoTalento(objAtributoTalentoOpcaoHierarquiaSuperior.getAtributoTalentoOpcaoPai(), objAtributoTalentoOpcaoHierarquiaSuperior.getAtributoTalento());
                    session.setAttribute(String.valueOf(objCategoriaAtributoTalentoHierarquiaSuperior.getIdentificador()), lstAtributoTalentoOpcoes);
                }

                // Seta form
                detalheTalentoForm.setAtributoTalentoValorado(String.valueOf(objCategoriaAtributoTalentoHierarquiaSuperior.getIdentificador()), new String[] { String.valueOf(objAtributoTalentoOpcaoHierarquiaSuperior.getIdentificador()) });
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
     * Metodo detalheTalentoBuscarOpcao
     */

    public ArrayList detalheTalentoBuscarOpcao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = this.request.getSession();

        // Obtém form
        DetalheTalentoForm detalheTalentoForm = (DetalheTalentoForm) this.form;
        // Instancia Facade
        TalentoFacade objTalentoFacade = new TalentoFacade();

        try
        {
            // Obtém categoria/atributo de talento a partir da chave recebida
            CategoriaAtributoTalento objCategoriaAtributoTalento = null;
            if (this.request.getParameter("categoriaAtributoTalento") != null && !"".equals(this.request.getParameter("categoriaAtributoTalento")))
            {
                objCategoriaAtributoTalento = objTalentoFacade.obterCategoriaAtributoTalentoPelaChave(this.request.getParameter("categoriaAtributoTalento"));
            }
            else
            {
                objCategoriaAtributoTalento = (CategoriaAtributoTalento) this.request.getAttribute("objCategoriaAtributoTalento");
            }

            // Cria opção de talento a partir da chave recebida
            AtributoTalentoOpcao objAtributoTalentoOpcao = null;
            if (this.request.getParameter("atributoTalentoOpcao") != null && !"".equals(this.request.getParameter("atributoTalentoOpcao")))
            {
                objAtributoTalentoOpcao = new AtributoTalentoOpcao();
                objAtributoTalentoOpcao.setIdentificador(new Integer(this.request.getParameter("atributoTalentoOpcao")));
            }
            else
            {
                objAtributoTalentoOpcao = (AtributoTalentoOpcao) this.request.getAttribute("objAtributoTalentoOpcao");
            }

            // Limpa toda a hierarquia inferior
            if (objCategoriaAtributoTalento != null)
            {
                List lstCategoriaAtributoTalentoHierarquiaInferior = objTalentoFacade.obterCategoriaAtributoTalentoHierarquiaInferior(objCategoriaAtributoTalento);
                Iterator itrCategoriaAtributoTalentoHierarquiaInferior = lstCategoriaAtributoTalentoHierarquiaInferior.iterator();
                while (itrCategoriaAtributoTalentoHierarquiaInferior.hasNext())
                {
                    CategoriaAtributoTalento objCategoriaAtributoTalentoHierarquiaInferior = (CategoriaAtributoTalento) itrCategoriaAtributoTalentoHierarquiaInferior.next();
                    session.removeAttribute(String.valueOf(objCategoriaAtributoTalentoHierarquiaInferior.getIdentificador()));
                    detalheTalentoForm.setAtributoTalentoValorado(String.valueOf(objCategoriaAtributoTalentoHierarquiaInferior.getIdentificador()), null);
                }
            }

            // Verifica se a categoria/atributo e as opções não estão nulas
            if (objCategoriaAtributoTalento != null && objAtributoTalentoOpcao != null)
            {
                // Obtém categoria/atributos filhos da opção selecionada
                List lstCategoriaAtributosTalentoFilhos = objTalentoFacade.obterCategoriaAtributosTalentoFilhos(objCategoriaAtributoTalento);

                // Obtém opções dos atributos de talento (na relação categoria/atributo)
                Iterator itrCategoriaAtributosTalentoFilhos = lstCategoriaAtributosTalentoFilhos.iterator();
                while (itrCategoriaAtributosTalentoFilhos.hasNext())
                {
                    CategoriaAtributoTalento objCategoriaAtributoTalentoFilho = (CategoriaAtributoTalento) itrCategoriaAtributosTalentoFilhos.next();

                    // Opções 
                    if ("U".equals(objCategoriaAtributoTalentoFilho.getAtributoTalento().getTipoHTML().getMultiplicidade())
                            || "M".equals(objCategoriaAtributoTalentoFilho.getAtributoTalento().getTipoHTML().getMultiplicidade()))
                    {
                        List lstAtributoTalentoOpcoes = objTalentoFacade.obterOpcoesPeloPaiEAtributoTalento(objAtributoTalentoOpcao, objCategoriaAtributoTalentoFilho.getAtributoTalento());
                        session.setAttribute(String.valueOf(objCategoriaAtributoTalentoFilho.getIdentificador()), lstAtributoTalentoOpcoes);
                    }
                }
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
     * Metodo detalheTalentoPrepararAlteracao
     */

    public ArrayList detalheTalentoPrepararAlteracao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = this.request.getSession();

        // Obtém form
        DetalheTalentoForm detalheTalentoForm = (DetalheTalentoForm) this.form;

        // Instancia Facade
        TalentoFacade objTalentoFacade = new TalentoFacade();

        try
        {
            // Obtém talento 
            Talento objTalento = null;
            if (this.request.getParameter("chave") != null)
            {
                objTalento = objTalentoFacade.obterPelaChave(this.request.getParameter("chave"));
            }
            else
            {
                objTalento = objTalentoFacade.obterPelaChave(detalheTalentoForm.getIdentificador());
            }

            // Preenche form e seta a categoria de talento na sessão
            detalheTalentoForm.setIdentificador(String.valueOf(objTalento.getIdentificador()));
            session.setAttribute("objCategoriaTalento", objTalento.getCategoriaTalento());

            // Obtém valorações
            List lstAtributosTalentoValorados = objTalentoFacade.obterValoracoesPorTalento(objTalento);

            // Obtém valorações  de talento, preenche o form, e verifica quais atributos têm 
            // filhos (na relação categoria/atributo) para buscar as opções
            Iterator itrAtributosTalentoValorados = lstAtributosTalentoValorados.iterator();
            AtributoTalentoValorado objAtributoTalentoValoradoAnterior = null;
            String strValoracoes = "";
            while (itrAtributosTalentoValorados.hasNext())
            {
                // Obtém valoração
                AtributoTalentoValorado objAtributoTalentoValorado = (AtributoTalentoValorado) itrAtributosTalentoValorados.next();

                // Como podem existir várias valorações para cada categoria/atributo, realiza a quebra
                if (objAtributoTalentoValoradoAnterior != null
                        && !objAtributoTalentoValorado.getCategoriaAtributoTalento().getIdentificador().equals(objAtributoTalentoValoradoAnterior.getCategoriaAtributoTalento().getIdentificador()))
                {
                    // Preenche form     
                    // System.out.println(strValoracoes);
                    detalheTalentoForm.setAtributoTalentoValorado(String.valueOf(objAtributoTalentoValoradoAnterior.getCategoriaAtributoTalento().getIdentificador()), strValoracoes.split("@#,#@"));

                    // Opções (se opção tiver filhos)
                    List lstCategoriaAtributosTalentoFilhos = objTalentoFacade.obterCategoriaAtributosTalentoFilhos(objAtributoTalentoValoradoAnterior.getCategoriaAtributoTalento());
                    if (lstCategoriaAtributosTalentoFilhos != null && objAtributoTalentoValoradoAnterior.getAtributoTalentoOpcao() != null)
                    {
                        List lstAtributoTalentoOpcoes = objTalentoFacade.obterOpcoesPeloPai(objAtributoTalentoValoradoAnterior.getAtributoTalentoOpcao());
                        Iterator itrCategoriaAtributosTalentoFilhos = lstCategoriaAtributosTalentoFilhos.iterator();
                        while (itrCategoriaAtributosTalentoFilhos.hasNext())
                        {
                            CategoriaAtributoTalento objCategoriaAtributoTalentoFilho = (CategoriaAtributoTalento) itrCategoriaAtributosTalentoFilhos.next();
                            session.setAttribute(String.valueOf(objCategoriaAtributoTalentoFilho.getIdentificador()), lstAtributoTalentoOpcoes);
                        }
                    }

                    // Apaga valorações
                    strValoracoes = "";
                }

                // Adiciona valoração
                if (objAtributoTalentoValorado.getAtributoTalentoOpcao() != null)
                {
                    strValoracoes += objAtributoTalentoValorado.getAtributoTalentoOpcao().getIdentificador() + "@#,#@";
                }
                else if (objAtributoTalentoValorado.getValoracao() != null)
                {
                    strValoracoes += objAtributoTalentoValorado.getValoracao() + "@#,#@";
                }

                // Seta valoração anterior
                objAtributoTalentoValoradoAnterior = objAtributoTalentoValorado;
            }

            // Preenche form                    
            detalheTalentoForm.setAtributoTalentoValorado(String.valueOf(objAtributoTalentoValoradoAnterior.getCategoriaAtributoTalento().getIdentificador()), strValoracoes.split("@#,#@"));

            // Opções (se opção tiver filhos)
            List lstCategoriaAtributosTalentoFilhos = objTalentoFacade.obterCategoriaAtributosTalentoFilhos(objAtributoTalentoValoradoAnterior.getCategoriaAtributoTalento());
            if (lstCategoriaAtributosTalentoFilhos != null && objAtributoTalentoValoradoAnterior.getAtributoTalentoOpcao() != null)
            {
                List lstAtributoTalentoOpcoes = objTalentoFacade.obterOpcoesPeloPaiEAtributoTalento(objAtributoTalentoValoradoAnterior.getAtributoTalentoOpcao(), objAtributoTalentoValoradoAnterior.getAtributoTalentoOpcao().getAtributoTalento());
                Iterator itrCategoriaAtributosTalentoFilhos = lstCategoriaAtributosTalentoFilhos.iterator();
                while (itrCategoriaAtributosTalentoFilhos.hasNext())
                {
                    CategoriaAtributoTalento objCategoriaAtributoTalentoFilho = (CategoriaAtributoTalento) itrCategoriaAtributosTalentoFilhos.next();
                    session.setAttribute(String.valueOf(objCategoriaAtributoTalentoFilho.getIdentificador()), lstAtributoTalentoOpcoes);
                }
            }

            // Título da tabela de detalhe
            session.setAttribute("strTituloTabelaDetalhe", ((MessageResources) this.mapRecursos.get("comum")).getMessage("sigesp.comum.interface.mensagem.alteracao.informacao"));
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
     * Metodo detalheTalentoPrepararInclusao
     */

    public ArrayList detalheTalentoPrepararInclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = this.request.getSession();

        // Instancia Facade
        TalentoFacade objTalentoFacade = new TalentoFacade();

        try
        {
            // Obtém categoria de talento selecionada
            CategoriaTalento objCategoriaTalento = null;
            if (this.request.getParameter("categoriaTalento") != null)
            {
                objCategoriaTalento = objTalentoFacade.obterCategoriaTalentoPelaChave(this.request.getParameter("categoriaTalento"));
                session.setAttribute("objCategoriaTalento", objCategoriaTalento);
            }
            else
            {
                objCategoriaTalento = (CategoriaTalento) session.getAttribute("objCategoriaTalento");
            }

            // Obtém categoria/atributos para inclusão
            List lstCategoriaAtributosTalento = objTalentoFacade.obterCategoriaAtributosTalento(objCategoriaTalento);
            this.request.setAttribute("lstCategoriaAtributosTalento", lstCategoriaAtributosTalento);

            // Obtém opções dos atributos de talento, e verifica quais atributos têm 
            // filhos (na relação categoria/atributo)
            Iterator itrCategoriaAtributosTalento = lstCategoriaAtributosTalento.iterator();
            String strCategoriaAtributosTalentoComFilhos = "";
            while (itrCategoriaAtributosTalento.hasNext())
            {
                CategoriaAtributoTalento objCategoriaAtributoTalento = (CategoriaAtributoTalento) itrCategoriaAtributosTalento.next();

                // Limpa opções anteriores, se necessário
                if (this.request.getParameter("limpar") != null && "true".equals(this.request.getParameter("limpar")))
                {
                    if ("U".equals(objCategoriaAtributoTalento.getAtributoTalento().getTipoHTML().getMultiplicidade())
                            || "M".equals(objCategoriaAtributoTalento.getAtributoTalento().getTipoHTML().getMultiplicidade()))
                    {
                        session.removeAttribute(String.valueOf(objCategoriaAtributoTalento.getIdentificador()));
                    }
                }

                //Trata atributo virtual
                if (GerenciadorAtributoVirtual.isAtributoVirtual(objCategoriaAtributoTalento.getAtributoTalento().getNome()))
                {

                    if (1 == 2)
                    {
                        //Abaixo apenas exemplos de configuracao de atributos
                        /*if(objCategoriaAtributoTalento.getAtributoTalento().getNome().equalsIgnoreCase("Curso Dataprev"))
                        {
                            //Seta como parametro a identificacao do usuario
                            GerenciadorAtributoVirtual.setString(1, String.valueOf(SegurancaWeb.obterSegurancaWeb(this.request.getSession()).obterUsuarioAutenticado().obterIdentificador()));
                            List lstAtributoTalentoOpcoes = GerenciadorAtributoVirtual.obtemValores(objCategoriaAtributoTalento.getAtributoTalento().getNome());
                            //Arrumar as datas
                            Iterator it = lstAtributoTalentoOpcoes.iterator();
                            while(it.hasNext()){

                                AtributoTalentoOpcaoVirtual aux = (AtributoTalentoOpcaoVirtual) it.next();
                                //No identificador vem todas as informacoes da query separadas por *|*
                                //O objetivo aqui é arrumar a data que esta no formato 2005-11-18 00:00:00.0  para 18/11/2005
                                //Pattern: .*[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9] [0-9][0-9]:[0-9][0-9]:[0-9][0-9].[0-9].*
                                if(aux.intIdentificador.matches(".*[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9] [0-9][0-9]:[0-9][0-9]:[0-9][0-9].[0-9].*"))
                                {
                                  //Obtem a posicao da parte de horas da data

                                  SimpleDateFormat formatadorIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                                  SimpleDateFormat formatadorOut = new SimpleDateFormat("dd/MM/yyyy");

                                  Pattern p = Pattern.compile("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9] [0-9][0-9]:[0-9][0-9]:[0-9][0-9].[0-9]");
                                  Matcher mat = p.matcher(aux.intIdentificador);

                                  while(mat.find()){

                                    for (int j = 0; j <= mat.groupCount(); ++j) {
                                      String data = mat.group(j);
                                      //Transforma a data desformatada em um objeto Data
                                      try{
                                        java.util.Date dataIn = formatadorIn.parse(data);
                                        String dataOut = formatadorOut.format(dataIn);
                                        //substitui a data desformatada por uma no formato dd/MM/yyyy
                                        aux.intIdentificador = aux.intIdentificador.replaceAll(data, dataOut);
                                      }
                                      catch(java.text.ParseException e)
                                      {

                                      }

                                    }
                                  }

                                }
                            }
                            session.setAttribute(String.valueOf(objCategoriaAtributoTalento.getIdentificador()), lstAtributoTalentoOpcoes);
                        }
                        else if(objCategoriaAtributoTalento.getAtributoTalento().getNome().equalsIgnoreCase("Instrutoria Dataprev"))
                        {
                            //Seta como parametro a identificacao do usuario
                            GerenciadorAtributoVirtual.setString(1, String.valueOf(SegurancaWeb.obterSegurancaWeb(this.request.getSession()).obterUsuarioAutenticado().obterIdentificador()));
                            List lstAtributoTalentoOpcoes = GerenciadorAtributoVirtual.obtemValores(objCategoriaAtributoTalento.getAtributoTalento().getNome());
                            session.setAttribute(String.valueOf(objCategoriaAtributoTalento.getIdentificador()), lstAtributoTalentoOpcoes);
                        }*/
                    }
                    else
                    {
                        // Tratamento padrão para os atributos virtuais
                        List lstAtributoTalentoOpcoes = GerenciadorAtributoVirtual.obtemValores(objCategoriaAtributoTalento.getAtributoTalento().getNome());
                        session.setAttribute(String.valueOf(objCategoriaAtributoTalento.getIdentificador()), lstAtributoTalentoOpcoes);
                    }

                }

                else
                {
                    // Opções (desde que o atributo não tenha filhos)
                    if ("U".equals(objCategoriaAtributoTalento.getAtributoTalento().getTipoHTML().getMultiplicidade())
                            || "M".equals(objCategoriaAtributoTalento.getAtributoTalento().getTipoHTML().getMultiplicidade()))
                    {
                        if (objCategoriaAtributoTalento.getAtributoTalento().getAtributoTalentoPai() == null)
                        {
                            List lstAtributoTalentoOpcoes = objTalentoFacade.obterOpcoesPorAtributoTalento(objCategoriaAtributoTalento.getAtributoTalento());
                            session.setAttribute(String.valueOf(objCategoriaAtributoTalento.getIdentificador()), lstAtributoTalentoOpcoes);
                        }
                    }

                    // Filhos
                    if ("U".equals(objCategoriaAtributoTalento.getAtributoTalento().getTipoHTML().getMultiplicidade()))
                    {
                        boolean blnExistenciaFilhos = objTalentoFacade.verificarExistenciaCategoriaAtributosFilhos(objCategoriaAtributoTalento);
                        if (blnExistenciaFilhos)
                        {
                            strCategoriaAtributosTalentoComFilhos += objCategoriaAtributoTalento.getIdentificador() + ",";
                        }
                    }
                }
            }

            // Armazena atributos com filho (na relação categoria/atributo)
            this.request.setAttribute("strCategoriaAtributosTalentoComFilhos", strCategoriaAtributosTalentoComFilhos);

            // Título da tabela de detalhe
            if (this.request.getParameter("chave") == null)
            {
                session.setAttribute("strTituloTabelaDetalhe", ((MessageResources) this.mapRecursos.get("comum")).getMessage("sigesp.comum.interface.mensagem.inclusao.informacao"));
            }

            // Verifica se há mensagem de inclusão para ser repassada
            if (this.request.getParameter("mensagemInclusao") != null)
            {
                this.mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", ((MessageResources) this.mapRecursos.get("comum")).getMessage("sigesp.comum.interface.mensagem.inclusao.realizada")));
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
        catch (IOException e)
        {
            CDException cde = new CDException(e.getMessage());
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
     * Metodo encaminhamentoTalentoEfetuarInclusao
     */

    public ArrayList encaminhamentoTalentoEfetuarInclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = this.request.getSession();

        // Obtém o form
        DetalheTalentoForm detalheTalentoForm = (DetalheTalentoForm) this.form;

        // Obtém categoria
        CategoriaTalento objCategoriaTalento = (CategoriaTalento) session.getAttribute("objCategoriaTalento");

        // Instancia Facade
        TalentoFacade objTalentoFacade = new TalentoFacade();

        // Instacia novo POJO
        Talento objTalento = new Talento();

        // Lista para valorações
        List lstAtributosTalentoValorados = new ArrayList();

        try
        {
            // Obtém usuário logado
            String strIdentificador = String.valueOf(SegurancaWeb.obterSegurancaWeb(session).obterUsuarioAutenticado().obterIdentificador());
            Pessoa objPessoa = objTalentoFacade.obterPessoaPelaChave(strIdentificador);

            // Monta objeto de talento
            objTalento.setCategoriaTalento(objCategoriaTalento);
            objTalento.setPessoa(objPessoa);

            // Monta lista de objets de valoração, a partir da leitura do form
            Set setAtributosTalentoValorados = detalheTalentoForm.getKeySetAtributoTalentoValorado();
            Iterator itrAtributosTalentoValorados = setAtributosTalentoValorados.iterator();
            while (itrAtributosTalentoValorados.hasNext())
            {
                String strKeyAtributoTalentoValorado = (String) itrAtributosTalentoValorados.next();
                String[] strAtributosTalentoValorados = detalheTalentoForm.getAtributoTalentoValorado(strKeyAtributoTalentoValorado);
                for (int i = 0; i < strAtributosTalentoValorados.length; i++)
                {
                    AtributoTalentoValorado objAtributoTalentoValorado = new AtributoTalentoValorado();
                    CategoriaAtributoTalento objCategoriaAtributoTalento = new CategoriaAtributoTalento();
                    objCategoriaAtributoTalento.setIdentificador(Integer.valueOf(strKeyAtributoTalentoValorado));
                    objAtributoTalentoValorado.setCategoriaAtributoTalento(objCategoriaAtributoTalento);
                    objAtributoTalentoValorado.setValoracao(strAtributosTalentoValorados[i]);
                    lstAtributosTalentoValorados.add(objAtributoTalentoValorado);
                }
            }

            // Inclui
            objTalentoFacade.incluir(objTalento, lstAtributosTalentoValorados);

            // Verifica se a categoria é perfil 
            if ("S".equals(objCategoriaTalento.getIndicativoUnicidade()))
            {
                this.request.setAttribute("strEncaminhamento", "talento");
            }
            else
            {
                this.request.setAttribute("strEncaminhamento", "detalheTalento");
                this.request.setAttribute("strCategoriaTalento", String.valueOf(objCategoriaTalento.getIdentificador()));
            }

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
     * Metodo exclusaoTalentoPrepararExclusao
     */

    public ArrayList exclusaoTalentoPrepararExclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia Facade
        TalentoFacade objTalentoFacade = new TalentoFacade();

        // Cria bean
        TalentoVisualizacao objTalentoVisualizacao = null;

        // Cria lista para inserção do registro para exclusão
        List lstAtributosTalento = new ArrayList();

        // Obtém chave para preparação de exclusão
        String strChave = this.request.getParameter("chave");

        try
        {
            // Obtém bean para preparação de exclusão
            objTalentoVisualizacao = objTalentoFacade.obterPelaChaveComDescricao(strChave);
            lstAtributosTalento.add(objTalentoVisualizacao);

            // Armazena dados no request
            this.request.setAttribute("lstAtributosTalento", lstAtributosTalento);
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
     * Metodo talentoEfetuarAlteracao
     */

    public ArrayList talentoEfetuarAlteracao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = this.request.getSession();

        // Obtém o form
        DetalheTalentoForm detalheTalentoForm = (DetalheTalentoForm) this.form;

        // Obtém categoria
        CategoriaTalento objCategoriaTalento = (CategoriaTalento) session.getAttribute("objCategoriaTalento");

        // Instancia Facade
        TalentoFacade objTalentoFacade = new TalentoFacade();

        // Instacia novo POJO
        Talento objTalento = new Talento();

        // Lista para valorações
        List lstAtributosTalentoValorados = new ArrayList();

        try
        {
            // Obtém usuário logado
            String strIdentificador = String.valueOf(SegurancaWeb.obterSegurancaWeb(session).obterUsuarioAutenticado().obterIdentificador());
            Pessoa objPessoa = objTalentoFacade.obterPessoaPelaChave(strIdentificador);

            // Monta objeto de talento
            objTalento.setIdentificador(Integer.valueOf(detalheTalentoForm.getIdentificador()));
            objTalento.setCategoriaTalento(objCategoriaTalento);
            objTalento.setPessoa(objPessoa);

            // Monta lista de objets de valoração, a partir da leitura do form
            Set setAtributosTalentoValorados = detalheTalentoForm.getKeySetAtributoTalentoValorado();
            Iterator itrAtributosTalentoValorados = setAtributosTalentoValorados.iterator();
            while (itrAtributosTalentoValorados.hasNext())
            {
                String strKeyAtributoTalentoValorado = (String) itrAtributosTalentoValorados.next();
                String[] strAtributosTalentoValorados = detalheTalentoForm.getAtributoTalentoValorado(strKeyAtributoTalentoValorado);
                for (int i = 0; i < strAtributosTalentoValorados.length; i++)
                {
                    AtributoTalentoValorado objAtributoTalentoValorado = new AtributoTalentoValorado();
                    CategoriaAtributoTalento objCategoriaAtributoTalento = new CategoriaAtributoTalento();
                    objCategoriaAtributoTalento.setIdentificador(Integer.valueOf(strKeyAtributoTalentoValorado));
                    objAtributoTalentoValorado.setCategoriaAtributoTalento(objCategoriaAtributoTalento);
                    objAtributoTalentoValorado.setValoracao(strAtributosTalentoValorados[i]);
                    lstAtributosTalentoValorados.add(objAtributoTalentoValorado);
                }
            }

            // Altera
            objTalentoFacade.alterar(objTalento, lstAtributosTalentoValorados);

            // Verifica se a categoria é perfil 
            if ("S".equals(objCategoriaTalento.getIndicativoUnicidade()))
            {
                this.request.setAttribute("strEncaminhamento", "talento");
            }
            else
            {
                this.request.setAttribute("strEncaminhamento", "detalheTalento");
                this.request.setAttribute("strCategoriaTalento", String.valueOf(objCategoriaTalento.getIdentificador()));
            }

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

        // Mensagens        
        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);
    }

    /**
     * Metodo talentoEfetuarExclusao
     */

    public ArrayList talentoEfetuarExclusao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém o form
        DetalheTalentoForm detalheTalentoForm = (DetalheTalentoForm) this.form;

        // Instancia Facade
        TalentoFacade objTalentoFacade = new TalentoFacade();

        // Instacia novo POJO
        Talento objTalento = new Talento();

        // Obtém chave para preparação de exclusão
        String strChave = this.request.getParameter("chave");

        try
        {
            // Exclui dados
        	
        	UsuarioAutenticado usuario = SegurancaWeb.obterSegurancaWeb(this.request.getSession()).obterUsuarioAutenticado();
        	Pessoa pessoa = new Pessoa();
        	pessoa.setIdentificador(usuario.obterIdentificador());
        	objTalento.setPessoa(pessoa);
        	
            objTalento.setIdentificador(Integer.valueOf(strChave));
            objTalentoFacade.excluir(objTalento);

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
     * Metodo talentoEfetuarFiltro
     */

    public ArrayList talentoEfetuarFiltro()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = this.request.getSession();

        // List para armazenar resultado da consulta
        List lstCategoriasTalento = null;
        List lstTalentos = null;
        String strTotalRegistros = "0";

        // Verifica página
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
        TalentoFacade objTalentoFacade = new TalentoFacade();

        try
        {
            // Obtém usuário logado
            String strIdentificador = String.valueOf(SegurancaWeb.obterSegurancaWeb(session).obterUsuarioAutenticado().obterIdentificador());
            Pessoa objPessoa = objTalentoFacade.obterPessoaPelaChave(strIdentificador);

            // Obtém categorias de talento disponíveis para o usuário
            lstCategoriasTalento = objTalentoFacade.obterCategoriasTalento(objPessoa);
            this.request.setAttribute("lstCategoriasTalento", lstCategoriasTalento);

            // Obtém a categoria de talento selecionada
            CategoriaTalento objCategoriaTalento = null;
            if (this.request.getParameter("categoriaTalento") != null)
            {
                objCategoriaTalento = new CategoriaTalento();
                objCategoriaTalento.setIdentificador(Integer.valueOf(this.request.getParameter("categoriaTalento")));
                session.setAttribute("objCategoriaTalento", objCategoriaTalento);
            }
            else
            {
                objCategoriaTalento = (CategoriaTalento) session.getAttribute("objCategoriaTalento");
            }

            // Obtém dados
            if (objCategoriaTalento.getIdentificador().intValue() > 0)
            {
                lstTalentos = objTalentoFacade.obterPorPessoaCategoriaTalentoPorPagina(objPessoa, objCategoriaTalento, intPagina, 20);
                if (intPagina == 1)
                {
                    strTotalRegistros = String.valueOf(objTalentoFacade.obterTotalRegistrosPorPessoaCategoriaTalento(objPessoa, objCategoriaTalento));
                    session.setAttribute("strTotalRegistros", strTotalRegistros);
                }
            }
            else
            {
                lstTalentos = objTalentoFacade.obterPorPessoaPorPagina(objPessoa, intPagina, 20);
                if (intPagina == 1)
                {
                    strTotalRegistros = String.valueOf(objTalentoFacade.obterTotalRegistrosPorPessoa(objPessoa));
                    session.setAttribute("strTotalRegistros", strTotalRegistros);
                }
            }
            this.request.setAttribute("lstTalentos", lstTalentos);
            this.request.setAttribute("strTotalRegistros", strTotalRegistros);
            this.request.setAttribute("strPaginacao", "talentoEfetuarFiltro.do");
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
     * Metodo talentoPrepararVisualizacao
     */

    public ArrayList talentoPrepararVisualizacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = this.request.getSession();

        // List para armazenar resultado da consulta
        List lstCategoriasTalento = null;
        List lstTalentos = null;
        String strTotalRegistros = "0";

        // Verifica página
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
        TalentoFacade objTalentoFacade = new TalentoFacade();

        try
        {
            // Obtém usuário logado
            String strIdentificador = String.valueOf(SegurancaWeb.obterSegurancaWeb(session).obterUsuarioAutenticado().obterIdentificador());
            Pessoa objPessoa = objTalentoFacade.obterPessoaPelaChave(strIdentificador);
            if (objPessoa != null)
            {

                // Obtém categorias de talento disponíveis para o usuário
                lstCategoriasTalento = objTalentoFacade.obterCategoriasTalento(objPessoa);
                this.request.setAttribute("lstCategoriasTalento", lstCategoriasTalento);

                // Obtém dados
                lstTalentos = objTalentoFacade.obterPorPessoaPorPagina(objPessoa, intPagina, 20);
                if (intPagina == 1)
                {
                    strTotalRegistros = String.valueOf(objTalentoFacade.obterTotalRegistrosPorPessoa(objPessoa));
                    session.setAttribute("strTotalRegistros", strTotalRegistros);
                }
                this.request.setAttribute("lstTalentos", lstTalentos);
                this.request.setAttribute("strTotalRegistros", strTotalRegistros);
                this.request.setAttribute("strPaginacao", "talentoPrepararVisualizacao.do");

                // Verifica se há mensagem de inclusão para ser repassada
                if (this.request.getParameter("mensagemInclusao") != null)
                {
                    this.mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", ((MessageResources) this.mapRecursos.get("comum")).getMessage("sigesp.comum.interface.mensagem.inclusao.realizada")));
                }
            }
            else
            {
                this.erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", "Os dados do usuário não foram encontrados"));
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

}

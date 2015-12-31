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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryParser.ParseException;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import sigesp.comum.util.struts.SigespSequencia;
import sigesp.comum.util.struts.actions.ActionMessagesUtil;
import br.gov.camara.biblioteca.util.DataNova;
import br.gov.camara.biblioteca.util.Numero;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.bancotalentos.facade.ConsultaTalentoFacade;
import br.gov.camara.negocio.bancotalentos.facade.CurriculoFacade;
import br.gov.camara.negocio.bancotalentos.facade.TalentoFacade;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalentoOpcao;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaAtributoTalento;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaTalento;
import br.gov.camara.negocio.bancotalentos.pojo.ConsultaTalento;
import br.gov.camara.negocio.bancotalentos.pojo.CriterioConsultaTalento;
import br.gov.camara.negocio.bancotalentos.pojo.TipoConsultaTalento;
import br.gov.camara.negocio.bancotalentos.util.GerenciadorAtributoVirtual;
import br.gov.camara.negocio.bancotalentos.util.SearchTalento;
import br.gov.camara.negocio.comum.pojo.TipoHTML;
import br.gov.camara.negocio.exception.NegocioException;
import br.gov.camara.seguranca.SegurancaWeb;
import br.gov.camara.seguranca.UsuarioAutenticado;
import br.gov.camara.util.aplicacao.AplicacaoPlugIn;
import br.gov.camara.util.relatorio.ExibicaoDocumento;
import br.gov.camara.util.relatorio.RelatorioFactory;
import br.gov.camara.util.relatorio.exception.RelatorioException;
import br.gov.camara.util.relatorio.jasper.RelatorioJasper;
import br.gov.camara.visao.bancotalentosgestao.form.ConsultaTalentoForm;

/**
 * Sequencia ConsultaTalentoSequencia
 */

public final class ConsultaTalentoSequencia extends SigespSequencia
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(ConsultaTalentoSequencia.class);

    /**
     * Metodo construtor
     *
     * @param mapping Mapeamento executado
     * @param form FormulÃ¡rio associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que jÃ¡ ocorreram
     * @param mensagens Colecao de mensagens a serem exibidas
     * @param ConsultaTalentoAction Classe Action associada
     */

    public ConsultaTalentoSequencia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages erros,
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
     * Metodo consultaTalentoAdicionarCriterio
     */

    public ArrayList consultaTalentoAdicionarCriterio()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = this.request.getSession();

        // Obtém form
        ConsultaTalentoForm consultaTalentoForm = (ConsultaTalentoForm) this.form;

        // Instancia Facade
        ConsultaTalentoFacade objConsultaTalentoFacade = new ConsultaTalentoFacade();

        try
        {
            // Objeto de consulta
            CategoriaAtributoTalento objCategoriaAtributoTalento = null;

            // Mapa de consultas de talento
            Map mapConsultasTalento = null;

            // Obtém map de consulta
            mapConsultasTalento = (Map) session.getAttribute("mapConsultasTalento");

            // Lista de consultas válidas
            ConsultaTalento consultaTalento = new ConsultaTalento();

            // Categoria do Talento
            CategoriaTalento objCategoriaTalento = objConsultaTalentoFacade.obterCategoriaTalentoPelaChave(consultaTalentoForm.getCategoriaTalento());
            if (objCategoriaTalento != null)
            {
                consultaTalento.setCategoriaTalento(objCategoriaTalento);
            }
            else
            {
                throw new NegocioException("Não foi possível recuperar os dados da categoria selecionada.");
            }

            // Obtém tipo de consulta
            TipoConsultaTalento objTipoConsultaTalento = new TipoConsultaTalento();
            objTipoConsultaTalento.setIdentificador(Integer.valueOf(consultaTalentoForm.getTipoConsulta()));
            if (objTipoConsultaTalento.getIdentificador().equals(new Integer(1)))
            {
                if (objCategoriaTalento.getIdentificador().intValue() >= 0)
                {
                    objTipoConsultaTalento.setDescricao("Preencheu");
                }
                else
                {
                    objTipoConsultaTalento.setDescricao("Possui");
                }
            }
            else if (objTipoConsultaTalento.getIdentificador().equals(new Integer(2)))
            {
                if (objCategoriaTalento.getIdentificador().intValue() >= 0)
                {
                    objTipoConsultaTalento.setDescricao("Não preencheu");
                }
                else
                {
                    objTipoConsultaTalento.setDescricao("Não possui");
                }
            }
            consultaTalento.setTipoConsultaTalento(objTipoConsultaTalento);

            // Obtém critério de consulta
            CriterioConsultaTalento objCriterioConsultaTalento = new CriterioConsultaTalento();
            objCriterioConsultaTalento.setIdentificador(Integer.valueOf(consultaTalentoForm.getCriterioConsulta()));
            if (objCriterioConsultaTalento.getIdentificador().equals(new Integer(1)))
            {
                objCriterioConsultaTalento.setDescricao("E");
            }
            else if (objCriterioConsultaTalento.getIdentificador().equals(new Integer(2)))
            {
                objCriterioConsultaTalento.setDescricao("OU");
            }
            consultaTalento.setCriterioConsultaTalento(objCriterioConsultaTalento);

            // Itera valorações da consulta
            Iterator itrAtributosTalentoValorados = consultaTalentoForm.getKeySetAtributoTalentoValorado().iterator();
            while (itrAtributosTalentoValorados.hasNext())
            {
                // Obtém a categoria/atributo a ser tratada
                String strCategoriaAtributoTalentoConsulta = (String) itrAtributosTalentoValorados.next();

                // Verifica se não é um intervalo (a valoração complementar é tratada junto com sua respectiva valoração)
                if (strCategoriaAtributoTalentoConsulta.indexOf("_") < 0)
                {
                    String strAtributoTalentoValorado[] = consultaTalentoForm.getAtributoTalentoValorado(strCategoriaAtributoTalentoConsulta);
                    String strAtributoTalentoValoradoComplementar[] = consultaTalentoForm.getAtributoTalentoValorado(strCategoriaAtributoTalentoConsulta
                            + "_complementar");

                    for (int i = 0; i < strAtributoTalentoValorado.length; i++)
                    {
                        if ((strAtributoTalentoValorado != null && !"".equals(strAtributoTalentoValorado[i]))
                                || (strAtributoTalentoValoradoComplementar != null && !"".equals(strAtributoTalentoValoradoComplementar[i])))
                        {
                            // Monta objeto de consulta
                            objCategoriaAtributoTalento = objConsultaTalentoFacade.obterCategoriaAtributoTalentoPelaChave(strCategoriaAtributoTalentoConsulta);

                            AtributoTalentoOpcao objAtributoTalentoOpcao = new AtributoTalentoOpcao();

                            //Trata atributo virtual
                            if (GerenciadorAtributoVirtual.isAtributoVirtual(objCategoriaAtributoTalento.getAtributoTalento().getNome()))
                            {
                                objAtributoTalentoOpcao.setDescricao(strAtributoTalentoValorado[i]);
                            }
                            else if (Integer.parseInt(strCategoriaAtributoTalentoConsulta) > 0 // Não é um atributo na categoria DADOS PESSOAIS
                                    && !"L".equals(objCategoriaAtributoTalento.getAtributoTalento().getTipoHTML().getMultiplicidade()))
                            {
                                if (!"".equals(consultaTalentoForm.getAtributoTalentoValorado(strCategoriaAtributoTalentoConsulta)))
                                {
                                    objAtributoTalentoOpcao = objConsultaTalentoFacade.obterAtributoTalentoOpcaoPelaChave(strAtributoTalentoValorado[i]);
                                }
                            }
                            else
                            {
                                if (objCategoriaAtributoTalento.getAtributoTalento().getIdentificador().equals(ConsultaTalentoFacade.ID_ATRIBUTO_SEXO))
                                {
                                    objAtributoTalentoOpcao = objConsultaTalentoFacade.obterAtributoTalentoOpcaoPelaChave(strAtributoTalentoValorado[i]);
                                }
                                else
                                {
                                    objAtributoTalentoOpcao.setDescricao(strAtributoTalentoValorado[i]);
                                }
                            }

                            String strOpcaoComplementar = null;
                            if (strAtributoTalentoValoradoComplementar != null)
                            {
                                // Obtém o tipo de dado do campo e verifica se está válido                                        
                                if ("D".equals(objCategoriaAtributoTalento.getAtributoTalento().getTipoDado()))
                                {
                                    if ((!"".equals(strAtributoTalentoValorado[i]) && !DataNova.validarData(strAtributoTalentoValorado[i]))
                                            || (!"".equals(strAtributoTalentoValoradoComplementar[i]) && !DataNova.validarData(strAtributoTalentoValoradoComplementar[i])))
                                    {
                                        throw new NegocioException("A data informada é inválida");
                                    }
                                }
                                if ("N".equals(objCategoriaAtributoTalento.getAtributoTalento().getTipoDado()))
                                {
                                    if ((!"".equals(strAtributoTalentoValorado[i]) && !Numero.validarNumero(strAtributoTalentoValorado[i]))
                                            || (!"".equals(strAtributoTalentoValoradoComplementar[i]) && !Numero.validarNumero(strAtributoTalentoValoradoComplementar[i])))
                                    {
                                        throw new NegocioException("O número informado é inválido");
                                    }
                                }

                                // Adiciona valoração
                                strOpcaoComplementar = strAtributoTalentoValoradoComplementar[i];
                            }

                            // Adiciona consulta à lista de consultas válidas
                            consultaTalento.addParametroConsulta(objCategoriaAtributoTalento, objAtributoTalentoOpcao, strOpcaoComplementar);
                        }
                    }
                }
            }

            // Verifica se houve critério válido
            if (consultaTalento != null)
            {
                if (consultaTalento.getListaParametrosConsulta() == null && consultaTalento.getCategoriaTalento().getIdentificador().intValue() == -1)
                {
                    throw new NegocioException("A categoria 'Dados Pessoais' requer que pelo menos um atributo seja preenchido.");
                }

                // Verifica se o contador existe na sessão
                long lngTempContadorCriterio = 0;
                NumberFormat nfmNumero = new DecimalFormat("00000");
                if (session.getAttribute("strContadorCriterio") != null)
                {
                    lngTempContadorCriterio = Long.parseLong((String) session.getAttribute("strContadorCriterio"));
                }

                // Armazena na sessão
                String strContadorCriterio = nfmNumero.format(++lngTempContadorCriterio);
                session.setAttribute("strContadorCriterio", strContadorCriterio);

                // Adiciona descrição da categoria
                session.setAttribute("objCategoriaTalento" + strContadorCriterio, objConsultaTalentoFacade.obterCategoriaTalentoPelaChave(consultaTalentoForm.getCategoriaTalento()));

                // Adiciona ao map
                mapConsultasTalento.put(strContadorCriterio, consultaTalento);

                // Mensagem
                this.mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", "Critério incluído com sucesso"));
            }
            else
            {
                this.erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", "Não foram informados critérios válidos"));
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
        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);
    }

    /**
     * Metodo consultaTalentoBuscarHierarquia
     */

    public ArrayList consultaTalentoBuscarHierarquia()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = this.request.getSession();

        // Obtém form
        ConsultaTalentoForm consultaTalentoForm = (ConsultaTalentoForm) this.form;

        // Instancia Facade
        TalentoFacade objConsultaTalentoFacade = new TalentoFacade();

        try
        {
            // Obtém categoria de talento 
            CategoriaTalento objCategoriaTalento = (CategoriaTalento) session.getAttribute("objCategoriaTalento");

            // Obtém opção de talento a partir da chave recebida
            AtributoTalentoOpcao objAtributoTalentoOpcao = null;
            objAtributoTalentoOpcao = objConsultaTalentoFacade.obterAtributoTalentoOpcaoPelaChave(this.request.getParameter("atributoTalentoOpcao"));

            // Obtém hierarquia superior da opção recebida
            List lstAtributoTalentoOpcaoHierarquiaSuperior = objConsultaTalentoFacade.obterAtributoTalentoOpcaoHierarquiaSuperior(objAtributoTalentoOpcao);

            // Seta categoria/atributo para buscar opção
            CategoriaAtributoTalento objCategoriaAtributoTalento = objConsultaTalentoFacade.obterCategoriaAtributoTalentoPorCategoriaTalentoAtributoTalento(objCategoriaTalento, objAtributoTalentoOpcao.getAtributoTalento());
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
                CategoriaAtributoTalento objCategoriaAtributoTalentoHierarquiaSuperior = objConsultaTalentoFacade.obterCategoriaAtributoTalentoPorCategoriaTalentoAtributoTalento(objCategoriaTalento, objAtributoTalentoOpcaoHierarquiaSuperior.getAtributoTalento());

                // Opções 
                if (objAtributoTalentoOpcaoHierarquiaSuperior.getAtributoTalentoOpcaoPai() != null)
                {
                    List lstAtributoTalentoOpcoes = objConsultaTalentoFacade.obterOpcoesPeloPaiEAtributoTalento(objAtributoTalentoOpcaoHierarquiaSuperior.getAtributoTalentoOpcaoPai(), objAtributoTalentoOpcaoHierarquiaSuperior.getAtributoTalento());
                    session.setAttribute(String.valueOf(objCategoriaAtributoTalentoHierarquiaSuperior.getIdentificador()), lstAtributoTalentoOpcoes);
                }

                // Seta form
                consultaTalentoForm.setAtributoTalentoValorado(String.valueOf(objCategoriaAtributoTalentoHierarquiaSuperior.getIdentificador()), new String[] { String.valueOf(objAtributoTalentoOpcaoHierarquiaSuperior.getIdentificador()) });
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
     * Metodo consultaTalentoBuscarOpcao
     */

    public ArrayList consultaTalentoBuscarOpcao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = this.request.getSession();

        // Obtém form
        ConsultaTalentoForm detalheTalentoForm = (ConsultaTalentoForm) this.form;
        // Instancia Facade
        ConsultaTalentoFacade objConsultaTalentoFacade = new ConsultaTalentoFacade();

        try
        {
            // Obtém categoria/atributo de talento a partir da chave recebida
            CategoriaAtributoTalento objCategoriaAtributoTalento = null;
            if (this.request.getParameter("categoriaAtributoTalento") != null && !"".equals(this.request.getParameter("categoriaAtributoTalento")))
            {
                objCategoriaAtributoTalento = objConsultaTalentoFacade.obterCategoriaAtributoTalentoPelaChave(this.request.getParameter("categoriaAtributoTalento"));
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
                List lstCategoriaAtributoTalentoHierarquiaInferior = objConsultaTalentoFacade.obterCategoriaAtributoTalentoHierarquiaInferior(objCategoriaAtributoTalento);
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
                List lstCategoriaAtributosTalentoFilhos = objConsultaTalentoFacade.obterCategoriaAtributosTalentoFilhos(objCategoriaAtributoTalento);

                // Obtém opções dos atributos de talento (na relação categoria/atributo)
                Iterator itrCategoriaAtributosTalentoFilhos = lstCategoriaAtributosTalentoFilhos.iterator();
                while (itrCategoriaAtributosTalentoFilhos.hasNext())
                {
                    CategoriaAtributoTalento objCategoriaAtributoTalentoFilho = (CategoriaAtributoTalento) itrCategoriaAtributosTalentoFilhos.next();

                    // Opções 
                    if ("U".equals(objCategoriaAtributoTalentoFilho.getAtributoTalento().getTipoHTML().getMultiplicidade())
                            || "M".equals(objCategoriaAtributoTalentoFilho.getAtributoTalento().getTipoHTML().getMultiplicidade()))
                    {
                        List lstAtributoTalentoOpcoes = objConsultaTalentoFacade.obterOpcoesPeloPaiEAtributoTalento(objAtributoTalentoOpcao, objCategoriaAtributoTalentoFilho.getAtributoTalento());
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
     * Metodo consultaTalentoPrepararConsulta
     */

    public ArrayList consultaTalentoPrepararConsulta()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = this.request.getSession();

        // Obtém form
        ConsultaTalentoForm consultaTalentoForm = (ConsultaTalentoForm) this.form;

        // Instancia Facade
        ConsultaTalentoFacade objConsultaTalentoFacade = new ConsultaTalentoFacade();

        try
        {
            // Verifica se houve seleção de categoria de talento
            if (consultaTalentoForm.getCategoriaTalento() != null && !"".equals(consultaTalentoForm.getCategoriaTalento()))
            {
                // Armazena categoria de talento na sessão
                CategoriaTalento objCategoriaTalento = objConsultaTalentoFacade.obterCategoriaTalentoPelaChave(consultaTalentoForm.getCategoriaTalento());
                session.setAttribute("objCategoriaTalento", objCategoriaTalento);

                // Obtém atributos de talento para consulta
                List lstCategoriaAtributosTalentoConsulta = objConsultaTalentoFacade.obterCategoriaAtributosTalento(objCategoriaTalento);
                this.request.setAttribute("lstCategoriaAtributosTalentoConsulta", lstCategoriaAtributosTalentoConsulta);

                // Obtém opções dos atributos de talento, e verifica quais atributos têm 
                // filhos (na relação categoria/atributo)
                Iterator itrCategoriaAtributosTalentoConsulta = lstCategoriaAtributosTalentoConsulta.iterator();
                String strCategoriaAtributosTalentoConsultaComFilhos = "";
                while (itrCategoriaAtributosTalentoConsulta.hasNext())
                {
                    CategoriaAtributoTalento objCategoriaAtributoTalento = (CategoriaAtributoTalento) itrCategoriaAtributosTalentoConsulta.next();

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
                        //Passo o tipo HTML do atributo para Text pois as consultas por um atributo virtual sempre serão em texto puro
                        TipoHTML tipoHtml = new TipoHTML();
                        tipoHtml.setDescricao("TEXT");
                        objCategoriaAtributoTalento.getAtributoTalento().setTipoHTML(tipoHtml);
                        //Nao deve ter opcoes pois a consulta por um atributo virtual sera um campo texto descritivo
                        List lstAtributoTalentoOpcoes = null;
                        session.setAttribute(String.valueOf(objCategoriaAtributoTalento.getIdentificador()), lstAtributoTalentoOpcoes);
                    }
                    else
                    {

                        // Opções (desde que o atributo não tenha filhos)
                        if ("U".equals(objCategoriaAtributoTalento.getAtributoTalento().getTipoHTML().getMultiplicidade())
                                || "M".equals(objCategoriaAtributoTalento.getAtributoTalento().getTipoHTML().getMultiplicidade()))
                        {
                            if (objCategoriaAtributoTalento.getAtributoTalento().getAtributoTalentoPai() == null)
                            {
                                List lstAtributoTalentoOpcoes = objConsultaTalentoFacade.obterOpcoesPorAtributoTalento(objCategoriaAtributoTalento.getAtributoTalento());
                                session.setAttribute(String.valueOf(objCategoriaAtributoTalento.getIdentificador()), lstAtributoTalentoOpcoes);
                            }
                        }

                        // Filhos
                        if ("U".equals(objCategoriaAtributoTalento.getAtributoTalento().getTipoHTML().getMultiplicidade()))
                        {
                            boolean blnExistenciaFilhos = objConsultaTalentoFacade.verificarExistenciaCategoriaAtributosFilhos(objCategoriaAtributoTalento);
                            if (blnExistenciaFilhos)
                            {
                                strCategoriaAtributosTalentoConsultaComFilhos += objCategoriaAtributoTalento.getIdentificador() + ",";
                            }
                        }
                    }
                }

                // Armazena atributos com filho (na relação categoria/atributo)
                this.request.setAttribute("strCategoriaAtributosTalentoConsultaComFilhos", strCategoriaAtributosTalentoConsultaComFilhos);

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

        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);
    }

    /**
     * Metodo consultaTalentoPrepararVisualizacao
     */

    public ArrayList consultaTalentoPrepararVisualizacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = this.request.getSession();

        // Instancia Facade
        ConsultaTalentoFacade objConsultaTalentoFacade = new ConsultaTalentoFacade();
        Map mapConsultasTalento = null;

        try
        {
            // Obtém categorias de talento para consulta
            List lstCategoriasTalentoConsulta = objConsultaTalentoFacade.obterCategoriasTalento();
            this.request.setAttribute("lstCategoriasTalentoConsulta", lstCategoriasTalentoConsulta);

            // Obtém map de consulta
            mapConsultasTalento = (Map) session.getAttribute("mapConsultasTalento");
            String criterioConsultaLucene = (String) session.getAttribute("criterioConsultaLucene");

            if ((mapConsultasTalento == null) || (this.request.getParameter("limpar") == null) || ("true".equals(this.request.getParameter("limpar"))))
            {
                mapConsultasTalento = new TreeMap();
                session.setAttribute("strTotalPessoasComTalentos", String.valueOf(objConsultaTalentoFacade.obterTotalPessoasComTalentos()));
            }
            else
            {
                session.removeAttribute("strTotalPessoasComTalentos");
            }
            session.setAttribute("mapConsultasTalento", mapConsultasTalento);

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
     * Metodo consultaTalentoRemoverCriterio
     */

    public ArrayList consultaTalentoRemoverCriterio()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Declarações
        HttpSession session = this.request.getSession();

        Map mapConsultasTalento = null;

        // Obtém map
        mapConsultasTalento = (Map) session.getAttribute("mapConsultasTalento");

        // Remove do map
        mapConsultasTalento.remove(this.request.getParameter("key"));

        // Remove descrição da categoria
        session.removeAttribute("strDesCategoria" + this.request.getParameter("key"));

        // Mensagem
        this.mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", "Critério removido com sucesso"));

        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);
    }

    /**
     * Metodo resultadoConsultaTalentoEfetuarConsulta
     */

    public ArrayList resultadoConsultaTalentoEfetuarConsulta()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém sessão
        HttpSession session = this.request.getSession();

        // Instancia Facade
        ConsultaTalentoFacade objConsultaTalentoFacade = new ConsultaTalentoFacade();
        Map mapConsultasTalento = null;

        try
        {
            // Verifica página

            if (this.request.getParameter("pagina") == null)
            {
                // Obtém usuário logado
                //String strIdentificador = String.valueOf(SegurancaWeb.obterSegurancaWeb(session).obterUsuarioAutenticado().obterIdentificador());
                UsuarioAutenticado objPessoa = SegurancaWeb.obterSegurancaWeb(session).obterUsuarioAutenticado();

                // Obtém parâmetros de configuração para a consulta
                StringBuffer stbTotalRegistros = new StringBuffer();
                mapConsultasTalento = (Map) session.getAttribute("mapConsultasTalento");
                String criterioConsultaLucene = (String) session.getAttribute("criterioConsultaLucene");
                boolean blnFiltroGrupo = false;
                String strFiltrarConsultaPorGrupo = AplicacaoPlugIn.obterConfiguracao("FILTRAR_CONSULTA_POR_GRUPO");
                if ("sim".equalsIgnoreCase(strFiltrarConsultaPorGrupo))
                {
                    blnFiltroGrupo = true;
                }
                else if (!"nao".equalsIgnoreCase(strFiltrarConsultaPorGrupo) && !"não".equalsIgnoreCase(strFiltrarConsultaPorGrupo))
                {
                    log.error("O arquivo BancoTalentosGestao-aplicacao.xml deve conter a entrada FILTRAR_CONSULTA_POR_GRUPO com a opção 'sim' ou 'não' (sem aspas).");
                    throw new NegocioException("Não foi especificado se as consultas devem ou não ser filtradas por grupo");
                }
                int intMaximoRegistros = Integer.parseInt(AplicacaoPlugIn.obterConfiguracao("MAXIMO_REGISTROS_CONSULTA_TALENTOS"));

                List lstConsultaTalentos = new ArrayList();

                if (this.request.getParameter("criterioConsultaLucene") != null && !"".equals(this.request.getParameter("criterioConsultaLucene")))
                {
                    criterioConsultaLucene = this.request.getParameter("criterioConsultaLucene");
                }

                //Efetua consulta Lucene
                if (criterioConsultaLucene != null && !"".equals(criterioConsultaLucene))
                {
                    try
                    {
                        SearchTalento search = SearchTalento.getInstance();
                        lstConsultaTalentos = search.performSearch(criterioConsultaLucene);
                    }
                    catch (IOException e)
                    {
                        throw new CDException(e);
                    }
                    catch (ParseException e)
                    {
                        throw new CDException(e);
                    }
                }
                else if (mapConsultasTalento != null && !mapConsultasTalento.isEmpty())
                {// Efetua consulta padrao do BT
                    try
                    {
                        lstConsultaTalentos = (objConsultaTalentoFacade.consultar(mapConsultasTalento, objPessoa, intMaximoRegistros, stbTotalRegistros, blnFiltroGrupo));
                    }
                    catch (Exception e)
                    {
                        throw new CDException(e.getMessage());
                    }
                }

                // Consulta talentos
                if (lstConsultaTalentos != null && !lstConsultaTalentos.isEmpty())
                {
                    session.setAttribute("lstConsultaTalentos", lstConsultaTalentos);
                    session.setAttribute("strTotalRegistros", "" + lstConsultaTalentos.size());
                    session.setAttribute("criterioConsultaLucene", this.request.getParameter("criterioConsultaLucene"));

                    // Insere total de registros
                    session.setAttribute("stbTotalRegistros", stbTotalRegistros);
                }
                else
                {
                    session.setAttribute("lstConsultaTalentos", new ArrayList());
                    // session.removeAttribute("lstConsultaTalentos");
                    session.removeAttribute("strTotalRegistros");
                    session.removeAttribute("criterioConsultaLucene");
                    session.removeAttribute("stbTotalRegistros");
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

        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);
    }

    /**
     * Metodo resultadoConsultaTalentoGerarRelatorio
     */

    public ArrayList resultadoConsultaTalentoGerarRelatorio()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Obtém form
        //ResultadoConsultaTalentoForm objForm = (ResultadoConsultaTalentoForm) this.form;

        // Instancia Facade
        CurriculoFacade objCurriculoFacade = new CurriculoFacade();

        String strIdePessoa[] = ((String) this.request.getParameter("curriculos")).split(",");

        if (strIdePessoa != null)
        {
            List lstRelatorio = null;
            try
            {
                // Obtem lista de servidores
                lstRelatorio = objCurriculoFacade.obterCurriculos(strIdePessoa);

                // Obtém relatório
                RelatorioJasper relatorioPrincipal = (RelatorioJasper) RelatorioFactory.obterJasper((String) this.action.getServlet().getInitParameter("relatorioBancoTalentosGestao"), "Curriculo");

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
        }
        else
        {
            this.erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", "Marque pelo menos um currículo para ser impresso"));
        }

        // Mensagens
        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);
    }

}

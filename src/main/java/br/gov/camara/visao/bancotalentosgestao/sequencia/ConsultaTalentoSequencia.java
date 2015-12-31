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
     * @param form Formulário associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que já ocorreram
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

        // Obt�m sess�o
        HttpSession session = this.request.getSession();

        // Obt�m form
        ConsultaTalentoForm consultaTalentoForm = (ConsultaTalentoForm) this.form;

        // Instancia Facade
        ConsultaTalentoFacade objConsultaTalentoFacade = new ConsultaTalentoFacade();

        try
        {
            // Objeto de consulta
            CategoriaAtributoTalento objCategoriaAtributoTalento = null;

            // Mapa de consultas de talento
            Map mapConsultasTalento = null;

            // Obt�m map de consulta
            mapConsultasTalento = (Map) session.getAttribute("mapConsultasTalento");

            // Lista de consultas v�lidas
            ConsultaTalento consultaTalento = new ConsultaTalento();

            // Categoria do Talento
            CategoriaTalento objCategoriaTalento = objConsultaTalentoFacade.obterCategoriaTalentoPelaChave(consultaTalentoForm.getCategoriaTalento());
            if (objCategoriaTalento != null)
            {
                consultaTalento.setCategoriaTalento(objCategoriaTalento);
            }
            else
            {
                throw new NegocioException("N�o foi poss�vel recuperar os dados da categoria selecionada.");
            }

            // Obt�m tipo de consulta
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
                    objTipoConsultaTalento.setDescricao("N�o preencheu");
                }
                else
                {
                    objTipoConsultaTalento.setDescricao("N�o possui");
                }
            }
            consultaTalento.setTipoConsultaTalento(objTipoConsultaTalento);

            // Obt�m crit�rio de consulta
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

            // Itera valora��es da consulta
            Iterator itrAtributosTalentoValorados = consultaTalentoForm.getKeySetAtributoTalentoValorado().iterator();
            while (itrAtributosTalentoValorados.hasNext())
            {
                // Obt�m a categoria/atributo a ser tratada
                String strCategoriaAtributoTalentoConsulta = (String) itrAtributosTalentoValorados.next();

                // Verifica se n�o � um intervalo (a valora��o complementar � tratada junto com sua respectiva valora��o)
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
                            else if (Integer.parseInt(strCategoriaAtributoTalentoConsulta) > 0 // N�o � um atributo na categoria DADOS PESSOAIS
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
                                // Obt�m o tipo de dado do campo e verifica se est� v�lido                                        
                                if ("D".equals(objCategoriaAtributoTalento.getAtributoTalento().getTipoDado()))
                                {
                                    if ((!"".equals(strAtributoTalentoValorado[i]) && !DataNova.validarData(strAtributoTalentoValorado[i]))
                                            || (!"".equals(strAtributoTalentoValoradoComplementar[i]) && !DataNova.validarData(strAtributoTalentoValoradoComplementar[i])))
                                    {
                                        throw new NegocioException("A data informada � inv�lida");
                                    }
                                }
                                if ("N".equals(objCategoriaAtributoTalento.getAtributoTalento().getTipoDado()))
                                {
                                    if ((!"".equals(strAtributoTalentoValorado[i]) && !Numero.validarNumero(strAtributoTalentoValorado[i]))
                                            || (!"".equals(strAtributoTalentoValoradoComplementar[i]) && !Numero.validarNumero(strAtributoTalentoValoradoComplementar[i])))
                                    {
                                        throw new NegocioException("O n�mero informado � inv�lido");
                                    }
                                }

                                // Adiciona valora��o
                                strOpcaoComplementar = strAtributoTalentoValoradoComplementar[i];
                            }

                            // Adiciona consulta � lista de consultas v�lidas
                            consultaTalento.addParametroConsulta(objCategoriaAtributoTalento, objAtributoTalentoOpcao, strOpcaoComplementar);
                        }
                    }
                }
            }

            // Verifica se houve crit�rio v�lido
            if (consultaTalento != null)
            {
                if (consultaTalento.getListaParametrosConsulta() == null && consultaTalento.getCategoriaTalento().getIdentificador().intValue() == -1)
                {
                    throw new NegocioException("A categoria 'Dados Pessoais' requer que pelo menos um atributo seja preenchido.");
                }

                // Verifica se o contador existe na sess�o
                long lngTempContadorCriterio = 0;
                NumberFormat nfmNumero = new DecimalFormat("00000");
                if (session.getAttribute("strContadorCriterio") != null)
                {
                    lngTempContadorCriterio = Long.parseLong((String) session.getAttribute("strContadorCriterio"));
                }

                // Armazena na sess�o
                String strContadorCriterio = nfmNumero.format(++lngTempContadorCriterio);
                session.setAttribute("strContadorCriterio", strContadorCriterio);

                // Adiciona descri��o da categoria
                session.setAttribute("objCategoriaTalento" + strContadorCriterio, objConsultaTalentoFacade.obterCategoriaTalentoPelaChave(consultaTalentoForm.getCategoriaTalento()));

                // Adiciona ao map
                mapConsultasTalento.put(strContadorCriterio, consultaTalento);

                // Mensagem
                this.mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", "Crit�rio inclu�do com sucesso"));
            }
            else
            {
                this.erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", "N�o foram informados crit�rios v�lidos"));
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

        // Obt�m sess�o
        HttpSession session = this.request.getSession();

        // Obt�m form
        ConsultaTalentoForm consultaTalentoForm = (ConsultaTalentoForm) this.form;

        // Instancia Facade
        TalentoFacade objConsultaTalentoFacade = new TalentoFacade();

        try
        {
            // Obt�m categoria de talento 
            CategoriaTalento objCategoriaTalento = (CategoriaTalento) session.getAttribute("objCategoriaTalento");

            // Obt�m op��o de talento a partir da chave recebida
            AtributoTalentoOpcao objAtributoTalentoOpcao = null;
            objAtributoTalentoOpcao = objConsultaTalentoFacade.obterAtributoTalentoOpcaoPelaChave(this.request.getParameter("atributoTalentoOpcao"));

            // Obt�m hierarquia superior da op��o recebida
            List lstAtributoTalentoOpcaoHierarquiaSuperior = objConsultaTalentoFacade.obterAtributoTalentoOpcaoHierarquiaSuperior(objAtributoTalentoOpcao);

            // Seta categoria/atributo para buscar op��o
            CategoriaAtributoTalento objCategoriaAtributoTalento = objConsultaTalentoFacade.obterCategoriaAtributoTalentoPorCategoriaTalentoAtributoTalento(objCategoriaTalento, objAtributoTalentoOpcao.getAtributoTalento());
            this.request.setAttribute("objCategoriaAtributoTalento", objCategoriaAtributoTalento);

            // Seta op��o para buscar op��o
            this.request.setAttribute("objAtributoTalentoOpcao", objAtributoTalentoOpcao);

            // Obt�m op��es dos atributos de talento (na rela��o categoria/atributo)
            Iterator itrAtributoTalentoOpcaoHierarquiaSuperior = lstAtributoTalentoOpcaoHierarquiaSuperior.iterator();
            while (itrAtributoTalentoOpcaoHierarquiaSuperior.hasNext())
            {
                // Obt�m op��o da hierarquia
                AtributoTalentoOpcao objAtributoTalentoOpcaoHierarquiaSuperior = (AtributoTalentoOpcao) itrAtributoTalentoOpcaoHierarquiaSuperior.next();

                // Obt�m categoria/atributo relacionado � op��o
                CategoriaAtributoTalento objCategoriaAtributoTalentoHierarquiaSuperior = objConsultaTalentoFacade.obterCategoriaAtributoTalentoPorCategoriaTalentoAtributoTalento(objCategoriaTalento, objAtributoTalentoOpcaoHierarquiaSuperior.getAtributoTalento());

                // Op��es 
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

        // Obt�m sess�o
        HttpSession session = this.request.getSession();

        // Obt�m form
        ConsultaTalentoForm detalheTalentoForm = (ConsultaTalentoForm) this.form;
        // Instancia Facade
        ConsultaTalentoFacade objConsultaTalentoFacade = new ConsultaTalentoFacade();

        try
        {
            // Obt�m categoria/atributo de talento a partir da chave recebida
            CategoriaAtributoTalento objCategoriaAtributoTalento = null;
            if (this.request.getParameter("categoriaAtributoTalento") != null && !"".equals(this.request.getParameter("categoriaAtributoTalento")))
            {
                objCategoriaAtributoTalento = objConsultaTalentoFacade.obterCategoriaAtributoTalentoPelaChave(this.request.getParameter("categoriaAtributoTalento"));
            }
            else
            {
                objCategoriaAtributoTalento = (CategoriaAtributoTalento) this.request.getAttribute("objCategoriaAtributoTalento");
            }

            // Cria op��o de talento a partir da chave recebida
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

            // Verifica se a categoria/atributo e as op��es n�o est�o nulas
            if (objCategoriaAtributoTalento != null && objAtributoTalentoOpcao != null)
            {
                // Obt�m categoria/atributos filhos da op��o selecionada
                List lstCategoriaAtributosTalentoFilhos = objConsultaTalentoFacade.obterCategoriaAtributosTalentoFilhos(objCategoriaAtributoTalento);

                // Obt�m op��es dos atributos de talento (na rela��o categoria/atributo)
                Iterator itrCategoriaAtributosTalentoFilhos = lstCategoriaAtributosTalentoFilhos.iterator();
                while (itrCategoriaAtributosTalentoFilhos.hasNext())
                {
                    CategoriaAtributoTalento objCategoriaAtributoTalentoFilho = (CategoriaAtributoTalento) itrCategoriaAtributosTalentoFilhos.next();

                    // Op��es 
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

        // Obt�m sess�o
        HttpSession session = this.request.getSession();

        // Obt�m form
        ConsultaTalentoForm consultaTalentoForm = (ConsultaTalentoForm) this.form;

        // Instancia Facade
        ConsultaTalentoFacade objConsultaTalentoFacade = new ConsultaTalentoFacade();

        try
        {
            // Verifica se houve sele��o de categoria de talento
            if (consultaTalentoForm.getCategoriaTalento() != null && !"".equals(consultaTalentoForm.getCategoriaTalento()))
            {
                // Armazena categoria de talento na sess�o
                CategoriaTalento objCategoriaTalento = objConsultaTalentoFacade.obterCategoriaTalentoPelaChave(consultaTalentoForm.getCategoriaTalento());
                session.setAttribute("objCategoriaTalento", objCategoriaTalento);

                // Obt�m atributos de talento para consulta
                List lstCategoriaAtributosTalentoConsulta = objConsultaTalentoFacade.obterCategoriaAtributosTalento(objCategoriaTalento);
                this.request.setAttribute("lstCategoriaAtributosTalentoConsulta", lstCategoriaAtributosTalentoConsulta);

                // Obt�m op��es dos atributos de talento, e verifica quais atributos t�m 
                // filhos (na rela��o categoria/atributo)
                Iterator itrCategoriaAtributosTalentoConsulta = lstCategoriaAtributosTalentoConsulta.iterator();
                String strCategoriaAtributosTalentoConsultaComFilhos = "";
                while (itrCategoriaAtributosTalentoConsulta.hasNext())
                {
                    CategoriaAtributoTalento objCategoriaAtributoTalento = (CategoriaAtributoTalento) itrCategoriaAtributosTalentoConsulta.next();

                    // Limpa op��es anteriores, se necess�rio
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
                        //Passo o tipo HTML do atributo para Text pois as consultas por um atributo virtual sempre ser�o em texto puro
                        TipoHTML tipoHtml = new TipoHTML();
                        tipoHtml.setDescricao("TEXT");
                        objCategoriaAtributoTalento.getAtributoTalento().setTipoHTML(tipoHtml);
                        //Nao deve ter opcoes pois a consulta por um atributo virtual sera um campo texto descritivo
                        List lstAtributoTalentoOpcoes = null;
                        session.setAttribute(String.valueOf(objCategoriaAtributoTalento.getIdentificador()), lstAtributoTalentoOpcoes);
                    }
                    else
                    {

                        // Op��es (desde que o atributo n�o tenha filhos)
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

                // Armazena atributos com filho (na rela��o categoria/atributo)
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

        // Obt�m sess�o
        HttpSession session = this.request.getSession();

        // Instancia Facade
        ConsultaTalentoFacade objConsultaTalentoFacade = new ConsultaTalentoFacade();
        Map mapConsultasTalento = null;

        try
        {
            // Obt�m categorias de talento para consulta
            List lstCategoriasTalentoConsulta = objConsultaTalentoFacade.obterCategoriasTalento();
            this.request.setAttribute("lstCategoriasTalentoConsulta", lstCategoriasTalentoConsulta);

            // Obt�m map de consulta
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

        // Declara��es
        HttpSession session = this.request.getSession();

        Map mapConsultasTalento = null;

        // Obt�m map
        mapConsultasTalento = (Map) session.getAttribute("mapConsultasTalento");

        // Remove do map
        mapConsultasTalento.remove(this.request.getParameter("key"));

        // Remove descri��o da categoria
        session.removeAttribute("strDesCategoria" + this.request.getParameter("key"));

        // Mensagem
        this.mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", "Crit�rio removido com sucesso"));

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

        // Obt�m sess�o
        HttpSession session = this.request.getSession();

        // Instancia Facade
        ConsultaTalentoFacade objConsultaTalentoFacade = new ConsultaTalentoFacade();
        Map mapConsultasTalento = null;

        try
        {
            // Verifica p�gina

            if (this.request.getParameter("pagina") == null)
            {
                // Obt�m usu�rio logado
                //String strIdentificador = String.valueOf(SegurancaWeb.obterSegurancaWeb(session).obterUsuarioAutenticado().obterIdentificador());
                UsuarioAutenticado objPessoa = SegurancaWeb.obterSegurancaWeb(session).obterUsuarioAutenticado();

                // Obt�m par�metros de configura��o para a consulta
                StringBuffer stbTotalRegistros = new StringBuffer();
                mapConsultasTalento = (Map) session.getAttribute("mapConsultasTalento");
                String criterioConsultaLucene = (String) session.getAttribute("criterioConsultaLucene");
                boolean blnFiltroGrupo = false;
                String strFiltrarConsultaPorGrupo = AplicacaoPlugIn.obterConfiguracao("FILTRAR_CONSULTA_POR_GRUPO");
                if ("sim".equalsIgnoreCase(strFiltrarConsultaPorGrupo))
                {
                    blnFiltroGrupo = true;
                }
                else if (!"nao".equalsIgnoreCase(strFiltrarConsultaPorGrupo) && !"n�o".equalsIgnoreCase(strFiltrarConsultaPorGrupo))
                {
                    log.error("O arquivo BancoTalentosGestao-aplicacao.xml deve conter a entrada FILTRAR_CONSULTA_POR_GRUPO com a op��o 'sim' ou 'n�o' (sem aspas).");
                    throw new NegocioException("N�o foi especificado se as consultas devem ou n�o ser filtradas por grupo");
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

        // Obt�m form
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

                // Obt�m relat�rio
                RelatorioJasper relatorioPrincipal = (RelatorioJasper) RelatorioFactory.obterJasper((String) this.action.getServlet().getInitParameter("relatorioBancoTalentosGestao"), "Curriculo");

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
        }
        else
        {
            this.erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", "Marque pelo menos um curr�culo para ser impresso"));
        }

        // Mensagens
        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);
    }

}

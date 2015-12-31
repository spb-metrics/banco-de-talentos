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


package br.gov.camara.visao.comum.sequencia;

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
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import br.gov.camara.biblioteca.util.DataNova;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.comum.facade.DataHoraBDFacade;
/**
 * Sequencia VersaoSequencia
 */

public final class VersaoSequencia extends SigespSequencia
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(VersaoSequencia.class);

    /**
     * Metodo construtor
     *
     * @param mapping Mapeamento executado
     * @param form Formulário associado (quando houver)
     * @param request Request
     * @param response Response
     * @param erros Colecao de erros que já ocorreram
     * @param mensagens Colecao de mensagens a serem exibidas
     * @param VersaoAction Classe Action associada
     */

    public VersaoSequencia (ActionMapping mapping, ActionForm form,
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
     * Metodo versaoPrepararVisualizacao
     */

    public ArrayList versaoPrepararVisualizacao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Recupera sessao
        HttpSession session = this.request.getSession();

        // Efetuar validacoes / preparacoes necessarias

        // Exemplo para adicionar um erro:
        // erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", "Mensagem de erro a ser exibida"));

        if (this.erros.isEmpty())
        {

            try
            {
                // -----------------------------------
                // Corpo do metodo - Completar aqui...
                // -----------------------------------

                Properties propriedadesBuild = new Properties();
                InputStream isPropriedadesBuild = session.getServletContext().getResourceAsStream("/WEB-INF/config/MANIFEST.MF");
                if (isPropriedadesBuild != null)
                {
                    propriedadesBuild.load(isPropriedadesBuild);
                }

                List listaPropriedadesVersao = new ArrayList();
                Iterator itrLoop = propriedadesBuild.keySet().iterator();
                while (itrLoop.hasNext())
                {
                    String nomePropriedade = (String) itrLoop.next();
                    Map mapPropriedadesVersao = new HashMap();
                    mapPropriedadesVersao.put("propriedade", nomePropriedade);
                    if ("Built-Date".equals(nomePropriedade))
                    {
                        String valor = propriedadesBuild.getProperty(nomePropriedade);
                        StringBuffer dataBuild = new StringBuffer();
                        dataBuild.append(valor.substring(6, 8)); // Dia
                        dataBuild.append("/");
                        dataBuild.append(valor.substring(4, 6)); // M�s
                        dataBuild.append("/");
                        dataBuild.append(valor.substring(0, 4)); // Ano
                        dataBuild.append(" ");
                        dataBuild.append(valor.substring(8, 10)); // Hora
                        dataBuild.append(":");
                        dataBuild.append(valor.substring(10, 12)); // Minutos
                        dataBuild.append(":");
                        dataBuild.append(valor.substring(12, 14)); // Segundos
                        mapPropriedadesVersao.put("valor", dataBuild.toString());
                    }
                    else
                    {
                        mapPropriedadesVersao.put("valor", propriedadesBuild.getProperty(nomePropriedade));
                    }
                    listaPropriedadesVersao.add(mapPropriedadesVersao);
                }

                // Id da inst�ncia atual do servidor de aplica��o
                Map mapPropriedadesVersao = new HashMap();
                mapPropriedadesVersao.put("propriedade", "Id inst�ncia serv. aplic.");
                mapPropriedadesVersao.put("valor", session.getServletContext().getAttribute("IdInstanciaServidorAplicacao"));
                listaPropriedadesVersao.add(mapPropriedadesVersao);

                // Data e Hora 
                mapPropriedadesVersao = new HashMap();
                mapPropriedadesVersao.put("propriedade", "Data e hora da consulta");
                mapPropriedadesVersao.put("valor", DataNova.formatarData(DataHoraBDFacade.obterTimeStampBD(), DataNova.FORMATO_DATAHORA));
                listaPropriedadesVersao.add(mapPropriedadesVersao);

                this.request.setAttribute("propriedadesVersao", listaPropriedadesVersao);
                // Exemplo de envio de uma mensagem (sucesso) para o usuario
                // mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", "Mensagem informativa a ser exibida"));
            }
            catch (IOException ioe)
            {
                this.erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", "Ocorreu um erro abrindo o arquivo com as propriedades da vers�o desta aplica��o: "
                        + ioe.getMessage()));
            }
            catch (CDException cde)
            {
                this.erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", this.obterMensagem(cde)));
            }
            finally
            {}
        }

        this.arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, this.mensagens);
        this.arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, this.erros);

        return (this.arlMensagens);

    }
     

}
    

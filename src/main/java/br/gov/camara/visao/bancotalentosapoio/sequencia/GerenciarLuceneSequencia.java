/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.camara.visao.bancotalentosapoio.sequencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import sigesp.comum.util.struts.SigespSequencia;
import sigesp.comum.util.struts.actions.ActionMessagesUtil;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.bancotalentos.facade.PessoaFacade;
import br.gov.camara.negocio.bancotalentos.facade.TalentoFacade;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalentoValorado;
import br.gov.camara.negocio.bancotalentos.pojo.Pessoa;
import br.gov.camara.negocio.bancotalentos.pojo.Talento;
import br.gov.camara.negocio.bancotalentos.util.IndexaTalento;

/**
 *
 * @author christian.braz
 */
public class GerenciarLuceneSequencia extends SigespSequencia
{

  // Inicializa o log
  private static Log log = LogFactory.getLog(GerenciarLuceneSequencia.class);

  /**
   * Metodo construtor
   *
   * @param mapping Mapeamento executado
   * @param form FormulÃ¡rio associado (quando houver)
   * @param request Request
   * @param response Response
   * @param erros Colecao de erros que jÃ¡ ocorreram
   * @param mensagens Colecao de mensagens a serem exibidas
   * @param AtributoTalentoAction Classe Action associada
   */
  public GerenciarLuceneSequencia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages erros,
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

  public ArrayList reindexar() throws IOException
  {
    if (log.isDebugEnabled())
    {
      log.debug("Entrada no metodo");
    }

    IndexaTalento idx = IndexaTalento.getInstance();
    PessoaFacade facadePessoa = new PessoaFacade();
    TalentoFacade talentoFacade = new TalentoFacade();

    try
    {
      //primeiro limpa o indice
      idx.limparIndice();

      List<Pessoa> lstPessoas = facadePessoa.obterTodos();
      Iterator it = lstPessoas.iterator();

      while (it.hasNext())
      {
        Pessoa p = (Pessoa) it.next();
        List talentos = talentoFacade.obterPorPessoaPorPagina(p, 1, 1000);
        //varre os talentos inserindo no indice do Lucene
        Iterator itTalentos = talentos.iterator();
        //Vai acomodar uma string unica com todos os talentos de um usuario
        StringBuilder descricao_talento = new StringBuilder();

        while (itTalentos.hasNext())
        {
          Talento t = (Talento) itTalentos.next();
          //t.setPessoa(p);

          //obtem as valoracoes de um talento
          List valoracoes = talentoFacade.obterValoracoesPorTalento(t);
          Iterator itValoracoes = valoracoes.iterator();

          while (itValoracoes.hasNext())
          {
            AtributoTalentoValorado v = (AtributoTalentoValorado) itValoracoes.next();

            //Tenta excluir campos do tipo "D"ata e "N"umero
            if (v.getCategoriaAtributoTalento().getAtributoTalento().getTipoDado() != null
                    && "D".equals(v.getCategoriaAtributoTalento().getAtributoTalento().getTipoDado())
                    || "N".equals(v.getCategoriaAtributoTalento().getAtributoTalento().getTipoDado()))
            {
              continue;
            }
            descricao_talento.append(" " + v.getValoracao());
          }

          valoracoes = null;
        }//quando termina esse while já varreu todos os talentos de um usuario em particular
        talentos = null;
        
        //Agora que concatenou todos os talentos pode-se inserir no Lucene
        idx.addDocumento(p.getIdentificador().toString(), descricao_talento.toString());

      }//fim da indexacao de todos os talentos cadastrados no banco de dados
      
       this.mensagens.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.mensagem.generica", "Indexação concluída com sucesso"));

    } catch (CDException cde)
    {
      if (log.isErrorEnabled())
      {
        log.error(cde.obterExcecoes());
      }

      erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.generico", cde.obterMensagem()));
    } finally
    {
      idx.close();
    }

    // Mensagens
    arlMensagens.add(ActionMessagesUtil.INDEX_MENSAGENS, mensagens);
    arlMensagens.add(ActionMessagesUtil.INDEX_ERROS, erros);

    return (arlMensagens);
  }
}

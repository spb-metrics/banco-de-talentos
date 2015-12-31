package br.gov.camara.visao.bancotalentosapoio.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * ActionForm FiltroConsultaGrupoForm
 *
 * @version FALTA DOCUMENTACAO
 * @author FALTA DOCUMENTACAO
 */

public final class FiltroConsultaGrupoForm extends ActionForm
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(FiltroConsultaGrupoForm.class);

    // Propriedades (variaveis de intancia) -----------------------------------

    private String[] grupoOrigem = null;

    private String[] grupoDestino = null;

    // Metodos (getters e setters) --------------------------------------------

    // GrupoOrigem

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String[] getGrupoOrigem()
    {
        return (this.grupoOrigem);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param grupoOrigem FALTA DOCUMENTACAO
     */

    public void setGrupoOrigem(String[] grupoOrigem)
    {
        this.grupoOrigem = grupoOrigem;
    }

    // GrupoDestino

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String[] getGrupoDestino()
    {
        return (this.grupoDestino);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param grupoDestino FALTA DOCUMENTACAO
     */

    public void setGrupoDestino(String[] grupoDestino)
    {
        this.grupoDestino = grupoDestino;
    }

    // Metodos publicos -------------------------------------------------------

    /**
     * Reseta o valor de todas as propriedades
     *
     * @param mapping O mapeamento utilizado para requisitar esta instancia
     * @param request A requisicao do servlet que esta sendo processada
     */

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
    /*

    if (log.isDebugEnabled())
    {
        log.debug("Entrada no metodo");
    }

    grupoOrigem = null;
    grupoDestino = null;
    
    */

    }

    /**
     * Valida as propriedades que foram setadas para esta requisicao HTTP
     * e retorna um objeto ActionErrors que encapsula qualquer
     * erro de validacao que foi encontrado. Se nao foi encontrado nenhum erro,
     * retorna null ou um objeto ActionErrors sem
     * nenhuma mensagem armazenada.
     *
     * @param mapping O mapeamento utilizado para requisitar esta instancia
     * @param request A requisicao do servlet que esta sendo processada
     */

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        ActionErrors erros = new ActionErrors();

        MessageResources messages = (MessageResources) request.getAttribute(Globals.MESSAGES_KEY);

        // Validar os campos do formulario de acordo com a pagina origem

        /*if ("FiltroConsultaGrupo".equals(mapping.getParameter()))
        {
        
            if ((grupoDestino == null) || (grupoDestino.length < 1))
            {
                erros.add(ActionErrors.GLOBAL_ERROR, new ActionError("sigesp.comum.erro.validacao.obrigatoriedade", messages.getMessage("visao.BancoTalentosApoio.interface.grupoDestino")));
            }
            
        }*/

        return erros;

    }

}

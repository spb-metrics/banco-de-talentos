
package br.gov.camara.visao.bancotalentosapoio.form;

import java.util.TreeMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionErrors;

/**
 * ActionForm OrdenacaoCategoriaAtributoTalentoForm
 *
 * @version FALTA DOCUMENTACAO
 * @author FALTA DOCUMENTACAO
 */

public final class OrdenacaoCategoriaAtributoTalentoForm extends ActionForm
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(OrdenacaoCategoriaAtributoTalentoForm.class);

    // Propriedades (variaveis de intancia) -----------------------------------

    private String[] sequencialPreenchimento = null;

    // Metodos (getters e setters) --------------------------------------------
    
    // SequencialPreenchimento

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */                                                                             

    public String[] getSequencialPreenchimento()
    {
        return (this.sequencialPreenchimento);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param sequencialPreenchimento FALTA DOCUMENTACAO
     */

    public void setSequencialPreenchimento(String[] sequencialPreenchimento)
    {
        this.sequencialPreenchimento = sequencialPreenchimento;
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

        sequencialPreenchimento = null;
        
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

    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        ActionErrors erros = new ActionErrors();

        MessageResources messages = (MessageResources) request.getAttribute(Globals.MESSAGES_KEY);

        // Validar os campos do formulario de acordo com a pagina origem

        if ("OrdenacaoCategoriaAtributoTalento".equals(mapping.getParameter()))
        {
        
        }

        return erros;

    }

}
    
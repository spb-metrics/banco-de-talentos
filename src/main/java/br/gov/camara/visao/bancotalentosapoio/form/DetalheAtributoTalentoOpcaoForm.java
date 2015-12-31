package br.gov.camara.visao.bancotalentosapoio.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

/**
 * ActionForm DetalheAtributoTalentoOpcaoForm
 *
 * @version FALTA DOCUMENTACAO
 * @author FALTA DOCUMENTACAO
 */

public final class DetalheAtributoTalentoOpcaoForm extends ActionForm
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(DetalheAtributoTalentoOpcaoForm.class);

    // Propriedades (variaveis de intancia) -----------------------------------

    private String identificador = null;

    private String atributoTalentoOpcaoPai = null;

    private String descricao = null;

    // Metodos (getters e setters) --------------------------------------------

    // Identificador

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String getIdentificador()
    {
        return (this.identificador);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param identificador FALTA DOCUMENTACAO
     */

    public void setIdentificador(String identificador)
    {
        this.identificador = identificador;
    }

    // AtributoTalentoOpcaoPai

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String getAtributoTalentoOpcaoPai()
    {
        return (this.atributoTalentoOpcaoPai);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param atributoTalentoOpcaoPai FALTA DOCUMENTACAO
     */

    public void setAtributoTalentoOpcaoPai(String atributoTalentoOpcaoPai)
    {
        this.atributoTalentoOpcaoPai = atributoTalentoOpcaoPai;
    }

    // Descricao

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String getDescricao()
    {
        return (this.descricao);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param descricao FALTA DOCUMENTACAO
     */

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
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

    identificador = null;
    atributoTalentoOpcaoPai = null;
    descricao = null;
    
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

        if ("DetalheAtributoTalentoOpcao".equals(mapping.getParameter()))
        {

            if ((descricao == null) || (descricao.length() < 1))
            {
                erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.validacao.obrigatoriedade", messages.getMessage("visao.BancoTalentosApoio.interface.descricao")));
            }

        }

        return erros;

    }

}

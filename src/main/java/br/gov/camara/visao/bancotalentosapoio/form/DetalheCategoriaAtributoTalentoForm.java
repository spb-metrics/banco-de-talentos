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
 * ActionForm DetalheCategoriaAtributoTalentoForm
 *
 * @version FALTA DOCUMENTACAO
 * @author FALTA DOCUMENTACAO
 */

public final class DetalheCategoriaAtributoTalentoForm extends ActionForm
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(DetalheCategoriaAtributoTalentoForm.class);

    // Propriedades (variaveis de intancia) -----------------------------------

    private String identificador = null;

    private String atributoTalento = null;

    private boolean indicativoObrigatoriedade = false;

    private boolean formacaoDescricao = false;

    private String dicaPreenchimento = null;

    private String apelido = null;

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

    // AtributoTalento

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String getAtributoTalento()
    {
        return (this.atributoTalento);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param atributoTalento FALTA DOCUMENTACAO
     */

    public void setAtributoTalento(String atributoTalento)
    {
        this.atributoTalento = atributoTalento;
    }

    // IndicativoObrigatoriedade

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public boolean getIndicativoObrigatoriedade()
    {
        return (this.indicativoObrigatoriedade);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param indicativoObrigatoriedade FALTA DOCUMENTACAO
     */

    public void setIndicativoObrigatoriedade(boolean indicativoObrigatoriedade)
    {
        this.indicativoObrigatoriedade = indicativoObrigatoriedade;
    }

    // FormacaoDescricao

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public boolean getFormacaoDescricao()
    {
        return (this.formacaoDescricao);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param formacaoDescricao FALTA DOCUMENTACAO
     */

    public void setFormacaoDescricao(boolean formacaoDescricao)
    {
        this.formacaoDescricao = formacaoDescricao;
    }

    // DicaPreenchimento

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String getDicaPreenchimento()
    {
        return (this.dicaPreenchimento);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param dicaPreenchimento FALTA DOCUMENTACAO
     */

    public void setDicaPreenchimento(String dicaPreenchimento)
    {
        this.dicaPreenchimento = dicaPreenchimento;
    }

    // Apelido

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String getApelido()
    {
        return (this.apelido);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param apelido FALTA DOCUMENTACAO
     */

    public void setApelido(String apelido)
    {
        this.apelido = apelido;
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
    atributoTalento = null;
    indicativoObrigatoriedade = false;
    formacaoDescricao = false;
    dicaPreenchimento = null;
    apelido = null;
    
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

        if ("DetalheCategoriaAtributoTalento".equals(mapping.getParameter()))
        {

            if ((atributoTalento == null) || (atributoTalento.length() < 1))
            {
                erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.validacao.obrigatoriedade", messages.getMessage("visao.BancoTalentosApoio.interface.atributoTalento")));
            }

        }

        return erros;

    }

}

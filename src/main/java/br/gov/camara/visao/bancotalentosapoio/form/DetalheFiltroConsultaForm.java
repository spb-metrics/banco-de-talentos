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
 * ActionForm DetalheFiltroConsultaForm
 *
 * @version FALTA DOCUMENTACAO
 * @author FALTA DOCUMENTACAO
 */

public final class DetalheFiltroConsultaForm extends ActionForm
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(DetalheFiltroConsultaForm.class);

    // Propriedades (variaveis de intancia) -----------------------------------

    private String identificador = null;

    private String nomeFiltroConsulta = null;

    private String[] grupoOrigem = null;

    private String[] grupoDestino = null;

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

    // NomeFiltroConsulta

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String getNomeFiltroConsulta()
    {
        return (this.nomeFiltroConsulta);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param nomeFiltroConsulta FALTA DOCUMENTACAO
     */

    public void setNomeFiltroConsulta(String nomeFiltroConsulta)
    {
        this.nomeFiltroConsulta = nomeFiltroConsulta;
    }

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

    nomeFiltroConsulta = null;
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

        if ("DetalheFiltroConsulta".equals(mapping.getParameter()))
        {

            if ((nomeFiltroConsulta == null) || (nomeFiltroConsulta.length() < 1))
            {
                erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.validacao.obrigatoriedade", messages.getMessage("visao.BancoTalentosApoio.interface.nomeFiltroConsulta")));
            }

            /*if ((grupoDestino == null) || (grupoDestino.length < 1))
            {
                erros.add(ActionErrors.GLOBAL_ERROR, new ActionError("sigesp.comum.erro.validacao.obrigatoriedade", messages.getMessage("visao.BancoTalentosApoio.interface.grupoDestino")));
            }*/

        }

        return erros;

    }

}

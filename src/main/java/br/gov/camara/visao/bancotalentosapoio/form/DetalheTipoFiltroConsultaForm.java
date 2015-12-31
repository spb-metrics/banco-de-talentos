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
 * ActionForm DetalheTipoFiltroConsultaForm
 *
 * @version FALTA DOCUMENTACAO
 * @author FALTA DOCUMENTACAO
 */

public final class DetalheTipoFiltroConsultaForm extends ActionForm
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(DetalheTipoFiltroConsultaForm.class);

    // Propriedades (variaveis de intancia) -----------------------------------

    private String nomeTipoFiltroConsulta = null;

    private String objetoControlado = null;

    private String identificador = null;

    // Metodos (getters e setters) --------------------------------------------

    // NomeTipoFiltroConsulta

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String getNomeTipoFiltroConsulta()
    {
        return (this.nomeTipoFiltroConsulta);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param nomeTipoFiltroConsulta FALTA DOCUMENTACAO
     */

    public void setNomeTipoFiltroConsulta(String nomeTipoFiltroConsulta)
    {
        this.nomeTipoFiltroConsulta = nomeTipoFiltroConsulta;
    }

    // ObjetoControlado

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String getObjetoControlado()
    {
        return (this.objetoControlado);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param objetoControlado FALTA DOCUMENTACAO
     */

    public void setObjetoControlado(String objetoControlado)
    {
        this.objetoControlado = objetoControlado;
    }

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

    nomeTipoFiltroConsulta = null;
    objetoControlado = null;
    identificador = null;
    
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

        if ("DetalheTipoFiltroConsulta".equals(mapping.getParameter()))
        {

            if ((nomeTipoFiltroConsulta == null) || (nomeTipoFiltroConsulta.length() < 1))
            {
                erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.validacao.obrigatoriedade", messages.getMessage("visao.BancoTalentosApoio.interface.nomeTipoFiltroConsulta")));
            }

            if ((objetoControlado == null) || (objetoControlado.length() < 1))
            {
                erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.validacao.obrigatoriedade", messages.getMessage("visao.BancoTalentosApoio.interface.objetoControlado")));
            }

        }

        return erros;

    }

}

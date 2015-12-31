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
 * ActionForm DetalheAtributoTalentoForm
 *
 * @version FALTA DOCUMENTACAO
 * @author FALTA DOCUMENTACAO
 */

public final class DetalheAtributoTalentoForm extends ActionForm
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(DetalheAtributoTalentoForm.class);

    // Propriedades (variaveis de intancia) -----------------------------------

    private String identificador = null;

    private String atributoTalentoPai = null;

    private String nome = null;

    private String tipoHTML = null;

    private String mascara = null;

    private String tabelaApoioMM = null;

    private boolean indicativoPesquisa = false;

    private String descricaoPesquisa = null;

    private String tipoDado = null;

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

    // AtributoTalentoPai

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String getAtributoTalentoPai()
    {
        return (this.atributoTalentoPai);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param atributoTalentoPai FALTA DOCUMENTACAO
     */

    public void setAtributoTalentoPai(String atributoTalentoPai)
    {
        this.atributoTalentoPai = atributoTalentoPai;
    }

    // Nome

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String getNome()
    {
        return (this.nome);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param nome FALTA DOCUMENTACAO
     */

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    // TipoHTML

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String getTipoHTML()
    {
        return (this.tipoHTML);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param tipoHTML FALTA DOCUMENTACAO
     */

    public void setTipoHTML(String tipoHTML)
    {
        this.tipoHTML = tipoHTML;
    }

    // Mascara

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String getMascara()
    {
        return (this.mascara);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param mascara FALTA DOCUMENTACAO
     */

    public void setMascara(String mascara)
    {
        this.mascara = mascara;
    }

    // TabelaApoioMM

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String getTabelaApoioMM()
    {
        return (this.tabelaApoioMM);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param tabelaApoioMM FALTA DOCUMENTACAO
     */

    public void setTabelaApoioMM(String tabelaApoioMM)
    {
        this.tabelaApoioMM = tabelaApoioMM;
    }

    // IndicativoPesquisa

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public boolean getIndicativoPesquisa()
    {
        return (this.indicativoPesquisa);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param indicativoPesquisa FALTA DOCUMENTACAO
     */

    public void setIndicativoPesquisa(boolean indicativoPesquisa)
    {
        this.indicativoPesquisa = indicativoPesquisa;
    }

    // DescricaoPesquisa

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String getDescricaoPesquisa()
    {
        return (this.descricaoPesquisa);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param descricaoPesquisa FALTA DOCUMENTACAO
     */

    public void setDescricaoPesquisa(String descricaoPesquisa)
    {
        this.descricaoPesquisa = descricaoPesquisa;
    }

    // TipoDado

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String getTipoDado()
    {
        return (this.tipoDado);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param tipoDado FALTA DOCUMENTACAO
     */

    public void setTipoDado(String tipoDado)
    {
        this.tipoDado = tipoDado;
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
    atributoTalentoPai = null;
    nome = null;
    tipoHTML = null;
    mascara = null;
    tabelaApoioMM = null;
    indicativoPesquisa = false;
    descricaoPesquisa = null;
    tipoDado = null;
    
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

        if ("DetalheAtributoTalento".equals(mapping.getParameter()))
        {

            if ((nome == null) || (nome.length() < 1))
            {
                erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.validacao.obrigatoriedade", messages.getMessage("visao.BancoTalentosApoio.interface.nome")));
            }

            if ((tipoHTML == null) || (tipoHTML.length() < 1))
            {
                erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.validacao.obrigatoriedade", messages.getMessage("visao.BancoTalentosApoio.interface.tipoHtml")));
            }

            if ((tipoDado == null) || (tipoDado.length() < 1))
            {
                erros.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.validacao.obrigatoriedade", messages.getMessage("visao.BancoTalentosApoio.interface.tipoDado")));
            }

        }

        return erros;

    }

}

/****
 * Copyright 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008 Câmara dos Deputados, Brasil
 * 
 * Este arquivo é parte do programa TALENTOS - Banco de Talentos
 *
 * O TALENTOS é um software livre; você pode redistribuí-lo e/ou modificá-lo dentro dos termos da Licença Pública Geral GNU como publicada pela Fundação do Software Livre (FSF); na versão 2 da Licença.
 *
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU para maiores detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU, sob o título "LICENCA.txt", junto com este programa, se não, escreva para a Fundação do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 ***/


package sigesp.comum.util.struts;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * ActionForm ConfiguracaoLogForm
 *
 * @version FALTA DOCUMENTACAO
 * @author FALTA DOCUMENTACAO
 */

public class SigespForm extends ActionForm
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(SigespForm.class);

    // Propriedades (variaveis de intancia) -----------------------------------

    private String strUsuarioUltimaAtualizacao = null;

    private String strDataUltimaAtualizacao = null;

    // Metodos (getters e setters) --------------------------------------------
    
    // Item

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */                                                                             

    public String getUsuarioUltimaAtualizacao()
    {
        return (this.strUsuarioUltimaAtualizacao);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param item FALTA DOCUMENTACAO
     */

    public void setUsuarioUltimaAtualizacao(String strUsuarioUltimaAtualizacao)
    {
        this.strUsuarioUltimaAtualizacao = strUsuarioUltimaAtualizacao;
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */                                                                             

    public String getDataUltimaAtualizacao()
    {
        return (this.strDataUltimaAtualizacao);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param item FALTA DOCUMENTACAO
     */

    public void setDataUltimaAtualizacao(String strDataUltimaAtualizacao)
    {
        this.strDataUltimaAtualizacao = strDataUltimaAtualizacao;
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

    }

    /**
     * Valida as propriedades que foram setadas para esta requisicao HTTP
     * e retorna um objeto ActionMessages que encapsula qualquer
     * erro de validacao que foi encontrado. Se nao foi encontrado nenhum erro,
     * retorna null ou um objeto ActionMessages sem
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

        return erros;

    }

}
    
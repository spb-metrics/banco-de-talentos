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

package br.gov.camara.visao.autenticacao.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

/**
 * ActionForm EncaminharLogonForm
 *
 * @version FALTA DOCUMENTACAO
 * @author FALTA DOCUMENTACAO
 */

public final class EncaminharLogonForm extends ActionForm
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(EncaminharLogonForm.class);

    // Propriedades (variaveis de intancia) -----------------------------------

    private String txtLogin = null;

    private String txtSenha = null;

    // Metodos (getters e setters) --------------------------------------------

    // TxtLogin

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String getTxtLogin()
    {
        return (this.txtLogin);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param txtLogin FALTA DOCUMENTACAO
     */

    public void setTxtLogin(String txtLogin)
    {
        this.txtLogin = txtLogin;
    }

    // TxtSenha

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */

    public String getTxtSenha()
    {
        return (this.txtSenha);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param txtSenha FALTA DOCUMENTACAO
     */

    public void setTxtSenha(String txtSenha)
    {
        this.txtSenha = txtSenha;
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

     txtLogin = null;
     txtSenha = null;

     */
    }

    /**
     * Valida as propriedades que foram setadas para esta requisicao HTTP e retorna um objeto ActionErrors que
     * encapsula qualquer erro de validacao que foi encontrado. Se nao foi encontrado nenhum erro, retorna null ou um
     * objeto ActionErrors sem nenhuma mensagem armazenada.
     *
     * @param mapping O mapeamento utilizado para requisitar esta instancia
     * @param request A requisicao do servlet que esta sendo processada
     *
     * @return DOCUMENTAR!
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
        if ("EncaminharLogon".equals(mapping.getParameter()))
        {
            if (this.txtLogin != null)
            {
                this.txtLogin = this.txtLogin.trim();
            }

            if ((this.txtLogin == null) || (this.txtLogin.length() < 1))
            {
                erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.validacao.obrigatoriedade", messages.getMessage("visao.Autenticacao.interface.txtLogin")));
            }
            

            if ((this.txtSenha == null) || (this.txtSenha.length() < 1))
            {
                erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("sigesp.comum.erro.validacao.obrigatoriedade", messages.getMessage("visao.Autenticacao.interface.txtSenha")));
            }
        }

        return erros;
    }

}

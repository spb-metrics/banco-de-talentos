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


package br.gov.camara.visao.bancotalentosgestao.form;

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
 * ActionForm ConsultaEstatisticaCadastroForm
 *
 * @version FALTA DOCUMENTACAO
 * @author FALTA DOCUMENTACAO
 */

public final class ConsultaEstatisticaCadastroForm extends ActionForm
{
    // Inicializa o log

    private static Log log = LogFactory.getLog(ConsultaEstatisticaCadastroForm.class);

    // Propriedades (variaveis de intancia) -----------------------------------

    private String dataInicio = null;

    private String dataTermino = null;

    private String[] quebrasDisponiveis = null;

    private String[] quebrasSelecionadas = null;

    // Metodos (getters e setters) --------------------------------------------
    
    // DataInicio

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */                                                                             

    public String getDataInicio()
    {
        return (this.dataInicio);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param dataInicio FALTA DOCUMENTACAO
     */

    public void setDataInicio(String dataInicio)
    {
        this.dataInicio = dataInicio;
    }
    
    // DataTermino

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */                                                                             

    public String getDataTermino()
    {
        return (this.dataTermino);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param dataTermino FALTA DOCUMENTACAO
     */

    public void setDataTermino(String dataTermino)
    {
        this.dataTermino = dataTermino;
    }
    
    // QuebrasDisponiveis

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */                                                                             

    public String[] getQuebrasDisponiveis()
    {
        return (this.quebrasDisponiveis);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param quebrasDisponiveis FALTA DOCUMENTACAO
     */

    public void setQuebrasDisponiveis(String[] quebrasDisponiveis)
    {
        this.quebrasDisponiveis = quebrasDisponiveis;
    }
    
    // QuebrasSelecionadas

    /**
     * FALTA DOCUMENTACAO
     *
     * @return FALTA DOCUMENTACAO
     */                                                                             

    public String[] getQuebrasSelecionadas()
    {
        return (this.quebrasSelecionadas);
    }

    /**
     * FALTA DOCUMENTACAO
     *
     * @param quebrasSelecionadas FALTA DOCUMENTACAO
     */

    public void setQuebrasSelecionadas(String[] quebrasSelecionadas)
    {
        this.quebrasSelecionadas = quebrasSelecionadas;
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

        dataInicio = null;
        dataTermino = null;
        quebrasDisponiveis = null;
        quebrasSelecionadas = null;
        
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

        if ("ConsultaEstatisticaCadastro".equals(mapping.getParameter()))
        {
        
        }

        return erros;

    }

}
    
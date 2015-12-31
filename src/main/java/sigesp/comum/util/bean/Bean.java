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

package sigesp.comum.util.bean;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sigesp.comum.util.exception.FabricaBeansException;

/**
 * Guarda um objeto javabean din�mico.
 */
public class Bean
{
    // Inicializa o log
    private static Log log = LogFactory.getLog("sigesp.comum.util.bean.Bean");
    Object objBean = null;

    /**
     * Cria um novo objeto Bean que guarda um javabean.
     *
     * @param objBean : Objeto javabean a ser mantido.
     */
    public Bean(Object objBean)
    {
        // Registra log
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no m�todo");
        }

        this.objBean = objBean;
    }

    public Object obterBean()
    {
        return this.objBean;
    }
    /**
     * Atribuir um valor a um atributo do javabean.
     *
     * @param strProp   : nome do atributo (case sensitive)
     * @param objValor  : valor a ser atribuido.
     *
     * @throws FabricaBeansException : Mensagens de erro.
     */
    public void atribuirPropriedade(String strProp, Object objValor) throws FabricaBeansException
    {
        // Registra log
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no m�todo");
        }

        try
        {
            strProp = strProp.substring(0, 1).toUpperCase() + strProp.substring(1).toLowerCase();
            PropertyUtils.setProperty(objBean, strProp, objValor);
        }
        catch (NoSuchMethodException nsme)
        {
            // Registra log
            if (log.isFatalEnabled())
            {
                log.fatal("Atributo " + strProp + " n�o encontrado no objBean. " + nsme.toString());
            }
            throw new FabricaBeansException("Atributo " + strProp + " n�o encontrado no objBean. " + nsme.toString());
        }
        catch (InvocationTargetException ite)
        {
            // Registra log
            if (log.isFatalEnabled())
            {
                log.fatal("Erro de Invocation Target:  " + ite.toString());
            }
            throw new FabricaBeansException("Erro de Invocation Target:  " + ite.toString());
        }
        catch (IllegalAccessException iae)
        {
            // Registra log
            if (log.isFatalEnabled())
            {
                log.fatal("Erro de Acesso Ilegal ao objBean:  " + iae.toString());
            }
            throw new FabricaBeansException("Erro de Acesso Ilegal ao objBean:  " + iae.toString());
        }
    }

    /**
     * Recuperar o valor de um atributo de um javabean din�mico.
     *
     * @param strProp   : nome do atributo.
     *
     * @return          : valor do atributo.
     *
     * @throws FabricaBeansException : Mensagens de erro.
     */
    public Object obterPropriedade(String strProp) throws FabricaBeansException
    {
        // Registra log
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no m�todo");
        }

        Object objPropriedade = null;
        try
        {
            try
            {
                objPropriedade = PropertyUtils.getProperty(objBean, strProp);
            }
            catch(Exception e)
            {
                strProp = strProp.substring(0, 1).toUpperCase() + strProp.substring(1).toLowerCase();
                objPropriedade = PropertyUtils.getProperty(objBean, strProp);
            }
        }
        catch (NoSuchMethodException nsme)
        {
            // Registra log
            if (log.isFatalEnabled())
            {
                log.fatal("Erro: Atributo " + strProp + " n�o encontrado no objBean. " + nsme.toString());
                try
                {
                    ArrayList tmp = descreverBean(objBean);
                    log.fatal("Descricao do bean:");
                    for (int i = 0; i < tmp.size(); i++)
                    {
                        log.fatal("Campo nr " + i + " : " + tmp.get(i));
                    }

                }
                catch (FabricaBeansException pe)
                {
                    pe.printStackTrace();
                }
            }
            throw new FabricaBeansException("Atributo " + strProp + " n�o encontrado no objBean. " + nsme.toString());
        }
        catch (InvocationTargetException ite)
        {
            // Registra log
            if (log.isFatalEnabled())
            {
                log.fatal("Erro: erro de Invocation Target:  " + ite.toString());
            }
            throw new FabricaBeansException("erro de Invocation Target:  " + ite.toString());
        }
        catch (IllegalAccessException iae)
        {
            // Registra log
            if (log.isFatalEnabled())
            {
                log.fatal("Erro de Acesso Ilegal ao objBean:  " + iae.toString());
            }
            throw new FabricaBeansException("erro de Acesso Ilegal ao objBean:  " + iae.toString());
        }
        if (objPropriedade == null)
        {
            return ""; // Se nulo, retornar string vazia.
        }

        return objPropriedade;
    }

    //-------------------------------------------------------------------------------

    /**
     * descreverBean:   retorna um ArrayList com os nomes dos atributos de um javabean din�mico.
     *
     * @param objBean :    Objeto javabean din�mico.
     *
     * @return :        ArrayList com os nomes dos atributos.
     *
     * @throws FabricaBeansException : No caso de erros na leitura dos atributos do javabean.
     */
    public static ArrayList descreverBean(Object objBean) throws FabricaBeansException
    {
        // Registra log
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no m�todo");
        }

        Map mapMapa = null;
        Iterator itrColunas = null;

        ArrayList arlDescricaoBean = new ArrayList();

        try
        {
            // Recuperar todos os atributos que tenham m�todos de acesso para leitura:
            mapMapa = PropertyUtils.describe(objBean);

            // Enviar conte�do do objBean para a linha recuperada:
            itrColunas = mapMapa.keySet().iterator();

            while (itrColunas.hasNext())
            {
                arlDescricaoBean.add("" + itrColunas.next());
            }
        }
        catch (NoSuchMethodException nsme)
        {
            if (log.isFatalEnabled())
            {
                log.fatal("Erro: N�o existem m�todos de acesso para o objBean " + objBean.toString());
            }
            throw new FabricaBeansException("N�o existem m�todos de acesso para o objBean " + objBean.toString());
        }
        catch (InvocationTargetException ite)
        {
            if (log.isFatalEnabled())
            {
                log.fatal("Erro: Problema ao invocar Target para o objBean " + objBean.toString());
            }
            throw new FabricaBeansException("Problema ao invocar Target para o objBean " + objBean.toString());
        }
        catch (IllegalAccessException iae)
        {
            if (log.isFatalEnabled())
            {
                log.fatal("Erro: M�todo de acesso Ilegal para o objBean " + objBean.toString());
            }
            throw new FabricaBeansException("M�todo de acesso Ilegal para o objBean " + objBean.toString());
        }
        catch (Exception e)
        {
            if (log.isFatalEnabled())
            {
                log.fatal(
                    "Erro inesperado em Bean.descreverBean(): "
                        + objBean.toString()
                        + "Classe do erro: "
                        + e.getCause()
                        + ". Mensagem = "
                        + e.getMessage());
            }
            throw new FabricaBeansException(
                "Erro inesperado em Bean.descreverBean(): "
                    + objBean.toString()
                    + "Classe do erro: "
                    + e.getCause()
                    + ". Mensagem = "
                    + e.getMessage());
        }

        return arlDescricaoBean;
    }

    /**
     * descreverBean:   retorna um ArrayList com os nomes dos atributos de um javabean din�mico.
     *
     * @return :        ArrayList com os nomes dos atributos.
     *
     * @throws FabricaBeansException : No caso de erros na leitura dos atributos do javabean.
     */
    public ArrayList descreverBean() throws FabricaBeansException
    {

        Object objBean = this.objBean;

        // Registra log
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no m�todo");
        }

        Map mapMapa = null;
        Iterator itrColunas = null;

        ArrayList arlDescricaoBean = new ArrayList();

        try
        {
            // Recuperar todos os atributos que tenham m�todos de acesso para leitura:
            mapMapa = PropertyUtils.describe(objBean);

            // Enviar conte�do do objBean para a linha recuperada:
            itrColunas = mapMapa.keySet().iterator();

            while (itrColunas.hasNext())
            {
                arlDescricaoBean.add("" + itrColunas.next());
            }
        }
        catch (NoSuchMethodException nsme)
        {
            if (log.isFatalEnabled())
            {
                log.fatal("Erro: N�o existem m�todos de acesso para o objBean " + objBean.toString());
            }
            throw new FabricaBeansException("N�o existem m�todos de acesso para o objBean " + objBean.toString());
        }
        catch (InvocationTargetException ite)
        {
            if (log.isFatalEnabled())
            {
                log.fatal("Erro: Problema ao invocar Target para o objBean " + objBean.toString());
            }
            throw new FabricaBeansException("Problema ao invocar Target para o objBean " + objBean.toString());
        }
        catch (IllegalAccessException iae)
        {
            if (log.isFatalEnabled())
            {
                log.fatal("Erro: M�todo de acesso Ilegal para o objBean " + objBean.toString());
            }
            throw new FabricaBeansException("M�todo de acesso Ilegal para o objBean " + objBean.toString());
        }
        catch (Exception e)
        {
            if (log.isFatalEnabled())
            {
                log.fatal(
                    "Erro inesperado em Bean.descreverBean(): "
                        + objBean.toString()
                        + "Classe do erro: "
                        + e.getCause()
                        + ". Mensagem = "
                        + e.getMessage());
            }
            throw new FabricaBeansException(
                "Erro inesperado em Bean.descreverBean(): "
                    + objBean.toString()
                    + "Classe do erro: "
                    + e.getCause()
                    + ". Mensagem = "
                    + e.getMessage());
        }

        return arlDescricaoBean;
    }

}

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
 * Guarda um objeto javabean dinâmico.
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
            log.debug("Entrada no método");
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
            log.debug("Entrada no método");
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
                log.fatal("Atributo " + strProp + " não encontrado no objBean. " + nsme.toString());
            }
            throw new FabricaBeansException("Atributo " + strProp + " não encontrado no objBean. " + nsme.toString());
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
     * Recuperar o valor de um atributo de um javabean dinâmico.
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
            log.debug("Entrada no método");
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
                log.fatal("Erro: Atributo " + strProp + " não encontrado no objBean. " + nsme.toString());
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
            throw new FabricaBeansException("Atributo " + strProp + " não encontrado no objBean. " + nsme.toString());
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
     * descreverBean:   retorna um ArrayList com os nomes dos atributos de um javabean dinâmico.
     *
     * @param objBean :    Objeto javabean dinâmico.
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
            log.debug("Entrada no método");
        }

        Map mapMapa = null;
        Iterator itrColunas = null;

        ArrayList arlDescricaoBean = new ArrayList();

        try
        {
            // Recuperar todos os atributos que tenham métodos de acesso para leitura:
            mapMapa = PropertyUtils.describe(objBean);

            // Enviar conteúdo do objBean para a linha recuperada:
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
                log.fatal("Erro: Não existem métodos de acesso para o objBean " + objBean.toString());
            }
            throw new FabricaBeansException("Não existem métodos de acesso para o objBean " + objBean.toString());
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
                log.fatal("Erro: Método de acesso Ilegal para o objBean " + objBean.toString());
            }
            throw new FabricaBeansException("Método de acesso Ilegal para o objBean " + objBean.toString());
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
     * descreverBean:   retorna um ArrayList com os nomes dos atributos de um javabean dinâmico.
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
            log.debug("Entrada no método");
        }

        Map mapMapa = null;
        Iterator itrColunas = null;

        ArrayList arlDescricaoBean = new ArrayList();

        try
        {
            // Recuperar todos os atributos que tenham métodos de acesso para leitura:
            mapMapa = PropertyUtils.describe(objBean);

            // Enviar conteúdo do objBean para a linha recuperada:
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
                log.fatal("Erro: Não existem métodos de acesso para o objBean " + objBean.toString());
            }
            throw new FabricaBeansException("Não existem métodos de acesso para o objBean " + objBean.toString());
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
                log.fatal("Erro: Método de acesso Ilegal para o objBean " + objBean.toString());
            }
            throw new FabricaBeansException("Método de acesso Ilegal para o objBean " + objBean.toString());
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

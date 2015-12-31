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

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sigesp.comum.util.exception.FabricaBeansException;

/**
 * Classe FabricaBeans: permite a cria��o de javabeans dinamicamente.
 *
 * @author Andr� Felipe Matos de Carvalho Camara dos Deputados 01/10/2002
 * @version 0.001
 */
public class FabricaBeans
{
    // Inicializa o log
    private static Log log = LogFactory.getLog("sigesp.comum.util.bean.FabricaBeans");

    /**
     * FabricaBeans: permite a cria��o de javabeans dinamicamente.
     *
     * @param itrColunas - ArrayList com os nomes dos atributos
     * @param arlValores - ArrayList de objetos (arlValores) de cada atributo.
     *
     * @return - Object javabean criado com os atributos setados.
     */
    public static Object criarBean(ArrayList itrColunas, ArrayList arlValores)
    {
        // Registra log
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no m�todo");
        }

        DynaBean objBean = null;
        int intCols = arlValores.size();

        try
        {
            DynaProperty[] dptPropriedades = new DynaProperty[intCols];

            for (int i = 0; i < intCols; i++)
            {
                dptPropriedades[i] = new DynaProperty("" + itrColunas.get(i));
            }

            BasicDynaClass dynaClass = new BasicDynaClass("registros", BasicDynaBean.class, dptPropriedades);

            objBean = dynaClass.newInstance();
            int i = 0;

            while (i < intCols)
            {
                objBean.set("" + itrColunas.get(i), arlValores.get(i));
                i++;
            }
        }
        catch (Exception erro)
        {
            if (log.isErrorEnabled())
            {
                log.error("Erro ao tentar criar Bean din�mico na classe FabricaBeans");
            }
        }

        return objBean;
    }

    /**
     * criarBean         Vers�o String[]  Recebe lista de atributos e arlValores para criar um objBean dinamico.
     *
     * @param itrColunas - String[] com os nomes dos atributos
     * @param arlValores - String[] com os arlValores de cada atributo.
     *
     * @return - Object javabean criado com os atributos setados.
     */
    public static Object criarBean(String[] itrColunas, String[] arlValores) throws FabricaBeansException
    {
        // Registra log
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no m�todo");
        }

        DynaBean objBean = null;

        int intCols = arlValores.length;

        try
        {
            DynaProperty[] dptPropriedades = new DynaProperty[intCols];

            for (int i = 0; i < intCols; i++)
            {
                dptPropriedades[i] = new DynaProperty("" + itrColunas[i]);
            }

            BasicDynaClass dynaClass = new BasicDynaClass("registros", BasicDynaBean.class, dptPropriedades);

            objBean = dynaClass.newInstance();
            int i = 0;

            while (i < intCols)
            {
                objBean.set("" + itrColunas[i], arlValores[i]);
                i++;
            }
        }
        catch (Exception erro)
        {
            if (log.isErrorEnabled())
            {
                log.error("Erro ao tentar criar Bean din�mico na classe FabricaBeans");
            }
            throw new FabricaBeansException("Erro ao tentar criar Bean din�mico na classe FabricaBeans");
        }

        return objBean;
    }

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
                log.fatal("N�o existem m�todos de acesso para o objBean " + objBean.toString());
            }
            throw new FabricaBeansException("N�o existem m�todos de acesso para o objBean " + objBean.toString());
        }
        catch (InvocationTargetException ite)
        {
            if (log.isFatalEnabled())
            {
                log.fatal("Problema ao invocar Target para o objBean " + objBean.toString());
            }
            throw new FabricaBeansException("Problema ao invocar Target para o objBean " + objBean.toString());
        }
        catch (IllegalAccessException iae)
        {
            if (log.isFatalEnabled())
            {
                log.fatal("M�todo de acesso Ilegal para o objBean " + objBean.toString());
            }
            throw new FabricaBeansException("M�todo de acesso Ilegal para o objBean " + objBean.toString());
        }
        catch (Exception e)
        {
            if (log.isFatalEnabled())
            {
                log.fatal(
                    "Erro inesperado em Persistencia.alterar( objBean , chave) ou n�o foi encontrado nenhum registro com a chave especificada"
                        + objBean.toString());
            }
            throw new FabricaBeansException(
                "Erro inesperado em Persistencia.alterar( objBean , chave) ou n�o foi encontrado nenhum registro com a chave especificada"
                    + objBean.toString());
        }

        return arlDescricaoBean;
    }
}

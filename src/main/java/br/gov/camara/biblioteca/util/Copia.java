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

package br.gov.camara.biblioteca.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe para poder obter c�pia de objetos JavaBean
 */
public class Copia
{
    /**
     * Logger for this class
     */
    private static final Log log = LogFactory.getLog(Copia.class);

    public static void criar(Object objOriginal, Object objCopia)
    {
        if (log.isDebugEnabled())
        {
            log.debug("criar(Object, Object) - start");
        }

        Class clsClasseObjeto;
        // objCopia = clsClasseObjeto.newInstance();
        Class clsClasseCopia;
        Method mtdOriginal[];
        String strNomeMetodoCopia = "";
        try
        {
            clsClasseObjeto = objOriginal.getClass();
            // objCopia = clsClasseObjeto.newInstance();
            clsClasseCopia = objCopia.getClass();
            mtdOriginal = clsClasseObjeto.getMethods();
            for (int i = 0; i < mtdOriginal.length; i++)
            {
                strNomeMetodoCopia = mtdOriginal[i].getName();
                if (strNomeMetodoCopia != null && !"".equals(strNomeMetodoCopia))
                {
                    if (strNomeMetodoCopia.startsWith("get")
                            && !strNomeMetodoCopia.equals("getClass")
                            && !strNomeMetodoCopia.startsWith("getCallback")
                            && !strNomeMetodoCopia.startsWith("getChaveEntidade"))
                    {
                        Object objRetornoCopia = mtdOriginal[i].invoke(objOriginal, null);
                        if (objRetornoCopia != null)
                        {
                            Class clsParametro = null;
                            if (objRetornoCopia.getClass().isPrimitive() && objRetornoCopia instanceof Boolean)
                            {
                                clsParametro = Boolean.TYPE;
                            }
                            else if (objRetornoCopia.getClass().isPrimitive() && objRetornoCopia instanceof Character)
                            {
                                clsParametro = Character.TYPE;
                            }
                            else if (objRetornoCopia.getClass().isPrimitive() && objRetornoCopia instanceof Byte)
                            {
                                clsParametro = Byte.TYPE;
                            }
                            else if (objRetornoCopia.getClass().isPrimitive() && objRetornoCopia instanceof Short)
                            {
                                clsParametro = Short.TYPE;
                            }
                            else if (objRetornoCopia.getClass().isPrimitive() && objRetornoCopia instanceof Integer)
                            {
                                clsParametro = Integer.TYPE;
                            }
                            else if (objRetornoCopia.getClass().isPrimitive() && objRetornoCopia instanceof Long)
                            {
                                clsParametro = Long.TYPE;
                            }
                            else if (objRetornoCopia.getClass().isPrimitive() && objRetornoCopia instanceof Float)
                            {
                                clsParametro = Float.TYPE;
                            }
                            else if (objRetornoCopia.getClass().isPrimitive() && objRetornoCopia instanceof Double)
                            {
                                clsParametro = Double.TYPE;
                            }
                            else
                            {
                                if (objRetornoCopia != null)
                                {
                                    clsParametro = objRetornoCopia.getClass();
                                    String nomeClasse = clsParametro.getName();
                                    if (nomeClasse.equals("java.util.GregorianCalendar"))
                                    {
                                        clsParametro = Class.forName("java.util.Calendar");
                                    }
                                    else if (nomeClasse.equals("net.sf.hibernate.collection.Set")
                                            || nomeClasse.equals("org.hibernate.collection.PersistentSet"))
                                    {
                                        clsParametro = Class.forName("java.util.Set");
                                    }
                                    else if (nomeClasse.equals("java.sql.Timestamp"))
                                    {
                                        clsParametro = Class.forName("java.util.Date");
                                    }

                                }
                            }
                            if (objRetornoCopia != null)
                            {
                                if (clsParametro.toString().indexOf("_$$_") >= 0)
                                {
                                    // JAVASSIST
                                    clsParametro = Class.forName(clsParametro.toString().substring(6, clsParametro.toString().indexOf("_$$_")));
                                }
                                else if (clsParametro.toString().indexOf('$') >= 0)
                                {
                                    // CGLIB ?
                                    clsParametro = Class.forName(clsParametro.toString().substring(6, clsParametro.toString().indexOf('$')));
                                }
                                Method mtdCopia = clsClasseCopia.getMethod("set" + strNomeMetodoCopia.substring(3, strNomeMetodoCopia.length()), new Class[] { clsParametro });
                                mtdCopia.invoke(objCopia, new Object[] { objRetornoCopia });
                            }
                        }
                    }
                }
            }
        }
        catch (IllegalAccessException iae)
        {
            log.error("criar(Object, Object)", iae);

            // iae.printStackTrace();
        }
        catch (InvocationTargetException ite)
        {
            log.error("criar(Object, Object)", ite);

            // ite.printStackTrace();
        }
        catch (NoSuchMethodException nsme)
        {
            log.error("criar(Object, Object)", nsme);

            // nsme.printStackTrace();
        }
        catch (ClassNotFoundException cnfe)
        {
            log.error("criar(Object, Object)", cnfe);

            // cnfe.printStackTrace();
        }
        catch (Exception e)
        {
            log.error("criar(Object, Object) - Nome do m�todo copia: " + strNomeMetodoCopia, e);

            // e.printStackTrace();
        }

        if (log.isDebugEnabled())
        {
            log.debug("criar(Object, Object) - end");
        }
    }
}

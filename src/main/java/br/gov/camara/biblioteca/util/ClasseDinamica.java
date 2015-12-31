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

/*
 * Created on 22/07/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.gov.camara.biblioteca.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;

/**
 * @author P_6414
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ClasseDinamica
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(ClasseDinamica.class);

    private static final String ERRO_CLASSE_NAO_ENCONTRADA = "A classe especificada n�o foi encontrada";
    private static final String ERRO_ACESSO_POR_REFLEXAO = "Ocorreu um erro de acesso por reflex�o";
    private static final String ERRO_METODO_NAO_ENCONTRADO = "O m�todo especificado n�o foi encontrado";
    private static final String ERRO_PARAMETRO_NAO_APROPRIADO = "O par�metro especificado n�o � apropriado";
    private static final String ERRO_CLASSE_ABSTRATA_OU_INTERFACE = "A classe especificada (classe abstrata ou interface) n�o pode ser instanciada";
    private static final String ERRO_METODO_DISPAROU_EXCECAO = "O m�todo executado disparou uma exce��o";
    private static final String ERRO_VIOLACAO_SEGURANCA = "Ocorreu um erro de viola��o de seguran�a";

    public ClassLoader obterClassLoader()
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterClassLoader() - start");
        }

        // Look up the class loader to be used
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null)
        {
            classLoader = ClasseDinamica.class.getClassLoader();
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterClassLoader() - end");
        }
        return classLoader;
    }

    public Class obterClasse(String nomeClasse) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterClasse(String) - start");
        }

        // Attempt to load the specified class
        Class returnClass;
        try
        {
            returnClass = (obterClassLoader().loadClass(nomeClasse));
        }
        catch (ClassNotFoundException e)
        {
            if (log.isErrorEnabled())
            {
                log.error(ERRO_CLASSE_NAO_ENCONTRADA);
            }

            throw new CDException(ERRO_CLASSE_NAO_ENCONTRADA);
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterClasse(String) - end");
        }
        return returnClass;
    }

    public Object obterInstancia(String nomeClasse) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterInstancia(String) - start");
        }

        // Attempt to load the specified class
        Object returnObject;
        try
        {
            returnObject = (obterClasse(nomeClasse).newInstance());
        }
        catch (IllegalAccessException e)
        {
            if (log.isErrorEnabled())
            {
                log.error(ERRO_ACESSO_POR_REFLEXAO);
            }

            throw new CDException(ERRO_ACESSO_POR_REFLEXAO);
        }
        catch (InstantiationException e)
        {
            if (log.isErrorEnabled())
            {
                log.error(ERRO_CLASSE_ABSTRATA_OU_INTERFACE);
            }

            throw new CDException(ERRO_CLASSE_ABSTRATA_OU_INTERFACE);
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterInstancia(String) - end");
        }
        return returnObject;
    }

    public Object obterInstancia(String nomeClasse, ParametroDinamico parametroDinamico) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterInstancia(String, ParametroDinamico) - start");
        }

        Class objClasse;
        Object returnObject;
        try
        {
            objClasse = obterClasse(nomeClasse);
            returnObject = (objClasse.getConstructor(parametroDinamico.obterArrayClassesParametros()).newInstance(parametroDinamico.obterArrayParametros()));
        }
        catch (IllegalAccessException e)
        {
            if (log.isErrorEnabled())
            {
                log.error(ERRO_ACESSO_POR_REFLEXAO);
            }

            throw new CDException(ERRO_ACESSO_POR_REFLEXAO);
        }
        catch (InstantiationException e)
        {
            if (log.isErrorEnabled())
            {
                log.error(ERRO_CLASSE_ABSTRATA_OU_INTERFACE);
            }

            throw new CDException(ERRO_CLASSE_ABSTRATA_OU_INTERFACE);
        }
        catch (IllegalArgumentException e1)
        {
            if (log.isErrorEnabled())
            {
                log.error(ERRO_PARAMETRO_NAO_APROPRIADO);
            }

            throw new CDException(ERRO_PARAMETRO_NAO_APROPRIADO);
        }
        catch (SecurityException e1)
        {
            if (log.isErrorEnabled())
            {
                log.error(ERRO_VIOLACAO_SEGURANCA);
            }

            throw new CDException(ERRO_VIOLACAO_SEGURANCA);
        }
        catch (InvocationTargetException e1)
        {
            if (log.isErrorEnabled())
            {
                log.error(ERRO_METODO_DISPAROU_EXCECAO);
            }

            throw new CDException(ERRO_METODO_DISPAROU_EXCECAO);
        }
        catch (NoSuchMethodException e1)
        {
            if (log.isErrorEnabled())
            {
                log.error(ERRO_METODO_NAO_ENCONTRADO);
            }

            throw new CDException(ERRO_METODO_NAO_ENCONTRADO);
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterInstancia(String, ParametroDinamico) - end");
        }
        return returnObject;
    }

}

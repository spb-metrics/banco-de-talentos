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

/**
 * 
 */
package br.gov.camara.biblioteca.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author P_6414
 *
 */
public class CaminhoAbsoluto
{
    /**
     * Logger for this class
     */
    private static final Log log = LogFactory.getLog(CaminhoAbsoluto.class);

    private static ClassLoader obterClassLoader()
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterClassLoader() - start");
        }

        ClassLoader returnClassLoader = Thread.currentThread().getContextClassLoader();
        if (log.isDebugEnabled())
        {
            log.debug("obterClassLoader() - end - return value=" + returnClassLoader);
        }
        return returnClassLoader;
    }

    public static String obterReferencias(String strDestino)
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterReferencias(String=" + strDestino + ") - start");
        }

        String strCaminho = null;

        ClassLoader objClassloader = obterClassLoader();

        Enumeration objURLcaminhos = null;

        try
        {
            objURLcaminhos = objClassloader.getResources(strDestino);
        }
        catch (IOException io)
        {
            log.error("obterReferencias(String)", io);

            // Paciência
        }

        if (objURLcaminhos != null)
        {
            strCaminho = "";
            while (objURLcaminhos.hasMoreElements())
            {
                URL objURLCaminho = (URL) objURLcaminhos.nextElement();
                String strCaminhoAux = obter(objURLCaminho);
                if (strCaminhoAux != null)
                {
                    strCaminho += strCaminhoAux;
                    strCaminho += File.pathSeparator;
                }
            }
            if ("".equals(strCaminho))
            {
                strCaminho = null;
            }
            else
            {
                strCaminho = strCaminho.substring(0, strCaminho.length() - 1);
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterReferencias(String=" + strDestino + ") - end - return value=" + strCaminho);
        }
        return strCaminho;
    }

    public static String obter(String strDestino)
    {
        if (log.isDebugEnabled())
        {
            log.debug("obter(String=" + strDestino + ") - start");
        }

        String strCaminho = null;

        ClassLoader objClassloader = obterClassLoader();

        URL objURLCaminho = objClassloader.getResource(strDestino);

        // O destino foi encontrado ?
        if (objURLCaminho != null)
        {
            strCaminho = obter(objURLCaminho);
        }

        if (log.isDebugEnabled())
        {
            log.debug("obter(String=" + strDestino + ") - end - return value=" + strCaminho);
        }
        return strCaminho;
    }

    public static String obter(URL urlDestino)
    {
        if (log.isDebugEnabled())
        {
            log.debug("obter(URL=" + urlDestino + ") - start");
        }

        String strCaminho = null;

        // O destino foi encontrado ?
        if (urlDestino != null)
        {
            File filDestino = null;
            try
            {
                filDestino = new File(new URI(urlDestino.toString()));
            }
            catch (Throwable t)
            {
                log.warn("Não foi possível abrir o arquivo '" + urlDestino.toString() + "':" + t.getMessage());
            }
            if (filDestino == null)
            {
                String strPath = urlDestino.getPath();
                if (strPath.matches("^/.:/.*"))
                {
                    strPath = strPath.substring(1);
                }
                filDestino = new File(strPath);
            }

            if (filDestino != null && filDestino.exists())
            {
                strCaminho = obter(filDestino);
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug("obter(URL=" + urlDestino + ") - end - return value=" + strCaminho);
        }
        return strCaminho;
    }

    public static String obter(File filDestino)
    {
        if (log.isDebugEnabled())
        {
            log.debug("obter(File filDestino=" + filDestino + ") - start");
        }

        String strCaminho = null;

        try
        {
            strCaminho = filDestino.getCanonicalPath();
            if (log.isDebugEnabled())
            {
                log.debug("obter(File) - getAbsolutePath()  = " + filDestino.getAbsolutePath());
                log.debug("obter(File) - getCanonicalPath() = " + filDestino.getCanonicalPath());
            }
        }
        catch (IOException ioe)
        {
            // Ocorreu um erro... Paciência...
            log.error("Ocorreu um erro ao tentar obter o caminho Canonical para o arquivo " + filDestino.toString());
        }

        if (log.isDebugEnabled())
        {
            log.debug("obter(File filDestino=" + filDestino + ") - end - return value=" + strCaminho);
        }
        return strCaminho;
    }

    public static String obterArquivos(String strDiretorio)
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterArquivos(String strDiretorio=" + strDiretorio + ") - start");
        }

        String returnString = obterArquivos(strDiretorio, null);
        if (log.isDebugEnabled())
        {
            log.debug("obterArquivos(String strDiretorio=" + strDiretorio + ") - end - return value=" + returnString);
        }
        return returnString;
    }

    public static String obterArquivos(String strDiretorio, String strFiltro)
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterArquivos(String strDiretorio=" + strDiretorio + ", String strFiltro=" + strFiltro + ") - start");
        }

        String strCaminhoRetorno = null;

        String strCaminho = obter(strDiretorio);
        if (strCaminho != null)
        {
            File filDiretorio = new File(strCaminho);
            if (filDiretorio.isDirectory())
            {
                strCaminhoRetorno = "";

                File[] filArquivos = filDiretorio.listFiles();
                for (int i = 0; i < filArquivos.length; i++)
                {
                    try
                    {
                        String strCaminhoCanonical = filArquivos[i].getCanonicalPath();
                        if (strFiltro == null || strCaminhoCanonical.indexOf(strFiltro) > -1)
                        {
                            strCaminhoRetorno += strCaminhoCanonical;
                            strCaminhoRetorno += File.pathSeparator;
                        }
                    }
                    catch (IOException ioe)
                    {
                        log.error("obterArquivos(String, String)", ioe);

                        // Ocorreu um erro recuperando o caminho canonical especificado. Paciência...
                    }
                }

                if ("".equals(strCaminhoRetorno))
                {
                    strCaminhoRetorno = null;
                }
                else
                {
                    strCaminhoRetorno = strCaminhoRetorno.substring(0, strCaminhoRetorno.length() - 1);
                }

            }
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterArquivos(String strDiretorio=" + strDiretorio + ", String strFiltro=" + strFiltro + ") - end - return value=" + strCaminhoRetorno);
        }
        return strCaminhoRetorno;
    }
}

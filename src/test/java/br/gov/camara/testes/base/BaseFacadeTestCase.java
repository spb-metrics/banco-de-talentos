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

package br.gov.camara.testes.base;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import br.gov.camara.exception.CDException;
import br.gov.camara.hibernate.HibernateUtilCD;

public abstract class BaseFacadeTestCase extends BaseNegocioTestCase
{
    private static HibernateUtilCD hibernateUtilCD = null;
    private static boolean blnAmbientePreparado = false;

    protected synchronized static void inicializarHibernate(String strNomeArquivoConfiguracaoHibernate) throws CDException
    {
    	if (hibernateUtilCD!=null)
    		return;
    	
        if (!blnAmbientePreparado)
        {
            prepararHibernate();
        }

        hibernateUtilCD = new HibernateUtilCD();
        hibernateUtilCD.inicializar(strNomeArquivoConfiguracaoHibernate);
    }

    protected static void finalizarHibernate() throws CDException
    {
        if (hibernateUtilCD != null)
        {
            hibernateUtilCD.finalizar();
            hibernateUtilCD = null;
        }
    }

    private static void prepararHibernate()
    {
        // Adicionar caminho dos arquivos MODULO-hibernate.xml do projeto web no classpath
        try
        {
            File file = new File(""); // Caminho RAIZ do PROJETO

            StringBuffer caminhoArquivosModuloHibernateXml = new StringBuffer();
            // Caminho raiz do projeto
            caminhoArquivosModuloHibernateXml.append(file.getAbsolutePath().replaceAll("\\\\", "/"));
            caminhoArquivosModuloHibernateXml.append("/");
            // Caminho para os arquivos do m�dulo web
            // caminhoArquivosModuloHibernateXml.append("web"); // => estrutura antiga
            caminhoArquivosModuloHibernateXml.append("src/main/webapp"); // estrutura maven
            caminhoArquivosModuloHibernateXml.append("/");
            // Diret�rio com os arquivos de configura��o dos m�dulos
            caminhoArquivosModuloHibernateXml.append("/WEB-INF/config/");

            File fileProjetoWeb = new File(caminhoArquivosModuloHibernateXml.toString());
            if (fileProjetoWeb.exists())
            {
                ClassLoader cl = new URLClassLoader(new URL[] { new URL("file", "", fileProjetoWeb.getPath().replaceAll("\\\\", "/") + "/") }, Thread.currentThread().getContextClassLoader());
                Thread.currentThread().setContextClassLoader(cl);
            }
        }
        catch (Exception mue)
        {
            // Paci�ncia, pois n�o deveria acontecer...
        }

        // Seta as propriedades necess�rias para o hibernate-config
        System.setProperty("org.xml.sax.driver", "org.apache.crimson.parser.XMLReaderImpl");
        System.setProperty("delimitadoraSessaoTransacaoImpl", "br.gov.camara.hibernate.JUnitDelimitadora");

        blnAmbientePreparado = true;
    }
}

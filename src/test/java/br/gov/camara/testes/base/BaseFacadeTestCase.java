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
            // Caminho para os arquivos do módulo web
            // caminhoArquivosModuloHibernateXml.append("web"); // => estrutura antiga
            caminhoArquivosModuloHibernateXml.append("src/main/webapp"); // estrutura maven
            caminhoArquivosModuloHibernateXml.append("/");
            // Diretório com os arquivos de configuração dos módulos
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
            // Paciência, pois não deveria acontecer...
        }

        // Seta as propriedades necessárias para o hibernate-config
        System.setProperty("org.xml.sax.driver", "org.apache.crimson.parser.XMLReaderImpl");
        System.setProperty("delimitadoraSessaoTransacaoImpl", "br.gov.camara.hibernate.JUnitDelimitadora");

        blnAmbientePreparado = true;
    }
}

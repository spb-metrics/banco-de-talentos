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

package br.gov.camara.util.relatorio.jasper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.biblioteca.util.CaminhoAbsoluto;
import br.gov.camara.exception.CDException;
import br.gov.camara.util.relatorio.ExibicaoDocumento;
import br.gov.camara.util.relatorio.Relatorio;
import br.gov.camara.util.relatorio.exception.RelatorioException;

/**
 * Implementa��o de relat�rio usando o framework JasperReports
 */
public class RelatorioJasper extends Relatorio
{
    /**
     * Logger for this class
     */
    private static final Log log = LogFactory.getLog(RelatorioJasper.class);

    // Par�metros do relat�rio
    Map mapParametros = new HashMap();

    // Vari�veis para armazenar a configura��o dos relat�rios
    Digester objDigester;

    Map mapConfiguracoes = new HashMap();

    String strClasspathAtual;

    String strValores;

    String strUtilizacao;

    public void adicionarClasspath(String strClasspath)
    {
        if (log.isDebugEnabled())
        {
            log.debug("adicionarClasspath(String=" + strClasspath + ") - start");
        }

        this.strClasspathAtual = strClasspath;
        this.strValores = "";

        if (log.isDebugEnabled())
        {
            log.debug("adicionarClasspath(String=" + strClasspath + ") - end");
        }
    }

    public void adicionarEntradas(String strValores)
    {
        if (log.isDebugEnabled())
        {
            log.debug("adicionarEntradas(String=" + strValores + ") - start");
        }

        String diretorio = "";
        if ("classes".equals(strValores))
        {
            diretorio = this.strLocalizacao.substring(0, this.strLocalizacao.indexOf("WEB-INF")) + "WEB-INF\\";
        }
        else if (this.strLocalizacao.indexOf(".ear") > 0)
        {
            diretorio = this.strLocalizacao.substring(0, this.strLocalizacao.indexOf(".ear")) + ".ear\\";
        }
        this.strValores += diretorio + strValores + System.getProperty("path.separator");
        this.mapConfiguracoes.put(this.strClasspathAtual, this.strValores);

        if (log.isDebugEnabled())
        {
            log.debug("adicionarEntradas(String=" + strValores + ") - end");
        }
    }

    public void atribuirUtilizacao(String strUtilizacao)
    {
        if (log.isDebugEnabled())
        {
            log.debug("atribuirUtilizacao(String=" + strUtilizacao + ") - start");
        }

        this.strUtilizacao = strUtilizacao;

        if (log.isDebugEnabled())
        {
            log.debug("atribuirUtilizacao(String=" + strUtilizacao + ") - end");
        }
    }

    private void atribuirClasspath()
    {
        if (log.isDebugEnabled())
        {
            log.debug("atribuirClasspath() - start");
        }

        String strCompileClassPath = "";
        String strCaminhoAuxiliar;

        // Ambiente de desenvolvimento utilizando o devloader (=sim) ou produ��o ???
        if ("sim".equals(System.getProperty("java.devloader")))
        {
            // Estamos no ambiente de desenvolvimento utilizando o 
            // Devloader do Sysdeo Tomcat Plugin CUSTOMIZADO

            // Obt�m todas as bibliotecas
            String strBasePath = CaminhoAbsoluto.obterReferencias("../../WEB-INF/classes");
            if (strBasePath != null)
            {
                StringTokenizer stkBasePath = new StringTokenizer(strBasePath, File.pathSeparator);
                while (stkBasePath.hasMoreTokens())
                {
                    String strToken = stkBasePath.nextToken();
                    strToken = strToken.replace('\\', '/');
                    strToken = strToken.substring(0, strToken.indexOf("/WEB-INF/classes"));

                    // only on windows 
                    if (strToken.charAt(1) == ':')
                    {
                        strToken = strToken.substring(2);
                    }
                    strToken = strToken.replace('/', '.');
                    strCaminhoAuxiliar = System.getProperty("java.devloader" + strToken.toLowerCase());

                    if (strCaminhoAuxiliar != null)
                    {
                        if (!"".equals(strCaminhoAuxiliar))
                        {
                            strCompileClassPath += strCaminhoAuxiliar;
                            strCompileClassPath += File.pathSeparator;
                            break;
                        }
                    }
                }
            }
        }
        else
        {
            // Obt�m todas as bibliotecas
            strCaminhoAuxiliar = CaminhoAbsoluto.obterArquivos("../../WEB-INF/lib", ".jar");
            if (strCaminhoAuxiliar != null)
            {
                strCompileClassPath += strCaminhoAuxiliar;
                strCompileClassPath += File.pathSeparator;
            }

            // Obt�m todos os classpath
            strCaminhoAuxiliar = CaminhoAbsoluto.obterReferencias("");
            if (strCaminhoAuxiliar != null)
            {
                strCompileClassPath += strCaminhoAuxiliar;
                strCompileClassPath += File.pathSeparator;
            }

            // Obt�m o classpath atual
            strCaminhoAuxiliar = System.getProperty("java.class.path");
            if (strCaminhoAuxiliar != null)
            {
                if (!"".equals(strCaminhoAuxiliar))
                {
                    strCompileClassPath += strCaminhoAuxiliar;
                    strCompileClassPath += File.pathSeparator;
                }
            }
        }

        // Atribui a vari�vel de sistema do jasper reports
        System.setProperty("jasper.reports.compile.class.path", strCompileClassPath);

        String strTempPath;
        strTempPath = CaminhoAbsoluto.obter("../../WEB-INF/classes");
        System.setProperty("jasper.reports.compile.temp", strTempPath);

        if (log.isDebugEnabled())
        {
            log.debug("atribuirClasspath() - end");
        }
    }

    private void configurar() throws RelatorioException
    {
        if (log.isDebugEnabled())
        {
            log.debug("configurar() - start");
        }

        // Configura os par�metros do relat�rio
        mapParametros.clear();
        mapParametros.put("Titulo", super.getTitulo());
        // atribuirClasspath();

        if (log.isDebugEnabled())
        {
            log.debug("configurar() - end");
        }
    }

    /**
     * Constr�i o objeto
     * 
     * @param strLocalizacao
     *            Localiza��o do arquivo de relat�rio
     * @param strRelatorio
     *            Relat�rio a ser utilizado
     */
    public RelatorioJasper(String strLocalizacao, String strRelatorio, boolean lerArquivo) throws RelatorioException
    {
        super(strLocalizacao, strRelatorio, lerArquivo);
        configurar();
    }

    /**
     * Obt�m o relat�rio desejado em formato PDF
     * 
     * @param colDados
     *            Collection contendo os dados a serem exibidos
     */
    public byte[] obterPDF(Collection colDados) throws RelatorioException
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterPDF(Collection=" + colDados + ") - start");
        }

        try
        {
            Iterator nomeParametros = getParametros().keySet().iterator();

            while (nomeParametros.hasNext())
            {
                String key = (String) nomeParametros.next();
                mapParametros.put(key, getParametros().get(key));
            }

            JasperPrint objJasperPrint = JasperFillManager.fillReport(compilaRelatorio(super.strRelatorio), mapParametros, new JRBeanCollectionDataSource(colDados)); //JasperManager.fillReport(compilaRelatorio(super.strRelatorio), mapParametros, new JRBeanCollectionDataSource(colDados));

            byte[] returnbyteArray = JasperExportManager.exportReportToPdf(objJasperPrint);
            if (log.isDebugEnabled())
            {
                log.debug("obterPDF(Collection=" + colDados + ") - end - return value=" + returnbyteArray);
            }
            return returnbyteArray;
        }
        catch (JRException jre)
        {
            log.error("obterPDF(Collection)", jre);

            log.error("obterPDF(Collection)", jre);
            new RelatorioException("Ocorreu um erro ao gerar o relat�rio");
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterPDF(Collection=" + colDados + ") - end - return value=" + null);
        }
        return null;
    }

    /**
     * Obt�m o relat�rio desejado em formato PDF apartir de um maparray
     * 
     * @param colDados
     *            Collection contendo os dados a serem exibidos
     */
    public byte[] obterPDF(Object[] array) throws RelatorioException
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterPDF(Object[]=" + array + ") - start");
        }

        try
        {
            Iterator nomeParametros = getParametros().keySet().iterator();

            while (nomeParametros.hasNext())
            {
                String key = (String) nomeParametros.next();
                mapParametros.put(key, getParametros().get(key));
            }

            JasperPrint objJasperPrint = JasperFillManager.fillReport(compilaRelatorio(super.strRelatorio), mapParametros, new JRMapArrayDataSource(array)); //JasperManager.fillReport(compilaRelatorio(super.strRelatorio), mapParametros, new JRMapArrayDataSource(array));

            byte[] returnbyteArray = JasperExportManager.exportReportToPdf(objJasperPrint);
            if (log.isDebugEnabled())
            {
                log.debug("obterPDF(Object[]=" + array + ") - end - return value=" + returnbyteArray);
            }
            return returnbyteArray;
        }
        catch (JRException jre)
        {
            log.error("obterPDF(Object[])", jre);

            log.error("obterPDF(Object[])", jre);
            new RelatorioException("Ocorreu um erro ao gerar o relat�rio");
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterPDF(Object[]=" + array + ") - end - return value=" + null);
        }
        return null;
    }

    /**
     * Obt�m o relat�rio desejado em formato PDF, para ser utilizado em
     * relat�rios baseados em report query
     * 
     * @param connection
     *            Connection conex�o JDBC
     *  
     */
    public byte[] obterPDF(Connection connection) throws RelatorioException
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterPDF(Connection=" + connection + ") - start");
        }

        try
        {
            Iterator nomeParametros = getParametros().keySet().iterator();

            while (nomeParametros.hasNext())
            {
                String key = (String) nomeParametros.next();
                mapParametros.put(key, getParametros().get(key));
            }

            JasperPrint objJasperPrint = JasperFillManager.fillReport(compilaRelatorio(super.strRelatorio), mapParametros, connection); //JasperManager.fillReport(compilaRelatorio(super.strRelatorio), mapParametros, connection);

            byte[] returnbyteArray = JasperExportManager.exportReportToPdf(objJasperPrint);
            if (log.isDebugEnabled())
            {
                log.debug("obterPDF(Connection=" + connection + ") - end - return value=" + returnbyteArray);
            }
            return returnbyteArray;
        }
        catch (JRException jre)
        {
            log.error("obterPDF(Connection)", jre);

            log.error("obterPDF(Connection)", jre);
            new RelatorioException("Ocorreu um erro ao gerar o relat�rio");
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterPDF(Connection=" + connection + ") - end - return value=" + null);
        }
        return null;
    }

    /**
     * Obt�m o relat�rio desejado em formato HTML que � armazenado no arquivo
     * indicado no parametro nomeArquivoHTML as imagens utilizadas
     * correspondentes ao relat�rio s�o colocadas dentro de um diret�rio com o
     * mesmo nome do arquivo HTML gerado com o sufixo _files acrescentado
     * 
     * @param colDados
     *            Collection contendo os dados a serem exibidos
     * @param nomeArquivoHTML
     *            String nome do arquivo HTML onde ser� gerado o relat�rio
     */
    public void obterHTML(Collection colDados, String nomeArquivoHTML) throws RelatorioException
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterHTML(Collection=" + colDados + ", String=" + nomeArquivoHTML + ") - start");
        }

        try
        {

            Iterator nomeParametros = getParametros().keySet().iterator();
            while (nomeParametros.hasNext())
            {
                String key = (String) nomeParametros.next();
                mapParametros.put(key, getParametros().get(key));
            }

            JasperPrint objJasperPrint = JasperFillManager.fillReport(compilaRelatorio(super.strRelatorio), mapParametros, new JRBeanCollectionDataSource(colDados)); // JasperManager.fillReport(compilaRelatorio(super.strRelatorio), mapParametros, new JRBeanCollectionDataSource(colDados));
            JasperExportManager.exportReportToHtmlFile(objJasperPrint, nomeArquivoHTML);
        }
        catch (JRException jre)
        {
            log.error("obterHTML(Collection, String)", jre);

            log.error("obterHTML(Collection, String)", jre);
            new RelatorioException("Ocorreu um erro ao gerar o relat�rio");
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterHTML(Collection=" + colDados + ", String=" + nomeArquivoHTML + ") - end");
        }
    }

    /**
     * Obt�m o relat�rio desejado em formato HTML que � armazenado no arquivo
     * indicado no parametro nomeArquivoHTML as imagens utilizadas
     * correspondentes ao relat�rio s�o colocadas dentro de um diret�rio com o
     * mesmo nome do arquivo HTML gerado com o sufixo _files acrescentado, para
     * ser utilizado em relat�rios baseados em report query
     * 
     * @param connection
     *            Connection conex�o JDBC
     * @param nomeArquivoHTML
     *            String nome do arquivo HTML onde ser� gerado o relat�rio
     */
    public void obterHTML(Connection connection, String nomeArquivoHTML) throws RelatorioException
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterHTML(Connection=" + connection + ", String=" + nomeArquivoHTML + ") - start");
        }

        try
        {

            Iterator nomeParametros = getParametros().keySet().iterator();
            while (nomeParametros.hasNext())
            {
                String key = (String) nomeParametros.next();
                mapParametros.put(key, getParametros().get(key));
            }

            JasperPrint objJasperPrint = JasperFillManager.fillReport(compilaRelatorio(super.strRelatorio), mapParametros, connection); //JasperManager.fillReport(compilaRelatorio(super.strRelatorio), mapParametros, connection);
            JasperExportManager.exportReportToHtmlFile(objJasperPrint, nomeArquivoHTML);
        }
        catch (JRException jre)
        {
            log.error("obterHTML(Connection, String)", jre);

            log.error("obterHTML(Connection, String)", jre);
            new RelatorioException("Ocorreu um erro ao gerar o relat�rio");
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterHTML(Connection=" + connection + ", String=" + nomeArquivoHTML + ") - end");
        }
    }

    private JasperReport compilaRelatorio(String nomeSemExtensao) throws RelatorioException
    {
        if (log.isDebugEnabled())
        {
            log.debug("compilaRelatorio(String=" + nomeSemExtensao + ") - start");
        }

        JasperReport objJasperReport = null;

        try
        {
            String strCaminhoJasperDesign = CaminhoAbsoluto.obter(super.strLocalizacao + File.separator + nomeSemExtensao + ".xml");
            File filJasperDesign = new File(strCaminhoJasperDesign);

            // Determina o caminho do arquivo compilado do relat�rio
            String strCaminhoJasperCompiled = strCaminhoJasperDesign.replaceAll(".xml", ".jasper");
            File filJasperCompiled = new File(strCaminhoJasperCompiled);

            // � necess�rio recompilar o arquivo de design do relat�rio ?
            if (filJasperCompiled == null || !filJasperCompiled.exists() || (filJasperDesign.lastModified() > filJasperCompiled.lastModified()))
            {
                // S� define o classpath do jasper se for necess�rio compilar
                atribuirClasspath();

                // Compila o relat�rio
                InputStream insRelatorio = filJasperDesign.toURL().openStream();
                JasperDesign jasperDesign = JRXmlLoader.load(insRelatorio);
                JasperCompileManager.compileReportToFile(jasperDesign, strCaminhoJasperCompiled);
            }

            // Carrega o relat�rio compilado
            objJasperReport = (JasperReport) JRLoader.loadObject(filJasperCompiled);
        }
        catch (JRException e)
        {
            log.error("compilaRelatorio(String)", e);

            throw new RelatorioException("Ocorreu um erro ao gerar o relat�rio: " + e.getMessage());
        }
        catch (IOException e)
        {
            log.error("compilaRelatorio(String)", e);

            throw new RelatorioException("Ocorreu um erro ao gerar o relat�rio: " + e.getMessage());
        }
        catch (Exception e)
        {
            log.error("compilaRelatorio(String)", e);

            throw new RelatorioException("Ocorreu um erro ao gerar o relat�rio: " + e.getMessage());
        }

        if (log.isDebugEnabled())
        {
            log.debug("compilaRelatorio(String=" + nomeSemExtensao + ") - end - return value=" + objJasperReport);
        }
        return objJasperReport;
    }

    public void adicionaSubRelatorio(String nomeSemExtensao) throws RelatorioException
    {
        if (log.isDebugEnabled())
        {
            log.debug("adicionaSubRelatorio(String=" + nomeSemExtensao + ") - start");
        }

        getParametros().put(nomeSemExtensao, compilaRelatorio(nomeSemExtensao));

        if (log.isDebugEnabled())
        {
            log.debug("adicionaSubRelatorio(String=" + nomeSemExtensao + ") - end");
        }
    }

    public void adicionaSubRelatorio(String nomeParametro, String nomeSemExtensao) throws RelatorioException
    {
        if (log.isDebugEnabled())
        {
            log.debug("adicionaSubRelatorio(String=" + nomeParametro + ", String=" + nomeSemExtensao + ") - start");
        }

        getParametros().put(nomeParametro, compilaRelatorio(nomeSemExtensao));

        if (log.isDebugEnabled())
        {
            log.debug("adicionaSubRelatorio(String=" + nomeParametro + ", String=" + nomeSemExtensao + ") - end");
        }
    }

    public void adicionaParametro(String nomeParametro, Object parametro) throws RelatorioException
    {
        if (log.isDebugEnabled())
        {
            log.debug("adicionaParametro(String=" + nomeParametro + ", Object=" + parametro + ") - start");
        }

        getParametros().put(nomeParametro, parametro);

        if (log.isDebugEnabled())
        {
            log.debug("adicionaParametro(String=" + nomeParametro + ", Object=" + parametro + ") - end");
        }
    }

    public static void gerarExibicaoPDFParaDownload(HttpServletResponse response, byte[] pdf) throws CDException
    {
        ExibicaoDocumento exibicao = new ExibicaoDocumento();
        exibicao.setAsAttachment(true).setTipoConteudo("application/pdf").setExtensaoArquivo(".pdf").gerarExibicaoDocumento(response, pdf);
    }

    public static void gerarExibicaoPDF(HttpServletResponse response, byte[] pdf) throws CDException
    {
        ExibicaoDocumento exibicao = new ExibicaoDocumento();
        exibicao.setAsAttachment(false).setTipoConteudo("application/pdf").setExtensaoArquivo(".pdf").gerarExibicaoDocumento(response, pdf);
    }

}
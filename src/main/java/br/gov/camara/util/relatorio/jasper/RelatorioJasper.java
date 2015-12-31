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
 * Implementação de relatório usando o framework JasperReports
 */
public class RelatorioJasper extends Relatorio
{
    /**
     * Logger for this class
     */
    private static final Log log = LogFactory.getLog(RelatorioJasper.class);

    // Parâmetros do relatório
    Map mapParametros = new HashMap();

    // Variáveis para armazenar a configuração dos relatórios
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

        // Ambiente de desenvolvimento utilizando o devloader (=sim) ou produção ???
        if ("sim".equals(System.getProperty("java.devloader")))
        {
            // Estamos no ambiente de desenvolvimento utilizando o 
            // Devloader do Sysdeo Tomcat Plugin CUSTOMIZADO

            // Obtém todas as bibliotecas
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
            // Obtém todas as bibliotecas
            strCaminhoAuxiliar = CaminhoAbsoluto.obterArquivos("../../WEB-INF/lib", ".jar");
            if (strCaminhoAuxiliar != null)
            {
                strCompileClassPath += strCaminhoAuxiliar;
                strCompileClassPath += File.pathSeparator;
            }

            // Obtém todos os classpath
            strCaminhoAuxiliar = CaminhoAbsoluto.obterReferencias("");
            if (strCaminhoAuxiliar != null)
            {
                strCompileClassPath += strCaminhoAuxiliar;
                strCompileClassPath += File.pathSeparator;
            }

            // Obtém o classpath atual
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

        // Atribui a variável de sistema do jasper reports
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

        // Configura os parâmetros do relatório
        mapParametros.clear();
        mapParametros.put("Titulo", super.getTitulo());
        // atribuirClasspath();

        if (log.isDebugEnabled())
        {
            log.debug("configurar() - end");
        }
    }

    /**
     * Constrói o objeto
     * 
     * @param strLocalizacao
     *            Localização do arquivo de relatório
     * @param strRelatorio
     *            Relatório a ser utilizado
     */
    public RelatorioJasper(String strLocalizacao, String strRelatorio, boolean lerArquivo) throws RelatorioException
    {
        super(strLocalizacao, strRelatorio, lerArquivo);
        configurar();
    }

    /**
     * Obtém o relatório desejado em formato PDF
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
            new RelatorioException("Ocorreu um erro ao gerar o relatório");
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterPDF(Collection=" + colDados + ") - end - return value=" + null);
        }
        return null;
    }

    /**
     * Obtém o relatório desejado em formato PDF apartir de um maparray
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
            new RelatorioException("Ocorreu um erro ao gerar o relatório");
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterPDF(Object[]=" + array + ") - end - return value=" + null);
        }
        return null;
    }

    /**
     * Obtém o relatório desejado em formato PDF, para ser utilizado em
     * relatórios baseados em report query
     * 
     * @param connection
     *            Connection conexão JDBC
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
            new RelatorioException("Ocorreu um erro ao gerar o relatório");
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterPDF(Connection=" + connection + ") - end - return value=" + null);
        }
        return null;
    }

    /**
     * Obtém o relatório desejado em formato HTML que é armazenado no arquivo
     * indicado no parametro nomeArquivoHTML as imagens utilizadas
     * correspondentes ao relatório são colocadas dentro de um diretório com o
     * mesmo nome do arquivo HTML gerado com o sufixo _files acrescentado
     * 
     * @param colDados
     *            Collection contendo os dados a serem exibidos
     * @param nomeArquivoHTML
     *            String nome do arquivo HTML onde será gerado o relatório
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
            new RelatorioException("Ocorreu um erro ao gerar o relatório");
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterHTML(Collection=" + colDados + ", String=" + nomeArquivoHTML + ") - end");
        }
    }

    /**
     * Obtém o relatório desejado em formato HTML que é armazenado no arquivo
     * indicado no parametro nomeArquivoHTML as imagens utilizadas
     * correspondentes ao relatório são colocadas dentro de um diretório com o
     * mesmo nome do arquivo HTML gerado com o sufixo _files acrescentado, para
     * ser utilizado em relatórios baseados em report query
     * 
     * @param connection
     *            Connection conexão JDBC
     * @param nomeArquivoHTML
     *            String nome do arquivo HTML onde será gerado o relatório
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
            new RelatorioException("Ocorreu um erro ao gerar o relatório");
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

            // Determina o caminho do arquivo compilado do relatório
            String strCaminhoJasperCompiled = strCaminhoJasperDesign.replaceAll(".xml", ".jasper");
            File filJasperCompiled = new File(strCaminhoJasperCompiled);

            // É necessário recompilar o arquivo de design do relatório ?
            if (filJasperCompiled == null || !filJasperCompiled.exists() || (filJasperDesign.lastModified() > filJasperCompiled.lastModified()))
            {
                // Só define o classpath do jasper se for necessário compilar
                atribuirClasspath();

                // Compila o relatório
                InputStream insRelatorio = filJasperDesign.toURL().openStream();
                JasperDesign jasperDesign = JRXmlLoader.load(insRelatorio);
                JasperCompileManager.compileReportToFile(jasperDesign, strCaminhoJasperCompiled);
            }

            // Carrega o relatório compilado
            objJasperReport = (JasperReport) JRLoader.loadObject(filJasperCompiled);
        }
        catch (JRException e)
        {
            log.error("compilaRelatorio(String)", e);

            throw new RelatorioException("Ocorreu um erro ao gerar o relatório: " + e.getMessage());
        }
        catch (IOException e)
        {
            log.error("compilaRelatorio(String)", e);

            throw new RelatorioException("Ocorreu um erro ao gerar o relatório: " + e.getMessage());
        }
        catch (Exception e)
        {
            log.error("compilaRelatorio(String)", e);

            throw new RelatorioException("Ocorreu um erro ao gerar o relatório: " + e.getMessage());
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
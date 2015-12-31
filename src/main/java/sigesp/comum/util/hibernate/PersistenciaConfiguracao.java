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

/*
 * Created on 10/08/2004 To change the template for this generated file go to Window - Preferences - Java - Code Style -
 * Code Templates
 */
package sigesp.comum.util.hibernate;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.util.XMLHelper;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import br.gov.camara.hibernate.exception.HibernateExceptionCD;

/*
 * import org.xml.sax.SAXException;
 */
/**
 * DOCUMENT ME!
 * 
 * @author p_999144 To change the template for this generated type comment go to Window - Preferences - Java - Code
 *         Style - Code Templates
 */
public class PersistenciaConfiguracao
{
    private static Log log = LogFactory.getFactory().getInstance(PersistenciaConfiguracao.class);

    private final String DEFAULT_CONFIGURACAO_HIBERNATE = "hibernate-config.xml";

    /**
     * Cria um novo objeto PersistenciaConfiguracao
     */
    public PersistenciaConfiguracao()
    {}

    /**
     * DOCUMENTAR!
     * 
     * @param arquivo DOCUMENTAR!
     * @return DOCUMENTAR!
     * @throws HibernateExceptionCD DOCUMENTAR!
     */
    public synchronized ColecaoDadosConexao doConfiguracao(String arquivo) throws HibernateExceptionCD
    {
        if (log.isTraceEnabled())
        {
            log.trace("doConfiguracao(String arquivo = " + arquivo + ") - inicio");
        }

        try
        {
            if (arquivo == null)
            {
                arquivo = this.DEFAULT_CONFIGURACAO_HIBERNATE;
            }

            // Cria referência para o arquivo de configuração
            Document document = this.obterReferenciaDocument(arquivo);

            // Recupera os dados do arquivo de configuração e dos arquivos de mapeamento
            ColecaoDadosConexao configuracoesConexoes = this.obterConexoesEMapeamentos(document);

            if (log.isTraceEnabled())
            {
                log.trace("doConfiguracao(String) - fim - return value = " + configuracoesConexoes);
            }
            return configuracoesConexoes;
        }
        catch (HibernateExceptionCD e)
        {
            log.error("Erro: PersistenciaConfiguracao - doConfiguracao", e);
            throw new HibernateExceptionCD("Erro: PersistenciaConfiguracao - doConfiguracao: " + e.getMessage());
        }
    }

    /**
     * Obtém uma referência (Document) para o arquivo de configuração do HibernatePlugin
     * 
     * @param arquivo Nome do arquivo de configuração do HibernatePlugin
     * @return Retorna o Document para o arquivo de configuração do HibernatePlugin
     * @throws HibernateExceptionCD
     */
    private Document obterReferenciaDocument(String arquivo) throws HibernateExceptionCD
    {
        if (log.isTraceEnabled())
        {
            log.trace("getDocumento(String arquivo = " + arquivo + ") - inicio");
        }

        try
        {
            // Cria referência para o arquivo de configuração
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(arquivo);
            if (stream == null)
            {
                log.error("Não foi possível localizar o seguinte arquivo: " + arquivo);
                throw new HibernateExceptionCD("Não foi possível localizar o seguinte arquivo: " + arquivo);
            }

            Document document = null;
            List errors = new ArrayList();
            SAXReader sr = (new XMLHelper()).createSAXReader(arquivo, errors, XMLHelper.DEFAULT_DTD_RESOLVER);
            sr.setValidation(false);

            int sAXReaderImp = 1;
            while (sAXReaderImp > 0)
            {
                try
                {
                    document = sr.read(new InputSource(stream));
                    sAXReaderImp = 0;
                }
                catch (DocumentException de)
                {
                    if (de.getNestedException() instanceof SAXException)
                    {
                        switch (sAXReaderImp)
                        {
                            case 1: // Crimson
                                System.setProperty("org.xml.sax.driver", "org.apache.crimson.parser.XMLReaderImpl");
                                break;
                            case 2: // Oracle
                                System.setProperty("org.xml.sax.driver", "oracle.xml.parser.v2.SAXParser");
                                break;
                            case 3: // Ælfred
                                System.setProperty("org.xml.sax.driver", "gnu.xml.aelfred2.XmlReader");
                                break;
                            case 4: // Piccolo
                                System.setProperty("org.xml.sax.driver", "com.bluecast.xml.Piccolo");
                                break;
                            default:
                                throw de;
                        }
                        sAXReaderImp++;
                    }
                }
            }

            if (errors.size() != 0)
            {
                throw new HibernateExceptionCD("configuração inválida", (Throwable) errors.get(0));
            }

            stream.close();

            if (log.isTraceEnabled())
            {
                log.trace("getDocumento(String, InputStream) - fim - return value = " + document);
            }
            return document;
        }
        catch (DocumentException e)
        {
            log.error("Erro: PersistenciaConfiguracao - getDocumento", e);
            throw new HibernateExceptionCD("Erro: PersistenciaConfiguracao - getDocumento: " + e.getMessage());
        }
        catch (IOException e)
        {
            log.error("Erro: PersistenciaConfiguracao - getDocumento", e);
            throw new HibernateExceptionCD("Erro: PersistenciaConfiguracao - getDocumento: " + e.getMessage());
        }
    }

    /**
     * Lê o Caminho para os módulos de mapeamento
     * 
     * @param config Documento XML
     * @return String contendo o caminho especificado
     * @throws HibernateExceptionCD
     */
    private String obterCaminhoModulosMapeamento(Document config) throws HibernateExceptionCD
    {
        if (log.isTraceEnabled())
        {
            log.trace("getLocalArquivosCfg(Document config = " + config + ") - inicio");
        }

        try
        {
            String caminhoModulosMapeamento = null;
            Iterator cfgElementos = config.getRootElement().element("configuracao").elementIterator();

            while (cfgElementos.hasNext())
            {
                Element elemento = (Element) cfgElementos.next();
                Attribute attribute = elemento.attribute("caminhoModulosMapeamento");
                if (attribute != null)
                {
                    caminhoModulosMapeamento = attribute.getValue();
                }
            }

            if (log.isTraceEnabled())
            {
                log.trace("getLocalArquivosCfg(Document) - fim - return value = " + caminhoModulosMapeamento);
            }
            return caminhoModulosMapeamento;
        }
        catch (Exception e)
        {
            log.error("Erro: PersistenciaConfiguracao - getLocalArquivosCfg", e);
            throw new HibernateExceptionCD("Erro: PersistenciaConfiguracao - getLocalArquivosCfg: " + e.getMessage());
        }
    }

    private ColecaoDadosConexao obterConexoesEMapeamentos(Document config) throws HibernateExceptionCD
    {
        if (log.isTraceEnabled())
        {
            log.trace("doBuildConfiguracao(Document config = " + config + ") - inicio");
        }

        try
        {
            ColecaoDadosConexao configuracoesConexoes = new ColecaoDadosConexao();

            // Recupera dados
            String caminhoModulosMapeamento = this.obterCaminhoModulosMapeamento(config);

            this.obterConexoesPropriedadesSubstituicoesModulosmapeamento(config, configuracoesConexoes);
            this.obterMapeamentos(configuracoesConexoes, caminhoModulosMapeamento);

            if (log.isTraceEnabled())
            {
                log.trace("doBuildConfiguracao(Document) - fim - return value = " + configuracoesConexoes);
            }
            return configuracoesConexoes;
        }
        catch (HibernateExceptionCD e)
        {
            log.error("Erro: PersistenciaConfiguracao - doBuildConfiguracao", e);
            throw new HibernateExceptionCD("Erro: PersistenciaConfiguracao - doBuildConfiguracao: " + e.getMessage());
        }
    }

    /**
     * Obtém os mapeamentos Hibernate das conexões especificadas
     * 
     * @param configuracoesConexoes Coleção dos dados das conexões lidas
     * @param caminhoModulosMapeamento Caminho para os arquivos que contém os mapeamentos Hibernate
     * @throws HibernateExceptionCD
     */
    private void obterMapeamentos(ColecaoDadosConexao configuracoesConexoes, String caminhoModulosMapeamento) throws HibernateExceptionCD
    {
        if (log.isTraceEnabled())
        {
            log.trace("getMapeamentos(ColecaoDadosConexao configuracoesConexoes = "
                    + configuracoesConexoes
                    + ", String local_arquivos_cfg = "
                    + caminhoModulosMapeamento
                    + ") - inicio");
        }

        try
        {
            // 1o) Iterar a coleção de dados das conexões
            // 2o) Para cada coleção, agrupar todos os mapeamentos em todos os
            // módulos associados

            Iterator conexoes = configuracoesConexoes.obterIterator();
            while (conexoes.hasNext())
            {
                // Recupera os dados da próxima conexão
                DadosConexao dadosConexao = (DadosConexao) conexoes.next();

                Iterator modulos = dadosConexao.getModulosConexao().entrySet().iterator();
                // Recupera os dados do próximo módulo que utiliza a conexão em
                // questão
                while (modulos.hasNext())
                {
                    Entry entradaModulo = (Entry) modulos.next();
                    String modulo = (String) entradaModulo.getKey();
                    String caminhoRelativo = (String) entradaModulo.getValue();

                    String nomeArquivoModuloMapeamento = "";
                    if (caminhoRelativo != null && !"".equals(caminhoRelativo))
                    {
                        nomeArquivoModuloMapeamento = caminhoModulosMapeamento + caminhoRelativo + modulo;
                    }
                    else
                    {
                        nomeArquivoModuloMapeamento = caminhoModulosMapeamento + modulo;
                    }

                    // Abre o arquivo de configuração do módulo em questão
                    Document document = this.obterReferenciaDocument(nomeArquivoModuloMapeamento);
                    Iterator mapeamentosConfiguracao = document.getRootElement().element("session-factory").elements().iterator();

                    List mapeamentos = dadosConexao.getMapeamentosConexao();

                    // Lê todas os mapeamentos do módulo em questão
                    while (mapeamentosConfiguracao.hasNext())
                    {
                        Element mapeamento = (Element) mapeamentosConfiguracao.next();
                        if ("mapping".equals(mapeamento.getName()))
                        {
                            if (!mapeamentos.contains(mapeamento.attribute("resource").getValue()))
                            {
                                mapeamentos.add(mapeamento.attribute("resource").getValue());
                            }
                        }
                    }
                }
            }
        }
        catch (HibernateExceptionCD e)
        {
            log.error("Erro: PersistenciaConfiguracao - doBuildMapeamento", e);
            throw new HibernateExceptionCD("Erro: PersistenciaConfiguracao - doBuildMapeamento: " + e.getMessage());
        }

        if (log.isTraceEnabled())
        {
            log.trace("getMapeamentos(Document, ColecaoDadosConexao, String) - fim");
        }
    }

    /**
     * Lê o arquivo de configuração e preenche a coleção com os nomes e dados das conexões definidas
     * 
     * @param configuracoesConexoes
     * @param config
     * @return
     * @throws HibernateExceptionCD
     */
    private void obterConexoesPropriedadesSubstituicoesModulosmapeamento(Document config, ColecaoDadosConexao configuracoesConexoes) throws HibernateExceptionCD
    {
        if (log.isTraceEnabled())
        {
            log.trace("getConexoes(Document config = " + config + ", ColecaoDadosConexao configuracoesConexoes = " + configuracoesConexoes + ") - inicio");
        }

        try
        {
            // Recupera todas as conexões
            Iterator iteratorConexoes = config.getRootElement().selectNodes("conexao").iterator();
            while (iteratorConexoes.hasNext())
            {
                // Verifica se a conexão em análise já foi carregada
                Element conexao = (Element) iteratorConexoes.next();
                List lstNomesConexao = this.criaListaNomesConexao(conexao.attribute("nome").getValue());
                String strNomeConexao;
                for (int i = 0; i < lstNomesConexao.size(); i++)
                {
                    strNomeConexao = (String) lstNomesConexao.get(i);
                    if (configuracoesConexoes.existeConexao(strNomeConexao))
                    {
                        String mensagemErro = "A configuração da conexão '" + strNomeConexao + "' está duplicada";
                        if (log.isErrorEnabled())
                        {
                            log.error(mensagemErro);
                        }
                        throw new HibernateExceptionCD(mensagemErro);
                    }
                }

                // Cria um novo dados desta conexao
                DadosConexao dadosConexao = new DadosConexao();

                // Atribiu o nome
                dadosConexao.setNomesConexao(lstNomesConexao);

                // Lê os dados da conexão em questão
                Iterator iteratorConexao = conexao.elementIterator();
                while (iteratorConexao.hasNext())
                {
                    Element propriedade = (Element) iteratorConexao.next();
                    String nomePropriedade = propriedade.getName();

                    // Lê as propriedades
                    if ("property".equals(nomePropriedade))
                    {
                        dadosConexao.getPropriedadesConexao().setProperty(propriedade.attribute("name").getValue(), propriedade.getText());
                    }

                    // Lê as substituicões
                    if ("substituicao".equals(nomePropriedade))
                    {
                        dadosConexao.getSubstituicoesConexao().setProperty(propriedade.attribute("chave").getValue(), propriedade.getText());
                    }

                    // Lê os interceptors
                    if ("interceptor".equals(nomePropriedade))
                    {
                        dadosConexao.addInterceptorConexao(propriedade.attribute("tipo").getValue(), propriedade.getText());
                    }

                    // Lê os módulos de mapeamento
                    if ("moduloMapeamento".equals(nomePropriedade))
                    {
                        String atributoCaminhoRelativo = null;
                        if (propriedade.attribute("caminhoRelativo") != null)
                        {
                            atributoCaminhoRelativo = propriedade.attribute("caminhoRelativo").getValue();
                        }

                        dadosConexao.getModulosConexao().put(propriedade.attribute("nomeArquivo").getValue(), atributoCaminhoRelativo);
                    }
                }

                // Adicionar esta nova conexão
                configuracoesConexoes.add(dadosConexao);
            }

            /*
             * if (configuracoesConexoes.tamanho() > 1) { String mensagemErro = "Somente uma conexão (definida no
             * arquivo de configuração) é suportada por esta versão"; if (log.isErrorEnabled()) {
             * log.error(mensagemErro); } throw new HibernateExceptionCD(mensagemErro); }
             */
        }
        catch (Exception e)
        {
            log.error("Erro: PersistenciaConfiguracao - doBuildMapeamento", e);
            throw new HibernateExceptionCD("Erro: PersistenciaConfiguracao - doBuildMapeamento: " + e.getMessage());
        }

        if (log.isTraceEnabled())
        {
            log.trace("getConexoes(Document, ColecaoDadosConexao) - fim");
        }
    }

    private List criaListaNomesConexao(String strNomesConexao)
    {
        ArrayList lstNomesConexao = new ArrayList();
        String[] strArrNomesConexao = strNomesConexao.split(",");

        for (int i = 0; i < strArrNomesConexao.length; i++)
        {
            String strNomeConexao = strArrNomesConexao[i].trim();
            if (!"".equals(strNomeConexao))
            {
                lstNomesConexao.add(strNomeConexao);
            }
        }

        return lstNomesConexao;
    }
}

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

            // Cria refer�ncia para o arquivo de configura��o
            Document document = this.obterReferenciaDocument(arquivo);

            // Recupera os dados do arquivo de configura��o e dos arquivos de mapeamento
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
     * Obt�m uma refer�ncia (Document) para o arquivo de configura��o do HibernatePlugin
     * 
     * @param arquivo Nome do arquivo de configura��o do HibernatePlugin
     * @return Retorna o Document para o arquivo de configura��o do HibernatePlugin
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
            // Cria refer�ncia para o arquivo de configura��o
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(arquivo);
            if (stream == null)
            {
                log.error("N�o foi poss�vel localizar o seguinte arquivo: " + arquivo);
                throw new HibernateExceptionCD("N�o foi poss�vel localizar o seguinte arquivo: " + arquivo);
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
                            case 3: // �lfred
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
                throw new HibernateExceptionCD("configura��o inv�lida", (Throwable) errors.get(0));
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
     * L� o Caminho para os m�dulos de mapeamento
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
     * Obt�m os mapeamentos Hibernate das conex�es especificadas
     * 
     * @param configuracoesConexoes Cole��o dos dados das conex�es lidas
     * @param caminhoModulosMapeamento Caminho para os arquivos que cont�m os mapeamentos Hibernate
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
            // 1o) Iterar a cole��o de dados das conex�es
            // 2o) Para cada cole��o, agrupar todos os mapeamentos em todos os
            // m�dulos associados

            Iterator conexoes = configuracoesConexoes.obterIterator();
            while (conexoes.hasNext())
            {
                // Recupera os dados da pr�xima conex�o
                DadosConexao dadosConexao = (DadosConexao) conexoes.next();

                Iterator modulos = dadosConexao.getModulosConexao().entrySet().iterator();
                // Recupera os dados do pr�ximo m�dulo que utiliza a conex�o em
                // quest�o
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

                    // Abre o arquivo de configura��o do m�dulo em quest�o
                    Document document = this.obterReferenciaDocument(nomeArquivoModuloMapeamento);
                    Iterator mapeamentosConfiguracao = document.getRootElement().element("session-factory").elements().iterator();

                    List mapeamentos = dadosConexao.getMapeamentosConexao();

                    // L� todas os mapeamentos do m�dulo em quest�o
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
     * L� o arquivo de configura��o e preenche a cole��o com os nomes e dados das conex�es definidas
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
            // Recupera todas as conex�es
            Iterator iteratorConexoes = config.getRootElement().selectNodes("conexao").iterator();
            while (iteratorConexoes.hasNext())
            {
                // Verifica se a conex�o em an�lise j� foi carregada
                Element conexao = (Element) iteratorConexoes.next();
                List lstNomesConexao = this.criaListaNomesConexao(conexao.attribute("nome").getValue());
                String strNomeConexao;
                for (int i = 0; i < lstNomesConexao.size(); i++)
                {
                    strNomeConexao = (String) lstNomesConexao.get(i);
                    if (configuracoesConexoes.existeConexao(strNomeConexao))
                    {
                        String mensagemErro = "A configura��o da conex�o '" + strNomeConexao + "' est� duplicada";
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

                // L� os dados da conex�o em quest�o
                Iterator iteratorConexao = conexao.elementIterator();
                while (iteratorConexao.hasNext())
                {
                    Element propriedade = (Element) iteratorConexao.next();
                    String nomePropriedade = propriedade.getName();

                    // L� as propriedades
                    if ("property".equals(nomePropriedade))
                    {
                        dadosConexao.getPropriedadesConexao().setProperty(propriedade.attribute("name").getValue(), propriedade.getText());
                    }

                    // L� as substituic�es
                    if ("substituicao".equals(nomePropriedade))
                    {
                        dadosConexao.getSubstituicoesConexao().setProperty(propriedade.attribute("chave").getValue(), propriedade.getText());
                    }

                    // L� os interceptors
                    if ("interceptor".equals(nomePropriedade))
                    {
                        dadosConexao.addInterceptorConexao(propriedade.attribute("tipo").getValue(), propriedade.getText());
                    }

                    // L� os m�dulos de mapeamento
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

                // Adicionar esta nova conex�o
                configuracoesConexoes.add(dadosConexao);
            }

            /*
             * if (configuracoesConexoes.tamanho() > 1) { String mensagemErro = "Somente uma conex�o (definida no
             * arquivo de configura��o) � suportada por esta vers�o"; if (log.isErrorEnabled()) {
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

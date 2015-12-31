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

package br.gov.camara.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import sigesp.comum.util.hibernate.ColecaoDadosConexao;
import sigesp.comum.util.hibernate.PersistenciaConfiguracao;
import br.gov.camara.hibernate.exception.HibernateExceptionCD;
import br.gov.camara.negocio.ParametroDAO;

/**
 * Initialize the Hibernate SessionFactory for this project as an application scope object.
 * 
 * @author Ted Husted
 * @version $Revision: 1.3 $ $Date: 2003/03/14 21:59:41 $
 */
public class HibernateUtilCD
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(HibernateUtilCD.class);

    /**
     * A field to store the reference to our SessionFactory. Can close and dispose if not null.
     */

    public static final String CHAVE_QUERY_TIMESTAMP_BD = "queryConsultaTimeStampBD";

    /**
     * The classes with mappings to add to the Configuration are enumerated here. There should be a "${class}.hbm.xml"
     * mapping file for each class stored with each compiled class file.
     * <p>
     * To complete the Hibernate setup, there must be a valid "hiberate.properties" file under the "classes" folder
     * (root of the classpath), which specifies the details of the database hookup.
     * </p>
     * <p>
     * The mapping documents and properties file is all that Hibernate requires.
     * </p>
     * <p>
     * A JDBC Driver is not included in this distribution and must be available on your server's or container's
     * classpath (e.g., the Tomcat common/lib directory).
     * </p>
     * 
     * @return A Configuration object
     * @throws org.hibernate.MappingException if any the mapping documents can be rendered.
     */
    protected static final ColecaoDadosConexao lerArquivoConfiguracaoHibernate(String strCaminhoEArquivoHibernateConfig) throws HibernateExceptionCD
    {
        if (log.isDebugEnabled())
        {
            log.debug("lerArquivoConfiguracaoHibernate() - start");
        }

        ColecaoDadosConexao colecaoDadosConexoes = null;

        try
        {
            PersistenciaConfiguracao persistencia = null;
            persistencia = new PersistenciaConfiguracao();
            colecaoDadosConexoes = persistencia.doConfiguracao(strCaminhoEArquivoHibernateConfig);
        }
        catch (HibernateException e)
        {
            if (log.isErrorEnabled())
            {
                log.error("Ocorreu o seguinte erro tentando obter a configuração do Hibernate:" + e.getMessage());
            }
            throw new HibernateExceptionCD(e);
        }

        if (log.isDebugEnabled())
        {
            log.debug("lerArquivoConfiguracaoHibernate() - end");
        }
        return colecaoDadosConexoes;
    }

    /**
     * DOCUMENTAR!
     * 
     * @param servlet DOCUMENTAR!
     * @param config DOCUMENTAR!
     */
    public synchronized void inicializar(String strCaminhoEArquivoHibernateConfig) throws HibernateExceptionCD
    {
        try
        {
            GerenciadorConfiguracaoHibernate.iniciar(lerArquivoConfiguracaoHibernate(strCaminhoEArquivoHibernateConfig));
        }
        catch (HibernateException e)
        {
            if (log.isErrorEnabled())
            {
                log.error("...Ocorreu o seguinte erro criando a SessionFactory do Hibernate: " + e.getMessage());
            }
            throw new HibernateExceptionCD(e);
        }
    }

    /**
     * DOCUMENTAR!
     * 
     * @param servlet DOCUMENTAR!
     * @param config DOCUMENTAR!
     */
    public synchronized void inicializar() throws HibernateExceptionCD
    {
        inicializar(null);
    }

    /**
     * DOCUMENTAR!
     */
    public synchronized void finalizar()
    {
        if (log.isInfoEnabled())
        {
            log.info("Finalizando o HibernatePlugIn...");
        }

        GerenciadorConfiguracaoHibernate.finalizar();

        if (log.isDebugEnabled())
        {
            log.debug("O HibernatePlugIn foi finalizado");
        }
    }

    public static void verificarSessoesTransacoesNaoFinalizadas() throws HibernateExceptionCD
    {
        String strNomeTransacoesAbertas = FabricaTransacaoHibernate.forcarFechamentoTransacoesAbertas();
        String strNomeSessoesAbertas = FabricaSessaoHibernate.forcarFechamentoSessoesAbertas();

        if (!"".equals(strNomeSessoesAbertas) || !"".equals(strNomeTransacoesAbertas))
        {
            throw new HibernateExceptionCD("Existiam sessões ("
                    + strNomeSessoesAbertas
                    + ") e/ou transações ("
                    + strNomeTransacoesAbertas
                    + ") abertas que foram finalizadas.");
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("Todas as sessões e transações do Hibernate foram finalizadas.");
            }
        }
    }

    public static int executarSQLComParametros(String nomeConexao, String strSQL, ParametroDAO parametros) throws HibernateExceptionCD
    {
        int retorno = 0;
        ConexaoJDBC conexao = null;

        if (strSQL != null && !"".equals(strSQL))
        {
            conexao = new ConexaoJDBC(FabricaSessaoHibernate.obterSessao(nomeConexao));
            retorno = conexao.executarComParametros(strSQL, parametros);
        }

        return retorno;
    }

    public static int executarSQL(String nomeConexao, String strSQL) throws HibernateExceptionCD
    {
        int retorno = 0;
        ConexaoJDBC conexao = null;

        if (strSQL != null && !"".equals(strSQL))
        {
            //exibirSQL(nomeConexao, strSQL);
            conexao = new ConexaoJDBC(FabricaSessaoHibernate.obterSessao(nomeConexao));
            retorno = conexao.executar(strSQL);
        }

        return retorno;
    }

    public static List carregarPorSQL(String nomeConexao, String strSQL) throws HibernateExceptionCD
    {
        // Declarações
        List lstResultado = null;

        ConexaoJDBC conexao = null;

        if (strSQL != null && !"".equals(strSQL))
        {
            //exibirSQL(nomeConexao, strSQL);
            conexao = new ConexaoJDBC(FabricaSessaoHibernate.obterSessao(nomeConexao));
            lstResultado = conexao.recuperar(strSQL);
        }

        return lstResultado;
    }

    public static List carregarPorSQLComParametros(String nomeConexao, String strSQL, ParametroDAO parametrosConsulta) throws HibernateExceptionCD
    {
        // Declarações
        List lstResultado = null;

        ConexaoJDBC conexao = null;

        if (strSQL != null && !"".equals(strSQL))
        {
            //exibirSQL(nomeConexao, strSQL);
            conexao = new ConexaoJDBC(FabricaSessaoHibernate.obterSessao(nomeConexao));
            lstResultado = conexao.recuperarComParametros(strSQL, parametrosConsulta);
        }

        return lstResultado;
    }

    public static Session obterSessao(String nomeConexao) throws HibernateExceptionCD
    {
        return FabricaSessaoHibernate.obterSessao(nomeConexao);
    }

    public static String obterPropriedadeSubstituicao(String nomeConexao, String chave) throws HibernateExceptionCD
    {
        return GerenciadorConfiguracaoHibernate.obterPropriedadeSubstituicao(nomeConexao, chave);
    }

    public static String obterDialeto(String nomeConexao) throws HibernateExceptionCD
    {
        return GerenciadorConfiguracaoHibernate.obterDialeto(nomeConexao);
    }

    public static boolean realizarFlushImediatamente(String nomeConexao) throws HibernateExceptionCD
    {
        return GerenciadorConfiguracaoHibernate.realizarFlushImediatamente(nomeConexao);
    }

    public static String obterNomeConexaoPadrao() throws HibernateExceptionCD
    {
        return GerenciadorConfiguracaoHibernate.obterNomeConexaoPadrao();
    }

    public static void iniciarTransacao(String nomeConexao) throws HibernateExceptionCD
    {
        FabricaTransacaoHibernate.iniciarTransacao(nomeConexao);
    }

    public static void realizarTransacao(String nomeConexao) throws HibernateExceptionCD
    {
        FabricaTransacaoHibernate.realizarTransacao(nomeConexao);
    }

    public static void desfazerTransacao(String nomeConexao) throws HibernateExceptionCD
    {
        FabricaTransacaoHibernate.desfazerTransacao(nomeConexao);
    }

    public static void fecharSessao(String nomeConexao) throws HibernateExceptionCD
    {
        FabricaSessaoHibernate.fecharSessao(nomeConexao);
    }

    public static String obterNomeConexaoTabela(String nomeTabela) throws HibernateExceptionCD
    {
        return GerenciadorConfiguracaoHibernate.obterNomeConexaoTabela(nomeTabela);
    }
}

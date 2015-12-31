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
package br.gov.camara.seguranca.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.biblioteca.util.ClasseDinamica;
import br.gov.camara.biblioteca.util.ParametroDinamico;
import br.gov.camara.exception.CDException;
import br.gov.camara.seguranca.Permissao;
import br.gov.camara.seguranca.SegurancaWeb;
import br.gov.camara.seguranca.UnidadeAutenticadora;
import br.gov.camara.seguranca.UsuarioAutenticado;
import br.gov.camara.seguranca.exception.SegurancaException;

/**
 * @author P_6414
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SegurancaUtil
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(SegurancaUtil.class);

    private static final String KEY_SEGURANCAWEB = "aplicacao.seguranca.implementacao.segurancaweb";
    private static final String KEY_UNIDADEAUTENTICADORA = "aplicacao.seguranca.implementacao.unidadeautenticadora";
    private static final String KEY_PERMISSAO = "aplicacao.seguranca.implementacao.permissao";

    public static final String KEY_ARQUIVOCONFIGURACAO = "resources/SegImpl.properties";

    private static final String ERRO_ARQUIVOCONFIGURACAO_SEGURANCA = "Ocorreu um tentando abrir o arquivo de configura��o da seguran�a";

    private static final String ERRO_INSTANCIANDO_SEGURANCAWEB = "Ocorreu um erro instanciando a classe especificada no arquivo de configura��o como implementa��o de 'SegurancaWeb'";
    private static final String ERRO_INSTANCIANDO_UNIDADEAUTENTICADORA = "Ocorreu um erro instanciando a classe especificada no arquivo de configura��o como implementa��o de 'UnidadeAutenticadora'";
    private static final String ERRO_INSTANCIANDO_PERMISSAO = "Ocorreu um erro instanciando a classe especificada no arquivo de configura��o como implementa��o de 'Permissao'";

    public SegurancaWeb obterInstanciaSegurancaWeb(UnidadeAutenticadora unidadeAutenticadora, UsuarioAutenticado usuarioAutenticado, Permissao permissao) throws SegurancaException
    {
        ParametroDinamico parametroDinamico = new ParametroDinamico();
        parametroDinamico.adicionarParametro(UnidadeAutenticadora.class, unidadeAutenticadora);
        parametroDinamico.adicionarParametro(UsuarioAutenticado.class, usuarioAutenticado);
        parametroDinamico.adicionarParametro(Permissao.class, permissao);

        SegurancaWeb objSegurancaWeb;
        try
        {
            objSegurancaWeb = (SegurancaWeb) (new ClasseDinamica()).obterInstancia(obterValorArqCfg(SegurancaUtil.KEY_SEGURANCAWEB), parametroDinamico);
        }
        catch (CDException e)
        {
            if (log.isErrorEnabled())
            {
                log.error(ERRO_INSTANCIANDO_SEGURANCAWEB);
            }

            throw new SegurancaException(ERRO_INSTANCIANDO_SEGURANCAWEB);
        }
        catch (IOException e)
        {
            if (log.isErrorEnabled())
            {
                log.error(ERRO_ARQUIVOCONFIGURACAO_SEGURANCA);
            }

            throw new SegurancaException(ERRO_ARQUIVOCONFIGURACAO_SEGURANCA);
        }
        return (objSegurancaWeb);
    }

    private Properties lerArquivoConfiguracao() throws IOException
    {
        return (lerArquivoConfiguracao(KEY_ARQUIVOCONFIGURACAO));
    }

    private Properties lerArquivoConfiguracao(String nomeArquivoConfiguracao) throws IOException
    {
        InputStream input;
        Properties propriedades = new Properties();

        input = (new ClasseDinamica()).obterClassLoader().getResourceAsStream(nomeArquivoConfiguracao);
        if (input == null)
        {
            throw (new FileNotFoundException());
        }
        propriedades.load(input);

        return (propriedades);
    }

    public String obterValorArqCfg(String chave) throws IOException
    {
        Properties arqCfg = lerArquivoConfiguracao();
        return arqCfg.getProperty(chave);
    }

    public UnidadeAutenticadora obterInstanciaUnidadeAutenticadora() throws SegurancaException
    {

        UnidadeAutenticadora objUnidadeAutenticadora;
        try
        {
            objUnidadeAutenticadora = (UnidadeAutenticadora) (new ClasseDinamica()).obterInstancia(obterValorArqCfg(SegurancaUtil.KEY_UNIDADEAUTENTICADORA));
        }
        catch (CDException e)
        {
            if (log.isErrorEnabled())
            {
                log.error(ERRO_INSTANCIANDO_UNIDADEAUTENTICADORA);
            }

            throw new SegurancaException(ERRO_INSTANCIANDO_UNIDADEAUTENTICADORA);
        }
        catch (IOException e)
        {
            if (log.isErrorEnabled())
            {
                log.error(ERRO_ARQUIVOCONFIGURACAO_SEGURANCA);
            }

            throw new SegurancaException(ERRO_ARQUIVOCONFIGURACAO_SEGURANCA);
        }
        return (objUnidadeAutenticadora);
    }

    public Permissao obterInstanciaPermissao() throws SegurancaException
    {

        Permissao objPermissao;
        try
        {
            objPermissao = (Permissao) (new ClasseDinamica()).obterInstancia(obterValorArqCfg(SegurancaUtil.KEY_PERMISSAO));
        }
        catch (CDException e)
        {
            if (log.isErrorEnabled())
            {
                log.error(ERRO_INSTANCIANDO_PERMISSAO);
            }

            throw new SegurancaException(ERRO_INSTANCIANDO_PERMISSAO);
        }
        catch (IOException e)
        {
            if (log.isErrorEnabled())
            {
                log.error(ERRO_ARQUIVOCONFIGURACAO_SEGURANCA);
            }

            throw new SegurancaException(ERRO_ARQUIVOCONFIGURACAO_SEGURANCA);
        }
        return (objPermissao);
    }

}

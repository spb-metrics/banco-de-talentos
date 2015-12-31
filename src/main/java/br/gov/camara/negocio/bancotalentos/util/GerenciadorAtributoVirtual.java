/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.gov.camara.negocio.bancotalentos.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.biblioteca.util.ClasseDinamica;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.bancotalentos.facade.AtributoVirtualFacade;

/**
 *
 * @author christian.braz
 */
public class GerenciadorAtributoVirtual
{

    // Inicializa o log
    private static Log log = LogFactory.getLog(GerenciadorAtributoVirtual.class);

    public static final String KEY_ARQUIVOCONFIGURACAO = "resources/AtributoVirtual.properties";

    private static Map parametrosConsulta = new HashMap();
    private static String arquivoConfiguracao = KEY_ARQUIVOCONFIGURACAO;

    public GerenciadorAtributoVirtual()
    {
        arquivoConfiguracao = KEY_ARQUIVOCONFIGURACAO;
    }

    public GerenciadorAtributoVirtual(String arquivoConf)
    {
        arquivoConfiguracao = arquivoConf;
    }

    private static Properties lerArquivoConfiguracao() throws IOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        InputStream input;
        Properties propriedades = new Properties();

        input = (new ClasseDinamica()).obterClassLoader().getResourceAsStream(arquivoConfiguracao);
        if (input == null)
        {
            throw (new FileNotFoundException("Não foi possível encontrar o arquivo " + arquivoConfiguracao));
        }
        propriedades.load(input);

        return (propriedades);
    }

    private static String obterValorArqCfg(String chave) throws IOException
    {
        Properties arqCfg = lerArquivoConfiguracao();
        return arqCfg.getProperty(chave);
    }

    public static boolean isAtributoVirtual(String nomeAtributo) throws IOException
    {
        String atributo = obterValorArqCfg(nomeAtributo.trim().replaceAll(" ", "") + ".conexao");
        if (atributo == null || "".equals(atributo.trim()))
            return false;
        else
            return true;
    }

    public static void setString(Integer posicao, String valor)
    {
        parametrosConsulta.put(posicao, valor);
    }

    public static void setInt(Integer posicao, Integer valor)
    {
        parametrosConsulta.put(valor, valor);
    }

    public static void setDouble(Integer posicao, Double valor)
    {
        parametrosConsulta.put(posicao, valor);
    }

    public static List obtemValores(String nomeAtributo) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        String nomeConexao = null;
        List resultado = null;
        try
        {
            nomeConexao = obterValorArqCfg(nomeAtributo.trim().replaceAll(" ", "") + ".conexao");
            String consultaSQL = obterValorArqCfg(nomeAtributo.trim().replaceAll(" ", "") + ".consultaSQL");

            AtributoVirtualFacade facade = new AtributoVirtualFacade(nomeConexao);
            resultado = facade.obterPorSQLParametrizado(consultaSQL, parametrosConsulta);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }

        return resultado;
    }

}

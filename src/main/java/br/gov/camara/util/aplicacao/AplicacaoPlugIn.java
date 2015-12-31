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

package br.gov.camara.util.aplicacao;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.xml.sax.SAXException;

/**
 * Plugin para a configuração do locale
 */
public class AplicacaoPlugIn implements PlugIn
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(AplicacaoPlugIn.class);

    private static final String NOME_PROPRIEDADE_PADRAO_APLICACAO = "VALOR_PADRAO";

    private String strArquivoConfiguracao;

    private String strNomeModulo;

    private Digester objDigester;

    private static Map mapConfiguracoes;

    private void lerArquivoConfiguracao(ServletContext objServletContext)
    {
        if (log.isDebugEnabled())
        {
            log.debug("lerArquivoConfiguracao(ServletContext=" + objServletContext + ") - start");
        }

        try
        {
            // Instancia digester
            this.objDigester = new Digester();
            this.objDigester.push(this);

            // Lê as configurações do classpath
            this.objDigester.addCallMethod("aplicacao-configuracao/propriedade", "adicionarPropriedade", 2);
            this.objDigester.addCallParam("aplicacao-configuracao/propriedade", 0, "nome");
            this.objDigester.addCallParam("aplicacao-configuracao/propriedade", 1, "valor");
            // Associa arquivo
            this.objDigester.parse(objServletContext.getResourceAsStream(this.strArquivoConfiguracao));
        }
        catch (IOException ioe)
        {
            log.error("lerArquivoConfiguracao(ServletContext=" + objServletContext + ")", ioe);

            throw new RuntimeException("Ocorreu um erro ao localizar o XML de configuração");
        }
        catch (SAXException saxe)
        {
            log.error("lerArquivoConfiguracao(ServletContext=" + objServletContext + ")", saxe);

            throw new RuntimeException("Ocorreu um erro ao ler o XML de configuração");
        }

        if (log.isDebugEnabled())
        {
            log.debug("lerArquivoConfiguracao(ServletContext=" + objServletContext + ") - end");
        }
    }

    /**
     * Este método "salva" as propriedades em um map de maps. No primeiro map, a key é o 
     * nome da propriedade, e no segundo, a key é o nome do módulo a que se refere aquela 
     * propriedade. No segundo map, qdo o segundo módulo é inserido, o valor definido para
     * o primeiro é definido como o valor padrão caso se tente obter o valor da propriedade
     * para um módulo nao definido no map.
     * 
     * @param strNome Propriedade
     * @param strValor Valor
     */
    public void adicionarPropriedade(String strNome, String strValor)
    {
        if (log.isDebugEnabled())
        {
            log.debug("adicionarPropriedade(String=" + strNome + ", String=" + strValor + ") - start");
        }

        Map mapTemp = null;
        // se ja existe a key
        if (mapConfiguracoes.containsKey(strNome))
        {
            mapTemp = (Map) mapConfiguracoes.get(strNome);
            mapConfiguracoes.remove(strNome);
        }
        else
        {
            mapTemp = new TreeMap();
        }
        // duplica 1a entrada com nome padrao
        if (mapTemp.size() == 1)
        {
            mapTemp.put(NOME_PROPRIEDADE_PADRAO_APLICACAO, mapTemp.get(mapTemp.keySet().iterator().next()));
        }
        mapTemp.put(strNomeModulo, strValor);
        mapConfiguracoes.put(strNome, mapTemp);

        if (log.isDebugEnabled())
        {
            log.debug("adicionarPropriedade(String=" + strNome + ", String=" + strValor + ") - end");
        }
    }

    /**
     * Obtém valor padrão da propriedade para a aplicação. Este valor pode estar definido pela key
     * "NOME_PROPRIEDADE_PADRAO_APLICACAO" ou sera o valor definido caso so haja um valor
     * para a propriedade.
     * 
     * @param strNome nome da propriedade
     * @return valor da propriedade
     */
    public static String obterConfiguracao(String strNome)
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterConfiguracao(String=" + strNome + ") - start");
        }

        String retorno = "";

        if (mapConfiguracoes == null)
            return retorno;

        if (mapConfiguracoes.containsKey(strNome))
        {
            Map mapTemp = (Map) mapConfiguracoes.get(strNome);
            if (mapTemp.containsKey(NOME_PROPRIEDADE_PADRAO_APLICACAO))
            {
                retorno = (String) mapTemp.get(NOME_PROPRIEDADE_PADRAO_APLICACAO);
            }
            else if (mapTemp.size() == 1)
            {
                retorno = (String) mapTemp.get(mapTemp.keySet().iterator().next());
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterConfiguracao(String=" + strNome + " - end");
        }

        return retorno;

    }

    /**
     * Obtém o valor da propriedade para o módulo em questao. Caso não exista,
     * verifica o valor padrao da propriedade para a aplicação.
     * 
     * @param strNome nome da propriedade
     * @param request request
     * @return valor da propriedade
     */
    public static String obterConfiguracao(String strNome, HttpServletRequest request)
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterConfiguracao(String=" + strNome + ", Request=" + request + ") - start");
        }

        String retorno = "";

        retorno = obterConfiguracao(strNome, request, false);

        if (log.isDebugEnabled())
        {
            log.debug("obterConfiguracao(String=" + strNome + ", Request=" + request + ") - end");
        }

        return retorno;
    }

    /**
     * Obtém o valor da propriedade para o módulo em questão. Caso não exista um valor para o modulo
     * e o flag especificoModulo esteja false, pega o valor padrao da propriedade.
     * 
     * @param strNome nome da propriedade
     * @param request request
     * @param especificoModulo flag que indica que consulta é específica para o
     *            módulo
     * @return valor da propriedade para o módulo
     */
    public static String obterConfiguracao(String strNome, HttpServletRequest request, boolean especificoModulo)
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterConfiguracao(String=" + strNome + ", Request=" + request + ") - start");
        }

        String retorno = "";

        String strModulo = ((ModuleConfig) request.getAttribute(Globals.MODULE_KEY)).getPrefix().substring(1);

        if (mapConfiguracoes.containsKey(strNome))
        {
            Map mapTemp = (Map) mapConfiguracoes.get(strNome);
            if (mapTemp.containsKey(strModulo))
            {
                retorno = (String) mapTemp.get(strModulo);
            }
            else if (!especificoModulo && mapTemp.containsKey(NOME_PROPRIEDADE_PADRAO_APLICACAO))
            {
                retorno = (String) mapTemp.get(NOME_PROPRIEDADE_PADRAO_APLICACAO);
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterConfiguracao(String=" + strNome + ", Request=" + request + ") - end");
        }

        return retorno;
    }

    /**
     * Obtém configuração a partir do "nome" no arquivo Aplicacao-config.xml
     * 
     * @param strNome Nome do parâmetro
     * @param servlet Servlet atual
     * @return String Contendo o valor da configuração
     * @deprecated
     */
    public static String obterConfiguracao(String strNome, ActionServlet servlet)
    {
        // String returnString = (String) ((Map) servlet.getServletContext().getAttribute("mapConfiguracoesPlugIn")).get(strNome);
        String returnString = obterConfiguracao(strNome);

        return returnString;
    }

    // Implementação método init
    public void init(ActionServlet servlet, ModuleConfig moduleConfig) throws ServletException
    {
        if (log.isDebugEnabled())
        {
            log.debug("init(ActionServlet=" + servlet + ", ModuleConfig=" + moduleConfig + ") - start");
        }

        // Cria mapa
        if (mapConfiguracoes == null)
            mapConfiguracoes = new TreeMap();

        this.strNomeModulo = moduleConfig.getPrefix().substring(1);

        lerArquivoConfiguracao(servlet.getServletContext());

        // servlet.getServletContext().setAttribute("mapConfiguracoesPlugIn",
        // mapConfiguracoes);

        if (log.isDebugEnabled())
        {
            log.debug("init(ActionServlet=" + servlet + ", ModuleConfig=" + moduleConfig + ") - end");
        }
    }

    // Implementação do método destroy
    public void destroy()
    {
        if (log.isDebugEnabled())
        {
            log.debug("destroy() - start");
        }

        if (log.isDebugEnabled())
        {
            log.debug("destroy() - end");
        }
    }

    /**
     * Obtém arquivo de configuração
     * 
     * @return String Contendo o dado
     */
    public String getArquivoConfiguracao()
    {
        if (log.isDebugEnabled())
        {
            log.debug("getArquivoConfiguracao() - start");
        }

        if (log.isDebugEnabled())
        {
            log.debug("getArquivoConfiguracao() - end");
        }
        return strArquivoConfiguracao;
    }

    /**
     * Preenche o arquivo de configuração
     * 
     * @param strArquivoConfiguracao Com o dado desejado
     */
    public void setArquivoConfiguracao(String strArquivoConfiguracao)
    {
        if (log.isDebugEnabled())
        {
            log.debug("setArquivoConfiguracao(String=" + strArquivoConfiguracao + ") - start");
        }

        this.strArquivoConfiguracao = strArquivoConfiguracao;

        if (log.isDebugEnabled())
        {
            log.debug("setArquivoConfiguracao(String=" + strArquivoConfiguracao + ") - end");
        }
    }

    /**
     * Obtém propriedade do arquivo de acordo com a chave passada
     * 
     * @param strMascara String contendo as máscaras a serem substituídas
     * @param strParam0 Valor do parametro que substitui a primeira máscara {0}
     * @return String Contendo a cláusula query correta
     */
    public static String realizarSubstituicao(String strMascara, String strParam0)
    {
        return realizarSubstituicao(strMascara, strParam0, null, null, null);
    }

    /**
     * Obtém propriedade do arquivo de acordo com a chave passada
     * 
     * @param strMascara String contendo as máscaras a serem substituídas
     * @param strParam0 Valor do parametro que substitui a primeira máscara {0}
     * @param strParam1 Valor do parametro que substitui a segunda máscara {1}
     * @return String Contendo a cláusula query correta
     */
    public static String realizarSubstituicao(String strMascara, String strParam0, String strParam1)
    {
        return realizarSubstituicao(strMascara, strParam0, strParam1, null, null);
    }

    /**
     * Obtém propriedade do arquivo de acordo com a chave passada
     * 
     * @param strMascara String contendo as máscaras a serem substituídas
     * @param strParam0 Valor do parametro que substitui a primeira máscara {0}
     * @param strParam1 Valor do parametro que substitui a segunda máscara {1}
     * @param strParam2 Valor do parametro que substitui a terceira máscara {2}
     * @return String Contendo a cláusula query correta
     */
    public static String realizarSubstituicao(String strMascara, String strParam0, String strParam1, String strParam2)
    {
        return realizarSubstituicao(strMascara, strParam0, strParam1, strParam2, null);
    }

    /**
     * Obtém propriedade do arquivo de acordo com a chave passada
     * 
     * @param strMascara String contendo as máscaras a serem substituídas
     * @param strParam0 Valor do parametro que substitui a primeira máscara {0}
     * @param strParam1 Valor do parametro que substitui a segunda máscara {1}
     * @param strParam2 Valor do parametro que substitui a terceira máscara {2}
     * @param strParam3 Valor do parametro que substitui a quarta máscara {3}
     * @return String Contendo
     */
    public static String realizarSubstituicao(String strMascara, String strParam0, String strParam1, String strParam2, String strParam3)
    {
        String strRetorno = strMascara;
        if (strParam0 != null)
        {
            strRetorno = strRetorno.replaceAll("\\{0}", strParam0);
        }
        if (strParam1 != null)
        {
            strRetorno = strRetorno.replaceAll("\\{1}", strParam1);
        }
        if (strParam2 != null)
        {
            strRetorno = strRetorno.replaceAll("\\{2}", strParam2);
        }
        if (strParam3 != null)
        {
            strRetorno = strRetorno.replaceAll("\\{3}", strParam3);
        }
        return strRetorno;
    }

}

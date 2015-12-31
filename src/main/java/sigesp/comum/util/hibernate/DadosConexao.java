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
 * Created on 19/11/2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package sigesp.comum.util.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Conteiner dos dados associados com uma conexão:
 *   - nome da conexão 
 *   - propriedades da conexão
 *   - módulos (arq. XML contendo os mapeamentos) associados à conexão
 *   - mapeamentos da conexão
 *   - substituições da conexão 
 */
public class DadosConexao
{
    private List dadosConexao = null;

    private static final int POSICAO_NOMES_CONEXAO = 0;
    private static final int POSICAO_PROPRIEDADES_CONEXAO = 1;
    private static final int POSICAO_MAPEAMENTOS_CONEXAO = 2;
    private static final int POSICAO_MODULOS_CONEXAO = 3;
    private static final int POSICAO_SUBSTITUICOES_CONEXAO = 4;
    private static final int POSICAO_INTERCEPTORS_CONEXAO = 5;
    private static final int POSICAO_CONTAINER_CONEXAO = 6;
    private static final int POSICAO_MAPEAMENTOS_TABELAS = 7;
    private static final int TAMANHO_POSICOES = 8;

    /**
     * Inicializa o conteiner da conexão e seus dados
     */
    public DadosConexao()
    {
        dadosConexao = new ArrayList(TAMANHO_POSICOES);
        for (int i = 0; i < TAMANHO_POSICOES; i++)
        {
            dadosConexao.add(null);
        }
        dadosConexao.set(POSICAO_NOMES_CONEXAO, new ArrayList());
        dadosConexao.set(POSICAO_PROPRIEDADES_CONEXAO, new Properties());
        dadosConexao.set(POSICAO_MAPEAMENTOS_CONEXAO, new ArrayList());
        dadosConexao.set(POSICAO_MODULOS_CONEXAO, new HashMap());
        dadosConexao.set(POSICAO_SUBSTITUICOES_CONEXAO, new Properties());
        dadosConexao.set(POSICAO_INTERCEPTORS_CONEXAO, new HashMap());
        dadosConexao.set(POSICAO_CONTAINER_CONEXAO, new HashMap());
        dadosConexao.set(POSICAO_MAPEAMENTOS_TABELAS, new ArrayList());
    }

    /**
     * Obtém o nome da conexão
     * 
     * @return String contendo o nome da conexão
     */
    public List getNomesConexao()
    {
        return (List) dadosConexao.get(POSICAO_NOMES_CONEXAO);
    }

    /**
     * Obtém as propriedades da conexão
     * 
     * @return Properties propriedades da conexão
     */
    public Properties getPropriedadesConexao()
    {
        return (Properties) dadosConexao.get(POSICAO_PROPRIEDADES_CONEXAO);
    }

    /**
     * Obtém os mapeamentos da conexão
     * 
     * @return List lista com os mapeamentos da conexão
     */
    public List getMapeamentosConexao()
    {
        return (List) dadosConexao.get(POSICAO_MAPEAMENTOS_CONEXAO);
    }

    /**
     * Obtém os mapeamentos das tabelas
     * 
     * @return List lista com os mapeamentos das tabelas
     */
    public List getMapeamentosTabelas()
    {
        return (List) dadosConexao.get(POSICAO_MAPEAMENTOS_TABELAS);
    }

    /**
     * Obtém os módulos associados à conexão
     * 
     * @return List lista com os módulos da conexão
     */
    public Map getModulosConexao()
    {
        return (Map) dadosConexao.get(POSICAO_MODULOS_CONEXAO);
    }

    /**
     * Obtém as substituições associadas à conexão
     * 
     * @return Properties propriedades com as substituições da conexão
     */
    public Properties getSubstituicoesConexao()
    {
        return (Properties) dadosConexao.get(POSICAO_SUBSTITUICOES_CONEXAO);
    }

    /**
     * Obtém os interceptors associados à conexão
     * 
     * @return Properties propriedades com os interceptors da conexão
     */
    private Map getInterceptorsConexao()
    {
        return (Map) dadosConexao.get(POSICAO_INTERCEPTORS_CONEXAO);
    }

    /**
     * Atribui o nome da conexão
     * 
     * @param nomeConexao
     *        Nome da conexão
     */
    public void setNomesConexao(List nomesConexao)
    {
        dadosConexao.set(POSICAO_NOMES_CONEXAO, nomesConexao);
    }

    /**
     * Atribui as propriedades da conexão
     * 
     * @param propriedadesConexao
     *        Propriedades da conexão
     */
    public void setPropriedadesConexao(Properties propriedadesConexao)
    {
        dadosConexao.set(POSICAO_PROPRIEDADES_CONEXAO, propriedadesConexao);
    }

    /**
     * Atribui os mapeamentos da conexão
     * 
     * @param mapeamentosConexao
     *        Mapeamentos da conexão
     */
    public void setMapeamentosConexao(List mapeamentosConexao)
    {
        dadosConexao.set(POSICAO_MAPEAMENTOS_CONEXAO, mapeamentosConexao);
    }

    /**
     * Atribui os mapeamentos das tabelas
     * 
     * @param mapeamentosConexao
     *        Mapeamentos da conexão
     */
    public void setMapeamentosTabelas(List mapeamentosTabelas)
    {
        dadosConexao.set(POSICAO_MAPEAMENTOS_TABELAS, mapeamentosTabelas);
    }

    /**
     * Atribui os módulos (nomes de arquivo XML contento a relação de
     * mapeamentos) da conexão
     * 
     * @param modulosConexao
     */
    public void setModulosConexao(Map modulosConexao)
    {
        dadosConexao.set(POSICAO_MODULOS_CONEXAO, modulosConexao);
    }

    /**
     * Atribui as substituições da conexão
     * 
     * @param substituicoesConexao
     */
    public void setSubstituicoesConexao(Properties substituicoesConexao)
    {
        dadosConexao.set(POSICAO_SUBSTITUICOES_CONEXAO, substituicoesConexao);
    }

    /**
     * Atribui os interceptors da conexão
     * 
     * @param interceptorsConexao
     */
    public void addInterceptorConexao(String tipo, String nomeClasseInterceptor)
    {
        List interceptors = (List) (getInterceptorsConexao().get(tipo));
        if (interceptors == null)
        {
            interceptors = new ArrayList();
            getInterceptorsConexao().put(tipo, interceptors);

        }
        interceptors.add(nomeClasseInterceptor);
    }

    /**
     * Atribui os interceptors da conexão
     * 
     * @param interceptorsConexao
     */
    public List getInterceptorsPorTipo(String tipo)
    {
        List interceptors = (List) (getInterceptorsConexao().get(tipo));
        return interceptors;
    }

    public Object getObjetoConexao(String chave)
    {
        return ((Map) dadosConexao.get(POSICAO_CONTAINER_CONEXAO)).get(chave);
    }

    public void setObjetoConexao(String chave, Object objeto)
    {
        ((Map) dadosConexao.get(POSICAO_CONTAINER_CONEXAO)).put(chave, objeto);
    }
}

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
 * Conteiner dos dados associados com uma conex�o:
 *   - nome da conex�o 
 *   - propriedades da conex�o
 *   - m�dulos (arq. XML contendo os mapeamentos) associados � conex�o
 *   - mapeamentos da conex�o
 *   - substitui��es da conex�o 
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
     * Inicializa o conteiner da conex�o e seus dados
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
     * Obt�m o nome da conex�o
     * 
     * @return String contendo o nome da conex�o
     */
    public List getNomesConexao()
    {
        return (List) dadosConexao.get(POSICAO_NOMES_CONEXAO);
    }

    /**
     * Obt�m as propriedades da conex�o
     * 
     * @return Properties propriedades da conex�o
     */
    public Properties getPropriedadesConexao()
    {
        return (Properties) dadosConexao.get(POSICAO_PROPRIEDADES_CONEXAO);
    }

    /**
     * Obt�m os mapeamentos da conex�o
     * 
     * @return List lista com os mapeamentos da conex�o
     */
    public List getMapeamentosConexao()
    {
        return (List) dadosConexao.get(POSICAO_MAPEAMENTOS_CONEXAO);
    }

    /**
     * Obt�m os mapeamentos das tabelas
     * 
     * @return List lista com os mapeamentos das tabelas
     */
    public List getMapeamentosTabelas()
    {
        return (List) dadosConexao.get(POSICAO_MAPEAMENTOS_TABELAS);
    }

    /**
     * Obt�m os m�dulos associados � conex�o
     * 
     * @return List lista com os m�dulos da conex�o
     */
    public Map getModulosConexao()
    {
        return (Map) dadosConexao.get(POSICAO_MODULOS_CONEXAO);
    }

    /**
     * Obt�m as substitui��es associadas � conex�o
     * 
     * @return Properties propriedades com as substitui��es da conex�o
     */
    public Properties getSubstituicoesConexao()
    {
        return (Properties) dadosConexao.get(POSICAO_SUBSTITUICOES_CONEXAO);
    }

    /**
     * Obt�m os interceptors associados � conex�o
     * 
     * @return Properties propriedades com os interceptors da conex�o
     */
    private Map getInterceptorsConexao()
    {
        return (Map) dadosConexao.get(POSICAO_INTERCEPTORS_CONEXAO);
    }

    /**
     * Atribui o nome da conex�o
     * 
     * @param nomeConexao
     *        Nome da conex�o
     */
    public void setNomesConexao(List nomesConexao)
    {
        dadosConexao.set(POSICAO_NOMES_CONEXAO, nomesConexao);
    }

    /**
     * Atribui as propriedades da conex�o
     * 
     * @param propriedadesConexao
     *        Propriedades da conex�o
     */
    public void setPropriedadesConexao(Properties propriedadesConexao)
    {
        dadosConexao.set(POSICAO_PROPRIEDADES_CONEXAO, propriedadesConexao);
    }

    /**
     * Atribui os mapeamentos da conex�o
     * 
     * @param mapeamentosConexao
     *        Mapeamentos da conex�o
     */
    public void setMapeamentosConexao(List mapeamentosConexao)
    {
        dadosConexao.set(POSICAO_MAPEAMENTOS_CONEXAO, mapeamentosConexao);
    }

    /**
     * Atribui os mapeamentos das tabelas
     * 
     * @param mapeamentosConexao
     *        Mapeamentos da conex�o
     */
    public void setMapeamentosTabelas(List mapeamentosTabelas)
    {
        dadosConexao.set(POSICAO_MAPEAMENTOS_TABELAS, mapeamentosTabelas);
    }

    /**
     * Atribui os m�dulos (nomes de arquivo XML contento a rela��o de
     * mapeamentos) da conex�o
     * 
     * @param modulosConexao
     */
    public void setModulosConexao(Map modulosConexao)
    {
        dadosConexao.set(POSICAO_MODULOS_CONEXAO, modulosConexao);
    }

    /**
     * Atribui as substitui��es da conex�o
     * 
     * @param substituicoesConexao
     */
    public void setSubstituicoesConexao(Properties substituicoesConexao)
    {
        dadosConexao.set(POSICAO_SUBSTITUICOES_CONEXAO, substituicoesConexao);
    }

    /**
     * Atribui os interceptors da conex�o
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
     * Atribui os interceptors da conex�o
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

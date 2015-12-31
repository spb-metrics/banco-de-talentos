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
 * Created on 21/11/2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package sigesp.comum.util.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Container para as conexões Hibernate lidas e seus dados
 * 
 */
public class ColecaoDadosConexao
{
    private List colecaoDadosConexao = null;

    /**
     * Inicializa o array dos dados das conexões
     *
     */
    public ColecaoDadosConexao()
    {
        colecaoDadosConexao = new ArrayList();
    }

    /**
     * Adiciona uma conexão com seus dados no array
     * @param dadosConexao Item (DadosConexao) a ser adicionado
     */
    public void add(DadosConexao dadosConexao)
    {
        colecaoDadosConexao.add(dadosConexao);
    }

    /**
     * Retorna a conexão informada (pelo nome) e seus dados   
     * @param nomeConexao Nome da conexão a ser retornada
     * @return Retorna a conexão especificada ou null se ela não foi encontrada
     */
    public DadosConexao obterDadosConexao(String nomeConexao)
    {
        DadosConexao dadosConexao = null;

        for (int i = 0; i < colecaoDadosConexao.size(); i++)
        {
            DadosConexao dadosConexaoTemp = (DadosConexao) colecaoDadosConexao.get(i);
            List nomesConexao = dadosConexaoTemp.getNomesConexao();
            for (int j = 0; j < nomesConexao.size(); j++)
            {
                if (nomeConexao.equals((String) nomesConexao.get(j)))
                {
                    dadosConexao = dadosConexaoTemp;
                    i = colecaoDadosConexao.size();
                    j = nomesConexao.size();
                }
            }
        }
        return dadosConexao;
    }

    /**
     * Obtém o Iterator da coleção de conexões 
     * @return Iterator da coleção de conexões
     */
    public Iterator obterIterator()
    {
        return colecaoDadosConexao.iterator();
    }

    /**
     * Verifica se a conexão especificada existe ou não na coleção
     * @param nomeConexao Nome da conexão procurada
     * @return True se a conexão foi encontrada, caso contrário false
     */
    public boolean existeConexao(String nomeConexao)
    {
        if (obterDadosConexao(nomeConexao) == null)
        {
            return false;
        }
        else return true;
    }

    /**
     * Verifica se a conexão especificada existe ou não na coleção
     * @param nomeConexao Nome da conexão procurada
     * @return True se a conexão foi encontrada, caso contrário false
     */
    public DadosConexao obterConexaoPeloIndice(int indice)
    {
        return (DadosConexao) colecaoDadosConexao.get(indice);
    }

    /**
     * Retorna a quantidade de itens na coleção
     * @return Tamanho da coleção
     */
    public int obterTamanho()
    {
        return colecaoDadosConexao.size();
    }
}
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
 * Container para as conex�es Hibernate lidas e seus dados
 * 
 */
public class ColecaoDadosConexao
{
    private List colecaoDadosConexao = null;

    /**
     * Inicializa o array dos dados das conex�es
     *
     */
    public ColecaoDadosConexao()
    {
        colecaoDadosConexao = new ArrayList();
    }

    /**
     * Adiciona uma conex�o com seus dados no array
     * @param dadosConexao Item (DadosConexao) a ser adicionado
     */
    public void add(DadosConexao dadosConexao)
    {
        colecaoDadosConexao.add(dadosConexao);
    }

    /**
     * Retorna a conex�o informada (pelo nome) e seus dados   
     * @param nomeConexao Nome da conex�o a ser retornada
     * @return Retorna a conex�o especificada ou null se ela n�o foi encontrada
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
     * Obt�m o Iterator da cole��o de conex�es 
     * @return Iterator da cole��o de conex�es
     */
    public Iterator obterIterator()
    {
        return colecaoDadosConexao.iterator();
    }

    /**
     * Verifica se a conex�o especificada existe ou n�o na cole��o
     * @param nomeConexao Nome da conex�o procurada
     * @return True se a conex�o foi encontrada, caso contr�rio false
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
     * Verifica se a conex�o especificada existe ou n�o na cole��o
     * @param nomeConexao Nome da conex�o procurada
     * @return True se a conex�o foi encontrada, caso contr�rio false
     */
    public DadosConexao obterConexaoPeloIndice(int indice)
    {
        return (DadosConexao) colecaoDadosConexao.get(indice);
    }

    /**
     * Retorna a quantidade de itens na cole��o
     * @return Tamanho da cole��o
     */
    public int obterTamanho()
    {
        return colecaoDadosConexao.size();
    }
}
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

import java.util.ArrayList;
import java.util.List;

public class JUnitDelimitadora implements DelimitadoraSessaoTransacao
{
    /**
     * Retorna a classe Facade e o método que chamou esta classe
     * 
     * @return nome da classe Facade e o nome do método separados por '.'
     */
    public String obterPrimeira()
    {
        String retorno = null;
        int tamanhoStackTrace;

        StackTraceElement[] stackTrace = null;
        stackTrace = new Throwable().getStackTrace();
        tamanhoStackTrace = stackTrace.length;

        // [0] -> A própria função determinarFacadeChamadora
        // [1] -> Algum método do Hibernate plugin (determinarFacadeChamadora é private)
        // [2] -> DAO (no mais cedo, na posição 2)
        // [3] -> Portanto começar a pesquisar no índice 3
        for (int i = 3; i < tamanhoStackTrace; i++)
        {
            if (stackTrace[i].getClassName().indexOf("TestCase") > -1)
            {
                retorno = stackTrace[i].getClassName();
                break;
            }
        }

        return retorno;
    }

    /**
     * Retorna a classe Facade e o método que chamou esta classe
     * 
     * @return nome da classe Facade e o nome do método separados por '.'
     */
    public List obterTodas()
    {
        ArrayList retorno = new ArrayList();
        int tamanhoStackTrace;

        StackTraceElement[] stackTrace = null;
        stackTrace = new Throwable().getStackTrace();
        tamanhoStackTrace = stackTrace.length;

        int i;
        // [0] -> A própria função determinarFacadeChamadora
        // [1] -> Algum método do Hibernate plugin (determinarFacadeChamadora é private)
        // [2] -> DAO (no mais cedo, na posição 2)
        // [3] -> Portanto começar a pesquisar no índice 3
        for (i = 3; i < tamanhoStackTrace; i++)
        {
            if (stackTrace[i].getClassName().indexOf("TestCase") > -1)
            {
                retorno.add(stackTrace[i].getClassName());
                break;
            }
        }

        for (++i; i < tamanhoStackTrace; i++)
        {
            if (stackTrace[i].getClassName().indexOf("TestCase") > -1)
            {
                if ((stackTrace[i].getClassName()).equals(retorno.get(0)))
                {
                    retorno.add(stackTrace[i].getClassName());
                }
                break;
            }
        }

        if (retorno.size() == 0)
        {
            retorno = null;
        }

        return retorno;
    }
}

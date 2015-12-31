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

package br.gov.camara.hibernate;

import java.util.ArrayList;
import java.util.List;

public class JUnitDelimitadora implements DelimitadoraSessaoTransacao
{
    /**
     * Retorna a classe Facade e o m�todo que chamou esta classe
     * 
     * @return nome da classe Facade e o nome do m�todo separados por '.'
     */
    public String obterPrimeira()
    {
        String retorno = null;
        int tamanhoStackTrace;

        StackTraceElement[] stackTrace = null;
        stackTrace = new Throwable().getStackTrace();
        tamanhoStackTrace = stackTrace.length;

        // [0] -> A pr�pria fun��o determinarFacadeChamadora
        // [1] -> Algum m�todo do Hibernate plugin (determinarFacadeChamadora � private)
        // [2] -> DAO (no mais cedo, na posi��o 2)
        // [3] -> Portanto come�ar a pesquisar no �ndice 3
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
     * Retorna a classe Facade e o m�todo que chamou esta classe
     * 
     * @return nome da classe Facade e o nome do m�todo separados por '.'
     */
    public List obterTodas()
    {
        ArrayList retorno = new ArrayList();
        int tamanhoStackTrace;

        StackTraceElement[] stackTrace = null;
        stackTrace = new Throwable().getStackTrace();
        tamanhoStackTrace = stackTrace.length;

        int i;
        // [0] -> A pr�pria fun��o determinarFacadeChamadora
        // [1] -> Algum m�todo do Hibernate plugin (determinarFacadeChamadora � private)
        // [2] -> DAO (no mais cedo, na posi��o 2)
        // [3] -> Portanto come�ar a pesquisar no �ndice 3
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

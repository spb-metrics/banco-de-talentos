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

package br.gov.camara.biblioteca.util;

import java.util.HashMap;
import java.util.Map;

public class ConstantesSingleton
{
    // Inicializa o log
    private static ConstantesSingleton instanceConstantesPlugIn = null;
    private static Map mapConstantes = null;

    private ConstantesSingleton()
    {
        if (mapConstantes == null)
        {
            mapConstantes = new HashMap();
        }
    }

    public static ConstantesSingleton getInstance()
    {
        if (instanceConstantesPlugIn == null)
        {
            synchronized (ConstantesSingleton.class)
            {
                if (instanceConstantesPlugIn == null)
                {
                    instanceConstantesPlugIn = new ConstantesSingleton();
                }
            }
        }
        return instanceConstantesPlugIn;
    }

    public Object getObjeto(Object chave)
    {
        return mapConstantes.get(chave);
    }

    public void setObjeto(Object chave, Object valor)
    {
        mapConstantes.put(chave, valor);
    }

    public void removerObjeto(Object chave)
    {
        mapConstantes.remove(chave);
    }
}

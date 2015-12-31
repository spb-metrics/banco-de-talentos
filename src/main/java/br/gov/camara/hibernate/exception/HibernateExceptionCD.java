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

package br.gov.camara.hibernate.exception;

import org.hibernate.HibernateException;

public class HibernateExceptionCD extends HibernateException
{
    /**
     * 
     */
    private static final long serialVersionUID = 3567578825481081786L;

    public HibernateExceptionCD(String string, Throwable root)
    {
        super(string, root);
    }

    public HibernateExceptionCD(String s)
    {
        super(s);
    }

    public HibernateExceptionCD(Throwable root)
    {
        super(root);
    }

    protected static String getMensagem(Throwable throwable)
    {
        String mensagem = throwable.getMessage();
        if (mensagem == null)
        {
            mensagem = "Ocorreu um erro do tipo " + throwable.toString();
        }
        return mensagem;
    }

    public static void dispararExcecao(Throwable e)
    {
        if (e instanceof HibernateExceptionCD)
        {
            throw (HibernateExceptionCD) e;
        }
        else
        {
            throw new HibernateExceptionCD(getMensagem(e), e);
        }
    }

    public static void dispararExcecao(String mensagem)
    {
        throw new HibernateExceptionCD(mensagem);
    }

    public static void dispararExcecao(String mensagem, Throwable throwable)
    {
        throw new HibernateExceptionCD(mensagem + " Erro: " + throwable.getMessage());
    }
}

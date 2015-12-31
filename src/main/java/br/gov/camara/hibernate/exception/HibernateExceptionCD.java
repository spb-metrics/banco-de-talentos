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

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

/**
 * 
 */
package br.gov.camara.hibernate.interceptor.session;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.gov.camara.hibernate.exception.HibernateExceptionCD;

/**
 * @author P_6414
 *
 */
public abstract class SessionInterceptor
{
    /**
     * Executa este método logo após obter a sessão do Hibernate
     * @param sessao Sessão recuperada do SessionFactory
     * @return retorna a sessão tratada por este método
     * @throws HibernateException
     */
    public Session executarAposObterNovaSessao(Session sessao) throws HibernateExceptionCD
    {
        return sessao;
    }

    /**
     * Executa este método logo antes de fechar a sessão do Hibernate
     * @param sessao Sessão que será fechada a seguir
     * @return retorna a sessão tratada por este método
     * @throws HibernateException
     */
    public Session executarAntesFecharSessao(Session sessao) throws HibernateExceptionCD
    {
        return sessao;
    }
}

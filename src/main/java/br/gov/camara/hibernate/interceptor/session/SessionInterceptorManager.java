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

package br.gov.camara.hibernate.interceptor.session;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import br.gov.camara.biblioteca.util.ClasseDinamica;
import br.gov.camara.exception.CDException;
import br.gov.camara.hibernate.exception.HibernateExceptionCD;

public class SessionInterceptorManager
{

    public static final String TIPO_INTERCEPTOR_SESSION = "session";

    private List lstInterceptorSession = null;

    public SessionInterceptorManager(List lstNomesInterceptadoresSession)
    {
        if (lstNomesInterceptadoresSession == null || lstNomesInterceptadoresSession.size() < 1)
        {
            throw new RuntimeException("A lista de interceptadores de sessão não pode ser vazia");
        }

        this.lstInterceptorSession = new ArrayList();
        ClasseDinamica classeDinamica = new ClasseDinamica();
        for (int i = 0; i < lstNomesInterceptadoresSession.size(); i++)
        {
            SessionInterceptor sessionInterceptor = null;

            try
            {
                sessionInterceptor = (SessionInterceptor) classeDinamica.obterInstancia((String) lstNomesInterceptadoresSession.get(i));
            }
            catch (CDException cde)
            {
                throw new RuntimeException("Ocorreu o seguinte erro carregando o interceptador de sessão '"
                        + (String) lstNomesInterceptadoresSession.get(i)
                        + "': "
                        + cde.getMessage());
            }

            this.lstInterceptorSession.add(sessionInterceptor);
        }
    }

    // EXECUTAR após OBTER SESSÃO
    public Session executarAposObterNovaSessao(Session sessao, String nomeConexao) throws HibernateExceptionCD
    {
        if (this.lstInterceptorSession != null)
        {
            for (int i = 0; i < this.lstInterceptorSession.size(); i++)
            {
                SessionInterceptor sessionInterceptor = (SessionInterceptor) this.lstInterceptorSession.get(i);
                sessao = sessionInterceptor.executarAposObterNovaSessao(sessao);
            }
        }
        return sessao;
    }

    // EXECUTAR antes FECHAR SESSÃO
    public Session executarAntesFechar(Session sessao, String nomeConexao) throws HibernateExceptionCD
    {
        if (this.lstInterceptorSession != null)
        {
            for (int i = 0; i < this.lstInterceptorSession.size(); i++)
            {
                SessionInterceptor sessionInterceptor = (SessionInterceptor) this.lstInterceptorSession.get(i);
                sessao = sessionInterceptor.executarAntesFecharSessao(sessao);
            }
        }
        return sessao;
    }
}

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
            throw new RuntimeException("A lista de interceptadores de sess�o n�o pode ser vazia");
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
                throw new RuntimeException("Ocorreu o seguinte erro carregando o interceptador de sess�o '"
                        + (String) lstNomesInterceptadoresSession.get(i)
                        + "': "
                        + cde.getMessage());
            }

            this.lstInterceptorSession.add(sessionInterceptor);
        }
    }

    // EXECUTAR ap�s OBTER SESS�O
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

    // EXECUTAR antes FECHAR SESS�O
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

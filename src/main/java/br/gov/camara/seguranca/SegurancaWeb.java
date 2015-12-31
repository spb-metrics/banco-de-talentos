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
 * Created on 04/04/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.gov.camara.seguranca;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.seguranca.exception.SegurancaException;
import br.gov.camara.seguranca.util.UsuarioAutenticadoUtil;

/**
 * @author P_6414 
 */
public abstract class SegurancaWeb
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(SegurancaWeb.class);

    /**
     * The session scope attribute under which the User object for the currently
     * logged in user is stored.
     */
    public static final String CHAVE_SEGURANCAWEB = "segurancaWeb";

    private UsuarioAutenticado usuarioAutenticado = null;

    private UnidadeAutenticadora unidadeAutenticadora = null;

    private Permissao permissao = null;

    public SegurancaWeb(UnidadeAutenticadora autenticadora, UsuarioAutenticado usuario, Permissao permissao)
    {
        this.usuarioAutenticado = usuario;
        this.unidadeAutenticadora = autenticadora;
        this.permissao = permissao;
        UsuarioAutenticadoUtil.setUsuarioAutenticado(usuario);
    }

    /**
     * Remove o usu�rio logado da sess�o de forma que seja necessario fazer um
     * novo Logon
     */
    public void invalidar(HttpSession sessao)
    {
        if (log.isDebugEnabled())
        {
            log.debug("invalidarUsuario(HttpSession sessao = " + sessao + ") - inicio");
        }

        if (sessao != null)
        {
            sessao.removeAttribute(SegurancaWeb.CHAVE_SEGURANCAWEB);
        }

        try
        {
            unidadeAutenticadora.limparAutenticacao(usuarioAutenticado);
        }
        catch (SegurancaException se)
        {
            if (log.isErrorEnabled())
            {
                log.debug("Ocorreu o seguinte erro limpando a autentica��o do usu�rio: " + se.getMessage());
            }

            // Paci�ncia se ocorreu algum erro... Estamos mesmo finalizando...
        }

        this.usuarioAutenticado = null;
        this.unidadeAutenticadora = null;
        this.permissao = null;
        UsuarioAutenticadoUtil.setUsuarioAutenticado(null);

        if (log.isDebugEnabled())
        {
            log.debug("invalidarUsuario() - fim");
        }
    }

    /**
     * Retorna verdadeiro se existe algum usu�rio logado na sess�o
     * 
     * @param session
     *        Sess�o que deve ser validada
     * @return DOCUMENTAR!
     */
    public boolean temUsuarioLogado(HttpSession session)
    {
        boolean blnTemUsuarioLogado = false;
        if (log.isDebugEnabled())
        {
            log.debug("temUsuarioLogado(HttpSession session = " + session + ") - inicio");
        }

        if (session == null)
        {
            if (log.isDebugEnabled())
            {
                log.debug("Sess�o inv�lida");
            }
        }
        else
        {
            SegurancaWeb segurancaWeb = SegurancaWeb.obterSegurancaWeb(session);
            if (segurancaWeb == null)
            {
                if (log.isDebugEnabled())
                {
                    log.debug("SegurancaWeb n�o encontrada na sess�o");
                }
            }
            else if (segurancaWeb.obterUsuarioAutenticado() != null)
            {
                blnTemUsuarioLogado = true;
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug("temUsuarioLogado() - fim - retorno valor = " + blnTemUsuarioLogado);
        }
        return blnTemUsuarioLogado;
    }

    /**
     * Retorna verdadeiro se existe algum usu�rio logado na sess�o
     * 
     * @param session
     *        Sess�o que deve ser validada
     * @return DOCUMENTAR!
     */
    public static SegurancaWeb obterSegurancaWeb(HttpSession session)
    {
        SegurancaWeb seguranca = null;

        if (session != null)
        {
            seguranca = (SegurancaWeb) session.getAttribute(SegurancaWeb.CHAVE_SEGURANCAWEB);
        }

        return seguranca;
    }

    public UsuarioAutenticado obterUsuarioAutenticado()
    {
        return this.usuarioAutenticado;
    }

    public UnidadeAutenticadora obterUnidadeAutenticadora()
    {
        return this.unidadeAutenticadora;
    }

    public Permissao obterPermissao()
    {
        return this.permissao;
    }
}

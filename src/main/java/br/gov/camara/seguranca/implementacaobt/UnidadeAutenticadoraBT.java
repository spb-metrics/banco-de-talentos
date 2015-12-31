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

/*
 * Created on 01/04/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.gov.camara.seguranca.implementacaobt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.autenticacaoperfil.facade.UsuarioSistemaFacade;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;
import br.gov.camara.seguranca.UsuarioAutenticado;
import br.gov.camara.seguranca.exception.SegurancaException;

/**
 * @author P_6414
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class UnidadeAutenticadoraBT implements br.gov.camara.seguranca.UnidadeAutenticadora
{
    /**
     * Logger para esta classe
     */
    private static final Log log = LogFactory.getLog(UnidadeAutenticadoraBT.class);

    /*
     * (non-Javadoc)
     * 
     * @see sigesp.comum.util.seguranca.UnidadeAutenticadora#autenticarUsuario(java.lang.String,
     *      java.lang.String)
     */
    public UsuarioAutenticado autenticarUsuario(String strLoginUsuario, String strSenhaUsuario) throws SegurancaException
    {
        UsuarioAutenticado usuarioBT = null;

        if (log.isDebugEnabled())
        {
            log.debug("autenticarUsuario(String strLoginUsuario = " + strLoginUsuario + ", String strSenhaUsuario) - inicio");
        }

        // Verifica usuário sistema
        try
        {
            UsuarioSistema usuario;
            UsuarioSistemaFacade usuarioFacade = new UsuarioSistemaFacade();
            usuario = usuarioFacade.obterPeloLogin(strLoginUsuario);
            if (usuario == null)
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Usuário não encontrado");
                }
                throw new SegurancaException("Usuário não encontrado");
            }
            else if (!strSenhaUsuario.equals(usuario.getSenhaHash()))
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Senha não confere");
                }
                throw new SegurancaException("Senha não confere");
            }
            else
            {

                try
                {
                    usuarioBT = new UsuarioAutenticadoBT(usuario);
                }
                catch (SegurancaException se)
                {
                    log.error("autenticarUsuario(String strLoginUsuario = " + strLoginUsuario + ", String strSenhaUsuario) - catch", se);

                    // TODO Implementar exceção 
                }

                if (log.isDebugEnabled())
                {
                    log.debug("autenticarUsuario() - fim - retorno valor = " + usuarioBT);
                }
            }
        }
        catch (CDException d)
        {
            log.error("autenticarUsuario(String strLoginUsuario = " + strLoginUsuario + ", String strSenhaUsuario = " + strSenhaUsuario + ") - catch", d);

            // TODO Implementar exceção
            throw new SegurancaException("Ocorreu um erro tentando autenticar o usuário");
        }

        if (log.isDebugEnabled())
        {
            log.debug("autenticarUsuario() - fim - retorno valor = " + null);
        }
        return usuarioBT;
    }

    /*
     * (non-Javadoc)
     * 
     * @see sigesp.comum.util.seguranca.UnidadeAutenticadora#alterarSenha(sigesp.comum.util.seguranca.UsuarioAutenticado,
     *      java.lang.String, java.lang.String)
     */
    public boolean alterarSenhaUsuario(UsuarioAutenticado usuarioAutenticado, String strSenhaAtual, String strNovaSenha) throws SegurancaException
    {
        if (log.isDebugEnabled())
        {
            log.debug("alterarSenhaUsuario(UsuarioAutenticado usuarioAutenticado = "
                    + usuarioAutenticado
                    + ", String strSenhaAtual = "
                    + strSenhaAtual
                    + ", String strNovaSenha = "
                    + strNovaSenha
                    + ") - inicio");
        }

        // TODO Auto-generated method stub

        if (log.isDebugEnabled())
        {
            log.debug("alterarSenhaUsuario() - fim - retorno valor = " + false);
        }
        return false;
    }

    /* (non-Javadoc)
     * @see br.gov.camara.seguranca.UnidadeAutenticadora#limparAutenticacao(br.gov.camara.seguranca.UsuarioAutenticado)
     */
    public boolean limparAutenticacao(UsuarioAutenticado usuarioAutenticado) throws br.gov.camara.seguranca.exception.SegurancaException
    {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see br.gov.camara.seguranca.UnidadeAutenticadora#validarAutenticacao(br.gov.camara.seguranca.UsuarioAutenticado)
     */
    public boolean validarAutenticacao(UsuarioAutenticado usuarioAutenticado) throws br.gov.camara.seguranca.exception.SegurancaException
    {
        boolean blnRetorno = false;

        if (usuarioAutenticado != null)
        {
            if (usuarioAutenticado.obterIdentificadorGrupo() != null && !"C".equals(usuarioAutenticado.obterIdentificadorGrupo()))
            {
                blnRetorno = true;
            }
        }

        return blnRetorno;
    }

    /* (non-Javadoc)
     * @see br.gov.camara.seguranca.UnidadeAutenticadora#validarSenhaUsuario(br.gov.camara.seguranca.UsuarioAutenticado, java.lang.String)
     */
    public boolean validarSenhaUsuario(UsuarioAutenticado usuarioAutenticado, String strSenhaAtual) throws SegurancaException
    {
        // TODO Auto-generated method stub
        return false;
    }

}
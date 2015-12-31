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

        // Verifica usu�rio sistema
        try
        {
            UsuarioSistema usuario;
            UsuarioSistemaFacade usuarioFacade = new UsuarioSistemaFacade();
            usuario = usuarioFacade.obterPeloLogin(strLoginUsuario);
            if (usuario == null)
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Usu�rio n�o encontrado");
                }
                throw new SegurancaException("Usu�rio n�o encontrado");
            }
            else if (!strSenhaUsuario.equals(usuario.getSenhaHash()))
            {
                if (log.isDebugEnabled())
                {
                    log.debug("Senha n�o confere");
                }
                throw new SegurancaException("Senha n�o confere");
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

                    // TODO Implementar exce��o 
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

            // TODO Implementar exce��o
            throw new SegurancaException("Ocorreu um erro tentando autenticar o usu�rio");
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
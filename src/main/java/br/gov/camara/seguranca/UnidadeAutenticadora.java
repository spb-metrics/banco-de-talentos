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
 * Created on 29/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.gov.camara.seguranca;

import br.gov.camara.seguranca.exception.SegurancaException;

/**
 * @author P_6414
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface UnidadeAutenticadora
{

    public UsuarioAutenticado autenticarUsuario(String strLoginUsuario, String strSenhaUsuario) throws SegurancaException;

    /**
     * Servico de alteraçao de senha de usuário.
     *
     * @param strSenhaAtual : Login do usuário.
     * @param strNovaSenha : Senha do usuário.
     *
     * @return : True, se a rotina encerrou-se normalmente e a senha foi alterada.
     *
     * @throws SegurancaException : Mensagem de erro, caso tenha havido problemas na alteração da senha.
     */
    public boolean alterarSenhaUsuario(UsuarioAutenticado usuarioAutenticado, String strSenhaAtual, String strNovaSenha) throws SegurancaException;

    /**
     * Servico de validação de senha de usuário.
     *
     * @param usuarioAutenticao : Usuário que já está autenticado.
     * @param strNovaSenha : Senha do usuário.
     *
     * @return : True, se a senha do usuário autenticado está correta.
     *
     * @throws SegurancaException : Mensagem de erro, caso tenha havido problemas na validação da senha.
     */
    public boolean validarSenhaUsuario(UsuarioAutenticado usuarioAutenticado, String strSenhaAtual) throws SegurancaException;

    /**
     * Servico que valida se a autenticação do usuário ainda é válida.
     *
     * @param UsuarioAutenticado autenticação do usuário que deve ser validada
     *
     * @return : True, se a rotina encerrou-se normalmente e a autenticação do usuário é válida.
     *
     * @throws SegurancaException : Mensagem de erro, caso tenha havido problemas na validação.
     */
    public boolean validarAutenticacao(UsuarioAutenticado usuarioAutenticado) throws SegurancaException;

    /**
     * Servico que limpa a autenticação de um usuário (logoff).
     *
     * @param UsuarioAutenticado autenticação do usuário que deve ser limpada
     *
     * @return : True, se a rotina encerrou-se normalmente e o usuário não está mais autenticado.
     *
     * @throws SegurancaException : Mensagem de erro, caso tenha havido problemas na validação.
     */
    public boolean limparAutenticacao(UsuarioAutenticado usuarioAutenticado) throws SegurancaException;

}

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
     * Servico de altera�ao de senha de usu�rio.
     *
     * @param strSenhaAtual : Login do usu�rio.
     * @param strNovaSenha : Senha do usu�rio.
     *
     * @return : True, se a rotina encerrou-se normalmente e a senha foi alterada.
     *
     * @throws SegurancaException : Mensagem de erro, caso tenha havido problemas na altera��o da senha.
     */
    public boolean alterarSenhaUsuario(UsuarioAutenticado usuarioAutenticado, String strSenhaAtual, String strNovaSenha) throws SegurancaException;

    /**
     * Servico de valida��o de senha de usu�rio.
     *
     * @param usuarioAutenticao : Usu�rio que j� est� autenticado.
     * @param strNovaSenha : Senha do usu�rio.
     *
     * @return : True, se a senha do usu�rio autenticado est� correta.
     *
     * @throws SegurancaException : Mensagem de erro, caso tenha havido problemas na valida��o da senha.
     */
    public boolean validarSenhaUsuario(UsuarioAutenticado usuarioAutenticado, String strSenhaAtual) throws SegurancaException;

    /**
     * Servico que valida se a autentica��o do usu�rio ainda � v�lida.
     *
     * @param UsuarioAutenticado autentica��o do usu�rio que deve ser validada
     *
     * @return : True, se a rotina encerrou-se normalmente e a autentica��o do usu�rio � v�lida.
     *
     * @throws SegurancaException : Mensagem de erro, caso tenha havido problemas na valida��o.
     */
    public boolean validarAutenticacao(UsuarioAutenticado usuarioAutenticado) throws SegurancaException;

    /**
     * Servico que limpa a autentica��o de um usu�rio (logoff).
     *
     * @param UsuarioAutenticado autentica��o do usu�rio que deve ser limpada
     *
     * @return : True, se a rotina encerrou-se normalmente e o usu�rio n�o est� mais autenticado.
     *
     * @throws SegurancaException : Mensagem de erro, caso tenha havido problemas na valida��o.
     */
    public boolean limparAutenticacao(UsuarioAutenticado usuarioAutenticado) throws SegurancaException;

}

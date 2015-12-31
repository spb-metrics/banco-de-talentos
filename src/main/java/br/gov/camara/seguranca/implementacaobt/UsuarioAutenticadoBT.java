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

package br.gov.camara.seguranca.implementacaobt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;
import br.gov.camara.seguranca.UsuarioAutenticado;
import br.gov.camara.seguranca.exception.SegurancaException;

/**
 * Respons�vel pela autentica�ao do usu�rio no banco de dados e pelo servico altera�ao de senha.
 */
public class UsuarioAutenticadoBT implements UsuarioAutenticado
{

    // Inicializa o log
    private static Log log = LogFactory.getLog(UsuarioAutenticadoBT.class);

    // Identifica��o do usu�rio
    private Integer intIdentificador = null;
    private String strLogin = null;
    private String strNome = null;
    private Integer intIdentificadorGrupo = null;

    /**
     * Cria um novo objeto Seguranca utilizando a autentica��o externa
     *
     * @param sesSessaoAtual DOCUMENTAR!
     * @param strAutenticacaoExterna DOCUMENTAR!
     * @param strLogin DOCUMENTAR!
     * @param strAutenticacaoExterna DOCUMENTAR!
     */
    public UsuarioAutenticadoBT(UsuarioSistema usuarioSistema) throws SegurancaException
    {
        if (log.isDebugEnabled())
        {
            log.debug("UsuarioBTWeb(UsuarioSistema usuarioSistema = " + usuarioSistema + ") - inicio");
        }

        this.intIdentificador = usuarioSistema.getIdentificador();
        this.strLogin = usuarioSistema.getLogin();
        this.strNome = usuarioSistema.getNome();
        this.intIdentificadorGrupo = usuarioSistema.getGrupo().getCodigo();

        if (log.isDebugEnabled())
        {
            log.debug("UsuarioBTWeb() - fim");
        }
    }

    /**
     * DOCUMENTAR!
     *
     * @return DOCUMENTAR!
     */
    public String obterLogin()
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterLogin() - inicio");
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterLogin() - fim - retorno valor = " + this.strLogin);
        }
        return this.strLogin;
    }

    /**
     * DOCUMENTAR!
     *
     * @return DOCUMENTAR!
     */
    public Integer obterIdentificador()
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterIdentificador() - inicio");
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterIdentificador() - fim - retorno valor = " + this.intIdentificador);
        }
        return this.intIdentificador;
    }

    /**
     * DOCUMENTAR!
     *
     * @return DOCUMENTAR!
     */
    public Integer obterIdentificadorGrupo()
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterIdentificadorGrupo() - inicio");
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterIdentificadorGrupo() - fim - retorno valor = " + this.intIdentificadorGrupo);
        }
        return this.intIdentificadorGrupo;
    }

    /**
     * DOCUMENTAR!
     *
     * @return DOCUMENTAR!
     */
    public String obterNome()
    {
        if (log.isDebugEnabled())
        {
            log.debug("obterNome() - inicio");
        }

        if (log.isDebugEnabled())
        {
            log.debug("obterNome() - fim - retorno valor = " + this.strNome);
        }
        return this.strNome;
    }

}

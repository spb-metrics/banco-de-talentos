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

package br.gov.camara.seguranca;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.seguranca.UsuarioAutenticado;
import br.gov.camara.seguranca.exception.SegurancaException;

public class AutenticacaoTest extends BaseAutenticacaoTestCase
{
    /**
    * Logger for this class
    */
    private static final Log log = LogFactory.getLog(AutenticacaoTest.class);

    public void testLoginM10002Sucesso() throws SegurancaException
    {
        log.info(MENSAGEM_INICIO_TESTE);
        String login = "M10002";
        String senha = "Senha";

        UsuarioAutenticado usuarioAutenticado = null;
        try
        {
        usuarioAutenticado = unidadeAutenticadora.autenticarUsuario(login, senha);
        }
        catch (CDException cde)
        {
        	// Ser� detectado abaixo
        }
        assertNotNull(usuarioAutenticado);
    }
}

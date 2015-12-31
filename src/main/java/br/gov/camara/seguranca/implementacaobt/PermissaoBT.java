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
package br.gov.camara.seguranca.implementacaobt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.autenticacaoperfil.facade.UsuarioSistemaFacade;
import br.gov.camara.seguranca.UsuarioAutenticado;
import br.gov.camara.seguranca.exception.SegurancaException;

/**
 * @author P_6414
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PermissaoBT implements br.gov.camara.seguranca.Permissao
{
    /**
     * Logger para esta classe
     */
    private static final Log log = LogFactory.getLog(PermissaoBT.class);

    /* (non-Javadoc)
     * @see sigesp.comum.util.seguranca.Permissao#temAutorizacaoFuncionalidade(sigesp.comum.util.seguranca.UsuarioAutenticado, java.lang.String)
     */
    public boolean temAutorizacaoFuncionalidade(UsuarioAutenticado usuarioAutenticado, String strFuncionalidade) throws SegurancaException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        if ((strFuncionalidade == null) || "".equals(strFuncionalidade))
        {
            if (log.isInfoEnabled())
            {
                log.info("Como o objeto controlado n�o foi informado, retorna false");
            }

            return false;
        }

        Boolean blnResultadoCache = null;

        // O objeto controlado j� foi validado ?
        // Implementar cache para melhorar o desempenho

        if (blnResultadoCache == null)
        {
            // N�o, ele ainda n�o foi validado (n�o est� no cache). Portanto:
            // validar...

            UsuarioSistemaFacade segurancaFacade = new UsuarioSistemaFacade();
            boolean blnTemAutorizacao = false;

            try
            {
                blnTemAutorizacao = segurancaFacade.verificarPermissaoUsuarioFuncionalidade(usuarioAutenticado.obterIdentificador().toString(), (usuarioAutenticado.obterIdentificadorGrupo() == null ? "" : usuarioAutenticado.obterIdentificadorGrupo().toString()), strFuncionalidade);
            }
            catch (CDException c)
            {
                log.debug("Ocorreu um erro tentando validar a permiss�o deste usu�rio");

                throw new SegurancaException("Ocorreu um erro tentando validar a permiss�o deste usu�rio");
            }

            blnResultadoCache = new Boolean(blnTemAutorizacao);

            // Registrar a autoriza��o para este item de menu no cache
            // Implementar cache: adicionarNoCachePermissoes(strFuncionalidade, blnResultadoCache);
        }

        return blnResultadoCache.booleanValue();
    }

}

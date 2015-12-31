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
                log.info("Como o objeto controlado não foi informado, retorna false");
            }

            return false;
        }

        Boolean blnResultadoCache = null;

        // O objeto controlado já foi validado ?
        // Implementar cache para melhorar o desempenho

        if (blnResultadoCache == null)
        {
            // Não, ele ainda não foi validado (não está no cache). Portanto:
            // validar...

            UsuarioSistemaFacade segurancaFacade = new UsuarioSistemaFacade();
            boolean blnTemAutorizacao = false;

            try
            {
                blnTemAutorizacao = segurancaFacade.verificarPermissaoUsuarioFuncionalidade(usuarioAutenticado.obterIdentificador().toString(), (usuarioAutenticado.obterIdentificadorGrupo() == null ? "" : usuarioAutenticado.obterIdentificadorGrupo().toString()), strFuncionalidade);
            }
            catch (CDException c)
            {
                log.debug("Ocorreu um erro tentando validar a permissão deste usuário");

                throw new SegurancaException("Ocorreu um erro tentando validar a permissão deste usuário");
            }

            blnResultadoCache = new Boolean(blnTemAutorizacao);

            // Registrar a autorização para este item de menu no cache
            // Implementar cache: adicionarNoCachePermissoes(strFuncionalidade, blnResultadoCache);
        }

        return blnResultadoCache.booleanValue();
    }

}

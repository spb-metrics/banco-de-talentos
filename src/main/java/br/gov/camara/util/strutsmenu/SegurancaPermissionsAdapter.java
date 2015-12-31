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

package br.gov.camara.util.strutsmenu;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.PermissionsAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.seguranca.SegurancaWeb;
import br.gov.camara.seguranca.UsuarioAutenticado;
import br.gov.camara.seguranca.exception.SegurancaException;

/**
 * DOCUMENTAR!
 */
public class SegurancaPermissionsAdapter implements PermissionsAdapter
{

    // Inicializa o log
    private static Log log = LogFactory.getLog(SegurancaPermissionsAdapter.class);

    private HttpSession session;

    private Map mapPermissoesUsuario;

    /**
     * Creates a new instance of SegurancaPermissionAdapter
     *
     * @param theMenuNames DOCUMENTAR!
     */
    public SegurancaPermissionsAdapter(HttpSession session)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Inicializando SegurancaPermissionAdapter");
        }
        this.session = session;
        this.mapPermissoesUsuario = new HashMap();
    }

    /**
     * If the menu is allowed, this should return true.
     *
     * @param menu DOCUMENTAR!
     *
     * @return whether or not the menu is allowed.
     */
    public boolean isAllowed(MenuComponent menu)
    {

        boolean blnTemAutorizacao = false;

        SegurancaWeb segurancaWeb = SegurancaWeb.obterSegurancaWeb(session);

        if (segurancaWeb != null && segurancaWeb.temUsuarioLogado(session))
        {
            UsuarioAutenticado usuarioWeb = SegurancaWeb.obterSegurancaWeb(session).obterUsuarioAutenticado();

            Boolean blnResultadoCache = null;
            String strObjetoControlado = null;

            // Qual o item sendo validado ?
            String strNome = menu.getName();

            // A permiss�o j� foi verificada ?...
            blnResultadoCache = (Boolean) mapPermissoesUsuario.get(strNome);
            if (blnResultadoCache == null)
            {
                // ...N�o, ela ainda n�o foi validada (n�o est� no cache). Portanto: validar...

                // Recupera o objeto controlado
                String strPagina = menu.getPage();
                if ("".equals(strPagina))
                {
                    strPagina = null;
                }
                String strURL = menu.getUrl();
                if ("".equals(strURL))
                {
                    strURL = null;
                }

                if (strPagina == null && strURL == null)
                {
                    // N�o existe um link para uma p�gina, portanto deve ser um menu agrupador

                    MenuComponent[] subMenus = menu.getMenuComponents();

                    if (subMenus.length > 0)
                    {
                        // Este item de menu tem filhos...

                        // Este usu�rio tem autoriza��o para algum item de menu filho ?
                        for (int i = 0; i < subMenus.length; i++)
                        {
                            if (isAllowed(subMenus[i]))
                            {
                                blnTemAutorizacao = true;
                            }
                        }

                        // Registra o log
                        if (log.isDebugEnabled())
                        {
                            if (blnTemAutorizacao)
                            {
                                log.debug("O usu�rio '"
                                        + usuarioWeb.obterLogin()
                                        + "' est� logado e tem permiss�o para o menu agrupador '"
                                        + strObjetoControlado
                                        + "' (pois tem permiss�o para um de seus filhos)");
                            }
                            else
                            {
                                log.debug("O usu�rio '"
                                        + usuarioWeb.obterLogin()
                                        + "' est� logado e n�o tem permiss�o para o menu agrupador '"
                                        + strObjetoControlado
                                        + "' (pois n�o tem permiss�o para nenhum de seus filhos)");
                            }
                        }
                    }
                    else
                    {
                        // Este item de menu n�o tem filhos...
                        // ... e como n�o tem p�gina associada, ...              /* n�o exibir */
                        // ... EXIBIR, pois deve ser um item pai (Menu superior) /* Acrescentado em 19/07/2007 */

                        blnTemAutorizacao = true;

                        // Registra o log
                        if (log.isDebugEnabled())
                        {
                            log.debug("O usu�rio '"
                                    + usuarioWeb.obterLogin()
                                    + "' est� logado e n�o tem permiss�o para o menu '"
                                    + strObjetoControlado
                                    + "' (pois ele n�o tem uma p�gina associada e nem menus filhos)");
                        }
                    }

                }
                else
                {
                    String strAvaliacao = strPagina;
                    if (strAvaliacao == null)
                    {
                        strAvaliacao = strURL;
                    }

                    // Ser� que � uma a��o do Struts (cont�m .do)?
                    int delimitadorFinal = strAvaliacao.lastIndexOf(".do");
                    if (delimitadorFinal > -1)
                    {
                        // � uma a��o do Struts, portanto devemos avaliar a quest�o de permiss�o
                        String auxiliar = strAvaliacao.substring(0, delimitadorFinal).trim();
                        if (auxiliar.length() > 0)
                        {
                            String[] subAuxiliar = auxiliar.split("[\'\"/ ]");
                            auxiliar = subAuxiliar[subAuxiliar.length - 1];

                            // Acrescentar a barra inicial
                            strObjetoControlado = "/" + auxiliar;
                        }
                        else
                        {
                            // N�o deveria acontecer, pois um link para '/.do' n�o faz muito sentido, mas liberar o acesso...
                            blnTemAutorizacao = true;
                        }
                    }
                    else
                    {
                        // N�o � uma a��o do tipo Struts, portanto liberar o acesso
                        blnTemAutorizacao = true;
                    }

                    if (!blnTemAutorizacao)
                    {

                        // Verificar a autoriza��o no banco de dados...
                        try
                        {
                            blnTemAutorizacao = SegurancaWeb.obterSegurancaWeb(session).obterPermissao().temAutorizacaoFuncionalidade(SegurancaWeb.obterSegurancaWeb(session).obterUsuarioAutenticado(), strObjetoControlado);

                            if (blnTemAutorizacao)
                            {
                                if (log.isDebugEnabled())
                                {
                                    log.debug("O usu�rio '" + usuarioWeb.obterLogin() + "' est� logado e tem permiss�o para o menu: " + strObjetoControlado);
                                }
                            }
                            else
                            {
                                if (log.isDebugEnabled())
                                {
                                    log.debug("O usu�rio '"
                                            + usuarioWeb.obterLogin()
                                            + "' est� logado mas N�O tem permiss�o para o menu: "
                                            + strObjetoControlado);
                                }
                            }
                        }
                        catch (SegurancaException ue)
                        {
                            blnTemAutorizacao = false;
                        }
                    }
                }

                // Registrar a autoriza��o para este item de menu no cache
                mapPermissoesUsuario.put(strNome, new Boolean(blnTemAutorizacao));
            }
            else
            {
                // Sim, ele j� foi validado e o resultado �...

                blnTemAutorizacao = blnResultadoCache.booleanValue();

                if (log.isDebugEnabled())
                {
                    if (blnTemAutorizacao)
                    {
                        log.debug("A permiss�o do usu�rio '"
                                + usuarioWeb.obterLogin()
                                + "' est� sendo recuperada do cache e ele tem permiss�o no objeto '"
                                + strObjetoControlado
                                + "'");
                    }
                    else
                    {
                        log.debug("A permiss�o do usu�rio '"
                                + usuarioWeb.obterLogin()
                                + "' est� sendo recuperada do cache, mas ele n�o tem permiss�o no objeto '"
                                + strObjetoControlado
                                + "'");
                    }
                }
            }

        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("Como n�o tem usu�rio logado, retornar que n�o tem acesso para o menu: " + menu.getName());
            }
        }

        return blnTemAutorizacao;
    }
}

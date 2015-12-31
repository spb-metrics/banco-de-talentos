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

            // A permissão já foi verificada ?...
            blnResultadoCache = (Boolean) mapPermissoesUsuario.get(strNome);
            if (blnResultadoCache == null)
            {
                // ...Não, ela ainda não foi validada (não está no cache). Portanto: validar...

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
                    // Não existe um link para uma página, portanto deve ser um menu agrupador

                    MenuComponent[] subMenus = menu.getMenuComponents();

                    if (subMenus.length > 0)
                    {
                        // Este item de menu tem filhos...

                        // Este usuário tem autorização para algum item de menu filho ?
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
                                log.debug("O usuário '"
                                        + usuarioWeb.obterLogin()
                                        + "' está logado e tem permissão para o menu agrupador '"
                                        + strObjetoControlado
                                        + "' (pois tem permissão para um de seus filhos)");
                            }
                            else
                            {
                                log.debug("O usuário '"
                                        + usuarioWeb.obterLogin()
                                        + "' está logado e não tem permissão para o menu agrupador '"
                                        + strObjetoControlado
                                        + "' (pois não tem permissão para nenhum de seus filhos)");
                            }
                        }
                    }
                    else
                    {
                        // Este item de menu não tem filhos...
                        // ... e como não tem página associada, ...              /* não exibir */
                        // ... EXIBIR, pois deve ser um item pai (Menu superior) /* Acrescentado em 19/07/2007 */

                        blnTemAutorizacao = true;

                        // Registra o log
                        if (log.isDebugEnabled())
                        {
                            log.debug("O usuário '"
                                    + usuarioWeb.obterLogin()
                                    + "' está logado e não tem permissão para o menu '"
                                    + strObjetoControlado
                                    + "' (pois ele não tem uma página associada e nem menus filhos)");
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

                    // Será que é uma ação do Struts (contém .do)?
                    int delimitadorFinal = strAvaliacao.lastIndexOf(".do");
                    if (delimitadorFinal > -1)
                    {
                        // É uma ação do Struts, portanto devemos avaliar a questão de permissão
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
                            // Não deveria acontecer, pois um link para '/.do' não faz muito sentido, mas liberar o acesso...
                            blnTemAutorizacao = true;
                        }
                    }
                    else
                    {
                        // Não é uma ação do tipo Struts, portanto liberar o acesso
                        blnTemAutorizacao = true;
                    }

                    if (!blnTemAutorizacao)
                    {

                        // Verificar a autorização no banco de dados...
                        try
                        {
                            blnTemAutorizacao = SegurancaWeb.obterSegurancaWeb(session).obterPermissao().temAutorizacaoFuncionalidade(SegurancaWeb.obterSegurancaWeb(session).obterUsuarioAutenticado(), strObjetoControlado);

                            if (blnTemAutorizacao)
                            {
                                if (log.isDebugEnabled())
                                {
                                    log.debug("O usuário '" + usuarioWeb.obterLogin() + "' está logado e tem permissão para o menu: " + strObjetoControlado);
                                }
                            }
                            else
                            {
                                if (log.isDebugEnabled())
                                {
                                    log.debug("O usuário '"
                                            + usuarioWeb.obterLogin()
                                            + "' está logado mas NÃO tem permissão para o menu: "
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

                // Registrar a autorização para este item de menu no cache
                mapPermissoesUsuario.put(strNome, new Boolean(blnTemAutorizacao));
            }
            else
            {
                // Sim, ele já foi validado e o resultado é...

                blnTemAutorizacao = blnResultadoCache.booleanValue();

                if (log.isDebugEnabled())
                {
                    if (blnTemAutorizacao)
                    {
                        log.debug("A permissão do usuário '"
                                + usuarioWeb.obterLogin()
                                + "' está sendo recuperada do cache e ele tem permissão no objeto '"
                                + strObjetoControlado
                                + "'");
                    }
                    else
                    {
                        log.debug("A permissão do usuário '"
                                + usuarioWeb.obterLogin()
                                + "' está sendo recuperada do cache, mas ele não tem permissão no objeto '"
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
                log.debug("Como não tem usuário logado, retornar que não tem acesso para o menu: " + menu.getName());
            }
        }

        return blnTemAutorizacao;
    }
}

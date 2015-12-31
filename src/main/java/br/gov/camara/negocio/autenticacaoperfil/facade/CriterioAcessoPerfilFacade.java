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

package br.gov.camara.negocio.autenticacaoperfil.facade;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.autenticacaoperfil.dao.CriterioAcessoPerfilDAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.CriterioAcessoPerfil;
import br.gov.camara.negocio.autenticacaoperfil.pojo.PerfilSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;
import br.gov.camara.seguranca.UsuarioAutenticado;

/**
 * Facade para atributo de talento
 */
public class CriterioAcessoPerfilFacade extends Facade
{
    // Variáveis de instância
    private static Log log = LogFactory.getLog(CriterioAcessoPerfilFacade.class);

    /**
     * Obtém o total de registros
     * 
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public int obterTotalRegistros() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        CriterioAcessoPerfilDAO objCriterioAcessoPerfilDAO = new CriterioAcessoPerfilDAO(obterNomeConexao());
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objCriterioAcessoPerfilDAO.obterTotalRegistros();
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return intTotalRegistros;
    }

    /**
     * Obtém os registros de determinada página
     * 
     * @param int Número da página a ser mostrada
     * @param int Quantidade de registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        CriterioAcessoPerfilDAO objCriterioAcessoPerfilDAO = new CriterioAcessoPerfilDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objCriterioAcessoPerfilDAO.obterPorPagina(intNumeroPagina, intMaximoPagina);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return lstRetorno;
    }

    /**
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return CriterioAcessoPerfil POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public CriterioAcessoPerfil obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        CriterioAcessoPerfilDAO objCriterioAcessoPerfilDAO = new CriterioAcessoPerfilDAO(obterNomeConexao());
        CriterioAcessoPerfil objCriterioAcessoPerfil = null;
        try
        {
            objCriterioAcessoPerfil = (CriterioAcessoPerfil) objCriterioAcessoPerfilDAO.obterPelaChave(strChave);
            //objCriterioAcessoPerfilDAO.inicializarPerfisSistema(objCriterioAcessoPerfil);

        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return objCriterioAcessoPerfil;
    }

    /**
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return CriterioAcessoPerfil POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public CriterioAcessoPerfil obterPelaChave(CriterioAcessoPerfil objCriterioAcessoPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        CriterioAcessoPerfilDAO objCriterioAcessoPerfilDAO = new CriterioAcessoPerfilDAO(obterNomeConexao());
        try
        {
            objCriterioAcessoPerfil = (CriterioAcessoPerfil) objCriterioAcessoPerfilDAO.obterPelaChave(objCriterioAcessoPerfil);
            if (objCriterioAcessoPerfil != null)
            {
                objCriterioAcessoPerfilDAO.inicializarCriterioAcesso(objCriterioAcessoPerfil);
            }

        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return objCriterioAcessoPerfil;
    }

    /**
     * Inclui um registro
     *
     * @param objCriterioAcessoPerfil POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String incluir(CriterioAcessoPerfil objCriterioAcessoPerfil, UsuarioAutenticado usuarioAutenticado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strChave = null;

        // Instancia DAO e inclui registro
        GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade(obterNomeConexao());
        CriterioAcessoPerfilDAO objCriterioAcessoPerfilDAO = new CriterioAcessoPerfilDAO(obterNomeConexao());
        UsuarioPerfilSistemaFacade objUsuarioPerfilSistemaFacade = new UsuarioPerfilSistemaFacade(obterNomeConexao());
        UsuarioCriterioAcessoPerfilFacade objUsuarioCriterioAcessoPerfilFacade = new UsuarioCriterioAcessoPerfilFacade();
        try
        {
            UsuarioSistema objUsuarioSistema = new UsuarioSistema();
            objUsuarioSistema.setIdentificador(usuarioAutenticado.obterIdentificador());

            if ((!objGestorSistemaFacade.isGestorSistema(objCriterioAcessoPerfil.getPerfilSistema().getSistema(), objUsuarioSistema))
                    && (!objUsuarioPerfilSistemaFacade.isGestorPerfil(objCriterioAcessoPerfil.getPerfilSistema(), objUsuarioSistema)))
            {
                throw new CDException("Somente gestores deste perfil podem incluir um critério");
            }

            List lstCriterioAcessoPerfil = objCriterioAcessoPerfilDAO.obterCriterios(objCriterioAcessoPerfil);
            if (lstCriterioAcessoPerfil != null && lstCriterioAcessoPerfil.size() > 0)
            {
                // Obtém apenas o primeiro registro para verificar se o registro retornado possui algum valor
                CriterioAcessoPerfil objCriterioAcessoPerfilList = (CriterioAcessoPerfil) lstCriterioAcessoPerfil.get(0);
                if ((objCriterioAcessoPerfilList.getValorCriterioAcessoPerfil() != null && !"".equals(objCriterioAcessoPerfilList.getValorCriterioAcessoPerfil()))
                        && (objCriterioAcessoPerfil.getValorCriterioAcessoPerfil() == null || "".equals(objCriterioAcessoPerfil.getValorCriterioAcessoPerfil())))
                {
                    throw new CDException("O campo valor do critério não pode estar em branco, pois já existe uma valoração para o critério escolhido atribuída ao perfil");
                }
                else if ((objCriterioAcessoPerfilList.getValorCriterioAcessoPerfil() == null || "".equals(objCriterioAcessoPerfilList.getValorCriterioAcessoPerfil()))
                        && (objCriterioAcessoPerfil.getValorCriterioAcessoPerfil() != null && !"".equals(objCriterioAcessoPerfil.getValorCriterioAcessoPerfil())))
                {
                    throw new CDException("Já existe uma atribuição do critério escolhido para o perfil com valor do critério em branco");
                }
            }

            DAO.iniciarTransacao(obterNomeConexao());
            objCriterioAcessoPerfilDAO.incluir(objCriterioAcessoPerfil);
            objUsuarioCriterioAcessoPerfilFacade.incluir(objCriterioAcessoPerfil);
            DAO.realizarTransacao(obterNomeConexao());
        }
        catch (Exception daoe)
        {
            DAO.desfazerTransacao(obterNomeConexao());
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return strChave;
    }

    /**
     * Altera um registro
     *
     * @param objCriterioAcessoPerfil POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void alterar(CriterioAcessoPerfil objCriterioAcessoPerfil, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro
        GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade(obterNomeConexao());
        CriterioAcessoPerfilDAO objCriterioAcessoPerfilDAO = new CriterioAcessoPerfilDAO(obterNomeConexao());
        UsuarioPerfilSistemaFacade objUsuarioPerfilSistemaFacade = new UsuarioPerfilSistemaFacade(obterNomeConexao());
        try
        {
            if ((!objGestorSistemaFacade.isGestorSistema(objCriterioAcessoPerfil.getPerfilSistema().getSistema(), objUsuarioSistema))
                    && (!objUsuarioPerfilSistemaFacade.isGestorPerfil(objCriterioAcessoPerfil.getPerfilSistema(), objUsuarioSistema)))
            {
                throw new CDException("Somente gestores deste perfil podem alterar um critério");
            }
            objCriterioAcessoPerfilDAO.alterar(objCriterioAcessoPerfil);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
    }

    /**
     * Exclui um registro
     *
     * @param objCriterioAcessoPerfil POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void excluir(CriterioAcessoPerfil objCriterioAcessoPerfil, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e exclui registro
        GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade(obterNomeConexao());
        CriterioAcessoPerfilDAO objCriterioAcessoPerfilDAO = new CriterioAcessoPerfilDAO(obterNomeConexao());
        UsuarioPerfilSistemaFacade objUsuarioPerfilSistemaFacade = new UsuarioPerfilSistemaFacade(obterNomeConexao());
        UsuarioCriterioAcessoPerfilFacade objUsuarioCriterioAcessoPerfilFacade = new UsuarioCriterioAcessoPerfilFacade();
        try
        {
            if ((!objGestorSistemaFacade.isGestorSistema(objCriterioAcessoPerfil.getPerfilSistema().getSistema(), objUsuarioSistema))
                    && (!objUsuarioPerfilSistemaFacade.isGestorPerfil(objCriterioAcessoPerfil.getPerfilSistema(), objUsuarioSistema)))
            {
                throw new CDException("Somente gestores deste perfil podem excluir um critério");
            }
            DAO.iniciarTransacao(obterNomeConexao());

            objUsuarioCriterioAcessoPerfilFacade.excluir(objCriterioAcessoPerfil);
            objCriterioAcessoPerfilDAO.excluir(objCriterioAcessoPerfil);

            DAO.realizarTransacao(obterNomeConexao());
        }
        catch (Exception daoe)
        {
            DAO.desfazerTransacao(obterNomeConexao());
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
    }

    /**
     * Obtém os registros de determinada página
     * 
     * @param int Número da página a ser mostrada
     * @param int Quantidade de registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterTodos() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        CriterioAcessoPerfilDAO objCriterioAcessoPerfilDAO = new CriterioAcessoPerfilDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objCriterioAcessoPerfilDAO.obterTodos();
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return lstRetorno;
    }

    /**
     * Obtém os registros de determinada página
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPeloPerfilSistema(CriterioAcessoPerfil objCriterioAcessoPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        CriterioAcessoPerfilDAO objCriterioAcessoPerfilDAO = new CriterioAcessoPerfilDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objCriterioAcessoPerfilDAO.obterPeloPerfilSistema(objCriterioAcessoPerfil);
            if (lstRetorno != null && lstRetorno.size() > 0)
            {
                objCriterioAcessoPerfilDAO.inicializarCriterioAcesso(lstRetorno);
            }
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return lstRetorno;
    }

    public void efetuarCopiaCriterios(PerfilSistema objPerfilSistemaOrigem, String[] idePerfilSistemaDestino, UsuarioAutenticado usuarioAutenticado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        List lstCriteriosPerfil = null;

        PerfilSistemaFacade objPerfilSistemaFacade = new PerfilSistemaFacade();
        CriterioAcessoPerfil objCriterioAcessoPerfil = new CriterioAcessoPerfil();
        try
        {
            DAO.iniciarTransacao(obterNomeConexao());
            objCriterioAcessoPerfil.setPerfilSistema(objPerfilSistemaOrigem);
            lstCriteriosPerfil = obterPeloPerfilSistema(objCriterioAcessoPerfil);

            for (int i = 0; i < idePerfilSistemaDestino.length; i++)
            {
                Integer intPerfilDestino = new Integer(idePerfilSistemaDestino[i]);
                for (int j = 0; j < lstCriteriosPerfil.size(); j++)
                {
                    CriterioAcessoPerfil objCriterioAcessoPerfilOrigem = (CriterioAcessoPerfil) lstCriteriosPerfil.get(j);
                    CriterioAcessoPerfil objCriterioAcessoPerfilDestino = new CriterioAcessoPerfil();
                    objCriterioAcessoPerfilDestino.setCriterioAcesso(objCriterioAcessoPerfilOrigem.getCriterioAcesso());
                    PerfilSistema objPerfilSistemaDestino = new PerfilSistema();
                    objPerfilSistemaDestino.setIdentificador(intPerfilDestino);
                    objPerfilSistemaDestino = objPerfilSistemaFacade.obterPelaChave(objPerfilSistemaDestino);
                    objCriterioAcessoPerfilDestino.setPerfilSistema(objPerfilSistemaDestino);
                    objCriterioAcessoPerfilDestino.setNomeCriterioAcessoPerfil(objCriterioAcessoPerfilOrigem.getNomeCriterioAcessoPerfil());
                    objCriterioAcessoPerfilDestino.setValorCriterioAcessoPerfil(objCriterioAcessoPerfilOrigem.getValorCriterioAcessoPerfil());
                    objCriterioAcessoPerfilDestino.setPreRequisito(objCriterioAcessoPerfilOrigem.getPreRequisito());
                    incluir(objCriterioAcessoPerfilDestino, usuarioAutenticado);
                }
            }
            DAO.realizarTransacao(obterNomeConexao());
        }
        catch (Exception daoe)
        {
            DAO.desfazerTransacao(obterNomeConexao());
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
    }
}

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
import br.gov.camara.negocio.autenticacaoperfil.dao.GestorSistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.dao.SistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.AgrupadorPerfil;
import br.gov.camara.negocio.autenticacaoperfil.pojo.GestorSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Grupo;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Sistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;

/**
 * Facade para atributo de talento
 */
public class SistemaFacade extends Facade
{
    // Variáveis de instância
    private static Log log = LogFactory.getLog(SistemaFacade.class);

    /**
     * Construtor Default
     */
    public SistemaFacade()
    {
        super();
    }

    /**
     * @param nomeConexao
     *
     */
    public SistemaFacade(String nomeConexao)
    {
        super(nomeConexao);
    }

    /**
     * Obtém o total de registros
     * @param objSistema 
     *
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public int obterTotalRegistros(Sistema objSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        int intTotalRegistros = 0;

        try
        {
            intTotalRegistros = objSistemaDAO.obterTotalRegistros(objSistema);
        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return intTotalRegistros;
    }

    /**
     * Obtém os registros de determinada página
     * @param intNumeroPagina 
     * @param intMaximoPagina 
     * @param objSistema 
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina, Sistema objSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        List lstRetorno = null;

        try
        {
            lstRetorno = objSistemaDAO.obterPorPagina(intNumeroPagina, intMaximoPagina, objSistema);
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
     * @param objSistema Chave do registro a ser obtido
     *
     * @return Sistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public Sistema obterPelaChave(Sistema objSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        Sistema objRetorno = null;

        try
        {
            objRetorno = (Sistema) objSistemaDAO.obterPelaChave(objSistema);

            if (objRetorno != null && objRetorno.getSistemaAgrupador() != null)
            {
                objSistemaDAO.inicializarSistemaAgrupador(objRetorno);
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

        return objRetorno;
    }

    /**
     * Obtém um registro a partir da chave
     *
     * @param strObjetoControlado Nome do Objeto Controlado 
     *
     * @return Sistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public Sistema obterPeloObjetoControlado(String strObjetoControlado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        Sistema objRetorno = null;

        try
        {
            objRetorno = (Sistema) objSistemaDAO.obterPeloObjetoControlado(strObjetoControlado);

            if (objRetorno != null && objRetorno.getSistemaAgrupador() != null)
            {
                objSistemaDAO.inicializarSistemaAgrupador(objRetorno);
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

        return objRetorno;
    }

    /**
     * Inclui um registro
     *
     * @param objSistema POJO representando o objeto a ser incluído
     * @param objUsuarioSistema 
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String incluir(Sistema objSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strChave = null;

        // Instancia DAO e inclui registro
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());

        try
        {
            DAO.iniciarTransacao(obterNomeConexao());

            GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade();
            GestorSistemaDAO objGestorSistemaDAO = new GestorSistemaDAO(obterNomeConexao());

            if (objSistema.getSistemaAgrupador() != null)
            {
                if (!objGestorSistemaFacade.isAdministradorSistema(objSistema.getSistemaAgrupador(), objUsuarioSistema))
                {
                    throw new CDException("Somente administradores deste sistema podem realizar inclusão de subsistemas");
                }
                else if (objSistemaDAO.isObjetoControladoExistente(objSistema))
                {
                    throw new CDException("O nome do objeto controlado já está cadastrado para outro sistema");
                }
                else
                {
                    strChave = objSistemaDAO.incluir(objSistema);
                }
            }
            else if (objSistemaDAO.isObjetoControladoExistente(objSistema))
            {
                throw new CDException("O nome do objeto controlado já está cadastrado para outro sistema");
            }
            else
            {
                strChave = objSistemaDAO.incluir(objSistema);
                objSistema.setIdentificador(new Integer(strChave));

                GestorSistema objGestorSistema = new GestorSistema();
                objGestorSistema.setIndicativoAdministrador("S");
                objGestorSistema.setSistema(objSistema);
                objGestorSistema.setUsuarioSistema(objUsuarioSistema);
                objGestorSistemaDAO.incluir(objGestorSistema);
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

        return strChave;
    }

    /**
     * Altera um registro
     *
     * @param objSistema POJO representando o objeto a ser incluído
     * @param objUsuarioSistema 
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void alterar(Sistema objSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade();

        try
        {

            if (!objGestorSistemaFacade.isAdministradorSistema(objSistema, objUsuarioSistema))
            {
                throw new CDException("Somente administradores deste sistema podem realizar alteração");
            }
            else if (objSistemaDAO.isObjetoControladoExistente(objSistema))
            {
                throw new CDException("O nome do objeto controlado já está cadastrado para outro sistema");
            }

            objSistemaDAO.alterar(objSistema);
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
     * @param objSistema POJO representando o objeto a ser incluído
     * @param objUsuarioSistema 
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void excluir(Sistema objSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e exclui registro
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        GestorSistemaDAO objGestorSistemaDAO = new GestorSistemaDAO(obterNomeConexao());
        GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade(obterNomeConexao());

        try
        {
            if (!objGestorSistemaFacade.isAdministradorSistema(objSistema, objUsuarioSistema))
            {
                throw new CDException("Somente administradores deste sistema podem realizar exclusão");
            }
            else
            {
                DAO.iniciarTransacao(obterNomeConexao());

                // Realiza exclusão do Gestor
                GestorSistema objGestorSistema = new GestorSistema();
                objGestorSistema.setSistema(objSistema);
                objGestorSistema.setUsuarioSistema(objUsuarioSistema);
                objGestorSistemaDAO.excluir(objGestorSistema);

                // Realiza exclusão do Sistema
                objSistemaDAO.excluir(objSistema);

                DAO.realizarTransacao(obterNomeConexao());
            }
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
     * Obtém todos os sistemas
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
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        List lstRetorno = null;

        try
        {
            lstRetorno = objSistemaDAO.obterTodos();
        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return lstRetorno;
    }

    /**
     * Obtém todos os sistemas
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public List obterTodosAgrupadorNull() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        List lstRetorno = null;

        try
        {
            lstRetorno = objSistemaDAO.obterTodosAgrupadorNull();
        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return lstRetorno;
    }

    /**
     * Obtém pelo agrupador
     *
     * @param objSistema objeto contendo os parâmetros
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public List obterPeloSistemaAgrupador(Sistema objSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        List lstRetorno = null;

        try
        {
            lstRetorno = objSistemaDAO.obterPeloSistemaAgrupador(objSistema);
        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return lstRetorno;
    }

    /**
     * Obtém pelo agrupador
     *
     * @param intIdentificador
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public List obterPeloSistemaAgrupador(Integer intIdentificador) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        List lstRetorno = null;

        try
        {
            lstRetorno = objSistemaDAO.obterPeloSistemaAgrupador(intIdentificador);
        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return lstRetorno;
    }

    /**
     * Obtém pelo agrupador
     *
     * @param objSistema objeto contendo os parâmetros
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public List obterSistemasFilho(Sistema objSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        List lstRetorno = null;

        try
        {
            lstRetorno = objSistemaDAO.obterSistemasFilhos(objSistema);
        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return lstRetorno;
    }

    /**
     * Obtém pela Chave com agrupador null
     *
     * @param objSistema objeto contendo os parâmetros
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public List obterPelaChaveAgrupadorNull(Sistema objSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        List lstRetorno = null;

        try
        {
            lstRetorno = objSistemaDAO.obterPelaChaveAgrupadorNull(objSistema);
        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return lstRetorno;
    }

    /**
     * Obtém os sistemas que o usuário está habilitado
     *
     * @param objUsuarioSistema objeto contendo os parâmetros
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public List obterPorUsuario(UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        List lstRetorno = null;

        try
        {
            lstRetorno = objSistemaDAO.obterPorUsuario(objUsuarioSistema);
        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return lstRetorno;
    }

    /**
     * Obtém os sistemas que o usuário está habilitado
     *
     * @param objUsuarioSistema objeto contendo os parâmetros
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public List obterPorUsuario(UsuarioSistema objUsuarioSistema, boolean blnFiltrarVisibilidade) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        List lstRetorno = null;

        try
        {
            lstRetorno = objSistemaDAO.obterPorUsuario(objUsuarioSistema, blnFiltrarVisibilidade);
        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return lstRetorno;
    }

    /**
     * Obtém os sistemas que o usuário está habilitado
     *
     * @param objUsuarioSistema objeto contendo os parâmetros
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public List obterSistemasComunsEntreUsuarioComAcessoEUsuarioEhGestor(UsuarioSistema objUsuario, UsuarioSistema objUsuarioLogado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        List lstRetorno = null;

        try
        {
            lstRetorno = objSistemaDAO.obterSistemasComunsEntreUsuarioComAcessoEUsuarioEhGestor(objUsuario, objUsuarioLogado);
        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return lstRetorno;
    }

    /**
     * Obtém os sistemas que o usuário está habilitado
     *
     * @param objUsuarioSistema objeto contendo os parâmetros
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public List obterPorGrupoUsuarioEhGestor(Grupo objGrupo, UsuarioSistema objUsuarioLogado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        List lstRetorno = null;

        try
        {
            lstRetorno = objSistemaDAO.obterPorGrupoUsuarioEhGestor(objGrupo, objUsuarioLogado);
        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return lstRetorno;
    }

    /**
     * Obtém os sistemas que o usuário está habilitado
     *
     * @param objUsuarioSistema objeto contendo os parâmetros
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public List obterSistemasUsuarioEhGestorAgrupadorNull(UsuarioSistema objUsuarioLogado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        List lstRetorno = null;

        try
        {
            lstRetorno = objSistemaDAO.obterSistemasUsuarioEhGestorAgrupadorNull(objUsuarioLogado);
        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return lstRetorno;
    }

    /**
     * Obtém os sistemas que o usuário está habilitado
     *
     * @param objUsuarioSistema objeto contendo os parâmetros
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public List obterSistemasUsuarioEhGestor(UsuarioSistema objUsuarioLogado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        List lstRetorno = null;

        try
        {
            lstRetorno = objSistemaDAO.obterSistemasUsuarioEhGestor(objUsuarioLogado);
        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return lstRetorno;
    }

    /**
     * Obtém os sistemas que o agrupador de perfil possui acesso
     *
     * @param objAgrupadorPerfil objeto contendo os parâmetros do agrupador
     * @param objUsuarioSistema objeto contendo os parâmetros do usuário logado no sistema
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public List obterSistemasDoAgrupador(AgrupadorPerfil objAgrupadorPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        SistemaDAO objSistemaDAO = new SistemaDAO(obterNomeConexao());
        List lstRetorno = null;

        try
        {
            lstRetorno = objSistemaDAO.obterSistemasDoAgrupador(objAgrupadorPerfil);
        }
        catch (Exception e)
        {
            throw new CDException(e.getMessage(), e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return lstRetorno;
    }

}

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
    // Vari�veis de inst�ncia
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
     * Obt�m o total de registros
     * @param objSistema 
     *
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public int obterTotalRegistros(Sistema objSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m os registros de determinada p�gina
     * @param intNumeroPagina 
     * @param intMaximoPagina 
     * @param objSistema 
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina, Sistema objSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m um registro a partir da chave
     *
     * @param objSistema Chave do registro a ser obtido
     *
     * @return Sistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public Sistema obterPelaChave(Sistema objSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
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
     * Obt�m um registro a partir da chave
     *
     * @param strObjetoControlado Nome do Objeto Controlado 
     *
     * @return Sistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public Sistema obterPeloObjetoControlado(String strObjetoControlado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
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
     * @param objSistema POJO representando o objeto a ser inclu�do
     * @param objUsuarioSistema 
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
                    throw new CDException("Somente administradores deste sistema podem realizar inclus�o de subsistemas");
                }
                else if (objSistemaDAO.isObjetoControladoExistente(objSistema))
                {
                    throw new CDException("O nome do objeto controlado j� est� cadastrado para outro sistema");
                }
                else
                {
                    strChave = objSistemaDAO.incluir(objSistema);
                }
            }
            else if (objSistemaDAO.isObjetoControladoExistente(objSistema))
            {
                throw new CDException("O nome do objeto controlado j� est� cadastrado para outro sistema");
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
     * @param objSistema POJO representando o objeto a ser inclu�do
     * @param objUsuarioSistema 
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
                throw new CDException("Somente administradores deste sistema podem realizar altera��o");
            }
            else if (objSistemaDAO.isObjetoControladoExistente(objSistema))
            {
                throw new CDException("O nome do objeto controlado j� est� cadastrado para outro sistema");
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
     * @param objSistema POJO representando o objeto a ser inclu�do
     * @param objUsuarioSistema 
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
                throw new CDException("Somente administradores deste sistema podem realizar exclus�o");
            }
            else
            {
                DAO.iniciarTransacao(obterNomeConexao());

                // Realiza exclus�o do Gestor
                GestorSistema objGestorSistema = new GestorSistema();
                objGestorSistema.setSistema(objSistema);
                objGestorSistema.setUsuarioSistema(objUsuarioSistema);
                objGestorSistemaDAO.excluir(objGestorSistema);

                // Realiza exclus�o do Sistema
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
     * Obt�m todos os sistemas
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public List obterTodos() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m todos os sistemas
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public List obterTodosAgrupadorNull() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m pelo agrupador
     *
     * @param objSistema objeto contendo os par�metros
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public List obterPeloSistemaAgrupador(Sistema objSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m pelo agrupador
     *
     * @param intIdentificador
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public List obterPeloSistemaAgrupador(Integer intIdentificador) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m pelo agrupador
     *
     * @param objSistema objeto contendo os par�metros
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public List obterSistemasFilho(Sistema objSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m pela Chave com agrupador null
     *
     * @param objSistema objeto contendo os par�metros
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public List obterPelaChaveAgrupadorNull(Sistema objSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m os sistemas que o usu�rio est� habilitado
     *
     * @param objUsuarioSistema objeto contendo os par�metros
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public List obterPorUsuario(UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m os sistemas que o usu�rio est� habilitado
     *
     * @param objUsuarioSistema objeto contendo os par�metros
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public List obterPorUsuario(UsuarioSistema objUsuarioSistema, boolean blnFiltrarVisibilidade) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m os sistemas que o usu�rio est� habilitado
     *
     * @param objUsuarioSistema objeto contendo os par�metros
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public List obterSistemasComunsEntreUsuarioComAcessoEUsuarioEhGestor(UsuarioSistema objUsuario, UsuarioSistema objUsuarioLogado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m os sistemas que o usu�rio est� habilitado
     *
     * @param objUsuarioSistema objeto contendo os par�metros
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public List obterPorGrupoUsuarioEhGestor(Grupo objGrupo, UsuarioSistema objUsuarioLogado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m os sistemas que o usu�rio est� habilitado
     *
     * @param objUsuarioSistema objeto contendo os par�metros
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public List obterSistemasUsuarioEhGestorAgrupadorNull(UsuarioSistema objUsuarioLogado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m os sistemas que o usu�rio est� habilitado
     *
     * @param objUsuarioSistema objeto contendo os par�metros
     *
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public List obterSistemasUsuarioEhGestor(UsuarioSistema objUsuarioLogado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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
     * Obt�m os sistemas que o agrupador de perfil possui acesso
     *
     * @param objAgrupadorPerfil objeto contendo os par�metros do agrupador
     * @param objUsuarioSistema objeto contendo os par�metros do usu�rio logado no sistema
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public List obterSistemasDoAgrupador(AgrupadorPerfil objAgrupadorPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
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

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
import br.gov.camara.negocio.autenticacaoperfil.dao.AgrupadorPerfilDAO;
import br.gov.camara.negocio.autenticacaoperfil.dao.PerfilSistemaAgrupadorPerfilDAO;
import br.gov.camara.negocio.autenticacaoperfil.dao.PerfilSistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.dao.SistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.AgrupadorPerfil;
import br.gov.camara.negocio.autenticacaoperfil.pojo.AgrupadorPerfilUsuario;
import br.gov.camara.negocio.autenticacaoperfil.pojo.PerfilSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.PerfilSistemaAgrupadorPerfil;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;

/**
 * Facade para atributo de talento
 */
public class PerfilSistemaAgrupadorPerfilFacade extends Facade
{
    // Vari�veis de inst�ncia
    private static Log log = LogFactory.getLog(PerfilSistemaAgrupadorPerfilFacade.class);

    /**
     * Construtor Default
     */
    public PerfilSistemaAgrupadorPerfilFacade()
    {
        super();
    }

    /**
     * @param nomeConexao
     *
     */
    public PerfilSistemaAgrupadorPerfilFacade(String nomeConexao)
    {
        super(nomeConexao);
    }

    /**
     * Obt�m o total de registros
     * 
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public int obterTotalRegistros() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        PerfilSistemaAgrupadorPerfilDAO objPerfilSistemaAgrupadorPerfilDAO = new PerfilSistemaAgrupadorPerfilDAO(obterNomeConexao());
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objPerfilSistemaAgrupadorPerfilDAO.obterTotalRegistros();
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
     * Obt�m os registros de determinada p�gina
     * 
     * @param int N�mero da p�gina a ser mostrada
     * @param int Quantidade de registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        PerfilSistemaAgrupadorPerfilDAO objPerfilSistemaAgrupadorPerfilDAO = new PerfilSistemaAgrupadorPerfilDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objPerfilSistemaAgrupadorPerfilDAO.obterPorPagina(intNumeroPagina, intMaximoPagina);
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
     * @param strChave Chave do registro a ser obtido
     * 
     * @return PerfilSistemaAgrupadorPerfil POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public PerfilSistemaAgrupadorPerfil obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        PerfilSistemaAgrupadorPerfilDAO objPerfilSistemaAgrupadorPerfilDAO = new PerfilSistemaAgrupadorPerfilDAO(obterNomeConexao());
        PerfilSistemaAgrupadorPerfil objPerfilSistemaAgrupadorPerfil = null;
        try
        {
            objPerfilSistemaAgrupadorPerfil = (PerfilSistemaAgrupadorPerfil) objPerfilSistemaAgrupadorPerfilDAO.obterPelaChave(strChave);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return objPerfilSistemaAgrupadorPerfil;
    }

    /**
     * Inclui um registro
     *
     * @param objPerfilSistemaAgrupadorPerfil POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String incluir(PerfilSistemaAgrupadorPerfil objPerfilSistemaAgrupadorPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strChave = null;

        // Instancia Facade
        AgrupadorPerfilFacade objAgrupadorPerfilFacade = new AgrupadorPerfilFacade();
        UsuarioCriterioAcessoPerfilFacade objUsuarioCriterioAcessoPerfilFacade = null;

        // Instancia DAO e inclui registro
        PerfilSistemaAgrupadorPerfilDAO objPerfilSistemaAgrupadorPerfilDAO = new PerfilSistemaAgrupadorPerfilDAO(obterNomeConexao());
        try
        {
            // Obt�m os usu�rios associados ao agrupador para associ�-los ao crit�rio de acesso
            List lstUsuarios = objAgrupadorPerfilFacade.obterUsuariosAssociados(objPerfilSistemaAgrupadorPerfil.getAgrupadorPerfil());
            if (lstUsuarios != null && lstUsuarios.size() > 0)
            {
                objUsuarioCriterioAcessoPerfilFacade = new UsuarioCriterioAcessoPerfilFacade();
                for (int i = 0; i < lstUsuarios.size(); i++)
                {
                    AgrupadorPerfilUsuario objAgrupadorPerfilUsuario = (AgrupadorPerfilUsuario) lstUsuarios.get(i);
                    objUsuarioCriterioAcessoPerfilFacade.incluir(objPerfilSistemaAgrupadorPerfil.getPerfilSistema(), objAgrupadorPerfilUsuario.getUsuarioSistema());
                }
            }

            strChave = objPerfilSistemaAgrupadorPerfilDAO.incluir(objPerfilSistemaAgrupadorPerfil);
        }
        catch (Exception daoe)
        {
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
     * @param objPerfilSistemaAgrupadorPerfil POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public void alterar(PerfilSistemaAgrupadorPerfil objPerfilSistemaAgrupadorPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro
        PerfilSistemaAgrupadorPerfilDAO objPerfilSistemaAgrupadorPerfilDAO = new PerfilSistemaAgrupadorPerfilDAO(obterNomeConexao());
        try
        {
            objPerfilSistemaAgrupadorPerfilDAO.alterar(objPerfilSistemaAgrupadorPerfil);
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
     * @param objPerfilSistemaAgrupadorPerfil POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public void excluir(PerfilSistemaAgrupadorPerfil objPerfilSistemaAgrupadorPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia Facade
        AgrupadorPerfilFacade objAgrupadorPerfilFacade = new AgrupadorPerfilFacade();
        UsuarioCriterioAcessoPerfilFacade objUsuarioCriterioAcessoPerfilFacade = null;

        // Instancia DAO e exclui registro
        PerfilSistemaAgrupadorPerfilDAO objPerfilSistemaAgrupadorPerfilDAO = new PerfilSistemaAgrupadorPerfilDAO(obterNomeConexao());
        try
        {
            // Obt�m os usu�rios associados ao agrupador para associ�-los ao crit�rio de acesso
            List lstUsuarios = objAgrupadorPerfilFacade.obterUsuariosAssociados(objPerfilSistemaAgrupadorPerfil.getAgrupadorPerfil());
            if (lstUsuarios != null && lstUsuarios.size() > 0)
            {
                objUsuarioCriterioAcessoPerfilFacade = new UsuarioCriterioAcessoPerfilFacade();
                for (int i = 0; i < lstUsuarios.size(); i++)
                {
                    AgrupadorPerfilUsuario objAgrupadorPerfilUsuario = (AgrupadorPerfilUsuario) lstUsuarios.get(i);
                    objUsuarioCriterioAcessoPerfilFacade.excluir(objPerfilSistemaAgrupadorPerfil.getPerfilSistema(), objAgrupadorPerfilUsuario.getUsuarioSistema());
                }
            }

            objPerfilSistemaAgrupadorPerfilDAO.excluir(objPerfilSistemaAgrupadorPerfil);
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
     * Obt�m os registros de determinada p�gina
     * 
     * @param int N�mero da p�gina a ser mostrada
     * @param int Quantidade de registros
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
        PerfilSistemaAgrupadorPerfilDAO objPerfilSistemaAgrupadorPerfilDAO = new PerfilSistemaAgrupadorPerfilDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objPerfilSistemaAgrupadorPerfilDAO.obterTodos();
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
     * Obt�m o total de registros
     * 
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public int obterTotalRegistrosPerfisDisponiveis(PerfilSistemaAgrupadorPerfil objPerfilSistemaAgrupadorPerfil, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        PerfilSistemaAgrupadorPerfilDAO objPerfilSistemaAgrupadorPerfilDAO = new PerfilSistemaAgrupadorPerfilDAO(obterNomeConexao());
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objPerfilSistemaAgrupadorPerfilDAO.obterTotalRegistrosPerfisDisponiveis(objPerfilSistemaAgrupadorPerfil, objUsuarioSistema);
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

    public List obterPerfisDisponiveisPorPagina(PerfilSistemaAgrupadorPerfil objPerfilSistemaAgrupadorPerfil, UsuarioSistema objUsuarioSistema, int intPagina, int intTotalRegistros) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        PerfilSistemaAgrupadorPerfilDAO objPerfilSistemaAgrupadorPerfilDAO = new PerfilSistemaAgrupadorPerfilDAO(obterNomeConexao());
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objPerfilSistemaAgrupadorPerfilDAO.obterPerfisDisponiveisPorPagina(objPerfilSistemaAgrupadorPerfil, objUsuarioSistema, intPagina, intTotalRegistros);
            if (lstRetorno != null && lstRetorno.size() > 0)
            {
                for (int i = 0; i < lstRetorno.size(); i++)
                {
                    objPerfilSistemaDAO.inicializarPerfilSistema((PerfilSistema) lstRetorno.get(i));
                }
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

    /**
     * Obt�m o total de registros
     * 
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public int obterTotalRegistrosPerfisSelecionados(AgrupadorPerfil objAgrupadorPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        PerfilSistemaAgrupadorPerfilDAO objPerfilSistemaAgrupadorPerfilDAO = new PerfilSistemaAgrupadorPerfilDAO(obterNomeConexao());
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objPerfilSistemaAgrupadorPerfilDAO.obterTotalRegistrosPerfisSelecionados(objAgrupadorPerfil);
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

    public List obterPerfisSelecionadosPorPagina(AgrupadorPerfil objAgrupadorPerfil, int intPagina, int intTotalRegistros) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        PerfilSistemaAgrupadorPerfilDAO objPerfilSistemaAgrupadorPerfilDAO = new PerfilSistemaAgrupadorPerfilDAO(obterNomeConexao());
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO();
        SistemaDAO objSistemaDAO = new SistemaDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objPerfilSistemaAgrupadorPerfilDAO.obterPerfisSelecionadosPorPagina(objAgrupadorPerfil, intPagina, intTotalRegistros);
            if (lstRetorno != null && lstRetorno.size() > 0)
            {
                for (int i = 0; i < lstRetorno.size(); i++)
                {
                    objPerfilSistemaDAO.inicializarPerfilSistema((PerfilSistema) lstRetorno.get(i));
                    objSistemaDAO.inicializarSistema(((PerfilSistema) lstRetorno.get(i)).getSistema());
                }
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

    public void atualizarPerfis(String strOperacao, PerfilSistemaAgrupadorPerfil objPerfilSistemaAgrupadorPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        try
        {
            if ("E".equals(strOperacao))
            {
                excluir(objPerfilSistemaAgrupadorPerfil);
            }
            else if ("I".equals(strOperacao))
            {
                incluir(objPerfilSistemaAgrupadorPerfil);
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

    }

    public List obterPeloPerfil(PerfilSistema objPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        PerfilSistemaAgrupadorPerfilDAO objPerfilSistemaAgrupadorPerfilDAO = new PerfilSistemaAgrupadorPerfilDAO(obterNomeConexao());
        AgrupadorPerfilDAO objAgrupadorPerfilDAO = new AgrupadorPerfilDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objPerfilSistemaAgrupadorPerfilDAO.obterPeloPerfil(objPerfilSistema);
            if (lstRetorno != null && lstRetorno.size() > 0)
            {
                for (int i = 0; i < lstRetorno.size(); i++)
                {
                    objAgrupadorPerfilDAO.inicializarAgrupadorPerfil(((PerfilSistemaAgrupadorPerfil) lstRetorno.get(i)).getAgrupadorPerfil());
                }
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
}

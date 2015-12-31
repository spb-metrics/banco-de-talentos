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
import br.gov.camara.negocio.autenticacaoperfil.dao.AgrupadorPerfilDAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.AgrupadorPerfil;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;

/**
 * Facade para atributo de talento
 */
public class AgrupadorPerfilFacade extends Facade
{
    // Variáveis de instância
    private static Log log = LogFactory.getLog(AgrupadorPerfilFacade.class);

    /**
     * Construtor Default
     */
    public AgrupadorPerfilFacade()
    {
        super();
    }

    /**
     * @param nomeConexao
     *
     */
    public AgrupadorPerfilFacade(String nomeConexao)
    {
        super(nomeConexao);
    }

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
        AgrupadorPerfilDAO objAgrupadorPerfilDAO = new AgrupadorPerfilDAO(obterNomeConexao());
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objAgrupadorPerfilDAO.obterTotalRegistros();
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
        AgrupadorPerfilDAO objAgrupadorPerfilDAO = new AgrupadorPerfilDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objAgrupadorPerfilDAO.obterPorPagina(intNumeroPagina, intMaximoPagina);
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
     * @return AgrupadorPerfil POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public AgrupadorPerfil obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        AgrupadorPerfilDAO objAgrupadorPerfilDAO = new AgrupadorPerfilDAO(obterNomeConexao());
        AgrupadorPerfil objAgrupadorPerfil = null;
        try
        {
            objAgrupadorPerfil = (AgrupadorPerfil) objAgrupadorPerfilDAO.obterPelaChave(strChave);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return objAgrupadorPerfil;
    }

    /**
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return AgrupadorPerfil POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public AgrupadorPerfil obterPelaChaveInicializaAutorizados(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        AgrupadorPerfilDAO objAgrupadorPerfilDAO = new AgrupadorPerfilDAO(obterNomeConexao());
        AgrupadorPerfil objAgrupadorPerfil = null;
        try
        {
            objAgrupadorPerfil = (AgrupadorPerfil) objAgrupadorPerfilDAO.obterPelaChave(strChave);
            objAgrupadorPerfilDAO.inicializarUsuariosAutorizados(objAgrupadorPerfil);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return objAgrupadorPerfil;
    }

    /**
     * Inclui um registro
     *
     * @param objAgrupadorPerfil POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String incluir(AgrupadorPerfil objAgrupadorPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strChave = null;

        // Instancia DAO e inclui registro
        AgrupadorPerfilDAO objAgrupadorPerfilDAO = new AgrupadorPerfilDAO(obterNomeConexao());
        try
        {
            strChave = objAgrupadorPerfilDAO.incluir(objAgrupadorPerfil);
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
     * @param objAgrupadorPerfil POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void alterar(AgrupadorPerfil objAgrupadorPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro
        AgrupadorPerfilDAO objAgrupadorPerfilDAO = new AgrupadorPerfilDAO(obterNomeConexao());
        try
        {
            objAgrupadorPerfilDAO.alterar(objAgrupadorPerfil);
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
     * @param objAgrupadorPerfil POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void excluir(AgrupadorPerfil objAgrupadorPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e exclui registro
        AgrupadorPerfilDAO objAgrupadorPerfilDAO = new AgrupadorPerfilDAO(obterNomeConexao());
        try
        {
            List lstRetorno = objAgrupadorPerfilDAO.obterUsuariosAssociados(objAgrupadorPerfil);
            if (lstRetorno != null && lstRetorno.size() > 0)
            {
                throw new CDException("Não é possível excluir um agrupador que possui usuário associado");
            }

            lstRetorno = objAgrupadorPerfilDAO.obterSistemasAssociados(objAgrupadorPerfil);
            if (lstRetorno != null && lstRetorno.size() > 0)
            {
                throw new CDException("Não é possível excluir um agrupador que possui sistema associado");
            }

            lstRetorno = objAgrupadorPerfilDAO.obterGruposAssociados(objAgrupadorPerfil);
            if (lstRetorno != null && lstRetorno.size() > 0)
            {
                throw new CDException("Não é possível excluir um agrupador que possui grupo associado");
            }

            lstRetorno = objAgrupadorPerfilDAO.obterPerfisAssociados(objAgrupadorPerfil);
            if (lstRetorno != null && lstRetorno.size() > 0)
            {
                throw new CDException("Não é possível excluir um agrupador que possui perfil associado");
            }

            objAgrupadorPerfilDAO.excluir(objAgrupadorPerfil);
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
     * Obtém os usuário associados ao agrupador
     * 
     * @param AgrupadorPerfil Contém os dados para consulta
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterUsuariosAssociados(AgrupadorPerfil objAgrupadorPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        AgrupadorPerfilDAO objAgrupadorPerfilDAO = new AgrupadorPerfilDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objAgrupadorPerfilDAO.obterUsuariosAssociados(objAgrupadorPerfil);
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
     * Obtém os perfis associados ao agrupador
     * 
     * @param AgrupadorPerfil Contém os dados para consulta
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPerfisAssociados(AgrupadorPerfil objAgrupadorPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        AgrupadorPerfilDAO objAgrupadorPerfilDAO = new AgrupadorPerfilDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objAgrupadorPerfilDAO.obterPerfisAssociados(objAgrupadorPerfil);
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
        AgrupadorPerfilDAO objAgrupadorPerfilDAO = new AgrupadorPerfilDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objAgrupadorPerfilDAO.obterTodos();
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
     * Obtém lista de agrupadores em que o usuário é autorizado
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterAgrupadoresDeAutorizadoPorPagina(UsuarioSistema objUsuarioSistema, int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        AgrupadorPerfilDAO objAgrupadorPerfilDAO = new AgrupadorPerfilDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objAgrupadorPerfilDAO.obterAgrupadoresDeAutorizadoPorPagina(objUsuarioSistema, intNumeroPagina, intMaximoPagina);
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
     * Obtém o total de registros
     * 
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public int obterTotalRegistrosAgrupadoresDeAutorizado(UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        AgrupadorPerfilDAO objAgrupadorPerfilDAO = new AgrupadorPerfilDAO(obterNomeConexao());
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objAgrupadorPerfilDAO.obterTotalRegistrosAgrupadoresDeAutorizado(objUsuarioSistema);
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

}

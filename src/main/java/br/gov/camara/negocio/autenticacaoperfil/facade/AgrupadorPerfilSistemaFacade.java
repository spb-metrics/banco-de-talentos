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
import br.gov.camara.negocio.autenticacaoperfil.dao.AgrupadorPerfilSistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.AgrupadorPerfil;
import br.gov.camara.negocio.autenticacaoperfil.pojo.AgrupadorPerfilSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Sistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;

/**
 * Facade para atributo de talento
 */
public class AgrupadorPerfilSistemaFacade extends Facade
{
    // Variáveis de instância
    private static Log log = LogFactory.getLog(AgrupadorPerfilSistemaFacade.class);

    /**
     * Construtor Default
     */
    public AgrupadorPerfilSistemaFacade()
    {
        super();
    }

    /**
     * @param nomeConexao
     *
     */
    public AgrupadorPerfilSistemaFacade(String nomeConexao)
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
        AgrupadorPerfilSistemaDAO objAgrupadorPerfilSistemaDAO = new AgrupadorPerfilSistemaDAO(obterNomeConexao());
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objAgrupadorPerfilSistemaDAO.obterTotalRegistros();
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
        AgrupadorPerfilSistemaDAO objAgrupadorPerfilSistemaDAO = new AgrupadorPerfilSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objAgrupadorPerfilSistemaDAO.obterPorPagina(intNumeroPagina, intMaximoPagina);
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
     * @return AgrupadorPerfilSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public AgrupadorPerfilSistema obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        AgrupadorPerfilSistemaDAO objAgrupadorPerfilSistemaDAO = new AgrupadorPerfilSistemaDAO(obterNomeConexao());
        AgrupadorPerfilSistema objAgrupadorPerfilSistema = null;
        try
        {
            objAgrupadorPerfilSistema = (AgrupadorPerfilSistema) objAgrupadorPerfilSistemaDAO.obterPelaChave(strChave);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return objAgrupadorPerfilSistema;
    }

    /**
     * Inclui um registro
     *
     * @param objAgrupadorPerfilSistema POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String incluir(AgrupadorPerfilSistema objAgrupadorPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strChave = null;

        // Instancia DAO e inclui registro
        AgrupadorPerfilSistemaDAO objAgrupadorPerfilSistemaDAO = new AgrupadorPerfilSistemaDAO(obterNomeConexao());
        try
        {
            strChave = objAgrupadorPerfilSistemaDAO.incluir(objAgrupadorPerfilSistema);
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
     * @param objAgrupadorPerfilSistema POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void alterar(AgrupadorPerfilSistema objAgrupadorPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro
        AgrupadorPerfilSistemaDAO objAgrupadorPerfilSistemaDAO = new AgrupadorPerfilSistemaDAO(obterNomeConexao());
        try
        {
            objAgrupadorPerfilSistemaDAO.alterar(objAgrupadorPerfilSistema);
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
     * @param objAgrupadorPerfilSistema POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void excluir(AgrupadorPerfilSistema objAgrupadorPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e exclui registro
        AgrupadorPerfilSistemaDAO objAgrupadorPerfilSistemaDAO = new AgrupadorPerfilSistemaDAO(obterNomeConexao());
        try
        {
            objAgrupadorPerfilSistemaDAO.excluir(objAgrupadorPerfilSistema);
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
        AgrupadorPerfilSistemaDAO objAgrupadorPerfilSistemaDAO = new AgrupadorPerfilSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objAgrupadorPerfilSistemaDAO.obterTodos();
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

    public List obterSistemasDisponiveis(AgrupadorPerfil objAgrupadorPerfil, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        AgrupadorPerfilSistemaDAO objAgrupadorPerfilSistemaDAO = new AgrupadorPerfilSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objAgrupadorPerfilSistemaDAO.obterSistemasDisponiveis(objAgrupadorPerfil, objUsuarioSistema);
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

    public List obterSistemasSelecionados(AgrupadorPerfil objAgrupadorPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        AgrupadorPerfilSistemaDAO objAgrupadorPerfilSistemaDAO = new AgrupadorPerfilSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objAgrupadorPerfilSistemaDAO.obterSistemasSelecionados(objAgrupadorPerfil);
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

    public void atualizarSistemas(AgrupadorPerfilSistema objAgrupadorPerfilSistema, List lstSistemas) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e inclui registro
        AgrupadorPerfilSistemaDAO objAgrupadorPerfilSistemaDAO = new AgrupadorPerfilSistemaDAO(obterNomeConexao());
        try
        {
            DAO.iniciarTransacao(obterNomeConexao());

            objAgrupadorPerfilSistemaDAO.excluir(objAgrupadorPerfilSistema);

            for (int i = 0; i < lstSistemas.size(); i++)
            {
                Sistema objSistema = (Sistema) lstSistemas.get(i);
                AgrupadorPerfilSistema objAgrupadorPerfilSistemaInclusao = new AgrupadorPerfilSistema();
                objAgrupadorPerfilSistemaInclusao.setAgrupadorPerfil(objAgrupadorPerfilSistema.getAgrupadorPerfil());
                objAgrupadorPerfilSistemaInclusao.setSistema(objSistema);
                incluir(objAgrupadorPerfilSistemaInclusao);
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

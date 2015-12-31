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
import br.gov.camara.negocio.autenticacaoperfil.dao.AgrupadorPerfilGrupoDAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.AgrupadorPerfil;
import br.gov.camara.negocio.autenticacaoperfil.pojo.AgrupadorPerfilGrupo;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Grupo;
import br.gov.camara.negocio.autenticacaoperfil.pojo.PerfilSistema;

/**
 * Facade para atributo de talento
 */
public class AgrupadorPerfilGrupoFacade extends Facade
{
    // Variáveis de instância
    private static Log log = LogFactory.getLog(AgrupadorPerfilGrupoFacade.class);

    /**
     * Construtor Default
     */
    public AgrupadorPerfilGrupoFacade()
    {
        super();
    }

    /**
     * @param nomeConexao
     *
     */
    public AgrupadorPerfilGrupoFacade(String nomeConexao)
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
        AgrupadorPerfilGrupoDAO objAgrupadorPerfilGrupoDAO = new AgrupadorPerfilGrupoDAO(obterNomeConexao());
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objAgrupadorPerfilGrupoDAO.obterTotalRegistros();
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
        AgrupadorPerfilGrupoDAO objAgrupadorPerfilGrupoDAO = new AgrupadorPerfilGrupoDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objAgrupadorPerfilGrupoDAO.obterPorPagina(intNumeroPagina, intMaximoPagina);
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
     * @return AgrupadorPerfilGrupo POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public AgrupadorPerfilGrupo obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        AgrupadorPerfilGrupoDAO objAgrupadorPerfilGrupoDAO = new AgrupadorPerfilGrupoDAO(obterNomeConexao());
        AgrupadorPerfilGrupo objAgrupadorPerfilGrupo = null;
        try
        {
            objAgrupadorPerfilGrupo = (AgrupadorPerfilGrupo) objAgrupadorPerfilGrupoDAO.obterPelaChave(strChave);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return objAgrupadorPerfilGrupo;
    }

    /**
     * Inclui um registro
     *
     * @param objAgrupadorPerfilGrupo POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String incluir(AgrupadorPerfilGrupo objAgrupadorPerfilGrupo) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strChave = null;

        // Instancia DAO e inclui registro
        AgrupadorPerfilGrupoDAO objAgrupadorPerfilGrupoDAO = new AgrupadorPerfilGrupoDAO(obterNomeConexao());
        try
        {
            strChave = objAgrupadorPerfilGrupoDAO.incluir(objAgrupadorPerfilGrupo);
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
     * @param objAgrupadorPerfilGrupo POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void alterar(AgrupadorPerfilGrupo objAgrupadorPerfilGrupo) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro
        AgrupadorPerfilGrupoDAO objAgrupadorPerfilGrupoDAO = new AgrupadorPerfilGrupoDAO(obterNomeConexao());
        try
        {
            objAgrupadorPerfilGrupoDAO.alterar(objAgrupadorPerfilGrupo);
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
     * @param objAgrupadorPerfilGrupo POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void excluir(AgrupadorPerfilGrupo objAgrupadorPerfilGrupo) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e exclui registro
        AgrupadorPerfilGrupoDAO objAgrupadorPerfilGrupoDAO = new AgrupadorPerfilGrupoDAO(obterNomeConexao());
        try
        {
            objAgrupadorPerfilGrupoDAO.excluir(objAgrupadorPerfilGrupo);
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
        AgrupadorPerfilGrupoDAO objAgrupadorPerfilGrupoDAO = new AgrupadorPerfilGrupoDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objAgrupadorPerfilGrupoDAO.obterTodos();
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

    public List obterGruposDisponiveis(AgrupadorPerfil objAgrupadorPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        AgrupadorPerfilGrupoDAO objAgrupadorPerfilGrupoDAO = new AgrupadorPerfilGrupoDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objAgrupadorPerfilGrupoDAO.obterGruposDisponiveis(objAgrupadorPerfil);
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

    public List obterGruposSelecionados(AgrupadorPerfil objAgrupadorPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        AgrupadorPerfilGrupoDAO objAgrupadorPerfilGrupoDAO = new AgrupadorPerfilGrupoDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objAgrupadorPerfilGrupoDAO.obterGruposSelecionados(objAgrupadorPerfil);
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

    public void atualizarGrupos(AgrupadorPerfilGrupo objAgrupadorPerfilGrupo, List lstGrupos) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e inclui registro
        AgrupadorPerfilGrupoDAO objAgrupadorPerfilGrupoDAO = new AgrupadorPerfilGrupoDAO(obterNomeConexao());
        try
        {
            DAO.iniciarTransacao(obterNomeConexao());

            objAgrupadorPerfilGrupoDAO.excluir(objAgrupadorPerfilGrupo);

            for (int i = 0; i < lstGrupos.size(); i++)
            {
                Grupo objGrupo = (Grupo) lstGrupos.get(i);
                AgrupadorPerfilGrupo objAgrupadorPerfilGrupoInclusao = new AgrupadorPerfilGrupo();
                objAgrupadorPerfilGrupoInclusao.setAgrupadorPerfil(objAgrupadorPerfilGrupo.getAgrupadorPerfil());
                objAgrupadorPerfilGrupoInclusao.setGrupo(objGrupo);
                incluir(objAgrupadorPerfilGrupoInclusao);
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

    public List obterPeloGrupoEPerfilSistema(Grupo objGrupo, PerfilSistema objPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        List lstRetorno = null;
        // Instancia DAO e obtém o registro pela chave
        AgrupadorPerfilDAO objAgrupadorPerfilDAO = new AgrupadorPerfilDAO();
        AgrupadorPerfilGrupoDAO objAgrupadorPerfilGrupoDAO = new AgrupadorPerfilGrupoDAO(obterNomeConexao());
        try
        {
            lstRetorno = objAgrupadorPerfilGrupoDAO.obterPeloGrupoEPerfilSistema(objGrupo, objPerfilSistema);
            if (lstRetorno != null && lstRetorno.size() > 0)
            {
                for (int i = 0; i < lstRetorno.size(); i++)
                {
                    AgrupadorPerfilGrupo objAgrupadorPerfilGrupo = (AgrupadorPerfilGrupo) lstRetorno.get(i);
                    objAgrupadorPerfilDAO.inicializarAgrupadorPerfil(objAgrupadorPerfilGrupo.getAgrupadorPerfil());
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

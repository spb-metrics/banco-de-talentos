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
    // Vari�veis de inst�ncia
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
     * Obt�m um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return AgrupadorPerfilGrupo POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public AgrupadorPerfilGrupo obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
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
     * @param objAgrupadorPerfilGrupo POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
     * @param objAgrupadorPerfilGrupo POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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
     * @param objAgrupadorPerfilGrupo POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
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

        // Instancia DAO e obt�m os registros da p�gina
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

        // Instancia DAO e obt�m os registros da p�gina
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
        // Instancia DAO e obt�m o registro pela chave
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

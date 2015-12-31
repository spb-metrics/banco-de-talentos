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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.autenticacaoperfil.dao.AgrupadorPerfilDAO;
import br.gov.camara.negocio.autenticacaoperfil.dao.AgrupadorPerfilUsuarioDAO;
import br.gov.camara.negocio.autenticacaoperfil.dao.UsuarioSistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.dto.ConsultaAgrupadorPerfilUsuario;
import br.gov.camara.negocio.autenticacaoperfil.dto.UsuarioPerfilSistemaDTO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.AgrupadorPerfil;
import br.gov.camara.negocio.autenticacaoperfil.pojo.AgrupadorPerfilUsuario;
import br.gov.camara.negocio.autenticacaoperfil.pojo.PerfilSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.PerfilSistemaAgrupadorPerfil;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;

/**
 * Facade para atributo de talento
 */
public class AgrupadorPerfilUsuarioFacade extends Facade
{
    // Vari�veis de inst�ncia
    private static Log log = LogFactory.getLog(AgrupadorPerfilUsuarioFacade.class);

    /**
     * Construtor Default
     */
    public AgrupadorPerfilUsuarioFacade()
    {
        super();
    }

    /**
     * @param nomeConexao
     *
     */
    public AgrupadorPerfilUsuarioFacade(String nomeConexao)
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
    public int obterTotalRegistros(AgrupadorPerfilUsuario objAgrupadorPerfilUsuario) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        AgrupadorPerfilUsuarioDAO objAgrupadorPerfilUsuarioDAO = new AgrupadorPerfilUsuarioDAO(obterNomeConexao());
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objAgrupadorPerfilUsuarioDAO.obterTotalRegistros(objAgrupadorPerfilUsuario);
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
    public List obterPorPagina(AgrupadorPerfilUsuario objAgrupadorPerfilUsuario, int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        AgrupadorPerfilUsuarioDAO objAgrupadorPerfilUsuarioDAO = new AgrupadorPerfilUsuarioDAO(obterNomeConexao());
        UsuarioSistemaDAO objUsuarioSistemaDAO = new UsuarioSistemaDAO(obterNomeConexao());

        List lstRetorno = null;
        try
        {
            lstRetorno = objAgrupadorPerfilUsuarioDAO.obterPorPagina(objAgrupadorPerfilUsuario, intNumeroPagina, intMaximoPagina);
            if (lstRetorno != null && lstRetorno.size() > 0)
            {
                for (int i = 0; i < lstRetorno.size(); i++)
                {
                    AgrupadorPerfilUsuario objAgrupadorPerfilUsuarioList = (AgrupadorPerfilUsuario) lstRetorno.get(i);
                    objUsuarioSistemaDAO.inicializarUsuarioSistema(objAgrupadorPerfilUsuarioList.getUsuarioSistema());
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
     * Obt�m um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return AgrupadorPerfilUsuario POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public AgrupadorPerfilUsuario obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        AgrupadorPerfilUsuarioDAO objAgrupadorPerfilUsuarioDAO = new AgrupadorPerfilUsuarioDAO(obterNomeConexao());
        UsuarioSistemaDAO objUsuarioSistemaDAO = new UsuarioSistemaDAO();
        AgrupadorPerfilUsuario objAgrupadorPerfilUsuario = null;
        try
        {
            objAgrupadorPerfilUsuario = (AgrupadorPerfilUsuario) objAgrupadorPerfilUsuarioDAO.obterPelaChave(strChave);
            if (objAgrupadorPerfilUsuario != null)
            {
                objUsuarioSistemaDAO.inicializarUsuarioSistema(objAgrupadorPerfilUsuario.getUsuarioSistema());
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
        return objAgrupadorPerfilUsuario;
    }

    /**
     * Inclui um registro
     *
     * @param objAgrupadorPerfilUsuario POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String incluir(AgrupadorPerfilUsuario objAgrupadorPerfilUsuario) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strChave = null;

        List lstPerfis = null;

        // Instacia Facade
        AgrupadorPerfilFacade objAgrupadorPerfilFacade = new AgrupadorPerfilFacade(obterNomeConexao());
        UsuarioCriterioAcessoPerfilFacade objUsuarioCriterioAcessoPerfilFacade = null;

        // Instancia DAO e inclui registro
        AgrupadorPerfilUsuarioDAO objAgrupadorPerfilUsuarioDAO = new AgrupadorPerfilUsuarioDAO(obterNomeConexao());
        try
        {
            if (objAgrupadorPerfilUsuarioDAO.obterPeloUsuarioEAgrupadorPerfil(objAgrupadorPerfilUsuario) != null)
            {
                throw new CDException("Usu�rio j� cadastrado.");
            }

            DAO.iniciarTransacao(obterNomeConexao());

            // Obt�m os perfis associados ao agrupador para realizar inclus�o dos crit�rios de acesso  
            lstPerfis = objAgrupadorPerfilFacade.obterPerfisAssociados(objAgrupadorPerfilUsuario.getAgrupadorPerfil());
            if (lstPerfis != null && lstPerfis.size() > 0)
            {
                objUsuarioCriterioAcessoPerfilFacade = new UsuarioCriterioAcessoPerfilFacade();

                for (int i = 0; i < lstPerfis.size(); i++)
                {
                    PerfilSistemaAgrupadorPerfil objPerfilSistemaAgrupadorPerfil = (PerfilSistemaAgrupadorPerfil) lstPerfis.get(i);
                    objUsuarioCriterioAcessoPerfilFacade.incluir(objPerfilSistemaAgrupadorPerfil.getPerfilSistema(), objAgrupadorPerfilUsuario.getUsuarioSistema());
                }
            }
            strChave = objAgrupadorPerfilUsuarioDAO.incluir(objAgrupadorPerfilUsuario);

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
     * @param objAgrupadorPerfilUsuario POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public void alterar(AgrupadorPerfilUsuario objAgrupadorPerfilUsuario) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro
        AgrupadorPerfilUsuarioDAO objAgrupadorPerfilUsuarioDAO = new AgrupadorPerfilUsuarioDAO(obterNomeConexao());
        try
        {
            objAgrupadorPerfilUsuarioDAO.alterar(objAgrupadorPerfilUsuario);
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
     * @param objAgrupadorPerfilUsuario POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public void excluir(AgrupadorPerfilUsuario objAgrupadorPerfilUsuario) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e exclui registro
        AgrupadorPerfilUsuarioDAO objAgrupadorPerfilUsuarioDAO = new AgrupadorPerfilUsuarioDAO(obterNomeConexao());
        try
        {
            objAgrupadorPerfilUsuarioDAO.excluir(objAgrupadorPerfilUsuario);
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
     * Exclui lista de registros
     *
     * @param lstAgrupadorPerfilUsuario POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public void excluir(List lstAgrupadorPerfilUsuario) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instacia Facade
        AgrupadorPerfilFacade objAgrupadorPerfilFacade = new AgrupadorPerfilFacade(obterNomeConexao());
        UsuarioCriterioAcessoPerfilFacade objUsuarioCriterioAcessoPerfilFacade = null;

        try
        {
            DAO.iniciarTransacao(obterNomeConexao());

            for (int i = 0; i < lstAgrupadorPerfilUsuario.size(); i++)
            {
                AgrupadorPerfilUsuario objAgrupadorPerfilUsuario = (AgrupadorPerfilUsuario) lstAgrupadorPerfilUsuario.get(i);
                objAgrupadorPerfilUsuario = obterPelaChave(objAgrupadorPerfilUsuario.getIdentificador().toString());
                // Obt�m os perfis associados ao agrupador para realizar inclus�o dos crit�rios de acesso  
                List lstPerfis = objAgrupadorPerfilFacade.obterPerfisAssociados(objAgrupadorPerfilUsuario.getAgrupadorPerfil());
                if (lstPerfis != null && lstPerfis.size() > 0)
                {
                    objUsuarioCriterioAcessoPerfilFacade = new UsuarioCriterioAcessoPerfilFacade();

                    for (int j = 0; j < lstPerfis.size(); j++)
                    {
                        PerfilSistemaAgrupadorPerfil objPerfilSistemaAgrupadorPerfil = (PerfilSistemaAgrupadorPerfil) lstPerfis.get(j);
                        objUsuarioCriterioAcessoPerfilFacade.excluir(objPerfilSistemaAgrupadorPerfil.getPerfilSistema(), objAgrupadorPerfilUsuario.getUsuarioSistema());
                    }
                }

                excluir(objAgrupadorPerfilUsuario);
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
        AgrupadorPerfilUsuarioDAO objAgrupadorPerfilUsuarioDAO = new AgrupadorPerfilUsuarioDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objAgrupadorPerfilUsuarioDAO.obterTodos();
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

    public AgrupadorPerfilUsuario obterPeloUsuarioEAgrupadorPerfil(AgrupadorPerfilUsuario objAgrupadorPerfilUsuario) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        AgrupadorPerfilUsuarioDAO objAgrupadorPerfilUsuarioDAO = new AgrupadorPerfilUsuarioDAO(obterNomeConexao());
        UsuarioSistemaDAO objUsuarioSistemaDAO = new UsuarioSistemaDAO();
        try
        {
            objAgrupadorPerfilUsuario = (AgrupadorPerfilUsuario) objAgrupadorPerfilUsuarioDAO.obterPeloUsuarioEAgrupadorPerfil(objAgrupadorPerfilUsuario);
            if (objAgrupadorPerfilUsuario != null)
            {
                objUsuarioSistemaDAO.inicializarUsuarioSistema(objAgrupadorPerfilUsuario.getUsuarioSistema());
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
        return objAgrupadorPerfilUsuario;
    }

    public List obterPeloUsuarioEPerfilSistema(UsuarioSistema objUsuarioSistema, PerfilSistema objPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        List lstRetorno = null;
        // Instancia DAO e obt�m o registro pela chave
        AgrupadorPerfilDAO objAgrupadorPerfilDAO = new AgrupadorPerfilDAO();
        AgrupadorPerfilUsuarioDAO objAgrupadorPerfilUsuarioDAO = new AgrupadorPerfilUsuarioDAO(obterNomeConexao());
        try
        {
            lstRetorno = objAgrupadorPerfilUsuarioDAO.obterPeloUsuarioEPerfilSistema(objUsuarioSistema, objPerfilSistema);
            if (lstRetorno != null && lstRetorno.size() > 0)
            {
                for (int i = 0; i < lstRetorno.size(); i++)
                {
                    AgrupadorPerfilUsuario objAgrupadorPerfilUsuario = (AgrupadorPerfilUsuario) lstRetorno.get(i);
                    objAgrupadorPerfilDAO.inicializarAgrupadorPerfil(objAgrupadorPerfilUsuario.getAgrupadorPerfil());
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

    public List obterPeloPerfil(PerfilSistema objPerfilSistema) throws CDException
    {

        List retorno = null;

        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        AgrupadorPerfilUsuarioDAO objAgrupadorPerfilUsuarioDAO = new AgrupadorPerfilUsuarioDAO(obterNomeConexao());
        try
        {
            retorno = objAgrupadorPerfilUsuarioDAO.obterPeloPerfil(objPerfilSistema);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return retorno;
    }

    public List obterPeloAgrupador(AgrupadorPerfil objAgrupadorPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        List lstRetorno = null;

        // Instancia DAO e obt�m o registro pela chave
        AgrupadorPerfilUsuarioDAO objAgrupadorPerfilUsuarioDAO = new AgrupadorPerfilUsuarioDAO(obterNomeConexao());
        UsuarioSistemaDAO objUsuarioSistemaDAO = new UsuarioSistemaDAO();
        try
        {
            lstRetorno = objAgrupadorPerfilUsuarioDAO.obterPeloAgrupador(objAgrupadorPerfil);
            if (lstRetorno != null && lstRetorno.size() > 0)
            {
                for (int i = 0; i < lstRetorno.size(); i++)
                {
                    AgrupadorPerfilUsuario objAgrupadorPerfilUsuarioList = (AgrupadorPerfilUsuario) lstRetorno.get(i);
                    objUsuarioSistemaDAO.inicializarUsuarioSistema(objAgrupadorPerfilUsuarioList.getUsuarioSistema());
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

    public Object obterUsuariosPerfil(PerfilSistema objPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        PerfilSistemaAgrupadorPerfilFacade objPerfilSistemaAgrupadorPerfilFacade = new PerfilSistemaAgrupadorPerfilFacade();

        List lstRetorno = new ArrayList();
        List lstAgrupadores = null;
        UsuarioSistemaDAO objUsuarioSistemaDAO = new UsuarioSistemaDAO();
        try
        {
            lstAgrupadores = objPerfilSistemaAgrupadorPerfilFacade.obterPeloPerfil(objPerfilSistema);
            if (lstAgrupadores != null && lstAgrupadores.size() > 0)
            {
                for (int i = 0; i < lstAgrupadores.size(); i++)
                {
                    PerfilSistemaAgrupadorPerfil objPerfilSistemaAgrupadorPerfil = (PerfilSistemaAgrupadorPerfil) lstAgrupadores.get(i);
                    ConsultaAgrupadorPerfilUsuario objConsultaAgrupadorPerfilUsuario = new ConsultaAgrupadorPerfilUsuario();
                    objConsultaAgrupadorPerfilUsuario.setAgrupadorPerfil(objPerfilSistemaAgrupadorPerfil.getAgrupadorPerfil());
                    List lstUsuarios = null;
                    List lstAgrupadorPerfilUsuario = obterPeloAgrupador(objPerfilSistemaAgrupadorPerfil.getAgrupadorPerfil());
                    if (lstAgrupadorPerfilUsuario != null)
                    {
                        lstUsuarios = new ArrayList();
                        for (int j = 0; j < lstAgrupadorPerfilUsuario.size(); j++)
                        {
                            AgrupadorPerfilUsuario objAgrupadorPerfilUsuario = (AgrupadorPerfilUsuario) lstAgrupadorPerfilUsuario.get(j);
                            UsuarioPerfilSistemaDTO objUsuarioPerfilSistemaDTO = new UsuarioPerfilSistemaDTO();
                            objUsuarioPerfilSistemaDTO.setUsuarioSistema(objAgrupadorPerfilUsuario.getUsuarioSistema());
                            if (!objUsuarioSistemaDAO.validarCriteriosAcesso(objPerfilSistema, objAgrupadorPerfilUsuario.getUsuarioSistema()))
                            {
                                objUsuarioPerfilSistemaDTO.setCriterioAcessoOk(false);
                            }
                            lstUsuarios.add(objUsuarioPerfilSistemaDTO);
                        }
                    }
                    objConsultaAgrupadorPerfilUsuario.setUsuarios(lstUsuarios);
                    lstRetorno.add(objConsultaAgrupadorPerfilUsuario);
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

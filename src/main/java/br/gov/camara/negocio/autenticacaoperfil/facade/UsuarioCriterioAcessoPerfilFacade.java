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

import br.gov.camara.biblioteca.util.ClasseDinamica;
import br.gov.camara.biblioteca.util.Copia;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.autenticacaoperfil.dao.PerfilSistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.dao.UsuarioCriterioAcessoPerfilDAO;
import br.gov.camara.negocio.autenticacaoperfil.dto.CriterioUsuarioPerfilSistemaDTO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.AgrupadorPerfilUsuario;
import br.gov.camara.negocio.autenticacaoperfil.pojo.CriterioAcesso;
import br.gov.camara.negocio.autenticacaoperfil.pojo.CriterioAcessoPerfil;
import br.gov.camara.negocio.autenticacaoperfil.pojo.PerfilSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioCriterioAcessoPerfil;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioPerfilSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;

/**
 * Facade para atributo de talento
 */
public class UsuarioCriterioAcessoPerfilFacade extends Facade
{
    // Vari�veis de inst�ncia
    private static Log log = LogFactory.getLog(UsuarioCriterioAcessoPerfilFacade.class);

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
        UsuarioCriterioAcessoPerfilDAO objUsuarioCriterioAcessoPerfilDAO = new UsuarioCriterioAcessoPerfilDAO(obterNomeConexao());
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objUsuarioCriterioAcessoPerfilDAO.obterTotalRegistros();
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
        UsuarioCriterioAcessoPerfilDAO objUsuarioCriterioAcessoPerfilDAO = new UsuarioCriterioAcessoPerfilDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objUsuarioCriterioAcessoPerfilDAO.obterPorPagina(intNumeroPagina, intMaximoPagina);
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
     * @return UsuarioCriterioAcessoPerfil POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     * 
     */
    public UsuarioCriterioAcessoPerfil obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m o registro pela chave
        UsuarioCriterioAcessoPerfilDAO objUsuarioCriterioAcessoPerfilDAO = new UsuarioCriterioAcessoPerfilDAO(obterNomeConexao());
        UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil = null;
        try
        {
            objUsuarioCriterioAcessoPerfil = (UsuarioCriterioAcessoPerfil) objUsuarioCriterioAcessoPerfilDAO.obterPelaChave(strChave);
            //objUsuarioCriterioAcessoPerfilDAO.inicializarPerfisSistema(objUsuarioCriterioAcessoPerfil);

        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return objUsuarioCriterioAcessoPerfil;
    }

    /**
     * Inclui um registro
     *
     * @param objUsuarioCriterioAcessoPerfil POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String incluir(UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strChave = null;

        // Instancia DAO e inclui registro
        UsuarioCriterioAcessoPerfilDAO objUsuarioCriterioAcessoPerfilDAO = new UsuarioCriterioAcessoPerfilDAO(obterNomeConexao());
        try
        {
            strChave = objUsuarioCriterioAcessoPerfilDAO.incluir(objUsuarioCriterioAcessoPerfil);
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
     * Inclui um registro
     *
     * @param objUsuarioCriterioAcessoPerfil POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public void incluir(CriterioAcessoPerfil objCriterioAcessoPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        UsuarioPerfilSistema objUsuarioPerfilSistema = new UsuarioPerfilSistema();
        AgrupadorPerfilUsuario objAgrupadorPerfilUsuario = new AgrupadorPerfilUsuario();
        UsuarioPerfilSistemaFacade objUsuarioPerfilSistemaFacade = new UsuarioPerfilSistemaFacade();
        AgrupadorPerfilUsuarioFacade objAgrupadorPerfilUsuarioFacade = new AgrupadorPerfilUsuarioFacade();
        List lstUsuarioPerfilSistema = null;
        List lstAgrupadorPerfilUsuario = null;
        try
        {

            DAO.iniciarTransacao();

            // Obt�m a lista de usu�rios associados ao perfil
            objUsuarioPerfilSistema.setPerfilSistema(objCriterioAcessoPerfil.getPerfilSistema());
            lstUsuarioPerfilSistema = objUsuarioPerfilSistemaFacade.obterPeloPerfilSistema(objUsuarioPerfilSistema);
            if (lstUsuarioPerfilSistema != null && lstUsuarioPerfilSistema.size() > 0)
            {
                // Inclui os crit�rios de acesso dos usu�rios
                for (int i = 0; i < lstUsuarioPerfilSistema.size(); i++)
                {
                    // Obt�m usu�rio do perfil
                    objUsuarioPerfilSistema = (UsuarioPerfilSistema) lstUsuarioPerfilSistema.get(i);

                    Object objValorCriterio = obterValorCriterio(objCriterioAcessoPerfil, objUsuarioPerfilSistema.getUsuarioSistema().getIdentificador());
                    if (objValorCriterio != null)
                    {
                        UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil = new UsuarioCriterioAcessoPerfil();
                        objUsuarioCriterioAcessoPerfil.setCriterioAcessoPerfil(objCriterioAcessoPerfil);
                        objUsuarioCriterioAcessoPerfil.setUsuarioSistema(objUsuarioPerfilSistema.getUsuarioSistema());
                        List lstUsuarioCriterioAcesso = obterPeloUsuarioCriterioAcesso(objUsuarioCriterioAcessoPerfil);
                        if (lstUsuarioCriterioAcesso == null || lstUsuarioCriterioAcesso.size() <= 0)
                        {
                            objUsuarioCriterioAcessoPerfil.setValoracaoCriterioAcesso(objValorCriterio.toString());
                            incluir(objUsuarioCriterioAcessoPerfil);
                        }
                    }
                }
            }

            // Abt�m a lista de usu�rios associados ao agrupador que possui o perfil
            lstAgrupadorPerfilUsuario = objAgrupadorPerfilUsuarioFacade.obterPeloPerfil(objCriterioAcessoPerfil.getPerfilSistema());
            if (lstAgrupadorPerfilUsuario != null && lstAgrupadorPerfilUsuario.size() > 0)
            {
                // Inclui os crit�rios de acesso dos usu�rios
                for (int i = 0; i < lstAgrupadorPerfilUsuario.size(); i++)
                {
                    // Obt�m usu�rio do perfil
                    objAgrupadorPerfilUsuario = (AgrupadorPerfilUsuario) lstAgrupadorPerfilUsuario.get(i);

                    Object objValorCriterio = obterValorCriterio(objCriterioAcessoPerfil, objAgrupadorPerfilUsuario.getUsuarioSistema().getIdentificador());
                    if (objValorCriterio != null)
                    {
                        UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil = new UsuarioCriterioAcessoPerfil();
                        objUsuarioCriterioAcessoPerfil.setCriterioAcessoPerfil(objCriterioAcessoPerfil);
                        objUsuarioCriterioAcessoPerfil.setUsuarioSistema(objAgrupadorPerfilUsuario.getUsuarioSistema());
                        List lstUsuarioCriterioAcesso = obterPeloUsuarioCriterioAcesso(objUsuarioCriterioAcessoPerfil);
                        if (lstUsuarioCriterioAcesso == null || lstUsuarioCriterioAcesso.size() <= 0)
                        {
                            objUsuarioCriterioAcessoPerfil.setValoracaoCriterioAcesso(objValorCriterio.toString());
                            incluir(objUsuarioCriterioAcessoPerfil);
                        }
                    }
                }
            }

            DAO.realizarTransacao();
        }
        catch (Exception daoe)
        {
            DAO.desfazerTransacao();
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
    }

    /**
     * Inicializa os crit�rios de acesso
     *
     * @param objUsuarioCriterioAcessoPerfil POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public void inicializarCriterios(UsuarioPerfilSistema objUsuarioPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        CriterioAcessoPerfil objCriterioAcessoPerfil = new CriterioAcessoPerfil();
        UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil = new UsuarioCriterioAcessoPerfil();
        UsuarioCriterioAcessoPerfilDAO objUsuarioCriterioAcessoPerfilDAO = new UsuarioCriterioAcessoPerfilDAO();

        List lstUsuarioCriterioAcessoPerfil = null;
        try
        {
            DAO.iniciarTransacao();

            objCriterioAcessoPerfil.setPerfilSistema(objUsuarioPerfilSistema.getPerfilSistema());
            objUsuarioCriterioAcessoPerfil.setCriterioAcessoPerfil(objCriterioAcessoPerfil);
            objUsuarioCriterioAcessoPerfil.setUsuarioSistema(objUsuarioPerfilSistema.getUsuarioSistema());
            lstUsuarioCriterioAcessoPerfil = objUsuarioCriterioAcessoPerfilDAO.obterPeloPerfilUsuarioSistema(objUsuarioCriterioAcessoPerfil);

            if (lstUsuarioCriterioAcessoPerfil != null)
            {
                for (int i = 0; i < lstUsuarioCriterioAcessoPerfil.size(); i++)
                {
                    objUsuarioCriterioAcessoPerfil = (UsuarioCriterioAcessoPerfil) lstUsuarioCriterioAcessoPerfil.get(i);

                    Object objValorCriterio = obterValorCriterio(objUsuarioCriterioAcessoPerfil.getCriterioAcessoPerfil(), objUsuarioCriterioAcessoPerfil.getUsuarioSistema().getIdentificador());

                    if (objValorCriterio != null)
                    {
                        objUsuarioCriterioAcessoPerfil.setValoracaoCriterioAcesso(objValorCriterio.toString());
                        alterar(objUsuarioCriterioAcessoPerfil);
                    }
                }
            }
            DAO.realizarTransacao();
        }
        catch (Exception daoe)
        {
            DAO.desfazerTransacao();
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
    }

    /**
     * Inclui um registro
     *
     * @param objUsuarioCriterioAcessoPerfil POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public void incluir(PerfilSistema objPerfilSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        CriterioAcessoPerfil objCriterioAcessoPerfil = new CriterioAcessoPerfil();
        CriterioAcessoPerfilFacade objCriterioAcessoPerfilFacade = new CriterioAcessoPerfilFacade();

        List lstCriterioAcessoPerfil = null;
        try
        {
            DAO.iniciarTransacao();

            objCriterioAcessoPerfil.setPerfilSistema(objPerfilSistema);
            lstCriterioAcessoPerfil = objCriterioAcessoPerfilFacade.obterPeloPerfilSistema(objCriterioAcessoPerfil);

            if (lstCriterioAcessoPerfil != null)
            {
                for (int i = 0; i < lstCriterioAcessoPerfil.size(); i++)
                {
                    objCriterioAcessoPerfil = (CriterioAcessoPerfil) lstCriterioAcessoPerfil.get(i);

                    Object objValorCriterio = obterValorCriterio(objCriterioAcessoPerfil, objUsuarioSistema.getIdentificador());

                    if (objValorCriterio != null)
                    {
                        UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil = new UsuarioCriterioAcessoPerfil();
                        objUsuarioCriterioAcessoPerfil.setCriterioAcessoPerfil(objCriterioAcessoPerfil);
                        objUsuarioCriterioAcessoPerfil.setUsuarioSistema(objUsuarioSistema);
                        List lstUsuarioCriterioAcesso = obterPeloUsuarioCriterioAcesso(objUsuarioCriterioAcessoPerfil);
                        if (lstUsuarioCriterioAcesso == null || lstUsuarioCriterioAcesso.size() <= 0)
                        {
                            objUsuarioCriterioAcessoPerfil.setValoracaoCriterioAcesso(objValorCriterio.toString());
                            incluir(objUsuarioCriterioAcessoPerfil);
                        }
                    }
                }
            }
            DAO.realizarTransacao();
        }
        catch (Exception daoe)
        {
            DAO.desfazerTransacao();
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
    }

    private Object obterValorCriterio(CriterioAcessoPerfil objCriterioAcessoPerfil, Integer intIdentificador) throws CDException
    {
        Object objValorCriterio = null;

        try
        {
            if (objCriterioAcessoPerfil.getCriterioAcesso() != null)
            {
                // Obt�m o crit�rio de acesso
                if (objCriterioAcessoPerfil.getCriterioAcesso().getClasseImplementadora() == null)
                {
                    CriterioAcessoFacade objCriterioAcessoFacade = new CriterioAcessoFacade();
                    CriterioAcesso objCriterioAcesso = objCriterioAcessoFacade.obterPelaChave(objCriterioAcessoPerfil.getCriterioAcesso());
                    objCriterioAcessoPerfil.setCriterioAcesso(objCriterioAcesso);
                }
                // Obt�m classe implementadora do crit�rio de acesso
                ClasseDinamica clsDinamica = new ClasseDinamica();

                br.gov.camara.seguranca.criterioacesso.CriterioAcesso objCriterio = (br.gov.camara.seguranca.criterioacesso.CriterioAcesso) clsDinamica.obterInstancia(objCriterioAcessoPerfil.getCriterioAcesso().getClasseImplementadora());

                if (objCriterio instanceof br.gov.camara.seguranca.criterioacesso.CriterioAcesso)
                {
                    objValorCriterio = objCriterio.getValorCriterioAcesso(intIdentificador);
                }
                else
                {
                    throw new CDException("Classe implementadora '"
                            + objCriterioAcessoPerfil.getCriterioAcesso().getClasseImplementadora()
                            + "' n�o implementa a classe ICriterioAcesso");
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

        return objValorCriterio;
    }

    /**
     * Altera um registro
     *
     * @param objUsuarioCriterioAcessoPerfil POJO representando o objeto a ser inclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public void alterar(UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro
        UsuarioCriterioAcessoPerfilDAO objUsuarioCriterioAcessoPerfilDAO = new UsuarioCriterioAcessoPerfilDAO(obterNomeConexao());
        try
        {
            objUsuarioCriterioAcessoPerfilDAO.alterar(objUsuarioCriterioAcessoPerfil);
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
     * Exclui os registros vinculados ao crit�rio de acesso passado como parametro
     *
     * @param objCriterioAcessoPerfil POJO representando o objeto a ser exclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public void excluir(CriterioAcessoPerfil objCriterioAcessoPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e exclui registro
        UsuarioCriterioAcessoPerfilDAO objUsuarioCriterioAcessoPerfilDAO = new UsuarioCriterioAcessoPerfilDAO(obterNomeConexao());
        UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil = new UsuarioCriterioAcessoPerfil();
        try
        {
            objUsuarioCriterioAcessoPerfil.setCriterioAcessoPerfil(objCriterioAcessoPerfil);
            objUsuarioCriterioAcessoPerfilDAO.excluir(objUsuarioCriterioAcessoPerfil);
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
     * Exclui os registros vinculados ao usu�rio passado como parametro
     *
     * @param objUsuarioPerfilSistema POJO representando o objeto a ser exclu�do
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public void excluir(PerfilSistema objPerfilSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e exclui registro
        UsuarioCriterioAcessoPerfilDAO objUsuarioCriterioAcessoPerfilDAO = new UsuarioCriterioAcessoPerfilDAO(obterNomeConexao());
        UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil = new UsuarioCriterioAcessoPerfil();
        CriterioAcessoPerfil objCriterioAcessoPerfil = new CriterioAcessoPerfil();
        CriterioAcessoPerfilFacade objCriterioAcessoPerfilFacade = new CriterioAcessoPerfilFacade();

        try
        {
            if (podeExcluir(objPerfilSistema, objUsuarioSistema))
            {
                objCriterioAcessoPerfil.setPerfilSistema(objPerfilSistema);
                List lstCriterioAcessoPerfil = objCriterioAcessoPerfilFacade.obterPeloPerfilSistema(objCriterioAcessoPerfil);
                if (lstCriterioAcessoPerfil != null)
                {
                    for (int i = 0; i < lstCriterioAcessoPerfil.size(); i++)
                    {
                        objCriterioAcessoPerfil = (CriterioAcessoPerfil) lstCriterioAcessoPerfil.get(i);
                        objUsuarioCriterioAcessoPerfil.setCriterioAcessoPerfil(objCriterioAcessoPerfil);
                        objUsuarioCriterioAcessoPerfil.setUsuarioSistema(objUsuarioSistema);

                        objUsuarioCriterioAcessoPerfilDAO.excluir(objUsuarioCriterioAcessoPerfil);
                    }
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
    }

    /**
     * Verifica se a exclus�o do crit�rio de acesso � necess�rio, pois caso exista usu�rio associado ao perfil 
     * pelo agrupado e pelo perfil de sistema o usu�rio tem que permanecer com a valora��o do crit�rio.
     *
     * @param objUsuarioSistema POJO contendo os dados do usu�rio
     * @param objPerfilSistema POJO contendo os dados do perfil
     *
     * @throws CDException se ocorrer algum erro relacionado ao neg�cio
     *
     */
    public boolean podeExcluir(PerfilSistema objPerfilSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        boolean blnRetorno = false;

        UsuarioPerfilSistemaFacade objUsuarioPerfilSistemaFacade = new UsuarioPerfilSistemaFacade(obterNomeConexao());
        AgrupadorPerfilUsuarioFacade objAgrupadorPerfilUsuarioFacade = new AgrupadorPerfilUsuarioFacade(obterNomeConexao());
        UsuarioPerfilSistema objUsuarioPerfilSistema = new UsuarioPerfilSistema();
        try
        {
            // Verifica se tem algum usu�rio associado ao perfil
            objUsuarioPerfilSistema.setUsuarioSistema(objUsuarioSistema);
            objUsuarioPerfilSistema.setPerfilSistema(objPerfilSistema);

            if (objUsuarioPerfilSistemaFacade.obterPelaChave(objUsuarioPerfilSistema) == null)
            {
                blnRetorno = true;
            }
            // Verifica se algum agrupador possui usu�rio associado com o perfil
            else
            {
                List lstAgrupadorPerfilUsuario = objAgrupadorPerfilUsuarioFacade.obterPeloUsuarioEPerfilSistema(objUsuarioSistema, objPerfilSistema);
                if (lstAgrupadorPerfilUsuario == null || lstAgrupadorPerfilUsuario.size() > 0)
                {
                    blnRetorno = true;
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

        return blnRetorno;
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
        UsuarioCriterioAcessoPerfilDAO objUsuarioCriterioAcessoPerfilDAO = new UsuarioCriterioAcessoPerfilDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objUsuarioCriterioAcessoPerfilDAO.obterTodos();
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

    public List obterPeloPerfilUsuarioSistema(UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        UsuarioCriterioAcessoPerfilDAO objUsuarioCriterioAcessoPerfilDAO = new UsuarioCriterioAcessoPerfilDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objUsuarioCriterioAcessoPerfilDAO.obterPeloPerfilUsuarioSistema(objUsuarioCriterioAcessoPerfil);
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

    public List obterCriteriosUsuario(UsuarioSistema objUsuarioSistema, PerfilSistema objPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        UsuarioCriterioAcessoPerfilDAO objUsuarioCriterioAcessoPerfilDAO = new UsuarioCriterioAcessoPerfilDAO(obterNomeConexao());
        CriterioAcessoPerfil objCriterioAcessoPerfil = new CriterioAcessoPerfil();
        UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil = new UsuarioCriterioAcessoPerfil();
        List lstUsuarioCriterioAcessoPerfil = null;
        List lstRetorno = new ArrayList();
        try
        {
            objCriterioAcessoPerfil.setPerfilSistema(objPerfilSistema);
            objUsuarioCriterioAcessoPerfil.setCriterioAcessoPerfil(objCriterioAcessoPerfil);
            objUsuarioCriterioAcessoPerfil.setUsuarioSistema(objUsuarioSistema);
            lstUsuarioCriterioAcessoPerfil = objUsuarioCriterioAcessoPerfilDAO.obterPeloPerfilUsuarioSistema(objUsuarioCriterioAcessoPerfil);

            // Verifica os crit�rios do usu�rio
            if (lstUsuarioCriterioAcessoPerfil != null && lstUsuarioCriterioAcessoPerfil.size() > 0)
            {
                for (int i = 0; i < lstUsuarioCriterioAcessoPerfil.size(); i++)
                {
                    objUsuarioCriterioAcessoPerfil = (UsuarioCriterioAcessoPerfil) lstUsuarioCriterioAcessoPerfil.get(i);
                    CriterioUsuarioPerfilSistemaDTO objCriterioUsuarioPerfilSistemaDTO = new CriterioUsuarioPerfilSistemaDTO();
                    Copia.criar(objUsuarioCriterioAcessoPerfil, objCriterioUsuarioPerfilSistemaDTO);

                    Object objValorCriterioAtual = obterValorCriterio(objUsuarioCriterioAcessoPerfil.getCriterioAcessoPerfil(), objUsuarioCriterioAcessoPerfil.getUsuarioSistema().getIdentificador());

                    if (objValorCriterioAtual != null)
                    {
                        objCriterioUsuarioPerfilSistemaDTO.setValorCriterioAtual(objValorCriterioAtual.toString());

                        if (objCriterioUsuarioPerfilSistemaDTO.getCriterioAcessoPerfil().getValorCriterioAcessoPerfil() != null)
                        {
                            objCriterioUsuarioPerfilSistemaDTO.setValoracaoCriterioAcesso(null);

                            if (objCriterioUsuarioPerfilSistemaDTO.getCriterioAcessoPerfil().getValorCriterioAcessoPerfil().equals(objValorCriterioAtual.toString()))
                            {
                                objCriterioUsuarioPerfilSistemaDTO.setPossuiAcesso(true);
                            }
                        }
                        else
                        {
                            if (objCriterioUsuarioPerfilSistemaDTO.getValoracaoCriterioAcesso().equals(objValorCriterioAtual.toString()))
                            {
                                objCriterioUsuarioPerfilSistemaDTO.setPossuiAcesso(true);
                            }
                        }
                    }
                    lstRetorno.add(objCriterioUsuarioPerfilSistemaDTO);
                }
            }
            // Verifica os crit�rios do grupo, apenas crit�rios valorados
            else if (objUsuarioSistema.getGrupo() != null)
            {
                PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO();
                if (objPerfilSistemaDAO.grupoUsuarioPossuiPerfil(objPerfilSistema, objUsuarioSistema))
                {
                    CriterioAcessoPerfilFacade objCriterioAcessoPerfilFacade = new CriterioAcessoPerfilFacade();
                    List lstCriterioAcesso = objCriterioAcessoPerfilFacade.obterPeloPerfilSistema(objCriterioAcessoPerfil);

                    if (lstCriterioAcesso != null && lstCriterioAcesso.size() > 0)
                    {
                        for (int i = 0; i < lstCriterioAcesso.size(); i++)
                        {
                            objCriterioAcessoPerfil = (CriterioAcessoPerfil) lstCriterioAcesso.get(i);
                            if (objCriterioAcessoPerfil.getValorCriterioAcessoPerfil() != null
                                    && !"".equals(objCriterioAcessoPerfil.getValorCriterioAcessoPerfil()))
                            {
                                CriterioUsuarioPerfilSistemaDTO objCriterioUsuarioPerfilSistemaDTO = new CriterioUsuarioPerfilSistemaDTO();
                                objCriterioUsuarioPerfilSistemaDTO.setCriterioAcessoPerfil(objCriterioAcessoPerfil);
                                objCriterioUsuarioPerfilSistemaDTO.setUsuarioSistema(objUsuarioSistema);
                                Object objValorCriterioAtual = obterValorCriterio(objCriterioAcessoPerfil, objUsuarioSistema.getIdentificador());

                                if (objValorCriterioAtual != null)
                                {
                                    objCriterioUsuarioPerfilSistemaDTO.setValorCriterioAtual(objValorCriterioAtual.toString());

                                    objCriterioUsuarioPerfilSistemaDTO.setValoracaoCriterioAcesso(null);

                                    if (objCriterioUsuarioPerfilSistemaDTO.getCriterioAcessoPerfil().getValorCriterioAcessoPerfil().equals(objValorCriterioAtual.toString()))
                                    {
                                        objCriterioUsuarioPerfilSistemaDTO.setPossuiAcesso(true);
                                    }
                                }
                                lstRetorno.add(objCriterioUsuarioPerfilSistemaDTO);
                            }
                        }
                    }
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

    public List obterPeloUsuarioCriterioAcesso(UsuarioCriterioAcessoPerfil objUsuarioCriterioAcessoPerfil) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obt�m os registros da p�gina
        UsuarioCriterioAcessoPerfilDAO objUsuarioCriterioAcessoPerfilDAO = new UsuarioCriterioAcessoPerfilDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objUsuarioCriterioAcessoPerfilDAO.obterPeloUsuarioCriterioAcesso(objUsuarioCriterioAcessoPerfil);
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

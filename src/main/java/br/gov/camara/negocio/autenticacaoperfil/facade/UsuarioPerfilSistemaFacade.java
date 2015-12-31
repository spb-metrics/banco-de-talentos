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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.biblioteca.util.Copia;
import br.gov.camara.biblioteca.util.DataNova;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.autenticacaoperfil.dao.PerfilSistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.dao.UsuarioPerfilSistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.dao.UsuarioSistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.dto.UsuarioPerfilSistemaDTO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Grupo;
import br.gov.camara.negocio.autenticacaoperfil.pojo.PerfilSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioPerfilSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;
import br.gov.camara.seguranca.UsuarioAutenticado;

/**
 * Facade para atributo de talento
 */
public class UsuarioPerfilSistemaFacade extends Facade
{

    /**
     * Construtor Default
     */
    public UsuarioPerfilSistemaFacade()
    {
        super();
    }

    /**
     * @param nomeConexao
     *
     */
    public UsuarioPerfilSistemaFacade(String nomeConexao)
    {
        super(nomeConexao);
    }

    // Variáveis de instância
    private static Log log = LogFactory.getLog(UsuarioPerfilSistemaFacade.class);

    /**
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return UsuarioPerfilSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public UsuarioPerfilSistema obterPelaChave(UsuarioPerfilSistema objUsuarioPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        UsuarioPerfilSistemaDAO objUsuarioPerfilSistemaDAO = new UsuarioPerfilSistemaDAO(obterNomeConexao());
        UsuarioPerfilSistema objRetorno = null;
        try
        {
            objRetorno = (UsuarioPerfilSistema) objUsuarioPerfilSistemaDAO.obterPelaChave(objUsuarioPerfilSistema);
            if (objRetorno != null)
            {
                objUsuarioPerfilSistemaDAO.inicializarPerfilSistema(objRetorno);
                objUsuarioPerfilSistemaDAO.inicializarUsuarioSistema(objRetorno);
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
     * @param objUsuarioPerfilSistema POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String incluir(UsuarioPerfilSistema objUsuarioPerfilSistema, UsuarioAutenticado usuarioAutenticado, String strObjetoControlado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strChave = null;

        // Instancia DAO e inclui registro
        UsuarioPerfilSistemaDAO objUsuarioPerfilSistemaDAO = new UsuarioPerfilSistemaDAO(obterNomeConexao());
        GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade(obterNomeConexao());
        UsuarioCriterioAcessoPerfilFacade objUsuarioCriterioAcessoPerfilFacade = new UsuarioCriterioAcessoPerfilFacade();
        try
        {
            UsuarioSistema objUsuarioSistema = new UsuarioSistema();
            objUsuarioSistema.setIdentificador(usuarioAutenticado.obterIdentificador());
            objUsuarioPerfilSistema.setUsuarioPermissaoAcesso(usuarioAutenticado.obterIdentificador());

            if ((!objGestorSistemaFacade.isGestorSistema(objUsuarioPerfilSistema.getPerfilSistema().getSistema(), objUsuarioSistema))
                    && (!isGestorPerfil(objUsuarioPerfilSistema.getPerfilSistema(), objUsuarioSistema)))
            {
                throw new CDException("Somente gestores deste perfil podem incluir um usuário");
            }
            else if (objUsuarioPerfilSistema.getDataInicioValidade() != null
                    && objUsuarioPerfilSistema.getDataTerminoValidade() != null
                    && objUsuarioPerfilSistema.getDataInicioValidade().after(objUsuarioPerfilSistema.getDataTerminoValidade()))
            {
                throw new CDException("Data de início de validade deve ser anterior à data de término");
            }
            else if (objUsuarioPerfilSistema.getDataInicioValidade() != null
                    && objUsuarioPerfilSistema.getDataInicioValidade().before(DataNova.formatarCalendar(DataNova.obterDataAtual())))
            {
                throw new CDException("Data de início de validade não pode ser anterior à data de hoje");
            }

            DAO.iniciarTransacao(obterNomeConexao());

            objUsuarioPerfilSistemaDAO.incluir(objUsuarioPerfilSistema);

            objUsuarioCriterioAcessoPerfilFacade.incluir(objUsuarioPerfilSistema.getPerfilSistema(), objUsuarioPerfilSistema.getUsuarioSistema());

            if ("S".equals(objUsuarioPerfilSistema.getIndicativoGestor()))
            {
                realizarInclusaoPerfil(objUsuarioPerfilSistema.getUsuarioSistema(), strObjetoControlado);
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
     * Inicializa os critérios de acesso
     *
     * @param objUsuarioPerfilSistema POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String inicializarCriterios(UsuarioPerfilSistema objUsuarioPerfilSistema, UsuarioAutenticado usuarioAutenticado, String strObjetoControlado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strChave = null;

        // Instancia DAO 
        GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade(obterNomeConexao());
        UsuarioCriterioAcessoPerfilFacade objUsuarioCriterioAcessoPerfilFacade = new UsuarioCriterioAcessoPerfilFacade();
        try
        {
            UsuarioSistema objUsuarioSistema = new UsuarioSistema();
            objUsuarioSistema.setIdentificador(usuarioAutenticado.obterIdentificador());
            objUsuarioPerfilSistema.setUsuarioPermissaoAcesso(usuarioAutenticado.obterIdentificador());

            if ((!objGestorSistemaFacade.isGestorSistema(objUsuarioPerfilSistema.getPerfilSistema().getSistema(), objUsuarioSistema))
                    && (!isGestorPerfil(objUsuarioPerfilSistema.getPerfilSistema(), objUsuarioSistema)))
            {
                throw new CDException("Somente gestores deste perfil podem incluir um usuário");
            }

            DAO.iniciarTransacao(obterNomeConexao());

            objUsuarioCriterioAcessoPerfilFacade.inicializarCriterios(objUsuarioPerfilSistema);

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
     * @param objUsuarioPerfilSistema POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void alterar(UsuarioPerfilSistema objUsuarioPerfilSistema, UsuarioSistema objUsuarioSistema, String strObjetoControlado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro
        GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade(obterNomeConexao());
        UsuarioPerfilSistemaDAO objUsuarioPerfilSistemaDAO = new UsuarioPerfilSistemaDAO(obterNomeConexao());
        try
        {
            if ((!objGestorSistemaFacade.isGestorSistema(objUsuarioPerfilSistema.getPerfilSistema().getSistema(), objUsuarioSistema))
                    && (!isGestorPerfil(objUsuarioPerfilSistema.getPerfilSistema(), objUsuarioSistema)))
            {
                throw new CDException("Somente gestores deste perfil podem alterar um usuário");
            }
            else if (objUsuarioPerfilSistema.getDataInicioValidade() != null
                    && objUsuarioPerfilSistema.getDataTerminoValidade() != null
                    && objUsuarioPerfilSistema.getDataInicioValidade().after(objUsuarioPerfilSistema.getDataTerminoValidade()))
            {
                throw new CDException("Data de início de validade deve ser anterior à data de término");
            }
            else if (objUsuarioPerfilSistema.getDataInicioValidade() != null)
            {
                // Seta usuário perfil sisteam form para setar posteriormente 
                UsuarioPerfilSistema objUsuarioPerfilSistemaForm = objUsuarioPerfilSistema;

                // Obtém o objet
                UsuarioPerfilSistema objUsuarioPerfilSistemaAnterior = (UsuarioPerfilSistema) objUsuarioPerfilSistemaDAO.obterPelaChave(objUsuarioPerfilSistema);
                if (objUsuarioPerfilSistemaAnterior.getDataInicioValidade() != null
                        && !(objUsuarioPerfilSistema.getDataInicioValidade().getTime().compareTo(objUsuarioPerfilSistemaAnterior.getDataInicioValidade().getTime()) == 0)
                        && objUsuarioPerfilSistema.getDataInicioValidade().before(DataNova.formatarCalendar(DataNova.obterDataAtual())))
                {
                    throw new CDException("Data de início de validade não pode ser anterior à data de hoje");
                }
                objUsuarioPerfilSistema = objUsuarioPerfilSistemaAnterior;
                objUsuarioPerfilSistema.setDataInicioValidade(objUsuarioPerfilSistemaForm.getDataInicioValidade());
                objUsuarioPerfilSistema.setDataTerminoValidade(objUsuarioPerfilSistemaForm.getDataTerminoValidade());
                objUsuarioPerfilSistema.setIndicativoGestor(objUsuarioPerfilSistemaForm.getIndicativoGestor());
                objUsuarioPerfilSistema.setUsuarioPermissaoAcesso(objUsuarioPerfilSistemaForm.getUsuarioPermissaoAcesso());

            }

            if (!objGestorSistemaFacade.isGestorSistema(objUsuarioPerfilSistema.getPerfilSistema().getSistema(), objUsuarioSistema)
                    && isGestorPerfil(objUsuarioPerfilSistema.getPerfilSistema(), objUsuarioSistema)
                    && (objUsuarioSistema.getIdentificador().intValue() != objUsuarioPerfilSistema.getUsuarioPermissaoAcesso().intValue()))
            {
                throw new CDException("Não é possível alterar usuário que foi incluído por outro gestor");
            }

            DAO.iniciarTransacao(obterNomeConexao());

            objUsuarioPerfilSistemaDAO.alterar(objUsuarioPerfilSistema);

            if ("S".equals(objUsuarioPerfilSistema.getIndicativoGestor()))
            {
                realizarInclusaoPerfil(objUsuarioPerfilSistema.getUsuarioSistema(), strObjetoControlado);
            }
            else if (!objUsuarioPerfilSistemaDAO.isGestorDeAlgumPerfil(objUsuarioPerfilSistema.getUsuarioSistema())
                    && !objGestorSistemaFacade.isGestorDeAlgumSistema(objUsuarioPerfilSistema.getUsuarioSistema()))
            {
                realizarExclusaoPerfil(objUsuarioPerfilSistema.getUsuarioSistema(), strObjetoControlado);
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

    public void realizarInclusaoPerfil(UsuarioSistema objUsuarioSistema, String strObjetoControlado) throws CDException
    {
        UsuarioPerfilSistemaDAO objUsuarioPerfilSistemaDAO = new UsuarioPerfilSistemaDAO(obterNomeConexao());
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());

        try
        {
            PerfilSistema objPerfilSistema = objPerfilSistemaDAO.obterPeloObjetoControladoDaFuncionalidade(strObjetoControlado);
            if (objPerfilSistema != null)
            {
                UsuarioPerfilSistema objUsuarioPerfil = new UsuarioPerfilSistema();
                objUsuarioPerfil.setUsuarioSistema(objUsuarioSistema);
                objUsuarioPerfil.setPerfilSistema(objPerfilSistema);
                objUsuarioPerfil.setIndicativoGestor("N");
                if (objUsuarioPerfilSistemaDAO.obterPelaChave(objUsuarioPerfil) == null)
                {
                    objUsuarioPerfilSistemaDAO.incluir(objUsuarioPerfil);
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
     * Exclui um registro
     *
     * @param objUsuarioPerfilSistema POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void excluir(UsuarioPerfilSistema objUsuarioPerfilSistema, UsuarioSistema objUsuarioSistema, String strObjetoControlado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e exclui registro
        UsuarioPerfilSistemaDAO objUsuarioPerfilSistemaDAO = new UsuarioPerfilSistemaDAO(obterNomeConexao());
        UsuarioCriterioAcessoPerfilFacade objUsuarioCriterioAcessoPerfilFacade = new UsuarioCriterioAcessoPerfilFacade();

        GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade(obterNomeConexao());
        try
        {
            if ((!objGestorSistemaFacade.isGestorSistema(objUsuarioPerfilSistema.getPerfilSistema().getSistema(), objUsuarioSistema))
                    && (!isGestorPerfil(objUsuarioPerfilSistema.getPerfilSistema(), objUsuarioSistema)))
            {
                throw new CDException("Somente gestores deste perfil podem excluir um usuário");
            }
            else if (!objGestorSistemaFacade.isGestorSistema(objUsuarioPerfilSistema.getPerfilSistema().getSistema(), objUsuarioSistema)
                    && isGestorPerfil(objUsuarioPerfilSistema.getPerfilSistema(), objUsuarioSistema)
                    && (objUsuarioPerfilSistema.getUsuarioPermissaoAcesso() == null || objUsuarioSistema.getIdentificador().intValue() != objUsuarioPerfilSistema.getUsuarioPermissaoAcesso().intValue()))
            {
                throw new CDException("Não é possível excluir usuário que foi incluído por outra pessoa");
            }

            DAO.iniciarTransacao(obterNomeConexao());

            objUsuarioCriterioAcessoPerfilFacade.excluir(objUsuarioPerfilSistema.getPerfilSistema(), objUsuarioPerfilSistema.getUsuarioSistema());

            if (!objUsuarioPerfilSistemaDAO.isGestorDeAlgumPerfil(objUsuarioPerfilSistema.getUsuarioSistema())
                    && !objGestorSistemaFacade.isGestorDeAlgumSistema(objUsuarioPerfilSistema.getUsuarioSistema()))
            {
                realizarExclusaoPerfil(objUsuarioPerfilSistema.getUsuarioSistema(), strObjetoControlado);
            }

            objUsuarioPerfilSistemaDAO.excluir(objUsuarioPerfilSistema);

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
     * Exclui um registro
     *
     * @param objUsuarioPerfilSistema POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void excluir(List lstUsuarioPerfilSistema, UsuarioSistema objUsuarioSistema, String strObjetoControlado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        try
        {
            DAO.iniciarTransacao(obterNomeConexao());

            for (int i = 0; i < lstUsuarioPerfilSistema.size(); i++)
            {
                UsuarioPerfilSistema objUsuarioPerfilSistema = (UsuarioPerfilSistema) lstUsuarioPerfilSistema.get(i);
                excluir(objUsuarioPerfilSistema, objUsuarioSistema, strObjetoControlado);
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

    public void realizarExclusaoPerfil(UsuarioSistema objUsuarioSistema, String strObjetoControlado) throws CDException
    {
        UsuarioPerfilSistemaDAO objUsuarioPerfilSistemaDAO = new UsuarioPerfilSistemaDAO(obterNomeConexao());
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());

        try
        {
            PerfilSistema objPerfilSistema = objPerfilSistemaDAO.obterPeloObjetoControladoDaFuncionalidade(strObjetoControlado);
            if (objPerfilSistema != null)
            {
                UsuarioPerfilSistema objUsuarioPerfil = new UsuarioPerfilSistema();
                objUsuarioPerfil.setUsuarioSistema(objUsuarioSistema);
                objUsuarioPerfil.setPerfilSistema(objPerfilSistema);
                objUsuarioPerfilSistemaDAO.excluir(objUsuarioPerfil);
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
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return UsuarioPerfilSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPeloPerfilSistema(UsuarioPerfilSistema objUsuarioPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        UsuarioPerfilSistemaDAO objUsuarioPerfilSistemaDAO = new UsuarioPerfilSistemaDAO(obterNomeConexao());
        List lstUsuarioPerfilSistema = null;
        List lstRetorno = new ArrayList();
        try
        {
            lstUsuarioPerfilSistema = objUsuarioPerfilSistemaDAO.obterPeloPerfilSistema(objUsuarioPerfilSistema);
            objUsuarioPerfilSistemaDAO.inicializarPerfilSistema(lstUsuarioPerfilSistema);
            objUsuarioPerfilSistemaDAO.inicializarUsuarioSistema(lstUsuarioPerfilSistema);

            for (int i = 0; i < lstUsuarioPerfilSistema.size(); i++)
            {
                UsuarioPerfilSistema objUsuarioPerfilSistemaList = (UsuarioPerfilSistema) lstUsuarioPerfilSistema.get(i);

                UsuarioPerfilSistemaDTO objUsuarioPerfilSistemaDTO = new UsuarioPerfilSistemaDTO();

                objUsuarioPerfilSistemaDTO = obterDTO(objUsuarioPerfilSistemaList);

                lstRetorno.add(objUsuarioPerfilSistemaDTO);
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
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return UsuarioPerfilSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPeloUsuarioSistema(UsuarioPerfilSistema objUsuarioPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        UsuarioPerfilSistemaDAO objUsuarioPerfilSistemaDAO = new UsuarioPerfilSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objUsuarioPerfilSistemaDAO.obterPeloUsuarioSistema(objUsuarioPerfilSistema);
            objUsuarioPerfilSistemaDAO.inicializarPerfilSistema(lstRetorno);
            objUsuarioPerfilSistemaDAO.inicializarUsuarioSistema(lstRetorno);

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
     * @return UsuarioPerfilSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterGestoresPerfil() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        UsuarioPerfilSistemaDAO objUsuarioPerfilSistemaDAO = new UsuarioPerfilSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objUsuarioPerfilSistemaDAO.obterGestoresPerfil();
            objUsuarioPerfilSistemaDAO.inicializarPerfilSistema(lstRetorno);
            objUsuarioPerfilSistemaDAO.inicializarUsuarioSistema(lstRetorno);

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
     * @return UsuarioPerfilSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPeloObjetoControlado(String strObjControlado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        UsuarioPerfilSistemaDAO objUsuarioPerfilSistemaDAO = new UsuarioPerfilSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objUsuarioPerfilSistemaDAO.obterPeloObjetoControlado(strObjControlado);
            objUsuarioPerfilSistemaDAO.inicializarPerfilSistema(lstRetorno);
            objUsuarioPerfilSistemaDAO.inicializarUsuarioSistema(lstRetorno);

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
     * Verifica se o usuário está cadastrado como gestor
     *
     * @param objPerfilSistema POJO representando o perfil do sistema a ser verificado
     * @param objUsuarioSistema POJO representando o usuário logado
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public boolean isGestorPerfil(PerfilSistema objPerfilSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        boolean blnRetorno = false;
        UsuarioPerfilSistemaDAO objUsuarioPerfilSistemaDAO = new UsuarioPerfilSistemaDAO(obterNomeConexao());
        UsuarioPerfilSistema objUsuarioPerfilSistema = new UsuarioPerfilSistema();
        objUsuarioPerfilSistema.setPerfilSistema(objPerfilSistema);
        objUsuarioPerfilSistema.setUsuarioSistema(objUsuarioSistema);
        try
        {
            if (objUsuarioPerfilSistemaDAO.isGestorPerfil(objUsuarioPerfilSistema))
            {
                blnRetorno = true;
            }
            else
            {
                PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
                objUsuarioPerfilSistema.setPerfilSistema((PerfilSistema) objPerfilSistemaDAO.obterPelaChave(objPerfilSistema));
                if (objUsuarioPerfilSistema.getPerfilSistema().getPerfilAgrupador() != null)
                {
                    blnRetorno = isGestorPerfil(objUsuarioPerfilSistema.getPerfilSistema().getPerfilAgrupador(), objUsuarioSistema);
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
     * Verifica se o usuário está cadastrado como gestor
     *
     * @param objPerfilSistema POJO representando o perfil do sistema a ser verificado
     * @param objUsuarioSistema POJO representando o usuário logado
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public boolean isGestorDeAlgumPerfil(UsuarioSistema objUsuarioSistema) throws CDException
    {
        boolean blnRetorno = false;
        UsuarioPerfilSistemaDAO objUsuarioPerfilSistemaDAO = new UsuarioPerfilSistemaDAO(obterNomeConexao());
        try
        {
            blnRetorno = objUsuarioPerfilSistemaDAO.isGestorDeAlgumPerfil(objUsuarioSistema);
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

    public UsuarioPerfilSistemaDTO obterDTO(UsuarioPerfilSistema objUsuarioPerfilSistema) throws CDException
    {
        UsuarioSistemaDAO objUsuarioSistemaDAO = new UsuarioSistemaDAO();

        UsuarioPerfilSistemaDTO objUsuarioPerfilSistemaDTO = new UsuarioPerfilSistemaDTO();

        try
        {
            Copia.criar(objUsuarioPerfilSistema, objUsuarioPerfilSistemaDTO);

            if (objUsuarioPerfilSistema.getDataInicioValidade() != null)
            {
                objUsuarioPerfilSistemaDTO.setDataInicio(DataNova.format(objUsuarioPerfilSistema.getDataInicioValidade()));
                if (objUsuarioPerfilSistema.getDataInicioValidade().after(DataNova.formatarCalendar(DataNova.obterDataAtual())))
                {
                    objUsuarioPerfilSistemaDTO.setDataInicioOk(false);
                }
            }

            if (objUsuarioPerfilSistema.getDataTerminoValidade() != null)
            {
                objUsuarioPerfilSistemaDTO.setDataTermino(DataNova.format(objUsuarioPerfilSistema.getDataTerminoValidade()));
                Date dtFimValidade = DataNova.formatarDate(DataNova.format(objUsuarioPerfilSistema.getDataTerminoValidade().getTime(), DataNova.FORMAT_DDMMYYYY));
                if (dtFimValidade.compareTo(DataNova.formatarDate(DataNova.obterDataAtual())) < 0)
                {
                    objUsuarioPerfilSistemaDTO.setDataTerminoOk(false);
                }
            }

            if (!objUsuarioSistemaDAO.validarCriteriosAcesso(objUsuarioPerfilSistema.getPerfilSistema(), objUsuarioPerfilSistema.getUsuarioSistema()))
            {
                objUsuarioPerfilSistemaDTO.setCriterioAcessoOk(false);
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

        return objUsuarioPerfilSistemaDTO;

    }

    public boolean usuarioPossuiPermissao(UsuarioPerfilSistema objUsuarioPerfilSistema) throws CDException
    {
        UsuarioSistemaDAO objUsuarioSistemaDAO = new UsuarioSistemaDAO();

        boolean blnRetorno = true;

        try
        {
            objUsuarioPerfilSistema = obterPelaChave(objUsuarioPerfilSistema);
            if (objUsuarioPerfilSistema != null)
            {
                if (objUsuarioPerfilSistema.getDataInicioValidade() != null)
                {
                    if (objUsuarioPerfilSistema.getDataInicioValidade().after(DataNova.formatarCalendar(DataNova.obterDataAtual())))
                    {
                        blnRetorno = false;
                    }
                }
                if (objUsuarioPerfilSistema.getDataTerminoValidade() != null && blnRetorno)
                {
                    Date dtFimValidade = DataNova.formatarDate(DataNova.format(objUsuarioPerfilSistema.getDataTerminoValidade().getTime(), DataNova.FORMAT_DDMMYYYY));
                    if (dtFimValidade.compareTo(DataNova.formatarDate(DataNova.obterDataAtual())) < 0)
                    {
                        blnRetorno = false;
                    }
                }
                if (!objUsuarioSistemaDAO.validarCriteriosAcesso(objUsuarioPerfilSistema.getPerfilSistema(), objUsuarioPerfilSistema.getUsuarioSistema())
                        && blnRetorno)
                {
                    if (!objUsuarioSistemaDAO.validarCriteriosAcessoGrupo(objUsuarioPerfilSistema.getPerfilSistema(), objUsuarioPerfilSistema.getUsuarioSistema().getIdentificador().toString()))
                    {
                        blnRetorno = false;
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

        return blnRetorno;

    }

    public boolean possuiGrupo(PerfilSistema objPerfilSistema, UsuarioSistema objUsuarioSistema)
    {
        boolean blnRetorno = false;
        if (objPerfilSistema.getGrupos() != null && objPerfilSistema.getGrupos().size() > 0 && objUsuarioSistema.getGrupo() != null)
        {
            Iterator itrGrupos = objPerfilSistema.getGrupos().iterator();
            while (itrGrupos.hasNext() && !blnRetorno)
            {
                Grupo objGrupo = (Grupo) itrGrupos.next();
                if (objGrupo.getCodigo().intValue() == objUsuarioSistema.getGrupo().getCodigo().intValue())
                {
                    blnRetorno = true;
                }
            }
        }
        return blnRetorno;

    }
}

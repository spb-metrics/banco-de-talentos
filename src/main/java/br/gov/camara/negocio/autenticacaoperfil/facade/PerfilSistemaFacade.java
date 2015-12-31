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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sigesp.comum.util.biblioteca.OrdenadorBean;
import br.gov.camara.biblioteca.util.Copia;
import br.gov.camara.biblioteca.util.DataNova;
import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.autenticacaoperfil.dao.FuncionalidadeSistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.dao.PerfilSistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.dao.UsuarioPerfilSistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.dto.ConsultaPermissoes;
import br.gov.camara.negocio.autenticacaoperfil.pojo.AgrupadorPerfilGrupo;
import br.gov.camara.negocio.autenticacaoperfil.pojo.AgrupadorPerfilUsuario;
import br.gov.camara.negocio.autenticacaoperfil.pojo.CriterioAcessoPerfil;
import br.gov.camara.negocio.autenticacaoperfil.pojo.FuncionalidadeSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.GestorSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Grupo;
import br.gov.camara.negocio.autenticacaoperfil.pojo.PerfilSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.PerfilSistemaAgrupadorPerfil;
import br.gov.camara.negocio.autenticacaoperfil.pojo.PerfilSistemaVisualizacao;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Sistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioPerfilSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;
import br.gov.camara.negocio.exception.DAOException;

/**
 * Facade para atributo de talento
 */
public class PerfilSistemaFacade extends Facade
{
    // Variáveis de instância
    private static Log log = LogFactory.getLog(PerfilSistemaFacade.class);

    /**
     * Construtor Default
     */
    public PerfilSistemaFacade()
    {
        super();
    }

    /**
     * @param nomeConexao
     *
     */
    public PerfilSistemaFacade(String nomeConexao)
    {
        super(nomeConexao);
    }

    /**
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return PerfilSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public PerfilSistema obterPelaChave(PerfilSistema objPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
        PerfilSistema objRetorno = null;
        try
        {
            objRetorno = (PerfilSistema) objPerfilSistemaDAO.obterPelaChave(objPerfilSistema);
            if (objRetorno != null)
            {
                objPerfilSistemaDAO.inicializarFuncionalidades(objRetorno);
                objPerfilSistemaDAO.inicializarPerfilAgrupador(objRetorno);
                objPerfilSistemaDAO.inicializarGrupos(objRetorno);
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
     * @param objPerfilSistema POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String incluir(PerfilSistema objPerfilSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strChave = null;

        // Instancia DAO e inclui registro
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
        try
        {
            realizarValidacoesAtualizacao(objPerfilSistema, objUsuarioSistema);

            PerfilSistema objPerf = objPerfilSistemaDAO.obterPeloObjetoControlado(objPerfilSistema.getObjetoControlado());

            if (objPerf != null)
            {
                throw new CDException("Já existe um perfil com o Objeto controlado informado");
            }
            strChave = objPerfilSistemaDAO.incluir(objPerfilSistema);
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
     * @param objPerfilSistema POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void alterar(PerfilSistema objPerfilSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
        try
        {
            realizarValidacoesAtualizacao(objPerfilSistema, objUsuarioSistema);

            PerfilSistema objPerfilSistemaExistente = objPerfilSistemaDAO.obterObjetoControladoCadastrado(objPerfilSistema);
            if (objPerfilSistemaExistente != null)
            {
                throw new CDException("Já existe um perfil com o objeto controlado '"
                        + objPerfilSistemaExistente.getObjetoControlado()
                        + "' cadastrado no sistema '"
                        + objPerfilSistemaExistente.getSistema().getNome()
                        + "'");
            }
            objPerfilSistemaDAO.alterar(objPerfilSistema);
        }
        catch (Exception e)
        {
            CDException.dispararExcecao(e);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
    }

    public void realizarValidacoesAtualizacao(PerfilSistema objPerfilSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade(obterNomeConexao());

        if (!objGestorSistemaFacade.isGestorSistema(objPerfilSistema.getSistema(), objUsuarioSistema))
        {
            throw new CDException("Somente gestores deste sistema podem incluir uma Funcionalidade");
        }
        if (objPerfilSistema.getPerfilAgrupador() != null)
        {
            objPerfilSistema.setPerfilAgrupador(obterPelaChave(objPerfilSistema.getPerfilAgrupador()));
            if (possuiUsuariosAssociados(objPerfilSistema.getPerfilAgrupador()))
            {
                throw new CDException("O perfil agrupador possui usuários associados e não pode ter sub-perfis");
            }
            else if (objPerfilSistema.getPerfilAgrupador().getGrupos() != null && objPerfilSistema.getPerfilAgrupador().getGrupos().size() > 0)
            {
                throw new CDException("O perfil agrupador possui grupos associados e não pode ter sub-perfis");
            }
            else if (objPerfilSistema.getPerfilAgrupador().getFuncionalidades() != null
                    && objPerfilSistema.getPerfilAgrupador().getFuncionalidades().size() > 0)
            {
                throw new CDException("O perfil agrupador possui funcionalidades associadas e não pode ter sub-perfis");
            }
        }

    }

    /**
     * Exclui um registro
     *
     * @param objPerfilSistema POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void excluir(PerfilSistema objPerfilSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e exclui registro
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
        GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade(obterNomeConexao());
        try
        {
            if (!objGestorSistemaFacade.isGestorSistema(objPerfilSistema.getSistema(), objUsuarioSistema))
            {
                throw new CDException("Somente gestores deste sistema podem excluir uma Funcionalidade");
            }
            if (objPerfilSistema.getGrupos() != null && objPerfilSistema.getGrupos().size() > 0)
            {
                throw new CDException("O perfil possui grupos associados e não pode ser excluído");
            }
            if (objPerfilSistema.getFuncionalidades() != null && objPerfilSistema.getFuncionalidades().size() > 0)
            {
                throw new CDException("O perfil possui funcionalidades associadas e não pode ser excluído");
            }
            objPerfilSistemaDAO.excluir(objPerfilSistema);
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
     * Inclui um registro
     *
     * @param objPerfilSistema POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     * @return retorna a chave do objeto que foi incluido
     *
     */
    public String atualizarGrupos(PerfilSistema objPerfilSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strChave = null;

        GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade();

        // Instancia DAO e inclui registro
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
        try
        {
            UsuarioPerfilSistemaFacade objUsuarioPerfilSistemaFacade = new UsuarioPerfilSistemaFacade();
            if ((!objGestorSistemaFacade.isGestorSistema(objPerfilSistema.getSistema(), objUsuarioSistema))
                    && (!objUsuarioPerfilSistemaFacade.isGestorPerfil(objPerfilSistema, objUsuarioSistema)))
            {
                throw new CDException("Somente gestores deste perfil podem atualizar grupos");
            }

            objPerfilSistemaDAO.alterar(objPerfilSistema);
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
     * Obtém um registro a partir do sistema e através do perfil agrupador 
     *
     * @param objPerfilSistema contendo os parâmetros de pesquisa
     * 
     * @return List contendo o resultado obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPeloSistemaPerfilAgrupador(PerfilSistema objPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objPerfilSistemaDAO.obterPeloSistemaPerfilAgrupador(objPerfilSistema);
            lstRetorno = obterVisualizacao(lstRetorno);
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
     * Obtém um registro a partir do sistema agrupador
     *
     * @param intIdentificador identificador do perfil agrupador
     * 
     * @return List contendo o resultado obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPeloPerfilAgrupador(Integer intIdentificador) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objPerfilSistemaDAO.obterPeloPerfilAgrupador(intIdentificador);
            lstRetorno = obterVisualizacao(lstRetorno);
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
     * Obtém um registro a partir do perfil agrupador 
     *
     * @param objPerfilSistema contendo os parâmetros de pesquisa
     * 
     * @return List contendo o resultado obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPeloPerfilAgrupador(PerfilSistema objPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objPerfilSistemaDAO.obterPeloPerfilAgrupador(objPerfilSistema);
            lstRetorno = obterVisualizacao(lstRetorno);
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
     * Obtém um registro a partir do sistema e através do perfil agrupador igual a null
     *
     * @param objPerfilSistema contendo os parâmetros de pesquisa
     * 
     * @return List contendo o resultado obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPeloSistemaAgrupadorNull(PerfilSistema objPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objPerfilSistemaDAO.obterPeloSistemaAgrupadorNull(objPerfilSistema);
            lstRetorno = obterVisualizacao(lstRetorno);
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
     * Obtém hierarquia de acordo com parâmetros informados
     *
     * @param objPerfilSistema contendo os parâmetros de pesquisa
     * 
     * @return List contendo o resultado obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterHierarquia(PerfilSistema objPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objPerfilSistemaDAO.obterHierarquia(objPerfilSistema);
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
     * Obtém os registros de um determinado sistema e caso o nome do objeto controlado ou o nome do perfil 
     * estejam preenchidos o filtro destes campo é realizado
     *
     * @param objPerfilSistema contendo os parâmetros de pesquisa
     * 
     * @return List contendo o resultado obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPelaChaveNomeEOuObjetoControladoEOuNomePerfil(PerfilSistema objPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objPerfilSistemaDAO.obterPelaChaveNomeEOuObjetoControladoEOuNomePerfil(objPerfilSistema);
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

    private List obterVisualizacao(List lstPerfilSistema)
    {
        List lstRetorno = new ArrayList();
        for (int i = 0; i < lstPerfilSistema.size(); i++)
        {
            PerfilSistema objPerfil = (PerfilSistema) lstPerfilSistema.get(i);
            PerfilSistemaVisualizacao objPerfilSistemaVisualizacao = new PerfilSistemaVisualizacao();
            Copia.criar(objPerfil, objPerfilSistemaVisualizacao);
            objPerfilSistemaVisualizacao.setRetorno(objPerfil.getIdentificador().intValue() + ";" + objPerfil.getDescricao());
            lstRetorno.add(objPerfilSistemaVisualizacao);
        }
        return lstRetorno;
    }

    public void validarInclusaoFuncionalidadePerfil(FuncionalidadeSistema objFuncionalidadeSistema, PerfilSistema objPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        FuncionalidadeSistemaDAO objFuncionalidadeSistemaDAO = new FuncionalidadeSistemaDAO(obterNomeConexao());
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
        try
        {
            if (!objFuncionalidadeSistemaDAO.possuiFilhos(objFuncionalidadeSistema))
            {
                throw new CDException("Somente funcionalidades agrupadoras podem ser associadas aos perfis");
            }
            if (objFuncionalidadeSistemaDAO.possuiNetos(objFuncionalidadeSistema))
            {
                throw new CDException("Somente funcionalidades agrupadoras de último nível pode ser associadas aos perfis");
            }
            if (objPerfilSistemaDAO.possuiFilhos(objPerfilSistema))
            {
                throw new CDException("Não é permitido associar uma funcionalidade de sistema a um perfil de sistema que agrupa outros perfis!");
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

    public List obterGruposDisponiveis(PerfilSistema objPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());

        List lstRetorno = null;
        try
        {
            lstRetorno = objPerfilSistemaDAO.obterGruposDisponiveis(objPerfilSistema);
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
     * @return PerfilSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPeloSistemaEOuUsuarioEOuGrupo(Sistema objSistema, UsuarioSistema objUsuarioSistema, Grupo objGrupo) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objPerfilSistemaDAO.obterPeloSistemaEOuUsuarioEOuGrupo(objSistema, objUsuarioSistema, objGrupo);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return obterVisualizacao(lstRetorno);
    }

    /**
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return PerfilSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPeloSistemaUsuarioEhGestor(Sistema objSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objPerfilSistemaDAO.obterPeloSistemaUsuarioEhGestor(objSistema, objUsuarioSistema);
            objPerfilSistemaDAO.inicializarGrupos(lstRetorno);
            objPerfilSistemaDAO.inicializarFuncionalidades(lstRetorno);
            return lstRetorno = obterVisualizacao(lstRetorno);
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
     * @return PerfilSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPeloSistemaUsuarioEhGestor(PerfilSistema objPerfilSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objPerfilSistemaDAO.obterPeloSistemaUsuarioEhGestor(objPerfilSistema, objUsuarioSistema);
            objPerfilSistemaDAO.inicializarGrupos(lstRetorno);
            objPerfilSistemaDAO.inicializarFuncionalidades(lstRetorno);
            lstRetorno = obterVisualizacao(lstRetorno);
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
     * @return PerfilSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPerfisPeloSistemaQueSaoComunsEntreUsuarioComAcessoEUsuarioEhGestor(PerfilSistema objPerfilSistema, UsuarioSistema objUsuario, UsuarioSistema objUsuarioLogado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objPerfilSistemaDAO.obterPerfisPeloSistemaQueSaoComunsEntreUsuarioComAcessoEUsuarioEhGestor(objPerfilSistema, objUsuario, objUsuarioLogado);
            if (lstRetorno != null && lstRetorno.size() > 0)
            {
                objPerfilSistemaDAO.inicializarGrupos(lstRetorno);
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
     * @return PerfilSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPeloSistemaGrupoUsuarioEhGestor(PerfilSistema objPerfilSistema, Grupo objGrupo, UsuarioSistema objUsuarioLogado) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objPerfilSistemaDAO.obterPeloSistemaGrupoUsuarioEhGestor(objPerfilSistema, objGrupo, objUsuarioLogado);
            objPerfilSistemaDAO.inicializarGrupos(lstRetorno);
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
     * @return PerfilSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public void realizarCopia(UsuarioSistema objUsuarioLogado, String ideUsuarioOrigem, boolean excluirExistentes, String[] ideUsuarioDestino, Sistema objSistema, String strDatInicioValidade, String strDatTerminoValidade) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        UsuarioPerfilSistemaDAO objUsuarioPerfilSistemaDAO = new UsuarioPerfilSistemaDAO(obterNomeConexao());
        try
        {
            if (!"".equals(strDatInicioValidade) && !"".equals(strDatTerminoValidade) && DataNova.isMaiorDoQue(strDatInicioValidade, strDatTerminoValidade))
            {
                throw new CDException("Data de início de validade deve ser anterior à data de término");
            }
            DAO.iniciarTransacao(obterNomeConexao());
            for (int i = 0; i < ideUsuarioDestino.length; i++)
            {
                if (excluirExistentes)
                {
                    objUsuarioPerfilSistemaDAO.excluirPerfil(objUsuarioLogado.getIdentificador(), new Integer(ideUsuarioDestino[i]), objSistema.getIdentificador());
                }
                objUsuarioPerfilSistemaDAO.realizarCopia(objUsuarioLogado.getIdentificador(), new Integer(ideUsuarioOrigem), new Integer(ideUsuarioDestino[i]), objSistema.getIdentificador(), strDatInicioValidade, strDatTerminoValidade);
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

    public boolean possuiUsuariosAssociados(PerfilSistema objPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        UsuarioPerfilSistemaDAO objUsuarioPerfilSistemaDAO = new UsuarioPerfilSistemaDAO(obterNomeConexao());
        UsuarioPerfilSistema objUsuarioPerfilSistema = new UsuarioPerfilSistema();

        List lstRetorno = null;
        try
        {
            objUsuarioPerfilSistema.setPerfilSistema(objPerfilSistema);
            lstRetorno = objUsuarioPerfilSistemaDAO.obterPeloPerfilSistema(objUsuarioPerfilSistema);

        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }
        return (lstRetorno != null && lstRetorno.size() > 0);
    }

    public boolean possuiFilhos(PerfilSistema objPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
        boolean retorno = false;

        try
        {
            retorno = objPerfilSistemaDAO.possuiFilhos(objPerfilSistema);
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

    public List obterPermissoesPorSistema(UsuarioSistema objUsuarioSistema, String strIdeSistema, boolean blnExport) throws CDException
    {
        List lstRetorno = new ArrayList();

        // Facades
        SistemaFacade objSistemaFacade = new SistemaFacade(obterNomeConexao());
        GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade(obterNomeConexao());

        // Pojos
        Sistema objSistema = new Sistema();
        GestorSistema objGestorSistema = new GestorSistema();
        PerfilSistema objPerfilSistema = null;
        try
        {

            // Obtém o sistema desejado
            objSistema.setIdentificador(new Integer(strIdeSistema));
            objSistema = objSistemaFacade.obterPelaChave(objSistema);
            // Adiciona sistema à lista
            lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloSistema'>Sistema: " + objSistema.getDescricao() + "</span>"), true, null));

            // Obtém os Gestores do sistema
            objGestorSistema.setSistema(objSistema);
            List lstGestores = objGestorSistemaFacade.obterPeloSistema(objGestorSistema);
            if (lstGestores != null)
            {
                lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloGestores'>" + realizarIdentacao(1, blnExport) + "Gestores:</span>"), true, null));
                for (int i = 0; i < lstGestores.size(); i++)
                {
                    objGestorSistema = (GestorSistema) lstGestores.get(i);
                    if (objGestorSistema.getIndicativoAdministrador().equals("S"))
                    {
                        lstRetorno.add(adicionarObjetoPermissao(("<span class='usuarioAdministrador'>"
                                + realizarIdentacao(2, blnExport)
                                + objGestorSistema.getUsuarioSistema().getNome() + "</span><span class='usuarioAdministrador.detalhe'> [Administrador]</span>"), true, null));
                    }
                    else
                    {
                        lstRetorno.add(adicionarObjetoPermissao(("<span class='usuarioGestorSistema'>"
                                + realizarIdentacao(2, blnExport)
                                + objGestorSistema.getUsuarioSistema().getNome() + "</span><span class='usuarioGestorSistema.detalhe'> [Gestor Sistema]</span>"), true, null));
                    }
                }
            }

            // Obtém os perfis do sistema
            objPerfilSistema = new PerfilSistema();
            objPerfilSistema.setSistema(objSistema);
            lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloPerfis'>" + realizarIdentacao(1, blnExport) + "Perfis:" + "</span>"), true, null));
            lstRetorno.addAll(obterPerfis(objPerfilSistema, objUsuarioSistema, 2, "", blnExport));

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

    private List obterPerfis(PerfilSistema objPerfilSistema, UsuarioSistema objUsuarioSistema, int intIdentacao, String strContadorPerfil, boolean blnExport) throws CDException
    {
        List lstRetorno = new ArrayList();

        // Facades
        UsuarioPerfilSistemaFacade objUsuarioPerfilSistemaFacade = new UsuarioPerfilSistemaFacade(obterNomeConexao());

        // Pojos
        UsuarioPerfilSistema objUsuarioPerfilSistema = new UsuarioPerfilSistema();
        Grupo objGrupo = null;
        FuncionalidadeSistema objFuncionalidadeSistema = null;
        try
        {
            List lstPerfis = obterPeloSistemaUsuarioEhGestor(objPerfilSistema, objUsuarioSistema);
            if (lstPerfis != null && lstPerfis.size() > 0)
            {
                for (int i = 0; i < lstPerfis.size(); i++)
                {
                    // Inclui nome do perfil do sistema
                    objPerfilSistema = (PerfilSistema) lstPerfis.get(i);

                    // Verifica se é agrupador e chama método de recursividade para obter filhos
                    if (possuiFilhos(objPerfilSistema))
                    {
                        lstRetorno.add(adicionarObjetoPermissao(("<span class='perfilSistema'>"
                                + realizarIdentacao(intIdentacao, blnExport)
                                + strContadorPerfil
                                + (i + 1)
                                + " - "
                                + objPerfilSistema.getNome() + "</span><span class='perfilSistema.detalhe'> [Agrupador]</span>"), true, null));
                        PerfilSistema objPerf = new PerfilSistema();
                        objPerf.setSistema(objPerfilSistema.getSistema());
                        objPerf.setPerfilAgrupador(objPerfilSistema);
                        lstRetorno.addAll(obterPerfis(objPerf, objUsuarioSistema, intIdentacao + 1, (i + 1) + ".", blnExport));
                    }
                    // Caso não seja agrupador realiza busca de funcionalidades, usuários e grupos
                    else
                    {
                        lstRetorno.add(adicionarObjetoPermissao(("<span class='perfilSistema'>"
                                + realizarIdentacao(intIdentacao, blnExport)
                                + strContadorPerfil
                                + (i + 1)
                                + " - "
                                + objPerfilSistema.getNome() + "</span>"), true, null));

                        //-----------------------------------------------------------
                        //---------- Obtém os critérios de acesso do Perfil ---------
                        //-----------------------------------------------------------
                        CriterioAcessoPerfilFacade objCriterioAcessoPerfilFacade = new CriterioAcessoPerfilFacade();
                        CriterioAcessoPerfil objCriterioAcessoPerfil = new CriterioAcessoPerfil();
                        objCriterioAcessoPerfil.setPerfilSistema(objPerfilSistema);
                        List lstCriterios = objCriterioAcessoPerfilFacade.obterPeloPerfilSistema(objCriterioAcessoPerfil);
                        if (lstCriterios != null && lstCriterios.size() > 0)
                        {
                            lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloCriterios'>"
                                    + realizarIdentacao(intIdentacao + 1, blnExport)
                                    + "Critérios de acesso:" + "</span>"), true, null));
                            for (int j = 0; j < lstCriterios.size(); j++)
                            {
                                objCriterioAcessoPerfil = (CriterioAcessoPerfil) lstCriterios.get(j);
                                StringBuffer strbDescricao = new StringBuffer();
                                if ("S".equals(objCriterioAcessoPerfil.getPreRequisito()))
                                {
                                    strbDescricao.append("<span class='prerequisitoCriterio'>");
                                    strbDescricao.append(realizarIdentacao(intIdentacao + 2, blnExport));
                                    strbDescricao.append("[Pré-requisito]</span> <span class='descricaoCriterio'>");
                                }
                                else
                                {
                                    strbDescricao.append("<span class='descricaoCriterio'>");
                                    strbDescricao.append(realizarIdentacao(intIdentacao + 2, blnExport));
                                }
                                strbDescricao.append(objCriterioAcessoPerfil.getCriterioAcesso().getNome());
                                strbDescricao.append(" - ");
                                strbDescricao.append(objCriterioAcessoPerfil.getNomeCriterioAcessoPerfil());
                                strbDescricao.append("</span>");
                                if (objCriterioAcessoPerfil.getValorCriterioAcessoPerfil() != null)
                                {
                                    strbDescricao.append("<span class='valorCriterio'> [Valor: ");
                                    strbDescricao.append(objCriterioAcessoPerfil.getValorCriterioAcessoPerfil());
                                    strbDescricao.append("]</span>");
                                }
                                lstRetorno.add(adicionarObjetoPermissao(strbDescricao.toString(), true, null));
                            }
                        }

                        //-----------------------------------------------------------
                        //---------- Obtém funcionalidades do Perfil ----------------
                        //-----------------------------------------------------------
                        if (objPerfilSistema.getFuncionalidades() != null && objPerfilSistema.getFuncionalidades().size() > 0)
                        {
                            lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloFuncionalidades'>"
                                    + realizarIdentacao(intIdentacao + 1, blnExport)
                                    + "Funcionalidades:" + "</span>"), true, null));
                            Iterator itrFuncionalidades = objPerfilSistema.getFuncionalidades().iterator();
                            while (itrFuncionalidades.hasNext())
                            {
                                objFuncionalidadeSistema = (FuncionalidadeSistema) itrFuncionalidades.next();
                                lstRetorno.add(adicionarObjetoPermissao(("<span class='funcionalidadePerfil'>"
                                        + realizarIdentacao(intIdentacao + 2, blnExport)
                                        + objFuncionalidadeSistema.getNome() + "</span>"), true, null));
                            }
                        }

                        //-----------------------------------------------------------
                        //---------- Obtém usuários do Perfil -----------------------
                        //-----------------------------------------------------------
                        objUsuarioPerfilSistema.setPerfilSistema(objPerfilSistema);
                        List lstUsuarios = objUsuarioPerfilSistemaFacade.obterPeloPerfilSistema(objUsuarioPerfilSistema);
                        if (lstUsuarios != null && lstUsuarios.size() > 0)
                        {
                            lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloUsuarios'>"
                                    + realizarIdentacao(intIdentacao + 1, blnExport)
                                    + "Usuários:" + "</span>"), true, null));

                            for (int j = 0; j < lstUsuarios.size(); j++)
                            {
                                objUsuarioPerfilSistema = (UsuarioPerfilSistema) lstUsuarios.get(j);
                                String strValidade = "";
                                if (objUsuarioPerfilSistema.getDataInicioValidade() != null)
                                {
                                    strValidade += " - Início: " + DataNova.format(objUsuarioPerfilSistema.getDataInicioValidade());
                                }
                                if (objUsuarioPerfilSistema.getDataTerminoValidade() != null)
                                {
                                    strValidade += " - Término: " + DataNova.format(objUsuarioPerfilSistema.getDataTerminoValidade());
                                }
                                if (objUsuarioPerfilSistema.getIndicativoGestor().equals("S"))
                                {
                                    lstRetorno.add(adicionarObjetoPermissao(("<span class='usuarioGestorPerfil'>"
                                            + realizarIdentacao(intIdentacao + 2, blnExport)
                                            + objUsuarioPerfilSistema.getUsuarioSistema().getNome()
                                            + "</span><span class='usuarioGestorPerfil.detalhe'> [Gestor Perfil]</span>" + strValidade), objUsuarioPerfilSistemaFacade.usuarioPossuiPermissao(objUsuarioPerfilSistema), objUsuarioPerfilSistema));

                                }
                                else
                                {
                                    lstRetorno.add(adicionarObjetoPermissao(("<span class='usuarioPerfil'>"
                                            + realizarIdentacao(intIdentacao + 2, blnExport)
                                            + objUsuarioPerfilSistema.getUsuarioSistema().getNome()
                                            + "</span>" + strValidade), objUsuarioPerfilSistemaFacade.usuarioPossuiPermissao(objUsuarioPerfilSistema), objUsuarioPerfilSistema));
                                }
                            }
                        }

                        //-----------------------------------------------------------
                        //---------- Obtém grupos do Perfil -------------------------
                        //-----------------------------------------------------------
                        if (objPerfilSistema.getGrupos() != null && objPerfilSistema.getGrupos().size() > 0)
                        {
                            lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloGrupos'>" + realizarIdentacao(intIdentacao + 1, blnExport) + "Grupos:" + "</span>"), true, null));
                            Iterator itrGrupos = objPerfilSistema.getGrupos().iterator();
                            while (itrGrupos.hasNext())
                            {
                                objGrupo = (Grupo) itrGrupos.next();
                                lstRetorno.add(adicionarObjetoPermissao(("<span class='grupoPerfil'>"
                                        + realizarIdentacao(intIdentacao + 2, blnExport)
                                        + objGrupo.getDescricao() + "</span>"), true, null));
                            }
                        }

                        //-----------------------------------------------------------
                        //---------- Monta lista de agrupadores --------------------- 
                        //-----------------------------------------------------------
                        lstRetorno.addAll(obterAgrupadoresPerfil(objPerfilSistema, intIdentacao, blnExport));
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

    private List obterAgrupadoresPerfil(PerfilSistema objPerfilSistema, int intIdentacao, boolean blnExport) throws CDException
    {
        List lstRetorno = new ArrayList();

        // Obtém agrupadores
        PerfilSistemaAgrupadorPerfilFacade objPerfilSistemaAgrupadorPerfilFacade = new PerfilSistemaAgrupadorPerfilFacade();

        List lstPerfilSistemaAgrupadorPerfil = objPerfilSistemaAgrupadorPerfilFacade.obterPeloPerfil(objPerfilSistema);
        // Obtém agrupadores associados ao perfil
        if (lstPerfilSistemaAgrupadorPerfil != null && lstPerfilSistemaAgrupadorPerfil.size() > 0)
        {
            lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloAgrupadores'>" + realizarIdentacao(intIdentacao + 1, blnExport) + "Agrupadores:" + "</span>"), true, null));
            for (int j = 0; j < lstPerfilSistemaAgrupadorPerfil.size(); j++)
            {
                PerfilSistemaAgrupadorPerfil objPerfilSistemaAgrupadorPerfil = (PerfilSistemaAgrupadorPerfil) lstPerfilSistemaAgrupadorPerfil.get(j);
                lstRetorno.add(adicionarObjetoPermissao(("<span class='agrupadorPerfil'>"
                        + realizarIdentacao(intIdentacao + 2, blnExport)
                        + objPerfilSistemaAgrupadorPerfil.getAgrupadorPerfil().getNome() + "</span>"), true, null));

                // Obtém os Usuários associados ao agrupador
                AgrupadorPerfilUsuarioFacade objAgrupadorPerfilUsuarioFacade = new AgrupadorPerfilUsuarioFacade();
                List lstAgrupadorPerfilUsuario = objAgrupadorPerfilUsuarioFacade.obterPeloAgrupador(objPerfilSistemaAgrupadorPerfil.getAgrupadorPerfil());

                if (lstAgrupadorPerfilUsuario != null && lstAgrupadorPerfilUsuario.size() > 0)
                {
                    lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloAgrupadorUsuario'>"
                            + realizarIdentacao(intIdentacao + 3, blnExport)
                            + "Usuários :" + "</span>"), true, null));

                    for (int k = 0; k < lstAgrupadorPerfilUsuario.size(); k++)
                    {
                        AgrupadorPerfilUsuario objAgrupadorPerfilUsuario = (AgrupadorPerfilUsuario) lstAgrupadorPerfilUsuario.get(k);
                        lstRetorno.add(adicionarObjetoPermissao(("<span class='agrupadorUsuario'>"
                                + realizarIdentacao(intIdentacao + 4, blnExport)
                                + objAgrupadorPerfilUsuario.getUsuarioSistema().getNome() + "</span>"), true, null));
                    }
                }

                // Obtém os Grupos associados ao agrupador
                AgrupadorPerfilGrupoFacade objAgrupadorPerfilGrupoFacade = new AgrupadorPerfilGrupoFacade();
                List lstAgrupadorPerfilGrupo = objAgrupadorPerfilGrupoFacade.obterGruposSelecionados(objPerfilSistemaAgrupadorPerfil.getAgrupadorPerfil());

                if (lstAgrupadorPerfilGrupo != null && lstAgrupadorPerfilGrupo.size() > 0)
                {
                    lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloAgrupadorGrupo'>"
                            + realizarIdentacao(intIdentacao + 3, blnExport)
                            + "Grupos :" + "</span>"), true, null));

                    for (int k = 0; k < lstAgrupadorPerfilGrupo.size(); k++)
                    {
                        Grupo objGrupoAgrupador = (Grupo) lstAgrupadorPerfilGrupo.get(k);
                        lstRetorno.add(adicionarObjetoPermissao(("<span class='agrupadorGrupo'>"
                                + realizarIdentacao(intIdentacao + 4, blnExport)
                                + objGrupoAgrupador.getDescricao() + "</span>"), true, null));
                    }
                }
            }
        }
        return lstRetorno;
    }

    private ConsultaPermissoes adicionarObjetoPermissao(String strDescricao, boolean blnPossuiAcesso, UsuarioPerfilSistema objUsuarioPerfilSistema)
    {
        ConsultaPermissoes objConsultaPermissoes = new ConsultaPermissoes();
        objConsultaPermissoes.setDescricao(strDescricao);
        objConsultaPermissoes.setPossuiPermissao(blnPossuiAcesso);
        objConsultaPermissoes.setUsuarioPerfilSistema(objUsuarioPerfilSistema);
        return objConsultaPermissoes;
    }

    private String realizarIdentacao(int intNivel, boolean blnExport)
    {
        String strRetorno = "";
        String strIdentacao = "";
        if (blnExport)
        {
            strIdentacao = "......";
        }
        else
        {
            strIdentacao = "&nbsp;&nbsp;&nbsp;&nbsp;";
        }
        for (int i = 0; i < intNivel; i++)
        {
            strRetorno += strIdentacao;
        }
        return strRetorno;
    }

    public List obterPermissoesPorUsuario(UsuarioSistema objUsuarioSistema, String strCodUsuario, boolean blnExport) throws CDException
    {
        List lstRetorno = new ArrayList();

        // Facades
        SistemaFacade objSistemaFacade = new SistemaFacade(obterNomeConexao());
        GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade(obterNomeConexao());
        UsuarioSistemaFacade objUsuarioSistemaFacade = new UsuarioSistemaFacade(obterNomeConexao());

        // Pojos
        UsuarioSistema objUsuario = new UsuarioSistema();
        GestorSistema objGestorSistema = new GestorSistema();
        PerfilSistema objPerfilSistema = null;

        try
        {

            // Obtém o usuário desejado
            objUsuario = objUsuarioSistemaFacade.obterPelaChave(strCodUsuario);
            if (objUsuario == null)
            {
                lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloUsuario'>Usuário não encontrado!</span>"), true, null));
            }
            else
            {
                // Adiciona sistema à lista
                lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloUsuario'>Usuário: " + objUsuario.getNome() + "</span>"), true, null));

                // Obtém os Sistemas que o usuário possui acesso
                List lstSistemas = objSistemaFacade.obterSistemasComunsEntreUsuarioComAcessoEUsuarioEhGestor(objUsuario, objUsuarioSistema);

                if (lstSistemas != null && lstSistemas.size() > 0)
                {
                    lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloSistemas'>" + realizarIdentacao(1, blnExport) + "Sistemas: </span>"), true, null));
                    Sistema objSistema = null;
                    for (int i = 0; i < lstSistemas.size(); i++)
                    {
                        objSistema = (Sistema) lstSistemas.get(i);
                        objGestorSistema.setSistema(objSistema);
                        if (objGestorSistemaFacade.isAdministradorSistema(objSistema, objUsuario))
                        {
                            lstRetorno.add(adicionarObjetoPermissao(("<span class='sistemaUsuario'>" + realizarIdentacao(2, blnExport) + objSistema.getNome() + "</span><span class='sistemaUsuario.administrador'> [Administrador]</span>"), true, null));
                        }
                        else if (objGestorSistemaFacade.isGestorSistema(objSistema, objUsuario))
                        {
                            lstRetorno.add(adicionarObjetoPermissao(("<span class='sistemaUsuario'>" + realizarIdentacao(2, blnExport) + objSistema.getNome() + "</span><span class='sistemaUsuario.gestor'> [Gestor Sistema]</span>"), true, null));
                        }
                        else
                        {
                            lstRetorno.add(adicionarObjetoPermissao(("<span class='sistemaUsuario'>" + realizarIdentacao(2, blnExport) + objSistema.getNome() + "</span>"), true, null));
                        }
                        objPerfilSistema = new PerfilSistema();
                        objPerfilSistema.setSistema(objSistema);

                        lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloPerfis'>" + realizarIdentacao(3, blnExport) + "Perfis:</span>"), true, null));

                        lstRetorno.addAll(obterPerfisPeloSistemaQueSaoComunsEntreUsuarioComAcessoEUsuarioEhGestor(objPerfilSistema, objUsuario, objUsuarioSistema, 4, "", blnExport));
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

    private List obterPerfisPeloSistemaQueSaoComunsEntreUsuarioComAcessoEUsuarioEhGestor(PerfilSistema objPerfilSistema, UsuarioSistema objUsuario, UsuarioSistema objUsuarioLogado, int intIdentacao, String strContadorPerfil, boolean blnExport) throws CDException
    {
        List lstRetorno = new ArrayList();
        UsuarioPerfilSistemaFacade objUsuarioPerfilSistemaFacade = new UsuarioPerfilSistemaFacade();

        // Pojos
        try
        {
            List lstPerfis = obterPerfisPeloSistemaQueSaoComunsEntreUsuarioComAcessoEUsuarioEhGestor(objPerfilSistema, objUsuario, objUsuarioLogado);
            if (lstPerfis != null && lstPerfis.size() > 0)
            {
                for (int i = 0; i < lstPerfis.size(); i++)
                {
                    // Inclui nome do perfil do sistema
                    objPerfilSistema = (PerfilSistema) lstPerfis.get(i);

                    UsuarioPerfilSistema objUsuarioPerfilSistema = new UsuarioPerfilSistema();
                    objUsuarioPerfilSistema.setUsuarioSistema(objUsuario);
                    objUsuarioPerfilSistema.setPerfilSistema(objPerfilSistema);

                    lstRetorno.add(adicionarObjetoPermissao(("<span class='perfilSistema'>"
                            + realizarIdentacao(intIdentacao, blnExport)
                            + strContadorPerfil
                            + (i + 1)
                            + " - "
                            + objPerfilSistema.getNome()
                            + "</span>" + obterTipoAcesso(objPerfilSistema, objUsuario)), objUsuarioPerfilSistemaFacade.usuarioPossuiPermissao(objUsuarioPerfilSistema), objUsuarioPerfilSistema));

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

    private String obterTipoAcesso(PerfilSistema objPerfilSistema, UsuarioSistema objUsuarioSistema) throws CDException
    {
        StringBuffer strRetorno = new StringBuffer();
        StringBuffer strGestor = new StringBuffer();
        StringBuffer strValidade = new StringBuffer();

        // Facades
        UsuarioPerfilSistemaFacade objUsuarioPerfilSistemaFacade = new UsuarioPerfilSistemaFacade();
        AgrupadorPerfilUsuarioFacade objAgrupadorPerfilUsuarioFacade = new AgrupadorPerfilUsuarioFacade();
        AgrupadorPerfilGrupoFacade objAgrupadorPerfilGrupoFacade = new AgrupadorPerfilGrupoFacade();

        // Pojos
        UsuarioPerfilSistema objUsuarioPerfilSistema = new UsuarioPerfilSistema();

        try
        {
            objUsuarioPerfilSistema.setPerfilSistema(objPerfilSistema);
            objUsuarioPerfilSistema.setUsuarioSistema(objUsuarioSistema);
            objUsuarioPerfilSistema = objUsuarioPerfilSistemaFacade.obterPelaChave(objUsuarioPerfilSistema);
            if (objUsuarioPerfilSistema != null)
            {
                strRetorno.append("<span class='tipoAcesso'> [Ponto");
                if (objUsuarioPerfilSistema.getIndicativoGestor().equals("S"))
                {
                    strGestor.append("<span class='usuarioGestorPerfil.detalhe'> [Gestor Perfil]</span>)");
                }

                if (objUsuarioPerfilSistema.getDataInicioValidade() != null)
                {
                    strValidade.append(" Início: " + DataNova.format(objUsuarioPerfilSistema.getDataInicioValidade()));
                }
                if (objUsuarioPerfilSistema.getDataTerminoValidade() != null)
                {
                    strValidade.append(" Término: " + DataNova.format(objUsuarioPerfilSistema.getDataTerminoValidade()));
                }
            }

            if (objPerfilSistema.getGrupos() != null && objPerfilSistema.getGrupos().size() > 0 && objUsuarioSistema.getGrupo() != null)
            {
                Iterator itrGrupos = objPerfilSistema.getGrupos().iterator();
                Grupo objGrupo = null;
                while (itrGrupos.hasNext())
                {
                    objGrupo = (Grupo) itrGrupos.next();
                    if (objGrupo.getCodigo().intValue() == objUsuarioSistema.getGrupo().getCodigo().intValue())
                    {
                        if (strRetorno.length() <= 0)
                        {
                            strRetorno.append("<span class='tipoAcesso'> [Grupo");
                        }
                        else
                        {
                            strRetorno.append(" / Grupo");
                        }
                    }
                }
            }

            // Verifica os agrupadores do usuário
            List lstAgrupadorUsuario = objAgrupadorPerfilUsuarioFacade.obterPeloUsuarioEPerfilSistema(objUsuarioSistema, objPerfilSistema);
            if (lstAgrupadorUsuario != null && lstAgrupadorUsuario.size() > 0)
            {
                if (strRetorno.length() <= 0)
                {
                    strRetorno.append("<span class='tipoAcesso'> [Agrupador(usuário)");
                }
                else
                {
                    strRetorno.append(" / Agrupador(usuário)");
                }

                for (int i = 0; i < lstAgrupadorUsuario.size(); i++)
                {
                    AgrupadorPerfilUsuario objAgrupadorPerfilUsuario = (AgrupadorPerfilUsuario) lstAgrupadorUsuario.get(i);
                    strRetorno.append(":" + objAgrupadorPerfilUsuario.getAgrupadorPerfil().getNome());
                }
            }

            // Verifica os agrupadores do grupo
            List lstAgrupador = objAgrupadorPerfilGrupoFacade.obterPeloGrupoEPerfilSistema(objUsuarioSistema.getGrupo(), objPerfilSistema);
            if (lstAgrupador != null && lstAgrupador.size() > 0)
            {
                if (strRetorno.length() <= 0)
                {
                    strRetorno.append("<span class='tipoAcesso'> [Agrupador(grupo)");
                }
                else
                {
                    strRetorno.append(" / Agrupador(grupo)");
                }

                for (int i = 0; i < lstAgrupador.size(); i++)
                {
                    AgrupadorPerfilGrupo objAgrupadorPerfilGrupo = (AgrupadorPerfilGrupo) lstAgrupador.get(i);
                    strRetorno.append(":" + objAgrupadorPerfilGrupo.getAgrupadorPerfil().getNome());
                }
            }

            if (strRetorno.length() > 0)
            {
                strRetorno.append("]</span>");
            }

            strRetorno.append(strGestor.toString() + " " + strValidade.toString());
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar(obterNomeConexao());
        }

        return strRetorno.toString();
    }

    public List obterPermissoesPorGrupo(UsuarioSistema objUsuarioSistema, String strCodGrupo, boolean blnExport) throws CDException
    {
        List lstRetorno = new ArrayList();

        // Facades
        SistemaFacade objSistemaFacade = new SistemaFacade();
        GrupoFacade objGrupoFacade = new GrupoFacade();

        // Pojos
        PerfilSistema objPerfilSistema = null;
        Grupo objGrupo = null;

        try
        {

            // Obtém o usuário desejado
            objGrupo = objGrupoFacade.obterPelaChave(strCodGrupo);
            if (objGrupo == null)
            {
                lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloGrupo'>Grupo não encontrado!</span>"), true, null));
            }
            else
            {
                // Adiciona sistema à lista
                lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloGrupo'>Grupo: " + objGrupo.getDescricao() + "</span>"), true, null));

                // Obtém os Sistemas que o usuário possui acesso
                List lstSistemas = objSistemaFacade.obterPorGrupoUsuarioEhGestor(objGrupo, objUsuarioSistema);

                if (lstSistemas != null && lstSistemas.size() > 0)
                {
                    lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloSistemas'>" + realizarIdentacao(1, blnExport) + "Sistemas: </span>"), true, null));
                    Sistema objSistema = null;
                    for (int i = 0; i < lstSistemas.size(); i++)
                    {
                        objSistema = (Sistema) lstSistemas.get(i);
                        lstRetorno.add(adicionarObjetoPermissao(("<span class='sistemaGrupo'>" + realizarIdentacao(2, blnExport) + objSistema.getNome() + "</span>"), true, null));

                        objPerfilSistema = new PerfilSistema();
                        objPerfilSistema.setSistema(objSistema);

                        lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloPerfis'>" + realizarIdentacao(3, blnExport) + "Perfis:</span>"), true, null));

                        lstRetorno.addAll(obterPerfisGrupo(objPerfilSistema, objGrupo, objUsuarioSistema, 4, "", blnExport));
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

    private List obterPerfisGrupo(PerfilSistema objPerfilSistema, Grupo objGrupo, UsuarioSistema objUsuarioLogado, int intIdentacao, String strContadorPerfil, boolean blnExport) throws CDException
    {
        List lstRetorno = new ArrayList();

        // Pojos
        try
        {
            List lstPerfis = obterPeloSistemaGrupoUsuarioEhGestor(objPerfilSistema, objGrupo, objUsuarioLogado);
            if (lstPerfis != null && lstPerfis.size() > 0)
            {
                for (int i = 0; i < lstPerfis.size(); i++)
                {
                    // Inclui nome do perfil do sistema
                    objPerfilSistema = (PerfilSistema) lstPerfis.get(i);

                    lstRetorno.add(adicionarObjetoPermissao(("<span class='perfilSistema'>"
                            + realizarIdentacao(intIdentacao, blnExport)
                            + strContadorPerfil
                            + (i + 1)
                            + " - "
                            + objPerfilSistema.getNome() + "</span>"), true, null));
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

    public List obterPermissoesGestores(UsuarioSistema objUsuarioLogado, String strObjControladoApoio, String strObjControladoGestao, boolean blnExport) throws CDException
    {
        List lstRetorno = new ArrayList();

        // Mapeamentos que serverm para auxiliar a pesquisa e otimizar a consulta ao banco

        // mapUsuario - armazena todos os usuários que possuem alguma habilitação
        Map mapUsuarios = new HashMap();
        // Armazena os usuários que possuem habilitação de administrador
        Map mapAdministrador = new HashMap();
        // Armazena os usuários que possuem habilitação de gestor de sistema
        Map mapGestorSistema = new HashMap();
        // Armazena os usuários que possuem habilitação de gestor de perfil
        Map mapGestorPerfil = new HashMap();
        // Armazena os usuários que possuem habilitação ao perfil de apoio
        Map mapPerfilApoio = new HashMap();
        // Armazena os usuários que possuem habilitação ao perfil de gestão
        Map mapPerfilGestao = new HashMap();

        try
        {
            // Obtém os administradores e os gestores de sistema (estão no mesmo método porque possuem a mesma tabela de acesso)
            obterAdministradorGestorSistema(mapUsuarios, mapAdministrador, mapGestorSistema);

            // Obtém os gestores de perfil
            obterGestorPerfil(mapUsuarios, mapGestorPerfil);

            // Obtém os usuário que possuem habilitação ao perfil de apoio
            obterUsuarioPerfil(mapUsuarios, mapPerfilApoio, strObjControladoApoio);

            // Obtém os usuário que possuem habilitação ao perfil de gestão
            obterUsuarioPerfil(mapUsuarios, mapPerfilGestao, strObjControladoGestao);

            List lstUsuarios = Collections.list(Collections.enumeration(mapUsuarios.values()));
            Collections.sort(lstUsuarios, new OrdenadorBean("nome"));

            for (int i = 0; i < lstUsuarios.size(); i++)
            {
                boolean blnAdministrador = false;
                boolean blnGestorSistema = false;
                boolean blnGestorPerfil = false;
                boolean blnPerfilApoio = false;
                boolean blnPerfilGestao = false;

                UsuarioSistema objUsuario = (UsuarioSistema) lstUsuarios.get(i);
                lstRetorno.add(adicionarObjetoPermissao(("<span class='nomeUsuario'>" + objUsuario.getNome() + "</span>"), true, null));

                // Tipo Gestor
                int intContGestor = 1;

                if (mapAdministrador.get(objUsuario.getIdentificador()) != null)
                {
                    blnAdministrador = true;
                    lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloTipoGestor'>" + realizarIdentacao(1, blnExport) + "Tipo Gestor:</span>"), true, null));
                    lstRetorno.add(adicionarObjetoPermissao((realizarIdentacao(2, blnExport)
                            + intContGestor++
                            + ") Administrador <span class='sistemas'>"
                            + obterDescricaoSistema(mapAdministrador, objUsuario.getIdentificador()) + "</span>"), true, null));
                }
                if (mapGestorSistema.get(objUsuario.getIdentificador()) != null)
                {
                    blnGestorSistema = true;

                    if (!blnAdministrador)
                    {
                        lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloTipoGestor'>" + realizarIdentacao(1, blnExport) + "Tipo Gestor:</span>"), true, null));
                    }
                    lstRetorno.add(adicionarObjetoPermissao((realizarIdentacao(2, blnExport)
                            + intContGestor++
                            + ") Gestor Sistema <span class='sistemas'>"
                            + obterDescricaoSistema(mapGestorSistema, objUsuario.getIdentificador()) + "</span>"), true, null));
                }
                if (mapGestorPerfil.get(objUsuario.getIdentificador()) != null)
                {
                    blnGestorPerfil = true;
                    if (!blnAdministrador && !blnGestorSistema)
                    {
                        lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloTipoGestor'>" + realizarIdentacao(1, blnExport) + "Tipo Gestor:</span>"), true, null));
                    }
                    lstRetorno.add(adicionarObjetoPermissao((realizarIdentacao(2, blnExport)
                            + intContGestor++
                            + ") Gestor Perfil <span class='sistemas'>"
                            + obterDescricaoPerfil(mapGestorPerfil, objUsuario.getIdentificador()) + "</span>"), true, null));
                }

                // Acessos
                int intContAcessos = 1;
                if (mapPerfilApoio.get(objUsuario.getIdentificador()) != null)
                {
                    blnPerfilApoio = true;

                    lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloAcessos'>" + realizarIdentacao(1, blnExport) + "Acessos:</span>"), true, null));

                    lstRetorno.add(adicionarObjetoPermissao((realizarIdentacao(2, blnExport) + intContAcessos++ + ") Possui acesso ao módulo de Perfil Apoio"), true, null));
                }
                if (mapPerfilGestao.get(objUsuario.getIdentificador()) != null)
                {
                    blnPerfilGestao = true;
                    if (!blnPerfilApoio)
                    {
                        lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloAcessos'>" + realizarIdentacao(1, blnExport) + "Acessos:</span>"), true, null));
                    }
                    lstRetorno.add(adicionarObjetoPermissao((realizarIdentacao(2, blnExport) + intContAcessos++ + ") Possui acesso ao módulo de Perfil Gestão"), true, null));
                }

                // Avisos
                montarAvisos(lstRetorno, blnAdministrador, blnGestorSistema, blnGestorPerfil, blnPerfilApoio, blnPerfilGestao, blnExport);

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

    private void montarAvisos(List lstRetorno, boolean blnAdministrador, boolean blnGestorSistema, boolean blnGestorPerfil, boolean blnPerfilApoio, boolean blnPerfilGestao, boolean blnExport)
    {
        boolean blnAvisos = false;
        int intContAvisos = 1;
        if (blnAdministrador && !blnPerfilApoio)
        {
            blnAvisos = true;

            lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloAvisos'>" + realizarIdentacao(1, blnExport) + "Avisos:</span>"), true, null));

            lstRetorno.add(adicionarObjetoPermissao((realizarIdentacao(2, blnExport) + intContAvisos++ + ") Usuário administrador sem acesso ao módulo de perfil apoio"), true, null));
        }
        if (blnGestorSistema && !blnPerfilGestao)
        {
            if (!blnAvisos)
            {
                blnAvisos = true;
                lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloAvisos'>" + realizarIdentacao(1, blnExport) + "Avisos:</span>"), true, null));
            }

            lstRetorno.add(adicionarObjetoPermissao((realizarIdentacao(2, blnExport) + intContAvisos++ + ") Usuário gestor de sistema sem acesso ao módulo de perfil gestão"), true, null));
        }
        if (blnGestorPerfil && !blnPerfilGestao)
        {
            if (!blnAvisos)
            {
                blnAvisos = true;
                lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloAvisos'>" + realizarIdentacao(1, blnExport) + "Avisos:</span>"), true, null));
            }

            lstRetorno.add(adicionarObjetoPermissao((realizarIdentacao(2, blnExport) + intContAvisos++ + ") Usuário gestor de perfil sem acesso ao módulo de perfil gestão"), true, null));
        }
        if (blnPerfilApoio && !blnAdministrador)
        {
            if (!blnAvisos)
            {
                blnAvisos = true;
                lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloAvisos'>" + realizarIdentacao(1, blnExport) + "Avisos:</span>"), true, null));
            }

            lstRetorno.add(adicionarObjetoPermissao((realizarIdentacao(2, blnExport) + intContAvisos++ + ") Usuário com acesso ao módulo de perfil apoio sem ser administrador"), true, null));
        }
        if (blnPerfilGestao && !blnGestorSistema && !blnGestorPerfil)
        {
            if (!blnAvisos)
            {
                blnAvisos = true;
                lstRetorno.add(adicionarObjetoPermissao(("<span class='tituloAvisos'>" + realizarIdentacao(1, blnExport) + "Avisos:</span>"), true, null));
            }

            lstRetorno.add(adicionarObjetoPermissao((realizarIdentacao(2, blnExport) + intContAvisos++ + ") Usuário com acesso ao módulo de perfil gestão sem ser gestor de sistema ou gestor de perfil"), true, null));
        }

    }

    private String obterDescricaoSistema(Map map, Object objKey)
    {
        String strSistemas = "";
        if (map.get(objKey) != null)
        {
            List lstSistema = (List) map.get(objKey);
            if (lstSistema != null)
            {
                strSistemas += "[";
                for (int j = 0; j < lstSistema.size(); j++)
                {
                    if (j != 0)
                    {
                        strSistemas += ", ";
                    }
                    Sistema objSistema = (Sistema) lstSistema.get(j);
                    strSistemas += objSistema.getObjetoControlado();
                }
                strSistemas += "]";
            }
        }
        return strSistemas;
    }

    private String obterDescricaoPerfil(Map map, Object objKey)
    {
        String strPerfil = "";
        if (map.get(objKey) != null)
        {
            List lstPerfil = (List) map.get(objKey);
            if (lstPerfil != null)
            {
                strPerfil += "[";
                for (int j = 0; j < lstPerfil.size(); j++)
                {
                    if (j != 0)
                    {
                        strPerfil += ", ";
                    }
                    PerfilSistema objPerfil = (PerfilSistema) lstPerfil.get(j);
                    strPerfil += objPerfil.getNome();
                }
                strPerfil += "]";
            }
        }
        return strPerfil;
    }

    private void obterAdministradorGestorSistema(Map mapUsuarios, Map mapAdministrador, Map mapGestorSistema) throws CDException
    {
        GestorSistemaFacade objGestorSistemaFacade = new GestorSistemaFacade();

        try
        {
            // Obtém a lista de usuários que são administradores e gestores de sistema ao qual o usuário logado possui acesso
            List lstGestorSistema = objGestorSistemaFacade.obterTodos();
            for (int i = 0; i < lstGestorSistema.size(); i++)
            {
                GestorSistema objGestorSistema = (GestorSistema) lstGestorSistema.get(i);

                // Caso seja administrador
                if ("S".equals(objGestorSistema.getIndicativoAdministrador()))
                {
                    adicionaLista(mapAdministrador, objGestorSistema.getUsuarioSistema().getIdentificador(), objGestorSistema.getSistema());
                }
                // Caso seja gestor de sistema
                else
                {
                    adicionaLista(mapGestorSistema, objGestorSistema.getUsuarioSistema().getIdentificador(), objGestorSistema.getSistema());
                }
                mapUsuarios.put(objGestorSistema.getUsuarioSistema().getIdentificador(), objGestorSistema.getUsuarioSistema());
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

    private void obterGestorPerfil(Map mapUsuarios, Map mapGestorPerfil) throws CDException
    {
        UsuarioPerfilSistemaFacade objUsuarioPerfilSistemaFacade = new UsuarioPerfilSistemaFacade();

        try
        {
            // Obtém a lista de usuários que são gestores de um determinado perfil ao qual o usuário logado possui acesso
            List lstUsuarioPerfilSistema = objUsuarioPerfilSistemaFacade.obterGestoresPerfil();
            for (int i = 0; i < lstUsuarioPerfilSistema.size(); i++)
            {
                UsuarioPerfilSistema objUsuarioPerfilSistema = (UsuarioPerfilSistema) lstUsuarioPerfilSistema.get(i);
                adicionaLista(mapGestorPerfil, objUsuarioPerfilSistema.getUsuarioSistema().getIdentificador(), objUsuarioPerfilSistema.getPerfilSistema());
                mapUsuarios.put(objUsuarioPerfilSistema.getUsuarioSistema().getIdentificador(), objUsuarioPerfilSistema.getUsuarioSistema());
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

    private void obterUsuarioPerfil(Map mapUsuarios, Map mapPerfil, String strObjControlado) throws CDException
    {
        UsuarioPerfilSistemaFacade objUsuarioPerfilSistemaFacade = new UsuarioPerfilSistemaFacade();

        try
        {
            // Obtém a lista de usuários que possuem perfis de apoio 
            List lstUsuarioPerfilApoio = objUsuarioPerfilSistemaFacade.obterPeloObjetoControlado(strObjControlado);
            for (int i = 0; i < lstUsuarioPerfilApoio.size(); i++)
            {
                UsuarioPerfilSistema objUsuarioPerfilSistema = (UsuarioPerfilSistema) lstUsuarioPerfilApoio.get(i);
                adicionaLista(mapPerfil, objUsuarioPerfilSistema.getUsuarioSistema().getIdentificador(), null);
                mapUsuarios.put(objUsuarioPerfilSistema.getUsuarioSistema().getIdentificador(), objUsuarioPerfilSistema.getUsuarioSistema());
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

    private void adicionaLista(Map map, Object objKey, Object objValue)
    {
        List lstLista = null;
        if (map.get(objKey) == null)
        {
            lstLista = new ArrayList();
        }
        else
        {
            lstLista = (List) map.get(objKey);
        }
        lstLista.add(objValue);
        map.put(objKey, lstLista);
    }

    /**
     * Obtém os registros de determinada página
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPerfilSistemaNotIn(PerfilSistema objPerfilSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO(obterNomeConexao());
        List lstRetorno = null;
        try
        {
            lstRetorno = objPerfilSistemaDAO.obterPerfilSistemaNotIn(objPerfilSistema);
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

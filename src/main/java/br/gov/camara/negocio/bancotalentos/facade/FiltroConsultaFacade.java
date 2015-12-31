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

package br.gov.camara.negocio.bancotalentos.facade;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.Facade;
import br.gov.camara.negocio.autenticacaoperfil.dao.PerfilSistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.dao.UsuarioSistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.PerfilSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Sistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;
import br.gov.camara.negocio.bancotalentos.dao.AtributoTalentoDAO;
import br.gov.camara.negocio.bancotalentos.dao.FiltroConsultaDAO;
import br.gov.camara.negocio.bancotalentos.dao.TipoFiltroConsultaDAO;
import br.gov.camara.negocio.bancotalentos.pojo.FiltroConsulta;
import br.gov.camara.negocio.bancotalentos.pojo.TipoFiltroConsulta;

/**
 * Facade para atributo de talento
 */
public class FiltroConsultaFacade extends Facade
{
    // Variáveis de instância
    private static Log log = LogFactory.getLog(FiltroConsultaFacade.class);

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
        FiltroConsultaDAO objFiltroConsultaDAO = new FiltroConsultaDAO();
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objFiltroConsultaDAO.obterTotalRegistros();
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return intTotalRegistros;
    }

    /**
     * Obtém o total de registros por tipo de filtro de consulta
     * 
     * @param TipoFiltroConsulta Objeto contendo os dados do tipo de filtro de consulta
     * 
     * @return int Contendo o total
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public int obterTotalRegistrosPorTipoFiltroConsulta(TipoFiltroConsulta objTipoFiltroConsulta) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        FiltroConsultaDAO objFiltroConsultaDAO = new FiltroConsultaDAO();

        // Recebe o total de registro
        int intTotalRegistros = 0;
        try
        {
            intTotalRegistros = objFiltroConsultaDAO.obterTotalRegistrosPorTipoFiltroConsulta(objTipoFiltroConsulta);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
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
        FiltroConsultaDAO objFiltroConsultaDAO = new FiltroConsultaDAO();

        // Recebe lista de retorno
        List lstRetorno = null;
        try
        {
            lstRetorno = objFiltroConsultaDAO.obterPorPagina(intNumeroPagina, intMaximoPagina);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }

    /**
     * Obtém os registros de determinada página por tipo de filtro de consulta
     * 
     * @param TipoFiltroConsulta Objetos Contendos os dados para consulta
     * @param int Número da página a ser mostrada
     * @param int Quantidade de registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPorTipoFiltroConsultaPorPagina(TipoFiltroConsulta objTipoFiltroConsulta, int intNumeroPagina, int intMaximoPagina) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros da página
        FiltroConsultaDAO objFiltroConsultaDAO = new FiltroConsultaDAO();

        // Recebe a lista de retorno
        List lstRetorno = null;
        try
        {
            lstRetorno = objFiltroConsultaDAO.obterPorTipoFiltroConsultaPorPagina(objTipoFiltroConsulta, intNumeroPagina, intMaximoPagina);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }

    /**
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return FiltroConsulta POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public FiltroConsulta obterPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        FiltroConsultaDAO objFiltroConsultaDAO = new FiltroConsultaDAO();
        FiltroConsulta objFiltroConsulta = null;
        try
        {
            // Obtém objeto pela chave
            objFiltroConsulta = (FiltroConsulta) objFiltroConsultaDAO.obterPelaChave(strChave);
            // Inicializa objetos relacionados
            objFiltroConsultaDAO.inicializarFiltroConsultaGrupo(objFiltroConsulta);
            objFiltroConsultaDAO.inicializarFiltroConsultaUsuario(objFiltroConsulta);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return objFiltroConsulta;
    }

    /**
     * Obtém um registro de perfil de sistema a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return PerfilSistema POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public PerfilSistema obterPerfilSistemaPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO();
        PerfilSistema objPerfilSistema = null;
        try
        {
            objPerfilSistema = (PerfilSistema) objPerfilSistemaDAO.obterPelaChave(strChave);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return objPerfilSistema;
    }

    /**
     * Obtém os atributos de talento
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterAtributosTalento() throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros
        AtributoTalentoDAO objAtributoTalentoDAO = new AtributoTalentoDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objAtributoTalentoDAO.obterTodos();
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }

    /**
     * Obtém os perfis de sistema pelo sistema a que ele pertence
     * 
     * @param objSistema Sistema que pertence o perfil de sistema
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterPerfisSistemaPeloSistema(Sistema objSistema) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros
        PerfilSistemaDAO objPerfilSistemaDAO = new PerfilSistemaDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objPerfilSistemaDAO.obterPeloSistema(objSistema);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }

    /**
     * Inclui um registro
     *
     * @param FiltroConsulta POJO representando o objeto a ser incluído
     *
     * @return retorna a chave do objeto que foi incluido
     * 
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public String incluir(FiltroConsulta objFiltroConsulta) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e inclui registro
        FiltroConsultaDAO objFiltroConsultaDAO = new FiltroConsultaDAO();
        String strChave = null;

        try
        {
            strChave = objFiltroConsultaDAO.incluir(objFiltroConsulta);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }

        return strChave;
    }

    /**
     * Altera um registro
     *
     * @param objFiltroConsulta POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void alterar(FiltroConsulta objFiltroConsulta) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e altera registro
        FiltroConsultaDAO objFiltroConsultaDAO = new FiltroConsultaDAO();
        try
        {
            objFiltroConsultaDAO.alterar(objFiltroConsulta);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
    }

    /**
     * Exclui um registro
     *
     * @param objFiltroConsulta POJO representando o objeto a ser incluído
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     *
     */
    public void excluir(FiltroConsulta objFiltroConsulta) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e exclui registro
        FiltroConsultaDAO objFiltroConsultaDAO = new FiltroConsultaDAO();
        try
        {
            objFiltroConsultaDAO.excluir(objFiltroConsulta);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
    }

    /**
     * Obtém os grupos disponíveis para determinada categoria de talento
     * 
     * @param objFiltroConsulta Categoria de talento a ser verificada
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterGruposDisponiveisCriterioConsulta(FiltroConsulta objFiltroConsulta) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros
        FiltroConsultaDAO objFiltroConsultaDAO = new FiltroConsultaDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objFiltroConsultaDAO.obterGruposDisponiveisCriterioConsulta(objFiltroConsulta);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }

    /**
     * Obtém os grupos disponíveis para determinada categoria de talento
     * 
     * @param objFiltroConsulta Categoria de talento a ser verificada
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterGruposDisponiveisFiltroConsultaGrupo(FiltroConsulta objFiltroConsulta) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros
        FiltroConsultaDAO objFiltroConsultaDAO = new FiltroConsultaDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objFiltroConsultaDAO.obterGruposDisponiveisFiltroConsultaGrupo(objFiltroConsulta);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }

    /**
     * Obtém os grupos selecionados para determinada categoria de talento
     * 
     * @param objFiltroConsulta Categoria de talento a ser verificada
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterGruposSelecionadosFiltroConsultaGrupo(FiltroConsulta objFiltroConsulta) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros
        FiltroConsultaDAO objFiltroConsultaDAO = new FiltroConsultaDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objFiltroConsultaDAO.obterGruposSelecionadosFiltroConsultaGrupo(objFiltroConsulta);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }

    /**
     * Obtém os grupos selecionados para determinada categoria de talento
     * 
     * @param objFiltroConsulta Categoria de talento a ser verificada
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public List obterGruposSelecionadosCriterioConsulta(FiltroConsulta objFiltroConsulta) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros
        FiltroConsultaDAO objFiltroConsultaDAO = new FiltroConsultaDAO();
        List lstRetorno = null;
        try
        {
            lstRetorno = objFiltroConsultaDAO.obterGruposSelecionadosCriterioConsulta(objFiltroConsulta);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return lstRetorno;
    }

    /**
     * Obtém uma categoria de talento a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return CategoriaTalento POJO representando o registro obtido
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public TipoFiltroConsulta obterTipoFiltroConsultaPelaChave(String strChave) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém o registro pela chave
        TipoFiltroConsultaDAO objTipoFiltroConsultaDAO = new TipoFiltroConsultaDAO();
        TipoFiltroConsulta objTipoFiltroConsulta = null;
        try
        {
            objTipoFiltroConsulta = (TipoFiltroConsulta) objTipoFiltroConsultaDAO.obterPelaChave(strChave);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
        return objTipoFiltroConsulta;
    }

    /**
     * Exclui o filtro de consulta de usuário
     * 
     * @param CriterioAcessoLotacao Usuário a ser verificado
     * @param FiltroConsulta Filtro de Consulta a ser verificado
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public void excluir(UsuarioSistema objUsuarioSistema, FiltroConsulta objFiltroConsulta) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no método");
        }

        try
        {
            // Itera coleção para procurar o registro desejado na exclusão
            Iterator itrFiltroConsultaUsuario = objFiltroConsulta.getFiltroConsultaUsuario().iterator();
            UsuarioSistema objUsuario = null;

            while (itrFiltroConsultaUsuario.hasNext())
            {
                objUsuario = (UsuarioSistema) itrFiltroConsultaUsuario.next();
                if (objUsuarioSistema.getIdentificador().equals(objUsuario.getIdentificador()))
                {
                    break;
                }
                objUsuario = null;
            }
            if (objUsuario != null)
            {
                objFiltroConsulta.getFiltroConsultaUsuario().remove(objUsuario);
            }

            alterar(objFiltroConsulta);
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
    }

    /**
     * Obtém os grupos disponíveis para determinado filtro de consulta de usuário
     * 
     * @param objFiltroConsulta Filtro de consulta a ser verificado
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public void incluir(UsuarioSistema objUsuarioSistema, FiltroConsulta objFiltroConsulta) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no método");
        }

        // Instancia DAO e obtém os registros
        UsuarioSistemaDAO objUsuarioSistemaDAO = new UsuarioSistemaDAO();

        try
        {
            // Obtém usuário
            objUsuarioSistema = (UsuarioSistema) objUsuarioSistemaDAO.obterPelaChave(objUsuarioSistema.getIdentificador().toString());
            // Verifica se usuário é válido
            if (objUsuarioSistema == null)
            {
                throw new CDException("Usuário não existe");
            }
            else
            {
                // Verifica se usuário já está cadastrado
                UsuarioSistema objUsuario = obterPelaChave(objUsuarioSistema.getIdentificador().toString(), objFiltroConsulta);
                if (objUsuario != null)
                {
                    throw new CDException("Usuário já cadastrado");
                }
                else
                {
                    // Inclui usuário através do método alterar da facade Filtro Consulta 
                    objFiltroConsulta.getFiltroConsultaUsuario().add(objUsuarioSistema);
                    alterar(objFiltroConsulta);
                }
            }
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }
    }

    /**
     * Obtém os grupos disponíveis para determinado filtro de consulta de usuário
     * 
     * @param objFiltroConsulta Filtro de consulta a ser verificado
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws CDException se ocorrer algum erro relacionado ao negócio
     * 
     */
    public UsuarioSistema obterPelaChave(String strChave, FiltroConsulta objFiltroConsulta) throws CDException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Instancia DAO e obtém os registros
        //FiltroConsultaGrupoDAO objFiltroConsultaGrupoDAO = new FiltroConsultaGrupoDAO();
        UsuarioSistema objUsuarioSistema = null;

        try
        {
            Iterator itrFiltroConsultaUsuario = objFiltroConsulta.getFiltroConsultaUsuario().iterator();
            while (itrFiltroConsultaUsuario.hasNext())
            {
                UsuarioSistema objUsuarioSistemaTemp = (UsuarioSistema) itrFiltroConsultaUsuario.next();
                if (strChave.equals("" + objUsuarioSistemaTemp.getIdentificador()))
                {
                    objUsuarioSistema = objUsuarioSistemaTemp;
                    break;
                }
            }
        }
        catch (Exception daoe)
        {
            CDException.dispararExcecao(daoe);
        }
        finally
        {
            DAO.desconectar();
        }

        return objUsuarioSistema;
    }

}

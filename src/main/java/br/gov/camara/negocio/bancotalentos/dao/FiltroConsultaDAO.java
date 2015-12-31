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

package br.gov.camara.negocio.bancotalentos.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.autenticacaoperfil.dao.UsuarioSistemaDAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Grupo;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;
import br.gov.camara.negocio.bancotalentos.pojo.FiltroConsulta;
import br.gov.camara.negocio.bancotalentos.pojo.TipoFiltroConsulta;
import br.gov.camara.negocio.exception.DAOException;
import br.gov.camara.negocio.util.Consulta;

/**
 * Classe DAO para a tabela FiltroConsulta
 */

public class FiltroConsultaDAO extends DAO
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(FiltroConsultaDAO.class);

    /**
     * Construtor sem argumentos
     * 
     */
    public FiltroConsultaDAO()
    {
        super("FiltroConsulta");
    }

    /**
     * Obt�m todos os registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterTodos() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM" + " FiltroConsulta filtroConsulta" +

        " ORDER BY" + " filtroConsulta.identificador ASC";

        return super.obter(strConsulta);
    }

    /**
     * Obt�m todos os registros pelo tipo de filtro de consulta
     * 
     * @param TipoFiltroConsulta Objeto contendo os dados do tipo de filtro de consulta
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterTodosPorTipoFiltroConsulta(TipoFiltroConsulta objTipoFiltroConsulta) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM" + " FiltroConsulta filtroConsulta" + " WHERE filtroConsulta.tipoFiltroConsulta.identificador = "
            + objTipoFiltroConsulta.getIdentificador() + " ORDER BY" + " filtroConsulta.identificador ASC";

        return super.obter(strConsulta);
    }

    /**
     * Obt�m um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public Object obterPelaChave(String strChave) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        String strConsulta = " FROM" + " FiltroConsulta filtroConsulta" +

        " WHERE " + " filtroConsulta.identificador = " + strChave;

        return (FiltroConsulta) obter(strConsulta).get(0);
    }

    /**
     * Obt�m o total de registros
     * 
     * @return int Total de registro
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalRegistros() throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " SELECT COUNT(*)" +

        " FROM" + " FiltroConsulta filtroConsulta";

        // Recupera os dados
        return obterTotalRegistros(strConsulta);
    }

    /**
     * Obt�m o total de registros pelo tipo de filtro da consulta
     * 
     * @return TipoFiltroConsulta Objeto contendo os dados do tipo de filtro de consulta
     * 
     * @return int Total de registros
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalRegistrosPorTipoFiltroConsulta(TipoFiltroConsulta objTipoFiltroConsulta) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " SELECT COUNT(*)" + " FROM" + " FiltroConsulta filtroConsulta" + " WHERE filtroConsulta.tipoFiltroConsulta.identificador = "
            + objTipoFiltroConsulta.getIdentificador();

        // Recupera os dados
        return obterTotalRegistros(strConsulta);
    }

    /**
     * Obt�m os registros de determinada p�gina
     *
     * @param int Contendo o n�mero da p�gina
     * @param int Contendo o m�ximo de registros por p�gina
     *  
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        // Monta consulta
        String strConsulta = " FROM" + " FiltroConsulta filtroConsulta" +

        " ORDER BY" + " filtroConsulta.identificador ASC";

        // Recupera os dados
        return obterPorPagina(intNumeroPagina, intMaximoPagina, strConsulta);
    }

    /**
     * Obt�m os registros de determinada p�gina
     * 
     * @param TipoFiltroConsulta Objeto contendo os dados do tipo de filtro de consulta
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPorTipoFiltroConsultaPorPagina(TipoFiltroConsulta objTipoFiltroConsulta, int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        // Monta consulta
        String strConsulta = " FROM" + " FiltroConsulta filtroConsulta" + " WHERE filtroConsulta.tipoFiltroConsulta.identificador = "
            + objTipoFiltroConsulta.getIdentificador() + " ORDER BY" + " filtroConsulta.identificador ASC";

        // Recupera os dados
        return obterPorPagina(intNumeroPagina, intMaximoPagina, strConsulta);
    }

    /**
     * Obt�m total de registros da consulta
     *
     * @param Consulta objeto contendo os filtros para consulta
     *  
     * @return List contendo o resultado da pesquisa
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public int obterTotalRegistros(Consulta objConsulta) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        // Declara��es
        int intRetorno = 0;

        try
        {
            String strFiltro = objConsulta.montarFiltro();

            // Monta query
            String strConsulta = " SELECT COUNT(*)" + " FROM" + " FiltroConsulta filtroConsulta";
            if (!strFiltro.trim().equals(""))
            {
                strConsulta += " WHERE " + strFiltro;
            }

            // Recupera os dados
            intRetorno = obterTotalRegistros(strConsulta);

            // Retorna
            return intRetorno;
        }
        catch (CDException cde)
        {
            throw new DAOException("Ocorreu um erro ao consultar registros na classe " + strNomeClasse, cde);
        }
    }

    /**
     * Exclui um registro
     *
     * @param Object POJO representando o objeto a ser exclu�do
     * 
     * @return String Contendo a consulta de exclus�o 
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public String excluirImpl(Object objPojo) throws DAOException
    {
        return " FROM FiltroConsulta filtroConsulta" + " WHERE filtroConsulta.identificador = " + ((FiltroConsulta) objPojo).getIdentificador();
    }

    /**
     * M�todo que pede a inicializa��o da propriedade grupoCriterioConsulta, que � lazy
     * 
     * @param FiltroConsulta Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarGrupoCriterioConsulta(FiltroConsulta objFiltroConsulta) throws DAOException
    {
        inicializarRelacionamento(objFiltroConsulta.getGrupoCriterioConsulta());
    }

    /* M�todo que pede a inicializa��o da propriedade grupoCriterioConsulta, que � lazy
     * 
     * @param List Lista contendo o objetos do tipo GrupoCriterioConsulta
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarGrupoCriterioConsulta(List lstPerfisSistema) throws DAOException
    {
        Iterator itrGrupoCriterioConsulta = lstPerfisSistema.iterator();
        while (itrGrupoCriterioConsulta.hasNext())
        {
            inicializarRelacionamento(((FiltroConsulta) itrGrupoCriterioConsulta.next()).getGrupoCriterioConsulta());
        }
    }

    /**
     * M�todo que pede a inicializa��o da propriedade filtroConsultaGrupo, que � lazy
     * 
     * @param FiltroConsulta Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarFiltroConsultaGrupo(FiltroConsulta objFiltroConsulta) throws DAOException
    {
        inicializarRelacionamento(objFiltroConsulta.getFiltroConsultaGrupo());
    }

    /* M�todo que pede a inicializa��o da propriedade filtroConsultaGrupo, que � lazy
     * 
     * @param List Cont�m objetos do tipo FiltroConsultaGrupo
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarFiltroConsultaGrupo(List lstFiltroConsulta) throws DAOException
    {
        Iterator itrFiltroConsultaGrupo = lstFiltroConsulta.iterator();
        while (itrFiltroConsultaGrupo.hasNext())
        {
            inicializarRelacionamento(((FiltroConsulta) itrFiltroConsultaGrupo.next()).getFiltroConsultaGrupo());
        }
    }

    /**
     * M�todo que pede a inicializa��o da propriedade filtroConsultaUsuario, que � lazy
     * 
     * @param FiltroConsulta Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarFiltroConsultaUsuario(FiltroConsulta objFiltroConsulta) throws DAOException
    {
        inicializarRelacionamento(objFiltroConsulta.getFiltroConsultaUsuario());
        /* TODO
         * C�gigo para fazer a inicializa��o do pojo. O m�todo acima: inicializarRelacionamento n�o est� fazendo
         * a inicializa��o devida. Por isso que foi feito o Iterator da cole��o.
         */
        Iterator itrFiltroConsultaUsuario = objFiltroConsulta.getFiltroConsultaUsuario().iterator();
        while (itrFiltroConsultaUsuario.hasNext())
        {
            String strNome = ((UsuarioSistema) itrFiltroConsultaUsuario.next()).getNome();
        }

    }

    /**
     * Obt�m os grupos dispon�veis para determinado Filtro de Consulta 
     * 
     * @param objFiltroConsulta Filtro de Consulta a ser verificado
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterGruposDisponiveisFiltroConsultaGrupo(FiltroConsulta objFiltroConsulta) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM" + " Grupo grupo" +

        " WHERE" + " grupo.codigo NOT IN" +

        " (SELECT filtroConsulta.filtroConsultaGrupo.elements" + " FROM" + " FiltroConsulta filtroConsulta" + " WHERE" + " filtroConsulta.identificador = "
            + objFiltroConsulta.getIdentificador() + ")" +

            " ORDER BY" + " grupo.descricao ASC";

        return obter(strConsulta);

    }

    /**
     * Obt�m os grupos dispon�veis para determinado Filtro de Consulta 
     * 
     * @param objFiltroConsulta Filtro de Consulta a ser verificado
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterGruposDisponiveisCriterioConsulta(FiltroConsulta objFiltroConsulta) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM" + " Grupo grupo" +

        " WHERE" + " grupo.codigo NOT IN" +

        " (SELECT filtroConsulta.grupoCriterioConsulta.elements" + " FROM" + " FiltroConsulta filtroConsulta" + " WHERE" + " filtroConsulta.identificador = "
            + objFiltroConsulta.getIdentificador() + ")" +

            " ORDER BY" + " grupo.descricao ASC";

        return obter(strConsulta);

    }

    /**
     * Obt�m os grupos dispon�veis para determinado Filtro de Consulta
     * 
     * @param objFiltroConsulta Filtro de Consulta a ser verificado
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterGruposSelecionadosFiltroConsultaGrupo(FiltroConsulta objFiltroConsulta) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM" + " Grupo grupo" +

        " WHERE" + " grupo.codigo IN" +

        " (SELECT filtroConsulta.filtroConsultaGrupo.elements" + " FROM" + " FiltroConsulta filtroConsulta" + " WHERE" + " filtroConsulta.identificador = "
            + objFiltroConsulta.getIdentificador() + ")" +

            " ORDER BY" + " grupo.descricao ASC";

        return obter(strConsulta);

    }

    /**
     * Obt�m os grupos dispon�veis para determinado Filtro de Consulta
     * 
     * @param objFiltroConsulta Filtro de Consulta a ser verificado
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterGruposSelecionadosCriterioConsulta(FiltroConsulta objFiltroConsulta) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " FROM" + " Grupo grupo" +

        " WHERE" + " grupo.codigo IN" +

        " (SELECT filtroConsulta.grupoCriterioConsulta.elements" + " FROM" + " FiltroConsulta filtroConsulta" + " WHERE" + " filtroConsulta.identificador = "
            + objFiltroConsulta.getIdentificador() + ")" +

            " ORDER BY" + " grupo.descricao ASC";

        return obter(strConsulta);

    }

    /**
     *  M�todo que retorna os grupos habilitados ao usu�rio passado como par�metro 
     * 
     * @param String Identificador do usu�rio
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public String obterGruposHabilitadosConsultaCurriculo(String strIdentificadorUsuario) throws DAOException
    {
        String strGrupos = ", ";

        // Obt�m usu�rio logado
        UsuarioSistemaDAO objUsuario = new UsuarioSistemaDAO();
        UsuarioSistema objUsuarioSistema = (UsuarioSistema) objUsuario.obterPelaChave(strIdentificadorUsuario);
        List lstGrupoCriterioConsulta = null;

        // Obt�m grupos de consulta para o usu�rio
        if (objUsuarioSistema != null)
        {
            lstGrupoCriterioConsulta = obterCriterioConsultaPorUsuario(objUsuarioSistema.getIdentificador().toString(), "ConsultaCurriculo");
            if (lstGrupoCriterioConsulta != null)
            {
                Iterator itrGrupoCriterioConsulta = lstGrupoCriterioConsulta.iterator();
                while (itrGrupoCriterioConsulta.hasNext())
                {
                    Grupo objGrupoCriterio = (Grupo) itrGrupoCriterioConsulta.next();
                    if (strGrupos.indexOf(", " + objGrupoCriterio.getCodigo() + ",") < 0)
                    {
                        strGrupos += objGrupoCriterio.getCodigo() + ", ";
                    }
                }
            }
            if (objUsuarioSistema.getGrupo() != null)
            {
                lstGrupoCriterioConsulta = obterCriterioConsultaPorGrupo(objUsuarioSistema.getGrupo().getCodigo().toString(), "ConsultaCurriculo");
                if (lstGrupoCriterioConsulta != null)
                {
                    Iterator itrGrupoCriterioConsulta = lstGrupoCriterioConsulta.iterator();
                    while (itrGrupoCriterioConsulta.hasNext())
                    {
                        Grupo objGrupoCriterio = (Grupo) itrGrupoCriterioConsulta.next();
                        if (strGrupos.indexOf(", " + objGrupoCriterio.getCodigo() + ",") < 0)
                        {
                            strGrupos += objGrupoCriterio.getCodigo() + ", ";
                        }
                    }
                }
            }
        }

        // Verifica se existe algum grupo para a consulta
        if (", ".equals(strGrupos))
        {
            throw new DAOException("O usu�rio n�o tem permiss�o para visualizar nenhum grupo");
        }
        else
        {
            strGrupos = strGrupos.substring(2, strGrupos.length() - 2);
        }

        return strGrupos;
    }

    /**
     * M�todo que pede a inicializa��o da propriedade filtroConsultaUsuario, que � lazy
     * 
     * @param UsuarioSistema Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
/*    public void inicializarFiltroConsultaUsuario(UsuarioSistema objUsuarioSistema) throws DAOException
    {
        inicializarRelacionamento(objUsuarioSistema.getFiltroConsultaUsuario());
    }*/
    
    /**
     *  M�todo que pede a inicializa��o da propriedade filtroConsultaUsuario, que � lazy
     * 
     * @param List  Lista de objetos que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
/*    public void inicializarFiltroConsultaUsuario(List lstFiltroConsultaUsuario) throws DAOException
    {
        Iterator itrFiltroConsultaUsuario = lstFiltroConsultaUsuario.iterator();
        while (itrFiltroConsultaUsuario.hasNext())
        {
            inicializarRelacionamento(((UsuarioSistema) itrFiltroConsultaUsuario.next()).getFiltroConsultaUsuario());
        }
    }
*/
    /**
     * Obt�m todos os crit�rios de consulta (Grupos) associados ao grupo e ao tipo de filtro consulta especificados
     * 
     * @param TipoFiltroConsulta Objeto contendo os dados do tipo de filtro de consulta
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    private List obterCriterioConsultaPorGrupo(String codGrupo, String nomObjetoControladoTipoFiltroConsulta) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " SELECT criterio" + " FROM" + " FiltroConsulta as filtro" + " join filtro.grupoCriterioConsulta as criterio"
            + " join filtro.filtroConsultaGrupo as filtroGrupo" + " join filtro.tipoFiltroConsulta as tipoFiltroConsulta"
            + " WHERE tipoFiltroConsulta.nomeObjetoControlado = '" + nomObjetoControladoTipoFiltroConsulta + "'" + " AND filtroGrupo IN" + " (FROM Grupo grupo"
            + " WHERE grupo.codigo = " + codGrupo + ")";

        return super.obter(strConsulta);
    }

    /**
     * Obt�m todos os crit�rios de consulta (Grupos) associados ao grupo e ao tipo de filtro consulta especificados
     * 
     * @param TipoFiltroConsulta Objeto contendo os dados do tipo de filtro de consulta
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    private List obterCriterioConsultaPorUsuario(String ideUsuario, String nomObjetoControladoTipoFiltroConsulta) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        String strConsulta = " SELECT criterio" + " FROM" + " FiltroConsulta as filtro" + " join filtro.grupoCriterioConsulta as criterio"
            + " join filtro.filtroConsultaUsuario as filtroUsuario" + " join filtro.tipoFiltroConsulta as tipoFiltroConsulta"
            + " WHERE tipoFiltroConsulta.nomeObjetoControlado = '" + nomObjetoControladoTipoFiltroConsulta + "'" + " AND filtroUsuario IN"
            + " (FROM UsuarioSistema usuario" + " WHERE usuario.identificador = " + ideUsuario + ")";

        return super.obter(strConsulta);
    }
}

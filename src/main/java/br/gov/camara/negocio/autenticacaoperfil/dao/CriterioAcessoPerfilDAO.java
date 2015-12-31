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

package br.gov.camara.negocio.autenticacaoperfil.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.CriterioAcessoPerfil;
import br.gov.camara.negocio.exception.DAOException;
import br.gov.camara.negocio.util.Consulta;

/**
 * Classe DAO para a tabela Grupo
 */

public class CriterioAcessoPerfilDAO extends DAO
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(CriterioAcessoPerfilDAO.class);

    /**
     * Construtor sem argumentos
     *
     */
    public CriterioAcessoPerfilDAO()
    {
        super("Criterio Acesso Perfil");
    }

    /**
     * Construtor sem argumentos
     *
     */
    public CriterioAcessoPerfilDAO(String nomeConexao)
    {
        super("Criterio Acesso Perfil", nomeConexao);
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
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" CriterioAcessoPerfil criterioAcessoPerfil");
        strbConsulta.append(" ORDER BY");
        strbConsulta.append(" criterioAcessoPerfil.criterioAcesso.nome ASC");

        return super.obter(strbConsulta.toString());
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

        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" CriterioAcessoPerfil criterioAcessoPerfil");
        strbConsulta.append(" WHERE criterioAcessoPerfil.identificador = ");
        strbConsulta.append(strChave);

        return obter(strbConsulta.toString()).get(0);
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
    public Object obterPelaChave(CriterioAcessoPerfil objCriterioAcessoPerfil) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" CriterioAcessoPerfil criterioAcessoPerfil");
        strbConsulta.append(" WHERE criterioAcessoPerfil.identificador = ");
        strbConsulta.append(objCriterioAcessoPerfil.getIdentificador());

        return obter(strbConsulta.toString()).get(0);
    }

    /**
     * Obt�m o total de registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
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

        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT COUNT(*)");
        strbConsulta.append(" FROM CriterioAcessoPerfil criterioAcessoPerfil");

        // Recupera os dados
        return obterTotalRegistros(strbConsulta.toString());
    }

    /**
     * Obt�m os registros de determinada p�gina
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina) throws DAOException
    {
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" CriterioAcessoPerfil criterioAcessoPerfil");
        strbConsulta.append(" ORDER BY");
        strbConsulta.append(" criterioAcessoPerfil.criterioAcesso.nome ASC");

        // Recupera os dados
        return obterPorPagina(intNumeroPagina, intMaximoPagina, strbConsulta.toString());
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
            StringBuffer strbConsulta = new StringBuffer();
            strbConsulta.append("SELECT COUNT(*) FROM CriterioAcessoPerfil criterioAcessoPerfil");
            if (!strFiltro.trim().equals(""))
            {
                strbConsulta.append(" WHERE ");
                strbConsulta.append(strFiltro);
            }

            // Recupera os dados
            intRetorno = obterTotalRegistros(strbConsulta.toString());
        }
        catch (CDException cde)
        {
            throw new DAOException("Ocorreu um erro ao consultar registros na classe " + strNomeClasse, cde);
        }

        // Retorna
        return intRetorno;
    }

    /**
     * Obt�m todos os registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPeloPerfilSistema(CriterioAcessoPerfil objCriterioAcessoPerfil) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" CriterioAcessoPerfil criterioAcessoPerfil");
        strbConsulta.append(" WHERE criterioAcessoPerfil.perfilSistema.identificador = ");
        strbConsulta.append(objCriterioAcessoPerfil.getPerfilSistema().getIdentificador().intValue());
        strbConsulta.append(" ORDER BY");
        strbConsulta.append(" criterioAcessoPerfil.criterioAcesso.nome ASC");

        return super.obter(strbConsulta.toString());
    }

    /**
     * Obt�m lista de crit�rios
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterCriterios(CriterioAcessoPerfil objCriterioAcessoPerfil) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" CriterioAcessoPerfil criterioAcessoPerfil");
        strbConsulta.append(" WHERE 1 = 1");

        if (objCriterioAcessoPerfil.getPerfilSistema() != null)
        {
            strbConsulta.append(" AND criterioAcessoPerfil.perfilSistema.identificador = ");
            strbConsulta.append(objCriterioAcessoPerfil.getPerfilSistema().getIdentificador().intValue());
        }
        if (objCriterioAcessoPerfil.getCriterioAcesso() != null)
        {
            strbConsulta.append(" AND criterioAcessoPerfil.criterioAcesso.identificador = ");
            strbConsulta.append(objCriterioAcessoPerfil.getCriterioAcesso().getIdentificador().intValue());
        }
        strbConsulta.append(" ORDER BY");
        strbConsulta.append(" criterioAcessoPerfil.criterioAcesso.nome ASC");

        return obter(strbConsulta.toString());
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
    public String excluirImpl(Object objCriterioAcessoPerfil) throws DAOException
    {
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM CriterioAcessoPerfil criterioAcessoPerfil");
        strbConsulta.append(" where criterioAcessoPerfil.identificador =");
        strbConsulta.append(((CriterioAcessoPerfil) objCriterioAcessoPerfil).getIdentificador().intValue());

        return strbConsulta.toString();
    }

    /**
     * M�todo que pede a inicializa��o da propriedade Gestores, que � lazy
     * 
     * @param UsuarioPerfilSistema Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * 
     * @throws DAOException Se ocorrer algum
     * 
     */
    public void inicializarCriterioAcesso(CriterioAcessoPerfil objCriterioAcessoPerfil) throws DAOException
    {
        inicializarRelacionamento(objCriterioAcessoPerfil.getCriterioAcesso());
    }

    /**
     * M�todo que pede a inicializa��o da propriedade EstruturaCEUnidade, que � lazy
     * 
     * @param List Lista de objetos do tipo CargoEfetivo
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * 
     * @throws DAOException Se ocorrer algum erro
     * 
     */
    public void inicializarCriterioAcesso(List lstCriterioAcessoPerfil) throws DAOException
    {

        for (int i = 0; i < lstCriterioAcessoPerfil.size(); i++)
        {
            inicializarCriterioAcesso((CriterioAcessoPerfil) lstCriterioAcessoPerfil.get(i));
        }
    }

    /**
     * Obt�m todos os registros pelo perfil, usu�rio e pre-requisito
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPeloPerfilSistemaPreRequisitoValorado(CriterioAcessoPerfil objCriterioAcessoPerfil) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" CriterioAcessoPerfil criterioAcessoPerfil");
        strbConsulta.append(" WHERE criterioAcessoPerfil.perfilSistema.identificador = ");
        strbConsulta.append(objCriterioAcessoPerfil.getPerfilSistema().getIdentificador().intValue());
        strbConsulta.append(" AND criterioAcessoPerfil.valorCriterioAcessoPerfil IS NOT NULL");
        if ("S".equals(objCriterioAcessoPerfil.getPreRequisito()))
        {
            strbConsulta.append(" AND criterioAcessoPerfil.preRequisito = 'S'");
        }
        else
        {
            strbConsulta.append(" AND criterioAcessoPerfil.preRequisito <> 'S'");
        }

        return super.obter(strbConsulta.toString());
    }

}

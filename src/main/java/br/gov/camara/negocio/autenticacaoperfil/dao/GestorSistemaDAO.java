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

package br.gov.camara.negocio.autenticacaoperfil.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.GestorSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.UsuarioSistema;
import br.gov.camara.negocio.exception.DAOException;

/**
 * 
 * Classe DAO para perfil de sistema
 *
 */

public class GestorSistemaDAO extends DAO
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(DAO.class);

    /**
     * Construtor sem argumentos
     *
     */
    public GestorSistemaDAO()
    {
        super("Gestor do Sistema");
    }

    /**
     * Construtor sem argumentos
     *
     */
    public GestorSistemaDAO(String nomeConexao)
    {
        super("Gestor do Sistema", nomeConexao);
    }

    /**
     * Obtém todos os registros
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
        strbConsulta.append(" GestorSistema gestorSistema");
        strbConsulta.append(" ORDER BY gestorSistema.usuarioSistema.nome ASC");

        // Retorna
        return obter(strbConsulta.toString());
    }

    /**
     * Obtém um registro a partir da chave
     *
     * @param strChave Chave do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public Object obterPelaChave(GestorSistema objGestorSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" GestorSistema gestorSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" gestorSistema.usuarioSistema.identificador = ");
        strbConsulta.append(objGestorSistema.getUsuarioSistema().getIdentificador().intValue());
        strbConsulta.append(" AND gestorSistema.sistema.identificador = ");
        strbConsulta.append(objGestorSistema.getSistema().getIdentificador().intValue());

        // Verifica se há retorno
        List lstResultado = obter(strbConsulta.toString());
        if (!lstResultado.isEmpty())
            return (GestorSistema) lstResultado.get(0);
        else
        {
            return null;
        }
    }

    /**
     * Obtém um registro a partir do objeto controlado
     *
     * @param strObjetoControlado Objeto controlado do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPeloUsuarioSistema(GestorSistema objGestorSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" GestorSistema gestorSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" gestorSistema.usuarioSistema.identificador = ");
        strbConsulta.append(objGestorSistema.getUsuarioSistema().getIdentificador().intValue());

        return obter(strbConsulta.toString());
    }

    /**
     * Obtém um registro a partir do objeto controlado
     *
     * @param strObjetoControlado Objeto controlado do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPeloSistema(GestorSistema objGestorSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" GestorSistema gestorSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" gestorSistema.sistema.identificador = ");
        strbConsulta.append(objGestorSistema.getSistema().getIdentificador().intValue());
        strbConsulta.append(" ORDER BY gestorSistema.usuarioSistema.nome");

        return obter(strbConsulta.toString());
    }

    /**
     * Exclui um registro
     *
     * @param Object POJO representando o objeto a ser excluído
     * 
     * @return String Contendo a consulta de exclusão 
     * 
     * @throws DAOException Se ocorrer algum erro
     * 
     */
    protected String excluirImpl(Object objPOJO) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" GestorSistema gestorSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" gestorSistema.usuarioSistema.identificador = ");
        strbConsulta.append(((GestorSistema) objPOJO).getUsuarioSistema().getIdentificador().intValue());
        strbConsulta.append(" AND gestorSistema.sistema.identificador = ");
        strbConsulta.append(((GestorSistema) objPOJO).getSistema().getIdentificador().intValue());
        
        return strbConsulta.toString();
    }

    /**
     * Método que pede a inicialização da propriedade Gestores, que é lazy
     * 
     * @param GestorSistema Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * 
     * @throws DAOException Se ocorrer algum
     * 
     */
    public void inicializarUsuarioSistema(GestorSistema objGestorSistema) throws DAOException
    {
        inicializarRelacionamento(objGestorSistema.getUsuarioSistema());
    }

    /**
     * Método que pede a inicialização da propriedade EstruturaCEUnidade, que é lazy
     * 
     * @param List Lista de objetos do tipo CargoEfetivo
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * 
     * @throws DAOException Se ocorrer algum erro
     * 
     */
    public void inicializarUsuarioSistema(List lstGestorSistema) throws DAOException
    {

        for (int i = 0; i < lstGestorSistema.size(); i++)
        {
            inicializarUsuarioSistema((GestorSistema) lstGestorSistema.get(i));
        }
    }

    /**
     * Método que pede a inicialização da propriedade Gestores, que é lazy
     * 
     * @param GestorSistema Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * 
     * @throws DAOException Se ocorrer algum
     * 
     */
    public void inicializarSistema(GestorSistema objGestorSistema) throws DAOException
    {
        inicializarRelacionamento(objGestorSistema.getSistema());
    }

    /**
     * Método que pede a inicialização da propriedade EstruturaCEUnidade, que é lazy
     * 
     * @param List Lista de objetos do tipo CargoEfetivo
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * 
     * @throws DAOException Se ocorrer algum erro
     * 
     */
    public void inicializarSistema(List lstGestorSistema) throws DAOException
    {

        for (int i = 0; i < lstGestorSistema.size(); i++)
        {
            inicializarSistema((GestorSistema) lstGestorSistema.get(i));
        }
    }

    public boolean isGestorSistema(GestorSistema objGestorSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" GestorSistema gestorSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" gestorSistema.usuarioSistema.identificador = ");
        strbConsulta.append(objGestorSistema.getUsuarioSistema().getIdentificador().intValue());
        strbConsulta.append(" AND gestorSistema.sistema.identificador = ");
        strbConsulta.append(objGestorSistema.getSistema().getIdentificador().intValue());

        // Verifica se há retorno
        List lstResultado = obter(strbConsulta.toString());
        return (!lstResultado.isEmpty());
    }

    public boolean isAdministradorSistema(GestorSistema objGestorSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" GestorSistema gestorSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" gestorSistema.usuarioSistema.identificador = ");
        strbConsulta.append(objGestorSistema.getUsuarioSistema().getIdentificador().intValue());
        strbConsulta.append(" AND gestorSistema.sistema.identificador = ");
        strbConsulta.append(objGestorSistema.getSistema().getIdentificador().intValue());
        strbConsulta.append(" AND gestorSistema.indicativoAdministrador = 'S'");

        // Verifica se há retorno
        List lstResultado = obter(strbConsulta.toString());
        return (!lstResultado.isEmpty());
    }

    public boolean isGestorDeAlgumSistema(UsuarioSistema objUsuarioSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" GestorSistema gestorSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" gestorSistema.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuarioSistema.getIdentificador().intValue());

        // Verifica se há retorno
        List lstResultado = obter(strbConsulta.toString());
        return (!lstResultado.isEmpty());
    }

    public boolean isAdministradorDeAlgumSistema(UsuarioSistema objUsuarioSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" GestorSistema gestorSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" gestorSistema.usuarioSistema.identificador = ");
        strbConsulta.append(objUsuarioSistema.getIdentificador().intValue());
        strbConsulta.append(" AND gestorSistema.indicativoAdministrador = 'S'");

        // Verifica se há retorno
        List lstResultado = obter(strbConsulta.toString());
        return (!lstResultado.isEmpty());
    }
    
    public List obterGestoresSistemaUsuarioLogado(UsuarioSistema objUsuarioLogado) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" GestorSistema gestorSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" gestorSistema.sistema.identificador IN (");
        strbConsulta.append("   SELECT gestorSistema.sistema.identificador ");
        strbConsulta.append("   FROM GestorSistema gestorSistema ");
        strbConsulta.append("   WHERE gestorSistema.usuarioSistema.identificador = ");
        strbConsulta.append(    objUsuarioLogado.getIdentificador().intValue());
        strbConsulta.append(" )");

        // Verifica se há retorno
        return obter(strbConsulta.toString());
    }

}

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
        strbConsulta.append(" GestorSistema gestorSistema");
        strbConsulta.append(" ORDER BY gestorSistema.usuarioSistema.nome ASC");

        // Retorna
        return obter(strbConsulta.toString());
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

        // Verifica se h� retorno
        List lstResultado = obter(strbConsulta.toString());
        if (!lstResultado.isEmpty())
            return (GestorSistema) lstResultado.get(0);
        else
        {
            return null;
        }
    }

    /**
     * Obt�m um registro a partir do objeto controlado
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
     * Obt�m um registro a partir do objeto controlado
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
     * @param Object POJO representando o objeto a ser exclu�do
     * 
     * @return String Contendo a consulta de exclus�o 
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
     * M�todo que pede a inicializa��o da propriedade Gestores, que � lazy
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
     * M�todo que pede a inicializa��o da propriedade EstruturaCEUnidade, que � lazy
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
     * M�todo que pede a inicializa��o da propriedade Gestores, que � lazy
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
     * M�todo que pede a inicializa��o da propriedade EstruturaCEUnidade, que � lazy
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

        // Verifica se h� retorno
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

        // Verifica se h� retorno
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

        // Verifica se h� retorno
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

        // Verifica se h� retorno
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

        // Verifica se h� retorno
        return obter(strbConsulta.toString());
    }

}

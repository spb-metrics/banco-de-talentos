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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.autenticacaoperfil.pojo.FuncionalidadeSistema;
import br.gov.camara.negocio.autenticacaoperfil.pojo.Sistema;
import br.gov.camara.negocio.exception.DAOException;

/**
 * 
 * Classe DAO para perfil de sistema
 *
 */

public class FuncionalidadeSistemaDAO extends DAO
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(DAO.class);

    /**
     * Construtor sem argumentos
     *
     */
    public FuncionalidadeSistemaDAO()
    {
        super("Funcionalidade Sistema");
    }

    /**
     * @param nomeConexao
     *
     */
    public FuncionalidadeSistemaDAO(String nomeConexao)
    {
        super(nomeConexao);
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
        strbConsulta.append(" FuncionalidadeSistema funcionalidadeSistema");
        strbConsulta.append(" ORDER BY funcionalidadeSistema.nome ASC");

        // Retorna
        return obter(strbConsulta.toString());
    }

    /**
     * Obt�m todas as funcionalidade de um sistema
     * 
     * @param objSistema Sistema a qual a funcionalidade pertence
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPeloSistema(Sistema objSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" FuncionalidadeSistema funcionalidadeSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" funcionalidadeSistema.sistema.identificador =");
        strbConsulta.append(objSistema.getIdentificador());
        strbConsulta.append(" ORDER BY funcionalidadeSistema.nome ASC");

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
    public Object obterPelaChave(FuncionalidadeSistema objFuncionalidadeSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        Object objRetorno = null;
        
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" FuncionalidadeSistema funcionalidadeSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" funcionalidadeSistema.identificador = ");
        strbConsulta.append(objFuncionalidadeSistema.getIdentificador().intValue());

        // Verifica se h� retorno
        List lstResultado = obter(strbConsulta.toString());
        if (!lstResultado.isEmpty()) 
        {
            objRetorno = (FuncionalidadeSistema) lstResultado.get(0);
        }
        return objRetorno;
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
    public FuncionalidadeSistema obterPeloObjetoControlado(String strObjetoControlado) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        
        FuncionalidadeSistema objRetorno = null;

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" FuncionalidadeSistema funcionalidadeSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" funcionalidadeSistema.objetoControlado = '");
        strbConsulta.append(strObjetoControlado.trim());
        strbConsulta.append("'");
        
        // Verifica se h� retorno
        List lstResultado = obter(strbConsulta.toString());
        if (!lstResultado.isEmpty())
        {
            objRetorno = (FuncionalidadeSistema) lstResultado.get(0);
        }
        return objRetorno;
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
    public boolean isObjetoControladoCadastrado(FuncionalidadeSistema objFuncionalidadeSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        boolean blnRetorno = false;
        
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" FuncionalidadeSistema funcionalidadeSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" funcionalidadeSistema.objetoControlado = '");
        strbConsulta.append(objFuncionalidadeSistema.getObjetoControlado().trim());
        strbConsulta.append("'");
        strbConsulta.append(" AND funcionalidadeSistema.identificador <> ");
        strbConsulta.append(objFuncionalidadeSistema.getIdentificador().intValue());

        // Verifica se h� retorno
        List lstResultado = obter(strbConsulta.toString());
        if (!lstResultado.isEmpty())
        {
            blnRetorno = true;
        }
        
        return blnRetorno;
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
    public FuncionalidadeSistema obterPeloObjetoControladoAgrupadorNull(String strObjetoControlado) 
    	throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        FuncionalidadeSistema objRetorno = null;
        
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" FuncionalidadeSistema funcionalidadeSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" funcionalidadeSistema.objetoControlado = '");
        strbConsulta.append(strObjetoControlado);
        strbConsulta.append("'");
        strbConsulta.append(" AND funcionalidadeSistema.funcionalidadeAgrupadora IS NULL");

        // Verifica se h� retorno
        List lstResultado = obter(strbConsulta.toString());
        if (!lstResultado.isEmpty())
        {
            objRetorno = (FuncionalidadeSistema) lstResultado.get(0);
        }
        return objRetorno;
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
    public String excluirImpl(Object objPOJO) throws DAOException
    {
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" FuncionalidadeSistema funcionalidadeSistema");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" funcionalidadeSistema.identificador = ");
        strbConsulta.append(((FuncionalidadeSistema) objPOJO).getIdentificador());
        
        return strbConsulta.toString();
    }

    /**
     * M�todo que pede a inicializa��o da propriedade sistema, que � lazy
     * 
     * @param PerfilSistema Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarSistema(FuncionalidadeSistema objFuncionalidadeSistema) throws DAOException
    {
        inicializarRelacionamento(objFuncionalidadeSistema.getSistema());
    }

    /**
     *  M�todo que pede a inicializa��o da propriedade Sistema, que � lazy
     * 
     * @param List  Lista de objetos que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarSistema(List lstFuncionalidadeSistema) throws DAOException
    {
        Iterator itrFuncionalidadeSistema = lstFuncionalidadeSistema.iterator();
        while (itrFuncionalidadeSistema.hasNext())
        {
            inicializarSistema(((FuncionalidadeSistema) itrFuncionalidadeSistema.next()));
        }
    }
    
    /**
     * M�todo que pede a inicializa��o da propriedade FuncionalidadeAgrupadora, que � lazy
     * 
     * @param PerfilSistema Classe que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarFuncionalidadeAgrupadora(FuncionalidadeSistema objFuncionalidadeSistema) throws DAOException
    {
        inicializarRelacionamento(objFuncionalidadeSistema.getFuncionalidadeAgrupadora());
    }

    /**
     *  M�todo que pede a inicializa��o da propriedade Funcionalidade Agrupadora, que � lazy
     * 
     * @param List  Lista de objetos que tem o atributo que vai ser recuperado
     * 
     * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */
    public void inicializarFuncionalidadeAgrupadora(List lstFuncionalidadeSistema) throws DAOException
    {
        Iterator itrFuncionalidadeSistema = lstFuncionalidadeSistema.iterator();
        while (itrFuncionalidadeSistema.hasNext())
        {
            inicializarFuncionalidadeAgrupadora(((FuncionalidadeSistema) itrFuncionalidadeSistema.next()));
        }
    }
    
    /**
     * Define o m�todo utilizado para contar os registros de uma consulta
     * 
     * @return int Contendo o total de registros da consulta especificada
     * 
     * @throws DAOException Se ocorrer algum erro
     * 
     */
    public int obterTotalRegistros(FuncionalidadeSistema objFuncionalidadeSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" SELECT COUNT(*)");
        strbConsulta.append(" FROM");
        strbConsulta.append(" FuncionalidadeSistema funcionalidadeSistema");
        
        if (objFuncionalidadeSistema.getFuncionalidadeAgrupadora() != null)
        {
            strbConsulta.append(" WHERE funcionalidadeSistema.funcionalidadeAgrupadora.identificador = ");
            strbConsulta.append(objFuncionalidadeSistema.getFuncionalidadeAgrupadora().getIdentificador().intValue());
        }
        else 
        {
            strbConsulta.append(" WHERE funcionalidadeSistema.funcionalidadeAgrupadora.identificador IS NULL ");
            strbConsulta.append(" AND funcionalidadeSistema.sistema.identificador = ");
            strbConsulta.append(objFuncionalidadeSistema.getSistema().getIdentificador().intValue());
        }

        // Recupera os dados
        return obterTotalRegistros(strbConsulta.toString());
    }

    /**
     * Obt�m os registros de determinada p�gina
     *
     * @param intNumeroPagina N�mero da p�gina a ser exibida
     * @param intMaximoPagina N�mero total de registros da p�gina
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina, FuncionalidadeSistema objFuncionalidadeSistema) throws DAOException
    {
        
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" FuncionalidadeSistema funcionalidadeSistema");
        
        if (objFuncionalidadeSistema.getFuncionalidadeAgrupadora() != null)
        {
            strbConsulta.append(" WHERE funcionalidadeSistema.funcionalidadeAgrupadora.identificador = ");
            strbConsulta.append(objFuncionalidadeSistema.getFuncionalidadeAgrupadora().getIdentificador().intValue());
        }
        else 
        {
            strbConsulta.append(" WHERE funcionalidadeSistema.funcionalidadeAgrupadora.identificador IS NULL ");
            strbConsulta.append(" AND funcionalidadeSistema.sistema.identificador = ");
            strbConsulta.append(objFuncionalidadeSistema.getSistema().getIdentificador().intValue());
        }
        
        strbConsulta.append(" ORDER BY funcionalidadeSistema.nome ASC");

        // Recupera os dados
        return obterPorPagina(intNumeroPagina, intMaximoPagina, strbConsulta.toString());
    }
    
    /**
     * Obt�m a hierarquia da funcionalidade sistema
     *
     * @param objFuncionalidadeSistema pojo contendo os par�metros da consulta
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    public List obterHierarquia(FuncionalidadeSistema objFuncionalidadeSistema) throws DAOException
    {
        List lstRetorno  = new ArrayList();
        
        if (objFuncionalidadeSistema.getFuncionalidadeAgrupadora() != null && 
                objFuncionalidadeSistema.getFuncionalidadeAgrupadora().getIdentificador().intValue() < 0) 
        {
            objFuncionalidadeSistema.setFuncionalidadeAgrupadora(null);
        }
        while (objFuncionalidadeSistema.getFuncionalidadeAgrupadora() != null)
        {
            objFuncionalidadeSistema = (FuncionalidadeSistema) obterPelaChave(
                    objFuncionalidadeSistema.getFuncionalidadeAgrupadora());

            if (objFuncionalidadeSistema == null) 
            {
                throw new DAOException("A funcionalidade especificado n�o existe");
            }
            else
            {
                lstRetorno.add(objFuncionalidadeSistema.getIdentificador().intValue() 
                        + ";" + objFuncionalidadeSistema.getNome());
            }
        }
        // Insere hierarquia inicial
        lstRetorno.add("-1;" + objFuncionalidadeSistema.getSistema().getDescricao());

        // Ordena hierarquia
        ListIterator litRetorno = lstRetorno.listIterator();
        lstRetorno = new ArrayList();
        while (litRetorno.hasNext())
        {
            litRetorno.next();
        }
        while (litRetorno.hasPrevious())
        {
            lstRetorno.add(lstRetorno.size() + 1 + ";" + litRetorno.previous());
        }
        
        return lstRetorno;
    }

    /**
     * Obt�m os registros pelo agrupador
     *
     * @param objSistema contendo os par�metros da pesquisa
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    public List obterPelaFuncionalidadeAgrupadora(FuncionalidadeSistema objFuncionalidadeSistema) throws DAOException
    {

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();

        strbConsulta.append(" FROM");
        strbConsulta.append(" FuncionalidadeSistema funcionalidadeSistema");
        strbConsulta.append(" WHERE 1=1");
        
        if (objFuncionalidadeSistema.getSistema() != null)
        {
            strbConsulta.append(" AND funcionalidadeSistema.sistema.identificador = ");
            strbConsulta.append(objFuncionalidadeSistema.getSistema().getIdentificador().intValue());
        }
        if (objFuncionalidadeSistema.getFuncionalidadeAgrupadora() == null)
        {
            strbConsulta.append(" AND funcionalidadeSistema.funcionalidadeAgrupadora.identificador IS NULL");
        }
        else
        {
            strbConsulta.append(" AND funcionalidadeSistema.funcionalidadeAgrupadora.identificador = ");
            strbConsulta.append(objFuncionalidadeSistema.getFuncionalidadeAgrupadora().getIdentificador().intValue());
        }
        // Recupera os dados
        return obter(strbConsulta.toString());
    }

    /**
     * Obt�m os registros pelo agrupador
     *
     * @param objSistema contendo os par�metros da pesquisa
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    public List obterPelaFuncionalidadeAgrupadora(Integer intIdentificador) throws DAOException
    {
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();
        
        strbConsulta.append(" FROM");
        strbConsulta.append(" FuncionalidadeSistema funcionalidadeSistema");

        if (intIdentificador == null)
        {
            strbConsulta.append(" WHERE funcionalidadeSistema.funcionalidadeAgrupadora.identificador IS NULL");
        }
        else
        {
            strbConsulta.append(" WHERE funcionalidadeSistema.funcionalidadeAgrupadora.identificador = ");
            strbConsulta.append(intIdentificador.intValue());
        }
        
        // Recupera os dados
        return obter(strbConsulta.toString());
    }

    /**
     * Obt�m os registros pelo FuncionalidadeSistema agrupador
     *
     * @param objFuncionalidadeSistema contendo os par�metros da pesquisa
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    public List obterPeloSistemaAgrupadorNull(FuncionalidadeSistema objFuncionalidadeSistema) throws DAOException
    {
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();
        
        strbConsulta.append(" FROM");
        strbConsulta.append(" FuncionalidadeSistema funcionalidadeSistema");
        strbConsulta.append(" WHERE funcionalidadeSistema.sistema.identificador = ");
        strbConsulta.append(objFuncionalidadeSistema.getSistema().getIdentificador().intValue());
        strbConsulta.append(" AND funcionalidadeSistema.funcionalidadeAgrupadora IS NULL");

        // Recupera os dados
        return obter(strbConsulta.toString());
    }
    
    /**
     * Obt�m os registros de uma consulta
     *
     * @param intNumeroPagina N�mero da p�gina a ser exibida
     * @param intMaximoPagina N�mero total de registros da p�gina
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro 
     * 
     */
    public List obterPelaConsulta(FuncionalidadeSistema objFuncionalidadeSistema) throws DAOException
    {
        // Monta query
    	StringBuffer strbConsulta = new StringBuffer();
        
        strbConsulta.append(" FROM"); 
        strbConsulta.append(" FuncionalidadeSistema funcionalidadeSistema ");
        strbConsulta.append(" WHERE funcionalidadeSistema.sistema.identificador = "); 
        strbConsulta.append(objFuncionalidadeSistema.getSistema().getIdentificador());
        
        if (objFuncionalidadeSistema.getNome() != null)
        {
        	strbConsulta.append(" AND UPPER(funcionalidadeSistema.nome) LIKE '%"); 
        	strbConsulta.append(objFuncionalidadeSistema.getNome().toUpperCase());
            strbConsulta.append("%' "); 
        }
        if (objFuncionalidadeSistema.getObjetoControlado() != null)
        {
        	strbConsulta.append(" AND UPPER(funcionalidadeSistema.objetoControlado) LIKE '%"); 
        	strbConsulta.append(objFuncionalidadeSistema.getObjetoControlado().toUpperCase());
            strbConsulta.append("%' "); 
        }
        
        strbConsulta.append(" ORDER BY funcionalidadeSistema.nome ASC");

        // Recupera os dados
        return obter(strbConsulta.toString());
    }

    /**
     * Obt�m um registro a partir do objeto controlado
     *
     * @param FuncionalidadeSistema Objeto controlado do registro a ser obtido
     * 
     * @return Object POJO representando o registro obtido
     *
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPeloObjetoControlado(FuncionalidadeSistema objFuncionalidadeSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        // Monta query
        StringBuffer strbConsulta = new StringBuffer();
        
        strbConsulta.append(" FROM"); 
        strbConsulta.append(" FuncionalidadeSistema funcionalidadeSistema ");
        strbConsulta.append(" WHERE"); 
        strbConsulta.append(" funcionalidadeSistema.objetoControlado = '"); 
        strbConsulta.append(objFuncionalidadeSistema.getObjetoControlado()); 
        strbConsulta.append("'"); 

        // Verifica se h� retorno
        return obter(strbConsulta.toString());
    }
    
    public boolean possuiFilhos(FuncionalidadeSistema objFuncionalidadeSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        boolean blnRetorno = false;
        
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();
        
        strbConsulta.append(" FROM"); 
        strbConsulta.append(" FuncionalidadeSistema funcionalidadeSistema ");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" funcionalidadeSistema.funcionalidadeAgrupadora.identificador = "); 
        strbConsulta.append(objFuncionalidadeSistema.getIdentificador().intValue()); 

        // Verifica se h� retorno
        List lstFuncionalidades = obter(strbConsulta.toString());
        
        if (lstFuncionalidades != null && lstFuncionalidades.size() > 0)
        {
        	blnRetorno = true;
        }
        return blnRetorno;
    }
 
    public boolean possuiNetos(FuncionalidadeSistema objFuncionalidadeSistema) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }

        boolean blnRetorno = false;
        
        // Monta query
        StringBuffer strbConsulta = new StringBuffer();
        
        strbConsulta.append(" FROM"); 
        strbConsulta.append(" FuncionalidadeSistema funcionalidadeSistema ");
        strbConsulta.append(" WHERE");
        strbConsulta.append(" funcionalidadeSistema.funcionalidadeAgrupadora.identificador IN ("); 
        strbConsulta.append("   SELECT func.identificador "); 
        strbConsulta.append("   FROM FuncionalidadeSistema func "); 
        strbConsulta.append("   WHERE func.funcionalidadeAgrupadora.identificador = "); 
        strbConsulta.append(    objFuncionalidadeSistema.getIdentificador().intValue()); 
        strbConsulta.append(")"); 

        // Verifica se h� retorno
        List lstFuncionalidades = obter(strbConsulta.toString());
        
        if (lstFuncionalidades != null && lstFuncionalidades.size() > 0)
        {
        	blnRetorno = true;
        }
        return blnRetorno;
    }
    
}

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

import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.exception.DAOException;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalentoOpcao;
import br.gov.camara.negocio.bancotalentos.pojo.AtributoTalentoValorado;
import br.gov.camara.negocio.bancotalentos.pojo.CategoriaAtributoTalento;
import br.gov.camara.negocio.bancotalentos.pojo.Talento;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.negocio.util.Consulta;
import br.gov.camara.negocio.util.ConsultaComum;

import br.gov.camara.exception.CDException;

/**
 * Classe DAO para a tabela AtributoTalentoValorado
 */
public class AtributoTalentoValoradoDAO extends DAO implements ConsultaComum
{
    private static Log log = LogFactory.getLog(AtributoTalentoValoradoDAO.class);
    
	/**
	 * Construtor sem argumentos
	 */
	public AtributoTalentoValoradoDAO()
	{
		super("Valora��es de talento");		
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
        String strConsulta =            
            " FROM" +
            " AtributoTalentoValorado atributoTalentoValorado" +
            " ORDER BY" +
            " atributoTalentoValorado.identificador ASC";
            
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

        String strConsulta = 
            " FROM" +
            " AtributoTalentoValorado atributoTalentoValorado" +
            " WHERE" +
            " atributoTalentoValorado.identificador = " + strChave;
            
        return (AtributoTalentoValorado) super.obter(strConsulta).get(0);
 
	}
	
	/**
	 * Obt�m as valora��es de determinado talento
	 * 
	 * @param objTalento Talento desejado
	 * 
	 * @return List contendo as valora��es
	 * 
	 * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados
	 */
    public List obterPeloTalento(Talento objTalento) throws DAOException
    {   
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
    
        // Monta query
        String strConsulta =
                " FROM" +
                " AtributoTalentoValorado atributoTalentoValorado" +
				
                " WHERE " +
				" atributoTalentoValorado.talento.identificador = " + objTalento.getIdentificador() +
				
                " ORDER BY" +
                " atributoTalentoValorado.categoriaAtributoTalento.sequencialOrdenacao ASC," +
                " atributoTalentoValorado.valoracao ASC";
        
        // Retorna
        return obter(strConsulta);
    }
    

    /**
     * Obt�m quantidade de valora��o de talentos por categoria/atributo
     * 
     * @param objCategoriaAtributoTalento Categoria/atributo desejado
     * 
     * @return int Com o total de registros 
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados 
     */
    public int obterTotalRegistrosPorCategoriaAtributoTalento(
            CategoriaAtributoTalento objCategoriaAtributoTalento) 
    		throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }            

        // Monta query
        String strConsulta =            
            " SELECT" +
            " COUNT(*)" +
            
            " FROM" +
            " AtributoTalentoValorado atributoTalentoValorado" +
			
            " WHERE " +
            " atributoTalentoValorado.categoriaAtributoTalento.identificador = " 
					+ objCategoriaAtributoTalento.getIdentificador();
        
        // Retorna
         return obterTotalRegistros(strConsulta);          
    }

    /**
     * Obt�m valora��o por talento e categoria/atributo de talento
     * 
     * @param objTalento Talento desejado
     * @param objCategoriaAtributoTalento Categoria/atributo desejado
     * 
     * @return Object contendo a valora��o 
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados 
     */
    public Object obterPorTalentoCategoriaAtributoTalento(Talento objTalento, 
    		CategoriaAtributoTalento objCategoriaAtributoTalento) 
    		throws DAOException
    {   
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }            

        // Monta query
        String strConsulta =            
            " FROM" +
            " AtributoTalentoValorado atributoTalentoValorado" +
			
            " WHERE " +
			" atributoTalentoValorado.talento.identificador = " + objTalento.getIdentificador() +
            " AND atributoTalentoValorado.categoriaAtributo.identificador = " 
					+ objCategoriaAtributoTalento.getIdentificador() +
					
            " ORDER BY" +
            " atributoTalentoValorado.identificador ASC";
        
        // Retorna
         return (AtributoTalentoValorado) super.obter(strConsulta).get(0);          
    }

    /**
     * Obt�m valora��es pela op��o
     * 
     * @param objAtributoTalentoOpcao Op��o de atributo de talento para recupera��o
     * 
     * @return List Contendo as valora��es 
     * 
     * @throws DAOException se ocorrer algum erro relacionado com o acesso a banco de dados 
     */
    public List obterPorCategoriaAtributoTalentoOpcao(AtributoTalentoOpcao objAtributoTalentoOpcao) 
    		throws DAOException
    {   
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }            

        // Monta query
        String strConsulta =            
            " FROM" +
            " AtributoTalentoValorado atributoTalentoValorado" +
			
            " WHERE " +
            " atributoTalentoValorado.atributoTalentoOpcao.identificador = " 
					+ objAtributoTalentoOpcao.getIdentificador() +
					
            " ORDER BY" +
            " atributoTalentoValorado.identificador ASC";
        
        // Retorna
         return  obter(strConsulta);          
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
    public String excluirImpl(Object objAtributoTalentoValorado)
        throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        
        // Retorna consulta de exclus�o
        return            
            " FROM" +
            " AtributoTalentoValorado atributoTalentoValorado" +
			
            " WHERE" +
            " atributoTalentoValorado.identificador = " + ((AtributoTalentoValorado) objAtributoTalentoValorado).getIdentificador();    }

    /**
     * Exclui registros pela sua categoria/atributo
     *
     * @param CategoriaAtributoTalento Com a categoria/atributo desejado
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public void excluirPorCategoriaAtributoTalento(CategoriaAtributoTalento objCategoriaAtributoTalento)
        throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        
        // Monta consulta de exclus�o
        String strConsulta =
            " FROM" +
            " AtributoTalentoValorado atributoTalentoValorado" +
			
            " WHERE" +
            " atributoTalentoValorado.categoriaAtributoTalento.identificador = " + 
            objCategoriaAtributoTalento.getIdentificador();
        
        //Executa
        excluir(strConsulta);
    }

    /**
     * Exclui registros pelo seu talento
     *
     * @param Talento Com o talento desejado
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public void excluirPorTalento(Talento objTalento)
        throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        
        // Monta consulta de exclus�o
        String strConsulta =
            " FROM" +
            " AtributoTalentoValorado atributoTalentoValorado" +
			
            " WHERE" +
            " atributoTalentoValorado.talento.identificador = " + 
            objTalento.getIdentificador();
        
        //Executa
        excluir(strConsulta);
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
            String strConsulta =
                    " SELECT COUNT(*)" +
                    " FROM" +
                    " AtributoTalentoValorado atributoTalentoValorado";
            if (!strFiltro.trim().equals(""))
            {
                strConsulta += 
                    " WHERE " + strFiltro; 
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
     * M�todo utilizado para efetuar consultas gen�ricas, por p�gina
     * 
     * @param objConsulta Objeto de consulta contendo os par�metros necess�rios para montar a query
     * @param intNumeroPagina N�mero da p�gina a ser exibida
     * @param intMaximoPagina N�mero total de registros da p�gina
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */ 
    public List consultar(Consulta objConsulta, int intNumeroPagina, int intMaximoPagina) 
    		throws DAOException
	{
	    if (log.isDebugEnabled())
	    {
	        log.debug("Entrada no metodo");
	    }
	    // Declara��es
	    List lstRetorno = null;        
	     
	    try
	    {
	        String strFiltro = objConsulta.montarFiltro();
	
	        // Monta query
	        String strConsulta =            
	                " FROM" +
	                " AtributoTalentoValorado atributoTalentoValorado";
	        if (!strFiltro.trim().equals("")) 
	        {
	            strConsulta += 
	                " WHERE " + strFiltro;
	        }
	        strConsulta += 
	                " ORDER BY" +
	                " atributoTalentoValorado.identificador ASC";
	                                
	        // Recupera os dados 
	        lstRetorno = obterPorPagina(intNumeroPagina, intMaximoPagina, strConsulta);
	        
	        // Retorna
	        return lstRetorno;
	    }
	    catch(CDException cde)
	    {
	        throw new DAOException("Ocorreu um erro ao consultar registros na classe " + strNomeClasse, cde);
	    }
	}

	/**
	 *  M�todo que pede a inicializa��o da propriedade talento, que � lazy
	 * 
	 * @param objAtributoTalentoValorado Objeto que tem o atributo que vai ser recuperado
	 * 
	 * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
	 * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
	 */	
	public void inicializarTalento(AtributoTalentoValorado objAtributoTalentoValorado) 
			throws DAOException
	{
		inicializarRelacionamento(objAtributoTalentoValorado.getTalento());
	}

	/**
	 *  M�todo que pede a inicializa��o da propriedade talento, que � lazy
	 * 
	 * @param List lstAtributosTalentoValorados Lista com os objetos que t�m o atributo que vai ser recuperado
	 * 
	 * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
	 * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
	 */	
	public void inicializarTalento(List lstAtributosTalentoValorados) 
			throws DAOException
	{
	    Iterator itrAtributosTalentoValorados = lstAtributosTalentoValorados.iterator();
	    while (itrAtributosTalentoValorados.hasNext())
	    {
			inicializarTalento((AtributoTalentoValorado) itrAtributosTalentoValorados.next());
	    }
	}

	/**
	 *  M�todo que pede a inicializa��o da propriedade categoriaAtributoTalento, que � lazy
	 * 
	 * @param AtributoTalentoValorado objAtributoTalentoValorado Classe que tem o atributo que vai ser recuperado
	 * 
	 * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
	 * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
	 */	
	public void inicializarCategoriaAtributoTalento(AtributoTalentoValorado objAtributoTalentoValorado) 
			throws DAOException
	{
		inicializarRelacionamento(objAtributoTalentoValorado.getCategoriaAtributoTalento());
	}

	/**
	 *  M�todo que pede a inicializa��o da propriedade categoriaAtributoTalento, que � lazy
	 * 
	 * @param List lstAtributosTalentoValorados Lista com os objetos que t�m o atributo que vai ser recuperado
	 * 
	 * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
	 * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
	 */	
	public void inicializarCategoriaAtributoTalento(List lstAtributosTalentoValorados) 
			throws DAOException
	{
	    Iterator itrAtributosTalentoValorados = lstAtributosTalentoValorados.iterator();
	    while (itrAtributosTalentoValorados.hasNext())
	    {
			inicializarCategoriaAtributoTalento((AtributoTalentoValorado) itrAtributosTalentoValorados.next());
	    }
	}

	/**
	 *  M�todo que pede a inicializa��o da propriedade atributoTalentoOpcao, que � lazy
	 * 
	 * @param objAtributoTalentoValorado Objeto que tem o atributo que vai ser recuperado
	 * 
	 * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
	 * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
	 */	
	public void inicializarAtributoTalentoOpcao(AtributoTalentoValorado objAtributoTalentoValorado) throws DAOException
	{
		inicializarRelacionamento(objAtributoTalentoValorado.getAtributoTalentoOpcao());
	}

	/**
	 *  M�todo que pede a inicializa��o da propriedade atributoTalentoOpcao, que � lazy
	 * 
	 * @param lstAtributosTalentoValorados Lista com os objetos que t�m o atributo que vai ser recuperado
	 * 
	 * @see br.gov.camara.negocio.DAO#inicializarRelacionamento(java.lang.Object)
	 * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
	 */	
	public void inicializarAtributoTalentoOpcao(List lstAtributosTalentoValorados) throws DAOException
	{
	    Iterator itrAtributosTalentoValorados = lstAtributosTalentoValorados.iterator();
	    while (itrAtributosTalentoValorados.hasNext())
	    {
			inicializarAtributoTalentoOpcao((AtributoTalentoValorado)itrAtributosTalentoValorados.next());
	    }
	}
}

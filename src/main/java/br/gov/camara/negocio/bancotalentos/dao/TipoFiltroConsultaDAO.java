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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.bancotalentos.pojo.TipoFiltroConsulta;
import br.gov.camara.negocio.exception.DAOException;
import br.gov.camara.negocio.util.Consulta;
import br.gov.camara.negocio.util.ConsultaComum;

/**
 * Classe DAO para a tabela Talento
 */
public class TipoFiltroConsultaDAO extends DAO implements ConsultaComum
{
    private static Log log = LogFactory.getLog(TipoFiltroConsultaDAO.class);
    
	/**
     * Construtor sem argumentos
	 */
	public TipoFiltroConsultaDAO()
	{
		super("TipoFiltroConsulta");
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
            " TipoFiltroConsulta tipoFiltroConsulta" +
			
            " ORDER BY" +
            " tipoFiltroConsulta.identificador ASC";
            
        return obter(strConsulta);
 
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
        
        // Monta consulta
        String strConsulta = 
            " FROM TipoFiltroConsulta tipoFiltroConsulta" +
            
            " WHERE " +
            "tipoFiltroConsulta.identificador = " + strChave;
        
        // Efetua consulta
        List lstResultado = obter(strConsulta);
        if (!lstResultado.isEmpty())
        {
            return lstResultado.get(0);                
        }
        else
        {
            return null;
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
    public String excluirImpl(Object objTipoFiltroConsulta)
        throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        
        // Retorna consulta de exclus�o
        return            
                " FROM" +
                " TipoFiltroConsulta tipoFiltroConsulta" +
                
                " WHERE" +
                " tipoFiltroConsulta.identificador = " + 
				((TipoFiltroConsulta) objTipoFiltroConsulta).getIdentificador();
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
    public int obterTotalRegistros()
    		throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        // Declara��es
        int intRetorno = 0;

        try
        {
            // Monta query
            String strConsulta =
                    " SELECT COUNT(*)" +
                    " FROM" +
                    " TipoFiltroConsulta tipoFiltroConsulta";

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
     * Obt�m TipoFiltroConsulta de acordo com filtro passado
     *
     * @param int N�mero da p�gina a ser mostrada
     * @param int Quantidade de registros
     * @param Consulta objeto contendo os filtros para consulta
     *  
     * @return List contendo o resultado da pesquisa
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List consultar(Consulta objConsulta, int intNumeroPagina, int intMaximoPagina) 
        	throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        // Declara��es
        List     lstRetorno = null;        
         
        try
        {
            String strFiltro = objConsulta.montarFiltro();

            // Monta query
            String strConsulta =            
                    " FROM" +
                    " TipoFiltroConsulta tipoFiltroConsulta";
            if (!strFiltro.trim().equals("")) 
            {
                strConsulta += 
                    " WHERE " + strFiltro;
            }
            strConsulta += 
                    " ORDER BY" +
                    " tipoFiltroConsulta.identificador ASC";
                                    
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
     * Obt�m os registros de determinada p�gina
     * 
     * @param int N�mero da p�gina a ser mostrada
     * @param int Quantidade de registros
     * 
     * @return List Contendo os POJOs representando os registro obtidos
     *
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     * 
     */
    public List obterPorPagina(int intNumeroPagina, int intMaximoPagina) 
            throws DAOException
    {
        // Monta consulta
        String strConsulta =            
                " FROM" +
                " TipoFiltroConsulta tipoFiltroConsulta" +
				
                " ORDER BY" +
                " tipoFiltroConsulta.identificador ASC";
        
        // Recupera os dados
        return obterPorPagina(intNumeroPagina, intMaximoPagina, strConsulta); 
    }

	/* (non-Javadoc)
	 * @see br.gov.camara.negocio.util.ConsultaComum#obterTotalRegistros(br.gov.camara.negocio.util.Consulta)
	 */
	public int obterTotalRegistros(Consulta objConsulta) throws CDException {
		// TODO Auto-generated method stub
		return 0;
	}
}

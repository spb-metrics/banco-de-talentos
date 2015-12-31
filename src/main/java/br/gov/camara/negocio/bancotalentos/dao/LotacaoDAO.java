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

import br.gov.camara.negocio.DAO;
import br.gov.camara.negocio.bancotalentos.pojo.Lotacao;
import br.gov.camara.negocio.exception.DAOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

import br.gov.camara.exception.CDException;
import br.gov.camara.negocio.util.Consulta;
import br.gov.camara.negocio.util.ConsultaComum;

/**
 * Classe DAO para a tabela SigespPessoalLotacao 
 *  
 */
public class LotacaoDAO extends DAO implements ConsultaComum 
{
    // Inicializa o log
    private static Log log = LogFactory.getLog(LotacaoDAO.class);

    /**
     * Construtor sem argumentos
     */
    public LotacaoDAO()
    {
        super("Lota��es");
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
            " Lotacao lotacao" +
			
			" ORDER BY" +
            " lotacao.inicio ASC";
        
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

        String strConsulta = 
            " FROM" +
            " LotacaoDAO lotacao" +
			
            " WHERE " +
			"lotacao.identificador = " + strChave;
            
        return (Lotacao) super.obter(strConsulta).get(0);
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
    public String excluirImpl(Object objLotacao)
        throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        
        // Retorna consulta de exclus�o
        return            
                " FROM" +
                " Lotacao lotacao" +
				
                " WHERE" +
                " lotacao.identificador = " + 
						((Lotacao) objLotacao).getIdentificador();
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
                    " Lotacao lotacao";
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
                    " objLotacao lotacao";
            if (!strFiltro.trim().equals("")) 
            {
                strConsulta += 
                    " WHERE " + strFiltro;
            }
            strConsulta += 
                    " ORDER BY" +
                    " lotacao.inicio ASC";
                                    
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
}

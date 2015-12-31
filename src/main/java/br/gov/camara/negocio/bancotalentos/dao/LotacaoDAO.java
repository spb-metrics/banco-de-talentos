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
        super("Lotações");
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
        String strConsulta = 
        	" FROM" +
            " Lotacao lotacao" +
			
			" ORDER BY" +
            " lotacao.inicio ASC";
        
        return obter(strConsulta);
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
     * @param Object POJO representando o objeto a ser excluído
     * 
     * @return String Contendo a consulta de exclusão 
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
        
        // Retorna consulta de exclusão
        return            
                " FROM" +
                " Lotacao lotacao" +
				
                " WHERE" +
                " lotacao.identificador = " + 
						((Lotacao) objLotacao).getIdentificador();
    }

    /**
     * Método utilizado para efetuar consultas genéricas, por página
     * 
     * @param objConsulta Objeto de consulta contendo os parâmetros necessários para montar a query
     * @param intNumeroPagina Número da página a ser exibida
     * @param intMaximoPagina Número total de registros da página
     * 
     * @throws DAOException Se ocorrer algum erro relacionado com o acesso a banco de dados
     */ 
    public int obterTotalRegistros(Consulta objConsulta) throws DAOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Entrada no metodo");
        }
        // Declarações
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
     * Método utilizado para efetuar consultas genéricas, por página
     * 
     * @param objConsulta Objeto de consulta contendo os parâmetros necessários para montar a query
     * @param intNumeroPagina Número da página a ser exibida
     * @param intMaximoPagina Número total de registros da página
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
        // Declarações
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
